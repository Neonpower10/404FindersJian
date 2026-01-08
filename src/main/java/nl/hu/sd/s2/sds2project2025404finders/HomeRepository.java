package nl.hu.sd.s2.sds2project2025404finders;

import com.google.gson.Gson;
import nl.hu.sd.s2.sds2project2025404finders.domain.HomeData;

import java.io.*;

public class HomeRepository {
    // Use Tomcat's base dir if present, otherwise use user.dir
    private static final String DATA_FILE;
    static {
        String base = System.getProperty("catalina.base"); // Tomcat base dir
        if (base == null || base.isEmpty()) {
            base = System.getProperty("user.dir");
        }
        DATA_FILE = base + File.separator + "homeData.json";
    }

    private final Gson gson = new Gson();

    public HomeData load() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            // Return default data object if file missing
            return new HomeData();
        }

        try (Reader reader = new FileReader(file)) {
            HomeData data = gson.fromJson(reader, HomeData.class);
            if (data == null) return new HomeData();
            return data;
        } catch (IOException e) {
            e.printStackTrace();
            return new HomeData();
        }
    }

    public void save(HomeData data) {
        File file = new File(DATA_FILE);
        // Try to ensure parent dir exists (should be fine for catalina.base / user.dir)
        file.getParentFile().mkdirs();
        try (Writer writer = new FileWriter(file)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
