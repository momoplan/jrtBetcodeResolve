package com.jrt.betcodeResolve.serviceTest;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.jrt.betcodeResolve.bean.BetcodeBean;
import com.jrt.betcodeResolve.service.SDResolveService;

/**
 * 
 * 福彩3D 将所有注码的 玩法、倍数、金额、注码实体bean中
 * 并将实体存入list中的测试类
 * @author
 * 		徐丽
 * 
 */
public class SDResolveServiceTest {
	private static final Logger logger = Logger.getLogger(SDResolveServiceTest.class);
	/**
	 * 福彩3D将所有注码的 玩法、倍数、金额、注码实体bean中
     * 并将实体存入list中的测试方法
	 */
	@Test
	public void getSDBetcodeListTest(){
		//所有的注码 "302,3,8,4,9,6,7^"//单选全复式
		String betcode = "001,3,2^001,2,2^001,3,2^001,2,2^001,3,2^001,2,2^"//直选单式 
			+ "011,3,2^011,2,2^014,5,6^017,9,8^" //组三单式
			+ "021,2,2^024,5,6^027,9,8^" //组六单式
			+ "1020^" //直选和值 
			+ "1120^" //组三和值
			+ "1220^" //组六和值
			+ "201,4,2,3,5~6,9,7,8,10,0~1,5,2,3,4,6,7^"//直选复式（单选按位包号）
			+ "312,3,8,4,9,6,7^"//组三复式
			+ "322,3,8,4,9,6,7^"//组六复式
			+ "343,6,7,8,4,5^"//单选单复式（直选包号）
			+ "542,1*5,4,3^" //胆拖复式(单选单胆拖)
			;
		List<BetcodeBean> list = SDResolveService.getSDBetcodeList(betcode, 1, "^", "~", ",", "*");
		for(int i=0;i<list.size();i++){
			BetcodeBean betcodeBean = (BetcodeBean)list.get(i);
			logger.info("3D实体内容:(玩法:"+betcodeBean.getGameMethod()
					+";注码:"+betcodeBean.getBetcode()
					+";注数:"+betcodeBean.getZhushu()+"注"
					+";总金额:"+betcodeBean.getTotalMoney()+"元="
					+betcodeBean.getTotalMoneyFen()+"分)");
			
			
		}
	}
	

}
