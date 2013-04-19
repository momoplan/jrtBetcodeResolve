package com.jrt.betcodeResolve.bean;

import com.jrt.betcodeResolve.util.Constant;

/**
 * 
 * 存放跟注码有关的属性包括(彩种、倍数、注数、玩法、总金额、投注的注码)
 * 
 * @author 徐丽
 */
public class BetcodeBean {
	private String lotno;// 彩种
	private String betcode;// 注码
	private String multiple;// 倍数
	private String zhushu;// 注数
	private String gameMethod;// 玩法
	private String totalMoney;// 总金额
	private String finalCode;// 拼接后的注码传到jrtLot

	public String getLotno() {
		return lotno;
	}

	public void setLotno(String lotno) {
		this.lotno = lotno;
	}

	public String getGameMethod() {
		return gameMethod;
	}

	public void setGameMethod(String gameMethod) {
		this.gameMethod = gameMethod;
	}

	public String getBetcode() {
		return betcode;
	}

	public void setBetcode(String betcode) {
		this.betcode = betcode;
	}

	public String getMultiple() {
		return multiple;
	}

	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}

	public String getZhushu() {
		return zhushu;
	}

	public void setZhushu(String zhushu) {
		this.zhushu = zhushu;
	}

	public String getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(String totalMoney) {
		this.totalMoney = totalMoney;
	}

	// 将总金额转换为分
	public String getTotalMoneyFen() {
		if (totalMoney == null || totalMoney.equals("")) {
			return totalMoney;
		} else {
			return String.valueOf(Integer.parseInt(totalMoney) * 100);
		}
	}

	//福彩拼接后的注码
	public String getFinalCode() {
		
		this.finalCode = Constant.CITY_CODE + Constant.QH_TAB + lotno
				+ Constant.QH_TAB + gameMethod + Constant.QH_TAB
				+ (Integer.parseInt(zhushu) < 10 ? "0" + zhushu : zhushu)
				+ Constant.QH_TAB + betcode;

		return finalCode;
	}

	public void setFinalCode(String finalCode) {
		this.finalCode = finalCode;
	}

}
