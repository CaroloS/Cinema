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

public class EditBookingsXML {

	// CREATES INSTANCE VARIABLES WHICH WILL BE SET BY THE CONTRUCTOR
	protected String inputFile;
	protected String rootElement;

	// CONSTRUCTOR - NEED TO SPECIFY AN INPUT FILE AND ROOT ELEMENT FOR XML WHEN
	// CREATING AN INSTANCE
	public EditBookingsXML(String inputFile, String rootElement) {
		this.inputFile = inputFile;
		this.rootElement = rootElement;
	}

	protected Element root = null;
	protected Document document = null;


	public void editsBookingXML() {

		File xmlFile = new File(inputFile);
		FileInputStream fis = null;

		if (xmlFile.exists()) {

			try {
				fis = new FileInputStream(xmlFile);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}

			SAXBuilder sb = new SAXBuilder();

			// PARSE THE XML CONTENT PROVIDED BY THE FILE INPUT STREAM AND
			// CREATE A DOCUMENT OBJECT
			try {
				document = sb.build(fis);
			} catch (JDOMException e) {
				e.printStackTrace();
				CinemaMain.LOGGER.warning("Couldn't create document from FileInputStream");

			} catch (IOException e) {
				e.printStackTrace();
			}

			// GET THE ROOT ELEMENT OF THE DOCUMENT OBJECT
			root = document.getRootElement();
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			List list = root.getChildren("filmBooking");

			for (int i = 0; i < list.size(); i++) {

				Element node = (Element) list.get(i);


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
