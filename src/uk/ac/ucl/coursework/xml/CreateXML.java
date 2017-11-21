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

public class CreateXML {

	// STATIC FILM VARIABLES TO PASS TO 'createsFilm()' METHOD
	private String title, genre, description, start, end, date;
	protected String inputFile;
	protected String rootElement;

	public CreateXML(String inputFile, String rootElement) {
		this.inputFile = inputFile;
		this.rootElement = rootElement;
	}

	// GETTERS AND SETTERS FOR THE PRIVATE VARIABLES
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * METHOD TO PASS TO THE 'ADD' BUTTON ON EMPLOYEE HOME PAGE LOADS/PARSES
	 * 'film.XML' IF EXISTS OR CREATES NEW XML FILE ADDS A FILM ELEMENT TO THE
	 * ROOT AND PASSES THE FILM VARIABLES AS THE CHILD ELEMENTS @exception
	 */

	Element root = null;
	Document document = null;

	public void getsRoot() {

		// CHECK IF 'film.xml' EXISTS
		File xmlFile = new File(inputFile);

		if (xmlFile.exists()) {

			// TRY TO LOAD 'film.xml' IF IT EXISTS
			try {

				// CREATE A FILE INPUT STREAM
				FileInputStream fis;
				fis = new FileInputStream(xmlFile);

				// USES SAXBuilder TO PARSE THE FILE, IF IT EXISTS
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

				// GET THE ROOT ELEMENT OF THE DOCUMENT OBJECT 'films'
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
			// IF THE FILE 'film.xml' DOES NOT EXIST CREATE A NEW DOCUMENT AND
			// NEW ROOT
			document = new Document();
			root = new Element(rootElement);
		}

	}

	public void createsFilm() {

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