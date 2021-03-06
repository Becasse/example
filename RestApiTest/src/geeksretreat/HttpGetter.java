package geeksretreat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class HttpGetter {

	public static void main(String[] args) {
		try {
			//String sXML = HttpGetter
		    //        .getDoc("http://api.geonames.org/weatherIcao?ICAO=LSZH&username=rheh&style=full");
		 
			String content = (String) new URL("http://www.yahoo.com/").getContent();
			
		    Document dom = HttpGetter.loadXMLFromString(content);
		 
		    Element el = dom.getDocumentElement();
		 
		    System.out.println(HttpGetter.getTextValue(el, "stationName"));
		    System.out.println(HttpGetter.getTextValue(el, "temperature"));
		} catch (Exception e) {
			
		}
	 
	}
	
	public static String getDoc(String urlToRead) {
		 
	    URL url;
	    HttpURLConnection conn;
	    BufferedReader rd;
	    String line;
	    String result = "";
	 
	    try {
	 
	        url = new URL(urlToRead);
	        conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	 
	        rd = new BufferedReader(
	                new InputStreamReader(conn.getInputStream()));
	 
	        while ((line = rd.readLine()) != null) {
	            result += line;
	        }
	 
	        rd.close();
	 
	    } catch (Exception e) {
	 
	        e.printStackTrace();
	 
	    }
	 
	    return result;
	}
	
	public static Document loadXMLFromString(String xml) throws Exception {
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    InputSource is = new InputSource(new StringReader(xml));
	    return builder.parse(is);
	}
	
	private static String getTextValue(Element ele, String tagName) {
	    String textVal = null;
	    NodeList nl = ele.getElementsByTagName(tagName);
	    if (nl != null && nl.getLength() > 0) {
	        Element el = (Element) nl.item(0);
	        textVal = el.getFirstChild().getNodeValue();
	    }
	 
	    return textVal;
	}
}
