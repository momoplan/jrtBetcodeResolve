package com.jrt.betcodeResolve.serviceTest;

import java.util.List;

import org.junit.Test;

import com.jrt.betcodeResolve.bean.BetcodeBean;
import com.jrt.betcodeResolve.service.SYXWResolveService;

public class SYXWResolveServiceTest {
	@Test
    public void getSYXWBetcodeListTest(){
	    //任选1-8
		String betcode = "R11;R11;R11;R11;R11;R11;R11;" +
				"R11,2,3,4,5,6;" +
		"R21,2;R21,2;R21,2;R21,10;" +
		"R21,2,3,4;" +
		"R31,2,3;R31,2,3;R31,2,3;R31,2,3;R31,2,3;" +
		"R31,2,3;" +
		"R41,2,3,4;R41,2,3,4,5,6,7;" +
		"R51,2,3,4,5;R51,2,3,4,5,6,9,10;" +
		"R61,2,3,4,5,6;R61,2,3,4,5,6,7,8;" +
		"R71,2,3,4,5,6,7;R71,2,3,4,5,6,7,8;" +
		"R81,2,3,4,5,6,7,8;R81,2,3,4,5,6,7,8;" +
		//前二和前三组选
		"Z24,10;Z24,10;Z24,10;Z24,10;Z24,10;" +
		"Z21,2,3,4,5,6,7,8;" +
		"Z31,7,10;Z31,7,10;Z31,7,10;Z31,7,10;" +
		"Z31,2,3,4,5,6,7,8,9;"+
		//胆拖
		"R21$5,8,10;R31$5,8,10;" +
		"R41,2$5,6,7,8,10;R51,2$5,6,7,8,10;" +
		"R61,2$5,6,7,8,10;R71,2$5,6,7,8,10,11;" +
		"R81,2,3,5,6,7,8$10,11;" +
		"Z21$5,8,10;Z31$5,6,7,8,10;"+
		//前二和前三直选
		"Q22-8;Q22-8;Q22-8;Q22-8;Q22-8;Q22-8;" +
		"Q21,2,3,4,5-8,9;" +
		"Q36-9-10;Q36-9-10;Q36-9-10;Q36-9-10;Q36-9-10;" +
		"Q31,4,6-5,8,9-7,10,11;"+
		
		//前二和前三直选重复号码
		"Q21,5,6-5,8,9;"+
		"Q31,5,6-5,8,9-7,10,11;"
		;
		List<BetcodeBean> list = SYXWResolveService.getSYXWBetcodeList(betcode, 1, ";", "-", ",", "$");
		for(int i=0;i<list.size();i++){
			BetcodeBean betcodeBean = list.get(i);
			System.out.println("(玩法:"+betcodeBean.getGameMethod()
					   +";注码:"+betcodeBean.getBetcode()
					   +";注数:"+betcodeBean.getZhushu()+"注"
					   +";金额:"+betcodeBean.getTotalMoney()+"元="+betcodeBean.getTotalMoneyFen()+"分)");
		}
  }
}
