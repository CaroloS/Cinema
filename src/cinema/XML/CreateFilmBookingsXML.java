package cinema.XML;

import java.io.FileWriter;
import java.io.IOException;

import org.jdom2.Attribute;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import cinema.CinemaMain;

/**
 * This class extends <code>cinema.shared_view.CreateXML</code>.
 * It has instance variables that define all the information about a film booking (seat booked, number available etc).
 * It defines getters and setters for these private variables. It defines a function to write the film 
 * information to an XML file using the filename and root passed in the constructor. 
 * @see <code>cinema.shared_view.CreateXML</code>.
 * @author carolinesmith, daianabassi
 *
 */
public class CreateFilmBookingsXML extends CreateXML {

	/**
	 * This constructor calls the parent and takes the String name of the 
	 * input file to create or write to, and the String XML root element of the file.
	 * @param inputFile the input file.
	 * @param rootElement the XML root element.
	 */
	public CreateFilmBookingsXML(String inputFile, String rootElement) {
		super(inputFile, rootElement);
	}

	// DECLARES INSTANCE VARIABLES TO WRITE TO XML
	private String bookingAttribute, bookedSeats, bookedNumber, unBookedNumber;

	public static Element selectedFilmNode;

	/**
	 * Gets the instance variable of the booking attribute 
	 * @return String booking attribute (film name + date + time)
	 */
	public String getBookingAttribute() {
		return bookingAttribute;
	}

	/**
	 * Sets the instance variable of booking attribute
	 * @param bookingAttribute String of booking attribute (film name + date + time)
	 */
	public void setBookingAttribute(String bookingAttribute) {
		this.bookingAttribute = bookingAttribute;
	}

	/**
	 * Gets the instance variable of the booked seats
	 * @return String booked seats
	 */
	public String getBookedSeats() {
		return bookedSeats;
	}

	/**
	 * Sets the instance variable of booked seats
	 * @param bookingAttribute String of booked seats
	 */
	public void setBookedSeats(String bookedSeats) {
		this.bookedSeats = bookedSeats;
	}

	/**
	 * Gets the instance variable of the booked number of seats
	 * @return String booked number
	 */
	public String getBookedNumber() {
		return bookedNumber;
	}

	/**
	 * Sets the instance variable of number booked seats
	 * @param bookingAttribute String of number booked seats
	 */
	public void setBookedNumber(String bookedNumber) {
		this.bookedNumber = bookedNumber;
	}
	
	/**
	 * Gets the instance variable of the number of available seats 
	 * @return String available
	 */
	public String getUnBookedNumber() {
		return unBookedNumber;
	}

	/**
	 * Sets the instance variable of available
	 * @param bookingAttribute String of available seats
	 */
	public void setUnBookedNumber(String unBookedNumber) {
		this.unBookedNumber = unBookedNumber;
	}

	/**
	 * Creates a new film booking element and sets the film booking instance variables as the child elements.
	 * Adds the new film booking element to the document root and sets the root to the document. 
	 * Writes the XML to the file specified in the constructor.
	 */
	public void createsBookings() {

		Element filmBooking = new Element("filmBooking");

		filmBooking.setAttribute(new Attribute("name", bookingAttribute));

		filmBooking.addContent(new Element("bookedSeats").setText(bookedSeats));
		filmBooking.addContent(new Element("bookedNumber").setText(bookedNumber));
		filmBooking.addContent(new Element("unBookedNumber").setText(unBookedNumber));

		root.addContent(filmBooking);
		document.setContent(root);

		// WRITE THE XML TO FILE SPECIFIED IN THE CONSTRUCTOR
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
	
	/**
	 * Creates initial film booking elements in filmBookings.xml when the program is first run. 
	 * It is called from <code>cinema.CinemaMain</code> with parameters passed for the film bookings
	 * to initially load the program with. 
	 * @param initAttribute String attribute is the film name + date + time
	 * @param initBookedSeats String of seats booked
	 * @param initBookedNumber String representation of number seats booked
	 * @param initAvailable String representation of number seats available
	 */
	public void createsInitialBookings(String initAttribute, String initBookedSeats, String initBookedNumber, String initAvailable) {

		Element filmBooking = new Element("filmBooking");

		filmBooking.setAttribute(new Attribute("name", initAttribute));

		filmBooking.addContent(new Element("bookedSeats").setText(initBookedSeats));
		filmBooking.addContent(new Element("bookedNumber").setText(initBookedNumber));
		filmBooking.addContent(new Element("unBookedNumber").setText(initAvailable));

		root.addContent(filmBooking);
		document.setContent(root);

		// WRITE THE XML TO FILE SPECIFIED IN THE CONSTRUCTOR
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

