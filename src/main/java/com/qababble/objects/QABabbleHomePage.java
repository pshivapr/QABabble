package com.qababble.objects;

import com.codesp.framework.*;

import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;

public class QABabbleHomePage {

	private static Selenium page=new Selenium();
	public String qababble = "https://www.meetup.com/";
	private EnvironmentProperties env = new EnvironmentProperties();
	
	@Step
	public void setup() throws Exception {
		env.setParam("node", "http://localhost:5555/wd/hub");
		env.setParam("browser", "chrome");
		env.setParam("baseUrl", qababble);
		env.setParam("version", "59");
		env.setParam("platform", "Windows");
		page.GridLauncher(env.getParam("node"), env.getParam("browser"), env.getParam("baseUrl"));
	}
	
	@Step
	public void Assert(String file) throws Exception {
    	page.utils().createTempFile("./log/"+page.utils().getCurrentDateTime("dd_MM_yyyy")+"/"+page.getEnv()+"/temp/"+file, "./log/"+page.utils().getCurrentDateTime("dd_MM_yyyy")+"/"+page.getEnv()+"/"+file);
		page.utils().compareFiles("./src/test/resources/QABabble/Assertions/"+page.getEnv()+"/"+file, "./log/"+page.utils().getCurrentDateTime("dd_MM_yyyy")+"/"+page.getEnv()+"/"+file);
	}
	
	@Step
	public void AssertFail(String file) throws Exception {
    	page.utils().createTempFile("./log/"+page.utils().getCurrentDateTime("dd_MM_yyyy")+"/"+page.getEnv()+"/temp/"+file, "./log/"+page.utils().getCurrentDateTime("dd_MM_yyyy")+"/"+page.getEnv()+"/"+file);
		page.utils().compareFilesAndFailOnDifference("./src/test/resources/QABabble/Assertions/"+page.getEnv()+"/"+file, "./log/"+page.utils().getCurrentDateTime("dd_MM_yyyy")+"/"+page.getEnv()+"/"+file);
	}
	
	@Step
	public void assertTest(String filename, String locator) throws Exception {
		String fileName=filename.replaceAll(" ", "");
		if(!fileName.endsWith(".txt")) {
			fileName=fileName+".txt";
		}
		page.utils().printToFile(fileName);
		page.WaitForPageToLoad(10);
		page.PrintPageElement(locator);
		AssertFail(fileName);
	}
	
	@Step
	public void login(String email, String pass) throws Exception {
		page.Input("email", email);
		page.Input("password", pass);
		page.ClickElement("@value=Log in");	
	}
	
	@Step
	public void assert_login_failure(String type) throws Exception {
		assertTest(type+".txt", "C_document");
	}
	
	@Step
	public void assert_login_pass(String type) throws Exception {
		page.Assert("element", "profileNav");
		page.Assert("element", "findNavBar");
		assertTest(type+".txt", "findNavBar");
	}
	
    @Attachment
    public byte[] screenCapture() {
    	return page.ScreenshotCapture();
    }
}
