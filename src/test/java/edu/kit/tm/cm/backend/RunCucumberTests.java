package edu.kit.tm.cm.backend;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"classpath:features/"}, glue = "edu.kit.tm.cm.backend.cucumber")
public class RunCucumberTests {
}
