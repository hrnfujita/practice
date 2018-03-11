package practice;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class WorldTime {
	public static void main(String args[]) {
		int inputCityNumber = 0; //都市の総数（入力）
		int maxCityCount = 100; //最大都市数
		int writerIntHH = 0; //投稿者時刻のHH(int)
		int writerIntMM = 0; //投稿者時刻のMM(int)
		int timeDiff = 0; //時差
		String writerHH = ""; //投稿者時刻のHH
		String writerColon = "";
		String writerMM = ""; //投稿者時刻のMM

		Scanner scanner = new Scanner(System.in); //入力

		try {
			inputCityNumber = scanner.nextInt();
		} catch (InputMismatchException e) {
			System.out.println("都市の総数は数値を入力してください：" + e);
			return;
		}

		if (inputCityNumber < 1 || inputCityNumber > maxCityCount) {
			System.out.println("都市の総数は1～" + maxCityCount + "を入力してください");
			return;
		}

		String cityArray[] = new String[maxCityCount];
		int timeDiffArray[] = new int[maxCityCount];
		for (int i = 0; i < inputCityNumber; i++) {
			String cityName = scanner.next(); //都市名
			int cityNameLength = cityName.length();
			if (cityNameLength < 1 || cityNameLength > 20 || !(cityName.matches("[a-z]+"))) {
				System.out.println("都市名（ユーザ）は英字小文字で1～20文字を入力してください：" + cityName);
				return;
			}
			cityArray[i] = cityName;

			try {
				timeDiff = scanner.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("時差は数値を入力してください：" + e);
				return;
			}

			if (timeDiff < -12 || timeDiff > 14) {
				System.out.println("時差は-12～14を入力してください");
				return;
			}
			timeDiffArray[i] = timeDiff;
		}

		String writerCityName = scanner.next(); //投稿者の都市名
		int writerCityNameLength = writerCityName.length();
		if (writerCityNameLength < 1 || writerCityNameLength > 20 || !(writerCityName.matches("[a-z]+"))) {
			System.out.println("都市名（投稿者）は英字小文字で1～20文字を入力してください：" + writerCityName);
			return;
		}

		//投稿者の都市が各ユーザの都市に存在するかチェック
		int cityCount = 0;
		for (cityCount = 0; cityCount < inputCityNumber; cityCount++) {
			if (cityArray[cityCount].equals(writerCityName)) {
				break;
			}
		}

		if (cityCount >= inputCityNumber) {
			System.out.println("投稿者の都市名は各ユーザの都市名に存在するものにしてください：" + writerCityName);
			return;
		}

		String writerTime = scanner.next(); //投稿者の時刻
		try {
			writerHH = writerTime.substring(0, 2); //時刻のHH
			writerColon = writerTime.substring(2, 3);
			writerMM = writerTime.substring(3); //時刻のMM
		} catch (StringIndexOutOfBoundsException e) {
			System.out.println("時刻はHH:MMで入力してください：" + e);
			return;
		}

		try {
			writerIntHH = Integer.parseInt(writerHH);
		} catch (NumberFormatException e) {
			System.out.println("時(HH)は00～23までの２桁で入力してください");
			return;
		}

		try {
			writerIntMM = Integer.parseInt(writerMM);
		} catch (NumberFormatException e) {
			System.out.println("分(MM)は00～59までの２桁で入力してください");
			return;
		}


		if (!(writerColon.equals(":"))) {
			System.out.println("時(HH)と分(MM)は\":\"で区切ってください：" + writerColon);
			return;
		}

		try {
			LocalTime lt = LocalTime.parse(writerHH + ":" + writerMM);
		} catch (DateTimeParseException e) {
			System.out.println("時(HH)は00～23、分(MM)は00～59までの２桁で入力してください：" + e);
			return;
		}

		scanner.close(); //入力終了

		//出力処理
		System.out.println();
		String outputHH = "";
		int writerTimeDiff = timeDiffArray[cityCount]; //投稿者の時差
		int[] localTime = new int[maxCityCount]; //現地時間

		// 取り扱う日付の形にフォーマット設定
		SimpleDateFormat sdformat = new SimpleDateFormat("HH:mm");

		for (int i = 0; i < inputCityNumber; i++) {
			localTime[i] = writerIntHH + timeDiffArray[i] - writerTimeDiff;
			Date dateTime = null;
			try {
				dateTime = sdformat.parse(localTime[i] + ":" + writerMM);
			} catch (ParseException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			System.out.println(cityArray[i] + " " + sdformat.format(dateTime));
		}
	}
}
