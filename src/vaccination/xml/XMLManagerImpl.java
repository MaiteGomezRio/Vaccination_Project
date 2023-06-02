package vaccination.xml;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

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
			File file = new File("./xmls/Dogs.xml");
			marshaller.marshal(d, file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Doctor xml2Doctor(File xml) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void doctor2Html(Doctor o) {
		// TODO Auto-generated method stub
		
	}

}