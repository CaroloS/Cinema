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
 * It has instance variables that define all the information about a cinema user (employee or customer).
 * It defines getters and setters for these private variables. It defines a function to write the user
 * information to an XML file using the filename and root passed in the constructor. 
 * @see <code>cinema.shared_view.CreateXML</code>.
 * @author carolinesmith, daianabassi
 *
 */
public class CreateUsersXML extends CreateXML{
	
	//DECLARES INSTANCE VARIABLES TO WRITE TO XML
	private String firstName, lastName, emailAddress, phoneNumber, userProfile, userName, password, profilePic;

	//CALLS THE PARENT CONSTRUCTOR 
	public CreateUsersXML (String inputFile, String rootElement) {
		super(inputFile, rootElement);
	}
	
	// GETTERS AND SETTERS FOR THE PRIVATE VARIABLES
	
	/**
	 * Gets the instance variable profilePic
	 * @return the String profilePic
	 */
	public String getProfilePic() {
			return profilePic;
		}

	/**
	 * Sets the instance variable profilePic
	 * @param profilePic the String to set it to
	 */
	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	/**
	 * Gets the instance variable firstName
	 * @return the String firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Sets the instance variable firstName
	 * @param profilePic the String to set it to
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the instance variable lastName
	 * @return the String LastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the instance variable lastName
	 * @param profilePic the String to set it to
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the instance variable emailAddress
	 * @return the String emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * Sets the instance variable emailAddress
	 * @param profilePic the String to set it to
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	/**
	 * Gets the instance variable phoneNumber
	 * @return the String phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * Sets the instance variable phoneNumber
	 * @param profilePic the String to set it to
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	/**
	 * Gets the instance variable password
	 * @return the String password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the instance variable password
	 * @param profilePic the String to set it to
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Gets the instance variable userProfile
	 * @return the String userProfile
	 */
	public String getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(String userProfile) {
		this.userProfile = userProfile;
	}
	
	/**
	 * Gets the instance variable userName
	 * @return the String userName
	 */
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	

	/**
	 * Creates a new user element and sets the user instance variables as the child elements
	 * of the user element. Sets the user attribute with a random number. 
	 * Adds the new booking element to the document root and sets the root to the document. 
	 * Writes the XML to the file specified in the constructor.
	 */
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
		user.addContent(new Element("profilePic").setText(profilePic));
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
			CinemaMain.LOGGER.warning("Couldn't write to file");
			e.printStackTrace();
		}
	}

}
