package nl.hu.sd.s2.sds2project2025404finders.verhuur;

import java.util.*;

public class ProductRepository {

    private Map<Integer, Product> products = new HashMap<>();
    private int idCounter = 1;

    public Product save(Product p) {
        if (p.getId() == 0) p.setId(idCounter++);
        products.put(p.getId(), p);
        return p;
    }

    public Product findById(int id) {
        return products.get(id);
    }

    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    public boolean delete(int id) {
        return products.remove(id) != null;
    }
}