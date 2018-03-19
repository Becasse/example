package com;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.JSONException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

@Path("/peticiones")
public class PeticionesService {
	private static final long serialVersionUID = 1L;

	@GET
	@Produces("application/json")
	public Response getUsers() throws JSONException {
		// List<User> users = new ArrayList<>();
		// users.add(new User("admin"));
		// users.add(new User("john"));
		// users.add(new User("usuario2"));


		String sXML = getDoc("http://api.geonames.org/weatherIcao?ICAO=LSZH&username=rheh&style=full");

		Document dom;
		try {
			dom = loadXMLFromString(sXML);
			Element el = dom.getDocumentElement();

			System.out.println(getTextValue(el, "stationName"));
			System.out.println(getTextValue(el, "temperature"));
			return Response.status(200).entity(getTextValue(el, "stationName")).build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

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

			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

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
