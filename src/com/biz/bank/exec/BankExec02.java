package com.biz.bank.exec;

import com.biz.bank.service.BankService;

public class BankExec02 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String readFile = "src/com/biz/bank/BankBalance.txt";
		BankService bs = new BankService(readFile);
		bs.bankMenu();
		
	}

}
