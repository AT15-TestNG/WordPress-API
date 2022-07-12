package com.jalasoft.wordpress.steps;

import api.methods.APIAuthorizationMethods;
import api.http.HttpHeaders;
import io.cucumber.java.en.Given;
import io.restassured.http.Header;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AuthorizationSteps {
    private final HttpHeaders headers;

    public AuthorizationSteps(HttpHeaders headers) {
        this.headers = headers;
    }

    @Given("^I am authorized with a user with \"(.*?)\" role$")
    public void getToken(String userRole) {
        Header authHeader = APIAuthorizationMethods.getAuthHeader(userRole);
        if (Objects.nonNull(authHeader)) {
            headers.addHeader(authHeader);
        } else {
            Assert.fail("Unable to retrieve authorization header for user --> " + userRole);
        }
    }

    @Given("^I am using a token with \"(.*?)\"$")
    public void getBadToken(String tokenValue) {
        Map<String, String> tokenValues = new HashMap<>();
        tokenValues.put("incorrect JWT format", "Bearer badToken");
        tokenValues.put("invalid JWT signature", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOjEsIm5hbWUiOiJ1c2VyIiwiaWF0IjoxNjU3MTM2ODUyLCJleHAiOjE4MTQ4MTY4NTJ9.kHwo8DI92-2VHk3ztMPjxoKv91BTr9jDgi666Vn5zqB");

        headers.addHeader(new Header("Authorization", tokenValues.get(tokenValue)));
    }
}
