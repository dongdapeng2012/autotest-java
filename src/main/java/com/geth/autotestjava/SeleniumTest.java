package com.geth.autotestjava;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumTest {
	public static void main(String[] args) {
		File directory = new File(System.getProperty("user.dir"));
		String path = directory.getParent();
		System.setProperty("webdriver.chrome.driver", path + "/Chrome/chromedriver.exe");

		WebDriver driver = new ChromeDriver();
		driver.get("http://www.baidu.com");

		String title = driver.getTitle();
		System.out.printf(title);

		driver.quit();

		String result = "ABC";
		createReport(result);
	}

	private static void createReport(String r) {
		File directory = new File(System.getProperty("user.dir"));
		String path = directory.getPath() + "/result.txt";
		File file = new File(path);
		System.out.print(file.getPath());
		if (!file.exists()) {
			file.getParentFile().mkdirs();
		}
		try {
			file.createNewFile();

			// write
			FileWriter fw = new FileWriter(file, true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(r);
			bw.flush();
			bw.close();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}