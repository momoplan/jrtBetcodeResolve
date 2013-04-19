package com.jrt.betcodeResolve.lotnoBetcodeUtil;

import java.util.ArrayList;
import java.util.List;

import com.jrt.betcodeResolve.util.BetcodeResolveUtil;
import com.jrt.betcodeResolve.util.Constant;

/**
 * 
 *		足彩解析注码算注数、金额、玩法的类
 * @author
 *		徐丽
 *
 */
public class ZCBetcodeUtil {
	
	/**
	 * 
	 *      足彩单式投注得注数
	 * @param 
	 * 		betcode 注码
	 * 		以任九场为例:3,1,0,1,3,1,1,#,1,#,#,1,#,#;3,1,0,1,3,1,1,#,1,#,#,1,#,#
	 * @param 
	 * 		tabNumber 多注之间的分隔符 示例中为";"
	 * @return   
	 * 		返回传入注码的注数 2注
	 * 
	 */
    public static int getZCSimplexZhushu(String betcode,String tabNumber){
    	//根据注码之间的分隔符tabNumber分隔注码
    	String codes[] = betcode.split("\\"+tabNumber);
    	
    	//注码的长度就是注码的注数
    	return codes.length;
    }
    
    /**
	 * 
	 *      足彩单式根据注数算金额
	 * @param 
	 * 		betcode 注码
	 * 		以任九场为例:3,1,0,1,3,1,1,#,1,#,#,1,#,#;3,1,0,1,3,1,1,#,1,#,#,1,#,#
	 * @param 
	 * 		tabNumber 多注之间的分隔符 示例中为";"
	 * @return   
	 * 		总金额 = 注数*倍数*单价（单注彩票的金额） 示例中为4元
	 * 
	 */
    public static int getZCSimplexMoney(String betcode,int multiple,String tabNumber){
    	//调用算注数的方法得到注数
    	int zhushu = getZCSimplexZhushu(betcode, tabNumber);
    	
    	//总金额=注数*倍数*彩票的单价
    	return zhushu*multiple*Constant.LOTTERY_PRICE;
    }
    
    /**
     * 
     *      得到复式注码的注数
     * @param 
     * 		betcode 注码  以任九场为例:310,1,0,1,3,1,1,#,1,#,#,1,#,#
     * @param 
     * 		sign 注码之间的分隔符 示例中为","
     * @return 
     * 		返回复式的注数 示例为3注
     * 
     */
    public static int getZCDuplexZhushu(String betcode,String sign){
    	//根据注码之间的分隔符sign分隔各场注码
    	String codes[] = betcode.split("\\"+sign);
    	
    	//调用算注数的方法得到足彩复式的注数
    	int zhushu = BetcodeResolveUtil.getZCDoubleNumber(codes);
    	return zhushu;
    }
    
    /**
     *  
     *      得到复式注码的金额
     * @param 
     * 		betcode 注码 以任九场为例:310,1,0,1,3,1,1,#,1,#,#,1,#,#
     * @param 
     * 		multiple 倍数示例中为1倍
     * @param 
     * 		sign 注码之间的分隔符 示例中为","
     * @return 
     * 		返回复式的总金额=注数*倍数*单价（单张彩票的金额）
     * 		示例中总金额为6元
     * 
     */
    public static int getZCDuplexMoney(String betcode,int multiple,String sign){
    	
    	//调用算注数的方法得到足彩复式的注数
    	int zhushu = getZCDuplexZhushu(betcode, sign);
    	
    	//总金额=注数*倍数*单价（单张彩票的金额）
    	return zhushu*multiple*Constant.LOTTERY_PRICE;
    }
    
    
    
    
    public static long convertNineZhushu(String betcode,String sign,
    		String dtTab,String streak) {
		int z1 = 0, z2 = 0, z3 = 0;
		String[] zhumas = betcode.split(sign);
		for (int i = 0; i < zhumas.length; i++) {
			if (!streak.equals(zhumas[i])) {
				if (zhumas[i].length() == 1) {
					z1 = z1 + 1;
				} else if (zhumas[i].length() == 2) {
					z2 = z2 + 1;
				} else if (zhumas[i].length() == 3) {
					z3 = z3 + 1;
				}
			}
		}

		long result = 0;
		for (int i = 0; i <= z1; i++) {
			for (int j = 0; j <= z2; j++) {
				int k = 9 - i - j;
				if (k <= z3 && k >= 0) {
					long nk1, nk2, nk3, exp1, exp2;
					nk1 = (z1 == 0 ? 1 : nchoosek(z1, i));//
					nk2 = (z2 == 0 ? 1 : nchoosek(z2, j));
					nk3 = (z3 == 0 ? 1 : nchoosek(z3, k));
					exp1 = exp(2, j);
					exp2 = exp(3, k);
					result = result + nk1 * nk2 * nk3 * exp1 * exp2;
				}
			}
		}
		return result;
	}


    public static int convertNineMoney(String betcode,int multiple, String sign,
    		String dtTab,String streak){
    	
    	int zhushu = (int)convertNineZhushu(betcode, sign, dtTab, streak);
    	return zhushu*multiple*Constant.LOTTERY_PRICE;
    }
    
    
    
