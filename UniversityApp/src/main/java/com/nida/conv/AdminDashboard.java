package com.nida.conv;

import static com.mongodb.client.model.Filters.eq;
import static java.util.Collections.singletonList;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


import static com.mongodb.client.model.Updates.*;

import org.bson.Document;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.nida.intf.AdminOperations;
import com.nida.model.Courses;
import com.nida.model.Students;

public class AdminDashboard implements AdminOperations {

	private MongoCollection<Students> students;
	static Scanner sc;

	static {
		displayMessage();
	}

	public AdminDashboard(MongoCollection<Students> studentCollection) {
		this.students = studentCollection;
		performOperation();
	}

	private static void displayMessage() {
		System.out.println("Welcome to your dashboard");
	}

	private void performOperation() {

		sc = new Scanner(System.in);
		String choice;

		while (true) {
			System.out.println("Which operation would you like to perform?");
			System.out.println("1. r for registering new students.");
			System.out.println("2. d for displaying a student record.");
			System.out.println("3. a for displaying all student records.");
			System.out.println("4. u for updating a student record.");
			System.out.println("5. e for erasing a student record");
			System.out.println("6. q to quit");
			choice = sc.next().toLowerCase();

			switch (choice) {
			case "r":
				registerStudents();
				break;

			case "d":
				displayRecord();
				break;

			case "a":
				displayAll();
				break;

			case "u":
				updateRecord();
				break;

			case "e":
				deleteRecords();
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
	}

	@Override
	public void registerStudents() {
		// TODO Auto-generated method stub
		System.out.println("\nRegistration Portal");
		System.out.println("----------------------");
		Random r = new Random();
		int n;
		System.out.println("How many students do you want to register?");
		sc = new Scanner(System.in);
		n = sc.nextInt();

		if (n == 0) {
			System.out.println("No students registered.");
		}

		else {
			for (int i = 1; i <= n; i++) {
				System.out.println("#" + i);

				System.out.println("Enter the name: ");
				String stName = sc.next();

				System.out.println("Enter the year: ");

				int stYear = sc.nextInt();
				if (stYear > 4 && stYear < 1) {
					System.out.println("Incorrect year. Enter years between 1 & 4.");
					stYear = sc.nextInt();
				}

				Students s = new Students()
						.setSid(Integer.parseInt(
								(Integer.toString(stYear) + Integer.toString(r.nextInt((9999 - 1000) + 1) + 1000))))
						.setName(stName).setYear(stYear).setCourses(singletonList(new Courses().setCourseId("111")))
						.setBalance(0);

				students.insertOne(s);
				System.out.println("Student " + s.getName() + " registered");
			}

			System.out.println("Registration completed.");
		}
	}

	@Override
	public void displayRecord() {
		// TODO Auto-generated method stub
		FindIterable<Students> s = null;
		
		System.out.println("\nEnter i to lookup by ID or n to lookup by name: ");
		String choice = sc.next().toLowerCase();

		if (choice.equals("i")) {
			System.out.println("Enter the ID: ");
			int stId = sc.nextInt();
			s = students.find(eq("sid", stId));
		}

		else if (choice.equals("n")) {
			System.out.println("Enter the name: ");
			String stName = sc.next();
			s = students.find(eq("name", stName));
		}

		else {
			System.out.println("Invalid option");
		}

		List<Students> target = new ArrayList<Students>();
		for (Students n : s) {
			target.add(n);
		}

		if (target.size() == 0) {
			System.out.println("Student(s) not found");
		}

		else {
			int i = 0;
			for (Students n : s) {
				i++;
				System.out.println("\nStudent " + i);
				System.out.println("----------------------");
				System.out.println(n);
			}

		}

	}

	@Override
	public void displayAll() {
		// TODO Auto-generated method stub
		System.out.println("\nStudent Records");
		System.out.println("----------------------");
		FindIterable<Students> st = students.find();

		int i = 0;
		for (Students s : st) {
			i++;
			System.out.println("\nStudent " + i);
			System.out.println("----------------------");
			System.out.println(s);
		}

	}

	@Override
	public void updateRecord() {
		// TODO Auto-generated method stub
		System.out.println("\nEnter the ID: ");
		int stId = sc.nextInt();
		Students s = students.find(eq("sid", stId)).first();

		if (s == null) {
			System.out.println("Student(s) not found");
		}

		else {
			System.out.println("This is the record you are updating: ");
			System.out.println(s);
		
		System.out.println("Enter y to update year or n to update name: ");
		String choice = sc.next().toLowerCase();

		if (choice.equals("y")) {
			System.out.println("Enter the ID: ");
			int stYear = sc.nextInt();
			students.updateOne(eq("sid", stId), set("year", stYear));
			System.out.println(s);
		}

		else if (choice.equals("n")) {
			System.out.println("Enter the name: ");
			String stName = sc.next();
			students.updateOne(eq("sid", stId), set("name", stName));
		}

		else {
			System.out.println("Invalid option");
		}
		
		System.out.println("Updated record: ");
		System.out.println("----------------------");
		System.out.println(students.find(eq("sid", stId)).first());
	}


	}

	@Override
	public void deleteRecords() {
		// TODO Auto-generated method stub

		System.out.println("\nEnter the ID: ");
		int stId = sc.nextInt();
		Students s = students.find(eq("sid", stId)).first();

		if (s == null) {
			System.out.println("Student(s) not found");
		}

		else {
			System.out.println("This is the record you are deleting: ");
			System.out.println("----------------------");
			System.out.println(s);
			students.deleteOne(new Document("sid", s.getSid()));
			System.out.println("Student record deleted");

		}

	}

}
