import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.io.IOException;

public class BodyTestWithSimpleMap extends BaseClass{

    @Test
    public void returnCorrectLogin() throws IOException {
        HttpGet get = new HttpGet(BASE_ENDPOINT + "/users/sir-Gee");
        response = buildClient().execute(get);

        String jsonBody = EntityUtils.toString(response.getEntity());
//        System.out.println(jsonBody);

        JSONObject jsonObject = new JSONObject(jsonBody);

//        System.out.println(getValueFor(jsonObject, "login"));
    }

}
