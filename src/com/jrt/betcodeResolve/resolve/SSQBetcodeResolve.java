package com.jrt.betcodeResolve.resolve;

import com.jrt.betcodeResolve.util.BetcodeResolveUtil;
import com.jrt.betcodeResolve.util.Constant;
import com.jrt.betcodeResolve.util.ValidateBetcodeUtil;

/**
 * 
 * 解析双色球注码的类 玩法包括-红单蓝单、红复蓝单、红单蓝复、红胆拖蓝单、红胆拖蓝复
 * 
 * @author 徐丽
 * 
 */
public class SSQBetcodeResolve {

	/**
	 * 
	 * 解析双色球单式（红单蓝单）玩法的注码
	 * 
	 * @param multiple
	 *            倍数
	 * @param betcode
	 *            注码 例:2,1,3,5,4,6+1^7,10,8,12,9,11+2^1,6,15,26,32,2+2^
	 * @param tabNumber
	 *            多注分隔符（单式有）例子中为"^"
	 * @param sign
	 *            传过来注码的格式标记符 例子中为","
	 * @param tab
	 *            红球和蓝球中间的分隔符 例子中为"+"
	 * @return 拼接完的注码
	 *         例:0002010203040506~01^0002070809101112~02^0002010206152632~02^
	 * 
	 */
	public static String getSSQSimplex(int multiple, String betcode,
			String tabNumber, String tab, String sign) {
		String stb = "";
		// 当倍数小于0的时在倍数前补"0"
		String bs = ((multiple < 10) ? "0" : "") + multiple;

		// 根据多注分隔符解析注码循环生成单式注码
		String ss[] = betcode.split("\\" + tabNumber);
		for (int i = 0; i < ss.length; i++) {
			// 玩法与倍数拼接
			stb += Constant.SSQ_RSBS + bs;
			// 替换标识符提取红球排序
			String strArray[] = ss[i].split("\\" + tab);

			String redBall = BetcodeResolveUtil.complement(strArray[0], sign,
					"").replace(sign, "");// 红球
			String blueBall = BetcodeResolveUtil.complement(strArray[1], sign,
					"").replace(sign, "");// 蓝球

			// 验证红球和蓝球
			if (ValidateBetcodeUtil
					.verifySSQRedBall(redBall, Constant.SSQ_RSBS)
					&& ValidateBetcodeUtil.verifySSQBlueBall(blueBall,
							Constant.SSQ_RSBS)) {
				// 红球调用排序的方法拼接蓝球
				String code = BetcodeResolveUtil.extractString(redBall)
						+ Constant.TAB + blueBall;
				stb += code + Constant.TABNUMBER;
			}
		}
		return stb;
	}

	/**
	 * 
	 * 双色球红复蓝单注码
	 * 
	 * @param multiple
	 *            倍数
	 * @param betcode
	 *            注码 例:1,6,2,5,3,4,9,7,8,10+1
	 * @param tab
	 *            红球和蓝球中间的分隔符 例子中为"+"
	 * @param sign
	 *            传过来注码的格式标记符 例子中为","
	 * @return 拼接完的注码 例:1002*01020304050607080910~01^
	 * 
	 */
	public static String getSSQRedDuplexBlueSimplex(int multiple,
			String betcode, String tab, String sign) {
		// 得到玩法
		String str = Constant.SSQ_RMBS;
		// 算倍数
		str += ((multiple < 10) ? "0" : "") + multiple + "*";

		// 根据红球和蓝球之间的分隔符tab分开红球和蓝球
		String strArray[] = betcode.split("\\" + tab);

		String redBall = BetcodeResolveUtil.complement(strArray[0], sign, "")
				.replace(sign, "");// 红球
		String blueBall = BetcodeResolveUtil.complement(strArray[1], sign, "")
				.replace(sign, "");// 蓝球

		// 验证红球和蓝球
		if (ValidateBetcodeUtil.verifySSQRedBall(redBall, Constant.SSQ_RMBS)
				&& ValidateBetcodeUtil.verifySSQBlueBall(blueBall,
						Constant.SSQ_RMBS)) {

			// 注码拼接包括玩法倍数累加
			str += BetcodeResolveUtil.extractString(redBall) + Constant.TAB
					+ blueBall + Constant.TABNUMBER;
		}
		return str;
	}

