package com.jrt.betcodeResolve.test;

import junit.framework.Assert;

import org.junit.Test;

import com.jrt.betcodeResolve.resolve.SSQBetcodeResolve;
/**
 * 
 * 双色球注码解析测试类
 * @author
 * 		徐丽
 *
 */
public class SSQBetcodeResovleTest {

	/**
	 * 福彩双色球单式玩法注码拼接测试
	 */
	@Test
	public void getSSQSimplexTest(){
		String betcode="2,1,3,5,4,6+1^7,10,8,12,9,11+2^1,6,15,26,32,2+2^";
		String str = SSQBetcodeResolve.getSSQSimplex(2, betcode,"^", "+", ",");
		Assert.assertEquals(str, "0002010203040506~01^0002070809101112~02^0002010206152632~02^");
	}
	
	public static void main(String args[]){
		String betcode="2,1,3,5,4,6+1^7,10,8,12,9,11+2^1,6,15,26,32,2+2^";
		String str = SSQBetcodeResolve.getSSQSimplex(2, betcode,"^", "+", ",");
		System.out.println(str);
	}
	
	/**
	 * 福彩双色球红复蓝单玩法注码拼接测试
	 */
	@Test
	public void  getSSQRedDuplexBlueSimplexTest(){
		String betcode = "1,6,2,5,3,4,9,7,8,10+1";
		String str =SSQBetcodeResolve.getSSQRedDuplexBlueSimplex(2, betcode, "+", ",");
		Assert.assertEquals(str, "1002*01020304050607080910~01^");
	}
	
	/**
	 * 福彩双色球红单蓝复玩法注码拼接测试
	 */
	@Test
	public void  getSSQRedSimplexBlueDuplexTest(){
		String betcode = "11,2,6,3,4,5+1,3,2";
		String str = SSQBetcodeResolve.getSSQRedSimplexBlueDuplex(2, betcode, "+", ",");
		Assert.assertEquals(str, "2002*020304050611~010203^");
	}
	
	/**
	 * 福彩双色球红复蓝复玩法注码拼接测试
	 */
	@Test
	public void  getSSQRedDuplexBlueDuplexTest(){
		String betcode = "1,5,2,3,9,4,6,7+1,3,2";
		String str = SSQBetcodeResolve.getSSQRedDuplexBlueDuplex(2, betcode, "+", ",");
		Assert.assertEquals(str, "3002*0102030405060709~010203^");
	}
	
	/**
	 * 福彩双色球红胆拖蓝单玩法注码拼接测试
	 */
	@Test
	public void  getSSQRedDanTuoBlueSimplexTest(){
		String betcode = "1,4,2,3*5,9,7,16+1";
		String str = SSQBetcodeResolve.getSSQRedDanTuoBlueSimplex(2, betcode, "+","*", ",");
		Assert.assertEquals(str, "400201020304*05070916~01^");
	}
	
	/**
	 * 福彩双色球红胆拖蓝复玩法注码拼接测试
	 */
	@Test
	public void  getSSQRedDanTuoBlueDuplexTest(){
		String betcode = "1,2,4,3*5,7,9,6+1,3,2";
		String str = SSQBetcodeResolve.getSSQRedDanTuoBlueDuplex(2, betcode, "+", "*", ",");
		Assert.assertEquals(str, "500201020304*05060709~010203^");
	}
	
	/**
	 * 福彩双色球红胆拖蓝单玩法注码拼接测试
	 */
	@Test
	public void  getSSQRedDanTuoBlueSimplexTest1(){
		String redBileCode = "1,14,2,3";
		String redDragCode = "5,9,7,6";
		String blueBall = "1";
		String str = SSQBetcodeResolve.getSSQRedDanTuoBlueSimplex1(2, redBileCode, redDragCode,blueBall, ",");
		Assert.assertEquals(str, "400201020314*05060709~01^");
	}
	
	/**
	 * 福彩双色球红胆拖蓝复玩法注码拼接测试
	 */
	@Test
	public void  getSSQRedDanTuoBlueDuplexTest1(){
		String redBileCode = "1,4,2,3";
		String redDragCode = "5,9,7,6";
		String blueBall = "1,3,2";
		String str = SSQBetcodeResolve.getSSQRedDanTuoBlueDuplex1(2, redBileCode, redDragCode, blueBall, ",");
		Assert.assertEquals(str, "500201020304*05060709~010203^");
	}

}
