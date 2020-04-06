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

public class FileUtils {

	public static File selectFilesAndDir() {
		JFileChooser jfc = new JFileChooser();
		File dir = new File("E:\\sts\\");
		jfc.setCurrentDirectory(dir);
		jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		jfc.showDialog(new JLabel(), "确定");
		return jfc.getSelectedFile();
	}

	public static List<String> readTestDoc(File file) {
		List<String> result = new ArrayList<String>();

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
				System.out.println("找不到指定的文件 at " + file.getAbsolutePath());
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错 at " + file.getAbsolutePath());
			e.printStackTrace();
		}

		return result;
	}

	public static void createReport(String parentPath, List<String> strList) {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyyMMdd_hhmmss");
		String nowStr = now.format(formatter2);

		String path = parentPath + "/result_" + nowStr + ".txt";
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
}