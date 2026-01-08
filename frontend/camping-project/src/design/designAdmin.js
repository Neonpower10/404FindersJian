// Admin-only guard
const role = sessionStorage.getItem("role");
if (role !== "admin") {
    window.location.href = "/index.html";
}

const $ = id => document.getElementById(id);
const themeSelector = $("themeSelector");
const applyBtn = $("applyBtn");
const deleteBtn = $("deleteBtn");
const toast = $("toast");
const customThemeName = $("customThemeName");
const customBgColor = $("customBgColor");
const customAccentColor = $("customAccentColor");
const customCardColor = $("customCardColor");
const saveCustomThemeBtn = $("saveCustomThemeBtn");

let themes = {};
let loaded = false;
const BASE_URL = "http://localhost:8080/sds2_project_2025_404_finders_war_exploded/api/themes";
const DEFAULT_THEMES = ["default", "forest - standard", "sunset - standard", "midnight - standard", "ocean - standard"];

const showToast = (msg, short = true) => {
    toast.textContent = msg;
    toast.classList.add("show");
    setTimeout(() => toast.classList.remove("show"), short ? 2000 : 6000);
};

const setControlsEnabled = enabled => {
    [themeSelector, applyBtn, deleteBtn, saveCustomThemeBtn]
        .filter(Boolean)
        .forEach(el => (el.disabled = !enabled));
};

const getContrastColor = (hex) => {
    const rgb = hex.match(/\w\w/g).map(v => parseInt(v, 16) / 255);

    const luminance = rgb.map(v =>
        v <= 0.03928
            ? v / 12.92
            : Math.pow((v + 0.055) / 1.055, 2.4)
    );

    const L = (0.2126 * luminance[0]) +
        (0.7152 * luminance[1]) +
        (0.0722 * luminance[2]);

    // Contrast ratio relative to white and black
    const contrastWhite = (1.05) / (L + 0.05);
    const contrastBlack = (L + 0.05) / 0.05;

    return contrastWhite >= contrastBlack ? "#ffffff" : "#000000";
};


const applyTheme = name => {
    if (!loaded || !themes[name]) return console.warn(`Theme '${name}' not found.`);
    Object.entries(themes[name]).forEach(([k, v]) => document.documentElement.style.setProperty(k, v));
    localStorage.setItem("selectedTheme", name);
    showToast(`Thema '${name}' toegepast`);
};

const populateThemeSelector = () => {
    themeSelector.innerHTML = Object.keys(themes)
        .map(n => `<option value="${n}">${n[0].toUpperCase() + n.slice(1)}</option>`)
        .join("");
};

const loadThemes = async () => {
    setControlsEnabled(false);
    try {
        const token = sessionStorage.getItem("jwt");
        const res = await fetch(BASE_URL, {
            headers: {
                "Accept": "application/json",
                "Authorization": token ? `Bearer ${token}` : ""
            }
        });
        if (!res.ok) {
            const err = await res.json();
            showToast(err.error || "Er is een fout opgetreden bij opslaan");
            return;
        }

        themes = await res.json();
        Object.assign(themes, JSON.parse(localStorage.getItem("customThemes") || "{}"));
        populateThemeSelector();

        const selected = localStorage.getItem("selectedTheme");
        const themeName = themes[selected] ? selected : "default";
        themeSelector.value = themeName;
        applyTheme(themeName);
    } catch (e) {
        console.error("Error loading themes:", e);
        themes = {
            default: {
                "--bg": "#f8fafc",
                "--text": "#0f172a",
                "--card-bg": "#ffffff",
                "--navbar-bg": "#2563eb",
                "--accent": "#2563eb",
                "--accent-text": "#ffffff",
                "--card-text": "#6b7280"
            }
        };
        populateThemeSelector();
        applyTheme("default");
    } finally {
        loaded = true;
        setControlsEnabled(true);
    }
};

