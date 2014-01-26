package com.plj.common.result;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.plj.common.error.ErrorCode.SystemCode;
import com.plj.common.error.ErrorMsg.SystemMsg;
import com.plj.common.error.MyError;

public class JsonResult implements Serializable
{
	public static boolean TRUE = true;
	
	private static boolean FALSE = false;
	
	private static final long serialVersionUID = -7045259646067973776L;
	
	private boolean success;//本次请求是否成功
	private List<MyError> errors;//本次请求失败的原因列�?
	private Object data;//本次请求成功返回的数�?
	
	public JsonResult()
	{
		success = true;//请求默认为成功的
	}
	
	public void addError(String errorCode, String errorMsg){
		addError(new MyError(errorCode, errorMsg));
	}
	
	public void addError(MyError error)
	{
		if(null != error)//错误对象部位�?
		{
			if(null == errors)
			{
				errors = new ArrayList<MyError>();//错误列表为空则初始化
			}
			errors.add(error);
			success = FALSE;//错误列表不为空，则success设置为false
		}
	}
	
	public boolean isSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public List<MyError> getErrors() {
		return errors;
	}
	
	public void setErrors(List<MyError> errors) {
		this.errors = errors;
	}
	
	public Object getData() {
		
		if(success)//如果没有错误，则返回数据，有错误则返回data为null
		{
			return data;
		}
		return null;
	}
	
	/**
	 * 获取缓存在jsonResult对象中的数据�?
	 * @param clearCacheData 是否在获取data数据后，清空data�?
	 * @return
	 */
	public Object getCacheData(boolean clearCacheData) {
		Object cache = data;
		if(clearCacheData)
		{
			this.data = null;
		}
		return cache;
	}
	
	public void setData(Object data) {
		this.data = data;
	}
	
	public String errorMsgs()
	{
		String errorMsgs = null;
		if(null != errors && errors.size() > 0)
		{
			StringBuilder errorMsgsBuilder = new StringBuilder();
			for(int i = 0; i < errors.size(); i++)
			{
				MyError error = errors.get(i);
				errorMsgsBuilder.append(error.getErrorMsg()).append("  ");
			}
			errorMsgs = errorMsgsBuilder.toString();
		}
		return errorMsgs;
	}
	
	
	public static void main(String[] args)
	{
		JsonResult js = new JsonResult();
		js.setSuccess(true);
		js.setData("测试");
		System.out.println(JSON.toJSON(js));
		
		JsonResult js2 = new JsonResult();
		js.setSuccess(false);
		List<MyError> errors = new ArrayList<MyError>();
		MyError error1 = new MyError();
		error1.setErrorCode(SystemCode.UNKNOW);
		error1.setErrorMsg(SystemMsg.UNKNOW);
		MyError error2 = new MyError();
		errors.add(error1);
		errors.add(error2);
		js2.setErrors(errors);
		System.out.println(JSON.toJSON(js2));
	}
}
