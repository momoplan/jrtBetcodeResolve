package com.jrt.betcodeResolve.test;

import org.junit.Assert;
import org.junit.Test;

import com.jrt.betcodeResolve.resolve.SSCBetcodeResolve;

/**
 * 
 * 时时彩的注码解析测试类
 * 
 * @author 徐丽
 * 
 */
public class SSCBetcodeResolveTest {

	/**
	 * 单式玩法解析注码测试
	 */
	@Test
	public void getSSCSimplexBetcodeTest() {
		String betcode = "1,2,3,5,6;4,5,6,7,8;2,4,5,6,9";// 五星
		betcode = SSCBetcodeResolve.getSSCSimplexBetcode(betcode, ";", "5D",",");
		Assert.assertEquals("5D|1,2,3,5,6;5D|4,5,6,7,8;5D|2,4,5,6,9", betcode);
		
		betcode = "1,2,3,5,6;4,5,6,7,8;2,4,5,6,9";// 五星通选
		betcode = SSCBetcodeResolve.getSSCSimplexBetcode(betcode, ";", "5T",",");
		Assert.assertEquals("5T|1,2,3,5,6;5T|4,5,6,7,8;5T|2,4,5,6,9", betcode);
		
		betcode = "5,1,8;9,5,6;6,7,8;5,6,9";// 三星
		betcode = SSCBetcodeResolve.getSSCSimplexBetcode(betcode, ";", "3D",",");
		Assert.assertEquals("3D|-,-,5,1,8;3D|-,-,9,5,6;3D|-,-,6,7,8;3D|-,-,5,6,9", betcode);
		
		betcode = "1,8;5,6;7,8;6,9";// 二星
		betcode = SSCBetcodeResolve.getSSCSimplexBetcode(betcode, ";", "2D",",");
		Assert.assertEquals("2D|-,-,-,1,8;2D|-,-,-,5,6;2D|-,-,-,7,8;2D|-,-,-,6,9", betcode);
		
		betcode = "8;6;8;9";// 一星
		betcode = SSCBetcodeResolve.getSSCSimplexBetcode(betcode, ";", "1D",",");
		Assert.assertEquals("1D|-,-,-,-,8;1D|-,-,-,-,6;1D|-,-,-,-,8;1D|-,-,-,-,9", betcode);
		
		betcode = "8,5;8,5;8,5";// 大小单双单式
		betcode = SSCBetcodeResolve.getSSCSimplexBetcode(betcode, ";", "DD",",");
		Assert.assertEquals("DD|85;DD|85;DD|85;", betcode);

	}
	
	/**
	 * 直选复式注码解析测试
	 */
	@Test
	public void getSSCDirectDoubleTest(){
		String betcode = "1,2,8-0,2,3-1,2,8-0,2,3-3,4,9";// 五星
		betcode = SSCBetcodeResolve.getSSCDirectDoubleBetcode(betcode, ",",";","-", "5D");
		Assert.assertEquals("5D|128,023,128,023,349", betcode);
		
		betcode = "1,2,8-0,2,3-1,2,8-0,2,3-3,4,9";// 五星通选
		betcode = SSCBetcodeResolve.getSSCDirectDoubleBetcode(betcode, ",",";","-", "5T");
		Assert.assertEquals("5T|128,023,128,023,349", betcode);
		
		betcode = "1,2,4-1,2";// 大小单双
		betcode = SSCBetcodeResolve.getSSCDirectDoubleBetcode(betcode, ",",";","-", "DD");
		Assert.assertEquals("DD|124,12", betcode);
		
		betcode = "1,2,5-1,3-8,4";// 三星
		betcode = SSCBetcodeResolve.getSSCDirectDoubleBetcode(betcode, ",",";","-", "3D");
		Assert.assertEquals("3D|-,-,125,13,84", betcode);
		
		betcode = "1,2,5-1,3";// 二星
		betcode = SSCBetcodeResolve.getSSCDirectDoubleBetcode(betcode, ",",";","-", "2D");
		Assert.assertEquals("2D|-,-,-,125,13", betcode);
		
		betcode = "1,2,5";// 一星
		betcode = SSCBetcodeResolve.getSSCDirectDoubleBetcode(betcode, ",",";","-", "1D");
		Assert.assertEquals("1D|-,-,-,-,125", betcode);

	}
	
	/**
	 * 二星注码解析测试
	 */
	@Test
	public void getSSCRXBetcodeTest(){
		String betcode = "5,6,7,8";//二星直选和值
		betcode = SSCBetcodeResolve.getSSCRXBetcode(betcode, ",", "H2");
		Assert.assertEquals("H2|5,6,7,8", betcode);
		
		betcode = "5,6,7,8";//二星组选和值
		betcode = SSCBetcodeResolve.getSSCRXBetcode(betcode, ",", "S2");
		Assert.assertEquals("S2|5,6,7,8", betcode);
		
		betcode = "5,6,7,8";//二星组选复式
		betcode = SSCBetcodeResolve.getSSCRXBetcode(betcode, ",", "F2");
		Assert.assertEquals("F2|5678", betcode);
	}
	
}
