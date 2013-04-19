package com.jrt.betcodeResolve.ssqUtil;

import junit.framework.Assert;

import org.junit.Test;

import com.jrt.betcodeResolve.lotnoBetcodeUtil.PLSBetcodeUtil;
/**
 * 
 * 排列三注码解析 获取金额、注数、玩法的测试类
 * @author
 *		徐丽
 */
public class PLSBetcodeUtilTest {
	
	@Test
	//直选单式、组选(包括组三和组六)单式
	public void getPLSSimplexMoneyTest(){
		int money = PLSBetcodeUtil.getPLSSimplexMoney("1,2,3;4,5,6;4,5,6", 2, ";");
		Assert.assertEquals(money, 12);
	}
	
	@Test
	//和值 3-直选和值、4-组选和值、5-组三和值、6-组六和值
	public void getPLSHezhiMoneyTest(){
		int money = 0; 
		money = PLSBetcodeUtil.getPLSHezhiMoney("12", 1, "3");
		Assert.assertEquals(money, 146);
		money = PLSBetcodeUtil.getPLSHezhiMoney("12", 1, "4");
		Assert.assertEquals(money, 28);
		money = PLSBetcodeUtil.getPLSHezhiMoney("12", 1, "5");
		Assert.assertEquals(money, 8);
		money = PLSBetcodeUtil.getPLSHezhiMoney("12", 1, "6");
		Assert.assertEquals(money, 20);
	}
	
	@Test
	//直选复式
	public void getPLSDirectDoubleMoneyTest(){
		int money = 0;
		money = PLSBetcodeUtil.getPLSDirectDoubleMoney("1,2;2,3;3,4", 1, ";", ",");
		Assert.assertEquals(money, 16);
		money = PLSBetcodeUtil.getPLSDirectDoubleMoney1("1,2", "2,3", "3,4", 1, ",");
		Assert.assertEquals(money, 16);
	}
	
	@Test
	//组三包号、组六包号
	public void getPLSDirectAndGroupMoneyTest(){
		int money = 0; 
		money = PLSBetcodeUtil.getPLSDirectAndGroupMoney("1,2,3", 1, ",", "7");
		Assert.assertEquals(money, 12);
		money = PLSBetcodeUtil.getPLSDirectAndGroupMoney("1,2,3,4", 2, ",", "8");
		Assert.assertEquals(money, 16);
	}
	
}
