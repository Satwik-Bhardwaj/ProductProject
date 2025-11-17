package com.mark.product.config.tools;

import com.mark.product.persistence.dto.ErrorResponseModel;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.converter.ResolvedSchema;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Map;

@Configuration
public class SwaggerConfig {

    private static final String APPLICATION_JSON = "application/json";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Product Project API")
                        .version("1.0")
                        .description("API documentation for Product project application"));
    }

    @Bean
    public OpenApiCustomizer openApiCustomizer() {

        ResolvedSchema errorResponseModelSchema = ModelConverters.getInstance()
                .resolveAsResolvedSchema(new AnnotatedType(ErrorResponseModel.class));
        Content contentWithExample400 =
                new Content().addMediaType(APPLICATION_JSON, new MediaType().schema(errorResponseModelSchema.schema)
                        .example(new ErrorResponseModel("BAD_REQUEST", "Invalid input", Map.of(
                                "field1", "Field 'price' must be a positive number",
                                "field2", "Field 'name' cannot be empty"
                        ), LocalDateTime.now())));
        Content contentWithExample401 =
                new Content().addMediaType(APPLICATION_JSON, new MediaType().schema(errorResponseModelSchema.schema)
                        .example(new ErrorResponseModel("UNAUTHORIZED", "Unauthorized access", LocalDateTime.now())));
        Content contentWithExample403 =
                new Content().addMediaType(APPLICATION_JSON, new MediaType().schema(errorResponseModelSchema.schema)
                        .example(new ErrorResponseModel("FORBIDDEN", "Access denied", LocalDateTime.now())));
        Content contentWithExample404 =
                new Content().addMediaType(APPLICATION_JSON, new MediaType().schema(errorResponseModelSchema.schema)
                        .example(new ErrorResponseModel("NOT_FOUND", "Resource not found", LocalDateTime.now())));
        Content contentWithExample500 =
                new Content().addMediaType(APPLICATION_JSON, new MediaType().schema(errorResponseModelSchema.schema)
                        .example(new ErrorResponseModel("INTERNAL_SERVER_ERROR", "An unexpected error occurred", LocalDateTime.now())));
        return openApi -> openApi.getPaths().forEach((path, pathItem) -> pathItem.readOperations().forEach(operation -> operation.getResponses()
                .addApiResponse("400", new ApiResponse()
                        .description("Bad Request")
                        .content(contentWithExample400))
                .addApiResponse("401", new ApiResponse()
                        .description("Unauthorized")
                        .content(contentWithExample401))
                .addApiResponse("403", new ApiResponse()
                        .description("Forbidden")
                        .content(contentWithExample403))
                .addApiResponse("404", new ApiResponse()
                        .description("Not Found")
                        .content(contentWithExample404))
                .addApiResponse("500", new ApiResponse()
                        .description("Internal Server Error")
                        .content(contentWithExample500))));
    }
}
