package com.geth.autotestjava;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class SeleniumTest {
	private static List<String> resultList = new ArrayList<String>();

	private static WebDriver driver = null;

	public static void main(String[] args) {
		File testFile = FileUtils.selectFilesAndDir();

		List<String> cmdList = FileUtils.readTestDoc(testFile);

		for (String cmd : cmdList) {
			String[] cArr = StringUtils.split(cmd, " ");
			switch (cArr[0]) {
			case "openbrowser":
				openBrowser(cArr[1]);
				break;
			case "openurl":
				openUrl(cArr[1]);
				break;
			case "checktitle":
				checkTitle(cmd, cArr[1]);
				break;
			case "checkattribute":
				checkAttribute(cmd, cArr[1], cArr[2], cArr[3]);
				break;
			case "click":
				click(cArr[1]);
				break;
			case "input":
				input(cArr[1], cArr[2]);
				break;
			case "clear":
				clear(cArr[1]);
				break;
			case "js":
				js(cArr[1]);
				break;
			case "inputbox":
				inputBox(cArr[1]);
				break;
			case "loading":
				loading();
				break;
			case "quitbrowser":
				quitBrowser();
				break;
			default:
				break;
			}

		}

		FileUtils.createReport(testFile.getParent(), resultList);

		cleanStaticObjects();

	}

	private static void loading() {
		JOptionPane.showMessageDialog(null, "loading...", "Please click OK after loading finish",
				JOptionPane.OK_OPTION);
	}

	private static void checkTitle(String cmd, String value) {
		try {
			resultList = checkTextEquals(cmd, value, driver.getTitle());
		} catch (Exception e) {
			resultList = addResultList("error -- checkTtitle");
		}
	}

	private static void checkAttribute(String cmd, String elementId, String attribute, String value) {
		try {
			resultList = checkTextEquals(cmd, value, driver.findElement(By.id(elementId)).getAttribute(attribute));
		} catch (Exception e) {
			resultList = addResultList("error -- checkAttribute");
		}
	}

	private static void click(String elementId) {
		try {
			driver.findElement(By.id(elementId)).click();
			resultList = addResultList("-- click " + elementId);
		} catch (Exception e) {
			resultList = addResultList("error -- click " + elementId);
		}
	}

	private static void input(String elementId, String value) {
		try {
			driver.findElement(By.id(elementId)).sendKeys(value);
			resultList = addResultList("-- input at " + elementId + ", value = " + value);
		} catch (Exception e) {
			resultList = addResultList("error -- input at " + elementId + ", value = " + value);
		}
	}

	private static void clear(String elementId) {
		try {
			driver.findElement(By.id(elementId)).clear();
			resultList = addResultList("-- clear at " + elementId);
		} catch (Exception e) {
			resultList = addResultList("error -- clear at " + elementId);
		}
	}

	private static void js(String js) {
		try {
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			jsExecutor.executeScript(js);
			resultList = addResultList("-- js " + js);
		} catch (Exception e) {
			resultList = addResultList("error -- js " + js);
		}
	}

	private static void inputBox(String elementId) {
		try {
			String inputValue = JOptionPane.showInputDialog("Please input a value at " + elementId);
			driver.findElement(By.id(elementId)).sendKeys(inputValue);
			resultList = addResultList("-- inputbox " + elementId);
		} catch (Exception e) {
			resultList = addResultList("error -- inputbox " + elementId);
		}
	}

	private static void openBrowser(String browser) {
		try {
			driver = BrowserUtils.openBrowser(browser);
			resultList = addResultList("--- open browser " + browser);
		} catch (Exception e) {
			resultList = addResultList("error --- open browser " + browser);
		}
	}

	private static void quitBrowser() {
		try {
			BrowserUtils.quitBrowser(driver);
			resultList = addResultList("--- quit browser chrome");
		} catch (Exception e) {
			resultList = addResultList("error --- quit browser chrome");
		}
	}

	private static void openUrl(String url) {
		try {
			driver.get(url);
			resultList = addResultList("--- open get url " + url);
		} catch (Exception e) {
			resultList = addResultList("error --- open get url " + url);
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

	public static List<String> checkTextEquals(String cmd, String strExp, String strReal) {
		if (StringUtils.equals(strExp, strReal)) {
			resultList = addResultList("true for " + cmd + ", real value is " + strReal);
		} else {
			resultList = addResultList("false for " + cmd + ", real value is " + strReal);
		}
		return resultList;
	}

}