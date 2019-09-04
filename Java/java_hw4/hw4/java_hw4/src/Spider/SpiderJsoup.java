package Spider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SpiderJsoup {
	public static void main(String[] args) throws IOException, InterruptedException {
		// 获取编辑推荐页
		Document document = Jsoup.connect("https://m.dxy.com/diseases")
				// 模拟火狐浏览器
				.userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)").get();
		String className = "disease-filter-list-card";
		Elements allUrl = document.getElementsByClass(className);
		SpiderJsoup sJsoup = new SpiderJsoup();
		int i = 0;
		for (Element url : allUrl) {
			String link = url.attr("href");
			String name = url.text();
			System.out.println(link);
//			if (i > 200) {
			Document page = Jsoup.connect(link + "/detail")
					// 模拟火狐浏览器
					.userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)").timeout(5 * 1000)
					.get();
//				System.out.println(page);
			String pageContent = sJsoup.filterUsefulInfo(page);
			sJsoup.WriteString(String.valueOf(i) + "-" + name + ".txt", pageContent);
			TimeUnit.SECONDS.sleep(1);

//			}

			System.out.println(i);
			i++;

		}

	}

	public String filterUsefulInfo(Document page) {
		String result = "";
		String titleName = "disease-detail-content-title";
		String contentName = "disease-detail-content-content";
		for (int i = 0; i < 6; i++) {
			Element filterID = page.getElementById(String.valueOf(i));
//			System.out.println(filterID);
			if (filterID != null) {
				Elements title = filterID.getElementsByClass(titleName);
//				System.out.println(title.text());
				result += "\n";
				result += title.text();

				Elements content = filterID.getElementsByClass(contentName);
				for (Element e : content) {
//					System.out.println(e.text());
					result += "\n";
					result += e.text();
				}
			}
		}

		return result;
	}

	public void WriteString(String filePath, String page) {
		try {
			File file = new File(filePath);
			PrintStream ps = new PrintStream(new FileOutputStream(file));
			ps.println(page);// 往文件里写入字符串
//            ps.append("http://www.jb51.net");// 在已有的基础上添加字符串
			ps.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}