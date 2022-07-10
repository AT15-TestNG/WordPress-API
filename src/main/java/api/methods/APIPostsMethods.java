package api.methods;

import api.APIManager;
import framework.CredentialsManager;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import utils.LoggerManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class APIPostsMethods {
    private static final APIManager apiManager = APIManager.getInstance();
    private static final CredentialsManager credentialsManager = CredentialsManager.getInstance();
    private static final LoggerManager log = LoggerManager.getInstance();

    public static boolean deletePostById(String postId) {
        String userRole = "administrator";
        Header header = APIAuthorizationMethods.getAuthHeader(userRole);
        Headers authHeaders = new Headers(header);

        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("force", true);

        String postsByIdEndpoint = credentialsManager.getPostsByIdEndpoint().replace("<id>", postId);

        Response response = apiManager.delete(postsByIdEndpoint, jsonAsMap, authHeaders);

        if (Objects.nonNull(response.jsonPath().getString("deleted"))) {
            return response.jsonPath().get("deleted");
        } else {
            log.error("Failed to delete Post with id: " + postId);
            return false;
        }
    }

    public static Response createAPost(String content, String title, String excerpt) {
        String userRole = "administrator";
        Header header = APIAuthorizationMethods.getAuthHeader(userRole);
        Headers authHeaders = new Headers(header);

        String postsEndpoint = credentialsManager.getPostsEndpoint();

        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("content", content);
        jsonAsMap.put("title", title);
        jsonAsMap.put("excerpt", excerpt);

        Response response = apiManager.post(postsEndpoint, jsonAsMap, authHeaders);

        if (response.jsonPath().getString("id") == null) {
            log.error("Post was not created");
            return null;
        } else {
            return response;
        }
    }
}
