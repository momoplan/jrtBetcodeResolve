package com.jrt.betcodeResolve.lotnoBetcodeUtil;

import com.jrt.betcodeResolve.util.Constant;

/**
 * 七星彩注码解析 获取金额、注数、玩法的类
 * @author 徐丽
 *
 */
public class QXCBetcodeUtil {
	/**
	 * 
	 * 		 七星彩 直选单式注码解析算注数
	 * @param 
	 * 		betcode 注码
	 * 		 示例：1,2,3,5,6,3,4;4,5,6,7,8,5,6;2,4,5,6,9,5,6
	 * @param 
	 * 		tcTabNumber 注码之间的分隔符 示例中为";"
	 * @return 
	 *      注数 示例中为3注
	 *      
	 */
     public static int getQXCSimplexZhushu(String betcode,String tcTabNumber){
    	 //根据注码直接的分隔符tcTabNumber解析注码
    	 String arrCode[] = betcode.split("\\"+tcTabNumber);
    	 
    	 //算 注数并返回
    	 return arrCode.length ;

     }
     
     /**
  	 * 
  	 * 		 七星彩直选单式注码解析算金额
  	 * @param 
  	 * 		betcode 注码
  	 * 		 示例：1,2,3,5,6,3,4;4,5,6,7,8,5,6;2,4,5,6,9,5,6
  	 * @param
  	 * 		multiple 倍数 为1倍
  	 * @param 
  	 * 		tcTabNumber 注码之间的分隔符 示例中为";"
  	 * @return 
  	 *      总金额 = 注数*倍数*单价(单张彩票的金额)
  	 *      示例中为6元
  	 *      
  	 */
      public static int getQXCSimplexMoney(String betcode,int multiple,String tcTabNumber){
      	 //调用单式算注数的方法得到注数
      	 int zhushu = getQXCSimplexZhushu(betcode, tcTabNumber);
      	 
      	 //算总金额 = 注数*倍数*单价(单张彩票的金额)
      	 return zhushu * multiple * Constant.LOTTERY_PRICE;
      	 
       }
      
  		/**
    	 * 
    	 *      七星彩直选复式注码解析得注数
    	 * @param 
    	 * 		betcode 注码
    	 *      示例 注码:1,2-3-2,1,2-5,0-3-7,8,2,4-6,8,3,4,9,0
    	 * @param 
    	 * 		qhTab 各个位数之间的分隔符 示例中为";"
    	 * @param
    	 * 		sign 注码之间的分隔符 示例中为","
    	 * @return
    	 * 		注数 示例中为243注
    	 * 
    	 */
     	public static int getQXCDirectDoubleZhushu(String betcode,String qhTab,
     			String sign){
     		int zhushu=1;
     		
     		//根据七位之间的分隔符qhTab分解各个位
    		String arrCode[] = betcode.split("\\"+qhTab);
    		for(int i=0;i<arrCode.length;i++){
    			//分别得到各位之间的注码数组
        		String codes[] = arrCode[i].split("\\"+sign);
        		zhushu*=codes.length;
    		}

    		return zhushu ;
     	}
     	
    	/**
    	 * 
    	 *      七星彩直选复式注码解析得金额
    	 * @param 
    	 * 		betcode 注码
    	 *      示例 注码:1,2,8-0,2,3-1,2,8-0,2,3-3,4,9-2,4,7-3,4,9
    	 * @param 
    	 * 		multiple 倍数 为1倍
    	 * @param 
    	 * 		qhTab 各个位数之间分隔符 示例中为"-"
    	 * @param
    	 * 		sign 注码之间的分隔符 示例中为","
    	 * @return
    	 * 		总金额 = 注数*倍数*单价(单张彩票的金额)
    	 *      示例中为486元
    	 *      
    	 */
    	public static int getQXCDirectDoubleMoney(String betcode, int multiple,
    			String qhTab, String sign) {
    		
    		//将各个位数的个数相乘就是注码的注数
    		int zhushu = getQXCDirectDoubleZhushu(betcode, qhTab, sign); 
    		
    		//算总金额 = 注数*倍数*单价(单张彩票的金额)并返回
    		return zhushu * multiple * Constant.LOTTERY_PRICE;
    	}
}
