package com.biz.bank.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.biz.bank.vo.BankVO;

public class BankService {

	List<BankVO> bankList;
	String strFileName;
	
	public BankService(String strFileName) {
		bankList = new ArrayList();
		this.strFileName = strFileName;
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
		Scanner scan = new Scanner(System.in);
		System.out.print("계좌번호를 입력해주세요 >>");
		String strId2 = scan.nextLine();
		BankVO b = findId(strId2);
		if(b == null) {
			System.out.println("계좌번호 없음");
			return;
		}
		
		int iB = b.getIntBalance();
		System.out.println("입금하실 금액을 입력하세요 >>");
		String strB = scan.nextLine();
		int intB = Integer.valueOf(strB);
		int LastB = iB + intB;
		b.setIntBalance(LastB);
		
		
		
		
		}
		
		
	}
	

