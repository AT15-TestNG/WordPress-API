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

public class APIAppPasswordsMethods {
    private static final LoggerManager log = LoggerManager.getInstance();
    public static final APIManager apiManager = APIManager.getInstance();
    public static final CredentialsManager credentialsManager = CredentialsManager.getInstance();

    public static Response CreateAnAppPasswordById(String userId) {
        Header header = APIAuthorizationMethods.getAuthHeader(DomainAppEnums.UserRole.ADMINISTRATOR.getUserRole());
        Headers authHeaders = new Headers(header);

        String appPasswordsByIdEndpoint = credentialsManager.getAppPasswordsByIdEndpoint().replace("<user_id>", userId);
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("name", "uniqueAppName" + StringManager.getTimeStamp());
        Response response = apiManager.post(appPasswordsByIdEndpoint, jsonAsMap, authHeaders);

        if (response.jsonPath().getString("uuid") == null) {
            log.error("Failed to create an app-password for "+ userId);
            return null;
        } else {
            return response;
        }
    }
}
