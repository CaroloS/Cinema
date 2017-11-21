package uk.ac.ucl.coursework.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import uk.ac.ucl.coursework.CinemaMain;

public class CreateXML {

	//CREATES INSTANCE VARIABLES WHICH WILL BE SET BY THE CONTRUCTOR
	protected String inputFile;
	protected String rootElement;

	//CONSTRUCTOR - NEED TO SPECIFY AN INPUT FILE AND ROOT ELEMENT FOR XML WHEN CREATING AN INSTANCE
	public CreateXML(String inputFile, String rootElement) {
		this.inputFile = inputFile;
		this.rootElement = rootElement;
	}
	
	Element root = null;
	Document document = null;
	
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
					;
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
			} catch (FileNotFoundException e) {
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