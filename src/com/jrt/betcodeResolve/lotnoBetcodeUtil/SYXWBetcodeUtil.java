package com.jrt.betcodeResolve.lotnoBetcodeUtil;

import com.jrt.betcodeResolve.resolve.SYXWBetcodeResolve;
import com.jrt.betcodeResolve.util.BetcodeResolveUtil;
import com.jrt.betcodeResolve.util.Constant;

/**
 * 江西11选5的算注数和金额
 * 
 * @author 徐丽
 * 
 */
public class SYXWBetcodeUtil {

	/**
	 * 任选1-8算注数的方法
	 * 
	 * @param betcode
	 *            注码示例:R11 R11,2,3,4,5,6// 任选一
	 *            R21,2 R21,2,3 // 任选二
	 *            R31,2,3 R31,2,3,4// 任选三
	 *            R41,2,3,4 R41,2,3,4,5,6,7 // 任选四
	 *            R51,2,3,4,5 R51,2,3,4,5,6,9,10// 任选五
	 *            R61,2,3,4,5,6 R61,2,3,4,5,6,7,8// 任选六
	 *            R71,2,3,4,5,6,7 R71,2,3,4,5,6,7,8 // 任选七
	 *            R81,2,3,4,5,6,7,8// 任选八
	 * @param sign
	 *            注码之间的分隔符 示例中为","
	 * @return 返回算得的注数
	 * 			示例中的注数分别为：1 6 1 3 1 4 1 35 1 56 1 28 1 8 1
	 */
	public static int getSYXWRXZhushu(String betcode, String sign) {
		int zhushu = 0;
		String wanfa = betcode.substring(1, 2);// 获取玩法
		// 根据注码之间的分隔符sign分隔分隔注码
		String codes[] = betcode.substring(2, betcode.length()).split(
				"\\" + sign);
		for (int i = 0; i < codes.length; i++) {
			// 任选一
			if (wanfa.equals("1")) {
				zhushu = codes.length;
				// 任选二到八
			} else {
				zhushu = (int) BetcodeResolveUtil.zuhe(Integer.parseInt(wanfa),
						codes.length);
			}

		}
		return zhushu;
	}
	
	/**
	 * 任选1-8算金额的方法
	 * 
	 * @param betcode
	 *            注码示例:R11 R11,2,3,4,5,6 R21,2 R21,2,3 R31,2,3 R31,2,3,4
	 *            R41,2,3,4 R41,2,3,4,5,6,7 R51,2,3,4,5 R51,2,3,4,5,6,9,10
	 *            R61,2,3,4,5,6 R61,2,3,4,5,6,7,8
	 *            R71,2,3,4,5,6,7 R71,2,3,4,5,6,7,8 R81,2,3,4,5,6,7,8
	 * @param sign
	 *            注码之间的分隔符 示例中为","
	 * @param  multiple 
	 * 				倍数 示例为1倍
	 * @return 返回算得的金额
	 * 			示例中金额分别为：2 12 2 6 2 8 2 70 2 112 2 56 2 16 2
	 */
	public static int getSYXWRXMoney(String betcode,String sign,int multiple){
		// 调用根据注码算注数的方法得到注数
		int zhushu = getSYXWRXZhushu(betcode, sign);
		// 算金额返回总金额
		return zhushu * multiple * Constant.LOTTERY_PRICE;
	}

