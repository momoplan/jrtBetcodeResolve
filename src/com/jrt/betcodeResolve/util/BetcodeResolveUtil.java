package com.jrt.betcodeResolve.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

/**
 * 
 * 	   工具类
 * @author   徐丽
 * 
 */
public class BetcodeResolveUtil {
//	private static Logger logger = Logger.getLogger(BetcodeResolveUtil.class); 
	/**
	 * 
	 * 		排序的方法
	 * @param
	 * 		 string 传入的要排序的数字
	 * @return 排序后的数  
	 * 
	 */
	public static String sortString(String string){
		StringBuffer buf = new StringBuffer();
		String[] str = new String[string.length() / 2];
		for(int i = 0; i < str.length; i++){
			str[i] = string.substring(i * 2, (i + 1) * 2);
		}
		int min;
		String temp;
		for(int i = 0; i < str.length; i++){
			min = i;
			for(int j = i + 1; j < str.length; j++){
				if(str[min].compareTo(str[j]) > 0){
					min = j;
				}
			}
			if(min != i){
				temp = str[i];
				str[i] = str[min];
				str[min] = temp;
			}
			buf.append(str[i]);
		}
		str = null;
		return buf.toString();
	}
	
	/**
	 * 
	 * 		根据得到的双色球的注码排序
	 * @param	str
	 * 		传入的注码
	 * @return
	 * 		排序完的注码   
	 * 
	 */
	public static String extractString(String str){
		String ssqWinbasecode=BetcodeResolveUtil.sortString(str);
		//对注码进行排序
		List<String> list = new ArrayList<String>();
		for(int i=0;i<ssqWinbasecode.length();i++)
		{
			list.add(ssqWinbasecode);
			Object[] o = list.toArray();
            Arrays.sort(o);
		}
		return ssqWinbasecode;
	}
	
	/**
	 * 
	 * 		补码（小于0的号码在其前面补"0"）
	 * @param 
	 * 		betcode 注码
	 * @param
	 * 		sign 注码之间分隔符
	 * @param
	 * 		separator 添加分隔符区分福彩和体彩
	 * @return
	 * 		返回实际注码
	 * 
	 */
	public static String complement(String betcode,String sign,String separator){
		String code="";
		if(betcode.equals("")){
			code="";
		}else{
			String arr[] = betcode.split("\\"+sign);
			for(int i=0;i<arr.length;i++){
				code += ((Integer.parseInt(arr[i]) < 10) ? "0" : "") + arr[i]+separator ;
			}
		}
		return code;
	}
	
	/**
	 * 
	 * 	  重载complement方法   补码（小于0的号码在其前面补"0"）
	 * @param 
	 * 		betcode 注码
	 * @param
	 * 		playName 玩法（福彩3D组选按升序排序）
	 * @param 
	 * 		sign 注码之间分隔符
	 * @param
	 * 		separator 添加分隔符区分福彩和体彩
	 * @return 
	 * 		返回实际注码
	 * 
	 */
	public static String complement(String betcode,String playName,String sign,String separator){
		String code="";
		if(betcode.equals("")){
			code="";
		}else{
			String arr[] = betcode.split("\\"+sign);
			for(int i=0;i<arr.length;i++){
				code += ((Integer.parseInt(arr[i]) < 10) ? "0" : "") + arr[i]+separator ;
			}
			//福彩3D组选（包括组三和组六）
			if(playName.equals("01")||playName.equals("02")){
				code = BetcodeResolveUtil.extractString(code);
			}
		}
		return code;
	}
	
