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
    private static final APIManager api = APIManager.getInstance();
    private static final CredentialsManager auth = CredentialsManager.getInstance();
    private static APIAuthorizationMethods instance;

    public static Header getAuthHeader(String userRole) {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("username", auth.getUserName(userRole));
        jsonAsMap.put("password", auth.getPassword(userRole));
        String tokenEndpoint = auth.getTokenEndpoint();
        Response res = api.post(tokenEndpoint, ContentType.JSON, jsonAsMap);

        if ((res.jsonPath().get("token_type") == null)||(res.jsonPath().get("jwt_token") == null)) {
            log.error("Error while getting token");
            return null;
        } else {
            String token = res.jsonPath().get("token_type") + " " + res.jsonPath().get("jwt_token");
            return new Header("Authorization", token);
        }
    }
}
