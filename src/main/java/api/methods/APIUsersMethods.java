package api.methods;

import api.APIManager;
import framework.CredentialsManager;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class APIUsersMethods {
    public static final APIManager apiManager = APIManager.getInstance();
    public static final CredentialsManager credentialsManager = CredentialsManager.getInstance();

    public static String deleteUserById(String userId) {
        String userRole = "administrator";
        Header header = APIAuthorizationMethods.getAuthHeader(userRole);
        Headers authHeaders = new Headers(header);
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("force", true);
        jsonAsMap.put("reassign", 1);

        String postsByIdEndpoint = credentialsManager.getUsersByIdEndpoint().replace("<id>", userId);
        Response response = apiManager.delete(postsByIdEndpoint, jsonAsMap, authHeaders);

        if (response.getBody().jsonPath().getString("deleted").equals("true")) {
            return response.jsonPath().getString("deleted");
        } else {
            return null;
        }
    }
}
