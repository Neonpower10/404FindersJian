package nl.hu.sd.s2.sds2project2025404finders;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class ThemeApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(ThemeResource.class);
        classes.add(RegistrationResource.class);
        classes.add(CorsFilter.class);
        classes.add(OptionsResource.class);
        return classes;
    }
}
