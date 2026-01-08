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
        List<Product> products = new ArrayList<>();
        products.add(new Product("Fiets",  "geen afbeelding", "Wilt u heerlijk de natuur in? Huur dan deze fiets!", BigDecimal.valueOf(19.99), 50));
        products.add(new Product("Kano",  "geen afbeelding", "Zoekt u naar rust? Met onze kanos kunt u heerlijk ontspannen. Probeer niet te vallen...", BigDecimal.valueOf(9.99), 100));
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
