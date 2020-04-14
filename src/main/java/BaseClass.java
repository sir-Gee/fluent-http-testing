import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

public class BaseClass {


    protected static final String BASE_ENDPOINT = "https://api.github.com";
    protected static final String JSON_TEST = "https://jsonplaceholder.typicode.com/posts";
    protected static final String OCEANIA_BASE_ENDPOINT = "https://www.oceaniacruises.com";
    protected static final String OCEANIA_GET_CRUISES_ENDPOINT = "https://www.oceaniacruises.com/api/cruisefinder/getcruises";
    CloseableHttpResponse response;
    ApiCommonRequests apiCommonRequests = ApiCommonRequests.getGetPostResponses();


    public CloseableHttpClient buildClient() {
        return HttpClientBuilder.create().build();
    }

    public void closeConnections(CloseableHttpClient client, CloseableHttpResponse response) throws IOException {
        client.close();
        response.close();
    }

    public static HttpGet getGet(String endpoint){
        return new HttpGet(BASE_ENDPOINT + endpoint);
    }

    public static HttpPost getPost(String endpoint){
        return new HttpPost(endpoint);
    }

}
