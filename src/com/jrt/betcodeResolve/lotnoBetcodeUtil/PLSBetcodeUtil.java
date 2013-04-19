package com.jrt.betcodeResolve.lotnoBetcodeUtil;

import com.jrt.betcodeResolve.util.BetcodeResolveUtil;
import com.jrt.betcodeResolve.util.Constant;

/**
 * 		排三注码解析 获取金额、注数、玩法的类
 * @author
 * 		徐丽
 */
public class PLSBetcodeUtil {
	
	/**
	 * 
	 * 		 排列三 直选单式、组选(包括组三和组六)单式注码解析算注数
	 * @param 
	 * 		betcode 注码
	 * 		 示例：1,2,3;4,5,6;4,5,6
	 * @param 
	 * 		tcTabNumber 注码之间的分隔符 示例中为";"
	 * @return 
	 *      注数 示例中为3注
	 *      
	 */
     public static int getPLSSimplexZhushu(String betcode,String tcTabNumber){
    	 //根据注码直接的分隔符tcTabNumber解析注码
    	 String arrCode[] = betcode.split("\\"+tcTabNumber);
    	 
    	 //算 注数并返回
    	 return arrCode.length ;

     }
	
	/**
	 * 
	 * 		 排列三直选单式、组选(包括组三和组六)单式注码解析算金额
	 * @param 
	 * 		betcode 注码
	 * 		 示例：1,2,3;4,5,6;4,5,6
	 * @param
	 * 		multiple 倍数 为1倍
	 * @param 
	 * 		tcTabNumber 注码之间的分隔符 示例中为";"
	 * @return 
	 *      总金额 = 注数*倍数*单价(单张彩票的金额)
	 *      示例中为6元
	 *      
	 */
     public static int getPLSSimplexMoney(String betcode,int multiple,String tcTabNumber){
    	 //调用单式算注数的方法得到注数
    	 int zhushu = getPLSSimplexZhushu(betcode, tcTabNumber);
    	 
    	 //算总金额 = 注数*倍数*单价(单张彩票的金额)
    	 return zhushu * multiple * Constant.LOTTERY_PRICE;
    	 
     }
     
     
     /**
  	 * 
  	 * 		排列三和值注码解析得注数
  	 * @param
  	 * 		betcode 和值数
  	 *      例:和值数12
  	 * @param 
  	 * 		playName 玩法名称
  	 *      3-直选和值、4-组选和值、5-组三和值、6-组六和值
  	 * @return
  	 * 		总金额 = 注数*倍数*单价(单张彩票的金额)
 	 *      示例中为6元
 	 *      
  	 */
  	public static int getPLSHezhiZhushu(String betcode,String playName){
  		//注数
 		int zhushu = 0;
 		//转换问整数
 		int code = Integer.parseInt(betcode);
 		if (playName.equals(Constant.PLS_ZHXHZ)) {
 			//直选和值
 			zhushu = BetcodeResolveUtil.getDirectHeZhiNumber(code);
 			
 		}else if (playName.equals(Constant.PLS_ZXHZ)) {
 			//组选和值
 			zhushu = BetcodeResolveUtil.getGroup3HeZhiNumber(code) + BetcodeResolveUtil.getGroup6HeZhiNumber(code);
 			
 		}else if (playName.equals(Constant.PLS_ZSHZ)) {
 			//组三和值
 			zhushu = BetcodeResolveUtil.getGroup3HeZhiNumber(code);
 			
 		}else if (playName.equals(Constant.PLS_ZLHZ)) {
 			//组六和值
 			zhushu = BetcodeResolveUtil.getGroup6HeZhiNumber(code);
 		}
 		return zhushu ;
  	}
  	
     /**
 	 * 
 	 * 		排列三和值注码解析得金额
 	 * @param
 	 * 		betcode 和值数
 	 *      例:和值数20
 	 * @param
 	 * 		multiple 倍数为1倍
 	 * @param 
 	 * 		playName 玩法名称
 	 *      3-直选和值、4-组选和值、5-组三和值、6-组六和值
 	 * @return
 	 * 		总金额 = 注数*倍数*单价(单张彩票的金额)
	 *      示例: 直选和值73注、组选和值14注、组三和值4注、组六和值10注
	 *      
 	 */
 	public static int getPLSHezhiMoney(String betcode,int multiple,String playName){
 		//调用算注数的方法得到注数
		int zhushu=getPLSHezhiZhushu(betcode, playName);
		
	    //算得总金额后返回(总金额 = 注数*倍数*单价(单张彩票的金额))
		return multiple * zhushu * Constant.LOTTERY_PRICE;
 	}
 	
 	/**
	 * 
	 *      排列三直选复式注码解析得注数
	 * @param 
	 * 		betcode 注码
	 *      示例 注码:1,2-2,3-3,4
	 * @param 
	 * 		qhTab 百位、十位、个位之间的分隔符 示例中为"-"
	 * @param
	 * 		sign 注码之间的分隔符 示例中为","
	 * @return
	 * 		注数 示例中为8注
	 * 
	 */
 	public static int getPLSDirectDoubleZhushu(String betcode,String qhTab,
 			String sign){
 		
 		//根据百位、十位、个位的分隔符qhTab分解百、十、个位
		String arrCode[] = betcode.split("\\"+qhTab);
		
		//分别得到百位、十位、个位的注码数组
		String hundredCode[] = arrCode[0].split("\\"+sign);
		String tenCode[] = arrCode[1].split("\\"+sign);
		String unitCode[] = arrCode[2].split("\\"+sign);
		
		//将百位、十位、个位的个数相乘就是注码的注数
		int zhushu = hundredCode.length * tenCode.length * unitCode.length; 

		return zhushu ;
 	}
 	
