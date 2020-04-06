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
		try {

			for (String cmd : cmdList) {
				String[] cArr = StringUtils.split(cmd, " ");
				String c0 = cArr[0];
				switch (c0) {
				case "openbrowser":
					driver = BrowserUtils.openBrowser(cArr[1]);
					resultList = addResultList("--- open browser " + cArr[1]);
					break;
				case "opengeturl":
					driver.get(cArr[1]);
					resultList = addResultList("--- open get url " + cArr[1]);
					break;
				case "checktitle":
					String title = driver.getTitle();
					resultList = checkTextEquals(cmd, cArr[1], title);
					break;
				case "checkattribute":
					String strSpecial = driver.findElement(By.id(cArr[1])).getAttribute(cArr[2]);
					resultList = checkTextEquals(cmd, cArr[3], strSpecial);
					break;
				case "click":
					driver.findElement(By.id(cArr[1])).click();
					resultList = addResultList("-- click " + cArr[1]);
					break;
				case "input":
					driver.findElement(By.id(cArr[1])).sendKeys(cArr[2]);
					resultList = addResultList("-- input at " + cArr[1] + ", value = " + cArr[2]);
					break;
				case "clear":
					driver.findElement(By.id(cArr[1])).clear();
					resultList = addResultList("-- clear at " + cArr[1]);
					break;
				case "quitbrowser":
					BrowserUtils.quitBrowser(driver);
					resultList = addResultList("--- quit browser chrome");
					break;
				case "js":
					JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
					jsExecutor.executeScript(cArr[1]);
					break;
				case "getinput":
					String elementId = cArr[1];
					String inputValue = JOptionPane.showInputDialog("Please input a value at " + elementId);
					driver.findElement(By.id(elementId)).sendKeys(inputValue);
					break;
				case "wait":
					JOptionPane.showMessageDialog(null, "loading...", "Please click OK after loading finish",
							JOptionPane.OK_OPTION);
					break;
				default:
					break;
				}

			}
		} catch (Exception e) {

		} finally {
			FileUtils.createReport(testFile.getParent(), resultList);

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

	public static List<String> checkTextEquals(String cmd, String strExp, String strReal) {
		if (StringUtils.equals(strExp, strReal)) {
			resultList = addResultList("true for " + cmd + ", real value is " + strReal);
		} else {
			resultList = addResultList("false for " + cmd + ", real value is " + strReal);
		}
		return resultList;
	}

}