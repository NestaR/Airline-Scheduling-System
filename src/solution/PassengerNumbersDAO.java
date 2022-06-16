package solution;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import baseclasses.DataLoadingException;
import baseclasses.IPassengerNumbersDAO;

/**
 * The PassengerNumbersDAO is responsible for loading an SQLite database
 * containing forecasts of passenger numbers for flights on dates
 */
public class PassengerNumbersDAO implements IPassengerNumbersDAO {
	ArrayList<String> passengernumbers = new ArrayList<String>();
	/**
	 * Returns the number of passenger number entries in the cache
	 * @return the number of passenger number entries in the cache
	 */
	@Override
	public int getNumberOfEntries() {
		// TODO Auto-generated method stub

		return passengernumbers.size()/3;
	}

	/**
	 * Returns the predicted number of passengers for a given flight on a given date, or -1 if no data available
	 * @param flightNumber The flight number of the flight to check for
	 * @param date the date of the flight to check for
	 * @return the predicted number of passengers, or -1 if no data available
	 */
	@Override
	public int getPassengerNumbersFor(int flightNumber, LocalDate date) {
		// TODO Auto-generated method stub

		for (int i = 0; i<passengernumbers.size(); i++)
		{
			
			if(passengernumbers.get(i).contains(Integer.toString(flightNumber)))
			{
				if(passengernumbers.get(i+1).contains(date.toString()))
				{
					return Integer.parseInt(passengernumbers.get(i-1));
				}
			}
			
		}
		return -1;
	}

	/**
	 * Loads the passenger numbers data from the specified SQLite database into a cache for future calls to getPassengerNumbersFor()
	 * Multiple calls to this method are additive, but flight numbers/dates previously cached will be overwritten
	 * The cache can be reset by calling reset() 
	 * @param p The path of the SQLite database to load data from
	 * @throws DataLoadingException If there is a problem loading from the database
	 */
	@Override
	public void loadPassengerNumbersData(Path p) throws DataLoadingException {
		// TODO Auto-generated method stub
		Connection c = null;
		try {

			c = DriverManager.getConnection("jdbc:sqlite:" + p);
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM PassengerNumbers;");
			
			while(rs.next())
			{

				passengernumbers.add(rs.getString("Passengers"));
				passengernumbers.add(rs.getString("FlightNumber"));
				passengernumbers.add(rs.getString("Date"));

				
			


			}

			
		}
		catch (SQLException se){
			se.printStackTrace();
			throw new DataLoadingException(se);
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
	 * Removes all data from the DAO, ready to start again if needed
	 */
	@Override
	public void reset() {
		// TODO Auto-generated method stub
		passengernumbers.clear();
	}

}
