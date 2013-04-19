package com.jrt.betcodeResolve.lotnoBetcodeUtil;

import com.jrt.betcodeResolve.util.BetcodeResolveUtil;
import com.jrt.betcodeResolve.util.Constant;

/**
 * 
 * 		福彩3D注码解析注码算注数、金额、玩法的类
 * @author
 * 		徐丽
 * 
 */
public class SDBetcodeUtil {

	/**
	 * 
	 * 		直选单式和组选单式的注码算注数
	 * @param
	 * 		betcode 注码
	 * 		例:1,2,3^1,2,2^4,5,6^7,8,9^0,9,1^
	 * @param
	 * 		tabNumber 注码之间分隔符 示例中为"^"
	 * @return
	 * 		注数 示例中为5注
	 * 
	 */
	public static int getSDSimplexZhushu(String betcode,String tabNumber) {
		//根据tabNumber分隔注码
		String code[] = betcode.split("\\" + tabNumber);
		//注数 解析后的长度
		return code.length ;
	}
	
	/**
	 * 
	 * 		直选单式和组选单式的注码解析金额算法
	 * @param
	 * 		betcode 注码
	 * 		例:1,2,3^1,2,2^4,5,6^7,8,9^0,9,1^
	 * @param
	 * 		multiple 倍数 例子为1倍
	 * @param
	 * 		tabNumber 注码之间分隔符 例子中为"^"
	 * @return
	 * 		金额=注数* 单价(单张彩票的金额)*倍数
	 * 		例子中金额为:10元
	 * 
	 */
	public static int getSDSimplexMoney(String betcode, int multiple,String tabNumber) {
		//调用算注数的方法算得注数
	    int zhushu = getSDSimplexZhushu(betcode, tabNumber);
	    //总金额=倍数*注数*倍数
		return multiple * zhushu * Constant.LOTTERY_PRICE;
	}
	
	/**
	 * 
	 * 		和值注码解析注数算法 
	 * @param
	 * 		betcode 和值数例20
	 * @param
	 * 		playName 玩法名称
	 *    10-直选和值(范围:0-27)、11-组三和值(范围:1-26)、12-组六和值(范围:3-24)
	 * @return
	 * 		注数 示例中直选和值为36注、组三和值4注、组六和值为4注
	 * 
	 */
	public static int getSDHezhiZhushu(String betcode,String playName){
		int zhushu = 0;
		//将和值数转换为整数
		int code = Integer.parseInt(betcode);
	    //判断不同玩法和值数的范围并 调用不同和值数的算法得到注数
		//直选和值
		if (playName.trim().equals(Constant.SD_ZXHZ)) {
			if(code >= 0 && code <= 27){//(范围:0-27)
			  zhushu = BetcodeResolveUtil.getDirectHeZhiNumber(code);
			}
		}
		//组三和值
		if (playName.trim().equals(Constant.SD_ZSHZ)) {
			if(code >= 1 && code <= 26){//(范围:1-26)
			   zhushu = BetcodeResolveUtil.getGroup3HeZhiNumber(code);
			}
		}
		//组六和值
		if (playName.trim().equals(Constant.SD_ZLHZ)) {
			if(code >= 3 && code <= 24){//(范围:3-24)
			   zhushu = BetcodeResolveUtil.getGroup6HeZhiNumber(code);
			}
		}
		return zhushu;
	}
	
