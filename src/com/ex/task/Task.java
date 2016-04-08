package com.ex.task;


public class Task {
	private int taskId;                      //����ID��
	private int taskStatus;                  //����״̬��1��ʾ��ɣ�0��ʾ�Ѿ�������-1��ʾδ��ʼ
	private String taskName;                 //������
	private String beginTime,endTime;        //������ɿ�ʼʱ��,����ʱ��
	private TaskStep[] steps;                //���������ɲ������
	private String taskGoal;                 //����Ŀ��
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public int getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(int taskStatus) {
		this.taskStatus = taskStatus;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getTaskGoal() {
		return taskGoal;
	}
	public void setTaskGoal(String taskGoal) {
		this.taskGoal = taskGoal;
	}
	public TaskStep[] getSteps() {
		return steps;
	}
	public void setSteps(TaskStep[] steps) {
		this.steps = steps;
	}
	public static Task getNew(){
		Task SomeTask=new Task();
		SomeTask.setBeginTime("");  //Ĭ�����������ִ�п�ʼʱ��Ϊ��ǰʱ��
		SomeTask.setEndTime("");
		SomeTask.setSteps(null);
		SomeTask.setTaskGoal("");
		SomeTask.setTaskId(-1);
		SomeTask.setTaskName("");
		return SomeTask;
	}

}

