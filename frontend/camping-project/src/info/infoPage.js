
document.addEventListener("DOMContentLoaded", () => {

const API_URL ="http://localhost:8080/sds2_project_2025_404_finders_war_exploded/api/camping-info";

  fetch(API_URL)
    .then((response) => {
      if (!response.ok) {
        throw new Error("Failed to load camping info: " + response.status);
      }
      return response.json();
    })
    .then((data) => {
      // Fill "Over de camping"
      const aboutElement = document.getElementById("about-text");
      if (aboutElement) {
        aboutElement.textContent = data.about;
      }

      // Practical info
      const addressElement = document.getElementById("address-text");
      if (addressElement) {
        addressElement.textContent = data.address;
      }

      const receptionElement = document.getElementById("reception-text");
      if (receptionElement) {
        receptionElement.textContent = data.receptionHours;
      }

      const seasonsElement = document.getElementById("seasons-text");
      if (seasonsElement && Array.isArray(data.seasons)) {
        seasonsElement.textContent = data.seasons.join(", ");
      }

      const contactElement = document.getElementById("contact-text");
      if (contactElement) {
        contactElement.textContent =
          `${data.contactEmail} · ${data.contactPhone}`;
      }

      // Facilities list
      const facilitiesList = document.getElementById("facilities-list");
      if (facilitiesList && Array.isArray(data.facilities)) {
        facilitiesList.innerHTML = "";
        data.facilities.forEach((facility) => {
          const li = document.createElement("li");
          li.textContent = facility;
          facilitiesList.appendChild(li);
        });
      }

      // House rules
      const rulesList = document.getElementById("rules-list");
      if (rulesList && Array.isArray(data.houseRules)) {
        rulesList.innerHTML = "";
        data.houseRules.forEach((rule) => {
          const li = document.createElement("li");
          li.textContent = rule;
          rulesList.appendChild(li);
        });
      }
    })
    .catch((error) => {
      console.error(error);

      // Message when the camping info could not be loaded
      const aboutElement = document.getElementById("about-text");
      if (aboutElement) {
        aboutElement.textContent =
          "Kon campinginformatie niet laden.";
      }
    });
});
