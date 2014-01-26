package com.plj.common.constants;

/**
 * 系统中的常量字段枚举类型，每一个枚举代表一类合法的数据。
 * 
 * @author zhezhiren
 * 
 */
public class FieldEnum {

	/**
	 * 员工状态
	 * 
	 * @author bin
	 * 
	 */
	public enum EmployeesStatus implements DropdownType {
		noraml("正常", true), delete("删除", true);
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
