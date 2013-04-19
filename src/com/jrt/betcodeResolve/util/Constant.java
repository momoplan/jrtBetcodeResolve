package com.jrt.betcodeResolve.util;

import java.util.ResourceBundle;

/**
 * 
 * 常量类
 * 
 */
public class Constant {

	public static final int LOTTERY_PRICE = 2;// 每注彩票的金额
	public static final int DLT_LOTTERY_PRICE = 3;// 大乐透追加每注彩票的金额

	public static final String CITY_CODE = "1512";// 内蒙福彩

	/**
	 * 彩种
	 */
	public static final String SSQ = "F47104";
	public static final String SSQ_DM = "B001";// 双色球

	public static final String SD = "F47103";
	public static final String SD_DM = "D3"; // 福彩3D

	public static final String QLC = "F47102";
	public static final String QLC_DM = "QL730";// 七乐彩

	public static final String SSC = "F47101";
	public static final String SSC_DM = "DT5";// 时时彩
	
	public static final String PLS = "T01002";
	public static final String PLS_DM = "PL3_33";// 体彩排列三
	
	public static final String PLW = "T01011";
	public static final String PLW_DM = "PLW_35";// 体彩排列五
	
	public static final String EEXW = "T01013";
	
	public static final String QXC = "T01009";
	public static final String QXC_DM = "QXC_10022";// 体彩七星彩
	
	public static final String DLT = "T01001";
	public static final String DLT_DM = "DLT_23529";// 体彩超级大乐透
	
	public static final String SFC14 = "T01003";
	public static final String SFC14_DM = "ZC_11";// 足彩胜负彩14场
	
	public static final String SFC9 = "T01004";
	public static final String SFC9_DM = "ZC_19";// 足彩胜负彩任九场
	
	public static final String JQC = "T01005";
	public static final String JQC_DM = "ZC_18";// 足彩进球彩
	
	public static final String BQC = "T01006";
	public static final String BQC_DM = "ZC_16";// 足彩六场半全场
	
	public static final String SYXW ="T01010";
	public static final String SYXW_DM ="XYXW_23009"; //江西11选5
	
	public static final String SYYDJ ="T01012";//十一运夺金
	
	public static final String JCZQ_SPF = "J00001";//竞彩足球胜平负
	public static final String JCZQ_BF = "J00002";//竞彩足球比分
	public static final String JCZQ_ZJQ = "J00003";//竞彩足球总进球
	public static final String JCZQ_BSF = "J00004";//竞彩足球半场胜平负
	public static final String JCLQ_SF = "J00005";//竞彩篮球胜负
	public static final String JCLQ_RFSF = "J00006";//竞彩篮球让分胜负
	public static final String JCLQ_DXF = "J00008";//竞彩篮球大小分
	public static final String JCLQ_SFC = "J00007";//竞彩篮球胜分差
	/**
	 * 双色球玩法
	 */
	public static final String SSQ_RSBS = "00";// 红单蓝单
	public static final String SSQ_RMBS = "10";// 红复蓝单
	public static final String SSQ_RSBM = "20";// 红单蓝复
	public static final String SSQ_RMBM = "30";// 红复蓝复
	public static final String SSQ_RTBS = "40";// 红拖蓝单   
	public static final String SSQ_RTBM = "50";// 红拖蓝复

	/**
	 * 福彩3D玩法
	 */
	public static final String SD_ZXDS = "00";// 直选单式
	public static final String SD_Z3DS = "01";// 组3单式
	public static final String SD_Z6DS = "02";// 组6单式
//	public static final String SD_ZXFS = "03";// 直选复式
	public static final String SD_ZXHZ = "10";// 直选和值（单选全包点）
	public static final String SD_ZSHZ = "11";// 组3和值（组3全包点）
	public static final String SD_ZLHZ = "12";// 组六和值（组6全包点）
	public static final String SD_WXTZ = "20";// 位选投注（3D直选复式）单选按位包号
	public static final String SD_Z3FS = "31";// 组3复式
	public static final String SD_Z6FS = "32";// 组6复式
	public static final String SD_DXDFS = "34";// 单选单复式(3D直选包号)
	public static final String SD_DTFS = "54";// 胆拖复式

	/**
	 * 福彩七乐彩玩法
	 */
	public static final String QLC_ZXDS = "00";// 单式
	public static final String QLC_ZXFS = "10";// 复式
	public static final String QLC_ZXDT = "20";// 胆拖

