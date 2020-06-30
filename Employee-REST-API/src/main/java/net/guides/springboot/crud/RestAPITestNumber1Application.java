package net.guides.springboot.crud;
import java.util.Collections;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.google.common.base.Predicates;


import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class RestAPITestNumber1Application {
	
	public static void main(String[] args) {
		SpringApplication.run(RestAPITestNumber1Application.class, args);
	}
	
	@Bean
	public Docket api() {
	    return new Docket(DocumentationType.SWAGGER_2)
	        .select()
	        .apis(RequestHandlerSelectors.any())
	        .paths(PathSelectors.any())
	        .paths(Predicates.not(PathSelectors.regex("/error.*")))
	        .build()
	    	.apiInfo(apiDetails());
	}
	
	private ApiInfo apiDetails(){
	       return new ApiInfo(
	               "Delta System",
	               "Documentation Information over Ticketing System",
	               "1.0",
	               "Free to use",
	               new springfox.documentation.service.Contact("Madhu Sharma","https://deltassi.com","madhuxsharma@gmail.com"),
	               "API License",
	               "https://deltassi.com",
	               Collections.emptyList());
	   }
}