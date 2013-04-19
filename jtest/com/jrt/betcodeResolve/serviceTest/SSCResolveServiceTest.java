package com.jrt.betcodeResolve.serviceTest;

import java.util.List;

import org.junit.Test;

import com.jrt.betcodeResolve.bean.BetcodeBean;
import com.jrt.betcodeResolve.service.SSCResolveService;

/**
 * 
 * 时时彩 将所有注码的 玩法、倍数、金额、注码实体bean中
 * 并将实体存入list中的测试类
 * @author
 * 		徐丽
 * 
 */
public class SSCResolveServiceTest {
	/**
	 * 时时彩将所有注码的 玩法、倍数、金额、注码实体bean中
     * 并将实体存入list中的测试方法
	 */
	@Test
	public void getSSCBetcodeListTest(){
		String betcode = "5D1,2,3,5,6;5D4,5,6,7,8;5D2,4,5,6,9;5D2,4,5,6,9;5D2,4,5,6,9;5D2,4,5,6,9;"// 五星单式
			+ "5T1,2,3,5,6;5T4,5,6,7,8;5T2,4,5,6,9;"// 五星通选单式
			+ "3D5,1,8;3D9,5,6;3D6,7,8;3D5,6,9;"// 三星单式
			+ "2D1,8;2D5,6;2D7,8;2D6,9;"// 二星单式
			+ "1D8;1D6;1D8;1D9;"// 一星单式
			+ "DD1,2;DD1,2;"// 大小单双单式
			+ "H25;"//二星直选和值（1-8个）
			+ "S25;"//二星组选和值（1-8个）
			+ "F24,5,6;"//二星组选复式（3-7个）						
			+ "5D1,2,8-0,2,3-1,2,8-0,2,3-3,4,9;"//五星直选复式
			+ "5T1,2,8-0,2,3-1,2,8-0,2,3-3,4,9;"// 五星通选复式
			+ "DD1,2,4-1,2;"// 大小单双
			+ "3D1,2,5-1,3-8,4;"// 三星直选复式
			+ "2D1,2,5-1,3;"// 二星直选复式
			+ "1D1,2,5;"// 一星直选复式
			;
		List<BetcodeBean> list = SSCResolveService.getSSCBetcodeList(betcode, 1, ";", ",", "-");
		for(int i=0;i<list.size();i++){
			BetcodeBean betcodeBean = (BetcodeBean)list.get(i);
			System.out.println("SSC实体内容:(玩法:"+betcodeBean.getGameMethod()
					+";注码:"+betcodeBean.getBetcode()
					+";注数:"+betcodeBean.getZhushu()+"注"
					+";总金额:"+betcodeBean.getTotalMoney()+"元="
					+betcodeBean.getTotalMoneyFen()+"分)");
			
			
		}
	}
}
