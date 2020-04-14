import response.GetResponse;
import response.PostResponse;

public class ApiCommonRequests {
    private GetResponse getResponse;
    private PostResponse postResponse;

    public GetResponse getRequest(){
        return getResponse;
    }

    public PostResponse postRequest(){
        return postResponse;
    }

    private ApiCommonRequests(GetResponse getResponse, PostResponse postResponse) {
        this.getResponse = getResponse;
        this.postResponse = postResponse;
    }



    public static ApiCommonRequests getGetPostResponses(){
        return new ApiCommonRequests(new GetResponse(), new PostResponse());
    }
}
