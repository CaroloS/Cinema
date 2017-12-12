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
import cinema.customer_controllers.CustAccountController;
import cinema.shared_controllers.LoginController;
import cinema.shared_controllers.SignUpController;

/**
 * A class that defines methods and variables to edit a user's booking XML. Function in this class is 
 * called from the customer account page when a customer deletes a film booking in the future.
 * @author carolinesmith, daianabassi
 *
 */
public class EditUserBookingsXML {
	// CREATES INSTANCE VARIABLES WHICH WILL BE SET BY THE CONTRUCTOR
	protected String inputFile;
	protected String rootElement;

	/**
	 * This constructor takes the String name of the input file to edit and write to, and 
	 * the String XML root element of the file.
	 * @param inputFile the input file.
	 * @param rootElement the XML root element.
	 */
	public EditUserBookingsXML(String inputFile, String rootElement) {
		this.inputFile = inputFile;
		this.rootElement = rootElement;
	}

	protected Element root = null;
	protected Document document = null;


	public void editsUserBooking() {

		File xmlFile = new File(inputFile);
		FileInputStream fis = null;

		//CREATES A FILE INPUT STREAM FROM THE FILE IF IT EXISTS
		if (xmlFile.exists()) {

			try {
				fis = new FileInputStream(xmlFile);
			} catch (FileNotFoundException e1) {
				CinemaMain.LOGGER.warning("Couldn't find file");
				e1.printStackTrace();
			}

			//CREATES AN INSTANCE OF SAXBUILDER TO PARSE THE FILE
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
				CinemaMain.LOGGER.warning("Couldn't close file input stream");
				e.printStackTrace();
			}

			//GETS THE LIST OF CHILD 'BOOKING' NODES OF THE ROOT ELEMENT
			List list = root.getChildren("booking");

			//ITETRATES THROUGH THE LIST OF NODES 
			for (int i = 0; i < list.size(); i++) {

				Element node = (Element) list.get(i);

				//IF THE NODE'S ATTRIBUTE MATCHES THE ID OF THE FILM TO BE DELETED - 
				//(WHICH IS SET USING THE STATIC FIELD IN 'CustAccountController')
				//THE NODE IS DELETED FROM THE XML
				if (node.getAttributeValue("bookingID").equalsIgnoreCase(CustAccountController.ID)) {
					root.removeContent(node);
					
				}
			}

			// WRITE THE XML TO FILE SPECIFIED IN THE CONSTRUCTOR
			try {
				FileWriter writer = new FileWriter(inputFile);
				XMLOutputter outputter = new XMLOutputter();

				outputter.setFormat(Format.getPrettyFormat());
				outputter.output(document, writer);
				// outputter.output(document, System.out);

				// CLOSE FILE 'user.xml'
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
