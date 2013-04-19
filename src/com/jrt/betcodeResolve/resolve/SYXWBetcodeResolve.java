package com.jrt.betcodeResolve.resolve;

import com.jrt.betcodeResolve.util.Constant;

/**
 * 江西11选5的注码解析的类
 * 
 * @author 徐丽
 * 
 */
public class SYXWBetcodeResolve {

	/**
	 * 
	 * 任选1-8 或选前二和前三组选注码解析
	 * 
	 * @param betcode
	 *            注码 示例:注码: R11;R11,2,3,4,5,6; R21,2;R21,2,3,4; R31,2,3;R31,2,3;
	 *            R41,2,3,4;R41,2,3,4,5,6,7; R51,2,3,4,5;R51,2,3,4,5,6,9,10;
	 *            R61,2,3,4,5,6;R61,2,3,4,5,6,7,8;
	 *            R71,2,3,4,5,6,7;R71,2,3,4,5,6,7,8;
	 *            R81,2,3,4,5,6,7,8;R81,2,3,4,5,6,7,8;
	 *            Z24,10;Z21,2,3,4,5,6,7,8;Z31,7,10;Z31,2,3,4,5,6,7,8,9;
	 * @param sign
	 *            传过来注码之间的分隔符 示例中为","
	 * @param tcTabNumber
	 *            多注之间的分隔符 示例中为";"
	 * @return 传给大赢家的注码 R1|01;R1|01 02 03 04 05 06; R2|01 02;R2|01 02 03 04;
	 *         R3|01 02 03;R3|01 02 03; R4|01 02 03 04;R4|01 02 03 04 05 06 07;
	 *         R5|01 02 03 04 05;R5|01 02 03 04 05 06 09 10; 
	 *         R6|01 02 03 04 05 06;R6|01 02 03 04 05 06 07 08;
	 *         R7|01 02 03 04 05 06 07;R7|01 02 03 04 05 06 07 08; 
	 *         R8|01 02 03 04 05 06 07 08;R8|01 02 03 04 05 06 07 08;
	 *         Z2|04 10;Z2|01 02 03 04 05 06 07 08;
	 *         Z3|01 07 10;Z3|01 02 03 04 05 06 07 08 09;
	 * 
	 */
	public static String getSYXWRXBetcode(String betcode, String tcTabNumber,
			String sign) {
		String betcode_new = "";
		// 根据单式多条之间的分隔符tcTabNumber分隔单式注码
		String betcodes[] = betcode.split("\\" + tcTabNumber);
		// 循环传入的多注注码
		for (int j = 0; j < betcodes.length; j++) {
			String wanfa = betcodes[j].substring(0, 2) + Constant.PLS_FGF;// 获取传入注码的玩法
			
			// 根据注码之间的分隔符sign分隔注码拼接返回
			String codes[] = betcodes[j].substring(2, betcodes[j].length()).split("\\" + sign);
			betcode_new += wanfa;// 拼接玩法
			for (int i = 0; i < codes.length; i++) {
				if (Integer.parseInt(codes[i]) < 10) {
					codes[i] = "0" + codes[i];
				}
				betcode_new += codes[i] + " ";
			}
			betcode_new = betcode_new.substring(0, betcode_new.length() - 1);// 去除最后一个空格
			betcode_new += Constant.TC_TABNUMBER;
		}
		return betcode_new;
	}
	
