package com.jrt.betcodeResolve.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.jrt.betcodeResolve.bean.BetcodeBean;
import com.jrt.betcodeResolve.lotnoBetcodeUtil.DLTBetcodeUtil;
import com.jrt.betcodeResolve.resolve.DLTBetcodeResovle;
import com.jrt.betcodeResolve.util.BetcodeProxyResolve;
import com.jrt.betcodeResolve.util.Constant;


/**
 * 
 * 大乐透 将所有注码的 玩法、倍数、金额、注码实体bean中
 * 并将实体存入list中的类
 * @author
 * 		徐丽
 * 
 */
public class DLTResolveService {
	private static final Logger logger = Logger.getLogger(DLTResolveService.class);
	
	/**
	 * 
	 * 大乐透 将所有注码的 玩法、倍数、金额、注码实体bean中
     * 并将实体存入list中的方法
	 * @param 
	 * 		betcode 注码
	 *      示例:注码为:1,15,4,5,6+1,7;1,15,4,5,6+1,7;1,15,4,5,6+1,7; 
	 *					1,15,4,5,6+1,7;1,15,4,5,6+1,7;1,15,4,5,6+1,7; //单式
	 *					1,2,15,4,5,6+1,7,2; //复式
	 *					1,2$3,4,5,6,7,8,9+1$2,3; //胆拖
	 *					1,2;//十二选二单式
	 *					1,2,3,4//十二选二复式
	 * @param 
	 * 		multiple 倍数 为1倍
	 * @param 
	 * 		tabNumber 各注之间的分隔符 示例中为";"
	 * @param 
	 * 		tab 红球和蓝球之间的分隔符 示例中为"+"
	 * @param 
	 * 		sign 注码之间的分隔符 示例中为","
	 * @param 
	 * 		dtTab 胆码和拖码之间的分隔符 示例中为"$"
	 * @param 
	 * 		superaddition 是否为追加     true-追加 false-不追加
	 * @return   
	 * 		实体bean的集合
	 * 		示例的结果如：
	 * 			大乐透实体内容:(玩法:单式;注码:01 15 04 05 06-01 07;01 15 04 05 06-01 07;01 15 04 05 06-01 07;01 15 04 05 06-01 07;
	 * 					01 15 04 05 06-01 07;注数:5注;金额:10元=1000分)
	 * 			大乐透实体内容:(玩法:单式;注码:01 15 04 05 06-01 07;注数:1注;金额:2元=200分)
	 * 			大乐透实体内容:(玩法:复式;注码:01 02 15 04 05 06-01 07 02;注数:18注;金额:36元=3600分)
	 * 			大乐透实体内容:(玩法:胆拖;注码:01 02$03 04 05 06 07 08 09-01$02 03;注数:70注;金额:140元=14000分)
	 * 			大乐透实体内容:(玩法:十二选二;注码:01 02;注数:1注;金额:2元=200分)
	 * 			大乐透实体内容:(玩法:十二选二;注码:01 02 03 04;注数:6注;金额:12元=1200分)
	 * 
	 */
	public static List<BetcodeBean> getDLTBetcodeList(String betcode,
			int multiple, String tabNumber,String tab, String sign,
			String dtTab, boolean superaddition) {
		
		List<BetcodeBean> list = new ArrayList<BetcodeBean>();
		BetcodeBean betcodeBean = null;
		String wanfa = "";
		String zhuma = "";
		int zhushu = 0;
		int totalMoney = 0;
		Vector<String> vector = null;
		try{
			//得到大乐透注码的数组对象vector
			//(betcode, "^", "+", ",", "$")
			//vector = BetcodeProxyResolve.getDLTVector(betcode, tabNumber, tab, sign, dtTab);
			vector = BetcodeProxyResolve.getVector(betcode, tabNumber);
			
			//循环遍历vector数组对象
			for(int i=0;i<vector.size();i++){
				betcodeBean = new BetcodeBean();
				betcodeBean.setLotno(Constant.DLT);
				
				// 得到所有的注码
				String lotteryCodes = (String) vector.get(i);
				wanfa = DLTBetcodeUtil.getDLTGameMethod(lotteryCodes, tabNumber, tab, sign, dtTab);
				
				//单式
				if(Constant.DLT_DS.equals(wanfa)){
				   zhuma = DLTBetcodeResovle.getDLTSimple(lotteryCodes, tabNumber, sign, tab);
				   zhushu = DLTBetcodeUtil.getDLTSimplexZhushu(lotteryCodes, tabNumber);
				   totalMoney = DLTBetcodeUtil.getDLTSimplexMoney(lotteryCodes, multiple, tabNumber, superaddition);
				   
				//复式
				}else if(Constant.DLT_FS.equals(wanfa)){
					lotteryCodes = lotteryCodes.replace(tabNumber, "");
					zhuma = DLTBetcodeResovle.getDLTDuplex(lotteryCodes, sign, tab);
					zhushu = DLTBetcodeUtil.getDLTDuplexZhushu(lotteryCodes, tab, sign);
					totalMoney = DLTBetcodeUtil.getDLTDuplexMoney(lotteryCodes, multiple, tab, sign, superaddition);
				
				//胆拖
				}else if(Constant.DLT_DT.equals(wanfa)){
					lotteryCodes = lotteryCodes.replace(tabNumber, "");
					zhuma = DLTBetcodeResovle.getDLTDantuo(lotteryCodes, sign, tab, dtTab);
					zhushu = DLTBetcodeUtil.getDLTDantuoZhushu(lotteryCodes, tab, dtTab, sign);
					totalMoney = DLTBetcodeUtil.getDLTDantuoMoney(lotteryCodes, multiple, tab, dtTab, sign, superaddition);
				
				//生肖乐（十二选二）
				}else if(Constant.DLT_SXL.equals(wanfa)){
					lotteryCodes = lotteryCodes.replace(tabNumber, "");
					zhuma = DLTBetcodeResovle.getAnimalHappy(lotteryCodes, sign);
					zhushu = DLTBetcodeUtil.getDLTAnimalHappyZhushu(lotteryCodes, sign);
					totalMoney = DLTBetcodeUtil.getDLTAnimalHappyMoney(lotteryCodes, multiple, sign);
				}
				
				//设置到betcodeBean实体bean中
				betcodeBean.setMultiple(String.valueOf(multiple));
				betcodeBean.setGameMethod(wanfa);
				betcodeBean.setZhushu(String.valueOf(zhushu));
				betcodeBean.setBetcode(zhuma);
				betcodeBean.setTotalMoney(String.valueOf(totalMoney));
				
				//添加到list集合中
				list.add(betcodeBean);
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("大乐透DLTVector="+vector+"tabNumber:"+tabNumber+"大乐透解析客户端传入的注码的注数、金额异常Exception" + e.toString());
		}
		return list;
	}
}
