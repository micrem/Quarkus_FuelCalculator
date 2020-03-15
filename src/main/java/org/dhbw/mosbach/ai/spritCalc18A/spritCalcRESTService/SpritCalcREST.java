package org.dhbw.mosbach.ai.spritCalc18A.spritCalcRESTService;

import org.dhbw.mosbach.ai.spritCalc18A.SpritCalcBL;
import org.dhbw.mosbach.ai.spritCalc18A.SpritCalcBLData;
import org.dhbw.mosbach.ai.spritCalc18A.externalAPIs.tankerKoenig.PetrolTyp;
import io.vertx.core.http.HttpServerRequest;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.jboss.logging.Logger;

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
                                 @QueryParam("petrolUsageCar") double petrolUsageCar) {
        System.out.printf("spritCalcAPI called");
        SpritCalcBL spritCalcService = new SpritCalcBL();
        List<SpritCalcBLData> apiResult;
        try {
            LOG.info("Request params:" + request.params().entries().size());
            request.params().entries().stream().forEach(e->LOG.info(e.getKey()+" "+e.getValue()));
            apiResult = spritCalcService.apiSearchStart(street, streetNum, postalCode, city, petrolTyp, petrolVolume, petrolUsageCar);

        } catch (IOException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).entity(null).build();
        }

        if (apiResult == null || apiResult.isEmpty() || !(apiResult.get(0).getMessage() == null)) {
            return Response.status(Response.Status.EXPECTATION_FAILED).entity(apiResult).build();
        }
        return Response.ok(apiResult, MediaType.APPLICATION_JSON).build();
    }

    @Path("testData")
    @GET
    //@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Operation(summary = "SpritCalcAPI GET Path",
            description = "SpritCalcAPI Service")
    public Response spritCalcAPI() {
        System.out.printf("spritCalcAPI TestData called");
        SpritCalcBL spritCalcService = new SpritCalcBL();
        List<SpritCalcBLData> apiResult = spritCalcService.getFallBackData("API Test Betrieb");
        return Response.ok(apiResult, MediaType.APPLICATION_JSON).build();
    }


}
