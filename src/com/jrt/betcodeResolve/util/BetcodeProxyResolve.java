package com.jrt.betcodeResolve.util;

import java.util.Vector;

/**
 * 
 * 注码解析代理类彩种包括双色球、福彩3D、七乐彩、排列三、大乐透、江西11选5
 * 
 * @author 徐丽
 * 
 */
public class BetcodeProxyResolve {
	
	/**
	 * 将注码存入Vector中区分单注
	 * @param betcode
	 * 			注码
	 * @param tabNumber 
	 * 			多注之间的分隔符
	 * @return
	 * 		  Vector 集合
	 */
	public static Vector<String> getVector(String betcode, String tabNumber) {
		String duplexCodes = "";
		String codes[] = betcode.split("\\" + tabNumber);
		//根据多注之间的分隔符拆分合并注码并累加
		for (int k = 0; k < codes.length; k++) {
			duplexCodes += codes[k] + tabNumber;
		}
        
		//将注码存入vector中并返回
		Vector<String> vector = new Vector<String>();
		if (!duplexCodes.equals("")) {
			String duplex[] = duplexCodes.split("\\" + tabNumber);
			for (int d = 0; d < duplex.length; d++) {
				vector.add(duplex[d] + tabNumber);
			}
		}
		return vector;
	}
	/**
	 * 
	 * 解析双色球注码存入vector
	 * 
	 * @param betcode
	 *            注码 例如：单式-红单蓝单:"1,6,15,26,32,2+9^1,6,15,26,32,2+10^
	 *            红复蓝单:1,6,2,5,3,4,9,7,8,10+1^ 红单蓝复:11,2,6,3,4,5+1,3,2^
	 *            红复蓝复:1,5,2,3,9,4,6,7+1,3,2^ 红胆拖蓝单:1,4,2,3*5,9,7,16+1^
	 *            红胆拖蓝复:1,2,4,3*5,7,9,6+1,3,2^"
	 * @param tabNumber
	 *            注码之间分隔符 例子中为"^"
	 * @param tab
	 *            红球与蓝球之间的分隔符 例子中为"+"
	 * @param sign
	 *            注码之间的分隔符 例子中为","
	 * 
	 * @return vector 对象(单式5注、复式)
	 *         例:[2,1,3,5,4,6+1^1,1,3,5,4,6+2^7,10,8,12,9,11+3
	 *         ^1,6,15,26,32,2+4^1,6,15,26,32,2+5,//单式5注为一组(以"^"分隔)
	 *         1,6,15,26,32,2+6^, 1,6,2,5,3,4,9,7,8,10+1^, //红复蓝单
	 *         11,2,6,3,4,5+1,3,2^, //红单蓝复 1,5,2,3,9,4,6,7+1,3,2^, //红复蓝复
	 *         1,4,2,3*5,9,7,16+1^, //红胆拖蓝单 1,2,4,3*5,7,9,6+1,3,2^] //红胆拖蓝复
	 */
	public static Vector<String> getSSQVector(String betcode, String tabNumber,
			String tab, String sign, String redTab) {

		String simpleCodes = "";
		String duplexCodes = "";
		String codes[] = betcode.split("\\" + tabNumber);
		for (int k = 0; k < codes.length; k++) {

			// 根据红球与蓝球之间的分隔符tab分隔蓝球与红球
			String redAndBlue[] = codes[k].split("\\" + tab);
			if (codes[k].indexOf(redTab) != -1) {
				duplexCodes += codes[k] + tabNumber;
			} else {

				// 根据注码之间的分隔符sign 将红球和蓝球注码分开
				String ss1[] = redAndBlue[0].split("[" + sign + "]");
				String ss2[] = redAndBlue[1].split("[" + sign + "]");

				// 判断蓝球和红球的长度算双色球具体玩法如下：
				// 单式
				if (ss1.length == 6 && ss2.length == 1) {
					simpleCodes += codes[k] + tabNumber;
				} else {
					// 得到复式注码
					duplexCodes += codes[k] + tabNumber;
				}
			}
		}

		Vector<String> vector = new Vector<String>();
		/**
		 * 解析单式存入vector中
		 */
		if (!simpleCodes.equals("")) {
			String simples[] = simpleCodes.split("\\" + tabNumber);
			getSimpleCodes(simples, vector, tabNumber);
		}

		/**
		 * 解析复式存入vector中
		 */
		if (!duplexCodes.equals("")) {
			String duplex[] = duplexCodes.split("\\" + tabNumber);
			for (int d = 0; d < duplex.length; d++) {
				vector.add(duplex[d] + tabNumber);
			}
		}
		return vector;
	}

	/**
	 * 
	 * 将单式5注为一组、复式为一组
	 * 
	 * @param simples
	 *            注码数组
	 * @param vector
	 *            转换为数组的对象
	 * @param tabNumber
	 *            多注之间的分隔符
	 * @return vector 对象
	 */
	public static Vector<String> getSimpleCodes(String simples[],
			Vector<String> vector, String tabNumber) {
		// 只有五注，或者五注的倍数提取五注为1组
		if (simples.length % 5 == 0) {
			int j = 0;
			for (int i = 0; i < simples.length / 5; i++) {
				vector.add(simples[j] + tabNumber + simples[j + 1] + tabNumber
						+ simples[j + 2] + tabNumber + simples[j + 3]
						+ tabNumber + simples[j + 4] + tabNumber);
				j = j + 5;
			}
		} else {
			// 否则提取五注为一组、其余为一组
			int j = 0;
			for (int i = 0; i < simples.length / 5; i++) {
				vector.add(simples[j] + tabNumber + simples[j + 1] + tabNumber
						+ simples[j + 2] + tabNumber + simples[j + 3]
						+ tabNumber + simples[j + 4] + tabNumber);
				j = j + 5;
			}
			String str = "";
			for (int i = 0; i < simples.length % 5; i++) {
				str += simples[j + i] + tabNumber;
			}
			if (str.endsWith(tabNumber)) {
				str = str.substring(0, str.length() - 1);
			}
			vector.add(str + tabNumber);
		}
		return vector;
	}

