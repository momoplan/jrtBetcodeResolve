package com.jrt.betcodeResolve.lotnoBetcodeUtil;

import com.jrt.betcodeResolve.util.BetcodeResolveUtil;
import com.jrt.betcodeResolve.util.Constant;

/**
 * 
 * 		双色球解析注码算注数、金额、玩法的类
 * @author 
 * 		徐丽
 * 
 */
public class SSQBetcodeUtil {
	/**
	 * 
	 * 		双色球红单蓝单玩法 - 算注数
	 * @param 
	 * 		betcode 注码
	 *      例：注码:2,1,3,5,4,6+1^7,10,8,12,9,11+2^1,6,15,26,32,2+2^
	 *         多注之间的分隔符是"^",根据这个分隔符分割得到其注数。
	 * @param 
	 * 		tabNumber 多注分隔符（单式有） 例子中多注分隔符为"^"
	 * @return 
	 * 		传入注码的注数 例子中注码的注数为3注
	 * 
	 */
	public static int getSSQSimplexZhushu(String betcode, String tabNumber) {

		// 根据多注分隔符tabNumber将注码分割放到数组中
		String ss[] = betcode.split("\\" + tabNumber);

		// 数组的长度就是传过来注码的注数
		return ss.length;
	}

	/**
	 * 
	 * 		双色球红单蓝单玩法 - 根据注码、倍数、玩法得金额
	 * @param 
	 * 		multiple 倍数 例如倍数为1倍
	 * @param 
	 * 		betcode 注码   
	 * 		例：注码:2,1,3,5,4,6+1^7,10,8,12,9,11+2^1,6,15,26,32,2+2^
	 *      调用算注数的方法得到注数，根据注数、倍数、彩票的单价算总金额
	 * @param 
	 * 		tabNumber
	 *      多注分隔符（单式有） 例子中的多注分隔符为"^"
	 * @return 
	 * 		总金额=注数*倍数*单价(单注彩票的金额) 例子中算的结果为 总金额 = 3*1*2=6
	 * 
	 */
	public static int getSSQSimplexMoney(String betcode, int multiple,
			String tabNumber) {
		// 调用根据注码算注数的方法得到注数
		int zhushu = getSSQSimplexZhushu(betcode, tabNumber);
		// 算金额返回总金额
		return zhushu * multiple * Constant.LOTTERY_PRICE;
	}

	/**
	 * 
	 * 		 福彩双色球复式投注 - 算注数 复式投注包括：红单蓝复、红复蓝单、红复蓝复
	 * @param 
	 * 		betcode 注码 
	 *      例(以红复蓝复为例):1,6,10,5,3,4,7+1,2,3
	 * @param 
	 * 		tab 红球和蓝球中间的分隔符 例子中为"+"
	 * @param 
	 * 		sign 传过来注码的格式标记符 例子中为","
	 * @return 
	 * 		注数 例子中的注数为21注
	 * 
	 */
	public static int getSSQDuplexZhushu(String betcode, String tab, String sign) {
		// 1.根据分隔符-分隔红球和蓝球
		String ballArray[] = betcode.split("\\" + tab);

		// 2.根据注码之间的分隔符 分别分隔蓝球和红球注码
		String red[] = ballArray[0].split("\\" + sign);
		String blue[] = ballArray[1].split("\\" + sign);

		// 3.根据蓝球和红球各个总数排列组合得到注数
		int zhushu = BetcodeResolveUtil.getDoubleBallNumber(red.length,
				blue.length);

		return zhushu;
	}

	/**
	 * 
	 * 		 双色球复式 - 根据注码、倍数得金额 复式投注玩法包括：红单蓝复、红复蓝单、红复蓝复
	 * @param 
	 *      betcode 注码
	 *      以红复蓝复为例:1,6,10,5,3,4,7+1,2,3
	 * @param 
	 * 		multiple 倍数 例如倍数为1
	 * @param 
	 * 	    tab 红球和蓝球中间的分隔符 例子中的分隔符为"+"
	 * @param 
	 * 	    sign 传过来注码的格式标记符 例子中注码之间的分隔符为","
	 * @return 
	 * 		总金额 = 注数*倍数*单价（单注彩票的金额）
	 * 
	 */
	public static int getSSQDuplexMoney(String betcode, int multiple,
			String tab, String sign) {

		// 调用算注数的方法算得传入注码的注数
		int zhushu = getSSQDuplexZhushu(betcode, tab, sign);

		// 根据注数算总金额(注数*倍数*金额)并且返回
		return zhushu * multiple * Constant.LOTTERY_PRICE;
	}

