package com.ex.task;


public class Task {
	private int taskId;                      //任务ID号
	private int taskStatus;                  //任务状态，1表示完成，0表示已经启动，-1表示未开始
	private String taskName;                 //任务名
	private String beginTime,endTime;        //任务完成开始时间,结束时间
	private TaskStep[] steps;                //任务由若干步骤组成
	private String taskGoal;                 //任务目标
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
		SomeTask.setBeginTime("");  //默认情况下任务执行开始时间为当前时间
		SomeTask.setEndTime("");
		SomeTask.setSteps(null);
		SomeTask.setTaskGoal("");
		SomeTask.setTaskId(-1);
		SomeTask.setTaskName("");
		return SomeTask;
	}

}

