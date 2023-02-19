package ru.aasmc.organization;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import ru.aasmc.organization.model.Organization;
import ru.aasmc.organization.repository.OrganizationRepository;

@SpringBootApplication
@RefreshScope
public class OrganizationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrganizationServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(OrganizationRepository repository) {
        return args -> {
            repository.save(new Organization("e6a625cc-718b-48c2-ac76-1dfdff9a531e", "Ostock", "Illary Huaylupo", "illaryhs@gmail.com", "888888888"));
            repository.save(new Organization("d898a142-de44-466c-8c88-9ceb2c2429d3", "OptimaGrowth", "Admin", "illaryhs@gmail.com", "888888888"));
            repository.save(new Organization("e839ee96-28de-4f67-bb79-870ca89743a0", "Ostock", "Admin", "illaryhs@gmail.com", "888888888"));
        };
    }

}