	/**
	 * 
	 * 解析福彩3D注码存入vector
	 * 
	 * @param betcode
	 *            注码 例：001,3,2^001,2,2^001,3,2^001,2,2^001,3,2^001,2,2^//直选单式
	 *            011,3,2^011,2,2^014,5,6^017,9,8^ //组三单式
	 *            021,2,2^024,5,6^027,9,8^ //组六单式 1020^ //直选和值 1120^ //组三和值
	 *            1220^ //组六和值
	 *            201,4,2,3,5*6,9,7,8,10,0*1,5,2,3,4,6,7^//直选复式（单选按位包号）
	 *            312,3,8,4,9,6,7^//组三复式 322,3,8,4,9,6,7^//组六复式
	 *            343,6,7,8,4,5^//单选单复式（直选包号） 542,1*5,4,3^ //胆拖复式(单选单胆拖)
	 * @param tabNumber
	 *            多注分隔符 示例中为"^"
	 * @return Vector 对象存放所有的注码集合 示例为:[001,3,2^001,2,2^001,3,2^001,2,2^001,3,2^,
	 *         001,2,2^, 011,3,2^011,2,2^014,5,6^017,9,8^,
	 *         021,2,2^024,5,6^027,9,8^, 1020^, 1120^, 1220^,
	 *         201,4,2,3,5*6,9,7,8,10,0*1,5,2,3,4,6,7^, 312,3,8,4,9,6,7^,
	 *         322,3,8,4,9,6,7^, 343,6,7,8,4,5^, 542,1*5,4,3^]
	 * 
	 */
	public static Vector<String> getSDVector(String betcode, String tabNumber) {
		String simpleCodesZX = "";
		String simpleCodesZS = "";
		String simpleCodesZL = "";
		String duplexCodes = "";

		// 根据分隔符tabNumber分隔注码
		String codes[] = betcode.split("\\" + tabNumber);
		Vector<String> vector = new Vector<String>();

		// 循环注码将单式(5注为一串)和复式分开
		for (int k = 0; k < codes.length; k++) {
			// 得到玩法
			String playName = codes[k].substring(0, 2);

			// 直选单式
			if (Constant.SD_ZXDS.equals(playName)) {
				simpleCodesZX += codes[k] + tabNumber;
				// 组三单式
			} else if (Constant.SD_Z3DS.equals(playName)) {
				simpleCodesZS += codes[k] + tabNumber;
				// 组六单式
			} else if (Constant.SD_Z6DS.equals(playName)) {
				simpleCodesZL += codes[k] + tabNumber;

				// 其他复式投注
			} else {
				duplexCodes += codes[k] + tabNumber;
			}
		}

		/**
		 * 解析单式注码存入vector中
		 */
		// 直选单式
		if (!simpleCodesZX.equals("")) {
			System.out.println("simpleCodesZX=" + simpleCodesZX);
			// 根据多注分隔符tabNumber分隔注码
			String simples[] = simpleCodesZX.split("\\" + tabNumber);
			// 将单式5注为一串、复式为一串分隔存入vector中
			getSimpleCodes(simples, vector, tabNumber);

			// 组三单式
		}
		if (!simpleCodesZS.equals("")) {
			System.out.println("simpleCodesZS=" + simpleCodesZS);
			// 根据多注分隔符tabNumber分隔注码
			String simples[] = simpleCodesZS.split("\\" + tabNumber);
			// 将单式5注为一串、复式为一串分隔存入vector中
			getSimpleCodes(simples, vector, tabNumber);

			// 组六单式
		}
		if (!simpleCodesZL.equals("")) {
			System.out.println("simpleCodesZL=" + simpleCodesZL);
			// 根据多注分隔符tabNumber分隔注码
			String simples[] = simpleCodesZL.split("\\" + tabNumber);
			// 将单式5注为一串、复式为一串分隔存入vector中
			getSimpleCodes(simples, vector, tabNumber);
		}

		/**
		 * 解析复式存入vector中
		 */
		if (!duplexCodes.equals("")) {
			System.out.println("duplexCodes=" + duplexCodes);
			String duplex[] = duplexCodes.split("\\" + tabNumber);
			for (int d = 0; d < duplex.length; d++) {
				vector.add(duplex[d] + tabNumber);
			}
		}
		return vector;
	}

	/**
	 * 
	 * 解析七乐彩注码存入vector
	 * 
	 * @param betcode
	 *            注码 示例:注码为:1,7,2,6,3,4,5^8,13,9,10,11,12,14^
	 *            15,20,16,17,18,19,21^22,24,23,25,26,27,28^
	 *            32,29,30,31,33,34,35^22,24,23,25,26,27,28^//单式
	 *            1,7,2,6,3,4,5,8,9,10^//复式 1,3,2*4,6,8,7,5,9,10^//胆拖
	 * @param tabNumber
	 *            注码之间分隔符 例子中为"^"
	 * @param sign
	 *            注码之间的分隔符 例子中为","
	 * @return vector 对象
	 *         [1,7,2,6,3,4,5^8,13,9,10,11,12,14^15,20,16,17,18,19,21^22
	 *         ,24,23,25,26,27,28^32,29,30,31,33,34,35^, 22,24,23,25,26,27,28^,
	 *         1,7,2,6,3,4,5,8,9,10^, 1,3,2*4,6,8,7,5,9,10^]
	 * 
	 */
	public static Vector<String> getQLCVector(String betcode, String tabNumber,
			String sign, String redTab) {
		String simpleCodes = "";
		String duplexCodes = "";

		// 根据多注之间的分隔符tabNumber分隔注码
		String codes[] = betcode.split("\\" + tabNumber);
		for (int k = 0; k < codes.length; k++) {

			// 判断是胆码还是单式或者复式
			if (codes[k].indexOf(redTab) == -1) {
				// 根据注码之间的分隔符sign
				String ss1[] = codes[k].split("[" + sign + "]");

				// 判断注码的长度七乐彩具体玩法如下：
				// 单式
				if (ss1.length == 7) {
					simpleCodes += codes[k] + tabNumber;

				} else {
					// 得到复式注码
					duplexCodes += codes[k] + tabNumber;
				}
			} else {
				// 得到胆拖与复式拼接
				duplexCodes += codes[k] + tabNumber;
			}
		}

		Vector<String> vector = new Vector<String>();
		/**
		 * 解析单式存入vector中
		 */
		if (!simpleCodes.equals("")) {
			String simples[] = simpleCodes.split("\\" + tabNumber);
			getSimpleCodes(simples, vector, tabNumber);
		}

		/**
		 * 解析复式存入vector中
		 */
		if (!duplexCodes.equals("")) {
			String duplex[] = duplexCodes.split("\\" + tabNumber);
			for (int d = 0; d < duplex.length; d++) {
				vector.add(duplex[d] + tabNumber);
			}
		}
		return vector;
	}

