package com.geth.autotestjava;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JLabel;

import org.apache.commons.lang3.StringUtils;

public class FileUtils {

	public static File selectFilesAndDir() {
		JFileChooser jfc = new JFileChooser();
		File dir = new File("E:\\sts\\");
		jfc.setCurrentDirectory(dir);
		jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		jfc.showDialog(new JLabel(), "确定");
		return jfc.getSelectedFile();
	}

	public static List<String> readTestDoc(File file) throws IOException {
		List<String> result = new ArrayList<String>();
		System.out.print("result at " + file.getPath());

		if (StringUtils.right(file.getName(), 4).equalsIgnoreCase(".csv")) {
			result = readCsv(file);
		} else {
			result = readTxt(file);
		}

		return result;
	}

	public static List<String> readTxt(File file) throws IOException {
		List<String> result = new ArrayList<String>();
		if (file.isFile() && file.exists()) {
			InputStreamReader read = new InputStreamReader(new FileInputStream(file), "UTF-8");// 考虑到编码格式
			BufferedReader bufferedReader = new BufferedReader(read);
			String lineTxt = null;
			while ((lineTxt = bufferedReader.readLine()) != null) {
				lineTxt.replaceAll(" ", ",");
				System.out.println(lineTxt);
				result.add(lineTxt);
			}
			read.close();
		} else {
			System.out.println("找不到指定的文件 at " + file.getAbsolutePath());
		}
		return result;
	}

	public static List<String> readCsv(File file) throws IOException {
		List<String> result = new ArrayList<String>();
		if (file.isFile() && file.exists()) {
			InputStreamReader read = new InputStreamReader(new FileInputStream(file), "UTF-8");// 考虑到编码格式
			BufferedReader bufferedReader = new BufferedReader(read);
			String lineTxt = null;
			while ((lineTxt = bufferedReader.readLine()) != null) {
				result.add(lineTxt);
			}
			read.close();
		} else {
			System.out.println("找不到指定的文件 at " + file.getAbsolutePath());
		}
		return result;
	}

	public static void createReport(File t, List<String> r) {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyyMMdd_hhmmss_SSS");
		String nowStr = now.format(formatter2);

		String path = t.getParent() + "/result_" + StringUtils.split(t.getName(), ".")[0] + "_" + nowStr + ".txt";
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
			for (String str : r) {
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
}