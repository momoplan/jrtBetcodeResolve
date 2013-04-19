package com.jrt.betcodeResolve.ssqUtil;

import junit.framework.Assert;

import org.junit.Test;

import com.jrt.betcodeResolve.lotnoBetcodeUtil.DLTBetcodeUtil;

/**
 * 
 * 大乐透注码解析 获取金额、注数、玩法的测试类
 * @author
 *		徐丽
 */
public class DLTBetcodeUtilTest {

	@Test
	//单式
	public void getDLTSimplexMoneyTest() {
		int money = DLTBetcodeUtil.getDLTSimplexMoney("1,15,4,5,6+1,7^1,15,4,5,6+1,7^1,15,4,5,6+1,7", 3, "^", false);
		Assert.assertEquals(money, 18);
	}
	
	@Test
	//复式
	public void getDLTDoubTest(){
		int money = DLTBetcodeUtil.getDLTDuplexMoney("1,2,15,4,5,6+1,7,2", 1, "+", ",", true);
		Assert.assertEquals(money, 54);
	}
	@Test
	//复式
	public void getDLTDoubTest1(){
		int money = DLTBetcodeUtil.getDLTDuplexMoney1("1,2,15,4,5,6","1,7,2", 1, ",", true);
		Assert.assertEquals(money, 54);
	}
	
	@Test
	//胆拖
	public void getDLTDantuoMoneyTest(){
		int money = DLTBetcodeUtil.getDLTDantuoMoney("1,2$3,4,5,6,7,8,9+1$2,3", 1, "+", "$", ",", true);
		Assert.assertEquals(money, 210);
	}
	
	@Test
	//胆拖
	public void getDLTDantuoMoneyTest1(){
		int money = DLTBetcodeUtil.getDLTDantuoMoney1("1,2","3,4,5,6,7,8,9","1", "2,3", 1, ",", true);
		Assert.assertEquals(money, 210);
	}
	
	@Test
	//生肖乐
	public void getDLTAnimalHappyTest(){
		int money = DLTBetcodeUtil.getDLTAnimalHappyMoney("1,2", 1, ",");
		Assert.assertEquals(money, 2);
	}
}
