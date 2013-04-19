package com.jrt.betcodeResolve.util;

import java.util.ArrayList;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

/**
 * 
 * 投注串转换为注码串
 * 
 */
public class CodeUtil {

	private static Logger logger = Logger.getLogger(CodeUtil.class);
	private static final String wan1 = "1512-F47104-00-01-";// 双色球
	private static final String wan2 = "1512-F47103-00-01-";// 3D
	private static final String wan3 = "1512-F47102-00-01-";// 七乐彩
	private static final String wan4 = "1512-F47101-00-01-";// 时时彩
	/**
	 * 双色球拼接后的注码解析
	 * @param betCode 注码
	 * @return json 存入玩法、倍数、注码
	 */
	public static JSONObject getSSQCodeString(String betCode){
		String bet[] = betCode.split("\\^");//将每注的注码分隔为单注
		StringBuffer stb = new StringBuffer();
		JSONObject jsonObject = new JSONObject();
		String wbet = "";
		for (int i = 0; i < bet.length; i++) {
			wbet = wan1 + bet[i] + "^";
			int[] nums = getPoolInfo(wbet);
			if (nums[0] == 0) {
				//单式
				for (int j = 4; j < nums.length - 1; j++) {
					stb.append(getBuZero(nums[j])+",");
				}
				stb.deleteCharAt(stb.length()-1); 
				stb.append("|" + getBuZero(nums[nums.length - 1])+"<br/>");
				jsonObject.put("wanfa", Constant.SSQ_RSBS);
				jsonObject.put("multiple", nums[1]);
				jsonObject.put("betcode", stb.toString());
	
			} else if (nums[0] == 10) {
				//红复蓝单
				for (int j = 4; j < nums.length - 1; j++) {
					stb.append(getBuZero(nums[j])+",");
				}
				stb.deleteCharAt(stb.length()-1); 
				stb.append("|" + getBuZero(nums[nums.length - 1]));
				jsonObject.put("wanfa", Constant.SSQ_RMBS);
				jsonObject.put("multiple", nums[1]);
				jsonObject.put("betcode", stb.toString());
				
			} else if (nums[0] == 20) {
				//红单蓝复
				for (int j = 4; j < 10; j++) {
					stb.append(getBuZero(nums[j])+",");
				}
				stb.deleteCharAt(stb.length()-1); 
				stb.append("|");
				for (int j = 10; j < nums.length; j++) {
					stb.append(getBuZero(nums[j])+",");
				}
				stb.deleteCharAt(stb.length()-1); 				
				jsonObject.put("wanfa", Constant.SSQ_RSBM);
				jsonObject.put("multiple", nums[1]);
				jsonObject.put("betcode", stb.toString());
	
			} else if (nums[0] == 30) {
				//红复蓝复
				for (int j = 4; j < 4 + nums[2]; j++) {
					stb.append(getBuZero(nums[j])+",");
				}
				stb.deleteCharAt(stb.length()-1); 
				stb.append("|");
				for (int j = 4 + nums[2]; j < nums.length; j++) {
					stb.append(getBuZero(nums[j])+",");
				}
				stb.deleteCharAt(stb.length()-1); 
				jsonObject.put("wanfa", Constant.SSQ_RMBM);
				jsonObject.put("multiple", nums[1]);
				jsonObject.put("betcode", stb.toString());
	
			} else if (nums[0] == 40) {//40010507101322*0106~01^
				//红胆拖蓝单
				for (int j = 5; j < 5 + nums[2]; j++) {
					stb.append(getBuZero(nums[j])+",");
				}
				stb.deleteCharAt(stb.length()-1);
				jsonObject.put("redDanma", stb.toString());//红球胆码
				stb.delete(0,stb.length());
				for (int j = 5 + nums[2]; j < nums.length - 1; j++) {
					stb.append(getBuZero(nums[j])+",");
				}
				stb.deleteCharAt(stb.length()-1);
				jsonObject.put("redTuoma", stb.toString());//红球拖码
				jsonObject.put("blueBall", getBuZero(nums[nums.length - 1]));//蓝球
				jsonObject.put("wanfa", Constant.SSQ_RTBS);
				jsonObject.put("multiple", nums[1]);
				
				
			} else if (nums[0] == 50) {
				//红胆拖蓝复
				for (int j = 5; j < 5 + nums[2]; j++) {
					stb.append(getBuZero(nums[j])+",");
				}
				stb.deleteCharAt(stb.length()-1);
				jsonObject.put("redDanma", stb.toString());//红球胆码
				
				stb.delete(0,stb.length());
				for (int j = 5 + nums[2]; j < 5 + nums[2] + nums[3]; j++) {
					stb.append(getBuZero(nums[j])+",");
				}
				stb.deleteCharAt(stb.length()-1);
				jsonObject.put("redTuoma", stb.toString());//红球拖码

				stb.delete(0,stb.length());
				for (int j = 5 + nums[2] + nums[3]; j < nums.length; j++) {
					stb.append(getBuZero(nums[j])+",");
				}
				stb.deleteCharAt(stb.length()-1);
				jsonObject.put("blueBall", stb.toString());//蓝球
				jsonObject.put("wanfa", Constant.SSQ_RTBM);
				jsonObject.put("multiple", nums[1]);
			}
		}
		//System.out.println("双色球解析注码之后所得:"+jsonObject);
		return jsonObject;
	}
	
	/**
	 * 
	 * 福彩3D单选按位包号       
	 * @param betcode 注码
	 * 		示例:2012050102030405^06050607080900^0701020304050607^
	 * @return 得到的最后注码 示例:第一位包号为:0102030405, 第二位包号为:060708091000,
	 *         第三位包号为:01020304050607,
	 *
	 */
	private static JSONObject getSDBaohao(String betcode) {
		
		//1.将注码分隔为百位、十位、个位
		String wei[] = betcode.split("\\^");
		StringBuffer codestring = new StringBuffer();
		JSONObject obj	= new JSONObject();

		int onenum = Integer.parseInt(wei[0].substring(4, 6));
		int twonum = Integer.parseInt(wei[1].substring(0, 2));
		int threenum = Integer.parseInt(wei[2].substring(0, 2));
		
	    //给百位注码添加逗号
		for (int i = 0; i < onenum; i++) {
			int zhuma = Integer.parseInt(wei[0].substring(6 + 2 * i, 8 + 2 * i));
			codestring.append(zhuma + "，");
		}
		codestring.deleteCharAt(codestring.length()-1); 
		codestring.append("|");
		//给十位注码添加逗号
		for (int i = 0; i < twonum; i++) {
			int zhuma = Integer.parseInt(wei[1].substring(2 + 2 * i, 4 + 2 * i));
			codestring.append(zhuma + "，");
		}
		codestring.deleteCharAt(codestring.length()-1); 
		codestring.append("|");
		//给个位注码添加逗号
		for (int i = 0; i < threenum; i++) {
			int zhuma = Integer.parseInt(wei[2].substring(2 + 2 * i, 4 + 2 * i));
			codestring.append(zhuma + "，");
		}
		codestring.deleteCharAt(codestring.length()-1); 
		obj.put("multiple", wei[0].substring(2, 4));
		obj.put("betcode", codestring.toString());
		return obj;
	}
	
	/**
	 * 
	 * 福彩3D注码解析
	 * @param betCode 返回的注码
	 * @return json 玩法、倍数、注码
	 */
	public static JSONObject getSDCodeString(String betCode){
		String bet[] = betCode.split("\\^");//将每注的注码分隔为单注
		StringBuffer stb = new StringBuffer();
		JSONObject jsonObject = new JSONObject();
		String wbet = "";
		if (betCode.startsWith(Constant.SD_WXTZ)) {
			// (直选复式)单选按位包号：2012050102030405^06060708091000^0701020304050607^
			jsonObject.put("wanfa", Constant.SD_WXTZ);//玩法
			JSONObject obj = getSDBaohao(betCode);
			jsonObject.put("multiple", obj.getString("multiple"));//倍数
			jsonObject.put("betcode", obj.getString("betcode"));//注码
			//System.out.println("福彩3D解析注码之后所得:"+jsonObject);
			return jsonObject;
		}
		for (int i = 0; i < bet.length; i++) {
			wbet = wan2 + bet[i] + "^";
			int[] nums = getPoolInfo(wbet);
			if (nums[0] == 0) {
				//直选单式
				for (int j = 3; j < nums.length; j++) {
					stb.append(nums[j]+"|");
				}
				stb.delete(stb.length()-1, stb.length());
				stb.append("；");
				jsonObject.put("wanfa", Constant.SD_ZXDS);
	
			} else if (nums[0] == 1) {
				//组三单式
				for (int j = 3; j < nums.length; j++) {
					stb.append(nums[j]+"，");
				}
				stb.delete(stb.length()-1, stb.length());
				stb.append("；");
				jsonObject.put("wanfa", Constant.SD_Z3DS);
			} else if (nums[0] == 2) {
				//组六单式
				for (int j = 3; j < nums.length; j++) {
					stb.append(nums[j]+"，");
				}
				stb.delete(stb.length()-1, stb.length());
				stb.append("；");
				jsonObject.put("wanfa", Constant.SD_Z6DS);
				
			} else if (nums[0] == 10) {
				//直选和值
				stb.append(nums[nums.length - 1]);
				jsonObject.put("wanfa", Constant.SD_ZXHZ);
	
			} else if (nums[0] == 11) {
				//组三和值
				stb.append(nums[nums.length - 1]);
				jsonObject.put("wanfa", Constant.SD_ZSHZ);
	
			} else if (nums[0] == 12) {
				//组6和值 
				stb.append(nums[nums.length - 1]);
				jsonObject.put("wanfa", Constant.SD_ZLHZ);
	
			} else if (nums[0] == 31) {
				//组3复式
				for (int j = 3; j < nums.length; j++) {
					stb.append(nums[j]+"，");
				}
				stb.deleteCharAt(stb.length()-1);
				jsonObject.put("wanfa", Constant.SD_Z3FS);
	
			} else if (nums[0] == 32) {
				//组6复式 
				for (int j = 3; j < nums.length; j++) {
					stb.append(nums[j]+"，");
				}
				stb.deleteCharAt(stb.length()-1);
				jsonObject.put("wanfa", Constant.SD_Z6FS);
	
			} else if (nums[0] == 34) {
				//直选包号 
				for (int j = 3; j < nums.length; j++) {
					stb.append(nums[j]+"，");
				}
				stb.deleteCharAt(stb.length()-1);
				jsonObject.put("wanfa", Constant.SD_DXDFS);
	
			} else if (nums[0] == 54) {
				// 胆拖复式
				stb.append("胆码：");
				for (int j = 4; j < 4 + nums[2]; j++) {
					stb.append(nums[j]+"，");
				}
				stb.deleteCharAt(stb.length()-1);
				stb.append("；拖码：");
				for (int j = 4 + nums[2]; j < nums.length; j++) {
					stb.append(nums[j]+"，");
				}
				stb.deleteCharAt(stb.length()-1);
				jsonObject.put("wanfa", Constant.SD_DTFS);
			}
			jsonObject.put("multiple", nums[1]);
			jsonObject.put("betcode", stb.toString());
		}
		//System.out.println("福彩3D解析注码之后所得:"+jsonObject);
		return jsonObject;
	}
	
