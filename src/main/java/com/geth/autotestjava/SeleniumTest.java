package com.geth.autotestjava;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumTest {

	private static List<String> resultList = new ArrayList<String>();

	private static WebDriver driver = null;

	public static void main(String[] args) {
		List<String> cmdList = FileUtils.readTestDoc();
		try {

			for (String cmd : cmdList) {
				String[] cArr = StringUtils.split(cmd, " ");

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
					resultList = check("checktitle", cArr[1], title);
					break;
				case "checktext":
					String text = driver.findElement(By.id(cArr[1])).getText();
					resultList = check("checktext", cArr[2], text);
					break;
				case "checkspecial":
					String strSpecial = driver.findElement(By.id(cArr[1])).getAttribute(cArr[2]);
					System.out.printf("expect " + strSpecial + ", is " + strSpecial);
					resultList = check("checkspecial", cArr[3], strSpecial);
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
				case "js":
					JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
					jsExecutor.executeScript(cArr[1]);
					break;
				case "getinput":
					String inputValue = JOptionPane.showInputDialog("Please input a value at " + cArr[1]);
					driver.findElement(By.id(cArr[1])).sendKeys(inputValue);
					break;
				default:
					break;
				}

			}
		} catch (Exception e) {

		} finally {
			FileUtils.createReport(resultList);

			cleanStaticObjects();
		}

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

	public static List<String> check(String function, String strExp, String strReal) {
		if (StringUtils.equalsIgnoreCase(strExp, strReal)) {
			resultList = addResultList(function + " result true. expect " + strExp + ", is " + strReal);
		} else {
			resultList = addResultList(function + " result false. expect " + strExp + ", is " + strReal);
		}
		return resultList;
	}

}