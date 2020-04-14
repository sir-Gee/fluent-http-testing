package response;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class GetRequest {
    private CloseableHttpResponse response;
    private CloseableHttpClient client;
    private StringBuilder url = new StringBuilder();
    private StringBuilder jsonBody;

    public GetRequest shouldReturn200(String url) throws IOException {
        this.url.append(url);
        client = buildClient();
        HttpGet get = new HttpGet(url);
        CloseableHttpResponse response = client.execute(get);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        System.out.printf("URL: %s, code: 200\n", url);
        response.close();

        return this;
    }

    public GetRequest shouldReturn200() throws IOException {
        client = buildClient();
        HttpGet get = new HttpGet(this.url.toString());
        CloseableHttpResponse response = client.execute(get);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        System.out.printf("URL: %s, code: 200\n", this.url.toString());
        response.close();

        return this;
    }


    public void shouldReturn404(String url) throws IOException {

        client = buildClient();
        HttpGet get = new HttpGet(url);
        CloseableHttpResponse response = client.execute(get);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), 404);
        System.out.printf("URL: %s, code: 404 ('Page Not Found')\n", url);
        response.close();
    }

    public void shouldReturn404() throws IOException {

        client = buildClient();
        HttpGet get = new HttpGet(this.url.toString());
        CloseableHttpResponse response = client.execute(get);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), 404);
        System.out.printf("URL: %s, code: 404 ('Page Not Found')\n", this.url.toString());
        response.close();
    }

    public GetRequest shouldHaveHeader(String headerName) throws IOException {
        client = buildClient();

        HttpGet get = new HttpGet(this.url.toString());
        response = client.execute(get);

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

    public GetRequest shouldHaveHeader(String url, String headerName) throws IOException {
        client = buildClient();

        HttpGet get = new HttpGet(url);
        response = client.execute(get);

        String actualHeader = Arrays.stream(response.getAllHeaders())
                .filter(header -> headerName.equalsIgnoreCase(header.getName()))
                .findFirst()
                .orElseThrow(()-> new RuntimeException("No header found: " + headerName))
                .getName();

        Assert.assertEquals(actualHeader.toLowerCase(), headerName.toLowerCase());
        System.out.printf("URL: %s\nHeader: %s, status: found\n", url, headerName);
        response.close();

        return this;
    }

    public GetRequest shouldHaveHeaders(String ...headersNames) throws IOException {
        client = buildClient();

        HttpGet get = new HttpGet(this.url.toString());
        response = client.execute(get);

        Arrays.stream(response.getAllHeaders())
                 .forEach(header ->
                         Arrays.asList(headersNames).contains(header));

        StringBuilder builder = new StringBuilder("These headers exist:\n");
        Arrays.stream(headersNames)
                .forEach(name -> builder.append(name + "\n"));

        System.out.println(builder.toString());
        response.close();

        return this;
    }

    public GetRequest toThisUrl(String url){
        this.url.append(url);
        return this;
    }


    private static CloseableHttpClient buildClient() {
        return HttpClientBuilder.create().build();
    }


    public void returnJsonBody() throws IOException {
        jsonBody = new StringBuilder();
        client = buildClient();

        HttpGet get = new HttpGet(this.url.toString());
        response = client.execute(get);

        System.out.println(EntityUtils.toString(this.response.getEntity()).replace("[","").replace("]",""));

//        System.out.println(EntityUtils.toString(this.response.getEntity()));
    }

    public GetRequest getJson() throws IOException {
        jsonBody = new StringBuilder();
        client = buildClient();

        HttpGet get = new HttpGet(this.url.toString());
        this.response = client.execute(get);
        this.jsonBody.append(EntityUtils.toString(this.response.getEntity())
                .replace("[","")
                .replace("]",""));

        return this;
    }

    public <T> T bindJsonToClass() throws IOException {
        return new ObjectMapper().readValue(EntityUtils.toString(this.response.getEntity()), new TypeReference<T>() {
        });
    }

    public Object getValueFor(String key){
        JSONObject jsonObject = new JSONObject(this.jsonBody.toString());
//        System.out.println(jsonObject.keySet());
        return jsonObject.get(key);

    }

    public <T> T unmarshall(Class<T> classToMap) throws IOException {
        return new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .readValue(this.jsonBody.toString(), classToMap);
    }

    public <T> List<T> boom() throws IOException {
        return new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .readValue(EntityUtils.toString(response.getEntity()), new TypeReference<List<T>>() {});
    }

}
