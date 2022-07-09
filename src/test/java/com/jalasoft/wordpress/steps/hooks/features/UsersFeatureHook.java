package com.jalasoft.wordpress.steps.hooks.features;

import api.http.HttpResponse;
import api.methods.APIUsersMethods;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.Objects;

public class UsersFeatureHook {
    private final HttpResponse response;

    public UsersFeatureHook(HttpResponse response) {
        this.response = response;
    }

    @Before("@RetrieveAUser or @RetrieveMe or @UpdateUser or @UpdateMe or @DeleteAUser")
    public void beforeRetrieveAUserFeature() {
        Response requestResponse = APIUsersMethods.createAUser();

        if (Objects.nonNull(requestResponse)) {
            response.setResponse(requestResponse);
        } else {
            Assert.fail("User was not created");
        }
    }

    @After("@CreateUser or @RetrieveAUser or @UpdateUser or @RetrieveMe or @UpdateMe")
    public void afterCreateAUserFeature() {
        String id = response.getResponse().jsonPath().getString("id");
        String status = APIUsersMethods.deleteUserById(id);

        Assert.assertEquals(status, "true", "User was not deleted");
    }
}
