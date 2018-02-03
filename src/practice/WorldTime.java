package practice;

import java.util.InputMismatchException;
import java.util.Scanner;

public class WorldTime {
    public static void main(String args[] ) {
    	int n = 0; //都市の総数（入力）
    	int maxCityCount = 100; //最大都市数
    	int int_hh = 0; //投稿者時刻のHH(int)
    	int int_mm = 0; //投稿者時刻のMM(int)
    	int s = 0; //時差
    	String hh = ""; //投稿者時刻のHH
    	String colon = "";
    	String mm = ""; //投稿者時刻のMM

        Scanner sc = new Scanner(System.in); //入力

        try {
        	n = sc.nextInt();
        	if (n < 1 || n > maxCityCount) {
        		throw new InputMismatchException();
        	}
        } catch (InputMismatchException e){
        	System.out.println("都市の総数は1～" + maxCityCount + "です：" + e);
        	return;
        }

        String city[] = new String[maxCityCount];
        int timeDiff[] = new int[maxCityCount];
        for (int i = 0; i < n; i++) {
            String p = sc.next(); //都市名
            int p_length= p.length();
           	if (p_length < 1 || p_length > 20 || !(p.matches("[a-z]+"))) {
            	System.out.println("都市名は英字小文字で1～20文字です（ユーザ）：" + p);
            	return;
            }
            city[i] = p;

            try {
            	s = sc.nextInt();
            	if (s < -12 || s > 14) {
            		throw new InputMismatchException();
            	}
            } catch (InputMismatchException e){
            	System.out.println("時差は-12～14です：" + e);
            	return;
            }
            timeDiff[i] = s;
        }

        String q = sc.next(); //投稿者の都市名
        int q_length= q.length();
       	if (q_length < 1 || q_length > 20 || !(q.matches("[a-z]+"))) {
        	System.out.println("都市名は英字小文字で1～20文字です（投稿者）：" + q);
        	return;
        }

       	//投稿者の都市が各ユーザの都市に存在するかチェック
       	int cityCount = 0;
       	for (cityCount = 0; cityCount < n; cityCount++) {
       		if (city[cityCount].equals(q)) {
       			break;
       		}
       	}
       	if (cityCount >= n) {
       		System.out.println("投稿者の都市名は各ユーザの都市名に存在するものにしてください" + q);
       		return;
       	}

        String t = sc.next(); //投稿者の時刻
        try {
        	hh = t.substring(0,2); //時刻のHH
        	colon = t.substring(2,3);
        	mm = t.substring(3); //時刻のMM
        } catch (StringIndexOutOfBoundsException e) {
        	System.out.println("時刻はHH:MMで入力してください：" + e);
        	return;
        }

        try {
        	int_hh = Integer.parseInt(hh);
        	if (int_hh < 0 || int_hh > 23) {
        		throw new NumberFormatException();
        	}
        } catch (NumberFormatException e){
        	System.out.println("時(HH)は00～23までを入力してください：" + e);
        	return;
        }

        if (!(colon.equals(":"))) {
        	System.out.println("時(HH)と分(MM)は\":\"で区切ってください：" + colon);
        	return;
        }

        try {
        	int_mm = Integer.parseInt(mm);
        	if (int_mm < 0 || int_mm > 59) {
        		throw new NumberFormatException();
        	}
        } catch (NumberFormatException e){
        	System.out.println("分(MM)は00～59までを入力してください：" + e);
        	return;
        }
        
        System.out.println();
        String out_hh = "";
        int q_timeDiff = timeDiff[cityCount]; //投稿者の時差
        int[] localTime = new int[maxCityCount]; //現地時間
        for (int i = 0; i < n; i++) {
        	localTime[i] = int_hh + timeDiff[i] - q_timeDiff;
        	//24時間制の対応
        	if (localTime[i] >= 24) {
        		localTime[i] = localTime[i] - 24;
        	} else 	if (localTime[i] < 0) {
        		localTime[i] = localTime[i] + 24;
        	}
        	out_hh = String.format("%02d", localTime[i]);
        	System.out.println(city[i] + " " + out_hh + ":" + mm);
        }
    }
}
