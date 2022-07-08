package api.methods;

import api.APIManager;
import framework.CredentialsManager;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import utils.LoggerManager;

import java.util.HashMap;
import java.util.Map;

public class APIAuthorizationMethods {
    private static final LoggerManager log = LoggerManager.getInstance();
    private static final APIManager apiManager = APIManager.getInstance();
    private static final CredentialsManager credentialsManager = CredentialsManager.getInstance();

    public static Header getAuthHeader(String userRole) {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("username", credentialsManager.getUserName(userRole));
        jsonAsMap.put("password", credentialsManager.getPassword(userRole));

        String tokenEndpoint = credentialsManager.getTokenEndpoint();

        Response response = apiManager.post(tokenEndpoint, ContentType.JSON, jsonAsMap);

        if ((response.jsonPath().get("token_type") == null) || (response.jsonPath().get("jwt_token") == null)) {
            return null;
        } else {
            String authorization = response.jsonPath().get("token_type") + " " + response.jsonPath().get("jwt_token");
            return new Header("Authorization", authorization);
        }
    }
}
