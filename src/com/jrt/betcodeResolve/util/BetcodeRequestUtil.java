package com.jrt.betcodeResolve.util;

import java.util.List;

import com.jrt.betcodeResolve.bean.BetcodeBean;
import com.jrt.betcodeResolve.service.SSQResolveService;

/**
 * 
 * 拼接传给后台的所有彩种的串
 * 
 */
public class BetcodeRequestUtil {
	/**
	 * BetcodeRequestUtil.requestJrtLotBetcode(betcode, 1,"^", "+", ",", "*");
	 * 传给后台的注码串:1512-F47104-00-05-0001070809101112~03^0001010206152632~04^
	 * 0001010206152632~05^0001010206152632~06^0001070809101112~07^;
	 * 传给后台的注码串:1512
	 * -F47104-00-04-0001010206152632~08^0001010206152632~09^0001010206152632
	 * ~10^0001010206152632~10^;
	 * 传给后台的注码串:1512-F47104-10-210-1001*01020304050607080910~01^;
	 * 传给后台的注码串:1512-F47104-20-03-2001*020304050611~010203^;
	 * 传给后台的注码串:1512-F47104-30-84-3001*0102030405060709~010203^;
	 * 传给后台的注码串:1512-F47104-40-06-400101020304*05070916~01^;
	 * 传给后台的注码串:1512-F47104-50-18-500101020304*05060709~010203^;
	 * 
	 */
	public static String requestJrtLotBetcode(String betcode, int multiple,
			String tabNumber, String tab, String sign, String redTab) {

		List<BetcodeBean> list = SSQResolveService.getSSQBetcodeList(betcode,
				multiple, tabNumber, tab, sign, redTab);
		for (int i = 0; i < list.size(); i++) {
			//实体类
			BetcodeBean betcodeBean = list.get(i);
			//注数
			String zhushu = betcodeBean.getZhushu();
			//注码串
			String finalCode = Constant.CITY_CODE + Constant.QH_TAB
					+ Constant.SSQ + Constant.QH_TAB
					+ betcodeBean.getGameMethod() + Constant.QH_TAB
					+ (Integer.parseInt(zhushu) < 10 ? "0" + zhushu : zhushu)
					+ Constant.QH_TAB + betcodeBean.getBetcode();
			System.out.println("传给后台的注码串:" + finalCode + ";\n注码串的金额:"
					+ betcodeBean.getTotalMoney() + "元");
		}
		return "";
	}
}
