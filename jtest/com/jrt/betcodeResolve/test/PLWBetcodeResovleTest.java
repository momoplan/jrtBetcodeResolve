package com.jrt.betcodeResolve.test;

import junit.framework.Assert;

import org.junit.Test;

import com.jrt.betcodeResolve.resolve.PLWBetcodeResovle;

/**
 * 
 * 排列五获取投注注码的测试类
 * @author 徐丽
 *
 */
public class PLWBetcodeResovleTest {
	/**
	 * 单式注码解析方法测试
	 */
	@Test
	public void getPLWSimplexBetcodeTest(){
		String str = PLWBetcodeResovle.getPLWSimplexBetcode("1,2,3,5,6;4,5,6,7,8;2,4,5,6,9", ";", ",");
		Assert.assertEquals(str, "1,2,3,5,6;4,5,6,7,8;2,4,5,6,9");
	}
	
	/**
	 * 直选复式注码解析方法测试
	 */
	@Test
	public void getPLWDirectDoubleBetcodeTest(){
		String str = PLWBetcodeResovle.getPLWDirectDoubleBetcode("1,2,8;0,2,3;1,2,8;0,2,3;3,4,9", ";", ",");
		Assert.assertEquals(str, "128,023,128,023,349");
	}
}
