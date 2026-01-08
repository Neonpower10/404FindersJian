## **NFR-001: Veilig – Login/communicatie via HTTPS/SSL**
**User Story 1**
Als gebruiker wil ik dat al mijn communicatie via HTTPS verloopt zodat mijn gegevens altijd veilig zijn tijdens gebruik van de webapplicatie.

**Definition of Ready (DoR):**
* SSL certificaat beschikbaar (test + productie)
* Serverconfiguratie ondersteunt HTTPS
* Testomgeving klaar om communicatie te valideren

**Definition of Done (DoD):**
* Alle endpoints alleen toegankelijk via HTTPS
* SSL certificaat correct geïnstalleerd en gevalideerd
* Alle verzoeken via HTTP worden automatisch doorgestuurd naar HTTPS
* Security test uitgevoerd zonder kritieke bevindingen

## **NFR-002: Altijd beschikbaar – Minimaal 99% uptime tijdens kampeerseizoen**

**User Story 2**
Als kampeerder wil ik altijd toegang hebben tot de applicatie zodat ik zonder problemen kan boeken, ook tijdens drukke periodes.

**Definition of Ready (DoR):**
* Hostingprovider met uptime SLA gekozen
* Monitoringtools beschikbaar
* Load balancer en fail over plan gedefinieerd

**Definition of Done (DoD):**
* Monitoring geïmplementeerd en alerts ingesteld
* Productieomgeving draait met redundantie
* Logboeken tonen uptime ≥ 99% tijdens testperiode

## **NFR-003: Schaalbaar – Minimaal 100 campings en 10.000 gelijktijdige gebruikers**

**User Story 3a**
Als superbeheerder wil ik dat het systeem minstens 100 campings kan ondersteunen zodat het platform groot genoeg is voor brede adoptie.

**User Story 3b**
Als kampeerder wil ik dat het systeem soepel blijft werken bij 10.000 gelijktijdige gebruikers zodat mijn ervaring niet vertraagt tijdens drukte.

**Definition of Ready (DoR):**
* Load en stress test plan beschikbaar
* Database ingericht met schaalbare structuur
* Cloud/hostingcapaciteit afgestemd op piekbelasting

**Definition of Done (DoD):**
* Systeem getest met 100+ campings zonder prestatieverlies
* Performance test toont dat 10.000 gelijktijdige gebruikers ondersteund worden
* Autoscaling (indien cloud) geconfigureerd

## **NFR-004: Snel – Acties reageren binnen 2 seconden**

**User Story 4**
Als **gebruiker** wil ik **dat elke actie (bijv. inloggen, boeken) binnen 2 seconden een reactie geeft** zodat **ik niet gefrustreerd raak door lange wachttijden**.

**Definition of Ready (DoR):**
* Prestatiecriteria vastgelegd (max 2 seconden per actie)
* Testscenario’s gedefinieerd voor de belangrijkste functies
* Monitoringtools beschikbaar

**Definition of Done (DoD):**
* Alle kritieke acties (login, boeking, beheer) reageren < 2 seconden
* Load tests bevestigen performance onder piekbelasting
* Resultaten vastgelegd in een performancerapport

## **NFR-005: Gebruikersvriendelijk – Simpel, duidelijk, voorbeelden/uitleg**

**User Story 5a**
Als kampeerder wil ik een duidelijke interface met voorbeelden** zodat ik eenvoudig een boeking kan maken zonder uitleg te vragen.

**User Story 5b**
Als campingbeheerder wil ik overzichtelijke formulieren en uitlegteksten zodat ik snel mijn campinggegevens kan beheren.

**Definition of Ready (DoR):**
* Wireframes en UI designs goedgekeurd
* Testgroep (gebruikers) beschikbaar voor feedback
* Uitleg en hulppagina’s gedefinieerd

**Definition of Done (DoD):**
* Interface getest met eindgebruikers en minimaal 80% begrijpt alle functies direct
* Consistente iconen, kleuren en labels toegepast
* Voorbeelden/uitleg zichtbaar bij formulieren en kritieke functies
* Positieve usability test resultaten vastgelegd
