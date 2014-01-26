package com.plj.service.sys;

import java.util.List;

import com.plj.domain.response.sys.AlertInfo;
import com.plj.domain.response.sys.MainAlertInfo;

/**
 * 首页接口
 * @author bin
 *
 */
public interface MainService {

	List<MainAlertInfo> getAlertInfo();

	AlertInfo getAlertDetail(String id);

}
