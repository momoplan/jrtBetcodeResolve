package com.jrt.betcodeResolve.lotnoBetcodeUtil;

import com.jrt.betcodeResolve.resolve.SYYDJBetcodeResolve;
import com.jrt.betcodeResolve.util.BetcodeResolveUtil;
import com.jrt.betcodeResolve.util.Constant;

/**
 * 十一运夺金的算注数和金额
 * 
 * 
 */
public class SYYDJBetcodeUtil {

	/**
	 * 任选1-8算注数的方法
	 * 
	 * @param betcode
	 *            注码
	 * @param sign
	 *            注码之间的分隔符 示例中为","
	 * @return 返回算得的注数
	 */
	public static int getSYYDJRXZhushu(String betcode, String sign) {
		int zhushu = 0;
		String wanfa = betcode.substring(0, 3);// 获取玩法
		// 根据注码之间的分隔符sign分隔分隔注码
		String codes[] = betcode.substring(4, betcode.length()).split(
				"\\" + sign);
		for (int i = 0; i < codes.length; i++) {
			// 任选一
			if (wanfa.equals("101")) {
				zhushu = codes.length;
				// 任选二到八
			} else {
				int k=2;
				if(wanfa.equals("112")||wanfa.equals("103")||wanfa.equals("122")||
					wanfa.equals("161")||wanfa.equals("164")||wanfa.equals("151")||
					wanfa.equals("109")||wanfa.equals("153")){
					k=3;
				}if(wanfa.equals("113")||wanfa.equals("104")||wanfa.equals("123")){
					k=4;
				}if(wanfa.equals("114")||wanfa.equals("105")||wanfa.equals("124")){
					k=5;
				}if(wanfa.equals("115")||wanfa.equals("106")||wanfa.equals("125")){
					k=6;
				}if(wanfa.equals("116")||wanfa.equals("107")||wanfa.equals("126")){
					k=7;
				}if(wanfa.equals("117")){
					k=8;
				}
				zhushu = (int) BetcodeResolveUtil.zuhe(k,
						codes.length);
			}

		}
		return zhushu;
	}
	
	/**
	 * 任选1-8算金额的方法
	 * 
	 * @param betcode
	 *            注码
	 * @param sign
	 *            注码之间的分隔符 示例中为","
	 * @param  multiple 
	 * 				倍数 示例为1倍
	 * @return 返回算得的金额
	 */
	public static int getSYYDJRXMoney(String betcode,String sign,int multiple){
		// 调用根据注码算注数的方法得到注数
		int zhushu = getSYYDJRXZhushu(betcode, sign);
		// 算金额返回总金额
		return zhushu * multiple * Constant.LOTTERY_PRICE;
	}

