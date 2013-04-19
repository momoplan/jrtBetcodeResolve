package com.jrt.betcodeResolve.lotnoBetcodeUtil;

import com.jrt.betcodeResolve.util.BetcodeResolveUtil;
import com.jrt.betcodeResolve.util.Constant;

/**
 * 
 * 时时彩注码解析 获取金额、注数、玩法的类 玩法包括(五星、三星、二星、一星、大小单双)
 * 
 * @author 徐丽
 */
public class SSCBetcodeUtil {

	/**
	 * 
	 * 时时彩单式注码解析算注数
	 * 
	 * @param betcode
	 *            注码 示例： 五星：1,2,3,5,6;4,5,6,7,8;4,5,6,7,8
	 *            三星：-,-,5,1,8;-,-,9,5,6;-,-,6,7,8;-,-,5,6,9
	 *            二星：-,-,-,1,8;-,-,-,5,6;-,-,-,7,8;-,-,-,6,9
	 *            一星：-,-,-,-,8;-,-,-,-,6;-,-,-,-,8;-,-,-,-,9
	 * @param tcTabNumber
	 *            注码之间的分隔符 示例中为";"
	 * @return 注数 示例中为均为3注
	 * 
	 */
	public static int getSSCSimplexZhushu(String betcode, String tcTabNumber) {
		// 根据注码直接的分隔符tcTabNumber解析注码
		String arrCode[] = betcode.split("\\" + tcTabNumber);

		// 算 注数并返回
		return arrCode.length;

	}

	/**
	 * 
	 * 时时彩单式注码解析算金额（适用于五星、三星、二星、一星）
	 * 
	 * @param betcode
	 *            注码 示例： 五星：1,2,3,5,6;4,5,6,7,8;2,4,5,6,9
	 *            三星：-,-,5,1,8;-,-,9,5,6;-,-,6,7,8;-,-,5,6,9
	 *            二星：-,-,-,1,8;-,-,-,5,6;-,-,-,7,8;-,-,-,6,9
	 *            一星：-,-,-,-,8;-,-,-,-,6;-,-,-,-,8;-,-,-,-,9
	 * @param multiple
	 *            倍数 为1倍
	 * @param tcTabNumber
	 *            注码之间的分隔符 示例中为";"
	 * @return 总金额 = 注数*倍数*单价(单张彩票的金额) 示例中均为6元
	 * 
	 */
	public static int getSSCSimplexMoney(String betcode, int multiple,
			String tcTabNumber) {
		// 调用单式算注数的方法得到注数
		int zhushu = getSSCSimplexZhushu(betcode, tcTabNumber);

		// 算总金额 = 注数*倍数*单价(单张彩票的金额)
		return zhushu * multiple * Constant.LOTTERY_PRICE;

	}

	/**
	 * 
	 * 时时彩直选复式注码解析得注数（适用于五星、三星、二星、一星）
	 * 
	 * @param betcode
	 *            注码 示例: 五星:1,2,8-0,2,3-1,2,8-0,2,3-3,4,9 三星:1,2,5-1,3-8,4
	 *            二星:1,2,5-1,3 一星:1,2,5
	 * @param qhTab
	 *            各个位数之间的分隔符 示例中为"-"
	 * @param sign
	 *            注码之间的分隔符 示例中为","
	 * @return 注数 示例中为 五星:243注；三星:12注；二星:6注；一星:3注
	 * 
	 */
	public static int getSSCDirectDoubleZhushu(String betcode, String qhTab,
			String sign) {
		int zhushu = 1;

		// 根据各位之间的分隔符qhTab分解各个位
		String arrCode[] = betcode.split("\\" + qhTab);
		for (int i = 0; i < arrCode.length; i++) {
			// 分别得到各位之间的注码数组
			String codes[] = arrCode[i].split("\\" + sign);
			zhushu *= codes.length;
		}
		return zhushu;
	}

	/**
	 * 
	 * 时时彩直选复式（五星通选）注码解析得金额
	 * 
	 * @param betcode
	 *            注码 示例: 五星:1,2,8-0,2,3-1,2,8-0,2,3-3,4,9 三星:1,2,5-1,3-8,4
	 *            二星:1,2,5-1,3 一星:1,2,5
	 * @param multiple
	 *            倍数 为1倍
	 * @param qhTab
	 *            各个位数之间分隔符 示例中为"-"
	 * @param sign
	 *            注码之间的分隔符 示例中为","
	 * @return 总金额 = 注数*倍数*单价(单张彩票的金额) 示例中 五星:486元；三星:24元；二星:12元；一星:6元
	 * 
	 */
	public static int getSSCDirectDoubleMoney(String betcode, int multiple,
			String qhTab, String sign) {

		// 将各个位数的个数相乘就是注码的注数
		int zhushu = getSSCDirectDoubleZhushu(betcode, qhTab, sign);

		// 算总金额 = 注数*倍数*单价(单张彩票的金额)并返回
		return zhushu * multiple * Constant.LOTTERY_PRICE;
	}

