package com.thinkersol.API;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * The following is licensed under the GNU General Public License v3.0 as found in the LICENSE.md file
 * @authors Safwaan Taher
 * @version 1.1
 * @since 8/30/2022
 * <p>
 * Abstract:
 * CorsConfig enables CORS for a local test of this 
 * SpringBoot application to ports [3000, 5432-5433, 8080, 5000]
 * </p>
 */
@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {
    /**
     * addCorsMappings:
     * Enables CORS for the specified ports on localhost
     * @author Safwaan Taher
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000","http://localhost:5433",
                        "http://localhost:5432", "http://localhost:8080", "http://localhost:5000" )
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("header1", "header2", "header3")
                .exposedHeaders("header1", "header2")
                .allowCredentials(false).maxAge(3600);
    }
}