	/**
	 * 
	 * 		和值注码解析金额算法
	 * @param
	 * 		betcode 和值数 例:20
	 * @param
	 * 		multiple 倍数 为1倍
	 * @param
	 * 		playName 玩法名称
	 *    10-直选和值(范围:0-27)、11-组三和值(范围:1-26)、12-组六和值(范围:3-24)
	 * @return
	 * 		总金额=倍数*注数*单价(单张彩票的金额)
	 *    示例中:直选和值为72元、组三和值8元、组六和值为8元
	 *    
	 */
	public static int getSDHezhiMoney(String betcode,int multiple,String playName){
		//调用算注数的方法得注数
		int zhushu = getSDHezhiZhushu(betcode, playName);
		//算总金额=倍数*注数*单价(单张彩票的金额)并返回
		return multiple * zhushu * Constant.LOTTERY_PRICE;
	}
	
	
	/**
	 * 
	 *      福彩3D单选按位包号(直选复式)算注数
	 * @param 
	 * 		betcode 注码
	 *      例:1,4,2,3+6,9+1,5,2,3^
	 * @param 
	 * 		tabNumber 百位、十位、个位之间的分隔符 示例中为"+"
	 * @param
	 * 		sign 注码之间的分隔符 示例中","
	 * @return
	 * 		注数  百位的个数*十位的个数*个位的个数 示例中为32注
	 * 
	 */
	public static int getSDDirectDoubleZhushu(String betcode,String tabNumber,String sign){
		//根据分隔符tabNumber解析注码（分解百、十、个位）
		String arrCode[] = betcode.split("\\"+tabNumber);
		String hundredCode[] = arrCode[0].split("\\"+sign);//百位
		String tenCode[] = arrCode[1].split("\\"+sign);//十位
		String unitCode[] = arrCode[2].split("\\"+sign);//个位
		//算注数 =百位的个数*十位的个数*个位的个数 
		int zhushu = hundredCode.length * tenCode.length * unitCode.length; 
		return zhushu;
	}
	
	/**
	 * 
	 *      福彩3D单选按位包号(直选复式)注码解析得金额
	 * @param 
	 * 		betcode 注码
	 *      例:1,4,2,3+6,9+1,5,2,3^
	 * @param 
	 * 		multiple 倍数 例子为1倍
	 * @param 
	 * 		tabNumber 百位、十位、个位之间的分隔符 示例中为"+"
	 * @param
	 * 		sign 注码之间的分隔符 示例中为","
	 * @return
	 * 		总金额=倍数*注数*单价(单张彩票的金额)
	 * 		示例中总金额为64元
	 * 
	 */
	public static int getSDDirectDoubleMoney(String betcode,int multiple,String tabNumber,String sign){
		//调用直选复式算注数的方法得到注数
		int zhushu = getSDDirectDoubleZhushu(betcode, tabNumber, sign);
		//算总金额=倍数*注数*单价(单张彩票的金额)
		return zhushu * multiple * Constant.LOTTERY_PRICE;
	}
	
	/**
	 * 
	 *      福彩3D注码解析得注数
	 * @param
	 * 		playName 玩法(34-代表单选单复式(直选包号)、31-组三复式、32-组六复式)
	 * @param 
	 * 		betcode 注码
	 *      示例:注码3,4,5,6,7,8
	 * @param
	 * 		sign 注码之间的分隔符 示例中为","
	 * @return
	 * 		注数 例:直选包号注数为120注、组三复式为30注、组六复式为20注
	 * 
	 */
	public static int getSDDirectAndGroupZhushu(String betcode,String sign,String playName){
		//根据分隔符sign分隔注码赋值给arrCode数组
		String arrCode[] = betcode.split("\\"+sign);
		
		int zhushu = 0;
		//根据不同的玩法算注数
		if(playName.equals(Constant.SD_DXDFS)){//直选包号
			zhushu=Integer.parseInt(String.valueOf(BetcodeResolveUtil.get3DSingleSelectSingleMultiple(3, arrCode.length)));
		}else if(playName.equals(Constant.SD_Z3FS)){//组三复式
			zhushu=BetcodeResolveUtil.getGroup3MultipleNumber(arrCode.length);
		}else if(playName.equals(Constant.SD_Z6FS)){//组六复式
			zhushu = BetcodeResolveUtil.getGroup6MultipleNumber(arrCode.length);
		}
		return zhushu ;
	}
	
	/**
	 * 
	 *      福彩3D调用算得注数的方法算总金额
	 * @param
	 * 		playName 玩法(34-代表单选单复式(直选包号)、31-组三复式、32-组六复式)
	 * @param 
	 * 		betcode 注码 
	 *      示例:注码3,4,5,6,7,8
	 * @param 
	 * 		multiple 倍数
	 * @param
	 * 		sign 注码之间的分隔符 示例中为","
	 * @return
	 * 		总金额=倍数*注数*单价(单张彩票的金额)
	 * 		例:直选包号为240元、组三复式为60元、组六复式为40元
	 * 
	 */
	public static int getSDDirectAndGroupMoney(String betcode,int multiple,String sign,String playName){
		//调用算注数的方法得注数
		int zhushu = getSDDirectAndGroupZhushu(betcode, sign, playName);
		//算总金额=倍数*注数*单价(单张彩票的金额)
		return zhushu * multiple * Constant.LOTTERY_PRICE;
	}
	
