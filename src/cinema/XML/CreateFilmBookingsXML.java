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

	public String getBookingAttribute() {
		return bookingAttribute;
	}

	public void setBookingAttribute(String bookingAttribute) {
		this.bookingAttribute = bookingAttribute;
	}

	public String getBookedSeats() {
		return bookedSeats;
	}

	public void setBookedSeats(String bookedSeats) {
		this.bookedSeats = bookedSeats;
	}

	public String getBookedNumber() {
		return bookedNumber;
	}

	public void setBookedNumber(String bookedNumber) {
		this.bookedNumber = bookedNumber;
	}

	public String getUnBookedNumber() {
		return unBookedNumber;
	}

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
	
	
	
	
	
	
}

