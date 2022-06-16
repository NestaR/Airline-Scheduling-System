package solution;

import java.nio.file.Paths;
import java.time.LocalDate;

import baseclasses.DataLoadingException;
import baseclasses.IAircraftDAO;
import baseclasses.ICrewDAO;
import baseclasses.IPassengerNumbersDAO;
import baseclasses.IRouteDAO;
import baseclasses.IScheduler;

/**
 * This class allows you to run the code in your classes yourself, for testing and development
 */
public class Main {

	public static void main(String[] args) {	
		IAircraftDAO aircraft = new AircraftDAO();
		ICrewDAO crew = new CrewDAO();
		IRouteDAO route = new RouteDAO();
		IPassengerNumbersDAO passengernumbers = new PassengerNumbersDAO();
		IScheduler scheduler = new Scheduler();
		try {
			aircraft.loadAircraftData(Paths.get("./data/aircraft.csv"));
			crew.loadCrewData(Paths.get("./data/crew.json"));
			route.loadRouteData(Paths.get("./data/routes.xml"));
			passengernumbers.loadPassengerNumbersData(Paths.get("./data/passengernumbers.db"));
			scheduler.generateSchedule(aircraft, crew, route, passengernumbers, LocalDate.of(2020, 7, 01), LocalDate.of(2020, 8, 31));
		}
		catch (DataLoadingException dle) {
			System.err.println("Error loading aircraft data");
			dle.printStackTrace();
		}
	}

}
