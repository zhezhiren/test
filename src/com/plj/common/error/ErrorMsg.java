package com.plj.common.error;

/**
 * 本接口用来储存错误消息. 可不使用本类存储消息，可使用property文件代替。
 * 
 * @author zhengxing
 * @version 1.0
 * @date 2013.1.17
 */
public interface ErrorMsg {
	public interface CommonMsg 
	{
		String DELETE_IDS_NULL = "请选择需要删除的记录";

		String DELETE_PARAM_ERROR = "删除是参数有误，参数为非整数";

		String TYPE_CONVERT_FAIL = "类型转换失败";

		String DATA_TYPE_ERROR = "下拉数据类型错误";
	}

	public interface SystemMsg {
		String UNKNOW = "未知错误; ";

		String ROLE_IS_NULL = "角色信息不能为空;";

		String ROLE_NAME_IS_NULL = "角色名称不能为空; ";

		String ROLE_TYPE_IS_NULL = "角色类型不能为空; ";

		String ROLE_DELETE_IDS_NULL = "请选择需要删除的角色列表; ";

		String ROLE_ID_IS_NULL = "请输入角色Id; ";

		String AUTHORIZATION_IS_NULL = "授权是参数不能为空;";

		String APPLICATION_IDS_NULL_DELETE = "请选择需要删除的应用;";

		String APPLICATION_UPDATE_ID_ERROR = "修改应用时，应用参数有误";

		String APPLICATION_NAME_NULL = "应用面名称不能为空";

		String APPLICATION_TYPE_ERROR = "应用类型有误";
		/** testSubFunctionData **/
		String SUBFUNCTIONDATA_INSERT_ERROR = "请输入Id";
		String SUBFUNCTIONDATA_ID_ERROR = "参数错误";

		String SUBFUNCTIONDATA_IS_NULL = "参数为空";

		String OPERATOR_GET_ID_NULL = "获取用户详情时，参数有误。";

		String OPERATOR_NOT_EXISTS = "用户不存在;";
		/** FunctionMsg **/
		String FUNCTION_IS_NULL = "参数为空";
		String ORGANIZATION_BEAN_NOT_EXISTS = "插入时数据参数为空";
		String ORGANIZATION_NOT_ID = "ORG参数id为空";

		/** Employee **/
		String EMPLOYEE_ORGID_IS_NULL = "请输入机构组织Id";
		String EMPLOYEE_ORGNAME_ID_NULL = "请输入机构组织名称";
		String ORGANIZATION_CODE_NOT_EXISTS = "ORG重复查询的参数为空";
		String EMPLOYEE_CODE_NOT_EXISTS = "EMP重复查询的参数为空";
		String ORGANIZATION_UPDATE_BEAN_ERROR = "ORG更新的参数为空或者错误";
		String ORGANIZATION_RETURN_NULL = "ORG数据库无此ID对应的记录";
		String ORGANIZATION_ORGID_IS_NULL = "请输入机构Id";

		String EMPLOYEE_NOT_ID = "EMP参数id为空";

		String EMPLOYEE_BEAN_NOT_EXISTS = "插入时数据参数为空";

		String EMPLOYEE_UPDATE_BEAN_ERROR = "EMP更新的参数为空或者错误";

		String EMPLOYEE_RETURN_NULL = "EMP数据库无此ID对应的记录";
		String EMPLOYEE_EMPID_IS_NULL = "请输入职工Ids";

		String ROLEID_IS_NULL = "角色ID为空";
		String ROLENAME_IS_NULL = "角色name为空";
		/** OperatorRole **/
		String OPERATORROLE_OPERATORID_IS_NULL = "请输入用户Id";
		String OPERATORROLE_ROLEID_IS_NULL = "请输入用户的角色Ids";
		/** Organization **/
		String ORGANIZATION_SEARCHORGANIZATION_OPTYPE_IS_NULL = "查询类型有误";

		/** Menu **/
		String MENU_MENUID_NOT_MATCH = "表AC_MENU中找不到可匹配的Id";
		String MENU_MENULEVEL_NOT_NULL = "菜单等级不能为空";
		String MENU_DISPLAYORDER_NOT_NULL = "显示顺序不能为空";
		String MENU_FUNCCODE_NOT_NULL = "功能代码不能为空";
		String MENU_MENUID_NOT_NULL = "菜单ID不能为空";
		String MENU_MENUID_DATA_CONVERSION_FAIL = "数据转换失败";

		String OPERATOR_ID_ERROR = "用户ID错误";
		String OPERATOR_UPDATE_ERROR = "用户修改错误";
		String OPERATOR_RETURN_ERROR = "用户修改返回错误";

		String FUNCNAME_IS_NULL = "查询参数funcName不能为空";
		String FUNCGROUPNAME_IS_NULL = "查询参数funcGroupName不能为空";

		String APPLICATION_FUNCCODE_IS_NULL = "输入值 funcCode 不能为空";

		String APPLICATION_FUNCNAME_IS_NULL = "输入值 funcName 不能为空";

		String FUNCTION_IDS_NULL_DELETE = "需要删除的 function id不能为空";

		// Application
		String FUNCGROUP_IS_NULL = "参数为空";

		String FTP_DOWNLOAD_FILE_NOT_FOUND = "Ftp文件不存在";
		String FTP_DOWNLOAD_ERROR = "Ftp下载错误";

		String FTP_DOWNLOAD_NO_FILE_MATCHED = "没有匹配的文件";

		String FTP_DOWNLOAD_COLLECT_NOT_FOUND = "指定Id的采集信息不存在";

		String FTP_DOWNLOAD_TASK_NOT_FOUND = "指定Id的任务信息不存在";

		// CollectDBConfig
		String COLLECTTASK_IDS_NULL_DELETE = "请选择需要删除的采集任务;";

		String DB_COLLECT_DRIVER_NOT_SUPPORTED = "数据库驱动不支持";

		String INSERT_COLLECTINFO_BY_NULL_ERROR = "新增采集信息失败";

		String INSERT_FILE_COLLECT_TIME_ERROR = "新增文件采集任务失败";

		String FIELD_IS_NULL = "必填字段为空";
		String COLLECT_INFO_INSERT_DB_IS_NULL = "数据库相应字段不允许插入空值，请确保有关字段不为空";
		String DB_COLLECT_ERROR = "数据库采集遇到未知错误";

		String INSERT_COLLECT_TASK_ERROR = "新增采集任务失败";
		String REVICE_IS_NULL = "请选择要修改的信息";

		String ORGID_IS_NULL="数据错误，请与管理员联系";

	}
}
