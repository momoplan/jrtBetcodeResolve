package com.jrt.betcodeResolve.resolve;

import com.jrt.betcodeResolve.util.Constant;

/**
 * 十一运夺金的注码解析的类
 * 
 * 
 */
public class SYYDJBetcodeResolve {

	/**
	 * 
	 * 任选1-8 或选前二和前三组选注码解析
	 * 
	 * @param betcode
	 *            注码 
	 * @param sign
	 *            传过来注码之间的分隔符 示例中为","
	 * @param tcTabNumber
	 *            多注之间的分隔符 示例中为";"
	 * @return 传给大赢家的注码
	 * 
	 */
	public static String getSYYDJRXBetcode(String betcode, String tcTabNumber,
			String sign) {
		String betcode_new = "";
		// 根据单式多条之间的分隔符tcTabNumber分隔单式注码
		String betcodes[] = betcode.split("\\" + tcTabNumber);
		// 循环传入的多注注码
		for (int j = 0; j < betcodes.length; j++) {
			String wanfa = betcodes[j].substring(0, 3) + "@";// 获取传入注码的玩法
			
			// 根据注码之间的分隔符sign分隔注码拼接返回
			String codes[] = betcodes[j].substring(4, betcodes[j].length()).split("\\" + sign);
			if(wanfa.equals("101@")||wanfa.equals("144@")||wanfa.equals("108@")||wanfa.equals("102@")||wanfa.equals("164@")||wanfa.equals("109@")||
					wanfa.equals("103@")||wanfa.equals("104@")||wanfa.equals("105@")||wanfa.equals("106@")||wanfa.equals("107@")){
				betcode_new += (wanfa+"*");// 拼接玩法
			}else{
				betcode_new += wanfa;// 拼接玩法
			}
			for (int i = 0; i < codes.length; i++) {
				if (Integer.parseInt(codes[i]) < 10) {
					codes[i] = "0" + codes[i];
				}
				betcode_new += codes[i];
			}
			betcode_new += Constant.TABNUMBER;
		}
		return betcode_new;
	}
	
	/**
	 * 
	 * 选前二或前三直选注码解析
	 * 
	 * @param betcode
	 *            注码 
	 * @param sign
	 *            传过来注码之间的分隔符 示例中为","
	 * @param tcTabNumber
	 *            多注之间的分隔符 示例中为";"
	 * @param qhTab
	 * 			   各位之间的分隔符示例中为"-"
	 * @return 传给大赢家的注码 
	 * 			
	 * 
	 */
	public static String getSYYDJQZBetcode(String betcode,String tcTabNumber,
			String sign,String qhTab){
		String betcode_new="";
		
		// 根据单式多条之间的分隔符tcTabNumber分隔单式注码
		String betcodes_new[] = betcode.split("\\" + tcTabNumber);
		for(int k=0;k<betcodes_new.length;k++){
			String wanfa = betcodes_new[k].substring(0, 3) + "@";// 获取传入注码的玩法
			if(wanfa.equals("101@")||wanfa.equals("144@")||wanfa.equals("108@")||wanfa.equals("102@")||wanfa.equals("164@")||wanfa.equals("109@")||
					wanfa.equals("103@")||wanfa.equals("104@")||wanfa.equals("105@")||wanfa.equals("106@")||wanfa.equals("107@")){
				betcode_new += (wanfa+"*");// 拼接玩法
			}else{
				betcode_new += wanfa;// 拼接玩法
			}
			
			//根据每位之间的分隔符qhTab分隔各位
			String betcodes[] = betcodes_new[k].substring(4, betcodes_new[k].length()).split("\\"+qhTab);
			for(int j=0;j<betcodes.length;j++){	
				String codes[] = betcodes[j].split("\\" + sign);
				if(wanfa.equals("162@")||wanfa.equals("142@")){
					for (int i = 0; i < codes.length; i++) {
						if (Integer.parseInt(codes[i]) < 10) {
							codes[i] = "0" + codes[i];
						}
						betcode_new += codes[i];
					}
					betcode_new=betcode_new.substring(0, betcode_new.length()) + "*";
				}else{
					for (int i = 0; i < codes.length; i++) {
						if (Integer.parseInt(codes[i]) < 10) {
							codes[i] = "0" + codes[i];
						}
						betcode_new += codes[i];
					}
				}
			}	
			if(wanfa.equals("162@")||wanfa.equals("142@")){
				betcode_new = betcode_new.substring(0, betcode_new.length()-1) + Constant.TABNUMBER;
			}else{
				betcode_new = betcode_new.substring(0, betcode_new.length()) + Constant.TABNUMBER;
			}
			
			
		}
		return betcode_new;
	}
	/**
	 * 
	 * 任选2-8 胆拖或选前二和前三组选胆拖注码解析
	 * 
	 * @param betcode
	 *            注码 
	 * @param sign
	 *            传过来注码之间的分隔符 示例中为","
	 * @param tcTabNumber
	 *            多注之间的分隔符 示例中为";"
	 * @param dtTab
	 * 			  胆码和拖码之间的分隔符示例中为"$"
	 * @return 传给大赢家的注码 

	 * 
	 */
	public static String getSYYDJRXDTBetcode(String betcode,String tcTabNumber,String sign,String dtTab){
		String betcode_new = "";
		// 根据单式多条之间的分隔符tcTabNumber分隔单式注码
		String betcodes_new[] = betcode.split("\\" + tcTabNumber);
		for(int k=0;k<betcodes_new.length;k++){
			String wanfa = betcodes_new[k].substring(0, 3) + "@";// 获取传入注码的玩法
			if(wanfa.equals("101@")||wanfa.equals("144@")||wanfa.equals("108@")||wanfa.equals("102@")||wanfa.equals("164@")||wanfa.equals("109@")||
					wanfa.equals("103@")||wanfa.equals("104@")||wanfa.equals("105@")||wanfa.equals("106@")||wanfa.equals("107@")){
				betcode_new += (wanfa+"*");// 拼接玩法
			}else{
				betcode_new += wanfa;// 拼接玩法
			}
			
			//根据胆码和拖码之间的分隔符dtTab分隔各位
			String betcodes[] = betcodes_new[k].substring(4, betcodes_new[k].length()).split("\\"+dtTab);
			for(int j=0;j<betcodes.length;j++){	
				String codes[] = betcodes[j].split("\\" + sign);
				for (int i = 0; i < codes.length; i++) {
					if (Integer.parseInt(codes[i]) < 10) {
						codes[i] = "0" + codes[i];
					}
					betcode_new += codes[i];
				}
				//拼接胆码和拖码
				betcode_new=betcode_new.substring(0, betcode_new.length())+Constant.REDTAB;
			}	
			// 去除最后一个空格
			betcode_new = betcode_new.substring(0, betcode_new.length()-1);
			betcode_new += Constant.TABNUMBER;
		}
		return betcode_new;
	}

}
