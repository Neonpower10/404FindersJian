/**
 * Loads the saved theme from localStorage and applies it to the document.
 * Called on every admin page to keep theme consistent.
 */
const savedTheme = localStorage.getItem("selectedTheme");

if (savedTheme) {
    const themes = {
        default: {
            "--bg": "#f8fafc",
            "--text": "#0f172a",
            "--card-bg": "#fff",
            "--accent": "#2563eb",
            "--muted": "#6b7280"
        },
        forest: {
            "--bg": "#ecfdf5",
            "--text": "#064e3b",
            "--card-bg": "#ffffff",
            "--accent": "#059669",
            "--muted": "#6b7280"
        },
        sunset: {
            "--bg": "#fff7ed",
            "--text": "#78350f",
            "--card-bg": "#ffffff",
            "--accent": "#ea580c",
            "--muted": "#6b7280"
        },
        midnight: {
            "--bg": "#0f172a",
            "--text": "#f8fafc",
            "--card-bg": "#1e293b",
            "--accent": "#6366f1",
            "--muted": "#94a3b8",
            "--btn-ghost-text": "#ffffff"
        },
        ocean: {
            "--bg": "#e0f2fe",
            "--text": "#082f49",
            "--card-bg": "#ffffff",
            "--accent": "#0284c7",
            "--muted": "#6b7280"
        }
    };

    const theme = themes[savedTheme] || themes["default"];
    Object.keys(theme).forEach(key => {
        document.documentElement.style.setProperty(key, theme[key]);
    });

    document.body.style.background = theme["--bg"];
    document.body.style.color = theme["--text"];
}
