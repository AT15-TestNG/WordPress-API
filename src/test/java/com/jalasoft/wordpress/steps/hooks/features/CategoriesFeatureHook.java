package com.jalasoft.wordpress.steps.hooks.features;

import api.http.HttpResponse;
import api.methods.APICategoriesMethods;
import io.cucumber.java.After;
import org.testng.Assert;

public class CategoriesFeatureHook {
    private final HttpResponse response;

    public CategoriesFeatureHook(HttpResponse response) {
        this.response = response;
    }

    @After("@CreateACategory")
    public void afterCreateACategory() {
        String id = response.getResponse().jsonPath().getString("id");
        Boolean deleted = APICategoriesMethods.deleteCategoryById(id);

        Assert.assertTrue(deleted, "Category was not deleted");
    }
}
