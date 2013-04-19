package com.jrt.betcodeResolve.ssqUtil;

import junit.framework.Assert;

import org.junit.Test;

import com.jrt.betcodeResolve.lotnoBetcodeUtil.QXCBetcodeUtil;
/**
 * 七星彩注码解析 获取金额、注数、玩法的测试类
 * @author 徐丽
 *
 */
public class QXCBetcodeUtilTest {
	
	/**
	 * 单式算注数和金额测试方法
	 */
	@Test
	public void getQXCSimplexTest(){
		String betcode = "1,2,3,5,6,3,4;4,5,6,7,8,5,6;2,4,5,6,9,5,6";
		int zhushu =  QXCBetcodeUtil.getQXCSimplexZhushu(betcode, ";");
		Assert.assertEquals(3, zhushu);
		
		int money = QXCBetcodeUtil.getQXCSimplexMoney(betcode, 1, ";");
		Assert.assertEquals(6, money);
	}
	
	/**
	 * 复式算注数和金额的测试方法 
	 */
	@Test
	public void getQXCDirectDoubleTest(){
		String betcode = "1,2-3-2,1,2-5,0-3-7,8,2,4-6,8,3,4,9,0";
		int zhushu = QXCBetcodeUtil.getQXCDirectDoubleZhushu(betcode, "-", ",");
		Assert.assertEquals(288, zhushu);
		
		int money = QXCBetcodeUtil.getQXCDirectDoubleMoney(betcode, 1, "-", ",");
		Assert.assertEquals(576, money);
	}
	
	
}
