package com.jrt.betcodeResolve.resolve;

import com.jrt.betcodeResolve.util.Constant;
import com.jrt.betcodeResolve.util.SSCConstant;

/**
 * 
 * 时时彩的注码解析
 * 
 * @author 徐丽
 * 
 */
public class SSCBetcodeResolve {

	/**
	 * 
	 *时时彩单式玩法注码解析
	 * 
	 * @param betcode
	 *            注码 示例：五星：1,2,3,5,6;4,5,6,7,8;4,5,6,7,8
	 *            三星：5,1,8;9,5,6;6,7,8;5,6,9 二星：1,8;5,6;7,8;6,9 一星：8;6;8;9
	 * @param tcTabNumber
	 *            多注之间的分隔符 示例中为";"
	 * 
	 * @param playName
	 *            包括（五星-5D、三星-3D、二星-2D、一星-1D）
	 * @return 拼接完的注码 示例中为: 五星：1,2,3,5,6;4,5,6,7,8;4,5,6,7,8
	 *         三星：-,-,5,1,8;-,-,9,5,6;-,-,6,7,8;-,-,5,6,9
	 *         二星：-,-,-,1,8;-,-,-,5,6;-,-,-,7,8;-,-,-,6,9
	 *         一星：-,-,-,-,8;-,-,-,-,6;-,-,-,-,8;-,-,-,-,9
	 */
	public static String getSSCSimplexBetcode(String betcode,
			String tcTabNumber, String playName,String sign) {

		// 拼接玩法
		String str = playName + Constant.PLS_FGF;

		// 累加玩法和注码拼接
		if (SSCConstant.SSC_WX.equals(playName) || SSCConstant.SSC_WXTX.equals(playName)) {// 五星单式和五星通选单式
			String codes[] = betcode.split("\\"+tcTabNumber);
			for(int i=0;i<codes.length;i++){
				str += codes[i]+Constant.TC_TABNUMBER+playName+Constant.PLS_FGF;
			}
			str = str.substring(0, str.length() - 4);
			
		}else if(SSCConstant.SSC_DXDS.equals(playName)){//大小单双单式
			String codes[] = betcode.split("\\"+tcTabNumber);
			for(int i=0;i<codes.length;i++){
				str+= codes[i].replace(sign, "")+ Constant.TC_TABNUMBER+playName+Constant.PLS_FGF;
			}
			str = str.substring(0, str.length() - 3);
		} else if (SSCConstant.SSC_SX.equals(playName)) {// 三星
			str +="-,-," ;
			String codes[] = betcode.split("\\" + tcTabNumber);
			for (int i = 0; i < codes.length; i++) {
				str += codes[i] + Constant.TC_TABNUMBER+playName+Constant.PLS_FGF+"-,-,";
			}
			str = str.substring(0, str.length() - 8);
		} else if (SSCConstant.SSC_RX.equals(playName)) {// 二星
			str += "-,-,-," ;
			String codes[] = betcode.split("\\" + tcTabNumber);
			for (int i = 0; i < codes.length; i++) {
				str += codes[i] + Constant.TC_TABNUMBER + playName + Constant.PLS_FGF+"-,-,-,";
			}
			str = str.substring(0, str.length() - 10);
		} else if (SSCConstant.SSC_YX.equals(playName)) {// 一星
			str += "-,-,-,-," ;
			String codes[] = betcode.split("\\" + tcTabNumber);
			for (int i = 0; i < codes.length; i++) {
				str += codes[i] + Constant.TC_TABNUMBER + playName + Constant.PLS_FGF+"-,-,-,-,";;
			}
			str = str.substring(0, str.length() - 12);
		}
		
		return str;
	}

	/**
	 * 
	 * 时时彩直选复式注码解析
	 * 
	 * @param betcode
	 *            注码 示例：五星：1,2,8-0,2,3-1,2,8-0,2,3-3,4,9 三星：1,2,5-1,3-8,4
	 *            二星：1,2,5-1,3 一星：1,2,5 大小单双： 1,2,4-1,2
	 * @param sign
	 *            注码之间的分隔符 示例中为","
	 * @param qhTab
	 *            各位之间的分隔符 示例中为"-"
	 * @param playName
	 *            玩法 包括（五星-5D、三星-3D、二星-2D、一星-1D、大小单双-DD）
	 * @return 拼接好的注码
	 */
	public static String getSSCDirectDoubleBetcode(String betcode, String sign,
			String tcTabNumber, String qhTab, String playName) {
		String str ="";
		if(playName.equals(SSCConstant.SSC_DXDS)){
			str=playName + Constant.PLS_FGF+betcode.replace(sign, "").replace(qhTab, Constant.SIGN);
		}else{
			// 将注码替换为所需要的注码值
			str = betcode.replace(sign, "").replace(qhTab, Constant.SIGN);
	
			// 根据玩法调用单式算法得到直选复式的注码并返回
			str = getSSCSimplexBetcode(str, tcTabNumber, playName,sign);
		}	

		return str;
	}

	/**
	 * 
	 * 二星直选和值、组选和值、组选复式玩法解析注码
	 * 
	 * @param betcode
	 *            注码 示例：5,6,7,8
	 * @param playName
	 *            （直选和值-H2，组选和值-S2，直选复式-F2）
	 * @param sign
	 *            注码之间的分隔符示例中为","
	 * @return 返回拼接完的注码
	 */
	public static String getSSCRXBetcode(String betcode, String sign,
			String playName) {
		// 二星直选和值、组选和值玩法
		String str = playName + Constant.PLS_FGF
				+ betcode.replace(sign, Constant.SIGN);

		// 二星组选复式玩法去除逗号sign
		if (playName.equals(SSCConstant.SSC_EXZXFS)) {
			str = str.replace(sign, "");
		}
		return str;
	}
}
