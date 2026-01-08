package nl.hu.sd.s2.sds2project2025404finders;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FacilityApplicationTest {

    @Test
    void testGetClassesRegistersResources() {
        FacilityApplication app = new FacilityApplication();
        Set<Class<?>> classes = app.getClasses();

        assertTrue(classes.contains(FacilityResource.class), "FacilityResource should be registered");
        assertTrue(classes.contains(CorsFilter.class), "CorsFilter should be registered");
    }
}
