/**
 * 
 */
package com.kaiwait.core.generator;

import static org.junit.Assert.fail;

import java.io.File;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.kaiwait.core.process.dynamic.vo.InterfaceDefinition;

/**
 * @author wings
 *
 */
public class InterfaceDefGenMainTest {
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
	 * {@link com.kaiwait.core.generator.InterfaceDefGenMain#getInterfaceDef(java.lang.String, java.lang.String)}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetInterfaceDef() throws Exception {

		try {
			InterfaceDefGenMain.getInterfaceDef(filepath, "blank.xlsx");
			fail("validate fail");
		} catch (TemplateValidateException e) {
			Assert.assertEquals("文件格式不正确", e.getMessage());
		}
		try {
			InterfaceDefGenMain.getInterfaceDef(filepath, "error_if_name_null.xlsx");
			fail("validate fail");
		} catch (TemplateValidateException e) {
			Assert.assertEquals("定义文件内容不完整,第1行接口名不能为空", e.getMessage());
		}
		try {
			InterfaceDefGenMain.getInterfaceDef(filepath, "error_if_type_null.xlsx");
			fail("validate fail");
		} catch (TemplateValidateException e) {
			Assert.assertEquals("定义文件内容不完整,第2行接口类型不能为空", e.getMessage());
		}
		try {
			InterfaceDefGenMain.getInterfaceDef(filepath, "error_if_rootpath_null.xlsx");
			fail("validate fail");
		} catch (TemplateValidateException e) {
			Assert.assertEquals("定义文件内容不完整,第3行请求地址第一级不能为空", e.getMessage());
		}
		try {
			InterfaceDefGenMain.getInterfaceDef(filepath, "error_if_path_null.xlsx");
			fail("validate fail");
		} catch (TemplateValidateException e) {
			Assert.assertEquals("定义文件内容不完整,第3行请求地址第二级不能为空", e.getMessage());
		}
		try {
			InterfaceDefGenMain.getInterfaceDef(filepath, "error_if_method_null.xlsx");
			fail("validate fail");
		} catch (TemplateValidateException e) {
			Assert.assertEquals("定义文件内容不完整,第4行调用方式不能为空", e.getMessage());
		}
		try {
			InterfaceDefGenMain.getInterfaceDef(filepath, "error_if_paging_null.xlsx");
			fail("validate fail");
		} catch (TemplateValidateException e) {
			Assert.assertEquals("定义文件内容不完整,第5行结果是否分页不能为空", e.getMessage());
		}
		InterfaceDefinition interfaceDef = InterfaceDefGenMain.getInterfaceDef(filepath,
				"error_if_inputlist_null.xlsx");
		Assert.assertEquals(0, interfaceDef.getInParamList().size());
		Assert.assertEquals(1, interfaceDef.getOutParamList().size());
		Assert.assertEquals("output", interfaceDef.getOutParamList().get(0).getName());
		Assert.assertEquals(4, interfaceDef.getOutParamList().get(0).getDetailParamList().size());
		Assert.assertEquals("prodName", interfaceDef.getOutParamList().get(0).getDetailParamList().get(0).getName());
		Assert.assertEquals("prodType", interfaceDef.getOutParamList().get(0).getDetailParamList().get(1).getName());
		Assert.assertEquals("expDate", interfaceDef.getOutParamList().get(0).getDetailParamList().get(2).getName());
		Assert.assertEquals("stockList", interfaceDef.getOutParamList().get(0).getDetailParamList().get(3).getName());
		Assert.assertEquals(1,
				interfaceDef.getOutParamList().get(0).getDetailParamList().get(3).getDetailParamList().size());
		Assert.assertEquals("stock", interfaceDef.getOutParamList().get(0).getDetailParamList().get(3)
				.getDetailParamList().get(0).getName());
		Assert.assertEquals(2, interfaceDef.getOutParamList().get(0).getDetailParamList().get(3).getDetailParamList()
				.get(0).getDetailParamList().size());
		Assert.assertEquals("name", interfaceDef.getOutParamList().get(0).getDetailParamList().get(3)
				.getDetailParamList().get(0).getDetailParamList().get(0).getName());
		Assert.assertEquals("address", interfaceDef.getOutParamList().get(0).getDetailParamList().get(3)
				.getDetailParamList().get(0).getDetailParamList().get(1).getName());

		interfaceDef = InterfaceDefGenMain.getInterfaceDef(filepath, "error_if_outputlist_notexist.xlsx");
		Assert.assertEquals(0, interfaceDef.getOutParamList().size());
		Assert.assertEquals(1, interfaceDef.getInParamList().size());
		Assert.assertEquals("input", interfaceDef.getInParamList().get(0).getName());
		Assert.assertEquals(3, interfaceDef.getInParamList().get(0).getDetailParamList().size());
		Assert.assertEquals("prodName", interfaceDef.getInParamList().get(0).getDetailParamList().get(0).getName());
		Assert.assertEquals("prodType", interfaceDef.getInParamList().get(0).getDetailParamList().get(1).getName());
		Assert.assertEquals("stockList", interfaceDef.getInParamList().get(0).getDetailParamList().get(2).getName());
		Assert.assertEquals(1,
				interfaceDef.getInParamList().get(0).getDetailParamList().get(2).getDetailParamList().size());
		Assert.assertEquals("stock",
				interfaceDef.getInParamList().get(0).getDetailParamList().get(2).getDetailParamList().get(0).getName());
		Assert.assertEquals(2, interfaceDef.getInParamList().get(0).getDetailParamList().get(2).getDetailParamList()
				.get(0).getDetailParamList().size());
		Assert.assertEquals("name", interfaceDef.getInParamList().get(0).getDetailParamList().get(2)
				.getDetailParamList().get(0).getDetailParamList().get(0).getName());
		Assert.assertEquals("address", interfaceDef.getInParamList().get(0).getDetailParamList().get(2)
				.getDetailParamList().get(0).getDetailParamList().get(1).getName());

		interfaceDef = InterfaceDefGenMain.getInterfaceDef(filepath, "error_if_outputlist_null.xlsx");
		Assert.assertEquals(0, interfaceDef.getOutParamList().size());
		Assert.assertEquals(1, interfaceDef.getInParamList().size());
		Assert.assertEquals("input", interfaceDef.getInParamList().get(0).getName());
		Assert.assertEquals(3, interfaceDef.getInParamList().get(0).getDetailParamList().size());
		Assert.assertEquals("prodName", interfaceDef.getInParamList().get(0).getDetailParamList().get(0).getName());
		Assert.assertEquals("prodType", interfaceDef.getInParamList().get(0).getDetailParamList().get(1).getName());
		Assert.assertEquals("stockList", interfaceDef.getInParamList().get(0).getDetailParamList().get(2).getName());
		Assert.assertEquals(1,
				interfaceDef.getInParamList().get(0).getDetailParamList().get(2).getDetailParamList().size());
		Assert.assertEquals("stock",
				interfaceDef.getInParamList().get(0).getDetailParamList().get(2).getDetailParamList().get(0).getName());
		Assert.assertEquals(2, interfaceDef.getInParamList().get(0).getDetailParamList().get(2).getDetailParamList()
				.get(0).getDetailParamList().size());
		Assert.assertEquals("name", interfaceDef.getInParamList().get(0).getDetailParamList().get(2)
				.getDetailParamList().get(0).getDetailParamList().get(0).getName());
		Assert.assertEquals("address", interfaceDef.getInParamList().get(0).getDetailParamList().get(2)
				.getDetailParamList().get(0).getDetailParamList().get(1).getName());

		try {
			interfaceDef = InterfaceDefGenMain.getInterfaceDef(filepath, "error_if_inputlist_type_validate1.xlsx");
			fail("validate fail");
		} catch (TemplateValidateException e) {
			Assert.assertEquals("定义文件内容不正确,第10行,参数列表只能有一个根对象", e.getMessage());
		}
		try {
			interfaceDef = InterfaceDefGenMain.getInterfaceDef(filepath, "error_if_inputlist_type_validate2.xlsx");
			fail("validate fail");
		} catch (TemplateValidateException e) {
			Assert.assertEquals("定义文件内容不正确,第14行stockList数组内只能包含一个属性", e.getMessage());
		}
		try {
			interfaceDef = InterfaceDefGenMain.getInterfaceDef(filepath, "error_if_inputlist_type_validate3.xlsx");
			fail("validate fail");
		} catch (TemplateValidateException e) {
			Assert.assertEquals("定义文件内容不正确,第15行stock对象内至少要包含一个属性", e.getMessage());
		}
		try {
			interfaceDef = InterfaceDefGenMain.getInterfaceDef(filepath, "error_if_inputlist_type_validate4.xlsx");
			fail("validate fail");
		} catch (TemplateValidateException e) {
			Assert.assertEquals("定义文件内容不正确,第14行stockList数组内必需包含一个属性", e.getMessage());
		}
		try {
			interfaceDef = InterfaceDefGenMain.getInterfaceDef(filepath, "error_if_inputlist_type_validate5.xlsx");
			fail("validate fail");
		} catch (TemplateValidateException e) {
			Assert.assertEquals("定义文件内容不完整,第15行stock没有结束", e.getMessage());
		}
		try {
			interfaceDef = InterfaceDefGenMain.getInterfaceDef(filepath, "error_if_inputlist_type_validate6.xlsx");
			fail("validate fail");
		} catch (TemplateValidateException e) {
			Assert.assertEquals("定义文件内容不完整,第14行stockList没有结束", e.getMessage());
		}
		try {
			interfaceDef = InterfaceDefGenMain.getInterfaceDef(filepath, "error_if_inputlist_type_validate7.xlsx");
			fail("validate fail");
		} catch (TemplateValidateException e) {
			Assert.assertEquals("定义文件内容错误,第10行,不允许对Object类型指定校验格式", e.getMessage());
		}
		try {
			interfaceDef = InterfaceDefGenMain.getInterfaceDef(filepath, "error_if_inputlist_type_validate8.xlsx");
			fail("validate fail");
		} catch (TemplateValidateException e) {
			Assert.assertEquals("定义文件内容错误,第14行,不允许对Array类型指定校验格式", e.getMessage());
		}
		try {
			interfaceDef = InterfaceDefGenMain.getInterfaceDef(filepath, "error_if_inputlist_type_validate9.xlsx");
			fail("validate fail");
		} catch (TemplateValidateException e) {
			Assert.assertEquals("定义文件内容错误,第10行,没有校验格式不允许指定附加格式", e.getMessage());
		}
		try {
			interfaceDef = InterfaceDefGenMain.getInterfaceDef(filepath, "error_if_inputlist_type_validate9.xlsx");
			fail("validate fail");
		} catch (TemplateValidateException e) {
			Assert.assertEquals("定义文件内容错误,第10行,没有校验格式不允许指定附加格式", e.getMessage());
		}
		try {
			interfaceDef = InterfaceDefGenMain.getInterfaceDef(filepath, "error_if_inputlist_type_validate10.xlsx");
			fail("validate fail");
		} catch (TemplateValidateException e) {
			Assert.assertEquals("定义文件内容错误,第11行,Date校验格式必需要指定附加格式", e.getMessage());
		}
		try {
			interfaceDef = InterfaceDefGenMain.getInterfaceDef(filepath, "error_if_inputlist_type_validate11.xlsx");
			fail("validate fail");
		} catch (TemplateValidateException e) {
			Assert.assertEquals("定义文件内容错误,第11行,Regular校验格式必需要指定附加格式", e.getMessage());
		}
		try {
			interfaceDef = InterfaceDefGenMain.getInterfaceDef(filepath, "error_if_inputlist_type_validate12.xlsx");
			fail("validate fail");
		} catch (TemplateValidateException e) {
			Assert.assertEquals("定义文件内容错误,第11行,Enums校验格式必需要指定附加格式", e.getMessage());
		}
		try {
			interfaceDef = InterfaceDefGenMain.getInterfaceDef(filepath, "error_if_inputlist_type_validate13.xlsx");
			fail("validate fail");
		} catch (TemplateValidateException e) {
			Assert.assertEquals("定义文件内容错误,第11行,不允许对Long校验格式指定附加格式", e.getMessage());
		}
		try {
			interfaceDef = InterfaceDefGenMain.getInterfaceDef(filepath, "error_if_inputlist_type_validate14.xlsx");
			fail("validate fail");
		} catch (TemplateValidateException e) {
			Assert.assertEquals("定义文件内容错误,第11行,不允许对Integer校验格式指定附加格式", e.getMessage());
		}
		try {
			interfaceDef = InterfaceDefGenMain.getInterfaceDef(filepath, "error_if_inputlist_type_validate15.xlsx");
			fail("validate fail");
		} catch (TemplateValidateException e) {
			Assert.assertEquals("定义文件内容错误,第11行,不允许对BigDecimal校验格式指定附加格式", e.getMessage());
		}
		try {
			interfaceDef = InterfaceDefGenMain.getInterfaceDef(filepath, "error_if_inputlist_type_validate16.xlsx");
			fail("validate fail");
		} catch (TemplateValidateException e) {
			Assert.assertEquals("定义文件内容不完整,第10行input没有结束", e.getMessage());
		}
		try {
			interfaceDef = InterfaceDefGenMain.getInterfaceDef(filepath, "error_if_inputlist_type_validate17.xlsx");
			fail("validate fail");
		} catch (TemplateValidateException e) {
			Assert.assertEquals("定义文件内容不完整,第10行input没有结束", e.getMessage());
		}
		try {
			interfaceDef = InterfaceDefGenMain.getInterfaceDef(filepath, "error_if_inputlist_type_validate18.xlsx");
			fail("validate fail");
		} catch (TemplateValidateException e) {
			Assert.assertEquals("定义文件内容不完整,第29行stockList没有结束", e.getMessage());
		}
		try {
			interfaceDef = InterfaceDefGenMain.getInterfaceDef(filepath, "error_if_inputlist_type_validate19.xlsx");
			fail("validate fail");
		} catch (TemplateValidateException e) {
			Assert.assertEquals("定义文件内容不正确,第17行address说明不能为空", e.getMessage());
		}
		try {
			interfaceDef = InterfaceDefGenMain.getInterfaceDef(filepath, "error_if_inputlist_type_validate20.xlsx");
			fail("validate fail");
		} catch (TemplateValidateException e) {
			Assert.assertEquals("定义文件内容不正确,第10行input说明不能为空", e.getMessage());
		}
		try {
			interfaceDef = InterfaceDefGenMain.getInterfaceDef(filepath, "error_if_inputlist_type_validate21.xlsx");
			fail("validate fail");
		} catch (TemplateValidateException e) {
			Assert.assertEquals("定义文件内容不正确,第10行变量名不能为空", e.getMessage());
		}
		try {
			interfaceDef = InterfaceDefGenMain.getInterfaceDef(filepath, "error_if_inputlist_type_validate22.xlsx");
			fail("validate fail");
		} catch (TemplateValidateException e) {
			Assert.assertEquals("定义文件内容不正确,第11行prodName类型不正确", e.getMessage());
		}
		try {
			interfaceDef = InterfaceDefGenMain.getInterfaceDef(filepath, "error_if_inputlist_type_validate23.xlsx");
			fail("validate fail");
		} catch (TemplateValidateException e) {
			Assert.assertEquals("定义文件内容错误,第10行,校验格式不正确", e.getMessage());
		}
//		InterfaceDefGenMain.genAll(filepath, "OK.xlsx");
	}

	/**
	 * Test method for
	 * {@link com.kaiwait.core.generator.InterfaceDefGenMain#makeBean(java.lang.String, java.lang.String, com.kaiwait.core.process.dynamic.vo.ParameterDefinition)}.
	 */
	@Test
	public void testMakeBean() {

	}

}
