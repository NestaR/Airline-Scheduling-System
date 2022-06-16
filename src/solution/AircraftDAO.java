package solution;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import baseclasses.Aircraft;
import baseclasses.DataLoadingException;
import baseclasses.IAircraftDAO;


/**
 * The AircraftDAO class is responsible for loading aircraft data from CSV files
 * and contains methods to help the system find aircraft when scheduling
 */
public class AircraftDAO implements IAircraftDAO {
	
	ArrayList<Aircraft> aircraft = new ArrayList<Aircraft>();
	/**
	 * Loads the aircraft data from the specified file, adding them to the currently loaded aircraft
	 * Multiple calls to this function, perhaps on different files, would thus be cumulative
	 * @param p A Path pointing to the file from which data could be loaded
	 * @throws DataLoadingException if anything goes wrong. The exception's "cause" indicates the underlying exception
     *
	 * Initially, this contains some starter code to help you get started in reading the CSV file...
	 */
	@Override
	public void loadAircraftData(Path p) throws DataLoadingException {	
		try {
			BufferedReader reader = Files.newBufferedReader(p);
			
			String line = "";

			reader.readLine();
			
			while( (line = reader.readLine()) != null) {
				Aircraft a = new Aircraft();
				

				String[] fields = line.split(",");
				

				String tailcode = fields[0];
				String type = fields[1];
				String manufacturer = fields[2];
				String model = fields[3];
				int seats = Integer.parseInt(fields[4]);
				int cabincrew = Integer.parseInt(fields[5]);
				String startingposition = fields[6];
				
				
				
				a.setTailCode(tailcode);
				a.setTypeCode(type);
				a.setManufacturer(Aircraft.Manufacturer.valueOf(manufacturer.toUpperCase()));
				a.setModel(model);
				a.setSeats(seats);
				a.setCabinCrewRequired(cabincrew);
				a.setStartingPosition(startingposition);
				aircraft.add(a);

			}
		}
		
		catch (IOException ioe) {

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
	
	/**
	 * Returns a list of all the loaded Aircraft with at least the specified number of seats
	 * @param seats the number of seats required
	 * @return a List of all the loaded aircraft with at least this many seats
	 */
	@Override
	public List<Aircraft> findAircraftBySeats(int seats) {
		// TODO Auto-generated method stub
		List<Aircraft> a = new ArrayList<Aircraft>();

			for (int i = 0; i<aircraft.size(); i++)
			{
				if (aircraft.get(i).getSeats() >= seats)
				{
					a.add(aircraft.get(i));
				}
			}
			return a;

		
	}

	/**
	 * Returns a list of all the loaded Aircraft that start at the specified airport code
	 * @param startingPosition the three letter airport code of the airport at which the desired aircraft start
	 * @return a List of all the loaded aircraft that start at the specified airport
	 */
	@Override
	public List<Aircraft> findAircraftByStartingPosition(String startingPosition) {
		// TODO Auto-generated method stub
		List<Aircraft> a = new ArrayList<Aircraft>();

			for (int i = 0; i<aircraft.size(); i++)
			{
				if (aircraft.get(i).getStartingPosition().contains(startingPosition))
				{
					a.add(aircraft.get(i));

				}
			}
			return a;

	}

	/**
	 * Returns the individual Aircraft with the specified tail code.
	 * @param tailCode the tail code for which to search
	 * @return the aircraft with that tail code, or null if not found
	 */
	@Override
	public Aircraft findAircraftByTailCode(String tailCode) {
		// TODO Auto-generated method stub
		Aircraft a = new Aircraft();

		for (int i = 0; i<aircraft.size(); i++)
		{
			if (aircraft.get(i).getTailCode().contains(tailCode))
			{
				a.setTailCode(tailCode);
				return a;
			}
		}
		return null;
		
	}

	/**
	 * Returns a List of all the loaded Aircraft with the specified type code
	 * @param typeCode the type code of the aircraft you wish to find
	 * @return a List of all the loaded Aircraft with the specified type code
	 */
	@Override
	public List<Aircraft> findAircraftByType(String typeCode) {
		// TODO Auto-generated method stub
		List<Aircraft> a = new ArrayList<Aircraft>();

			for (int i = 0; i<aircraft.size(); i++)
			{
				if (aircraft.get(i).getTypeCode().contains(typeCode))
				{
					a.add(aircraft.get(i));
				}
			}
			return a;
	}

	/**
	 * Returns a List of all the currently loaded aircraft
	 * @return a List of all the currently loaded aircraft
	 */
	@Override
	public List<Aircraft> getAllAircraft() {
		List<Aircraft> a = new ArrayList<Aircraft>();

			for (int i = 0; i<aircraft.size(); i++)
			{
				a.add(aircraft.get(i));
			}
			return a;
	}

	/**
	 * Returns the number of aircraft currently loaded 
	 * @return the number of aircraft currently loaded
	 */
	@Override
	public int getNumberOfAircraft() {
		return aircraft.size();
	}

	/**
	 * Unloads all of the aircraft currently loaded, ready to start again if needed
	 */
	@Override
	public void reset() {
		aircraft.clear();

	}

}
