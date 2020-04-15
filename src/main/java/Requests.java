import response.GetRequest;
import response.PostRequest;

/**
 * Copy to your pom.xml:
 *         <dependency>
 *             <groupId>org.apache.httpcomponents</groupId>
 *             <artifactId>httpclient</artifactId>
 *             <version>4.5.2</version>
 *         </dependency>
*/

public class Requests {
    private GetRequest getRequest;
    private PostRequest postRequest;

    public GetRequest sendGet(){
        return getRequest;
    }

    public PostRequest sendPost(){
        return postRequest;
    }

    private Requests(GetRequest getRequest, PostRequest postRequest) {
        this.getRequest = getRequest;
        this.postRequest = postRequest;
    }

    public static Requests getGetPostRequests(){
        return new Requests(new GetRequest(), new PostRequest());
    }
}