	/**
	 * 大乐透玩法
	 */
	public static final String DLT_DS = "0";// 单式
	public static final String DLT_FS = "1";// 复式
	public static final String DLT_DT = "2";// 胆拖
	public static final String DLT_SXL = "3";// 十二选二(生肖乐)

	/**
	 * 排列三玩法
	 */
	public static final String PLS_ZHX = "01"; // 直选
	public static final String PLS_ZX = "06"; // 组选
	public static final String PLS_ZHXHZ = "S1";// 直选和值
	public static final String PLS_ZXHZ = "S9"; // 组选和值
	public static final String PLS_ZSHZ = "S3"; // 组三和值
	public static final String PLS_ZLHZ = "S6"; // 组六和值
	public static final String PLS_ZSBH = "F3"; // 组三包号
	public static final String PLS_ZLBH = "F6"; // 组六包号
	public static final String PLS_FGF ="|";//排三玩法与注码分隔符

	/**
	 * 体彩——>足彩
	 */
	public final static String ZC_SFC = "11";// 胜负彩14场
	public final static String ZC_RJC = "19";// 任九场
	public final static String ZC_JQC = "18";// 进球彩
	public final static String ZC_BQC = "16";// 半全场
	public static final String ZC_DS = "0";// 单式
	public static final String ZC_FS = "1";// 复式
	public static final String ZC_DT = "2";// 胆拖
	public static final String ZC_ZJ = "3";// 转九
	
	/**
	 * 高频彩--江西11选5
	 */
	public final static String SYXW_RX = "R";//任选直选和胆拖玩法（任选一到任选八在后面直接加上1-8）
	public final static String SYXW_QX2 = "Q2";//选前二直选
	public final static String SYXW_QX3 = "Q3";//选前三直选
	public final static String SYXW_ZX2 ="Z2";//选前二组选胆拖
	public final static String SYXW_ZX3 ="Z3";//选前三组选胆拖
	/**
	 * 体彩彩22选5玩法
	 */
	public static final String EEXW_DS = "0";// 单式
	public static final String EEXW_FS = "1";// 复式
	public static final String EEXW_DT = "2";// 胆拖

	// 读取配置文件的类
	private static ResourceBundle rb = ResourceBundle.getBundle("jrtBetcodeResolve");

	/**
	 * 从配置文件中得到的分隔符
	 */
	/*
	 * 福彩分隔符
	 */
	public final static String SIGN = rb.getString("sign");// 注码之间分隔符","
	public final static String TAB = rb.getString("tab");// 红球和蓝球之间的分隔符"~"
	public final static String TABNUMBER = rb.getString("tabNumber");// 多注或复式之间的分隔符"^"
	public final static String REDTAB = rb.getString("redTab");// 胆码与拖码之间的分隔符"*"

	/*
	 * 体彩分隔符
	 */
	public final static String DT_TAB = rb.getString("dtTab");// 超级大乐透胆码和拖码之间的分隔符"$"
	public final static String QH_TAB = rb.getString("qhTab");// 超级大乐透前区和后区之间的分隔符"-"
	public final static String TC_TABNUMBER = rb.getString("tcTabNumber");// 多注之间的分隔符";"
	public final static String ZC_STREAK = rb.getString("streak");// 足彩任九场场次代表符"#"
	public final static String EEXW_TAb = rb.getString("eexwTab");// 足彩任九场场次代表符"#"

	/*
	 * 竞彩分隔符
	 */
	public final static String JC_TAB = rb.getString("jcTab");// 竞彩胜平负注码之间的分隔符"|"
	/**
	 * bankId -- 充值方式-民生(msy001)、支付宝(zfb001)、DNA(dna001)、易宝(y00003)、19pay(gyj001
	 * )、如意彩点卡(ryc001) accesstype -- 渠道 -web(B)、wap(W)、客户端(C)
	 * 
	 * cardType -- 民生、易宝、支付宝银行卡(01)、支付宝(03)支付宝语音(04)、
	 * 点卡充值(JUNNET-0201,SNDACARD-0202,ZHENGTU-
	 * 0204,SZX-0203,UNICOM-0206,DXJFK-0221)、 如意彩点卡(0300)
	 * 
	 * dna充值的灰名单
	 */
	public static final String YL_GATEID = "8607";//银联gateId
	public static final String ZFB_BANKID = "zfb001";// 支付宝
	public static final String MSY_BANKID = "msy001";// 民生银行
	public static final String DNA_BANKID = "dna001";// DNA
	public static final String YB_BANKID = "y00003";// 易宝
	public static final String PAY19_BANKID = "gyj001";// 翼支付
	public static final String RYC_BANKID = "ryc001";// 如意彩点卡
	public static final String SHYLZF_BANKID = "syl001"; //上海银联支付
	public static final String YLZF_BANKID = "syl002"; //银联支付

