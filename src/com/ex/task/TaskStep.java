package com.ex.task;


public class TaskStep {
	

	private int stepId;                  //����Id��
	private int[] performerId;           //ÿһ����������һ�������ߣ����Ը���id����somebody������Ϣ
	private String[] performerTask;         //��������ɵĽ�ɫ
	private String stepContent,stepEndtime;  //��������,���ʱ��
	private boolean isCompleted;             //�����Ƿ����
	private String timeKey;                  //��ʱ������Ĳ���Ψһ��־��
	private int whichStep;                   //�������к�
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