const saveCustomTheme = async () => {
    const name = customThemeName.value.trim().toLowerCase();
    if (!name) return showToast("Voer een naam in voor je thema");
    if (themes[name]) return showToast("Een thema met die naam bestaat al!");

    const bg = customBgColor.value;
    const accent = customAccentColor.value;
    const card = customCardColor.value;
    const navbar = $("customNavbarBg").value;

    const newTheme = {
        "--bg": bg,
        "--text": getContrastColor(bg),
        "--card-bg": card,
        "--navbar-bg": navbar,
        "--accent": accent,
        "--accent-text": getContrastColor(accent),
        "--card-text": getContrastColor(card)
    };

    try {
        const token = sessionStorage.getItem("jwt");
        const res = await fetch(BASE_URL, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": token ? `Bearer ${token}` : ""
            },
            body: JSON.stringify({ name, variables: newTheme })
        });

        if (!res.ok) {
            const err = await res.json();
            showToast(err.error || "Er is een fout opgetreden bij opslaan");
            return;
        }

        themes[name] = newTheme;
        populateThemeSelector();
        themeSelector.value = name;
        applyTheme(name);
        showToast(`Nieuw thema '${name}' opgeslagen!`);
    } catch (e) {
        console.error(e);
        showToast("Kon thema niet opslaan (backend niet bereikbaar?)", false);
    }
};

const deleteTheme = async () => {
    const name = themeSelector.value;
    if (DEFAULT_THEMES.includes(name)) return showToast("Standaardthema's kunnen niet worden verwijderd");
    if (!confirm(`Weet je zeker dat je '${name}' wilt verwijderen?`)) return;

    try {
        const token = sessionStorage.getItem("jwt");
        const res = await fetch(`${BASE_URL}/${name}`, {
            method: "DELETE",
            headers: {
                "Authorization": token ? `Bearer ${token}` : ""
            }
        });
        if (!res.ok) {
            const err = await res.json();
            showToast(err.error || "Er is een fout opgetreden bij opslaan");
            return;
        }

        delete themes[name];
        populateThemeSelector();
        themeSelector.value = "default";
        applyTheme("default");
        showToast(`Thema '${name}' verwijderd`);
    } catch (e) {
        console.error(e);
        showToast("Kon thema niet verwijderen", false);
    }
};

const loadThemeForEditing = () => {
    const name = themeSelector.value;
    if (!themes[name]) return showToast("Selecteer eerst een bestaand thema");

    const theme = themes[name];

    $("customThemeName").value = name;
    $("customBgColor").value = theme["--bg"] || "#ffffff";
    $("customAccentColor").value = theme["--accent"] || "#2563eb";
    $("customCardColor").value = theme["--card-bg"] || "#f3f4f6";
    $("customNavbarBg").value = theme["--navbar-bg"] || "#2563eb";

    showToast(`Thema '${name}' geladen voor bewerking`);
};

const updateCustomTheme = async () => {
    const oldName = themeSelector.value;
    const newName = customThemeName.value.trim().toLowerCase();

    if (!themes[oldName]) return showToast("Selecteer een bestaand thema om te bewerken");
    if (!newName) return showToast("Voer een geldige naam in");
    if (newName !== oldName && themes[newName]) return showToast("Een thema met die naam bestaat al!");

    const bg = customBgColor.value;
    const accent = customAccentColor.value;
    const card = customCardColor.value;
    const navbar = $("customNavbarBg").value;

    const updatedTheme = {
        "--bg": bg,
        "--text": getContrastColor(bg),
        "--card-bg": card,
        "--navbar-bg": navbar,
        "--accent": accent,
        "--accent-text": getContrastColor(accent),
        "--card-text": getContrastColor(card)
    };

    try {
        const token = sessionStorage.getItem("jwt");
        const res = await fetch(`${BASE_URL}/${oldName}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                "Authorization": token ? `Bearer ${token}` : ""
            },
            body: JSON.stringify({ newName, variables: updatedTheme })
        });

        if (!res.ok) {
            const err = await res.json();
            showToast(err.error || "Fout bij bijwerken van thema");
            return;
        }

        delete themes[oldName];
        themes[newName] = updatedTheme;

        populateThemeSelector();
        themeSelector.value = newName;
        applyTheme(newName);
        showToast(`Thema '${newName}' bijgewerkt!`);
    } catch (e) {
        console.error(e);
        showToast("Kon thema niet bijwerken (backend niet bereikbaar?)", false);
    }
};


applyBtn.addEventListener("click", () => {
    applyTheme(themeSelector.value);
    location.reload();
});
deleteBtn.addEventListener("click", deleteTheme);
saveCustomThemeBtn.addEventListener("click", saveCustomTheme);
$("editThemeBtn").addEventListener("click", loadThemeForEditing);
$("updateThemeBtn").addEventListener("click", updateCustomTheme);


void loadThemes();
