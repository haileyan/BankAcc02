package com.biz.bank.exec;

import java.util.Scanner;

import com.biz.bank.service.BankService;

public class BankExec01 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String readFile = "src/com/biz/bank/BankBalance.txt";
		BankService bs = new BankService(readFile);
		bs.readFile();
		
	
		
		
	}

}
