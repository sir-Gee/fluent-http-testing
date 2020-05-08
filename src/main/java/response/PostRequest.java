package response;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import util.StatusCodes;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PostRequest {

    private CloseableHttpResponse response;
    private CloseableHttpClient client;
    private HttpPost post;
    private StringBuilder url;
    private Header header;
    private StringBuilder headerName;
    private StringBuilder headerValue;
    private List<NameValuePair> urlParameters = new ArrayList<>();
    private boolean isExecuted = false;



    public PostRequest addHeader(String name, String value){
        headerName = new StringBuilder(name);
        headerValue = new StringBuilder(value);

        System.out.printf("Added header: name - %s, value - %s\n", headerName, headerValue);
        return this;
    }


    public PostRequest setParameter(String key, String value) throws IOException {
        setup();
        urlParameters.add(new BasicNameValuePair(key, value));


        this.post.setEntity(new UrlEncodedFormEntity(urlParameters));

        System.out.println("Parameter added: '" + key +"' - '" + value + "'\n");

//        Test
//        System.out.println("Check entity: " + this.post.getEntity());


        return this;
    }

    public PostRequest setParameters(Map<String, String> keyValues) throws IOException {
        setup();
        keyValues.keySet().forEach(
                key -> {
                    urlParameters.add(new BasicNameValuePair(key, keyValues.get(key)));
                    System.out.println("Parameter added: '" + key +"' - '" + keyValues.get(key) + "'");
                }
        );


        this.post.setEntity(new UrlEncodedFormEntity(urlParameters));

//        Test
//        System.out.println("Check entity: " + this.post.getEntity());

        return this;
    }

    public PostRequest toThisUrl(String url){
        this.url = new StringBuilder();
        this.url.append(url);

        System.out.printf("Sending POST request to the URL: %s\n", this.url.toString());

        return this;
    }

    public PostRequest shouldReturnCode(int statusCode){
        if(isExecuted) {
            Assert.assertEquals(this.response.getStatusLine().getStatusCode(), statusCode);
            System.out.println(StatusCodes.getStatusCode(statusCode));
        }
        else{
            throw new RuntimeException("You should execute the request before getting the response back.\n");
        }
        return this;
    }

    public PostRequest setBasicAuth(String username, String password) throws AuthenticationException {
        setup();

        UsernamePasswordCredentials credentials
                = new UsernamePasswordCredentials(username, password);
        this.post.addHeader(new BasicScheme().authenticate(credentials, this.post, null));

        if(this.url.toString().isEmpty()){
            throw new RuntimeException("URL is not set!");
        }

        System.out.println("Basic auth added");

        return this;
    }

    public PostRequest getResponseStatusCode() throws IOException {
        System.out.println(StatusCodes.getStatusCode(this.response.getStatusLine().getStatusCode()));

        System.out.println(EntityUtils.toString(this.response.getEntity()));

        return this;
    }

    private void setup() {
        this.client = HttpClientBuilder.create().build();
        this.post = new HttpPost(this.url.toString());
    }

    public PostRequest execute() throws IOException {
        this.response = this.client.execute(this.post);
        isExecuted = true;

        return this;
    }


}
