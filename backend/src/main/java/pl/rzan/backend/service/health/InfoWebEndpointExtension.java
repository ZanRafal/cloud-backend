package pl.rzan.backend.service.health;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;
import pl.rzan.backend.DiskInfoCollector;

import java.util.HashMap;

@Component
public class InfoWebEndpointExtension implements InfoContributor {

    private final DiskInfoCollector delegate = DiskInfoCollector.getInstance();

    @Override
    public void contribute(Info.Builder builder) {
        HashMap<String, DiskInfoCollector.DiskInfo> info = delegate.getDiskInfo();
        builder.withDetail("Disks Quantity", delegate.getDiskQuantity());
        builder.withDetail("Available Disks", info).build();
    }

}

