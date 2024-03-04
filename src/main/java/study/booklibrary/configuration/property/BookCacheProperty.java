package study.booklibrary.configuration.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@ConfigurationProperties(prefix = "book.cache")
public class BookCacheProperty {

    private List<String> cacheNames = new ArrayList<>();

    private Map<String, ExpiryProperty> caches = new HashMap<>();

}
