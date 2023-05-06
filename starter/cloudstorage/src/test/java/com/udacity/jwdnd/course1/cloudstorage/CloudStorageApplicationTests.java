package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.note.model.NotePage;
import com.udacity.jwdnd.course1.cloudstorage.credential.model.CredentialPage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.time.Duration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

    public String baseURL;

    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.firefoxdriver().setup();
    }

    @AfterEach
    public void afterEach() {
        if (driver != null && ((RemoteWebDriver) driver).getSessionId() != null) {
            try {
                driver.quit();
            }
            catch (Exception e) {
                System.out.println("Failed to close web driver.");
            }
        }
    }

    @BeforeEach
    public void beforeEach() {
        baseURL = "http://localhost:" + port;
        driver = new FirefoxDriver();
    }

	@Test
	public void assertHomePageNotAccessibleWithoutLoggingin() {
		driver.get(baseURL + "/home");
		Assertions.assertNotEquals("Home", driver.getTitle());
	}

	@Test
	public void getLoginPage() {
		driver.get(baseURL + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
    public void userSignupAndAccessValidation() {
		doMockSignUp("Access", "Test", "AT", "Test@123");
        doLogIn("AT", "Test@123");

		driver.get(baseURL + "/home");
		Assertions.assertEquals("Home", driver.getTitle());

        doLogOut();

		driver.get(baseURL + "/home");
		Assertions.assertNotEquals("Home", driver.getTitle());
    }

    @Test
    public void notesCurdTest() {
		WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(2));

		doMockSignUp("Notes", "Test", "NT", "Test@123");
        doLogIn("NT", "Test@123");
		driver.get(baseURL + "/note");
		webDriverWait.until(ExpectedConditions.titleContains("Home"));

        NotePage notePage = new NotePage(driver);

        notePage.addNote("title1", "description1");
		driver.get(baseURL + "/note");
		webDriverWait.until(ExpectedConditions.titleContains("Home"));

        Assertions.assertTrue(notePage.checkIfFirstEntryMatches("title1", "description1"));

        doLogOut();
        doLogIn("NT", "Test@123");
		driver.get(baseURL + "/note");
		webDriverWait.until(ExpectedConditions.titleContains("Home"));

        notePage.editFirstNote("title1", "description1");
		driver.get(baseURL + "/note");
		webDriverWait.until(ExpectedConditions.titleContains("Home"));

        Assertions.assertTrue(notePage.checkIfFirstEntryMatches("title1", "description1"));

        doLogOut();
        doLogIn("NT", "Test@123");
		driver.get(baseURL + "/note");
		webDriverWait.until(ExpectedConditions.titleContains("Home"));

        notePage.deleteFirstNote();
		driver.get(baseURL + "/note");
		webDriverWait.until(ExpectedConditions.titleContains("Home"));

        Assertions.assertTrue(notePage.isViewRowsEmpty());
    }

    @Test
    public void credentialsCurdTest() {
		WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(2));

		doMockSignUp("Credentials", "Test", "CT", "Test@123");
        doLogIn("CT", "Test@123");
		driver.get(baseURL + "/credential");
		webDriverWait.until(ExpectedConditions.titleContains("Home"));

        CredentialPage credentialPage = new CredentialPage(driver);

        credentialPage.addCredential("http://google.com", "user1", "password");
		driver.get(baseURL + "/credential");
		webDriverWait.until(ExpectedConditions.titleContains("Home"));

        Assertions.assertTrue(credentialPage.checkIfFirstEntryMatches("http://google.com", "user1"));

        doLogOut();
        doLogIn("CT", "Test@123");
		driver.get(baseURL + "/credential");
		webDriverWait.until(ExpectedConditions.titleContains("Home"));

        credentialPage.editFirstCredential("http://yahoo.com", "user2", "password");
		driver.get(baseURL + "/credential");
		webDriverWait.until(ExpectedConditions.titleContains("Home"));

        Assertions.assertTrue(credentialPage.checkIfFirstEntryMatches("http://yahoo.com", "user2"));

        doLogOut();
        doLogIn("CT", "Test@123");
		driver.get(baseURL + "/credential");
		webDriverWait.until(ExpectedConditions.titleContains("Home"));

        credentialPage.deleteFirstCredential();
		driver.get(baseURL + "/credential");
		webDriverWait.until(ExpectedConditions.titleContains("Home"));

        Assertions.assertTrue(credentialPage.isViewRowsEmpty());
    }

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(2));
		driver.get(baseURL + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success-msg")));
        String successMsg = driver.findElement(By.id("success-msg")).getText();
        System.out.println(successMsg);
		Assertions.assertTrue(successMsg.contains("You successfully signed up!"));
	}

	
	
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get(baseURL + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(2));

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));
	}

    private void doLogOut()
    {
	    driver.get(baseURL + "/home");
		WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(2));

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout-button")));
		WebElement logoutButton = driver.findElement(By.id("logout-button"));
		logoutButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Login"));
    }

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","Test@123");
		
		// Check if we have been redirected to the log in page.
		Assertions.assertEquals(baseURL + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","Test@123");
		doLogIn("UT", "Test@123");
		
		// Try to access a random made-up URL.
		driver.get(baseURL + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","Test@123");
		doLogIn("LFT", "Test@123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(2));
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));
	}

}
