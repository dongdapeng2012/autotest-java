package com.geth.autotestjava;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumTest {

	private static List<String> resultList = new ArrayList<String>();

	private static WebDriver driver = null;

	public static void main(String[] args) {
		List<String> cmdList = FileUtils.readTestDoc();

		for (String cmd : cmdList) {
			String[] cArr = StringUtils.split(cmd, " ");
			String result = null;
			switch (cArr[0]) {
			case "openbrowser":
				driver = BrowserUtils.openBrowser(cArr[1]);
				resultList = addResultList("open browser " + cArr[1]);
				break;
			case "opengeturl":
				driver.get(cArr[1]);
				resultList = addResultList("open get url " + cArr[1]);
				break;
			case "checktitle":
				String title = driver.getTitle();
				System.out.printf("expect " + cArr[1] + ", is " + title);
				resultList = check(cArr[1], title);
				break;
			case "checktext":
				String text = driver.findElement(By.id(cArr[1])).getText();
				resultList = check(cArr[2], text);
				break;
			case "checkspecial":
				String strSpecial = driver.findElement(By.id(cArr[1])).getAttribute(cArr[2]);
				System.out.printf("expect " + strSpecial + ", is " + strSpecial);
				resultList = check(cArr[3], strSpecial);
				break;
			case "click":
				driver.findElement(By.id(cArr[1])).click();
				break;
			case "input":
				driver.findElement(By.id(cArr[1])).sendKeys(cArr[2]);
				break;
			case "clear":
				driver.findElement(By.id(cArr[1])).clear();
				break;
			case "quitbrowser":
				driver.quit();
				resultList = addResultList("quit browser chrome");
				break;
			default:
				break;
			}

		}

		FileUtils.createReport(resultList);

		cleanStaticObjects();
	}

	public static List<String> addResultList(String r) {
		System.out.println(r);
		resultList.add(r);
		return resultList;
	}

	public static void cleanStaticObjects() {
		resultList = null;

		driver.quit();
		driver = null;
	}

	public static List<String> check(String strExp, String strReal) {
		if (StringUtils.equalsIgnoreCase(strExp, strReal)) {
			resultList = addResultList("checktitle result true. expect " + strExp + ", is " + strReal);
		} else {
			resultList = addResultList("checktitle result false. expect " + strExp + ", is " + strReal);
		}
		return resultList;
	}
}