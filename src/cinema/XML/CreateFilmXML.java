package cinema.XML;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import org.jdom2.Attribute;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import cinema.CinemaMain;

/**
 * This class extends <code>cinema.shared_view.CreateXML</code>.
 * It has instance variables that define all the information of a film. It defines
 * getters and setters for these private variables. It defines a function to write the film 
 * information to an XML file using the filename and root passed in the constructor. 
 * @see <code>cinema.shared_view.CreateXML</code>.
 * @author carolinesmith, daianabassi
 *
 */
public class CreateFilmXML extends CreateXML {

	//DECLARES INSTANCE VARIABLES TO WRITE TO XML
	private String title, genre, description, start, length, dateTimes, image;

	/**
	 * This constructor calls the parent and takes the String name of the 
	 * input file to create or write to, and the String XML root element of the file.
	 * @param inputFile the input file.
	 * @param rootElement the XML root element.
	 */
	public CreateFilmXML(String inputFile, String rootElement) {
		super(inputFile, rootElement);
	}

	// GETTERS AND SETTERS FOR THE PRIVATE VARIABLES
	/**
	 * Gets the instance variable film title
	 * @return the film title String
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the instance variable film title
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Gets the instance variable film genre
	 * @return the genre String
	 */
	public String getGenre() {
		return genre;
	}

	/**
	 * Sets the instance variable film genre
	 * @param genre the genre to set
	 */
	public void setGenre(String genre) {
		this.genre = genre;
	}

	/**
	 * Gets the instance variable film description
	 * @return the film description String
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the instance variable film description
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the instance variable DateTimes
	 * @return the dateTimes String
	 */
	public String getDateTimes() {
		return dateTimes;
	}

	/**
	 * Sets the instance variable dateTimes
	 * @param dateTimes the String datTimes to set
	 */
	public void setDateTimes(String dateTimes) {
		this.dateTimes = dateTimes;
	}
	
	/**
	 * Gets the instance variable image
	 * @return the String image
	 */
	public String getImage() {
		return image;
	}
	
	/**
	 * Sets the instance variable image
	 * @param image the String image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}


	/**
	 * Creates a new film element and sets the film instance variables as the child elements
	 * of the film element. Defines a random number ID attribute. Adds the new film element
	 * to the document root and sets the root to the document. Writes the XML to the file
	 * specified in the constructor.
	 */
	public void createsFilm() {

		// GENERATE RANDOM NUMBER FOR FILM ID
		Random rand = new Random();
		int n = rand.nextInt(1000);
		
		//CREATES A FILM ELEMENT AND SETS THE INSTANCE VARIABLES AS THE CHILD ELEMENTS OF FILM 
		Element film = new Element("film");
		film.setAttribute(new Attribute("id", Integer.toString(n)));
		film.addContent(new Element("title").setText(title));
		film.addContent(new Element("genre").setText(genre));
		film.addContent(new Element("description").setText(description));
		//film.addContent(new Element("start").setText(start));
		film.addContent(new Element("length").setText("1 hour"));
		film.addContent(new Element("dateTimes").setText(dateTimes));
		film.addContent(new Element("image").setText(image));
		root.addContent(film);
		document.setContent(root);

		//WRITE THE XML TO FILE SPECIFIED IN THE CONSTRUCTOR
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
	}
	

	
	/**
	 * Creates initial film elements in film.xml when the program is first run. 
	 * It is called from <code>cinema.CinemaMain</code> with parameters passed for the films
	 * to initially load the program with. 
	 * @param initTitle the String film title
	 * @param initGenre the String film Genre
	 * @param initDescription the String film description
	 * @param initDateTimes the String of dates and times
	 * @param initImage the String image relative path
	 */
	public void createsInitialFilm(String initTitle, String initGenre, String initDescription, String initDateTimes, String initImage) {

		// GENERATE RANDOM NUMBER FOR FILM ID
		Random rand = new Random();
		int n = rand.nextInt(1000);
		
		//CREATES A FILM ELEMENT AND SETS THE INSTANCE VARIABLES AS THE CHILD ELEMENTS OF FILM 
		Element film = new Element("film");
		film.setAttribute(new Attribute("id", Integer.toString(n)));
		film.addContent(new Element("title").setText(initTitle));
		film.addContent(new Element("genre").setText(initGenre));
		film.addContent(new Element("description").setText(initDescription));
		film.addContent(new Element("length").setText("1 hour"));
		film.addContent(new Element("dateTimes").setText(initDateTimes));
		film.addContent(new Element("image").setText(initImage));
		root.addContent(film);
		document.setContent(root);

		//WRITE THE XML TO FILE SPECIFIED IN THE CONSTRUCTOR
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
	}

}
