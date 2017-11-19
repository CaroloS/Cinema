package uk.ac.ucl.coursework.xml;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class ReadXMLFile {
	
	/*
	public static void main(String[] args) {

	  SAXBuilder builder = new SAXBuilder();
	  File xmlFile = new File("film.xml");

	  try {

		Document document = (Document) builder.build(xmlFile);
		Element rootNode = document.getRootElement();
		List list = rootNode.getChildren("film");

		for (int i = 0; i < list.size(); i++) {

		   Element node = (Element) list.get(i);

		   Attribute filmID = node.getAttribute("id");
		   String filmTitle = node.getChildText("title");
		   String genre = node.getChildText("genre");
		   String description = node.getChildText("description");
		   String startTime = node.getChildText("start");
		   String endTime = node.getChildText("end");
		   String date = node.getChildText("date");
		   
		   System.out.println(filmID + " " + filmTitle + " " + genre + " " + description + " " + startTime + " " + endTime + " " + date);

		}

	  } catch (IOException io) {
		System.out.println(io.getMessage());
	  } catch (JDOMException jdomex) {
		System.out.println(jdomex.getMessage());
	  }
	}
	
	*/
}
