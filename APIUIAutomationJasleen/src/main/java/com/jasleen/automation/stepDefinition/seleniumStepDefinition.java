package com.jasleen.automation.stepDefinition;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;

public class seleniumStepDefinition implements En {

	private static ResourceBundle configProp = ResourceBundle.getBundle("application");
	public static WebDriver wd = null;

	public seleniumStepDefinition() {
		Given("{string} of trademe website to launch them and browse", (String webUrl) -> {
			
			String DriverPath = System.getProperty("user.dir") + "\\src\\test\\resources\\drivers\\";

				System.setProperty("webdriver.chrome.driver", DriverPath + "chromedriver98.exe");
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--incognito");
				DesiredCapabilities capabilities = new DesiredCapabilities();
				capabilities.setCapability(ChromeOptions.CAPABILITY, options);
				wd = new ChromeDriver(options);
				wd.get( configProp.getString(webUrl));
				wd.manage().window().maximize();
				System.out.println("Browser launched !!");
				Thread.sleep(1000);


		});

		Given("search the details of {string} car", (String car) -> {
			
			WebElement element = wd.findElement(By.xpath("//input[@id='search']"));
			element.click();
			element.clear();
			element.sendKeys(car);
			wd.findElement(By.xpath("//button[text()=' Search ']")).click();
			Thread.sleep(3000);
			
		});

		Then("validate the below details", (DataTable dataTable) -> {
			String eNumberPlate = null;
			String eKilometers = null;
			String eBody = null;
			String eSeats = null;

		    List<Map<String, String>> details = dataTable.asMaps(String.class, String.class);
		    for(Map<String, String> detail : details) {
		    	eNumberPlate = detail.get("NumberPlate");
		    	eKilometers = detail.get("Kilometers");
		    	eBody = detail.get("Body");
		    	eSeats = detail.get("Seats");
		    }
		    wd.findElement(By.xpath("(//div[@aria-label='2009 Suzuki Swift.'])[1]")).click();
		    Thread.sleep(3000);
		    String aNumberPlate = wd.findElement(By.xpath("(//div[contains(@class, 'tm-motors-vehicle-attributes__tag--content')])[7]")).getText();
		    String aKm = wd.findElement(By.xpath("(//div[contains(@class, 'tm-motors-vehicle-attributes__tag--content')])[1]")).getText();
		    String aBody = wd.findElement(By.xpath("(//div[contains(@class, 'tm-motors-vehicle-attributes__tag--content')])[2]")).getText();
		    String aSeats = wd.findElement(By.xpath("(//div[contains(@class, 'tm-motors-vehicle-attributes__tag--content')])[3]")).getText();
		    System.out.println("Values Retrieved: "+ aNumberPlate +aKm+ aBody +aSeats);
		    
			Assert.assertEquals("Value doesn't Match" , eNumberPlate, aNumberPlate.split(":")[1].trim());
			Assert.assertEquals("Value doesn't Match" , eKilometers, aKm.trim());
			Assert.assertEquals("Value doesn't Match" , eBody, aBody.trim());
			Assert.assertEquals("Value doesn't Match" , eSeats, aSeats.trim());
			wd.quit();
			System.out.println("TEST PASSED!!!");
		});
	}

}
