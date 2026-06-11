package br.gov.caixa.resource;

import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@OpenAPIDefinition(
        tags = {
                @Tag(name="Simulação", description="Operações de simulações.")
        },
        info = @Info(
                title = "API de Simulação de Financiamento",
                version = "1.0.0",
                description = "API para realizar simulações de financiamento imobiliário.",
                contact = @Contact(
                        name = "Suporte API",
                        email = "simulador@gmail.com",
                        url = "https://www.caixa.gov.br/"
                ),
                license = @License(
                        name = "Licença MIT",
                        url = "https://opensource.org/licenses/MIT"
                )
        )
)
public class APIResource extends Application {
}
