package com.thars.question;

public class Question {
	public int quid;
	public String question_show;
	public String a_ans;
	public String b_ans;
	public String c_ans;
	public String d_ans;
	public String right_ans;

	public Question() {

	}

	public Question(int quid, String question_show, String a_ans, String b_ans, String c_ans, String d_ans, String right_ans) {
		this.quid = quid;
		this.question_show = question_show;
		this.a_ans = a_ans;
		this.b_ans = b_ans;
		this.c_ans = c_ans;
		this.d_ans = d_ans;
		this.right_ans = right_ans;
	}

	public int getQuid() {
		return quid;
	}

	public void setUid(int quid) {
		this.quid = quid;
	}

	public String getQuestion_show() {
		return question_show;
	}

	public void setQuestion_show(String question_show) {
		this.question_show = question_show;
	}
//
	public String getA_ans() {
		return a_ans;
	}
	public void setA_ans(String a_ans) {
		this.a_ans = a_ans;
	}
	//
	public String getB_ans() {
		return b_ans;
	}
	public void setB_ans(String b_ans) {
		this.b_ans = b_ans;
	}
	public String getC_ans() {
		return c_ans;
	}

	public void setC_ans(String c_ans) {
		this.c_ans = c_ans;
	}
	public String getD_ans() {
		return d_ans;
	}

	public void setD_ans(String d_ans) {
		this.d_ans = d_ans;
	}
	public String getRight_ans() {
		return right_ans;
	}

	public void setRight_ans(String right_ans) {
		this.right_ans = right_ans;
	}
  public String toString (){
	return "quid"+quid+"question_show"+question_show+"a_ans"+a_ans+"b_ans"+b_ans+"c_ans"+c_ans+"d_ans"+d_ans+"right_ans"+right_ans;
	  
  }
}
