// ============= CONFIGURAÇÃO SWAGGER PARA ALGACOMMENTS =============

// 1. SwaggerConfig.java - Configuração personalizada
package com.fonseca.algacomments.commentService.common;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${server.port:8080}")
    private String serverPort;

    @Bean
    public OpenAPI algaCommentsOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("AlgaComments - Comment Service API")
                        .version("1.0.0")
                        .description("""
                                ## API do Serviço de Comentários AlgaComments
                                
                                Este microserviço é responsável por:
                                - **Criação de comentários** com validação automática
                                - **Moderação inteligente** via serviço externo
                                - **Consulta paginada** de comentários
                                - **Tratamento robusto de erros** e timeouts
                                
                                ### Fluxo de Criação:
                                1. Comentário é submetido via POST
                                2. Validação de campos obrigatórios
                                3. Envio para serviço de moderação
                                4. Aprovação/Rejeição automática
                                5. Persistência no banco H2
                                
                                Desenvolvido durante o curso **EMS AlgaWorks**
                                """))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:" + serverPort)
                                .description("🏠 Ambiente Local - Comment Service"),
                        new Server()
                                .url("http://localhost:8081")
                                .description("🛡️ Serviço de Moderação")))
                .tags(List.of(
                        new Tag()
                                .name("Comments")
                                .description("🗨️ Operações de gerenciamento de comentários")
                                .externalDocs(new ExternalDocumentation()
                                        .description("Documentação do domínio")
                                        .url("https://docs.algacomments.com/comments")),
                        new Tag()
                                .name("Moderation")
                                .description("🛡️ Sistema de moderação automática"),
                        new Tag()
                                .name("Health")
                                .description("❤️ Monitoramento e saúde do serviço")))
                .components(new Components()
                        .addResponses("BadRequest", new ApiResponse()
                                .description("Dados inválidos ou campos obrigatórios ausentes")
                                .content(new Content()
                                        .addMediaType("application/json", new MediaType()
                                                .schema(new Schema<>().$ref("#/components/schemas/ValidationErrorResponse")))))
                        .addResponses("NotFound", new ApiResponse()
                                .description("Comentário não encontrado")
                                .content(new Content()
                                        .addMediaType("application/json", new MediaType()
                                                .schema(new Schema<>().$ref("#/components/schemas/ErrorResponse")))))
                        .addResponses("UnprocessableEntity", new ApiResponse()
                                .description("Comentário rejeitado pela moderação")
                                .content(new Content()
                                        .addMediaType("application/json", new MediaType()
                                                .schema(new Schema<>().$ref("#/components/schemas/ErrorResponse")))))
                        .addResponses("GatewayTimeout", new ApiResponse()
                                .description("Timeout no serviço de moderação")
                                .content(new Content()
                                        .addMediaType("application/json", new MediaType()
                                                .schema(new Schema<>().$ref("#/components/schemas/ErrorResponse")))))
                );
    }
}