    /**
     * 
     *      足彩任九场胆拖 算注数的方法
     * @param betcode 注码
     * @param sign 注码之间的分隔符
     * @param dtTab 胆码和拖码之间的分隔符
     * @param streak 场次代替符
     * @return    
     * 		注数
     * 
     */
	public static long getZCRJCDTZhushu(String betcode,String sign,
    		String dtTab,String streak) {
		int d2=0; //胆码中双选的个数
		int d3=0; //胆码中三选的个数
		
		int t1=0; //拖码中单选的个数
		int t2=0; //拖码中双选的个数
		int t3=0; //拖码中三选的个数
		int choose=0;//从拖码选择来构成整个投注号码的个数（9-胆码个数）
		int danmaCount=0;//胆码个数
		
		//根据胆码和拖码之间的分隔符dtTab分隔胆码和拖码
		String codes[] = betcode.split("\\"+dtTab);
		String danma = codes[0];//胆码
		String tuoma = codes[1];//拖码
		
		//获取胆码个数
		String danmas[] = danma.split("\\"+sign);
		for(int i=0;i<danmas.length;i++){
			
			//判断是否存在胆码代替法“#”若不存在让胆码个数+1
			if(danmas[i].indexOf(streak)<=-1){			
				danmaCount++;
			}
			if(danmas[i].length()==2){
				d2++;
			}else if(danmas[i].length()==3){
				d3++;
			}
		}
		System.out.println("胆码中双选的个数d2="+d2+";胆码中三选的个数d3="+d3);
		
		choose = 9-danmaCount;
		System.out.println("choose="+choose);
		
		//获取拖码的个数
		String tuomas[] = tuoma.split("\\"+sign);
		for(int i=0;i<tuomas.length;i++){
			if(tuomas[i].length()==1){
				if(!tuomas[i].equals(Constant.ZC_STREAK)){
					t1++;
				}
			}else if(tuomas[i].length()==2){
				t2++;
			}else if(tuomas[i].length()==3){
				t3++;
			}
		}
		System.out.println("拖码中单选个数t1="+t1+";拖码中双选的个数t2="+t2+";拖码中三选的个数t3="+t3);
		
		//调用算足彩任九场胆拖注数的算法获取注数并返回
		long zhushu = BetcodeResolveUtil.dantuo(d2, d3, t1, t2, t3, choose);
		return zhushu;
	}
	
	 /**
     * 
     *      足彩算金额的方法
     * @param betcode 注码
     * @param multiple 倍数
     * @param sign 注码之间的分隔符
     * @param dtTab 胆码和拖码之间的分隔符
     * @param streak 场次代替符
     * @return    
     * 		总金额 = 注数*倍数*彩票的单价(单张彩票的金额)
     *
     */
    public static long getZCRJCDTMoney(String betcode,int multiple, String sign,
    		String dtTab,String streak){
    	//调用算注数的方法
    	long zhushu = getZCRJCDTZhushu(betcode, sign, dtTab, streak);
    	//总金额 = 注数*倍数*彩票的单价(单张彩票的金额)
    	return zhushu*multiple*Constant.LOTTERY_PRICE;
    	
    }
	
    
    /**
     * 
     *      足彩任九场胆拖 算注数的方法
     * @param betcode 注码
     * @param sign 注码之间的分隔符
     * @param dtTab 胆码和拖码之间的分隔符
     * @param streak 场次代替符
     * @param playName 玩法
     * @return    
     * 		注数
     * 
     */
    public static int getRJCDTZhushu(String betcode,String sign,
    		String dtTab,String streak,String playName){
    	List<String> d = new ArrayList<String>();
    	List<String> t = new ArrayList<String>();
    	String danmas[]={};
    	String tuomas[]={};
    	if(dtTab!=null && !dtTab.equals("")){
    		//根据胆码和拖码之间的分隔符dtTab分隔胆码和拖码
        	String codes[] = betcode.split("\\"+dtTab);
        	
        	//根据注码之间的分隔符得到胆码和拖码的个数得到注数
        	danmas = codes[0].split("\\"+sign);
        	tuomas = codes[1].split("\\"+sign);
        	
        	//将胆码放到集合中
        	for(String str:danmas){
    			if(!streak.equals(str)){
    				d.add(str);
    			}
    		}
        	
        	//将拖码放到集合中
    		for(String str:tuomas){
    			if(!streak.equals(str)){
    				t.add(str);
    			}
    		}
    	}
		//调用算注数的方法算得注数并返回
    	int zhushu = BetcodeResolveUtil.getRJCCount(d, t, 9);
    	return zhushu;
    }
    
    /**
     * 
     *      足彩算金额的方法
     * @param betcode 注码
     * @param multiple 倍数
     * @param sign 注码之间的分隔符
     * @param dtTab 胆码和拖码之间的分隔符
     * @param streak 场次代替符
     * @return    
     * 		总金额 = 注数*倍数*彩票的单价(单张彩票的金额)
     *
     */
    public static int getRJCDTMoney(String betcode,int multiple, String sign,
    		String dtTab,String streak,String playName){
    	//调用算注数的方法
    	int zhushu = getRJCDTZhushu(betcode, sign, dtTab, streak, playName);
    	//总金额 = 注数*倍数*彩票的单价(单张彩票的金额)
    	return zhushu*multiple*Constant.LOTTERY_PRICE;
    	
    }
    
    
    public static long nchoosek(int n,int k) {
		if(n <= 0 || k < 0 || n < k) {
			return -1;
		}
		if(k == 0 || n==k) {
			return 1;
		}
		if(k > n/2) {
			k = n - k;
		}
		
		long result = multiplyByStep(n,n-k+1)/multiplyByStep(k,1);
		
		return result;
	}
    
    
    public static long multiplyByStep(int m,int n) {
		if(m < 0 || n < 0) {
			return -1;
		}
		
		long result = 1l;
		
		if(m >= n) {
			for(int i = n;i <= m;i++) {
				result = result * i;
			}
		}else {
			for(int i = m;i <= n;i++) {
				result = result * i;
			}
		}
		return result;
	}
    
    
    public static long exp(long d, long z) {
		long result = 1L;
		for (int i = 0; i < z; i++) {
			result = result * d;
		}
		return result;
	}
}
