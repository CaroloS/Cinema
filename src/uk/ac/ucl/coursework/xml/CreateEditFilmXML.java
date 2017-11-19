package uk.ac.ucl.coursework.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import uk.ac.ucl.coursework.CinemaMain;

public class CreateEditFilmXML {
	
	//STATIC FILM VARIABLES TO PASS TO 'createsFilm()' METHOD
	private static String title, genre, description, start, end, date; 
	
	//GETTERS AND SETTERS FOR THE PRIVATE VARIABLES
	public static String getTitle() {
		return title;
	}

	public static void setTitle(String title) {
		CreateEditFilmXML.title = title;
	}

	public static String getGenre() {
		return genre;
	}

	public static void setGenre(String genre) {
		CreateEditFilmXML.genre = genre;
	}

	public static String getDescription() {
		return description;
	}

	public static void setDescription(String description) {
		CreateEditFilmXML.description = description;
	}

	public static String getStart() {
		return start;
	}

	public static void setStart(String start) {
		CreateEditFilmXML.start = start;
	}

	public static String getEnd() {
		return end;
	}

	public static void setEnd(String end) {
		CreateEditFilmXML.end = end;
	}

	public static String getDate() {
		return date;
	}

	public static void setDate(String date) {
		CreateEditFilmXML.date = date;
	}
	
	/**METHOD TO PASS TO THE 'ADD' BUTTON ON EMPLOYEE HOME PAGE
	 * LOADS/PARSES 'film.XML' IF EXISTS OR CREATES NEW XML FILE
	 * ADDS A FILM ELEMENT TO THE ROOT AND PASSES THE FILM VARIABLES AS THE CHILD ELEMENTS
	 * @exception 
	 */
	public static void createsFilm() {

		// CHECK IF 'film.xml' EXISTS
		Document document = null;
		Element root = null;
		File xmlFile = new File("film.xml");

		if (xmlFile.exists()) {

			// TRY TO LOAD 'film.xml' IF IT EXISTS
			try {

				// CREATE A FILE INPUT STREAM
				FileInputStream fis;
				fis = new FileInputStream(xmlFile);

				// USES SAXBuilder TO PARSE THE FILE, IF IT EXISTS
				SAXBuilder sb = new SAXBuilder();

				// PARSE THE XML CONTENT PROVIDED BY THE FILE INPUT STREAM AND CREATE A DOCUMENT
				// OBJECT
				try {
					document = sb.build(fis);
				}
				catch (JDOMException e) {
					e.printStackTrace();
					CinemaMain.LOGGER.warning("Couldn't create document from FileInputStream using film.XML");;
				}
				catch (IOException e) {
					e.printStackTrace();
				}

				// GET THE ROOT ELEMENT OF THE DOCUMENT OBJECT 'films'
				root = document.getRootElement();
				try {
					fis.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		}

		else {
			// IF THE FILE 'film.xml' DOES NOT EXIST CREATE A NEW DOCUMENT AND NEW ROOT
			document = new Document();
			root = new Element("films");
		}

		// GENERATE RANDOM NUMBER FOR FILM ID
		Random rand = new Random();
		int n = rand.nextInt(1000);

		Element film = new Element("film");
		film.setAttribute(new Attribute("id", Integer.toString(n)));
		film.addContent(new Element("title").setText(title));
		film.addContent(new Element("genre").setText(genre));
		film.addContent(new Element("description").setText(description));
		film.addContent(new Element("start").setText(start));
		film.addContent(new Element("end").setText(end));
		film.addContent(new Element("date").setText(date));
		root.addContent(film);
		document.setContent(root);

		try {
			FileWriter writer = new FileWriter("film.xml");
			XMLOutputter outputter = new XMLOutputter();

			outputter.setFormat(Format.getPrettyFormat());
			outputter.output(document, writer);
			// outputter.output(document, System.out);

			// CLOSE FILE 'film.xml'
			writer.close();

		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}