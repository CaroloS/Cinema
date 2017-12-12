package cinema.XML;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import org.jdom2.Attribute;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import cinema.CinemaMain;
import cinema.shared_controllers.LoginController;

/**
 * This class extends <code>cinema.shared_view.CreateXML</code>.
 * It has instance variables that define all the information about a customer booking.
 * It defines getters and setters for these private variables. It defines a function to write the film 
 * information to an XML file using the filename and root passed in the constructor. 
 * @see <code>cinema.shared_view.CreateXML</code>.
 * @author carolinesmith, daianabassi
 *
 */
public class CreateUserBookingsXML extends CreateXML {
	
	//DECLARES INSTANCE VARIABLES TO WRITE TO XML
		private String filmName, filmDate, filmTime, seatBooked, userID;

	//	CALLS THE PARENT CONSTRUCTOR 
		public CreateUserBookingsXML (String inputFile, String rootElement) {
			super(inputFile, rootElement);
		}
	
		/**
		 * Gets the instance variable filmName
		 * @return the String filmName
		 */
		public String getFilmName() {
				return filmName;
			}

		/**
		 * Sets the instance variable filmName
		 * @param filmName the String to set it to
		 */
		public void setFilmName(String filmName) {
			this.filmName = filmName;
		}

		/**
		 * Gets the instance variable FilmDate
		 * @return the String filmDate
		 */ 
		public String getFilmDate() {
			return filmDate;
		}

		/**
		 * Sets the instance variable filmDate
		 * @param filmDate the String to set it to
		 */
		public void setFilmDate(String filmDate) {
			this.filmDate = filmDate;
		}

		/**
		 * Gets the instance variable filmTime
		 * @return the String filmTime
		 */
		public String getFilmTime() {
			return filmTime;
		}
		
		/**
		 * Sets the instance variable filmTime
		 * @param filmTime the String to set it to
		 */
		public void setFilmTime(String filmTime) {
			this.filmTime = filmTime;
		}

		/**
		 * Gets the instance variable seatBooked
		 * @return the String seatBooked
		 */
		public String getSeatBooked() {
			return seatBooked;
		}

		/**
		 * Sets the instance variable seatBooked
		 * @param seatBooked the String to set it to
		 */
		public void setSeatBooked(String seatBooked) {
			this.seatBooked = seatBooked;
		}

		/**
		 * Gets the instance variable userID
		 * @return the Strin userID
		 */
		public String getUserID() {
			return userID;
		}
		
		/**
		 * Sets the instance variable userID
		 * @param userID the String to set it to
		 */
		public void setUserID(String userID) {
			this.userID = userID;
		}

		
		/**
		 * Creates a new booking element and sets the user booking instance variables as the child elements
		 * of the booking element. Defines the booking attribute using the ID of the logged in user. 
		 * Adds the new booking element to the document root and sets the root to the document. 
		 * Writes the XML to the file specified in the constructor.
		 */
	public void CreateUserBooking() {
		
		// GENERATE RANDOM NUMBER FOR FILM ID
				Random rand = new Random();
				int n = rand.nextInt(1000);
		
		
		//CREATES A FILM ELEMENT AND SETS THE INSTANCE VARIABLES AS THE CHILD ELEMENTS OF FILM 
				Element booking = new Element("booking");
				booking.setAttribute(new Attribute("userID", LoginController.userID));
				booking.setAttribute(new Attribute("bookingID", Integer.toString(n)));
				booking.addContent(new Element("filmName").setText(filmName));
				booking.addContent(new Element("filmDate").setText(filmDate));
				booking.addContent(new Element("filmTime").setText(filmTime));
				booking.addContent(new Element("seatBooked").setText(seatBooked));
				root.addContent(booking);
				document.setContent(root);

				//WRITE THE XML TO FILE SPECIFIED IN THE CONSTRUCTOR
				try {
					FileWriter writer = new FileWriter(inputFile);
					XMLOutputter outputter = new XMLOutputter();

					outputter.setFormat(Format.getPrettyFormat());
					outputter.output(document, writer);
					// outputter.output(document, System.out);

					// CLOSE FILE 'film.xml'
					writer.close();

				} catch (IOException e) {
					CinemaMain.LOGGER.warning("Couldn't write to file");
					e.printStackTrace();
				}
	}

}
