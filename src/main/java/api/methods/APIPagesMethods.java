package api.methods;

import api.APIManager;
import framework.CredentialsManager;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import utils.LoggerManager;

import java.util.HashMap;
import java.util.Map;

public class APIPagesMethods {
    public static final APIManager apiManager = APIManager.getInstance();
    public static final CredentialsManager credentialsManager = CredentialsManager.getInstance();
    public static final LoggerManager log = LoggerManager.getInstance();

    public static String deletePageById(String pageId) {
        String userRole = "administrator";
        Header header = APIAuthorizationMethods.getAuthHeader(userRole);
        Headers authHeaders = new Headers(header);

        String pagesByIdEndpoint = credentialsManager.getPageByIdEndpoint().replace("<id>", pageId);

        Response response = apiManager.delete(pagesByIdEndpoint, authHeaders);

        if (response.jsonPath().getString("status").equals("trash")) {
            return response.jsonPath().getString("status");
        } else {
            log.error("page was not deleted");
            return null;
        }
    }

    public static Response createAPage(String content, String title, String excerpt) {
        String userRole = "administrator";
        Header header = APIAuthorizationMethods.getAuthHeader(userRole);
        Headers authHeaders = new Headers(header);

        String pageEndpoint = credentialsManager.getPagesEndpoint();

        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("content", content);
        jsonAsMap.put("title", title);
        jsonAsMap.put("excerpt", excerpt);

        Response response = apiManager.post(pageEndpoint, jsonAsMap, authHeaders);

        if (response.jsonPath().getString("id") == null) {
            log.error("page was not created");
            return null;
        } else {
            return response;
        }
    }
}
