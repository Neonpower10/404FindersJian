import ProductService from "./service/ProductService.js";

const params = new URLSearchParams(window.location.search);
const productName = params.get("productName");
const productDetailsContainer = document.querySelector(".productDetails");

let amountProductValue = 1

function getAllDetails() {
    ProductService.getProductByName(productName)
        .then((p) => {
            productDetailsContainer.innerHTML = `
            <div class="leftSide">
                <h1>${p.productName}</h1>
                <section><img src=${p.imageUrl} alt="Product afbeelding" class="productImage"</section>
                <h3>Productbeschrijving</h3>
                <section class="productDescription"><p>${p.productDescription}</p></section>
            </div>
            <form class="rightSide">
                <div class="inputForm">
                    <h3>Vul de startdatum en einddatum in:</h3>
                    <label for="startDate">Startdatum:</label>
                    <input type="date" name="startDate">
                    <label for="endDate">Eindatum:</label>
                    <input type="date" name="endDate">
                    
                    <h3>Hoeveel van het product wil je huren?</h3>
                    <button type="button" class="minBtn">-</button>
                    <span id="amountProduct">1</span>
                    <button type="button" class="plusBtn">+</button>
                    
                    <button type="button" class="nextStep">Volgende stap</button>
                </div>
            </form>
            `
            minAndAddBtn()
        })
}

function minAndAddBtn() {
    const minBtn = document.querySelector(".minBtn")
    const plusBtn = document.querySelector(".plusBtn")
    const amountProduct = document.getElementById("amountProduct")


    minBtn.addEventListener("click", () => {
        if (amountProductValue > 1) {
            amountProductValue--;
            amountProduct.textContent = amountProductValue;
        } else {
            alert("Je moet minimaal 1 van het product huren");
        }
    });

    plusBtn.addEventListener("click", () => {
        amountProductValue++;
        amountProduct.textContent = amountProductValue;
    });

}

window.addEventListener("DOMContentLoaded", () => {
    getAllDetails()
})