	/**
	 * 前二和前三直选算注数的方法
	 * 
	 * @param betcode
	 *            注码 
	 * @param sign 注码之间的分隔符 示例中为",";
	 * @param qhTab 各个注码之间的分隔符 示例中为"-"
	 * @return
	 */
	public static int getSYYDJQXZhushu(String betcode, String sign,String qhTab) {
		
		int zhushu = 0;
		int chongfu = 0;
		//根据个位和十位之间的分隔符qhTab
		String betcodes[] = betcode.substring(4, betcode.length()).split("\\"+qhTab);
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
			zhushu = (gw.length*sw.length*mw.length)-(sw.length*cf_gm)-(gw.length*cf_sm)-(mw.length*cf_gs)+(cf_gsm*2);
		}
		return zhushu;
	}
	
	
	/**
	 * 前二和前三直选算金额的方法
	 * 
	 * @param betcode
	 *            注码 
	 * @param sign 注码之间的分隔符 示例中为",";
	 * @param qhTab 各个注码之间的分隔符 示例中为"-" 
	 * @param  multiple 
	 * 				倍数 示例为1倍
	 * @return
	 */
	public static int getSYYDJQXMoney(String betcode, String sign,String qhTab,int multiple) {
		// 调用根据注码算注数的方法得到注数
		int zhushu = getSYYDJQXZhushu(betcode, sign,qhTab);
		// 算金额返回总金额
		return zhushu * multiple * Constant.LOTTERY_PRICE;
	}

	/**
	 * 前二和前三组选算注数的方法
	 * 
	 * @param betcode
	 *            注码 
	 * @param sign 注码之间的分隔符 示例中为",";
	 * @return
	 */
	public static int getSYYDJQZZhushu(String betcode,String sign){
		int zhushu = 0;
		//获取组选新注码
		String betcodeNew = SYYDJBetcodeResolve.getSYYDJRXBetcode(betcode, Constant.TC_TABNUMBER, sign);
		betcodeNew = betcodeNew.substring(4, betcodeNew.length()).replace(" ", "");
		//System.out.println("新注码为betcodeNew="+betcodeNew);
				
		int count = BetcodeResolveUtil.getStringArrayFromString(betcodeNew).size();
		
		//获取玩法判断是前二组选还是前三组选
		String wanfa = betcode.substring(0,3);
		if(wanfa.equals("133")){
			zhushu = (int)BetcodeResolveUtil.nchoosek(count, 2);
		}else if(wanfa.equals("153")){
			zhushu = (int)BetcodeResolveUtil.nchoosek(count, 3);
		}
		return zhushu;
	}
	
	/**
	 * 前二和前三组选算金额的方法
	 * 
	 * @param betcode
	 *            注码 
	 * @param sign 注码之间的分隔符 示例中为",";
	 * @param multiple 
	 * 				倍数 示例为1倍
	 * @return
	 */
	public static int getSYYDJQZMoney(String betcode, String sign,int multiple) {
		// 调用根据注码算注数的方法得到注数
		int zhushu = getSYYDJQZZhushu(betcode, sign);
		// 算金额返回总金额
		return zhushu * multiple * Constant.LOTTERY_PRICE;
	}
	
	/**
	 * 任选2-8胆拖和前二或前三胆拖算注数的方法
	 * 
	 * @param betcode
	 *            注码
	 * @param sign
	 *            注码之间的分隔符 示例中为","
	 * @param dtTab
	 * 			胆码和拖码之间的分隔符 示例中为"$"
	 * @return 返回算得的注数
	 */
	public static int getSYYDJ_RX_DTZhushu(String betcode,String sign,String dtTab){
		int zhushu = 0;
		
		//获取胆拖新注码
		String betcodeNew = SYYDJBetcodeResolve.getSYYDJRXDTBetcode(betcode, Constant.TC_TABNUMBER, sign, dtTab);
		betcodeNew = betcodeNew.substring(4, betcodeNew.length()).replace(" ", "");
		
		//根据胆码和拖码之间的分隔符dtTab分隔胆码和拖码
		String codes[] = betcodeNew.split("\\"+Constant.REDTAB);
		int dmCount = BetcodeResolveUtil.getStringArrayFromString(codes[0]).size();
		int tmCount = BetcodeResolveUtil.getStringArrayFromString(codes[1]).size();
		//获取任选胆拖的玩法
		String wanfa = betcode.substring(0,3);
		
		//根据玩法算得胆拖的注数
		int k=2;
		if(wanfa.equals("112")||wanfa.equals("103")||wanfa.equals("122")
				||wanfa.equals("161")||wanfa.equals("164")||wanfa.equals("151")
				||wanfa.equals("109")||wanfa.equals("153")){
			k=3;
		}if(wanfa.equals("113")||wanfa.equals("104")||wanfa.equals("123")){
			k=4;
		}if(wanfa.equals("114")||wanfa.equals("105")||wanfa.equals("124")){
			k=5;
		}if(wanfa.equals("115")||wanfa.equals("106")||wanfa.equals("125")){
			k=6;
		}if(wanfa.equals("116")||wanfa.equals("107")||wanfa.equals("126")){
			k=7;
		}if(wanfa.equals("117")){
			k=8;
		}
		
		zhushu = (int)BetcodeResolveUtil.nchoosek(tmCount, k-dmCount);
		return zhushu;
	}
	/**
	 * 任选2-8胆拖算金额的方法
	 * 
	 * @param betcode
	 *            注码
	 * @param sign
	 *            注码之间的分隔符 示例中为","
	 * @param dtTab
	 * 			胆码和拖码之间的分隔符 示例中为"$"
	 * @param multiple 
	 * 				倍数 示例为1倍
	 * @return 返回算得的金额
	 */
	public static int getSYYDJ_RX_DTMoney(String betcode, String sign,String dtTab,int multiple) {
		// 调用根据注码算注数的方法得到注数
		int zhushu = getSYYDJ_RX_DTZhushu(betcode, sign, dtTab);
		// 算金额返回总金额
		return zhushu * multiple * Constant.LOTTERY_PRICE;
	}
}
