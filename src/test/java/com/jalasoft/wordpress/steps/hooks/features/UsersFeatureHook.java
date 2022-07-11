package com.jalasoft.wordpress.steps.hooks.features;

import api.http.HttpResponse;
import api.methods.APIUsersMethods;
import constants.DomainAppEnums;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.Objects;

public class UsersFeatureHook {
    private final HttpResponse response;
    private String userId;

    public UsersFeatureHook(HttpResponse response) {
        this.response = response;
    }

    @Before(order = 1, value ="@RetrieveAUser or @RetrieveMe or @UpdateUser or @UpdateMe or @DeleteAUser or @DeleteMe")
    public void beforeRetrieveAUserFeature() {
        Response requestResponse = APIUsersMethods.createAPropertyUser(DomainAppEnums.UserRole.ADMINISTRATOR.getUserRole());

        if (Objects.nonNull(requestResponse)) {
            response.setResponse(requestResponse);
        } else {
            Assert.fail("User was not created");
        }
        userId = response.getResponse().jsonPath().getString("id");
    }

    @Before(order = 1, value ="@CreateAnAppPassword or @GetAllAppPasswordsById")
    public void createAUniqueUser() {
        Response requestResponse = APIUsersMethods.createAUniqueUser(DomainAppEnums.UserRole.ADMINISTRATOR.getUserRole());

        if (Objects.nonNull(requestResponse)) {
            response.setResponse(requestResponse);
        } else {
            Assert.fail("User was not created");
        }
        userId = response.getResponse().jsonPath().getString("id");
    }

    @Before(order = 1, value ="@GetStatusByNameSubscriberUser")
    public void beforeTestsWithSubscriberUser() {
        Response requestResponse = APIUsersMethods.createAPropertyUser(DomainAppEnums.UserRole.SUBSCRIBER.getUserRole());

        if (Objects.nonNull(requestResponse)) {
            response.setResponse(requestResponse);
        } else {
            Assert.fail("User was not created");
        }
        userId = response.getResponse().jsonPath().getString("id");

    }

    @After("@CreateUser or @RetrieveAUser or @UpdateUser or @RetrieveMe or @UpdateMe or @GetStatusByNameSubscriberUser or @CreateAnAppPassword or @GetAllAppPasswordsById")
    public void afterCreateAUserFeature() {
        String status = APIUsersMethods.deleteUserById(userId);

        Assert.assertEquals(status, "true", "User was not deleted");
    }
}