	/**
	 * 
	 * 		 双色球复式 - 算注数 复式投注玩法包括：红单蓝复、红复蓝单、红复蓝复
	 * @param 
	 * 		redBall 红球
	 *      以红复蓝复为例:红球:1,6,10,5,3,4,7
	 * @param 
	 * 		blueBall 蓝球
	 *      以红复蓝复为例:蓝球:1,2,3
	 * @param 
	 * 		sign 传过来注码的格式标记符 例子中标记法为","
	 * @return 
	 * 		注数 例子中的注码注数为21
	 * 
	 */
	public static int getSSQDuplexZhushu1(String redBall, String blueBall,
			String sign) {

		// 1.根据注码之间的分隔符 分别分隔蓝球和红球注码
		String red[] = redBall.split("\\" + sign);
		String blue[] = blueBall.split("\\" + sign);

		// 2.根据蓝球和红球各个总数排列组合得到注数
		int zhushu = BetcodeResolveUtil.getDoubleBallNumber(red.length,
				blue.length);
		return zhushu;
	}

	/**
	 * 
	 * 		 双色球复式 - 根据注码、倍数得金额 复式投注玩法包括：红单蓝复、红复蓝单、红复蓝复
	 * @param 
	 * 		redBall 红球
	 *      以红复蓝复为例:红球:1,6,10,5,3,4,7
	 * @param 
	 * 		blueBall 蓝球
	 *      以红复蓝复为例:红球:1,6,10,5,3,4,7;蓝球:1,2,3
	 * @param 
	 * 		multiple 倍数 例如倍数为1
	 * @param 
	 * 		sign 传过来注码的格式标记符 例子中注码之间的标记符为","
	 * @return 
	 * 		总金额 = 注数*倍数*单价（单注彩票的金额）
	 * 
	 */
	public static int getSSQDuplexMoney1(String redBall, String blueBall,
			int multiple, String sign) {

		// 调用算注数的方法算得传入注码的注数
		int zhushu = getSSQDuplexZhushu1(redBall, blueBall, sign);

		// 根据注数算总金额(注数*倍数*金额)并且返回
		return zhushu * multiple * Constant.LOTTERY_PRICE;
	}

	/**
	 * 
	 * 		 双色球胆拖玩法 - 算注数 
	 * @param 
	 * 		betcode 注码以红胆拖蓝复式为例:10,2,4,3*5,7,9,6+1,3,2
	 * @param 
	 * 		tab 红球和蓝球中间的分隔符 例子中为"+"
	 * @param 
	 * 		sign 传过来注码的格式标记符 例子中为","
	 * @param 
	 * 		redTab 红球胆码与拖码之间的分隔符 例子中为"*"
	 * @return 
	 * 		注数 根据红球胆码个数、拖码个数、蓝球个数排列组合算注数 例子中注数为18注
	 * 
	 */
	public static int getSSQDantuoZhushu(String betcode, String tab,
			String redTab, String sign) {
		// 1.根据tab分隔符 分隔红球和蓝球
		String codeArray[] = betcode.split("\\" + tab);

		// 2.根据redTab分隔符 分隔红球胆码和红球拖码
		String redBall[] = codeArray[0].split("\\" + redTab);

		// 3.根据注码之间分隔符sign 得到红球胆码、拖码和蓝球
		String redBallDan[] = redBall[0].split("\\" + sign);
		String redBallTuo[] = redBall[1].split("\\" + sign);
		String blue[] = codeArray[1].split("\\" + sign);

		// 4.根据红球胆码个数、拖码个数、蓝球个数排列组合算注数
		int zhushu = BetcodeResolveUtil.getDoubleBallDantuoNumber(
				redBallDan.length, redBallTuo.length, blue.length);

		return zhushu;
	}

	/**
	 * 
	 * 		胆拖 - 根据注码、倍数得金额 
	 * @param 
	 * 		betcode 注码以红胆拖蓝复式为例:10,2,4,3*5,7,9,6+1,3,2
	 * @param 
	 * 		multiple 倍数 例如1倍
	 * @param 
	 * 		tab 红球和蓝球中间的分隔符 例子中为"+"
	 * @param 
	 * 		sign 传过来注码的格式标记符 例子中为","
	 * @param 
	 * 		redTab 红球胆码与拖码之间的分隔符 例子中为"*"
	 * @return 
	 * 		总金额 = 注数*倍数*单价（单注彩票的金额）
	 * 
	 */
	public static int getSSQDantuoMoney(String betcode, int multiple,
			String tab, String redTab, String sign) {

		// 1.调用算注数的方法getSSQDantuoZhushu 计算注数
		int zhushu = getSSQDantuoZhushu(betcode, tab, redTab, sign);

		// 2.算总金额(注数*倍数*金额)并且返回总金额
		return zhushu * multiple * Constant.LOTTERY_PRICE;
	}

