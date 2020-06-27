package com.nida.model;

import java.util.List;

public class Students {
	
	public int sid;
	public String name;
	public int year;
	private List<Courses> courses;
	public double balance;


	public double getBalance() {
		return balance;
	}


	public Students setBalance(double balance) {
		this.balance = balance;
		return this;
	}


	public String getName() {
		return name;
	}

	
	public int getSid() {
		return sid;
	}


	public Students setName(String name) {
		this.name = name;
		return this;
	}

	public int getYear() {
		return year;
	}

	public Students setYear(int year) {	
		this.year = year;
		return this;
	}


	public Students setSid(int sid) {
		this.sid = sid;
		return this;
	}


	public List<Courses> getCourses() {
		return courses;
	}


	public Students setCourses(List<Courses> courses) {
		this.courses = courses;
		return this;
	}

	
	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("");
			sb.append("Student_ID: ").append(sid);
			sb.append("\nStudent_Name: ").append(name);
			sb.append("\nYear:").append(year);
			sb.append("\nCourse List: ").append(courses);
			sb.append("\nBalance: ").append(balance);
			return sb.toString();
		}	

}
