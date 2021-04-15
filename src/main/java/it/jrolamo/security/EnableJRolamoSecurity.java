package it.jrolamo.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

/**
 *
 * @author Vittorio Valent
 *
 * @since 0.0.1
 */
@Import(value = JRolamoSecurityMongoDB.class)
@Target(value = { ElementType.TYPE })
@Retention(value = RetentionPolicy.RUNTIME)
public @interface EnableJRolamoSecurity {
    
}
