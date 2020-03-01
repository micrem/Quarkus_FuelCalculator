package org.dhbw.mosbach.ai.owm;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/test")
public class TEST {


    @POST
    @Produces(MediaType.TEXT_HTML)
    public String getAPI(@FormParam("searchStringInput") String searchStringInput)
    {
        String js = "<script>" +
                "var x = document.getElementById('responseDiv');\n" +
                "var createResponse = document.createElement('div'); // Create New Element Div\n" +
                "createResponse.innerHTML = '" + searchStringInput + " CLICK ME!';\n" +
                "createResponse.setAttribute('id', 'clicker'); \n" +
                "x.appendChild(createResponse);\n" +
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
                "</script>\n";

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

                js +

         //       "<script src='js/form.js'></script>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";
        return returnStr;
    }




    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getInput()
    {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "<h2>Eingabemaske</h2>\n" +
                " <form action=\"/test\" method=\"post\"> \n" +
                "  <label for=\"searchStringInput\">Ort:</label><br>\n" +
                "  <input type=\"text\" id=\"searchStringInput\" name=\"searchStringInput\" value=\"Mosbach\"><br>  \n" +
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