/**
 * 
 */
package com.kaiwait.core.generator;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.kaiwait.common.utils.StringUtil;
import com.kaiwait.core.process.dynamic.vo.InterfaceDefinition;
import com.kaiwait.core.process.dynamic.vo.InterfaceTypeEnum;
import com.kaiwait.core.process.dynamic.vo.ParameterDefinition;
import com.kaiwait.core.process.dynamic.vo.ParameterFormatEnum;
import com.kaiwait.core.process.dynamic.vo.ParameterTypeEnum;
import com.kaiwait.template.ITemplateBuilder;
import com.kaiwait.template.TemplateFactory;
import com.kaiwait.utils.common.ExcelHelper;
/**
 * @author wings
 *
 */
public class InterfaceDefGenMain {
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	private static final String TEMPLATE_FILE_PATH;
	static {
		File f = new File(InterfaceDefGenMain.class.getResource("/com/kaiwait/core/generator/").getPath());
		TEMPLATE_FILE_PATH = (f.getAbsolutePath() + "/template/Bean.ftl");
	}
	public static void main(String[] args) throws Exception {
		//String filepath = "/media/wings/DATA/work1/workspace_wings/core/src/test/resources/generator/";
		String filepath = "F:\\newjppworkspace\\core\\src\\test\\resources\\generator\\";
		String fileName = "changePwd.xlsx";
		genAll(filepath, fileName);
	}
	
	public static void genAll(String filepath, String fileName) throws Exception {
		InterfaceDefinition ifDef = getInterfaceDef(filepath, fileName);

		OBJECT_MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
		System.out.println(OBJECT_MAPPER.writeValueAsString(ifDef));
		// 入参Bean生成
		makeBean(ifDef.getRootPath(), ifDef.getPath(), ifDef.getInParamList().get(0));

		// 出参Bean生成
		makeBean(ifDef.getRootPath(), ifDef.getPath(), ifDef.getOutParamList().get(0));

		System.out.println();
	}

	public static InterfaceDefinition getInterfaceDef(String filepath, String fileName) throws TemplateValidateException {
		ExcelHelper eh = new ExcelHelper(new ExcelHelper.FormInfo(filepath, fileName), 0, 0);
		if (eh.getLastRow() < 9) {
			throw new TemplateValidateException("文件格式不正确");
		}
		InterfaceDefinition ifDef = new InterfaceDefinition();
		ParameterFormatEnum.valueOf("Enums");
		eh.moveNext();
		if (StringUtil.isBlank(eh.getString(1))) {
			throw new TemplateValidateException("定义文件内容不完整,第" + (eh.getCurrentRow() + 1) + "行接口名不能为空");
		}
		ifDef.setName(eh.getString(1));// 接口名
		eh.moveNext();// 接口类型
		if (StringUtil.isBlank(eh.getString(1))) {
			throw new TemplateValidateException("定义文件内容不完整,第" + (eh.getCurrentRow() + 1) + "行接口类型不能为空");
		}
		ifDef.setType(InterfaceTypeEnum.valueOf(eh.getString(1).toLowerCase()));
		eh.moveNext();// 请求地址
		String requestRootPath = eh.getString(1);
		if (StringUtil.isBlank(requestRootPath)) {
			throw new TemplateValidateException("定义文件内容不完整,第" + (eh.getCurrentRow() + 1) + "行请求地址第一级不能为空");
		}
		ifDef.setRootPath(requestRootPath);
		String requestPath = eh.getString(2);
		if (StringUtil.isBlank(requestPath)) {
			throw new TemplateValidateException("定义文件内容不完整,第" + (eh.getCurrentRow() + 1) + "行请求地址第二级不能为空");
		}
		ifDef.setPath(requestPath);
		eh.moveNext();// 调用方式
		if (StringUtil.isBlank(eh.getString(1))) {
			throw new TemplateValidateException("定义文件内容不完整,第" + (eh.getCurrentRow() + 1) + "行调用方式不能为空");
		}
		ifDef.setMethod(eh.getString(1));
		eh.moveNext();// 是否分页
		if (StringUtil.isBlank(eh.getString(1))) {
			throw new TemplateValidateException("定义文件内容不完整,第" + (eh.getCurrentRow() + 1) + "行结果是否分页不能为空");
		}
		ifDef.setPaging("Y".equalsIgnoreCase(eh.getString(1)));
		eh.moveNext();// 请求参数
		eh.moveNext();// 变量名
		// 入参列表开始
		List<ParameterDefinition> inParamList = new LinkedList<ParameterDefinition>();
		ifDef.setInParamList(inParamList);
		ParameterDefinition paramDef;
		while ((paramDef = getParamDefinition(eh, null)) != null) {
			inParamList.add(paramDef);
		}
		validateParamList(inParamList, null);
		// 出参列表开始
		List<ParameterDefinition> outParamList = new LinkedList<ParameterDefinition>();
		ifDef.setOutParamList(outParamList);
		eh.moveNext();// 变量名
		while ((paramDef = getParamDefinition(eh, null)) != null) {
			outParamList.add(paramDef);
		}
		// 校验文件内容
		validateParamList(outParamList, null);
		return ifDef;
	}

