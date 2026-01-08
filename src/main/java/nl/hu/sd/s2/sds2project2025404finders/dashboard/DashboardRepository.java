package nl.hu.sd.s2.sds2project2025404finders.dashboard;

/**
 * Repository class responsible for fetching dashboard data.
 * In a real application, this would connect to a database.
 */
public class DashboardRepository {

    public DashboardData fetchDashboardData() {
        DashboardData data = new DashboardData();

        // Mock data for demonstration
        data.setNewMessages(3);
        data.setNewBookings(5);
        data.setArrivals(2);
        data.setDepartures(4);

        return data;
    }
}
