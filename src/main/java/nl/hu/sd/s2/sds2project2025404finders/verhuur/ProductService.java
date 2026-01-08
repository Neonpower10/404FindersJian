package nl.hu.sd.s2.sds2project2025404finders.verhuur;

import java.util.List;

public class ProductService {

    private ProductRepository repo = new ProductRepository();

    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    public Product getProductById(int id) {
        Product p = repo.findById(id);
        if (p == null) {
            throw new IllegalArgumentException("Product not found");
        }
        return p;
    }

    public Product createProduct(Product p) {
        validateProduct(p);
        return repo.save(p);
    }

    public Product updateProduct(int id, Product updated) {
        Product existing = getProductById(id);

        validateProduct(updated);

        existing.setProductName(updated.getProductName());
        existing.setProductDescription(updated.getProductDescription());
        existing.setProductImageUrl(updated.getProductImageUrl());
        existing.setPricePerDay(updated.getPricePerDay());
        existing.setProductStock(updated.getProductStock());

        return repo.save(existing);
    }

    public boolean deleteProduct(int id) {
        return repo.delete(id);
    }

    private void validateProduct(Product p) {
        if (p.getProductName() == null || p.getProductName().isBlank())
            throw new IllegalArgumentException("Product name required");

        if (p.getProductDescription() == null || p.getProductDescription().isBlank())
            throw new IllegalArgumentException("Product description required");

        if (p.getPricePerDay() == null || p.getPricePerDay().doubleValue() <= 0)
            throw new IllegalArgumentException("Price must be > 0");

        if (p.getProductStock() < 0)
            throw new IllegalArgumentException("Stock cannot be negative");
    }
}