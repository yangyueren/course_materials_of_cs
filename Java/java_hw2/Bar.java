package hw2_yyr;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Bar {
	public String question = "";
	public char[] charQuestion = new char[16];
	public String answer = "";
	public Character symbol = '3'; //choose mode
	public int bars = 0;// bars
	public int numberOfOperators = 2;
	public int maxDigit = 3;
	public String[] a = new String[]{"***", "***", "***", "***", "*", "*"};
	public String[] b = new String[]{"***", "***", "***", "***", "*", "*"};
	public int [] detResult = new int[15];//difference compare : a and b
	public String formalInput = "";
	public static Map<Character, Integer> map = new HashMap<Character, Integer>(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put('0', 0b1111011);
			put('1', 0b1001000);
			put('2', 0b0111101);
			put('3', 0b1101101);
			put('4', 0b1001110);
			put('5', 0b1100111);
			put('6', 0b1110111);
			put('7', 0b1001001);
			put('8', 0b1111111);
			put('9', 0b1101111);
			put('+', 0b0110000000);
			put('-', 0b0010000000);
			put('=', 0b1010000000);
			put('*', 0);
		}
	};
	

    public Bar() {
		// TODO Auto-generated constructor stub
    	question = "";
    	answer = "";
    	symbol = '3'; //choose mode
    	maxDigit = 3;
    	bars = 0;// bars
    	formalInput = "";
	}
	
	public void setCharacter(int maxDigit, int numOfOperators, Character userSymbol, int bar) {
		symbol = userSymbol;
		bars = bar;
		numberOfOperators = numOfOperators;
		this.maxDigit = maxDigit;
		
	}
	public int maxDigitRand() {
		Random random = new Random();
		if (maxDigit == 1) {
			return random.nextInt(9) % 9 + 1;
		}
		else if (maxDigit == 2) {
			return random.nextInt(99) % 99 + 1;
		}
		else {
			return random.nextInt(999) % 999 + 1;
		}
	}
	// input and form it into b[];
	public void userInput() {
		Scanner scan = new Scanner(System.in);
		String user_input = "";
		System.out.println("user input the answer:");
		String temp = "";
//		temp = scan.nextLine();
//		scan.nextLine();
		
		
		while(scan.hasNextLine()) {
			temp = scan.nextLine();
			
			break;
		}
		
		
		
		temp.replace(" ", "");
		user_input = temp;
		
		for (int i = 0, j = 0; i < user_input.length(); i++) {
			String chara = String.valueOf(user_input.charAt(i));
			if (user_input.charAt(i) == '+' || user_input.charAt(i) == '-') {
				if (b[4].equals("*")) {
					b[4] = b[4].replaceFirst("\\*", chara);
					j++;
				}
				else if (!b[4].equals("*") && b[5].equals("*")) {
					b[5] = b[5].replaceFirst("\\*", chara);
					j++;
				}
			}
			
			else if (user_input.charAt(i) == '=') {
				i = i + 1;
				for (; i < user_input.length(); i++) {
					b[3] = b[3].replaceFirst("\\*", String.valueOf(user_input.charAt(i)));
				}
			}
			else {
				b[j] = b[j].replaceFirst("\\*", chara);
			}
		}
		
		formalInput = b[0] + b[4] + b[1] + b[5] + b[2] + "=" + b[3];
		
		//debug
		return;
	}
	//a[]  answer  question     **************************************************
 	
	
	public String showResult() {
		String finalResult = answer.replaceAll("\\*", "");
		return finalResult;
	}
	//judge b[] is self equal
	public boolean isEqual() {
		boolean is_equal = false;
		int[] c = new int[6];
		int temp1 = 0;
		int temp2 = 0;
		String star = new String();
		for (int i = 0; i < 4; i++) {
			star = b[i].replaceAll("\\*", "");
			if (!star.equals("")) {
				c[i] = Integer.parseInt(star);
			}
			else {
				c[i] = 0;
			}
					
		}
		if (b[4].equals("+")) {
			temp1 = c[0] + c[1];
		}
		else if (b[4].equals("-")) {
			temp1 = c[0] - c[1];
		}
		
		if (b[5].equals("+")) {
			temp2 = temp1 + c[2];
		}
		else if (b[5].equals("-")) {
			temp2 = temp1 - c[2];
		}
		else{
			temp2 = temp1;
		}
		if (c[3] == temp2) {
			is_equal = true;
		}
		return is_equal;
	}
	
	
	//judge whether a and b
	public boolean compare() {
		boolean result = false;
		
//		if(formalInput.equals(answer)) {
//			return true;
//		}
		int total = 0;
		calculateEachBar(formalInput);
		switch (symbol) {
		//+ bars
		case '1':
			for(int i = 0; i < 14; i++) {
				if (detResult[i] < 0) {
					return false;
				}
				else {
					total = total + detResult[i];
				}
			}
			if (total == bars) {
				result = true;
			}
			break;
			
		//- bars
		case '0':
			for(int i = 0; i < 14; i++) {
				if (detResult[i] < 0) {
					return false;
				}
				else {
					total = total + detResult[i];
				}
			}
			if (total == bars) {
				result = true;
			}
			break;
		// transport bars
		case '2':
			for(int i = 0; i < 14; i++) {
				total = total + detResult[i];
			}
			if (total == 2 * bars) {
				result = true;
			}
			break;
		default:
			
			break;
		}
		
		return result;
	}
	public void calculateEachBar(String user_input) {
		int []temp = new int[16];
		for(int i = 0; i < 15; i++) {
//			System.out.println(question.charAt(i));
//			System.out.println(question);
//			System.out.println(map.get(question.charAt(i)));
//			System.out.println(map.get(user_input.charAt(i)));
			temp[i] = map.get(question.charAt(i)) ^ map.get(user_input.charAt(i));
			detResult[i] = numberOf1(temp[i]);
		}
	}
	//calculate the number of 1
	 public int numberOf1(int n) {
	        int sum = 0;
	        while(n != 0){
	            sum++;
	            n = n & (n - 1);
	        }
	        return sum;
	 }
	 int [][] difference = {
			 {0, -4, 0, 0, 0, 0, 0, -3, 1, 0   ,0,0,0},
			 {4, 0, 0, 3, 2, 0, 0, 1, 5, 4  ,0,0,0},
			 {0, 0, 0, 0, 0, 0, 0, 0, 2, 0  ,0,0,0},
			 {0, -3, 0, 0, 0, 0, 0, 0, 2, 1  ,0,0,0},
			 {0, -2, 0, 0, 0, 0, 0, 0, 3, 2  ,0,0,0 },
			 {0, 0, 0, 0, 0, 0, 1, 0, 2, 1   ,0,0,0},
			 {0, 0, 0, 0, 0, -1, 0, 0, 1, 0  ,0,0,0 },
			 {3, -1, 0, 2, 0, 0, 0, 0, 4, 3   ,0,0,0},
			 {-1,-5,-2,-2,-3,-2,-1,-4, 0,-1  ,0,0,0},
			 {0, -4, 0,-1,-2,-1, 0,-3, 1, 0  ,0,0,0},
			 {0,0,0,0,0,0,0,0,0,0,0, -1,0},
			 {0,0,0,0,0,0,0,0,0,0,1, 0,0},
			 {0,0,0,0,0,0,0,0,0,0,0, 0,0}
	 };
	 
	 int [][] trans = {
			 {0,0,0,0,0,0,1,0,0,1 ,0,0},
			 {0,0,0,0,0,0,0,0,0,0 ,0,0},
			 {0,0,0,1,0,2,0,0,0,0 ,0,0},
			 {0,0,1,0,0,1,0,0,0,0 ,0,0},
			 {0,0,0,0,0,0,0,0,0,0 ,0,0},
			 {0,0,2,1,0,0,0,0,0,0 ,0,0},
			 {0,0,0,0,0,0,0,0,0,1 ,0,0},
			 {0,0,0,0,0,0,0,0,0,0 ,0,0},
			 {0,0,0,0,0,0,0,0,0,0 ,0,0},
			 {0,0,0,0,0,0,1,0,0,0 ,0,0},
			 {0,0,0,0,0,0,0,0,0,0 ,0,0},
			 {0,0,0,0,0,0,0,0,0,0 ,0,0}
	 };
	 //a[]中原答案，question是生成的字符串，answer也是答案之一。
	 public String generate() {
			String randomString = "";
			Random random = new Random();
			
			int []randomNum = new int[]{0,0,0,0};
			randomNum[3] = 0;
			while (randomNum[3] <= 0 || randomNum[3] >= 1000) {
				for (int i = 0; i < numberOfOperators; i++) {
					randomNum[i] = maxDigitRand();
					
					
				}
				
				if (random.nextInt(100) % 2 == 0) {
					if (random.nextInt(100) % 2 == 0) {
						randomNum[3] = randomNum[0] + randomNum[1] + randomNum[2];
						randomString = String.valueOf(randomNum[0]) + "+" + String.valueOf(randomNum[1])+"+"+String.valueOf(randomNum[2])+"="+String.valueOf(randomNum[3]);
					}
					else {
						randomNum[3] = randomNum[0] + randomNum[1] - randomNum[2];
						randomString = String.valueOf(randomNum[0]) + "+" + String.valueOf(randomNum[1])+"-"+String.valueOf(randomNum[2])+"="+String.valueOf(randomNum[3]);
					}
				}
				else {
					if (random.nextInt(100) % 2 == 0) {
						randomNum[3] = randomNum[0] - randomNum[1] + randomNum[2];
						randomString = String.valueOf(randomNum[0]) + "-" + String.valueOf(randomNum[1])+"+"+String.valueOf(randomNum[2])+"="+String.valueOf(randomNum[3]);
					}
					else {
						randomNum[3] = randomNum[0] - randomNum[1] - randomNum[2];
						randomString = String.valueOf(randomNum[0]) + "-" + String.valueOf(randomNum[1])+"-"+String.valueOf(randomNum[2])+"="+String.valueOf(randomNum[3]);
					}
				}
				
			}
			
			
			
			
			for (int i = 0, j = 0; i < randomString.length(); i++) {
				String chara = String.valueOf(randomString.charAt(i));
				if (randomString.charAt(i) == '+' || randomString.charAt(i) == '-') {
					if (a[4].equals("*")) {
						a[4] = a[4].replaceFirst("\\*", chara);
						j++;
					}
					else if (!a[4].equals("*") && a[5].equals("*")) {
						a[5] = a[5].replaceFirst("\\*", chara);
						j++;
					}
				}
				
				else if (randomString.charAt(i) == '=') {
					for (i = i + 1; i < randomString.length(); i++) {
						a[3] = a[3].replaceFirst("\\*", String.valueOf(randomString.charAt(i)));
					}
				}
				else {
					a[j] = a[j].replaceFirst("\\*", chara);
				}
			}
			if (numberOfOperators == 2) {
				a[5] = "*";
				a[2] = "***";
			}
			//my answer
			answer = a[0] + a[4] + a[1] + a[5] + a[2] + "=" + a[3];
			//my initial question, to generate moved question.
			//debug
//			answer = new String("687+104****=791");
			
			question = answer;
			charQuestion = question.toCharArray();
			
			//在递归生成数字时标记该位已经使用过
			for (int i = 0; i < 15; i++) {
				change[i] = 0;
			}
			
			if (symbol == '1') {
				increaseBarOfOneNum(bars);
			}
			else if (symbol == '0') {
				decreaseBarOfOneNum(bars);
			}
			else {
				tranBarOfOneNum(bars);
			}
			
			
			question = new String(charQuestion);
			return question;
	 }
	 
	
	int change[] = new int[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

	 public void increaseBarOfOneNum(int totalBar) {
		 
		 
		 Random random = new Random();
		 int curBar = 0;
		 int location = 0;
		 formalInput = answer;//test if the question is suitable;
		 if (compare() || totalBar == 0) {
			
			return;
		}
		 else {
			 curBar = random.nextInt(totalBar) % totalBar + 1;
			 location = random.nextInt(14);
			 while (change[location] == 3) {
				 location = random.nextInt(14);				
			}
			 change[location] += 1;
			 int num1, num2;
			 int temp = 0;
			 if (charQuestion[location] == '+' || charQuestion[location] == '-') {
				num1 = charQuestion[location] / 2 -11;
			}
			 else if (charQuestion[location] == '*' || charQuestion[location] == '=') {
				num1 = 12;
			}
			 else{
				 num1 = (int)(charQuestion[location] - '0');
			}	 
			
			 
			 num2 = -1;
			 for (int i = 0; i < 13; i++) {
				if (difference[num1][i] > temp && difference[num1][i] <= curBar) {
					temp = difference[num1][i];
					num2 = i;
				}
			}
			 if (temp != 0) {
				 charQuestion[location] = (char) (num2 + '0');
				 if (charQuestion[location] == ':') {
					charQuestion[location] = '+';
				}
			}
			 curBar = temp;
			 
			// System.out.println(totalBar - curBar);
			 
			 question = new String(charQuestion);
			 increaseBarOfOneNum(totalBar - curBar);
			 
			 return;
		 }

	 }
	 public void decreaseBarOfOneNum(int totalBar) {
		 Random random = new Random();
		 int curBar = 0;
		 int location = 0;
		 formalInput = answer;//test if the question is suitable;
		 if (compare() || totalBar == 0) {
			
			return;
		}
		 else {
			 curBar = random.nextInt(totalBar) % totalBar + 1;
			 location = random.nextInt(14);
			 while (change[location] == 3) {
				 location = random.nextInt(14);				
			}
			 change[location] += 1;
			 
			 int num1, num2;
			 int temp = 0;
			 if (charQuestion[location] == '+' || charQuestion[location] == '-') {
				num1 = charQuestion[location] / 2 -11;
			}
			 else if (charQuestion[location] == '*' || charQuestion[location] == '=') {
				num1 = 12;
			}
			 else{
				 num1 = (int)(charQuestion[location] - '0');
			}	 
			 num2 = -1;
			 for (int i = 0; i < 13; i++) {
				if (difference[num1][i] < temp && difference[num1][i] >= (0-curBar)) {
					temp = difference[num1][i];
					num2 = i;
				}
			}
			 if (temp != 0) {
				 charQuestion[location] = (char) (num2 + '0');
				 if (charQuestion[location] == ';') {
						charQuestion[location] = '-';
					}
			}
			 
			 curBar = 0 - temp;
			 question = new String(charQuestion);
			 decreaseBarOfOneNum(totalBar - curBar);
			 
			 return;
		 }
	 }
	 
	 
	 
	 public void tranBarOfOneNum(int totalBar) {
		 
		 formalInput = answer;//test if the question is suitable;
		 while(compare() == false || answer.equals(question)) {
			 question = answer;
			 increaseBarForTrans(totalBar);
			 
			 decreaseBarForTrans(totalBar);
		 }

	 }
	 
public void increaseBarForTrans(int totalBar) {

		 Random random = new Random();
		 int curBar = 0;
		 int location = 0;
		 formalInput = answer;//test if the question is suitable;
		 if (compareForTrans('0') || totalBar == 0) {
			
			return;
		}
		 else {
			 curBar = random.nextInt(totalBar) % totalBar + 1;
			 location = random.nextInt(14);
			 while (change[location] == 3) {
				 location = random.nextInt(14);				
			}
			 change[location] += 1;
			 int num1, num2;
			 int temp = 0;
			 if (charQuestion[location] == '+' || charQuestion[location] == '-') {
				num1 = charQuestion[location] / 2 -11;
			}
			 else if (charQuestion[location] == '*' || charQuestion[location] == '=') {
				num1 = 12;
			}
			 else{
				 num1 = (int)(charQuestion[location] - '0');
			}	
			 num2 = -1;
			 for (int i = 0; i < 13; i++) {
				if (difference[num1][i] > temp && difference[num1][i] <= curBar) {
					temp = difference[num1][i];
					num2 = i;
				}
			}
			 if (temp != 0) {
				 charQuestion[location] = (char) (num2 + '0');
				 if (charQuestion[location] == ':') {
						charQuestion[location] = '+';
					}
			}
			 curBar = temp;
			 question = new String(charQuestion);
			 increaseBarForTrans(totalBar - curBar);
//			 question = charQuestion.toString();
			 return;
		 }

	 }
	 public void decreaseBarForTrans(int totalBar) {
		 Random random = new Random();
		 int curBar = 0;
		 int location = 0;
		 formalInput = answer;//test if the question is suitable;
		 if (compareForTrans('1') || compare() || (totalBar == 0)) {
			
			return;
		}
		 else {
			 
			 curBar = random.nextInt(totalBar) % totalBar + 1;
			 location = random.nextInt(14);
			 while (change[location] == 3) {
				 location = random.nextInt(14);				
			}
			 change[location] += 1;
			 
			 int num1, num2;
			 int temp = 0;
			 if (charQuestion[location] == '+' || charQuestion[location] == '-') {
				num1 = charQuestion[location] / 2 -11;
			}
			 else if (charQuestion[location] == '*' || charQuestion[location] == '=') {
				num1 = 12;
			}
			 else{
				 num1 = (int)(charQuestion[location] - '0');
			}	 
			 num2 = -1;
			 for (int i = 0; i < 13; i++) {
				if (difference[num1][i] < temp && difference[num1][i] >= (0-curBar)) {
					temp = difference[num1][i];
					num2 = i;
				}
			}
			 if (temp != 0) {
				 charQuestion[location] = (char) (num2 + '0');
				 if (charQuestion[location] == ';') {
						charQuestion[location] = '-';
					}
			}
			 
			 curBar = 0 - temp;
			 question = new String(charQuestion);
			 decreaseBarForTrans(totalBar - curBar);
//			 question = charQuestion.toString();
			 return;
		 }
	 }
	 public boolean compareForTrans(Character tranSymbol) {
			boolean result = false;
			int tranBar = bars;
			int total = 0;
			calculateEachBar(formalInput);//formal_input = answer , answer compare to question
			switch (tranSymbol) {
			//+ bars
			case '0':
				for(int i = 0; i < 15; i++) {
					if (detResult[i] < 0) {
						return false;
					}
					else {
						total = total + detResult[i];
					}
				}
				if (total == tranBar) {
					result = true;
				}
				break;
				
			//- bars
			case '1':
				for(int i = 0; i < 15; i++) {
					if (detResult[i] > 0) {
						return false;
					}
					else {
						total = total + detResult[i];
					}
				}
				if (total == tranBar * 2) {
					result = true;
				}
				break;
			
			
			default:
				
				break;
			}
			
			return result;
		}

}
