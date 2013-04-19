package com.jrt.betcodeResolve.lotnoBetcodeUtil;

import com.jrt.betcodeResolve.util.BetcodeResolveUtil;
import com.jrt.betcodeResolve.util.Constant;

/**
 * 
 * 		七乐彩注码解析获取金额、注数、玩法
 * @author
 * 		徐丽
 */
public class QLCBetcodeUtil {
	
	/**
	 * 
	 *      七乐彩单式解析注码算注数
	 * @param 
	 * 		betcode 注码
	 *      例:注码:1,7,2,6,3,4,5^8,13,9,10,11,12,14^15,20,16,17,18,19,21^
	 * @param 
	 * 		tabNumber 注数之间分隔符 示例中为"^"
	 * @return    
	 * 		注数 示例中为3注
	 * 
	 */
	public static int getQLCSimplexZhushu(String betcode,String tabNumber){
		//根据注数之间的分隔符tabNumber分隔注码
		String arrCode[] = betcode.split("\\"+tabNumber);
		
		//得到注数并返回
		return arrCode.length ;
	}
	
	/**
	 * 
	 *      七乐彩单式解析注码算金额
	 * @param 
	 * 		betcode 注码
	 *      例:注码:1,7,2,6,3,4,5^8,13,9,10,11,12,14^15,20,16,17,18,19,21^
	 * @param 
	 * 		multiple 倍数 为1倍
	 * @param 
	 * 		tabNumber 注数之间分隔符 示例中为"^"
	 * @return    
	 * 		总金额 = 注数*倍数*单价(单张彩票的金额)
	 * 		示例为6元
	 * 
	 */
	public static int getQLCSimplexMoney(String betcode,int multiple,String tabNumber){
		
		//调用算注数的方法得到彩票的注数
		int zhushu = getQLCSimplexZhushu(betcode, tabNumber);
		
		//算总金额 = 注数*倍数*单价(单张彩票的金额)
		return zhushu * multiple * Constant.LOTTERY_PRICE;

	}
	
	/**
	 * 
	 *      七乐彩复式注码解析算注数
	 * @param 
	 * 		betcode 注码
	 *      示例:注码:1,7,2,6,3,4,5,8,9,10
	 * @param 
	 * 		sign 注码之间分隔符 示例中为","
	 * @return    
	 * 		注数 示例中为120注
	 * 
	 */
	public static int getQLCDuplexZhushu(String betcode, String sign){
		//根据注码之间的分隔符sign分隔注码
		String arrCode[] = betcode.split("\\"+sign);
		
		//调用七乐彩算注数的方法得到注数并返回
		int zhushu = BetcodeResolveUtil.getQilecaiNumber(arrCode.length);
		return zhushu;
	}
	
	/**
	 * 
	 *      七乐彩复式投注算金额
	 * @param 
	 * 		betcode 注码
	 *      示例:注码:1,7,2,6,3,4,5,8,9,10
	 * @param 
	 * 		multiple 倍数为1倍
	 * @param 
	 * 		sign 注码之间分隔符 示例中为","
	 * @return 
	 * 		总金额 = 注数*倍数*单价(单张彩票的金额)
	 * 		示例为240元
	 * 
	 */
	public static int getQLCDuplexMoney(String betcode,int multiple,String sign){
		
		//调用算注数的方法得到七乐彩的注数
		int zhushu = getQLCDuplexZhushu(betcode, sign);
		//算金额= 注数*倍数*单价(单张彩票的金额)
		return zhushu * multiple * Constant.LOTTERY_PRICE;
	}
	
	
	/**
	 * 
	 *      七乐彩胆拖投注解析注码算金额
	 * @param 
	 * 		betcode 注码
	 *      示例:注码:"1,3,2*4,6,8,7,5,9,10"
	 * @param 
	 * 		sign 注码之间分隔符 示例中为"," 
	 * @return    
	 * 		注数 示例中为35注
	 * 
	 */
	public static int getQLCDanTuoZhushu(String betcode,String redTab,String sign){
		
		//根据胆码和拖码之间的分隔符分隔胆码和拖码
		String arrCodes[] = betcode.split("\\"+redTab);
		
		//分别解析胆码和拖码得到胆码、拖码的个数
		String danmacode[] = arrCodes[0].split("\\" + sign);
		String tuomacode[] = arrCodes[1].split("\\" + sign);
		
		//调用七乐彩胆拖算注数的方法得到注数并返回
		int zhushu = BetcodeResolveUtil.getQilecaiDantuoNumber(danmacode.length, tuomacode.length);
		return zhushu ;
	}
	
