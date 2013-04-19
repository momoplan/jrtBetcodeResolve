package com.jrt.betcodeResolve.ssqUtil;

import org.junit.Assert;
import org.junit.Test;

import com.jrt.betcodeResolve.lotnoBetcodeUtil.SYXWBetcodeUtil;

/**
 * 11选5的算注数和金额的测试类
 * @author 徐丽
 *
 */
public class SYXWBetcodeUtilTest {
  
  /**
   * 任选1-8算注数的方法测试
   */
  @Test
  public void getSYXWRXZhushuTest(){
	  String betcode = "R11;";
	  int zhushu = SYXWBetcodeUtil.getSYXWRXZhushu(betcode, ",");
	  Assert.assertEquals(1, zhushu);
	  
	  betcode =  "R11,2,3,4,5,6;" ;
	  zhushu = SYXWBetcodeUtil.getSYXWRXZhushu(betcode, ",");
	  Assert.assertEquals(6, zhushu);
	  
	  betcode =  "R21,2;" ;
	  zhushu = SYXWBetcodeUtil.getSYXWRXZhushu(betcode, ",");
	  Assert.assertEquals(1, zhushu);
	  
	  betcode =  "R21,2,3;" ;
	  zhushu = SYXWBetcodeUtil.getSYXWRXZhushu(betcode, ",");
	  Assert.assertEquals(3, zhushu);
	  
	  betcode =  "R31,2,3;" ;
	  zhushu = SYXWBetcodeUtil.getSYXWRXZhushu(betcode, ",");
	  Assert.assertEquals(1, zhushu);
	  
	  betcode =  "R31,2,3,4;" ;
	  zhushu = SYXWBetcodeUtil.getSYXWRXZhushu(betcode, ",");
	  Assert.assertEquals(4, zhushu);
	  
	  betcode =  "R41,2,3,4;" ;
	  zhushu = SYXWBetcodeUtil.getSYXWRXZhushu(betcode, ",");
	  Assert.assertEquals(1, zhushu);
	  
	  betcode =  "R41,2,3,4,5,6,7;" ;
	  zhushu = SYXWBetcodeUtil.getSYXWRXZhushu(betcode, ",");
	  Assert.assertEquals(35, zhushu);
	  
	  betcode =  "R51,2,3,4,5;" ;
	  zhushu = SYXWBetcodeUtil.getSYXWRXZhushu(betcode, ",");
	  Assert.assertEquals(1, zhushu);
	  
	  betcode =  "R51,2,3,4,5,6,9,10;" ;
	  zhushu = SYXWBetcodeUtil.getSYXWRXZhushu(betcode, ",");
	  Assert.assertEquals(56, zhushu);
	  
	  betcode =  "R61,2,3,4,5,6;" ;
	  zhushu = SYXWBetcodeUtil.getSYXWRXZhushu(betcode, ",");
	  Assert.assertEquals(1, zhushu);
	  
	  betcode =  "R61,2,3,4,5,6,7,8;" ;
	  zhushu = SYXWBetcodeUtil.getSYXWRXZhushu(betcode, ",");
	  Assert.assertEquals(28, zhushu);
	  
	  betcode =  "R71,2,3,4,5,6,7;" ;
	  zhushu = SYXWBetcodeUtil.getSYXWRXZhushu(betcode, ",");
	  Assert.assertEquals(1, zhushu);
	  
	  betcode =  "R71,2,3,4,5,6,7,8;" ;
	  zhushu = SYXWBetcodeUtil.getSYXWRXZhushu(betcode, ",");
	  Assert.assertEquals(8, zhushu);
	  
	  betcode =  "R81,2,3,4,5,6,7,8;" ;
	  zhushu = SYXWBetcodeUtil.getSYXWRXZhushu(betcode, ",");
	  Assert.assertEquals(1, zhushu);
  }
  
