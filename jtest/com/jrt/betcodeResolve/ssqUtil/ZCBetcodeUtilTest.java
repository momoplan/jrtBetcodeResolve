package com.jrt.betcodeResolve.ssqUtil;

import org.junit.Assert;
import org.junit.Test;

import com.jrt.betcodeResolve.lotnoBetcodeUtil.ZCBetcodeUtil;

/**
 * 
 * 足彩注码解析 获取金额、注数、玩法的测试类
 * @author
 *		徐丽
 */
public class ZCBetcodeUtilTest {
	@Test
	//足彩单式
	public void getZCSimplexZhushuTest(){
		String betcode ="3,1,0,1,3,1,1,#,1,#,#,1,#,#;3,1,0,1,3,1,1,#,1,#,#,1,#,#";
		//算注数
		int zhushu = ZCBetcodeUtil.getZCSimplexZhushu(betcode, ";");
		Assert.assertEquals(2, zhushu);
		//算金额
		int money = ZCBetcodeUtil.getZCSimplexMoney(betcode, 1, ";");
		Assert.assertEquals(4, money);
	}
	
	@Test
	//足彩复式
	public void getZCDuplexZhushuTest(){
		String betcode ="310,1,0,1,3,1,1,#,1,#,#,1,#,#";
		//算注数
		int zhushu = ZCBetcodeUtil.getZCDuplexZhushu(betcode, ",");
		Assert.assertEquals(3, zhushu);
		//算金额
		int money = ZCBetcodeUtil.getZCDuplexMoney(betcode, 1, ",");
		Assert.assertEquals(6, money);
	}
	
	//足彩任九场胆拖算注数
	@Test
	public void getZCRJCDTZhushuTest(){
		String betcode = "0,#,#,#,#,31,#,#,#,#,#,#,#,#$#,1,0,1,#,#,1,#,3,3,03,#,1,#";
		long zhushu = ZCBetcodeUtil.getZCRJCDTZhushu(betcode, ",", "$", "#");
		Assert.assertEquals(30, zhushu);
		
		betcode ="#,#,#,#,#,#,#,#,#,#,#,#,#,#$31,3,3,3,3,3,3,3,3,3,#,#,#,#";
		zhushu = ZCBetcodeUtil.getZCRJCDTZhushu(betcode, ",", "$", "#");
		Assert.assertEquals(19, zhushu);
		
	    betcode = "#,#,#,#,#,#,#,#,#,#,#,#,#,#$30,3,#,31,30,31,30,10,30,3,31,0,#,3";
		zhushu = ZCBetcodeUtil.getZCRJCDTZhushu(betcode, ",", "$", "#");
		Assert.assertEquals(16128, zhushu);
		
		betcode = "#,#,#,#,#,#,#,#,#,#,#,#,#,#$31,31,310,310,3,3,3,3,3,3,#,#,#,#";
		zhushu = ZCBetcodeUtil.getZCRJCDTZhushu(betcode, ",", "$", "#");
		Assert.assertEquals(276, zhushu);
	}

	//足彩任九场胆拖算金额
	@Test
	public void getZCRJCDTMoneyTest(){
		String betcode = "0,#,#,#,#,31,#,#,#,#,#,#,#,#$#,1,0,1,#,#,1,#,3,3,03,#,1,#";
		long moeny = ZCBetcodeUtil.getZCRJCDTMoney(betcode, 1, ",", "$", "#");
		Assert.assertEquals(60, moeny);
		
		betcode ="#,#,#,#,#,#,#,#,#,#,#,#,#,#$31,3,3,3,3,3,3,3,3,3,#,#,#,#";
		moeny = ZCBetcodeUtil.getZCRJCDTMoney(betcode, 1, ",", "$", "#");
		Assert.assertEquals(38, moeny);
		
		betcode = "#,#,#,#,#,#,#,#,#,#,#,#,#,#$30,3,#,31,30,31,30,10,30,3,31,0,#,3";
		moeny = ZCBetcodeUtil.getZCRJCDTMoney(betcode, 1, ",", "$", "#");
		Assert.assertEquals(32256, moeny);
	}
	
	@Test
	//足彩任九场胆拖
    public void getZCZhushuTest(){
		String betcode ="#,#,#,#,#,#,#,#,#,#,#,#,#,#$31,3,3,3,3,3,3,3,3,3,#,#,#,#";
		System.out.println("注数:"+ZCBetcodeUtil.getRJCDTZhushu(betcode,",","$","#","19"));
	}
}
