import org.apache.http.client.methods.HttpGet;
import org.testng.annotations.Test;
import util.ResponseUtils;

import java.io.IOException;

import static org.testng.Assert.assertEquals;

public class BodyTestWithJackson extends BaseClass{

    @Test
    public void returnCorrectLogin() throws IOException {
        HttpGet get = new HttpGet(BASE_ENDPOINT + "/users/sir-Gee");

        response = buildClient().execute(get);

        User user = ResponseUtils.unmarshall(response, User.class);

        assertEquals(user.getLogin(), "sir-Gee");
    }

    @Test
    public void notFoundMessageIsCorrect() throws IOException {

        response = buildClient().execute(getGet("/nonexisting"));

        NotFound notFoundMessage = ResponseUtils.unmarshall(response, NotFound.class);

        assertEquals(notFoundMessage.getMessage().toLowerCase(), "not found");
    }


}