  /**
   * 任选1-8算金额的方法测试
   */
  @Test
  public void getSYXWRXMoneyTest(){
	  String betcode = "R11;";
	  int money = SYXWBetcodeUtil.getSYXWRXMoney(betcode, ",",1);
	  Assert.assertEquals(2, money);
	  
	  betcode =  "R11,2,3,4,5,6;" ;
	  money = SYXWBetcodeUtil.getSYXWRXMoney(betcode, ",",1);
	  Assert.assertEquals(12, money);
	  
	  betcode =  "R21,2;" ;
	  money = SYXWBetcodeUtil.getSYXWRXMoney(betcode, ",",1);
	  Assert.assertEquals(2, money);
	  
	  betcode =  "R21,2,3;" ;
	  money = SYXWBetcodeUtil.getSYXWRXMoney(betcode, ",",1);
	  Assert.assertEquals(6, money);
	  
	  betcode =  "R31,2,3;" ;
	  money = SYXWBetcodeUtil.getSYXWRXMoney(betcode, ",",1);
	  Assert.assertEquals(2, money);
	  
	  betcode =  "R31,2,3,4;" ;
	  money = SYXWBetcodeUtil.getSYXWRXMoney(betcode, ",",1);
	  Assert.assertEquals(8, money);
	  
	  betcode =  "R41,2,3,4;" ;
	  money = SYXWBetcodeUtil.getSYXWRXMoney(betcode, ",",1);
	  Assert.assertEquals(2, money);
	  
	  betcode =  "R41,2,3,4,5,6,7;" ;
	  money = SYXWBetcodeUtil.getSYXWRXMoney(betcode, ",",1);
	  Assert.assertEquals(70, money);
	  
	  betcode =  "R51,2,3,4,5;" ;
	  money = SYXWBetcodeUtil.getSYXWRXMoney(betcode, ",",1);
	  Assert.assertEquals(2, money);
	  
	  betcode =  "R51,2,3,4,5,6,9,10;" ;
	  money = SYXWBetcodeUtil.getSYXWRXMoney(betcode, ",",1);
	  Assert.assertEquals(112, money);
	  
	  betcode =  "R61,2,3,4,5,6;" ;
	  money = SYXWBetcodeUtil.getSYXWRXMoney(betcode, ",",1);
	  Assert.assertEquals(2, money);
	  
	  betcode =  "R61,2,3,4,5,6,7,8;" ;
	  money = SYXWBetcodeUtil.getSYXWRXMoney(betcode, ",",1);
	  Assert.assertEquals(56, money);
	  
	  betcode =  "R71,2,3,4,5,6,7;" ;
	  money = SYXWBetcodeUtil.getSYXWRXMoney(betcode, ",",1);
	  Assert.assertEquals(2, money);
	  
	  betcode =  "R71,2,3,4,5,6,7,8;" ;
	  money = SYXWBetcodeUtil.getSYXWRXMoney(betcode, ",",1);
	  Assert.assertEquals(16, money);
	  
	  betcode =  "R81,2,3,4,5,6,7,8;" ;
	  money = SYXWBetcodeUtil.getSYXWRXMoney(betcode, ",",1);
	  Assert.assertEquals(2, money);
  }
  
