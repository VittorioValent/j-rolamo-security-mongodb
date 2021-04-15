package it.jrolamo.security;

import it.jrolamo.security.repository.RoleRepository;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 *
 * @author Vittorio Valent
 *
 * @since 0.0.1
 */
@Configuration
@ComponentScan(basePackageClasses = JRolamoSecurityMongoDB.class)
@EnableMongoRepositories(basePackageClasses = RoleRepository.class)
public class JRolamoSecurityMongoDB {

}
