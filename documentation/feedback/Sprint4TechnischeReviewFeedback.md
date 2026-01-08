# Technische Review Notities

## Feedback voor Jian
**Vraag:** De repository-file gebruikt een ander pattern dan de DAO. Moet ik deze anders opbouwen?

**Antwoord (Jos):** Hoeft niet per se. Waar staan je queries?

**Reactie (Jian):** Niet in de repository.

**Opmerking (Jos):** Blijf SQL oefenen. Volgend semester komt SQL meer op de achtergrond, maar ik ga het nog wel checken.

## Feedback voor Zakaria
**Vraag (Jian):** De JWT wordt nu opgeslagen in sessionStorage. Is dat correct? Zou dat niet in de backend moeten?

**Antwoord (Jos):** Wat levert dat op? De token is van de client, dus die moet hem zelf opslaan.

**Opmerking:** LocalStorage houdt het langer vast, maar sessionStorage is voor jullie use case prima.

## Feedback voor Robin
### Domeinmodel
**Vraag (Jian):** Kloppen de relaties? En hoe zit het met de plus- en mintekens?

**Antwoord (Jos):** Check de uitleg over encapsulation in de kennisbank. '-' is private, '+' is public. Let op: int hoort met kleine letter.

**Vraag (Jos):** Waarom heeft elke klasse een id?

**Antwoord (Jian):** Leek handig, bijvoorbeeld bij meerdere thema's voor makkelijke identificatie.

**Opmerking (Jos):** Dat kan ook met een naam. Jullie hebben geen database-logica in het domein, dus ids zijn nergens voor nodig.

- bookingId zou eigenlijk een bookingnummer moeten zijn.
- Een restaurantId is niet nodig voor een reservering.
- Het domeinmodel moet meer dan een plaatje zijn — ook tekst met uitleg, regels en redenen (bijv. is telefoonnummer verplicht? Waarom integer?).

### SQL Queries (nog steeds Robin)
**Vraag (Jian):** Als ik een query plak, gebeurt er niets.

**Antwoord (Jos):** Waarschijnlijk door spaties of andere characters. Het script is groot. Refresh de tables, dan staan ze er.

**Advies:** Maak regels voor syntax-gevoelige delen.

## Feedback van de klas
### Sterkten
- Heel extensief model gemaakt
- Nice dat je alleen durft te staan!

### Kansen
- Betere voorbereiding: check vooraf wat je wilt laten zien en waar je vragen over hebt (ging na het begin wel goed)
