package spritCalcRESTService;

import apis.ApiRequestData;
import apis.ApiRequests;
import apis.petrolApi.PetrolTyp;
import io.vertx.core.http.HttpServerRequest;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.jboss.logging.Logger;

import javax.annotation.Resource;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.*;
import java.util.List;

@Path("/spritCalc")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SpritCalcREST {

    private static final Logger LOG = Logger.getLogger(SpritCalcREST.class);
    @Context
    UriInfo info;

    @Context
    HttpServerRequest request;

    @GET
    //@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Operation(summary = "SpritCalcAPI GET Path",
            description = "SpritCalcAPI Service")
    public Response spritCalcAPI(@QueryParam("street") @DefaultValue("") String street,
                                 @QueryParam("streetNum") @DefaultValue("0") int streetNum,
                                 @QueryParam("postalCode") @DefaultValue("0") int postalCode,
                                 @QueryParam("city") String city,
                                 @QueryParam("petrolTyp") PetrolTyp petrolTyp,
                                 @QueryParam("petrolVolume") double petrolVolume,
                                 @QueryParam("petrolUsageCar")double petrolUsageCar) {
        System.out.printf("spritCalcAPI called");
        ApiRequests spritCalcService = new ApiRequests();
        List<ApiRequestData> apiResult;
        try {
//            LOG.info("Request params:" + request.params().entries().size());
//            request.params().entries().stream().forEach(e->LOG.info(e.getKey()+" "+e.getValue()));
            apiResult = spritCalcService.apiSearchStart(street, streetNum, postalCode, city, petrolTyp, petrolVolume, petrolUsageCar);

        } catch (IOException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).entity(null).build();
        }

        if (apiResult==null || apiResult.isEmpty() || !(apiResult.get(0).getMessage()==null)){
            return Response.status(Response.Status.EXPECTATION_FAILED).entity(apiResult).build();
        }
        return Response.ok(apiResult, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/getHTMLtest/{path}")
    public InputStream getFile(@PathParam("path") String path) {
        try {
            File f = new File("META-INF/resources/SpritCalc.html");
            return new FileInputStream(f);
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            return null;
        }
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/getHTML")
    public String getHTMLResponse(@QueryParam("city") String city,  @QueryParam("PLZ") @DefaultValue("0") Integer postalCode,
                                  @QueryParam("streetNum") @DefaultValue("0") Integer streetNum, @DefaultValue("") @QueryParam("street") String street,
                                  @QueryParam("inputSpritTyp") String petrolTypeString, @QueryParam("Verbrauch") Double petrolUsageCar,
                                  @QueryParam("Tankmenge") Double petrolVolume){
        System.out.println("getHTMLResponse");
        ApiRequests spritCalcService = new ApiRequests();
        List<ApiRequestData> apiResult;
        PetrolTyp petrolType = PetrolTyp.valueOf(petrolTypeString);

        try {
//            LOG.info("Request params:" + request.params().entries().size());
//            request.params().entries().stream().forEach(e->LOG.info(e.getKey()+" "+e.getValue()));
            apiResult = spritCalcService.apiSearchStart(street, streetNum, postalCode, city, petrolType, petrolVolume, petrolUsageCar);

        } catch (IOException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).entity(null).build().toString();
        }

        if (apiResult==null || apiResult.isEmpty() || !(apiResult.get(0).getMessage()==null)){
            return Response.status(Response.Status.EXPECTATION_FAILED).entity(apiResult).build().toString();
        }

        return SpritCalcHTMLResponse.htmlResponse(apiResult);
    }


}
