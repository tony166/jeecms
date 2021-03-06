package com.jeecms.cms.action.front;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeecms.cms.entity.main.CmsSite;
import com.jeecms.cms.service.CmsSiteFlowCache;
import com.jeecms.cms.web.CmsUtils;
import com.jeecms.common.web.RequestUtils;
import com.jeecms.common.web.ResponseUtils;

@Controller
public class CmsSiteFlowAct {
	
	
	/**
	 * 站点流量统计
	 * @since 1.0 
	 * @param request
	 * @param response
	 * @param page
	 * @param referer
	 * <br><b>作者： @author 朱瑞</b>
	 * <br>创建时间：2015年9月16日 下午5:16:05
	 */
	@RequestMapping("/flow_statistic.jspx")
	public void flowStatistic(HttpServletRequest request,
			HttpServletResponse response, String page, String referer) {
		if (!StringUtils.isBlank(page)) {
			String ip = RequestUtils.getIpAddr(request);
			CmsSite site = CmsUtils.getSite(request);
			String sessionId = RequestUtils.getRequestedSessionId(request);
			cmsSiteFlowCache.flow(site, ip, sessionId, page, referer);
			ResponseUtils.renderJson(response, "true");
		} else {
			ResponseUtils.renderJson(response, "false");
		}
	}
	
	@Autowired
	private CmsSiteFlowCache cmsSiteFlowCache;
}
