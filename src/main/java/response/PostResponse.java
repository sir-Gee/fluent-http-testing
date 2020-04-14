package response;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;

public class PostResponse {

    private CloseableHttpResponse response;
    private CloseableHttpClient client;
    private StringBuilder url = new StringBuilder();
    private Header header;


    public PostResponse ofThisUrl (String url){
        this.url.append(url);
        return this;
    }

    public PostResponse shouldPostAndReturnHeaders(String header, String value){
        response.setHeader(header, value);
        return this;
    }

}
