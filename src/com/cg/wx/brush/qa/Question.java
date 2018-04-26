package com.cg.wx.brush.qa;

public class Question {
	private String question;
	private String time;
	private String answer;
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	@Override
	public String toString() {
		return "Question [question=" + question + ", time=" + time
				+ ", answer=" + answer + ", getQuestion()=" + getQuestion()
				+ ", getTime()=" + getTime() + ", getAnswer()=" + getAnswer()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
}
