package nl.hu.sd.s2.sds2project2025404finders.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Product {
    private String productName;
    private String productImageUrl;
    private String productDescription;
    private BigDecimal pricePerDay;
    private int productStock;
    private static final List<Product> products = new ArrayList<>();

    static {
        products.add(new Product(
                "Fiets",
                "geen afbeelding",
                "Deze comfortabele fiets is ideaal voor kampeerders die de omgeving willen verkennen. "
                        + "Dankzij de stevige banden en soepele versnellingen fietst u moeiteloos over zowel verharde "
                        + "wegen als bospaden. Perfect voor een dagje uit of een snelle boodschap in het dorp.",
                BigDecimal.valueOf(19.99),
                50
        ));

        products.add(new Product(
                "Kano",
                "geen afbeelding",
                "Geniet van ultieme rust op het water met deze stabiele kano. Geschikt voor beginners én "
                        + "ervaren peddelaars. De kano biedt voldoende ruimte voor twee personen en lichte bagage, "
                        + "waardoor hij ideaal is voor ontspannen tochten over meren en rustige rivieren.",
                BigDecimal.valueOf(9.99),
                100
        ));

        products.add(new Product(
                "Luchtbed",
                "geen afbeelding",
                "Dit comfortabele luchtbed biedt extra ondersteuning en isolatie van de koude grond. "
                        + "Eenvoudig op te blazen en compact op te bergen. Een perfecte keuze voor kampeerders "
                        + "die net wat meer comfort willen tijdens het slapen.",
                BigDecimal.valueOf(6.99),
                80
        ));

        products.add(new Product(
                "Slaapzak",
                "geen afbeelding",
                "Warme en zachte slaapzak geschikt voor meerdere seizoenen. "
                        + "De slaapzak houdt lichaamswarmte goed vast en is ademend, "
                        + "waardoor u comfortabel slaapt bij zowel frisse als mildere temperaturen.",
                BigDecimal.valueOf(7.49),
                120
        ));

        products.add(new Product(
                "Campingstoel",
                "geen afbeelding",
                "Inklapbare campingstoel met stevige armleuningen en comfortabele zitting. "
                        + "Ideaal om te ontspannen bij het kampvuur, een boek te lezen of samen te genieten "
                        + "van lange zomeravonden op de camping.",
                BigDecimal.valueOf(4.99),
                200
        ));

        products.add(new Product(
                "Campingtafel",
                "geen afbeelding",
                "Praktische en stabiele campingtafel die eenvoudig op te zetten is. "
                        + "Geschikt voor maaltijden, spelletjes of als extra werkruimte. "
                        + "Dankzij het inklapbare ontwerp is de tafel makkelijk te vervoeren.",
                BigDecimal.valueOf(8.99),
                40
        ));

        products.add(new Product(
                "Barbecue",
                "geen afbeelding",
                "Compacte barbecue waarmee u eenvoudig een heerlijke maaltijd bereidt op de camping. "
                        + "Geschikt voor houtskool en eenvoudig schoon te maken. "
                        + "Perfect voor gezellige avonden met vrienden of familie.",
                BigDecimal.valueOf(12.99),
                15
        ));

        products.add(new Product(
                "Koelbox",
                "geen afbeelding",
                "Deze koelbox houdt uw eten en drinken langdurig koel, zelfs op warme dagen. "
                        + "Ideaal voor kampeertrips, picknicks en stranddagen. "
                        + "Voorzien van een stevig handvat voor eenvoudig transport.",
                BigDecimal.valueOf(5.99),
                60
        ));
    }

    public Product() {
    }

    public Product(String productName, String productImageUrl, String productDescription, BigDecimal pricePerDay, int productStock) {
        if (productName == null || productName.trim().isEmpty()) {
            throw new IllegalArgumentException("Product naam mag niet leeg zijn");
        }
        this.productName =  productName;

        if (productImageUrl == null || productImageUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("Product afbeelding mag niet leeg zijn");
        }
        this.productImageUrl = productImageUrl;

        if (productDescription == null || productDescription.trim().isEmpty()) {
            throw new IllegalArgumentException("Product beschrijving mag niet leeg zijn");
        }
        this.productDescription = productDescription;

        if (pricePerDay == null || pricePerDay.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Prijs moet hoger dan 0 zijn");
        }
        this.pricePerDay = pricePerDay;

        if (productStock < 0) {
            throw new IllegalArgumentException("Voorraad moet minimaal 0 zijn");
        }
        this.productStock = productStock;
    }

    public static List<Product> getProducts() {
        return products;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        if (productName == null || productName.trim().isEmpty()) {
            throw new IllegalArgumentException("Product naam mag niet leeg zijn");
        }
        this.productName = productName;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        if (productImageUrl == null || productImageUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("Product afbeelding mag niet leeg zijn");
        }
        this.productImageUrl = productImageUrl;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        if (productDescription == null || productDescription.trim().isEmpty()) {
            throw new IllegalArgumentException("Product beschrijving mag niet leeg zijn");
        }
        this.productDescription = productDescription;
    }

    public BigDecimal getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(BigDecimal pricePerDay) {
        if (pricePerDay == null || pricePerDay.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Prijs moet hoger dan 0 zijn");
        }
        this.pricePerDay = pricePerDay;
    }

    public int getProductStock() {
        return productStock;
    }

    public void setProductStock(int productStock) {
        if (productStock < 0) {
            throw new IllegalArgumentException("Voorraad moet minimaal 0 zijn");
        }
        this.productStock = productStock;
    }

    public boolean isInStock() {
        return productStock > 0;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productName='" + productName + '\'' +
                ", productImageUrl='" + productImageUrl + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", pricePerDay=" + pricePerDay +
                ", productStock=" + productStock +
                '}';
    }

}
