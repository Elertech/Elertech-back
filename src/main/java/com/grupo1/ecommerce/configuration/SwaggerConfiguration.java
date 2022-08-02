package com.grupo1.ecommerce.configuration;

import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

@Configuration
public class SwaggerConfiguration {

	@Bean
	public OpenAPI springEcommerceOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("API Ecommerce Elertech")
						.description("Desenvolvedores: Vinicius Campanholi , Guilherme Ariza, "
								+ "Vanessa Jesus, Lucas Theberge e Pamela Azevedo")
						.version("v.2.0")
						.license(new License()
								.name("elertech.com")
								.url("https://elertech.netlify.app"))
						.contact(new Contact()
								.name("Vinicius Campanholi, Guilherme Ariza, "
										+ "Vanessa Jesus, Lucas Theberge e Pamela Azevedo")
								.email("viniciuscampanholi@ghotmail.com,guilherme.ariza@gmail.com,lucas.theberge@gmail.com"
										+ "pamela.azevedo@gmail.com")))
				.externalDocs(new ExternalDocumentation()
						.description("Repositório GitHub")
						.url("https://github.com/Elertech"));

	}

	@Bean
	public OpenApiCustomiser customerGlobalHeaderOpenApiCustomiser() {
		return openApi -> {
			openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> {
				ApiResponses apiResponses = operation.getResponses();
				apiResponses.addApiResponse("200", createApiResponse("Sucesso!!!"));
				apiResponses.addApiResponse("201", createApiResponse("Objeto Persistido"));
				apiResponses.addApiResponse("204", createApiResponse("Objeto Excluído"));
				apiResponses.addApiResponse("400", createApiResponse("Erro na Requisição"));
				apiResponses.addApiResponse("401", createApiResponse("Acesso Não Autorizado"));
				apiResponses.addApiResponse("404", createApiResponse("Objeto Não Encontrado"));
				apiResponses.addApiResponse("500", createApiResponse("Erro na Aplicação"));
			}));
		};
	}

	private ApiResponse createApiResponse(String message) {
		return new ApiResponse().description(message);

	}
}