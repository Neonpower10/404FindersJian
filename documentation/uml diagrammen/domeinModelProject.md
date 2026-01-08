```mermaid

classDiagram

%% ================
%%     CLASSES
%% ================

    class CampersAccount {
        - campersId : int (required)
        - firstName : string (required)
        - infix : string (optional)
        - lastName : string (required)
        - adress : string (required)
        - email : string (required)
        - userPassword : string (required)
    }

    class OwnersAccount {
        - ownersId : int (required)
        - firstName : string (required)
        - namePrefix : string (optional)
        - lastName : string (required)
        - camping : string (optional)
        - email : string (required)
        - ownerPassword : string (required)
    }

    class ContactMessage {
        - contactMessageId : int (required)
        - subject : string (required)
        - email : string (required)
        - messageSend : string (required)
        + campersId : int (required)
    }

    class Inbox {
    %% Kan niet leeg zijn → comment toegevoegd
    %% Deze inbox verzamelt binnenkomende items voor een eigenaar of camping
    }

    class Camping {
        - campingId : int (required)
        - campingName : string (required)
        + ownerId : int (required)
    }

    class Booking {
        - bookingsId : int (required)
        - gender : string (optional)
        - firstName : string (required)
        - infix : string (optional)
        - lastName : string (required)
        - email : string (required)
        - phoneNumber : string (required)
        - country : string (required)
        - place : string (required)
        - postcode : string (required)
        - streetName : string (required)
        - houseNumber : string (required)
        - startDate : string (required)
        - endDate : string (required)
        - peopleAmount : int (required)
        + campingId : int (required)
        + campersId : int (required)
    }

    class Facility {
        - facilityId : int (required)
        - facilityName : string (required)
        - facilityDescription : string (optional)
        - availability : boolean (required)
        + campingId : int (required)
    }

    class Theme {
        - themeId : int (required)
        - themeName : string (required)
        - background : string (required)
        - description : string (optional)
        - card : string (optional)
        - navbar : string (optional)
        - accent : string (optional)
        - muted : string (optional)
        + campingId : int (required)
    }

    class CampingPossibilities {
        - equipmentId : int (required)
        - equipmentName : string (required)
    }

    class CampingCapacity {
        - capacityId : int (required)
        - capacity : int (required)
        + campingId : int (required)
        + equipmentId : int (required)
    }

    class CampingInfo {
        - infoId : int (required)
        - information : string (required)
        + campingId : int (required)
    }

    class Bread {
        - breadId : int (required)
        - breadName : string (required)
        + campingId : int (required)
    }

    class BreadOrder {
        - orderId : int (required)
        - firstName : string (required)
        - infix : string (optional)
        - lastName : string (required)
        - phoneNumber : string (required)
        - amount : int (required)
        - orderDate : string (required)
        - requests : string (optional)
        + campersId : int (required)
        + breadId : int (required)
    }

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

%% ================
%%   RELATIONSHIPS
%% ================

    OwnersAccount "*" --> "1" Camping
    OwnersAccount "*" --> "1" Inbox

    Camping "1" --> "*" Booking
    CampersAccount "1" --> "*" Booking
    CampersAccount "*" --> "1" Camping

    CampersAccount "1" --> "*" ContactMessage
    CampersAccount "1" --> "*" BreadOrder

    Camping "1" --> "*" Facility
    Camping "1" --> "1" Inbox

    Booking "*" --> "1" Inbox
    ContactMessage "*" --> "1" Inbox
    BreadOrder "*" --> "1" Inbox

    Camping "1" --> "*" Theme
    Camping "*" --> "1" CampingCapacity
    CampingCapacity "1" --> "*" CampingPossibilities

    Camping "1" --> "1" CampingInfo

    Camping "1" --> "*" Bread
    Bread "1" --> "*" BreadOrder

    Product "1" --> "*" Reservation
    Camping "1" --> "*" Product
    Reservation "*" --> "1" Inbox
    CampersAccount "1" --> "*" Reservation
    OwnersAccount "1" --> "*" Product

```

Korte uitleg van het domeinmodel

Dit domeinmodel beschrijft de belangrijkste onderdelen van het reserveringssysteem voor een campingplatform en hoe deze met elkaar verbonden zijn. Het model toont twee hoofdgebruikers: Camper (gast) en Owner (campingeigenaar). Een eigenaar beheert één of meerdere campings, die verschillende eigenschappen kunnen hebben, zoals faciliteiten, thema’s en capaciteiten.

Gasten kunnen bij campings boekingen plaatsen, reserveringen maken bij het restaurant en brood bestellen bij de bakkerij. Deze acties zijn opgenomen in de klassen Booking, Reservation en BreadOrder. Daarnaast bevat het model een Inbox voor berichten en aanvragen die eigenaren kunnen beheren.

Elke klasse bevat de relevante eigenschappen, zoals namen, datums, contactgegevens en aantallen personen. Het model laat ook zien welke gegevens verplicht zijn en hoe de objecten onderling verbonden zijn, bijvoorbeeld dat een camping meerdere boekingen kan hebben of dat een restaurant meerdere reserveringen ontvangt. Het doel is een overzichtelijk beeld van de informatie en interacties binnen het platform, zowel voor gasten als eigenaren.
