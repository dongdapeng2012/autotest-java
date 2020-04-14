package com.geth.autotestjava;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JOptionPane;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class SeleniumTest {
	private static List<String> resultList = new ArrayList<String>();

	private static Map<Integer, List<String>> resultListMap = new ConcurrentHashMap<Integer, List<String>>();

	private static WebDriver driver = null;

	public static void main(String[] args) {
		File testFile = FileUtils.selectFilesAndDir();

		runTest(testFile, 0);

		cleanStaticObjects();

	}

	private static boolean loading() {
		JOptionPane.showMessageDialog(null, "loading...", "Please click OK after loading finish",
				JOptionPane.OK_OPTION);
		resultList = addResultList("true --- Loading...");
		return true;
	}

	private static boolean checkTitle(String cmd, String value) {
		try {
			return checkTextEquals(cmd, value, driver.getTitle());
		} catch (Exception e) {
			resultList = addResultList("error -- checkTtitle");
			return false;
		}
	}

	private static boolean checkAttribute(String cmd, String elementId, String attribute, String value) {
		try {
			return checkTextEquals(cmd, value, driver.findElement(By.id(elementId)).getAttribute(attribute));
		} catch (Exception e) {
			resultList = addResultList("error -- checkAttribute");
			return false;
		}
	}

	private static boolean click(String elementId) {
		try {
			driver.findElement(By.id(elementId)).click();
			resultList = addResultList("true --- click " + elementId);
			return true;
		} catch (Exception e) {
			resultList = addResultList("error --- click " + elementId);
			return false;
		}
	}

	private static boolean input(String elementId, String value) {
		try {
			driver.findElement(By.id(elementId)).sendKeys(value);
			resultList = addResultList("true --- input at " + elementId + ", value = " + value);
			return true;
		} catch (Exception e) {
			resultList = addResultList("error --- input at " + elementId + ", value = " + value);
			return false;
		}
	}

	private static boolean clear(String elementId) {
		try {
			driver.findElement(By.id(elementId)).clear();
			resultList = addResultList("true --- clear at " + elementId);
			return true;
		} catch (Exception e) {
			resultList = addResultList("error --- clear at " + elementId);
			return false;
		}
	}

	private static boolean js(String js) {
		try {
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			jsExecutor.executeScript(js);
			resultList = addResultList("true --- js " + js);
			return true;
		} catch (Exception e) {
			resultList = addResultList("error --- js " + js);
			return false;
		}
	}

	private static boolean inputBox(String elementId) {
		try {
			String inputValue = JOptionPane.showInputDialog("Please input a value at " + elementId);
			driver.findElement(By.id(elementId)).sendKeys(inputValue);
			resultList = addResultList("true --- inputbox " + elementId);
			return true;
		} catch (Exception e) {
			resultList = addResultList("error --- inputbox " + elementId);
			return false;
		}
	}

	private static boolean openBrowser(String browser) {
		try {
			driver = BrowserUtils.openBrowser(browser);
			resultList = addResultList("true --- open browser " + browser);
			return true;
		} catch (Exception e) {
			resultList = addResultList("error --- open browser " + browser + "\n" + e);
			return false;
		}
	}

	private static boolean quitBrowser() {
		try {
			BrowserUtils.quitBrowser(driver);
			resultList = addResultList("true --- quit browser chrome");
			return true;
		} catch (Exception e) {
			resultList = addResultList("error --- quit browser chrome");
			return false;
		}
	}

	private static boolean openUrl(String url) {
		try {
			driver.get(url);
			resultList = addResultList("true --- open url " + url);
			return true;
		} catch (Exception e) {
			resultList = addResultList("error --- open url " + url);
			return false;
		}
	}

	public static boolean checkTextEquals(String cmd, String strExp, String strReal) {
		boolean result = StringUtils.equals(strExp, strReal);
		resultList = addResultList(result + " --- " + cmd + ", real value is " + strReal);
		return result;
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

	public static boolean runTest(File testFile, Integer depth) {
		List<String> cmdList = FileUtils.readTestDoc(testFile);

		boolean result = true;
		for (String cmd : cmdList) {
			String[] cArr = StringUtils.split(cmd, " ");
			if (cArr.length == 0) {
				continue;
			}
			switch (cArr[0]) {
			case "openbrowser":
				result = openBrowser(cArr[1]) && result;
				break;
			case "openurl":
				result = openUrl(cArr[1]) && result;
				break;
			case "checktitle":
				result = checkTitle(cmd, cArr[1]) && result;
				break;
			case "checkattribute":
				result = checkAttribute(cmd, cArr[1], cArr[2], cArr[3]) && result;
				break;
			case "click":
				result = click(cArr[1]) && result;
				break;
			case "input":
				result = input(cArr[1], cArr[2]) && result;
				break;
			case "clear":
				result = clear(cArr[1]) && result;
				break;
			case "js":
				result = js(cArr[1]) && result;
				break;
			case "inputbox":
				result = inputBox(cArr[1]) && result;
				break;
			case "loading":
				result = loading() && result;
				break;
			case "quitbrowser":
				quitBrowser();
				break;
			case "runscript":
				resultListMap.put(depth, new ArrayList<>(resultList));
				resultList = new ArrayList<String>();
				boolean runScriptResult = runTest(new File(cArr[1]), depth + 1) && result;
				result = runScriptResult && result;
				resultList = resultListMap.remove(depth);
				resultList = addResultList(runScriptResult + " --- " + cmd);
				break;
			default:
				break;
			}
		}

		FileUtils.createReport(testFile, resultList);

		return result;
	}
}