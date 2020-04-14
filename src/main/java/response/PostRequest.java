package response;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;

public class PostRequest {

    private CloseableHttpResponse response;
    private CloseableHttpClient client;
    private StringBuilder url = new StringBuilder();
    private Header header;


    public PostRequest toThisUrl(String url){
        this.url.append(url);
        return this;
    }

    public PostRequest shouldReturnHeaders(String header, String value){
        response.setHeader(header, value);
        return this;
    }

}
