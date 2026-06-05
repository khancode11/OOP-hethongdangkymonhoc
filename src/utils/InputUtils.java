package utils;

import java.util.Scanner;

public class InputUtils {
	private static final Scanner SCANNER = new Scanner(System.in);

	private InputUtils() {
	}

	public static String readNonEmptyString(String prompt) {
		while (true) {
			System.out.print(prompt);
			String input = SCANNER.nextLine().trim();
			if (!input.isEmpty()) {
				return input;
			}
			System.out.println("Khong duoc de trong, vui long nhap lại.");
		}
	}

	public static String readString(String prompt) {
		System.out.print(prompt);
		return SCANNER.nextLine();
	}

	public static int readInt(String prompt) {
		while (true) {
			System.out.print(prompt);
			try {
				//parse so nguyen, neu loi thi bat ngoai le va nhap lai.
				return Integer.parseInt(SCANNER.nextLine().trim());
			} catch (NumberFormatException ex) {
				System.out.println("Gia tri khong hop le. Vui long nhap so nguyen.");
			}
		}
	}

	public static int readIntInRange(String prompt, int min, int max) {
		while (true) {
			int value = readInt(prompt);
			if (value >= min && value <= max) {
				return value;
			}
			System.out.println("Gia tri phai nam trong khoang [" + min + ", " + max + "].");
		}
	}

	public static double readDouble(String prompt) {
		while (true) {
			System.out.print(prompt);
			try {
				//parse so thuc, neu loi thi bat ngoai le va nhap lai.
				return Double.parseDouble(SCANNER.nextLine().trim());
			} catch (NumberFormatException ex) {
				System.out.println("Gia tri khong hop le. Vui long nhap so thuc.");
			}
		}
	}

	public static boolean readYesNo(String prompt) {
		while (true) {
			System.out.print(prompt + " (y/n): ");
			String input = SCANNER.nextLine().trim().toLowerCase();
			if (input.equals("y") || input.equals("yes")) {
				return true;
			}
			if (input.equals("n") || input.equals("no")) {
				return false;
			}
			System.out.println("Vui long nhap 'y' hoac 'n'.");
		}
	}
}
