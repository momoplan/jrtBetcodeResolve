package com.jrt.betcodeResolve.util;


/**
 * 
 * 验证类
 * 
 */
public class ValidateBetcodeUtil {

	/**
	 * 
	 * 验证空字符串
	 * 
	 * @param str
	 *            传入的字符串
	 * @return 返回true or false
	 * 
	 */
	public static boolean verifyEmpty(String str) {
		// 如果字符串为null或""不合法
		if (str == null || str.trim() == null || str.trim().equals("")) {
			return false;
		}
		return true;
	}

	/*
	 * 验证是否是全角，如果是全角。 那么转换为半角
	 */
	public static final String QJToBJChange(String QJStr) {
		if (QJStr.length() == QJStr.getBytes().length) {
			return QJStr;
		} else {
			char[] chr = QJStr.toCharArray();
			String str = "";
			for (int i = 0; i < chr.length; i++) {
				if (chr[i] > 65280 && chr[i] < 65375) {
					chr[i] = (char) ((int) chr[i] - 65248);
				}
				str += chr[i];
			}
			return str;
		}
	}

	/**
	 * 
	 * 验证双色球红球注码
	 * 
	 * @param redBall
	 *            红球注码
	 * @param playName
	 *            玩法
	 * @return true or false
	 */
	public static boolean verifySSQRedBall(String redBall, String playName) {

		// 验证红球是否为空字符
		if (!verifyEmpty(redBall)) {
			return false;
		}
		// 红球号码为6个01-33的数字连在一起
		String regex = "";
		// 单式
		if (playName.equals(Constant.SSQ_RSBS)) {
			regex = "(0[1-9]|[1-2]\\d|3[0-3]){6}";

			// 复式
		} else if (playName.equals(Constant.SSQ_RSBM)
				|| playName.equals(Constant.SSQ_RMBM)
				|| playName.equals(Constant.SSQ_RMBS)) {
			regex = "(0[1-9]|[1-2]\\d|3[0-3]){6,20}";
		}

		if (!redBall.matches(regex)) {
			return false;
		}

		return true;
	}

	/**
	 * 
	 * 验证双色球蓝球注码
	 * 
	 * @param blueBall
	 *            蓝球注码
	 * @param playName
	 *            玩法
	 * @return true or false
	 */
	public static boolean verifySSQBlueBall(String blueBall, String playName) {

		// 验证蓝球是否为空字符
		if (!verifyEmpty(blueBall)) {
			return false;
		}

		// 蓝球号码为1个01-16的数字
		String regex = "";
		// 单式、红胆拖蓝单
		if (playName.equals(Constant.SSQ_RSBS)
				|| playName.equals(Constant.SSQ_RTBS)) {
			regex = "(0[1-9]|1[0-6]){1}";

			// 复式、红胆拖蓝复
		} else if (playName.equals(Constant.SSQ_RSBM)
				|| playName.equals(Constant.SSQ_RMBM)
				|| playName.equals(Constant.SSQ_RMBS)
				|| playName.equals(Constant.SSQ_RTBM)) {
			regex = "(0[1-9]|1[0-6]){1,16}";
		}

		if (!blueBall.matches(regex)) {
			return false;
		}

		return true;
	}

