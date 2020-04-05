package com.geth.autotestjava;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

	public static void createReport(String str) {
		File directory = new File(System.getProperty("user.dir"));
		String path = directory.getParent() + "/result.txt";
		File file = new File(path);

		System.out.print("result at " + file.getPath());

		if (!file.exists()) {
			file.getParentFile().mkdirs();
		}
		try {
			file.createNewFile();

			// write
			FileWriter fw = new FileWriter(file, false);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(str);
			bw.flush();
			bw.close();
			fw.close();
		} catch (IOException e) {
			System.out.println("写入文件内容出错 at " + path);
			e.printStackTrace();
		}
	}

	public static void createReport(List<String> strList) {
		File directory = new File(System.getProperty("user.dir"));
		String path = directory.getParent() + "/result.txt";
		File file = new File(path);

		System.out.print("result at " + file.getPath());

		if (!file.exists()) {
			file.getParentFile().mkdirs();
		}
		try {
			file.createNewFile();

			// write
			FileWriter fw = new FileWriter(file, false);
			BufferedWriter bw = new BufferedWriter(fw);
			for (String str : strList) {
				bw.write(str);
				bw.write("\n");
			}

			bw.flush();
			bw.close();
			fw.close();
		} catch (IOException e) {
			System.out.println("写入文件内容出错 at " + path);
			e.printStackTrace();
		}
	}

	public static List<String> readTestDoc() {
		List<String> result = new ArrayList<String>();

		File directory = new File(System.getProperty("user.dir"));
		String path = directory.getParent() + "/test.txt";
		File file = new File(path);

		System.out.print("result at " + file.getPath());

		try {
			String encoding = "GBK";
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					System.out.println(lineTxt);
					result.add(lineTxt);
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件 at " + path);
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错 at " + path);
			e.printStackTrace();
		}

		return result;
	}
}