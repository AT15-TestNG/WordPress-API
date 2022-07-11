package com.jalasoft.wordpress.steps.hooks.features;

import api.http.HttpResponse;
import api.http.HttpScenarioContext;
import api.methods.APIAppPasswordsMethods;
import io.cucumber.java.Before;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.Objects;

public class AppPasswordsFeatureHook {
    private final HttpResponse response;
    private final HttpScenarioContext scenarioContext;

    public AppPasswordsFeatureHook(HttpResponse response, HttpScenarioContext scenarioContext) {
        this.response = response;
        this.scenarioContext = scenarioContext;
    }

    @Before(order = 2, value ="@GetAllAppPasswordsById2 or @GetAppPasswordsByIdByUuid2 or @UpdateAppPasswordsByIdByUuid2")
    public void CreateAnAppPasswordById() {
        String userId = response.getResponse().jsonPath().getString("id");
        scenarioContext.addScenarioContext("userId",userId);
        Response requestResponse = APIAppPasswordsMethods.CreateAnAppPasswordById(userId);
        scenarioContext.addScenarioContext("name", requestResponse.jsonPath().getString("name"));
        scenarioContext.addScenarioContext("uuid", requestResponse.jsonPath().getString("uuid"));

        if (Objects.nonNull(requestResponse)) {
            response.setResponse(requestResponse);
        } else {
            Assert.fail("app-password was not created");
        }
    }
}
