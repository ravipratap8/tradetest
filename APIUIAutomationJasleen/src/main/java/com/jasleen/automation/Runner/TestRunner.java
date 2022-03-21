package com.jasleen.automation.Runner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;


@RunWith(Cucumber.class)
@CucumberOptions(
		tags =   "@Automation",
		features = ".\\src\\test\\java\\features"
		,glue={"com.jasleen.automation.stepDefinition"}, plugin = {"pretty", "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"}
		)


public class TestRunner {

}
