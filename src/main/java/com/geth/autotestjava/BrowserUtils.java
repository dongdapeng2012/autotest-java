package com.geth.autotestjava;

import java.io.File;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BrowserUtils {

	public static WebDriver openBrowser(String browser) {
		File directory = new File(System.getProperty("user.dir"));
		String path = directory.getParent();
		WebDriver driver = null;
		switch (browser) {
		case "chrome":
			System.setProperty("webdriver.chrome.driver", path + "/Chrome/chromedriver.exe");
			driver = new ChromeDriver();
			break;
		default:
			break;
		}
		return driver;
	}
	
	public static void quitBrowser(WebDriver driver) {
		driver.quit();
	}

}