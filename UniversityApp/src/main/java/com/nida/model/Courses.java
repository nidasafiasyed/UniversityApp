package com.nida.model;

public class Courses{
	
	private String courseId;
	private String courseName;
	
	
	public String getCourseId() {
		return courseId;
	}

	public Courses setCourseId(String courseId) {
		
		switch(courseId) {
		case "111":
			this.courseName = "Orientation";
			break;
		
		case "H101":
			this.courseName = "History 101";
			break;
			
		case "M101":
			this.courseName = "Mathematics 101";
			break;
			
		case "E101":
			this.courseName = "English 101";
			break;
		
		case "C101":
			this.courseName = "Chemistry 101";
			break;
			
		case "CS101":
			this.courseName = "Computer Science 101";
			break;
			
			default:
				this.courseName = "Enrollment Pending";
				break;
		}
		
		
			this.courseId = courseId;	
		
		
		return this;
	}
	
	public String getCourseName() {
		return courseName;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("|");
		sb.append("Course_ID: ").append(courseId);
		sb.append("\tCourse_Name: ").append(courseName);
		sb.append("|");
		return sb.toString();

	}
	
}

