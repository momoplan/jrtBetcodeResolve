package com.jrt.betcodeResolve.resolve;

import com.jrt.betcodeResolve.util.BetcodeResolveUtil;
import com.jrt.betcodeResolve.util.Constant;

/**
 * 
 * 	     大乐透注码解析得到拼接完的注码 
 * 	     玩法包括:单式、复式、胆拖、生肖乐(十二选二)
 * @author
 * 	       徐丽
 * 
 */
public class DLTBetcodeResovle {
	
	/**
	 * 
	 * 		 大乐透注码单式和复式解析得到拼接完的注码 
	 * @param 
	 * 		betcode 注码
	 * 		示例:注码:1,12,15,4,5,6+1,7;10,14,22,25,31+03,09;03,12,15,26,35+03,12;
	 * 			07,10,19,33,34+01,02;09,13,19,22,35+02,09;02,14,15,27,35+03,04
	 * @param 
	 * 		tabNumber 多注之间的分隔符 示例中为";"
	 * @param 
	 * 		sign 注码之间的分隔符 示例中为","
	 * @param 
	 * 		tab 前注码和后注码之间的分隔符 示例中为"+"
	 * @return 
	 * 		拼接完的注码  格式:前区-后区
	 * 		示例:01 12 15 04 05 06-01 07//单式
	 * 
	 */
	public static String getDLTSimple(String betcode,String tabNumber, 
			String sign, String tab) {
		String str = "";
		// 根据分隔符tab分割前注码和后注码
		//根据各注之间的分隔符tabNumber分隔各注
		String ss[] = betcode.split("\\" + tabNumber);
		
		// 循环注码数组并拼接注码
		for (int i = 0; i < ss.length; i++) {
			
			//根据红球和蓝球之间的分隔符tab 分隔蓝球和红球
			String arr[] = ss[i].split("\\"+tab);
			
			// 对注码小于10的补"0"并排序,拼接注码=注码+多注之间的分隔符(";")
			// 调用公共方法在不足10的注码前补"0"
			str += BetcodeResolveUtil.complement(arr[0], sign, " ").trim() 
				+ Constant.QH_TAB
				+ BetcodeResolveUtil.complement(arr[1], sign, " ").trim()
				+ tabNumber;
		}

		return str;
	}
	
	/**
	 * 
	 * 		大乐透注码复式解析得到拼接完的注码 
	 * @param 
	 * 		betcode 注码 示例:注码:1,12,15,4,5,6+1,6,7//复式
	 * @param 
	 * 		sign 注码之间的分隔符 示例中为","
	 * @param 
	 * 		tab 前注码和后注码之间的分隔符 示例中为"+"
	 * @return 
	 * 		拼接完的注码  格式:前区-后区 示例:01 12 15 04 05 06-01 06 07//复式
	 * 
	 */
	public static String getDLTDuplex(String betcode, String sign,
			String tab) {
		String str = "";
		// 根据分隔符tab分割前注码和后注码
		String arr[] = betcode.split("\\" + tab);
		
		// 调用公共方法在不足10的注码前补"0"
		str = BetcodeResolveUtil.complement(arr[0], sign, " ").trim() 
			+ Constant.QH_TAB
			+ BetcodeResolveUtil.complement(arr[1], sign, " ").trim();

		return str;
	}

	/**
	 * 
	 * 		超级大乐透胆拖注码解析得到拼接完的注码
	 * @param 
	 * 		betcode 注码
	 * 		示例:18$8,12,15,19,28+$2,8
	 * 			6,18$8,12,15,19,28+2$7,8,11
	 * @param 
	 * 		sign 注码之间的分隔符  示例中为","
	 * @param 
	 * 		tab 前区与后区的分隔符  示例中为"+"
	 * @param 
	 * 		dtTab 前胆码和前拖码、后胆码和后拖码之间的分隔符 示例中为"$"
	 * @return 
	 * 		拼接完的注码 格式为:前区胆码$前区拖码-后区胆码$后区拖码
	 * 		示例:18$08 12 15 19 28-$02 08
	 * 			 06 18$08 12 15 19 28-02$07 08 11
	 * 
	 */
	public static String getDLTDantuo(String betcode, String sign,
			String tab, String dtTab) {
		String str = "";
		// 根据分隔符tab分割前区和后区
		String array[] = betcode.split("\\" + tab);
		
		// 根据胆码和拖码之间的分隔符dtTab分割前区胆码和前区拖码
		String arrForword[] = array[0].split("\\" + dtTab);
		
		// 根据胆码和拖码之间的分隔符dtTab分割后区胆码和后区拖码
		String arrBack[] = array[1].split("\\" + dtTab);
		
		// 将注码小于10时在注码前补"0" 并拼接注码返回
		str = BetcodeResolveUtil.complement(arrForword[0], sign, " ").trim()
			+ Constant.DT_TAB
			+ BetcodeResolveUtil.complement(arrForword[1], sign, " ").trim() 
			+ Constant.QH_TAB
			+ BetcodeResolveUtil.complement(arrBack[0], sign, " ").trim()
			+ Constant.DT_TAB
			+ BetcodeResolveUtil.complement(arrBack[1], sign, " ").trim();
		return str;
	}

	/**
	 * 
	 * 		超级大乐透胆拖注码解析得到拼接完的注码
	 * @param 
	 * 		forwordDanCode 前区胆码
	 * 		示例: 1.前区胆码:18 
	 * 			  2.前区胆码:6,18
	 * @param 
	 * 		forwordTuocode 前区拖码
	 * 		示例: 1.前区拖码:8,12,15,19,28  
	 * 			  2.前区拖码:8,12,15,19,28
	 * 
	 * @param 
	 * 		backDanCode 后区胆码
	 * 		示例: 1.后区胆码:""
	 * 			  2.后区胆码:2
	 * @param 
	 * 		backTuoCode 后区拖码
	 * 		示例: 1.后区拖码2,8 
	 * 			  2.后区拖码7,8,11
	 * @param 
	 * 		sign 注码之间的分隔符 示例中为","
	 * @return 
	 * 		拼接完的注码 格式为:前区胆码$前区拖码-后区胆码$后区拖码
	 * 		示例:18$08 12 15 19 28-$02 08
	 * 			06 18$08 12 15 19 28-02$07 08 11
	 * 
	 */
	public static String getDLTDantuo1(String forwordDanCode,
			String forwordTuocode, String backDanCode, String backTuoCode,
			String sign) {
		
		// 将注码小于10时在注码前补"0" 并拼接注码返回
		String str = BetcodeResolveUtil.complement(forwordDanCode, sign, " ").trim()
				+ Constant.DT_TAB
				+ BetcodeResolveUtil.complement(forwordTuocode, sign, " ").trim()
				+ Constant.QH_TAB
				+ BetcodeResolveUtil.complement(backDanCode, sign, " ").trim()
				+ Constant.DT_TAB
				+ BetcodeResolveUtil.complement(backTuoCode, sign, " ").trim();
		return str;
	}

	/**
	 * 
	 * 		生肖乐(十二选二)注码解析 
	 * @param 
	 * 		betcode 注码 示例:2,8
	 * @param 
	 * 		sign 注码之间的分隔符 示例中为","
	 * @return 
	 * 		拼接完的注码 示例为02 08
	 * 
	 */
	public static String getAnimalHappy(String betcode, String sign) {
		// 将注码小于10时在注码前补"0"并去掉注码之间的分隔符sign  拼接注码返回
		return BetcodeResolveUtil.complement(betcode, sign, " ").trim();
	}
}
