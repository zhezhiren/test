package com.plj.common.constants;

/**
 * ϵͳ�еĳ����ֶ�ö�����ͣ�ÿһ��ö�ٴ���һ��Ϸ������ݡ�
 * 
 * @author zhezhiren
 * 
 */
public class FieldEnum {

	/**
	 * Ա��״̬
	 * 
	 * @author bin
	 * 
	 */
	public enum EmployeesStatus implements DropdownType {
		noraml("����", true), delete("ɾ��", true);
		private String value;
		private boolean isShow;

		EmployeesStatus(String value, boolean isShow) {
			this.value = value;
			this.setShow(isShow);
		}

		public boolean isShow() {
			return isShow;
		}

		private void setShow(boolean isShow) {
			this.isShow = isShow;
		}

		public String getValue() {
			return value;
		}
	}
}
