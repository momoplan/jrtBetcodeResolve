package com.jrt.betcodeResolve.util.test;

import org.junit.Assert;
import org.junit.Test;

import com.jrt.betcodeResolve.util.BetcodeResolveUtil;
import com.jrt.betcodeResolve.util.ValidateBetcodeUtil;

/**
 * 
 *	注码验证的测试类
 *
 */
public class ValidateBetcodeUtilTest {
	
	@Test
	/**
	 * 验证足彩的范围 及注码
	 * 胜负彩14场和任九场(14-38个)
	 * 六场半全场 (12-32个)
	 * 进球彩(8-32个)
	 */
	public void validateZCBetcodtTest(){
		//足彩胜负彩14场注码验证
		String betcode = "3,1,3,0,3,3,3,0,3,3,1,3,3,3";
		Assert.assertEquals(true,ValidateBetcodeUtil.validateZCBetcode(betcode,",","11"));
		
		//任九场注码验证
		betcode = "3,1,3,#,3,3,#,0,3,#,1,3,3,3";
		Assert.assertEquals(true,ValidateBetcodeUtil.validateZCBetcode(betcode,",","19"));
		
		//六场半全场注码验证
		betcode = "3,1,3,0,3,3,3,0,3,3,1,3";
		Assert.assertEquals(true,ValidateBetcodeUtil.validateZCBetcode(betcode,",","16"));
		
		//足彩进球彩
		betcode = "2,0,3,1,1,2,1,1";
		Assert.assertEquals(true,ValidateBetcodeUtil.validateZCBetcode(betcode,",","18"));
		
		
		//足彩任九场胆拖算注数
		betcode = "0,#,#,#,#,31,#,#,#,#,#,#,#,#$#,1,0,1,#,#,1,#,3,3,03,#,1,#";
		System.out.println(BetcodeResolveUtil.dantuo(1, 0, 7, 1, 0, 7));
		betcode = "#,#,#,#,#,#,#,#,#,#,#,#,#,#$30,3,#,31,30,31,30,10,30,3,31,0,#,3";
		System.out.println(BetcodeResolveUtil.dantuo(0, 0, 4, 8, 0, 9));
		
		betcode = "#,#,#,#,#,#,#,#,#,#,#,#,#,#$31,31,310,310,3,3,3,3,3,3,#,#,#,#";
		System.out.println(BetcodeResolveUtil.dantuo(0, 0, 6, 2, 2, 9));
	}
	
	
}
