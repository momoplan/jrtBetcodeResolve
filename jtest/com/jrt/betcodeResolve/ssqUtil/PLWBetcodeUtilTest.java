package com.jrt.betcodeResolve.ssqUtil;

import junit.framework.Assert;

import org.junit.Test;

import com.jrt.betcodeResolve.lotnoBetcodeUtil.PLWBetcodeUtil;

/**
 * 排列五注码解析 获取金额、注数、玩法的测试类
 * @author 徐丽
 *
 */
public class PLWBetcodeUtilTest {
	/**
	 * 单式算注数和金额测试方法
	 */
	@Test
	public void getPLWSimplexTest(){
		//算注数
		int zhushu = PLWBetcodeUtil.getPLWSimplexZhushu("1,2,3,5,6;4,5,6,7,8;2,4,5,6,9", ";");
		Assert.assertEquals(zhushu, 3);
		//算金额
		int money = PLWBetcodeUtil.getPLWSimplexMoney("1,2,3,5,6;4,5,6,7,8;2,4,5,6,9", 2, ";");
		Assert.assertEquals(money, 12);
	}
	
	/**
	 * 直选复式算注数和金额的测试方法
	 */
	@Test
	public void getPLWDirectDoubleTest(){
		//算注数
		int zhushu = PLWBetcodeUtil.getPLWDirectDoubleZhushu("1,2-0,2,3-1,5,2,8-0,1,5,2,3-1,2,5,3,4,9", "-", ",");
		Assert.assertEquals(zhushu, 720);
		//算金额
		int money = PLWBetcodeUtil.getPLWDirectDoubleMoney("1,2,8-0,2,3-1,2,8-0,2,3-3,4,9", 1, "-", ",");
		Assert.assertEquals(money, 486);
	}

}
