package com.sbplatform.web.system.pojo.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**   
 * @Title: Entity
 * @Description: 定时任务管理
 * @date 2013-09-21 20:47:43
 * @version V1.0   
 *
 */
@Entity
@Table(name = "s_t_time_task", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class TimeTask implements java.io.Serializable {
	/**id*/
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "ID", nullable = false, length = 32)
	private String id;
	
	/**任务ID*/
	@Column(name = "TASK_ID", nullable = false, length = 100)
	private String taskId;
	
	/**任务描述*/
	@Column(name = "TASK_DESCRIBE", nullable = false, length = 50)
	private String taskDescribe;
	
	/**cron表达式*/
	@Column(name = "CRON_EXPRESSION", nullable = false, length = 100)
	private String cronExpression;
	
	/**是否生效了0未生效,1生效了*/
	@Column(name = "IS_EFFECT", nullable = false, length = 1)
	private String isEffect;
	
	/**是否运行0停止,1运行*/
	@Column(name = "IS_START", nullable = false, length = 1)
	private String isStart;
	/**创建时间*/
	@Column(name = "CREATE_DATE", nullable = true)
	private Date createDate;
	/**创建人ID*/
	@Column(name = "CREATE_BY", nullable = true, length = 32)
	private String createBy;
	/**创建人名称*/
	@Column(name = "CREATE_NAME", nullable = true, length = 32)
	private String createName;
	/**修改时间*/
	@Column(name = "UPDATE_DATE", nullable = true)
	private Date updateDate;
	/**修改人ID*/
	@Column(name = "UPDATE_BY", nullable = true, length = 32)
	private String updateBy;
	/**修改人名称*/
	@Column(name = "UPDATE_NAME", nullable = true, length = 32)
	private String updateName;

	/**
	 *方法: 取得String
	 *@return: String  id
	 */
	public String getId() {
		return this.id;
	}

	/**
	 *方法: 设置String
	 *@param: String  id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 *方法: 取得String
	 *@return: String  任务ID
	 */
	public String getTaskId() {
		return this.taskId;
	}

	/**
	 *方法: 设置String
	 *@param: String  任务ID
	 */
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	/**
	 *方法: 取得String
	 *@return: String  任务描述
	 */
	public String getTaskDescribe() {
		return this.taskDescribe;
	}

	/**
	 *方法: 设置String
	 *@param: String  任务描述
	 */
	public void setTaskDescribe(String taskDescribe) {
		this.taskDescribe = taskDescribe;
	}

	/**
	 *方法: 取得String
	 *@return: String  cron表达式
	 */
	public String getCronExpression() {
		return this.cronExpression;
	}

	/**
	 *方法: 设置String
	 *@param: String  cron表达式
	 */
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	/**
	 *方法: 取得String
	 *@return: String  是否生效了0未生效,1生效了
	 */
	public String getIsEffect() {
		return this.isEffect;
	}

	/**
	 *方法: 设置String
	 *@param: String  是否生效了0未生效,1生效了
	 */
	public void setIsEffect(String isEffect) {
		this.isEffect = isEffect;
	}

	/**
	 *方法: 取得String
	 *@return: String 是否运行0停止,1运行
	 */
	public String getIsStart() {
		return this.isStart;
	}

	/**
	 *方法: 设置String
	 *@param: String  是否运行0停止,1运行
	 */
	public void setIsStart(String isStart) {
		this.isStart = isStart;
	}

	/**
	 *方法: 取得Date
	 *@return: Date  创建时间
	 */
	public Date getCreateDate() {
		return this.createDate;
	}

	/**
	 *方法: 设置Date
	 *@param: Date  创建时间
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 *方法: 取得String
	 *@return: String  创建人ID
	 */
	public String getCreateBy() {
		return this.createBy;
	}

	/**
	 *方法: 设置String
	 *@param: String  创建人ID
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	/**
	 *方法: 取得String
	 *@return: String  创建人名称
	 */
	public String getCreateName() {
		return this.createName;
	}

	/**
	 *方法: 设置String
	 *@param: String  创建人名称
	 */
	public void setCreateName(String createName) {
		this.createName = createName;
	}

	/**
	 *方法: 取得Date
	 *@return: Date  修改时间
	 */
	public Date getUpdateDate() {
		return this.updateDate;
	}

	/**
	 *方法: 设置Date
	 *@param: Date  修改时间
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 *方法: 取得String
	 *@return: String  修改人ID
	 */
	public String getUpdateBy() {
		return this.updateBy;
	}

	/**
	 *方法: 设置String
	 *@param: String  修改人ID
	 */
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	/**
	 *方法: 取得String
	 *@return: String  修改人名称
	 */
	public String getUpdateName() {
		return this.updateName;
	}

	/**
	 *方法: 设置String
	 *@param: String  修改人名称
	 */
	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
}
