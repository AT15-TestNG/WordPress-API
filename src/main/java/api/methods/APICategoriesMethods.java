package api.methods;

import api.APIManager;
import framework.CredentialsManager;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class APICategoriesMethods {
    public static final APIManager apiManager = APIManager.getInstance();
    public static final CredentialsManager credentialsManager = CredentialsManager.getInstance();

    public static boolean deleteCategoryById(String categoryId) {
        String userRole = "administrator";
        Header header = APIAuthorizationMethods.getAuthHeader(userRole);
        Headers authHeaders = new Headers(header);
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("force", true);

        String categoryByIdEndpoint = credentialsManager.getCategoriesByIdEndpoint().replace("<id>", categoryId);

        Response response = apiManager.delete(categoryByIdEndpoint, jsonAsMap, authHeaders);

        if (response.jsonPath().get("deleted")) {
            return response.jsonPath().get("deleted");
        } else {
            return false;
        }
    }

    public static Response createACategory(String name) {
        String userRole = "administrator";
        Header header = APIAuthorizationMethods.getAuthHeader(userRole);
        Headers authHeaders = new Headers(header);

        String categoryEndpoint = credentialsManager.getCategoriesEndpoint();

        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("name", name);

        Response response = apiManager.post(categoryEndpoint, jsonAsMap, authHeaders);

        if (response.jsonPath().getString("id") == null) {
            return null;
        } else {
            return response;
        }
    }
}
