package com.plj.action.sys;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.plj.common.constants.DropdownType;
import com.plj.common.constants.FieldEnum.EmployeesStatus;


@Controller
@RequestMapping(value = "/common")
public class CommonAction 
{
	
	@RequestMapping(value = "/getBaseData.do")
	@ResponseBody
	public Object getBaseData(String type, Boolean isAll,
			HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			TypeFactory typeFactory = Enum.valueOf(TypeFactory.class, type);
			if(null == isAll || !isAll)//是否获取�?��数据，目前仅对CollectType进行了处�?
			{
				return JSON.toJSON(typeFactory.getFactory().getPartData());
			}else
			{
				return JSON.toJSON(typeFactory.getFactory().getAllData());
			}
		}catch (Exception e)
		{
			return new String[1][1];
		}
	}
}

enum TypeFactory
{
	employeesStatus(DataFactory.create(EmployeesStatus.values()));
	private DataFactory factory;
	TypeFactory(DataFactory factory)
	{
		this.factory = factory;
	}
	public DataFactory getFactory()
	{
		return factory;
	}
}

class DataFactory
{
	protected DataFactory()
	{
	}
	
	protected DataFactory(DropdownType[] values)
	{
		int size = values.length;
		allData = new String[size][2];
		String[][] partBuff = new String[size][2];//部分数据的缓存，长度初始为枚举数�?
		int alli = 0;//�?��数据的长�?
		int parti = 0;
		for(DropdownType e : values)
		{
			String[] buffer = new String[2];
			buffer[0] = e.name();
			buffer[1] = e.getValue();
			allData[alli] = buffer;
			alli++;
			if(e.isShow())
			{
				partBuff[parti] = buffer;
				parti++;
			}
		}
		partData = Arrays.copyOf(partBuff, parti);
	}

	public static DataFactory create(DropdownType[] values){
		return new DataFactory(values);
	}
	protected String[][] partData = null;	
	protected String[][] allData = null;
	
	public String[][] getPartData()
	{
		return partData;
	}
	
	public String[][] getAllData()
	{
		return allData;
	}
	
}