package Users;

import java.io.FileWriter;
import java.io.IOException;

import org.jdom2.Attribute;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import cinema.XML.CreateXML;
import cinema.control.LoginController;

public class CreateUserBookingsXML extends CreateXML {
	
	//DECLARES INSTANCE VARIABLES TO WRITE TO XML
		private String filmName, filmDate, filmTime, seatBooked, userID;

	//	CALLS THE PARENT CONSTRUCTOR 
		public CreateUserBookingsXML (String inputFile, String rootElement) {
			super(inputFile, rootElement);
		}
	
	public String getFilmName() {
			return filmName;
		}

		public void setFilmName(String filmName) {
			this.filmName = filmName;
		}

		public String getFilmDate() {
			return filmDate;
		}


		public void setFilmDate(String filmDate) {
			this.filmDate = filmDate;
		}

		public String getFilmTime() {
			return filmTime;
		}

		public void setFilmTime(String filmTime) {
			this.filmTime = filmTime;
		}

		public String getSeatBooked() {
			return seatBooked;
		}

		public void setSeatBooked(String seatBooked) {
			this.seatBooked = seatBooked;
		}

		public String getUserID() {
			return userID;
		}

		public void setUserID(String userID) {
			this.userID = userID;
		}


	public void CreateUserBooking() {
		
		
		//CREATES A FILM ELEMENT AND SETS THE INSTANCE VARIABLES AS THE CHILD ELEMENTS OF FILM 
				Element booking = new Element("booking");
				booking.setAttribute(new Attribute("userID", LoginController.userID));
				booking.addContent(new Element("filmName").setText(filmName));
				booking.addContent(new Element("filmDate").setText(filmDate));
				booking.addContent(new Element("filmTime").setText(filmTime));
				booking.addContent(new Element("seatBooked").setText(seatBooked));
				root.addContent(booking);
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
