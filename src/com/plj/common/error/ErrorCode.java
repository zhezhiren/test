package com.plj.common.error;

/**
 * 本接口用来储存错误常量代�?
 * @author zhengxing
 * @version 1.0
 * @date 2013.1.17
 */
public interface ErrorCode
{
	public interface CommonCode
	{
		String DELETE_IDS_NULL = "1";
		
		String DELETE_PARAM_ERROR = "1";
		
		String TYPE_CONVERT_FAIL="1";
		
		String DATA_TYPE_ERROR = "911";
	}
	
	public interface SystemCode
	{
		String UNKNOW = "1";
		
		String ROLE_IS_NULL = "2";
		
		String ROLE_NAME_IS_NULL = "3";
		
		String ROLE_TYPE_IS_NULL = "4";
		
		String ROLE_DELETE_IDS_NULL = "5";
		
		String ROLE_ID_IS_NULL = "6";
		
		String AUTHORIZATION_IS_NULL = "7";
		
		String APPLICATION_IDS_NULL_DELETE = "8";
		
		String APPLICATION_UPDATE_ID_ERROR = "9";
		
		String APPLICATION_NAME_NULL = "10";
		
		String APPLICATION_TYPE_ERROR = "11";
		/**testSubFunctionData**/
		String SUBFUNCTIONDATA_INSERT_ERROR="12";
		String SUBFUNCTIONDATA_ID_ERROR="13";
		String SUBFUNCTIONDATA_IS_NULL="14";
		
		String OPERATOR_GET_ID_NULL="15";
		
		String OPERATOR_NOT_EXISTS="16";
		
		/**FunctionCode**/
		String FUNCTION_IS_NULL = "17";
		String ORGANIZATION_BEAN_NOT_EXISTS="18";
		String ORGANIZATION_NOT_ID="19";
		/**Employee**/
		String EMPLOYEE_ORGID_IS_NULL="20";
		String EMPLOYEE_ORGNAME_ID_NULL="21";
		String ORGANIZATION_CODE_NOT_EXISTS="22";
		String EMPLOYEE_CODE_NOT_EXISTS="23";
		String ORGANIZATION_UPDATE_BEAN_ERROR="24";
		String ORGANIZATION_RETURN_NULL="25";
		String ORGANIZATION_ORGID_IS_NULL="26";
		/**Role**/
		String ROLEID_IS_NULL="27";
		String ROLENAME_IS_NULL="28";
		/**OperatorRole**/
		String OPERATORROLE_OPERATORID_IS_NULL="29";
		String OPERATORROLE_ROLEID_IS_NULL="30";
		/**Organization**/
		String ORGANIZATION_SEARCHORGANIZATION_OPTYPE_IS_NULL="31";
		/**Employee**/
		String EMPLOYEE_BEAN_NOT_EXISTS = "32";

		String EMPLOYEE_NOT_ID = "33";

		String EMPLOYEE_UPDATE_BEAN_ERROR = "34";

		String EMPLOYEEN_UPDATE_BEAN_ERROR = "35";

		String EMPLOYEE_RETURN_NULL = "36";
		String EMPLOYEE_EMPID_IS_NULL = "37";
		
		/**Menu**/
		String MENU_MENUID_NOT_MATCH="38";
		String MENU_MENULEVEL_NOT_NULL="44";
		String MENU_DISPLAYORDER_NOT_NULL="45";
		String MENU_FUNCCODE_NOT_NULL="46";
		String MENU_MENUID_NOT_NULL="47";
		String MENU_MENUID_DATA_CONVERSION_FAIL="48";

		String OPERATOR_ID_ERROR = "38";
		String OPERATOR_UPDATE_ERROR = "39";
		String OPERATOR_RETURN_ERROR = "40";
		
		String FUNCNAME_IS_NULL = "41";
		
		String FUNCGROUPNAME_IS_NULL="42";

		String APPLICATION_FUNCCODE_IS_NULL = "43";

		String APPLICATION_FUNCNAME_IS_NULL = "44";

		String FUNCTION_IDS_NULL_DELETE = "45";
		
		String FUNCGROUP_IS_NULL="46";
		
		String FTP_DOWNLOAD_FILE_NOT_FOUND = "47";
		String FTP_DOWNLOAD_ERROR = "48";
		String FTP_DOWNLOAD_NO_FILE_MATCHED = "49";
		String FTP_DOWNLOAD_TASK_NOT_FOUND = "50";
		String FTP_DOWNLOAD_COLLECT_NOT_FOUND = "51";
		
		//CollectDBConfig
		String COLLECTTASK_IDS_NULL_DELETE="56";
		
		String DB_COLLECT_DRIVER_NOT_SUPPORTED = "57";
		
		String INSERT_COLLECTINFO_BY_NULL_ERROR="58";

		String INSERT_FILE_COLLECT_TIME_ERROR="61";
		
		
		
		String FIELD_IS_NULL="64";

		String DB_COLLECT_ERROR = "65";
		
		String COLLECT_INFO_INSERT_DB_IS_NULL="66";
		
		String INSERT_COLLECT_TASK_ERROR="73";
		
		
		String REVICE_IS_NULL="78";
		
		String ORGID_IS_NULL="79";
	}
}
