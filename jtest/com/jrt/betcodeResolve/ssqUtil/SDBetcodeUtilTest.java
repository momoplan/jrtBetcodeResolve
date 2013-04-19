package com.jrt.betcodeResolve.ssqUtil;

import junit.framework.Assert;

import org.junit.Test;

import com.jrt.betcodeResolve.lotnoBetcodeUtil.SDBetcodeUtil;

/**
 * 
 * 福彩3D注码解析 获取金额、注数、玩法的测试类
 * @author
 *		徐丽
 */
public class SDBetcodeUtilTest {

	@Test
	//直选单式和组选单式
	public void getSDSimplexAndGroupMoneyTest(){
		int money = SDBetcodeUtil.getSDSimplexMoney("1,2,3^1,2,2^4,5,6^7,8,9^0,9,1^", 2,"^");
		Assert.assertEquals(money, 20);
	}
	
	@Test
	//和值
	public void getSDHeZhiTest(){
		int money = 0;
		money = SDBetcodeUtil.getSDHezhiMoney("20", 1, "10");//0-27中选一个和值
		Assert.assertEquals(money, 72);
		money = SDBetcodeUtil.getSDHezhiMoney("20", 1, "11");//1-26中选一个和值
		Assert.assertEquals(money, 8);
		money = SDBetcodeUtil.getSDHezhiMoney("20", 1, "12");//3-24中选一个和值
		Assert.assertEquals(money, 8);
		
	}
	
	@Test
	//单选按位包号(直选复式)
	public void getSDDirectDoubleMoneyTest(){
		int money = SDBetcodeUtil.getSDDirectDoubleMoney("1,4,2,3^6,9^1,5,2,3^", 1, "^", ",");
		Assert.assertEquals(money, 64);
	}
	
	@Test
	public void getSDDirectAndGroupMoneyTest(){
		int money = 0;
		//单选单复式(直选包号)
		money = SDBetcodeUtil.getSDDirectAndGroupMoney("3,4,5,6,7,8", 1, ",","34");
		Assert.assertEquals(money, 240);
		//组三复式
		money = SDBetcodeUtil.getSDDirectAndGroupMoney("3,4,5,6,7,8", 1, ",","31");
		Assert.assertEquals(money, 60);
		//组六复式
		money = SDBetcodeUtil.getSDDirectAndGroupMoney("3,4,5,6,7,8", 1, ",","32");
		Assert.assertEquals(money, 40);
	}
	
	@Test
	public void getSDDantuoMoneyTest(){
		//胆拖复式
		int money = SDBetcodeUtil.getSDDantuoMoney1("3,6", "5,7,9", 1, ",");
		Assert.assertEquals(money, 36);
		
		//胆拖复式
		money = SDBetcodeUtil.getSDDantuoMoney("3,6*5,7,9", 1, "*", ",");
		Assert.assertEquals(money, 36);
	}

}
