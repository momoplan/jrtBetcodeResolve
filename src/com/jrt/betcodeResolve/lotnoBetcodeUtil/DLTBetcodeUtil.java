package com.jrt.betcodeResolve.lotnoBetcodeUtil;

import com.jrt.betcodeResolve.util.BetcodeResolveUtil;
import com.jrt.betcodeResolve.util.Constant;

/**
 * 
 * 大乐透注码解析 获取金额、注数、玩法的类
 * @author
 *		徐丽
 */
public class DLTBetcodeUtil {
	
	/**
	 * 
	 * 体彩大乐透单式玩法			
	 * @param 
	 * 		betcode 注码
	 * 		示例:1,15,4,5,6+1,7^1,15,4,5,6+1,7^1,15,4,5,6+1,7^
	 * @param 
	 * 		tabNumber 每注之间的分隔符 示例中为"^"
	 * @return 
	 *      注数 示例为3注
	 *      
	 */
     public static int getDLTSimplexZhushu(String betcode,String tabNumber){
    	 
    	 //根据注码之间的分隔符tabNumber分隔注码
    	 String arrCode[] = betcode.split("\\"+tabNumber);
    	 
    	 //注码的长度就为总共的注数
    	 return arrCode.length;
     }
	
	/**
	 * 
	 * 		 大乐透单式玩法
	 * @param 
	 * 		betcode 注码
	 * 		示例:1,15,4,5,6+1,7^1,15,4,5,6+1,7^1,15,4,5,6+1,7^
	 * @param
	 *      multiple 倍数 为1倍
	 * @param 
	 * 		tabNumber 每注之间的分隔符示例中为"^"
	 * @param
	 * 		superaddition 是否追加 true-追加 false-不追加
	 * @return 
	 *      总金额=注数*倍数*单张彩票的金额
	 *      示例:不追加金额为6元; 追加金额为9元
	 *      
	 */
     public static int getDLTSimplexMoney(String betcode,int multiple,String tabNumber,boolean superaddition){
    	 int money = 0;
    	 //调用算注数的方法得到注数
    	 int zhushu = getDLTSimplexZhushu(betcode, tabNumber);
    	 
    	 //判断是否追加-算金额(注数*倍数*金额)
    	 if(superaddition){
    		//是追加的话单张彩票的金额为3元
	    	money = zhushu * multiple * Constant.DLT_LOTTERY_PRICE;
	     }else{
	    	money = zhushu * multiple * Constant.LOTTERY_PRICE;
	     }
    	 return money;
     }
     
     /**
  	 * 
  	 * 		大乐透复式投注
  	 * @param 
  	 * 		betcode 注码
  	 * 		示例:1,2,15,4,5,6+1,7,2
  	 * @param 
  	 * 		tab 红球和蓝球之间分隔符示例中为"+"
  	 * @param 
  	 * 		sign 注码之间分隔符 示例中为","
  	 * @return 
  	 *      注数 示例注数为27注
  	 *      
  	 */
      public static int getDLTDuplexZhushu(String betcode,String tab,String sign){
     	// 1.分隔红球和蓝球
  		String ballArray[] = betcode.split("\\" + tab);

  		// 2.给蓝球和红球注码分隔
  		String red[] = ballArray[0].split("\\" + sign);
  		String blue[] = ballArray[1].split("\\" + sign);

  		// 3.调用算注数的方法得到注数
  		int zhushu = BetcodeResolveUtil.getDaleTouNumber(red.length,blue.length);
  		
  		return zhushu;
      }
     
     /**
 	 * 
 	 * 		大乐透复式投注
 	 * @param 
 	 * 		betcode 注码
 	 * 		示例:1,2,15,4,5,6+1,7,2
 	 * @param
	 *      multiple 倍数为1倍
 	 * @param 
 	 * 		tab 红球和蓝球之间分隔符示例中为"+"
 	 * @param 
 	 * 		sign 注码之间分隔符 示例中为","
 	 * @param
 	 * 		superaddition 是否追加
 	 * 		true-追加 false-不追加
 	 * @return 
 	 *       总金额=注数*倍数*单张彩票的金额
	 *      示例:不追加金额为54元; 追加金额为81元
	 *      
 	 */
     public static int getDLTDuplexMoney(String betcode,int multiple,String tab,
 			String sign,boolean superaddition){
    	int money = 0;

 		// 调用算注数的方法得到注数
 		int zhushu = getDLTDuplexZhushu(betcode, tab, sign);
    
 		// 判断是否追加-算金额(注数*倍数*金额)
 		if(superaddition){
 			money = zhushu * multiple * Constant.DLT_LOTTERY_PRICE;
 		}else{
 			money = zhushu * multiple * Constant.LOTTERY_PRICE;
 		}
 		return money;
     }
     