	/**
	 * 
	 * 双色球红单蓝复玩法注码解析
	 * 
	 * @param multiple
	 *            倍数
	 * @param betcode
	 *            注码 例:11,2,6,3,4,5+1,3,2
	 * @param tab
	 *            红球和蓝球中间的分隔符 例子中为"+"
	 * @param sign
	 *            传过来注码的格式标记符 例子中为","
	 * @return 拼接完的注码 例:2002*020304050611~010203^
	 * 
	 */
	public static String getSSQRedSimplexBlueDuplex(int multiple,
			String betcode, String tab, String sign) {
		// 玩法
		String str = Constant.SSQ_RSBM;

		// 倍数
		str += ((multiple < 10) ? "0" : "") + multiple + "*";

		// 根据红球和蓝球之间的分隔符tab分开红球和蓝球
		String strArray[] = betcode.split("\\" + tab);

		String redBall = BetcodeResolveUtil.complement(strArray[0], sign, "")
				.replace(sign, "");// 红球
		String blueBall = BetcodeResolveUtil.complement(strArray[1], sign, "")
				.replace(sign, "");// 蓝球

		// 验证红球和蓝球
		if (ValidateBetcodeUtil.verifySSQRedBall(redBall, Constant.SSQ_RSBM)
				&& ValidateBetcodeUtil.verifySSQBlueBall(blueBall,
						Constant.SSQ_RSBM)) {

			// 注码拼接包括玩法倍数累加
			str += BetcodeResolveUtil.extractString(redBall) + Constant.TAB
					+ BetcodeResolveUtil.extractString(blueBall)
					+ Constant.TABNUMBER;
		}
		return str;
	}

	/**
	 * 
	 * 双色球红复蓝复玩法注码解析
	 * 
	 * @param multiple
	 *            倍数
	 * @param betcode
	 *            注码 例:1,5,2,3,9,4,6,7+1,3,2
	 * @param tab
	 *            红球和蓝球中间的分隔符 例子中为"+"
	 * @param sign
	 *            传过来注码的格式标记符 例子中为","
	 * @return 拼接完的注码 例:3002*0102030405060709~010203^
	 * 
	 */
	public static String getSSQRedDuplexBlueDuplex(int multiple,
			String betcode, String tab, String sign) {
		// 玩法
		String str = Constant.SSQ_RMBM;

		// 倍数
		str += ((multiple < 10) ? "0" : "") + multiple + "*";

		// 根据红球和蓝球之间的分隔符tab分开红球和蓝球
		String strArray[] = betcode.split("\\" + tab);

		String redBall = BetcodeResolveUtil.complement(strArray[0], sign, "")
				.replace(sign, "");// 红球
		String blueBall = BetcodeResolveUtil.complement(strArray[1], sign, "")
				.replace(sign, "");// 蓝球

		// 验证红球和蓝球
		if (ValidateBetcodeUtil.verifySSQRedBall(redBall, Constant.SSQ_RMBM)
				&& ValidateBetcodeUtil.verifySSQBlueBall(blueBall,
						Constant.SSQ_RMBM)) {

			// 注码拼接包括玩法倍数累加
			str += BetcodeResolveUtil.extractString(redBall) + Constant.TAB
					+ BetcodeResolveUtil.extractString(blueBall)
					+ Constant.TABNUMBER;
		}
		return str;
	}

	/**
	 * 
	 * 双色球红胆拖蓝单
	 * 
	 * @param multiple
	 *            倍数
	 * @param betcode
	 *            注码 例：1,4,2,3*5,9,7,16+1
	 * @param redTab
	 *            红球胆码与拖码之间的分隔符 例子中为"*"
	 * @param tab
	 *            红球和蓝球中间的分隔符 例子中为"+"
	 * @param sign
	 *            传过来注码的格式标记符 例子中为","
	 * @return 拼接完的注码 例：400201020304*05070916~01^
	 * 
	 */
	public static String getSSQRedDanTuoBlueSimplex(int multiple,
			String betcode, String tab, String redTab, String sign) {
		String str = Constant.SSQ_RTBS;// 玩法
		str += ((multiple < 10) ? "0" : "") + multiple;// 倍数
		// 根据分隔符tab分隔红球和蓝球
		String strArray[] = betcode.split("\\" + tab);
		// 根据分隔符redTab 对红球胆码和拖码进行分割
		String reds[] = strArray[0].split("\\" + redTab);

		String blueBall = BetcodeResolveUtil.complement(strArray[1], sign, "");// 蓝球

		// 对红球胆码和拖码进行排序去除特色字符得到红球
		String redBalls = BetcodeResolveUtil.extractString(BetcodeResolveUtil
				.complement(reds[0], sign, "").replace(sign, ""))
				+ Constant.REDTAB
				+ BetcodeResolveUtil.extractString(BetcodeResolveUtil
						.complement(reds[1], sign, "").replace(sign, ""));

		// 拼接红球胆码、红球拖码、蓝球注码并且返回
		str += redBalls + Constant.TAB + blueBall + Constant.TABNUMBER;
		return str;
	}