	/**
	 * 
	 * 七乐彩注码解析
	 * @param betCode 返回的注码
	 * @return json 玩法、倍数、注码
	 */
	public static JSONObject getQLCCodeString(String betCode){
		String bet[] = betCode.split("\\^");//将每注的注码分隔为单注
		StringBuffer stb = new StringBuffer();
		JSONObject jsonObject = new JSONObject();
		String wbet = "";
		/**
		 * 得到除特殊彩种以外的其他彩种注码算法
		 */
		for (int i = 0; i < bet.length; i++) {
			wbet = wan3 + bet[i] + "^";
			int[] nums = getPoolInfo(wbet);
			
			//单式 
			if (nums[0] == 0) {
				for (int x = 2; x < 9; x++) {
					stb.append(getBuZero(nums[x])+"，");
				}
				stb.deleteCharAt(stb.length()-1);
				stb.append("<br/>");
				jsonObject.put("wanfa", Constant.QLC_ZXDS);
				jsonObject.put("multiple", nums[1]);
				jsonObject.put("betcode", stb.toString());
			}
			
			//复式
			if (nums[0] == 10) {
				for (int x = 2; x < nums.length; x++) {
					stb.append(getBuZero(nums[x])+"，");
				}
				stb.deleteCharAt(stb.length()-1);
				jsonObject.put("wanfa", Constant.QLC_ZXFS);
				jsonObject.put("multiple", nums[1]);
				jsonObject.put("betcode", stb.toString());
			}
			
			//胆拖 
			if (nums[0] == 20) {
				
				for (int x = 2; x < nums.length; x++) {
					if (nums[x] == -1) {
						stb.deleteCharAt(stb.length()-1);
						jsonObject.put("danma", stb.toString().trim());
						stb.delete(0,stb.length());
						continue;
					}
					stb.append(getBuZero(nums[x])+"，");
				}
				stb.deleteCharAt(stb.length()-1);
				jsonObject.put("wanfa", Constant.QLC_ZXDT);
				jsonObject.put("multiple", nums[1]);
				jsonObject.put("tuoma", stb.toString().trim());
			}
		}
		//System.out.println("七乐彩解析注码之后所得:"+jsonObject);
		return jsonObject;
	}
	/**
	 * 时时彩注码解析
	 * @param betCode 注码
	 * @return Json串
	 */
	public static JSONObject getSSCCodeString(String betCode){
		String bet[] = betCode.split("\\^");//将每注的注码分隔为单注
		StringBuffer stb = new StringBuffer();
		JSONObject jsonObject = new JSONObject();
		String wbet = "";
		// 时时彩
		if (betCode.startsWith("2") || betCode.startsWith("9")
				|| betCode.startsWith("d7") || betCode.startsWith("d8")) {
			// 位选
			String wxinfo = getWeiXuanInfo(wan4 + betCode);
			jsonObject.put("betcode", wxinfo);
			return jsonObject;

		}
		// 普通的时时彩
		for (int i = 0; i < bet.length; i++) {
			wbet = wan4 + bet[i] + "^";
			String wfinfo = getSSCString(wbet);
			stb.append(wfinfo);
		}
		jsonObject.put("betcode", stb);
		return jsonObject;
	}

	/**
	 * 
	 * 用于解析时时彩位选，任选的注码
	 * 
	 * @param s 注码信息
	 * @return 解析后的玩法、倍数及注码串
	 * 
	 */
	public static String getWeiXuanInfo(String s) {
		String t_stype = null;// 玩法
		String t_selecttype = null;// 选码类型
		String t_smul = null;// 倍数
		StringBuffer result = new StringBuffer("");
		int t_add = 1;
		// String type = s.substring(5 + t_add, 10 + t_add);// 彩种
		t_stype = s.substring(17 + t_add, 18 + t_add);
		t_selecttype = s.substring(18 + t_add, 19 + t_add);
		t_smul = s.substring(19 + t_add, 23 + t_add);

		String[] wfinfo = getWFInfo(t_stype, t_selecttype);
		result.append("选码类型：" + wfinfo[1]);
		String[] wei = s.split("\\^");

		int len = wei.length;
		if (t_stype == "2") {
			int geshu = Integer.parseInt(wei[0].substring(24, 26));
			result.append("第1位:" + geshu + "个数:");
			for (int i = 0; i < geshu; i++) {
				int temp = Integer.parseInt(wei[0].substring(26 + i * 2,
						28 + i * 2));
				result.append("为" + temp + ",");
			}

			for (int i = 1; i < len; i++) {
				result.append("第" + i + 1 + "位:");
				int geshu1 = Integer.parseInt(wei[i].substring(0, 2));
				result.append(geshu1 + "个数:");
				for (int j = 0; j < geshu; j++) {
					int temp = Integer.parseInt(wei[i].substring(2 + j * 2,
							4 + j * 2));
					result.append("为" + temp + ",");
				}
			}
		}
		if (t_stype == "9") {

			int geshu = Integer.parseInt(wei[0].substring(24, 26));
			result.append("第1位:" + geshu + "个数,");
			for (int i = 0; i < geshu; i++) {
				int temp = Integer.parseInt(wei[0].substring(26 + i * 2,
						28 + i * 2));
				result.append("为" + temp + ",");
			}

			for (int i = 1; i < len; i++) {
				result.append("第" + i + 1 + "位:");
				int geshu1 = Integer.parseInt(wei[i].substring(0, 2));
				result.append(geshu1 + "个数,");
				for (int j = 0; j < geshu; j++) {
					int temp = Integer.parseInt(wei[i].substring(2 + j * 2,
							4 + j * 2));
					result.append("为" + temp + ",");
				}
			}
		}

		if (t_stype == "d" && (t_selecttype == "7" || t_selecttype == "8")) {
			String wz = s.substring(24, 26);
			result.append("位置：" + getWZInfo(wz));
			int geshu = Integer.parseInt(wei[0].substring(26, 28));
			result.append("第1位:" + geshu + "个数,");
			for (int i = 0; i < geshu; i++) {
				int temp = Integer.parseInt(wei[0].substring(28 + i * 2,
						30 + i * 2));
				result.append("为" + temp + ",");
			}

			for (int i = 1; i < len; i++) {
				result.append("第" + i + 1 + "位:");
				int geshu1 = Integer.parseInt(wei[i].substring(0, 2));
				result.append(geshu1 + "个数,");
				for (int j = 0; j < geshu; j++) {
					int temp = Integer.parseInt(wei[i].substring(2 + j * 2,
							4 + j * 2));
					result.append("为" + temp + ",");
				}
			}
		}

		result.append(wfinfo[0]);
		result.append(Integer.valueOf(t_smul) + "倍");
		return result.toString();

	}	
	
	/**
	 * 
	 *  福彩注码解析显示内容
	 * @param s 注码串
	 * @return 解析后的注码
	 * 
	 */
	public static int[] getPoolInfo(String s) {
		String t_redN = "";// 红色球字符串
		String t_blueN = "";// 蓝色球字符串
		String t_tuoN = "";// 拖码球
		StringBuffer t_sb = new StringBuffer("");
		int[] t_Pin = null;
		int t_add = 1;

		// 彩种
		String type = s.substring(5 + t_add, 10 + t_add);
		// 玩法
		int t_stype = Integer.parseInt(s.substring(17 + t_add, 19 + t_add));
		// 倍数
		int t_smul = Integer.parseInt(s.substring(19 + t_add, 21 + t_add));

		if (Constant.SSQ.substring(1).equals(type)) {
			if (t_stype == 00) {// 红单蓝单
				for (int i = 21 + t_add; i < s.length(); i++) {
					if (s.charAt(i) == '~') {
						t_redN = t_sb.toString();
						t_sb = null;
						t_sb = new StringBuffer("");
						continue;
					}
					if (s.charAt(i) == '^') {
						t_blueN = t_sb.toString();
						t_sb = null;
						t_sb = new StringBuffer("");
						break;
					}
					t_sb.append(s.charAt(i));
				}

				t_Pin = new int[4 + (t_redN.length() / 2)
						+ (t_blueN.length() / 2)];
				t_Pin[0] = t_stype;
				t_Pin[1] = t_smul;
				t_Pin[2] = t_redN.length() / 2;
				t_Pin[3] = t_blueN.length() / 2;
				for (int i = 0; i < t_redN.length(); i += 2) {
					t_Pin[4 + i / 2] = Integer.parseInt(t_redN.substring(i,
							i + 2));
				}
				for (int i = 0; i < t_blueN.length(); i += 2) {
					t_Pin[4 + t_Pin[2] + i / 2] = Integer.parseInt(t_blueN
							.substring(i, i + 2));
				}
			} else if (t_stype == 10) {// 红复蓝单
				for (int i = 22 + t_add; i < s.length(); i++) {
					if (s.charAt(i) == '~') {
						t_redN = t_sb.toString();
						t_sb = null;
						t_sb = new StringBuffer("");
						continue;
					}
					if (s.charAt(i) == '^') {
						t_blueN = t_sb.toString();
						t_sb = null;
						t_sb = new StringBuffer("");
						break;
					}
					t_sb.append(s.charAt(i));
				}

				t_Pin = new int[4 + (t_redN.length() / 2)
						+ (t_blueN.length() / 2)];
				t_Pin[0] = t_stype;
				t_Pin[1] = t_smul;
				t_Pin[2] = t_redN.length() / 2;
				t_Pin[3] = t_blueN.length() / 2;
				for (int i = 0; i < t_redN.length(); i += 2) {
					t_Pin[4 + i / 2] = Integer.parseInt(t_redN.substring(i,
							i + 2));
				}
				for (int i = 0; i < t_blueN.length(); i += 2) {
					t_Pin[4 + t_Pin[2] + i / 2] = Integer.parseInt(t_blueN
							.substring(i, i + 2));
				}
			} else if (t_stype == 20) {// 红单蓝复
				for (int i = 22 + t_add; i < s.length(); i++) {
					if (s.charAt(i) == '~') {
						t_redN = t_sb.toString();
						t_sb = null;
						t_sb = new StringBuffer("");
						continue;
					}
					if (s.charAt(i) == '^') {
						t_blueN = t_sb.toString();
						t_sb = null;
						t_sb = new StringBuffer("");
						break;
					}
					t_sb.append(s.charAt(i));
				}

				t_Pin = new int[4 + (t_redN.length() / 2)
						+ (t_blueN.length() / 2)];
				t_Pin[0] = t_stype;
				t_Pin[1] = t_smul;
				t_Pin[2] = t_redN.length() / 2;
				t_Pin[3] = t_blueN.length() / 2;
				for (int i = 0; i < t_redN.length(); i += 2) {
					t_Pin[4 + i / 2] = Integer.parseInt(t_redN.substring(i,
							i + 2));
				}
				for (int i = 0; i < t_blueN.length(); i += 2) {
					t_Pin[4 + t_Pin[2] + i / 2] = Integer.parseInt(t_blueN
							.substring(i, i + 2));
				}
			} else if (t_stype == 30) {// 红复蓝复
				for (int i = 22 + t_add; i < s.length(); i++) {
					if (s.charAt(i) == '~') {
						t_redN = t_sb.toString();
						t_sb = null;
						t_sb = new StringBuffer("");
						continue;
					}
					if (s.charAt(i) == '^') {
						t_blueN = t_sb.toString();
						t_sb = null;
						t_sb = new StringBuffer("");
						break;
					}
					t_sb.append(s.charAt(i));
				}
				t_Pin = new int[4 + (t_redN.length() / 2)
						+ (t_blueN.length() / 2)];
				t_Pin[0] = t_stype;
				t_Pin[1] = t_smul;
				t_Pin[2] = t_redN.length() / 2;
				t_Pin[3] = t_blueN.length() / 2;
				for (int i = 0; i < t_redN.length(); i += 2) {
					t_Pin[4 + i / 2] = Integer.parseInt(t_redN.substring(i,
							i + 2));
				}
				for (int i = 0; i < t_blueN.length(); i += 2) {
					t_Pin[4 + t_Pin[2] + i / 2] = Integer.parseInt(t_blueN
							.substring(i, i + 2));
				}
			} else if (t_stype == 40) {// 胆拖蓝单
				for (int i = 21 + t_add; i < s.length(); i++) {
					if (s.charAt(i) == '*') {
						t_redN = t_sb.toString();
						t_sb = null;
						t_sb = new StringBuffer("");
						continue;
					}
					if (s.charAt(i) == '~') {
						t_tuoN = t_sb.toString();
						t_sb = null;
						t_sb = new StringBuffer("");
						continue;
					}
					if (s.charAt(i) == '^') {
						t_blueN = t_sb.toString();
						t_sb = null;
						t_sb = new StringBuffer("");
						break;
					}
					t_sb.append(s.charAt(i));
				}

				t_Pin = new int[5 + (t_redN.length() / 2)
						+ (t_tuoN.length() / 2) + (t_blueN.length() / 2)];
				t_Pin[0] = t_stype;
				t_Pin[1] = t_smul;
				t_Pin[2] = t_redN.length() / 2;
				t_Pin[3] = t_tuoN.length() / 2;
				t_Pin[4] = t_blueN.length() / 2;
				for (int i = 0; i < t_redN.length(); i += 2) {
					t_Pin[5 + i / 2] = Integer.parseInt(t_redN.substring(i,
							i + 2));
				}
				for (int i = 0; i < t_tuoN.length(); i += 2) {
					t_Pin[5 + t_Pin[2] + i / 2] = Integer.parseInt(t_tuoN
							.substring(i, i + 2));
				}
				for (int i = 0; i < t_blueN.length(); i += 2) {
					t_Pin[5 + t_Pin[2] + t_Pin[3] + i / 2] = Integer
							.parseInt(t_blueN.substring(i, i + 2));
				}
			} else if (t_stype == 50) {// 胆拖蓝复
				for (int i = 21 + t_add; i < s.length(); i++) {
					if (s.charAt(i) == '*') {
						t_redN = t_sb.toString();
						t_sb = null;
						t_sb = new StringBuffer("");
						continue;
					}
					if (s.charAt(i) == '~') {
						t_tuoN = t_sb.toString();
						t_sb = null;
						t_sb = new StringBuffer("");
						continue;
					}
					if (s.charAt(i) == '^') {
						t_blueN = t_sb.toString();
						t_sb = null;
						t_sb = new StringBuffer("");
						break;
					}
					t_sb.append(s.charAt(i));
				}

				t_Pin = new int[5 + (t_redN.length() / 2)
						+ (t_tuoN.length() / 2) + (t_blueN.length() / 2)];
				t_Pin[0] = t_stype;
				t_Pin[1] = t_smul;
				t_Pin[2] = t_redN.length() / 2;
				t_Pin[3] = t_tuoN.length() / 2;
				t_Pin[4] = t_blueN.length() / 2;
				for (int i = 0; i < t_redN.length(); i += 2) {
					t_Pin[5 + i / 2] = Integer.parseInt(t_redN.substring(i,
							i + 2));
				}
				for (int i = 0; i < t_tuoN.length(); i += 2) {
					t_Pin[5 + t_Pin[2] + i / 2] = Integer.parseInt(t_tuoN
							.substring(i, i + 2));
				}
				for (int i = 0; i < t_blueN.length(); i += 2) {
					t_Pin[5 + t_Pin[2] + t_Pin[3] + i / 2] = Integer
							.parseInt(t_blueN.substring(i, i + 2));
				}
			}
		} else if (Constant.SD.substring(1).equals(type)) {
			if (t_stype == 0 || t_stype == 1 || t_stype == 2 || t_stype == 10
					|| t_stype == 11 || t_stype == 12 || t_stype == 30) {
				// 单选//组3//组6//直和值//组3值//组6值//单选复式
				for (int i = 21 + t_add; i < s.length(); i++) {
					if (s.charAt(i) == '^') {
						t_redN = t_sb.toString();
						t_sb = null;
						t_sb = new StringBuffer("");
						continue;
					}
					t_sb.append(s.charAt(i));
				}

				t_Pin = new int[3 + (t_redN.length() / 2)];
				t_Pin[0] = t_stype;
				t_Pin[1] = t_smul;
				t_Pin[2] = t_redN.length() / 2;
				for (int i = 0; i < t_redN.length(); i += 2) {
					t_Pin[3 + i / 2] = Integer.parseInt(t_redN.substring(i,
							i + 2));
				}
			} else if (t_stype == 31 || t_stype == 32 || t_stype == 34) {// 组3复式//组6复式
				for (int i = 23 + t_add; i < s.length(); i++) {
					if (s.charAt(i) == '^') {
						t_redN = t_sb.toString();
						t_sb = null;
						t_sb = new StringBuffer("");
						continue;
					}
					t_sb.append(s.charAt(i));
				}

				t_Pin = new int[3 + (t_redN.length() / 2)];
				t_Pin[0] = t_stype;
				t_Pin[1] = t_smul;
				t_Pin[2] = t_redN.length() / 2;
				for (int i = 0; i < t_redN.length(); i += 2) {
					t_Pin[3 + i / 2] = Integer.parseInt(t_redN.substring(i,
							i + 2));
				}
			} else if (t_stype == 54) {// 胆拖
				for (int i = 21 + t_add; i < s.length(); i++) {
					if (s.charAt(i) == '*') {
						t_redN = t_sb.toString();
						t_sb = null;
						t_sb = new StringBuffer("");
						continue;
					}
					if (s.charAt(i) == '^') {
						t_blueN = t_sb.toString();
						t_sb = null;
						t_sb = new StringBuffer("");
						break;
					}
					t_sb.append(s.charAt(i));
				}

				t_Pin = new int[4 + (t_redN.length() / 2)
						+ (t_blueN.length() / 2)];
				t_Pin[0] = t_stype;
				t_Pin[1] = t_smul;
				t_Pin[2] = t_redN.length() / 2;
				t_Pin[3] = t_blueN.length() / 2;
				for (int i = 0; i < t_redN.length(); i += 2) {
					t_Pin[4 + i / 2] = Integer.parseInt(t_redN.substring(i,
							i + 2));
				}
				for (int i = 0; i < t_blueN.length(); i += 2) {
					t_Pin[4 + t_Pin[2] + i / 2] = Integer.parseInt(t_blueN
							.substring(i, i + 2));
				}
			}

		} else if (Constant.QLC.substring(1).equals(type)) {
			if (t_stype == 0) {
				t_Pin = new int[9];
				t_Pin[0] = t_stype;
				t_Pin[1] = t_smul;

				for (int i = 0; i < 7; i++) {
					// String s1="1512-F47102-00-01-这是单式0001 01020304050607";
					t_Pin[i + 2] = Integer.parseInt(s.substring(22 + i * 2,
							24 + 2 * i));
				}

			}
			if (t_stype == 10) {

				int fu = s.indexOf("*");
				int len = s.length();
				int chang = ((len - 1) - (fu + 1) + 1) / 2;
				t_Pin = new int[chang + 2];
				t_Pin[0] = t_stype;
				t_Pin[1] = t_smul;

				for (int i = 0; i < chang; i++) {
					// s1="1512-F47102-00-01-前面18个字符    这是复式码1001*01020304050607080910^";
					t_Pin[i + 2] = Integer.parseInt(s.substring(fu + 1 + i * 2,
							fu + 3 + 2 * i));
				}

			}
			if (t_stype == 20) {
				int len = s.length();
				int dan = s.indexOf("*");
				int danma = ((dan - 1) - 22 + 1) / 2;
				int tuoma = ((len - 1) - (dan + 1) + 1) / 2;
				t_Pin = new int[danma + tuoma + 2 + 1];
				t_Pin[0] = t_stype;
				t_Pin[1] = t_smul;
				for (int i = 0; i < danma; i++) {
					// s1="1512-F47102-00-01- 这是胆拖码2001010203*04050607080910^";
					t_Pin[i + 2] = Integer.parseInt(s.substring(22 + i * 2,
							24 + i * 2));
				}
				t_Pin[danma + 2] = -1;// 区分胆码和托码的标志

				for (int i = 0; i < tuoma; i++) {
					// s1="1512-F47102-00-01- 这是胆拖码2001010203*04050607080910^";
					t_Pin[i + 2 + danma + 1] = Integer.parseInt(s.substring(dan
							+ 1 + i * 2, dan + 3 + i * 2));
				}
			}

		}
		return t_Pin;
	}

