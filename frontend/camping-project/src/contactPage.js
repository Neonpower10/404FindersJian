//Loads the file when the html page is loaded
document.addEventListener("DOMContentLoaded", () => {

    //Extracts the information send
    document.getElementById("contactForm").addEventListener("submit", function(e) {
    e.preventDefault();
    
    const formData = new FormData(e.target);
    const data = Object.fromEntries(formData.entries());

    //Sends it to the console
    console.log(data);

    //Transfer to json
    const jsonData = JSON.stringify(data, null, 2);
    console.log(jsonData);
    
    //Send to json
    fetch("/save", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: jsonData
    });
  });
});
