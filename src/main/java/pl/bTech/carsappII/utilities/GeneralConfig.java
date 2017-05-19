package pl.bTech.carsappII.utilities;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Kamil Radek<kamil.radek@btech.pl>
 */
@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan("pl.bTech.carsappII")
@EnableJpaRepositories("pl.bTech.carsappII.*.repository")
@Import(SqlConfig.class)
public class GeneralConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("*").allowedOrigins("*").allowedHeaders("*").allowCredentials(true);
    }
}
