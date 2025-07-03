import java.io.*;
import java.util.*;

import java.io.IOException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class weather{
    public static  String parseOpeanweatherResponse(String responseBody) {
	return responseBody;   
     
    }
    public static String formatPhraseForURI(String phrase) {
        return phrase.replace(" ","=&");
    }

    public static HttpResponse<String> get(String uri, String key) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(uri))
            .header("appid", key) // Only include the appid header
            .method("GET", HttpRequest.BodyPublishers.noBody())
            .build();
        
        return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    }


    
    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length == 2) {  // Expecting city name, language, and zip
            String city = args[0];
            String zip = args[1];
            String key = "9894588f6c10bc8341e960eff3d7cd56"; 
            String host = "api.openweathermap.org";
String uri = "http://api.openweathermap.org/data/2.5/weather?units=imperial&q=" + formatPhraseForURI(city) 
                + "&zip=" + zip
                + "&lang=en" 
                + "&appid=" + key;
          
	    ///q=Williamstown&zip=01267&lang=en&appid=9894588f6c10bc8341e960eff3d7cd56
            // if response status code is successful
HttpResponse<String> response = get(uri, key);
String responseBody = response.body();
	if (response.statusCode() == 200) {
                // print the completions after parsing the response
                // HINT: use the parseShazamResponse helper method
	    String [] words = ( parseOpeanweatherResponse(responseBody)).split("[,:{}]+");
	    List<String> weather = Arrays.asList(words);
	    System.out.println("Location Description & Weather Conditions for " + city + ":");
	    System.out.println(" Longitude: " + weather.get(3));
	    System.out.println(" Latitude: " + weather.get(5));
	    System.out.println(" Sea_level: " + weather.get(33));
	    System.out.println(" Ground_Level: " + weather.get(35));
	    System.out.println(" Pressure: " + weather.get(29));
	    System.out.println(" Humidity: " + weather.get(31));
	    System.out.println(" Visibility: " + weather.get(37));
	    System.out.println( " Wind speed: " + weather.get(40));
	    System.out.println(" The sky: " + weather.get(13));
	    System.out.println(" The tempereture is: " + weather.get(21)+ " but feels like: "+ weather.get(23) );
	      
		double tempfeel =  Double.parseDouble(weather.get(23));
		 System.out.println("ADVICE");
		if ( tempfeel <= 60.0){
		    System.out.println("--It will be cold, consider packing to keep warm");
		}
	        
		else{
		    System.out.println("--It will be a warm trip");
		}
		System.out.println("--Make necessary decisions depending on your personal needs");
	       
	}
	else {
	    System.out.println("Error 400s");
	}
	}
	else {
	    System.out.println(" Please provide the city name, and zipcode respectfully");
	}
}
}
