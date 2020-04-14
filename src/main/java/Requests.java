import response.GetRequest;
import response.PostRequest;

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