	/**
	 * 注码去“0”的方法
	 * @param betcode 注码
	 * @param sign 各个注码之间的分隔符
	 * @return 去“0”以后的注码串
	 */
	public static String outZero(String betcode,String sign){
		  String codes[] = betcode.split("\\"+sign);
		  String code= "";
		  for(int i=0;i<codes.length;i++){
		   code += codes[i].replaceFirst("^0*", "")+sign;   
		  }
		  code = code.substring(0, code.length()-1);
		  return code;
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
	 *
	 * 获取双色球注数
	 * @param redBallCount 红球的个数
	 * @param blueBallCount 蓝球的个数
	 * @return 双色复式注数
	 * 
	 */
	public static int getDoubleBallNumber(int redBallCount, int blueBallCount){//得到双色球复式的注数
		long C_r = zuhe(6,redBallCount);
		long C_b = zuhe(1,blueBallCount);
		long doubleBallNumber = (C_r * C_b);
		return (int)doubleBallNumber;
	}
	
	/**
	 * 
	 *      双色球胆拖玩法排列组合的方法
	 * @param 
	 * 		redDanmaArrayLength 红球胆码的个数
	 * @param 
	 * 		redTuomaArrayLength 红球拖码的个数
	 * @param 
	 * 		blueArrayLength 蓝球的个数
	 * @return
	 * 		排列组合算注数
	 * 
	 */
	public static int getDoubleBallDantuoNumber(int redDanmaArrayLength, int redTuomaArrayLength, int blueArrayLength) {
		int zhushu = (int)(zuhe(6-redDanmaArrayLength, redTuomaArrayLength) * zuhe(1, blueArrayLength));
		return zhushu;
	}
	
	/**
	 *
	 * 获取大乐透注数
	 * @param redBallCount 前区注码的个数
	 * @param blueBallCount 后区注码的个数
	 * @return 大乐透的注数   
	 * 
	 */
	public static int getDaleTouNumber(int redBallCount, int blueBallCount){
		long C_r = zuhe(5,redBallCount);
		long C_b = zuhe(2,blueBallCount);
		long doubleBallNumber = (C_r * C_b);
		return (int)doubleBallNumber;
	}
	
	/**
	 *
	 * 获取大乐透胆拖注数
	 *@param redBallD 前区胆码的个数
	 *@param redBallT 前区拖码的个数
	 *@param blueBallD 后区胆码的个数
	 *@param blueBallT 后区拖码的个数
	 *@return 大乐透胆拖的注数  
	 * 
	 */
	public static int getDaLeTouNumberDT(int redBallD,int redBallT, int blueBallD,int blueBallT){
		long C_r = zuhe(5-redBallD,redBallT);
		long C_b = zuhe(2-blueBallD,blueBallT);
		long doubleBallNumber = (C_r * C_b);
		return (int)doubleBallNumber;
	}
	
	/**
	 * 取得大乐透12选2式的注数
	 * @param num
	 * @return 注数
	 */
	public static int getDaletouSXNumber(int num) {
		return (int)zuhe(2,num);
	}
	
	/**
	 * 取得直选和值的注码(包括福彩3D和排列三)
	 * @param num
	 * @return 注数
	 */
	public static int getDirectHeZhiNumber(int num) {
		int zxHHH[] = {1,3,6,10,15,21,28,36,45,55,63,69,73,75,75,73,69,63,55,45,36,28,21,15,10,6,3,1};
		return zxHHH[num];
	}
	
	/**
	 * 取得组3和值的注码(包括福彩3D和排列三)
	 * @param num
	 * @return 注数
	 */
	public static int getGroup3HeZhiNumber(int num) {
		int z3HHH[] = {1,2,1,3,3,3,4,5,4,5,5,4,5,5,4,5,5,4,5,4,3,3,3,1,2,1};
		return z3HHH[num-1];
	}
	/**
	 * 取得组6和值的注码(包括福彩3D和排列三)
	 * @param num 和值数
	 * @return 注数
	 */
	public static int getGroup6HeZhiNumber(int num) {
		int z6HHH[] = {0,0,1,1,2,3,4,5,7,8,9,10,10,10,10,9,8,7,5,4,3,2,1,1,0,0};
		return z6HHH[num-1];
	}
	
	/**
	 * 取得单选单复式(3D直选包号)的注数
	 * @param n
	 * @param m
	 * @return 注数
	 */
	public static long get3DSingleSelectSingleMultiple(int n, int m) {
		long t_a = 0;
		t_a = (jiec(m).divide(jiec(m-n))).longValue();
		return t_a;
	}
	
	/**
	 * 获取3D的组3复式的注数
	 * @param arrayLength   用户输入的注码数组的长度
	 * @return              注数
	 */
	public static int getGroup3MultipleNumber(int arrayLength) {
		int zhushu = (int)zuhe(2,arrayLength)*2;
		return zhushu;
	}
	/**
	 * 获取3D的组6复式的注数
	 * @param arrayLength   用户输入的注码数组的长度
	 * @return              注数
	 */
	public static int getGroup6MultipleNumber(int arrayLength) {
		int zhushu = (int)zuhe(3,arrayLength);
		return zhushu;
	}
	
	/**
	 * 取得3D胆拖复式注数
	 * @param danmaArrayLength
	 * @param tuomaArrayLength
	 * @return 注数
	 */
	public static int getDanmaMultiple3DNumber(int danmaArrayLength, int tuomaArrayLength) {
		int zhushu = (int)zuhe(3-danmaArrayLength, tuomaArrayLength)*6;
		return zhushu;
	}
	
	/**
	 * 取得七乐彩复式的注数
	 * @param num
	 * @return 注数
	 */
	public static int getQilecaiNumber(int num) {
		return (int)zuhe(7,num);
	}
	
	/**
	 * 获得七乐彩胆拖注数
	 * @param danmaLength
	 * @param tuomaLength
	 * @return 注数
	 */
	public static int getQilecaiDantuoNumber(int danmaLength, int tuomaLength) {
		int zhushu = (int)zuhe(7-danmaLength, tuomaLength);
		return zhushu;
	}
	/**
	 * 获得22选5胆拖注数
	 * @param danmaLength
	 * @param tuomaLength
	 * @return 注数
	 */
	public static int geteexwDantuoNumber(int danmaLength, int tuomaLength) {
		int zhushu = (int)zuhe(5-danmaLength, tuomaLength);
		return zhushu;
	}
	/**
	 * 取得22选5复式的注数
	 * @param num
	 * @return 注数
	 */
	public static int geteexwNumber(int num) {
		return (int)zuhe(5,num);
	}
	//-----------------------时时彩算法--------------------
	/**
	 * 二星直选和值计算注数
	 * @param num 和值数
	 * @return 注数 
	 */
	public static long getSSCRXHezhi(int num) {
		int[] doubleGroup = {1,2,3,4,5,6,7,8,9,10,9,8,7,6,5,4,3,2,1};
		return doubleGroup[num];
	}
	
	/**
	 * 二星组选和值计算注数
	 * @param num 和值数
	 * @return 注数 
	 */
	public static long getSSCRXZXHezhi(int num) {
		int[] doubleGroup = {1,1,2,2,3,3,4,4,5,5,5,4,4,3,3,2,2,1,1};
		return doubleGroup[num];
	}
	
	/**
	 * 三星和值计算注数
	 * @param num 和值数
	 * @return 注数
	 */
	public static long getSSCSXHezhi(int num) {
		int[] threeGroup = {1,3,6,10,15,21,28,36,45,55,63,69,73,75,75,73,69,63,55,45,36,28,21,15,10,6,3,1};
		return threeGroup[num];
	} 
	
	
	/**
	 * 两组之间获取重复个数
	 * @param gw 第一位数组
	 * @param sw 第二位数组
	 * @return 返回两组数组之间总个数
	 */
	public static int chongfuCount (String gw[],String sw[]){
		int cf_gs =0;
		//循环判断两组之间是否有重复的有重复的让其加1
		for(int s=0;s<sw.length;s++){
			for(int g=0;g<gw.length;g++){
				if(gw[g].trim().equals(sw[s].trim())){		
					cf_gs++;
				}
			}
		}
		return cf_gs;
	}
	
	/**
	 * 合并两组之间的注码
	 * @param mw 第一组数组
	 * @param sw 第二组数组
	 * @return 返回两组之间相同的注码
	 */
	public static String getNewCodes(String mw[],String sw[]){
		String sm = "";
		//获取末位、十位重复注码
		for(int i=0;i<mw.length;i++){
			for(int s=0;s<sw.length;s++){
				if(mw[i].trim().equals(sw[s].trim())){
					sm+=mw[i]+",";
				}
			}
	    }
		return sm;
	}
	
	/**
	 * 计算从n中选出k个数的组合数
	 * @param n 样本总数
	 * @param k 选取样本数
	 * @return 组合数
	 */
	public static long nchoosek(int n,int k) {
		//验证传入参数，n不能小于等于0，k不能小于0，n不能小于k；k=0的时候，规定组合数为1
		if(n <= 0 || k < 0 || n < k) {
			return -1;
		}
		if(k == 0 || n==k) {
			return 1;
		}
		//按照组合数性质，在k大于n/2时，计算从n中选出n-k的值，减少计算量
		if(k > n/2) {
			k = n - k;
		}		
		//通过组合数公式求组合数
		long result = multiplyByStep(n, n - k + 1) / multiplyByStep(k,1);
		
		return result;
	}
	
	/**
	 * 计算从m到n,以1为步长的成绩(m:1:n),m、n为正整数
	 * @param m 正整数
	 * @param n 正整数
	 * @return 步长为1，m到n的乘机，-1表示传入的mn为负数
	 */
	public static long multiplyByStep(int m,int n) {
		//数据验证，规定m和n不能小于0
		if(m < 0 || n < 0) {
			return -1;
		}
		
		//定义默认返回值
		long result = 1l;
		
		//m大于等于n，从n以1为步长乘到m;m小于n，从m以1为步长乘到n
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
	
	
//--------------------------------新足彩算法-----------------------------------------

	/**
	 * 幂运算
	 * 
	 * @param d
	 *            底数
	 * @param z
	 *            指数
	 * @return 计算结果
	 */
	public static long exp(long d, long z) {
		long result = 1L;
		for (int i = 0; i < z; i++) {
			result = result * d;
		}
		return result;
	}

	/**
	 * 足彩胆拖计算注数
	 * 
	 * @param d2
	 *            胆码中双选的个数
	 * @param d3
	 *            胆码中三选的个数
	 * @param t1
	 *            拖码中单选的个数
	 * @param t2
	 *            拖码中双选的个数
	 * @param t3
	 *            拖码中三选的个数
	 * @param choose
	 *            从拖码选择来构成整个投注号码的个数（9-胆码个数）
	 * @return 注数
	 */
	public static long dantuo(int d2, int d3, int t1, int t2, int t3, int choose) {
		long m = exp(2, d2);
		long n = exp(3, d3);
		long result = 0;
		for (int i = 0; i <= t1; i++) {
			for (int j = 0; j <= t2; j++) {
				int k = choose - i - j;
				if(k <= t3 && k >= 0) {
					long nk1, nk2, nk3, exp1, exp2;
					nk1 = (t1 == 0 ? 1 : nchoosek(t1, i));//
					nk2 = (t2 == 0 ? 1 : nchoosek(t2, j));
					nk3 = (t3 == 0 ? 1 : nchoosek(t3, k));
					exp1 = exp(2, j);
					exp2 = exp(3, k);
					result = result + nk1 * nk2 * nk3 * exp1 * exp2;
					System.out.println("i=" + i + ",j=" + j + ",k=" + k);//拖码单选、双选、三选的个数
				}
				
			}
		}
		if (m > 0)
			result = result * m;
		if (n > 0)
			result = result * n;

		return result;
	}
	
	
	/**
	 * 	将输入的注码转换成数组(江西11选5)
	 * @param strArray 输入参数:注码数组,格式为字符串
	 * @return        输出参数:注码数组
	 */
	@SuppressWarnings("unchecked")
	public static Vector getStringArrayFromString(String strArray) {
		Vector v = new Vector();
		
		int l = strArray.length();
		int h = l/2;
	
		int n = 0;
		for (int i = 0; i < h; i++) {
			String ss = strArray.substring(n, n+2);
			n = n + 2;
			v.add(ss);
		}
		return v;
	
	} 
		
	
	//-----------------------足彩算法--------------------
	/**
	 * 
	 *      得到足彩的注数
	 * @param codes
	 * 		注码的集合
	 * @return   
	 * 		返回注码的注数 
	 * 
	 */
	public static int getZCDoubleNumber(String codes[]){
		int zhushu = 1;
		//分析一场注码的个数
    	for(int i=0;i<codes.length;i++){
    		//注码的长度
    		int codeLength = codes[i].length();
    		
    		//根据注码的长度算注数
    		if(codeLength == 2){
    			zhushu = zhushu*2;
    		}else if(codeLength == 3){
    			zhushu = zhushu*3;
    		}else if(codeLength == 4){
    			zhushu = zhushu*4;
    		}
    	}
    	return zhushu;
	}
	/**
	 *  
	 *      算足彩任九场胆拖的注数
	 * @param d
	 * 		胆码的集合
	 * @param t
	 * 		拖码的集合
	 * @param l
	 * 		数字，要选取的长度
	 * @return  
	 * 		足彩任九场 胆拖的注数
	 *
	 */
	public static int getRJCCount(List<String> d,List<String> t ,int l){
		int dl = d.size();//胆码的大小
		int tl = t.size();//拖码的大小
		System.out.println("dl="+dl+";tl="+tl);
		
		//判断胆码的个数是否大于等于场次9或者胆码的个数+拖码的个数是否小于场次
		if(dl >= l || dl + tl < l) {
			return 0;
		}
		
		//调用组合集合的方法
		List<List<String>> a = listCombinate(t, l - dl);
		System.out.println("method:getRJCCount;调用组合集合的方法算得a.size="+a.size());
		
		//算出当前有多少集合
		int c = 0;
		String[] at = null;
		
		//循环
		for(int i = 0; i < a.size(); i++) {
			StringBuilder sb = new StringBuilder();
			if(dl > 0) {
				List<String> temp = a.get(i);
				for(String s : temp) {
					sb.append(s).append(",");
				}
				for(String s : d) {
					sb.append(s).append(",");
				}
				at = sb.toString().split(",");
			} else {
				List<String> temp = a.get(0);
				for(String s : temp) {
					sb.append(s).append(",");
				}
				at = sb.toString().split(",");
			}
			c += getLiancheng(at, at.length-1);
		}
		System.out.println("method:getRJCCount;注数c="+c);
		return c;
	}
	
	/**
	 *
	 * 		遍历组合项   从拖码的个数n中选择num个集合
	 * @param arr 
	 * 		拖码的集合
	 * @param num 
	 * 		选取的顶数 （场次-胆码集合的个数）
	 * @return
	 * 		遍历组合
	 * 
	 */
	public static List<List<String>> listCombinate(List<String> arr, int num) {
		List<List<String>> result = new ArrayList<List<String>>();
		System.out.println("method:listCombinate;拖码的集合arr="+arr+";num="+num);
		
		//得到传入的拖码集合的大小
		int n = arr.size();
		if (num > n) {
			throw new RuntimeException("数组集合arr中只有" + n + "个元素。");
		}
		
		// 初始化
		int[] bs = new int[n];
		for (int i = 0; i < n; i++) {
			bs[i] = 0;
		}
		for (int i = 0; i < num; i++) {
			bs[i] = 1;
		}
		boolean flag = true;
		boolean tempFlag = false;
		int pos = 0;
		int sum = 0;
		// 首先找到第一个10组合，然后变成01，同时将左边所有的1移动到数组的最左边
		do {
			sum = 0;
			pos = 0;
			tempFlag = true;
			result.add(listAdd(bs, arr));

			for (int i = 0; i < n - 1; i++) {
				if (bs[i] == 1 && bs[i + 1] == 0) {
					bs[i] = 0;
					bs[i + 1] = 1;
					pos = i;
					break;
				}
			}
			
			// 将左边的1全部移动到数组的最左边
			for (int i = 0; i < pos; i++) {
				if (bs[i] == 1) {
					sum++;
				}
			}
			for (int i = 0; i < pos; i++) {
				if (i < sum) {
					bs[i] = 1;
				} else {
					bs[i] = 0;
				}
			}

			// 检查是否所有的1都移动到了最右边
			for (int i = n - num; i < n; i++) {
				if (bs[i] == 0) {
					tempFlag = false;
					break;
				}
			}
			if (tempFlag == false) {
				flag = true;
			} else {
				flag = false;
			}

		} while (flag);
		
		//当拖码的个数不等于（场次-胆码集合的个数）添加集合
		if(n != num) {
			result.add(listAdd(bs, arr));
		}
		return result;
	}
	
	/**
	 * 
	 *      增加新集合
	 * @param bs
	 * 		转换后的数组集合
	 * @param arr
	 * 		传入的数组
	 * @return
	 * 		返回新集合内容    
	 * 
	 */
	private static List<String> listAdd(int[] bs, List<String> arr) {
		List<String> list = new ArrayList<String>();
		int pos = 0;
		for (int i = 0; i < bs.length; i++) {
			if (bs[i] == 1) {
				list.add(arr.get(i));//增加下一个数组
				pos++;
			}
		}
		return list;
	}
	
	/**
	 * 
	 * 		用递归计算一个数组各元素（string型）的长度连乘积
	 * @param a
	 *       数组
	 * @param i
	 * 		数据的长度
	 * @return 
	 * 		 长度连成积  
	 * 
	 */
	public static int getLiancheng(String[] a, int i) {	
		int n = 1;
		for(; i >= 0; i--) {
			if(i<0){return n;}
			n *= a[i].toString().length();
		}
		return n;
	}
	
}
