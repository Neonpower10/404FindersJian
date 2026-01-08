package nl.hu.sd.s2.sds2project2025404finders;

import jakarta.ws.rs.core.Response;
import nl.hu.sd.s2.sds2project2025404finders.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductResourceTest {

    private ProductResource productResource;

    @BeforeEach
    void setUp() {
        productResource = new ProductResource();

        ProductRepository.clearAll();

        Product p = new Product();
        p.setProductName("Mountainbike");
        p.setPricePerDay(new BigDecimal("15.00"));
        p.setProductStock(5);
        ProductRepository.add(p);
    }

    @Test
    void getAllProductsReturnsList() {
        List<Product> products = productResource.getAllProducts();

        assertNotNull(products);
        assertFalse(products.isEmpty());
    }

    @Test
    void getProductReturns200WhenExists() {
        try (Response response = productResource.getProduct(1)) {
            assertEquals(200, response.getStatus());
            assertNotNull(response.getEntity());
        }
    }

    @Test
    void getProductReturns404WhenNotExists() {
        try (Response response = productResource.getProduct(9999)) {
            assertEquals(404, response.getStatus());
        }
    }
}
