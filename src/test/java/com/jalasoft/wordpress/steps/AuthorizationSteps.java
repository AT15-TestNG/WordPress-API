package com.jalasoft.wordpress.steps;

import api.methods.APIAuthorizationMethods;
import api.http.HttpHeaders;
import constants.DomainAppEnums;
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

    @Given("^An authorized user with \"(.*?)\" role$")
    public void changeToken(String userRole) {
        Header authHeader = APIAuthorizationMethods.getAuthHeader(userRole, DomainAppEnums.UserNames.USERNAME.getUserName());
        if (Objects.nonNull(authHeader)) {
            headers.addHeader(authHeader);
        } else {
            Assert.fail("Unable to retrieve authorization header for user --> " + userRole);
        }
    }

    @Given("^I am using a token with \"(.*?)\"$")
    public void getInvalidToken(String tokenValue) {
        Map<String, String> tokenValues = new HashMap<>();
        tokenValues.put("incorrect JWT format", "bearer 123456");
        tokenValues.put("invalid JWT signature", "bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOjEsIm5hbWUiOiJzc2Fob25lcm8iLCJpYXQiOjE2NTcxMTQxNTksImV4cCI6MTgxNDc5NDE1OX0.BOOwhosxHZvu7AGkzoWmHOla22nmkzCIMX7J-7_k4Ma");

        headers.addHeader(new Header("Authorization", tokenValues.get(tokenValue)));
    }
}
