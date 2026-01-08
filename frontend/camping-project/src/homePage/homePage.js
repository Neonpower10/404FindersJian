const bookNowButton = document.querySelector(".btn-primary")
const bookingCard = document.getElementById("bookingCard")
const bottomBookButton = document.querySelector(".cta-btn")

bookNowButton.addEventListener("click", (e) => {
    // login check. look if there is a jwt!
    const token = sessionStorage.getItem("jwt");
    if (!token) {
        e.preventDefault();

        // Save where they tried to go
        sessionStorage.setItem("redirect_after_login", "bookingsPage.html");

        window.location.href = "accountLogin.html"
        return
    }
})

bookingCard.addEventListener("click", (e) => {
    // login check. look if there is a jwt!
    const token = sessionStorage.getItem("jwt");
    if (!token) {
        e.preventDefault();

        // Save where they tried to go
        sessionStorage.setItem("redirect_after_login", "bookingsPage.html");

        window.location.href = "accountLogin.html"
        return
    }
})

bottomBookButton.addEventListener("click", (e) => {
    // login check. look if there is a jwt!
    const token = sessionStorage.getItem("jwt");
    if (!token) {
        e.preventDefault();

        // Save where they tried to go
        sessionStorage.setItem("redirect_after_login", "bookingsPage.html");

        window.location.href = "accountLogin.html"
        return
    }
})