  /**
   * 前二和前三直选算注数的测试方法
   */
  @Test
  public void getSYXWQXZhushuTest(){
	  //前二直选单式
	  String betcode="Q22-8;";
	  Assert.assertEquals(1, SYXWBetcodeUtil.getSYXWQXZhushu(betcode, ",", "-"));
	  //前二直选复式
	  betcode = "Q21,2,3,4,5-8,9;";
	  Assert.assertEquals(10, SYXWBetcodeUtil.getSYXWQXZhushu(betcode, ",", "-"));
	  //前三直选单式
	  betcode = "Q36-9-10;";
	  Assert.assertEquals(1, SYXWBetcodeUtil.getSYXWQXZhushu(betcode, ",", "-"));
	  //前三直选复式
	  betcode = "Q31,4,6-5,8,9-7,10,11;";
	  Assert.assertEquals(27, SYXWBetcodeUtil.getSYXWQXZhushu(betcode, ",", "-"));
	  
	  //前二重复号码算注数
	  betcode = "Q21,2,3,4,5-3,4,5";
	  Assert.assertEquals(12, SYXWBetcodeUtil.getSYXWQXZhushu(betcode, ",", "-"));
	  
	  //前二重复号码算注数
	  betcode = "Q21,2,3,4,5,6,7,8,9,10,11-3";
	  Assert.assertEquals(10, SYXWBetcodeUtil.getSYXWQXZhushu(betcode, ",", "-"));
	  
	  //前三重复号码算注数
	  betcode = "Q21,2,3,4,5-3,4,5-3,4,5";
	  Assert.assertEquals(18, SYXWBetcodeUtil.getSYXWQXZhushu(betcode, ",", "-"));
	  
	  //前三重复号码算注数
	  betcode = "Q21,2,3,4,5-7,8,9-6,4,5";
	  Assert.assertEquals(39, SYXWBetcodeUtil.getSYXWQXZhushu(betcode, ",", "-"));
	
  }
  /**
   * 前二和前三直选算金额的测试方法
   */
  @Test
  public void getSYXWQXMoneyTest(){
	  //前二直选单式
	  String betcode="Q22-8;";
	  Assert.assertEquals(2, SYXWBetcodeUtil.getSYXWQXMoney(betcode, ",", "-",1));
	  //前二直选复式
	  betcode = "Q21,2,3,4,5-8,9;";
	  Assert.assertEquals(20, SYXWBetcodeUtil.getSYXWQXMoney(betcode, ",", "-",1));
	  //前三直选单式
	  betcode = "Q36-9-10;";
	  Assert.assertEquals(2, SYXWBetcodeUtil.getSYXWQXMoney(betcode, ",", "-",1));
	  //前三直选复式
	  betcode = "Q31,4,6-5,8,9-7,10,11;";
	  Assert.assertEquals(54, SYXWBetcodeUtil.getSYXWQXMoney(betcode, ",", "-",1));
  }
  
  /**
   * 前二或前三组选单式和复式算注数
   */
  @Test
  public void getSYXWQZZhushuTest(){
	  
	  //前二组选单式
	  String betcode = "Z24,10;";
	  Assert.assertEquals(1,SYXWBetcodeUtil.getSYXWQZZhushu(betcode, ","));
	  
	  //前二组选复式
	  betcode = "Z21,2,3,4,5,6,7,8;";
	  Assert.assertEquals(28,SYXWBetcodeUtil.getSYXWQZZhushu(betcode, ","));
	  
	  //前三组选单式
	  betcode = "Z31,7,10;";
	  Assert.assertEquals(1,SYXWBetcodeUtil.getSYXWQZZhushu(betcode, ","));
	  
	  //前三组选复式
	  betcode = "Z31,2,3,4,5,6,7,8,9;";
	  Assert.assertEquals(84,SYXWBetcodeUtil.getSYXWQZZhushu(betcode, ","));
  }
  /**
   * 前二或前三组选单式和复式算金额
   */
  @Test
  public void getSYXWQZMoneyTest(){
	  
	  //前二组选单式
	  String betcode = "Z24,10;";
	  Assert.assertEquals(2,SYXWBetcodeUtil.getSYXWQZMoney(betcode, ",", 1));
	  
	  //前二组选复式
	  betcode = "Z21,2,3,4,5,6,7,8;";
	  Assert.assertEquals(56,SYXWBetcodeUtil.getSYXWQZMoney(betcode, ",", 1));
	  
	  //前三组选单式
	  betcode = "Z31,7,10;";
	  Assert.assertEquals(2,SYXWBetcodeUtil.getSYXWQZMoney(betcode, ",", 1));
	  
	  //前三组选复式
	  betcode = "Z31,2,3,4,5,6,7,8,9;";
	  Assert.assertEquals(168,SYXWBetcodeUtil.getSYXWQZMoney(betcode, ",", 1));
  }
  
