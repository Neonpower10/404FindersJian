````mermaid
classDiagram
    class Product{
        - productName : string [1]
        - productImageUrl : string [0..1]
        - productDescription : string [1]
        - pricePerDay : bigDecimal [1]
        - productStock : int [1]
        + isInStock() bool
        + addProducts()
    }
    class Reservation{
        - reservationStartDate : date [1]
        - reservationEndDate : date [1]
        - productAmount : int [1]
        + isLoggedIn() bool
        + calculatePrice() bigDecimal
    }

    Product "1" --> "*" Reservation
````