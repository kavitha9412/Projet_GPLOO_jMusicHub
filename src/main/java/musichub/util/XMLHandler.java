package musichub.util;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.SAXException;
import org.apache.log4j.Logger;
import org.w3c.dom.*;
import java.io.IOException;
import java.io.Serializable;
import java.io.File;



public class XMLHandler  implements Serializable{
	
	private static Logger logger = Logger.getLogger(XMLHandler.class);
	TransformerFactory transformerFactory;
	Transformer transformer;
	DocumentBuilderFactory documentFactory;
	DocumentBuilder documentBuilder;

	public XMLHandler() {
		try {
			transformerFactory = TransformerFactory.newInstance();
			transformer = transformerFactory.newTransformer();
			documentFactory = DocumentBuilderFactory.newInstance();
			documentBuilder = documentFactory.newDocumentBuilder();
		} catch (TransformerException tfe) {
			logger.error("Error - {}",tfe);
        } catch (ParserConfigurationException pce) {
        	logger.error("Error - {}",pce);
        }
	}
	
	public void createXMLFile(Document document, String filePath)
	{
		try {
		// create the xml file
        //transform the DOM Object to an XML File
		DOMSource domSource = new DOMSource(document);
		StreamResult streamResult = new StreamResult(new File(filePath));

		// If you use
		// StreamResult result = new StreamResult(System.out);
		// the output will be pushed to the standard output ...
		// You can use that for debugging 

		transformer.transform(domSource, streamResult);
		
		} catch (TransformerException tfe) {
			logger.error("Error - {}",tfe);
        }
	}
	
	public Document createXMLDocument()
	{
		return documentBuilder.newDocument();
	}		
	
	public NodeList parseXMLFile (String filePath) {
		NodeList elementNodes = null;
		try {
			Document document= documentBuilder.parse(new File(filePath));
			Element root = document.getDocumentElement();
			
			elementNodes = root.getChildNodes();	
		}
		catch (SAXException e) 
		{
			logger.error("Error - {}",e);
		}
		catch (IOException e) 
		{
			logger.error("Error - {}",e);
		}
		return elementNodes;
	}
	
	

}