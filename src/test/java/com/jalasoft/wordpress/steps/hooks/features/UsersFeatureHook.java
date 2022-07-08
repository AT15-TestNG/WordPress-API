package com.jalasoft.wordpress.steps.hooks.features;

import api.http.HttpResponse;
import api.methods.APIPostsMethods;
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

    @Before("@RetrieveAUser")
    public void beforeRetrieveAUserFeature() {
        String name = "Adolfo";
        String email = "adolfo@email.com";
        String password = "123456";
        String description = "I am a user test";


        Response requestResponse = APIUsersMethods.createAUser(name, email, password, description);

        if (Objects.nonNull(requestResponse)) {
            response.setResponse(requestResponse);
        } else {
            Assert.fail("User was not created");
        }
    }

    @After("@CreateUser or @RetrieveAUser")
    public void afterCreateAUserFeature() {
        String id = response.getResponse().jsonPath().getString("id");
        String status = APIUsersMethods.deleteUserById(id);

        Assert.assertEquals(status, "true", "User was not deleted");
    }
}
