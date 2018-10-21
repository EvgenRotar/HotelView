package com.evgen.config;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.evgen.*")
public class WebConfig implements WebMvcConfigurer {

  private final ApplicationContext applicationContext;

  @Autowired
  public WebConfig(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }

  @Bean
  public MappingJackson2HttpMessageConverter jsonConverter() {
    MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
    ObjectMapper objectMapper = new ObjectMapper();
    jsonConverter.setObjectMapper(objectMapper);
    return jsonConverter;
  }

  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    converters.add(jsonConverter());
  }

  @Bean
  public SpringResourceTemplateResolver templateResolver() {
    SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
    templateResolver.setApplicationContext(applicationContext);
    templateResolver.setPrefix("/WEB-INF/views/");
    templateResolver.setSuffix(".html");
    return templateResolver;
  }

  @Bean
  public SpringTemplateEngine templateEngine() {
    SpringTemplateEngine templateEngine = new SpringTemplateEngine();
    templateEngine.setTemplateResolver(templateResolver());
    templateEngine.setEnableSpringELCompiler(true);
    return templateEngine;
  }

  @Override
  public void configureViewResolvers(ViewResolverRegistry registry) {
    ThymeleafViewResolver resolver = new ThymeleafViewResolver();
    resolver.setTemplateEngine(templateEngine());
    registry.viewResolver(resolver);
  }

}