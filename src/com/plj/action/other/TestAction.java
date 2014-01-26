package com.plj.action.other;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.DispatcherServlet;

import com.plj.common.tools.mybatis.bean.Condition;
import com.plj.common.tools.mybatis.bean.Field;
import com.plj.common.tools.mybatis.bean.Order;
import com.plj.domain.bean.common.CommonTest;
import com.plj.domain.request.common.CommonDeleteByIds;
import com.plj.service.common.CommonService;

@Controller
@RequestMapping("test")
public class TestAction
{
	private static Logger log = LoggerFactory.getLogger(TestAction.class);
	private int count = 13;
	@Autowired
	@Qualifier("commonService")
	CommonService commonService;
	
	@RequestMapping("test.json")
	@ResponseBody
	public Object test(String text, String name)
	{
		System.out.println(text);
		ClassPathResource resource = new ClassPathResource("DispatcherServlet.properties", DispatcherServlet.class);
		log.info(resource.getDescription());
		log.info("info");
		log.debug("debug");
		return "test";
	}
	
	@RequestMapping("test1.json")
	@ResponseBody
	public Object test(HttpServletRequest request)
	{
		System.out.println(request.getRequestURI());
		System.out.println(request.getRequestURL());
		log.info("info");
		log.debug("debug");
		return "test";
	}
	
	/**
	 * qunimalagebi¡£
	 * ÂéÀ±¸ô±Ú
	 * @param request  mabi
	 * @param response  mabi
	 * @return malaga
	 */
	@RequestMapping("testjsp.json")
	public Object test(HttpServletRequest request, HttpServletResponse response)
	{
		
		System.out.println(request.getRequestURI());
		System.out.println(request.getRequestURL());
		log.info("info");
		log.debug("debug");
		return "test";
	}
	
	@RequestMapping("/test.do")
	public Object testdo(HttpServletRequest request, HttpServletResponse response)
	{
		System.out.println(request.getRequestURI());
		System.out.println(request.getRequestURL());
		log.info("info");
		log.debug("debug");
		return "test";
	}
	
	@RequestMapping("/testCommon.json")
	@ResponseBody
	public Object testCommonService(CommonTest bean)
	{
		String statement = bean.getStatement();
		//int i = commonService.insertOne(statement, bean);
		//CommonTest value = commonService.selectOne(statement, id);
		return "test : " + 0;
	}
	
	@RequestMapping("commonUpdate.json")
	@ResponseBody
	public Object commonUpdate()
	{
		return null;
	}
	
	@RequestMapping("commonInsert.json")
	@ResponseBody
	public Object commonInsert()
	{
		return null;
	}
	
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
	
	
	
	@RequestMapping("testInsert.json")
	@ResponseBody
	public Object testInsert()
	{
		String tableName="common_test";
		Field<String> name = new Field<String>("name", "test" + count++);
		Field<String> password = new Field<String>("password", "test1");
		List<Field<?>> fields = new ArrayList<Field<?>>(2);
		fields.add(name);
		fields.add(password);
		int result = 0;
		result = commonService.insertOne(tableName, fields);
		return result;
	}
	
	
	
	@RequestMapping("testUpdate.json")
	@ResponseBody
	public Object testUpdate()
	{
		String tableName="common_test";
		Field<Integer> key = new Field<Integer>("id", 4);
		Field<String> name = new Field<String>("name", "test");
		Field<String> password = new Field<String>("password", "test");
		List<Field<?>> fields = new ArrayList<Field<?>>(2);
		fields.add(name);
		fields.add(password);
		int result = 0;
		result = commonService.updateByPrimaryKey(tableName, key, fields);
		return result;
	}
	
	@RequestMapping("testSelect.json")
	@ResponseBody
	public Object testSelect()
	{
		String tableName="common_test";
		Condition<Integer> key = new Condition<Integer>("id", Condition.Sign.equal, 4);
		ArrayList<Condition<?>> conditions = new ArrayList<Condition<?>>(2);
		conditions.add(key);
		List<Map<String, ?>> result = commonService.selectList(tableName, conditions, null, null);
		return result;
	}
	
	@RequestMapping("testSelect2.json")
	@ResponseBody
	public Object testSelect2()
	{
		String tableName="common_test";
		Condition<String> key = new Condition<String>("name", Condition.Sign.like, "%test%");
		Condition<String> key2 = new Condition<String>(Condition.Connector.AND);
		Condition<String> key3 = new Condition<String>("password", Condition.Sign.equal, "test");
		ArrayList<Condition<?>> conditions = new ArrayList<Condition<?>>(2);
		conditions.add(key);
		conditions.add(key2);
		conditions.add(key3);
		Order order = new Order("id", Order.OrderDir.DESC);
		ArrayList<Order> orders = new ArrayList<Order>();
		orders.add(order);
		List<Map<String, ?>> result = commonService.selectList(tableName, conditions, orders, null);
		return result;
	}
}
