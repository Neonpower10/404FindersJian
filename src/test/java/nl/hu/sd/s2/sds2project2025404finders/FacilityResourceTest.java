//package nl.hu.sd.s2.sds2project2025404finders;
//
//import jakarta.ws.rs.core.Response;
//import nl.hu.sd.s2.sds2project2025404finders.domain.Facility;
//import nl.hu.sd.s2.sds2project2025404finders.facilities.FacilityRepository;
//import nl.hu.sd.s2.sds2project2025404finders.facilities.FacilityResource;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.MockedStatic;
//import org.mockito.Mockito;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class FacilityResourceTest {
//
//    private FacilityResource resource;
//
//    @BeforeEach
//    void setUp() {
//        resource = new FacilityResource();
//    }
//
//    @Test
//    void testGetAllFacilities() {
//        Facility f1 = new Facility(); f1.setName("A");
//        Facility f2 = new Facility(); f2.setName("B");
//
//        try (MockedStatic<FacilityRepository> mockRepo = Mockito.mockStatic(FacilityRepository.class)) {
//            mockRepo.when(FacilityRepository::getAll).thenReturn(List.of(f1, f2));
//            List<Facility> result = resource.getAllFacilities();
//            assertEquals(2, result.size());
//        }
//    }
//
//    @Test
//    void testGetFacilityFound() {
//        Facility f = new Facility(); f.setId(1); f.setName("Test");
//        try (MockedStatic<FacilityRepository> mockRepo = Mockito.mockStatic(FacilityRepository.class)) {
//            mockRepo.when(() -> FacilityRepository.findById(1)).thenReturn(f);
//            Response response = resource.getFacility(1);
//            assertEquals(200, response.getStatus());
//            assertEquals(f, response.getEntity());
//        }
//    }
//
//    @Test
//    void testGetFacilityNotFound() {
//        try (MockedStatic<FacilityRepository> mockRepo = Mockito.mockStatic(FacilityRepository.class)) {
//            mockRepo.when(() -> FacilityRepository.findById(99)).thenReturn(null);
//            Response response = resource.getFacility(99);
//            assertEquals(404, response.getStatus());
//        }
//    }
//
//    @Test
//    void testAddFacility() {
//        Facility f = new Facility(); f.setName("Pool");
//        try (MockedStatic<FacilityRepository> mockRepo = Mockito.mockStatic(FacilityRepository.class)) {
//            mockRepo.when(() -> FacilityRepository.add(f)).thenReturn(f);
//            Response response = resource.addFacility(f);
//            assertEquals(201, response.getStatus());
//            assertEquals(f, response.getEntity());
//        }
//    }
//
//    @Test
//    void testUpdateFacilityFound() {
//        Facility f = new Facility(); f.setName("Updated");
//        try (MockedStatic<FacilityRepository> mockRepo = Mockito.mockStatic(FacilityRepository.class)) {
//            mockRepo.when(() -> FacilityRepository.update(1, f)).thenReturn(f);
//            Response response = resource.updateFacility(1, f);
//            assertEquals(200, response.getStatus());
//        }
//    }
//
//    @Test
//    void testUpdateFacilityNotFound() {
//        Facility f = new Facility();
//        try (MockedStatic<FacilityRepository> mockRepo = Mockito.mockStatic(FacilityRepository.class)) {
//            mockRepo.when(() -> FacilityRepository.update(99, f)).thenReturn(null);
//            Response response = resource.updateFacility(99, f);
//            assertEquals(404, response.getStatus());
//        }
//    }
//
//    @Test
//    void testDeleteFacilityFound() {
//        try (MockedStatic<FacilityRepository> mockRepo = Mockito.mockStatic(FacilityRepository.class)) {
//            mockRepo.when(() -> FacilityRepository.delete(1)).thenReturn(true);
//            Response response = resource.deleteFacility(1);
//            assertEquals(204, response.getStatus());
//        }
//    }
//
//    @Test
//    void testDeleteFacilityNotFound() {
//        try (MockedStatic<FacilityRepository> mockRepo = Mockito.mockStatic(FacilityRepository.class)) {
//            mockRepo.when(() -> FacilityRepository.delete(99)).thenReturn(false);
//            Response response = resource.deleteFacility(99);
//            assertEquals(404, response.getStatus());
//        }
//    }
//}
