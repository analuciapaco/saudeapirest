/**
 * @Empresa: Conexa Saúde
 * @author: Ana Lúcia Seles
  *@Versão: Classe = 1
 */

package com.conexa.saudeapirest.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket saudeApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("api.saude.com")).build().apiInfo(metaInfo());
	}

	private ApiInfo metaInfo() {
		ApiInfo apiInfo = new ApiInfo("Conexa Saúde API REST", "API REST de agendamento de consultas.", "1.0",
				"Terms os Service",
				new Contact("Ana Lúcia Seles Pacó",
						"https://www.linkedin.com/in/ana-l%C3%BAcia-seles-pac%C3%B3-5934a158/", "analuciasp@gmail.com"),
				"Apache License Version 2.0", "https://www.apache.org/licesen.html", new ArrayList<VendorExtension>());
		return apiInfo;

	}

}
