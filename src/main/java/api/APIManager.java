package api;

import framework.CredentialsManager;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;;
import utils.LoggerManager;

import java.util.Map;

public class APIManager {
    private static final LoggerManager log = LoggerManager.getInstance();
    private static APIManager instance;
    private APIManager() {
        initialize();
    }
    public static APIManager getInstance() {
        if (instance == null) {
            instance = new APIManager();
        }
        return instance;
    }
    private void initialize() {
        log.info("Initializing API Manager");
        RestAssured.baseURI = CredentialsManager.getInstance().getBaseURL();
        RestAssured.basePath = CredentialsManager.getInstance().getBasePath();
        RestAssured.port = CredentialsManager.getInstance().getAPIServicePort();
        RestAssured.registerParser("text/html", Parser.JSON);
    }
    public Response get(String endpoint) {
        return RestAssured.given().get(endpoint);
    }
    public Response get(String endpoint, Headers headers) {
        return RestAssured.given().headers(headers).get(endpoint);
    }
    public Response post(String endpoint, ContentType contentType, Object object) {
        return RestAssured.given().contentType(contentType).body(object).post(endpoint);
    }
    public Response post(String endpoint, Headers headers, ContentType contentType, Object object) {
        return RestAssured.given().headers(headers).contentType(contentType).body(object).post(endpoint);
    }
    public Response post(String endpoint, Map<String, Object> queryParams, Headers headers) {
        return RestAssured.given().queryParams(queryParams).headers(headers).post(endpoint);
    }
    public Response put(String endpoint, ContentType contentType, Object object) {
        return RestAssured.given().contentType(contentType).body(object).put(endpoint);
    }
    public Response put(String endpoint, Headers headers, ContentType contentType, Object object) {
        return RestAssured.given().headers(headers).contentType(contentType).body(object).put(endpoint);
    }
    public Response put(String endpoint, Map<String, Object> queryParams, Headers headers) {
        return RestAssured.given().queryParams(queryParams).headers(headers).put(endpoint);
    }
    public Response delete(String endpoint) {
        return RestAssured.given().delete(endpoint);
    }
    public Response delete(String endpoint, Headers headers) {
        return RestAssured.given().headers(headers).delete(endpoint);
    }
    public Response delete(String endpoint, Map<String, Object> queryParams, Headers headers) {
        return RestAssured.given().queryParams(queryParams).headers(headers).delete(endpoint);
    }
}
