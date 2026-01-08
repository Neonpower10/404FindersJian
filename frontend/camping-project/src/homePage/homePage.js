// Buttons for login checks
const bookNowButton = document.querySelector(".btn-primary");
const bookingCard = document.getElementById("bookingCard");
const bottomBookButton = document.querySelector(".cta-btn");

// Replace this with your actual Tomcat app path
const API_URL = "http://localhost:8080/sds2_project_2025_404_finders_war_exploded/api/home";

function requireLogin(e) {
    const token = sessionStorage.getItem("jwt");
    if (!token) {
        e.preventDefault();
        sessionStorage.setItem("redirect_after_login", "bookingsPage.html");
        window.location.href = "accountLogin.html";
        return false;
    }
    return true;
}

bookNowButton?.addEventListener("click", requireLogin);
bookingCard?.addEventListener("click", requireLogin);
bottomBookButton?.addEventListener("click", requireLogin);

/* =====================================================
   Load home page content from backend
===================================================== */
async function loadHomePageContent() {
    try {
        const res = await fetch(API_URL);
        if (!res.ok) throw new Error("Backend responded with " + res.status);
        const home = await res.json();

        // -----------------------------
        // TEXT FIELDS
        // -----------------------------
        const textFields = [
            "heroEyebrow", "heroTitle", "heroText",
            "discoverTitle", "discoverText",
            "cardBookingText", "cardFacilitiesText", "cardContactText", "cardAboutText",
            "usp1Title", "usp1Text", "usp2Title", "usp2Text",
            "usp3Title", "usp3Text", "usp4Title", "usp4Text",
            "ctaTitle", "ctaText"
        ];

        textFields.forEach(id => {
            const el = document.getElementById(id);
            if (el && home[id]) el.textContent = home[id];
        });

        // -----------------------------
        // HERO IMAGE
        // -----------------------------
        const heroImg = document.getElementById("heroImage");
        if (heroImg) {
            heroImg.src = home.heroImage || "public/icons/campingMoodImage.jpg";
        }

        // -----------------------------
        // CARD IMAGES
        // -----------------------------
        const cardImageIDs = [
            "cardBookingImage",
            "cardFacilitiesImage",
            "cardContactImage",
            "cardAboutImage"
        ];

        cardImageIDs.forEach(id => {
            const imgEl = document.getElementById(id);
            if (imgEl) {
                imgEl.src = home[id] || "public/icons/campingMoodImage.jpg";
            }
        });
    } catch (err) {
        console.error("Error loading home page content:", err);
        // Optional fallback
        const heroTitle = document.getElementById("heroTitle");
        if (heroTitle) heroTitle.textContent = "Kon geen gegevens laden van de server.";
    }
}

document.addEventListener("DOMContentLoaded", loadHomePageContent);