	/**
	 * 
	 * 解析大乐透注码存入vector数组对象中
	 * 
	 * @param betcode
	 *            注码 示例:注码为:1,15,4,5,6+1,7^1,15,4,5,6+1,7^1,15,4,5,6+1,7^
	 *            1,15,4,5,6+1,7^1,15,4,5,6+1,7^1,15,4,5,6+1,7^ //单式
	 *            1,2,15,4,5,6+1,7,2^ //复式 1,2$3,4,5,6,7,8,9+1$2,3^ //胆拖
	 *            1,2^//十二选二单式 1,2,3,4//十二选二复式
	 * @param tabNumber
	 *            各注之间的分隔符 示例中为"^"
	 * @param tab
	 *            红球和蓝球之间的分隔符 示例中为"+"
	 * @param sign
	 *            注码之间的分隔符 示例中为","
	 * @param dtTab
	 *            胆码和拖码之间的分隔符 示例中为"$"
	 * @return vector 对象
	 *         [1,15,4,5,6+1,7^1,15,4,5,6+1,7^1,15,4,5,6+1,7^1,15,4,5,6
	 *         +1,7^1,15,4,5,6+1,7^, 1,15,4,5,6+1,7^, 1,2,15,4,5,6+1,7,2^,
	 *         1,2$3,4,5,6,7,8,9+1$2,3^, 1,2^, 1,2,3,4^]
	 */
	public static Vector<String> getDLTVector(String betcode, String tabNumber,
			String tab, String sign, String dtTab) {
		String simpleCodes = "";
		String duplexCodes = "";

		// 根据多注之间的分隔符tabNumber分隔注码
		String codes[] = betcode.split("\\" + tabNumber);

		// 大乐透具体玩法如下：
		for (int k = 0; k < codes.length; k++) {
			/**
			 * 十二选二
			 */
			if (codes[k].indexOf(tab) != -1) {

				// 根据红球与蓝球之间的分隔符tab分隔蓝球与红球
				String redAndBlue[] = codes[k].split("\\" + tab);

				// 根据注码之间的分隔符sign 将红球和蓝球注码分开
				String ss1[] = redAndBlue[0].split("[" + sign + "]");
				String ss2[] = redAndBlue[1].split("[" + sign + "]");

				// 判断是胆码、生肖乐还是单式或者复式
				if (codes[k].indexOf(dtTab) == -1
						|| codes[k].indexOf(tab) == -1) {

					/**
					 * 单式
					 */
					if (ss1.length == 5 && ss2.length == 2) {
						simpleCodes += codes[k] + tabNumber;
						/**
						 * 复式
						 */
					} else {
						// 得到复式注码
						duplexCodes += codes[k] + tabNumber;
					}
					/**
					 * 胆拖
					 */
				} else {
					// 得到胆拖、复式拼接的注码
					duplexCodes += codes[k] + tabNumber;
				}
			} else {
				// 得到胆拖、复式、十二选二拼接的注码
				duplexCodes += codes[k] + tabNumber;
			}
		}

		Vector<String> vector = new Vector<String>();
		/**
		 * 解析单式存入vector中
		 */
		if (!simpleCodes.equals("")) {
			// 根据注码之间的分隔符tabNumber 分隔单式注码（5注为一串）
			String simples[] = simpleCodes.split("\\" + tabNumber);
			getSimpleCodes(simples, vector, tabNumber);
		}

		/**
		 * 解析复式存入vector中
		 */
		if (!duplexCodes.equals("")) {
			// 根据注码之间的分隔符tabNumber 分隔胆拖注码
			String duplex[] = duplexCodes.split("\\" + tabNumber);
			for (int d = 0; d < duplex.length; d++) {
				vector.add(duplex[d] + tabNumber);
			}
		}
		return vector;
	}

	/**
	 * 
	 * 解析排列三注码存入vector
	 * 
	 * @param betcode
	 *            注码 011,3,2;011,2,2;011,3,2;011,2,2;011,3,2;011,2,2;//直选单式
	 *            061,3,3;061,2,2;061,2,2;064,5,6;067,9,8; //组选单式 S120; //直选和值
	 *            S920; //组选和值 S320; //组三和值 S620; //组六和值 F32,3,8,4,9,6,7;//组三包号
	 *            F62,3,8,4,9,6,7;//组六包号
	 * @param tabNumber
	 *            多注分隔符 示例中为";"
	 * @return Vector 对象存放所有的注码集合
	 * 
	 */
	public static Vector<String> getPLSVector(String betcode, String tabNumber,
			String qhTab) {
		String simpleCodesZX = "";
		String simpleCodesZS = "";
		String simpleCodesZL = "";
		String duplexCodes = "";

		// 根据分隔符tabNumber分隔注码
		String codes[] = betcode.split("\\" + tabNumber);
		Vector<String> vector = new Vector<String>();

		// 循环注码将单式(5注为一串)和复式分开
		for (int k = 0; k < codes.length; k++) {
			// 得到玩法
			String playName = codes[k].substring(0, 2);
			// System.out.println(playName);
			// 直选
			if (Constant.PLS_ZHX.equals(playName)) {
				if (codes[k].indexOf(qhTab) > -1) {
					duplexCodes += codes[k] + tabNumber;
				} else {
					simpleCodesZX += codes[k] + tabNumber;
				}
				// 组选
			} else if (Constant.PLS_ZX.equals(playName)) {
				simpleCodesZS += codes[k] + tabNumber;

				// 其他复式投注
			} else {
				duplexCodes += codes[k] + tabNumber;
			}
		}

		/**
		 * 解析单式注码存入vector中
		 */
		// 直选单式
		if (!simpleCodesZX.equals("")) {
			System.out.println("simpleCodesZX=" + simpleCodesZX);
			// 根据多注分隔符tabNumber分隔注码
			String simples[] = simpleCodesZX.split("\\" + tabNumber);
			// 将单式5注为一串、复式为一串分隔存入vector中
			getSimpleCodes(simples, vector, tabNumber);

			// 组三单式
		}
		if (!simpleCodesZS.equals("")) {
			System.out.println("simpleCodesZS=" + simpleCodesZS);
			// 根据多注分隔符tabNumber分隔注码
			String simples[] = simpleCodesZS.split("\\" + tabNumber);
			// 将单式5注为一串、复式为一串分隔存入vector中
			getSimpleCodes(simples, vector, tabNumber);

			// 组六单式
		}
		if (!simpleCodesZL.equals("")) {
			System.out.println("simpleCodesZL=" + simpleCodesZL);
			// 根据多注分隔符tabNumber分隔注码
			String simples[] = simpleCodesZL.split("\\" + tabNumber);
			// 将单式5注为一串、复式为一串分隔存入vector中
			getSimpleCodes(simples, vector, tabNumber);
		}

		/**
		 * 解析复式存入vector中
		 */
		if (!duplexCodes.equals("")) {
			System.out.println("duplexCodes=" + duplexCodes);
			String duplex[] = duplexCodes.split("\\" + tabNumber);
			for (int d = 0; d < duplex.length; d++) {
				vector.add(duplex[d] + tabNumber);
			}
		}
		return vector;
	}

