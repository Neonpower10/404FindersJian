# Requirements documentatie Jian

## Functionele Requirements

FR-001: Modulair systeem (Must Have)
Beschrijving: Het systeem moet modulair zijn zodat campings modules kunnen activeren of deactiveren.
Actoren: Superbeheerder, Campingbeheerder
Voorwaarden: Het systeem is toegankelijk en een camping is geregistreerd.
Uitkomst: De camping gebruikt enkel de gewenste modules.

FR-002: Rollenbeheer (Must Have)
Beschrijving: Het systeem moet drie rollen ondersteunen: Superbeheerder (P&J BV), Campingbeheerder, en
Gebruiker/kampeerder.
Actoren: Superbeheerder, Campingbeheerder, Gebruiker
Voorwaarden: Account is aangemaakt en rol is toegewezen.
Uitkomst: Gebruikers hebben toegang tot functies die bij hun rol passen.

FR-003: Boekingsfunctionaliteit (Must Have)
Beschrijving: Kampeerders moeten boekingen kunnen maken en beheren.
Actoren: Gebruiker/kampeerder
Voorwaarden: Gebruiker is ingelogd.
Uitkomst: Boeking wordt opgeslagen in het systeem.

FR-004: Seizoensinstellingen (Must Have)
Beschrijving: Het systeem moet seizoensinstellingen ondersteunen (continu, delen van het jaar, specifieke periodes).
Actoren: Campingbeheerder
Voorwaarden: Camping is actief in het systeem.
Uitkomst: Beschikbaarheid van de camping wordt aangepast volgens de seizoensinstellingen.

FR-005: Beveiligde login (Must Have)
Beschrijving: Alle gebruikers moeten via een beveiligde login toegang krijgen.
Actoren: Alle rollen
Voorwaarden: Gebruiker heeft geregistreerde inloggegevens.
Uitkomst: Toegang tot het systeem of foutmelding bij verkeerde inlog.

FR-006: Data-opslag (Must Have)
Beschrijving: Gegevens moeten persistent opgeslagen worden.
Actoren: Alle rollen
Voorwaarden: Het systeem is beschikbaar.
Uitkomst: Data blijft bewaard, ook na systeemcrashes.

FR-007: Schaalbaarheid voor campings (Must Have)
Beschrijving: Het systeem moet geschikt zijn voor kleine, middelgrote en grote campings.
Actoren: Superbeheerder, Campingbeheerder
Voorwaarden: De camping is geregistreerd.
Uitkomst: Het systeem functioneert ongeacht de omvang van de camping.

FR-008: Beheeromgeving (Should Have)
Beschrijving: Campingbeheerders moeten modules kunnen activeren/deactiveren in een beheeromgeving.
Actoren: Campingbeheerder
Voorwaarden: Ingelogd als campingbeheerder.
Uitkomst: Modules worden aangepast naar wens van de camping.

FR-009: Broodjesservice-module (Should Have)
Beschrijving: Het systeem moet een broodjesservice-module bevatten.
Actoren: Campingbeheerder, Gebruiker
Voorwaarden: Module is geactiveerd.
Uitkomst: Kampeerders kunnen broodjes bestellen.

FR-010: Sanitairbeheer-module (Should Have)
Beschrijving: Het systeem moet sanitairbeheer ondersteunen.
Actoren: Campingbeheerder
Voorwaarden: Module is geactiveerd.
Uitkomst: Beheerder kan sanitair reserveren of beheren.

FR-011: Rapportages/statistieken (Should Have)
Beschrijving: Het systeem moet rapportages kunnen genereren, zoals gebruik van modules en bezettingsgraad.
Actoren: Superbeheerder, Campingbeheerder
Voorwaarden: Data aanwezig in het systeem.
Uitkomst: Rapportages beschikbaar in het beheerdersdashboard.

FR-012: Prototype/demo omgeving (Should Have)
Beschrijving: Het systeem moet een demo-omgeving ondersteunen voor nieuwe campings.
Actoren: Superbeheerder, Campingbeheerder
Voorwaarden: Nieuwe camping wordt aangemaakt.
Uitkomst: Demo-versie beschikbaar met beperkte data.

FR-013: Maatwerkmodules (Could Have)
Beschrijving: Het systeem moet maatwerkmodules kunnen ondersteunen per camping.
Actoren: Superbeheerder, Campingbeheerder
Voorwaarden: Extra module wordt aangevraagd.
Uitkomst: Nieuwe module is beschikbaar voor de camping.

FR-014: Externe integraties (Could Have)
Beschrijving: Het systeem kan koppelen met externe systemen (bijv. betalingen).
Actoren: Superbeheerder, Campingbeheerder
Voorwaarden: Externe koppeling is geconfigureerd.
Uitkomst: Data uit externe systemen wordt geïntegreerd.

FR-015: Multilingual support (Could Have)
Beschrijving: Het systeem kan in meerdere talen worden gebruikt.
Actoren: Gebruikers
Voorwaarden: Taaloptie beschikbaar in instellingen.
Uitkomst: Systeem toont interface in gekozen taal.

