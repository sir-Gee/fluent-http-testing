package util;

import java.util.HashMap;
import java.util.Map;

public class StatusCodes {

    private static Map <String, String> statusCodes = new HashMap<>();

    private static String getStatusCode(String statusCode){

//        2xx
        statusCodes.put("200", "Received code: 200 ('OK')\n");
        statusCodes.put("201", "Received code: 201 ('Created')\n");
        statusCodes.put("204", "Received code: 204 ('No Content')\n");
//        3xx
        statusCodes.put("304", "Received code: 304 ('Not Modified')\n");
//        4xx
        statusCodes.put("400", "Received code: 400 ('Bad Request')\n");
        statusCodes.put("401", "Received code: 401 ('Not Authorized')\n");
        statusCodes.put("402", "Received code: 402 ('Payment Required')\n");
        statusCodes.put("403", "Received code: 403 ('Forbidden')\n");
        statusCodes.put("404", "Received code: 404 ('Not Found')\n");
        statusCodes.put("405", "Received code: 405 ('Method Not Allowed')\n");
        statusCodes.put("408", "Received code: 408 ('Request Timeout')\n");
//        5xx
        statusCodes.put("500", "Received code: 500 ('Internal Server Error')\n");
        statusCodes.put("501", "Received code: 501 ('Not Implemented')\n");
        statusCodes.put("502", "Received code: 502 ('Bad Gateway')\n");

        return statusCodes.getOrDefault(statusCode, "Code Not Found");
    }

    public static String getStatusCode(int statusCode){
       return getStatusCode(String.valueOf(statusCode));
    }

}
