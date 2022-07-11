package com.jalasoft.wordpress.steps.hooks.features;

import api.http.HttpResponse;
import api.methods.APITagsMethods;
import io.cucumber.java.After;
import org.testng.Assert;

public class TagsFeatureHook {
    private final HttpResponse response;

    public TagsFeatureHook(HttpResponse response) {
        this.response = response;
    }

    @After("@CreateATag")
    public void afterCreateACategory() {
        String id = response.getResponse().jsonPath().getString("id");
        Boolean deleted = APITagsMethods.deleteTagById(id);

        Assert.assertTrue(deleted, "Tag was not deleted");
    }
}