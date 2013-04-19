package com.jrt.betcodeResolve.resolve;

import com.jrt.betcodeResolve.util.Constant;

/**
 * 
 * 		排列五的注码解析
 * @author
 * 		徐丽
 * 
 */
public class PLWBetcodeResovle {
	/**
	 * 
	 * 		排列五单式注码解析
	 * @param 
	 * 		betcode 注码 示例:注码:1,2,3,5,6;4,5,6,7,8;2,4,5,6,9
	 * @param 
	 * 		sign 传过来注码之间的分隔符 示例中为","
	 * @param
	 * 		tcTabNumber 多注之间的分隔符 示例中为";"
	 * @return 
	 * 		传给大赢家的注码 
	 * 
	 */
	public static String getPLWSimplexBetcode(String betcode, String tcTabNumber,String sign) {
		 //根据传入的注码内容分解所需注码内容
		String str = betcode.replace(sign, Constant.SIGN).replace(tcTabNumber, Constant.TC_TABNUMBER); 
		return str;
	}
	
	/**
	 * 
	 * 		排列五直选复式注码解析
	 * @param 
	 * 		betcode 注码 示例:注码:1,2,8;0,2,3;1,2,8;0,2,3;3,4,9
	 * @param 
	 * 		sign 传过来注码之间的分隔符 示例中为","
	 * @param
	 * 		qhTab 万位、千位、百位、十位、个位之间的分隔符 示例中为";"
	 * @return 
	 * 		传给大赢家的注码 
	 * 
	 */
	public static String getPLWDirectDoubleBetcode(String betcode,String qhTab,String sign){
		String str = betcode.replace(sign, "").replace(qhTab, Constant.SIGN);
		return str;
	}
}
