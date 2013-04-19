package com.jrt.betcodeResolve.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.jrt.betcodeResolve.bean.BetcodeBean;
import com.jrt.betcodeResolve.lotnoBetcodeUtil.EEXWBetcodeUtil;
import com.jrt.betcodeResolve.resolve.EEXWBetcodeResolve;
import com.jrt.betcodeResolve.util.BetcodeProxyResolve;
import com.jrt.betcodeResolve.util.Constant;


/**
 * 
 * 		22选5 将所有注码 玩法、倍数、金额、注码存入注码实体bean中
 * 		并且存入list中返回的类
 * @author
 * 		徐丽
 * 
 */
public class EEXWResolveService {
	private static final Logger logger = Logger.getLogger(EEXWResolveService.class);
	
	/**
	 * 
	 *      根据前台传过来的注码，将玩法、拼接的注码、金额、倍数、注数存入实体bean中
	 *      并且存入list中返回的类
	 * @param betcode 注码 示例:注码为:1,7,2,6,3,4,5^8,13,9,10,11,12,14^
	 *      15,20,16,17,18,19,21^22,24,23,25,26,27,28^
	 *      32,29,30,31,33,34,35^22,24,23,25,26,27,28^//单式
	 *      1,7,2,6,3,4,5,8,9,10^//复式
	 *      1,3,2*4,6,8,7,5,9,10^//胆拖
	 * @param multiple
	 * 			倍数
	 * @param tabNumber
	 * 			多注之间的分隔符 示例中为"^"
	 * @param sign
	 * 			注码之间的分隔符 示例中为","
	 * @param redTab
	 * 			胆码和拖码之间的分隔符 示例中为"*"
	 * @return 
	 * 		   实体bean的集合
	 *       示例为:
	 *       玩法:00;注码:000101020304050607^000108091011121314^000115161718192021^000122232425262728^000129303132333435^;
	 *             注数:5注;金额:10元=1000分
	 *       玩法:00;注码:000122232425262728^;注数:1注;金额:2元=200分
	 *       玩法:10;注码:1001*01020304050607080910^;注数:120注;金额:240元=24000分
	 *       玩法:20;注码:2001010203*04050607080910^;注数:35注;金额:70元=7000分
	 *       
	 */
	public static List<BetcodeBean> getEEXWBetcodeList(String betcode,
			int multiple, String tabNumber, String sign,String redTab) {
		
		List<BetcodeBean> list = new ArrayList<BetcodeBean>();
		BetcodeBean betcodeBean = null;
		String wanfa = "";
		String zhuma = "";
		int zhushu = 0;
		int totalMoney = 0;
		Vector<String> vector = null;
		try{
			//得到22选5注码的数组对象vector
			//vector = BetcodeProxyResolve.getEEXWVector(betcode, tabNumber, sign, redTab);
			vector = BetcodeProxyResolve.getVector(betcode, tabNumber);
			
			//循环遍历vector数组对象
			for(int i=0;i<vector.size();i++){
				betcodeBean = new BetcodeBean();
				betcodeBean.setLotno(Constant.EEXW);
				// 得到所有的注码
				String lotteryCodes = (String) vector.get(i);
				
				//得到22选5的玩法
				wanfa = EEXWBetcodeUtil.getEEXWGameMethod(lotteryCodes, tabNumber, sign, redTab);
				
				/**
				 * 22选5单式玩法
				 */
				if(Constant.EEXW_DS.equals(wanfa)){
//					logger.info("单式5注为一串:"+lotteryCodes);
					 //单式注码
				     zhuma = EEXWBetcodeResolve.getEEXWSimplex(multiple, lotteryCodes, tabNumber, sign);
				     //单式注数
				     zhushu = EEXWBetcodeUtil.getEEXWSimplexZhushu(lotteryCodes, tabNumber);
				     //单式总金额
				     totalMoney = EEXWBetcodeUtil.getEEXWSimplexMoney(lotteryCodes, multiple, tabNumber);
				 
				/**
				 * 22选5复式玩法
				 */
				}else if(Constant.EEXW_FS.equals(wanfa)){
					lotteryCodes = lotteryCodes.replace(tabNumber, ""); 
//					logger.info("复式:"+lotteryCodes);
					 //复式注码
				     zhuma = EEXWBetcodeResolve.getEEXWDuplex(multiple, lotteryCodes, sign);
				     //复式注数
				     zhushu = EEXWBetcodeUtil.getEEXWDuplexZhushu(lotteryCodes, sign);
				     //复式总金额
				     totalMoney = EEXWBetcodeUtil.getEEXWDuplexMoney(lotteryCodes, multiple, sign);
				     
				/**
				 * 22选5胆拖
				 */
				}else if(Constant.EEXW_DT.equals(wanfa)){
					 lotteryCodes = lotteryCodes.replace(tabNumber, ""); 
//					 logger.info("胆拖:"+lotteryCodes);
					 //胆拖注码
				     zhuma = EEXWBetcodeResolve.getEEXWDanTuo(multiple, lotteryCodes, redTab, sign);
				     //胆拖注数
				     zhushu = EEXWBetcodeUtil.getEEXWDanTuoZhushu(lotteryCodes, redTab, sign);
				     //胆拖总金额
				     totalMoney = EEXWBetcodeUtil.getEEXWDanTuoMoney(lotteryCodes, multiple, redTab, sign);
				}
				
				//将倍数、玩法、注数、注码、金额设置到实体类中
				betcodeBean.setMultiple(String.valueOf(multiple));
				betcodeBean.setGameMethod(wanfa);
				betcodeBean.setZhushu(String.valueOf(zhushu));
				betcodeBean.setBetcode(zhuma);
				betcodeBean.setTotalMoney(String.valueOf(totalMoney));
				
				//添加的集合中并返回
				list.add(betcodeBean);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.info("传入的注码betcode:" + betcode);
			logger.error("22选5EEXWVector="+vector+"22选5解析客户端传入的注码的注数、金额异常Exception" + e.toString());
		}
		return list;
	}
}
