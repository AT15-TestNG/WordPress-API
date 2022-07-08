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

    public static Response createAUser(String name, String email, String password, String description) {
        String userRole = "administrator";
        Header header = APIAuthorizationMethods.getAuthHeader(userRole);
        Headers authHeaders = new Headers(header);

        String usersEndpoint = credentialsManager.getUsersEndpoint();

        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("username", name);
        jsonAsMap.put("email", email);
        jsonAsMap.put("password", password);
        jsonAsMap.put("description", description);

        Response response = apiManager.post(usersEndpoint, jsonAsMap, authHeaders);

        if (response.jsonPath().getString("id") == null) {
            return null;
        } else {
            return response;
        }
    }

    public static String deleteUserById(String userId) {
        String userRole = "administrator";
        Header header = APIAuthorizationMethods.getAuthHeader(userRole);
        Headers authHeaders = new Headers(header);
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("force", true);
        jsonAsMap.put("reassign", 1);

        String usersByIdEndpoint = credentialsManager.getUsersByIdEndpoint().replace("<id>", userId);
        Response response = apiManager.delete(usersByIdEndpoint, jsonAsMap, authHeaders);

        if (response.getBody().jsonPath().getString("deleted").equals("true")) {
            return response.jsonPath().getString("deleted");
        } else {
            return null;
        }
    }
}
