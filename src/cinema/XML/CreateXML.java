package cinema.XML;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import cinema.CinemaMain;

/**
 * This class defines a function and fields to create an XML file - assigning the new file the filename and root
 * element passed in the constructor. If the file already exists, the function in this class parses the 
 * file using SAXBuilder and gets the document object and root element.
 * @author carolinesmith, daianabassi
 *
 */
public class CreateXML {

	//CREATES INSTANCE VARIABLES WHICH WILL BE SET BY THE CONTRUCTOR
	protected String inputFile;
	protected String rootElement;

	/**
	 * This constructor takes the String name of the input file to create or write to, and 
	 * the String XML root element of the file.
	 * @param inputFile the input file.
	 * @param rootElement the XML root element.
	 */
	public CreateXML(String inputFile, String rootElement) {
		this.inputFile = inputFile;
		this.rootElement = rootElement;
	}
	
	protected Element root = null;
	protected Document document = null;
	
	/**
	 * Checks if input file exists, creates a file input stream, uses SAXBuilder to parse the 
	 * input XML file and create a document object. Gets the root of the document object. Creates
	 * a new document and root element if file doesn't exist.
	 */
	public void getsRoot() {

		// CHECK IF INPUT FILE EXISTS
		File xmlFile = new File(inputFile);

		if (xmlFile.exists()) {

			// TRY TO LOAD INPUT FILE IF IT EXISTS
			try {

				// CREATE A FILE INPUT STREAM
				FileInputStream fis;
				fis = new FileInputStream(xmlFile);

				// USES SAXBuilder TO PARSE THE INPUT FILE, IF IT EXISTS
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
			} catch (FileNotFoundException e) {
				CinemaMain.LOGGER.warning("Couldn't find the file");

				e.printStackTrace();
			}

		}

		else {
			// IF THE FILE INPUTFILE DOES NOT EXIST CREATE A NEW DOCUMENT AND NEW ROOT
			document = new Document();
			root = new Element(rootElement);
		}

	}

}