	/**
	 * 
	 * 选前二或前三直选注码解析
	 * 
	 * @param betcode
	 *            注码 示例:Q22-8;Q21,2,3,4,5-8,9;
	 *            Q36-9-10;Q31,4,6-5,8,9-7,10,11;
	 * @param sign
	 *            传过来注码之间的分隔符 示例中为","
	 * @param tcTabNumber
	 *            多注之间的分隔符 示例中为";"
	 * @param qhTab
	 * 			   各位之间的分隔符示例中为"-"
	 * @return 传给大赢家的注码 
	 * 			Q2|02,08;Q2|01 02 03 04 05,08 09;
	 * 			Q3|06,09,10;Q3|01 04 06,05 08 09,07 10 11;
	 * 
	 */
	public static String getSYXWQZBetcode(String betcode,String tcTabNumber,
			String sign,String qhTab){
		String betcode_new="";
		
		// 根据单式多条之间的分隔符tcTabNumber分隔单式注码
		String betcodes_new[] = betcode.split("\\" + tcTabNumber);
		for(int k=0;k<betcodes_new.length;k++){
			String wanfa = betcodes_new[k].substring(0, 2) + Constant.PLS_FGF;// 获取传入注码的玩法
			betcode_new += wanfa;// 拼接玩法
			
			//根据每位之间的分隔符qhTab分隔各位
			String betcodes[] = betcodes_new[k].substring(2, betcodes_new[k].length()).split("\\"+qhTab);
			for(int j=0;j<betcodes.length;j++){	
				String codes[] = betcodes[j].split("\\" + sign);
				for (int i = 0; i < codes.length; i++) {
					if (Integer.parseInt(codes[i]) < 10) {
						codes[i] = "0" + codes[i];
					}
					betcode_new += codes[i] + " ";
				}
				//将qhTab替换为大赢家所需符号“,”
				betcode_new=betcode_new.substring(0, betcode_new.length() - 1)+Constant.SIGN;
			}	
			// 去除最后一个空格
			betcode_new = betcode_new.substring(0, betcode_new.length() - 1);
			betcode_new += Constant.TC_TABNUMBER;
			
		}
		return betcode_new;
	}
	/**
	 * 
	 * 任选2-8 胆拖或选前二和前三组选胆拖注码解析
	 * 
	 * @param betcode
	 *            注码 示例:R21$5,8,10;R31$5,8,10; 
	 *            R41,2$5,6,7,8,10;R51,2$5,6,7,8,10;
	 *            R61,2$5,6,7,8,10;R71,2$5,6,7,8,10,11;
	 *            R81,2,3,5,6,7,8$10,11;
	 *            Z21$5,8,10;Z31$5,6,7,8,10;
	 * @param sign
	 *            传过来注码之间的分隔符 示例中为","
	 * @param tcTabNumber
	 *            多注之间的分隔符 示例中为";"
	 * @param dtTab
	 * 			  胆码和拖码之间的分隔符示例中为"$"
	 * @return 传给大赢家的注码 
	 * 			R2|01$05 08 10;R3|01$05 08 10;
	 * 			R4|01 02$05 06 07 08 10;
	 * 			R5|01 02$05 06 07 08 10;
	 * 			R6|01 02$05 06 07 08 10;
	 * 			R7|01 02$05 06 07 08 10 11;
	 * 			R8|01 02 03 05 06 07 08$10 11;
	 * 			Z2|01$05 08 10;Z3|01$05 06 07 08 10;
	 * 
	 */
	public static String getSYXWRXDTBetcode(String betcode,String tcTabNumber,String sign,String dtTab){
		String betcode_new = "";
		// 根据单式多条之间的分隔符tcTabNumber分隔单式注码
		String betcodes_new[] = betcode.split("\\" + tcTabNumber);
		for(int k=0;k<betcodes_new.length;k++){
			String wanfa = betcodes_new[k].substring(0, 2) + Constant.PLS_FGF;// 获取传入注码的玩法
			betcode_new += wanfa;// 拼接玩法
			
			//根据胆码和拖码之间的分隔符dtTab分隔各位
			String betcodes[] = betcodes_new[k].substring(2, betcodes_new[k].length()).split("\\"+dtTab);
			for(int j=0;j<betcodes.length;j++){	
				String codes[] = betcodes[j].split("\\" + sign);
				for (int i = 0; i < codes.length; i++) {
					if (Integer.parseInt(codes[i]) < 10) {
						codes[i] = "0" + codes[i];
					}
					betcode_new += codes[i] + " ";
				}
				//拼接胆码和拖码
				betcode_new=betcode_new.substring(0, betcode_new.length() - 1)+Constant.DT_TAB;
			}	
			// 去除最后一个空格
			betcode_new = betcode_new.substring(0, betcode_new.length() - 1);
			betcode_new += Constant.TC_TABNUMBER;
		}
		return betcode_new;
	}

}
