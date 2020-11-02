/**
 * @Description ${name}模板字段
 * @Author wanght
 */
public enum ${rootPath?cap_first}${path?cap_first}UploadFileFieldEnum {
	<#list inParamList as param>
	${param.name}("${param.comment}")<#if param_has_next>,<#else>;</#if>
	</#list>
    
    private String fieldName;

    ${rootPath?cap_first}${path?cap_first}UploadFileFieldEnum(String fieldName){
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
