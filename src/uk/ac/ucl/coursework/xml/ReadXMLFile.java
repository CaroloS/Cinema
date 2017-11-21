package uk.ac.ucl.coursework.xml;

import java.io.File;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class ReadXMLFile {

	private String inputFile;
	Document document = null;
	Element rootNode = null;
	

	public ReadXMLFile(String inputFile) {
		this.inputFile = inputFile;
	}

	public Element readsXML() {

		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File(inputFile);

		try {

			document = (Document) builder.build(xmlFile);
			rootNode = document.getRootElement();

		} catch (IOException io) {
			System.out.println(io.getMessage());
		} catch (JDOMException jdomex) {
			System.out.println(jdomex.getMessage());
		}
		
		return rootNode;

	}

}
