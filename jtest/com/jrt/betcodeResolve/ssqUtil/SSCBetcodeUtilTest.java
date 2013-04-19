package com.jrt.betcodeResolve.ssqUtil;

import junit.framework.Assert;

import org.junit.Test;

import com.jrt.betcodeResolve.lotnoBetcodeUtil.SSCBetcodeUtil;

/**
 * 
 * 时时彩注码解析测试类
 * 
 * @author 徐丽
 * 
 */
public class SSCBetcodeUtilTest {
	/**
	 * 单式玩法算多注测试
	 */
	@Test
	public void getSSCSimplexZhushuTest() {
		String betcode = "1,2,3,5,6;4,5,6,7,8;2,4,5,6,9";// 五星
		int zhushu = SSCBetcodeUtil.getSSCSimplexZhushu(betcode, ";");
		Assert.assertEquals(3, zhushu);

		betcode = "5,1,8;9,5,6;6,7,8;5,6,9";// 三星
		zhushu = SSCBetcodeUtil.getSSCSimplexZhushu(betcode, ";");
		Assert.assertEquals(4, zhushu);

		betcode = "1,8;5,6;7,8;6,9";// 二星
		zhushu = SSCBetcodeUtil.getSSCSimplexZhushu(betcode, ";");
		Assert.assertEquals(4, zhushu);

		betcode = "8;6;8;9";// 一星
		zhushu = SSCBetcodeUtil.getSSCSimplexZhushu(betcode, ";");
		Assert.assertEquals(4, zhushu);
	}

	/**
	 * 单式玩法算多注得金额测试
	 */
	@Test
	public void getSSCSimplexMoneyTest() {
		String betcode = "1,2,3,5,6;4,5,6,7,8;2,4,5,6,9";// 五星
		int money = SSCBetcodeUtil.getSSCSimplexMoney(betcode, 1, ";");
		Assert.assertEquals(6, money);

		betcode = "5,1,8;9,5,6;6,7,8;5,6,9";// 三星
		money = SSCBetcodeUtil.getSSCSimplexMoney(betcode, 1, ";");
		Assert.assertEquals(8, money);

		betcode = "1,8;5,6;7,8;6,9";// 二星
		money = SSCBetcodeUtil.getSSCSimplexMoney(betcode, 1, ";");
		Assert.assertEquals(8, money);

		betcode = "8;6;8;9";// 一星
		money = SSCBetcodeUtil.getSSCSimplexMoney(betcode, 1, ";");
		Assert.assertEquals(8, money);
	}

	/**
	 * 直选复式算注数测试
	 */
	@Test
	public void getSSCDirectDoubleZhushuTest() {
		String betcode = "1,2,8-0,2,3-1,2,8-0,2,3-3,4,9";// 五星
		int zhushu = SSCBetcodeUtil.getSSCDirectDoubleZhushu(betcode, "-", ",");
		Assert.assertEquals(243, zhushu);

		betcode = "1,2,5-1,3-8,4";// 三星
		zhushu = SSCBetcodeUtil.getSSCDirectDoubleZhushu(betcode, "-", ",");
		Assert.assertEquals(12, zhushu);

		betcode = "1,2,5-1,3";// 二星
		zhushu = SSCBetcodeUtil.getSSCDirectDoubleZhushu(betcode, "-", ",");
		Assert.assertEquals(6, zhushu);

		betcode = "1,2,5";// 一星
		zhushu = SSCBetcodeUtil.getSSCDirectDoubleZhushu(betcode, "-", ",");
		Assert.assertEquals(3, zhushu);
		
		betcode = "1,2,4-1,2";// 大小单双
		zhushu = SSCBetcodeUtil.getSSCDirectDoubleZhushu(betcode,"-", ",");
		Assert.assertEquals(6, zhushu);
	}
	
