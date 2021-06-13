import java.text.DecimalFormat;
import java.util.ArrayList;

public class Hotel
{
	/////////////////////////////////////////////////////////////////////////////////////////
	// Instance Variables
	/////////////////////////////////////////////////////////////////////////////////////////
	// TODO 7: Add code to create instance variables encapsulating info found in Hotels.txt...
	long uniqueId;
	String hotelName;
	String streetAddress;
	String city;
	String stateAbbreviation;
	double pricePerNight;
	static ArrayList<Reservation> reservations = new ArrayList<Reservation>();
	
	/////////////////////////////////////////////////////////////////////////////////////////
	// Overloaded Constructor
	/////////////////////////////////////////////////////////////////////////////////////////
	public Hotel(long id, String name, String addr, String cityName, String stAbbrev, double price)
	{
		// TODO 8: Add code to finish overloaded constructor (works as expected)...
		uniqueId = id;
		hotelName = name;
		streetAddress = addr;
		city = cityName;
		stateAbbreviation = stAbbrev;
		pricePerNight = price;
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////
	// Default constructor
	/////////////////////////////////////////////////////////////////////////////////////////
	public Hotel()
	{
		// TODO 9: Add code to finish default constructor (you are free to pick default values)...
		uniqueId = 0;
		hotelName = "";
		streetAddress = "";
		city = "";
		stateAbbreviation = "";
		pricePerNight = 0;
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////
	// Class Methods
	/////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////
	// This method takes in a new reservation and compares it against 
	// all other reservations in this hotels reservations ArrayList.
	// Returns true if the new reservation can be made; returns false
	// if the new reserveration (newRes) will conflict with an old
	// reservation.
	/////////////////////////////////////////////////////////////////////////////////////////
	public boolean canBook(Reservation newRes)
	{
		// TODO 10: Add code to complete method...
		int check = 0;
		for(int i = 0; i < reservations.size(); i++) {
			if(reservations.get(i).getHotelID() == newRes.getHotelID() &&  reservations.get(i).getCinMonth() == newRes.getCinMonth() && ((newRes.getCinDay() >= reservations.get(i).getCinDay() && newRes.getCinDay() <= reservations.get(i).getCoutDay()) || newRes.getCoutDay() >= reservations.get(i).getCinDay() && newRes.getCoutDay() <= reservations.get(i).getCoutDay())) {
				check = 1;
			}
		}
		if (check == 1) {
			return false;
		}
		else {
			return true;
		}
		
		
	
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////
	// Adds the new reservation (newRes) to the ArrayList of reservations
	// (instance variables)
	/////////////////////////////////////////////////////////////////////////////////////////
	public void addReservation(Reservation newRes)
	{
		// TODO 11: Add code to complete method...
		reservations.add(newRes);
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////
	// SIMPLE method that uses the previous two methods (canBook() and addReservation()). If
	// canBook() returns true, calls addReservation() to add newRes and returns true;
	// otherwise, returns false.
	/////////////////////////////////////////////////////////////////////////////////////////
	public boolean addResIfCanBook(Reservation newRes)
	{
		// TODO 12: Add code to complete method...
		if (canBook(newRes) == true) {
			addReservation(newRes);
			return true;
		}
		else {
			return false;
		}
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////////////
	// Generating display String for printout (kind of like the toString() method). Should be
	// in the following form:
	// 		1) Azusa Inn (23 Main St., Azusa, CA) @ $159.00/night
	//
	// NOTE: In this example, the "1)" is the uniqueId of this hotel, "Azusa Inn", is the 
	// name of the hotel, etc., etc...
	/////////////////////////////////////////////////////////////////////////////////////////
	public String toDisplayString()
	{
		// TODO 13: Add code to complete method...
		DecimalFormat mf = new DecimalFormat("$0.00/night");
		String str = "";
		str += uniqueId + ") " + hotelName + " (" + streetAddress + ", " + city + ", " + stateAbbreviation + ")";
		str += " @ " + mf.format(pricePerNight);
		return str;
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////
	// Getters/Setters
	/////////////////////////////////////////////////////////////////////////////////////////
	// TODO 14: Add code to create ALL getters/setters (REMINDER: Eclipse can do this for you
	// if you've already created the instance variables)...
	public long getUniqueId() {
		return uniqueId;
	}

	public void setUniqueID(long UniqueID) {
		this.uniqueId = UniqueID;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String HotelName) {
		this.hotelName = HotelName;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String StreetAddress) {
		this.streetAddress = StreetAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String City) {
		this.city = City;
	}

	public String getStateAbbreviation() {
		return stateAbbreviation;
	}

	public void setStateAbbreviation(String StateAbbreviation) {
		this.stateAbbreviation = StateAbbreviation;
	}

	public double getPricePerNight() {
		return pricePerNight;
	}

	public void setPricePerNight(double PricePerNight) {
		this.pricePerNight = PricePerNight;
	}

}








