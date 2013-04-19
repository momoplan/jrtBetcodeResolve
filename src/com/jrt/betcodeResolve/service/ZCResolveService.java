package com.jrt.betcodeResolve.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.jrt.betcodeResolve.bean.BetcodeBean;
import com.jrt.betcodeResolve.lotnoBetcodeUtil.ZCBetcodeUtil;
import com.jrt.betcodeResolve.resolve.ZCBetcodeResovle;
import com.jrt.betcodeResolve.util.BetcodeProxyResolve;
import com.jrt.betcodeResolve.util.Constant;


/**
 *      足彩 将所有注码 玩法、倍数、金额、注码存入注码实体bean中
 * 		并且存入list中返回的类
 * @author 
 * 		徐丽
 * 
 */
public class ZCResolveService {
	
	/**
	 * 
	 * @param betcode
	 * 		注码：示例:
	 * 		"1103,1,0,1,3,1,1,3,1,3,1,1,1,3;1103,1,0,1,3,1,1,3,1,3,1,1,1,3;1103,1,0,1,3,1,1,3,1,3,1,1,1,3;"
	 * 		"1103,1,0,1,3,1,1,3,1,3,1,1,1,3;1103,1,0,1,3,1,1,3,1,3,1,1,1,3;" 
	 * 		"1103,1,0,1,3,1,1,3,1,3,1,1,1,3;"//胜负彩14场单式
	 * 		"11131,10,0,1,3,1,1,3,1,3,1,1,1,3;"//胜负彩14场复式
	 * 		"1903,#,0,1,#,1,#,3,#,3,#,1,1,3;"//任九场单式
	 * 		"19131,10,#,1,#,1,#,3,#,3,1,#,1,3;"//任九场复式
	 * 		"1920,#,#,#,#,31,#,#,#,#,#,#,#,#$#,1,0,1,#,#,1,#,3,3,03,#,1,#;"//任九场胆拖
	 * 		"1603,1,0,1,3,1,1,3,1,3,1,1;"//足彩六场半全场单式
	 * 		"16130,1,03,01,3,1,1,3,1,3,1,1;"//足彩六场半全场复式
	 * 		"1803,1,0,1,3,1,1,3;"//足彩进球彩单式
	 * 		"18130,1,03,01,3,1,1,3;"//足彩进球彩复式
	 * @param multiple
	 * 		倍数 示例为1倍
	 * @param tcTabNumber
	 * 		多注之间的分隔符 示例中为";"
	 * @param sign
	 * 		场次之间的分隔符 示例中为","
	 * @param dtTab
	 * 		胆拖之间的分隔符 示例中为"$"
	 * @param streak
	 * 		场次代替符 示例中为"#"
	 * @return 实体集合
	 * 	示例为：(玩法:0;彩种:T01003;注码:3,1,0,1,3,1,1,3,1,3,1,1,1,3;3,1,0,1,3,1,1,3,1,3,1,1,1,3;3,1,0,1,3,1,1,3,1,3,1,1,1,3;3,1,0,1,3,1,1,3,1,3,1,1,1,3;3,1,0,1,3,1,1,3,1,3,1,1,1,3;注数:5注;总金额:10元=1000分)
	 * 			(玩法:0;彩种:T01003;注码:3,1,0,1,3,1,1,3,1,3,1,1,1,3;注数:1注;总金额:2元=200分)
	 * 			(玩法:0;彩种:T01005;注码:3,1,0,1,3,1,1,3;注数:1注;总金额:2元=200分)
	 * 			(玩法:0;彩种:T01006;注码:3,1,0,1,3,1,1,3,1,3,1,1;注数:1注;总金额:2元=200分)
	 * 			(玩法:1;彩种:T01003;注码:31,10,0,1,3,1,1,3,1,3,1,1,1,3;注数:4注;总金额:8元=800分)
	 * 			(玩法:0;彩种:T01004;注码:3,#,0,1,#,1,#,3,#,3,#,1,1,3;注数:1注;总金额:2元=200分)
	 * 			(玩法:1;彩种:T01004;注码:31,10,#,1,#,1,#,3,#,3,1,#,1,3;注数:4注;总金额:8元=800分)
	 * 			(玩法:2;彩种:T01004;注码:0,#,#,#,#,31,#,#,#,#,#,#,#,#$#,1,0,1,#,#,1,#,3,3,03,#,1,#;注数:30注;总金额:60元=6000分)
	 * 			(玩法:1;彩种:T01006;注码:30,1,03,01,3,1,1,3,1,3,1,1;注数:8注;总金额:16元=1600分)
	 * 			(玩法:1;彩种:T01005;注码:30,1,03,01,3,1,1,3;注数:8注;总金额:16元=1600分)
	 * 
	 */
	public static List<BetcodeBean> getZCBetcodeList(String betcode,
			int multiple, String tcTabNumber, String sign, String dtTab, String streak) {
		List<BetcodeBean> list = new ArrayList<BetcodeBean>();
		BetcodeBean betcodeBean = null;
		String zhuma = "";
		int zhushu = 0;
		int totalMoney = 0;
		
		// 得到所有足彩注码的数组对象vector
		//Vector<String> vector = BetcodeProxyResolve.getZCVector(betcode, tcTabNumber, sign, streak);
		if(betcode.substring(0, 3).equals("191")) {
			String codes[] = betcode.substring(3).split("\\"+sign);
			String code = "";
			int totalSelect = 0;
			for(String s:codes) {
				if(!s.equals(Constant.ZC_STREAK)) {
					totalSelect = totalSelect + 1;
				}
			}
			if(totalSelect >= 10) {
//				List<String> zhumaList = transform(betcode.substring(3));
//				StringBuffer sb = new StringBuffer();
//				for(String zhuma2:zhumaList) {
//					sb.append(zhuma2).append(";");
//				}
//				
//				code = sb.toString();
//				betcode = code.substring(0, code.length()-1);
				
				betcode = "193"+betcode.substring(3);
			}
			
		}
		
		

		
		
		
		
		System.out.println(betcode+"betcodeee");
		
		Vector<String> vector = BetcodeProxyResolve.getVector(betcode, tcTabNumber);
		for (int i = 0; i < vector.size(); i++) {
			betcodeBean = new BetcodeBean();
			betcodeBean.setMultiple(String.valueOf(multiple));

			String code = vector.get(i);
			String lotno = code.substring(0, 2);//获取彩种
			
			String wanfa = code.substring(2,3);//获取玩法
			betcodeBean.setGameMethod(wanfa);
			
			//去除彩种和玩法获取新注码
			String code_new ="";
			String codes[] = code.split("\\"+tcTabNumber);
			for(int j=0;j<codes.length;j++){
				code_new += codes[j].substring(3)+tcTabNumber;
			}
			code_new = code_new.substring(0, code_new.length()-1);
			System.out.println("新注码为:"+code_new);
			//单式玩法
			if(wanfa.equals(Constant.ZC_DS)){
				zhushu = ZCBetcodeUtil.getZCSimplexZhushu(code_new, tcTabNumber);
				totalMoney = ZCBetcodeUtil.getZCSimplexMoney(code_new, multiple, tcTabNumber);
			}else if(wanfa.equals(Constant.ZC_FS)){
				zhushu = ZCBetcodeUtil.getZCDuplexZhushu(code_new, sign);
				totalMoney = ZCBetcodeUtil.getZCDuplexMoney(code_new, multiple, sign);
			}else if(wanfa.equals(Constant.ZC_DT)){
				zhushu = (int)ZCBetcodeUtil.getZCRJCDTZhushu(code_new, sign, dtTab, streak);
				totalMoney = (int)ZCBetcodeUtil.getZCRJCDTMoney(code_new, multiple, sign, dtTab, streak);
			}else if(wanfa.equals(Constant.ZC_ZJ)){
				zhushu = (int)ZCBetcodeUtil.convertNineZhushu(code_new, sign, dtTab, streak);
				totalMoney = (int)ZCBetcodeUtil.convertNineMoney(code_new, multiple, sign, dtTab, streak);
			}
			
			//设置彩种内容和注码
			if(lotno.equals(Constant.ZC_SFC)){
				betcodeBean.setLotno(Constant.SFC14);
				zhuma = ZCBetcodeResovle.getZCBetcode(code_new, sign);
			}else if(lotno.equals(Constant.ZC_RJC)){
				betcodeBean.setLotno(Constant.SFC9);
				zhuma = ZCBetcodeResovle.getZCBetcode(code_new, sign, streak);
			}else if(lotno.equals(Constant.ZC_BQC)){
				betcodeBean.setLotno(Constant.BQC);
				zhuma = ZCBetcodeResovle.getZCBetcode(code_new, sign);
			}else if(lotno.equals(Constant.ZC_JQC)){
				betcodeBean.setLotno(Constant.JQC);
				zhuma = ZCBetcodeResovle.getZCBetcode(code_new, sign);
			}
			betcodeBean.setBetcode(zhuma);
			betcodeBean.setZhushu(String.valueOf(zhushu));
			betcodeBean.setTotalMoney(String.valueOf(totalMoney));
			list.add(betcodeBean);
		}
		return list;		 
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
