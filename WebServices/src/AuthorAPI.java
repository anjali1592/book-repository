import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class AuthorAPI {
	String url = null;
	String xml = null;
	URL urlXml = null;
	BufferedReader br = null;
	StringBuilder sb;
    String s = null;
	public AuthorAPI() throws IOException {
		// callGoodReadsAPI();
	}

	public ArrayList<String> callGoodReadsAPI(String Url) throws IOException,
			ParserConfigurationException, SAXException {
		Url = Url.replaceAll(" ", "%20");
		ArrayList<String> al = new ArrayList<String>();
		url = "https://www.goodreads.com/search/index.xml?key=LQdvhUKbZ8HB5HbXCkCQTw&q=";
		String output = xmlOutput(url + Url);
		// System.out.println(output);
		al = parseXml(output);
		return al;
	}

	public String xmlOutput(String url) throws IOException {
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

	public ArrayList<String> parseXml(String xml_input)
			throws ParserConfigurationException, SAXException, IOException {
		ArrayList<String> al = new ArrayList<String>();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = dbf.newDocumentBuilder();
		StringBuilder sb = new StringBuilder();
		Node n_item, n_child, n_works_elements, n_title_text, n_works;
		sb.append(xml_input);
		ByteArrayInputStream bis = new ByteArrayInputStream(sb.toString()
				.getBytes("UTF-8"));
		Document d = builder.parse(bis);
		NodeList nl = d.getElementsByTagName("search");
		for (int i = 0; i < nl.getLength(); i++) {
			n_item = nl.item(i);
			System.out.println(n_item.getNodeName());
			NodeList n_results = n_item.getChildNodes();
			for (int k = 0; k < n_results.getLength(); k++) {
				n_works = n_results.item(k);
				// System.out.println(n_works.getNodeName());
				if (n_works.getNodeName().equals("results")) {
					NodeList n_works_result = n_works.getChildNodes();
					for (int m = 0; m < n_works_result.getLength(); m++) {
						n_works_elements = n_works_result.item(m);
						if (n_works_elements.getNodeName().equals("work")) {
							NodeList n_best = n_works_elements.getChildNodes();
							for (int n = 0; n < n_best.getLength(); n++) {
								Node n_title = n_best.item(n);
								if (n_title.getNodeName().equals("best_book")) {
									NodeList n_titleList = n_title
											.getChildNodes();
									for (int h = 0; h < n_titleList.getLength(); h++) {
										Node book_list = n_titleList.item(h);
										if (book_list.getNodeName().equals(
												"title")) {
											al.add(book_list.getTextContent());
											System.out.println(book_list
													.getTextContent());
										}
									}
								}
							}
						}

					}
				}

			}

			System.out.println();
            
		}
		return al;

	}
public void callToAPI(){
	
}
	public static void main(String args[]) {

	}
}
