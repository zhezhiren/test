package com.plj.action.other;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.plj.domain.request.common.CommonDeleteByIds;
import com.plj.service.common.CommonService;

@Controller
@RequestMapping("common")
public class BaseAction
{
	@Autowired
	@Qualifier("commonService")
	CommonService commonService;
	
	@RequestMapping("commonDeleteById.json")
	@ResponseBody
	public Object commonDeleteById(String tableName, String field, long id)
	{
		int i = commonService.deleteByPrimaryKey(tableName, field, id);
		return i;
	}
	
	@RequestMapping("commonDeleteByIds.json")
	@ResponseBody
	public Object commonDeleteByIds(CommonDeleteByIds ids)
	{
		int result = 0;
		if(ids != null && ids.getIds() != null & ids.getIds().get(1) != null)
		{
			result = commonService.deleteByPrimaryKeys(ids.getTableName(), ids.getField(), ids.getIds());
		}
		return result;
	}
	
	
}
