	/**
	 * ${name}请求的入口
	 * @param model 模型对象
	 * @param req request对象
	 <#if inParamList?? && inParamList?size != 0  >
	 * @param reqVo 请求参数
	 </#if>
	 */
	@RequestMapping(value = "/${path}", method = RequestMethod.${method})
	public void ${path}(ModelMap model, HttpServletRequest req
			<#if inParamList?? && inParamList?size != 0>,@RequestBody ${controllerInputBeanName} reqVo</#if>) {
		LogCvt.info("${name}请求参数：" + JSON.toJSONString(reqVo));
		String clientId = req.getAttribute(Constants.CLIENT_ID) + "";
		BankOperatorVo bankOperator = (BankOperatorVo) req.getAttribute(Constants.BANKOPERATOR);
		LogCvt.info("操作员信息返回" + JSON.toJSONString(bankOperator));
		try {
			// 参数有效性校验
			<#list inParamList as param>
				<#if param.required >
			if (StringUtil.isEmpty(reqVo.get${param.name?cap_first}())) {
				LogCvt.error("${name}失败，原因: ${param.comment}不能为空");
				model.put(ResultEnum.FAIL.getCode(), EnumTypes.thrift_err.getCode());
				model.put(ResultEnum.MESSAGE.getCode(), "${param.comment}不能为空");
				return;
			}
				</#if>
				<#if param.format?? && param.format == "Long" >
					<#if param.required == false>
			if (StringUtil.isNotEmpty(reqVo.get${param.name?cap_first}()) && 
					<#else>
			if (
					</#if>
			 !NumberUtil.isLong(reqVo.get${param.name?cap_first}())) {
				LogCvt.error("${name}失败，原因: ${param.comment}类型不正确,必需输入整数");
				model.put(ResultEnum.FAIL.getCode(), EnumTypes.thrift_err.getCode());
				model.put(ResultEnum.MESSAGE.getCode(), "${param.comment}类型不正确,必需输入整数");
				return;
			}
				</#if>
				<#if param.format?? && param.format == "Integer" >
					<#if param.required == false>
			if (StringUtil.isNotEmpty(reqVo.get${param.name?cap_first}()) && 
					<#else>
			if (
					</#if>
			 !NumberUtil.isInteger(reqVo.get${param.name?cap_first}())) {
				LogCvt.error("${name}失败，原因: ${param.comment}类型不正确,必需输入整数");
				model.put(ResultEnum.FAIL.getCode(), EnumTypes.thrift_err.getCode());
				model.put(ResultEnum.MESSAGE.getCode(), "${param.comment}类型不正确,必需输入整数");
				return;
			}
				</#if>
			</#list>

			// 调用服务
			model.putAll(${controllerServiceName?uncap_first}.${path}(clientId<#if type != "query">, bankOperator.getUsername()</#if><#if inParamList?? && inParamList?size != 0  >, reqVo</#if>));
		} catch (TException e) {
			LogCvt.error("${name}请求失败，原因:" + e.getMessage(), e);
			model.put(ResultEnum.FAIL.getCode(), EnumTypes.thrift_err.getCode());
			model.put(ResultEnum.MESSAGE.getCode(), EnumTypes.thrift_err.getMessage());
		} catch (Exception e) {
			LogCvt.error("${name}失败，原因:" + e.getMessage(), e);
			model.put(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getDescrition());
			model.put(ResultEnum.MESSAGE.getCode(), EnumTypes.syserr.getMessage());
		}
	}
