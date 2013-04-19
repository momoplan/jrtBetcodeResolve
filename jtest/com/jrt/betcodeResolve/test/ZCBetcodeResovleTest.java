package com.jrt.betcodeResolve.test;

import junit.framework.Assert;

import org.junit.Test;

import com.jrt.betcodeResolve.resolve.ZCBetcodeResovle;

/**
 * 
 * 足彩注码解析测试类
 * @author
 * 		徐丽
 *
 */
public class ZCBetcodeResovleTest {

	/**
	 * 
	 * 足彩单式和复式注码拼接测试
	 * 
	 */
	@Test
	public void getZCBetcodeTest(){
		
		//胜负彩14场单式
		String betcode = "3,1,0,1,3,1,1,3,1,3,1,1,1,3";
		Assert.assertEquals("3,1,0,1,3,1,1,3,1,3,1,1,1,3", ZCBetcodeResovle.getZCBetcode(betcode, ","));
		
		//胜负彩14场复式
		betcode = "31,10,0,1,3,1,1,3,1,3,1,1,1,3";
		Assert.assertEquals("31,10,0,1,3,1,1,3,1,3,1,1,1,3", ZCBetcodeResovle.getZCBetcode(betcode, ","));
		
		//任九场单式
		betcode = "3,$,0,1,$,1,$,3,$,3,$,1,1,3";
		Assert.assertEquals("3,#,0,1,#,1,#,3,#,3,#,1,1,3", ZCBetcodeResovle.getZCBetcode(betcode, ",", "$"));
		
		//任九场复式
		betcode = "31,10,$,1,$,1,$,3,$,3,1,$,1,3";
		Assert.assertEquals("31,10,#,1,#,1,#,3,#,3,1,#,1,3", ZCBetcodeResovle.getZCBetcode(betcode, ",", "$"));
		
		//足彩六场半全场单式
		betcode = "3,1,0,1,3,1,1,3,1,3,1,1";
		Assert.assertEquals("3,1,0,1,3,1,1,3,1,3,1,1", ZCBetcodeResovle.getZCBetcode(betcode, ","));
		
		//足彩六场半全场复式
		betcode = "30,1,03,01,3,1,1,3,1,3,1,1";
		Assert.assertEquals("30,1,03,01,3,1,1,3,1,3,1,1", ZCBetcodeResovle.getZCBetcode(betcode, ","));
		
		//足彩进球彩单式
		betcode = "3,1,0,1,3,1,1,3";
		Assert.assertEquals("3,1,0,1,3,1,1,3", ZCBetcodeResovle.getZCBetcode(betcode, ","));
		
		//足彩进球彩复式
		betcode = "30,1,03,01,3,1,1,3";
		Assert.assertEquals("30,1,03,01,3,1,1,3", ZCBetcodeResovle.getZCBetcode(betcode, ","));
	}
}
