package com.plj.service.sys;

import java.util.Date;
import java.util.List;

import com.plj.common.tools.mybatis.page.bean.Pagination;
import com.plj.domain.decorate.sys.Message;

public interface MessageService 
{
	public int insert(Message message);
	
	public int update(Message message);
	
	public Message getLastMessage();
	
	public Message getMessageByid(Long id);
	
	public List<Message> getMessages(Date createTimeStart, Date createTimeEnd
			, String content, String create, Pagination page);
	
	public int deleteByIds(List<Long> ids);
}
