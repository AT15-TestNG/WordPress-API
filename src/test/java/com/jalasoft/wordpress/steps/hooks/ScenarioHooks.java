package com.jalasoft.wordpress.steps.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.LoggerManager;

import java.util.logging.Level;

public class ScenarioHooks {
    private  static final LoggerManager log = LoggerManager.getInstance();

    public void disableOtherJavaLoggers() {
        java.util.logging.Logger.getLogger("").setLevel(Level.OFF);
    }

    @Before(order = 1)
    public void beforeScenario(Scenario scenario) {
        log.info("Scenario: --> " + scenario.getName());
        //disableOtherJavaLoggers();
    }

    @After(order = 1)
    public void afterScenario(Scenario scenario) {
        log.info("Scenario: --> " + scenario.getStatus() + " : " + scenario.getName());
    }

    @After(order = 2)
    public void afterFailedScenario(Scenario scenario) {
        if(scenario.getStatus().toString().equalsIgnoreCase("failed")) {
            System.out.println(scenario.getName() + " scenario has failed");
        }
    }
}
