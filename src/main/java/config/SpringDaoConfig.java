package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = "java")
@Import(value = {DatabaseConfig.class})
public class SpringDaoConfig {
}
