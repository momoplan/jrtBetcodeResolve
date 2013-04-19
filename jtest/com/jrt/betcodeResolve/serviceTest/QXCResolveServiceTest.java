package com.jrt.betcodeResolve.serviceTest;

import java.util.List;

import org.junit.Test;

import com.jrt.betcodeResolve.bean.BetcodeBean;
import com.jrt.betcodeResolve.service.QXCResolveService;

/**
 * 七星彩注码解析测试类
 * @author Administrator
 *
 */
public class QXCResolveServiceTest {
	@Test
	public void getQXCBetcodeListTest(){
		 String betcode = "1,2,3,5,6,3,4;4,5,6,7,8,5,6;2,4,5,6,9,5,6;2,4,5,6,9,5,6;2,4,5,6,9,5,6;2,4,5,6,9,5,6;" +//单式
	  		"1,2-3-2,1,2-5,0-3-7,8,2,4-6,8,3,4,9,0;1,2-3-2,1,2-5,0-3-7,8,2,4-6,8,3,4,9,0";	//复式
		 List<BetcodeBean> list = QXCResolveService.getQXCBetcodeList(betcode, 1, ";", ",", "-");
		 for(int i=0;i<list.size();i++){
				BetcodeBean betcodeBean = (BetcodeBean)list.get(i);
				System.out.println("(注码:"+betcodeBean.getBetcode()
						+";注数:"+betcodeBean.getZhushu()+"注"
						+";总金额:"+betcodeBean.getTotalMoney()+"元="
						+betcodeBean.getTotalMoneyFen()+"分)");
				
				
			}
	}
}
