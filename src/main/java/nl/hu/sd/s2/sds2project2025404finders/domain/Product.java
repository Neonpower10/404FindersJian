package nl.hu.sd.s2.sds2project2025404finders.domain;

import java.math.BigDecimal;

public class Product {
    private int id;
    private String productName;
    private String productImageUrl;
    private String productDescription;
    private BigDecimal pricePerDay;
    private int productStock;

    public Product() {}

    public Product(int id, String productName, String productImageUrl, String productDescription,
                   BigDecimal pricePerDay, int productStock) {
        this.id = id;
        this.productName = productName;
        this.productImageUrl = productImageUrl;
        this.productDescription = productDescription;
        this.pricePerDay = pricePerDay;
        this.productStock = productStock;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getProductImageUrl() { return productImageUrl; }
    public void setProductImageUrl(String productImageUrl) { this.productImageUrl = productImageUrl; }

    public String getProductDescription() { return productDescription; }
    public void setProductDescription(String productDescription) { this.productDescription = productDescription; }

    public BigDecimal getPricePerDay() { return pricePerDay; }
    public void setPricePerDay(BigDecimal pricePerDay) { this.pricePerDay = pricePerDay; }

    public int getProductStock() { return productStock; }
    public void setProductStock(int productStock) { this.productStock = productStock; }

    public boolean isInStock() {
        return productStock > 0;
    }

    public void addProducts() {
        this.productStock += 1;
    }
}
