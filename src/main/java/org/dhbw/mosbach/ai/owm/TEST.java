package org.dhbw.mosbach.ai.owm;

import org.json.JSONArray;
import org.json.JSONObject;
import apis.petrolApi.PetrolStationApi;
import apis.petrolApi.PetrolTyp;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/test")
public class TEST {


    @POST
    @Produces(MediaType.TEXT_HTML)
    public String getAPI(@FormParam("searchLongInput") Double searchLongInput, @FormParam("searchLatInput") Double searchLatInput)
    {
        StringBuilder strBuilderJS = new StringBuilder();
        PetrolStationApi petrolStationApi = new PetrolStationApi(searchLatInput,searchLongInput, PetrolTyp.e10);
        petrolStationApi.search();
        strBuilderJS.append("<script>" + "var x = document.getElementById('responseDiv');\n");
        for (int i = 0; i < petrolStationApi.returnObj.getJSONArray("stations").length(); i++) {
            JSONObject element = petrolStationApi.returnObj.getJSONArray("stations").getJSONObject(i);
            strBuilderJS.append(printStationStringValue(element, "place"));
            strBuilderJS.append(printStationStringValue(element, "name"));
            //strBuilderJS.append(printStationStringValue(element, "price")); //price not a string, generic method won't work
            strBuilderJS.append("var linebreak = document.createElement('br');\ncreateResponse.appendChild(linebreak);\n");
        }

        strBuilderJS.append(
                "\n" +
                "var heading = document.createElement('h2'); // Heading of Response\n" +
                "heading.innerHTML = 'Response';\n" +
                "createResponse.appendChild(heading);\n" +
                "\n" +
                "var line = document.createElement('hr'); // Giving Horizontal Row After Heading\n" +
                "createResponse.appendChild(line);\n" +
                "\n" +
                "var linebreak = document.createElement('br');\n" +
                "createResponse.appendChild(linebreak);\n"+
                "document.getElementById(\"clicker\").addEventListener(\"click\", myFunction);\n" +
                "\n" +
                "function myFunction() {\n" +
                "  document.getElementById(\"clicker\").innerHTML = \"YOU CLICKED ME!\";\n" +
                "}\n" +
                "</script>\n");

        String returnStr = "\n" +
                "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<title> Response </title> <!-- Include CSS file here -->\n" +
                //"<link href='css/form.css' rel='stylesheet'>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div id='main'>\n" +
                "<h1>Response with JS</h1>\n" +
                "<div id='responseDiv'></div>\n" +

                strBuilderJS.toString() +

         //       "<script src='js/form.js'></script>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";
        return returnStr;
    }

    private String printStationStringValue(JSONObject element, String var) {
        StringBuilder stationValue = new StringBuilder();
        stationValue.append("var createResponse = document.createElement('div');");
        stationValue.append("createResponse.innerHTML = '").append(var).append(": ").append(element.getString(var)).append("';\n");
        stationValue.append("x.appendChild(createResponse);\n");
        stationValue.append("var linebreak = document.createElement('br');\ncreateResponse.appendChild(linebreak);\n");
        return stationValue.toString();
    }


    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getInput()
    {
        //<input type=number step=any />
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "<h2>Eingabemaske</h2>\n" +
                " <form action=\"/test\" method=\"post\"> \n" +
                "  <label for=\"searchStringInput\">long:</label><br>\n" +
                "  <input type=number step=any id=\"searchLongInput\" name=\"searchLongInput\" value=\"9.120130\"><br>  \n" +
                "  <label for=\"searchStringInput\">lat:</label><br>\n" +
                "  <input type=number step=any id=\"searchLatInput\" name=\"searchLatInput\" value=\"49.337470\"><br>  \n" +
                "<input type=\"submit\" value=\"Submit\">" +
                "</form>\n" +
                "</body>\n" +
                "</html>\n";
    }

    public static void main(String[] args) {

        JSONArray jArr = new JSONArray(); //neues Array
        jArr.put(new JSONObject()); // neues leeres JSON-Object auf Index =0
        System.out.println( jArr.getJSONObject(0).isNull("value") );
        //output : "true"

    }

}


/*
<p id="demo">Click me.</p>

<script>
document.getElementById("demo").addEventListener("click", myFunction);

function myFunction() {
  document.getElementById("demo").innerHTML = "YOU CLICKED ME!";
}
</script>
 */