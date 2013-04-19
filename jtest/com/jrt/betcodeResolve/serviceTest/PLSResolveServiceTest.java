package com.jrt.betcodeResolve.serviceTest;

import java.util.List;

import org.junit.Test;

import com.jrt.betcodeResolve.bean.BetcodeBean;
import com.jrt.betcodeResolve.service.PLSResolveService;

/**
 * 
 * 排列三 将所有注码的 玩法、倍数、金额、注码实体bean中
 * 并将实体存入list中的测试类
 * @author
 * 		徐丽
 * 
 */
public class PLSResolveServiceTest {
	/**
	 * 排列三将所有注码的 玩法、倍数、金额、注码实体bean中
     * 并将实体存入list中的测试方法
	 */
	@Test
	public void getPLSBetcodeList(){
		String betcode = "011,3,2;011,2,2;011,3,2;011,2,2;011,3,2;011,2,2;"//直选单式 
			+"011,7,8,9-1,7,8,9-1,8,9;"//直选复式
			+ "061,3,3;061,2,2;061,2,2;064,5,6;067,9,8;" //组选单式
			+ "S120;" //直选和值 
			+ "S920;" //组选和值
			+ "S320;" //组三和值
			+ "S620;" //组六和值
			+ "F32,3,8,4,9,6,7;"//组三包号
			+ "F62,3,8,4,9,6,7;"//组六包号
			;
		List<BetcodeBean> list = PLSResolveService.getPLSBetcodeList(betcode, 1, ";", ",","-");
		
		for(int i=0;i<list.size();i++){
			BetcodeBean betcodeBean = (BetcodeBean)list.get(i);
			System.out.println("PLS实体内容:(玩法:"+betcodeBean.getGameMethod()
					+";注码:"+betcodeBean.getBetcode()
					+";注数:"+betcodeBean.getZhushu()+"注"
					+";总金额:"+betcodeBean.getTotalMoney()+"元="
					+betcodeBean.getTotalMoneyFen()+"分)");
			
			
		}
	}
	
}
