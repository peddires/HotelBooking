package step_definitions;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.runtime.java.guice.ScenarioScoped;
import domain.CustomerDetails;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@ScenarioScoped
public class HotelBookingSteps {
    public  WebDriver webDriver = new PhantomJSDriver(DesiredCapabilities.phantomjs());
    public static final String URL = "http://hotel-test.equalexperts.io/";
    public List<CustomerDetails> customerOriginalBookingDetailsList;

    @Given("^I am on the bookings page$")
    public void iAmOnTheBookingPage() {
        webDriver.manage().window().maximize();
        webDriver.get(URL);
        webDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        String expectedPageTitle = "Hotel Booking form";
        String actualTitle = webDriver.getTitle();
        assertThat(actualTitle.equalsIgnoreCase(expectedPageTitle)).overridingErrorMessage("There is some problem not able to access page");

    }

    @When("^I create a booking with following details$")
    public void iEnterFollowingDetails(List<CustomerDetails> customerDetails) throws Throwable {
        this.customerOriginalBookingDetailsList = customerDetails;
        (new WebDriverWait(webDriver, 60)).until(ExpectedConditions.visibilityOfElementLocated(By.id("firstname")));
        assertThat(customerDetails).overridingErrorMessage("Please enter customer details for booking").isNotEmpty();
        customerDetails.stream().forEach(customerDetail -> {
            webDriver.findElement(By.id("firstname")).sendKeys(customerDetail.getFirstName());
            webDriver.findElement(By.id("lastname")).sendKeys(customerDetail.getLastName());
            webDriver.findElement(By.id("totalprice")).sendKeys(customerDetail.getPrice());
            new Select(webDriver.findElement(By.id("depositpaid"))).selectByVisibleText(customerDetail.getDeposit());
            webDriver.findElement(By.id("checkin")).sendKeys(customerDetail.getCheckIn());
            webDriver.findElement(By.id("checkout")).sendKeys(customerDetail.getCheckOut());
            try {
                I_submit_the_create_booking_button();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }
    @When("^I submit the create booking button$")
    public void I_submit_the_create_booking_button() throws Throwable {
        webDriver.findElement(By.cssSelector("input[value=\" Save \"]")).click();
        webDriver.navigate().refresh();
    }

    @Then("^i verify the details of booking are added to the system$")
    public void i_verify_the_details_of_booking_are_added_to_the_system() throws Throwable {

        List<WebElement> expectedRows = findCreatedBooking();
        assertThat(expectedRows).overridingErrorMessage("There are no bookings found").isNotEmpty();
        CustomerDetails expectedCustomerDetails = new CustomerDetails();
        List<CustomerDetails> expectedCustomerBookingDetailsList = new ArrayList<>();
        for (WebElement row : expectedRows)
            System.out.println(row.getText());
        int i = 0;
        for (WebElement row : expectedRows) {
            String[] expectedCustomerDetailFields = row.getText().split("\n");
            assertThat(expectedCustomerDetailFields).isNotEmpty();
            expectedCustomerDetails.setFirstName(expectedCustomerDetailFields[0]);
            expectedCustomerDetails.setLastName(expectedCustomerDetailFields[1]);
            expectedCustomerDetails.setPrice(expectedCustomerDetailFields[2]);
            expectedCustomerDetails.setDeposit(expectedCustomerDetailFields[3]);
            expectedCustomerDetails.setCheckIn(expectedCustomerDetailFields[4]);
            expectedCustomerDetails.setCheckOut(expectedCustomerDetailFields[5]);
            expectedCustomerBookingDetailsList.add(i, expectedCustomerDetails);
            i++;
        }
        assertThat(expectedCustomerBookingDetailsList.size()).isGreaterThanOrEqualTo(customerOriginalBookingDetailsList.size());
    }

    public List<WebElement> findCreatedBooking() throws InterruptedException {

        Thread.sleep(3000);
        WebElement table = webDriver.findElement(By.id("bookings"));
        List<WebElement> rows = table.findElements(By.cssSelector("html body div.container div#bookings div.row"));
        List<WebElement> expectedRows =
                rows.stream()
                        .filter(row -> customerOriginalBookingDetailsList.stream().filter(c ->
                                        row.getText().contains(c.getFirstName()) && row.getText().contains(c.getLastName())).findAny().isPresent()
                        ).collect(Collectors.toList());
        return expectedRows;
    }


    @When("^I delete the booking$")
    public void I_delete_the_booking() throws Throwable {
        List<WebElement> expectedRows = findCreatedBooking();
        assertThat(expectedRows).overridingErrorMessage("The customer record doesnot exist.Enter valid customer booking details to delete the booking").isNotEmpty();
        for (WebElement row : expectedRows)
            row.findElement(By.cssSelector("input[value=\"Delete\"]")).click();

    }

    @Then("^I verify that the booking is deleted$")
    public void I_verify_that_the_booking_is_deleted() throws Throwable {
        List<WebElement> expectedRows = findCreatedBooking();
        assertThat(expectedRows).overridingErrorMessage("Customer booking is not deleted Successfully").isEmpty();
    }

    @When("^I delete the booking with below details$")
    public void I_delete_the_booking_with_below_details(List<CustomerDetails> customerDetails) throws Throwable {
        this.customerOriginalBookingDetailsList = customerDetails;
        I_delete_the_booking();
    }
}

