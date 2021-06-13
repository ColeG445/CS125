/* CS 125 - Intro to Computer Science II
 * File Name: CS125_Project4_Client.java
 * Project 6 - Due X/XX/XXXX
 * Instructor: Dr. Dan Grissom
 * 
 * Name: FirstName LastName
 * Description: Insert your meaningful description for Project 4.
 */

/*
 * Uses JSON external library to parse JSON objects/arrays/values. 
 * Specifically parses out the main/secondary text/description of
 * each establishment and prints.
 *
 * In order to use this library, you will need to:
 *	1) Download the newest JSON library/JAR (org.json) from: http://mvnrepository.com/artifact/org.json/json
 *	2) Create a new folder called "libs" right by the "src" folder in your project
 *	3) Copy the JSON JAR file to the "libs" folder you just created
 *	4) Add the JAR file to the project; in Eclipse:
 *		a) Click "Project->Properties" from the menu at the top of Eclipse
 *		b) Click "Java Build Path" from the list on the right
 *		c) Click the "Libraries" tab and then click the "Add External JARs..." button
 *		d) Find/select your JAR file on the file system
 */

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class CS125_Project4_Client extends JFrame {

	///////////////////////////////////////////////////////////////////
	// Instance variables/components


	///////////////////////////////////////////////////////////////////
	// DefaultListModel is bound to lstResults
	private DefaultListModel<Place> dlmResult = new DefaultListModel<Place>();

	///////////////////////////////////////////////////////////////////
	// APIs SECTION
	///////////////////////////////////////////////////////////////////
	private final static String mapsGeocodeBaseUrl = "https://maps.googleapis.com/maps/api/geocode/json?";
	private final static String placesNearbySearchBaseUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
	private final static String placesDetailBaseUrl = "https://maps.googleapis.com/maps/api/place/details/json?";
	private final static String mapsDirectionBaseUrl = "https://maps.googleapis.com/maps/api/directions/json?"; 

	// Once you obtain a key, include it here instead of "YOUR_KEY_HERE":
	private final static String mapsGeocodeApiKey = "AIzaSyDMU-qzL7ill5vK7S0j6DQBEQKnCEjaUnE"; // Get your own key by clicking "Get A Key" on the following website: https://developers.google.com/maps/documentation/geocoding/start
	private final static String mapsDirectionsApiKey = "AIzaSyCSGjvsiHpv-PuzLRTRk5FX1XCL-qId2ac"; // Get your own key by clicking "Get A Key" on the following website: https://developers.google.com/maps/documentation/directions/
	private final static String placesApiKey = "AIzaSyAmqTeIrrZNu133ZcmWkGFmGDOH4M-rnkg"; // Get your own key by clicking "Get A Key" on the following website: https://developers.google.com/places/web-service/

	///////////////////////////////////////////////////////////////////
	// MAIN
	///////////////////////////////////////////////////////////////////
	public static void main(String[] args) {
		// Your program should always output your name and the project number.
		// DO NOT DELETE OR COMMENT OUT. Replace with relevant info.
		System.out.println("Cole Gunter");
		System.out.println("Project 4");
		System.out.println("");

		// Launch GUI
		CS125_Project4_Client frame = new CS125_Project4_Client();
		frame.setVisible(true);
	}

	///////////////////////////////////////////////////////////////////
	// Constructor - calls initComponents and createEvents
	///////////////////////////////////////////////////////////////////
	public CS125_Project4_Client() {
		initComponents();
		createEvents();
	}

	///////////////////////////////////////////////////////////////////
	// All component initializations done here in this method
	///////////////////////////////////////////////////////////////////
	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 817, 357);
		
		// TODO 1: Use WindowBuilder to layout controls (most
		// auto-generated WindowBuilder code will go here)
	}

	///////////////////////////////////////////////////////////////////
	// All event handlers placed here in this method
	///////////////////////////////////////////////////////////////////
	private void createEvents() {
		///////////////////////////////////////////////////////////////
		// Calls Google Maps (Geocode) and Google Places APIs to return
		// a list of places with the search radius from the starting
		// address
		//TODO 2: Uncomment, read/understand when you have the btnSearch
		 //button in the JFrame
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// Convert address to latitude/longitude
				String address = txtStartAddress.getText();
				String geocodeUrl = generateGeocodeRequestUrl(address);
				String geocodeResponse = getStandardHtmlResponse(geocodeUrl);
				String latLong = parseGeocodeResponse(geocodeResponse);

				// Making web call to get nearby places search results from user input
				double meters = Double.parseDouble(txtSearchRadius.getText()) * 1609.34;
				String nearbyPlacesUrl = generateNearbyPlacesRequestUrl(latLong, txtQuery.getText(), (int)meters);
				System.out.println("DEBUG PRINT: " + nearbyPlacesUrl);
				String nearbyPlacesResponse = getStandardHtmlResponse(nearbyPlacesUrl);
				ArrayList<Place> places = parseNearbyPlacesResponse(nearbyPlacesResponse);

				// Populate JList with our results
				dlmResult.clear();
				for (Place p : places)
					dlmResult.addElement(p);
			}
		});
		


		///////////////////////////////////////////////////////////////
		// Calls Google Places API to return some place details for
		// the selected places (in the list) 
		 //TODO 7: Uncomment and read/understand when you have the btnDetails
		// button in the JFrame
		btnDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Get URL and response for place details
				String placeDetailUrl = generatePlaceDetailsRequestUrl((Place)lstResults.getSelectedValue());
				System.out.println("DEBUG PRINT: " + placeDetailUrl);
				String placeDetailResponse = getStandardHtmlResponse(placeDetailUrl);

				// Format the response and place in text area
				String formattedPlaceDetails = parsePlaceDetailResponse(placeDetailResponse);
				txtDetails.setText(formattedPlaceDetails);
			}
		});
		

		///////////////////////////////////////////////////////////////
		// Calls Google Maps API to return some directions to
		// the selected places (in the list, from the starting address)
		// TODO 10: Uncomment and read/understand when you have the btnDirections
		// button in the JFrame
		btnDirections.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Get URL and response for place details
				String mapDirectionUrl = generateDirectionsRequestUrl(txtStartAddress.getText(), (Place)lstResults.getSelectedValue());
				System.out.println("DEBUG PRINT: " + mapDirectionUrl);
				String mapDirectionResponse = getStandardHtmlResponse(mapDirectionUrl);

				// Format the response and place in text area
				String formattedDirectionDetails = parseDirectionsResponse(mapDirectionResponse);
				txtDetails.setText(formattedDirectionDetails);
			}
		});
		
	}

	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	// The following four methods generate the URL request strings for    //
	// the four API calls we need to make.								  //
	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////	
	////////////////////////////////////////////////////////////////////////
	// Generates a URL request for the Google Maps Geocode web-services API
	// according to the following web-page: https://developers.google.com/maps/documentation/geocoding/start
	// The purpose of this call is to get a JSON response with latitude and
	// longitude for the supplied address.
	////////////////////////////////////////////////////////////////////////
	public static String generateGeocodeRequestUrl(String address)
	{
		String url = mapsGeocodeBaseUrl;
		// TODO 3: Finish forming URL
		
		return url;
	}

	////////////////////////////////////////////////////////////////////////
	// Generates a URL request for the Google Places web-services API
	// according to the following web-page: https://developers.google.com/places/web-service/
	////////////////////////////////////////////////////////////////////////
	public static String generateNearbyPlacesRequestUrl(String latLong, String query, int meters)
	{
		String url = placesNearbySearchBaseUrl;
		// TODO 5: Finish forming URL
		
		return url;
	}

	////////////////////////////////////////////////////////////////////////
	// Generates a URL request for the Google Places web-services API
	// according to the following web-page: https://developers.google.com/places/web-service/
	////////////////////////////////////////////////////////////////////////
	public static String generatePlaceDetailsRequestUrl(Place place)
	{
		String url = placesDetailBaseUrl;
		// TODO 8: Finish forming URL
		
		return url;
	}

	////////////////////////////////////////////////////////////////////////
	// Generates a URL request for the Google Maps directions web-services API
	// according to the following web-page: https://developers.google.com/maps/documentation/directions/
	////////////////////////////////////////////////////////////////////////
	public static String generateDirectionsRequestUrl(String origin, Place destPlace)
	{
		String url = mapsDirectionBaseUrl;
		// TODO 11: Finish forming URL
		
		return url;
	}

	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	// The following four methods use the JSONObject and JSONArray		  //
	// classes defined in the org.json JAR file you imported to parse	  //
	// through each of the four responses from the four URL requests.	  //
	// Each method's header describes what kind of data it is returning.  //
	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	// Return the lat,long pair from the given geocode response
	// Ex: 34.5029,39.40201
	////////////////////////////////////////////////////////////////////////
	private static String parseGeocodeResponse(String response)
	{
		// TODO 4: Convert response string to JSON & dive down into JSON response to get lat/long
		return "TODO 4: parseGeocodeResponse not yet implemented.";
	}

	////////////////////////////////////////////////////////////////////////
	// Returns an arrayList of places given the nearby places results/response
	// This method needs to parse out the relevant info for a place that
	// we need (see the instance variables in Place.java) and create an object
	// for each place in the response that is passed in as a parameter (each
	// response is added to a new ArrayList, which is returned).
	////////////////////////////////////////////////////////////////////////
	private static ArrayList<Place> parseNearbyPlacesResponse(String response)
	{
		// Init places arrayList
		ArrayList<Place> places = new ArrayList<Place>();

		// TODO 6: Dive down into response string; when you find the actual
		// nearby places results, iteration through each one, creating a
		// Place object and adding to the places array.
		

		return places;
	}

	////////////////////////////////////////////////////////////////////////
	// Return a formatted directions string given the directions response.
	// Ex of formatted return String: 
	// START ADDRESS:
	//    901 E Alosta Ave, Azusa, CA 91702, USA
	//
	// END ADDRESS:
	//    1377 E Gladstone St, Glendora, CA 91740, USA
	//
	// DISTANCE (TIME):
	//    4.5 mi (10 mins)
	//
	// STEP-BY-STEP NAVIGATION:
	//    1: Head south toward E Alosta Ave/Historic Rte 66 W
	//    2: Turn right onto E Alosta Ave/Historic Rte 66 W
	//    3: Turn left at the 1st cross street onto N Citrus Ave
	//    4: Take the Interstate 210 ramp to San Bernardino
	//    5: Merge onto I-210 E
	//    6: Take exit 43 for Sunflower Ave
	//    7: Turn right onto S Sunflower Ave
	//    8: Turn left onto E Gladstone St
	//    9: Turn left at N Shellman Ave
	//    10: Turn right
	//    11: Turn right
	//    12: Turn left
	////////////////////////////////////////////////////////////////////////
	private static String parseDirectionsResponse(String response) {
	
		// TODO 9: Convert response string to JSON & dive down into JSON response
		// to get all the relevant data
		return "TODO 9: parseDirectionsResponse not yet implemented.";
	}

	////////////////////////////////////////////////////////////////////////
	// Returns a formatted place details string given the details response.
	// Ex. of formatted details return string:
	// ADDRESS:
	//	     1377 East Gladstone Street #104, Glendora
	//
	// PHONE:
	//	     (909) 305-0505
	//
	// WEB:
	//	     https://www.mooyah.com/locations/glendora-ca-268/
	//
	// HOURS:
	//	     N/A     Monday: 11:00 AM - 9:00 PM
	//	     Tuesday: 11:00 AM - 9:00 PM
	//	     Wednesday: 11:00 AM - 9:00 PM
	//	     Thursday: 11:00 AM - 9:00 PM
	//	     Friday: 11:00 AM - 10:00 PM
	//	     Saturday: 11:00 AM - 10:00 PM
	//	     Sunday: 11:00 AM - 9:00 PM
	////////////////////////////////////////////////////////////////////////
	private static String parsePlaceDetailResponse(String response) {
		// Create variables
		String phone = "N/A";
		String hours = "N/A";
		String web = "N/A";
		String address = "N/A";
		String dayHours = "N/A";

		// TODO 12: Convert response string to JSON & dive down into JSON response
		// to get all the relevant data
		return "TODO 12: parsePlaceDetailResponse not yet implemented.";
	}

	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	// The following method is included for your convenience and should	  //
	// NOT be edited for any reason.
	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	// Uses the google places web-services API by sending a standard
	// HTML request and returns the result as a String in JSON format.
	////////////////////////////////////////////////////////////////////////
	public static String getStandardHtmlResponse(String urlRequest)
	{
		// Create URL Request
		HttpURLConnection connection = null;

		try
		{
			//Create connection
			URL url = new URL(urlRequest);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			//connection.setRequestProperty("Content-Length", Integer.toString(urlParameters.getBytes().length));
			connection.setRequestProperty("Content-Language", "en-US");  

			connection.setUseCaches(false);
			connection.setDoOutput(true);

			//Send request
			DataOutputStream wr = new DataOutputStream (connection.getOutputStream());
			//wr.writeBytes(urlParameters);
			wr.close();

			//Get Response  
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
			String line;
			while ((line = rd.readLine()) != null)
			{
				response.append(line);
				response.append('\r');
			}
			rd.close();

			return response.toString();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Connection failed.");
		}
		finally
		{
			if (connection != null)
			{
				connection.disconnect();
			}
		}
		return null;
	}
}