	/**
	 * 前二和前三直选算注数的方法
	 * 
	 * @param betcode
	 *            注码 示例：Q22-8; 前二直选单式
	 *            Q21,2,3,4,5-8,9; 前二直选复式
	 *            Q36-9-10; 前三直选单式
	 *            Q31,4,6-5,8,9-7,10,11; 前三直选复式
	 * @param sign 注码之间的分隔符 示例中为",";
	 * @param qhTab 各个注码之间的分隔符 示例中为"-"
	 * @return
	 * 		算得的注数 示例分别为 1 10 1 27
	 */
	public static int getSYXWQXZhushu(String betcode, String sign,String qhTab) {
		
		int zhushu = 0;
		int chongfu = 0;
		//根据个位和十位之间的分隔符qhTab
		String betcodes[] = betcode.substring(2, betcode.length()).split("\\"+qhTab);
		String sw[] = betcodes[0].split("\\"+sign);
		String gw[] = betcodes[1].split("\\"+sign);
		
		//前二直选
		if(betcodes.length==2){
			
			//获取相同的注码并加1
			chongfu = BetcodeResolveUtil.chongfuCount(sw,gw);
			zhushu = sw.length*gw.length-chongfu;
			System.out.println("注数chongfu="+chongfu);
			
		//前三直选
		}else if(betcodes.length==3){
			int cf_gs = 0;int cf_sm=0;int cf_gm=0;int cf_gsm=0;
			String mw[] = betcodes[2].split("\\"+sign);
			
			cf_gs = BetcodeResolveUtil.chongfuCount(sw,gw);//个位与十位中的重复个数
			cf_gm = BetcodeResolveUtil.chongfuCount(gw, mw);//个位与末尾的重复个数
			cf_sm = BetcodeResolveUtil.chongfuCount(sw, mw);//十位与末尾的重复个数
			
			//获取末位、十位重复注码
			String sm = BetcodeResolveUtil.getNewCodes(mw, sw);
			System.out.println("末位和十位的注码数组"+sm);
			
			//获取个位、十位重复注码
			String gs = BetcodeResolveUtil.getNewCodes(sw, gw);;
			System.out.println("个位和十位的注码数组"+gs);
			
			String newSM[] = new String [11];
			String newGS[] = new String [11];
			//重新生成两组数组组合算得个位、十位、末位的个数
			if(!sm.equals("") && !gs.equals("")){
				newSM = sm.substring(0,sm.length()-1).split("\\,");
				newGS = gs.substring(0,gs.length()-1).split("\\,");
				cf_gsm=BetcodeResolveUtil.chongfuCount(newSM, newGS);
			}
			//System.out.println("cf_gsm="+cf_gsm);
			zhushu = (gw.length*sw.length*mw.length)-(sw.length*cf_gm)-(gw.length*cf_sm)-(mw.length*cf_gs)+(cf_gsm*2);
		}
		//System.out.println("注数zhushu="+zhushu);
//	    //获取新注码
//		String betcodeNew = SYXWBetcodeResolve.getSYXWQZBetcode(betcode, Constant.TC_TABNUMBER, sign, qhTab);
//		betcodeNew = betcodeNew.substring(3, betcodeNew.length()-1).replace(" ", "");
////		System.out.println("新注码为betcodeNew="+betcodeNew);
//		
//		//分隔百位、十位和个位
//		String codes[] =betcodeNew.split( "\\" + sign);
//		for(int i=0;i<codes.length;i++){
//			zhushu*=(int)BetcodeResolveUtil.getStringArrayFromString(codes[i]).size();
//		}
		return zhushu;
	}
	
	
	/**
	 * 前二和前三直选算金额的方法
	 * 
	 * @param betcode
	 *            注码 示例：Q22-8; 前二直选单式
	 *            Q21,2,3,4,5-8,9; 前二直选复式
	 *            Q36-9-10; 前三直选单式
	 *            Q31,4,6-5,8,9-7,10,11; 前三直选复式
	 * @param sign 注码之间的分隔符 示例中为",";
	 * @param qhTab 各个注码之间的分隔符 示例中为"-" 
	 * @param  multiple 
	 * 				倍数 示例为1倍
	 * @return
	 * 		算得的金额 示例分别为 2 20 2 54
	 */
	public static int getSYXWQXMoney(String betcode, String sign,String qhTab,int multiple) {
		// 调用根据注码算注数的方法得到注数
		int zhushu = getSYXWQXZhushu(betcode, sign,qhTab);
		// 算金额返回总金额
		return zhushu * multiple * Constant.LOTTERY_PRICE;
	}

	/**
	 * 前二和前三组选算注数的方法
	 * 
	 * @param betcode
	 *            注码 示例：Z24,10; 前二组选单式
	 *            Z21,2,3,4,5,6,7,8; 前二组选复式
	 *            Z31,7,10; 前三组选单式
	 *            Z31,2,3,4,5,6,7,8,9; 前三组选复式
	 * @param sign 注码之间的分隔符 示例中为",";
	 * @return
	 * 		算得的注数 示例分别为 1 10 1 27
	 */
	public static int getSYXWQZZhushu(String betcode,String sign){
		int zhushu = 0;
		//获取组选新注码
		String betcodeNew = SYXWBetcodeResolve.getSYXWRXBetcode(betcode, Constant.TC_TABNUMBER, sign);
		betcodeNew = betcodeNew.substring(3, betcodeNew.length()).replace(" ", "");
		//System.out.println("新注码为betcodeNew="+betcodeNew);
				
		int count = BetcodeResolveUtil.getStringArrayFromString(betcodeNew).size();
		
		//获取玩法判断是前二组选还是前三组选
		String wanfa = betcode.substring(0,2);
		if(wanfa.equals(Constant.SYXW_ZX2)){
			zhushu = (int)BetcodeResolveUtil.nchoosek(count, 2);
		}else if(wanfa.equals(Constant.SYXW_ZX3)){
			zhushu = (int)BetcodeResolveUtil.nchoosek(count, 3);
		}
		//System.out.println("zhushu="+zhushu);
		return zhushu;
	}
	
