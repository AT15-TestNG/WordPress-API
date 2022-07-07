package api.methods;

import api.APIManager;
import framework.CredentialsManager;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.http.Header;
import utils.LoggerManager;

import java.util.HashMap;
import java.util.Map;

public class APIAuthorizationMethods {
    private static final LoggerManager log = LoggerManager.getInstance();
    private static final APIManager apimanager = APIManager.getInstance();
    private static final CredentialsManager credentialsManager = CredentialsManager.getInstance();
    private static APIAuthorizationMethods instance;

    public static Header getAuthHeader(String userRole) {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("username", credentialsManager.getUserName(userRole));
        jsonAsMap.put("password", credentialsManager.getPassword(userRole));
        String tokenEndpoint = credentialsManager.getTokenEndpoint();
        Response response = apimanager.post(tokenEndpoint, ContentType.JSON, jsonAsMap);

        if ((response.jsonPath().get("token_type") == null)||(response.jsonPath().get("jwt_token") == null)) {
            log.error("Error while getting token");
            return null;
        } else {
            String token = response.jsonPath().get("token_type") + " " + response.jsonPath().get("jwt_token");
            return new Header("Authorization", token);
        }
    }
}
