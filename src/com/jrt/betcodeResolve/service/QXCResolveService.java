package com.jrt.betcodeResolve.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.jrt.betcodeResolve.bean.BetcodeBean;
import com.jrt.betcodeResolve.lotnoBetcodeUtil.QXCBetcodeUtil;
import com.jrt.betcodeResolve.resolve.QXCBetcodeResovle;
import com.jrt.betcodeResolve.util.BetcodeProxyResolve;
import com.jrt.betcodeResolve.util.Constant;


/**
 * 
 * 	  七星彩将所有注码的 玩法、倍数、金额、注码实体bean中
 * @author
 * 	  徐丽
 * 
 */
public class QXCResolveService {
	
	private static Logger logger = Logger.getLogger(QXCResolveService.class);
	
	/**
	 * 
	 * 七星彩注码解析
	 * @param 
	 * 		betcode 注码
	 * 		1,2,3,5,6,3,4;4,5,6,7,8,5,6;2,4,5,6,9,5,6;2,4,5,6,9,5,6;2,4,5,6,9,5,6;2,4,5,6,9,5,6;//单式 
	 *		1,2-3-2,1,2-5,0-3-7,8,2,4-6,8,3,4,9,0;1,2-3-2,1,2-5,0-3-7,8,2,4-6,8,3,4,9,0 //直选复式
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
	 * 		(注码:1,2,3,5,6,3,4;4,5,6,7,8,5,6;2,4,5,6,9,5,6;2,4,5,6,9,5,6;2,4,5,6,9,5,6;;注数:5注;总金额:10元=1000分)
	 * 		(注码:2,4,5,6,9,5,6;;注数:1注;总金额:2元=200分)
	 * 		(注码:12,3,212,50,3,7824,683490;;注数:288注;总金额:576元=57600分)
	 * 		(注码:12,3,212,50,3,7824,683490;;注数:288注;总金额:576元=57600分)
	 * 		
	 */
	public static List<BetcodeBean> getQXCBetcodeList(String betcode,
			int multiple, String tcTabNumber, String sign,String qhTab) {
		
		List<BetcodeBean> list = new ArrayList<BetcodeBean>();
		BetcodeBean betcodeBean = null;
		String zhuma = "";
		int zhushu = 0;
		int totalMoney = 0;
		Vector<String> vector = null;
		
		try{
			//调用算注码的方法得到存注码数组vector对象
			//vector = BetcodeProxyResolve.getQXCVector(betcode, tcTabNumber,qhTab);
			vector = BetcodeProxyResolve.getVector(betcode, tcTabNumber);
			
			//循环该对象得到不同玩法的注码、注数、总金额
			for(int i=0;i<vector.size();i++){
				betcodeBean = new BetcodeBean();
				betcodeBean.setLotno(Constant.QXC);
				
				//倍数
				betcodeBean.setMultiple(String.valueOf(multiple));
				//得到注码
				String code = vector.get(i);
				String dsCodes[] = code.split("\\"+tcTabNumber);
				String dsCode = "";
				for(int j=0;j<dsCodes.length;j++){
					dsCode+=dsCodes[j]+tcTabNumber;
				}
				
				//判断是七星彩单式还是复式
				if(dsCode.indexOf(qhTab)>-1){
				    System.out.println("复式:"+dsCode);
					//算得的注数
					zhushu = QXCBetcodeUtil.getQXCDirectDoubleZhushu(dsCode, qhTab, sign);
					//算得金额
					totalMoney = QXCBetcodeUtil.getQXCDirectDoubleMoney(dsCode, multiple, qhTab, sign);
					//调用算注码的方法算得注码
					zhuma = QXCBetcodeResovle.getQXCDirectDoubleBetcode(dsCode, qhTab, sign);	
				}else{
					System.out.println("单式:"+dsCode);
					//算得的注数
					zhushu = QXCBetcodeUtil.getQXCSimplexZhushu(dsCode, tcTabNumber);
					//算得金额
					totalMoney = QXCBetcodeUtil.getQXCSimplexMoney(dsCode, multiple, tcTabNumber);
					//调用算注码的方法算得注码
					zhuma = QXCBetcodeResovle.getQXCSimplexBetcode(dsCode, tcTabNumber, sign);
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
			logger.error("体彩七星彩注码解析算金额、得注数vector"+vector+"异常Exception:"+e.toString());
		}
		return list;
	}
	
}
