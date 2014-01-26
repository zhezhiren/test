package com.plj.service.sys.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plj.common.tools.mybatis.page.bean.Pagination;
import com.plj.dao.sys.MessageDao;
import com.plj.domain.decorate.sys.Message;
import com.plj.service.sys.MessageService;

@Service("messageService")
public class MessageServiceImpl implements MessageService
{
	@Autowired
	MessageDao messageDao;
	
	public int insert(Message message)
	{
		int i = 0;
		if(null != message && null != message.getCreateBy()
				&& null != message.getContent())
		{
			i = messageDao.insert(message);
		}else
		{
			return -1;
		}
		return i;
	}

	@Override
	public int update(Message message)
	{
		int i = 0;
		if(null != message && null != message.getId() 
				&& null != message.getContent())
		{
			i = messageDao.updateByIdSelective(message);
		}else
		{
			i = -1;
		}
		return i;
	}

	@Override
	public Message getLastMessage()
	{
		return messageDao.selectLastMessage();
	}

	@Override
	public Message getMessageByid(Long id)
	{
		Message message = null;
		if(null != id)
		{
			message = messageDao.selectById(id);
		}
		return message;
	}

	@Override
	public List<Message> getMessages(Date createTimeStart, Date createTimeEnd,
			String content, String create, Pagination page)
	{
		HashMap<String, Object> map = new HashMap<String, Object>(4);
		map.put("createTimeStart", createTimeStart);
		map.put("createTimeEnd", createTimeEnd);
		if(StringUtils.isNotBlank(content))
		{
			map.put("content", content);
		}
		if(StringUtils.isNotBlank(create))
		{
			map.put("create", create);
		}
		map.put("page", page);
		
		return messageDao.getMessages(map);
	}

	@Override
	public int deleteByIds(List<Long> ids)
	{
		if(null != ids && ids.size() > 0)
		{
			return messageDao.deleteByIds(ids);
		}
		return 0;
	}
	
}
