package nl.hu.sd.s2.sds2project2025404finders;

import nl.hu.sd.s2.sds2project2025404finders.HomeRepository;
import nl.hu.sd.s2.sds2project2025404finders.domain.HomeData;

public class HomeService {

    private final HomeRepository repository = new HomeRepository();

    public HomeData getHomeData() {
        return repository.load();
    }

    public void updateHomeData(HomeData data) {
        repository.save(data);
    }
}
