package com.sbplatform.web.system.controller.core;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quartz.CronTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sbplatform.core.common.controller.BaseController;
import com.sbplatform.core.common.hibernate.qbc.CriteriaQuery;
import com.sbplatform.core.common.model.json.AjaxJson;
import com.sbplatform.core.common.model.json.DataGrid;
import com.sbplatform.core.constant.Globals;
import com.sbplatform.core.timer.DynamicTask;
import com.sbplatform.core.util.MyBeanUtils;
import com.sbplatform.core.util.StringUtil;
import com.sbplatform.tag.core.easyui.TagUtil;
import com.sbplatform.web.system.pojo.base.TimeTask;
import com.sbplatform.web.system.service.SystemService;
import com.sbplatform.web.system.service.TimeTaskService;


/**   
 * @Title: Controller
 * @Description: 定时任务管理
 * @author jueyue
 * @date 2013-09-21 20:47:43
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/timeTaskController")
public class TimeTaskController extends BaseController {

	@Autowired
	private TimeTaskService timeTaskService;
	@Autowired
	private DynamicTask dynamicTask;
	@Autowired
	private SystemService systemService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 定时任务管理列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "timeTask")
	public ModelAndView timeTask(HttpServletRequest request) {
		return new ModelAndView("system/timetask/timeTaskList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TimeTask timeTask,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TimeTask.class, dataGrid);
		//查询条件组装器
		com.sbplatform.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, timeTask, request.getParameterMap());
		this.timeTaskService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除定时任务管理
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(TimeTask timeTask, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		timeTask = systemService.getEntity(TimeTask.class, timeTask.getId());
		message = "定时任务管理删除成功";
		timeTaskService.delete(timeTask);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		j.setMessage(message);
		return j;
	}


	/**
	 * 添加定时任务管理
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(TimeTask timeTask, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		CronTrigger trigger = new CronTrigger();
		try {
			trigger.setCronExpression(timeTask.getCronExpression());
		} catch (ParseException e) {
			j.setMessage("Cron表达式错误");
			return j;
		}
		if (StringUtil.isNotEmpty(timeTask.getId())) {
			message = "定时任务管理更新成功";
			TimeTask t = timeTaskService.get(TimeTask.class, timeTask.getId());
			try {
				if(!timeTask.getCronExpression().equals(t.getCronExpression())){
					timeTask.setIsEffect("0");
				}
				MyBeanUtils.copyBeanNotNull2Bean(timeTask, t);
				timeTaskService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "定时任务管理更新失败";
			}
		} else {
			message = "定时任务管理添加成功";
			timeTaskService.save(timeTask);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMessage(message);
		return j;
	}

	/**
	 * 定时任务管理列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(TimeTask timeTask, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(timeTask.getId())) {
			timeTask = timeTaskService.getEntity(TimeTask.class, timeTask.getId());
			req.setAttribute("timeTaskPage", timeTask);
		}
		return new ModelAndView("system/timetask/timeTask");
	}
	
	/**
	 * 更新任务时间使之生效
	 */
	@RequestMapping(params = "updateTime")
	@ResponseBody
	public AjaxJson updateTime(TimeTask timeTask, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		timeTask = timeTaskService.get(TimeTask.class, timeTask.getId());
		boolean isUpdate = dynamicTask.updateCronExpression(timeTask.getTaskId() , timeTask.getCronExpression());
		if(isUpdate){
			timeTask.setIsEffect("1");
			timeTask.setIsStart("1");
			timeTaskService.updateEntitie(timeTask);
		}
		j.setMessage(isUpdate?"定时任务管理更新成功":"定时任务管理更新失败");
		return j;
	}
	/**
	 * 启动或者停止任务
	 */
	@RequestMapping(params = "startOrStopTask")
	@ResponseBody
	public AjaxJson startOrStopTask(TimeTask timeTask, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		boolean isStart = timeTask.getIsStart().equals("1");
		timeTask = timeTaskService.get(TimeTask.class, timeTask.getId());
		boolean isSuccess = dynamicTask.startOrStop(timeTask.getTaskId() ,isStart);
		if(isSuccess){
			timeTask.setIsStart(isStart?"1":"0");
			timeTaskService.updateEntitie(timeTask);
			systemService.addLog((isStart?"开启任务":"停止任务")+timeTask.getTaskId(),
					Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}
		j.setMessage(isSuccess?"定时任务管理更新成功":"定时任务管理更新失败");
		return j;
	}
	
}