	/**
	 * 
	 * 解析排列五注码存入vector
	 * 
	 * @param betcode
	 *            注码
	 *            示例：1,2,3,5,6;4,5,6,7,8;2,4,5,6,9;2,4,5,6,9;2,4,5,6,9;2,4,5,6
	 *            ,9;2,4,5,6,9; //单式
	 *            1,2,8-0,2,3-1,2,8-0,2,3-3,4,9;1,2,8-0,2,3-1,2,8-0,2,3-3,4,9;
	 *            //复式
	 * @param tcTabNumber
	 *            多注分隔符 示例中为";"
	 * @param qhTab
	 *            复式万位、千位、百位、十位、个位之间的分隔符"-"
	 * @return Vector 对象存放所有的注码集合
	 *         示例：[1,2,3,5,6;4,5,6,7,8;2,4,5,6,9;2,4,5,6,9;2,4,5,6,9;,
	 *         2,4,5,6,9;2,4,5,6,9;, 1,2,8-0,2,3-1,2,8-0,2,3-3,4,9;,
	 *         1,2,8-0,2,3-1,2,8-0,2,3-3,4,9;]
	 */
	public static Vector<String> getPLWVector(String betcode,
			String tcTabNumber, String qhTab) {
		String simpleCodes = "";
		String duplexCodes = "";

		String codes[] = betcode.split("\\" + tcTabNumber);
		for (int k = 0; k < codes.length; k++) {
			if (codes[k].indexOf(qhTab) > -1) {

				// 根据多注之间分隔符tcTabNumber分隔复式注码
				String qhTabCode[] = codes[k].split("\\" + tcTabNumber);
				for (int j = 0; j < qhTabCode.length; j++) {
					// 得到复式注码
					duplexCodes += qhTabCode[j] + tcTabNumber;
				}
			} else {
				// 得到单式注码
				simpleCodes += codes[k] + tcTabNumber;
			}
		}

		System.out.println("simpleCodes=" + simpleCodes);
		System.out.println("duplexCodes=" + duplexCodes);
		Vector<String> vector = new Vector<String>();
		/**
		 * 解析单式存入vector中
		 */
		if (!simpleCodes.equals("")) {
			String simples[] = simpleCodes.split("\\" + tcTabNumber);
			getSimpleCodes(simples, vector, tcTabNumber);
		}

		/**
		 * 解析复式存入vector中
		 */
		if (!duplexCodes.equals("")) {
			String duplex[] = duplexCodes.split("\\" + tcTabNumber);
			for (int d = 0; d < duplex.length; d++) {
				vector.add(duplex[d] + tcTabNumber);
			}
		}
		System.out.println("排列五存入vector=" + vector);
		return vector;
	}

	/**
	 * 
	 * 解析七星彩注码存入vector
	 * 
	 * @param betcode
	 *            注码 示例：1,2,3,5,6,3,4;4,5,6,7,8,5,6;2,4,5,6,9,5,6; //单式
	 *            1,2-3-2,1,2-5,0-3-7,8,2,4-6,8,3,4,9,0; //复式
	 * @param tcTabNumber
	 *            多注分隔符 示例中为";"
	 * @param qhTab
	 *            复式各位之间的分隔符"-"
	 * @return Vector 对象存放所有的注码集合
	 *         示例：[1,2,3,5,6;4,5,6,7,8;2,4,5,6,9;2,4,5,6,9;2,4,5,6,9;,
	 *         2,4,5,6,9;2,4,5,6,9;, 1,2,8-0,2,3-1,2,8-0,2,3-3,4,9;,
	 *         1,2,8-0,2,3-1,2,8-0,2,3-3,4,9;]
	 */
	public static Vector<String> getQXCVector(String betcode,
			String tcTabNumber, String qhTab) {
		String simpleCodes = "";
		String duplexCodes = "";

		String codes[] = betcode.split("\\" + tcTabNumber);
		for (int k = 0; k < codes.length; k++) {
			if (codes[k].indexOf(qhTab) > -1) {

				// 根据多注之间分隔符tcTabNumber分隔复式注码
				String qhTabCode[] = codes[k].split("\\" + tcTabNumber);
				for (int j = 0; j < qhTabCode.length; j++) {
					// 得到复式注码
					duplexCodes += qhTabCode[j] + tcTabNumber;
				}
			} else {
				// 得到单式注码
				simpleCodes += codes[k] + tcTabNumber;
			}
		}

		System.out.println("simpleCodes=" + simpleCodes);
		System.out.println("duplexCodes=" + duplexCodes);
		Vector<String> vector = new Vector<String>();
		/**
		 * 解析单式存入vector中
		 */
		if (!simpleCodes.equals("")) {
			String simples[] = simpleCodes.split("\\" + tcTabNumber);
			getSimpleCodes(simples, vector, tcTabNumber);
		}

		/**
		 * 解析复式存入vector中
		 */
		if (!duplexCodes.equals("")) {
			String duplex[] = duplexCodes.split("\\" + tcTabNumber);
			for (int d = 0; d < duplex.length; d++) {
				vector.add(duplex[d] + tcTabNumber);
			}
		}
		System.out.println("七星彩存入vector=" + vector);
		return vector;
	}

