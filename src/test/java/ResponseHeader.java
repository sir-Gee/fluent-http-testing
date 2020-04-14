import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import util.ResponseUtils;

import java.io.IOException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ResponseHeader extends BaseClass {


    CloseableHttpClient client;
    CloseableHttpResponse response;

    @BeforeMethod
    public void setup(){
        client = buildClient();
    }

    @AfterMethod
    public void tearDown() throws IOException {
        closeConnections(client, response);
    }

    @Test
    public void contentTypeJson () throws IOException {
        HttpGet get = new HttpGet(BASE_ENDPOINT);
        response = client.execute(get);

        Header contentType = response.getEntity().getContentType();
        assertEquals(contentType.getValue(), "application/json; charset=utf-8");
    }

    @Test
    public void serverIsGitHub () throws IOException {
        HttpGet get = new HttpGet(BASE_ENDPOINT);
        response = client.execute(get);
        String headerValue = ResponseUtils.getHeader(response, "Server");
        assertEquals(headerValue.toLowerCase(), "github.com");
    }

    @Test
    public void eTagIsPresent() throws IOException {
        HttpGet get = new HttpGet(BASE_ENDPOINT);
        response = client.execute(get);

        assertTrue(ResponseUtils.headerIsPresent(response, "ETag"));

    }

}
