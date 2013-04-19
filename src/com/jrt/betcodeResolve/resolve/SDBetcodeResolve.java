package com.jrt.betcodeResolve.resolve;

import com.jrt.betcodeResolve.util.BetcodeResolveUtil;
import com.jrt.betcodeResolve.util.Constant;

/**
 * 
 * 		福彩3D投注注码解析得到需要拼接传到后台的注码
 * 		玩法包括直选单式、组选单式、直选和值、组选和值、选按位包号(直选复式)、
 * 		组三复式、组六复式、单选单复式(直选包号)、胆拖复式(单选单胆拖)
 * @author
 * 		徐丽
 * 
 */
public class SDBetcodeResolve {

	/**
	 * 
	 * 		3D单选单式和组选单式注码解析
	 * @param
	 * 		multiple 倍数为1倍
	 * @param
	 * 		playName 玩法名称 (00-代表单选、01-组三、02-组六)
	 * @param
	 * 		tabNumber 多注分隔符（单式和组选有）示例中为"^"
	 * @param
	 * 		betcode 注码
	 * 		例:1,3,2^1,2,2^4,5,6^7,9,8^0,9,1^
	 * @param
	 * 		sign 各个注码之间的分隔符 示例中为","
	 * @return
	 * 		拼接好的注码 :玩法+倍数+实际注码 (小于10的在其前补"0")
	 * 		示例：0102010203^0102010202^0102040506^0102070809^0102000109^
	 * 
	 */
	public static String getSDSimplexAndGroup(int multiple, String playName,
			String betcode, String tabNumber, String sign) {
		String str = "";
		// 倍数小于10的在其前补"0"
		String bs = ((multiple < 10) ? "0" : "") + multiple;
		//根据分隔符tabNumber解析注码
		String ss[] = betcode.split("\\" + tabNumber);
		for (int i = 0; i < ss.length; i++) {
			// 玩法、倍数、注码拼接并返回拼接完的注码
			str += playName
					+ bs
					+ BetcodeResolveUtil.complement(ss[i],playName, sign, "").replace(
							sign, "") + Constant.TABNUMBER;
		}
		return str;
	}

	/**
	 * 
	 * 		3D和值注码解析
	 * @param
	 * 		multiple 倍数 为2倍
	 * @param
	 * 		playName 玩法名称(10-直选和值、11-组三和值、12-组六和值)
	 * @param 
	 * 		betcode 和值数 20
	 * @return 
	 * 		拼接好的注码 :玩法+倍数+和值数
	 * 		示例:直选和值-100220^ 组三和值-200220^ 组六和值-300220^
	 * 
	 */
	public static String getSDHeZhi(int multiple, String playName, String betcode) {
		String str = "";
		// 倍数小于10的在其前补"0"
		String bs = ((multiple < 10) ? "0" : "") + multiple;
		
		//将和值数转换为整数
		int code = Integer.parseInt(betcode);
		
		//直选和值
		if (playName.trim().equals(Constant.SD_ZXHZ)) {
			if(code >= 0 && code <= 27){//(范围:0-27)
				//注码小于10的在其前补"0"
				betcode = ((code < 10) ? "0" : "") + code;
			}
		}
		//组三和值
		if (playName.trim().equals(Constant.SD_ZSHZ)) {
			if(code >= 1 && code <= 26){//(范围:1-26)
				betcode = ((code < 10) ? "0" : "") + code;
			}
		}
		//组六和值
		if (playName.trim().equals(Constant.SD_ZLHZ)) {
			if(code >= 3 && code <= 24){//(范围:3-24)
				betcode = ((code < 10) ? "0" : "") + code;
			}
		}
		//拼接完的注码：玩法+倍数+和值数
		str = playName + bs + betcode + Constant.TABNUMBER;
		return str;
	}

	/**
	 * 
	 * 		福彩3D单选按位包号(直选复式)注码解析
	 * @param
	 * 		multiple 倍数 为2倍
	 * @param
	 * 		betcode 注码
	 * 		例:1,4,2,3,5+6,9,7,8,10,0+1,5,2,3,4,6,7^
	 * @param
	 * 		sign 注码之间的分隔符 示例中为","
	 * @param
	 * 		tab 百位、十位、个位之间的分隔符示例中为"+"
	 * @return
	 * 		 拼接好的注码 2002050102030405+06000607080910+0701020304050607^
	 * 
	 */
	public static String getSDDirectDouble(int multiple, String betcode, String sign,
			String tab) {
		String str = Constant.SD_WXTZ;// 玩法
		// 倍数小于10的在其前补"0"
		String bs = ((multiple < 10) ? "0" : "") + multiple;
		
		// 根据组之间的分隔符tab分隔百位、十位、个位
		String array[] = betcode.split("\\" + tab);
		
		//拼接玩法+倍数
		str += bs;
		
		for (int i = 0; i < array.length; i++) {
			//对注码进行排序
			String code = BetcodeResolveUtil.extractString(BetcodeResolveUtil
					.complement(array[i], sign, "").replace(sign, ""));// 注码去掉注码之间的分隔符sign
			
			// 注码的个数小于10补“0”
			String num = (((code.length() / 2) < 10) ? "0" : "")
					+ (code.length() / 2);
			
			//继续拼接注码:玩法+倍数+注码个数+注码
			str += num + code + Constant.TABNUMBER;
		}
		return str;
	}
	
