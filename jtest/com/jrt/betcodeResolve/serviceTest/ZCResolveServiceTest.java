package com.jrt.betcodeResolve.serviceTest;

import java.util.List;

import org.junit.Test;

import com.jrt.betcodeResolve.bean.BetcodeBean;
import com.jrt.betcodeResolve.service.ZCResolveService;

/**
 * 
 * 足彩注码解析的测试类
 * @author 徐丽
 *
 */
public class ZCResolveServiceTest {
	
	@Test
	public void getZCBetcodeListTest(){
		String betcode = "1103,1,0,1,3,1,1,3,1,3,1,1,1,3;1103,1,0,1,3,1,1,3,1,3,1,1,1,3;1103,1,0,1,3,1,1,3,1,3,1,1,1,3;"
			+ "1103,1,0,1,3,1,1,3,1,3,1,1,1,3;1103,1,0,1,3,1,1,3,1,3,1,1,1,3;" 
			+ "1103,1,0,1,3,1,1,3,1,3,1,1,1,3;"//胜负彩14场单式
			+ "11131,10,0,1,3,1,1,3,1,3,1,1,1,3;"//胜负彩14场复式
			+ "1903,#,0,1,#,1,#,3,#,3,#,1,1,3;"//任九场单式
			+ "19131,10,#,1,#,1,#,3,#,3,1,#,1,3;"//任九场复式
			+ "1920,#,#,#,#,31,#,#,#,#,#,#,#,#$#,1,0,1,#,#,1,#,3,3,03,#,1,#;"//任九场胆拖
			+ "1603,1,0,1,3,1,1,3,1,3,1,1;"//足彩六场半全场单式
			+ "16130,1,03,01,3,1,1,3,1,3,1,1;"//足彩六场半全场复式
			+ "1803,1,0,1,3,1,1,3;"//足彩进球彩单式
			+ "18130,1,03,01,3,1,1,3;"//足彩进球彩复式
			;
		List<BetcodeBean> list = ZCResolveService.getZCBetcodeList(betcode, 1, ";", ",", "$", "#");
		for(int i=0;i<list.size();i++){
			BetcodeBean betcodeBean = (BetcodeBean)list.get(i);
			System.out.println("(玩法:"+betcodeBean.getGameMethod()
					+";彩种:"+betcodeBean.getLotno()
					+";注码:"+betcodeBean.getBetcode()
					+";注数:"+betcodeBean.getZhushu()+"注"
					+";总金额:"+betcodeBean.getTotalMoney()+"元="
					+betcodeBean.getTotalMoneyFen()+"分)");
			
			
		}
	}
}