	/**
	 * 
	 * 时时彩二星直选和值解析注码算注数
	 * 
	 * @param betcode
	 *            和值数 示例:多个和值数:9,1,2
	 *            		单个和值数:9
	 * @param sign 示例中为","
	 * @return 注数 
	 * 			示例:多个和值数:15注   单个和值数:10注
	 * 
	 */
	public static int getSSCRXHezhiZhushu(String betcode,String sign) {
		int zhushu = 0;
		//若是多个和值
		if(betcode.indexOf(sign)>-1){
			String codes[]=betcode.split("\\"+sign);
			for(int i=0;i<codes.length;i++){
				zhushu+=Integer.parseInt(String.valueOf(BetcodeResolveUtil.getSSCRXHezhi(Integer.parseInt(codes[i]))));
			}
			
		}else{
			// 将和值数转换为整数
			int code = Integer.parseInt(betcode);
			// 调用算二星和值的算法算得注数返回
			zhushu = Integer.parseInt(String.valueOf(BetcodeResolveUtil.getSSCRXHezhi(code)));
		}
		return zhushu;
	}

	/**
	 * 
	 * 时时彩二星直选和值解析注码算金额
	 * 
	 * @param betcode
	 *            和值数 示例:2
	 * @param sign 
	 * @return 注数 示例:6元
	 * 
	 */
	public static int getSSCRXHezhiMoney(String betcode,String sign, int multiple) {

		// 调用算二星和值算注数的方法得到注数
		int zhushu = getSSCRXHezhiZhushu(betcode,sign);
		// 算总金额 = 注数*倍数*单价(单张彩票的金额)并返回
		return zhushu * multiple * Constant.LOTTERY_PRICE;
	}

	/**
	 * 时时彩二星组选和值算注数
	 * 
	 * @param betcode
	 *            和值数示例:2
	 * @return 注数 示例:2
	 */
	public static int getSSCRXZXHezhiZhushu(String betcode,String sign) {
		
		int zhushu = 0;
		//若是多个和值
		if(betcode.indexOf(sign)>-1){
			String codes[]=betcode.split("\\"+sign);
			for(int i=0;i<codes.length;i++){
				zhushu+=Integer.parseInt(String.valueOf(BetcodeResolveUtil.getSSCRXZXHezhi(Integer.parseInt(codes[i]))));
			}
			
		}else{
		
			// 将和值数转换为整数
			int code = Integer.parseInt(betcode);
			// 调用算法算注数并返回
			zhushu = Integer.parseInt(String.valueOf(BetcodeResolveUtil
					.getSSCRXZXHezhi(code)));
		}
		return zhushu;
	}

	/**
	 * 
	 * 时时彩二星组选和值解析注码算金额
	 * 
	 * @param betcode
	 *            和值数 示例:2
	 * @return 注数 示例:2元
	 * 
	 */
	public static int getSSCRXZXHezhiMoney(String betcode,String sign, int multiple) {

		// 调用算二星和值算注数的方法得到注数
		int zhushu = getSSCRXZXHezhiZhushu(betcode,sign);
		// 算总金额 = 注数*倍数*单价(单张彩票的金额)并返回
		return zhushu * multiple * Constant.LOTTERY_PRICE;
	}

	/**
	 * 
	 * 时时彩二星组选复式注码解析算注数
	 * 
	 * @param betcode
	 *            注码 示例:2,3,4
	 * @param sign
	 *            注码之间的分隔符 示例中为","
	 * @return 返回注数 示例中为3注
	 */
	public static int getSSCRXDirectDoubleZhushu(String betcode, String sign) {
		// 根据注码之间的分隔符sign解析注码
		String arrCode[] = betcode.split("\\" + sign);

		// 调用算组选复式的方法算得注数并返回
		return (int) BetcodeResolveUtil.nchoosek(arrCode.length, 2);
	}
	
	/**
	 * 
	 * 时时彩二星组选复式注码解析算注数
	 * 
	 * @param betcode
	 *            注码 示例:2,3,4
	 * @param sign
	 *            注码之间的分隔符 示例中为","
	 * @return 返回注数 示例中为6元
	 */
	public static int getSSCRXDirectDoubleMoney(String betcode,int multiple,String sign){
		// 调用算二星组选复式算注数的方法得到注数
		int zhushu = getSSCRXDirectDoubleZhushu(betcode,sign);
		// 算总金额 = 注数*倍数*单价(单张彩票的金额)并返回
		return zhushu * multiple * Constant.LOTTERY_PRICE;
	}

}
