package com.jrt.betcodeResolve.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.jrt.betcodeResolve.bean.BetcodeBean;
import com.jrt.betcodeResolve.lotnoBetcodeUtil.SYXWBetcodeUtil;
import com.jrt.betcodeResolve.resolve.SYXWBetcodeResolve;
import com.jrt.betcodeResolve.util.BetcodeProxyResolve;
import com.jrt.betcodeResolve.util.Constant;

/**
 * 江西11选5 将所有注码 玩法、倍数、金额、注码存入注码实体bean中 并且存入list中返回的类
 * 
 * @author 徐丽
 * 
 */
public class SYXWResolveService {
	private static final Logger logger = Logger
			.getLogger(SYXWResolveService.class);

	/**
	 * 
	 * @param betcode
	 *            注码 示例中为： //任选1-8 R11;R11;R11;R11;R11;R11;R11;R11,2,3,4,5,6;
	 *            R21,2;R21,2;R21,2;R21,10; + R21,2,3,4;
	 *            R31,2,3;R31,2,3;R31,2,3;R31,2,3;R31,2,3; R31,2,3;
	 *            R41,2,3,4;R41,2,3,4,5,6,7;
	 *            R51,2,3,4,5;R51,2,3,4,5,6,9,10;
	 *            R61,2,3,4,5,6;R61,2,3,4,5,6,7,8;
	 *            R71,2,3,4,5,6,7;R71,2,3,4,5,6,7,8;
	 *            R81,2,3,4,5,6,7,8;R81,2,3,4,5,6,7,8; //前二和前三组选
	 *            Z24,10;Z24,10;Z24,10;Z24,10;Z24,10; Z21,2,3,4,5,6,7,8;
	 *            Z31,7,10;Z31,7,10;Z31,7,10;Z31,7,10;
	 *            Z31,2,3,4,5,6,7,8,9;+ //胆拖 R21$5,8,10;R31$5,8,10;
	 *            R41,2$5,6,7,8,10;R51,2$5,6,7,8,10;
	 *            R61,2$5,6,7,8,10;R71,2$5,6,7,8,10,11;
	 *            R81,2,3,5,6,7,8$10,11; Z21$5,8,10;Z31$5,6,7,8,10;+
	 *            //前二和前三直选 Q22-8;Q22-8;Q22-8;Q22-8;Q22-8;Q22-8;
	 *            Q21,2,3,4,5-8,9;
	 *            Q36-9-10;Q36-9-10;Q36-9-10;Q36-9-10;Q36-9-10;
	 *            Q31,4,6-5,8,9-7,10,11;;
	 * @param multiple
	 *            倍数 示例中为1倍
	 * @param tcTabNumber
	 *            各注之间的分隔符 示例中为";"
	 * @param qhTab
	 *            各位之间的分隔符 示例中为"-"(前二或前三直选有)
	 * @param sign
	 *            各注之间的分隔符 示例中为","
	 * @param dtTab
	 *            胆码和拖码之间的分隔符 示例中为"$"
	 * @return (玩法:R1;注码:R1|01;R1|01;R1|01;R1|01;R1|01;;注数:5注;金额:10元=1000分)
	 *         (玩法:R1;注码:R1|01;R1|01;;注数:2注;金额:4元=400分) 
	 *         (玩法:R2;注码:R2|01 02;R2|01 02;R2|01 02;R2|01 10;;注数:4注;金额:8元=800分)
	 *         (玩法:R3;注码:R3|01 02 03;R3|01 02 03;R3|01 02 03;R3|01 02 03;R3|01 02 03;;注数:5注;金额:10元=1000分) 
	 *         (玩法:R3;注码:R3|01 02 03;;注数:1注;金额:2元=200分)
	 *         (玩法:R4;注码:R4|01 02 03 04;;注数:1注;金额:2元=200分) 
	 *         (玩法:R5;注码:R5|01 02 03 04 05;;注数:1注;金额:2元=200分) 
	 *         (玩法:R6;注码:R6|01 02 03 04 05 06;;注数:1注;金额:2元=200分) 
	 *         (玩法:R7;注码:R7|01 02 03 04 05 06 07;;注数:1注;金额:2元=200分) 
	 *         (玩法:R8;注码:R8|01 02 03 04 05 06 07 08;R8|01 02 03 04 05 06 07 08;;注数:2注;金额:4元=400分)
	 *         (玩法:Q2;注码:Q2|02,08;Q2|02,08;Q2|02,08;Q2|02,08;Q2|02,08;;注数:5注;金额:10元=1000分)
	 *         (玩法:Q2;注码:Q2|02,08;;注数:1注;金额:2元=200分)
	 *         (玩法:Q3;注码:Q3|06,09,10;Q3|06,09,10;Q3|06,09,10;Q3|06,09,10;Q3|06,09,10;;注数:5注;金额:10元=1000分)
	 *         (玩法:Z2;注码:Z2|04 10;Z2|04 10;Z2|04 10;Z2|04 10;Z2|04 10;;注数:5注;金额:10元=1000分) 
	 *         (玩法:Z3;注码:Z3|01 07 10;Z3|01 07 10;Z3|01 07 10;Z3|01 07 10;;注数:4注;金额:8元=800分) 
	 *         (玩法:R1;注码:R1|01 02 03 04 05 06;;注数:6注;金额:12元=1200分) 
	 *         (玩法:R2;注码:R2|01 02 03 04;;注数:6注;金额:12元=1200分) 
	 *         (玩法:R4;注码:R4|01 02 03 04 05 06 07;;注数:35注;金额:70元=7000分) 
	 *         (玩法:R5;注码:R5|01 02 03 04 05 06 09 10;;注数:56注;金额:112元=11200分) 
	 *         (玩法:R6;注码:R6|01 02 03 04 05 06 07 08;;注数:28注;金额:56元=5600分)
	 *         (玩法:R7;注码:R7|01 02 03 04 05 06 07 08;;注数:8注;金额:16元=1600分)
	 *         (玩法:Z2;注码:Z2|01 02 03 04 05 06 07 08;;注数:28注;金额:56元=5600分) 
	 *         (玩法:Z3;注码:Z3|01 02 03 04 05 06 07 08 09;;注数:84注;金额:168元=16800分) 
	 *         (玩法:R2;注码:R2|01$05 08 10;;注数:3注;金额:6元=600分) 
	 *         (玩法:R3;注码:R3|01$05 08 10;;注数:3注;金额:6元=600分)
	 *         (玩法:R4;注码:R4|01 02$05 06 07 08 10;;注数:10注;金额:20元=2000分)
	 *         (玩法:R5;注码:R5|01 02$05 06 07 08 10;;注数:10注;金额:20元=2000分)
	 *         (玩法:R6;注码:R6|01 02$05 06 07 08 10;;注数:5注;金额:10元=1000分)
	 *         (玩法:R7;注码:R7|01 02$05 06 07 08 10 11;;注数:6注;金额:12元=1200分)
	 *         (玩法:R8;注码:R8|01 02 03 05 06 07 08$10 11;;注数:2注;金额:4元=400分)
	 *         (玩法:Z2;注码:Z2|01$05 08 10;;注数:3注;金额:6元=600分) 
	 *         (玩法:Z3;注码:Z3|01$05 06 07 08 10;;注数:10注;金额:20元=2000分) 
	 *         (玩法:Q2;注码:Q2|01 02 03 04 05,08 09;;注数:10注;金额:20元=2000分) 
	 *         (玩法:Q3;注码:Q3|01 04 06,05 08 09,07 10 11;;注数:27注;金额:54元=5400分)
	 */
	public static List<BetcodeBean> getSYXWBetcodeList(String betcode,
			int multiple, String tcTabNumber, String qhTab, String sign,
			String dtTab) {

		List<BetcodeBean> list = new ArrayList<BetcodeBean>();
		BetcodeBean betcodeBean = null;
		Vector<String> vector = null;

		try {
			// 得到所有江西11选5注码的数组对象vector
			//vector = BetcodeProxyResolve.getSYXWVector(betcode, tcTabNumber,sign, dtTab, qhTab);
			vector = BetcodeProxyResolve.getVector(betcode, tcTabNumber);

			String zhuma = ""; // 单个注码
			int zhushu = 0; // 注数
			int totalMoney = 0; // 总金额
			for (int i = 0; i < vector.size(); i++) {
				betcodeBean = new BetcodeBean();
				betcodeBean.setBetcode(Constant.SYXW);
				betcodeBean.setMultiple(String.valueOf(multiple));
				// 得到所有的注码
				String lotteryCodes = (String) vector.get(i);
				String wanfa = lotteryCodes.substring(0, 2);// 获取玩法
				betcodeBean.setGameMethod(wanfa);

				// 胆拖
				if (lotteryCodes.indexOf(dtTab) > -1) {
					zhuma = SYXWBetcodeResolve.getSYXWRXDTBetcode(lotteryCodes,
							tcTabNumber, sign, dtTab);
					zhushu = SYXWBetcodeUtil.getSYXW_RX_DTZhushu(lotteryCodes,
							sign, dtTab);
					totalMoney = SYXWBetcodeUtil.getSYXW_RX_DTMoney(
							lotteryCodes, sign, dtTab, multiple);
					// 前二或前三直选
				} else if (lotteryCodes.indexOf(qhTab) > -1) {
					zhuma = SYXWBetcodeResolve.getSYXWQZBetcode(lotteryCodes,
							tcTabNumber, sign, qhTab);
					String codes[] = lotteryCodes.split("\\" + tcTabNumber);
					for (int j = 0; j < codes.length; j++) {
						zhushu = SYXWBetcodeUtil.getSYXWQXZhushu(codes[j],
								sign, qhTab)
								* codes.length;
						totalMoney = SYXWBetcodeUtil.getSYXWQXMoney(codes[j],
								sign, qhTab, multiple)
								* codes.length;
					}

				} else {
					zhuma = SYXWBetcodeResolve.getSYXWRXBetcode(lotteryCodes,
							tcTabNumber, sign);
					// 前二或前三组选
					if (wanfa.equals(Constant.SYXW_ZX2)
							|| wanfa.equals(Constant.SYXW_ZX3)) {
						String codes[] = lotteryCodes.split("\\" + tcTabNumber);
						for (int j = 0; j < codes.length; j++) {
							zhushu = SYXWBetcodeUtil.getSYXWQZZhushu(codes[j],
									sign)
									* codes.length;
							totalMoney = SYXWBetcodeUtil.getSYXWQZMoney(
									codes[j], sign, multiple)
									* codes.length;
						}
					} else {// 任选1-8直选单复式
						String codes[] = lotteryCodes.split("\\" + tcTabNumber);
						for (int j = 0; j < codes.length; j++) {
							zhushu = SYXWBetcodeUtil.getSYXWRXZhushu(codes[j],
									sign)
									* codes.length;
							totalMoney = SYXWBetcodeUtil.getSYXWRXMoney(
									codes[j], sign, multiple)
									* codes.length;
						}
					}
				}
				betcodeBean.setBetcode(zhuma);
				betcodeBean.setZhushu(String.valueOf(zhushu));
				betcodeBean.setTotalMoney(String.valueOf(totalMoney));
				list.add(betcodeBean);
			}
		} catch (Exception e) {
			logger.info("传入的注码betcode:" + betcode);
			logger.error("vector=" + vector + "江西11选5解析客户端传入的注码的注数、金额异常Exception"
					+ e.toString());
			e.printStackTrace();
		}
		return list;
	}
}
