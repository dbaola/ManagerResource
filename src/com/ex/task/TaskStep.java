package com.ex.task;


public class TaskStep {
	

	private int stepId;                  //步骤Id号
	private int[] performerId;           //每一个步骤至少一个参与者，可以根据id查找somebody其它信息
	private String[] performerTask;         //参与者完成的角色
	private String stepContent,stepEndtime;  //步骤内容,完成时限
	private boolean isCompleted;             //步骤是否完成
	private String timeKey;                  //由时间产生的步骤唯一标志号
	private int whichStep;                   //步骤序列号
	private int taskId;
	
	public int getStepId() {
		return stepId;
	}
	public void setStepId(int stepId) {
		this.stepId = stepId;
	}
	public int[] getPerformerId() {
		return performerId;
	}
	public void setPerformerId(int[] performerId) {
		this.performerId = performerId;
	}
	public String[] getPerformerTask() {
		return performerTask;
	}
	public void setPerformerTask(String[] performerTask) {
		this.performerTask = performerTask;
	}
	public String getStepContent() {
		return stepContent;
	}
	public void setStepContent(String stepContent) {
		this.stepContent = stepContent;
	}
	public String getStepEndtime() {
		return stepEndtime;
	}
	public void setStepEndtime(String stepEndtime) {
		this.stepEndtime = stepEndtime;
	}
	public boolean isCompleted() {
		return isCompleted;
	}
	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}
	public String getTimeKey() {
		return timeKey;
	}
	public void setTimeKey(String timeKey) {
		this.timeKey = timeKey;
	}
	public int getWhichStep() {
		return whichStep;
	}
	public void setWhichStep(int whichStep) {
		this.whichStep = whichStep;
	}
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
}