	private static ParameterDefinition getParamDefinition(ExcelHelper eh, ParameterDefinition parent) throws TemplateValidateException {
		String paramName;
		String comment;
		while (true) {
			if (eh.hasNext()) {
				eh.moveNext();// 参数名
				paramName = eh.getStringNvl(0).trim();
				comment = eh.getStringNvl(1).trim();
				if (StringUtils.isNotBlank(paramName)) {
						break;
				} else {
					if (StringUtils.isNotBlank(comment)) {
						throw new TemplateValidateException("定义文件内容不正确,第" + (eh.getCurrentRow() +1) + "行变量名不能为空");
					}
				}
			} else {
				if (parent != null) {
					throw new TemplateValidateException(
							"定义文件内容不完整,第" + parent.getDefineLine() + "行" + parent.getName() + "没有结束");
				}
				return null;
			}
		}
		if ("返回参数".equals(paramName)) {
			if (parent != null) {
				throw new TemplateValidateException("定义文件内容不完整,第" + parent.getDefineLine() + "行" + parent.getName() + "没有结束");
			}
			return null;
		}
		
		ParameterDefinition paramDef = new ParameterDefinition();
		paramDef.setParent(parent);
		paramDef.setDefineLine(eh.getCurrentRow() + 1);
		paramDef.setName(paramName);
		paramDef.setComment(eh.getStringNvl(1).trim());
		if (StringUtil.isBlank(paramDef.getComment())) {
			if (parent != null) {
				if (parent.getName().equals(paramDef.getName())) {
					return null;
				}
				ParameterDefinition tmpDefinition = parent.getParent();
				while (tmpDefinition != null) {
					if (tmpDefinition.getName().equals(paramDef.getName())) {
						throw new TemplateValidateException(
								"定义文件内容不完整,第" + parent.getDefineLine() + "行" + parent.getName() + "没有结束");
					}
					tmpDefinition = tmpDefinition.getParent();
				}
			}
			throw new TemplateValidateException("定义文件内容不正确,第" + paramDef.getDefineLine() + "行" + paramDef.getName() + "说明不能为空");
		} else {
			
		}
		try {
			paramDef.setType(ParameterTypeEnum.valueOf(eh.getStringNvl(2).trim()));
		} catch (IllegalArgumentException e) {
			throw new TemplateValidateException("定义文件内容不正确,第" + paramDef.getDefineLine() + "行" + paramDef.getName() + "类型不正确", e);
		}
		paramDef.setRequired(!"Y".equalsIgnoreCase(eh.getStringNvl(3).trim()));
		String format = eh.getString(4);
		if (format != null) {
			try {
				paramDef.setFormat(ParameterFormatEnum.valueOf(format.trim()));
			} catch (IllegalArgumentException e) {
				throw new TemplateValidateException("定义文件内容错误,第" + parent.getDefineLine() + "行,校验格式不正确", e);
			}
			if (ParameterTypeEnum.Object.equals(paramDef.getType())
					|| ParameterTypeEnum.Array.equals(paramDef.getType())) {
				throw new TemplateValidateException(
						"定义文件内容错误,第" + paramDef.getDefineLine() + "行,不允许对" + paramDef.getType() + "类型指定校验格式");
			}
		}
		String formatPattern = eh.getStringNvl(5).trim();
		if (StringUtil.isNotBlank(formatPattern)) {
			if (paramDef.getFormat() == null) {
				throw new TemplateValidateException("定义文件内容错误,第" + paramDef.getDefineLine() + "行,没有校验格式不允许指定附加格式");
			}
			if (ParameterFormatEnum.Long.equals(paramDef.getFormat())
					|| ParameterFormatEnum.Integer.equals(paramDef.getFormat())
					|| ParameterFormatEnum.BigDecimal.equals(paramDef.getFormat())) {
				throw new TemplateValidateException(
						"定义文件内容错误,第" + paramDef.getDefineLine() + "行,不允许对" + paramDef.getFormat() + "校验格式指定附加格式");
			}
		} else {
			if (paramDef.getFormat() != null) {
				if (!ParameterFormatEnum.Long.equals(paramDef.getFormat())
						&& !ParameterFormatEnum.Integer.equals(paramDef.getFormat())
						&& !ParameterFormatEnum.BigDecimal.equals(paramDef.getFormat())) {
					throw new TemplateValidateException(
							"定义文件内容错误,第" + paramDef.getDefineLine() + "行," + paramDef.getFormat() + "校验格式必需要指定附加格式");
				}
			}
		}
		paramDef.setFormatPattern(formatPattern);
		String length = eh.getString(6);
		if (length != null) {
			paramDef.setLength(Integer.parseInt(length.trim()));
		}
		paramDef.setDescription(eh.getStringNvl(7).trim());
		switch (paramDef.getType()) {
		case String:
			return paramDef;
		case Array:
		case Object:
			Thread.yield();
			List<ParameterDefinition> detailParamList = new LinkedList<ParameterDefinition>();
			paramDef.setDetailParamList(detailParamList);
			ParameterDefinition detailParam;
			while ((detailParam = getParamDefinition(eh, paramDef)) != null) {
				detailParamList.add(detailParam);
			}
			return paramDef;

		default:
			return null;
		}

	}

