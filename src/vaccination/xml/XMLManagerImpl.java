package vaccination.xml;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import vaccination.ifaces.XMLManager;
import vaccination.pojos.Doctor;



public class XMLManagerImpl implements XMLManager {
	
		
	@Override
	public void doctor2Xml(Doctor d) {
		try {
			// Create the JAXBContext
			JAXBContext jaxbContext = JAXBContext.newInstance(Doctor.class);
			// Get the marshaller
			Marshaller marshaller = jaxbContext.createMarshaller();
			// Pretty formatting
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			File file = new File("./xmls/Patients.xml");
			marshaller.marshal(d, file);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Doctor xml2Doctor(File xml) {
		
		try {
			// Create the JAXBContext
			JAXBContext jaxbC = JAXBContext.newInstance(Doctor.class);
			// Create the JAXBMarshaller
			Unmarshaller jaxbU = jaxbC.createUnmarshaller();
			// Create the object by reading from a file
			Doctor doctor = (Doctor) jaxbU.unmarshal(xml);
			
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public void doctor2Html(String sourcePath, String xsltPath,String resultDir) {
		TransformerFactory tFactory = TransformerFactory.newInstance();
		try {
			Transformer transformer = tFactory.newTransformer(new StreamSource(new File(xsltPath)));
			transformer.transform(new StreamSource(new File(sourcePath)),new StreamResult(new File(resultDir)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	

}