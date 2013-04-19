package com.jrt.betcodeResolve.test;

import junit.framework.Assert;

import org.junit.Test;

import com.jrt.betcodeResolve.resolve.SDBetcodeResolve;

/**
 * 福彩3D注码解析测试类
 * @author
 * 		徐丽
 *
 */
public class SDBetcodeResovleTest {
 
	/**
	 * 福彩3D单选单式和组选单式玩法注码拼接测试
	 */
	@Test
	public void getSDSimplexAndGroupTest(){
		String betcode = "1,3,2^1,2,2^4,5,6^7,9,8^0,9,1^";
		String str = SDBetcodeResolve.getSDSimplexAndGroup(2, "01", betcode, "^", ",");
		Assert.assertEquals(str, "0102010203^0102010202^0102040506^0102070809^0102000109^");
	}
	
	/**
	 * 福彩3D和值玩法注码拼接测试
	 */
	@Test
	public void getSDHeZhiTest(){
		String str="";
		str = SDBetcodeResolve.getSDHeZhi(2, "10", "20");
		Assert.assertEquals(str, "100220^");
		str = SDBetcodeResolve.getSDHeZhi(2, "11", "20");
		Assert.assertEquals(str, "110220^");
		str = SDBetcodeResolve.getSDHeZhi(2, "12", "20");
		Assert.assertEquals(str, "120220^");
	}
	
	/**
	 * 福彩3D直选复式（单选按位包号）玩法注码拼接测试
	 */
	@Test
	public void getSDDirectDoubleTest(){
		String betcode = "1,4,2,3,5^6,9,7,8,10,0^1,5,2,3,4,6,7^";
		String str = SDBetcodeResolve.getSDDirectDouble(2,betcode,",","^");
		Assert.assertEquals(str, "2002050102030405^06000607080910^0701020304050607^");
	}
	
	/**
	 * 福彩3D单选单复式（直选包号）玩法注码拼接测试
	 */
	@Test
	public void getSDDirectAndPackageNoTest(){
		String betcode = "3,6,7,8,4,5";
		String str = SDBetcodeResolve.getSDDirectAndPackageNo(2, betcode, ",");
		Assert.assertEquals(str, "340206030405060708^");
		
	}
	
	/**
	 * 福彩3D单选复式、组选复式玩法注码拼接测试
	 */
	@Test
	public void getSDSimplexAndGroupDoubleTest(){
		String betcode = "2,3,8,4,9,6,7";
		String str = SDBetcodeResolve.getSDSimplexAndGroupDouble(2, "31", betcode,",");
		Assert.assertEquals(str, "31020702030406070809^");
	}
	
	/**
	 * 福彩3D胆拖复式玩法注码拼接测试
	 */
	@Test
	public void getSDDantuoTest(){
		String str = SDBetcodeResolve.getSDDantuo1("2,1", "5,4,3", 1, ",");
		Assert.assertEquals(str, "54010102*030405^");
		
		str = SDBetcodeResolve.getSDDantuo(1, "2,1*5,4,3", "*", ",");
		Assert.assertEquals(str, "54010102*030405^");
	}

}
