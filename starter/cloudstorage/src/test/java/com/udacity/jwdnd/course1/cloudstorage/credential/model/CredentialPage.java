package com.udacity.jwdnd.course1.cloudstorage.credential.model;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;
import java.time.Duration;

public class CredentialPage {

    @FindBy(id = "add-credential-button")
    private WebElement addCredentialButton;

    @FindBy(id = "credential-url")
    private WebElement credentialUrl;

    @FindBy(id = "credential-username")
    private WebElement credentialUsername;

    @FindBy(id = "credential-password")
    private WebElement credentialPassword;

    @FindBy(id = "credential-form-submit-button")
    private WebElement credentialSubmit;

    @FindBy(id = "credentialTable")
    private WebElement credentialTable;

    private WebDriverWait webDriverWait;

    public CredentialPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public void addCredential(String url, String userName, String password) {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-credential-button")));
        addCredentialButton.click();
        fillAndSubmitForm(url, userName, password);
    }

    public void editFirstCredential(String url, String userName, String password) {
        clickEditOfEntry(getViewRow(0));
        fillAndSubmitForm(url, userName, password);
    }

    private void fillAndSubmitForm(String url, String userName, String password) {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
        credentialUrl.click();
        credentialUrl.clear();
        credentialUrl.sendKeys(url);
        credentialUsername.click();
        credentialUsername.clear();
        credentialUsername.sendKeys(userName);
        credentialPassword.click();
        credentialPassword.clear();
        credentialPassword.sendKeys(password);
        credentialSubmit.click();
        webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("credential-url")));
    }

    public void deleteFirstCredential() {
        clickDeleteOfEntry(getViewRow(0));
    }

    private List<WebElement> getViewRows() {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTable")));
        return credentialTable.findElement(By.tagName("tbody")).findElements(By.xpath("./child::*"));
    }

    public static class DataDoesNotExistException extends RuntimeException {
    }

    private void clickEditOfEntry(WebElement row) {
        List<WebElement> cols = row.findElements(By.xpath("./child::*"));
        cols.get(0).findElement(By.tagName("button")).click();
    }

    private void clickDeleteOfEntry(WebElement row) {
        List<WebElement> cols = row.findElements(By.xpath("./child::*"));
        cols.get(1).findElement(By.tagName("form")).findElement(By.tagName("button")).click();
    }

    private boolean checkIfEntryMatches(WebElement row, String url, String userName) {
        List<WebElement> cols = row.findElements(By.xpath("./child::*"));
        return cols.get(2).getText().equals(url) && cols.get(3).getText().equals(userName);
    }

    private WebElement getViewRow(int index) {
        List<WebElement> rows = getViewRows();
        if (rows.isEmpty() || index >= rows.size()) {
            throw new DataDoesNotExistException();
        }
        return rows.get(index);
    }

    public boolean isViewRowsEmpty() {
        return getViewRows().isEmpty();
    }

    public boolean checkIfFirstEntryMatches(String url, String userName) {
        return checkIfEntryMatches(getViewRow(0), url, userName);
    }

}
