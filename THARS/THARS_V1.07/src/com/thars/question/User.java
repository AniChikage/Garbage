package com.thars.question;

public class User {
	public String uname;
	public String upassword;

	public User() {

	}

	public User(String uname, String upassword) {
		this.uname = uname;
		this.upassword = upassword;

	}


	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getUpassword() {
		return upassword;
	}
	public void setUpassword(String upassword) {
		this.upassword = upassword;
	}
	

  public String toString (){
	return "uname"+uname+"upassword"+upassword;
	  
  }
}
