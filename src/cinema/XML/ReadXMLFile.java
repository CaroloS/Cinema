package cinema.XML;

import java.io.File;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import cinema.CinemaMain;

/**
 * This class defines a method to read an XML file using SAXBuilder to get the document 
 * object and root node. 
 * @author carolinesmith, daianabassi
 *
 */
public class ReadXMLFile {

	private String inputFile;
	Document document = null;
	Element rootNode = null;
	
	/**
	 * This constructor takes the String name of the input file to read from.
	 * @param inputFile the input file.
	 */
	public ReadXMLFile(String inputFile) {
		this.inputFile = inputFile;
	}

	/**
	 * Parses the input file passed in the constructor using SAXBuilder. Gets the document object
	 * and returns the root node.
	 * @return
	 */
	public Element readsXML() {

		//CREATES INSTANCE OF SAXBUILDER TO PARSE THE XML FILE PASSED IN THE CONSTRUCTOR
		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File(inputFile);

		try {
			
			//GETS THE DOCUMENT OBJECT AND ROOT NODE OF THE DOCUMENT
			document = (Document) builder.build(xmlFile);
			rootNode = document.getRootElement();

		} catch (IOException io) {
			CinemaMain.LOGGER.warning("Couldn't open file");
		//	System.out.println(io.getMessage());
		} catch (JDOMException jdomex) {
			CinemaMain.LOGGER.warning("Couldn't parse file");
		//	System.out.println(jdomex.getMessage());
		}
		
		//RETURNS THE ROOT NODE
		return rootNode;

	}

}
