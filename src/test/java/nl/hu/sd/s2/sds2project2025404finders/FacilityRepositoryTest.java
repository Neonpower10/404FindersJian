package nl.hu.sd.s2.sds2project2025404finders;

import nl.hu.sd.s2.sds2project2025404finders.domain.Facility;
import nl.hu.sd.s2.sds2project2025404finders.facilities.FacilityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FacilityRepositoryTest {

    @BeforeEach
    void setUp() {
        FacilityRepository.getAll().clear();
    }

    @Test
    void testAddAndFindById() {
        Facility f = new Facility();
        f.setName("Pool");
        f.setDescription("Outdoor pool");

        Facility added = FacilityRepository.add(f);

        assertNotNull(added.getId());
        assertEquals("Pool", FacilityRepository.findById(added.getId()).getName());
    }

    @Test
    void testUpdateExistingFacility() {
        Facility f = new Facility();
        f.setName("Gym");
        FacilityRepository.add(f);

        Facility updated = new Facility();
        updated.setName("Updated Gym");
        updated.setDescription("24/7 access");

        Facility result = FacilityRepository.update(f.getId(), updated);
        assertNotNull(result);
        assertEquals("Updated Gym", result.getName());
        assertEquals("24/7 access", result.getDescription());
    }

    @Test
    void testUpdateFacilityUpdatesAllFields() {
        Facility f = new Facility();
        f.setName("Old Name");
        f.setDescription("Old Desc");
        f.setImage("old.jpg");
        f.setStatus("Closed");
        FacilityRepository.add(f);

        Facility updated = new Facility();
        updated.setName("New Name");
        updated.setDescription("New Desc");
        updated.setImage("new.jpg");
        updated.setStatus("Open");

        Facility result = FacilityRepository.update(f.getId(), updated);
        assertEquals("New Name", result.getName());
        assertEquals("New Desc", result.getDescription());
        assertEquals("new.jpg", result.getImage());
        assertEquals("Open", result.getStatus());
    }


    @Test
    void testUpdateNonExistingFacilityReturnsNull() {
        Facility updated = new Facility();
        updated.setName("Nonexistent");
        assertNull(FacilityRepository.update(99, updated));
    }

    @Test
    void testDeleteFacility() {
        Facility f = new Facility();
        f.setName("Spa");
        FacilityRepository.add(f);

        boolean deleted = FacilityRepository.delete(f.getId());
        assertTrue(deleted);
        assertNull(FacilityRepository.findById(f.getId()));
    }

    @Test
    void testGetAll() {
        FacilityRepository.add(new Facility());
        FacilityRepository.add(new Facility());
        List<Facility> all = FacilityRepository.getAll();
        assertEquals(2, all.size());
    }
}
