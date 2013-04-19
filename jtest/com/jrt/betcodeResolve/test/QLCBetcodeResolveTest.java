package com.jrt.betcodeResolve.test;

import junit.framework.Assert;

import org.junit.Test;

import com.jrt.betcodeResolve.resolve.QLCBetcodeResolve;

/**
 * 
 * 七乐彩注码解析测试类
 * @author
 * 		徐丽
 *
 */
public class QLCBetcodeResolveTest {

	/**
	 * 七乐彩单式玩法注码拼接测试
	 */
	@Test
	public void getQLCSimplexTest(){
		String betcode = "1,7,2,6,3,4,5^8,13,9,10,11,12,14^15,20,16,17,18,19,21^22,24,23,25,26,27,28^32,29,30,31,33,34,35^";
		String str = QLCBetcodeResolve.getQLCSimplex(2, betcode, "^", ",");
		Assert.assertEquals(str, "000201020304050607^000208091011121314^000215161718192021^000222232425262728^000229303132333435^");
	}
	
	/**
	 * 七乐彩复式玩法注码拼接测试
	 */
	@Test
	public void getQLCDuplexTest(){
		String betcode = "1,7,2,6,3,4,5,8,9,10";
		String str = QLCBetcodeResolve.getQLCDuplex(2, betcode, ",");
		Assert.assertEquals(str,"1002*01020304050607080910^");
	}
	
	/**
	 * 七乐彩胆拖玩法注码拼接测试
	 */
	@Test
	public void getQLCDanTuoTest(){
		String bileCode = "1,3,2";
		String dragCode = "4,6,8,7,5,9,10";
		String str = QLCBetcodeResolve.getQLCDanTuo1(2, bileCode, dragCode, ",");
		Assert.assertEquals(str,"2002010203*04050607080910^");
		String betcode = "1,3,2*4,6,8,7,5,9,10";
		str = QLCBetcodeResolve.getQLCDanTuo(2, betcode, "*", ",");
		Assert.assertEquals(str,"2002010203*04050607080910^");
	}

}
