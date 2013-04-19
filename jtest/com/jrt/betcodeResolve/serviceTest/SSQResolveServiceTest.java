package com.jrt.betcodeResolve.serviceTest;

import java.util.List;

import org.junit.Test;

import com.jrt.betcodeResolve.bean.BetcodeBean;
import com.jrt.betcodeResolve.service.SSQResolveService;

/**
 * 
 * 双色球 将所有注码的 玩法、倍数、金额、注码实体bean中
 * 并将实体存入list中的测试类
 * @author
 * 		徐丽
 * 
 */
public class SSQResolveServiceTest {

	/**
	 * 双色球将所有注码的 玩法、倍数、金额、注码实体bean中
     * 并将实体存入list中的测试方法
	 */
	@Test
	public void getSSQBetcodeListTest(){
		String betcode = "7,10,8,12,9,11~3^1,6,15,26,32,2~4^1,6,15,26,32,2~5^1,6,15,26,32,2~6^7,10,8,12,9,11~7^" 
			+ "1,6,15,26,32,2~8^1,6,15,26,32,2~9^1,6,15,26,32,2~10^1,6,15,26,32,2~10^" //单式-红单蓝单
			+ "1,6,2,5,3,4,9,7,8,10~1^" //红复蓝单
			+ "11,2,6,3,4,5~1,3,2^" //红单蓝复
			+ "1,5,2,3,9,4,6,7~1,3,2^"//红复蓝复
			+ "1,4,2,3,6*5,9~1^" //红胆拖蓝单
			+ "1,2,4,3*5,7,9,6~1,3,2^"//红胆拖蓝复
			;
//		String code = "01,03,04,12,21,27+07^01,03,04,05,06*19,20+16^";
//		String betcode = BetcodeResolveUtil.outZero(code, ",");
		
		List<BetcodeBean> list = SSQResolveService.getSSQBetcodeList(betcode, 1, "^", "~", ",", "*");
		System.out.println(list.size());
		
		for(int i=0; i<list.size(); i++){
			BetcodeBean betcodeBean = list.get(i);
			System.out.println("双色球实体内容:(注码:"+betcodeBean.getBetcode()+";注数:"+betcodeBean.getZhushu()
						+";倍数:"+betcodeBean.getMultiple()+";玩法:"+betcodeBean.getGameMethod()
						+";金额:"+betcodeBean.getTotalMoney()+"元="+betcodeBean.getTotalMoneyFen()+"分)");
		}
	}
	
	
}
