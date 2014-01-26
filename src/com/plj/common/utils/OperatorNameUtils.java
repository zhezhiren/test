package com.plj.common.utils;

import com.plj.common.tools.spring.PropertyConfigurer;

public class OperatorNameUtils {
	
	private static final String OP_NAME= "OperatorName";
	public static String[] getAllOperatorNameArray(String code){
		String names = PropertyConfigurer.getContextProperty(OP_NAME+code);
		if(names.length()<=0){
			return null;
		}
		return names.split(",");
	}
	
	public static String[][] getAllOperatorNameMap(String code){
		String names = PropertyConfigurer.getContextProperty(OP_NAME+code);
		if(names.length()<=0){
			return null;
		}
		String[] nameArray =  names.split(",");
		String[][] nameMap = new String[nameArray.length][2];
		for(int i = 0 ; i<nameArray.length;i++ ){
			nameMap[i][0]=nameArray[i];
			nameMap[i][1]=nameArray[i];
		}
		return nameMap;
	}

}
