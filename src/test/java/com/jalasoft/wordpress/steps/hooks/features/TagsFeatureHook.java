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
    private String tagId;

    public TagsFeatureHook(HttpResponse response) {
        this.response = response;
    }

    @After("@CreateATag or @RetrieveATag or @UpdateATag or @RetrieveATagAsSubscriber or @UpdateATagAsSubscriber or @DeleteATagAsSubscriber")
    public void afterCreateATag() {
        if (Objects.nonNull(response.getResponse().jsonPath().getString("id"))) {
            String id = response.getResponse().jsonPath().getString("id");
            boolean deleted = APITagsMethods.deleteTagById(id);
            Assert.assertTrue(deleted, "Tag was not deleted");
        } else if (Objects.nonNull(tagId)) {
            boolean deleted = APITagsMethods.deleteTagById(tagId);

            Assert.assertTrue(deleted, "Tag was not deleted");
        } else {
            Assert.fail("Tag was not able to being deleted");
        }
    }
    @Before("@RetrieveATag or @UpdateATag or @DeleteATag or @DeleteATagError501 or @RetrieveATagAsSubscriber or @UpdateATagAsSubscriber or @DeleteATagAsSubscriber")
    public void beforeRetrieveATag() {
        String name = "Tag Name Example";

        Response requestResponse = APITagsMethods.createATag(name);
        tagId = requestResponse.jsonPath().getString("id");
        if (Objects.nonNull(requestResponse)) {
            response.setResponse(requestResponse);
        } else {
            Assert.fail("Tag was not created");
        }
    }
}