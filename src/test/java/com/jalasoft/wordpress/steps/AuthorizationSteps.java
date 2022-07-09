package com.jalasoft.wordpress.steps;

import api.methods.APIAuthorizationMethods;
import api.http.HttpHeaders;
import io.cucumber.java.en.Given;
import io.restassured.http.Header;
import org.testng.Assert;

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
        Header authHeader = APIAuthorizationMethods.getAuthHeader(userRole, "testng");
        if (Objects.nonNull(authHeader)) {
            headers.addHeader(authHeader);
        } else {
            Assert.fail("Unable to retrieve authorization header for user --> " + userRole);
        }
    }
}
