package nl.hu.sd.s2.sds2project2025404finders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.ws.rs.ext.ContextResolver;
import jakarta.ws.rs.ext.Provider;

/**
 * Configures Jackson ObjectMapper to support Java 8 date/time types (LocalDate, etc.)
 * This is needed so that Jackson can serialize/deserialize LocalDate objects.
 */
@Provider
public class JacksonObjectMapperProvider implements ContextResolver<ObjectMapper> {

    private final ObjectMapper objectMapper;

    public JacksonObjectMapperProvider() {
        objectMapper = new ObjectMapper();
        // Register JavaTimeModule to handle LocalDate, LocalDateTime, etc.
        objectMapper.registerModule(new JavaTimeModule());
        // Don't write dates as timestamps, write them as ISO-8601 strings
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return objectMapper;
    }
}


