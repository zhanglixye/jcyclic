/**
 * 
 */
package com.kaiwait.core.validator.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaiwait.common.exception.ValidateException;
import com.kaiwait.core.generator.InterfaceDefGenMain;
import com.kaiwait.core.generator.InterfaceDefGenMainTest;
import com.kaiwait.core.process.dynamic.vo.InterfaceDefinition;

/**
 * @author wings
 *
 */
public class JsonRuleValidatorTest {
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	private static String filepath;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		File f = new File(InterfaceDefGenMainTest.class.getResource("/generator/").getPath());
		filepath = (f.getAbsolutePath() + "/");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link com.kaiwait.core.validator.impl.JsonRuleValidator#validate(java.lang.String, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)}.
	 * 
	 * @throws Exception
	 */
	
	@Test
	public void testValidate() throws Exception {
		JsonRuleValidator validator = new JsonRuleValidator();
		InterfaceDefinition interfaceDef;
		try {
			interfaceDef = InterfaceDefGenMain.getInterfaceDef(filepath, "validate_null.xlsx");
			validator.validate(interfaceDef, "a");
			Assert.fail("");
		} catch (ValidateException e) {

		}
		String result;
		// 允许为空测试
		{
			// 都不为空
			interfaceDef = InterfaceDefGenMain.getInterfaceDef(filepath, "validate_null.xlsx");
			Input input = genBasicVo();
			String writeValueAsString = OBJECT_MAPPER.writeValueAsString(input);
			result = validator.validate(interfaceDef, writeValueAsString);
			Assert.assertNull(result);
			// 空Body
			result = validator.validate(interfaceDef, "");
			Assert.assertNull(result);
			result = validator.validate(interfaceDef, null);
			Assert.assertNull(result);
			// 空属性
			input.setProdName(null);
			input.setProdType(null);
			result = validator.validate(interfaceDef, OBJECT_MAPPER.writeValueAsString(input));
			Assert.assertNull(result);
			// 嵌套空属性
			for (Stock stock : input.getStockList()) {
				stock.setAddress(null);
				stock.setName(null);
			}
			result = validator.validate(interfaceDef, OBJECT_MAPPER.writeValueAsString(input));
			Assert.assertNull(result);
			// 嵌套null对象
			input.getStockList().clear();
			input.getStockList().add(null);
			input.getStockList().add(null);
			result = validator.validate(interfaceDef, OBJECT_MAPPER.writeValueAsString(input));
			Assert.assertNull(result);
			// 空list
			input.getStockList().clear();
			result = validator.validate(interfaceDef, OBJECT_MAPPER.writeValueAsString(input));
			Assert.assertNull(result);
			// null list
			input.setStockList(null);
			result = validator.validate(interfaceDef, OBJECT_MAPPER.writeValueAsString(input));
			Assert.assertNull(result);
		}
		// 不允许为空测试
		{

			// 都不为空
			interfaceDef = InterfaceDefGenMain.getInterfaceDef(filepath, "validate_notnull.xlsx");
			Input input = genBasicVo();
			String writeValueAsString = OBJECT_MAPPER.writeValueAsString(input);
			result = validator.validate(interfaceDef, writeValueAsString);
			Assert.assertNull(result);
			// 空Body
			result = validator.validate(interfaceDef, "");
			Assert.assertEquals("数据不能为空", result);
			result = validator.validate(interfaceDef, null);
			Assert.assertEquals("数据不能为空", result);
			// 空属性
			input.setProdName(null);
			result = validator.validate(interfaceDef, OBJECT_MAPPER.writeValueAsString(input));
			Assert.assertEquals("产品名称不能为空", result);
			input = genBasicVo();
			input.setProdType(null);
			result = validator.validate(interfaceDef, OBJECT_MAPPER.writeValueAsString(input));
			Assert.assertEquals("产品类型不能为空", result);
			// 嵌套空属性
			input = genBasicVo();
			input.getStockList().get(0).setAddress(null);
			result = validator.validate(interfaceDef, OBJECT_MAPPER.writeValueAsString(input));
			Assert.assertEquals("仓库地址不能为空", result);
			input = genBasicVo();
			input.getStockList().get(0).setName(null);
			result = validator.validate(interfaceDef, OBJECT_MAPPER.writeValueAsString(input));
			Assert.assertEquals("仓库名称不能为空", result);
			input = genBasicVo();
			input.getStockList().get(1).setAddress(null);
			result = validator.validate(interfaceDef, OBJECT_MAPPER.writeValueAsString(input));
			Assert.assertEquals("仓库地址不能为空", result);
			input = genBasicVo();
			input.getStockList().get(1).setName(null);
			result = validator.validate(interfaceDef, OBJECT_MAPPER.writeValueAsString(input));
			Assert.assertEquals("仓库名称不能为空", result);
			// 嵌套null对象
			input = genBasicVo();
			input.getStockList().add(null);
			result = validator.validate(interfaceDef, OBJECT_MAPPER.writeValueAsString(input));
			Assert.assertEquals("仓库信息不能为空", result);
			input = genBasicVo();
			input.getStockList().add(0, null);
			result = validator.validate(interfaceDef, OBJECT_MAPPER.writeValueAsString(input));
			Assert.assertEquals("仓库信息不能为空", result);
			input = genBasicVo();
			input.getStockList().add(1, null);
			result = validator.validate(interfaceDef, OBJECT_MAPPER.writeValueAsString(input));
			Assert.assertEquals("仓库信息不能为空", result);
			// 空list
			input = genBasicVo();
			input.getStockList().clear();
			result = validator.validate(interfaceDef, OBJECT_MAPPER.writeValueAsString(input));
			Assert.assertNull(result);
			// null list
			input = genBasicVo();
			input.setStockList(null);
			result = validator.validate(interfaceDef, OBJECT_MAPPER.writeValueAsString(input));
			Assert.assertEquals("仓库列表不能为空", result);
		}
		// 类型测试
		{
			interfaceDef = InterfaceDefGenMain.getInterfaceDef(filepath, "validate_type_format.xlsx");
			// 正确
			MemberInfo memberInfo = genMemberInfo();
			result = validator.validate(interfaceDef, OBJECT_MAPPER.writeValueAsString(memberInfo));
			Assert.assertNull(result);
			//Integer负数
			memberInfo.setAge("-10");
			result = validator.validate(interfaceDef, OBJECT_MAPPER.writeValueAsString(memberInfo));
			Assert.assertNull(result);
			//Long负数
			memberInfo.setAmount("-10");
			result = validator.validate(interfaceDef, OBJECT_MAPPER.writeValueAsString(memberInfo));
			Assert.assertNull(result);
			//bigdecimal 负数
			memberInfo.setTotal("-10.02");
			result = validator.validate(interfaceDef, OBJECT_MAPPER.writeValueAsString(memberInfo));
			Assert.assertNull(result);
			//bigdecimal 无小数
			memberInfo.setTotal("100000000000000000000");
			result = validator.validate(interfaceDef, OBJECT_MAPPER.writeValueAsString(memberInfo));
			Assert.assertNull(result);
			
			//长度不正确
			memberInfo = genMemberInfo();
			memberInfo.setName("诸葛孔明灯");
			result = validator.validate(interfaceDef, OBJECT_MAPPER.writeValueAsString(memberInfo));
			Assert.assertEquals("参数[姓名]长度超过限制,最多输入4个字符", result);
			memberInfo = genMemberInfo();
			memberInfo.setMailAddress("www@163.com");
			result = validator.validate(interfaceDef, OBJECT_MAPPER.writeValueAsString(memberInfo));
			Assert.assertEquals("参数[邮箱地址]长度超过限制,最多输入10个字符", result);
			memberInfo = genMemberInfo();
			memberInfo.setAmount("100000000000000000000");
			result = validator.validate(interfaceDef, OBJECT_MAPPER.writeValueAsString(memberInfo));
			Assert.assertEquals("参数[总数]长度超过限制,最多输入20个字符", result);
			//枚举不正确
			memberInfo = genMemberInfo();
			memberInfo.setSex("L");
			result = validator.validate(interfaceDef, OBJECT_MAPPER.writeValueAsString(memberInfo));
			Assert.assertEquals("参数[性别]只能输入[M;F]其中一个", result);
			//正则不正确
			memberInfo = genMemberInfo();
			memberInfo.setMailAddress("wht.com");
			result = validator.validate(interfaceDef, OBJECT_MAPPER.writeValueAsString(memberInfo));
			Assert.assertEquals("参数[邮箱地址]格式错误", result);
			//Integer不正确
			memberInfo = genMemberInfo();
			memberInfo.setAge("abc");
			result = validator.validate(interfaceDef, OBJECT_MAPPER.writeValueAsString(memberInfo));
			Assert.assertEquals("参数[年龄]类型错误,只能输入整数", result);
			memberInfo.setAge("100000000000");
			result = validator.validate(interfaceDef, OBJECT_MAPPER.writeValueAsString(memberInfo));
			Assert.assertEquals("参数[年龄]类型错误,只能输入整数", result);
			memberInfo.setAge("10.02");
			result = validator.validate(interfaceDef, OBJECT_MAPPER.writeValueAsString(memberInfo));
			Assert.assertEquals("参数[年龄]类型错误,只能输入整数", result);
			//Long不正确
			memberInfo = genMemberInfo();
			memberInfo.setAmount("abc");
			result = validator.validate(interfaceDef, OBJECT_MAPPER.writeValueAsString(memberInfo));
			Assert.assertEquals("参数[总数]类型错误,只能输入整数", result);
			memberInfo.setAmount("99223372036854775807");
			result = validator.validate(interfaceDef, OBJECT_MAPPER.writeValueAsString(memberInfo));
			Assert.assertEquals("参数[总数]类型错误,只能输入整数", result);
			memberInfo.setAmount("10.02");
			result = validator.validate(interfaceDef, OBJECT_MAPPER.writeValueAsString(memberInfo));
			Assert.assertEquals("参数[总数]类型错误,只能输入整数", result);
			//BigDecimal不正确
			memberInfo = genMemberInfo();
			memberInfo.setTotal("abc");
			result = validator.validate(interfaceDef, OBJECT_MAPPER.writeValueAsString(memberInfo));
			Assert.assertEquals("参数[总计]类型错误,请输入正确的数值", result);
			//日期不正确
			memberInfo = genMemberInfo();
			memberInfo.setBirthday("abc");
			result = validator.validate(interfaceDef, OBJECT_MAPPER.writeValueAsString(memberInfo));
			Assert.assertEquals("参数[生日]格式错误,必需输入YYYY-MM-DD格式的正确的日期", result);
			memberInfo.setBirthday("2017-1-1");
			result = validator.validate(interfaceDef, OBJECT_MAPPER.writeValueAsString(memberInfo));
			Assert.assertEquals("参数[生日]格式错误,必需输入YYYY-MM-DD格式的正确的日期", result);
			memberInfo = genMemberInfo();
			memberInfo.setBirthday("2007-00-01");
			result = validator.validate(interfaceDef, OBJECT_MAPPER.writeValueAsString(memberInfo));
			Assert.assertEquals("参数[生日]格式错误,必需输入YYYY-MM-DD格式的正确的日期", result);
			memberInfo.setBirthday("2007-02-29");
			result = validator.validate(interfaceDef, OBJECT_MAPPER.writeValueAsString(memberInfo));
			Assert.assertEquals("参数[生日]格式错误,必需输入YYYY-MM-DD格式的正确的日期", result);
			memberInfo.setBirthday("2007-04-31");
			result = validator.validate(interfaceDef, OBJECT_MAPPER.writeValueAsString(memberInfo));
			Assert.assertEquals("参数[生日]格式错误,必需输入YYYY-MM-DD格式的正确的日期", result);
			memberInfo.setBirthday("2007-04-30 23:59:59");
			result = validator.validate(interfaceDef, OBJECT_MAPPER.writeValueAsString(memberInfo));
			Assert.assertEquals("参数[生日]格式错误,必需输入YYYY-MM-DD格式的正确的日期", result);
		}
	}
	
	

	private MemberInfo genMemberInfo() {
		MemberInfo memberInfo = new MemberInfo();
		memberInfo.setAge("10");
		memberInfo.setAmount("200000000000000");
		memberInfo.setBirthday("2017-01-01");
		memberInfo.setMailAddress("wht@163.cn");
		memberInfo.setName("王仙");
		memberInfo.setSex("M");
		memberInfo.setTotal("2017.1234");
		return memberInfo;
	}

	private Input genBasicVo() {
		Input input = new Input();
		input.setProdName("test");
		input.setProdType("testProdType");
		List<Stock> stockList = new ArrayList<Stock>();
		Stock stock = new Stock();
		stock.setAddress("testAddress");
		stock.setName("name");
		stockList.add(stock);
		stock = new Stock();
		stock.setAddress("testAddress2");
		stock.setName("name2");
		stockList.add(stock);
		input.setStockList(stockList);
		return input;
	}

}