     /**
  	 * 
  	 * 		大乐透复式投注 - 根据注码、倍数得注数
  	 * @param
  	 * 		redBall 红球
  	 * 		示例:红球:1,2,15,4,5,6
  	 * @param 
  	 * 		blueBall 蓝球 
  	 * 		示例:蓝球:1,7,2
  	 * @param 
  	 * 		sign 各个注码之间的分隔符","
  	 * @return
  	 * 		 注数 示例中为27注
  	 * 		
  	 */
  	public static int getDLTDuplexZhushu1(String redBall, String blueBall,
  			String sign){
  		 		
  		// 1. 给蓝球和红球注码分隔
  		String red[] = redBall.split("\\" + sign);
  		String blue[] = blueBall.split("\\" + sign);

  		// 2.调用大乐透算复式注数的方法得到注数
  		int zhushu = BetcodeResolveUtil.getDaleTouNumber(red.length,blue.length);

  		return zhushu;
  	}
     
    /**
 	 * 
 	 * 		大乐透复式投注 - 根据注码、倍数得金额
 	 * @param
 	 * 		redBall 红球
 	 * 		示例:红球:1,2,15,4,5,6
 	 * @param 
 	 * 		blueBall 蓝球 
 	 * 		示例: 蓝球:1,7,2
 	 * @param
	 *      multiple 倍数 为1倍
 	 * @param 
 	 * 		sign 各个注码之间的分隔符","
 	 * @param
 	 * 		superaddition 是否追加 true-追加 false-不追加
 	 * @return
 	 * 		  总金额=注数*倍数*单张彩票的金额
	 *      示例:不追加金额为54元; 追加金额为81元
 	 * 		
 	 */
 	public static int getDLTDuplexMoney1(String redBall, String blueBall, int multiple,
 			String sign,boolean superaddition){
 		int money = 0;

 		// 调用算注数的方法得到注数
 		int zhushu = getDLTDuplexZhushu1(redBall, blueBall, sign);

 	    // 判断是否追加-算金额(注数*倍数*金额)
 		if(superaddition){
 			money = zhushu * multiple * Constant.DLT_LOTTERY_PRICE;
 		}else{
 			money = zhushu * multiple * Constant.LOTTERY_PRICE;
 		}
 		return money;
 	}
 	
 	/**
	 * 
	 * 		大乐透胆拖玩法  - 根据注码、倍数得注数
	 * @param 
	 * 		betcode 注码
	 *      示例:1,2$3,4,5,6,7,8,9+1$2,3
	 * @param 
	 * 		tab 红球和蓝球中间的分隔符 示例中为"+"
	 * @param 
	 * 		sign 传过来注码的格式标记符 示例中为","
	 * @param 
	 * 		dtTab 胆码与拖码之间的分隔符 示例中为"$"
	 * @return
	 * 		注数 示例为105注 
	 * 		
	 */
	public static int getDLTDantuoZhushu(String betcode, String tab,
			String dtTab, String sign){
		
		// 1.根据分隔符tab分隔红球和蓝球
		String codeArray[] = betcode.split("\\" + tab);

		// 2.根据胆码和拖码之间的分隔符dtTab 分隔红球胆码和拖码
		String redBall[] = codeArray[0].split("\\" + dtTab);
		String blueBall[] = codeArray[1].split("\\" + dtTab);
		
		// 3.得到红球胆码、拖码和蓝球的数组
		String redBallDan[] = redBall[0].split("\\" + sign);
		String redBallTuo[] = redBall[1].split("\\" + sign);
		String blueDan[] = blueBall[0].split("\\" + sign);
		int a = 0;
		for(int i=0;i<blueDan.length;i++){
			if(blueDan[i].trim().equals("")){
				break;
			}else{
				a=blueDan.length;
				break;
			}
		}
		String blueTuo[] = blueBall[1].split("\\" + sign);
		
		// 4.计算注数
		int zhushu = BetcodeResolveUtil.getDaLeTouNumberDT(
				redBallDan.length, redBallTuo.length, a, blueTuo.length);
		
		return zhushu;
	}
 	
