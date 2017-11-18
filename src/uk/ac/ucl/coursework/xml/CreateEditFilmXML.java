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

public class CreateEditFilmXML {
	public static void main(String[] args) {

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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// GET THE ROOT ELEMENT OF THE DOCUMENT OBJECT 'films'
				root = document.getRootElement();
				try {
					fis.close();
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
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
		film.addContent(new Element("title").setText("Scary Movie"));
		film.addContent(new Element("genre").setText("Thriller"));
		film.addContent(new Element("description").setText("A year after disposing of the body of a man they accidentally killed, a group of dumb teenagers are stalked by a bumbling serial killer."));
		film.addContent(new Element("start").setText("07:00 pm"));
		film.addContent(new Element("end").setText("9:00 pm"));
		film.addContent(new Element("date").setText("20-11-2017"));
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