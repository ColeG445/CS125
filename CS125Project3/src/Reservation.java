import java.io.Serializable;
import java.text.DecimalFormat;

public class Reservation implements Serializable
{
	/////////////////////////////////////////////////////////////////////////////////////////
	// Instance Variables
	/////////////////////////////////////////////////////////////////////////////////////////
	// TODO 1: Add code to create instance variables encapsulating the reservation described
	// in the project prompt...
	int CinMonth;
	int CoutMonth;
	int CinDay;
	int CoutDay;
	int CMonth;
	long HotelID;
	
	
	/////////////////////////////////////////////////////////////////////////////////////////
	// Overloaded Constructor
	/////////////////////////////////////////////////////////////////////////////////////////
	public Reservation(long hotId, int inMonth, int outMonth, int inDay, int outDay)
	{
		// TODO 2: Add code to finish overloaded constructor (works as expected)...
		HotelID = hotId;
		CinMonth = inMonth;
		CoutMonth = outMonth;
		CinDay = inDay;
		CoutDay = outDay;
	}
	/////////////////////////////////////////////////////////////////////////////////////////
	// Overloaded constructor #2
	/////////////////////////////////////////////////////////////////////////////////////////
	public Reservation(long hotId, int month, int inDay, int outDay)
	{
		// TODO 3: Add code to finish default constructor (you are free to pick default values)...
		HotelID=hotId;
		CMonth=month;
		CinDay=inDay;
		CoutDay=outDay;
	}
	

	/////////////////////////////////////////////////////////////////////////////////////////
	// Class Methods
	/////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////
	// Generating display String for printout (kind of like the toString() method). Should be
	// in the following form:
	// 		1/1 - 1/2
	/////////////////////////////////////////////////////////////////////////////////////////
	public String getFormattedDisplayString()
	{
		// TODO 4: Add code to complete method...
		String str = "";
		str += CinMonth + "/" + CinDay + " - " + CoutMonth + "/" + CoutDay;
		return str;
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////
	// Generating display String for printout (kind of like the toString() method). Should be
	// in the following form:
	// 		1/5 - 1/6 @ $179.00 per night
	/////////////////////////////////////////////////////////////////////////////////////////
	public String getFormattedDisplayString(double currentPrice)
	{
		// TODO 5: Add code to complete method...
		DecimalFormat mf=new DecimalFormat("$0.00 per night");
		String str = "";
		str+=CinMonth +"/"+CinDay + " - " +CoutMonth +"/"+CoutDay + " @ "+ mf.format(currentPrice);
		return str;
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////
	// Getters/Setters
	/////////////////////////////////////////////////////////////////////////////////////////
	// TODO 6: Add code to create ALL getters/setters (REMINDER: Eclipse can do this for you
	// if you've already created the instance variables)...
	public long getHotelID() {
		return HotelID;
	}
	public void setHotelID(long hotelID) {
		HotelID = hotelID;
	}
	public int getCinMonth() {
		return CinMonth;
	}
	public void setCinMonth(int cinMonth) {
		CinMonth = cinMonth;
	}
	public int getCoutMonth() {
		return CoutMonth;
	}
	public void setCoutMonth(int coutMonth) {
		CoutMonth = coutMonth;
	}
	public int getCinDay() {
		return CinDay;
	}
	public void setCinDay(int cinDay) {
		CinDay = cinDay;
	}
	public int getCoutDay() {
		return CoutDay;
	}
	public void setCoutDay(int coutDay) {
		CoutDay = coutDay;
	}
	public int getCMonth() {
		return CMonth;
	}
	public void setCMonth(int cMonth) {
		CMonth = cMonth;
	}

}








