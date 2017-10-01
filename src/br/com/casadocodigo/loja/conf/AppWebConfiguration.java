package br.com.casadocodigo.loja.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@EnableWebMvc
@ComponentScan(basePackages = "br.com.casadocodigo")
public class AppWebConfiguration extends WebMvcConfigurerAdapter {
	
	@Bean
	public InternalResourceViewResolver
		internalResourceViewResolver() {
			InternalResourceViewResolver resolver =
			new InternalResourceViewResolver();
			resolver.setPrefix("/WEB-INF/views/");
			resolver.setSuffix(".jsp");
			return resolver;	
	}
	
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver cmr = new CommonsMultipartResolver();
		return cmr;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations(
				"/resources/");
		registry.addResourceHandler("/image/**")
				.addResourceLocations("/image/");
	}

}