	/**
	 * 
	 *      福彩3D胆拖复式(单选单胆拖)注码解析
	 * @param 
	 * 		betcode 注码
	 *      例:2,1*5,4,3
	 * @param 
	 * 	    sign 注码之间的分隔符  示例中为","
	 * @param 
	 * 	    redTab 胆码和拖码之间的分隔符 示例中为"*"
	 * @return    
	 * 		注数 示例中的注数为36注
	 */
	public static int getSDDantuoZhushu(String betcode,String redTab,String sign){
		//根据胆码和拖码之间的分隔符redTab分隔胆码和拖码
		String betcodes[] = betcode.split("\\"+redTab);
		//根据分隔符sign 分隔胆码或拖码得到胆码和拖码的个数
		String danmaCode[]=betcodes[0].split("\\"+sign);
		String tuomaCode[]=betcodes[1].split("\\"+sign);
		//根据胆码和拖码的个数获取注数 并返回
		int zhushu = BetcodeResolveUtil.getDanmaMultiple3DNumber(danmaCode.length, tuomaCode.length);
		return zhushu;
	}
	
	/**
	 * 
	 *      福彩3D胆拖复式(单选单胆拖)注码解析
	 * @param 
	 * 		multiple 倍数 为 1 倍
	 * @param 
	 * 		betcode 注码
	 *      例:2,1*5,4,3
	 * @param 
	 * 	    sign 注码之间的分隔符  示例中为","
	 * @param 
	 * 	    redTab 胆码和拖码之间的分隔符 示例中为"*"
	 * @return    
	 * 		总金额=倍数*注数*单价(单张彩票的金额) 示例中为72元
	 * 
	 */
	public static int getSDDantuoMoney(String betcode,int multiple,String redTab,String sign){
		//根据注码获取注数
		int zhushu = getSDDantuoZhushu(betcode, redTab, sign);
		//算总金额=倍数*注数*单价(单张彩票的金额)
		return zhushu * multiple * Constant.LOTTERY_PRICE;
	}
	
	/**
	 * 
	 *      福彩3D胆拖复式(单选单胆拖)注码解析算注数
	 * @param 
	 * 		danma 胆码
	 *      例:胆码2,1
	 * @param 
	 * 		tuoma 拖码
	 *      例:拖码5,4,3
	 * @param 
	 * 	    sign 注码之间的分隔符 示例中为","
	 * @return    
	 * 		注数 = 示例中为36注
	 * 
	 */
	public static int getSDDantuoZhushu1(String danma,String tuoma,String sign){
		//分隔注码
		String danmaCode[]=danma.split("\\"+sign);
		String tuomaCode[]=tuoma.split("\\"+sign);
		//根据注码获取注数
		int zhushu = BetcodeResolveUtil.getDanmaMultiple3DNumber(danmaCode.length, tuomaCode.length);
		//算注数
		return zhushu;
	}
	/**
	 * 
	 *      福彩3D胆拖复式(单选单胆拖)注码解析得金额
	 * @param 
	 * 		danma 胆码
	 *      例:胆码2,1
	 * @param 
	 * 		tuoma 拖码
	 *      例:拖码5,4,3
	 * @param 
	 * 		multiple 倍数 为 1倍
	 * @param 
	 * 	    sign 注码之间的分隔符 示例中为","
	 * @return    
	 * 		总金额=倍数*注数*单价(单张彩票的金额) 示例中为72元
	 * 
	 */
	public static int getSDDantuoMoney1(String danma,String tuoma,int multiple,String sign){
		//根据注码获取注数
		int zhushu = getSDDantuoZhushu1(danma, tuoma, sign);
		//算总金额=倍数*注数*单价(单张彩票的金额) 示例中为72元
		return zhushu * multiple * Constant.LOTTERY_PRICE;
	}
	
}
