document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("contactForm");

  form.addEventListener("submit", async (e) => {
    e.preventDefault();

    const formData = new FormData(form);
    const data = Object.fromEntries(formData.entries());

    console.log(data)

    try {
      const response = await fetch("http://localhost:8080/sds2_project_2025_404_finders_war_exploded/api/contactPage", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          name: data["name"],
          email: data["email"],
          message: data["message"],
        }),
      });

      if (response.ok) {
        const serverMessage = await response.json();
        showMessage(form, serverMessage.message, "green");
      } else {
        showMessage(form, "Error sending message: " + response.statusText, "red");
      }
    } catch (err) {
      showMessage(form, "Connection error. Try again later.", "red");
    }

    form.reset();
  });

  function showMessage(form, text, color) {
    const message = document.createElement("div");
    message.textContent = text;
    message.style.color = color;
    message.style.marginTop = "10px";
    form.appendChild(message);
    setTimeout(() => message.remove(), 3000);
  }
});
