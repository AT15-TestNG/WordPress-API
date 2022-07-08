package com.jalasoft.wordpress.steps.hooks.features;

import api.http.HttpResponse;
import api.methods.APIUsersMethods;
import io.cucumber.java.After;
import org.testng.Assert;

public class UsersFeatureHook {
    private final HttpResponse response;

    public UsersFeatureHook(HttpResponse response) {
        this.response = response;
    }

    @After("@CreateUser")
    public void afterCreateAUserFeature() {
        String id = response.getResponse().jsonPath().getString("id");
        String status = APIUsersMethods.deleteUserById(id);

        Assert.assertEquals(status, "true", "post was not deleted");
    }
}