FR-016: Mobiele ondersteuning (Could Have)
Beschrijving: Het systeem kan mobiel gebruikt worden (web of app).
Actoren: Alle rollen
Voorwaarden: Toegang via mobiel apparaat.
Uitkomst: Systeem functioneert correct op smartphones en tablets.

FR-017: Notificatiesysteem (Could Have)
Beschrijving: Het systeem kan meldingen sturen (bijv. bevestigingen, herinneringen).
Actoren: Gebruiker, Campingbeheerder
Voorwaarden: Gebruiker staat notificaties toe.
Uitkomst: Gebruiker ontvangt melding of update.

Niet-functionele Requirements
NFR-001: Beveiliging (Must Have)
Beschrijving: Het systeem moet gebruik maken van veilige login, autorisatie en HTTPS/SSL.
Actoren: Alle rollen
Voorwaarden: Gebruiker logt in via beveiligde verbinding.
Uitkomst: Gegevens en accounts zijn beschermd.

NFR-002: Beschikbaarheid (Must Have)
Beschrijving: Het systeem moet minimaal 99% uptime hebben tijdens het kampeerseizoen.
Actoren: Alle rollen
Voorwaarden: Systeem in productie.
Uitkomst: Continu gebruik zonder grote onderbrekingen.

NFR-003: Schaalbaarheid (Must Have)
Beschrijving: Het systeem moet minstens 100 campings en 10.000 gelijktijdige gebruikers ondersteunen.
Actoren: Superbeheerder, Campingbeheerder, Gebruiker
Voorwaarden: Servercapaciteit beschikbaar.
Uitkomst: Systeem blijft snel en stabiel bij veel gebruikers.

NFR-004: Performance (Must Have)
Beschrijving: Het systeem moet binnen 2 seconden reageren op gebruikersacties.
Actoren: Alle rollen
Voorwaarden: Normale netwerkverbinding.
Uitkomst: Snelle gebruikerservaring.

NFR-005: Data persistentie (Must Have)
Beschrijving: Gegevens moeten veilig opgeslagen en hersteld kunnen worden.
Actoren: Alle rollen
Voorwaarden: Back-ups zijn actief.
Uitkomst: Data blijft bewaard bij crashes.

NFR-006: Compatibiliteit (Must Have)
Beschrijving: Het systeem moet functioneren in alle gangbare browsers (Chrome, Edge, Firefox, Safari).
Actoren: Alle rollen
Voorwaarden: Gebruiker gebruikt een ondersteunde browser.
Uitkomst: Applicatie functioneert correct.

NFR-007: Usability (Should Have)
Beschrijving: De UI moet intuïtief zijn zodat een campingbeheerder modules eenvoudig kan beheren.
Actoren: Campingbeheerder
Voorwaarden: Ingelogd in de beheeromgeving.
Uitkomst: Beheerder kan zonder training modules bedienen.

NFR-008: Onderhoudbaarheid (Should Have)
Beschrijving: Het systeem moet gemakkelijk uitbreidbaar zijn zonder bestaande functionaliteiten te verstoren.
Actoren: Ontwikkelaars, Superbeheerder
Voorwaarden: Systeem draait stabiel.
Uitkomst: Nieuwe modules kunnen eenvoudig worden toegevoegd.

NFR-009: Logging & monitoring (Should Have)
Beschrijving: Het systeem moet logbestanden en monitoringtools ondersteunen.
Actoren: Superbeheerder, Ontwikkelaars
Voorwaarden: Logging staat aan.
Uitkomst: Fouten en prestaties worden bijgehouden.

NFR-010: Documentatie (Should Have)
Beschrijving: Het systeem moet handleidingen en technische documentatie bieden.
Actoren: Beheerders, Ontwikkelaars
Voorwaarden: Documentatie is up-to-date.
Uitkomst: Gebruikers en ontwikkelaars begrijpen het systeem sneller.

NFR-011: Offline support (Could Have)
Beschrijving: Het systeem kan beperkte offline functionaliteit bieden.
Actoren: Gebruiker
Voorwaarden: Internet tijdelijk niet beschikbaar.
Uitkomst: Data wordt later gesynchroniseerd.

NFR-012: Toegankelijkheid (Could Have)
Beschrijving: Het systeem kan voldoen aan WCAG 2.1-richtlijnen.
Actoren: Gebruiker
Voorwaarden: Gebruiker met beperking gebruikt het systeem.
Uitkomst: Toegang voor bredere doelgroep.

NFR-013: Multi-device support (Could Have)
Beschrijving: Het systeem kan volledig responsief zijn voor tablets en smartphones.
Actoren: Alle rollen
Voorwaarden: Toegang via ander apparaat.
Uitkomst: Correcte weergave en gebruikservaring.

NFR-014: Internationale standaarden (Could Have)
Beschrijving: Het systeem kan naast GDPR/AVG voldoen aan regels in andere landen.
Actoren: Superbeheerder, Campingbeheerder
Voorwaarden: Camping actief in ander land.
Uitkomst: Juridische compliance.

NFR-015: Automatische updates (Could Have)
Beschrijving: Het systeem kan zichzelf automatisch bijwerken zonder downtime.
Actoren: Superbeheerder, Ontwikkelaars
Voorwaarden: Update beschikbaar.
Uitkomst: Nieuwe versie zonder onderbrekingen actief.