 	/**
	 * 
	 *      排列三直选复式注码解析得金额
	 * @param 
	 * 		betcode 注码
	 *      示例 注码:1,2-2,3-3,4
	 * @param 
	 * 		multiple 倍数 为1倍
	 * @param 
	 * 		qhTab 百位、十位、个位之间的分隔符 示例中为"-"
	 * @param
	 * 		sign 注码之间的分隔符 示例中为","
	 * @return
	 * 		总金额 = 注数*倍数*单价(单张彩票的金额)
	 *      示例中为16元
	 *      
	 */
 	public static int getPLSDirectDoubleMoney(String betcode,int multiple,
 			String qhTab,String sign){
		
		//将百位、十位、个位的个数相乘就是注码的注数
		int zhushu = getPLSDirectDoubleZhushu(betcode, qhTab, sign); 
		
		//算总金额 = 注数*倍数*单价(单张彩票的金额)并返回
		return zhushu * multiple * Constant.LOTTERY_PRICE;
 	}
 	
 	/**
	 * 
	 *      排列三直选复式注码解析得金额
	 * @param 
	 * 		hundreds 百位
	 *      示例 注码:百位1,2
	 * @param 
	 * 		tens 十位
	 *      示例 注码:十位2,3 
	 * @param 
	 * 		units 个位
	 *      示例 注码:个位3,4
	 * @param
	 * 		sign 注码之间的分隔符 示例为","
	 * @return
	 * 		总金额 = 注数*倍数*单价(单张彩票的金额)
	 *      示例中为8注
	 *      
	 */
 	public static int getPLSDirectDoubleZhushu1(String hundreds,String tens,String units,String sign){
 		//分别得到百位、十位、个位的注码数组
		String hundredCode[] = hundreds.split("\\"+sign);//百位
		String tenCode[] = tens.split("\\"+sign);//十位
		String unitCode[] = units.split("\\"+sign);//个位
		
		//将百位、十位、个位的个数相乘就是注码的注数
		int zhushu = hundredCode.length * tenCode.length * unitCode.length; 
		
		return zhushu ;
 	}
 	
 	/**
	 * 
	 *      排列三直选复式注码解析得金额
	 * @param 
	 * 		hundreds 百位
	 *      示例 注码:百位1,2
	 * @param 
	 * 		tens 十位
	 *      示例 注码:十位2,3
	 * @param 
	 * 		units 个位
	 *      示例 注码:个位3,4
	 * @param 
	 * 		multiple 倍数为1倍
	 * @param
	 * 		sign 注码之间的分隔符 示例为","
	 * @return
	 * 		总金额 = 注数*倍数*单价(单张彩票的金额)
	 *      示例中为16元
	 *      
	 */
 	public static int getPLSDirectDoubleMoney1(String hundreds,String tens,String units,int multiple,String sign){
 		//分别得到百位、十位、个位的注码数组
		String hundredCode[] = hundreds.split("\\"+sign);//百位
		String tenCode[] = tens.split("\\"+sign);//十位
		String unitCode[] = units.split("\\"+sign);//个位
		
		//将百位、十位、个位的个数相乘就是注码的注数
		int zhushu = hundredCode.length * tenCode.length * unitCode.length; 
		
		//算总金额 = 注数*倍数*单价(单张彩票的金额)并返回
		return zhushu * multiple * Constant.LOTTERY_PRICE;
 	}
 	
 	/**
	 * 
	 *      排列三组三包号、组六包号注码解析得金额
	 * @param
	 * 		playName 玩法名称 7-组三包号、8-组六包号
	 * @param 
	 * 		betcode 注码
	 *      示例:注码:组三包号:1,2,3  组六包号:1,2,3,4  
	 * @param
	 * 		sign 注码之间的分隔符 示例中为","
	 * @return
	 * 		注数  示例:组三包号6注   组六包号8注
	 * 
	 */
	public static int getPLSDirectAndGroupZhushu(String betcode,String sign,String playName){
		
		//根据注码之间的分隔符sign分隔注码
		String arrCode[] = betcode.split("\\"+sign);
		int zhushu = 0;
		//根据不同的玩法算金额
		if(playName.equals(Constant.PLS_ZSBH)){
			//组三包号
			zhushu=BetcodeResolveUtil.getGroup3MultipleNumber(arrCode.length);
			
		}else if(playName.equals(Constant.PLS_ZLBH)){
			//组六包号
			zhushu = BetcodeResolveUtil.getGroup6MultipleNumber(arrCode.length);
		}
		return zhushu;
	}
 	
 	/**
	 * 
	 *      排列三组三包号、组六包号注码解析得金额 
	 * @param
	 * 		playName 玩法名称 7-组三包号、8-组六包号
	 * @param 
	 * 		betcode 注码
	 *      示例:注码:组三包号:1,2,3  组六包号:1,2,3,4 
	 * @param 
	 * 		multiple 倍数为1倍
	 * @param
	 * 		sign 注码之间的分隔符 示例中为","
	 * @return
	 * 		总金额 = 注数*倍数*单价(单张彩票的金额)
	 *      示例:组三包号12元 组六包号16元
	 *      
	 */
	public static int getPLSDirectAndGroupMoney(String betcode,int multiple,String sign,String playName){
		//调用算注数的方法算得注数
		int zhushu = getPLSDirectAndGroupZhushu(betcode, sign, playName);
		
		//总金额 = 注数*倍数*单价(单张彩票的金额)
		return zhushu * multiple * Constant.LOTTERY_PRICE;
	}
}
