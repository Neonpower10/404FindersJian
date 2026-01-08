package nl.hu.sd.s2.sds2project2025404finders;

import jakarta.ws.rs.core.Response;
import nl.hu.sd.s2.sds2project2025404finders.info.CampingInfo;
import nl.hu.sd.s2.sds2project2025404finders.info.CampingInfoResource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the CampingInfoResource REST endpoint.
 * Verifies that the default camping info can be retrieved and that
 * invalid updates are handled correctly.
 */
class CampingInfoResourceTest {

    @Test
    void getCampingInfo_returnsOkWithBody() {
        // Arrange: maak een nieuwe resource
        CampingInfoResource resource = new CampingInfoResource();

        // Act: roep de GET-methode aan
        Response response = resource.getCampingInfo();

        // Assert: status + body
        assertEquals(200, response.getStatus(), "GET /camping-info should return 200 OK");

        Object entity = response.getEntity();
        assertNotNull(entity, "Response entity should not be null");
        assertTrue(entity instanceof CampingInfo, "Entity should be a CampingInfo object");

        CampingInfo info = (CampingInfo) entity;

        // Extra asserts op de dummy data
        assertNotNull(info.getAbout(), "About text should not be null");
        assertNotNull(info.getAddress(), "Address should not be null");
        assertFalse(info.getFacilities().isEmpty(), "Facilities list should not be empty");
        assertFalse(info.getHouseRules().isEmpty(), "House rules list should not be empty");
    }

    @Test
    void updateCampingInfo_withNullBody_returnsBadRequest() {
        // Arrange
        CampingInfoResource resource = new CampingInfoResource();

        // Act: stuur een 'null' body
        Response response = resource.updateCampingInfo(null);

        // Assert
        assertEquals(400, response.getStatus(),
                "PUT /camping-info with null body should return 400 BAD_REQUEST");
    }
}

