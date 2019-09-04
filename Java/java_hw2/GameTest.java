package hw2_yyr;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameTest {
	static String user_input = new String("344");
	public static void main(String[] args) {
		Present present = new Present();
		
		
		while (true) {
			
			
		
			System.out.println("Input maxDigit(1-3) operators(2-3) mode(0-2) and bars(0-10) and :");
			Bar test = new Bar();
			
			
			//设置symbol bars
			Character sysmbol = '3';
			int bars = 0;
			int numOfOperator = 2;
			int maxDigit = 3;
			Scanner in = new Scanner(System.in);
			
			maxDigit = in.nextInt();
			numOfOperator = in.nextInt();
			String s = in.next();
			char[] chars = s.toCharArray();
			sysmbol = chars[0];  //c就是读入的单个字符
			bars = in.nextInt();
			
			
			
			test.setCharacter(maxDigit, numOfOperator, sysmbol, bars);
			
			//生存问题
			String question = test.generate();
			question = question.replaceAll("\\*", "");
			
			System.out.println("The question is below: " + question);
			present.presentBars(question);
//			System.out.println(question);
			System.out.println("One possible answer of the question is below:");
			System.out.println(test.answer.replaceAll("\\*", ""));
			
			while (true) {
				//用户输入答案
				test.userInput();
				
				if (test.formalInput.equals("***********=***")) {
					String re = test.showResult();
					System.out.println("The answer is as follows:");
					System.out.println(re);
					System.out.println("");
					break;
				}
				//验证答案
				else {
					if (test.isEqual()) {
						if (test.compare()) {
							System.out.println("Congratulations, you are right.");
							break;
						}
						else {
							System.out.println("The input is not appropriate.");
						}
					}
					else {
						System.out.println("The input is not equal.");
					}
				}
				
			}
			
		}
	}
	
	
	
	
	
	// strip the illegal character
	 public static String stringFilter (String str){
	        String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
	        Pattern p = Pattern.compile(regEx);
	        Matcher m = p.matcher(str);
	        return m.replaceAll("").trim();
	    }

}