import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;


public class GoogleAPI {
	String url = null;
	String xml = null;
	URL urlXml = null;
	static String file_name =null;
	BufferedReader br = null;
	StringBuilder sb;
	Book b;
	File f1;
    String s;
	public GoogleAPI() throws IOException {
		
		b = new Book();
	}

	public HashMap<String,Book> callGoogleApi(String Url) throws IOException,
			ParserConfigurationException, SAXException, ParseException {
		file_name = Url;
		HashMap<String, Book> hm = new HashMap<String,Book>();
		Url = Url.replaceAll(" ", "%20");
		System.out.println("URL: "+Url);
		url = "https://www.googleapis.com/books/v1/volumes?q=";
		String output = jasonOutput(url+Url+"=epub&key=AIzaSyB9Q4qRzN5wLVrN4sUIF6gAcgJ0RP8pRE0");
	    hm= parseJason(output);
		return hm;
		
		
	}

	public String jasonOutput(String url) throws IOException {
		
		urlXml = new URL(url);
		HttpURLConnection huc = (HttpURLConnection) urlXml.openConnection();
		huc.setRequestMethod("GET");
		huc.connect();
		br = new BufferedReader(new InputStreamReader(huc.getInputStream()));
		sb = new StringBuilder();
		while ((s = br.readLine()) != null) {
			sb.append(s + "\n");
		}
		return sb.toString();
	}
	
public HashMap<String,Book> parseJason(String json_input) throws ParseException, IOException{

//	
    HashMap<String, Book> hm = new HashMap<String,Book >();  
	JSONParser parser = new JSONParser();
	//Object o;
	JSONArray jArray;
	JSONObject jo;
	Object o = parser.parse(json_input);
	jo = (JSONObject) o;
	jArray = (JSONArray)jo.get("items");
	for(Object o1 : jArray){
		System.out.println("-----------------------------------");
		jo = (JSONObject)o1;
        JSONObject volume = (JSONObject)jo.get("volumeInfo");
        String description = (String) volume.get("description");
        b.description = description;
        System.out.println(description);
       
        JSONObject accessInfo = (JSONObject)jo.get("accessInfo");
        JSONObject epub = (JSONObject)accessInfo.get("epub");
        Boolean epub1 = (Boolean)epub.get("isAvailable");
        if(epub1 == true){
        	String link = (String) epub.get("acsTokenLink");
        	System.out.println("Epub available: "+link);
        	b.epub = "Epub available: "+link;
        	
        }
        else{
        	System.out.println("Epub not available");
        	b.epub = "Epub not available";
        	//w.write("Epub not available");
        }
        JSONObject pdf1 = (JSONObject) accessInfo.get("pdf");
        Boolean pdf = (Boolean)pdf1.get("isAvailable");
        if(pdf == true){
        	System.out.println("PDF is available");
        	b.pdf = "PDF is available";
        	//w.write("PDF is available");
        }
        else{
        	System.out.println("PDF is not available");
        	b.pdf = "PDF not available";
        	//w.write("PDF is not available");
        }
        break;
	}

  hm.put(file_name, b);	
   return hm;	

	
}
public static void main(String args[]) throws IOException, ParserConfigurationException, SAXException, ParseException{

}


}
