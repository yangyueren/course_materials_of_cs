package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

import Spider.SpiderJsoup;
import lucene.Lu;

public class MainInteraction {
	public static void main(String[] args) throws FileNotFoundException {
		String sourceFilePath = "/Users/yryang/Desktop/gitcode/java_hw4";
		String filePath = "/Users/yryang/Desktop/gitcode/lucene";// 创建索引的存储目录

		Lu lu = new Lu();
		SpiderJsoup spider = new SpiderJsoup();

		System.out.println(
				"Welcome to use the query search engine\nPlease input the string you want to query(input quit to eixt):");
		Scanner scanner = new Scanner(System.in);

		String input = scanner.next();

		while (!input.equals("quit")) {
			input = search(input);
		}
		scanner.close();
		System.out.println("Good bye!");
	}

	public static String search(String queryStr) throws FileNotFoundException {

		String query = queryStr;
		Lu lu = new Lu();
		String filePath = "/Users/yryang/Desktop/gitcode/lucene";// 创建索引的存储目录
		String[] result = lu.searrh(filePath, query);
		int total = Integer.parseInt(result[0]);
		if (total > 20) {
			total = 20;
			System.out.println("There are total " + result[0]
					+ " queryed results and we only show the 20 items!\nYou can type the number in front of the item to check the details of the disease.");
		} else {
			System.out.println("There are total " + result[0]
					+ " queryed results!\nYou can type the number in front of the item to check the details of the disease.");
		}

		int i = 1;
		String shownResult = "";
		for (i = 1; i <= 20; i++) {
			String name = result[i];
			if (name != null) {
				String[] temp = name.split("-|\\.");
				shownResult += String.valueOf(i) + ":" + temp[1] + "; ";
				if (i == 10) {

					shownResult += "\n";
				}
			}

		}
		System.out.println(shownResult);

		Scanner scanner = new Scanner(System.in);
		String item = scanner.next().replaceAll("\n", "");

		while (isInteger(item) && !item.equals("")) {
			int ite = Integer.valueOf(item);
			if (ite >= 1 && ite <= 20) {
				String file = result[ite];
				System.out.println(file);
				File f = new File(file);
				String content = new Scanner(f).useDelimiter("\\Z").next();
				System.out.println(content);
				System.out.println(
						"\n\nYou can type the number in front of the item to check the details of the disease or new string to query!");
				System.out.println(shownResult);
				item = scanner.next();
			}
		}

//		scanner.close();
		return item;
	}

	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}

}