	/**
	 * 直选复式算金额测试
	 */
	@Test
	public void getSSCDirectDoubleMoneyTest() {
		String betcode = "1,2,8-0,2,3-1,2,8-0,2,3-3,4,9";// 五星
		int zhushu = SSCBetcodeUtil.getSSCDirectDoubleMoney(betcode, 1, "-", ",");
		Assert.assertEquals(486, zhushu);

		betcode = "1,2,5-1,3-8,4";// 三星
		zhushu = SSCBetcodeUtil.getSSCDirectDoubleMoney(betcode, 1, "-", ",");
		Assert.assertEquals(24, zhushu);

		betcode = "1,2,5-1,3";// 二星
		zhushu = SSCBetcodeUtil.getSSCDirectDoubleMoney(betcode, 1, "-", ",");
		Assert.assertEquals(12, zhushu);

		betcode = "1,2,5";// 一星
		zhushu = SSCBetcodeUtil.getSSCDirectDoubleMoney(betcode, 1, "-", ",");
		Assert.assertEquals(6, zhushu);
		
		betcode = "1,2,4-1,2";// 大小单双
		zhushu = SSCBetcodeUtil.getSSCDirectDoubleMoney(betcode, 1, "-", ",");
		Assert.assertEquals(12, zhushu);
	}
	
	/**
	 * 时时彩二星直选和值算注数测试
	 */
	@Test
	public void getSSCRXHezhiZhushuTest(){
		String betcode = "9,1,2";
		int zhushu = SSCBetcodeUtil.getSSCRXHezhiZhushu(betcode,",");
		Assert.assertEquals(15, zhushu);
		
		betcode = "9";
		zhushu = SSCBetcodeUtil.getSSCRXHezhiZhushu(betcode,",");
		Assert.assertEquals(10, zhushu);
	}
	
	/**
	 * 时时彩二星直选和值算金额测试SSCRXZXHezhi
	 */
	@Test
	public void getSSCRXHezhiMoneyTest(){
		String betcode = "9,1,2";
		int money = SSCBetcodeUtil.getSSCRXHezhiMoney(betcode,",", 1);
		Assert.assertEquals(30, money);
		
		betcode = "9";
		 money = SSCBetcodeUtil.getSSCRXHezhiMoney(betcode,",", 1);
		Assert.assertEquals(20, money);
	}
	
	/**
	 * 时时彩二星组选和值算注数测试
	 */
	@Test
	public void getSSCRXZXHezhiZhushuTest(){
		String betcode = "2";
		int zhushu = SSCBetcodeUtil.getSSCRXZXHezhiZhushu(betcode,",");
		Assert.assertEquals(2, zhushu);
		
		betcode = "1,2,6";
		zhushu = SSCBetcodeUtil.getSSCRXZXHezhiZhushu(betcode,",");
		Assert.assertEquals(7, zhushu);
	}
	
	/**
	 * 时时彩二星组选和值算金额测试
	 */
	@Test
	public void getSSCRXZXHezhiMoneyTest(){
		String betcode = "2";
		int money = SSCBetcodeUtil.getSSCRXZXHezhiMoney(betcode,",", 1);
		Assert.assertEquals(4, money);
		
		betcode = "1,2,6";
		money = SSCBetcodeUtil.getSSCRXZXHezhiMoney(betcode,",", 1);
		Assert.assertEquals(14, money);
	}
	
	/**
	 * 时时彩二星组选复式算注数测试
	 */
	@Test
	public void getSSCRXDirectDoubleZhushuTest(){
		String betcode = "2,3,4,0,9";
		int zhushu = SSCBetcodeUtil.getSSCRXDirectDoubleZhushu(betcode, ",");
		Assert.assertEquals(10, zhushu);
	}
	
	/**
	 * 时时彩二星组选复式算金额测试
	 */
	public void getSSCRXDirectDoubleMoneyTest(){
		String betcode = "2,3,4,0,9";
		int money = SSCBetcodeUtil.getSSCRXDirectDoubleMoney(betcode,1, ",");
		Assert.assertEquals(20, money);
	}
}