	/**
	 * 
	 * 验证双色球胆码和拖码
	 * 
	 * @param danma
	 *            双色球胆码
	 * @param tuoma
	 *            双色球拖码
	 * @return true or false
	 * 
	 */
	public static boolean verifySSQDantuCode(String danma, String tuoma) {
		// 验证红球胆码和红球拖码是否为空字符
		if (!verifyEmpty(danma) && !verifyEmpty(tuoma)) {
			return false;
		}

		// 红球胆码1-5个，红球拖码2-20个
		String regDanma = "([1-9]|1[0-9]|2[0-9]|3[0-3]){1,5}";
		String regTuoma = "([1-9]|1[0-9]|2[0-9]|3[0-3]){2,20}";
		if (!danma.matches(regDanma) && !tuoma.matches(regTuoma)) {
			return false;
		}

		// 验证胆码与拖码总数不能小于7、不能大于25
		int len = danma.length() + tuoma.length();
		if (len < 7 && len > 25) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * 福彩3D注码验证
	 * 
	 * @return true or false
	 * 
	 */
	public static boolean verifySDCode(String hundreds, String tens,
			String units, String sign) {
		// 判断是否为空字符
		if (!verifyEmpty(hundreds) || !verifyEmpty(tens) || !verifyEmpty(units)) {
			return false;
		}
		String str = "(\\d){1,10}";
		if (!hundreds.matches(str) || !tens.matches(str) || !units.matches(str)) {
			return false;
		}

		// 根据注码之间的分隔符sign分隔注码
		String hundred[] = hundreds.split("\\" + sign);
		String ten[] = tens.split("\\" + sign);
		String unit[] = units.split("\\" + sign);
		if (!verifyRepeat(hundred) || !verifyRepeat(ten) || !verifyRepeat(unit)) {
			return false;
		}
		return true;
	}

	/**
	 * 验证注码是否重复
	 */
	public static boolean verifyRepeat(String s[]) {
		for (int i = 0; i < s.length; i++) {
			for (int j = s.length - 1; j > i; j--) {
				if (s[i].equals(s[j])) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 
	 * 验证福彩3D复式注码
	 * 
	 * @return true or false
	 * 
	 */
	public static boolean verify3DDuplex(String betcode, String sign) {
		// 验证是否为空字符
		if (!verifyEmpty(betcode)) {
			return false;
		}
		// 验证注码是否合法
		String regex = "(\\d){2,10}";
		if (!betcode.matches(regex)) {
			return false;
		}
		// 验证注码是否重复
		String codes[] = betcode.split("\\" + sign);
		if (!verifyRepeat(codes)) {
			return false;
		}
		return true;
	}
	
	/**
	 * 验证福彩3D组6单式
	 * @param betcode 注码
	 * @param sign 注码之间的分隔符
	 * @return true or false
	 */
	public static boolean verify3DGroup6Simple(String betcode,String sign){	
		// 验证是否为空字符
		if (!verifyEmpty(betcode)) {
			return false;
		}
		// 验证注码是否合法
		String regex = "(\\d){2,10}";
		if (!betcode.matches(regex)) {
			return false;
		}
		// 验证注码是否重复
		String codes[] = betcode.split("\\" + sign);
		if (!verifyRepeat(codes)) {
			return false;
		}
		return true;
	}

	/**
	 * 验证福彩3D组六和值
	 * 
	 * @return true or false
	 */
	public static boolean verify3DGroup6HeZhi(String hezhi) {
		//验证和值是否为空
		if (!verifyEmpty(hezhi)) {
			return false;
		}
		
		//验证和值的合法性
		String regex = "([3-9]|1[0-9]|2[0-4])";
		if (!hezhi.matches(regex)) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * 足彩胜负彩14场、任九场、半全场、进球彩验证注码验证 范围:胜负彩14场和任九场(14-38个) 六场半全场 (12-32个)
	 * 进球彩(8-32个)
	 * 
	 * @param betcode
	 *            注码
	 * @param sign
	 *            注码之间的分隔符
	 * @param playName
	 *            玩法(足彩胜负彩-11,足彩任九场-19,六场半全场-18,进球彩-16)
	 * @return true or false
	 * 
	 */
	public static boolean validateZCBetcode(String betcode, String sign,
			String playName) {
		// 判断传入的注码是否是空字符
		if (!verifyEmpty(betcode)) {
			return true;
		}
		// 注码正则
		String regex = "";
		// 胜负彩14场
		if (Constant.ZC_SFC.equals(playName)) {
			regex = "[0,1,3]{14,38}";

			// 任九场
		} else if (Constant.ZC_RJC.equals(playName)) {
			regex = "[0,1,3,#,$]{14,38}";

			// 六场半全场
		} else if (Constant.ZC_BQC.equals(playName)) {
			regex = "[0,1,3]{12,32}";

			// 进球彩
		} else if (Constant.ZC_JQC.equals(playName)) {
			regex = "[0-3]{8,32}";
		}
		// 正则跟传入的注码进行对比
		if (betcode.replace(sign, "").matches(regex)) {
			return true;
		}
		return false;
	}
}
