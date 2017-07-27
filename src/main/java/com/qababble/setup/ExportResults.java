package com.qababble.setup;

import com.codesp.reporting.Reporter;

public class ExportResults {
	
	static Reporter report = new Reporter();

	public static void main(String []args) throws Exception {    
		report.postXMLResults("http://localhost:8086", "QABabble", "Demo", "target/surefire-reports/TEST-TestSuite.xml");
	 }
  
}

