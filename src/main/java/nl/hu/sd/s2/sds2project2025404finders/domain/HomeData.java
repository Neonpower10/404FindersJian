package nl.hu.sd.s2.sds2project2025404finders.domain;

import java.io.Serializable;

public class HomeData implements Serializable {
    public String heroEyebrow;
    public String heroTitle;
    public String heroText;
    public String heroImage;

    public String discoverTitle;
    public String discoverText;

    public String cardBookingText;
    public String cardFacilitiesText;
    public String cardContactText;
    public String cardAboutText;

    public String cardBookingImage;
    public String cardFacilitiesImage;
    public String cardContactImage;
    public String cardAboutImage;

    public String usp1Title;
    public String usp1Text;
    public String usp2Title;
    public String usp2Text;
    public String usp3Title;
    public String usp3Text;
    public String usp4Title;
    public String usp4Text;

    public String ctaTitle;
    public String ctaText;

    public HomeData() {
        this.heroEyebrow = "Welkom bij Camping De Natuur";
        this.heroTitle = "Ontsnap naar rust en ruimte midden in de natuur";
        this.heroText = "Geniet van ruime staanplaatsen, moderne faciliteiten en een gastvrije sfeer. Boek eenvoudig je verblijf.";
        this.heroImage = "public/icons/campingMoodImage.jpg";
        this.discoverTitle = "Ontdek onze camping";
        this.discoverText = "Alles wat je nodig hebt voor een ontspannen verblijf.";

        this.cardBookingText = "Reserveer snel en eenvoudig.";
        this.cardFacilitiesText = "Sanitair, speelmogelijkheden en meer.";
        this.cardContactText = "Vragen? We helpen je graag.";
        this.cardAboutText = "Lees meer over onze camping.";

        this.cardBookingImage = "public/icons/campingMoodImage.jpg";
        this.cardFacilitiesImage = "public/icons/campingMoodImage.jpg";
        this.cardContactImage = "public/icons/campingMoodImage.jpg";
        this.cardAboutImage = "public/icons/campingMoodImage.jpg";

        this.usp1Title = "Natuurrijk";
        this.usp1Text = "Rust, ruimte en groen.";
        this.usp2Title = "Comfort";
        this.usp2Text = "Modern sanitair en goede voorzieningen.";
        this.usp3Title = "Gezinsvriendelijk";
        this.usp3Text = "Activiteiten voor jong en oud.";
        this.usp4Title = "Goede locatie";
        this.usp4Text = "Dichtbij wandel- en fietsroutes.";

        this.ctaTitle = "Klaar om te verblijven?";
        this.ctaText = "Bekijk beschikbaarheid en reserveer je plek.";
    }
}
