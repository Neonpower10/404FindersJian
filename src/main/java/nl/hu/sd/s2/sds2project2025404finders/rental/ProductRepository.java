package nl.hu.sd.s2.sds2project2025404finders.rental;

import nl.hu.sd.s2.sds2project2025404finders.domain.Product;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ProductRepository {
    private static final List<Product> products = new ArrayList<>();
    private static final AtomicInteger idCounter = new AtomicInteger(1);

    public static List<Product> getAll() {
        return products;
    }

    public static Product findById(int id) {
        return products.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public static Product add(Product product) {
        product.setId(idCounter.getAndIncrement());
        products.add(product);
        return product;
    }

    public static Product update(int id, Product updated) {
        Product existing = findById(id);
        if (existing != null) {
            existing.setProductName(updated.getProductName());
            existing.setProductImageUrl(updated.getProductImageUrl());
            existing.setProductDescription(updated.getProductDescription());
            existing.setPricePerDay(updated.getPricePerDay());
            existing.setProductStock(updated.getProductStock());
        }
        return existing;
    }

    public static boolean delete(int id) {
        return products.removeIf(p -> p.getId() == id);
    }
}
