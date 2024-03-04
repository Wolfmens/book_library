package study.booklibrary.configuration.property;

import lombok.Data;

import java.time.Duration;

@Data
public class ExpiryProperty {

    private Duration expiry = Duration.ZERO;

}
