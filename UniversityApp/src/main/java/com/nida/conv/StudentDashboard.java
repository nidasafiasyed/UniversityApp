package com.nida.conv;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReturnDocument;
import com.nida.intf.StudentOperations;
import com.nida.model.Students;
import com.nida.model.Courses;

public class StudentDashboard implements StudentOperations{
	
	private MongoCollection<Students> students;
	static Scanner sc;

	static {
		
		displaymessage();
	}
	
	public StudentDashboard(MongoCollection<Students> studentCollection) {
		this.students = studentCollection;
		loginStudent();
	}
	
	private static void displaymessage() {
		System.out.println("Welcome to the Student dashboard");
	}
	
	private void loginStudent() {

		sc = new Scanner(System.in);
		System.out.println("Enter your Student ID: ");
		int stId = sc.nextInt();
		Students s = students.find(eq("sid", stId)).first();
		
		if (s == null) {
			System.out.println("Student(s) not found");
		}

		else {
			String choice;

		while (true) {
			System.out.println("Which operation would you like to perform?");
			System.out.println("1. d for displaying your record.");
			System.out.println("2. e for enrolling in courses.");
			System.out.println("3. p for paying your balance.");
			System.out.println("4. q to quit");
			choice = sc.next().toLowerCase();

			switch (choice) {
			case "d":
				displayPortal(stId);
				break;

			case "p":
				payTuition(stId);
				break;

			case "e":
				enrollCourse(stId);
				break;

			case "q":
				System.out.println("Exiting...");
				System.exit(0);
				break;

			default:
				System.out.println("Invalid option");
				break;
			}

		}
	
		}	}

	@Override
	public void displayPortal(int stId) {
		// TODO Auto-generated method stub
		System.out.println("\nYour Record ");
		System.out.println("----------------------");
		Students s = students.find(eq("sid", stId)).first(); 
		System.out.println(s);
		
	}

	@Override
	public void enrollCourse(int stId) {
		// TODO Auto-generated method stub
		
		Students s = students.find(eq("sid", stId)).first(); 
		System.out.println(s);
		
		List<Courses> newCourses = new ArrayList<Courses>(s.getCourses());
		
		System.out.println("\nHow many courses do you want to enroll in?");
		sc = new Scanner(System.in);
		int n = sc.nextInt();
		
		boolean enrollFlag = false;
		
		double e = 0;

		if (n == 0) {
			System.out.println("No courses registered.");
		}

		else {
			for (int i = 1; i <= n; i++) {
				
				System.out.println("Enter CourseId #" + i);
				String c = sc.next();

				
				for(Courses cs : newCourses)
				{ 
					if(cs.getCourseId().contains(c)) {
						enrollFlag = true; 
						} 
					}
				 
				
				if(enrollFlag) {
				 System.out.println("You have already enrolled for this course"); 
				 } 
				
				else {	
					newCourses.add(new Courses().setCourseId(c));
					e = e + 600;
				}

			}
			s.setCourses(newCourses);
		 
		Document filterByStudentId = new Document("sid", s.getSid());
		 
		FindOneAndReplaceOptions returnDocAfterReplace = new
		FindOneAndReplaceOptions() .returnDocument(ReturnDocument.AFTER);
		 
		Students enrolled = students.findOneAndReplace(filterByStudentId, s, returnDocAfterReplace);
		
		students.updateOne(eq("sid", enrolled.getSid()), set("balance", enrolled.getBalance() - e));
		
		System.out.println("\nEnrollment finished");
		System.out.println("\n Your record");
		System.out.println("----------------------");
		Students enStudent = students.find(eq("sid", s.getSid())).first();
		System.out.println(enStudent);
		
	}}

	@Override
	public void payTuition(int stId) {
		// TODO Auto-generated method stub
		
		System.out.println("\nEnter the amount you want to pay: ");
		double amt = sc.nextInt();
		
		Students s = students.find(eq("sid", stId)).first(); 
		System.out.println(s);
		
		students.updateOne(eq("sid", s.getSid()), set("balance", s.getBalance() + amt));
		System.out.println("Amount paid.");
		System.out.println("\nYour Record: ");
		System.out.println("----------------------");
		Students balStudent = students.find(eq("sid", s.getSid())).first(); 
		System.out.println(balStudent);
		
	}

}
