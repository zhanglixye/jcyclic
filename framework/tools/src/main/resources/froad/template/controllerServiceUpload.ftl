package ${controllerServicePackage};
import org.springframework.web.multipart.MultipartFile;
/**
 * @author wanght
 *
 */
@Service
public class ${controllerServiceName?cap_first} {
	/**
	 * ${name}
	 * @param clientId 客户端ID
	 * @param updateUser 当前操作用户
	 * @param file 上传文件
	 * @return 处理结果
	 * @throws TException 服务接口调用异常
	 */
	public Map<String, Object> ${path}(String clientId, String updateUser, MultipartFile file) throws TException {
		//初始化处理结果 默认值是成功
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put(ResultEnum.CODE.getCode(), EnumTypes.success.getCode());
		resMap.put(ResultEnum.MESSAGE.getCode(), EnumTypes.success.getMessage());
		
		//excel读取
		Workbook wb = null;
		Sheet sheet = null;
		Row row = null;
		InputStream inputStream = null;
		try {
			inputStream = file.getInputStream();
			if (file.getOriginalFilename().endsWith(".xlsx")){
				wb = new XSSFWorkbook(inputStream);
			}else {
				wb = new HSSFWorkbook(inputStream);
			}
			sheet = wb.getSheetAt(0);
			int total = sheet.getLastRowNum() + 1;
			if (total == 1){
				LogCvt.error("excel文件内容为空");
				resMap.put(ResultEnum.FAIL.getCode(), EnumTypes.illlegal.getCode());
				resMap.put(ResultEnum.MESSAGE.getCode(), "excel文件内容为空");
				return resMap;
			}

			// 遍历
			Set<String> recordKeySet = new HashSet<String>();
			List<${serverInputBeanName}DetailVo> recordList = new LinkedList<${serverInputBeanName}DetailVo>();
			for (int i = 0; i < total; i++) {
				row = sheet.getRow(i);
				// 字段个数
				if (row == null || row.getLastCellNum() != ${inParamList?size}){
					LogCvt.error("上传文件的第" + (i+1) + "行字段个数不匹配");
					resMap.put(ResultEnum.FAIL.getCode(), EnumTypes.illlegal.getCode());
					resMap.put(ResultEnum.MESSAGE.getCode(), "上传文件的第" + (i+1) + "行字段个数不匹配");
					return resMap;
				}
				// 字段顺序
				if (i == 0){
					if(
					<#list inParamList as param>
							<#if param_index != 0>|| </#if>!${rootPath?cap_first}${path?cap_first}UploadFileFieldEnum.${param.name}.getFieldName().equals(JxlsExcelUtil.readCellAsString(sheet, i, ${param_index}))
					</#list>
						) {
						LogCvt.error("excel文件字段顺序错误");
						resMap.put(ResultEnum.FAIL.getCode(), EnumTypes.illlegal.getCode());
						resMap.put(ResultEnum.MESSAGE.getCode(), "excel文件字段顺序错误");
						return resMap;
					}
				}else {
					//组装数据
					${serverInputBeanName}DetailVo ${serverInputBeanName?uncap_first}DetailVo = new ${serverInputBeanName}DetailVo();
					<#list inParamList as param>
					${serverInputBeanName?uncap_first}DetailVo.set${param.name?cap_first}(JxlsExcelUtil.readCellAsString(sheet, i, ${param_index}));
					</#list>
					// 输入参数有效性校验
					<#list inParamList as param>
						<#if param.required >
					if (StringUtils.isEmpty(${serverInputBeanName?uncap_first}DetailVo.get${param.name?cap_first}())) {
						LogCvt.error("${name}失败，原因: 第" + (i+1) + "行[${param.comment}]不能为空");
						resMap.put(ResultEnum.FAIL.getCode(), EnumTypes.illlegal.getCode());
						resMap.put(ResultEnum.MESSAGE.getCode(), "${name}失败，原因: 第" + (i+1) + "行[${param.comment}]不能为空");
						return resMap;
					}
						</#if>
						<#if param.format?? && param.format == "Long" >
							
					if (<#if param.required == false>StringUtils.isNotEmpty(${serverInputBeanName?uncap_first}DetailVo.get${param.name?cap_first}()) && </#if>!NumberUtil.isLong(${serverInputBeanName?uncap_first}DetailVo.get${param.name?cap_first}())) {
						LogCvt.error("${name}失败，原因: 第" + (i+1) + "行[${param.comment}]类型不正确,必需输入整数");
						resMap.put(ResultEnum.FAIL.getCode(), EnumTypes.illlegal.getCode());
						resMap.put(ResultEnum.MESSAGE.getCode(), "${name}失败，原因: 第" + (i+1) + "行[${param.comment}]类型不正确,必需整数");
						return resMap;
					}
						</#if>
						<#if param.format?? && param.format == "Integer" >
							
					if (<#if param.required == false>StringUtils.isNotEmpty(${serverInputBeanName?uncap_first}DetailVo.get${param.name?cap_first}()) && </#if>!NumberUtil.isInteger(${serverInputBeanName?uncap_first}DetailVo.get${param.name?cap_first}())) {
						LogCvt.error("${name}失败，原因: 第" + (i+1) + "行[${param.comment}]类型不正确,必需输入整数");
						resMap.put(ResultEnum.FAIL.getCode(), EnumTypes.illlegal.getCode());
						resMap.put(ResultEnum.MESSAGE.getCode(), "${name}失败，原因: 第" + (i+1) + "行[${param.comment}]类型不正确,必需输入整数");
						return resMap;
					}
						</#if>
						<#if param.format?? && param.format == "Number" >
					if (<#if param.required == false>StringUtils.isNotEmpty(${serverInputBeanName?uncap_first}DetailVo.get${param.name?cap_first}()) && </#if>!NumberUtils.isNumber(${serverInputBeanName?uncap_first}DetailVo.get${param.name?cap_first}())) {
						LogCvt.error("${name}失败，原因: 第" + (i+1) + "行[${param.comment}]类型不正确,必需输入数值");
						resMap.put(ResultEnum.FAIL.getCode(), EnumTypes.illlegal.getCode());
						resMap.put(ResultEnum.MESSAGE.getCode(), "${name}失败，原因: 第" + (i+1) + "行[${param.comment}]类型不正确,必需输入数值");
						return resMap;
					}
						</#if>
						<#if param.length??>
					if (<#if param.required == false>StringUtils.isNotEmpty(${serverInputBeanName?uncap_first}DetailVo.get${param.name?cap_first}()) && </#if>${serverInputBeanName?uncap_first}DetailVo.get${param.name?cap_first}().length() > ${param.length}) {
						LogCvt.error("${name}失败，原因: 第" + (i+1) + "行[${param.comment}]长度不正确,最多允许输入${param.length}个字符");
						resMap.put(ResultEnum.FAIL.getCode(), EnumTypes.illlegal.getCode());
						resMap.put(ResultEnum.MESSAGE.getCode(), "${name}失败，原因: 第" + (i+1) + "行[${param.comment}]长度不正确,最多允许输入${param.length}个字符");
						return resMap;
					}
						</#if>
					</#list>
					// 判断是否导入数据中是否已存在
					if (recordKeySet.contains(${serverInputBeanName?uncap_first}DetailVo.getXXX())){
						LogCvt.info("跳过重复数据，内容:" + JSON.toJSONString(${serverInputBeanName?uncap_first}DetailVo));
						continue;
					}else {
						recordKeySet.add(${serverInputBeanName?uncap_first}DetailVo.getXXX());
					}
					recordList.add(${serverInputBeanName?uncap_first}DetailVo);
				}
			}
		
			//调用后端服务接口
			${serverInputBeanName} ${serverInputBeanName?uncap_first} = new ${serverInputBeanName}();
			${serverInputBeanName?uncap_first}.setUpdateUser(updateUser);
			${serverInputBeanName?uncap_first}.setClientId(clientId);
			${serverInputBeanName?uncap_first}.setRecordList(recordList);
			ResultVo resultVo = sss.yyy(${serverInputBeanName?uncap_first});
			LogCvt.info("${name}接口返回：" + JSON.toJSONString(resultVo));
			resMap.put(ResultEnum.CODE.getCode(), resultVo.getResultCode());
			resMap.put(ResultEnum.MESSAGE.getCode(), resultVo.getResultDesc());
			return resMap;
		} catch (IOException e) {
			LogCvt.error("${name}请求失败，原因:" + e.getMessage(), e);
			resMap.put(ResultEnum.FAIL.getCode(), EnumTypes.fail.getCode());
			resMap.put(ResultEnum.MESSAGE.getCode(), "${name}请求失败，原因:文件操作异常");
			return resMap;
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (Exception e) {
					LogCvt.error("文件流关闭失败");
				}
			}
		}
	}
}
