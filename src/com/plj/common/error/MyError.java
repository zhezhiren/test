package com.plj.common.error;

import java.io.Serializable;

/**
 * 错误消息对象
 * @author zhengxing
 * @version 1.0
 * @date 2013.1.17
 */
public class MyError implements ErrorCode , ErrorMsg, Serializable
{
	public MyError()
	{
		
	}
	
	public MyError(String code, String msg)
	{
		this.errorCode = code;
		this.errorMsg = msg;
	}
	
	public String getErrorCode()
    {
    	return errorCode;
    }

	public void setErrorCode(String errorCode)
    {
    	this.errorCode = errorCode;
    }

	public String getErrorMsg()
    {
    	return errorMsg;
    }

	public void setErrorMsg(String errorMsg)
    {
    	this.errorMsg = errorMsg;
    }
	
	public int hashCode ()
	{
		if (Integer.MIN_VALUE == this.hashCode) 
		{
			if (null == this.errorCode)
			{
				return super.hashCode();
			}else
			{
				String hashStr = this.getClass().getName() + ":" + this.errorCode.hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}
	
	public boolean equals(Object obj)
	{
		if (null == obj)
		{ 
			return false;
		}
		if (!(obj instanceof MyError))
		{
			return false;
		}else
		{
			MyError error = (MyError) obj;
			if (null == this.errorCode || null == error.getErrorCode())
			{
				return false;
			}else
			{
				return (this.errorCode.equals(error.getErrorCode()));
			}
		}
	}
	
	private String errorCode;
	
	private String errorMsg;
	
	private static final long serialVersionUID = 8537290776237780557L;
	
	private int hashCode = Integer.MIN_VALUE;;
}
