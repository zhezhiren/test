package com.plj.action.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.plj.common.error.MyError;
import com.plj.common.result.JsonResult;
import com.plj.common.result.ListData;
import com.plj.domain.decorate.sys.Message;
import com.plj.domain.request.common.DeleteByLongIds;
import com.plj.domain.request.sys.GetMessagesRequest;
import com.plj.service.sys.MessageService;

@Controller
@RequestMapping("/message")
public class MessageAction
{
	@Autowired
	@Qualifier("messageService")
	MessageService messageService;
	
	@RequestMapping("/addMessage.json")
	@ResponseBody
	public Object addMessage(HttpServletRequest request, Message message)
	{
		String userName = (String) request.getSession().getAttribute("userName");
		JsonResult result = new JsonResult();
		if(message != null && message.getContent() != null)
		{
			message.setCreateBy(userName);
			message.init();
			int i = messageService.insert(message);
			if(i <= 0)
			{
				result.addError("", "");
			}
		}else
		{
			result.addError("", "");
		}
		return result;
	}
	
	@RequestMapping("/updateMessage.json")
	@ResponseBody
	Object updateMessage(Message message)
	{
		JsonResult result = new JsonResult();
		int i = messageService.update(message);
		if(i <= 0)
		{
			result.addError("", "");
		}
		return result;
	}
	
	@RequestMapping("/getMessages.json")
	@ResponseBody
	public Object getMessages(GetMessagesRequest request)
	{
		JsonResult result = new JsonResult();
		List<MyError> errors = request.checkError();
		if(null != errors)
		{
			result.setErrors(errors);
			return result;
		}
		List<Message> messages  = messageService.getMessages(request.getTime1()
				, request.getTime2(), request.getContent(), null, request);
		ListData<Message> data = new ListData<Message>(request.getTotalCount(), messages);
		result.setData(data);
		return result;
	}
	
	//已经移动至HomeAction�?
	/*@RequestMapping("/getLastMessage.json")
	@ResponseBody
	public Object getLastMessage()
	{
		JsonResult result = new JsonResult();
		Message message  = messageService.getLastMessage();
		result.setData(message);
		return result;
	}*/
	
	@RequestMapping("/getMessageById.json")
	@ResponseBody
	public Object getMessageById(Long id)
	{
		JsonResult result = new JsonResult();
		if(null != id)
		{
			Message message  = messageService.getMessageByid(id);
			result.setData(message);
		}
		return result;
	}
	
	@RequestMapping("/deleteMessages.json")
	@ResponseBody
	public Object deleteMessages(DeleteByLongIds ids)
	{
		JsonResult result = new JsonResult();
		if(null != ids)
		{
			int i = messageService.deleteByIds(ids.getIds());
			result.setData(i);
		}
		return result;
	}
	
	
}
