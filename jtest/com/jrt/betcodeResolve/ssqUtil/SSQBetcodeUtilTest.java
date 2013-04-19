package com.jrt.betcodeResolve.ssqUtil;

import junit.framework.Assert;

import org.junit.Test;

import com.jrt.betcodeResolve.lotnoBetcodeUtil.SSQBetcodeUtil;

/**
 * 
 * 双色球注码解析 获取金额、注数、玩法的测试类
 * @author
 *		徐丽
 */
public class SSQBetcodeUtilTest {
	@Test
	// 单式
	public void getSimplexSSQMoneyTest() {
		int money = SSQBetcodeUtil.getSSQSimplexMoney(
				"2,1,3,5,4,6+1^7,10,8,12,9,11+2^1,6,15,26,32,2+2^", 2, "^");		
		Assert.assertEquals(money, 12);
	}

	@Test
	// 复式
	public void getSSQDuplexMoneyTest(){//1001*01020304050607080910~01^  
		int money = SSQBetcodeUtil.getSSQDuplexMoney("1,6,10,5,3,4,7+1,2,3", 2, "+", ",");
		Assert.assertEquals(money, 84);
	}
	
	@Test
	// 复式
	public void getSSQDuplexMoneyTest1(){
		int money = SSQBetcodeUtil.getSSQDuplexMoney1("1,6,10,5,3,4,7", "1,2,3", 2, ",");
		Assert.assertEquals(money, 84);
	}
	
	@Test
	//胆拖
	public void getDantuoMoneyTest() {
		int money = SSQBetcodeUtil.getSSQDantuoMoney("10,2,4,3*5,7,9,6+1,3,2", 2, "+", "*", ",");
		Assert.assertEquals(money, 72);
	}
	
	@Test
	//胆拖
	public void getDantuoMoneyTest1(){
		int money = SSQBetcodeUtil.getSSQDantuoMoney1("10,2,4,3", "5,7,9,6", "1,3,2", 2, ",");
		Assert.assertEquals(money, 72);
	}
}
