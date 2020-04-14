import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class Get200 extends BaseClass{

//    ApiCommonRequests apiCommonRequests = ApiCommonRequests.getGetPostResponses();
    CloseableHttpClient client;
    CloseableHttpResponse response;

    @BeforeMethod
    public void setup(){
        client = buildClient();
    }

//    @AfterMethod
//    public void tearDown() throws IOException {
//        closeConnections(client, response);
//    }

    @Test(dataProvider = "endpoints")
    public void getReturns401 (String endpoint) throws IOException {
        HttpGet get = new HttpGet(BASE_ENDPOINT + endpoint);
        response = client.execute(get);

        assertEquals(response.getStatusLine().getStatusCode(), 401);
    }

    @Test
    public void oceaniaRequest () throws IOException {
//        HttpPost post = new HttpPost(OCEANIA_GET_CRUISES_ENDPOINT);
//        HttpGet get = new HttpGet(OCEANIA_GET_CRUISES_ENDPOINT);
        response = client.execute(getPost(OCEANIA_GET_CRUISES_ENDPOINT));
        String json = EntityUtils.toString(response.getEntity());
        System.out.println(json);

//        Cruises cruises = util.ResponseUtils.unmarshall(response, Cruises.class);
//        System.out.println(cruises.getAvailable());
//        System.out.println(response.getEntity());
//        assertEquals(response.getStatusLine().getStatusCode(), 200);
    }


    @Test
    public void getReturns404 () throws IOException {
        HttpGet get = new HttpGet(BASE_ENDPOINT + "/nonexisting");
        response = client.execute(get);

        assertEquals(response.getStatusLine().getStatusCode(), 404);
    }


    @DataProvider
    public Object [][] endpoints(){
        return new Object[][]{
          {"/user"},
          {"/user/followers"}
        };
    }


    @Test
    public void test200() throws IOException {
        apiCommonRequests.getRequest()
                .ofThisUrl("http://api.github.co")
                .shouldReturn200();
    }

    @Test
    public void test400() throws IOException {
        apiCommonRequests.getRequest()
                .ofThisUrl("http://api.github.com/user/ggdr3r")
                .shouldReturn404();
    }

    @Test
    public void test200andHeader() throws IOException {
        apiCommonRequests.getRequest()
                .ofThisUrl("http://api.github.com")
                .shouldHaveHeader("Server");
    }

    @Test
    public void testBuilder() throws IOException {
        apiCommonRequests.getRequest()
                .shouldReturn200("http://api.github.com")
                .shouldHaveHeader("Server")
        .shouldHaveHeaders("server", "server");
    }

    @Test
    public void testBuilder2() throws IOException {
        apiCommonRequests.getRequest()
                .ofThisUrl("http://api.github.com")
                .shouldReturn200()
                .shouldHaveHeader("server");
    }

    @Test
    public void checkJsonValueForKey() throws IOException {
        apiCommonRequests.getRequest()
                .ofThisUrl("https://jsonplaceholder.typicode.com/posts")
                .getJson();

        List<Posts> posts = apiCommonRequests.getRequest()
                .boom();

        System.out.println(posts.size());

    }

    @Test
    public void printJson() throws IOException {
        apiCommonRequests.getRequest()
                .ofThisUrl("https://jsonplaceholder.typicode.com/posts")
                .returnJsonBody();
    }


    @Test
    public void testPost(){
        apiCommonRequests.postRequest()
                .ofThisUrl("")
                .shouldPostAndReturnHeaders("","");
    }

//    @Test
//    public void testJson() throws IOException {
//        String json = apiCommonRequests.getRequest()
//                .ofThisUrl("https://jsonplaceholder.typicode.com/posts")
//                .returnJsonBody();
//
//        List<Posts> list = apiCommonRequests.getRequest()
//                .bindJsonToClass();
//
//        list.stream()
//                .filter(x -> x.id == 2)
//                .forEach(System.out::println);
//
//
//        System.out.println(json);
//    }




}
