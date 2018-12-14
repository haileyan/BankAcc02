package com.biz.bank.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.biz.bank.vo.BankVO;

/*
 * BankService 클래스에서 핵심부분
 * 1. findId() : bankList에서 계좌번호를 조회하는 부분
 * 	가. 매개변수로 strId(String) 값을 받고 
 * 	나. bankList를 순회(반복)하면서 
 * 	다. bankList에 들어있는 vo의 strId값을 추출해서 
 * 		(bankList.get(i).getStrId 또는 vo.getStrId() 이용)
 * 	라. 매개변수로 받은 strId와 일치하는 값이 있는지 검사를 한다.
 * 		if(vo.getStrId().equals(strId)), if(strId.equals(vo.getStrid())
 * 
 * 	마. 만약 bankList에 찾고자 하는 id가 없을 경우
 * 		if(vo.getStrId().equals(strId) == false)
 * 		또는 if(!vo.getStrId().equals(strId))
 * 
 *	바. findId()는 null을 return해서 값이 없음을 알리고
 *	
 *	사. 만약 bankList에 찾고자 하는 id가 있으면 
 *	아. findId()는 찾은 vo를 return 해준다.
 */

public class BankService {

	List<BankVO> bankList;
	String strFileName;
	Scanner scan;
	String ioFolder;
	int intPlus;
	
	public BankService(String strFileName) {
		bankList = new ArrayList();
		this.strFileName = strFileName;
		this.ioFolder = "src/com/biz/bank/iolist/";
		scan = new Scanner(System.in);
		this.intPlus = 0;
	}
	
	//	입출금 내역을 저장하는 method
	public void bankIoWrite(BankVO v, int intB, int intPlus) {
		FileWriter fr;
		PrintWriter pw;
		String thisId = v.getStrId(); // 계좌번호
		
		try {
			// 2번째 매개변수 true : 파일을 Append Mode로 열어라
			fr = new FileWriter(ioFolder + thisId,true);
			pw = new PrintWriter(fr);
			if(intPlus == 1){	
				pw.printf("%s:%s:%d:%s:%d\n", v.getStrLastDate(), "입금", 
							intB, v.getStrId(), v.getIntBalance() );
			} else {
				pw.printf("%s:%s:%d:%s:%d\n", v.getStrLastDate(), "출금", 
						intB, v.getStrId(), v.getIntBalance() );
			}
			pw.close();
			fr.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void readFile() {
		FileReader fr;
		BufferedReader buffer;
		
		try {
			fr = new FileReader(strFileName);
			buffer = new BufferedReader(fr);
			while(true) {
				String read = buffer.readLine();
				if(read == null) break;
				String[] readLine = read.split(":");
				BankVO vo = new BankVO();
				vo.setStrId(readLine[0]);
				vo.setIntBalance(Integer.valueOf(readLine[1]));
				vo.setStrLastDate(readLine[2]);
				System.out.printf("%s-%d-%s\n", 
						vo.getStrId(), vo.getIntBalance(), vo.getStrLastDate());
				bankList.add(vo);
			}
			buffer.close();
			fr.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	public BankVO findId(String strId) {
		for(BankVO vo:bankList) {
			if(vo.getStrId().equals(strId)) {
				return vo;
			} 
		} 
			return null;
	} 
	
	public void bankInPut() {
		System.out.print("계좌번호를 입력해주세요 >>");
		String strId2 = scan.nextLine();
		BankVO b = findId(strId2);
		if(b == null) {
			System.out.println("계좌번호 없음");
			return;
		}
		
		int iB = b.getIntBalance();
		System.out.print("입금하실 금액을 입력하세요 >>");
		String strB = scan.nextLine();
		int intB = Integer.valueOf(strB);
		int LastB = iB + intB;
		b.setIntBalance(LastB);
		System.out.println("입금이 완료되었습니다");
		intPlus = 1;
		this.bankIoWrite(b, intB, intPlus);
		}
	
	public void bankOutPut() {
		System.out.print("계좌 번호를 입력하세요 >>");
		String strId3 = scan.nextLine();
		BankVO vo = findId(strId3);
		for(BankVO v : bankList) {
			if(v == null) {
				System.out.println("입력하신 계좌번호를 찾을 수 없습니다");
				break;
			}
		}
			System.out.print("출금하실 금액을 입력해주세요 >>");
			int intBalance = vo.getIntBalance();
			String strAb = scan.nextLine();
			int intAb = Integer.valueOf(strAb);
			if(intAb > intBalance) {
				 System.out.println("잔액 부족 출금불가");
			}
			intBalance = intBalance - intAb;
			vo.setIntBalance(intBalance);
			System.out.println("출금이 완료 되었습니다");
			intPlus = 0;
			this.bankIoWrite(vo, intAb, intPlus);
	}
		
	public void bankMenu() {
		this.readFile();
		while(true) {
			System.out.println("============================================");
			System.out.println("이용하실 메뉴를 선택하세요");
			System.out.print("01.입금/02.출금/03.계좌조회 /0.종료 >>");
			String strChoice = scan.nextLine();
			int intChoice = Integer.valueOf(strChoice);
			if (intChoice == 00){
				System.out.println("이용해주셔서 감사합니다");
				break;
			}
			if(intChoice == 01) {
				this.bankInPut();
			} if(intChoice == 02) {
				this.bankOutPut();
			} 
		}
		
	}
	
	}
	