	/**
	 * 
	 * 		福彩3D单选单复式(直选包号)注码解析
	 * @param
	 * 		multiple 倍数 为2倍
	 * @param 
	 * 		betcode 注码
	 * 		示例:1,4,2,3,5^6,9,7,8,10,0^1,5,2,3,4,6,7^
	 * @param
	 * 		sign 注码之间的分隔 示例中为","
	 * @return
	 * 		 拼接好的注码=玩法+倍数+注码的个数+注码
	 * 		示例:3402050102030405^06000607080910^0701020304050607^
	 * 
	 */
	public static String getSDDirectAndPackageNo(int multiple,String betcode,String sign){
		String wf = Constant.SD_DXDFS;//玩法
		
		// 倍数小于10的在其前补"0"
		String bs = ((multiple < 10) ? "0" : "") + multiple;
		
		// 对注码进行排序
		String code = BetcodeResolveUtil.extractString(BetcodeResolveUtil
				.complement(betcode, sign, "").replace(sign, ""));// 注码去掉各个注码之间的分隔符sign
		
		// 注码的个数小于10补“0”
		String num = (((code.length() / 2) < 10) ? "0" : "")+ (code.length() / 2);
		
		// 拼接的注码:玩法+倍数+注码的个数+注码+"^"
		String str = wf + bs + num + code + Constant.TABNUMBER;
		return str;
	}

	/**
	 * 
	 * 		 福彩3D单选组选复式注码解析
	 * @param
	 * 		multiple 倍数  为2倍
	 * @param
	 * 		playName 玩法(31-组三、32-组六)
	 * @param 
	 * 		betcode 注码
	 * 		示例:2,3,8,4,9,6,7
	 * @param
	 * 		sign 注码分隔符 示例中为","
	 * @return
	 * 		 拼接好的注码
	 * 		示例:组三31020702030406070809^
	 * 
	 */
	public static String getSDSimplexAndGroupDouble(int multiple, String playName,
			String betcode, String sign) {
		String str = "";
		// 倍数小于10的在其前补"0"
		String bs = ((multiple < 10) ? "0" : "") + multiple;
		// 对注码进行排序
		String code = BetcodeResolveUtil.extractString(BetcodeResolveUtil
				.complement(betcode, sign, "").replace(sign, ""));//去掉注码之间的分隔符sign
		
		//注码的个数小于10补“0”
		String num = (((code.length() / 2) < 10) ? "0" : "")
				+ (code.length() / 2);
		
		// 注码结果为玩法+倍数+注码的个数+"^" 
		str += playName + bs + num + code + Constant.TABNUMBER;
		return str;
	}
	
	/**
	 *  
	 *      福彩3D胆拖复式(单选单胆拖)注码解析
	 * @param 
	 * 		danma 胆码 例:拖码:5,4,3
	 * @param 
	 * 		tuoma 拖码 例:胆码:2,1
	 * @param 
	 * 		multiple 倍数 为2倍
	 * @param 
	 * 	    sign 注码之间的分隔符 为","
	 * @return    
	 * 		拼接的注码：
	 * 		示例为:54010102*030405^
	 * 
	 */
	public static String getSDDantuo1(String danma,String tuoma,int multiple,String sign){
		String str = "";
		// 倍数小于10的在其前补"0"
		String bs = ((multiple < 10) ? "0" : "") + multiple;
		
		//玩法
		String wf = Constant.SD_DTFS;
		
		//胆码排序并去掉分隔符sign
		String danmaCode = BetcodeResolveUtil.extractString(BetcodeResolveUtil
				.complement(danma, sign, "").replace(sign, ""));
		
		//拖码排序并去掉分隔符sign
		String tuomaCode = BetcodeResolveUtil.extractString(BetcodeResolveUtil
				.complement(tuoma, sign, "").replace(sign, ""));
		
		//拼接的注码:玩法+倍数+胆码+"*"+拖码+"^"
		str+=wf+bs+danmaCode+Constant.REDTAB+tuomaCode+Constant.TABNUMBER;
		return str;
	}
	
	/**
	 * 
	 *      福彩3D胆拖复式(单选单胆拖)注码解析
	 * @param 
	 * 		multiple 倍数 为 1 倍
	 * @param 
	 * 		betcode 注码 例:2,1*5,4,3
	 * @param 
	 * 	    sign 注码之间的分隔符  示例中为","
	  * @param 
	 * 	    redTab 胆码和拖码之间的分隔符 示例中为"*"
	 * @return    
	 * 		拼接的注码54120102*030405^ 
	 * 
	 */
	public static String getSDDantuo(int multiple,String betcode,String redTab,String sign){
		String str = "";
		// 倍数小于10的在其前补"0"
		String bs = ((multiple < 10) ? "0" : "") + multiple;
		
		//玩法
		String wf = Constant.SD_DTFS;
		
		//根据分隔符胆码和拖码之间的redTab分隔胆码与拖码
		String codes[] = betcode.split("\\"+redTab);
		
		//胆码排序并去掉分隔符sign
		String danmaCode = BetcodeResolveUtil.extractString(BetcodeResolveUtil
				.complement(codes[0], sign, "").replace(sign, ""));
		
		//拖码排序并去掉分隔符sign
		String tuomaCode = BetcodeResolveUtil.extractString(BetcodeResolveUtil
				.complement(codes[1], sign, "").replace(sign, ""));
		
		//拼接的注码:玩法+倍数+胆码+"*"+拖码+"^"
		str+=wf+bs+danmaCode+Constant.REDTAB+tuomaCode+Constant.TABNUMBER;
		return str;
	}
}