	/**
	 *  数字不足10在其前面补“0”
	 * @param num
	 *            传入的数字
	 * @return 补完0的字符串
	 * 
	 */
	public static String getBuZero(int num) {
		String a = "";
		if (num < 10) {
			a = "0" + num;
		} else {
			a = num + "";
		}
		return a;
	}

	/**
	 *
	 * 得到时时彩的玩法
	 * @param wf 玩法代码
	 * @param xuan 玩法代码的具体选择
	 * @return 玩法
	 * 
	 */
	public static String[] getWFInfo(String wf, String xuan) {
		String[] rewf = new String[2];
		rewf[0] = "";
		rewf[1] = "";

		if (wf.equals("0")) {
			rewf[0] = "单式";
			if (xuan.equals("0")) {
				rewf[1] = "一星";
			}
			if (xuan.equals("1")) {
				rewf[1] = "二星";
			}
			if (xuan.equals("2")) {
				rewf[1] = "三星";
			}
			if (xuan.equals("3")) {
				rewf[1] = "四星";
			}
			if (xuan.equals("4")) {
				rewf[1] = "五星";
			}

		}
		if (wf.equals("1")) {
			rewf[0] = "复式";
			if (xuan.equals("1")) {
				rewf[1] = "二星";
			}
			if (xuan.equals("2")) {
				rewf[1] = "三星";
			}
			if (xuan.equals("3")) {
				rewf[1] = "四星";
			}
			if (xuan.equals("4")) {
				rewf[1] = "五星";
			}

		}
		if (wf.equals("2")) {
			rewf[0] = "位选";
			if (xuan.equals("0")) {
				rewf[1] = "一星";
			}
			if (xuan.equals("1")) {
				rewf[1] = "二星";
			}
			if (xuan.equals("2")) {
				rewf[1] = "三星";
			}
			if (xuan.equals("3")) {
				rewf[1] = "四星";
			}
			if (xuan.equals("4")) {
				rewf[1] = "五星";
			}

		}
		if (wf.equals("3")) {
			rewf[0] = "大小奇偶";
			if (xuan.equals("1")) {
				rewf[1] = "固定二星";
			}

		}
		if (wf.equals("4")) {
			rewf[0] = "组选单式";
			if (xuan.equals("1")) {
				rewf[1] = "二星组选";
			}
			if (xuan.equals("2")) {
				rewf[1] = "三星组三";
			}
			if (xuan.equals("3")) {
				rewf[1] = "三星组六";
			}
			if (xuan.equals("4")) {
				rewf[1] = "四星组四";
			}
			if (xuan.equals("5")) {
				rewf[1] = "四星组选六";
			}
			if (xuan.equals("6")) {
				rewf[1] = "四星组选十二";
			}
			if (xuan.equals("7")) {
				rewf[1] = "四星组选二十四";
			}
			if (xuan.equals("8")) {
				rewf[1] = "五星组选五";
			}
			if (xuan.equals("9")) {
				rewf[1] = "五星组选十";
			}
			if (xuan.equals("a")) {
				rewf[1] = "五星组选二十";
			}
			if (xuan.equals("b")) {
				rewf[1] = "五星组选三十";
			}
			if (xuan.equals("c")) {
				rewf[1] = "五星组选六十";
			}
			if (xuan.equals("d")) {
				rewf[1] = "五星组选一百二十";
			}
		}
		// 任2
		if (wf.equals("5")) {
			rewf[0] = "五星通选";
			if (xuan.equals("4")) {
				rewf[1] = "固定五星";
			}
		}
		if (wf.equals("6")) {
			rewf[0] = "组选复式";
			if (xuan.equals("1")) {
				rewf[1] = "二星";
			}
			if (xuan.equals("2")) {
				rewf[1] = "三星组选三";
			}
			if (xuan.equals("3")) {
				rewf[1] = "三星组选六";
			}
			if (xuan.equals("4")) {
				rewf[1] = "四星组选4复式";
			}
			if (xuan.equals("4")) {
				rewf[1] = "四星组选4复式";
			}
			if (xuan.equals("5")) {
				rewf[1] = "四星组选6复式";
			}
			if (xuan.equals("6")) {
				rewf[1] = "四星组选12复式";
			}
			if (xuan.equals("7")) {
				rewf[1] = "四星组选24复式";
			}
			if (xuan.equals("8")) {
				rewf[1] = "五星星组选5复式";
			}
			if (xuan.equals("9")) {
				rewf[1] = "五星组选10复式";
			}
			if (xuan.equals("a")) {
				rewf[1] = "五星组选20复式";
			}
			if (xuan.equals("b")) {
				rewf[1] = "五星组选30复式";
			}
			if (xuan.equals("c")) {
				rewf[1] = "五星组选60复式";
			}
			if (xuan.equals("d")) {
				rewf[1] = "五星组选120复式";
			}

		}
		if (wf.equals("7")) {
			rewf[0] = "组选包点";
			if (xuan.equals("1")) {
				rewf[1] = "二星组选包点";
			}
			if (xuan.equals("2")) {
				rewf[1] = "三星组三包点";
			}
			if (xuan.equals("3")) {
				rewf[1] = "三星组六包点";
			}
		}
		if (wf.equals("8")) {
			if (xuan.equals("1")) {
				rewf[1] = "二星";
			}
		}
		if (wf.equals("9")) {
			rewf[0] = "二星组选分位";
			if (xuan.equals("1")) {
				rewf[1] = "二星";
			}
		}
		if (wf.equals("a")) {
			rewf[0] = "保点";
			if (xuan.equals("1")) {
				rewf[1] = "二星";
			}
			if (xuan.equals("2")) {
				rewf[1] = "三星";
			}
		}
		if (wf.equals("b")) {
			rewf[0] = "三星包对";
			if (xuan.equals("0")) {
				rewf[1] = "固定";
			}
		}
		if (wf.equals("c")) {
			rewf[0] = "组选胆拖";
			if (xuan.equals("4")) {
				rewf[1] = "四星组选4胆拖";
			}
			if (xuan.equals("5")) {
				rewf[1] = "四星组选6胆拖";
			}
			if (xuan.equals("6")) {
				rewf[1] = "四星组选12胆拖";
			}
			if (xuan.equals("7")) {
				rewf[1] = "四星组选24胆拖";
			}
			if (xuan.equals("8")) {
				rewf[1] = "五星组选5胆拖";
			}
			if (xuan.equals("9")) {
				rewf[1] = "五星组选10胆拖";
			}
			if (xuan.equals("a")) {
				rewf[1] = "五星组选20胆拖";
			}
			if (xuan.equals("b")) {
				rewf[1] = "五星组选30胆拖";
			}
			if (xuan.equals("c")) {
				rewf[1] = "五星组选60胆拖";
			}
			if (xuan.equals("d")) {
				rewf[1] = "五星组选120胆拖";
			}
		}
		if (wf.equals("d")) {
			rewf[0] = "任选";
			if (xuan.equals("0")) {
				rewf[1] = "任选一";
			}
			if (xuan.equals("1")) {
				rewf[1] = "任选二";
			}
			if (xuan.equals("2")) {
				rewf[1] = "任选三";
			}
			if (xuan.equals("3")) {
				rewf[1] = "任选二包点";
			}
			if (xuan.equals("4")) {
				rewf[1] = "任选三包点";
			}
			if (xuan.equals("5")) {
				rewf[1] = "任选二跨度";
			}
			if (xuan.equals("6")) {
				rewf[1] = "任选三跨度";
			}
			if (xuan.equals("7")) {
				rewf[1] = "任选二位选";
			}
			if (xuan.equals("2")) {
				rewf[1] = "任选三位选";
			}
		}
		if (wf.equals("e")) {
			rewf[0] = "趣味/区间";
			if (xuan.equals("0")) {
				rewf[1] = "趣味二星";
			}
			if (xuan.equals("1")) {
				rewf[1] = "区间二星";
			}
			if (xuan.equals("2")) {
				rewf[1] = "趣味二星包点";
			}
			if (xuan.equals("3")) {
				rewf[1] = "区间二星包点";
			}
			if (xuan.equals("4")) {
				rewf[1] = "趣味二星位选";
			}
			if (xuan.equals("5")) {
				rewf[1] = "区间二星位选";
			}
		}

		if (wf.equals("f")) {
			rewf[0] = "五星组选重号";
			if (xuan.equals("0")) {
				rewf[1] = "好事成双";
			}
			if (xuan.equals("1")) {
				rewf[1] = "三星报喜";
			}
			if (xuan.equals("2")) {
				rewf[1] = "四季发财";
			}
		}

		return rewf;
	}
    
