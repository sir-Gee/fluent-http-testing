package response;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static response.BaseRequest.buildClient;
import static response.BaseRequest.checkGeneric;
import static response.BaseRequest.toThisUrlGen;

public class GetRequest {
    private CloseableHttpResponse response;
    private CloseableHttpClient client;
    private HttpGet get;
    private StringBuilder url;
    private StringBuilder jsonBody;
    private StringBuilder headerName;
    private StringBuilder headerValue;

    public GetRequest toThisUrl(String url){
        this.url = new StringBuilder();
        this.url.append(url);
        System.out.printf("Sending GET request to the URL: %s\n", this.url.toString());
        return this;
    }

    public GetRequest addHeader(String name, String value){
        headerName = new StringBuilder(name);
        headerValue = new StringBuilder(value);

//        toThisUrlGen(new GetRequest(), url);
        System.out.printf("Added header: name - %s, value - %s\n", headerName.toString(), headerValue.toString());
        return this;
    }

    public GetRequest shouldReturn200() throws IOException {
        setup();

        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        System.out.println("Received code: 200 ('OK')\n");
        response.close();

        return this;
    }

    public GetRequest shouldReturn204() throws IOException {
        setup();

        Assert.assertEquals(response.getStatusLine().getStatusCode(), 204);
        System.out.println("Received code: 204 ('No Content')\n");
        response.close();

        return this;
    }

    public GetRequest shouldReturn304() throws IOException {
        setup();

        Assert.assertEquals(response.getStatusLine().getStatusCode(), 304);
        System.out.println("Received code: 304 ('Not Modified')\n");
        response.close();

        return this;
    }


    public void shouldReturn400() throws IOException {
        setup();

        Assert.assertEquals(response.getStatusLine().getStatusCode(), 400);
        System.out.println("Received code: 400 ('Bad Request')\n");
        response.close();
    }

    public GetRequest shouldReturn401() throws IOException {
        setup();

        Assert.assertEquals(response.getStatusLine().getStatusCode(), 401);
        System.out.println("Received code: 401 ('Not Authorized')\n");
        response.close();

        return this;
    }

    public GetRequest shouldReturn402() throws IOException {
        setup();

        Assert.assertEquals(response.getStatusLine().getStatusCode(), 402);
        System.out.println("Received code: 402 ('Payment Required')\n");
        response.close();

        return this;
    }

    public void shouldReturn403() throws IOException {
        setup();

        Assert.assertEquals(response.getStatusLine().getStatusCode(), 403);
        System.out.println("Received code: 403 ('Forbidden')\n");
        response.close();
    }


    public void shouldReturn404() throws IOException {
        setup();

        Assert.assertEquals(response.getStatusLine().getStatusCode(), 404);
        System.out.println("Received code: 404 ('Not Found')\n");
        response.close();
    }

    public void shouldReturn405() throws IOException {
        setup();

        Assert.assertEquals(response.getStatusLine().getStatusCode(), 405);
        System.out.println("Received code: 405 ('Method Not Allowed')\n");
        response.close();
    }

    public void shouldReturn408() throws IOException {
        setup();

        Assert.assertEquals(response.getStatusLine().getStatusCode(), 408);
        System.out.println("Received code: 408 ('Request Timeout')\n");
        response.close();
    }

    public void shouldReturn500() throws IOException {
        setup();

        Assert.assertEquals(response.getStatusLine().getStatusCode(), 500);
        System.out.println("Received code: 500 ('Internal Server Error')\n");
        response.close();
    }

    public void shouldReturn501() throws IOException {
        setup();

        Assert.assertEquals(response.getStatusLine().getStatusCode(), 501);
        System.out.println("Received code: 501 ('Not Implemented')\n");
        response.close();
    }

    public void shouldReturn502() throws IOException {
        setup();

        Assert.assertEquals(response.getStatusLine().getStatusCode(), 502);
        System.out.println("Received code: 502 ('Bad Gateway')\n");
        response.close();
    }

    public GetRequest shouldHaveHeader(String headerName) throws IOException {
        setup();

        String actualHeader = Arrays.stream(response.getAllHeaders())
                .filter(header -> headerName.equalsIgnoreCase(header.getName()))
                .findFirst()
                .orElseThrow(()-> new RuntimeException("No header found: " + headerName))
                .getName();

        Assert.assertEquals(actualHeader.toLowerCase(), headerName.toLowerCase());
        System.out.printf("Header found: %s\n", headerName);
        response.close();

        return this;
    }


    public GetRequest shouldHaveHeaders(String ...headersNames) throws IOException {
        setup();

        List findHeadersLowerCase = Arrays.stream(headersNames)
                .map(String::toLowerCase)
                .collect(Collectors.toList());

        StringBuilder builder = new StringBuilder("Headers found:\n");

        Arrays.stream(this.response.getAllHeaders())
                 .forEach(header -> {
                     if(findHeadersLowerCase.contains(header.getName().toLowerCase())) {
                         builder.append(header.getName() + "\n");
                     }
                 });

        System.out.println(builder.toString());
        response.close();

        return this;
    }


    public void returnJsonBody() throws IOException {
        jsonBody = new StringBuilder();
        client = buildClient();
        get = new HttpGet(this.url.toString());
        response = client.execute(get);

        System.out.println(EntityUtils.toString(this.response.getEntity()).replace("[","").replace("]",""));

//        System.out.println(EntityUtils.toString(this.response.getEntity()));
    }

    public GetRequest getJson() throws IOException {
        jsonBody = new StringBuilder();
        client = buildClient();
        get = new HttpGet(this.url.toString());
        this.response = client.execute(get);
        this.jsonBody.append(EntityUtils.toString(this.response.getEntity())
                .replace("[","")
                .replace("]",""));

        return this;
    }

    public CloseableHttpResponse returnJson(){
        return this.response;
    }

//    public <T> T bindJsonToClass() throws IOException {
//        return new ObjectMapper().readValue(EntityUtils.toString(this.response.getEntity()), new TypeReference<T>() {
//        });
//    }
//
//    public Object getValueFor(String key){
//        JSONObject jsonObject = new JSONObject(this.jsonBody.toString());
////        System.out.println(jsonObject.keySet());
//        return jsonObject.get(key);
//
//    }

    public <T> T unmarshall(Class<T> classToMap) throws IOException {
        return new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .readValue(this.jsonBody.toString(), classToMap);
    }

//    public <T> List<T> boom() throws IOException {
//        return new ObjectMapper()
//                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
//                .readValue(EntityUtils.toString(response.getEntity()), new TypeReference<List<T>>() {});
//    }

    private void setup() throws IOException {
        this.client = HttpClientBuilder.create().build();
        this.get = new HttpGet(this.url.toString());
        if(this.headerName != null){
            this.get.addHeader(this.headerName.toString(), this.headerValue.toString());
        }
        this.response = this.client.execute(this.get);
    }

}
