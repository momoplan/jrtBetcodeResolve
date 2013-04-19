package com.jrt.betcodeResolve.serviceTest;

import java.util.List;

import org.junit.Test;

import com.jrt.betcodeResolve.bean.BetcodeBean;
import com.jrt.betcodeResolve.service.PLWResolveService;

/**
 * 排列五注码解析测试类
 * @author Administrator
 *
 */
public class PLWResolveServiceTest {
	@Test
	public void getPLWBetcodeListTest(){
		 String betcode = "1,2,3,5,6;4,5,6,7,8;2,4,5,6,9;2,4,5,6,9;2,4,5,6,9;2,4,5,6,9;2,4,5,6,9;" +//单式
	  		"1,2,8-0,2,3-1,2,8-0,2,3-3,4,9;1,2,8-0,2,3-1,2,8-0,2,3-3,4,9";	//复式
		 List<BetcodeBean> list = PLWResolveService.getPLWBetcodeList(betcode, 1, ";", ",", "-");
		 for(int i=0;i<list.size();i++){
				BetcodeBean betcodeBean = (BetcodeBean)list.get(i);
				System.out.println("PLW实体内容:(注码:"+betcodeBean.getBetcode()
						+";注数:"+betcodeBean.getZhushu()+"注"
						+";总金额:"+betcodeBean.getTotalMoney()+"元="
						+betcodeBean.getTotalMoneyFen()+"分)");
				
				
			}
	}
}