	/**
	 * 
	 * 时时彩查找百位、十位、百位
	 * @param wz 具体位置
	 * @return 具体是哪位
	 * 
	 */
	public static String getWZInfo(String wz) {
		String rewz = "";

		if (wz.equals("01")) {
			rewz = "个位";
		}
		if (wz.equals("02")) {
			rewz = "十位";
		}
		if (wz.equals("04")) {
			rewz = "百位";
		}
		if (wz.equals("08")) {
			rewz = "千位";
		}
		if (wz.equals("16")) {
			rewz = "万位";
		}
		// 任2
		if (wz.equals("03")) {
			rewz = "十个";
		}
		if (wz.equals("05")) {
			rewz = "百个";
		}
		if (wz.equals("06")) {
			rewz = "百十";
		}
		if (wz.equals("09")) {
			rewz = "千个";
		}
		if (wz.equals("10")) {
			rewz = "千十";
		}
		if (wz.equals("12")) {
			rewz = "千百";
		}
		if (wz.equals("17")) {
			rewz = "万个";
		}
		if (wz.equals("18")) {
			rewz = "万十";
		}
		if (wz.equals("20")) {
			rewz = "万百";
		}
		if (wz.equals("24")) {
			rewz = "万千";
		}

		// 任选3位
		if (wz.equals("07")) {
			rewz = "百十个";
		}
		if (wz.equals("11")) {
			rewz = "千十个";
		}
		if (wz.equals("13")) {
			rewz = "千百个";
		}
		if (wz.equals("14")) {
			rewz = "千百十";
		}
		if (wz.equals("19")) {
			rewz = "万十个";
		}
		if (wz.equals("21")) {
			rewz = "万百个";
		}
		if (wz.equals("22")) {
			rewz = "万百十";
		}
		if (wz.equals("25")) {
			rewz = "万千个";
		}
		if (wz.equals("26")) {
			rewz = "万千十";
		}
		if (wz.equals("28")) {
			rewz = "万千百";
		}
		return rewz;
	}

	/**
	 * 
	 * 用于解析时时彩的注码
	 * 
	 * @param s 注码信息
	 * @return 解析后时时彩的注码
	 */
	public static ArrayList<String> getSSCInfo(String s) {
		// System.out.println(s);
		String type = null;// 彩种
		String t_stype = null;// 玩法
		String t_selecttype = null;// 选码类型
		String t_smul = null;// 倍数
		ArrayList<String> t_Pin = new ArrayList<String>();
		int t_add = 1;
		type = s.substring(5 + t_add, 10 + t_add);
		t_stype = s.substring(17 + t_add, 18 + t_add);
		t_selecttype = s.substring(18 + t_add, 19 + t_add);
		t_smul = s.substring(19 + t_add, 23 + t_add);

		if (type.equals(Constant.SSC.substring(0))) {
			if (t_stype.equals("c")) {
				int len = s.length();
				int dan = s.indexOf("*");
				int danma = ((dan - 1) - 24 + 1) / 2;
				int tuoma = ((len - 1) - (dan + 1) + 1) / 2;
				t_Pin.add(t_stype);
				t_Pin.add(t_selecttype);
				t_Pin.add(t_smul);
				for (int i = 0; i < danma; i++) {
					// String
					// s1="1512-F47102-00-01- 这是胆拖码2001010203*04050607080910^";

					t_Pin.add(s.substring(24 + i * 2, 26 + i * 2));
				}
				t_Pin.add("拖码：");// 区分胆码和托码的标志

				for (int i = 0; i < tuoma; i++) {
					// String
					// s1="1512-F47102-00-01- 这是胆拖码2001010203*04050607080910^";

					t_Pin.add(s.substring(dan + 1 + i * 2, dan + 3 + i * 2));
				}
			} else {

				t_Pin.add(t_stype);
				t_Pin.add(t_selecttype);
				t_Pin.add(t_smul);
				int chang = (s.length() - 24) / 2;
				for (int i = 0; i < chang; i++) {
					t_Pin.add(s.substring(24 + i * 2, 24 + 2 + i * 2));
				}
			}
		}

		return t_Pin;
	}

	/**
	 * 
	 * 时时彩的返回内容
	 * @param s 注码
	 * @return 新的注码内容
	 * 
	 */
	public static String getSSCString(String s) {
		ArrayList<String> list = getSSCInfo(s);
		StringBuffer result = new StringBuffer("");
		String[] wfinfo = getWFInfo(list.get(0), list.get(1));

		result.append("选码类型：" + wfinfo[1]);
		if (list.get(0).equals("c")) {
			result.append("胆码：");
			for (int i = 3; i < list.size(); i++) {
				result.append(list.get(i));
			}
		} else if (list.get(0).equals("e")) {
			if (list.get(1).equals("0") || list.get(1).equals("2")) {
				if (list.get(3).equals("00")) {
					result.append("百位选小号");
				}
				if (list.get(3).equals("01")) {
					result.append("百位选大号");
				}

			}
			if (list.get(1).equals("1") || list.get(1).equals("3")) {
				if (list.get(3).equals("00")) {
					result.append("百位选一区");
				}
				if (list.get(3).equals("01")) {
					result.append("百位选二区");
				}
				if (list.get(3).equals("02")) {
					result.append("百位选三区");
				}
				if (list.get(3).equals("03")) {
					result.append("百位选四区");
				}
				if (list.get(3).equals("04")) {
					result.append("百位选五区");
				}

			}
			result.append("注码：" + list.get(4));
		} else {
			result.append("注码：");
			for (int i = 3; i < list.size(); i++) {
				result.append(list.get(i) + ",");
			}
		}

		result.append(wfinfo[0]);// 玩法
		result.append(Integer.valueOf(list.get(2)) + "倍 ");// 倍数

		return result.toString();
	}
	
