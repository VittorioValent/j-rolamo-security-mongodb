package it.jrolamo.security;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Vittorio Valent
 *
 * @since 0.0.1
 */
@Configuration
@ComponentScan(basePackageClasses = JRolamoSecurityMongoDB.class)
// @EnableMongoRepositories(basePackageClasses = AbstractRoleRepository.class)
public class JRolamoSecurityMongoDB {

}
