package com.jrt.betcodeResolve.test;

import junit.framework.Assert;

import org.junit.Test;

import com.jrt.betcodeResolve.resolve.DLTBetcodeResovle;
/**
 * 
 * 超级大乐透注码解析测试类
 * @author
 * 		徐丽
 *
 */
public class DLTBetcodeResovleTest {
	
	
	/**
	 * 超级大乐透单式玩法注码拼接测试
	 */
	@Test
	public void getSimplexTest(){
		String betcode ="1,12,15,4,5,6+1,7;10,14,22,25,31+3,9;3,12,15,26,35+3,12;" +
				"7,10,19,33,34+1,2;9,13,19,22,35+2,9;2,14,15,27,35+3,4";
		Assert.assertEquals(DLTBetcodeResovle.getDLTSimple(betcode, ";", ",", "+"),
				"01 12 15 04 05 06-01;10 14 22 25 31-03 09;03 12 15 26 35-03 12;07 10 19 33 34-01 02;09 13 19 22 35-02 09;02 14 15 27 35-03 04");
	}
	
	/**
	 * 超级大乐透复式玩法注码拼接测试
	 */
	@Test
    public void getDLTDuplexTest(){
		String simpleBetcode = "1;2;3;4;5+6;7";
		String Duplex1Betcode = "1,12,15,4,5,6+1,7";
		String Duplex2Betcode = "1,12,15,4,5+1,6,7";
		String Duplex3Betcode = "1,12,15,4,5,6+1,6,7";
		Assert.assertEquals(DLTBetcodeResovle.getDLTDuplex(simpleBetcode, ";", "+"), "01 02 03 04 05-06 07");
		Assert.assertEquals(DLTBetcodeResovle.getDLTDuplex(Duplex1Betcode, ",", "+"), "01 12 15 04 05 06-01 07");
		Assert.assertEquals(DLTBetcodeResovle.getDLTDuplex(Duplex2Betcode, ",", "+"), "01 12 15 04 05-01 06 07");
		Assert.assertEquals(DLTBetcodeResovle.getDLTDuplex(Duplex3Betcode, ",", "+"), "01 12 15 04 05 06-01 06 07");
		
	}
	
	/**
	 * 超级大乐透胆拖玩法注码拼接测试
	 */
	@Test
	public void getDLTDantuoTest(){
		String dtCode1 = "18$8,12,15,19,28+$2,8";
		String dtCode2 = "6,18$8,12,15,19,28+2$7,8,11";
		Assert.assertEquals(DLTBetcodeResovle.getDLTDantuo(dtCode1, ",", "+", "$"), "18$08 12 15 19 28-$02 08");
		Assert.assertEquals(DLTBetcodeResovle.getDLTDantuo(dtCode2, ",", "+", "$"), "06 18$08 12 15 19 28-02$07 08 11");
		
		Assert.assertEquals(DLTBetcodeResovle.getDLTDantuo1("18","8,12,15,19,28","","2,8",","), "18$08 12 15 19 28-$02 08");
		Assert.assertEquals(DLTBetcodeResovle.getDLTDantuo1("6,18", "8,12,15,19,28", "2", "7,8,11",","), "06 18$08 12 15 19 28-02$07 08 11");
	}
	
	/**
	 * 超级大乐透生肖乐(十二选二)单式和复式玩法注码拼接测试
	 */
	@Test
	public void getAnimalHappyTest(){
		String betcode = "2,8";
		Assert.assertEquals(DLTBetcodeResovle.getAnimalHappy(betcode, ","),"02 08");
		betcode = "2,3,8";
		Assert.assertEquals(DLTBetcodeResovle.getAnimalHappy(betcode, ","),"02 03 08");
	}
}