	/**
	 * 时时彩分解单复式存入vector中
	 * 
	 * @param betcode
	 *            注码 示例:
	 *            "5D1,2,3,5,6;5D4,5,6,7,8;5D2,4,5,6,9;5D2,4,5,6,9;5D2,4,5,6,9;5D2,4,5,6,9;"
	 *            // 五星单式 "5T1,2,3,5,6;5T4,5,6,7,8;5T2,4,5,6,9;"// 五星通选单式
	 *            "3D5,1,8;3D9,5,6;3D6,7,8;3D5,6,9;"// 三星单式
	 *            "2D1,8;2D5,6;2D7,8;2D6,9;"// 二星单式 "1D8;1D6;1D8;1D9;"// 一星单式
	 *            "DD1,2;"// 大小单双单式 "H25;"//二星直选和值（1-8个） "S25;"//二星组选和值（1-8个）
	 *            "F25;"//二星组选复式（3-7个）
	 *            "5D1,2,8-0,2,3-1,2,8-0,2,3-3,4,9;"//五星直选复式
	 *            "5T1,2,8-0,2,3-1,2,8-0,2,3-3,4,9;"// 五星通选复式
	 *            "DD1,2,4-1,2;"//大小单双 "3D1,2,5-1,3-8,4;"// 三星直选复式
	 *            "2D1,2,5-1,3;"// 二星直选复式 "1D1,2,5;"// 一星直选复式
	 * @param tcTabNumber
	 *            多注之间的分隔符 示例中为";"
	 * @param qhTab
	 *            直选复式各位之间的分隔符 示例中为"-"
	 * @param sign
	 *            注码之间的分隔符 示例中为","
	 * @return 返回存入的vector 示例中为
	 *         [5D1,2,3,5,6;5D4,5,6,7,8;5D2,4,5,6,9;5D2,4,5,6,9;5D2,4,5,6,9;,
	 *         5D2,4,5,6,9;, 3D5,1,8;3D9,5,6;3D6,7,8;3D5,6,9;,
	 *         2D1,8;2D5,6;2D7,8;2D6,9;, 1D8;1D6;1D8;1D9;,
	 *         5T1,2,3,5,6;5T4,5,6,7,8;5T2,4,5,6,9;, DD1,2;, H25;, S25;, F25;,
	 *         5D1,2,8-0,2,3-1,2,8-0,2,3-3,4,9;,
	 *         5T1,2,8-0,2,3-1,2,8-0,2,3-3,4,9;, DD1,2,4-1,2;, 3D1,2,5-1,3-8,4;,
	 *         2D1,2,5-1,3;, 1D1,2,5;]
	 */
	public static Vector<String> getSSCVector(String betcode,
			String tcTabNumber, String qhTab, String sign) {
		String simpleCodes_wx = "";
		String simpleCodes_sx = "";
		String simpleCodes_rx = "";
		String simpleCodes_yx = "";
		String simpleCodes_wxtx = "";
		String simpleCodes_dxds = "";
		String duplexCodes = "";

		// 根据分隔符tabNumber分隔注码simpleCodes_sx
		String codes[] = betcode.split("\\" + tcTabNumber);
		Vector<String> vector = new Vector<String>();

		// 循环注码将单式(5注为一串)和复式分开
		for (int i = 0; i < codes.length; i++) {

			// 得到玩法
			String playName = codes[i].substring(0, 2);

			// 若玩法是一星
			if (playName.equals(SSCConstant.SSC_YX)) {
				String ydcodes[] = codes[i].split("\\" + sign);
				if (ydcodes.length > 1) {
					duplexCodes += codes[i] + tcTabNumber;
				} else {
					simpleCodes_yx += codes[i] + tcTabNumber;
				}
			} else {

				// 根据分隔符qhTab(-)判断是单式还是直选复式
				if (codes[i].indexOf(qhTab) > -1) {
					// 直选复式
					duplexCodes += codes[i] + tcTabNumber;
				} else {
					// 五星单式
					if (playName.equals(SSCConstant.SSC_WX)) {
						simpleCodes_wx += codes[i] + tcTabNumber;
					} else if (playName.equals(SSCConstant.SSC_SX)) {
						simpleCodes_sx += codes[i] + tcTabNumber;
					} else if (playName.equals(SSCConstant.SSC_RX)) {
						simpleCodes_rx += codes[i] + tcTabNumber;
					} else if (playName.equals(SSCConstant.SSC_WXTX)) {
						simpleCodes_wxtx += codes[i] + tcTabNumber;
					} else if (playName.equals(SSCConstant.SSC_DXDS)) {
						simpleCodes_dxds += codes[i] + tcTabNumber;
					} else {
						duplexCodes += codes[i] + tcTabNumber;
					}

				}
			}

		}

		/**
		 * 解析单式注码存入vector中
		 */
		// 单式-根据多注分隔符tcTabNumber分隔注码并将单式5注为一串、复式为一串分隔存入vector中
		if (!simpleCodes_wx.equals("")) {
			// System.out.println("simpleCodes_wx=" + simpleCodes_wx);
			String simples[] = simpleCodes_wx.split("\\" + tcTabNumber);
			getSimpleCodes(simples, vector, tcTabNumber);
		}
		if (!simpleCodes_sx.equals("")) {
			// System.out.println("simpleCodes_sx=" + simpleCodes_sx);
			String simples[] = simpleCodes_sx.split("\\" + tcTabNumber);
			getSimpleCodes(simples, vector, tcTabNumber);
		}
		if (!simpleCodes_rx.equals("")) {
			// System.out.println("simpleCodes_rx=" + simpleCodes_rx);
			String simples[] = simpleCodes_rx.split("\\" + tcTabNumber);
			getSimpleCodes(simples, vector, tcTabNumber);
		}
		if (!simpleCodes_yx.equals("")) {
			// System.out.println("simpleCodes_yx=" + simpleCodes_yx);
			String simples[] = simpleCodes_yx.split("\\" + tcTabNumber);
			getSimpleCodes(simples, vector, tcTabNumber);
		}
		if (!simpleCodes_wxtx.equals("")) {
			// System.out.println("simpleCodes_wxtx=" + simpleCodes_wxtx);
			String simples[] = simpleCodes_wxtx.split("\\" + tcTabNumber);
			getSimpleCodes(simples, vector, tcTabNumber);
		}
		if (!simpleCodes_dxds.equals("")) {
			// System.out.println("simpleCodes_wxtx=" + simpleCodes_wxtx);
			String simples[] = simpleCodes_dxds.split("\\" + tcTabNumber);
			getSimpleCodes(simples, vector, tcTabNumber);
		}

		/**
		 * 解析复式存入vector中
		 */
		if (!duplexCodes.equals("")) {
			// System.out.println("duplexCodes=" + duplexCodes);
			String duplex[] = duplexCodes.split("\\" + tcTabNumber);
			for (int d = 0; d < duplex.length; d++) {
				vector.add(duplex[d] + tcTabNumber);
			}
		}

		return vector;
	}

