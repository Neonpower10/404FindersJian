package nl.hu.sd.s2.sds2project2025404finders;

import nl.hu.sd.s2.sds2project2025404finders.domain.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ProductRepository {
    private static final List<Product> products = new ArrayList<>();
    private static final AtomicInteger idCounter = new AtomicInteger(1);

    static {
        Product bike = new Product();
        bike.setProductId(idCounter.getAndIncrement());
        bike.setProductName("Mountainbike");
        bike.setProductDescription("Stevige mountainbike voor offroad ritten.");
        bike.setProductImageUrl(null);
        bike.setPricePerDay(new BigDecimal("15.00"));
        bike.setProductStock(5);
        products.add(bike);

        Product bbq = new Product();
        bbq.setProductId(idCounter.getAndIncrement());
        bbq.setProductName("BBQ set");
        bbq.setProductDescription("Complete BBQ set met grill en tang.");
        bbq.setPricePerDay(new BigDecimal("10.00"));
        bbq.setProductStock(3);
        products.add(bbq);
    }

    public static List<Product> getAll() {
        return products;
    }

    public static Product findById(int id) {
        return products.stream()
                .filter(p -> p.getProductId() == id)
                .findFirst()
                .orElse(null);
    }

    public static Product add(Product product) {
        if (product.getProductId() == 0) {
            product.setProductId(idCounter.getAndIncrement());
        }
        products.add(product);
        return product;
    }

    public static Product update(int id, Product updated) {
        Product existing = findById(id);
        if (existing == null || updated == null) return null;

        if (updated.getProductName() != null) existing.setProductName(updated.getProductName());
        if (updated.getProductDescription() != null) existing.setProductDescription(updated.getProductDescription());
        if (updated.getProductImageUrl() != null) existing.setProductImageUrl(updated.getProductImageUrl());
        if (updated.getPricePerDay() != null) existing.setPricePerDay(updated.getPricePerDay());
        if (updated.getProductStock() >= 0) existing.setProductStock(updated.getProductStock());

        return existing;
    }

    public static boolean delete(int id) {
        return products.removeIf(p -> p.getProductId() == id);
    }

    public static void clearAll() {
        products.clear();
        idCounter.set(1);
    }
}