	/**
	 * 
	 * 双色球红胆拖蓝单 - 另一种
	 * 
	 * @param multiple
	 *            倍数
	 * @param redBileCode
	 *            红球胆码 例:红球胆码:1,4,2,3
	 * @param redDragCode
	 *            红球拖码 例:红球拖码:5,9,7,16
	 * @param blueBall
	 *            蓝球 例:蓝球:1
	 * @param sign
	 *            传过来注码的格式标记符 例子中为","
	 * @return 拼接完的注码 例:400201020304*05070916~01^
	 * 
	 */
	public static String getSSQRedDanTuoBlueSimplex1(int multiple,
			String redBileCode, String redDragCode, String blueBall, String sign) {
		// 玩法
		String str = Constant.SSQ_RTBS;

		// 倍数
		str += ((multiple < 10) ? "0" : "") + multiple;

		blueBall = BetcodeResolveUtil.complement(blueBall, sign, "").replace(
				sign, "");

		// 对红球胆码拖码进行排序并且去除特殊字符拼接注码 并返回
		str += BetcodeResolveUtil.extractString(BetcodeResolveUtil.complement(
				redBileCode, sign, "").replace(sign, ""))
				+ Constant.REDTAB
				+ BetcodeResolveUtil.extractString(BetcodeResolveUtil
						.complement(redDragCode, sign, "").replace(sign, ""))
				+ Constant.TAB + blueBall + Constant.TABNUMBER;
		return str;
	}

	/**
	 * 
	 * 双色球红胆拖蓝复 注码解析
	 * 
	 * @param multiple
	 *            倍数
	 * @param betcode
	 *            注码 例:1,2,4,3*5,7,9,6+1,3,2
	 * @param redTab
	 *            红球胆码与拖码之间的分隔符 例子中为"*"
	 * @param tab
	 *            红球和蓝球中间的分隔符 例子中为"+"
	 * @param sign
	 *            传过来注码的格式标记符 例子中为","
	 * @return 拼接完的注码 例:500201020304*05060709~010203^
	 * 
	 */
	public static String getSSQRedDanTuoBlueDuplex(int multiple,
			String betcode, String tab, String redTab, String sign) {
		// 玩法
		String str = Constant.SSQ_RTBM;

		// 倍数
		str += ((multiple < 10) ? "0" : "") + multiple;

		// 根据分隔符tab分隔红球和蓝球
		String strArray[] = betcode.split("\\" + tab);

		// 根据分隔符redTab分隔红球胆码和拖码
		String redBall[] = strArray[0].split("\\" + redTab);

		String blueBall = BetcodeResolveUtil.complement(strArray[1], sign, "");// 蓝球

		// 对红球胆码和拖码进行排序去除特色字符得到红球
		String redBalls = BetcodeResolveUtil.extractString(BetcodeResolveUtil
				.complement(redBall[0], sign, "").replace(sign, ""))
				+ Constant.REDTAB
				+ BetcodeResolveUtil.extractString(BetcodeResolveUtil
						.complement(redBall[1], sign, "").replace(sign, ""));

		// 对红球胆码拖码进行排序并且去除特殊字符拼接注码 并返回
		str += redBalls + Constant.TAB
				+ BetcodeResolveUtil.extractString(blueBall)
				+ Constant.TABNUMBER;
		return str;
	}

	/**
	 * 
	 * 双色球红胆拖蓝复
	 * 
	 * @param multiple
	 *            倍数
	 * @param redBileCode
	 *            红球胆码 例:红球胆码:1,2,4,3
	 * @param redDragCode
	 *            红球拖码 例:红球拖码:5,7,9,6
	 * @param blueBall
	 *            蓝球 例:蓝球:1,3,2
	 * @param sign
	 *            传过来注码的格式标记符 例子中为","
	 * @return 拼接完的注码 例:500201020304*05060709~010203^
	 * 
	 */
	public static String getSSQRedDanTuoBlueDuplex1(int multiple,
			String redBileCode, String redDragCode, String blueBall, String sign) {
		String str = Constant.SSQ_RTBM;// 玩法
		str += ((multiple < 10) ? "0" : "") + multiple;// 倍数

		blueBall = BetcodeResolveUtil.complement(blueBall, sign, "").replace(
				sign, "");// 蓝球

		// 注码拼接
		str += BetcodeResolveUtil.extractString(BetcodeResolveUtil.complement(
				redBileCode, sign, "").replace(sign, ""))
				+ Constant.REDTAB
				+ BetcodeResolveUtil.extractString(BetcodeResolveUtil
						.complement(redDragCode, sign, "").replace(sign, ""))
				+ Constant.TAB
				+ BetcodeResolveUtil.extractString(blueBall)
				+ Constant.TABNUMBER;
		return str;
	}

}
