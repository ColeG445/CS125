/* CS 125 - Intro to Computer Science II
 * File Name: CS125_Project3_Client.java
 * Project 3 - Due X/XX/XXXX
 * Instructor: Dr. Dan Grissom
 * 
 * Name: FirstName LastName
 * Description: Insert your meaningful description for Project 3.
 */

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class CS125_Project3_Client
{
	public static void main(String[] args)
	{
		// Your program should always output your name and the lab/problem
		// number. DO NOT DELETE OR COMMENT OUT. Replace with relevant info.
		// TODO 15: Update your name...
		System.out.println("Cole Gunter");
		System.out.println("Project 3");
		System.out.println("");

		// Create scanner
		Scanner scan = new Scanner(System.in);

		// Perform initial reading of data
		ArrayList<Hotel> hotels = getHotelsFromFile("Hotels.txt");
		ArrayList<Reservation> reservations = readReservationsFromFile("Reservations.txt");

		// Associate each reservation with the appropriate hotel
		assignReservationsToCorrectHotel(reservations, hotels);

		// Print out welcome 
		System.out.println("Welcome to the Parental Paradise Hotel Chain.");
		Hotel selectedHotel = null;

		// Prompt with hotel list and repeat until get valid hotel selection
		do
		{		
			System.out.println("Please select from one of the following hotels (select the number and press enter) to make a reservation: ");
			for (Hotel h : hotels)
				System.out.println("\t" + h.toDisplayString());
			System.out.print("\nYour selection: ");

			// Read user input & get hotel
			int choice = scan.nextInt();

			for (Hotel h : hotels)
				if (h.getUniqueId() == choice)
					selectedHotel = h;

			if (selectedHotel == null)
				System.out.println("Invalid hotel choice. Please try again...");

		} while (selectedHotel == null);

		// Create variables for reservation
		int month;
		int checkinDay;
		int checkoutDay;
		boolean invalidDateRange = true;

		// Prompt for check-in month and check-in/check-out day until get valid selection
		do
		{
			System.out.println("Please enter details about your reservation request: ");
			System.out.print("\tMonth (1-12): ");
			month = scan.nextInt();
			System.out.print("\tCheck-in day: ");
			checkinDay = scan.nextInt();
			System.out.print("\tCheck-out day: ");
			checkoutDay = scan.nextInt();

			if (month <= 0 || month > 12)
				System.out.println("Invalid month choice. Please try again...");
			else if (checkinDay <= 0 || checkoutDay <= 0)
				System.out.println("Invalid check-in or check-out choice; must be greater than 0. Please try again...");
			else if (checkinDay > 31 || checkoutDay > 31)
				System.out.println("Invalid check-in or check-out choice; must be greater than 0. Please try again...");
			else if (checkoutDay == checkinDay)
				System.out.println("Invalid check-in or check-out range; must stay at least one night. Please try again...");
			else if (checkoutDay <= checkinDay)
				System.out.println("Invalid check-in or check-out range; cannot checkout before checking in. Please try again...");
			else
			{
				invalidDateRange = false;
			}

		} while (invalidDateRange);

		// Create reservation from user input
		Reservation newRes = new Reservation(selectedHotel.getUniqueId(), month, month, checkinDay, checkoutDay);

		// Try to add reservation
		if (selectedHotel.addResIfCanBook(newRes))
		{
			// Then add new reservation to global reservations list and write out to file
			System.out.println("Reservation successfully added: " + selectedHotel.getHotelName() + ": " + newRes.getFormattedDisplayString(selectedHotel.getPricePerNight()));
			reservations.add(newRes);
			writeReservationFile("Reservations.txt", reservations);
		}
		else
		{
			System.out.println("Could not add the reservation (" + newRes.getFormattedDisplayString() + ") to " + selectedHotel.getHotelName() + " b/c of a conflict.");
			System.out.println("Please re-run the program to try a new date.");
		}

		// Exit program
		System.out.println("\nThank you for using the Parental Paradise Hotel Manager.");

	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////
	// This method takes in a list of hotels and reservations. At this point, the hotel
	// objects should have an empty ArrayList of Reservations (as an instance variable).
	// This method cycles through the reservations and assigns them to the hotel with
	// matching uniqueId as the resrevation's hotelId.
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	private static void assignReservationsToCorrectHotel(ArrayList<Reservation> reservations, ArrayList<Hotel> hotels)
	{
		// TODO 16: Add code to complete method...

		//cycles through arraylists to see when the IDs match and adds the reservation.
		for (int i = 0; i < reservations.size(); i++) {
			for (int j = 0; j < hotels.size(); j++) {
				if (reservations.get(i).getHotelID() == hotels.get(j).getUniqueId()) {
					hotels.get(j).addReservation(reservations.get(i));
				}
			}
		}
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////
	// Reads Hotels from fileName and returns as a new ArrayList of hotels.
	//
	// Uses plain-text input.
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	private static ArrayList<Hotel> getHotelsFromFile(String fileName)
	{
		// TODO 17: Add code to complete method...



		//Initialize input streams
		//Arraylist Hotel that will be returned

		FileInputStream fis = null;
		Scanner fscan = null;
		ArrayList<Hotel> hotels = new ArrayList<Hotel>();
		Hotel hotel = new Hotel();
		try {
			fis = new FileInputStream(fileName);
			fscan = new Scanner(fis);

			//keeps reading words from file until the requirements are not met
			while (fscan.hasNextLine()) {
				String line = fscan.nextLine();
				Scanner lineScan = new Scanner(line);
				lineScan.useDelimiter(",");
				Hotel h = new Hotel();
				String start = lineScan.next().trim();
				long hotelID = Long.parseLong(start.substring(6, 7));
				h.setUniqueID(hotelID);
				h.setHotelName(lineScan.next().trim());
				h.setStreetAddress(lineScan.next().trim());
				h.setCity(lineScan.next().trim());
				h.setStateAbbreviation(lineScan.next().trim());
				String finish = lineScan.next().trim();
				double ppn = Double.parseDouble(finish.substring(0, finish.length()-1));
				h.setPricePerNight(ppn);
				hotels.add(h);
			}
		}
		catch(FileNotFoundException e)
		{
			System.out.println("ERROR (File not found): " + e.getMessage());
		}
		catch(IOException e)
		{
			System.out.println("ERROR (File not found): " + e.getMessage());
		}
		finally{
			try
			{
				fis.close();
				fscan.close();
			}
			catch(Exception e) { System.out.println("ERROR: " + e.getMessage());
			}
		}
		return hotels;
	}



	//////////////////////////////////////////////////////////////////////////////////////////////////////
	// Reads Reservations from a given file and stores them into the reservations
	// list. Returns a new ArrayList of reservations read in from file. If no reservations
	// in file found at fileName, should return an empty ArrayList. 
	//
	// Uses serialize for input.
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	private static ArrayList<Reservation> readReservationsFromFile(String fileName)
	{
		// TODO 18: Add code to complete method...

		FileInputStream fis = null;
		ObjectInputStream ois = null;
		ArrayList<Reservation> reservations = new ArrayList<Reservation>();
		//reads objects from file to arraylist
		try {
			fis = new FileInputStream(fileName);
			ois = new ObjectInputStream(fis);
			while (true) {
				Reservation r = (Reservation)ois.readObject();
				reservations.add(r);
			}
		}
		catch (EOFException e) {
			System.out.println(fileName + " file successfully read.");
		}
		catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
		catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}catch(Exception e)
		{
			System.out.println("ERROR: " + e.getMessage());
		}
		finally
		{
			try
			{
				if(ois != null)
					ois.close();
				if(fis != null)
					fis.close();
			}
			catch (IOException e)
			{
				System.out.println("ERROR: " + e.getMessage());
			}
		}

		return reservations;
	}




	//////////////////////////////////////////////////////////////////////////////////////////////////////
	// Overwrites the file at fileName with the reservations found in globalReservations. 
	// globalReservations should contain the reservations found in the file when the program
	// begin, as well as any new reservation the user made. Returns true upon success; false upon failure
	//
	// Uses serialize for input.
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	private static boolean writeReservationFile(String fileName, ArrayList<Reservation> globalReservations)
	{
		// TODO 19: Add code to complete method...
		readReservationsFromFile(fileName);
		//Initialize Output streams
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		boolean readFail = false;

		//writes objects to file
		try {
			fos = new FileOutputStream(fileName);
			oos = new ObjectOutputStream(fos);
			//writes objects to reservation file
			for (Reservation r : globalReservations) {
				oos.writeObject(r);
			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			readFail = false;
		}
		catch (IOException e) {
			e.printStackTrace();
			readFail = false;
		}finally
		{
			try
			{
				// Close files
				oos.close();
				fos.close();
			}
			catch (IOException e)
			{
				System.out.println("ERROR: " + e.getMessage());
			}
		}
		//return if the whether the reading failed or not
		if (readFail)
			return false;
		else
			return true; //means the file was read successfully
	}

}


/******************************************************************************
Insert 2 test cases, which represent program input/output for two different
combinations of inputs. You may literally copy and paste your console contents,
but your two test cases should be DIFFERENT from any test cases given in the
Project description itself.

------------
Test Case 1:
------------
(Your test case I/O here.)

------------
Test Case 2:
------------
(Your test case I/O here.)
 ******************************************************************************/