 	/**
	 * 
	 * 		大乐透胆拖玩法  - 根据注码、倍数得金额
	 * @param 
	 * 		betcode 注码
	 *      示例:1,2$3,4,5,6,7,8,9+1$2,3
	 * @param 
	 * 		multiple 倍数为1倍
	 * @param
	 * 		tab 红球和蓝球中间的分隔符 示例中为"+"
	 * @param 
	 * 		sign 传过来注码的格式标记符 示例中为","
	 * @param 
	 * 		dtTab 胆码与拖码之间的分隔符 示例中为"$"
	 * @param
 	 * 		superaddition 是否追加 true-追加 false-不追加
	 * @return
	 *		总金额=注数*倍数*单张彩票的金额
	 *      示例:不追加金额为210元; 追加金额为315元
	 * 		
	 */
	public static int getDLTDantuoMoney(String betcode, int multiple, String tab,
			String dtTab, String sign,boolean superaddition){
		int money = 0;
		
		// 调用算注数的方法计算注数
		int zhushu = getDLTDantuoZhushu(betcode, tab, dtTab, sign);

		// 判断是否追加-算金额(注数*倍数*金额)
		if(superaddition){
 			money = zhushu * multiple * Constant.DLT_LOTTERY_PRICE;
 		}else{
 			money = zhushu * multiple * Constant.LOTTERY_PRICE;
 		}
		return money;
	}
	
	/**
	 * 
	 * 		大乐透胆拖投注 - 根据注码、倍数得金额
	 * @param 
	 * 		redBallDanma 红球胆码
	 * 		示例:红球胆码:1,2
	 * @param 
	 * 		redBallTuoma 红球拖码
	 * 		示例:红球拖码:3,4,5,6,7,8,9
	 * @param 
	 * 		blueBallDanma 蓝球胆码
	 * 		示例:蓝球胆码:1
	 * @param 
	 * 		blueBallTuoma 蓝球拖码
	 * 		示例:蓝球拖码:2,3
	 * @param 
	 * 		sign 传过来注码的格式标记符 示例中为","
	 * @return
	 * 		注数 示例为105注
	 * 		
	 */
	public static int getDLTDantuoZhushu1(String redBallDanma, String redBallTuoma, String blueBallDanma,
			String blueBallTuoma, String sign){

		// 1.根据注码分隔符sign 提取红球胆码、拖码和蓝球胆码、拖码
		String redBallDan[] = redBallDanma.split("\\" + sign);
		String redBallTuo[] = redBallTuoma.split("\\" + sign);
		String blueBallDan[] = blueBallDanma.split("\\" + sign);
		String blueBallTuo[] = blueBallTuoma.split("\\" + sign);
		
		// 2.调用算注数的方法计算注数
		int zhushu = BetcodeResolveUtil.getDaLeTouNumberDT(
				redBallDan.length, redBallTuo.length,blueBallDan.length,blueBallTuo.length);

		return zhushu;
	}
	
	/**
	 * 
	 * 		大乐透胆拖投注 - 根据注码、倍数得金额
	 * @param 
	 * 		redBallDanma 红球胆码
	 * 		示例:红球胆码:1,2
	 * @param 
	 * 		redBallTuoma 红球拖码
	 * 		示例:红球拖码:3,4,5,6,7,8,9
	 * @param 
	 * 		blueBallDanma 蓝球胆码
	 * 		示例: 蓝球胆码:1
	 * @param 
	 * 		blueBallTuoma 蓝球拖码
	 * 		示例:蓝球拖码:2,3
	 * @param 
	 * 		multiple 倍数为1倍
	 * @param 
	 * 		sign 传过来注码的格式标记符 示例中为","
	 * @param
 	 * 		superaddition 是否追加 true-追加 false-不追加
	 * @return
	 * 		总金额=注数*倍数*单张彩票的金额
	 *      示例:不追加金额为210元; 追加金额为315元
	 * 		
	 */
	public static int getDLTDantuoMoney1(String redBallDanma, String redBallTuoma, String blueBallDanma,
			String blueBallTuoma,int multiple, String sign,boolean superaddition){
		int money = 0;
		
		// 调用算注数的方法计算注数
		int zhushu = getDLTDantuoZhushu1(redBallDanma, redBallTuoma, blueBallDanma, blueBallTuoma, sign);

		// 判断是否追加-算金额(注数*倍数*金额)
		if(superaddition){
 			money = zhushu * multiple * Constant.DLT_LOTTERY_PRICE;
 		}else{
 			money = zhushu * multiple * Constant.LOTTERY_PRICE;
 		}
		return money;
	}
	