	/**
	 * 前二和前三组选算金额的方法
	 * 
	 * @param betcode
	 *            注码 示例：Z24,10; 前二组选单式
	 *            Z21,2,3,4,5,6,7,8; 前二组选复式
	 *            Z31,7,10; 前三组选单式
	 *            Z31,2,3,4,5,6,7,8,9; 前三组选复式
	 * @param sign 注码之间的分隔符 示例中为",";
	 * @param multiple 
	 * 				倍数 示例为1倍
	 * @return
	 * 		算得的金额 示例分别为 2 20 2 54
	 */
	public static int getSYXWQZMoney(String betcode, String sign,int multiple) {
		// 调用根据注码算注数的方法得到注数
		int zhushu = getSYXWQZZhushu(betcode, sign);
		// 算金额返回总金额
		return zhushu * multiple * Constant.LOTTERY_PRICE;
	}
	
	/**
	 * 任选2-8胆拖和前二或前三胆拖算注数的方法
	 * 
	 * @param betcode
	 *            注码示例:R21$5,8,10;
	 *            R31$5,8,10;
	 *            R41,2$5,6,7,8,10;
	 *            R51,2$5,6,7,8,10;
	 *            R61,2$5,6,7,8,10;
	 *            R71,2$5,6,7,8,10,11;
	 *            R81,2,3,5,6,7,8$10,11;
	 *            Z21$5,8,10;
	 *            Z31$5,6,7,8,10;
	 * @param sign
	 *            注码之间的分隔符 示例中为","
	 * @param dtTab
	 * 			胆码和拖码之间的分隔符 示例中为"$"
	 * @return 返回算得的注数
	 * 			示例中的注数分别为：3 3 10 10 5 6 2 3 3 10
	 */
	public static int getSYXW_RX_DTZhushu(String betcode,String sign,String dtTab){
		int zhushu = 0;
		
		//获取胆拖新注码
		String betcodeNew = SYXWBetcodeResolve.getSYXWRXDTBetcode(betcode, Constant.TC_TABNUMBER, sign, dtTab);
		betcodeNew = betcodeNew.substring(3, betcodeNew.length()).replace(" ", "");
		//System.out.println("新注码为betcodeNew="+betcodeNew);
		
		//根据胆码和拖码之间的分隔符dtTab分隔胆码和拖码
		String codes[] = betcodeNew.split("\\"+Constant.DT_TAB);
		int dmCount = BetcodeResolveUtil.getStringArrayFromString(codes[0]).size();
		int tmCount = BetcodeResolveUtil.getStringArrayFromString(codes[1]).size();
		//获取任选胆拖的玩法
		String wanfa = betcode.substring(1,2);
		
		//根据玩法算得胆拖的注数
		zhushu = (int)BetcodeResolveUtil.nchoosek(tmCount, Integer.parseInt(wanfa)-dmCount);
		return zhushu;
	}
	/**
	 * 任选2-8胆拖算金额的方法
	 * 
	 * @param betcode
	 *            注码示例:R21$5,8,10;
	 *            R31$5,8,10;
	 *            R41,2$5,6,7,8,10;
	 *            R51,2$5,6,7,8,10;
	 *            R61,2$5,6,7,8,10;
	 *            R71,2$5,6,7,8,10,11;
	 *            R81,2,3,5,6,7,8$10,11;
	 *            Z21$5,8,10;
	 *            Z31$5,6,7,8,10;
	 * @param sign
	 *            注码之间的分隔符 示例中为","
	 * @param dtTab
	 * 			胆码和拖码之间的分隔符 示例中为"$"
	 * @param multiple 
	 * 				倍数 示例为1倍
	 * @return 返回算得的金额
	 * 			示例中的金额分别为：6 6 20 20 10 12 4 6 20
	 */
	public static int getSYXW_RX_DTMoney(String betcode, String sign,String dtTab,int multiple) {
		// 调用根据注码算注数的方法得到注数
		int zhushu = getSYXW_RX_DTZhushu(betcode, sign, dtTab);
		// 算金额返回总金额
		return zhushu * multiple * Constant.LOTTERY_PRICE;
	}
}
