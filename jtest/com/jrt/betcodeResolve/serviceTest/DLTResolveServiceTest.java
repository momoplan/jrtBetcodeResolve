package com.jrt.betcodeResolve.serviceTest;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.jrt.betcodeResolve.bean.BetcodeBean;
import com.jrt.betcodeResolve.service.DLTResolveService;

/**
 * 
 * 大乐透 将所有注码的 玩法、倍数、金额、注码实体bean中
 * 并将实体存入list中的测试类
 * @author
 * 		徐丽
 * 
 */
public class DLTResolveServiceTest {
	
	private static final Logger logger = Logger.getLogger(DLTResolveServiceTest.class);
	
	/**
	 * 大乐透 将所有注码的 玩法、倍数、金额、注码实体bean中
     * 并将实体存入list中的测试方法
	 */
	@Test
	public void getDLTBetcodeListTest(){
		String betcode =  "1,15,4,5,6-1,7;1,15,4,5,6-1,7;1,15,4,5,6-1,7;" 
			+"1,15,4,5,6-1,7;1,15,4,5,6-1,7;1,15,4,5,6-1,7;" //单式
			+ "1,2,15,4,5,6-1,7,2;" //复式
			+ "1,2$3,4,5,6,7,8,9-1$2,3;" //胆拖
			+ "1,2,3,4$15,16-$4,10;" //胆拖
			+ "1,2;"//十二选二单式
			+ "1,2,3,4;"//十二选二复式
			+ "3,4,5,6$28,34-10$7,9;22,28,30,32,34-4,7;"
			;
		List<BetcodeBean>  list = DLTResolveService.getDLTBetcodeList(betcode, 1, ";", "-", ",", "$", false);
		for(BetcodeBean betcodeBean : list){
			//BetcodeBean betcodeBean = list.get(i);
			logger.info("大乐透实体内容:(玩法:"+betcodeBean.getGameMethod()
					   +";注码:"+betcodeBean.getBetcode()
					   +"注数:"+betcodeBean.getZhushu()+"注"
					   +";金额:"+betcodeBean.getTotalMoney()+"元="+betcodeBean.getTotalMoneyFen()+"分)");
		}
	}
}
