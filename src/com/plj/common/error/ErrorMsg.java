package com.plj.common.error;

/**
 * ���ӿ��������������Ϣ. �ɲ�ʹ�ñ���洢��Ϣ����ʹ��property�ļ����档
 * 
 * @author zhengxing
 * @version 1.0
 * @date 2013.1.17
 */
public interface ErrorMsg {
	public interface CommonMsg 
	{
		String DELETE_IDS_NULL = "��ѡ����Ҫɾ���ļ�¼";

		String DELETE_PARAM_ERROR = "ɾ���ǲ������󣬲���Ϊ������";

		String TYPE_CONVERT_FAIL = "����ת��ʧ��";

		String DATA_TYPE_ERROR = "�����������ʹ���";
	}

	public interface SystemMsg {
		String UNKNOW = "δ֪����; ";

		String ROLE_IS_NULL = "��ɫ��Ϣ����Ϊ��;";

		String ROLE_NAME_IS_NULL = "��ɫ���Ʋ���Ϊ��; ";

		String ROLE_TYPE_IS_NULL = "��ɫ���Ͳ���Ϊ��; ";

		String ROLE_DELETE_IDS_NULL = "��ѡ����Ҫɾ���Ľ�ɫ�б�; ";

		String ROLE_ID_IS_NULL = "�������ɫId; ";

		String AUTHORIZATION_IS_NULL = "��Ȩ�ǲ�������Ϊ��;";

		String APPLICATION_IDS_NULL_DELETE = "��ѡ����Ҫɾ����Ӧ��;";

		String APPLICATION_UPDATE_ID_ERROR = "�޸�Ӧ��ʱ��Ӧ�ò�������";

		String APPLICATION_NAME_NULL = "Ӧ�������Ʋ���Ϊ��";

		String APPLICATION_TYPE_ERROR = "Ӧ����������";
		/** testSubFunctionData **/
		String SUBFUNCTIONDATA_INSERT_ERROR = "������Id";
		String SUBFUNCTIONDATA_ID_ERROR = "��������";

		String SUBFUNCTIONDATA_IS_NULL = "����Ϊ��";

		String OPERATOR_GET_ID_NULL = "��ȡ�û�����ʱ����������";

		String OPERATOR_NOT_EXISTS = "�û�������;";
		/** FunctionMsg **/
		String FUNCTION_IS_NULL = "����Ϊ��";
		String ORGANIZATION_BEAN_NOT_EXISTS = "����ʱ���ݲ���Ϊ��";
		String ORGANIZATION_NOT_ID = "ORG����idΪ��";

		/** Employee **/
		String EMPLOYEE_ORGID_IS_NULL = "�����������֯Id";
		String EMPLOYEE_ORGNAME_ID_NULL = "�����������֯����";
		String ORGANIZATION_CODE_NOT_EXISTS = "ORG�ظ���ѯ�Ĳ���Ϊ��";
		String EMPLOYEE_CODE_NOT_EXISTS = "EMP�ظ���ѯ�Ĳ���Ϊ��";
		String ORGANIZATION_UPDATE_BEAN_ERROR = "ORG���µĲ���Ϊ�ջ��ߴ���";
		String ORGANIZATION_RETURN_NULL = "ORG���ݿ��޴�ID��Ӧ�ļ�¼";
		String ORGANIZATION_ORGID_IS_NULL = "���������Id";

		String EMPLOYEE_NOT_ID = "EMP����idΪ��";

		String EMPLOYEE_BEAN_NOT_EXISTS = "����ʱ���ݲ���Ϊ��";

		String EMPLOYEE_UPDATE_BEAN_ERROR = "EMP���µĲ���Ϊ�ջ��ߴ���";

		String EMPLOYEE_RETURN_NULL = "EMP���ݿ��޴�ID��Ӧ�ļ�¼";
		String EMPLOYEE_EMPID_IS_NULL = "������ְ��Ids";

		String ROLEID_IS_NULL = "��ɫIDΪ��";
		String ROLENAME_IS_NULL = "��ɫnameΪ��";
		/** OperatorRole **/
		String OPERATORROLE_OPERATORID_IS_NULL = "�������û�Id";
		String OPERATORROLE_ROLEID_IS_NULL = "�������û��Ľ�ɫIds";
		/** Organization **/
		String ORGANIZATION_SEARCHORGANIZATION_OPTYPE_IS_NULL = "��ѯ��������";

		/** Menu **/
		String MENU_MENUID_NOT_MATCH = "��AC_MENU���Ҳ�����ƥ���Id";
		String MENU_MENULEVEL_NOT_NULL = "�˵��ȼ�����Ϊ��";
		String MENU_DISPLAYORDER_NOT_NULL = "��ʾ˳����Ϊ��";
		String MENU_FUNCCODE_NOT_NULL = "���ܴ��벻��Ϊ��";
		String MENU_MENUID_NOT_NULL = "�˵�ID����Ϊ��";
		String MENU_MENUID_DATA_CONVERSION_FAIL = "����ת��ʧ��";

		String OPERATOR_ID_ERROR = "�û�ID����";
		String OPERATOR_UPDATE_ERROR = "�û��޸Ĵ���";
		String OPERATOR_RETURN_ERROR = "�û��޸ķ��ش���";

		String FUNCNAME_IS_NULL = "��ѯ����funcName����Ϊ��";
		String FUNCGROUPNAME_IS_NULL = "��ѯ����funcGroupName����Ϊ��";

		String APPLICATION_FUNCCODE_IS_NULL = "����ֵ funcCode ����Ϊ��";

		String APPLICATION_FUNCNAME_IS_NULL = "����ֵ funcName ����Ϊ��";

		String FUNCTION_IDS_NULL_DELETE = "��Ҫɾ���� function id����Ϊ��";

		// Application
		String FUNCGROUP_IS_NULL = "����Ϊ��";

		String FTP_DOWNLOAD_FILE_NOT_FOUND = "Ftp�ļ�������";
		String FTP_DOWNLOAD_ERROR = "Ftp���ش���";

		String FTP_DOWNLOAD_NO_FILE_MATCHED = "û��ƥ����ļ�";

		String FTP_DOWNLOAD_COLLECT_NOT_FOUND = "ָ��Id�Ĳɼ���Ϣ������";

		String FTP_DOWNLOAD_TASK_NOT_FOUND = "ָ��Id��������Ϣ������";

		// CollectDBConfig
		String COLLECTTASK_IDS_NULL_DELETE = "��ѡ����Ҫɾ���Ĳɼ�����;";

		String DB_COLLECT_DRIVER_NOT_SUPPORTED = "���ݿ�������֧��";

		String INSERT_COLLECTINFO_BY_NULL_ERROR = "�����ɼ���Ϣʧ��";

		String INSERT_FILE_COLLECT_TIME_ERROR = "�����ļ��ɼ�����ʧ��";

		String FIELD_IS_NULL = "�����ֶ�Ϊ��";
		String COLLECT_INFO_INSERT_DB_IS_NULL = "���ݿ���Ӧ�ֶβ���������ֵ����ȷ���й��ֶβ�Ϊ��";
		String DB_COLLECT_ERROR = "���ݿ�ɼ�����δ֪����";

		String INSERT_COLLECT_TASK_ERROR = "�����ɼ�����ʧ��";
		String REVICE_IS_NULL = "��ѡ��Ҫ�޸ĵ���Ϣ";

		String ORGID_IS_NULL="���ݴ����������Ա��ϵ";

	}
}
