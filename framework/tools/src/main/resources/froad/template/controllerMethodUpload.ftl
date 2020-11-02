	/**
	 * ${name}请求的入口
	 * @param model 模型对象
	 * @param req request对象
	 * @param file 上传文件
	 */
	@RequestMapping(value = "/${path}", method = RequestMethod.${method})
	public void ${path}(ModelMap model, HttpServletRequest req,
			@RequestParam("file") MultipartFile file) {
		LogCvt.info("${name}请求到达,文件名:" + file.getOriginalFilename());
		String clientId = req.getAttribute(Constants.CLIENT_ID) + "";
		BankOperatorVo bankOperator = (BankOperatorVo) req.getAttribute(Constants.BANKOPERATOR);
		LogCvt.info("操作员信息返回" + JSON.toJSONString(bankOperator));
		try {
			// 参数有效性校验
			String fileName = file.getOriginalFilename();
			if(fileName.indexOf(".xlsx") == -1 && fileName.indexOf(".xls") == -1) {
				LogCvt.info("文件格式有误:" + fileName);
				model.put(ResultEnum.FAIL.getCode(), EnumTypes.illlegal.getCode());
				model.put(ResultEnum.MESSAGE.getCode(), "文件格式有误");
				return;
			}
		
			// 调用服务
			model.putAll(${controllerServiceName?uncap_first}.${path}(clientId, bankOperator.getUsername(), file));
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
