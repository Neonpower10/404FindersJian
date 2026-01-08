package nl.hu.sd.s2.sds2project2025404finders.dashboard;

/**
 * Service layer that retrieves dashboard data from the repository.
 */
public class DashboardService {

    private final DashboardRepository repository = new DashboardRepository();

    public DashboardData getDashboardData() {
        return repository.fetchDashboardData();
    }
}
