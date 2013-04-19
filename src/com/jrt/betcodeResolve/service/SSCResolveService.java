package com.jrt.betcodeResolve.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.jrt.betcodeResolve.bean.BetcodeBean;
import com.jrt.betcodeResolve.lotnoBetcodeUtil.SSCBetcodeUtil;
import com.jrt.betcodeResolve.resolve.SSCBetcodeResolve;
import com.jrt.betcodeResolve.util.BetcodeProxyResolve;
import com.jrt.betcodeResolve.util.SSCConstant;

/**
 * 
 * 时时彩解析存入vector的注码算得金额、注数、注码
 * 
 * @author 徐丽
 * 
 */
public class SSCResolveService {

	/**
	 * 
	 * 时时彩将所有注码的 玩法、倍数、金额、注码实体bean中 并将实体存入list中的方法
	 * 
	 * @param betcode
	 *            注码 示例:"5D1,2,3,5,6;5D4,5,6,7,8;5D2,4,5,6,9;5D2,4,5,6,9;5D2,4,5,6,9;5D2,4,5,6,9;"// 五星单式
	 *            "5T1,2,3,5,6;5T4,5,6,7,8;5T2,4,5,6,9;"// 五星通选单式
	 *            "3D5,1,8;3D9,5,6;3D6,7,8;3D5,6,9;"// 三星单式
	 *            "2D1,8;2D5,6;2D7,8;2D6,9;"// 二星单式 
	 *            "1D8;1D6;1D8;1D9;"// 一星单式
	 *            "DD1,2;"// 大小单双单式 
	 *            "H25;"//二星直选和值（1-8个）
	 *            "S25;"//二星组选和值（1-8个）
	 *            "F25;"//二星组选复式（3-7个）
	 *            "5D1,2,8-0,2,3-1,2,8-0,2,3-3,4,9;"//五星直选复式
	 *            "5T1,2,8-0,2,3-1,2,8-0,2,3-3,4,9;"// 五星通选复式
	 *            "DD1,2,4-1,2;"//大小单双 
	 *            "3D1,2,5-1,3-8,4;"// 三星直选复式 
	 *            "2D1,2,5-1,3;"// 二星直选复式
	 *            "1D1,2,5;"// 一星直选复式
	 * @param multiple
	 *            倍数 示例为1倍
	 * @param tcTabNumber
	 *            多注之间的分隔符 示例中为";"
	 * @param sign
	 *            多注之间的分隔符 示例中为","
	 * @param qhTab
	 *            直选复式各位之间的分隔符 示例中为"-"
	 * @return 返回的实际玩法和注码
	 * 示例为：SSC实体内容:(玩法:5D;注码:5D|1,2,3,5,6;4,5,6,7,8;2,4,5,6,9;2,4,5,6,9;2,4,5,6,9;;注数:5注;总金额:10元=1000分)
	 * 		(玩法:5D;注码:5D|2,4,5,6,9;;注数:1注;总金额:2元=200分)
	 * 		(玩法:3D;注码:3D|-,-,5,1,8;-,-,9,5,6;-,-,6,7,8;-,-,5,6,9;注数:4注;总金额:8元=800分)
	 * 		(玩法:2D;注码:2D|-,-,-,1,8;-,-,-,5,6;-,-,-,7,8;-,-,-,6,9;注数:4注;总金额:8元=800分)
	 * 		(玩法:1D;注码:1D|-,-,-,-,8;-,-,-,-,6;-,-,-,-,8;-,-,-,-,9;注数:4注;总金额:8元=800分)
	 * 		(玩法:5T;注码:5T|1,2,3,5,6;4,5,6,7,8;2,4,5,6,9;;注数:3注;总金额:6元=600分)
	 * 		(玩法:DD;注码:DD|12;;注数:1注;总金额:2元=200分)
	 * 		(玩法:H2;注码:H2|5;;注数:6注;总金额:12元=1200分)
	 * 		(玩法:S2;注码:S2|5;注数:3注;总金额:6元=600分)
	 * 		(玩法:F2;注码:F2|456;;注数:3注;总金额:6元=600分)
	 * 		(玩法:5D;注码:5D|128,023,128,023,349;;注数:243注;总金额:486元=48600分)
	 * 		(玩法:5T;注码:5T|128,023,128,023,349;;注数:243注;总金额:486元=48600分)
	 * 		(玩法:DD;注码:DD|124,12;;注数:6注;总金额:12元=1200分)
	 * 		(玩法:3D;注码:3D|-,-,125,13,84;注数:12注;总金额:24元=2400分)
	 * 		(玩法:2D;注码:2D|-,-,-,125,13;注数:6注;总金额:12元=1200分)
	 * 		(玩法:1D;注码:1D|-,-,-,-,125;注数:3注;总金额:6元=600分)
	 * 
	 */
	public static List<BetcodeBean> getSSCBetcodeList(String betcode,
			int multiple, String tcTabNumber, String sign, String qhTab) {
		List<BetcodeBean> list = new ArrayList<BetcodeBean>();
		BetcodeBean betcodeBean = null;
		String zhuma = "";
		int zhushu = 0;
		int totalMoney = 0;
		Vector<String> vector = null;
		// 调用存入vector中的所有注码的方法解析注码、注数、金额
		//vector = BetcodeProxyResolve.getSSCVector(betcode, tcTabNumber, qhTab, sign);
		vector = BetcodeProxyResolve.getVector(betcode, tcTabNumber);

		// 循环该对象得到不同玩法的注码、注数、总金额
		for (int i = 0; i < vector.size(); i++) {
			betcodeBean = new BetcodeBean();
			betcodeBean.setLotno(SSCConstant.SSC);
			betcodeBean.setMultiple(String.valueOf(multiple));

			String code = vector.get(i);
			String wanfa = code.substring(0, 2);
			betcodeBean.setGameMethod(wanfa);// 设置玩法
			
			// 获取新注码
			String fsCodes[] = code.split("\\" + tcTabNumber);
			String fsCode = "";
			for (int j = 0; j < fsCodes.length; j++) {
				fsCode += fsCodes[j].substring(2) + tcTabNumber;
			}

			// 判断改注码是单式还是复式
			if (code.indexOf(qhTab) > -1) {
				// 五星直选复式和五星通选复式
				zhuma = SSCBetcodeResolve.getSSCDirectDoubleBetcode(fsCode,sign, tcTabNumber, qhTab, wanfa);
				zhushu = SSCBetcodeUtil.getSSCDirectDoubleZhushu(fsCode, qhTab,sign);
				totalMoney = SSCBetcodeUtil.getSSCDirectDoubleMoney(fsCode,
						multiple, qhTab, sign);

			} else {
				if (wanfa.equals(SSCConstant.SSC_EXZXFS)) {// 二星组选复式
					zhuma = SSCBetcodeResolve.getSSCRXBetcode(fsCode, sign, wanfa);
					zhushu = SSCBetcodeUtil.getSSCRXDirectDoubleZhushu(fsCode, sign);
					totalMoney = SSCBetcodeUtil.getSSCRXDirectDoubleMoney(fsCode, multiple, sign);
				} else if (wanfa.equals(SSCConstant.SSC_EXFXHZ)) {// 二星直选和值
					zhuma = SSCBetcodeResolve.getSSCRXBetcode(fsCode, sign, wanfa);
					zhushu = SSCBetcodeUtil.getSSCRXHezhiZhushu(fsCode.replace(tcTabNumber, ""), sign);
					totalMoney = SSCBetcodeUtil.getSSCRXHezhiMoney(fsCode.replace(tcTabNumber, ""), sign, multiple);
				} else if (wanfa.equals(SSCConstant.SSC_EXZXHZ)) {// 二星组选和值
					zhuma = SSCBetcodeResolve.getSSCRXBetcode(fsCode.replace(tcTabNumber, ""), sign, wanfa);
					zhushu = SSCBetcodeUtil.getSSCRXZXHezhiZhushu(fsCode.replace(tcTabNumber, ""), sign);
					totalMoney = SSCBetcodeUtil.getSSCRXZXHezhiMoney(fsCode.replace(tcTabNumber, ""), sign, multiple);
				} else if (wanfa.equals(SSCConstant.SSC_YX)) {// 一星
					//获取一星内容判断其是否是复式
					if(fsCode.indexOf(sign)>-1){
						zhushu = SSCBetcodeUtil.getSSCDirectDoubleZhushu(fsCode,qhTab, sign);
						totalMoney = SSCBetcodeUtil.getSSCDirectDoubleMoney(fsCode,multiple, qhTab, sign);
					}else{
						zhushu = SSCBetcodeUtil.getSSCSimplexZhushu(fsCode, tcTabNumber);
						totalMoney = SSCBetcodeUtil.getSSCSimplexMoney(fsCode, multiple, tcTabNumber);
					}
					zhuma = SSCBetcodeResolve.getSSCDirectDoubleBetcode(fsCode,sign, tcTabNumber, qhTab, wanfa);
					
				} else {
					zhuma = SSCBetcodeResolve.getSSCSimplexBetcode(fsCode,tcTabNumber, wanfa, sign);
					zhushu = SSCBetcodeUtil.getSSCSimplexZhushu(fsCode,tcTabNumber);
					totalMoney = SSCBetcodeUtil.getSSCSimplexMoney(fsCode,multiple, tcTabNumber);
				}
			}
			// 设置注码的实体类中包括注码、注数、总金额
			betcodeBean.setBetcode(zhuma);
			betcodeBean.setZhushu(String.valueOf(zhushu));
			betcodeBean.setTotalMoney(String.valueOf(totalMoney));

			// 将设置的betcodeBean添加到list中去
			list.add(betcodeBean);

		}
		return list;
	}
}
