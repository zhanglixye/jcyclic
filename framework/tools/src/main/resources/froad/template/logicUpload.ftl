	/**
	 * ${name}
	 * @param clientId 客户端ID
	 * @param updateUser 当前操作者
	 * @param recordList 批处理数据
	 * @return ${name}处理结果
	 */
	ResultVo ${path}(
		 String clientId
		,String updateUser
		,List<${serverInputBeanName}DetailVo> recordList
	);
