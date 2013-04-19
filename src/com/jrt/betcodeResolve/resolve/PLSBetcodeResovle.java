package com.jrt.betcodeResolve.resolve;

import com.jrt.betcodeResolve.util.Constant;

/**
 * 
 * 		排列三的注码解析
 * @author
 * 		徐丽
 * 
 */
public class PLSBetcodeResovle {
	/**
	 * 
	 * 		排列三注码解析
	 * @param 
	 * 		betcode 注码 示例:注码:1,2,3
	 * @param 
	 * 		playName 玩法名称
	 * 		(01-直选、06-组选、S1-直选和值、S9-组选和值、S3-组三和值、S6-组六和值、F3-组三包号、F6-组六包号)
	 * @param 
	 * 		sign 传过来注码之间的分隔符 示例中为";"
	 * @return 
	 * 		传给大赢家的注码 
	 * 		 示例:直选:1|1,2,3              组选:6|1,2,3                直选和值:S1|1,2,3
	 *           组选和值:S9|1,2,3   组三和值:S3|1,2,3     组六和值:S6|1,2,3   
	 *           组三包号:F3|1,2,3   组六包号:F6|1,2,3
	 * 
	 */
	public static String getPLSBetcode(String betcode, String playName,
			String sign,String tcTabNumber) {
		String str = "";
		if(betcode.indexOf(tcTabNumber)>-1){
			String codes[]=betcode.split("\\"+tcTabNumber);
			for(int i=0;i<codes.length;i++){
				str+=codes[i]+Constant.TC_TABNUMBER;
			}
			str = str.substring(0, str.length()-1);
		}
		
		// 直选1
		if (Constant.PLS_ZHX.equals(playName))
			str = Constant.PLS_ZHX.substring(1) + Constant.PLS_FGF + betcode.replace(sign, Constant.SIGN);

		// 组选2
		else if (Constant.PLS_ZX.equals(playName))
			str = Constant.PLS_ZX.substring(1) + Constant.PLS_FGF + betcode.replace(sign, Constant.SIGN);
		// 直选和值3
		else if (Constant.PLS_ZHXHZ.equals(playName))
			str = Constant.PLS_ZHXHZ + Constant.PLS_FGF + betcode.replace(sign, Constant.SIGN);
		// 组选和值4
		else if (Constant.PLS_ZXHZ.equals(playName))
			str = Constant.PLS_ZXHZ + Constant.PLS_FGF + betcode.replace(sign, Constant.SIGN);
		// 组三和值5
		else if (Constant.PLS_ZSHZ.equals(playName))
			str = Constant.PLS_ZSHZ + Constant.PLS_FGF + betcode.replace(sign, Constant.SIGN);
		// 组六和值6
		else if (Constant.PLS_ZLHZ.equals(playName))
			str = Constant.PLS_ZLHZ + Constant.PLS_FGF + betcode.replace(sign, Constant.SIGN);
		// 组三包号7
		else if (Constant.PLS_ZSBH.equals(playName))
			str = Constant.PLS_ZSBH + Constant.PLS_FGF + betcode.replace(sign, "");
		// 组六包号8
		else if (Constant.PLS_ZLBH.equals(playName))
			str = Constant.PLS_ZLBH + Constant.PLS_FGF + betcode.replace(sign, "");

		return str;
	}
}
