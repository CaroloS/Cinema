package cinema.XML;

import java.io.FileWriter;
import java.io.IOException;

import org.jdom2.Attribute;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class CreateBookingsXML extends CreateXML {

	public CreateBookingsXML(String inputFile, String rootElement) {
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
			e.printStackTrace();
		}
	}
	
	
	public void editsBookings() {

		

		selectedFilmNode.getChild("bookedSeats").setText("poo");
		selectedFilmNode.getChild("bookedNumber").setText("ten");
		selectedFilmNode.getChild("unBookedNumber").setText("eleven");

		root.addContent(selectedFilmNode);
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
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
}

/*
 * 
 * public void editsBookings() {
 * 
 * selectedFilmNode.getChild("bookedSeats").setText("poo");
 * selectedFilmNode.getChild("booked").setText("ten");
 * selectedFilmNode.getChild("unbooked").setText("eleven");
 * 
 * }
 * 
 * public void writesToXML() {
 * 
 * }
 */
