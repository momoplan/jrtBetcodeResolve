package com.jrt.betcodeResolve.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.jrt.betcodeResolve.bean.BetcodeBean;
import com.jrt.betcodeResolve.lotnoBetcodeUtil.PLSBetcodeUtil;
import com.jrt.betcodeResolve.resolve.PLSBetcodeResovle;
import com.jrt.betcodeResolve.util.BetcodeProxyResolve;
import com.jrt.betcodeResolve.util.Constant;


/**
 * 
 * 	  排三注码将所有注码的 玩法、倍数、金额、注码实体bean中的类
 * @author
 * 	  徐丽
 * 
 */
public class PLSResolveService {
	
	private static Logger logger = Logger.getLogger(PLSResolveService.class);
	
	/**
	 * 
	 * 
	 * @param betcode 注码
	 * 		011,3,2;011,2,2;011,3,2;011,2,2;011,3,2;011,2,2;//直选单式 
	 *		061,3,3;061,2,2;061,2,2;064,5,6;067,9,8; //组选单式
	 *		S120; //直选和值 
	 *		S920; //组选和值
	 *		S320; //组三和值
	 *		S620; //组六和值
	 *		F32,3,8,4,9,6,7;//组三包号
	 *		F62,3,8,4,9,6,7;//组六包号
	 *
	 * @param multiple 倍数 示例中为1倍
	 * @param tabNumber 多注之间的分隔符 示例中为";"
	 * @param sign 各个注码之间的分隔符 示例中为","
	 * @param qhTab 直选复式的符号"-"
	 * @return 实体内容的集合  
	 * 		示例结果如下：
	 * 		PLS实体内容:(玩法:01;注码:1|1,3,2;1,2,2;1,3,2;1,2,2;1,3,2;;注数:5注;总金额:10元=1000分)
	 * 		PLS实体内容:(玩法:01;注码:1|1,2,2;;注数:1注;总金额:2元=200分)
	 * 		PLS实体内容:(玩法:06;注码:6|1,3,3;1,2,2;1,2,2;4,5,6;7,9,8;;注数:5注;总金额:10元=1000分)
	 * 		PLS实体内容:(玩法:S1;注码:S1|20;注数:36注;总金额:72元=7200分)
	 * 		PLS实体内容:(玩法:S9;注码:S9|20;注数:8注;总金额:16元=1600分)
	 * 		PLS实体内容:(玩法:S3;注码:S3|20;注数:4注;总金额:8元=800分)
	 * 		PLS实体内容:(玩法:S6;注码:S6|20;注数:4注;总金额:8元=800分)
	 * 		PLS实体内容:(玩法:F3;注码:F3|2,3,8,4,9,6,7;注数:42注;总金额:84元=8400分)
	 * 		PLS实体内容:(玩法:F6;注码:F6|2,3,8,4,9,6,7;注数:35注;总金额:70元=7000分)
	 * 
	 */
	public static List<BetcodeBean> getPLSBetcodeList(String betcode,
			int multiple, String tabNumber, String sign,String qhTab) {
		
		List<BetcodeBean> list = new ArrayList<BetcodeBean>();
		BetcodeBean betcodeBean = null;
		String zhuma = "";
		int zhushu = 0;
		int totalMoney = 0;
		Vector<String> vector = null;
		
		try{
			//调用算注码的方法得到存注码数组vector对象
			//vector = BetcodeProxyResolve.getPLSVector(betcode, tabNumber,qhTab);
			vector = BetcodeProxyResolve.getVector(betcode, tabNumber);
			
			//循环该对象得到不同玩法的注码、注数、总金额
			for(int i=0;i<vector.size();i++){
				betcodeBean = new BetcodeBean();
				betcodeBean.setLotno(Constant.PLS);
				
				//倍数
				betcodeBean.setMultiple(String.valueOf(multiple));
				//得到注码
				String code = vector.get(i);
				//玩法
				String wanfa = code.substring(0,2);
				betcodeBean.setGameMethod(wanfa);
				
				/**
				 * 直选单式、复式、组选单式
				 */
				if (Constant.PLS_ZHX.equals(wanfa)
						||Constant.PLS_ZX.equals(wanfa)) {
					String dsCodes[]=code.split("\\"+tabNumber);
					String dsCode = "";
					for(int j=0;j<dsCodes.length;j++){
						dsCode+=dsCodes[j].substring(2)+tabNumber;
					}
					

					//判断是排三单式还是复式
					if(dsCode.indexOf(qhTab)>-1){
//						logger.info("玩法:"+wanfa +";复式:"+dsCode);
						//算得的注数
						zhushu = PLSBetcodeUtil.getPLSDirectDoubleZhushu(dsCode, qhTab, sign);
						//算得金额
						totalMoney = PLSBetcodeUtil.getPLSDirectDoubleMoney(dsCode, multiple, qhTab, sign);
						//调用算注码的方法算得注码
						zhuma = PLSBetcodeResovle.getPLSBetcode(dsCode.replace(",", ";").replace("-", ",").replace(";", ""), wanfa, sign,tabNumber);
						
					}else{
//						logger.info("玩法:"+wanfa +";单式:"+dsCode);
						//算得的注数
						zhushu = PLSBetcodeUtil.getPLSSimplexZhushu(dsCode, tabNumber);
						//算得金额
						totalMoney = PLSBetcodeUtil.getPLSSimplexMoney(dsCode, multiple, tabNumber);
						//调用算注码的方法算得注码
						zhuma = PLSBetcodeResovle.getPLSBetcode(dsCode, wanfa, sign,tabNumber);
					}

				
				/**
				 * 直选和值、组三和值、组六和值
				 */
				}else if(Constant.PLS_ZHXHZ.equals(wanfa)
						||Constant.PLS_ZXHZ.equals(wanfa)
						||Constant.PLS_ZSHZ.equals(wanfa)
						||Constant.PLS_ZLHZ.equals(wanfa)){
					
					//去掉玩法得注码
					String fscode = code.substring(2).replace(tabNumber, "");
//					logger.info("玩法:" + wanfa +";和值:"+fscode);
					//调用算注码的方法算得注码
					zhuma = PLSBetcodeResovle.getPLSBetcode(fscode, wanfa, sign,tabNumber);
					//算得的注数
					zhushu = PLSBetcodeUtil.getPLSHezhiZhushu(fscode, wanfa);
					//算得金额
					totalMoney = PLSBetcodeUtil.getPLSHezhiMoney(fscode, multiple, wanfa);
				
				
				/**
				 * 组三包号、组六包号
				 */
				}else if(Constant.PLS_ZSBH.equals(wanfa)
						|| Constant.PLS_ZLBH.equals(wanfa)){
					//去掉玩法得注码
					String fscode = code.substring(2).replace(tabNumber, "");
//					logger.info("玩法:" + wanfa +";包号:"+fscode);
					
					//调用算注码的方法算得注码
					zhuma = PLSBetcodeResovle.getPLSBetcode(fscode, wanfa, sign,tabNumber);
					
					//算得的注数
					zhushu = PLSBetcodeUtil.getPLSDirectAndGroupZhushu(fscode, sign, wanfa);
					//算得金额
					totalMoney = PLSBetcodeUtil.getPLSDirectAndGroupMoney(fscode, multiple, sign, wanfa);
					
				}
				
				//设置注码的实体类中包括注码、注数、总金额
				betcodeBean.setBetcode(zhuma);
				betcodeBean.setZhushu(String.valueOf(zhushu));
				betcodeBean.setTotalMoney(String.valueOf(totalMoney));
				
				//将设置的betcodeBean添加到list中去
				list.add(betcodeBean);
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.info("传入的注码betcode:" + betcode);
			logger.error("体彩排列三注码解析算金额、得注数vector"+vector+"异常Exception:"+e.toString());
		}
		return list;
	}
	
}
