package com.jrt.betcodeResolve.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.jrt.betcodeResolve.bean.BetcodeBean;
import com.jrt.betcodeResolve.lotnoBetcodeUtil.SYYDJBetcodeUtil;
import com.jrt.betcodeResolve.resolve.SYYDJBetcodeResolve;
import com.jrt.betcodeResolve.util.BetcodeProxyResolve;
import com.jrt.betcodeResolve.util.Constant;

/**
 * 十一运夺金 将所有注码 玩法、倍数、金额、注码存入注码实体bean中 并且存入list中返回的类
 * 
 * 
 */
public class SYYDJResolveService {
	private static final Logger logger = Logger
			.getLogger(SYYDJResolveService.class);

	/**
	 * 
	 * @param betcode
	 *            注码 示例中为： //任选1-8 （复式，胆拖，单式）
	 *            101!*0102^;
	 *            102!*010203^;111!0102^;121!01*02030405060708^;
	 *            103!*010203040506070809^;112!010203^;122!01*02030405060708^;
	 *            104!*010203040506070809^;113!01020304^;123!01*02030405060708^;
	 *            105!*010203040506070809^;114!0102030405^;124!01*02030405060708^;
	 *            106!*010203040506070809^;115!010203040506^;125!01*02030405060708^;
	 *            107!*010203040506070809^;116!01020304050607^;126!01*02030405060708^;
	 *            117!0102030405060708^;
	 *           
	 *            //前二和前三组选（复式，胆拖，单式）
	 *            108!01*0203040506070809^;131!0102^;133!01*0203^;
	 *            109!01*0203040506070809^;151!010203^;153!01*020304^;
	 *            
	 *            //前二和前三直选(定位复式，单式)
	 *            142!*010203^;141!0102^;
	 *            162!*01020304^;161!010203^;
	 *            
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
	 * @return 
	 */
	public static List<BetcodeBean> getSYYDJBetcodeList(String betcode,
			int multiple, String tcTabNumber, String qhTab, String sign,
			String dtTab) {

		List<BetcodeBean> list = new ArrayList<BetcodeBean>();
		BetcodeBean betcodeBean = null;
		Vector<String> vector = null;

		try {
			// 得到所有十一运夺金注码的数组对象vector
			//vector = BetcodeProxyResolve.getSYXWVector(betcode, tcTabNumber,sign, dtTab, qhTab);
			vector = BetcodeProxyResolve.getVector(betcode, tcTabNumber);

			String zhuma = ""; // 单个注码
			int zhushu = 0; // 注数
			int totalMoney = 0; // 总金额
			for (int i = 0; i < vector.size(); i++) {
				betcodeBean = new BetcodeBean();
				betcodeBean.setBetcode(Constant.SYYDJ);
				betcodeBean.setMultiple(String.valueOf(multiple));
				// 得到所有的注码
				String lotteryCodes = (String) vector.get(i);
				String wanfa = lotteryCodes.substring(0, 3);// 获取玩法
				betcodeBean.setGameMethod(wanfa);

				// 胆拖
				if (lotteryCodes.indexOf(dtTab) > -1) {
					zhuma = SYYDJBetcodeResolve.getSYYDJRXDTBetcode(lotteryCodes,
							tcTabNumber, sign, dtTab);
					zhushu = SYYDJBetcodeUtil.getSYYDJ_RX_DTZhushu(lotteryCodes,
							sign, dtTab);
					totalMoney = SYYDJBetcodeUtil.getSYYDJ_RX_DTMoney(
							lotteryCodes, sign, dtTab, multiple);
					// 前二或前三直选
				} else if (lotteryCodes.indexOf(qhTab) > -1) {
					zhuma = SYYDJBetcodeResolve.getSYYDJQZBetcode(lotteryCodes,
							tcTabNumber, sign, qhTab);
					String codes[] = lotteryCodes.split("\\" + tcTabNumber);
					for (int j = 0; j < codes.length; j++) {
						zhushu = SYYDJBetcodeUtil.getSYYDJQXZhushu(codes[j],
								sign, qhTab)
								* codes.length;
						totalMoney = SYYDJBetcodeUtil.getSYYDJQXMoney(codes[j],
								sign, qhTab, multiple)
								* codes.length;
					}

				} else {
					zhuma = SYYDJBetcodeResolve.getSYYDJRXBetcode(lotteryCodes,
							tcTabNumber, sign);
					// 前二或前三组选
					if (wanfa.equals("133")
							|| wanfa.equals("153")) {
						String codes[] = lotteryCodes.split("\\" + tcTabNumber);
						for (int j = 0; j < codes.length; j++) {
							zhushu = SYYDJBetcodeUtil.getSYYDJQZZhushu(codes[j],
									sign)
									* codes.length;
							totalMoney = SYYDJBetcodeUtil.getSYYDJQZMoney(
									codes[j], sign, multiple)
									* codes.length;
						}
					} else {// 任选1-8直选单复式
						String codes[] = lotteryCodes.split("\\" + tcTabNumber);
						for (int j = 0; j < codes.length; j++) {
							zhushu = SYYDJBetcodeUtil.getSYYDJRXZhushu(codes[j],
									sign)
									* codes.length;
							totalMoney = SYYDJBetcodeUtil.getSYYDJRXMoney(
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
			logger.error("vector=" + vector + "十一运夺金解析客户端传入的注码的注数、金额异常Exception"
					+ e.toString());
			e.printStackTrace();
		}
		return list;
	}
}