  @Test
  public void getSYXW_RX_DTZhushuTest(){
	  //任选二胆拖
	  String betcode = "R21$5,8,10;";
	  Assert.assertEquals(3,SYXWBetcodeUtil.getSYXW_RX_DTZhushu(betcode, ",", "$"));
	  
	  //任选三胆拖
	  betcode = "R31$5,8,10;";
	  Assert.assertEquals(3,SYXWBetcodeUtil.getSYXW_RX_DTZhushu(betcode, ",", "$"));
	  betcode = "R31$2,3,4,5,6,7,8,9,10;";
	  Assert.assertEquals(36,SYXWBetcodeUtil.getSYXW_RX_DTZhushu(betcode, ",", "$"));
	  
	  //任选四胆拖
	  betcode = "R41,2$5,6,7,8,10;";
	  Assert.assertEquals(10,SYXWBetcodeUtil.getSYXW_RX_DTZhushu(betcode, ",", "$"));
	  
	  //任选五胆拖
	  betcode = "R51,2$5,6,7,8,10;";
	  Assert.assertEquals(10,SYXWBetcodeUtil.getSYXW_RX_DTZhushu(betcode, ",", "$"));
	  
	  //任选六胆拖
	  betcode = "R61,2$5,6,7,8,10;";
	  Assert.assertEquals(5,SYXWBetcodeUtil.getSYXW_RX_DTZhushu(betcode, ",", "$"));
	  
	  //任选七胆拖
	  betcode = "R71,2$5,6,7,8,10,11;";
	  Assert.assertEquals(6,SYXWBetcodeUtil.getSYXW_RX_DTZhushu(betcode, ",", "$"));
	  
	  //任选八胆拖
	  betcode = "R81,2,3,5,6,7,8$10,11;";
	  Assert.assertEquals(2,SYXWBetcodeUtil.getSYXW_RX_DTZhushu(betcode, ",", "$"));
	  
	  
	  //前二组选胆拖
	  betcode = "Z21$5,8,10;";
	  Assert.assertEquals(3,SYXWBetcodeUtil.getSYXW_RX_DTZhushu(betcode, ",", "$"));
	  //前三组选胆拖
	  betcode = "Z31$5,6,7,8,10;";
	  Assert.assertEquals(10,SYXWBetcodeUtil.getSYXW_RX_DTZhushu(betcode, ",", "$"));
  }
  
  @Test
  public void getSYXW_RX_DTMoneyTest(){
	  //任选二胆拖
	  String betcode = "R21$5,8,10;";
	  Assert.assertEquals(6,SYXWBetcodeUtil.getSYXW_RX_DTMoney(betcode, ",", "$",1));
	  
	  //任选三胆拖
	  betcode = "R31$5,8,10;";
	  Assert.assertEquals(6,SYXWBetcodeUtil.getSYXW_RX_DTMoney(betcode, ",", "$",1));
	  
	  //任选四胆拖
	  betcode = "R41,2$5,6,7,8,10;";
	  Assert.assertEquals(20,SYXWBetcodeUtil.getSYXW_RX_DTMoney(betcode, ",", "$",1));
	  
	  //任选五胆拖
	  betcode = "R51,2$5,6,7,8,10;";
	  Assert.assertEquals(20,SYXWBetcodeUtil.getSYXW_RX_DTMoney(betcode, ",", "$",1));
	  
	  //任选六胆拖
	  betcode = "R61,2$5,6,7,8,10;";
	  Assert.assertEquals(10,SYXWBetcodeUtil.getSYXW_RX_DTMoney(betcode, ",", "$",1));
	  
	  //任选七胆拖
	  betcode = "R71,2$5,6,7,8,10,11;";
	  Assert.assertEquals(12,SYXWBetcodeUtil.getSYXW_RX_DTMoney(betcode, ",", "$",1));
	  
	  //任选八胆拖
	  betcode = "R81,2,3,5,6,7,8$10,11;";
	  Assert.assertEquals(4,SYXWBetcodeUtil.getSYXW_RX_DTMoney(betcode, ",", "$",1));
	  
	  //前二胆拖
	  betcode = "Z21$5,8,10;";
	  Assert.assertEquals(6,SYXWBetcodeUtil.getSYXW_RX_DTMoney(betcode, ",", "$",1));
	  //前三胆拖
	  betcode = "Z31$5,6,7,8,10;";
	  Assert.assertEquals(20,SYXWBetcodeUtil.getSYXW_RX_DTMoney(betcode, ",", "$",1));
  }
}
