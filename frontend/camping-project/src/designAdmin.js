const themeSelector = document.getElementById("themeSelector");
const applyBtn = document.getElementById("applyBtn");
const resetBtn = document.getElementById("resetBtn");
const toast = document.getElementById("toast");

let themes = {};
let loaded = false;

// Helper: toast
function showToast(message, short = true) {
    toast.innerText = message;
    toast.classList.add("show");
    setTimeout(() => toast.classList.remove("show"), short ? 2000 : 4000);
}

// Enable/disable controls
function setControlsEnabled(enabled) {
    themeSelector.disabled = !enabled;
    applyBtn.disabled = !enabled;
    resetBtn.disabled = !enabled;
}

function applyTheme(themeName) {
    if (!loaded) return;

    const theme = themes[themeName];
    if (!theme) {
        console.warn(`Theme '${themeName}' not found.`);
        return;
    }

    Object.entries(theme).forEach(([key, value]) => {
        document.documentElement.style.setProperty(key, value);
    });


    localStorage.setItem("selectedTheme", themeName);
    showToast(`Thema '${themeName}' toegepast`);

}

function resetTheme() {
    if (!loaded) return;
    applyTheme("default");
    themeSelector.value = "default";
}

async function loadThemes() {
    setControlsEnabled(false);
    themeSelector.innerHTML = ""; // clear dropdown

    try {
        const response = await fetch("http://localhost:8080/sds2_project_2025_404_finders_war_exploded/api/themes");
        if (!response.ok) throw new Error("Failed to load themes");

        themes = await response.json();

        // dropdown vullen
        Object.keys(themes).forEach(themeName => {
            const option = document.createElement("option");
            option.value = themeName;
            option.textContent = themeName.charAt(0).toUpperCase() + themeName.slice(1);
            themeSelector.appendChild(option);
        });

        loaded = true;

        const savedTheme = localStorage.getItem("selectedTheme") || "default";
        if (themes[savedTheme]) {
            themeSelector.value = savedTheme;
            applyTheme(savedTheme);
        } else {
            themeSelector.value = "default";
            applyTheme("default");
        }

    } catch (err) {
        console.error("Error loading themes:", err);

        // fallback
        themes = {
            default: {
                "--bg": "#f8fafc",
                "--text": "#0f172a",
                "--card-bg": "#ffffff",
                "--accent": "#2563eb",
                "--muted": "#6b7280"
            }
        };
        themeSelector.innerHTML = `<option value="default">Default</option>`;
        themeSelector.value = "default";
        applyTheme("default");
        loaded = true;

    } finally {
        setControlsEnabled(true);
    }
}

applyBtn.addEventListener("click", () => {
    applyTheme(themeSelector.value)
    location.reload()
});

resetBtn.addEventListener("click", resetTheme);

loadThemes();

