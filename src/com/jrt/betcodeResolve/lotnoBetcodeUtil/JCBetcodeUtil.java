package com.jrt.betcodeResolve.lotnoBetcodeUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jrt.betcodeResolve.util.BaseMath;
import com.jrt.betcodeResolve.util.Constant;

/**
 * 
 *		竞彩解析注码算注数、金额、玩法的类
 *
 */
public class JCBetcodeUtil {
	
	/**
	 * 
	 *      足彩单式投注得注数
	 * @param 
	 * 		betcode 注码
	 * 		500@20101004|1|301|3^
	 * @param 
	 * 		tabNumber 多注之间的分隔符 示例中为"^"
	 * @return   
	 * 		返回传入注码的注数 1注
	 * 
	 */
    public static int getJCSimplexZhushu(String betcode,String tabNumber){
    	//根据注码之间的分隔符tabNumber分隔注码
    	String codes[] = betcode.split("\\"+tabNumber);
    	int simplexZhushu = 0;
    	for(int i=0;i<codes.length;i++){
    		simplexZhushu += codes[i].split("|")[3].length();
    	}
    	//注码的长度就是注码的注数
    	return simplexZhushu;
    }
    
    /**
	 * 
	 *      竞彩单式根据注数算金额
	 * @param 
	 * 		betcode 注码
	 * 		500@20101004|1|301|3^20101005|2|201|3^
	 * @param 
	 * 		tabNumber 多注之间的分隔符 示例中为"^"
	 * @return   
	 * 		总金额 = 注数*倍数*单价（单注彩票的金额） 示例中为4元
	 * 
	 */
    public static int getJCSimplexMoney(String betcode,int multiple,String tabNumber){
    	//调用算注数的方法得到注数
    	int zhushu = getJCSimplexZhushu(betcode, tabNumber);
    	
    	//总金额=注数*倍数*彩票的单价
    	return zhushu*multiple*Constant.LOTTERY_PRICE;
    }
    
    /**
     * 
     *      得到复式注码的注数
     * @param index 对应玩法的索引 
     * @param
     * 		betcode 注码  
     * 500@20101004|1|301|30^
     * 500@20101004|1|301|0^20101004|1|302|3^
     * @param 
     * 		sign 注码之间的分隔符 示例中为"^"
     * @return 
     * 		返回复式的注数 示例为2注
     * 
     */
    public static int getJCDuplexZhushu(int index,String betcode,String sign){
    	//根据注码之间的分隔符sign分隔各场注码
    	//String codes[] = betcode.split("\\"+sign);
    	
    	//调用算注数的方法得到竞彩复式的注数
    	int zhushu = getJCDoubleNumber(index,betcode);
    	return zhushu;
    }
    
    /**
     *  
     *      得到复式注码的金额
     * @param 
     * 		betcode 注码 
	 * 500@20101004|1|301|30^
     * 500@20101004|1|301|0^20101004|1|302|3^
     * @param 
     * 		multiple 倍数示例中为1倍
     * @param 
     * 		sign 注码之间的分隔符 示例中为"^"
     * @return 
     * 		返回复式的总金额=注数*倍数*单价（单张彩票的金额）
     * 
     */
    public static int getJCDuplexMoney(int index ,String betcode,int multiple,String sign){
    	
    	//调用算注数的方法得到足彩复式的注数
    	int zhushu = getJCDuplexZhushu(index,betcode, sign);
    	
    	//总金额=注数*倍数*单价（单张彩票的金额）
    	return zhushu*multiple*Constant.LOTTERY_PRICE;
    }
    
