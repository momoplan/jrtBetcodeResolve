package com.jrt.betcodeResolve.test;

import junit.framework.Assert;

import org.junit.Test;

import com.jrt.betcodeResolve.resolve.PLSBetcodeResovle;

/**
 * 
 *		体彩排列三注码解析的测试类
 * @author
 *		徐丽
 *
 */
public class PLSBetcodeResovleTest {
	
	/**
	 * 返回体彩排三注码的测试方法
	 * (01-直选、06-组选、S1-直选和值、S9-组选和值、S3-组三和值、S6-组六和值、F3-组三包号、F6-组六包号)
	 */
	@Test
    public void getPLSBetcodeTest(){
	   String betcode ="1,2,3;1,2,3";
	   Assert.assertEquals(PLSBetcodeResovle.getPLSBetcode(betcode,"01", ",",";"), "1|1,2,3;1,2,3");
	   betcode ="1,2,3";
	   Assert.assertEquals(PLSBetcodeResovle.getPLSBetcode(betcode,"06", ",",";"), "6|1,2,3");
	   betcode ="1,2,3";
	   Assert.assertEquals(PLSBetcodeResovle.getPLSBetcode(betcode,"S1", ",",";"), "S1|1,2,3");
	   betcode ="1,2,3";
	   Assert.assertEquals(PLSBetcodeResovle.getPLSBetcode(betcode,"S9", ",",";"), "S9|1,2,3");
	   betcode ="1,2,3";
	   Assert.assertEquals(PLSBetcodeResovle.getPLSBetcode(betcode,"S3", ",",";"), "S3|1,2,3");
	   betcode ="1,2,3";
	   Assert.assertEquals(PLSBetcodeResovle.getPLSBetcode(betcode,"S6", ",",";"), "S6|1,2,3");
	   betcode ="1,2,3";
	   Assert.assertEquals(PLSBetcodeResovle.getPLSBetcode(betcode,"F3", ",",";"), "F3|1,2,3");
	   betcode ="1,2,3";
	   Assert.assertEquals(PLSBetcodeResovle.getPLSBetcode(betcode,"F6", ",",";"), "F6|1,2,3");
    }
}
