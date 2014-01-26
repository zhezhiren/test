package com.plj.dao.sys;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.plj.domain.decorate.sys.Message;

@Repository
public interface MessageDao 
{
    public int insert(Message record);

    public int insertSelective(Message record);

    public Message selectById(Long id);

    public int updateByIdSelective(Message record);

    public int updateById(Message record);
    
    public Message selectLastMessage();
    
    public List<Message> getMessages(Map<String, Object> map);
    
    public int deleteByIds(@Param("ids") List<Long> ids);
}