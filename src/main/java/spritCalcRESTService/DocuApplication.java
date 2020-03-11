package spritCalcRESTService;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.ws.rs.core.Application;

@OpenAPIDefinition(
        info = @Info(
                title="SpritCalc REST API DHBW-Mosbach 2020",
                version = "1.0",
                contact = @Contact(
                        name = "SpritCalc API Support",
                        url = "www.google.de",
                        email = "help@microsoft.com"))
)
public class DocuApplication extends Application {
}
