package com.jrt.betcodeResolve.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.jrt.betcodeResolve.bean.BetcodeBean;
import com.jrt.betcodeResolve.lotnoBetcodeUtil.PLWBetcodeUtil;
import com.jrt.betcodeResolve.resolve.PLWBetcodeResovle;
import com.jrt.betcodeResolve.util.BetcodeProxyResolve;
import com.jrt.betcodeResolve.util.Constant;


/**
 * 
 * 	  排列五将所有注码的 玩法、倍数、金额、注码实体bean中
 * @author
 * 	  徐丽
 * 
 */
public class PLWResolveService {
	
	private static Logger logger = Logger.getLogger(PLWResolveService.class);
	
	/**
	 * 
	 * 排列五注码解析
	 * @param 
	 * 		betcode 注码
	 * 		1,2,3,5,6;4,5,6,7,8;2,4,5,6,9;2,4,5,6,9;2,4,5,6,9;2,4,5,6,9;2,4,5,6,9;//单式 
	 *		1,2,8-0,2,3-1,2,8-0,2,3-3,4,9;1,2,8-0,2,3-1,2,8-0,2,3-3,4,9 //直选复式
	 *
	 * @param 
	 * 		multiple 倍数 示例中为1倍
	 * @param 
	 * 		tcTabNumber 多注之间的分隔符 示例中为";"
	 * @param 
	 * 		sign 各个注码之间的分隔符 示例中为","
	 * @param 
	 * 		qhTab 直选复式的符号"-"
	 * @return 
	 * 		实体内容的集合
	 * 		(注码:1,2,3,5,6;4,5,6,7,8;2,4,5,6,9;2,4,5,6,9;2,4,5,6,9;;注数:5注;总金额:10元=1000分)
	 *		(注码:2,4,5,6,9;2,4,5,6,9;;注数:2注;总金额:4元=400分)
	 *		(注码:128,023,128,023,349;;注数:243注;总金额:486元=48600分)
	 *		(注码:128,023,128,023,349;;注数:243注;总金额:486元=48600分)  
	 * 		
	 */
	public static List<BetcodeBean> getPLWBetcodeList(String betcode,
			int multiple, String tcTabNumber, String sign,String qhTab) {
		
		List<BetcodeBean> list = new ArrayList<BetcodeBean>();
		BetcodeBean betcodeBean = null;
		String zhuma = "";
		int zhushu = 0;
		int totalMoney = 0;
		Vector<String> vector = null;
		
		try{
			//调用算注码的方法得到存注码数组vector对象
			//vector = BetcodeProxyResolve.getPLWVector(betcode, tcTabNumber,qhTab);
			vector = BetcodeProxyResolve.getVector(betcode, tcTabNumber);
			
			//循环该对象得到不同玩法的注码、注数、总金额
			for(int i=0;i<vector.size();i++){
				betcodeBean = new BetcodeBean();
				betcodeBean.setLotno(Constant.PLW);
				
				//倍数
				betcodeBean.setMultiple(String.valueOf(multiple));
				//得到注码
				String code = vector.get(i);
				String dsCodes[] = code.split("\\"+tcTabNumber);
				String dsCode = "";
				for(int j=0;j<dsCodes.length;j++){
					dsCode+=dsCodes[j]+tcTabNumber;
				}
				
				//判断是排列五单式还是复式
				if(dsCode.indexOf(qhTab)>-1){
				    System.out.println("复式:"+dsCode);
					//算得的注数
					zhushu = PLWBetcodeUtil.getPLWDirectDoubleZhushu(dsCode, qhTab, sign);
					//算得金额
					totalMoney = PLWBetcodeUtil.getPLWDirectDoubleMoney(dsCode, multiple, qhTab, sign);
					//调用算注码的方法算得注码
					zhuma = PLWBetcodeResovle.getPLWDirectDoubleBetcode(dsCode, qhTab, sign);	
				}else{
					System.out.println("单式:"+dsCode);
					//算得的注数
					zhushu = PLWBetcodeUtil.getPLWSimplexZhushu(dsCode, tcTabNumber);
					//算得金额
					totalMoney = PLWBetcodeUtil.getPLWSimplexMoney(dsCode, multiple, tcTabNumber);
					//调用算注码的方法算得注码
					zhuma = PLWBetcodeResovle.getPLWSimplexBetcode(dsCode, tcTabNumber, sign);
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
			logger.error("体彩排列五注码解析算金额、得注数vector"+vector+"异常Exception:"+e.toString());
		}
		return list;
	}
	
}
