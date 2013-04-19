package com.jrt.betcodeResolve.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.jrt.betcodeResolve.bean.BetcodeBean;
import com.jrt.betcodeResolve.lotnoBetcodeUtil.SSQBetcodeUtil;
import com.jrt.betcodeResolve.resolve.SSQBetcodeResolve;
import com.jrt.betcodeResolve.util.BetcodeProxyResolve;
import com.jrt.betcodeResolve.util.Constant;

/**
 *      福彩双色球 将所有注码 玩法、倍数、金额、注码存入注码实体bean中
 * 		并且存入list中返回的类
 * @author 
 * 		徐丽
 * 
 */
@SuppressWarnings( { "unchecked" })
public class SSQResolveService {
	private static final Logger logger = Logger.getLogger(SSQResolveService.class);
	
	/**
	 * 
	 * 		         通过注码存储的数组分析注码设置到实体bean中并且存入list中返回的类
	 * 			
	 * @param betcode 注码
	 * 		     例:注码 7,10,8,12,9,11+3^1,6,15,26,32,2+4^1,6,15,26,32,2+5^
	 * 				1,6,15,26,32,2+6^7,10,8,12,9,11+7^1,6,15,26,32,2+8^
	 * 				1,6,15,26,32,2+9^1,6,15,26,32,2+10^1,6,15,26,32,2+10^ //单式-红单蓝单  
	 *              1,6,2,5,3,4,9,7,8,10+1^ //红复蓝单 
	 *              11,2,6,3,4,5+1,3,2^ //红单蓝复 
	 *              1,5,2,3,9,4,6,7+1,3,2^//红复蓝复 
	 *              1,4,2,3*5,9,7,16+1^//红胆拖蓝单 
	 *              1,2,4,3*5,7,9,6+1,3,2^//红胆拖蓝复
	 * @param multiple
	 *            倍数
	 * @param tabNumber
	 *            注码之间分隔符 例子中为"^"
	 * @param tab
	 *            红球与蓝球之间的分隔符 例子中为"+"
	 * @param sign
	 *            注码之间的分隔符 例子中为","
	 * @param redTab
	 *            胆码与拖码之间的分隔符 例子中为"*"
	 * @return 实体集合 示例中为:
	 *         注码:0001070809101112~03^0001010206152632~04^0001010206152632~05^
	 *         0001010206152632~06^0001070809101112~07^; 注数:5;倍数:1;玩法:00;金额:10元
	 *         注码:0001010206152632~08^0001010206152632~09^0001010206152632~10^
	 *         0001010206152632~10^;注数:4;倍数:1;玩法:00;金额:8元
	 *         注码:1001*01020304050607080910~01^;注数:210;倍数:1;玩法:10;金额:420元
	 *         注码:2001*020304050611~010203^;注数:3;倍数:1;玩法:20;金额:6元
	 *         注码:3001*0102030405060709~010203^;注数:84;倍数:1;玩法:30;金额:168元
	 *         注码:400101020304*05070916~01^;注数:6;倍数:1;玩法:40;金额:12元
	 *         注码:500101020304*05060709~010203^;注数:18;倍数:1;玩法:50;金额:36元
	 *         
	 */
	public static List<BetcodeBean> getSSQBetcodeList(String betcode,
			int multiple, String tabNumber, String tab, String sign,
			String redTab) {

		List<BetcodeBean> list = new ArrayList();
		BetcodeBean betcodeBean = null;
		Vector<String> vector = null;

		try {
			// 得到所有双色球注码的数组对象vector
			vector = BetcodeProxyResolve.getVector(betcode,tabNumber);

//			logger.info("tabNumber=" + tabNumber + ";tab=" + tab + ";sign="
//					+ sign + ";redTab=" + redTab);

			String zhuma = ""; // 单个注码
			int zhushu = 0; // 注数
			int totalMoney = 0; // 总金额
			for (int i = 0; i < vector.size(); i++) {
				betcodeBean = new BetcodeBean();
				betcodeBean.setLotno(Constant.SSQ);
				// 得到所有的注码
				String lotteryCodes = (String) vector.get(i);
				// 调用玩法
				String wanfa = SSQBetcodeUtil.getSSQGameMethod(lotteryCodes,
						tabNumber, tab, sign, redTab);
				// 倍数
				betcodeBean.setMultiple(String.valueOf(multiple));
				betcodeBean.setGameMethod(wanfa);

				// 单式(红单蓝单)
				if (Constant.SSQ_RSBS.equals(wanfa)) {
					lotteryCodes = lotteryCodes + Constant.TABNUMBER;
					logger.info("lotteryCodes=" + lotteryCodes);

					// 调用算注码的方法得到注码
					zhuma = SSQBetcodeResolve.getSSQSimplex(multiple,
							lotteryCodes, tabNumber, tab, sign);

					// 调用算单式注数的方法得到注数
					zhushu = SSQBetcodeUtil.getSSQSimplexZhushu(lotteryCodes,
							tabNumber);

					// 单式总金额
					totalMoney = SSQBetcodeUtil.getSSQSimplexMoney(
							lotteryCodes, multiple, tabNumber);
//					logger.info("单式:注码=" + zhuma + ";注数=" + zhushu + "注;总金额="
//							+ totalMoney + "元");
					
					// 设置到实体类中
					betcodeBean.setBetcode(zhuma);
					betcodeBean.setZhushu(String.valueOf(zhushu));
					betcodeBean.setTotalMoney(String.valueOf(totalMoney));

					// 复式(红复蓝单、红单蓝复、红复蓝复)
				} else if (Constant.SSQ_RMBS.equals(wanfa)
						|| Constant.SSQ_RSBM.equals(wanfa)
						|| Constant.SSQ_RMBM.equals(wanfa)) {
					lotteryCodes = lotteryCodes.replace(tabNumber, "");
//					logger.info("lotteryCodes=" + lotteryCodes);

					// 分别得到复式各种玩法的注码
					if (Constant.SSQ_RMBS.equals(wanfa)) {
						zhuma = SSQBetcodeResolve.getSSQRedDuplexBlueSimplex(
								multiple, lotteryCodes, tab, sign);
					} else if (Constant.SSQ_RSBM.equals(wanfa)) {
						zhuma = SSQBetcodeResolve.getSSQRedSimplexBlueDuplex(
								multiple, lotteryCodes, tab, sign);
					} else if (Constant.SSQ_RMBM.equals(wanfa)) {
						zhuma = SSQBetcodeResolve.getSSQRedDuplexBlueDuplex(
								multiple, lotteryCodes, tab, sign);
					}
					zhushu = SSQBetcodeUtil.getSSQDuplexZhushu(lotteryCodes,
							tab, sign);// 复式注数
					totalMoney = SSQBetcodeUtil.getSSQDuplexMoney(lotteryCodes,
							multiple, tab, sign);// 复式总金额

//					logger.info("复式:注码=" + zhuma + ";注数=" + zhushu + "注;总金额="
//							+ totalMoney + "元");
					betcodeBean.setBetcode(zhuma);
					betcodeBean.setZhushu(String.valueOf(zhushu));
					betcodeBean.setTotalMoney(String.valueOf(totalMoney));

					// 胆拖(红胆拖蓝复、红胆拖蓝单)
				} else if (Constant.SSQ_RTBS.equals(wanfa)
						|| Constant.SSQ_RTBM.equals(wanfa)) {
					lotteryCodes = lotteryCodes.replace(tabNumber, "");
					logger.info("lotteryCodes=" + lotteryCodes);

					// 分别得到复式各种玩法的注码
					if (Constant.SSQ_RTBS.equals(wanfa)) {
						//双色球胆拖
						zhuma = SSQBetcodeResolve.getSSQRedDanTuoBlueSimplex(
								multiple, lotteryCodes, tab, redTab, sign);
					} else if (Constant.SSQ_RTBM.equals(wanfa)) {
						zhuma = SSQBetcodeResolve.getSSQRedDanTuoBlueDuplex(
								multiple, lotteryCodes, tab, redTab, sign);
					}
					zhushu = SSQBetcodeUtil.getSSQDantuoZhushu(lotteryCodes,
							tab, redTab, sign);// 胆拖注数
					totalMoney = SSQBetcodeUtil.getSSQDantuoMoney(lotteryCodes,
							multiple, tab, redTab, sign);// 胆拖总金额

//					logger.info("胆拖:注码=" + zhuma + ";注数=" + zhushu + "注;总金额="
//							+ totalMoney + "元");
					betcodeBean.setBetcode(zhuma);
					betcodeBean.setZhushu(String.valueOf(zhushu));
					betcodeBean.setTotalMoney(String.valueOf(totalMoney));
				}
				list.add(betcodeBean);
			}
		} catch (Exception e) {
			logger.info("传入的注码betcode:" + betcode);
			logger.error("vector=" + vector+"双色球解析客户端传入的注码的注数、金额异常Exception" + e.toString());
			e.printStackTrace();
		}
		return list;
	}
}
