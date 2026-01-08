// Our dummy data
const broodjesData = [
    { name: 'Croissant', price: 1.50, image: 'url_to_image_1.jpg' },
    { name: 'Dark whole wheat', price: 2.10, image: 'url_to_image_2.jpg' },
    { name: 'Kaiser roll', price: 0.90, image: 'url_to_image_3.jpg' }
];

// Loop through the data to create a card for each item
broodjesData.forEach(broodje => {
    // 1. Create the elements
    const cardDiv = document.createElement('div');
    const nameElement = document.createElement('h2');
    const priceElement = document.createElement('p');
    const imageElement = document.createElement('img');

    // 2. Fill the elements with data
    nameElement.textContent = broodje.name;
    priceElement.textContent = `€${broodje.price}`;
    imageElement.src = broodje.image;
    imageElement.alt = broodje.name;

    // 3. Append the elements to the main div
    cardDiv.appendChild(nameElement);
    cardDiv.appendChild(priceElement);
    cardDiv.appendChild(imageElement);

    // Optional: add a CSS class for styling
    cardDiv.classList.add('bread-card');

    // 4. Add the complete card to the HTML page
    // We assume an element with id 'bread-list' already exists in the HTML
    const breadListContainer = document.getElementById('bread-list');
    if (breadListContainer) {
        breadListContainer.appendChild(cardDiv);
    }
});