package cinema.XML;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import cinema.CinemaMain;
import cinema.customer_controllers.WhatsOnCustController;
import cinema.shared_controllers.BookingController;

/**
 * A class that defines methods and variables to edit film bookings XML. Function in this class is 
 * called from the booking page when a customer makes a booking.
 * @author carolinesmith, daianabassi
 *
 */
public class EditBookingsXML {

	// CREATES INSTANCE VARIABLES WHICH WILL BE SET BY THE CONTRUCTOR
	protected String inputFile;
	protected String rootElement;

	/**
	 * This constructor takes the String name of the input file to edit and write to, and 
	 * the String XML root element of the file.
	 * @param inputFile the input file.
	 * @param rootElement the XML root element.
	 */
	public EditBookingsXML(String inputFile, String rootElement) {
		this.inputFile = inputFile;
		this.rootElement = rootElement;
	}

	protected Element root = null;
	protected Document document = null;

	/**
	 * Parses the input file and gets the root element and list of child nodes - i.e. the list
	 * of film bookings. If the film booking node attribute matches the unique page title on the 
	 * bookings page (i.e. matches the film being booked) - the XML is updated with the new 
	 * booking information and written back to the XML file. 
	 */
	public void editsBookingXML() {
		
		File xmlFile = new File(inputFile);
		FileInputStream fis = null;

		if (xmlFile.exists()) {

			//CREATES A FILE INPUT STREAM FROM THE FILE IF IT EXISTS
			try {
				fis = new FileInputStream(xmlFile);
			} catch (FileNotFoundException e1) {
				CinemaMain.LOGGER.warning("Couldn't find file");
				e1.printStackTrace();
			}

			//CREATES AN INSTANCE OF SAXBUILDER TO PARSE THE FILE
			SAXBuilder sb = new SAXBuilder();

			// PARSES THE XML CONTENT PROVIDED BY THE FILE INPUT STREAM AND
			// CREATES A DOCUMENT OBJECT
			try {
				document = sb.build(fis);
			} catch (JDOMException e) {
				e.printStackTrace();
				CinemaMain.LOGGER.warning("Couldn't create document from FileInputStream");

			} catch (IOException e) {
				e.printStackTrace();
			}

			// GETS THE ROOT ELEMENT OF THE DOCUMENT OBJECT
			root = document.getRootElement();
			try {
				fis.close();
			} catch (IOException e) {
				CinemaMain.LOGGER.warning("Couldn't close file");
				e.printStackTrace();
			}

			//GETS THE LIST OF CHILD 'FILMBOOKING' NODES OF THE ROOT ELEMENT
			List list = root.getChildren("filmBooking");

			//ITETRATES THROUGH THE LIST OF NODES 
			for (int i = 0; i < list.size(); i++) {

				Element node = (Element) list.get(i);

				//IF THE NODE'S ATTRIBUTE IS THE SAME AS THE UNIQUE PAGE TITLE FOR THE BOOKING PAGE 
				//i.e. IF THE FILM SELECTED FOR BOOKING IS THAT SAME AS THIS FILM NODE IN THE XML
				//SET THE XML WITH THE NEW BOOKING INFORMATION AFTER A CUSTOMER BOOKS A FILM
				if (node.getAttributeValue("name").equalsIgnoreCase(WhatsOnCustController.pageTitle)) {
					node.getChild("bookedSeats").setText(BookingController.bookedSeats);
					node.getChild("bookedNumber").setText(BookingController.totalBooked);
					node.getChild("unBookedNumber").setText(BookingController.totalUnBooked);

				}
			}

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

		} else {
			// IF THE FILE INPUTFILE DOES NOT EXIST CREATE A NEW DOCUMENT AND
			// NEW ROOT
			document = new Document();
			root = new Element(rootElement);
		}

	}

}