	/**
	 * 
	 *      大乐透生肖乐(十二选二)注码解析算注数
	 * @param 
	 * 		betcode 注码 
	 *      示例:1,2
	 * @param 
	 * 		sign 注码之间分隔符 示例中为","
	 * @return
	 *      注数 示例中为1注
	 *      
	 */
	public static int getDLTAnimalHappyZhushu(String betcode,String sign){
		 //根据注码之间的分隔符sign分隔注码
		 String code[] = betcode.split("\\"+sign);
		 
		 //调用算注数分方法得注数
		 int zhushu = BetcodeResolveUtil.getDaletouSXNumber(code.length);
		 
		 return zhushu ;
	 }
	
	/**
	 * 
	 *      大乐透生肖乐(十二选二)注码解析得金额
	 * @param 
	 * 		betcode 注码 
	 *      示例:1,2
	 * @param 
	 * 		multiple 倍数 为1倍
	 * @param 
	 * 		sign 注码之间分隔符 示例中为","
	 * @return
	 *      总金额=注数*倍数*单张彩票的金额  
	 *      示例中为2元
	 *      
	 */
	public static int getDLTAnimalHappyMoney(String betcode,int multiple,String sign){
		//注码分隔
		 String code[] = betcode.split("\\"+sign);
		 //根据注码得注数
		 int zhushu = BetcodeResolveUtil.getDaletouSXNumber(code.length);
		 //算金额(注数*倍数*金额)
		 return zhushu * multiple * Constant.LOTTERY_PRICE;
	 }
	
	/**
	 * 
	 * 根据注码得到大乐透的玩法
	 * @param betcode
	 *            注码 例如:"1,15,4,5,6+1,7^1,15,4,5,6+1,7^1,15,4,5,6+1,7^ 
	 *					1,15,4,5,6+1,7^1,15,4,5,6+1,7^1,15,4,5,6+1,7^ //单式
	 *					1,2,15,4,5,6+1,7,2^ //复式
	 *					1,2$3,4,5,6,7,8,9+1$2,3^ //胆拖
	 *					1,2^//十二选二单式
	 *					1,2,3,4//十二选二复式
	 * @param tabNumber
	 *            注码之间分隔符 例子中为"^"
	 * @param tab
	 * 			红球和蓝球之间的分隔符 示例中为"+"
	 * @param sign
	 *            注码之间的分隔符 例子中为","
	 * @param dtTab
	 *            胆码与拖码之间的分隔符 例子中为"$" 
	 * @return 返回玩法 
	 * 		       例:单式、复式、胆拖、胆拖
	 * 
	 */
	public static String getDLTGameMethod(String betcode,String tabNumber,
			String tab,String sign,String dtTab){
		
		String gameMethod = "";
		//根据多注之间的分隔符tabNumber分隔注码
		String codes[] = betcode.split("\\" + tabNumber);
		
		// 大乐透具体玩法如下：
		for (int k = 0; k < codes.length; k++) {
			/**
			 * 十二选二
			 */
			if(codes[k].indexOf(tab) != -1){
				
				// 根据红球与蓝球之间的分隔符tab分隔蓝球与红球
				String redAndBlue[] = codes[k].split("\\" + tab);
	
				// 根据注码之间的分隔符sign 将红球和蓝球注码分开
				String ss1[] = redAndBlue[0].split("[" + sign + "]");
				String ss2[] = redAndBlue[1].split("[" + sign + "]");
				
				// 判断是胆码还是单式或者复式
				if(codes[k].indexOf(dtTab) == -1){
					
				  /**
				   *  单式
				   */
					if (ss1.length == 5 && ss2.length == 2) {
						gameMethod = Constant.DLT_DS;
					/**
					 * 复式
					 */
					} else {
						// 得到复式注码
						gameMethod = Constant.DLT_FS;
					}
					/**
					 * 胆拖
					 */
				}else{
					//得到胆拖、复式拼接的注码
					gameMethod = Constant.DLT_DT;
				}
		    }else{
		    	//得到胆拖、复式、十二选二拼接的注码
		    	gameMethod = Constant.DLT_SXL;
		    }
		}
		return gameMethod;
	}
}
