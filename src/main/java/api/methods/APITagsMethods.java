package api.methods;

import api.APIManager;
import framework.CredentialsManager;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class APITagsMethods {
    public static final APIManager apiManager = APIManager.getInstance();
    public static final CredentialsManager credentialsManager = CredentialsManager.getInstance();

    public static boolean deleteTagById(String tagId) {
        String userRole = "administrator";
        Header header = APIAuthorizationMethods.getAuthHeader(userRole);
        Headers authHeaders = new Headers(header);
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("force", true);

        String tagsByIdEndpoint = credentialsManager.getTagsByIdEndpoint().replace("<id>", tagId);

        Response response = apiManager.delete(tagsByIdEndpoint, jsonAsMap, authHeaders);

        if (response.jsonPath().get("deleted")) {
            return response.jsonPath().get("deleted");
        } else {
            return false;
        }
    }
}
