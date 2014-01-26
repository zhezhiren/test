package com.plj.domain.base.sys;

import java.io.Serializable;
import java.util.Date;

public class BaseMessage implements Serializable
{
	private static final long serialVersionUID = 1437133491633409947L;

	private Long id;

    private String createBy;

    private String content;

    private Date effectiveStart;

    private Date effectiveEnd;

    private Date createTime;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getCreateBy()
    {
        return createBy;
    }

    public void setCreateBy(String createBy)
    {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content == null ? null : content.trim();
    }

    public Date getEffectiveStart()
    {
        return effectiveStart;
    }

    public void setEffectiveStart(Date effectiveStart)
    {
        this.effectiveStart = effectiveStart;
    }

    public Date getEffectiveEnd()
    {
        return effectiveEnd;
    }

    public void setEffectiveEnd(Date effectiveEnd)
    {
        this.effectiveEnd = effectiveEnd;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
}