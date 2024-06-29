package musicTranspose.monitoringserver;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;

@EnableAdminServer
@SpringBootApplication
public class MonitoringServer {
    public static void main(String[] args) {

        SpringApplication.run(MonitoringServer.class, args);
    }

}