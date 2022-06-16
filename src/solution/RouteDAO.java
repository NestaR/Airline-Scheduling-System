package solution;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import baseclasses.DataLoadingException;
import baseclasses.IRouteDAO;
import baseclasses.Route;

/**
 * The RouteDAO parses XML files of route information, each route specifying
 * where the airline flies from, to, and on which day of the week
 */
public class RouteDAO implements IRouteDAO {
	ArrayList<Route> route = new ArrayList<Route>();
	/**
	 * Finds all flights that depart on the specified day of the week
	 * @param dayOfWeek A three letter day of the week, e.g. "Tue"
	 * @return A list of all routes that depart on this day
	 */
	@Override
	public List<Route> findRoutesByDayOfWeek(String dayOfWeek) {
		// TODO Auto-generated method stub
		List<Route> a = new ArrayList<Route>();
		for (int i = 0; i<route.size(); i++)
		{
			if (route.get(i).getDayOfWeek().contains(dayOfWeek))
			{
				a.add(route.get(i));
			}
		}
		return a;
	}

	/**
	 * Finds all of the flights that depart from a specific airport on a specific day of the week
	 * @param airportCode the three letter code of the airport to search for, e.g. "MAN"
	 * @param dayOfWeek the three letter day of the week code to search for, e.g. "Tue"
	 * @return A list of all routes from that airport on that day
	 */
	@Override
	public List<Route> findRoutesByDepartureAirportAndDay(String airportCode, String dayOfWeek) {
		// TODO Auto-generated method stub
		List<Route> a = new ArrayList<Route>();
		for (int i = 0; i<route.size(); i++)
		{
			if (route.get(i).getDepartureAirportCode().contains(airportCode) && route.get(i).getDayOfWeek().contains(dayOfWeek))
			{
				a.add(route.get(i));
			}
		}
		return a;
	}

	/**
	 * Finds all of the flights that depart from a specific airport
	 * @param airportCode the three letter code of the airport to search for, e.g. "MAN"
	 * @return A list of all of the routes departing the specified airport
	 */
	@Override
	public List<Route> findRoutesDepartingAirport(String airportCode) {
		// TODO Auto-generated method stub
		List<Route> a = new ArrayList<Route>();
		for (int i = 0; i<route.size(); i++)
		{
			if (route.get(i).getDepartureAirportCode().contains(airportCode))
			{
				a.add(route.get(i));
			}
		}
		return a;
	}

	/**
	 * Finds all of the flights that depart on the specified date
	 * @param date the date to search for
	 * @return A list of all routes that depart on this date
	 */
	@Override
	public List<Route> findRoutesbyDate(LocalDate date) {
		// TODO Auto-generated method stub
		List<Route> a = new ArrayList<Route>();
		for (int i = 0; i<route.size(); i++)
		{
			if (date.getDayOfWeek().toString().contains(route.get(i).getDayOfWeek().toUpperCase()))
			{
				a.add(route.get(i));
			}
		}
		return a;
	}

	/**
	 * Returns The full list of all currently loaded routes
	 * @return The full list of all currently loaded routes
	 */
	@Override
	public List<Route> getAllRoutes() {
		// TODO Auto-generated method stub
		List<Route> a = new ArrayList<Route>();
		System.out.println(route.size());
		for (int i = 0; i<route.size(); i++)
		{
			a.add(route.get(i));
		}
		return a;
	}

	/**
	 * Returns The number of routes currently loaded
	 * @return The number of routes currently loaded
	 */
	@Override
	public int getNumberOfRoutes() {
		// TODO Auto-generated method stub
		return route.size();
	}

	/**
	 * Loads the route data from the specified file, adding them to the currently loaded routes
	 * Multiple calls to this function, perhaps on different files, would thus be cumulative
	 * @param p A Path pointing to the file from which data could be loaded
	 * @throws DataLoadingException if anything goes wrong. The exception's "cause" indicates the underlying exception
	 */
	@Override
	public void loadRouteData(Path arg0) throws DataLoadingException {
		// TODO Auto-generated method stub
		try
		{
			InputStream reader = Files.newInputStream(arg0);
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = db.parse(reader);

			
			Element root = doc.getDocumentElement();

			NodeList children = root.getChildNodes();

				for(int i = 0; i<children.getLength();i++)
				{
					Route a = new Route();
					Node c = children.item(i);
					if(c.getNodeName().equals("Route"))
					{
						

								Element eElement = (Element) c;
								//a.setArrivalAirport(d.getChildNodes().item(0).getNodeValue());
								//System.out.println(eElement.getElementsByTagName("FlightNumber").item(0).getTextContent());
								a.setFlightNumber(Integer.parseInt(eElement.getElementsByTagName("FlightNumber").item(0).getTextContent()));
								//System.out.println(eElement.getElementsByTagName("DayOfWeek").item(0).getTextContent());
								a.setDayOfWeek(eElement.getElementsByTagName("DayOfWeek").item(0).getTextContent());
								//System.out.println(eElement.getElementsByTagName("DepartureTime").item(0).getTextContent());
								a.setDepartureTime(LocalTime.parse(eElement.getElementsByTagName("DepartureTime").item(0).getTextContent()));
								//System.out.println(eElement.getElementsByTagName("DepartureAirport").item(0).getTextContent());
								a.setDepartureAirport(eElement.getElementsByTagName("DepartureAirport").item(0).getTextContent());
								//System.out.println(eElement.getElementsByTagName("DepartureAirportCode").item(0).getTextContent());
								a.setDepartureAirportCode(eElement.getElementsByTagName("DepartureAirportCode").item(0).getTextContent());
								//System.out.println(eElement.getElementsByTagName("ArrivalTime").item(0).getTextContent());
								a.setArrivalTime(LocalTime.parse(eElement.getElementsByTagName("ArrivalTime").item(0).getTextContent()));
								//System.out.println(eElement.getElementsByTagName("ArrivalAirport").item(0).getTextContent());
								a.setArrivalAirport(eElement.getElementsByTagName("ArrivalAirport").item(0).getTextContent());
								//System.out.println(eElement.getElementsByTagName("ArrivalAirportCode").item(0).getTextContent());
								a.setArrivalAirportCode(eElement.getElementsByTagName("ArrivalAirportCode").item(0).getTextContent());
								//System.out.println(eElement.getElementsByTagName("Duration").item(0).getTextContent());
								a.setDuration(Duration.parse(eElement.getElementsByTagName("Duration").item(0).getTextContent()));
								
								route.add(a);

					}
				}
				
		}
		catch (ParserConfigurationException | SAXException | IOException ioe)
		{
			throw new DataLoadingException(ioe);
		}
		catch (DateTimeParseException ioe)
		{
			throw new DataLoadingException(ioe);
		}
		catch (NullPointerException ioe) {
			throw new DataLoadingException(ioe);
		}
		catch (NumberFormatException ioe) {
			throw new DataLoadingException(ioe);
		}
		catch (IllegalArgumentException ioe)
		{
			throw new DataLoadingException(ioe);
		}
	}

	/**	 * Unloads all of the crew currently loaded, ready to start again if needed
	 */
	@Override
	public void reset() {
		// TODO Auto-generated method stub
		route.clear();
	}

}
