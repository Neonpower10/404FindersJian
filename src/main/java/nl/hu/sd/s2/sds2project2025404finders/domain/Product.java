package nl.hu.sd.s2.sds2project2025404finders.domain;

import java.math.BigDecimal;

/**
 * Represents a rental product that can be rented by campers.
 */
public class Product {
    private int productId;
    private String productName;
    private String productImageUrl;
    private String productDescription;
    private BigDecimal pricePerDay;
    private int productStock;
    // Published status: true if product is visible to customers, false if it's still a concept
    // New products start as unpublished (false) so admin can review before publishing
    private boolean published;

    /**
     * Default constructor required for JSON serialization.
     */
    public Product() {
    }

    public Product(String productName, String productDescription, BigDecimal pricePerDay, int productStock) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.pricePerDay = pricePerDay;
        this.productStock = productStock;
        this.published = false; // Default to unpublished (concept) so admin can review before publishing
    }

    /**
     * Checks if the product is currently in stock.
     * A product is in stock if productStock is greater than 0.
     * 
     * @return true if product is available (stock > 0), false otherwise
     */
    public boolean isInStock() {
        return productStock > 0;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public BigDecimal getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(BigDecimal pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public int getProductStock() {
        return productStock;
    }

    public void setProductStock(int productStock) {
        this.productStock = productStock;
    }

    /**
     * Gets the published status of the product.
     * @return true if product is published (visible to customers), false if it's a concept
     */
    public boolean isPublished() {
        return published;
    }

    /**
     * Sets the published status of the product.
     * @param published true to publish (make visible to customers), false to unpublish
     */
    public void setPublished(boolean published) {
        this.published = published;
    }
}

