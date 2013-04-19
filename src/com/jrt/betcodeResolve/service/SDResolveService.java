package com.jrt.betcodeResolve.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.jrt.betcodeResolve.bean.BetcodeBean;
import com.jrt.betcodeResolve.lotnoBetcodeUtil.SDBetcodeUtil;
import com.jrt.betcodeResolve.resolve.SDBetcodeResolve;
import com.jrt.betcodeResolve.util.BetcodeProxyResolve;
import com.jrt.betcodeResolve.util.Constant;


/**
 * 
 * 		福彩3D 将所有注码 玩法、倍数、金额、注码存入注码实体bean中
 * 		并且存入list中返回的类
 * @author
 * 		徐丽
 * 
 */
public class SDResolveService {
	private static final Logger logger = Logger.getLogger(SDResolveService.class);
	/**
	 *  
	 *      根据前台传过来的玩法+注码，将玩法、拼接的注码、金额、倍数、注数存入实体bean中
	 * @param betcode 注码
	 * 		例:001,3,2^001,2,2^001,3,2^001,2,2^001,3,2^, 001,2,2^, //直选单式
	 *      011,3,2^011,2,2^014,5,6^017,9,8^, //组三单式
	 *      021,2,2^024,5,6^027,9,8^, 		//组六单式
	 *      1020^, 1120^, 1220^, 	//直选和值、组三和值、组六和值
	 *      201,4,2,3,5+6,9,7,8,10,0+1,5,2,3,4,6,7^, //位选包号 (直选复式)
	 *      312,3,8,4,9,6,7^, //组三复式
	 *      322,3,8,4,9,6,7^, //组六复式
	 *      343,6,7,8,4,5^, //单选单复式(直选包号)
	 *      542,1*5,4,3^   //胆拖复式
	 * @param multiple
	 * 			倍数
	 * @param tabNumber
	 * 			多注之间的分隔符 示例中为"^"
	 * @param tab
	 * 			百位、十位、个位之间的分隔符 示例中为"+"
	 * @param sign
	 * 			注码之间的分隔符 示例中为","
	 * @param redTab
	 * 			胆码和拖码之间的分隔符 示例中为"*"
	 * @return 
	 * 		   实体bean的集合
	 *    示例中为:
	 * 		玩法:00;注码:0001010302^0001010202^0001010302^0001010202^0001010302^;注数:5注;总金额:10元=1000分.
	 * 		玩法:00;注码:0001010202^;注数:1注;总金额:2元=200分.
	 * 		玩法:01;注码:0101010203^0101010202^0101040506^0101070809^;注数:4注;总金额:8元=800分.
	 * 		玩法:02;注码:0201010202^0201040506^0201070809^;注数:3注;总金额:6元=600分.
	 * 		玩法:10;注码:100120^;注数:36注;总金额:72元=7200分.
	 * 		玩法:11;注码:110120^;注数:4注;总金额:8元=800分.
	 *		玩法:12;注码:120120^;注数:4注;总金额:8元=800分.
	 * 		玩法:20;注码:2001050102030405^06000607080910^0701020304050607^;注数:210注;总金额:420元=42000分.
	 * 		玩法:31;注码:31010702030406070809^;注数:42注;总金额:84元=8400分.
	 * 		玩法:32;注码:32010702030406070809^;注数:35注;总金额:70元=7000分.
	 *		玩法:34;注码:340106030405060708^;注数:120注;总金额:240元=24000分.
	 * 		玩法:54;注码:54010102*030405^;注数:18注;总金额:36元=3600分.
	 * 
	 */
	public static List<BetcodeBean> getSDBetcodeList(String betcode,
			int multiple, String tabNumber, String tab, String sign,
			String redTab) {
	
		List<BetcodeBean> list = new ArrayList<BetcodeBean>();
		BetcodeBean betcodeBean = null;
		String zhuma = "";
		int zhushu = 0;
		int totalMoney = 0;
		Vector<String> vector = null;
		
		try{
			//调用算注码的方法得到存注码数组vector对象
			//vector = BetcodeProxyResolve.getSDVector(betcode, tabNumber);
			vector = BetcodeProxyResolve.getVector(betcode, tabNumber);
			
			//循环该对象得到不同玩法的注码、注数、总金额
			for(int i=0;i<vector.size();i++){
				betcodeBean = new BetcodeBean();
				betcodeBean.setLotno(Constant.SD);
				
				//倍数
				betcodeBean.setMultiple(String.valueOf(multiple));
				//得到注码
				String code = vector.get(i);
				//玩法
				String wanfa = code.substring(0,2);
				betcodeBean.setGameMethod(wanfa);
				
				/**
				 * 直选单式、组三单式、组六单式
				 */
				if (Constant.SD_ZXDS.equals(wanfa)
						||Constant.SD_Z3DS.equals(wanfa)
						||Constant.SD_Z6DS.equals(wanfa)) {
					String dsCodes[]=code.split("\\"+tabNumber);
					String dsCode = "";
					for(int j=0;j<dsCodes.length;j++){
						dsCode+=dsCodes[j].substring(2)+tabNumber;
					}
//					logger.info("玩法:"+wanfa +";单式:"+dsCode);
					//调用算注码的方法算得注码
					zhuma = SDBetcodeResolve.getSDSimplexAndGroup(multiple, wanfa, dsCode, tabNumber, sign);
					//算得的注数
					zhushu = SDBetcodeUtil.getSDSimplexZhushu(dsCode, tabNumber);
					//算得金额
					totalMoney = SDBetcodeUtil.getSDSimplexMoney(dsCode, multiple, tabNumber);
				
				/**
				 * 直选和值、组三和值、组六和值
				 */
				}else if(Constant.SD_ZXHZ.equals(wanfa)
						||Constant.SD_ZSHZ.equals(wanfa)
						||Constant.SD_ZLHZ.equals(wanfa)){
					
					//去掉玩法得注码
					String fscode = code.substring(2).replace(tabNumber, "");
//					logger.info("玩法:" + wanfa +";和值:"+fscode);
					//调用算注码的方法算得注码
					zhuma = SDBetcodeResolve.getSDHeZhi(multiple, wanfa, fscode);
					//算得的注数
					zhushu = SDBetcodeUtil.getSDHezhiZhushu(fscode, wanfa);
					//算得金额
					totalMoney = SDBetcodeUtil.getSDHezhiMoney(fscode, multiple, wanfa);
				
				/**
				 *  位选投注（3D直选复式）
				 */
				}else if(Constant.SD_WXTZ.equals(wanfa)){
					//去掉玩法得注码
					String fscode = code.substring(2).replace(tabNumber, "");
//					logger.info("玩法:" + wanfa +";直选复式:"+fscode);
					//调用算注码的方法算得注码
					zhuma = SDBetcodeResolve.getSDDirectDouble(multiple, fscode, sign, tab);
					//算得的注数
					zhushu = SDBetcodeUtil.getSDDirectDoubleZhushu(fscode, tab, sign);
					//算得金额
					totalMoney = SDBetcodeUtil.getSDDirectDoubleMoney(fscode, multiple, tab, sign);
				
				/**
				 * 单选全复式、组三复式、组六复式、单选单复式
				 */
				}else if(Constant.SD_Z3FS.equals(wanfa)
						|| Constant.SD_Z6FS.equals(wanfa)
						|| Constant.SD_DXDFS.equals(wanfa)){
					//去掉玩法得注码
					String fscode = code.substring(2).replace(tabNumber, "");
//					logger.info("玩法:" + wanfa +";复式:"+fscode);
					
					if(Constant.SD_DXDFS.equals(wanfa)){
						zhuma = SDBetcodeResolve.getSDDirectAndPackageNo(multiple, fscode, sign);
					}else{
						//调用算注码的方法算得注码
						zhuma = SDBetcodeResolve.getSDSimplexAndGroupDouble(multiple, wanfa, fscode, sign);
					}
					//算得的注数
					zhushu = SDBetcodeUtil.getSDDirectAndGroupZhushu(fscode, sign, wanfa);
					//算得金额
					totalMoney = SDBetcodeUtil.getSDDirectAndGroupMoney(fscode, multiple, sign, wanfa);
					
				
				/**
				 * 胆拖复式(单选单胆拖)
				 */
				}else if(Constant.SD_DTFS.equals(wanfa)){
					
					//去掉玩法得注码
					String fscode = code.substring(2).replace(tabNumber, "");
//					logger.info("玩法:" + wanfa +";胆拖:"+fscode);
					
					//调用算注码的方法算得注码
					zhuma = SDBetcodeResolve.getSDDantuo(multiple, fscode, redTab, sign);
					//算得的注数
					zhushu = SDBetcodeUtil.getSDDantuoZhushu(fscode, redTab, sign);
					//算得金额
					totalMoney = SDBetcodeUtil.getSDDantuoMoney(fscode, multiple, redTab, sign);
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
			logger.error("解析存入数组对象SDVector:"+vector+"福彩3D注码解析算金额、得注数异常Exception:"+e.toString());
		}
		return list;
	}

}
