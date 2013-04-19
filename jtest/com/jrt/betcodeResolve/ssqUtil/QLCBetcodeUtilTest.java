package com.jrt.betcodeResolve.ssqUtil;

import junit.framework.Assert;

import org.junit.Test;

import com.jrt.betcodeResolve.lotnoBetcodeUtil.QLCBetcodeUtil;
/**
 * 
 * 七乐彩注码解析 获取金额、注数、玩法的测试类
 * @author
 *		徐丽
 */
public class QLCBetcodeUtilTest {
   
   @Test
   //单式
   public void getQLCSimplexMoneyTest(){
	   int money = QLCBetcodeUtil.getQLCSimplexMoney("1,7,2,6,3,4,5^8,13,9,10,11,12,14^15,20,16,17,18,19,21^", 1, "^");
	   Assert.assertEquals(money, 6);
   }
   
   @Test
   //复式
   public void getQLCDuplexMoneyTest(){
	   int money = QLCBetcodeUtil.getQLCDuplexMoney("1,7,2,6,3,4,5,8,9,10", 1, ",");
	   Assert.assertEquals(money, 240);
   }
   
   @Test
   //胆拖
   public void getQLCDanTuoMoneyTest(){
	   int money = QLCBetcodeUtil.getQLCDanTuoMoney1("1,3,2","4,6,8,7,5,9,10", 1, ",");
	   Assert.assertEquals(money, 70);
	   
	   money = QLCBetcodeUtil.getQLCDanTuoMoney("1,3,2*4,6,8,7,5,9,10", 1, "*", ",");
	   Assert.assertEquals(money, 70);
   }
}
