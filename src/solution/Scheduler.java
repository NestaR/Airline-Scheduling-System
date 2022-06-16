package solution;
import java.time.LocalDate;
import java.util.ArrayList;

import baseclasses.Aircraft;
import baseclasses.CabinCrew;
import baseclasses.Crew;
import baseclasses.DataLoadingException;
import baseclasses.DoubleBookedException;
import baseclasses.FlightInfo;
import baseclasses.IAircraftDAO;
import baseclasses.ICrewDAO;
import baseclasses.IPassengerNumbersDAO;
import baseclasses.IRouteDAO;
import baseclasses.IScheduler;
import baseclasses.Pilot;
import baseclasses.Route;
import baseclasses.Schedule;
import baseclasses.SchedulerRunner;

public class Scheduler implements IScheduler {
	ArrayList<Schedule> schedule = new ArrayList<Schedule>();
	@Override
	public Schedule generateSchedule(IAircraftDAO arg0, ICrewDAO arg1, IRouteDAO arg2, IPassengerNumbersDAO arg3,
			LocalDate arg4, LocalDate arg5) {
		Schedule s = new Schedule(arg2, arg4, arg5);
		try
		{
			
			for(FlightInfo flight : s.getCompletedAllocations())
			{

				Aircraft plane = arg0.findAircraftByTailCode("G-JMAB");	
				s.allocateAircraftTo(plane, flight);
				
				CabinCrew crew = (CabinCrew) arg1.findCabinCrewByHomeBase("MAN");
				s.allocateCabinCrewTo((CabinCrew) arg1.findCabinCrewByHomeBase("MAN"), flight);
					
				Pilot captain = (Pilot) arg1.findPilotsByHomeBase("MAN");
				s.allocateCaptainTo(captain, flight);
					
				Pilot firstOfficer = (Pilot) arg1.findPilotsByHomeBase("MAN");
				s.allocateFirstOfficerTo(firstOfficer, flight);
				
				
			}
			
		}
		catch(DoubleBookedException ioe)
		{
			ioe.printStackTrace();
		}
		//System.out.println(s);
	return s;
		
	}
	@Override
	public void setSchedulerRunner(SchedulerRunner arg0) {
	

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

}
