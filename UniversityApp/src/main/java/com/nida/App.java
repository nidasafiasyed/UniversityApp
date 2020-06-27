package com.nida;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.io.Console;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.nida.conv.AdminDashboard;
import com.nida.conv.StudentDashboard;
import com.nida.model.Students;

/**
 * Hello world!
 *
 */
public class App {

	public static void main(String[] args) 
{
		Logger.getLogger("org.mongodb.driver").setLevel(Level.SEVERE);

		CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
				fromProviders(PojoCodecProvider.builder().automatic(true).build()));

		try (MongoClient mongoClient = MongoClients.create()) {

			MongoDatabase database = mongoClient.getDatabase("University").withCodecRegistry(pojoCodecRegistry);

			MongoCollection<Students> studentCollection = database.getCollection("Students", Students.class);

			Scanner sc = new Scanner(System.in);
			
			System.out.println("Please enter 1 for admin and 2 for student: ");

			int input = sc.nextInt();
			if(input == 1) {
				System.out.println("Please enter your password: (admin)");
				
				if (sc.next().equals("admin")) {
					System.out.println("Login success!");
					new AdminDashboard(studentCollection);
				}
				else {
					System.out.println("Invalid credentials");
				}}
			else if (input == 2) {
				System.out.println("You are a student");
				new StudentDashboard(studentCollection);
			}
				else {
					System.out.println("Invalid option, Goodbye.");
					System.exit(0);
				}
			sc.close();
		
		}

	}

}
