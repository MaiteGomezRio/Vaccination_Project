package vaccination.ifaces;

import java.io.File;

import vaccination.pojos.Doctor;
public interface XMLManager {
	public void doctor2Xml(Doctor o);
	public Doctor xml2Doctor(File xml);
	public void doctor2Html(String sourcePath, String xsltPath,String resultDir);
}
