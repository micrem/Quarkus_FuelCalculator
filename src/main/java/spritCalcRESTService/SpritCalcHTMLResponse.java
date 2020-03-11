package spritCalcRESTService;

import apis.ApiRequestData;

import java.util.List;

public class SpritCalcHTMLResponse {
    static public String htmlResponse(List<ApiRequestData> apiResult) {
        StringBuilder builder = new StringBuilder();
        String header = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "\n" +
                "    <title>SpritCalcApp</title>\n" +
                "\n" +
                "    <meta name=\"description\" content=\"Source code generated using layoutit.com\">\n" +
                "    <meta name=\"author\" content=\"DHBW WebServices SpritCalc Gruppe\">\n" +
                "\n" +
                "    <link href=\"css/bootstrap.min.css\" rel=\"stylesheet\">\n" +
                "    <link href=\"css/style.css\" rel=\"stylesheet\">\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "\n" +
                "<div style=\"width: 400px; \" class=\"container-fluid text-center\" id=\"container\">\n" +
                "    <div class=\"row text-center\">\n" +
                "        <div class=\"col\">\n" +
                "            <img id=\"logo\" name=\"logo\" class=\"img-fluid p-3\" style=\"opacity:0\" src=\"img/logo.png\"/>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "\n" +
                "    <div class=\"row text-center\">\n" +
                "        <hr/>\n" +
                "        <h3 class=\"\">\n" +
                "            SpritCalcApp\n" +
                "        </h3>\n" +
                "        <hr/>\n" +
                "    </div>\n";
        String cards = "";
        for (int i = 0; i < apiResult.size(); i++) {
            ApiRequestData e = apiResult.get(0);
            cards =
                    "    <div id=\"card1\" class=\"row-fluid my-3\">\n" +
                            "        <div class=\"card text-center\">\n" +
                            "            <h5 name=\"header\" class=\"card-header\">" + i + "</h5>\n" +
                            "            <div class=\"card-body\">\n" +
                            "                <h2 name=\"title\" class=\"card-title\">" + e.getPetrolStationDat().getName() + " </h2>\n" +
                            "                <ul name=\"list\" class=\"list-group list-group-flush \">\n" +
                            "                    <li class=\"list-group-item\">Gesamtpreis " + e.getCompletePrice() + "</li>\n" +
                            "                    <li class=\"list-group-item\">Fahrtkosten " + e.getTravelcost() + "</li>\n" +
                            "                    <li class=\"list-group-item\">Entfernung " + e.getRouteData().getDistance() + "</li>\n" +
                            "                    <li class=\"list-group-item\">Adresse:" + e.getPetrolStationDat().getPlaceNamer() + " " + e.getPetrolStationDat().getStreet() + e.getPetrolStationDat().getHouseNumber() + "</li>\n" +
                            "                </ul>\n" +
                            "            </div>\n" +
                            "        </div>\n" +
                            "    </div>\n" +
                            "\n";
        }
        String end =
                "</div>\n" +
                        "\n" +
                        "</div>\n" +
                        "</div>\n" +
                        "\n" +
                        "<script src=\"js/jquery.min.js\"></script>\n" +
                        "<script src=\"js/bootstrap.min.js\"></script>\n" +
                        "<script src=\"js/scripts.js\"></script>\n" +
                        "\n" +
                        "<script>\n" +
                        "$(\"#logo\").animate({ opacity: '1' },\"fast\",\n" +
                        "\tfunction(){\n" +
                        "\t\t$(\"#results\").animate({ opacity: '1'},\"fast\");\n" +
                        "\t\t$(\"#logo\").animate({ width: '150px'},\"fast\");\n" +
                        "\t\t}\n" +
                        ");\n" +
                        "\n" +
                        "\n" +
                        "$(\"#card1 [name='title']\").html(\"TEST2\");\n" +
                        "\n" +
                        "const userAction = async () => {\n" +
                        "  const response = await fetch('http://example.com/movies.json', {\n" +
                        "    method: 'POST',\n" +
                        "    body: myBody, // string or object\n" +
                        "    headers: {\n" +
                        "      'Content-Type': 'application/json'\n" +
                        "    }\n" +
                        "  });\n" +
                        "  const myJson = await response.json(); //extract JSON from the http response\n" +
                        "  alert(myJson);\n" +
                        "\n" +
                        "  userAction();\n" +
                        "\n" +
                        "}\n" +
                        "</script>\n" +
                        "\n" +
                        "</body>\n" +
                        "</html>";
        return header + cards + end;
    }
}
