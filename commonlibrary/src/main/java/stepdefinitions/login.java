package stepdefinitions;

import java.io.IOException;

import web.Ui;

public class login {
	
	Ui ui = new Ui();
	
	public void LaunchBrowserToTest(String browser, String remoteURL) throws Exception {
		ui.initialize(browser, remoteURL);
	}
	
	public void LoginApplicationWithUsernameAndPassword(String url, String username, String password) throws IOException {
		ui.navigateToUrl(url);
		ui.sendKeys("//label[contains(text(),'User name:')]//preceding-sibling::input", "xpath", username);
		ui.sendKeys("//label[contains(text(),'Password:')]//preceding-sibling::input", "xpath", password);
		ui.click("//button[@class='primary-btn']", "xpath");
		ui.waitForElementToBeDisplayed("//main[@class='container-fluid main-content']", "xpath", "20");
	}
	
	public String VerifyUserHasLoggedIn(String username) throws IOException {
		if (ui.isElementDisplayed("//h1[contains(text(),'"+username+" - Indents')]", "xpath")) {
			return "User "+username+" has logged In";
		} else {
			return "Login failed for "+username;
		}
	}
	
	public String GetDefaultDepotName() throws IOException {
		return ui.getText("//span[starts-with(@class,'tag-default')]/span", "xpath");
	}
	
	public void LogoutTheApplication() throws IOException {
		ui.click("//span[@class='pointer']", "xpath");
		ui.click("//a[contains(text(),'LOG OUT')]", "xpath");
	}
}