	/**
	 * 
	 * @param betcode
	 *            注码 示例:"1103,1,0,1,3,1,1,3,1,3,1,1,1,3;1103,1,0,1,3,1,1,3,1,3,1,1,1,3;1103,1,0,1,3,1,1,3,1,3,1,1,1,3;"
	 *            "1103,1,0,1,3,1,1,3,1,3,1,1,1,3;1103,1,0,1,3,1,1,3,1,3,1,1,1,3;"
	 *            "1103,1,0,1,3,1,1,3,1,3,1,1,1,3;"//胜负彩14场单式
	 *            "1903,#,0,1,#,1,#,3,#,3,#,1,1,3;"//任九场单式
	 *            "1603,1,0,1,3,1,1,3,1,3,1,1;"//足彩六场半全场单式
	 *            "1803,1,0,1,3,1,1,3;"//足彩进球彩单式
	 * 
	 *            "11131,10,0,1,3,1,1,3,1,3,1,1,1,3;"//胜负彩14场复式
	 *            "19131,10,#,1,#,1,#,3,#,3,1,#,1,3;"//任九场复式
	 *            "1920,#,#,#,#,31,#,#,#,#,#,#,#,#$#,1,0,1,#,#,1,#,3,3,03,#,1,#;"
	 *            //任九场胆拖 "16130,1,03,01,3,1,1,3,1,3,1,1;"//足彩六场半全场复式
	 *            "18130,1,03,01,3,1,1,3;"//足彩进球彩复式
	 * @param tcTabNumber
	 *            多注之间的分隔符 示例中为";"
	 * @param sign
	 *            足彩每场之间的分隔符 示例中为","
	 * @param streak
	 *            不足场次代替符 示例中为"#"
	 * @return 返回存入vector 示例如下:
	 *         3,1,0,1,3,1,1,3,1,3,1,1,1,3;3,1,0,1,3,1,1,3,1,3,1,1,1,3;
	 *         3,1,0,1,3,1,1,3,1,3,1,1,1,3;3,1,0,1,3,1,1,3,1,3,1,1,1,3;
	 *         3,1,0,1,3,1,1,3,1,3,1,1,1,3;, 3,1,0,1,3,1,1,3,1,3,1,1,1,3;,
	 *         3,1,0,1,3,1,1,3;, 3,1,0,1,3,1,1,3,1,3,1,1;,
	 *         31,10,0,1,3,1,1,3,1,3,1,1,1,3;, 3,#,0,1,#,1,#,3,#,3,#,1,1,3;,
	 *         31,10,#,1,#,1,#,3,#,3,1,#,1,3;,
	 *         0,#,#,#,#,31,#,#,#,#,#,#,#,#$#,1,0,1,#,#,1,#,3,3,03,#,1,#;,
	 *         30,1,03,01,3,1,1,3,1,3,1,1;, 30,1,03,01,3,1,1,3;]
	 * 
	 */
	public static Vector<String> getZCVector(String betcode,
			String tcTabNumber, String sign, String streak) {

		String simpleCodes_sfc = "";
		String simpleCodes_rjc = "";
		String simpleCodes_bqc = "";
		String simpleCodes_jqc = "";
		String duplexCodes = "";

		// 根据分隔符tabNumber分隔注码
		String codes[] = betcode.split("\\" + tcTabNumber);
		Vector<String> vector = new Vector<String>();

		// 循环注码将单式(5注为一串)和复式分开
		for (int i = 0; i < codes.length; i++) {
			// 获取彩种和玩法
			String lotno = codes[i].substring(0, 2);
			String playName = codes[i].substring(2, 3);

			// 将玩法去除获取新注码
			// codes[i] = codes[i].substring(3);
			// 拼接胆拖注码
			if (codes[i].indexOf(streak) > -1) {
				duplexCodes += codes[i] + tcTabNumber;
			} else {
				// 判断是单式还是复式
				if (playName.equals(Constant.ZC_DS)) {
					if (lotno.equals(Constant.ZC_SFC)) {// 胜负彩
						simpleCodes_sfc += codes[i] + tcTabNumber;
					} else if (lotno.equals(Constant.ZC_RJC)) {// 任九场
						simpleCodes_rjc += codes[i] + tcTabNumber;
					} else if (lotno.equals(Constant.ZC_JQC)) {// 进球彩
						simpleCodes_jqc += codes[i] + tcTabNumber;
					} else if (lotno.equals(Constant.ZC_BQC)) {// 半全场
						simpleCodes_bqc += codes[i] + tcTabNumber;
					}
				} else {
					// 拼接复式注码
					duplexCodes += codes[i] + tcTabNumber;
				}
			}
		}

		/**
		 * 解析单式注码存入vector中
		 */
		// 单式-根据多注分隔符tcTabNumber分隔注码并将单式5注为一串、复式为一串分隔存入vector中
		if (!simpleCodes_sfc.equals("")) {
			String simples[] = simpleCodes_sfc.split("\\" + tcTabNumber);
			getSimpleCodes(simples, vector, tcTabNumber);
		}
		if (!simpleCodes_rjc.equals("")) {
			String simples[] = simpleCodes_rjc.split("\\" + tcTabNumber);
			getSimpleCodes(simples, vector, tcTabNumber);
		}
		if (!simpleCodes_jqc.equals("")) {
			String simples[] = simpleCodes_jqc.split("\\" + tcTabNumber);
			getSimpleCodes(simples, vector, tcTabNumber);
		}
		if (!simpleCodes_bqc.equals("")) {
			String simples[] = simpleCodes_bqc.split("\\" + tcTabNumber);
			getSimpleCodes(simples, vector, tcTabNumber);
		}

		/**
		 * 解析复式存入vector中
		 */
		if (!duplexCodes.equals("")) {
			String duplex[] = duplexCodes.split("\\" + tcTabNumber);
			for (int d = 0; d < duplex.length; d++) {
				vector.add(duplex[d] + tcTabNumber);
			}
		}
		System.out.println("足彩vector=" + vector);
		return vector;
	}