	/**
	 * 
	 *      七乐彩胆拖投注解析注码算金额
	 * @param 
	 * 		betcode 注码
	 *      示例:注码:"1,3,2*4,6,8,7,5,9,10"
	 * @param 
	 * 		multiple 倍数 为1倍
	 * @param
	 * 		redTab 胆码和拖码之间的分隔符 示例中为"*"
	 * @param 
	 * 		sign 注码之间分隔符 示例中为"," 
	 * @return    
	 * 		总金额 = 注数*倍数*单价(单张彩票的金额)
	 * 		示例为70元
	 * 
	 */
	public static int getQLCDanTuoMoney(String betcode,int multiple,String redTab,String sign){
		
		//调用七乐彩胆拖算注数的方法得到注数
		int zhushu = getQLCDanTuoZhushu(betcode, redTab, sign);
		//算金额 = 注数*倍数*单价(单张彩票的金额)
		return zhushu * multiple * Constant.LOTTERY_PRICE;
	}
	
	
	/**
	 * 
	 *      七乐彩胆拖投注解析注码算金额
	 * @param 
	 * 		danma 胆码
	 *      示例:胆码:"1,3,2"
	 * @param 
	 * 		tuoma 拖码
	 *      示例:拖码:"4,6,8,7,5,9,10"
	 * @param 
	 * 		sign 注码之间分隔符 示例中为"," 
	 * @return    
	 * 		注数 示例为35元
	 * 
	 */
	public static int getQLCDanTuoZhushu1(String danma,String tuoma,String sign){
		
		//分别解析胆码和拖码得到胆码、拖码的个数
		String danmacode[] = danma.split("\\" + sign);
		String tuomacode[] = tuoma.split("\\" + sign);
		
		//调用七乐彩算注数的方法的注数
		int zhushu = BetcodeResolveUtil.getQilecaiDantuoNumber(danmacode.length, tuomacode.length);
		return zhushu;
	}
	
	/**
	 * 
	 *      七乐彩胆拖投注解析注码算金额
	 * @param 
	 * 		danma 胆码
	 *      示例:胆码:"1,3,2"
	 * @param 
	 * 		tuoma 拖码
	 *      示例:拖码:"4,6,8,7,5,9,10"
	 * @param 
	 * 		multiple 倍数 为1倍
	 * @param 
	 * 		sign 注码之间分隔符 示例中为"," 
	 * @return    
	 * 		总金额 = 注数*倍数*单价(单张彩票的金额)
	 * 		示例为70元
	 * 
	 */
	public static int getQLCDanTuoMoney1(String danma,String tuoma,int multiple,String sign){

		//调用七乐彩胆拖算注数的方法得到注数
		int zhushu = getQLCDanTuoZhushu1(danma, tuoma, sign);
		
		//算金额 = 注数*倍数*单价(单张彩票的金额)
		return zhushu * multiple * Constant.LOTTERY_PRICE;
	}
	
	/**
	 * 
	 *  根据注码得到七乐彩的玩法 
	 * @param betcode
	 *            注码例如:1,7,2,6,3,4,5^8,13,9,10,11,12,14^
	 *      15,20,16,17,18,19,21^22,24,23,25,26,27,28^
	 *      32,29,30,31,33,34,35^22,24,23,25,26,27,28^//单式
	 *      1,7,2,6,3,4,5,8,9,10^//复式
	 *      1,3,2*4,6,8,7,5,9,10^//胆拖
	 * @param tabNumber
	 *            注码之间分隔符 例子中为"^"
	 * @param sign
	 *            注码之间的分隔符 例子中为","
	 * @param redTab
	 *            胆码与拖码之间的分隔符 例子中为"*"
	 *            
	 * @return 返回玩法 例:单式:"00"; 复式:"10"; 胆拖:"20"; 
	 * 
	 */
	public static String getQLCGameMethod(String betcode,String tabNumber,String sign,String redTab){
		String gameMethod = "";
		String codes[] = betcode.split("\\"+tabNumber);
		for(int i=0;i<codes.length;i++){
			//判断是胆码还是单式或者复式
			if(codes[i].indexOf(redTab) == -1){
				// 根据注码之间的分隔符sign 
				String ss1[] = codes[i].split("[" + sign + "]");
	
				// 判断注码的长度七乐彩具体玩法如下：
				// 单式
				if (ss1.length == 7) {
					gameMethod = Constant.QLC_ZXDS;
	
				} else {
					// 得到复式注码
					gameMethod= Constant.QLC_ZXFS;
				}
			}else{
				//得到胆拖与复式拼接
				gameMethod = Constant.QLC_ZXDT;
			}
		}
		return gameMethod;
	}
}
