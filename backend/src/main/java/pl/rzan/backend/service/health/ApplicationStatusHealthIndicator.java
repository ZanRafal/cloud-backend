package pl.rzan.backend.service.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.HttpCodeStatusMapper;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component()
public class ApplicationStatusHealthIndicator implements HealthIndicator, HttpCodeStatusMapper {
    private final LocalDateTime CREATED = getStartTime();

    @Override
    public Health health() {
        Health.Builder status = Health.up()
                .withDetail("SectionName", "Application Health-check")
                .withDetail("startTime", getFormattedStartTime())
                .withDetail("runningFor", getUpTime());

        return status.build();
    }
    private String getFormattedStartTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return CREATED.format(formatter);
    }
    private LocalDateTime getStartTime() {
        return LocalDateTime.now();
    }
    private long getUpTime() {
        return (System.currentTimeMillis() - CREATED.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()) / 1000;
    }

    @Override
    public int getStatusCode(Status status) {
        if(status.equals(Status.UP)) {
            return 200;
        } else if (status.equals(Status.DOWN) || status.equals(Status.UNKNOWN)) {
            return 500;
        } else if (status.equals(Status.OUT_OF_SERVICE)) {
            return 503;
        }
        return 200;
    }
}