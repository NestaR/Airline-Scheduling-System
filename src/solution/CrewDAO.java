package solution;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import baseclasses.CabinCrew;
import baseclasses.Crew;
import baseclasses.DataLoadingException;
import baseclasses.ICrewDAO;
import baseclasses.Pilot;
import baseclasses.Pilot.Rank;

/**
 * The CrewDAO is responsible for loading data from JSON-based crew files 
 * It contains various methods to help the scheduler find the right pilots and cabin crew
 */
public class CrewDAO implements ICrewDAO {
	ArrayList<Crew> cabincrew = new ArrayList<Crew>();
	ArrayList<Crew> pilotcrew = new ArrayList<Crew>();
	/**
	 * Loads the crew data from the specified file, adding them to the currently loaded crew
	 * Multiple calls to this function, perhaps on different files, would thus be cumulative
	 * @param p A Path pointing to the file from which data could be loaded
	 * @throws DataLoadingException if anything goes wrong. The exception's "cause" indicates the underlying exception
	 */
	@Override
	public void loadCrewData(Path p) throws DataLoadingException {

		try {
		
			BufferedReader reader = Files.newBufferedReader(p);
			
			String json = ""; String line = "";
			while((line = reader.readLine()) != null) { json = json + line;}
				
				JSONObject staff = new JSONObject(json);
				JSONArray pilots = staff.getJSONArray("pilots");
				
				
				for(int i=0;i<pilots.length();i++)
				{			
					
					Pilot PilotCrew = new Pilot();
					JSONObject pilot = pilots.getJSONObject(i);
					
					JSONArray type = pilot.getJSONArray("typeRatings");

					PilotCrew.setForename(pilot.getString("forename"));
					
					PilotCrew.setSurname(pilot.getString("surname"));
					
					PilotCrew.setRank(pilot.getEnum(Rank.class, "rank"));

					PilotCrew.setHomeBase(pilot.getString("homebase"));
					
					for (int j=0;j<type.length();j++)
					{
						PilotCrew.setQualifiedFor(type.getString(j));
					}
					
					pilotcrew.add(PilotCrew);
				}
				
				JSONArray cabinCrew = staff.getJSONArray("cabincrew");
				
				for(int i=0;i<cabinCrew.length();i++)
				{			
					CabinCrew CrewCabin = new CabinCrew();
					JSONObject Cabin = cabinCrew.getJSONObject(i);
					
					JSONArray type = Cabin.getJSONArray("typeRatings");
					
					
					CrewCabin.setForename(Cabin.getString("forename"));
					
					CrewCabin.setSurname(Cabin.getString("surname"));
					
					CrewCabin.setHomeBase(Cabin.getString("homebase"));
					
					for (int j=0;j<type.length();j++)
					{
						CrewCabin.setQualifiedFor(type.getString(j));
					}
					cabincrew.add(CrewCabin);
					
				}
				
			
        } catch (IOException ioe) {
        	throw new DataLoadingException(ioe);
        }
		catch (NullPointerException ioe) {
			throw new DataLoadingException(ioe);
		}
		catch (JSONException ioe) {
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
 
	
	
	/**
	 * Returns a list of all the cabin crew based at the airport with the specified airport code
	 * @param airportCode the three-letter airport code of the airport to check for
	 * @return a list of all the cabin crew based at the airport with the specified airport code
	 */
	
	@Override
	public List<CabinCrew> findCabinCrewByHomeBase(String airportCode) {
		// TODO Auto-generated method stub
		List<CabinCrew> a = new ArrayList<CabinCrew>();
			for (int i = 0; i<cabincrew.size(); i++)
			{
				if (cabincrew.get(i).getHomeBase().contains(airportCode))
				{
					a.add((CabinCrew) cabincrew.get(i));
				}
			}
			return a;
	}

	/**
	 * Returns a list of all the cabin crew based at a specific airport AND qualified to fly a specific aircraft type
	 * @param typeCode the type of plane to find cabin crew for
	 * @param airportCode the three-letter airport code of the airport to check for
	 * @return a list of all the cabin crew based at a specific airport AND qualified to fly a specific aircraft type
	 */
	@Override
	public List<CabinCrew> findCabinCrewByHomeBaseAndTypeRating(String typeCode, String airportCode) {
		// TODO Auto-generated method stub
		List<CabinCrew> a = new ArrayList<CabinCrew>();
		for (int i = 0; i<cabincrew.size(); i++)
		{
			if (cabincrew.get(i).getHomeBase().contains(airportCode) && cabincrew.get(i).getTypeRatings().contains(typeCode))
			{
				a.add((CabinCrew) cabincrew.get(i));
			}
		}
		return a;
	}

	/**
	 * Returns a list of all the cabin crew currently loaded who are qualified to fly the specified type of plane
	 * @param typeCode the type of plane to find cabin crew for
	 * @return a list of all the cabin crew currently loaded who are qualified to fly the specified type of plane
	 */
	@Override
	public List<CabinCrew> findCabinCrewByTypeRating(String typeCode) {
		// TODO Auto-generated method stub
		List<CabinCrew> a = new ArrayList<CabinCrew>();
		for (int i = 0; i<cabincrew.size(); i++)
		{
			if (cabincrew.get(i).getTypeRatings().contains(typeCode))
			{
				a.add((CabinCrew) cabincrew.get(i));
			}
		}
		return a;
	}

	/**
	 * Returns a list of all the pilots based at the airport with the specified airport code
	 * @param airportCode the three-letter airport code of the airport to check for
	 * @return a list of all the pilots based at the airport with the specified airport code
	 */
	@Override
	public List<Pilot> findPilotsByHomeBase(String airportCode) {
		// TODO Auto-generated method stub
		List<Pilot> a = new ArrayList<Pilot>();
		for (int i = 0; i<pilotcrew.size(); i++)
		{
			if (pilotcrew.get(i).getHomeBase().contains(airportCode))
			{
				a.add((Pilot) pilotcrew.get(i));
			}
		}
		return a;
	}

	/**
	 * Returns a list of all the pilots based at a specific airport AND qualified to fly a specific aircraft type
	 * @param typeCode the type of plane to find pilots for
	 * @param airportCode the three-letter airport code of the airport to check for
	 * @return a list of all the pilots based at a specific airport AND qualified to fly a specific aircraft type
	 */
	@Override
	public List<Pilot> findPilotsByHomeBaseAndTypeRating(String typeCode, String airportCode) {
		// TODO Auto-generated method stub
		List<Pilot> a = new ArrayList<Pilot>();
		for (int i = 0; i<pilotcrew.size(); i++)
		{
			if (pilotcrew.get(i).getHomeBase().contains(airportCode) && pilotcrew.get(i).getTypeRatings().contains(typeCode))
			{
				a.add((Pilot) pilotcrew.get(i));
			}
		}
		return a;
	}

	/**
	 * Returns a list of all the pilots currently loaded who are qualified to fly the specified type of plane
	 * @param typeCode the type of plane to find pilots for
	 * @return a list of all the pilots currently loaded who are qualified to fly the specified type of plane
	 */
	@Override
	public List<Pilot> findPilotsByTypeRating(String typeCode) {
		// TODO Auto-generated method stub
		List<Pilot> a = new ArrayList<Pilot>();
		for (int i = 0; i<pilotcrew.size(); i++)
		{
			if (pilotcrew.get(i).getTypeRatings().contains(typeCode))
			{
				a.add((Pilot) pilotcrew.get(i));
			}
		}
		return a;
	}

	/**
	 * Returns a list of all the cabin crew currently loaded
	 * @return a list of all the cabin crew currently loaded
	 */
	@Override
	public List<CabinCrew> getAllCabinCrew() {
		// TODO Auto-generated method stub
		List<CabinCrew> a = new ArrayList<CabinCrew>();
		for (int i = 0; i<cabincrew.size(); i++)
		{

	    	  a.add((CabinCrew) cabincrew.get(i));

		}
		return a;
	}

	/**
	 * Returns a list of all the crew, regardless of type
	 * @return a list of all the crew, regardless of type
	 */
	@Override
	public List<Crew> getAllCrew() {
		// TODO Auto-generated method stub
		List<Crew> a = new ArrayList<Crew>();
		
		for (int i = 0; i<cabincrew.size(); i++)
		{
	    	  a.add(cabincrew.get(i));
		}
		for (int j = 0; j<pilotcrew.size(); j++)
		{
	    	  a.add(pilotcrew.get(j));
		}
		return a;
	}

	/**
	 * Returns a list of all the pilots currently loaded
	 * @return a list of all the pilots currently loaded
	 */
	@Override
	public List<Pilot> getAllPilots() {
		// TODO Auto-generated method stub
		List<Pilot> a = new ArrayList<Pilot>();
		for (int i = 0; i<pilotcrew.size(); i++)
		{

	    	  a.add((Pilot) pilotcrew.get(i));

		}
		return a;
	}

	@Override
	public int getNumberOfCabinCrew() {
		// TODO Auto-generated method stub
		return cabincrew.size();
	}

	/**
	 * Returns the number of pilots currently loaded
	 * @return the number of pilots currently loaded
	 */
	@Override
	public int getNumberOfPilots() {
		// TODO Auto-generated method stub
		return pilotcrew.size();
	}

	/**
	 * Unloads all of the crew currently loaded, ready to start again if needed
	 */
	@Override
	public void reset() {
		// TODO Auto-generated method stub
		pilotcrew.clear();
		cabincrew.clear();
	}

}
