package hw2_yyr;

public class Present {
	String [] num0 = new String[] {
			" * * * ",
			" *   * ",
			" *   * ",
			" *   * ",
			" * * * "
	};
	String [] num1 = new String[] {
			"     * ",
			"     * ",
			"     * ",
			"     * ",
			"     * "
	};
	String [] num2 = new String[] {
			" * * * ",
			"     * ",
			" * * * ",
			" *     ",
			" * * * "
	};
	String [] num3 = new String[] {
			" * * * ",
			"     * ",
			" * * * ",
			"     * ",
			" * * * "
	};
	String [] num4 = new String[] {
			" *   * ",
			" *   * ",
			" * * * ",
			"     * ",
			"     * "
	};
	String [] num5 = new String[] {
			" * * * ",
			" *     ",
			" * * * ",
			"     * ",
			" * * * "
	};
	String [] num6 = new String[] {
			" * * * ",
			" *     ",
			" * * * ",
			" *   * ",
			" * * * "
	};
	String [] num7 = new String[] {
			" * * * ",
			"     * ",
			"     * ",
			"     * ",
			"     * "
	};
	String [] num8 = new String[] {
			" * * * ",
			" *   * ",
			" * * * ",
			" *   * ",
			" * * * "
			
	};
	
	String [] num9 = new String[] {
			" * * * ",
			" *   * ",
			" * * * ",
			"     * ",
			" * * * "
			
	};
	String [] symPlus = new String[] {
			"       ",
			"   *   ",
			" * * * ",
			"   *   ",
			"       "
	};
	String [] symMinus = new String[] {
			"       ",
			"       ",
			" * * * ",
			"       ",
			"       "
	};
	String [] symEqual = new String[] {
			"       ",
			" * * * ",
			"       ",
			" * * * ",
			"       "
	};
	public void presentBars(String input) {
		String [] result = new String[] {
				"",
				"",
				"",
				"",
				""
		};
		
		char[] show = input.toCharArray();
		int length = input.length();
		for (int i = 0; i < length; i++) {
			switch (show[i]) {
			case '0':
				for (int j = 0; j < 5; j++) {
					result[j] += num0[j];
				}
				break;
			
			case '1':
				for (int j = 0; j < 5; j++) {
					result[j] += num1[j];
				}
				break;

			case '2':
				for (int j = 0; j < 5; j++) {
					result[j] += num2[j];
				}
				break;

			case '3':
				for (int j = 0; j < 5; j++) {
					result[j] += num3[j];
				}
				break;
			case '4':
				for (int j = 0; j < 5; j++) {
					result[j] += num4[j];
				}
				break;
			case '5':
				for (int j = 0; j < 5; j++) {
					result[j] += num5[j];
				}
				break;
			case '6':
				for (int j = 0; j < 5; j++) {
					result[j] += num6[j];
				}
				break;
			case '7':
				for (int j = 0; j < 5; j++) {
					result[j] += num7[j];
				}
				break;
			case '8':
				for (int j = 0; j < 5; j++) {
					result[j] += num8[j];
				}
				break;
			case '9':
				for (int j = 0; j < 5; j++) {
					result[j] += num9[j];
				}
				break;
			case '+':
				for (int j = 0; j < 5; j++) {
					result[j] += symPlus[j];
				}
				break;
			case '-':
				for (int j = 0; j < 5; j++) {
					result[j] += symMinus[j];
				}
				break;
			case '=':
				for (int j = 0; j < 5; j++) {
					result[j] += symEqual[j];
				}
				break;

			default:
				break;
			}
			
		}
		for (int k = 0; k < 5; k++) {
			System.out.println(result[k]);
		}
		return;
		
	}
}
