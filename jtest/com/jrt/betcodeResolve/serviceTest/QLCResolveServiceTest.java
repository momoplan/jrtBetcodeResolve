package com.jrt.betcodeResolve.serviceTest;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.jrt.betcodeResolve.bean.BetcodeBean;
import com.jrt.betcodeResolve.service.QLCResolveService;

/**
 * 
 * 七乐彩将所有注码的 玩法、倍数、金额、注码实体bean中
 * 并将实体存入list中的测试类
 * @author
 * 		徐丽
 * 
 */
public class QLCResolveServiceTest {
	private static final Logger logger = Logger.getLogger(QLCResolveServiceTest.class);
	
	/**
	 * 七乐彩 将所有注码的 玩法、倍数、金额、注码实体bean中
     * 并将实体存入list中的测试方法
	 */
	@Test
	public void getQLCBetcodeList(){
	   String betcode = "1,7,2,6,3,4,5^8,13,9,10,11,12,14^"
		      +"15,20,16,17,18,19,21^22,24,23,25,26,27,28^"
		      +"32,29,30,31,33,34,35^22,24,23,25,26,27,28^"//单式
		      +"1,7,2,6,3,4,5,8,9,10^"//复式
		      +"1,3,2*4,6,8,7,5,9,10^";//胆拖
	   List<BetcodeBean> list = QLCResolveService.getQLCBetcodeList(betcode, 1, "^", ",", "*");
	   for(int i=0;i<list.size();i++){
		   BetcodeBean betcodeBean = list.get(i);
		   logger.info("七乐彩实体内容:(玩法:"+betcodeBean.getGameMethod()
				   +";注码:"+betcodeBean.getBetcode()
				   +";注数:"+betcodeBean.getZhushu()+"注"
				   +";金额:"+betcodeBean.getTotalMoney()+"元="+betcodeBean.getTotalMoneyFen()+"分)");
	   }
	}
  
}