	/**
	 * 
	 * 福彩注码解析返回所要注码格式
	 * @param lotNo 彩种
	 * @param betCode 注码
	 * @return jsonObject
	 */
	public static JSONObject getCodeString(String lotNo,String betCode,String lotmulti){
		JSONObject jsonObject = null;
		try {
			//双色球
			if(lotNo.equals(Constant.SSQ) || lotNo.equals(Constant.SSQ_DM)){
				jsonObject = getSSQCodeString(betCode);
				return jsonObject;
				
			//福彩3D
			}else if(lotNo.equals(Constant.SD) || lotNo.equals(Constant.SD_DM)){
				jsonObject = getSDCodeString(betCode);
				return jsonObject;
				
			//七乐彩
			}else if(lotNo.equals(Constant.QLC) || lotNo.equals(Constant.QLC_DM)){
				jsonObject = getQLCCodeString(betCode);
				return jsonObject;
			
//			//时时彩
//			}else if(Constant.SSC_DM.equals(lotNo) || Constant.SSC.equals(lotNo)){
//				jsonObject = getSSCCodeString(betCode);
//				return jsonObject;
				
			//体彩
			}else if(lotNo.equals(Constant.PLS) || lotNo.equals(Constant.PLS_DM) 
					|| lotNo.equals(Constant.DLT ) || lotNo.equals(Constant.DLT_DM)
					|| lotNo.equals(Constant.PLW ) || lotNo.equals(Constant.PLW_DM)
					|| lotNo.equals(Constant.QXC ) || lotNo.equals(Constant.QXC_DM)
					|| lotNo.equals(Constant.SFC14) || lotNo.equals(Constant.SFC14_DM) 
					|| lotNo.equals(Constant.SFC9) || lotNo.equals(Constant.SFC9_DM)
					|| lotNo.equals(Constant.JQC) || lotNo.equals(Constant.JQC_DM)
					|| lotNo.equals(Constant.BQC) || lotNo.equals(Constant.BQC_DM)
					|| lotNo.equals(SSCConstant.SSC) || lotNo.equals(SSCConstant.SSC_DM)
					|| lotNo.equals(Constant.SYXW) || lotNo.equals(Constant.SYXW_DM) || lotNo.equals(Constant.SYYDJ ) ||lotNo.equals(Constant.EEXW) ){
				
				jsonObject = CodeUtil.getBetcode(lotNo, betCode,lotmulti);
				return jsonObject;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("解析后的注码为:"+jsonObject+";注码不合法；注码betcode=" + betCode + e.toString());
		}
		return new JSONObject();
	}
	
	/**
	 * 
	 * 得到排列三注码返回玩法
	 * @param betcode
	 * @return 体彩排三的玩法
	 */
	public static String getPLSMethod(String betcode) {
		String gameMethod = "";
		String[] array = betcode.split("\\|");
		if (array[0].equals("1")) {
			gameMethod = "直选";
		} else if (array[0].equals("6")) {
			gameMethod = "组选";
		} else if (array[0].equals("S1")) {
			gameMethod = "直选和值";
		} else if (array[0].equals("S9")) {
			gameMethod = "组选和值";
		} else if (array[0].equals("S3")) {
			gameMethod = "组三和值";
		} else if (array[0].equals("S6")) {
			gameMethod = "组六和值";
		} else if (array[0].equals("F3")) {
			gameMethod = "组三包号";
		} else if (array[0].equals("F6")) {
			gameMethod = "组六包号";
		}
		return gameMethod;
	}
	
	/**
	 * 
	 * 体彩注码解析返回注码
	 * @param betcode
	 * @return 解析后的注码
	 */
	public static JSONObject getBetcode(String lotNo, String betcode,String multiple) {
		JSONObject obj = new JSONObject();		
		//判断是否有；兼容前面的注码
		if (betcode.indexOf(";") > -1) {
			String[] array = betcode.split("\\;");
			StringBuffer stb = new StringBuffer();
			
			//排列三
			if (lotNo.equals(Constant.PLS) || lotNo.equals(Constant.PLS_DM)) {
				for (int i = 0; i < array.length; i++) {
					if (array[i].indexOf("|") > -1) {
						String arr[] = array[i].split("\\|");
						if(arr[0].indexOf("_")>-1){
							String a[] = arr[0].split("\\_");
							obj.put("multiple", a[0]);
							obj.put("wanfa", a[1]);
						}else{
							obj.put("multiple", multiple);
							obj.put("wanfa", arr[0]);
						}
						stb.append(arr[1]+"<br/>");
					}else{
						stb.append(array[i]+"<br/>");
					}
				}
				obj.put("betcode", stb.toString());

			//
			}else if ( lotNo.equals(SSCConstant.SSC)) {
				for (int i = 0; i < array.length; i++) {
					if (array[i].indexOf("|") > -1) {
						String arr[] = array[i].split("\\|");
						if(arr[0].indexOf("_")>-1){
							String a[] = arr[0].split("\\_");
							obj.put("multiple", a[0]);
							obj.put("wanfa", getWanfa(array[i],lotNo).get("wanfa"));
						}else{
							obj.put("multiple", multiple);
							obj.put("wanfa", getWanfa(array[i],lotNo).get("wanfa"));
						}
						stb.append(getSSCCode(arr[1],obj.getString("wanfa"))+"<br/>");
					}else{
						stb.append(getSSCCode(array[i],obj.getString("wanfa"))+"<br/>");
					}
				}
				obj.put("betcode", stb.toString());
				
			//大乐透
			} else if (lotNo.equals(Constant.DLT) || lotNo.equals(Constant.DLT_DM)) {
				if(betcode.indexOf("_")>-1){
					String arr[] = betcode.split("\\_");
					obj.put("multiple", arr[0]);
					//大乐透分解功能
					dltBetcodeResult(arr[1], obj);
				}else{
					obj.put("multiple", multiple);
					dltBetcodeResult(betcode, obj);
				}
			//十一选五
			} else if(lotNo.equals(Constant.SYXW) || lotNo.equals(Constant.SYXW_DM)){
				for(String betcode1 : array){
					if (betcode1.indexOf("|") > -1) {
						String arr[] = betcode1.split("\\|");
						obj.put("betcode",arr[1]+"<br/>");
					}else{
						obj.put("betcode",betcode1+"<br/>");
					}
					obj.put("wanfa",getWanfa(betcode1,lotNo).get("wanfa"));
					obj.put("multiple", multiple);
				}
			//十一运夺金	
			} else if(lotNo.equals(Constant.SYYDJ )){
				for(String betcode1 : array){
					if (betcode1.indexOf("@") > -1) {
						String arr[] = betcode1.split("\\@");
						if(arr[1].substring(0, 1).equals("*")){
							arr[1]=arr[1].substring(1, arr[1].length());
						}
						if(arr[1].indexOf("*")!=-1){
							arr[1]=arr[1].replace("*", ",");
						}
						obj.put("betcode",arr[1].replace("^", "")+"<br/>");
					}else{
						if(betcode1.substring(0, 1).equals("*")){
							betcode1=betcode1.substring(1, betcode1.length());
						}
						if(betcode1.indexOf("*")!=-1){
							betcode1=betcode1.replace("*", ",");
						}
						obj.put("betcode",betcode1.replace("^", "")+"<br/>");
					}
					obj.put("wanfa",getWanfa(betcode1,lotNo));
					obj.put("multiple", multiple);
				}
				
			}else if(lotNo.equals(Constant.EEXW)){//22选5
				for(String betcode1 : array){
					if (betcode1.indexOf("@") > -1) {
						String arr[] = betcode.split("\\@");
						if("2".equals(arr[0])||arr[1].indexOf("*")>-1){//胆拖
							String code[] =arr[1].replace("^", "").split("\\*");
							String newcode1 ="";
							for(int i=0;i<code[0].length();i+=2 ){
								newcode1+=code[0].subSequence(i, i+2)+"，";
							}
							String newcode2 ="";
							for(int i=0;i<code[1].length();i+=2 ){
								newcode2+=code[1].subSequence(i, i+2)+"，";
							}
							obj.put("danma",newcode1.substring(0, newcode1.length()-1));
							obj.put("tuoma",newcode2.substring(0, newcode2.length()-1));
						}else{
						
						String code[] =arr[1].split("\\^");
						String newcode ="";
						for(int i=0;i<code[0].length();i+=2 ){
							newcode+=code[0].subSequence(i, i+2)+"，";
						}
						obj.put("betcode",newcode.substring(0, newcode.length()-1));
						}
						obj.put("multiple", betcode.split("\\^").length);
						obj.put("wanfa",arr[0]);
					}
				}
			}//足彩注码解析
			else if (lotNo.equals(Constant.SFC14) || lotNo.equals(Constant.SFC14_DM) 
					|| lotNo.equals(Constant.SFC9) || lotNo.equals(Constant.SFC9_DM)
					|| lotNo.equals(Constant.JQC) || lotNo.equals(Constant.JQC_DM)
					|| lotNo.equals(Constant.BQC) || lotNo.equals(Constant.BQC_DM)) {
				obj.put("multiple", multiple);
				obj.put("wanfa",getZCBetcode(lotNo,betcode));
			
			//其他彩种注码解析
			}else{
				if(betcode.indexOf("_")>-1){
					String arr[] = betcode.split("\\_");
					obj.put("multiple", arr[0]);
					obj.put("betcode", arr[1]+"<br/>");
				}else{
					if (betcode.indexOf("|") > -1) {
						String arr[] = betcode.split("\\|");
						String a[] = arr[0].split("\\_");
						obj.put("wanfa", a[1]);
					}
					obj.put("multiple", multiple);
					if(lotNo.equals(Constant.PLW)||lotNo.equals(Constant.PLW_DM)||lotNo.equals(Constant.QXC)||lotNo.equals(Constant.QXC_DM)){
					obj.put("betcode",betcode.replace(",", "|")+"<br/>");
					}else{
						obj.put("betcode", betcode+"<br/>");
					}
				}
			}

		} else {
			//排列三   
			if (lotNo.equals(Constant.PLS) || lotNo.equals(Constant.PLS_DM)) {
				//取到玩法
				JSONObject value = getPLSBetcode(betcode,multiple);
				obj.put("multiple", getBackValue("multiple", value));
				obj.put("wanfa", getBackValue("wanfa", value));
				obj.put("betcode", value.getString("betcode"));

				//
			} else if (lotNo.equals(SSCConstant.SSC)) {
				//取到玩法
				//分隔注码和玩法
				String array[] = betcode.split("\\|");
				String arr[] = array[0].split("\\_");
				//判断下划线是否存在
				if(array[0].indexOf("_")>-1){
					obj.put("multiple", arr[0]);
					obj.put("wanfa", getWanfa(betcode,lotNo).get("wanfa"));
				}else{
					obj.put("multiple", multiple);
					obj.put("wanfa", getWanfa(betcode,lotNo).get("wanfa"));
				}
				obj.put("betcode", array[1]+"<br/>");
				if("大小单双".equals(obj.getString("wanfa"))){
					obj.getString("betcode").replace("1","大").replace("2","小").replace("4","双").replace("5","单");
				}
				//大乐透
			} else if (lotNo.equals(Constant.DLT) || lotNo.equals(Constant.DLT_DM)) {
				if(betcode.indexOf("_")>-1){
					String arr[] = betcode.split("\\_");
					obj.put("multiple", arr[0]);
					//调用大乐透注码分解功能
					dltBetcodeResult(arr[1], obj);
				}else{
					obj.put("multiple", multiple);
					dltBetcodeResult(betcode, obj);
				}
			//十一选五	
			}else if(lotNo.equals(Constant.SYXW) || lotNo.equals(Constant.SYXW_DM)){
				if (betcode.indexOf("|") > -1) {
					String arr[] = betcode.split("\\|");
					obj.put("betcode",arr[1].replace(";", ""));
				}else{
					obj.put("betcode",betcode.replace(";", ""));
				}
				obj.put("wanfa",getWanfa(betcode,lotNo).get("wanfa"));
				obj.put("multiple", multiple);
				//十一运夺金
			}else if(lotNo.equals(Constant.SYYDJ )){
				if (betcode.indexOf("@") > -1) {
					String arr[] = betcode.split("@");
					if(arr[1].substring(0, 1).equals("*")){
						//复式
						String single="";
						for(int i=1;i<arr[1].length()-1;){
							single += arr[1].substring(i, i+2) + " ";
							i+=2;
						}
						arr[1]=single.substring(0, single.length()-1);
					}else if(arr[1].indexOf("*")!=-1){
						//定位复式(142、162)
						if(arr[0].equals("142")||arr[0].equals("162")){
							arr[1] = arr[1].replace("*", ",");
							String num[] = arr[1].split(",");
							String num1 = "";
							String othernum = "";
							String lastnum = "";
							for(int i=0;i<num[0].length()-3;){
								num1 += num[0].substring(i, i+2) + " ";
								i+=2;
							}
							for(int n=1;n<num.length-1;n++){
								othernum += num[n] + ",";
							}
							othernum = num[0].substring(num[0].length()-2,num[0].length())+","+othernum+num[num.length-1].substring(0,2);
							for(int i=2;i<num[num.length-1].length()-1;){
								lastnum += num[num.length-1].substring(i, i+2) + " ";
								i+=2;
							}
							arr[1]=num1+othernum+lastnum;
						}else{
							//胆拖
							
							arr[1] = arr[1].replace("*", ",");
							String num[] = arr[1].split(",");
							String before="";
							String after="";
							for(int i=0;i<num[0].length()-1;){
								before += num[0].substring(i, i+2) + " ";
								i+=2;
							}
							for(int i=0;i<num[1].length()-1;){
								after += num[1].substring(i, i+2) + " ";
								i+=2;
							}
							arr[1]=before+"$"+after;
						}
					}else{
						//单式
						String single="";
						for(int i=0;i<arr[1].length()-1;){
							single += arr[1].substring(i, i+2) + " ";
							i+=2;
						}
						arr[1]=single.substring(0, single.length()-1);
					}
					obj.put("betcode",arr[1].replace("^", ""));
				}else{
					if(betcode.substring(0, 1).equals("*")){
						betcode=betcode.substring(1, betcode.length());
					}
					if(betcode.indexOf("*")!=-1){
						betcode=betcode.replace("*", ",");
					}
					obj.put("betcode",betcode.replace("^", ""));
				}
				obj.put("wanfa",getWanfa(betcode,lotNo).get("wanfa"));
				obj.put("multiple", multiple);
			}else if(lotNo.equals(Constant.EEXW)){//22选5
					if (betcode.indexOf("@") > -1) {
						String arr[] = betcode.split("\\@");
						if("2".equals(arr[0])||arr[1].indexOf("*")>-1){//胆拖
							String code[] =arr[1].replace("^", "").split("\\*");
							String newcode1 ="";
							for(int i=0;i<code[0].length();i+=2 ){
								newcode1+=code[0].subSequence(i, i+2)+"，";
							}
							String newcode2 ="";
							for(int i=0;i<code[1].length();i+=2 ){
								newcode2+=code[1].subSequence(i, i+2)+"，";
							}
							obj.put("danma",newcode1.substring(0, newcode1.length()-1));
							obj.put("tuoma",newcode2.substring(0, newcode2.length()-1));
						}else{
						
						String code[] =arr[1].split("\\^");
						String newcode ="";
						for(int i=0;i<code[0].length();i+=2 ){
							newcode+=code[0].subSequence(i, i+2)+"，";
						}
						obj.put("betcode",newcode.substring(0, newcode.length()-1));
						}
						obj.put("multiple", betcode.split("\\^").length);
						obj.put("wanfa",arr[0]);
					}
			}
			else if (lotNo.equals(Constant.SFC14) || lotNo.equals(Constant.SFC14_DM) 
					|| lotNo.equals(Constant.SFC9) || lotNo.equals(Constant.SFC9_DM)
					|| lotNo.equals(Constant.JQC) || lotNo.equals(Constant.JQC_DM)
					|| lotNo.equals(Constant.BQC) || lotNo.equals(Constant.BQC_DM)) {
				obj.put("multiple", multiple);
				obj.put("wanfa",getZCBetcode(lotNo,betcode));
						
				//其他彩种注码解析
			}else{
				if(betcode.indexOf("_")>-1){
					String arr[] = betcode.split("\\_");
					obj.put("multiple", arr[0]);
					if(lotNo.equals(Constant.PLW)||lotNo.equals(Constant.PLW_DM)||lotNo.equals(Constant.QXC)||lotNo.equals(Constant.QXC_DM)){
						obj.put("betcode",arr[1].replace(",", "|")+"<br/>");
						}else{
							obj.put("betcode", arr[1]+"<br/>");
						}
				}else{
					obj.put("multiple", multiple);
					if(lotNo.equals(Constant.PLW)||lotNo.equals(Constant.PLW_DM)||lotNo.equals(Constant.QXC)||lotNo.equals(Constant.QXC_DM)){
						obj.put("betcode",betcode.replace(",", "|")+"<br/>");
						}else{
							obj.put("betcode", betcode+"<br/>");
						}
				}
			}
		}
//		System.out.println("体彩注码解析返回:"+obj);
		logger.debug("体彩注码解析返回:"+obj);
		return obj;
	}
	
	/**
	 * 
	 * 大乐透注码解析内容
	 * @param betcode 注码
	 * @param obj json
	 * @return 返回注码
	 */
	private static void dltBetcodeResult(String betcode,JSONObject obj){
		//判断是生肖乐还是其他玩法
		if(betcode.indexOf("-")>-1){
			if (betcode.indexOf("$") > -1) {
				obj.put("wanfa", Constant.DLT_DT);
				// 胆拖解析注码前区胆码和拖码
				String dt[] = betcode.split("\\-");
				if(dt[0].indexOf("$")>-1){
					String dt1[] = dt[0].split("\\$");
					obj.put("qianDanma", dt1[0].replace(" ", ","));
					obj.put("qianTuoma", dt1[1].replace(" ", ","));
				}
				if(dt[1].indexOf("$")>-1){
					//后区胆码和拖码
					String dt2[] = dt[1].split("\\$");
					obj.put("houDanma", dt2[0].replace(" ", ","));
					obj.put("houTuoma", dt2[1].replace(" ", ","));
				}else{
					obj.put("houDanma", dt[1].replace(" ", ","));
				}
			} else {
				String shuzu[] = betcode.split("\\;");
				//单式或复式玩法的注码
				for(int i=0;i<shuzu.length;i++){
					String a[] = shuzu[i].split("\\-");
					if(a[0].replace(" ", "").length()==10 && a[1].replace(" ", "").length()==4){
						obj.put("wanfa", Constant.DLT_DS);
					}else{
						obj.put("wanfa", Constant.DLT_FS);
					}
				}
				obj.put("betcode",betcode.replace(" ",",").replace("-", "|")+"<br/>");					
			}
		}else{
			//生肖乐玩法和注码
			obj.put("wanfa", Constant.DLT_SXL);
			obj.put("betcode",betcode+"<br/>");
		}

	}
	/**
	 * 
	 * 解析排列三的注码 
	 * @param betcode
	 * @return 分割后的注码
	 * 
	 */
	public static JSONObject getPLSBetcode(String betcode,String multiple) {
		//分隔注码和玩法
		String array[] = betcode.split("\\|");
		String arr[] = array[0].split("\\_");
		JSONObject obj = new JSONObject();
		//判断下划线是否存在
		if(array[0].indexOf("_")>-1){
			obj.put("multiple", arr[0]);
			obj.put("wanfa", arr[1]);
		}else{
			obj.put("multiple", multiple);
			obj.put("wanfa", array[0]);
		}
		if("F6".equals(obj.get("wanfa"))){
		String arrs=array[1].replace("",",");
		obj.put("betcode",arrs .substring(1,arrs.length()-1));
		}else if("F3".equals(obj.get("wanfa"))){
			String arrs=array[1].replace("",",");
			obj.put("betcode",arrs .substring(1,arrs.length()-1));
		}else{
		obj.put("betcode",array[1].replace(",","|"));
		}
		return obj;

	}
	
	/**
	 * 得到大乐透的注码--返回玩法
	 * @param betCode
	 * @return 大乐透的玩法
	 */
	public static String getDLTGameMethod(String betCode){
		String gameMethod = "";
		if(betCode.indexOf("-")>-1){
			Object [] arraryObj =betCode.split("\\-");
			if(arraryObj[0].toString().indexOf("$")>-1){
				gameMethod="胆拖投注";
			}else{
				String array = ((String) arraryObj[0]).replaceAll(" ", ""); 
				String array1 = ((String) arraryObj[1]).replaceAll(" ", "").replace(";", ""); 
				if(array.length()==10 && array1.length()==4){
					gameMethod="单式投注";
				}else {
					gameMethod="复式投注";
				}
			}
		}else{
			gameMethod="十二选二";
		}
		return gameMethod;
	}
	
	/**
	 * 
	 * 解析大乐透的注码
	 * @param betcode
	 * @return 解析后的注码
	 * 
	 */
	public static String getDLTBetcode(String betcode){
		StringBuffer sbf = new StringBuffer();
		if (betcode.indexOf("$") > -1) {
			// 胆拖解析注码
			String dt[] = betcode.split("\\-");
			String dt1[] = dt[0].split("\\$");
			sbf.append("前区胆码:" + dt1[0].replace(" ", "") + "前区拖码:"
					+ dt1[1].replace(" ", "") + " ");
			String dt2[] = dt[1].split("\\$");
			sbf.append("后区胆码:" + dt2[0].replace(" ", "")
					+ "后区拖码:" + dt2[1].replace(" ", "") + " ");
		} else {
			sbf.append("注码：");
			sbf.append(betcode);
		}
		return sbf.toString();
	}

	/**
	 * 
	 * 算足彩复式玩法
	 * @param betcode
	 * @return 足彩复式玩法
	 * 
	 */
	public static String getZCMethod(String betcode){
		String method = "";
		String arr[] = betcode.split("\\_");
		String codes[]=arr[1].split("\\,");
		for(int i=0;i<codes.length;i++){
			if(codes[i].length()>1){
				method="复式";	
				break;
			}
		}
		
		return method;
	}
	
	/**
	 * 
	 * 解析足彩的注码
	 * @param betcode
	 * @return 足彩解析后的注码
	 * 
	 */
/*	public static String getZCBetcode(String betcode) {
		StringBuffer sbf = new StringBuffer();
		String dt[] = betcode.split("\\_");
		if (betcode.indexOf("$") > -1) {
			String dt1[] = dt[1].split("\\$");
			sbf.append("胆码:" + dt1[0].replace(" ", "") + "拖码:"
					+ dt1[1].replace(" ", "") + " ");
		}else{
			sbf.append("注码:");
			sbf.append(dt[1]);
		}
		return sbf.toString();
	}*/
	
	public static String getZCBetcode(String lotno,String betcode) {
		StringBuffer sbf = new StringBuffer();
		//String bets[] = betcode.split(",");
		String dt = betcode.replaceAll(",", "");
		//足彩胜负彩
		if(lotno.equals(Constant.SFC14) || lotno.equals(Constant.SFC14_DM)){
			if(dt.length()==14){
				sbf.append("单式");
			}else{
				sbf.append("复式");
			}			
		//任九场	
		}else if(lotno.equals(Constant.SFC9) || lotno.equals(Constant.SFC9_DM)){
			if(dt.length()==14){
				sbf.append("单式");
				
			}else{
				if(dt.indexOf("$")>-1){
					sbf.append("胆拖");
				}
				sbf.append("复式");
			}
		//半全场		
		}else if(lotno.equals(Constant.BQC) || lotno.equals(Constant.BQC_DM)){
			if(dt.length()==12){
				sbf.append("单式");
			}else{
				sbf.append("复式");
			}
		//进球彩
		}else if(lotno.equals(Constant.JQC) || lotno.equals(Constant.JQC_DM)){
			if(dt.length()==8){
				sbf.append("单式");
			}else{
				sbf.append("复式");
			}
		}
		
		return sbf.toString();
	}
		
	/**足彩
	 * 根据注码得到注码中单选、双选、三选的个数
	 * @param betcode
	 * 
	 */
	public static JSONObject getSelectNum(String betcode){
		JSONObject selectNum = new JSONObject();
		if(betcode.indexOf("$")>-1){
			betcode =betcode.replace('$',',');
		}
		String bets[] = betcode.split(",");
		int radio =0;
		int twoselect = 0;
		int threeselect = 0;
		for(int i=0;i<bets.length;i++){
			if(bets[i].indexOf("#")>-1){
				continue;
			}

			if(bets[i].length()==1){radio++;}
			else if(bets[i].length()==2){twoselect++;}
			else{threeselect++;}
		}
		selectNum.put("radio",radio);
		selectNum.put("twoselect",twoselect);
		selectNum.put("threeselect",threeselect);
		return selectNum;
	}
	
	
	
	/**
	 * 
	 *  验证返回的json
	 * @param str 键
	 * @param objValue json
	 * @return 判断是否有传入的键
	 * @throws JSONException
	 */
	public static String getBackValue(String str,JSONObject objValue) throws JSONException{
		String result =  objValue.get(str)==null?"":objValue.getString(str).equals("")?"":objValue.getString(str) ;
		return result.trim();
	}
	
	/**
	 * 根据彩种，将中奖号码的球进行拆分 如：01，02，03
	 * @param play_name  彩种
	 * @param win_base_code  红球号码
	 * @param win_special_code  蓝球号码
	 * @return 拆分完的注码jsonObject
	 */
	public static JSONObject getNewDrawBet(String play_name,String win_base_code,String win_special_code){
		JSONObject prize = new JSONObject();
		//有蓝球的号码2位拆分
		if(Constant.SSQ.equals(play_name)||Constant.SSQ_DM.equals(play_name)
    			||Constant.QLC.equals(play_name)||Constant.QLC_DM.equals(play_name)
    			||Constant.DLT.equals(play_name)||Constant.DLT_DM.equals(play_name)){
			
    		String new_win = new String ();
    		String new_special = new String ();
    		//红球 （大乐透传过来的参数都是通过排序后且中间没有空格的字符串）
    		if(Constant.DLT.equals(play_name)||Constant.DLT_DM.equals(play_name)){
	    		for(int i=0;i<win_base_code.length();i+=2){
	    			new_win = new_win+win_base_code.substring(i,i+2)+",";
	    		}
    		}else{
    			for(int i=0;i<win_base_code.length();i+=2){
	    			new_win = new_win+win_base_code.substring(i,i+2)+",";
	    		}
    		}
    		new_win= new_win.substring(0,new_win.length()-1);
//    		System.out.println("新的红球的开奖号码："+new_win);
    		logger.debug("新的红球的开奖号码："+new_win);
    		
    		//蓝球 （大乐透传过来的参数都是通过排序后且中间没有空格的字符串）
    		if(Constant.DLT.equals(play_name)||Constant.DLT_DM.equals(play_name)){
    			
    			for(int j=0;j<win_special_code.length();j+=2){
	    			new_special = new_special+win_special_code.substring(j,j+2)+",";
//	    			System.out.println("大乐透新的蓝球号码是："+new_special);
	    		}
    			logger.debug("大乐透新的蓝球号码是："+new_special);
    		}else{
    			
	    		for(int j=0;j<win_special_code.length();j+=2){
	    			new_special = new_special+win_special_code.substring(j,j+2)+",";
	    		}
    		}
	    		new_special = new_special.substring(0,new_special.length()-1);
//	    		System.out.println("新的蓝球号码是："+new_special);
	    		logger.debug("新的蓝球号码是："+new_special);
    		
    		prize.put("win_base_code",new_win);
    		prize.put("win_special_code", new_special);
    		
    	}
				
		//仅红球的号码2位拆分
    	if(Constant.SD.equals(play_name)||Constant.SD_DM.equals(play_name)){
    		String new_win = new String ();
    		for(int i=0;i<win_base_code.length();i+=2){
    			new_win = new_win+win_base_code.substring(i+1,i+2)+",";
    		}
    		new_win= new_win.substring(0,new_win.length()-1);
//    		System.out.println("新的开奖号码："+new_win);
    		logger.debug("新的开奖号码："+new_win);
    		prize.put("win_base_code",new_win);
    	}
    	//仅红球的号码1位拆分
    	if(Constant.PLS.equals(play_name)||Constant.PLS_DM.equals(play_name)||
    			Constant.PLW.equals(play_name)||Constant.PLW_DM.equals(play_name)||
    			Constant.QXC.equals(play_name)||Constant.QXC_DM.equals(play_name)){
    		String new_win = new String ();
    		for(int i=0;i<win_base_code.length();i+=1){
    			new_win = new_win+win_base_code.substring(i,i+1)+",";
    		}
    		new_win= new_win.substring(0,new_win.length()-1);
//    		System.out.println("新的开奖号码："+new_win);
    		logger.debug("新的开奖号码："+new_win);
    		prize.put("win_base_code",new_win);
    	}
    	
    	return prize;
	}
	
	/**
	 * 将获奖的注数进行拆分
	 * @param prizeinfo 1,0,0,0,0,0,0,0,0,0,
	 * @return 返回注数内容
	 */
	public static JSONObject getPrizeBet(String prizeinfo){
		JSONObject jsPrizeBet = new JSONObject();
		String newPrize[] = prizeinfo.split(",");
		for(int i=0;i<newPrize.length;i++){
			jsPrizeBet.put("prizeinfo"+i, newPrize[i]);
		} 
//		System.out.println("将获奖的注数进行拆分为："+jsPrizeBet);
		logger.debug("将获奖的注数进行拆分为："+jsPrizeBet);
		return jsPrizeBet;
	}
	
/**
 * 时时彩与江西11选5 的注码解析	
 * @param betCode 
 * @param lotno
 * @return 返回具体的玩法
 */
	public static JSONObject getWanfa (String betCode,String lotno){
		JSONObject zhuma = new JSONObject();
		//江西11选5 玩法解析
		if("T01010".equals(lotno)){
			if(betCode.indexOf(";")>-1){
				String code[] = betCode.split("\\;");
				if(code[0].indexOf("|")>-1){
					String arr[] = code[0].split("\\|");
					if("R1".equals(arr[0])){
						zhuma.put("wanfa","任选一");
						//zhuma.put("code", arr[1]);
					}else if("R2".equals(arr[0])){
						 if(arr[1].indexOf("$")>-1){
						  zhuma.put("wanfa","任选二胆拖");
						  //zhuma.put("code", )
						 }else{
						   zhuma.put("wanfa","任选二");
						 }
					}else if("R3".equals(arr[0])){
						 if(arr[1].indexOf("$")>-1){
							  zhuma.put("wanfa","任选三胆拖");
							  //zhuma.put("code", )
							 }else{
							   zhuma.put("wanfa","任选三");
							 }
					}else if("R4".equals(arr[0])){
						 if(arr[1].indexOf("$")>-1){
							  zhuma.put("wanfa","任选四胆拖");
							  //zhuma.put("code", )
							 }else{
							   zhuma.put("wanfa","任选四");
							 }
					}else if("R5".equals(arr[0])){
						 if(arr[1].indexOf("$")>-1){
							  zhuma.put("wanfa","任选五胆拖");
							  //zhuma.put("code", )
							 }else{
							   zhuma.put("wanfa","任选五");
							 }
					}else if("R6".equals(arr[0])){
						 if(arr[1].indexOf("$")>-1){
							  zhuma.put("wanfa","任选六胆拖");
							  //zhuma.put("code", )
							 }else{
							   zhuma.put("wanfa","任选六");
							 }
					}else if("R7".equals(arr[0])){
						 if(arr[1].indexOf("$")>-1){
							  zhuma.put("wanfa","任选七胆拖");
							  //zhuma.put("code", )
							 }else{
							   zhuma.put("wanfa","任选七");
							 }
					}else if("R8".equals(arr[0])){
						 if(arr[1].indexOf("$")>-1){
							  zhuma.put("wanfa","任选八胆拖");
							  //zhuma.put("code", )
							 }else{
							   zhuma.put("wanfa","任选八");
							 }
					}else if("Z3".equals(arr[0])){
						 if(arr[1].indexOf("$")>-1){
							  zhuma.put("wanfa","前三组胆");
							  //zhuma.put("code", )
							 }else{
							   zhuma.put("wanfa","前三组选");
							 }
					}else if("Z2".equals(arr[0])){
						 if(arr[1].indexOf("$")>-1){
							  zhuma.put("wanfa","前二组胆");
							  //zhuma.put("code", )
							 }else{
							   zhuma.put("wanfa","前二组选");
							 }
					}else if("Q2".equals(arr[0])){
							 zhuma.put("wanfa","前二直选");
							
					}else if("Q3".equals(arr[0])){
							 zhuma.put("wanfa","前三直选");
							
					}
					
			}
			
			
		}else{
			if(betCode.indexOf("|")>-1){
				String arr[] = betCode.split("\\|");
				if("R1".equals(arr[0])){
					zhuma.put("wanfa","任选一");
					//zhuma.put("code", arr[1]);
				}else if("R2".equals(arr[0])){
					 if(arr[1].indexOf("$")>-1){
					  zhuma.put("wanfa","任选二胆拖");
					  //zhuma.put("code", )
					 }else{
					   zhuma.put("wanfa","任选二");
					 }
				}else if("R3".equals(arr[0])){
					 if(arr[1].indexOf("$")>-1){
						  zhuma.put("wanfa","任选三胆拖");
						  //zhuma.put("code", )
						 }else{
						   zhuma.put("wanfa","任选三");
						 }
				}else if("R4".equals(arr[0])){
					 if(arr[1].indexOf("$")>-1){
						  zhuma.put("wanfa","任选四胆拖");
						  //zhuma.put("code", )
						 }else{
						   zhuma.put("wanfa","任选四");
						 }
				}else if("R5".equals(arr[0])){
					 if(arr[1].indexOf("$")>-1){
						  zhuma.put("wanfa","任选五胆拖");
						  //zhuma.put("code", )
						 }else{
						   zhuma.put("wanfa","任选五");
						 }
				}else if("R6".equals(arr[0])){
					 if(arr[1].indexOf("$")>-1){
						  zhuma.put("wanfa","任选六胆拖");
						  //zhuma.put("code", )
						 }else{
						   zhuma.put("wanfa","任选六");
						 }
				}else if("R7".equals(arr[0])){
					 if(arr[1].indexOf("$")>-1){
						  zhuma.put("wanfa","任选七胆拖");
						  //zhuma.put("code", )
						 }else{
						   zhuma.put("wanfa","任选七");
						 }
				}else if("R8".equals(arr[0])){
					 if(arr[1].indexOf("$")>-1){
						  zhuma.put("wanfa","任选八胆拖");
						  //zhuma.put("code", )
						 }else{
						   zhuma.put("wanfa","任选八");
						 }
				}else if("Z3".equals(arr[0])){
					 if(arr[1].indexOf("$")>-1){
						  zhuma.put("wanfa","前三组胆");
						  //zhuma.put("code", )
						 }else{
						   zhuma.put("wanfa","前三组选");
						 }
				}else if("Z2".equals(arr[0])){
					 if(arr[1].indexOf("$")>-1){
						  zhuma.put("wanfa","前二组胆");
						  //zhuma.put("code", )
						 }else{
						   zhuma.put("wanfa","前二组选");
						 }
				}else if("Q2".equals(arr[0])){
						 zhuma.put("wanfa","前二直选");
						
				}else if("Q3".equals(arr[0])){
						 zhuma.put("wanfa","前三直选");
						
				}
				
		}
			
			
		}
			//时时彩 玩法解析
		} else if("T01007".equals(lotno)){
		   if(betCode.indexOf(";")>-1){
			 String code[] = betCode.split("\\;");
	           if(code[0].indexOf("|")>-1){
			 String arr[] = code[0].split("\\|");
			if("5D".equals(arr[0])){
				 zhuma.put("wanfa","五星");
			}else if("3D".equals(arr[0])){
				zhuma.put("wanfa","三星");
			}else if("2D".equals(arr[0])){
				zhuma.put("wanfa","二星");
			}else if("1D".equals(arr[0])){
				zhuma.put("wanfa","一星");
			}else if("5F".equals(arr[0])){
				zhuma.put("wanfa","五星复选");
			}else if("5T".equals(arr[0])){
				zhuma.put("wanfa","五星通选");
			}else if("3F".equals(arr[0])){
				zhuma.put("wanfa","三星复选");
			}else if("2F".equals(arr[0])){
				zhuma.put("wanfa","二星复选");
			}else if("H2".equals(arr[0])){
				zhuma.put("wanfa","二星和值");
			}else if("S2".equals(arr[0])){
				zhuma.put("wanfa","二星组和");
			}else if("DD".equals(arr[0])){
				zhuma.put("wanfa","大小单双");
			}else if("Z2".equals(arr[0])){
				zhuma.put("wanfa","二星组选");
			}else if("F2".equals(arr[0])){
				zhuma.put("wanfa","二星组复");
			}
			}
		   }else{
			   if(betCode.indexOf("|")>-1){
					 String arr[] = betCode.split("\\|");
					if("5D".equals(arr[0])){
						 zhuma.put("wanfa","五星");
					}else if("3D".equals(arr[0])){
						zhuma.put("wanfa","三星");
					}else if("2D".equals(arr[0])){
						zhuma.put("wanfa","二星");
					}else if("1D".equals(arr[0])){
						zhuma.put("wanfa","一星");
					}else if("5F".equals(arr[0])){
						zhuma.put("wanfa","五星复选");
					}else if("5T".equals(arr[0])){
						zhuma.put("wanfa","五星通选");
					}else if("3F".equals(arr[0])){
						zhuma.put("wanfa","三星复选");
					}else if("2F".equals(arr[0])){
						zhuma.put("wanfa","二星复选");
					}else if("H2".equals(arr[0])){
						zhuma.put("wanfa","二星和值");
					}else if("S2".equals(arr[0])){
						zhuma.put("wanfa","二星组和");
					}else if("DD".equals(arr[0])){
						zhuma.put("wanfa","大小单双");
					}else if("Z2".equals(arr[0])){
						zhuma.put("wanfa","二星组选");
					}else if("F2".equals(arr[0])){
						zhuma.put("wanfa","二星组复");
					}
					}
			   
		   }
		}else //十一运夺金 玩法解析
			if("T01012".equals(lotno)){
				if(betCode.indexOf(";")>-1){
					String code[] = betCode.split("\\;");
					if(code[0].indexOf("@")>-1){
						String arr[] = code[0].split("\\@");
						if("101".equals(arr[0])){
							zhuma.put("wanfa","任选一");
						}else if("121".equals(arr[0])){
							zhuma.put("wanfa","任选二胆拖");
						}else if("102".equals(arr[0])){
							zhuma.put("wanfa","任选二复式");
						}else if("111".equals(arr[0])){
							zhuma.put("wanfa","任选二单式");
						}else if("122".equals(arr[0])){
							zhuma.put("wanfa","任选三胆拖");
						}else if("103".equals(arr[0])){
							zhuma.put("wanfa","任选三复式");
						}else if("112".equals(arr[0])){
							zhuma.put("wanfa","任选三单式");
						}else if("123".equals(arr[0])){
							zhuma.put("wanfa","任选四胆拖");
						}else if("104".equals(arr[0])){
							zhuma.put("wanfa","任选四复式");
						}else if("113".equals(arr[0])){
							zhuma.put("wanfa","任选四单式");
						}else if("124".equals(arr[0])){
							zhuma.put("wanfa","任选五胆拖");
						}else if("105".equals(arr[0])){
							zhuma.put("wanfa","任选五复式");
						}else if("114".equals(arr[0])){
							zhuma.put("wanfa","任选五单式");
						}else if("125".equals(arr[0])){
							zhuma.put("wanfa","任选六胆拖");
						}else if("106".equals(arr[0])){
							zhuma.put("wanfa","任选六复式");
						}else if("115".equals(arr[0])){
							zhuma.put("wanfa","任选六单式");
						}else if("126".equals(arr[0])){
							zhuma.put("wanfa","任选七胆拖");
						}else if("107".equals(arr[0])){
							zhuma.put("wanfa","任选七复式");
						}else if("116".equals(arr[0])){
							zhuma.put("wanfa","任选七单式");
						}else if("117".equals(arr[0])){
							zhuma.put("wanfa","任选八");
						}else if("153".equals(arr[0])){
							zhuma.put("wanfa","前三组胆");
						}else if("109".equals(arr[0])){
							zhuma.put("wanfa","前三组选复式");
						}else if("151".equals(arr[0])){
							zhuma.put("wanfa","前三组选单式");
						}else if("133".equals(arr[0])){
							zhuma.put("wanfa","前二组胆");
						}else if("108".equals(arr[0])){
							zhuma.put("wanfa","前二组选复式");
						}else if("131".equals(arr[0])){
							zhuma.put("wanfa","前二组选单式");
						}else if("144".equals(arr[0])){
							zhuma.put("wanfa","前二直选复式");
						}else if("141".equals(arr[0])){
							zhuma.put("wanfa","前二直选单式");
						}else if(arr[0]=="164"){
							zhuma.put("wanfa","前三直选复式");
						}else if(arr[0]=="161"){
							zhuma.put("wanfa","前三直选单式");
						}
						
				}
				
				
			}else{
				if(betCode.indexOf("@")>-1){
					String arr[] = betCode.split("\\@");
					if("101".equals(arr[0])){
						zhuma.put("wanfa","任选一");
					}else if("121".equals(arr[0])){
						zhuma.put("wanfa","任选二胆拖");
					}else if("102".equals(arr[0])){
						zhuma.put("wanfa","任选二复式");
					}else if("111".equals(arr[0])){
						zhuma.put("wanfa","任选二单式");
					}else if("122".equals(arr[0])){
						zhuma.put("wanfa","任选三胆拖");
					}else if("103".equals(arr[0])){
						zhuma.put("wanfa","任选三复式");
					}else if("112".equals(arr[0])){
						zhuma.put("wanfa","任选三单式");
					}else if("123".equals(arr[0])){
						zhuma.put("wanfa","任选四胆拖");
					}else if("104".equals(arr[0])){
						zhuma.put("wanfa","任选四复式");
					}else if("113".equals(arr[0])){
						zhuma.put("wanfa","任选四单式");
					}else if("124".equals(arr[0])){
						zhuma.put("wanfa","任选五胆拖");
					}else if("105".equals(arr[0])){
						zhuma.put("wanfa","任选五复式");
					}else if("114".equals(arr[0])){
						zhuma.put("wanfa","任选五单式");
					}else if("125".equals(arr[0])){
						zhuma.put("wanfa","任选六胆拖");
					}else if("106".equals(arr[0])){
						zhuma.put("wanfa","任选六复式");
					}else if("115".equals(arr[0])){
						zhuma.put("wanfa","任选六单式");
					}else if("126".equals(arr[0])){
						zhuma.put("wanfa","任选七胆拖");
					}else if("107".equals(arr[0])){
						zhuma.put("wanfa","任选七复式");
					}else if("116".equals(arr[0])){
						zhuma.put("wanfa","任选七单式");
					}else if("117".equals(arr[0])){
						zhuma.put("wanfa","任选八");
					}else if("153".equals(arr[0])){
						zhuma.put("wanfa","前三组胆");
					}else if("109".equals(arr[0])){
						zhuma.put("wanfa","前三组选复式");
					}else if("151".equals(arr[0])){
						zhuma.put("wanfa","前三组选单式");
					}else if("133".equals(arr[0])){
						zhuma.put("wanfa","前二组胆");
					}else if("108".equals(arr[0])){
						zhuma.put("wanfa","前二组选复式");
					}else if("131".equals(arr[0])){
						zhuma.put("wanfa","前二组选单式");
					}else if("142".equals(arr[0])){
						zhuma.put("wanfa","前二直选复式");
					}else if("141".equals(arr[0])){
						zhuma.put("wanfa","前二直选单式");
					}else if("162".equals(arr[0])){
						zhuma.put("wanfa","前三直选复式");
					}else if("161".equals(arr[0])){
						zhuma.put("wanfa","前三直选单式");
					}
			}
			}
			}
		logger.info("注码解析："+zhuma);
		return zhuma ;
		
	}
	/**
	 * 十一运夺金拼接后的注码解析
	 * @param betCode 注码
	 * @return json 存入玩法、倍数、注码
	 */
	public static JSONObject getSYYDJCodeString(String betCode){
		String bet[] = betCode.split("\\^");//将每注的注码分隔为单注
		StringBuffer stb = new StringBuffer();
		JSONObject jsonObject = new JSONObject();
		String wbet = "";
		for (int i = 0; i < bet.length; i++) {
			wbet = wan1 + bet[i] + "^";
			int[] nums = getPoolInfo(wbet);
			if (nums[0] == 0) {
				//单式
				for (int j = 4; j < nums.length - 1; j++) {
					stb.append(getBuZero(nums[j])+"，");
				}
				stb.deleteCharAt(stb.length()-1); 
				stb.append("|" + getBuZero(nums[nums.length - 1])+"<br/>");
				jsonObject.put("wanfa", Constant.SSQ_RSBS);
				jsonObject.put("multiple", nums[1]);
				jsonObject.put("betcode", stb.toString());
	
			} 
		}
		//System.out.println("双色球解析注码之后所得:"+jsonObject);
		return jsonObject;
	}
	/**
	 * 格式化开奖号码
	 * @param lotno  彩种
	 * @param wincode  开奖号码
	 * @param specialcode   特殊号码
	 * @return
	 */
	  public static JSONObject FormatWinCode(String lotno,String wincode,String specialcode){
		  String code ="";
		  JSONObject   betcode = new JSONObject();
		  if(!"null".equals(wincode.trim())){
		  if("F47104".equals(lotno)){//双色球注码解析
				
				 if("".equals(specialcode)){
						 specialcode= wincode.split("\\|")[1];
						 wincode =wincode.split("\\|")[0];
					 }
					for(int i=0;i<wincode.length();i=i+2){//红球格式化
						if("".equals(code)){
							code =wincode.substring(i, i+2);
						}else{
						code =code+","+wincode.substring(i, i+2);
						}
					}
					code =code+"|"+specialcode;
			}else if("F47103".equals(lotno)){//福彩3D   020101
				 for(int i=1;i<wincode.length();i=i+2){
						if("".equals(code)){
							code =wincode.substring(i, i+1);
						}else{
						code =code+","+wincode.substring(i, i+1);
						}
					}
			}else if("F47102".equals(lotno)){//七乐彩  07101316181927|29 
				 if("".equals(specialcode)){
						 specialcode= wincode.split("\\|")[1];
						 wincode =wincode.split("\\|")[0];
					 }
					for(int i=0;i<wincode.length();i=i+2){//红球格式化
						if("".equals(code)){
							code =wincode.substring(i, i+2);
						}else{
						code =code+","+wincode.substring(i, i+2);
						}
					}
					code =code+"|"+specialcode;
			}else if("T01001".equals(lotno)){//大乐透   17 23 26 30 32+06 12 
				 if("".equals(specialcode)){
					 specialcode= wincode.replace(" ","").split("\\+")[1];
					 wincode =wincode.replace(" ","").split("\\+")[0];
				 }
				for(int i=0;i<wincode.length();i=i+2){//红球格式化
					if(i==0){
						code =wincode.substring(i, i+2);
					}else{
					code =code+","+wincode.substring(i, i+2);
					}
				}
				code =code+"|";
				for(int i=0;i<specialcode.length();i=i+2){//红球格式化
					if(i==0){
						code =code +specialcode.substring(i, i+2);
					}else{
					code =code+","+specialcode.substring(i, i+2);
					}
				}
			}else if("T01002".equals(lotno)){//排列3  677
				 for(int i=1;i<wincode.length();i++){
						if("".equals(code)){
							code =wincode.substring(i, i+1);
						}else{
						code =code+"|"+wincode.substring(i, i+1);
						}
					}
			}else if("T01011".equals(lotno)){//排列5  32497
				 for(int i=1;i<wincode.length();i++){
						if("".equals(code)){
							code =wincode.substring(i, i+1);
						}else{
						code =code+"|"+wincode.substring(i, i+1);
						}
					}
			}else if("T01009".equals(lotno)){//七星彩  9767711
				 for(int i=1;i<wincode.length();i++){
						if("".equals(code)){
							code =wincode.substring(i, i+1);
						}else{
						code =code+"|"+wincode.substring(i, i+1);
						}
					}
			}else{
				 code =wincode;	
			}
		  }else{
			  code ="";
		  }
		  betcode.put("code",code);
		return betcode;
		  
	  }
	  public static String getSSCCode(String betCode,String wanfa){
		  String  codeString ="";
		  if("大小单双".equals(wanfa)){
			  for(int i=0;i<betCode.split("").length;i++){
				  String code = betCode.split("")[i];
				  if("2".equals(code)){
					  codeString+="大";
				  }else  if("1".equals(code)){
					  codeString+="小";
				  } else if("4".equals(code)){
					  codeString+="双";
				  } else if("5".equals(code)){
					  codeString+="单";
				  }
			  }
		  }else{
			  codeString=betCode;
				}
			return codeString;
		}
	  public static String getLQTouzhu(String code,String lotno){
		  String  codeString ="";
		  if(Constant.JCZQ_BF.equals(lotno)){//竞彩足球胜负
			  for(int i=1;i<code.split("").length;i+=2){
				  if("99".equals(code.split("")[i]+code.split("")[i+1])){
					  codeString+="平其他,";
				  }else  if("09".equals(code.split("")[i]+code.split("")[i+1])){
					  codeString+="负其他,";
				  }else  if("90".equals(code.split("")[i]+code.split("")[i+1])){
					  codeString+="胜其他,";
				  }else{
					  codeString+=code.split("")[i]+":"+code.split("")[i+1]+",";
				  }
			  }
		  }else if(Constant.JCZQ_BSF.equals(lotno)){//竞彩足球半场胜负
			  for(int i=1;i<code.split("").length;i+=2){
				  codeString+=code.split("")[i].replaceAll("0","负").replaceAll("1","平").replaceAll("3","胜")+"-"+code.split("")[i+1].replaceAll("0","负").replaceAll("1","平").replaceAll("3","胜")+",";
			  }
			  
		  }else if(Constant.JCZQ_ZJQ.equals(lotno)){//竞彩足球总进球数
			  for(int i=1;i<code.split("").length;i++){
				  if("7".equals(code.split("")[i])){
					  codeString+="7个以上,";
				  }else{
					  codeString+=code.split("")[i]+",";
				  }
			  }
			  
		  }else if(Constant.JCLQ_SFC.equals(lotno)){//竞彩篮球胜分差
			  for(int i=1;i<code.split("").length;i+=2){
				  if("01".equals(code.split("")[i]+code.split("")[i+1])){
					  codeString+="主胜1-5";
				  }else if("02".equals(code.split("")[i]+code.split("")[i+1])){
					  codeString+="主胜6-10";
				  }else if("03".equals(code.split("")[i]+code.split("")[i+1])){
					  codeString+="主胜11-15";
				  }else if("04".equals(code.split("")[i]+code.split("")[i+1])){
					  codeString+="主胜16-20";
				  }else if("05".equals(code.split("")[i]+code.split("")[i+1])){
					  codeString+="主胜21-25";
				  }else if("06".equals(code.split("")[i]+code.split("")[i+1])){
					  codeString+="主胜26+";
				  }else if("11".equals(code.split("")[i]+code.split("")[i+1])){
					  codeString+="客胜1-5";
				  }else if("12".equals(code.split("")[i]+code.split("")[i+1])){
					  codeString+="客胜6-10";
				  }else if("13".equals(code.split("")[i]+code.split("")[i+1])){
					  codeString+="客胜11-15";
				  }else if("14".equals(code.split("")[i]+code.split("")[i+1])){
					  codeString+="客胜16-20";
				  }else if("15".equals(code.split("")[i]+code.split("")[i+1])){
					  codeString+="客胜21-25";
				  }else if("16".equals(code.split("")[i]+code.split("")[i+1])){
					  codeString+="客胜26+";
				  }
				  codeString+=",";
			  }
		  }else if(Constant.JCLQ_SF.equals(lotno)||Constant.JCLQ_RFSF.equals(lotno)||Constant.JCZQ_SPF.equals(lotno)){//竞彩篮球足球 胜（平）负
			  for(int i=1;i<code.split("").length;i++){
				  if("3".equals(code.split("")[i])){
					  codeString+="胜";
				  }else if("0".equals(code.split("")[i])){
					  codeString+="负";
				  }else if("1".equals(code.split("")[i])){
					  codeString+="平";
				  }
				  codeString+=",";
			  }
		  }else if(Constant.JCLQ_DXF.equals(lotno)){
			  for(int i=1;i<code.split("").length;i++){
				  if("2".equals(code.split("")[i])){
					  codeString+="小分";
				  }else if("1".equals(code.split("")[i])){
					  codeString+="大分";
				  }
				  codeString+=",";
			  }
		  }
			return codeString.substring(0, codeString.length()-1);
		}
}
