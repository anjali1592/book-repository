import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
public class APICall {
String url =null;
String xml = null;
URL urlXml =null;
BufferedReader br = null;
StringBuilder sb;
String s = null;
public APICall() throws IOException, ParserConfigurationException, SAXException{
//	String key = "harry%20potter";
//	url = "http://svcs.sandbox.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsByKeywords&SERVICE-VERSION=1.0.0&SECURITY-APPNAME=Rocheste-BookSear-SBX-8d2cad3ef-26928319&RESPONSE-DATA-FORMAT=XML&REST-PAYLOAD&keywords=";
//	String output = xmlOutput(url+key);
//	System.out.println(output);
//	parseXml(output);
}
public HashMap<String,String>callequest(String key) throws IOException, ParserConfigurationException, SAXException{
	key = key.replaceAll(" ", "%20");
	url = "http://svcs.sandbox.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsByKeywords&SERVICE-VERSION=1.0.0&SECURITY-APPNAME=Rocheste-BookSear-SBX-8d2cad3ef-26928319&RESPONSE-DATA-FORMAT=XML&REST-PAYLOAD&keywords=";
	HashMap<String, String> hm = new HashMap<String,String>();
	String output = xmlOutput(url+key);
	System.out.println(output);
	hm = parseXml(output);
	return hm;
}
public String xmlOutput(String url) throws IOException{
	urlXml = new URL(url);
	HttpURLConnection huc = (HttpURLConnection) urlXml.openConnection();
    huc.setRequestMethod("GET");
    huc.connect();
    br = new BufferedReader(new InputStreamReader(huc.getInputStream()));
    sb = new StringBuilder();
    while ((s = br.readLine()) != null)
    {
      sb.append(s + "\n");
    }
    return sb.toString();

}

public HashMap<String,String> parseXml(String xml_input) throws ParserConfigurationException, SAXException, IOException{
HashMap<String,String> hm = new HashMap<String,String>();
DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
DocumentBuilder builder = dbf.newDocumentBuilder();
StringBuilder sb = new StringBuilder();
Node n_item,n_child;
sb.append(xml_input);
ByteArrayInputStream bis =  new ByteArrayInputStream(sb.toString().getBytes("UTF-8"));
Document d = builder.parse(bis);
NodeList nl = d.getElementsByTagName("item");
for(int i = 0 ;i<nl.getLength();i++){
	n_item = nl.item(i);
NodeList n2 = n_item.getChildNodes();
System.out.println("Element: "+ n_item.getTextContent());
for(int j=0;j<n2.getLength();j++){
	n_child = n2.item(j);
if(n_child.getNodeName().equals("shippingInfo") || n_child.getNodeName().equals("productID")
		||n_child.getNodeName().equals("returnsAccepted")||n_child.getNodeName().equals("condition")
		||n_child.getNodeName().equals("isMultiVariationListing")
		||n_child.getNodeName().equals("topRatedListing")||n_child.getNodeName().equals("primaryCategory")
		||n_child.getNodeName().equals("itemId")||n_child.getNodeName().equals("autopay")
		||n_child.getNodeName().equals("sellingStatus")||n_child.getNodeName().equals("listingInfo")
		||n_child.getNodeName().equals("topRatedListing")){
continue;
}
else{
	System.out.println(n_child.getNodeName()+ ": "+n_child.getTextContent());
	hm.put(n_child.getNodeName(), n_child.getTextContent());
}
}
System.out.println();
}
return hm;
}
public static void main(String args[]) throws IOException, ParserConfigurationException, SAXException{
	APICall ac = new APICall();
}
}
