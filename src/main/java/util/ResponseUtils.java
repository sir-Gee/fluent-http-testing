package util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Arrays;

public class ResponseUtils {


    public static String getHeader(CloseableHttpResponse response, String headerName){


       return Arrays.stream(response.getAllHeaders())
                .filter(header -> headerName.equalsIgnoreCase(header.getName()))
                .findFirst()
                .orElseThrow(()-> new RuntimeException("No header found: " + headerName))
                .getValue();
    }

    public static boolean headerIsPresent(CloseableHttpResponse response, String headerName){
        return Arrays.stream(response.getAllHeaders())
                .anyMatch(header -> header.getName().equalsIgnoreCase(headerName));
    }

    public static <T> T unmarshall(CloseableHttpResponse response, Class<T> classToMap) throws IOException {
        String jsonBody = EntityUtils.toString(response.getEntity());

        return new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .readValue(jsonBody, classToMap);
    }

}
