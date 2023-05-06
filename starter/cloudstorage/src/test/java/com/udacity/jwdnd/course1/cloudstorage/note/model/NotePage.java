package com.udacity.jwdnd.course1.cloudstorage.note.model;

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

public class NotePage {

    @FindBy(id = "add-note-button")
    private WebElement addNoteButton;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(id = "note-form-submit-button")
    private WebElement noteFormSubmitButton;

    @FindBy(id = "noteTable")
    private WebElement noteTable;

    private WebDriverWait webDriverWait;

    public NotePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public void addNote(String title, String description) {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-note-button")));
        addNoteButton.click();
        fillAndSubmitForm(title, description);
    }

    public void editFirstNote(String title, String description) {
        clickEditOfEntry(getViewRow(0));
        fillAndSubmitForm(title, description);
    }

    private void fillAndSubmitForm(String title, String description) {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
        noteTitle.click();
        noteTitle.clear();
        noteTitle.sendKeys(title);
        noteDescription.click();
        noteDescription.clear();
        noteDescription.sendKeys(description);
        noteFormSubmitButton.click();
        webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("note-title")));
    }

    public void deleteFirstNote() {
        clickDeleteOfEntry(getViewRow(0));
    }

    private List<WebElement> getViewRows() {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteTable")));
        return noteTable.findElement(By.tagName("tbody")).findElements(By.xpath("./child::*"));
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

    private boolean checkIfEntryMatches(WebElement row, String title, String description) {
        List<WebElement> cols = row.findElements(By.xpath("./child::*"));
        return cols.get(2).getText().equals(title) && cols.get(3).getText().equals(description);
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

    public boolean checkIfFirstEntryMatches(String title, String description) {
        return checkIfEntryMatches(getViewRow(0), title, description);
    }

}
