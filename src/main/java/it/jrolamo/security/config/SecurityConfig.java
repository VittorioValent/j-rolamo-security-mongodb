package it.jrolamo.security.config;

import com.google.common.collect.ImmutableList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.Header;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import it.jrolamo.security.core.JWTAuthenticationEntryPoint;
import it.jrolamo.security.core.RequestFilter;
import it.jrolamo.security.service.IUserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@PropertySource("classpath:/security/security.yml")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private IUserService userService;

    @Autowired
    private RequestFilter requestFilter;

    @Value("${security.disable}")
    private final boolean disableSecurity = false;

    /**
     * Configurazione dell'AuthenticationManagerBuilder. L'oggetto auth viene
     * valorizzato con UserService (che implementa UserDetailsService e quindi il
     * metodo loadByUsername(String username)). All'avvio dell'applicazione, Spring
     * usa il builder per configurare l'oggetto AuthenticationManager.
     *
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    /**
     * Bean di Spring Security che si fa onere di verificare la correttezza delle
     * credenziali al momento del login di un utente.
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures {@link HttpSecurity} params like
     * <ul>
     * <li>CSRF and CORS,
     * <li>Auth path whitelist,
     * <li>{@link Header}
     * <li>{@link JWTAuthenticationEntryPoint},
     * <li>{@link SessionManagementConfigurer}.
     * </ul>
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // httpSecurity.cors().configurationSource(corsConfigurationSource());
        if (disableSecurity) {
            httpSecurity.csrf().disable().authorizeRequests().anyRequest().permitAll();
        } else {
            httpSecurity.csrf().disable().authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll().anyRequest()
                    .authenticated().and().csrf().ignoringAntMatchers("/**").and().headers().frameOptions().sameOrigin()
                    .and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            httpSecurity.addFilterBefore(requestFilter, UsernamePasswordAuthenticationFilter.class);
        }
        httpSecurity.cors();

    }

    /**
     * All these path segments are auth-free
     */
    private static final String[] AUTH_WHITELIST = { "/v2/api-docs/**", "/swagger-**", "/h2-console/**",
            "/api/**/public/**", "/auth/**", "/swagger-resources/**", "/webjars/**", "/workshop/**", "/actuator/**" };

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(ImmutableList.of("*"));
        configuration.setAllowedMethods(ImmutableList.of("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type",
                "Access-Control-Allow-Headers", "Access-Control-Allow-Origin"));
        // configuration.setExposedHeaders(ImmutableList.of("Authorization",
        // "Cache-Control", "Content-Type",
        // "Access-Control-Allow-Headers", "Access-Control-Allow-Origin"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