	/**
	 * 
	 * 		双色球胆拖玩法 - 算注数 
	 * @param
	 * 		redBallDanma 红胆码以红胆拖蓝复式为例:红胆码:10,2,4,3 
	 * @param 
	 * 		redBallTuoma 红拖码以红胆拖蓝复式为例:红拖码:5,7,9,6
	 * @param 
	 * 		blueBall 以红胆拖蓝复式为例:蓝球:1,3,2
	 * @param 
	 * 		sign 传过来注码的格式标记符 例子中为","
	 * @return 
	 * 		注数 根据红球胆码个数、拖码个数、蓝球个数排列组合算注数 例子中注数为18注
	 * 
	 */
	public static int getSSQDantuoZhushu1(String redBallDanma,
			String redBallTuoma, String blueBall, String sign) {

		// 1.根据注码之间的分隔符sign,提取红球胆码、拖码和蓝球计算个数
		String redBallDan[] = redBallDanma.split("\\" + sign);
		String redBallTuo[] = redBallTuoma.split("\\" + sign);
		String blue[] = blueBall.split("\\" + sign);

		// 2.根据红球胆码个数、拖码个数、蓝球个数排列组合算注数并返回
		int zhushu = BetcodeResolveUtil.getDoubleBallDantuoNumber(
				redBallDan.length, redBallTuo.length, blue.length);

		return zhushu;
	}

	/**
	 * 
	 * 		双色球胆拖 - 根据注码、倍数得金额 
	 * @param 
	 * 		redBallDanma 红球胆码 以红胆拖蓝复式为例:红胆码:10,2,4,3;
	 * @param 
	 * 		redBallTuoma 红球拖码以红胆拖蓝复式为例:红拖码:5,7,9,6
	 * @param 
	 * 		blueBall 蓝球以红胆拖蓝复式为例:蓝球:1,3,2
	 * @param 
	 * 		multiple 倍数 例如1倍
	 * @param 
	 * 		sign 传过来注码的格式标记符 例子为","
	 * @return 
	 * 		总金额 = 注数*倍数*单价（单注彩票的金额）
	 * 
	 */
	public static int getSSQDantuoMoney1(String redBallDanma,
			String redBallTuoma, String blueBall, int multiple, String sign) {

		// 1.调用算注数的方法getSSQDantuoZhushu1 计算注数
		int zhushu = getSSQDantuoZhushu1(redBallDanma, redBallTuoma, blueBall,
				sign);

		// 2.总金额 = 注数*倍数*单价（单注彩票的金额）
		return zhushu * multiple * Constant.LOTTERY_PRICE;
	}

	/**
	 * 
	 * 		根据注码得到双色球的玩法
	 * @param 
	 * 		betcode 注码 例如:1,6,15,26,32,2+10^,
	 *               1,6,2,5,3,4,9,7,8,10+1^, 11,2,6,3,4,5+1,3,2^,
	 *               1,5,2,3,9,4,6,7+1,3,2^, 1,4,2,3*5,9,7,16+1^,
	 *               1,2,4,3*5,7,9,6+1,3,2^
	 * @param 
	 * 		tabNumber 注码之间分隔符 例子中为"^"
	 * @param 
	 * 		tab 红球与蓝球之间的分隔符 例子中为"+"
	 * @param 
	 * 		sign 注码之间的分隔符 例子中为","
	 * @param 
	 * 		redTab 胆码与拖码之间的分隔符 例子中为"*"
	 *            
	 * @return 
	 * 		返回玩法 例: 红单蓝单:"00"; 红复蓝单:"10"; 红单蓝复:"20"; 红复蓝复:"30"; 红拖蓝单:"40";
	 *         红拖蓝复:"50";
	 * 
	 * 
	 */
	public static String getSSQGameMethod(String betcode, String tabNumber,
			String tab, String sign, String redTab) {
		String gameMethod = "";
		String codes[] = betcode.split("\\" + tabNumber);
		for (int k = 0; k < codes.length; k++) {

			// 根据红球与蓝球之间的分隔符tab分隔蓝球与红球
			String redAndBlue[] = codes[k].split("\\" + tab);
			if(codes[k].indexOf(redTab) != -1){
				
				if (codes[k].indexOf(redTab) != -1) {// 红胆拖蓝复
					String ss2[] = redAndBlue[1].split("[" + sign + "]");
					if(ss2.length > 1){
						gameMethod = Constant.SSQ_RTBM;
					}else if(ss2.length==1){//红胆拖蓝单
						gameMethod = Constant.SSQ_RTBS;
					}
				} 
				
			}else{
				// 根据注码之间的分隔符sign 将红球和蓝球注码分开
				String ss1[] = redAndBlue[0].split("[" + sign + "]");
				String ss2[] = redAndBlue[1].split("[" + sign + "]");
	
				// 判断蓝球和红球的长度算双色球具体玩法如下：
				// 单式
				if (ss1.length == 6 && ss2.length == 1) {
					gameMethod = Constant.SSQ_RSBS;
	
				} else if (ss1.length > 6 && ss2.length == 1) {
					gameMethod = Constant.SSQ_RMBS;// 红复蓝单
	
				} else if (ss1.length == 6 && ss2.length > 1) {
					gameMethod = Constant.SSQ_RSBM;// 红单蓝复
	
				} else if (ss1.length > 6 && ss2.length > 1) {
					gameMethod = Constant.SSQ_RMBM;// 红复蓝复
					
				}
		    }
	   }
		return gameMethod;
	}

}
