package api.methods;

import api.APIManager;
import constants.DomainAppEnums;
import framework.CredentialsManager;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import utils.LoggerManager;
import utils.StringManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class APICommentsMethods {
    public static final LoggerManager log = LoggerManager.getInstance();
    public static final APIManager apiManager = APIManager.getInstance();
    public static final CredentialsManager credentialsManager = CredentialsManager.getInstance();

    public static boolean deleteCommentById(String commentId) {
        String userRole = DomainAppEnums.UserRole.ADMINISTRATOR.getUserRole();
        Header header = APIAuthorizationMethods.getAuthHeader(userRole);
        Headers authHeaders = new Headers(header);

        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("force", true);

        String commentsEndpoint = credentialsManager.getCommentsByIdEndpoint().replace("<id>", commentId);

        Response response = apiManager.delete(commentsEndpoint,jsonAsMap, authHeaders);

        if (Objects.nonNull(response.jsonPath().getString("deleted"))) {
            return response.jsonPath().get("deleted");
        } else {
            log.error("Failed to delete comment");
            return false;
        }
    }

    public static Response createAComment() {
        String userRole = DomainAppEnums.UserRole.ADMINISTRATOR.getUserRole();
        Header header = APIAuthorizationMethods.getAuthHeader(userRole);
        Headers authHeaders = new Headers(header);

        String content = "Content TestNG Example" + StringManager.generateStringDate();
        String author_name = "TestNG" + StringManager.generateStringDate();
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("post", 1);
        jsonAsMap.put("author_name", author_name);
        jsonAsMap.put("author_email", "wapuu@wordpress.example");
        jsonAsMap.put("content", content);

        Response response = apiManager.post(credentialsManager.getCommentsEndpoint(), jsonAsMap, authHeaders);

        if (response.jsonPath().getString("id") == null) {
            log.error("Failed to create comment");
            return null;
        } else {
            return response;
        }
    }
}
