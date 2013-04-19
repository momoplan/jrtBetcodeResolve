package com.jrt.betcodeResolve.resolve;
import com.jrt.betcodeResolve.util.Constant;

/**
 * 
 *		竞彩胜平负注码解析
 *
 */
public class JCBetcodeResovle {
	/**
	 * 
	 *      竞彩胜平负
	 * @param 
	 * 		betcode 注码     
	 *      单式注码(500@20101004|1|301|30^)
	 *      复式注码(500@20101004|1|301|0^20101004|1|302|3^)
	 * @param 
	 * 		jcTab 注码之间的分隔符   示例中为"|"
	 * @return   
	 * 		拼接完的注码 
	 * 		示例:单式注码(500@20101004|1|301|30^)
	 *      复式注码(500@20101004|1|301|0^20101004|1|302|3^)
	 * 
	 */
	public static String getJCBetcode(String betcode,String jcTab){
		//根据分隔符jcTab将注码分隔
		String codes[] = betcode.split("\\"+jcTab);
		
		String code = "";
		//循环注码将得到的注码拼接成所需的注码(去掉最后一个字符)
		for(int i=0;i<codes.length;i++){
			code+=codes[i]+(i==codes.length-1? "": Constant.JC_TAB);
		}

		//将得到的注码返回
		return code;
	}
	
}
