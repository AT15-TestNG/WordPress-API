package com.jalasoft.wordpress.steps.hooks.features;

import api.http.HttpResponse;
import api.methods.APITagsMethods;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.Objects;

public class TagsFeatureHook {
    private final HttpResponse response;

    public TagsFeatureHook(HttpResponse response) {
        this.response = response;
    }

    @After("@CreateATag or @RetrieveATag or @UpdateATag")
    public void afterCreateACategory() {
        String id = response.getResponse().jsonPath().getString("id");
        boolean deleted = APITagsMethods.deleteTagById(id);

        Assert.assertTrue(deleted, "Tag was not deleted");
    }
    @Before("@RetrieveATag or @UpdateATag or @DeleteATag or @DeleteATagError501")

    public void beforeRetrieveATag() {
        String name = "Tag Name Example";

        Response requestResponse = APITagsMethods.createATag(name);

        if (Objects.nonNull(requestResponse)) {
            response.setResponse(requestResponse);
        } else {
            Assert.fail("Tag was not created");
        }
    }
}