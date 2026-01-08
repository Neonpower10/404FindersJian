package nl.hu.sd.s2.sds2project2025404finders;

import jakarta.ws.rs.core.Response;
import nl.hu.sd.s2.sds2project2025404finders.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ReservationResourceTest {

    private ReservationResource reservationResource;
    private Product testProduct;

    @BeforeEach
    void setUp() {
        reservationResource = new ReservationResource();

        ProductRepository.clearAll();
        ReservationRepository.clearAll();

        testProduct = new Product();
        testProduct.setProductName("Testfiets");
        testProduct.setPricePerDay(new BigDecimal("15.00"));
        testProduct.setProductStock(10);

        ProductRepository.add(testProduct);
    }

    @Test
    void createReservationSucceedsWithValidData() {
        ReservationResource.CreateReservationRequest req = new ReservationResource.CreateReservationRequest();
        req.productId = testProduct.getProductId();
        req.startDate = "2025-01-10";
        req.endDate = "2025-01-12";
        req.productAmount = 2;

        try (Response response = reservationResource.createReservation(req)) {
            assertEquals(201, response.getStatus());

            @SuppressWarnings("unchecked")
            Map<String, Object> body = (Map<String, Object>) response.getEntity();

            assertNotNull(body);
            assertEquals(true, body.get("ok"));
            assertNotNull(body.get("reservationId"));
            assertEquals(testProduct.getProductId(), body.get("productId"));
            assertEquals(2, body.get("productAmount"));
            assertNotNull(body.get("totalPrice"));
        }
    }

    @Test
    void createReservationFailsWhenEndBeforeStart() {
        ReservationResource.CreateReservationRequest req = new ReservationResource.CreateReservationRequest();
        req.productId = testProduct.getProductId();
        req.startDate = "2025-01-12";
        req.endDate = "2025-01-10";
        req.productAmount = 1;

        try (Response response = reservationResource.createReservation(req)) {
            assertEquals(400, response.getStatus());
        }
    }
}
