package api.http;

import io.restassured.response.Response;

public class HttpResponse {
    private Response response;

    public void setResponse(Response response) {
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }
}
