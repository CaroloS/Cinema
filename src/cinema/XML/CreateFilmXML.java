package cinema.XML;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import org.jdom2.Attribute;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class CreateFilmXML extends CreateXML {

	//DECLARES INSTANCE VARIABLES TO WRITE TO XML
	private String title, genre, description, start, length, date, image, rating;


	//CALLS THE PARENT CONSTRUCTOR 
	public CreateFilmXML(String inputFile, String rootElement) {
		super(inputFile, rootElement);
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

	public String getLength() {
		return length;
	}

	public void setLength(String end) {
		this.length = length;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

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
		film.addContent(new Element("start").setText(start));
		film.addContent(new Element("length").setText("1 hour"));
		film.addContent(new Element("date").setText(date));
		film.addContent(new Element("image").setText(image));
		film.addContent(new Element("rating").setText(rating));
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
			e.printStackTrace();
		}
	}

}
