package vaccination.xml;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import vaccination.ifaces.PatientManager;
import vaccination.ifaces.XMLManager;
import vaccination.jdbc.ConnectionManager;
import vaccination.jdbc.JDBCPatientManager;
import vaccination.pojos.Doctor;
import vaccination.pojos.Patient;



public class XMLManagerImpl implements XMLManager {
	
		
	@Override
	public void doctor2Xml(Doctor d) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Doctor.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			File file = new File("./xmls/Sample-Doctor.xml");
			marshaller.marshal(d, file);
			marshaller.marshal(d, System.out);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Doctor xml2Doctor(File xml) {
		
		ConnectionManager c = new ConnectionManager();
		PatientManager patientMan = new JDBCPatientManager(c.getConnection());
		
		Doctor doctor=null;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Doctor.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			doctor = (Doctor) unmarshaller.unmarshal(xml);
			for (Patient patient : doctor.getPatients()) {
                patientMan.insertPatient(patient, doctor);
            }
			System.out.println("Patients inserted in the database!");
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return doctor;
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