  //-----------------------竞彩注数算法--------------------
	/**
	 * 
	 *      得到竞彩复式的注数
	 * @param betcode 注码的集合
	 * @param index 对应玩法的索引
	 * @return   
	 * 		返回注码的注数 
	 * 
	 */
	public static int getJCDoubleNumber(int index,String betcode){
		//得到销售代码（即玩法）
		String wanfa = betcode.substring(0,betcode.indexOf("+"));
		String newBetcode = betcode.substring(betcode.indexOf("+")+1); 
		long zhushu = 0;
		if(wanfa.indexOf("-")!=-1){
				if(wanfa.split("-")[index].equals("502")){
					List<List<String>> list = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 2);
					for(List<String> codes:list) {
						String[] codelength = getSelectCode(codes);
						zhushu = zhushu + mulSelectCode(codelength);
					}
				}else if(wanfa.split("-")[index].equals("503")){
					List<List<String>> list = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 3);
					for(List<String> codes:list) {
						String[] codelength = getSelectCode(codes);
						zhushu = zhushu + mulSelectCode(codelength);
					}
				}else if(wanfa.split("-")[index].equals("504")){
					List<List<String>> list = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 4);
					for(List<String> codes:list) {
						String[] codelength = getSelectCode(codes);
						zhushu = zhushu + mulSelectCode(codelength);
					}
				}else if(wanfa.split("-")[index].equals("505")){
					List<List<String>> list = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 5);
					for(List<String> codes:list) {
						String[] codelength = getSelectCode(codes);
						zhushu = zhushu + mulSelectCode(codelength);
					}
				}else if(wanfa.split("-")[index].equals("506")){
					List<List<String>> list = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 6);
					for(List<String> codes:list) {
						String[] codelength = getSelectCode(codes);
						zhushu = zhushu + mulSelectCode(codelength);
					}
				}else if(wanfa.split("-")[index].equals("507")){
					List<List<String>> list = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 7);
					for(List<String> codes:list) {
						String[] codelength = getSelectCode(codes);
						zhushu = zhushu + mulSelectCode(codelength);
					}
				}else if(wanfa.split("-")[index].equals("508")){
					List<List<String>> list = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 8);
					for(List<String> codes:list) {
						String[] codelength = getSelectCode(codes);
						zhushu = zhushu + mulSelectCode(codelength);
					}
				}else if(wanfa.split("-")[index].equals("526")){
					List<List<String>> list_cn3 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 3);
					
					List<List<String>> list_all = new ArrayList<List<String>>();
					
					for(List<String> list:list_cn3) {
						list_all.addAll(getBetcodeCollection(list,2));
					}
					for(List<String> codes:list_all) {
						String[] codelength = getSelectCode(codes);
						zhushu = zhushu + mulSelectCode(codelength);
					}
				}else if(wanfa.split("-")[index].equals("527")){
					//C(n,3)
					List<List<String>> list_cn3 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 3);
					
					List<List<String>> list_all = new ArrayList<List<String>>();
					
					//C(3,2)
					for(List<String> list:list_cn3) {
						list_all.addAll(getBetcodeCollection(list,2));
					}
					
					//3*4=C(3,2)+C(3,3)
					list_all.addAll(list_cn3);
					
					for(List<String> codes:list_all) {
						String[] codelength = getSelectCode(codes);
						zhushu = zhushu + mulSelectCode(codelength);
					}
				}else if(wanfa.split("-")[index].equals("528")){
					//C(n,4)
					List<List<String>> list_cn4 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 4);
					
					List<List<String>> list_all = new ArrayList<List<String>>();
					
					//C(4,2)
					for(List<String> list:list_cn4) {
						list_all.addAll(getBetcodeCollection(list,2));
					}
					for(List<String> codes:list_all) {
						String[] codelength = getSelectCode(codes);
						zhushu = zhushu + mulSelectCode(codelength);
					}
				}else if(wanfa.split("-")[index].equals("529")){
					//C(n,4)
					List<List<String>> list_cn4 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 4);
					
					List<List<String>> list_all = new ArrayList<List<String>>();
					
					//C(4,4)
					list_all.addAll(list_cn4);
					//C(4,3)
					for(List<String> list:list_cn4) {
						list_all.addAll(getBetcodeCollection(list, 3));
					}
					//C(4,2)
					for(List<String> list:list_cn4) {
						list_all.addAll(getBetcodeCollection(list, 2));
					}
					for(List<String> codes:list_all) {
						String[] codelength = getSelectCode(codes);
						zhushu = zhushu + mulSelectCode(codelength);
					}
				}else if(wanfa.split("-")[index].equals("530")){
					//C(n,5)
					List<List<String>> list_cn5 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 5);
					
					//C(5,2)
					List<List<String>> list_all = new ArrayList<List<String>>();
					for(List<String> list:list_cn5) {
						list_all.addAll(getBetcodeCollection(list, 2));
					}
					
					for(List<String> codes:list_all) {
						String[] codelength = getSelectCode(codes);
						zhushu = zhushu + mulSelectCode(codelength);
					}
				}else if(wanfa.split("-")[index].equals("531")){
					//C(n,5)
					List<List<String>> list_cn5 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 5);
					
					List<List<String>> list_all = new ArrayList<List<String>>();
					
					//C(5,3)
					for(List<String> list:list_cn5) {
						list_all.addAll(getBetcodeCollection(list, 3));
					}
					
					//C(5,2)
					for(List<String> list:list_cn5) {
						list_all.addAll(getBetcodeCollection(list, 2));
					}
					
					for(List<String> codes:list_all) {
						String[] codelength = getSelectCode(codes);
						zhushu = zhushu + mulSelectCode(codelength);
					}
				}else if(wanfa.split("-")[index].equals("532")){
					//C(n,5)
					List<List<String>> list_cn5 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 5);
					
					List<List<String>> list_all = new ArrayList<List<String>>();
					
					//C(5,5)
					list_all.addAll(list_cn5);

					//C(5,4)
					for(List<String> list:list_cn5) {
						list_all.addAll(getBetcodeCollection(list, 4));
					}
					
					//C(5,3)
					for(List<String> list:list_cn5) {
						list_all.addAll(getBetcodeCollection(list, 3));
					}
					
					//C(5,2)
					for(List<String> list:list_cn5) {
						list_all.addAll(getBetcodeCollection(list, 2));
					}
					
					for(List<String> codes:list_all) {
						String[] codelength = getSelectCode(codes);
						zhushu = zhushu + mulSelectCode(codelength);
					}
				}else if(wanfa.split("-")[index].equals("533")){
					//C(n,6)
					List<List<String>> list_cn6 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 6);
					
					List<List<String>> list_all = new ArrayList<List<String>>();
					
					//C(6,2)
					for(List<String> list:list_cn6) {
						list_all.addAll(getBetcodeCollection(list, 2));
					}

					for(List<String> codes:list_all) {
						String[] codelength = getSelectCode(codes);
						zhushu = zhushu + mulSelectCode(codelength);
					}
				}else if(wanfa.split("-")[index].equals("534")){
					//C(n,6)
					List<List<String>> list_cn6 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 6);
					
					List<List<String>> list_all = new ArrayList<List<String>>();
					
					//C(6,2)
					for(List<String> list:list_cn6) {
						list_all.addAll(getBetcodeCollection(list, 2));
					}
					
					//C(6,3)
					for(List<String> list:list_cn6) {
						list_all.addAll(getBetcodeCollection(list, 3));
					}

					for(List<String> codes:list_all) {
						String[] codelength = getSelectCode(codes);
						zhushu = zhushu + mulSelectCode(codelength);
					}
				}else if(wanfa.split("-")[index].equals("535")){
					//C(n,6)
					List<List<String>> list_cn6 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 6);
					
					List<List<String>> list_all = new ArrayList<List<String>>();
					
					//C(6,4)
					for(List<String> list:list_cn6) {
						list_all.addAll(getBetcodeCollection(list, 4));
					}
					//C(6,3)
					for(List<String> list:list_cn6) {
						list_all.addAll(getBetcodeCollection(list, 3));
					}
					//C(6,2)
					for(List<String> list:list_cn6) {
						list_all.addAll(getBetcodeCollection(list, 2));
					}
					
					for(List<String> codes:list_all) {
						String[] codelength = getSelectCode(codes);
						zhushu = zhushu + mulSelectCode(codelength);
					}
				}else if(wanfa.split("-")[index].equals("536")){
					//C(n,6)
					List<List<String>> list_cn6 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 6);
					
					List<List<String>> list_all = new ArrayList<List<String>>();
					
					//C(6,6)
					list_all.addAll(list_cn6);

					//C(6,5)
					for(List<String> list:list_cn6) {
						list_all.addAll(getBetcodeCollection(list, 5));
					}
					
					//C(6,4)
					for(List<String> list:list_cn6) {
						list_all.addAll(getBetcodeCollection(list, 4));
					}
					//C(6,3)
					for(List<String> list:list_cn6) {
						list_all.addAll(getBetcodeCollection(list, 3));
					}
					//C(6,2)
					for(List<String> list:list_cn6) {
						list_all.addAll(getBetcodeCollection(list, 2));
					}
					
					for(List<String> codes:list_all) {
						String[] codelength = getSelectCode(codes);
						zhushu = zhushu + mulSelectCode(codelength);
					}
				}else if(wanfa.split("-")[index].equals("537")){

					//C(n,7)
					List<List<String>> list_cn7 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 7);
					
					List<List<String>> list_all = new ArrayList<List<String>>();
					
					//C(7,7)
					list_all.addAll(list_cn7);
					//C(7,6)
					for(List<String> list:list_cn7) {
						list_all.addAll(getBetcodeCollection(list, 6));
					}
					//C(7,5)
					for(List<String> list:list_cn7) {
						list_all.addAll(getBetcodeCollection(list, 5));
					}
					//C(7,4)
					for(List<String> list:list_cn7) {
						list_all.addAll(getBetcodeCollection(list, 4));
					}
					//C(7,3)
					for(List<String> list:list_cn7) {
						list_all.addAll(getBetcodeCollection(list, 3));
					}
					//C(7,2)
					for(List<String> list:list_cn7) {
						list_all.addAll(getBetcodeCollection(list, 2));
					}
					
					for(List<String> codes:list_all) {
						String[] codelength = getSelectCode(codes);
						zhushu = zhushu + mulSelectCode(codelength);
					}
				}else if(wanfa.split("-")[index].equals("538")){
					//C(n,8)
					List<List<String>> list_cn8 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 8);
					
					List<List<String>> list_all = new ArrayList<List<String>>();
					
					//C(8,8)
					list_all.addAll(list_cn8);
					//C(8,7)
					for(List<String> list:list_cn8) {
						list_all.addAll(getBetcodeCollection(list, 7));
					}
					//C(8,6)
					for(List<String> list:list_cn8) {
						list_all.addAll(getBetcodeCollection(list, 6));
					}
					//C(8,5)
					for(List<String> list:list_cn8) {
						list_all.addAll(getBetcodeCollection(list, 5));
					}
					//C(8,4)
					for(List<String> list:list_cn8) {
						list_all.addAll(getBetcodeCollection(list, 4));
					}
					//C(8,3)
					for(List<String> list:list_cn8) {
						list_all.addAll(getBetcodeCollection(list, 3));
					}
					//C(8,2)
					for(List<String> list:list_cn8) {
						list_all.addAll(getBetcodeCollection(list, 2));
					}
					
					for(List<String> codes:list_all) {
						String[] codelength = getSelectCode(codes);
						zhushu = zhushu + mulSelectCode(codelength);
					}
				}else if(wanfa.split("-")[index].equals("539")){
					//C(n,4)
					List<List<String>> list_cn4 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 4);
					
					//C(4,3)
					List<List<String>> list_all = new ArrayList<List<String>>();
					for(List<String> list:list_cn4) {
						list_all.addAll(getBetcodeCollection(list, 3));
					}
					
					for(List<String> codes:list_all) {
						String[] codelength = getSelectCode(codes);
						zhushu = zhushu + mulSelectCode(codelength);
					}
				}else if(wanfa.split("-")[index].equals("540")){
					//C(n,4)
					List<List<String>> list_cn4 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 4);
					
					List<List<String>> list_all = new ArrayList<List<String>>();
					
					//C(4,4)
					list_all.addAll(list_cn4);
					//C(4,3)
					for(List<String> list:list_cn4) {
						list_all.addAll(getBetcodeCollection(list, 3));
					}
					for(List<String> codes:list_all) {
						String[] codelength = getSelectCode(codes);
						zhushu = zhushu + mulSelectCode(codelength);
					}
				}else if(wanfa.split("-")[index].equals("541")){
					//C(n,5)
					List<List<String>> list_cn5 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 5);
					
					List<List<String>> list_all = new ArrayList<List<String>>();
					
					//C(5,5)
					list_all.addAll(list_cn5);
					//C(5,4)
					for(List<String> list:list_cn5) {
						list_all.addAll(getBetcodeCollection(list, 4));
					}
					//C(5,3)
					for(List<String> list:list_cn5) {
						list_all.addAll(getBetcodeCollection(list, 3));
					}
					
					for(List<String> codes:list_all) {
						String[] codelength = getSelectCode(codes);
						zhushu = zhushu + mulSelectCode(codelength);
					}
				}else if(wanfa.split("-")[index].equals("542")){
					//C(n,6)
					List<List<String>> list_cn6 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 6);
					
					List<List<String>> list_all = new ArrayList<List<String>>();
					
					//C(6,3)
					for(List<String> list:list_cn6) {
						list_all.addAll(getBetcodeCollection(list, 3));
					}
					
					for(List<String> codes:list_all) {
						String[] codelength = getSelectCode(codes);
						zhushu = zhushu + mulSelectCode(codelength);
					}
				}else if(wanfa.split("-")[index].equals("543")){
					//C(n,6)
					List<List<String>> list_cn6 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 6);
					
					List<List<String>> list_all = new ArrayList<List<String>>();
					
					//C(6,6)
					list_all.addAll(list_cn6);
					//C(6,5)
					for(List<String> list:list_cn6) {
						list_all.addAll(getBetcodeCollection(list, 5));
					}
					//C(6,4)
					for(List<String> list:list_cn6) {
						list_all.addAll(getBetcodeCollection(list, 4));
					}
					//C(6,3)
					for(List<String> list:list_cn6) {
						list_all.addAll(getBetcodeCollection(list, 3));
					}
					
					for(List<String> codes:list_all) {
						String[] codelength = getSelectCode(codes);
						zhushu = zhushu + mulSelectCode(codelength);
					}
				}else if(wanfa.split("-")[index].equals("544")){
					//C(n,5)
					List<List<String>> list_cn5 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 5);
					
					List<List<String>> list_all = new ArrayList<List<String>>();
					
					//C(5,4)
					for(List<String> list:list_cn5) {
						list_all.addAll(getBetcodeCollection(list, 4));
					}
					
					for(List<String> codes:list_all) {
						String[] codelength = getSelectCode(codes);
						zhushu = zhushu + mulSelectCode(codelength);
					}
				}else if(wanfa.split("-")[index].equals("545")){
					//C(n,5)
					List<List<String>> list_cn5 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 5);
					
					List<List<String>> list_all = new ArrayList<List<String>>();
					
					//C(5,5)
					list_all.addAll(list_cn5);
					//C(5,4)
					for(List<String> list:list_cn5) {
						list_all.addAll(getBetcodeCollection(list, 4));
					}
					
					for(List<String> codes:list_all) {
						String[] codelength = getSelectCode(codes);
						zhushu = zhushu + mulSelectCode(codelength);
					}
				}else if(wanfa.split("-")[index].equals("546")){
					//C(n,6)
					List<List<String>> list_cn6 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 6);
					
					List<List<String>> list_all = new ArrayList<List<String>>();
					
					//C(6,6)
					list_all.addAll(list_cn6);
					//C(6,5)
					for(List<String> list:list_cn6) {
						list_all.addAll(getBetcodeCollection(list, 5));
					}
					//C(6,4)
					for(List<String> list:list_cn6) {
						list_all.addAll(getBetcodeCollection(list, 4));
					}
					for(List<String> codes:list_all) {
						String[] codelength = getSelectCode(codes);
						zhushu = zhushu + mulSelectCode(codelength);
					}
				}else if(wanfa.split("-")[index].equals("547")){
					//C(n,7)
					List<List<String>> list_cn7 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 7);
					
					List<List<String>> list_all = new ArrayList<List<String>>();
					
					//C(7,4)
					for(List<String> list:list_cn7) {
						list_all.addAll(getBetcodeCollection(list, 4));
					}
					
					for(List<String> codes:list_all) {
						String[] codelength = getSelectCode(codes);
						zhushu = zhushu + mulSelectCode(codelength);
					}
				}else if(wanfa.split("-")[index].equals("548")){
					//C(n,8)
					List<List<String>> list_cn8 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 8);
					
					List<List<String>> list_all = new ArrayList<List<String>>();
					
					//C(8,4)
					for(List<String> list:list_cn8) {
						list_all.addAll(getBetcodeCollection(list, 4));
					}
					
					for(List<String> codes:list_all) {
						String[] codelength = getSelectCode(codes);
						zhushu = zhushu + mulSelectCode(codelength);
					}
				}else if(wanfa.split("-")[index].equals("549")){
					//C(n,6)
					List<List<String>> list_cn6 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 6);
					
					List<List<String>> list_all = new ArrayList<List<String>>();
					
					//C(6,5)
					for(List<String> list:list_cn6) {
						list_all.addAll(getBetcodeCollection(list, 5));
					}
					
					for(List<String> codes:list_all) {
						String[] codelength = getSelectCode(codes);
						zhushu = zhushu + mulSelectCode(codelength);
					}
				}else if(wanfa.split("-")[index].equals("550")){
					//C(n,6)
					List<List<String>> list_cn6 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 6);
					
					List<List<String>> list_all = new ArrayList<List<String>>();
					//C(6,6)
					list_all.addAll(list_cn6);
					
					//C(6,5)
					for(List<String> list:list_cn6) {
						list_all.addAll(getBetcodeCollection(list, 5));
					}
					
					for(List<String> codes:list_all) {
						String[] codelength = getSelectCode(codes);
						zhushu = zhushu + mulSelectCode(codelength);
					}
				}else if(wanfa.split("-")[index].equals("551")){
					//C(n,7)
					List<List<String>> list_cn7 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 7);
					
					List<List<String>> list_all = new ArrayList<List<String>>();
					
					//C(7,5)
					for(List<String> list:list_cn7) {
						list_all.addAll(getBetcodeCollection(list, 5));
					}
					
					for(List<String> codes:list_all) {
						String[] codelength = getSelectCode(codes);
						zhushu = zhushu + mulSelectCode(codelength);
					}
				}else if(wanfa.split("-")[index].equals("552")){
					//C(n,8)
					List<List<String>> list_cn8 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 8);
					
					List<List<String>> list_all = new ArrayList<List<String>>();
					
					//C(8,5)
					for(List<String> list:list_cn8) {
						list_all.addAll(getBetcodeCollection(list, 5));
					}
					
					for(List<String> codes:list_all) {
						String[] codelength = getSelectCode(codes);
						zhushu = zhushu + mulSelectCode(codelength);
					}
				}else if(wanfa.split("-")[index].equals("553")){
					//C(n,7)
					List<List<String>> list_cn7 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 7);
					
					List<List<String>> list_all = new ArrayList<List<String>>();
					
					//C(7,6)
					for(List<String> list:list_cn7) {
						list_all.addAll(getBetcodeCollection(list, 6));
					}
					
					for(List<String> codes:list_all) {
						String[] codelength = getSelectCode(codes);
						zhushu = zhushu + mulSelectCode(codelength);
					}
				}else if(wanfa.split("-")[index].equals("554")){
					//C(n,7)
					List<List<String>> list_cn7 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 7);
					
					List<List<String>> list_all = new ArrayList<List<String>>();
					
					//C(7,7)
					list_all.addAll(list_cn7);
					
					//C(7,6)
					for(List<String> list:list_cn7) {
						list_all.addAll(getBetcodeCollection(list, 6));
					}
					
					for(List<String> codes:list_all) {
						String[] codelength = getSelectCode(codes);
						zhushu = zhushu + mulSelectCode(codelength);
					}
				}else if(wanfa.split("-")[index].equals("555")){
					//C(n,8)
					List<List<String>> list_cn8 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 8);
					
					List<List<String>> list_all = new ArrayList<List<String>>();
					
					//C(8,6)
					for(List<String> list:list_cn8) {
						list_all.addAll(getBetcodeCollection(list, 6));
					}
					
					for(List<String> codes:list_all) {
						String[] codelength = getSelectCode(codes);
						zhushu = zhushu + mulSelectCode(codelength);
					}
				}else if(wanfa.split("-")[index].equals("556")){
					//C(n,8)
					List<List<String>> list_cn8 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 8);
					
					List<List<String>> list_all = new ArrayList<List<String>>();
					
					//C(8,7)
					for(List<String> list:list_cn8) {
						list_all.addAll(getBetcodeCollection(list, 7));
					}
					
					for(List<String> codes:list_all) {
						String[] codelength = getSelectCode(codes);
						zhushu = zhushu + mulSelectCode(codelength);
					}
				}else if(wanfa.split("-")[index].equals("557")){
					//C(n,8)
					List<List<String>> list_cn8 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 8);
					
					List<List<String>> list_all = new ArrayList<List<String>>();
					
					//C(8,8)
					list_all.addAll(list_cn8);
					//C(8,7)
					for(List<String> list:list_cn8) {
						list_all.addAll(getBetcodeCollection(list, 7));
					}
					
					for(List<String> codes:list_all) {
						String[] codelength = getSelectCode(codes);
						zhushu = zhushu + mulSelectCode(codelength);
					}
				}	
		}else{
			if(wanfa.equals("502")){
				List<List<String>> list = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 2);
				for(List<String> codes:list) {
					String[] codelength = getSelectCode(codes);
					zhushu = zhushu + mulSelectCode(codelength);
				}
			}else if(wanfa.equals("503")){
				List<List<String>> list = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 3);
				for(List<String> codes:list) {
					String[] codelength = getSelectCode(codes);
					zhushu = zhushu + mulSelectCode(codelength);
				}
			}else if(wanfa.equals("504")){
				List<List<String>> list = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 4);
				for(List<String> codes:list) {
					String[] codelength = getSelectCode(codes);
					zhushu = zhushu + mulSelectCode(codelength);
				}
			}else if(wanfa.equals("505")){
				List<List<String>> list = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 5);
				for(List<String> codes:list) {
					String[] codelength = getSelectCode(codes);
					zhushu = zhushu + mulSelectCode(codelength);
				}
			}else if(wanfa.equals("506")){
				List<List<String>> list = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 6);
				for(List<String> codes:list) {
					String[] codelength = getSelectCode(codes);
					zhushu = zhushu + mulSelectCode(codelength);
				}
			}else if(wanfa.equals("507")){
				List<List<String>> list = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 7);
				for(List<String> codes:list) {
					String[] codelength = getSelectCode(codes);
					zhushu = zhushu + mulSelectCode(codelength);
				}
			}else if(wanfa.equals("508")){
				List<List<String>> list = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 8);
				for(List<String> codes:list) {
					String[] codelength = getSelectCode(codes);
					zhushu = zhushu + mulSelectCode(codelength);
				}
			}else if(wanfa.equals("526")){
				List<List<String>> list_cn3 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 3);
				
				List<List<String>> list_all = new ArrayList<List<String>>();
				
				for(List<String> list:list_cn3) {
					list_all.addAll(getBetcodeCollection(list,2));
				}
				for(List<String> codes:list_all) {
					String[] codelength = getSelectCode(codes);
					zhushu = zhushu + mulSelectCode(codelength);
				}
			}else if(wanfa.equals("527")){
				//C(n,3)
				List<List<String>> list_cn3 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 3);
				
				List<List<String>> list_all = new ArrayList<List<String>>();
				
				//C(3,2)
				for(List<String> list:list_cn3) {
					list_all.addAll(getBetcodeCollection(list,2));
				}
				
				//3*4=C(3,2)+C(3,3)
				list_all.addAll(list_cn3);
				
				for(List<String> codes:list_all) {
					String[] codelength = getSelectCode(codes);
					zhushu = zhushu + mulSelectCode(codelength);
				}
			}else if(wanfa.equals("528")){
				//C(n,4)
				List<List<String>> list_cn4 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 4);
				
				List<List<String>> list_all = new ArrayList<List<String>>();
				
				//C(4,2)
				for(List<String> list:list_cn4) {
					list_all.addAll(getBetcodeCollection(list,2));
				}
				for(List<String> codes:list_all) {
					String[] codelength = getSelectCode(codes);
					zhushu = zhushu + mulSelectCode(codelength);
				}
			}else if(wanfa.equals("529")){
				//C(n,4)
				List<List<String>> list_cn4 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 4);
				
				List<List<String>> list_all = new ArrayList<List<String>>();
				
				//C(4,4)
				list_all.addAll(list_cn4);
				//C(4,3)
				for(List<String> list:list_cn4) {
					list_all.addAll(getBetcodeCollection(list, 3));
				}
				//C(4,2)
				for(List<String> list:list_cn4) {
					list_all.addAll(getBetcodeCollection(list, 2));
				}
				for(List<String> codes:list_all) {
					String[] codelength = getSelectCode(codes);
					zhushu = zhushu + mulSelectCode(codelength);
				}
			}else if(wanfa.equals("530")){
				//C(n,5)
				List<List<String>> list_cn5 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 5);
				
				//C(5,2)
				List<List<String>> list_all = new ArrayList<List<String>>();
				for(List<String> list:list_cn5) {
					list_all.addAll(getBetcodeCollection(list, 2));
				}
				
				for(List<String> codes:list_all) {
					String[] codelength = getSelectCode(codes);
					zhushu = zhushu + mulSelectCode(codelength);
				}
			}else if(wanfa.equals("531")){
				//C(n,5)
				List<List<String>> list_cn5 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 5);
				
				List<List<String>> list_all = new ArrayList<List<String>>();
				
				//C(5,3)
				for(List<String> list:list_cn5) {
					list_all.addAll(getBetcodeCollection(list, 3));
				}
				
				//C(5,2)
				for(List<String> list:list_cn5) {
					list_all.addAll(getBetcodeCollection(list, 2));
				}
				
				for(List<String> codes:list_all) {
					String[] codelength = getSelectCode(codes);
					zhushu = zhushu + mulSelectCode(codelength);
				}
			}else if(wanfa.equals("532")){
				//C(n,5)
				List<List<String>> list_cn5 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 5);
				
				List<List<String>> list_all = new ArrayList<List<String>>();
				
				//C(5,5)
				list_all.addAll(list_cn5);
	
				//C(5,4)
				for(List<String> list:list_cn5) {
					list_all.addAll(getBetcodeCollection(list, 4));
				}
				
				//C(5,3)
				for(List<String> list:list_cn5) {
					list_all.addAll(getBetcodeCollection(list, 3));
				}
				
				//C(5,2)
				for(List<String> list:list_cn5) {
					list_all.addAll(getBetcodeCollection(list, 2));
				}
				
				for(List<String> codes:list_all) {
					String[] codelength = getSelectCode(codes);
					zhushu = zhushu + mulSelectCode(codelength);
				}
			}else if(wanfa.equals("533")){
				//C(n,6)
				List<List<String>> list_cn6 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 6);
				
				List<List<String>> list_all = new ArrayList<List<String>>();
				
				//C(6,2)
				for(List<String> list:list_cn6) {
					list_all.addAll(getBetcodeCollection(list, 2));
				}
	
				for(List<String> codes:list_all) {
					String[] codelength = getSelectCode(codes);
					zhushu = zhushu + mulSelectCode(codelength);
				}
			}else if(wanfa.equals("534")){
				//C(n,6)
				List<List<String>> list_cn6 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 6);
				
				List<List<String>> list_all = new ArrayList<List<String>>();
				
				//C(6,2)
				for(List<String> list:list_cn6) {
					list_all.addAll(getBetcodeCollection(list, 2));
				}
				
				//C(6,3)
				for(List<String> list:list_cn6) {
					list_all.addAll(getBetcodeCollection(list, 3));
				}
	
				for(List<String> codes:list_all) {
					String[] codelength = getSelectCode(codes);
					zhushu = zhushu + mulSelectCode(codelength);
				}
			}else if(wanfa.equals("535")){
				//C(n,6)
				List<List<String>> list_cn6 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 6);
				
				List<List<String>> list_all = new ArrayList<List<String>>();
				
				//C(6,4)
				for(List<String> list:list_cn6) {
					list_all.addAll(getBetcodeCollection(list, 4));
				}
				//C(6,3)
				for(List<String> list:list_cn6) {
					list_all.addAll(getBetcodeCollection(list, 3));
				}
				//C(6,2)
				for(List<String> list:list_cn6) {
					list_all.addAll(getBetcodeCollection(list, 2));
				}
				
				for(List<String> codes:list_all) {
					String[] codelength = getSelectCode(codes);
					zhushu = zhushu + mulSelectCode(codelength);
				}
			}else if(wanfa.equals("536")){
				//C(n,6)
				List<List<String>> list_cn6 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 6);
				
				List<List<String>> list_all = new ArrayList<List<String>>();
				
				//C(6,6)
				list_all.addAll(list_cn6);
	
				//C(6,5)
				for(List<String> list:list_cn6) {
					list_all.addAll(getBetcodeCollection(list, 5));
				}
				
				//C(6,4)
				for(List<String> list:list_cn6) {
					list_all.addAll(getBetcodeCollection(list, 4));
				}
				//C(6,3)
				for(List<String> list:list_cn6) {
					list_all.addAll(getBetcodeCollection(list, 3));
				}
				//C(6,2)
				for(List<String> list:list_cn6) {
					list_all.addAll(getBetcodeCollection(list, 2));
				}
				
				for(List<String> codes:list_all) {
					String[] codelength = getSelectCode(codes);
					zhushu = zhushu + mulSelectCode(codelength);
				}
			}else if(wanfa.equals("537")){
	
				//C(n,7)
				List<List<String>> list_cn7 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 7);
				
				List<List<String>> list_all = new ArrayList<List<String>>();
				
				//C(7,7)
				list_all.addAll(list_cn7);
				//C(7,6)
				for(List<String> list:list_cn7) {
					list_all.addAll(getBetcodeCollection(list, 6));
				}
				//C(7,5)
				for(List<String> list:list_cn7) {
					list_all.addAll(getBetcodeCollection(list, 5));
				}
				//C(7,4)
				for(List<String> list:list_cn7) {
					list_all.addAll(getBetcodeCollection(list, 4));
				}
				//C(7,3)
				for(List<String> list:list_cn7) {
					list_all.addAll(getBetcodeCollection(list, 3));
				}
				//C(7,2)
				for(List<String> list:list_cn7) {
					list_all.addAll(getBetcodeCollection(list, 2));
				}
				
				for(List<String> codes:list_all) {
					String[] codelength = getSelectCode(codes);
					zhushu = zhushu + mulSelectCode(codelength);
				}
			}else if(wanfa.equals("538")){
				//C(n,8)
				List<List<String>> list_cn8 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 8);
				
				List<List<String>> list_all = new ArrayList<List<String>>();
				
				//C(8,8)
				list_all.addAll(list_cn8);
				//C(8,7)
				for(List<String> list:list_cn8) {
					list_all.addAll(getBetcodeCollection(list, 7));
				}
				//C(8,6)
				for(List<String> list:list_cn8) {
					list_all.addAll(getBetcodeCollection(list, 6));
				}
				//C(8,5)
				for(List<String> list:list_cn8) {
					list_all.addAll(getBetcodeCollection(list, 5));
				}
				//C(8,4)
				for(List<String> list:list_cn8) {
					list_all.addAll(getBetcodeCollection(list, 4));
				}
				//C(8,3)
				for(List<String> list:list_cn8) {
					list_all.addAll(getBetcodeCollection(list, 3));
				}
				//C(8,2)
				for(List<String> list:list_cn8) {
					list_all.addAll(getBetcodeCollection(list, 2));
				}
				
				for(List<String> codes:list_all) {
					String[] codelength = getSelectCode(codes);
					zhushu = zhushu + mulSelectCode(codelength);
				}
			}else if(wanfa.equals("539")){
				//C(n,4)
				List<List<String>> list_cn4 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 4);
				
				//C(4,3)
				List<List<String>> list_all = new ArrayList<List<String>>();
				for(List<String> list:list_cn4) {
					list_all.addAll(getBetcodeCollection(list, 3));
				}
				
				for(List<String> codes:list_all) {
					String[] codelength = getSelectCode(codes);
					zhushu = zhushu + mulSelectCode(codelength);
				}
			}else if(wanfa.equals("540")){
				//C(n,4)
				List<List<String>> list_cn4 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 4);
				
				List<List<String>> list_all = new ArrayList<List<String>>();
				
				//C(4,4)
				list_all.addAll(list_cn4);
				//C(4,3)
				for(List<String> list:list_cn4) {
					list_all.addAll(getBetcodeCollection(list, 3));
				}
				for(List<String> codes:list_all) {
					String[] codelength = getSelectCode(codes);
					zhushu = zhushu + mulSelectCode(codelength);
				}
			}else if(wanfa.equals("541")){
				//C(n,5)
				List<List<String>> list_cn5 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 5);
				
				List<List<String>> list_all = new ArrayList<List<String>>();
				
				//C(5,5)
				list_all.addAll(list_cn5);
				//C(5,4)
				for(List<String> list:list_cn5) {
					list_all.addAll(getBetcodeCollection(list, 4));
				}
				//C(5,3)
				for(List<String> list:list_cn5) {
					list_all.addAll(getBetcodeCollection(list, 3));
				}
				
				for(List<String> codes:list_all) {
					String[] codelength = getSelectCode(codes);
					zhushu = zhushu + mulSelectCode(codelength);
				}
			}else if(wanfa.equals("542")){
				//C(n,6)
				List<List<String>> list_cn6 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 6);
				
				List<List<String>> list_all = new ArrayList<List<String>>();
				
				//C(6,3)
				for(List<String> list:list_cn6) {
					list_all.addAll(getBetcodeCollection(list, 3));
				}
				
				for(List<String> codes:list_all) {
					String[] codelength = getSelectCode(codes);
					zhushu = zhushu + mulSelectCode(codelength);
				}
			}else if(wanfa.equals("543")){
				//C(n,6)
				List<List<String>> list_cn6 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 6);
				
				List<List<String>> list_all = new ArrayList<List<String>>();
				
				//C(6,6)
				list_all.addAll(list_cn6);
				//C(6,5)
				for(List<String> list:list_cn6) {
					list_all.addAll(getBetcodeCollection(list, 5));
				}
				//C(6,4)
				for(List<String> list:list_cn6) {
					list_all.addAll(getBetcodeCollection(list, 4));
				}
				//C(6,3)
				for(List<String> list:list_cn6) {
					list_all.addAll(getBetcodeCollection(list, 3));
				}
				
				for(List<String> codes:list_all) {
					String[] codelength = getSelectCode(codes);
					zhushu = zhushu + mulSelectCode(codelength);
				}
			}else if(wanfa.equals("544")){
				//C(n,5)
				List<List<String>> list_cn5 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 5);
				
				List<List<String>> list_all = new ArrayList<List<String>>();
				
				//C(5,4)
				for(List<String> list:list_cn5) {
					list_all.addAll(getBetcodeCollection(list, 4));
				}
				
				for(List<String> codes:list_all) {
					String[] codelength = getSelectCode(codes);
					zhushu = zhushu + mulSelectCode(codelength);
				}
			}else if(wanfa.equals("545")){
				//C(n,5)
				List<List<String>> list_cn5 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 5);
				
				List<List<String>> list_all = new ArrayList<List<String>>();
				
				//C(5,5)
				list_all.addAll(list_cn5);
				//C(5,4)
				for(List<String> list:list_cn5) {
					list_all.addAll(getBetcodeCollection(list, 4));
				}
				
				for(List<String> codes:list_all) {
					String[] codelength = getSelectCode(codes);
					zhushu = zhushu + mulSelectCode(codelength);
				}
			}else if(wanfa.equals("546")){
				//C(n,6)
				List<List<String>> list_cn6 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 6);
				
				List<List<String>> list_all = new ArrayList<List<String>>();
				
				//C(6,6)
				list_all.addAll(list_cn6);
				//C(6,5)
				for(List<String> list:list_cn6) {
					list_all.addAll(getBetcodeCollection(list, 5));
				}
				//C(6,4)
				for(List<String> list:list_cn6) {
					list_all.addAll(getBetcodeCollection(list, 4));
				}
				for(List<String> codes:list_all) {
					String[] codelength = getSelectCode(codes);
					zhushu = zhushu + mulSelectCode(codelength);
				}
			}else if(wanfa.equals("547")){
				//C(n,7)
				List<List<String>> list_cn7 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 7);
				
				List<List<String>> list_all = new ArrayList<List<String>>();
				
				//C(7,4)
				for(List<String> list:list_cn7) {
					list_all.addAll(getBetcodeCollection(list, 4));
				}
				
				for(List<String> codes:list_all) {
					String[] codelength = getSelectCode(codes);
					zhushu = zhushu + mulSelectCode(codelength);
				}
			}else if(wanfa.equals("548")){
				//C(n,8)
				List<List<String>> list_cn8 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 8);
				
				List<List<String>> list_all = new ArrayList<List<String>>();
				
				//C(8,4)
				for(List<String> list:list_cn8) {
					list_all.addAll(getBetcodeCollection(list, 4));
				}
				
				for(List<String> codes:list_all) {
					String[] codelength = getSelectCode(codes);
					zhushu = zhushu + mulSelectCode(codelength);
				}
			}else if(wanfa.equals("549")){
				//C(n,6)
				List<List<String>> list_cn6 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 6);
				
				List<List<String>> list_all = new ArrayList<List<String>>();
				
				//C(6,5)
				for(List<String> list:list_cn6) {
					list_all.addAll(getBetcodeCollection(list, 5));
				}
				
				for(List<String> codes:list_all) {
					String[] codelength = getSelectCode(codes);
					zhushu = zhushu + mulSelectCode(codelength);
				}
			}else if(wanfa.equals("550")){
				//C(n,6)
				List<List<String>> list_cn6 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 6);
				
				List<List<String>> list_all = new ArrayList<List<String>>();
				//C(6,6)
				list_all.addAll(list_cn6);
				
				//C(6,5)
				for(List<String> list:list_cn6) {
					list_all.addAll(getBetcodeCollection(list, 5));
				}
				
				for(List<String> codes:list_all) {
					String[] codelength = getSelectCode(codes);
					zhushu = zhushu + mulSelectCode(codelength);
				}
			}else if(wanfa.equals("551")){
				//C(n,7)
				List<List<String>> list_cn7 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 7);
				
				List<List<String>> list_all = new ArrayList<List<String>>();
				
				//C(7,5)
				for(List<String> list:list_cn7) {
					list_all.addAll(getBetcodeCollection(list, 5));
				}
				
				for(List<String> codes:list_all) {
					String[] codelength = getSelectCode(codes);
					zhushu = zhushu + mulSelectCode(codelength);
				}
			}else if(wanfa.equals("552")){
				//C(n,8)
				List<List<String>> list_cn8 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 8);
				
				List<List<String>> list_all = new ArrayList<List<String>>();
				
				//C(8,5)
				for(List<String> list:list_cn8) {
					list_all.addAll(getBetcodeCollection(list, 5));
				}
				
				for(List<String> codes:list_all) {
					String[] codelength = getSelectCode(codes);
					zhushu = zhushu + mulSelectCode(codelength);
				}
			}else if(wanfa.equals("553")){
				//C(n,7)
				List<List<String>> list_cn7 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 7);
				
				List<List<String>> list_all = new ArrayList<List<String>>();
				
				//C(7,6)
				for(List<String> list:list_cn7) {
					list_all.addAll(getBetcodeCollection(list, 6));
				}
				
				for(List<String> codes:list_all) {
					String[] codelength = getSelectCode(codes);
					zhushu = zhushu + mulSelectCode(codelength);
				}
			}else if(wanfa.equals("554")){
				//C(n,7)
				List<List<String>> list_cn7 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 7);
				
				List<List<String>> list_all = new ArrayList<List<String>>();
				
				//C(7,7)
				list_all.addAll(list_cn7);
				
				//C(7,6)
				for(List<String> list:list_cn7) {
					list_all.addAll(getBetcodeCollection(list, 6));
				}
				
				for(List<String> codes:list_all) {
					String[] codelength = getSelectCode(codes);
					zhushu = zhushu + mulSelectCode(codelength);
				}
			}else if(wanfa.equals("555")){
				//C(n,8)
				List<List<String>> list_cn8 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 8);
				
				List<List<String>> list_all = new ArrayList<List<String>>();
				
				//C(8,6)
				for(List<String> list:list_cn8) {
					list_all.addAll(getBetcodeCollection(list, 6));
				}
				
				for(List<String> codes:list_all) {
					String[] codelength = getSelectCode(codes);
					zhushu = zhushu + mulSelectCode(codelength);
				}
			}else if(wanfa.equals("556")){
				//C(n,8)
				List<List<String>> list_cn8 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 8);
				
				List<List<String>> list_all = new ArrayList<List<String>>();
				
				//C(8,7)
				for(List<String> list:list_cn8) {
					list_all.addAll(getBetcodeCollection(list, 7));
				}
				
				for(List<String> codes:list_all) {
					String[] codelength = getSelectCode(codes);
					zhushu = zhushu + mulSelectCode(codelength);
				}
			}else if(wanfa.equals("557")){
				//C(n,8)
				List<List<String>> list_cn8 = getBetcodeCollection(Arrays.asList(newBetcode.split("\\^")), 8);
				
				List<List<String>> list_all = new ArrayList<List<String>>();
				
				//C(8,8)
				list_all.addAll(list_cn8);
				//C(8,7)
				for(List<String> list:list_cn8) {
					list_all.addAll(getBetcodeCollection(list, 7));
				}
				
				for(List<String> codes:list_all) {
					String[] codelength = getSelectCode(codes);
					zhushu = zhushu + mulSelectCode(codelength);
				}
			}		
	}
    	return (int) zhushu;
	}
	
	/**
	 * 
	 * @param wanfa 玩法
	 * @return resultNum（选择的组合数，比如2串1，此处为2）
	 */
	
	public static int returnResultNum(String wanfa){
		if(wanfa.equals("502")&&wanfa.equals("509")){
			return 2;
		}else if(wanfa.equals("503")&&wanfa.equals("510")&&wanfa.equals("511")&&wanfa.equals("526")&&wanfa.equals("527")){
			return 3;
		}else if(wanfa.equals("504")&&wanfa.equals("512")&&wanfa.equals("513")&&wanfa.equals("514")&&wanfa.equals("528")&&wanfa.equals("529")
				&&wanfa.equals("539")&&wanfa.equals("540")){
			return 4;
		}else if(wanfa.equals("505")&&wanfa.equals("515")&&wanfa.equals("516")&&wanfa.equals("517")&&wanfa.equals("518")&&wanfa.equals("530")
				&&wanfa.equals("531")&&wanfa.equals("532")&&wanfa.equals("541")&&wanfa.equals("544")&&wanfa.equals("545")){
			return 5;
		}else if(wanfa.equals("506")&&wanfa.equals("519")&&wanfa.equals("520")&&wanfa.equals("521")&&wanfa.equals("522")&&wanfa.equals("523")
				&&wanfa.equals("533")&&wanfa.equals("534")&&wanfa.equals("535")&&wanfa.equals("536")&&wanfa.equals("542")&&wanfa.equals("543")
				&&wanfa.equals("546")&&wanfa.equals("549")&&wanfa.equals("550")){
			return 6;
		}else if(wanfa.equals("507")&&wanfa.equals("524")&&wanfa.equals("537")&&wanfa.equals("547")&&wanfa.equals("551")&&wanfa.equals("553")
				&&wanfa.equals("554")){
			return 7;
		}else if(wanfa.equals("508")&&wanfa.equals("525")&&wanfa.equals("538")&&wanfa.equals("548")&&wanfa.equals("552")&&wanfa.equals("555")
				&&wanfa.equals("556")&&wanfa.equals("557")){
			return 8;
		}
		return 1;
	}
	
	/**
	 * 
	 * @param wanfa 玩法
	 * @return resultZhushu（选择的组合注数，比如2串1，此处为1）
	 */
	
	public static int returnResultZhushu(String wanfa){
		if(wanfa.equals("502")&&wanfa.equals("503")&&wanfa.equals("504")&&wanfa.equals("505")&&wanfa.equals("506")&&wanfa.equals("507")
				&&wanfa.equals("508")){
			return 1;
		}
		else if(wanfa.equals("509")&&wanfa.equals("526")){
			return 3;
		}
		else if(wanfa.equals("510")&&wanfa.equals("528")&&wanfa.equals("545")&&wanfa.equals("549")){
			return 6;
		}
		else if(wanfa.equals("511")&&wanfa.equals("550")&&wanfa.equals("553")){
			return 7;
		}
		else if(wanfa.equals("512")&&wanfa.equals("530")){
			return 10;
		}
		else if(wanfa.equals("513")){
			return 14;
		}
		else if(wanfa.equals("514")&&wanfa.equals("515")&&wanfa.equals("533")){
			return 15;
		}
		else if(wanfa.equals("516")){
			return 25;
		}
		else if(wanfa.equals("517")){
			return 30;
		}
		else if(wanfa.equals("518")){
			return 31;
		}
		else if(wanfa.equals("519")&&wanfa.equals("551")){
			return 21;
		}
		else if(wanfa.equals("520")){
			return 41;
		}
		else if(wanfa.equals("521")&&wanfa.equals("552")){
			return 56;
		}
		else if(wanfa.equals("522")){
			return 62;
		}
		else if(wanfa.equals("523")){
			return 63;
		}
		else if(wanfa.equals("524")){
			return 127;
		}
		else if(wanfa.equals("525")){
			return 255;
		}
		else if(wanfa.equals("527")&&wanfa.equals("539")){
			return 4;
		}
		else if(wanfa.equals("529")){
			return 11;
		}
		else if(wanfa.equals("531")&&wanfa.equals("542")){
			return 20;
		}
		else if(wanfa.equals("532")){
			return 26;
		}
		else if(wanfa.equals("534")){
			return 35;
		}
		else if(wanfa.equals("535")){
			return 50;
		}
		else if(wanfa.equals("536")){
			return 57;
		}
		else if(wanfa.equals("537")){
			return 120;
		}
		else if(wanfa.equals("538")){
			return 247;
		}
		else if(wanfa.equals("540")&&wanfa.equals("544")){
			return 5;
		}
		else if(wanfa.equals("541")){
			return 16;
		}
		else if(wanfa.equals("543")){
			return 42;
		}
		else if(wanfa.equals("546")){
			return 22;
		}
		else if(wanfa.equals("547")){
			return 35;
		}
		else if(wanfa.equals("548")){
			return 70;
		}
		else if(wanfa.equals("554")&&wanfa.equals("556")){
			return 8;
		}
		else if(wanfa.equals("555")){
			return 28;
		}
		else if(wanfa.equals("557")){
			return 9;
		}
		return 1;
	}
	/**
	 * 组合计算公式
	 * @param m
	 * @param n
	 * @return  返回 C(n,m)
	 */
	public static long zuhe(int m,int n){
		long t_a = 0;
		t_a = (jiec(n).divide((jiec(n-m).multiply(jiec(m))))).longValue();
		return t_a;
	}
	
	/**
	 * 阶乘计算公式
	 * @param a 
	 * @return  返回a的阶乘   a!
	 */
	private static BigDecimal jiec(long a){
		BigDecimal t_a = new BigDecimal(1);
		for (int i = 1; i <= a; i++) {
			t_a = t_a.multiply(new BigDecimal(i));
		}
		return t_a;
	}
	
	
	/**
	 * C算法
	 * @param betcodes
	 * @param select
	 * @return
	 */
	protected static List<List<String>> getBetcodeCollection(List<String> betcodes,int select) {
		//初始化原始数据
		int[] a = new int[betcodes.size()];
		for (int i = 0; i < a.length; i++) {
			a[i] = i;
		}
		//接收数据
		int[] b = new int[select];
		
		List<int[]> list = new ArrayList<int[]>();
		
		//进行组合
		BaseMath.combine(a, a.length, select, b, select, list);
		
		//返回数据对象
		List<List<String>> reList = new ArrayList<List<String>>();
		for(int[] result:list) {
			List<String> codeList = new ArrayList<String>();
			for(int p:result) {
				codeList.add(betcodes.get(p));
			}
			reList.add(codeList);
		}
		
		return reList;
	}
	
	/**
	 * 把list转换为注码
	 * @param list
	 * @return
	 */
	protected String getSortBetCode(List<String> list) {
		List<Long> issues = new ArrayList<Long>();
		Map<Long,String> codesMap = new HashMap<Long,String>();
		long issue = 0;
		for(String code:list) {
			issue = Long.parseLong(code.split("\\|")[0]+code.split("\\|")[1]+code.split("\\|")[2]);
			issues.add(issue);
			codesMap.put(issue, code);
		}
		Collections.sort(issues);
		StringBuilder sb = new StringBuilder();
		for(long i:issues) {
			sb.append(codesMap.get(i)).append("^");
		}
		return sb.toString();
	}
	

	/**
	 * 选出一串注码(多场次注码联排)中除去时间场次信息的投注信息
	 * @param betcode
	 * @return
	 */
	protected static String[] getSelectCode(String betcode) {
		List<String> betcodes = Arrays.asList(betcode.split("\\^"));
		return getSelectCode(betcodes);
	}
	
	/**
	 * 选出注码List(每个list中的一个注码为一个场次的注码)中除去时间场次信息的投注信息
	 * @param betcodes
	 * @return
	 */
	protected static String[] getSelectCode(List<String> betcodes) {
		String[] codes = new String[betcodes.size()];
		for(int i = 0;i<betcodes.size();i++) {
			codes[i] = betcodes.get(i).split("\\|")[3];
		}
		return codes;
	}
	
	
	protected static long mulSelectCode(String[] selects) {
		long total = 1;
		for(String select:selects) {
			total = total * select.length();
		}
		return total;
	}
}
