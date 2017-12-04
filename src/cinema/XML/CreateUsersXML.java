package cinema.XML;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import org.jdom2.Attribute;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;


public class CreateUsersXML extends CreateXML{
	
	//DECLARES INSTANCE VARIABLES TO WRITE TO XML
	private String firstName, lastName, emailAddress, phoneNumber, userProfile, userName, password;

//	CALLS THE PARENT CONSTRUCTOR 
	public CreateUsersXML (String inputFile, String rootElement) {
		super(inputFile, rootElement);
	}



	// GETTERS AND SETTERS FOR THE PRIVATE VARIABLES
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

	public String getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(String userProfile) {
		this.userProfile = userProfile;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public void createUser() {

		// GENERATE RANDOM NUMBER FOR FILM ID
		Random rand = new Random();
		int n = rand.nextInt(1000);
		
		//CREATES A FILM ELEMENT AND SETS THE INSTANCE VARIABLES AS THE CHILD ELEMENTS OF FILM 
		Element user = new Element("User");
		user.setAttribute(new Attribute("id", Integer.toString(n)));
		user.addContent(new Element("FirstName").setText(firstName));
		user.addContent(new Element("LastName").setText(lastName));
		user.addContent(new Element("EmailAddress").setText(emailAddress));
		user.addContent(new Element("PhoneNumber").setText(phoneNumber));
		user.addContent(new Element("UserProfile").setText(userProfile));
		user.addContent(new Element("UserName").setText(userName));
		user.addContent(new Element("Password").setText(password));
		root.addContent(user);
		document.setContent(root);

		//WRITE THE XML TO FILE SPECIFIED IN THE CONSTRUCTOR
		try {
			FileWriter writer = new FileWriter(inputFile);
			XMLOutputter outputter = new XMLOutputter();

			outputter.setFormat(Format.getPrettyFormat());
			outputter.output(document, writer);
			// outputter.output(document, System.out);

			// CLOSE FILE 'users.xml'
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
