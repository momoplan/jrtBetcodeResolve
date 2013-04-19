package com.jrt.betcodeResolve.lotnoBetcodeUtil;

import com.jrt.betcodeResolve.util.Constant;

/**
 * 排列五注码解析 获取金额、注数、玩法的类
 * @author 徐丽
 *
 */
public class PLWBetcodeUtil {
	/**
	 * 
	 * 		 排列五 直选单式注码解析算注数
	 * @param 
	 * 		betcode 注码
	 * 		 示例：1,2,3,5,6;4,5,6,7,8;2,4,5,6,9
	 * @param 
	 * 		tcTabNumber 注码之间的分隔符 示例中为";"
	 * @return 
	 *      注数 示例中为3注
	 *      
	 */
     public static int getPLWSimplexZhushu(String betcode,String tcTabNumber){
    	 //根据注码直接的分隔符tcTabNumber解析注码
    	 String arrCode[] = betcode.split("\\"+tcTabNumber);
    	 
    	 //算 注数并返回
    	 return arrCode.length ;

     }
     
     /**
 	 * 
 	 * 		 排列五直选单式注码解析算金额
 	 * @param 
 	 * 		betcode 注码
 	 * 		 示例：1,2,3,5,6;4,5,6,7,8;2,4,5,6,9
 	 * @param
 	 * 		multiple 倍数 为1倍
 	 * @param 
 	 * 		tcTabNumber 注码之间的分隔符 示例中为";"
 	 * @return 
 	 *      总金额 = 注数*倍数*单价(单张彩票的金额)
 	 *      示例中为6元
 	 *      
 	 */
      public static int getPLWSimplexMoney(String betcode,int multiple,String tcTabNumber){
     	 //调用单式算注数的方法得到注数
     	 int zhushu = getPLWSimplexZhushu(betcode, tcTabNumber);
     	 
     	 //算总金额 = 注数*倍数*单价(单张彩票的金额)
     	 return zhushu * multiple * Constant.LOTTERY_PRICE;
     	 
      }
      
      
  	/**
  	 * 
  	 *      排列五直选复式注码解析得注数
  	 * @param 
  	 * 		betcode 注码
  	 *      示例 注码:1,2,8-0,2,3-1,2,8-0,2,3-3,4,9
  	 * @param 
  	 * 		qhTab 万位、千位、百位、十位、个位之间的分隔符 示例中为";"
  	 * @param
  	 * 		sign 注码之间的分隔符 示例中为","
  	 * @return
  	 * 		注数 示例中为243注
  	 * 
  	 */
   	public static int getPLWDirectDoubleZhushu(String betcode,String qhTab,
   			String sign){
   		
   		//根据万位、千位、百位、十位、个位的分隔符qhTab分解百、十、个位
  		String arrCode[] = betcode.split("\\"+qhTab);
  		
  		//分别得到万位、千位、百位、十位、个位的注码数组
  		String millionCode[] = arrCode[0].split("\\"+sign);
  		String thousandsCode[] = arrCode[1].split("\\"+sign);
  		String hundredCode[] = arrCode[2].split("\\"+sign);
  		String tenCode[] = arrCode[3].split("\\"+sign);
  		String unitCode[] = arrCode[4].split("\\"+sign);
  		
  		//将万位、千位、百位、十位、个位的个数相乘就是注码的注数
  		int zhushu = millionCode.length*thousandsCode.length*hundredCode.length * tenCode.length * unitCode.length; 

  		return zhushu ;
   	}
   	
   	/**
	 * 
	 *      排列五直选复式注码解析得金额
	 * @param 
	 * 		betcode 注码
	 *      示例 注码:1,2,8-0,2,3-1,2,8-0,2,3-3,4,9
	 * @param 
	 * 		multiple 倍数 为1倍
	 * @param 
	 * 		qhTab 万位、千位、百位、十位、个位之间的分隔符 示例中为"-"
	 * @param
	 * 		sign 注码之间的分隔符 示例中为","
	 * @return
	 * 		总金额 = 注数*倍数*单价(单张彩票的金额)
	 *      示例中为486元
	 *      
	 */
	public static int getPLWDirectDoubleMoney(String betcode, int multiple,
			String qhTab, String sign) {
		
		//将万位、千位、百位、十位、个位的个数相乘就是注码的注数
		int zhushu = getPLWDirectDoubleZhushu(betcode, qhTab, sign); 
		
		//算总金额 = 注数*倍数*单价(单张彩票的金额)并返回
		return zhushu * multiple * Constant.LOTTERY_PRICE;
	}
}
