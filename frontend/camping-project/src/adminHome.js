/**
 * Admin panel logic for editing the home page content.
 * All data is now fetched and saved via the backend (/api/home).
 */

const $ = (id) => document.getElementById(id);
const API_URL = "http://localhost:8080/sds2_project_2025_404_finders_war_exploded/api/home";

const textFields = {
    heroEyebrow: "p_heroEyebrow",
    heroTitle: "p_heroTitle",
    heroText: "p_heroText",

    cardBookingText: "p_cardBookingText",
    cardFacilitiesText: "p_cardFacilitiesText",
    cardContactText: "p_cardContactText",
    cardAboutText: "p_cardAboutText",

    usp1Title: "p_usp1Title",
    usp1Text: "p_usp1Text",
    usp2Title: "p_usp2Title",
    usp2Text: "p_usp2Text",
    usp3Title: "p_usp3Title",
    usp3Text: "p_usp3Text",
    usp4Title: "p_usp4Title",
    usp4Text: "p_usp4Text",

    ctaTitle: "p_ctaTitle",
    ctaText: "p_ctaText"
};

const imageFields = {
    heroImageInput: "heroImage",
    cardBookingImage: "cardBookingImage",
    cardFacilitiesImage: "cardFacilitiesImage",
    cardContactImage: "cardContactImage",
    cardAboutImage: "cardAboutImage"
};

let data = null;

/* =====================================================
   INITIAL DATA LOAD (from backend)
===================================================== */
async function loadHomeData() {
    try {
        const res = await fetch(API_URL);
        if (!res.ok) throw new Error("Failed to fetch home data");
        const home = await res.json();
        data = { home };
        fillFields(data);
    } catch (err) {
        console.error("Error loading data:", err);
        showToast("Fout bij laden van gegevens!");
    }
}

/**
 * Fill all admin inputs and preview fields.
 */
function fillFields(sourceData = data) {
    const home = sourceData.home;

    // Text fields
    for (const key in textFields) {
        const input = $(key);
        const preview = $(textFields[key]);
        const value = home[key] || "";

        if (input) input.value = value;
        if (preview) preview.textContent = value;
    }

    // Image previews
    for (const inputId in imageFields) {
        const dataKey = imageFields[inputId];
        const preview = $("p_" + dataKey);

        if (preview && home[dataKey]) {
            preview.src = home[dataKey];
        }
    }
}

/* =====================================================
   IMAGE UPLOAD BINDING
===================================================== */
function bindImageUpload(inputId, dataKey) {
    const input = $(inputId);
    if (!input) return;

    input.addEventListener("change", (e) => {
        const file = e.target.files?.[0];
        if (!file) return;

        const reader = new FileReader();
        reader.onload = () => {
            data.home[dataKey] = reader.result;
            const preview = $("p_" + dataKey);
            if (preview) preview.src = reader.result;
        };
        reader.readAsDataURL(file);
    });
}

for (const inputId in imageFields) {
    bindImageUpload(inputId, imageFields[inputId]);
}

/* =====================================================
   TEXT FIELD BINDING
===================================================== */
for (const key in textFields) {
    const input = $(key);
    const previewId = textFields[key];

    if (!input) continue;

    input.addEventListener("input", () => {
        data.home[key] = input.value;
        const preview = $(previewId);
        if (preview) preview.textContent = input.value;
    });
}

/* =====================================================
   SAVE TO BACKEND
===================================================== */
$("saveBtn").addEventListener("click", async () => {
    try {
        const token = sessionStorage.getItem("jwt"); // Assuming JWT login is used

        const res = await fetch(API_URL, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": token ? "Bearer " + token : ""
            },
            body: JSON.stringify(data.home)
        });

        if (!res.ok) throw new Error("Failed to save");

        showToast("Homepagina opgeslagen!");
    } catch (err) {
        console.error("Error saving:", err);
        showToast("Fout bij opslaan van gegevens!");
    }
});

/* =====================================================
   RESET TO DEFAULT (via backend default reset)
===================================================== */
async function resetHome() {
    // Option 1: reset to local defaults and save them again
    if (!data) return;

    const confirmed = confirm("Weet je zeker dat je wilt resetten naar standaardwaarden?");
    if (!confirmed) return;

    try {
        // Default template for reset
        const defaultData = {
            heroEyebrow: "Welkom bij Camping De Natuur",
            heroTitle: "Ontsnap naar rust en ruimte midden in de natuur",
            heroText: "Geniet van ruime staanplaatsen, moderne faciliteiten en een gastvrije sfeer. Boek eenvoudig je verblijf.",
            heroImage: "public/icons/campingMoodImage.jpg",
            discoverTitle: "Ontdek onze camping",
            discoverText: "Alles wat je nodig hebt voor een ontspannen verblijf.",
            cardBookingText: "Reserveer snel en eenvoudig.",
            cardFacilitiesText: "Sanitair, speelmogelijkheden en meer.",
            cardContactText: "Vragen? We helpen je graag.",
            cardAboutText: "Lees meer over onze camping.",
            cardBookingImage: "public/icons/campingMoodImage.jpg",
            cardFacilitiesImage: "public/icons/campingMoodImage.jpg",
            cardContactImage: "public/icons/campingMoodImage.jpg",
            cardAboutImage: "public/icons/campingMoodImage.jpg",
            usp1Title: "Natuurrijk",
            usp1Text: "Rust, ruimte en groen.",
            usp2Title: "Comfort",
            usp2Text: "Modern sanitair en goede voorzieningen.",
            usp3Title: "Gezinsvriendelijk",
            usp3Text: "Activiteiten voor jong en oud.",
            usp4Title: "Goede locatie",
            usp4Text: "Dichtbij wandel- en fietsroutes.",
            ctaTitle: "Klaar om te verblijven?",
            ctaText: "Bekijk beschikbaarheid en reserveer je plek."
        };

        const token = sessionStorage.getItem("jwt");

        const res = await fetch(API_URL, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": token ? "Bearer " + token : ""
            },
            body: JSON.stringify(defaultData)
        });

        if (!res.ok) throw new Error("Reset failed");

        data.home = defaultData;
        fillFields(data);
        showToast("Homepagina gereset naar standaardwaarden!");
    } catch (err) {
        console.error("Error resetting:", err);
        showToast("Fout bij resetten van gegevens!");
    }
}

$("resetBtn").addEventListener("click", resetHome);

/* =====================================================
   TOAST MESSAGE
===================================================== */
function showToast(message) {
    const toast = $("toast");
    toast.textContent = message;
    toast.classList.add("show");

    setTimeout(() => toast.classList.remove("show"), 2500);
}

/* =====================================================
   INITIALIZATION
===================================================== */
document.addEventListener("DOMContentLoaded", loadHomeData);
