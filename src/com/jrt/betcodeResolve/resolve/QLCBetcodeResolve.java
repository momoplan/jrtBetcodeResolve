package com.jrt.betcodeResolve.resolve;

import com.jrt.betcodeResolve.util.BetcodeResolveUtil;
import com.jrt.betcodeResolve.util.Constant;

/**
 * 
 * 	     福彩七乐彩投注注码解析得到需要拼接传到后台的注码
 * @author 
 * 			徐丽
 * 
 */
public class QLCBetcodeResolve {
	/**
	 * 
	 * 		七乐彩单式注码解析
	 * @param
	 * 		multiple 倍数为2倍
	 * @param
	 * 		betcode 注码
	 * 		示例:注码 1,7,2,6,3,4,5^8,13,9,10,11,12,14^
	 * 				 15,20,16,17,18,19,21^22,24,23,25,26,27,28^
	 * 				 32,29,30,31,33,34,35^
	 * @param
	 * 		tabNumber 多注分隔符（单式有） 示例中为"^"
	 * @param
	 * 		sign 传过来注码的格式标记符 示例中为","
	 * @return
	 * 		拼接完的注码=玩法+倍数+注码+单注之间的分隔符"^"
	 *      示例:000201020304050607^000208091011121314^
	 *      	000215161718192021^000222232425262728^
	 *      	000229303132333435^
	 * 
	 */
	public static String getQLCSimplex(int multiple, String betcode, String tabNumber,
			String sign) {
		String str = "";
		// 倍数小于10的在其前补"0"
		String bs = ((multiple < 10) ? "0" : "") + multiple;
		
		//根据各注之间的分隔符tabNumber分隔各注
		String ss[] = betcode.split("\\" + tabNumber);
		
		// 循环注码数组并拼接注码
		for (int i = 0; i < ss.length; i++) {
			// 对注码小于10的补"0"并排序,拼接注码=玩法+倍数+注码+单注之间的分隔符"^"
			str += Constant.QLC_ZXDS
					+ bs
					+ BetcodeResolveUtil.extractString(BetcodeResolveUtil
							.complement(ss[i], sign, "").replace(sign, ""))
					+ Constant.TABNUMBER;
		}
		return str;
	}

	/**
	 * 
	 * 		七乐彩复式注码解析
	 * @param
	 * 		 multiple 倍数 为2倍
	 * @param
	 * 		 betcode 注码
	 * 	           示例：注码1,7,2,6,3,4,5,8,9,10
	 * @param
	 * 		 sign 传过来注码的格式标记符 示例中为","
	 * @return
	 * 		   拼接完的注码=玩法+倍数+"*"+注码+"^"
	 *  	  示例:1002*01020304050607080910^
	 * 
	 */
	public static String getQLCDuplex(int multiple, String betcode, String sign) {
		// 玩法
		String str = Constant.QLC_ZXFS;
		
		// 倍数小于10的在其前补"0"
		String bs = ((multiple < 10) ? "0" : "") + multiple;
		
		// 对注码小于10的补"0"并排序,拼接注码=玩法+倍数+"*"+注码+"^"
		str += bs
				+ Constant.REDTAB
				+ BetcodeResolveUtil.extractString(BetcodeResolveUtil
						.complement(betcode, sign, "").replace(sign, "")) 
				+ Constant.TABNUMBER;
		return str;
	}
	
	/**
	 * 
	 * 		七乐彩胆拖注码解析
	 * @param 
	 * 		multiple 倍数为2倍
	 * @param
	 * 		betcode 注码
	 * 		示例:"1,3,2*4,6,8,7,5,9,10";
	 * @param
	 * 		 redTab 胆码和拖码之间的分隔符 示例中为"*" 
	 * @param 
	 * 		 sign 传过来注码的格式标记符
	 * @return 
	 * 		拼接完的注码=玩法+倍数+胆码+*+拖码+"^"
	 * 		示例:2002010203*04050607080910^
	 * 
	 */	
	public static String getQLCDanTuo(int multiple, String betcode, String redTab,
			String sign) {
		// 玩法
		String str = Constant.QLC_ZXDT;
		// 倍数小于10的在其前补"0"
		String bs = ((multiple < 10) ? "0" : "") + multiple;
		
		//根据胆码和拖码之间的分隔符redTab 分隔胆码和拖码
		String codes[] = betcode.split("\\"+redTab);
		
		// 对胆码和拖码小于10的补"0"并排序,拼接注码=玩法+倍数+胆码+*+拖码+"^"
		str += bs
				+ BetcodeResolveUtil.extractString(BetcodeResolveUtil
						.complement(codes[0], sign, "").replace(sign, ""))
				+ Constant.REDTAB
				+ BetcodeResolveUtil.extractString(BetcodeResolveUtil
						.complement(codes[1], sign, "").replace(sign, ""))
				+ Constant.TABNUMBER;
		return str;
	}

	/**
	 *  
	 * 		七乐彩胆拖注码解析
	 * @param
	 * 		multiple 倍数为2倍
	 * @param
	 * 		bileCode 胆码
	 * 		示例:胆码:1,3,2 
	 * @param
	 * 		dragCode 拖码
	 * 		示例:拖码:4,6,8,7,5,9,10
	 * @param
	 * 		sign 传过来注码的之间分隔符 示例中为","
	 * @return
	 * 		拼接完的注码=玩法+倍数+胆码+*+拖码+"^"
	 * 		示例:2002010203*04050607080910^
	 * 
	 */
	public static String getQLCDanTuo1(int multiple, String bileCode, String dragCode,
			String sign) {
		// 玩法
		String str = Constant.QLC_ZXDT;
		// 倍数小于10的在其前补"0"
		String bs = ((multiple < 10) ? "0" : "") + multiple;
		
		// 对胆码和拖码小于10的补"0"并排序,拼接注码=玩法+倍数+胆码+*+拖码+"^"
		str += bs
				+ BetcodeResolveUtil.extractString(BetcodeResolveUtil
						.complement(bileCode, sign, "").replace(sign, ""))
				+ Constant.REDTAB
				+ BetcodeResolveUtil.extractString(BetcodeResolveUtil
						.complement(dragCode, sign, "").replace(sign, ""))
				+ Constant.TABNUMBER;
		return str;
	}
}
