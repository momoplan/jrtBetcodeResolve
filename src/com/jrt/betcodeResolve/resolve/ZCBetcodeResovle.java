package com.jrt.betcodeResolve.resolve;

import java.util.ArrayList;
import java.util.List;

import com.jrt.betcodeResolve.util.Constant;

/**
 * 
 *		足彩胜负彩注码解析
 * @author
 *		徐丽
 *
 */
public class ZCBetcodeResovle {
	/**
	 * 
	 *      足彩胜负彩14场 单式和复式、半全场、进球彩投注  3-胜、1-负、0-平
	 * @param 
	 * 		betcode 注码     以胜负彩14场为例
	 *      单式注码(3,1,0,1,3,1,1,3,1,3,1,1,1,3)
	 *      复式注码(31,10,0,1,3,1,1,3,1,3,1,1,1,3)
	 * @param 
	 * 		sign 注码之间的分隔符   示例中为","
	 * @return   
	 * 		拼接完的注码 
	 * 		示例:单式注码(3,1,0,1,3,1,1,3,1,3,1,1,1,3)
	 *          复式注码(31,10,0,1,3,1,1,3,1,3,1,1,1,3)
	 * 
	 */
	public static String getZCBetcode(String betcode,String sign){
		//根据分隔符tab将注码分隔
		String codes[] = betcode.split("\\"+sign);
		
		String code = "";
		//循环注码将得到的注码拼接成大赢家所需的注码(去掉最后一个字符)
		for(int i=0;i<codes.length;i++){
			code+=codes[i]+(i==codes.length-1? "": Constant.SIGN);
		}

		//将得到的注码返回
		return code;
	}
	
	/**
	 * 
	 *      足彩任九场单式与复式(14场中任选九场)
	 * @param 
	 * 		betcode 注码
	 *      例：单式注码(3,#,0,1,#,1,#,3,#,3,#,1,1,3)
	 *          复式注码(31,10,#,1,#,1,#,3,#,3,1,#,1,3)
	 * @param 
	 * 		sign 注码之间的分隔符示例中为","
	 * @param 
	 * 		streak 场次代替符 示例中为"#"
	 * @return    
	 * 		拼接完的注码 
	 * 		示例：单式注码(3,#,0,1,#,1,#,3,#,3,#,1,1,3)
	 *          复式注码(31,10,#,1,#,1,#,3,#,3,1,#,1,3)
	 * 
	 */
	public static String getZCBetcode(String betcode,String sign,String streak){
		
		
		
		//根据注码之间的分隔符tab分隔注码
		String codes[] = betcode.split("\\"+sign);
		String code = "";

//		int totalSelect = 0;
//		for(String s:codes) {
//			if(!s.equals(Constant.ZC_STREAK)) {
//				totalSelect = totalSelect + 1;
//			}
//		}
		
//		if(totalSelect >= 10 && !betcode.contains(Constant.DT_TAB)) {
//			List<String> zhumaList = transform(betcode);
//			StringBuffer sb = new StringBuffer();
//			for(String zhuma:zhumaList) {
//				sb.append(zhuma).append(";");
//			}
//			
//			code = sb.toString();
//			return code.substring(0, code.length()-1);
//		}else {
			//将注码拼接(去掉最后一个字符)
			for(int i=0;i<codes.length;i++){
				code+=codes[i].replace(streak, Constant.ZC_STREAK)+(i==codes.length-1? "": Constant.SIGN);
			}
			return code;
//		}
		
	}
	
	
	/**
	 * 递归组合算法，将组合后的注码放入list中
	 * @param a 原始数据
	 * @param n 原始数据个数
	 * @param m 选出数据个数
	 * @param b 选出数据放入的数组
	 * @param M 选出数据个数常量
	 * @param list 存放最终组合注码的list
	 */
	private static void combine(int a[], int n, int m, int b[], final int M,List<int[]> list) {
		for (int i = n; i >= m; i--) // 注意这里的循环范围
		{
			b[m - 1] = i - 1;
			if (m > 1)
				combine(a, i - 1, m - 1, b, M,list);
			else // m == 1, 输出一个组合
			{
				int[] result = new int[M];
				for (int j = M - 1; j >= 0; j--) {
					result[j] = a[b[j]];
				}
				list.add(result);

			}
		}
	}
	
	/**
	 * 字符数组转换成字符串，同时添加玩法
	 * @param betcode
	 * @return
	 */
	private static String arrayToString(String[] betcode) {
		String betcodeString = "";
		for(String s:betcode) {
			betcodeString = betcodeString + s + ",";
		}
		
		betcodeString = betcodeString.substring(0, betcodeString.length()-1);
		
		if(betcodeString.length()==27) {
			betcodeString = Constant.ZC_RJC + Constant.ZC_DS + betcodeString;
		}else {
			betcodeString = Constant.ZC_RJC + Constant.ZC_FS + betcodeString;
		}
		
		
		return betcodeString;
	}
	
	/**
	 * 重置字符串数组
	 * @param init
	 */
	private static void resetArray(String[] init) {
		for(int i = 0;i < init.length;i++) {
			init[i] = "#";
		}
	}
	
	
	private static List<String> transform(String betcode) {
		int total=0;
		String[] betcodes = betcode.split(",");
		for(String bet:betcodes) {
			if(!bet.equals("#")) {
				total = total + 1;
			}
		}
		
		int[] a = new int[total];//原始数据
		int mark = 0;
		for(int i = 0;i < betcodes.length;i++) {
			if(!betcodes[i].equals("#")) {
				a[mark] = i;
				mark = mark + 1;
			}
		}
		
		
		int[] b = new int[9];//存储数据 
		List<int[]> list = new ArrayList<int[]>();
		combine(a,total,9,b,9,list);
		

		List<String> zhumaList = new ArrayList<String>();
		String[] initArray = {"#","#","#","#","#","#","#","#","#","#","#","#","#","#"};
		for(int[] positions:list) {
			for(int p:positions) {
				initArray[p] = betcodes[p];
			}
			zhumaList.add(arrayToString(initArray));
			resetArray(initArray);
		}
		return zhumaList;
		
	}
	
}
