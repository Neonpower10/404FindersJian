/**
 * Loads the saved theme from backend and applies it globally.
 */
(async function loadAndApplyTheme() {
    const savedTheme = localStorage.getItem("selectedTheme") || "default";

    try {
        const res = await fetch("http://localhost:8080/sds2_project_2025_404_finders_war_exploded/api/themes");
        if (!res.ok) throw new Error("Failed to load themes");

        const themes = await res.json();
        const theme = themes[savedTheme] || themes["default"];

        Object.keys(theme).forEach(key => {
            document.documentElement.style.setProperty(key, theme[key]);
        });

        document.body.style.background = theme["--bg"];
        document.body.style.color = theme["--text"];
    } catch (e) {
        console.warn("Could not load backend themes, using fallback:", e);
        document.documentElement.style.setProperty("--bg", "#f8fafc");
        document.documentElement.style.setProperty("--text", "#0f172a");
    }
})();