	public static final String ZFB_BANKPAY = "bankPay";//支付宝网银
	public static final String ZFB_DIRECTPAY = "directPay";//在线充值的type
	public static final String BANK_CARDTYPE = "01";// 支付宝银行卡充值
	public static final String ZFB_CARDTYPE = "03";// 支付宝在线充值
	public static final String ZFB_CARDTYPE_SOUND = "04";// 支付宝语音充值
	public static final String ZFB_CARDTYPE_GS="ICBCB2C";//工商银行
	public static final String ZFB_CARDTYPE_ZS="CMB";//招商银行
	public static final String ZFB_CARDTYPE_JS="CCB";//中国建设银行
	public static final String ZFB_CARDTYPE_NY="ABC";//中国农业银行
	public static final String ZFB_CARDTYPE_PF="SPDB";//上海浦东发展银行
	public static final String ZFB_CARDTYPE_XY="CIB";//兴业银行
	public static final String ZFB_CARDTYPE_GF="GDB";//广东发展银行
	public static final String ZFB_CARDTYPE_SF="SDB";//深圳发展银行
	public static final String ZFB_CARDTYPE_JT="COMM";//交通银行
	public static final String ZFB_CARDTYPE_YZ="POSTGC";//邮政储蓄银行
	public static final String ZFB_CARDTYPE_ZX="CITIC";//中信银行
	public static final String ZFB_CARDTYPE_SH="SHBANK";//上海银行
	public static final String ZFB_CARDTYPE_NB="NBBANK";//宁波银行
	public static final String ZFB_CARDTYPE_HZ="HZCBB2C";//杭州银行
	public static final String ZFB_CARDTYPE_GD="CEBBANK";//中国光大银行
	public static final String ZFB_CARDTYPE_ZG="BOCB2C";//中国银行
	
	public static final String SHYLZF_CARDTYPE = "0600"; //上海银联支付
	public static final String YLZF_CARDTYPE = "0602"; //银联支付
	
	
	public static final String POINT_NAME_JUNNET = "JUNNET";//骏网一卡通
	public static final String POINT_NAME_SNDACARD = "SNDACARD";//盛大
	public static final String POINT_NAME_ZHENGTU = "ZHENGTU";//征途
	public static final String POINT_NAME_SZX = "SZX";//神州行
	public static final String POINT_NAME_UNICOM = "UNICOM";//联通
	public static final String POINT_NAME_DXJFK = "DXJFK";//电信充值卡

	public static final String POINT_JUNNET = "0201"; // 点卡充值
	public static final String POINT_UNNET = "0001";
	public static final String POINT_SNDACARD = "0202";
	public static final String POINT_ZHENGTU = "0204";
	public static final String POINT_SZX = "0203";
	public static final String POINT_UNICOM = "0206";
	public static final String POINT_DXJFK = "0221";

	public static final String RYC_POINT_CARDTYPE = "0300";// 如意彩点卡
	
	public static final String WEB_AGENCYNO = "B";

	public static final String DNA_CHARGE_GREYLIST = "T437";
	public static final String DNA_CHARGE_GREYLIST1 = "T438";
	
	
	 // 大通栏图片的路径
	 private static ResourceBundle rbint = ResourceBundle.getBundle("ruyicai");
	 public static final String PIC = rbint.getString("picture_upload");
	 // 从配置文件中得到渠道号是web，wap或客户端
	 public static final String NEWS_ANGCYNO = rbint.getString("newsAngecyNo");
	
	 /**
	 * 资讯的类别1-网站公告，2-新闻，3-公益广告，4-双色球和大乐透专家推荐，5-排列三、福彩3D及其他专家推荐
	 */
	 public static final String CATEGORY_TYPE_AN = rbint.getString("Announce");
	 public static final String CATEGORY_TYPE_NEWS = rbint.getString("news");
	 public static final String CATEGORY_TYPE_COMMONWEAL = rbint.getString("commonweal");
	 public static final String CATEGORY_TYPE_SSQANDDLT = rbint.getString("ssqAndDlt");
	 public static final String CATEGORY_TYPE_PLSANDSD = rbint.getString("plsAndSd");

}