	/**
	 * 
	 * 
	 * @param betcode
	 *            注码 //任选1-8
	 *            R11;R11;R11;R11;R11;R11;R11;R11,2,3,4,5,6;R21,2;R21,
	 *            2;R21,2;R21,10;R21,2,3,4;
	 *            R31,2,3;R31,2,3;R31,2,3;R31,2,3;R31,2,3;R31,2,3;
	 *            R41,2,3,4;R41,2,3,4,5,6,7; R51,2,3,4,5;R51,2,3,4,5,6,9,10;
	 *            R61,2,3,4,5,6;R61,2,3,4,5,6,7,8;
	 *            R71,2,3,4,5,6,7;R71,2,3,4,5,6,7,8;
	 *            R81,2,3,4,5,6,7,8;R81,2,3,4,5,6,7,8; //前二和前三组选
	 *            Z24,10;Z24,10;Z24,10;Z24,10;Z24,10;Z21,2,3,4,5,6,7,8;
	 *            Z31,7,10;Z31,7,10;Z31,7,10;Z31,7,10; Z31,2,3,4,5,6,7,8,9; //胆拖
	 *            R21$5,8,10;R31$5,8,10; R41,2$5,6,7,8,10;R51,2$5,6,7,8,10;
	 *            R61,2$5,6,7,8,10;R71,2$5,6,7,8,10,11;
	 *            R81,2,3,5,6,7,8$10,11;Z21$5,8,10;Z31$5,6,7,8,10; //前二和前三直选
	 *            Q22-8;Q22-8;Q22-8;Q22-8;Q22-8;Q22-8; Q21,2,3,4,5-8,9;
	 *            Q36-9-10;Q36-9-10;Q36-9-10;Q36-9-10;Q36-9-10;
	 *            Q31,4,6-5,8,9-7,10,11;
	 * @param tcTabNumber
	 *            多注之间的分隔符 示例中为";"
	 * @param sign
	 *            注码之间的分隔符 示例中为","
	 * @param dtTab
	 *            胆码和拖码之间的分隔符 示例中为"$"
	 * @param qhTab
	 *            各位之间的分隔符示例中为"-"(注：只有前二和前三直选有)
	 * @return 将不同玩法组合，单式5注为一串，复式一注为一串存入Vector中返回
	 *       示例如: [R11;R11;R11;R11;R11;,
	 *         R11;R11;, //任选1-8 R21,2;R21,2;R21,2;R21,10;,
	 *         R31,2,3;R31,2,3;R31,2,3;R31,2,3;R31,2,3;, R31,2,3;, R41,2,3,4;,
	 *         R51,2,3,4,5;, R61,2,3,4,5,6;, R71,2,3,4,5,6,7;, R11,2,3,4,5,6;,
	 *         R21,2,3,4;, R41,2,3,4,5,6,7;, R51,2,3,4,5,6,9,10;,
	 *         R61,2,3,4,5,6,7,8;, R71,2,3,4,5,6,7,8;,
	 *         R81,2,3,4,5,6,7,8;R81,2,3,4,5,6,7,8;,
	 *         Q22-8;Q22-8;Q22-8;Q22-8;Q22-8;, Q22-8;, //前二和前三直选
	 *         Q36-9-10;Q36-9-10;Q36-9-10;Q36-9-10;Q36-9-10;,
	 *         Z24,10;Z24,10;Z24,10;Z24,10;Z24,10;, //前二和前三组选
	 *         Z31,7,10;Z31,7,10;Z31,7,10;Z31,7,10;, "Z21,2,3,4,5,6,7,8;,
	 *         Z31,2,3,4,5,6,7,8,9;, //胆拖 R21$5,8,10;, R31$5,8,10;,
	 *         R41,2$5,6,7,8,10;, R51,2$5,6,7,8,10;, R61,2$5,6,7,8,10;,
	 *         R71,2$5,6,7,8,10,11;, R81,2,3,5,6,7,8$10,11;, Z21$5,8,10;,
	 *         Z31$5,6,7,8,10;, Q21,2,3,4,5-8,9;, Q31,4,6-5,8,9-7,10,11;]
	 */
	public static Vector<String> getSYXWVector(String betcode,
			String tcTabNumber, String sign, String dtTab, String qhTab) {
		Vector<String> vector = new Vector<String>();
		String codes[] = betcode.split("\\" + tcTabNumber);// 分隔注码
		String simpleCodes_R1 = "";
		String simpleCodes_R2 = "";
		String simpleCodes_R3 = "";
		String simpleCodes_R4 = "";
		String simpleCodes_R5 = "";
		String simpleCodes_R6 = "";
		String simpleCodes_R7 = "";
		String simpleCodes_R8 = "";
		String simpleCodes_Q2 = "";
		String simpleCodes_Q3 = "";
		String simpleCodes_Z2 = "";
		String simpleCodes_Z3 = "";
		String duplexCodes = "";
		for (int i = 0; i < codes.length; i++) {
			String wanfa = codes[i].substring(0, 2);// 得到注码
			if (codes[i].indexOf(dtTab) > -1) {// 胆拖注码
				duplexCodes += codes[i] + Constant.TC_TABNUMBER;
			} else {
				// 获取不同玩法的注码
				String code_new[] = codes[i].substring(2, codes[i].length())
						.split("\\" + sign);
				// System.out.println(codes[i]+"===="+code_new.length);
				if (wanfa.equals(Constant.SYXW_RX + "1")
						&& code_new.length == 1) {// 任一
					simpleCodes_R1 += codes[i] + Constant.TC_TABNUMBER;
				} else if (wanfa.equals(Constant.SYXW_RX + "2")
						&& code_new.length == 2) {// 任二
					simpleCodes_R2 += codes[i] + Constant.TC_TABNUMBER;
				} else if (wanfa.equals(Constant.SYXW_RX + "3")
						&& code_new.length == 3) {// 任三
					simpleCodes_R3 += codes[i] + Constant.TC_TABNUMBER;
				} else if (wanfa.equals(Constant.SYXW_RX + "4")
						&& code_new.length == 4) {// 任四
					simpleCodes_R4 += codes[i] + Constant.TC_TABNUMBER;
				} else if (wanfa.equals(Constant.SYXW_RX + "5")
						&& code_new.length == 5) {// 任五
					simpleCodes_R5 += codes[i] + Constant.TC_TABNUMBER;
				} else if (wanfa.equals(Constant.SYXW_RX + "6")
						&& code_new.length == 6) {// 任六
					simpleCodes_R6 += codes[i] + Constant.TC_TABNUMBER;
				} else if (wanfa.equals(Constant.SYXW_RX + "7")
						&& code_new.length == 7) {// 任七
					simpleCodes_R7 += codes[i] + Constant.TC_TABNUMBER;
				} else if (wanfa.equals(Constant.SYXW_RX + "8")
						&& code_new.length == 8) {// 任八
					simpleCodes_R8 += codes[i] + Constant.TC_TABNUMBER;
				} else if (wanfa.equals(Constant.SYXW_QX2)
						&& code_new.length == 1) {// 前二直选单式
					simpleCodes_Q2 += codes[i] + Constant.TC_TABNUMBER;
				} else if (wanfa.equals(Constant.SYXW_QX3)
						&& code_new.length == 1) {// 前三直选单式
					simpleCodes_Q3 += codes[i] + Constant.TC_TABNUMBER;
				} else if (wanfa.equals(Constant.SYXW_ZX2)
						&& code_new.length == 2) {// 前二组选单式
					simpleCodes_Z2 += codes[i] + Constant.TC_TABNUMBER;
				} else if (wanfa.equals(Constant.SYXW_ZX3)
						&& code_new.length == 3) {// 前三组选单式
					simpleCodes_Z3 += codes[i] + Constant.TC_TABNUMBER;
				} else {
					duplexCodes += codes[i] + Constant.TC_TABNUMBER;// 其他注码
				}
			}
		}

		/**
		 * 解析单式注码存入vector中
		 */
		// 单式-根据多注分隔符tcTabNumber分隔注码并将单式5注为一串、复式为一串分隔存入vector中
		if (!simpleCodes_R1.equals("")) {
			String simples[] = simpleCodes_R1.split("\\" + tcTabNumber);
			getSimpleCodes(simples, vector, tcTabNumber);
		}
		if (!simpleCodes_R2.equals("")) {
			String simples[] = simpleCodes_R2.split("\\" + tcTabNumber);
			getSimpleCodes(simples, vector, tcTabNumber);
		}
		if (!simpleCodes_R3.equals("")) {
			String simples[] = simpleCodes_R3.split("\\" + tcTabNumber);
			getSimpleCodes(simples, vector, tcTabNumber);
		}
		if (!simpleCodes_R4.equals("")) {
			String simples[] = simpleCodes_R4.split("\\" + tcTabNumber);
			getSimpleCodes(simples, vector, tcTabNumber);
		}
		if (!simpleCodes_R5.equals("")) {
			String simples[] = simpleCodes_R5.split("\\" + tcTabNumber);
			getSimpleCodes(simples, vector, tcTabNumber);
		}
		if (!simpleCodes_R6.equals("")) {
			String simples[] = simpleCodes_R6.split("\\" + tcTabNumber);
			getSimpleCodes(simples, vector, tcTabNumber);
		}
		if (!simpleCodes_R7.equals("")) {
			String simples[] = simpleCodes_R7.split("\\" + tcTabNumber);
			getSimpleCodes(simples, vector, tcTabNumber);
		}
		if (!simpleCodes_R8.equals("")) {
			String simples[] = simpleCodes_R8.split("\\" + tcTabNumber);
			getSimpleCodes(simples, vector, tcTabNumber);
		}

		if (!simpleCodes_Q2.equals("")) {
			String simples[] = simpleCodes_Q2.split("\\" + tcTabNumber);
			getSimpleCodes(simples, vector, tcTabNumber);
		}
		if (!simpleCodes_Q3.equals("")) {
			String simples[] = simpleCodes_Q3.split("\\" + tcTabNumber);
			getSimpleCodes(simples, vector, tcTabNumber);
		}
		if (!simpleCodes_Z2.equals("")) {
			String simples[] = simpleCodes_Z2.split("\\" + tcTabNumber);
			getSimpleCodes(simples, vector, tcTabNumber);
		}
		if (!simpleCodes_Z3.equals("")) {
			String simples[] = simpleCodes_Z3.split("\\" + tcTabNumber);
			getSimpleCodes(simples, vector, tcTabNumber);
		}

		/**
		 * 解析复式存入vector中
		 */
		if (!duplexCodes.equals("")) {
			String duplex[] = duplexCodes.split("\\" + tcTabNumber);
			for (int d = 0; d < duplex.length; d++) {
				vector.add(duplex[d] + tcTabNumber);
			}
		}
		System.out.println("江西11选5vector=" + vector);
		return vector;
	}
}