	protected static ParameterDefinition makeBean(String rootPath, String path, ParameterDefinition paramDef)
			throws Exception {
		
		if (ParameterTypeEnum.String.equals(paramDef.getType())) {
			// 参数类型是字符串类型
			ParameterFormatEnum format = paramDef.getFormat();
			if (ParameterFormatEnum.Long.equals(format) || ParameterFormatEnum.Integer.equals(format)
					|| ParameterFormatEnum.BigDecimal.equals(format)) {
				paramDef.setJavaType(format.name());
			} else {
				paramDef.setJavaType(ParameterTypeEnum.String.name());
			}
			return paramDef;
		} else {
			if (ParameterTypeEnum.Object.equals(paramDef.getType())) {
				//参数是对象类型,生成对应的Bean
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("rootPath", rootPath);
				dataMap.put("path", path);
				dataMap.put("paramDef", paramDef);
				paramDef.setJavaType(StringUtil.firstUpperCase(paramDef.getName()));
				for (ParameterDefinition detailParamDef : paramDef.getDetailParamList()) {
					makeBean(rootPath, path, detailParamDef);
				}
				ITemplateBuilder templateBuilder = TemplateFactory.getTemplateBuilder();
				System.out.println(templateBuilder.build(TEMPLATE_FILE_PATH, dataMap));
			} else {
				//参数是数组类型
				String javaType = "List<";
				ParameterDefinition detailBean = makeBean(rootPath, path, paramDef.getDetailParamList().get(0));
				javaType = javaType + detailBean.getJavaType() + ">";
				paramDef.setJavaType(javaType);
			}
		}
		return paramDef;
	}

	private static void validateParamList(List<ParameterDefinition> paramList, ParameterDefinition parent) throws TemplateValidateException {
		if (parent == null && paramList.size() > 1) {

			throw new TemplateValidateException("定义文件内容不正确,第" + paramList.get(1).getDefineLine() + "行,参数列表只能有一个根对象");
		}

		for (ParameterDefinition paramDef : paramList) {

			if (ParameterTypeEnum.Array.equals(paramDef.getType())) {
				if (paramDef.getDetailParamList() == null || paramDef.getDetailParamList().size() < 1) {
					throw new TemplateValidateException(
							"定义文件内容不正确,第" + paramDef.getDefineLine() + "行" + paramDef.getName() + "数组内必需包含一个属性");
				}
				if (paramDef.getDetailParamList().size() > 1) {
					throw new TemplateValidateException(
							"定义文件内容不正确,第" + paramDef.getDefineLine() + "行" + paramDef.getName() + "数组内只能包含一个属性");
				}
				validateParamList(paramDef.getDetailParamList(), paramDef);
			}
			if (ParameterTypeEnum.Object.equals(paramDef.getType())) {
				if (paramDef.getDetailParamList() == null || paramDef.getDetailParamList().size() < 1) {
					throw new TemplateValidateException(
							"定义文件内容不正确,第" + paramDef.getDefineLine() + "行" + paramDef.getName() + "对象内至少要包含一个属性");
				}
				validateParamList(paramDef.getDetailParamList(), paramDef);
			}
		}
	}
}
