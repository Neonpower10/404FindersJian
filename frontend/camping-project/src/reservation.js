import { addReservation } from "/src/reservationsApi.js";
import { getProduct } from "/src/productsApi.js";

const params = new URLSearchParams(window.location.search);
const productId = params.get("id");

let product;

getProduct(productId).then((p) => (product = p));

document.getElementById("reserve").onclick = async () => {
    const reservation = {
        reservationStartDate: params.get("start"),
        reservationEndDate: params.get("end"),
        productAmount: parseInt(params.get("amount")),
        product: product,
    };

    await addReservation(reservation);
    alert("Reservering geplaatst!");
};
