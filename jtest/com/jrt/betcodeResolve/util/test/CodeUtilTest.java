package com.jrt.betcodeResolve.util.test;

import net.sf.json.JSONObject;

import org.junit.Test;

import com.jrt.betcodeResolve.util.CodeUtil;

/**
 * 
 * 投注串转换为注码串的测试类
 * 
 */
public class CodeUtilTest {
	
	//双色球
	@Test
	public void getSSQCodeStringTest(){
		//单式
		String betCode = "0001070809101112~03^0001010206152632~04^0001010206152632~05^0001010206152632~06^0001070809101112~07^";
		JSONObject obj = CodeUtil.getSSQCodeString(betCode);
		System.out.println(obj);
		
		//红复蓝单
		betCode="1001*01020304050607080910~01^";
		obj = CodeUtil.getSSQCodeString(betCode);
		System.out.println(obj);
		
		//红单蓝复
		betCode="2001*020304050611~010203^";
		obj = CodeUtil.getSSQCodeString(betCode);
		System.out.println(obj);
		
		//红复蓝复
		betCode="3001*0102030405060709~010203^";
		obj = CodeUtil.getSSQCodeString(betCode);
		System.out.println(obj);
		
		//红胆拖蓝单
		betCode="400101020304*05070916~01^";
		obj = CodeUtil.getSSQCodeString(betCode);
		System.out.println(obj);
		
		//红胆拖蓝复
		betCode="500101020304*05060709~010203^";
		obj = CodeUtil.getSSQCodeString(betCode);
		System.out.println(obj);			
	}
	
	/**
	 * 福彩3D
	 */
	@Test
	public void getSDCodeStringTest(){
		String caizhong="F47103";
		//直选单式
		String betCode="0001030705^0001040106^0001020702^0001030501^0001000608^";
		JSONObject obj = CodeUtil.getSDCodeString(betCode);
		System.out.println(obj);	
		
		//组三单式
		betCode="0101010707^0101010404^0101010101^0101050808^0101030808^";
		obj = CodeUtil.getSDCodeString(betCode);
		System.out.println(obj);	
		
		//组六单式
		betCode="0201030408^0201000204^0201060709^0201050607^0201000206^";
		obj = CodeUtil.getSDCodeString(betCode);
		System.out.println(obj);
		
		//直选和值
		betCode="100102^";
		obj = CodeUtil.getSDCodeString(betCode);
		System.out.println(obj);
		
		//直选包号(直选复式)
		betCode = "2012050102030405^06050607080900^0701020304050607^";
		obj = CodeUtil.getSDCodeString(betCode);
		System.out.println(obj);
		
		// 组3复式
		betCode="311206030405060708^";
		obj = CodeUtil.getSDCodeString(betCode);
		System.out.println(obj);
		
		// 组6复式
		betCode="321206030405060708^";
		obj = CodeUtil.getSDCodeString(betCode);
		System.out.println(obj);
		
		// 单选单复式(3D直选包号)
		betCode="341206030405060708^";
		obj = CodeUtil.getSDCodeString(betCode);
		System.out.println(obj);
		
		// 胆拖复式
		betCode="54010102*030405^";
		obj = CodeUtil.getSDCodeString(betCode);
		System.out.println(obj);
	}
	
	@Test
	public void getQLCCodeStringTest(){
		
		//七乐彩
		String caizhong="F47102";
		
		//单式
		String betCode="000122232425262728^000102030405060708^000112131415161718^";
		JSONObject obj = CodeUtil.getQLCCodeString(betCode);
		System.out.println(obj);
		
		//复式
		betCode = "1001*01020304050607080910^";
		obj = CodeUtil.getQLCCodeString(betCode);
		System.out.println(obj);
		
		//胆拖
		betCode = "2001010203*04050607080910^";
		obj = CodeUtil.getQLCCodeString(betCode);
		System.out.println(obj);
	}
	
	@Test
	public void getTCCodeStringTest(){
		
		//体彩排列三
		String caizhong="T01002";
		String betCode = "1_6|2,5,9";
		JSONObject obj = CodeUtil.getBetcode(caizhong, betCode,"") ;
		System.out.println(obj);
		
		betCode = "1_6|0,0,5;0,0,5;5,5,4;0,0,5;5,5,4;";
		obj = CodeUtil.getBetcode(caizhong, betCode,"") ;
		System.out.println(obj);
//		
//		//超级大乐透
//		caizhong="T01001";
//		betCode="1_09 10 17 21 29-09 12;01 07 15 22 25-06 12;08 12 14 18 26-03 07;09 16 20 21 23-04 08;03 08 12 16 27-08 09";
//		betCode="1_09 10 17 21 29-09 12";
//		str = CodeUtil.getBetcode(caizhong, betCode) +" "
//	      + CodeUtil.getDLTGameMethod(betCode)+"  "
//	      + betCode.substring(0,1)+"倍";
//		
//		//足彩
//		caizhong ="T01004";
//		betCode="2_#,#,#,#,#,#,#,#,#,#,#,#,#,#$3,#,#,0,#,3,1,3,#,3,3,1,3,#";
//		betCode="1_03,1,0,1,0,1,3,0,#,#,1,#,#,#";
//		
//		str = CodeUtil.getBetcode(caizhong, betCode) +" "
//		      + CodeUtil.getZCMethod(betCode)+"  "
//		      + betCode.substring(0,1)+"倍";
//		System.out.println(str);
		
	}
}
