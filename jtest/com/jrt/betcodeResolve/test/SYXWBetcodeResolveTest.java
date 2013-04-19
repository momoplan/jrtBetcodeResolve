package com.jrt.betcodeResolve.test;

import org.junit.Assert;
import org.junit.Test;

import com.jrt.betcodeResolve.resolve.SYXWBetcodeResolve;

/**
 * 11选5拼接注码的测试类
 * @author Administrator
 *
 */
public class SYXWBetcodeResolveTest {
	/**
	 * 任选1-8 或选前二和前三组选注码解析测试
	 */
	@Test
    public void getSYXWRXBetcodeTest(){
	  String betcode = "R11;R11,2,3,4,5,6;" +
	  		"R21,2;R21,2,3,4;" +
	  		"R31,2,3;R31,2,3;" +
	  		"R41,2,3,4;R41,2,3,4,5,6,7;" +
	  		"R51,2,3,4,5;R51,2,3,4,5,6,9,10;" +
	  		"R61,2,3,4,5,6;R61,2,3,4,5,6,7,8;" +
	  		"R71,2,3,4,5,6,7;R71,2,3,4,5,6,7,8;" +
	  		"R81,2,3,4,5,6,7,8;R81,2,3,4,5,6,7,8;" +
	  		"Z24,10;Z21,2,3,4,5,6,7,8;Z31,7,10;Z31,2,3,4,5,6,7,8,9;";
	  System.out.println("任选1-8 或选前二和前三组选注码解析之后的注码为betcode_new="
			  +SYXWBetcodeResolve.getSYXWRXBetcode(betcode, ";", ","));
	 Assert.assertEquals("R1|01;R1|01 02 03 04 05 06;" +
	 		"R2|01 02;R2|01 02 03 04;" +
	 		"R3|01 02 03;R3|01 02 03;" +
	 		"R4|01 02 03 04;R4|01 02 03 04 05 06 07;" +
	 		"R5|01 02 03 04 05;R5|01 02 03 04 05 06 09 10;" +
	 		"R6|01 02 03 04 05 06;R6|01 02 03 04 05 06 07 08;" +
	 		"R7|01 02 03 04 05 06 07;R7|01 02 03 04 05 06 07 08;" +
	 		"R8|01 02 03 04 05 06 07 08;R8|01 02 03 04 05 06 07 08;" +
	 		"Z2|04 10;Z2|01 02 03 04 05 06 07 08;Z3|01 07 10;Z3|01 02 03 04 05 06 07 08 09;",
	 		SYXWBetcodeResolve.getSYXWRXBetcode(betcode, ";", ","));
	
    }
	
	/**
	 * 选前二或前三直选注码解析测试
	 */
	@Test
	public void getSYXWQZBetcodeTest(){
		String betcode = "Q22-8;Q21,2,3,4,5-8,9;Q36-9-10;Q31,4,6-5,8,9-7,10,11;";
		System.out.println("选前二或前三直选注码解析的注码为betcode_new="
				+SYXWBetcodeResolve.getSYXWQZBetcode(betcode, ";", ",","-"));
		Assert.assertEquals("Q2|02,08;Q2|01 02 03 04 05,08 09;" +
				"Q3|06,09,10;Q3|01 04 06,05 08 09,07 10 11;",
				SYXWBetcodeResolve.getSYXWQZBetcode(betcode, ";", ",","-"));
	}
	
	/**
	 * 任选2-8 或选前二和前三组选胆拖注码解析测试
	 */
	@Test
	public void getSYXWRXDTBetcodeTest(){
		String betcode = "R21$5,8,10;R31$5,8,10;" +
				"R41,2$5,6,7,8,10;R51,2$5,6,7,8,10;" +
				"R61,2$5,6,7,8,10;R71,2$5,6,7,8,10,11;" +
				"R81,2,3,5,6,7,8$10,11;" +
				"Z21$5,8,10;Z31$5,6,7,8,10;";
		System.out.println("选前二或前三直选注码解析的注码为betcode_new="
				+SYXWBetcodeResolve.getSYXWRXDTBetcode(betcode, ";", ",","$"));
		Assert.assertEquals("R2|01$05 08 10;R3|01$05 08 10;" +
				"R4|01 02$05 06 07 08 10;R5|01 02$05 06 07 08 10;" +
				"R6|01 02$05 06 07 08 10;R7|01 02$05 06 07 08 10 11;" +
				"R8|01 02 03 05 06 07 08$10 11;" +
				"Z2|01$05 08 10;Z3|01$05 06 07 08 10;",
				SYXWBetcodeResolve.getSYXWRXDTBetcode(betcode, ";", ",","$"));
	}
}
