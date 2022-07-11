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

public class APIUsersMethods {
    private static final LoggerManager log = LoggerManager.getInstance();
    public static final APIManager apiManager = APIManager.getInstance();
    public static final CredentialsManager credentialsManager = CredentialsManager.getInstance();

    public static Response createAPropertyUser(String role) {
        Header header = APIAuthorizationMethods.getAuthHeader(DomainAppEnums.UserRole.ADMINISTRATOR.getUserRole());
        Headers authHeaders = new Headers(header);

        String usersEndpoint = credentialsManager.getUsersEndpoint();

        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("username", credentialsManager.getUserName(role));
        jsonAsMap.put("email", "serjmendieta@gmail.com");
        jsonAsMap.put("password", credentialsManager.getPassword(role));
        jsonAsMap.put("roles", role);

        Response response = apiManager.post(usersEndpoint, jsonAsMap, authHeaders);

        if (response.jsonPath().getString("id") == null) {
            log.error("Failed to create user");
            return null;
        } else {
            return response;      }
    }


    public static Response createAUniqueUser(String role) {
        Header header = APIAuthorizationMethods.getAuthHeader(DomainAppEnums.UserRole.ADMINISTRATOR.getUserRole());
        Headers authHeaders = new Headers(header);

        String usersEndpoint = credentialsManager.getUsersEndpoint();

        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("username", credentialsManager.getUserName(role) + StringManager.getTimeStamp());
        jsonAsMap.put("email", "serjmendieta"+StringManager.getTimeStamp()+"@gmail.com");
        jsonAsMap.put("password", credentialsManager.getPassword(role));
        jsonAsMap.put("roles", role);

        Response response = apiManager.post(usersEndpoint, jsonAsMap, authHeaders);

        if (response.jsonPath().getString("id") == null) {
            log.error("Failed to create user");
            return null;
        } else {
            return response;
        }
    }
    public static String deleteUserById(String userId) {
        Header header = APIAuthorizationMethods.getAuthHeader(DomainAppEnums.UserRole.ADMINISTRATOR.getUserRole());
        Headers authHeaders = new Headers(header);
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("force", true);
        jsonAsMap.put("reassign", 1);

        String usersByIdEndpoint = credentialsManager.getUsersByIdEndpoint().replace("<id>", userId);
        Response response = apiManager.delete(usersByIdEndpoint, jsonAsMap, authHeaders);

        if (response.getBody().jsonPath().getString("deleted").equals("true")) {
            return response.jsonPath().getString("deleted");
        } else {
            log.error("Failed to delete user");
            return null;
        }
    }

}