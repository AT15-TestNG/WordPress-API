package api.methods;

import api.APIManager;
import constants.DomainAppEnums;
import framework.CredentialsManager;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import utils.LoggerManager;

import java.util.HashMap;
import java.util.Map;

public class APIPostsMethods {
    public static final LoggerManager log = LoggerManager.getInstance();
    public static final APIManager api = APIManager.getInstance();
    public static final CredentialsManager auth = CredentialsManager.getInstance();

    public static String deletePostById(String postId) {
        String userRole = DomainAppEnums.UserRole.ADMINISTRATOR.getUserRole();
        Header authHeader = APIAuthorizationMethods.getAuthHeader(userRole);
        Headers headers = new Headers(authHeader);

        String postsByIdEndpoint = auth.getPostsByIdEndpoint().replace("<id>", postId);
        Response response = api.delete(postsByIdEndpoint, headers);

        if (response.jsonPath().getString("status").equals("trash")) {
            return response.jsonPath().getString("status");
        } else {
            log.error("Error while deleting post");
            return null;
        }
    }

    public static Response createAPost(String content, String title, String excerpt) {
        String userRole = DomainAppEnums.UserRole.ADMINISTRATOR.getUserRole();
        Header header = APIAuthorizationMethods.getAuthHeader(userRole);
        Header authHeader = APIAuthorizationMethods.getAuthHeader(userRole);
        Headers headers = new Headers(authHeader);

        String postsEndpoint = auth.getPostsEndpoint();

        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("content", content);
        jsonAsMap.put("title", title);
        jsonAsMap.put("excerpt", excerpt);

        Response response = api.post(postsEndpoint, jsonAsMap, headers);

        return (response.jsonPath().getString("id")) == null ? null : response;
    }
}