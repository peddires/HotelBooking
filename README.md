# HotelBookin
***********PREREQUISITE********************

Project Setup: 

Must have the following JDK installed in order to build the project : Java 1.8
Must have one of the build tools installed to build the project : Ex; Gradle 2.5 (this test is generated with)
Must have one of the JAVA IDEs  to build and run the tests. ex: IntelliJ  14 (this test is generated with)
   required plugins (cucumber for java) 
Must have the following Jar dependencies set up in your build tool to run the tests
  ‘org.assertj:assertj-core:2.0.0'  'org.apache.httpcomponents:httpclient:4.5.2'  "info.cukes:cucumber-java:1.2.2”
'org.seleniumhq.selenium', name: 'selenium-java', version: '3.0.1' 



Running tests:

  The cucumber feature (.feature) files can be run directly from IDE (with cucumber plugin specific to IDE) with default configuration.  (Right click on the directory of the features and Run all Features in <src/test/resources>/run individual feature files.

Brief Summary on tests conducted :

Have done tests to create and delete multiple bookings with input data feeding from BDD.
Tests conducted for both single and multiple customer bookings.
Booking system is not tested for different browsers (concentrated only on FireFox)
Tests not covered to validate the input fields.
Tests not covered to check for unique booking details(per customer)
tested for deleting any existing booking from the system (data needs to be feed into feature file).

Author Comments:

  There are two feature files one for Create Bookings  and other for Delete Bookings. 
  Each file contains one acceptance tests for each Create and Delete bookings.
  There are 2 java files. One Step definitions class (HotelBookingSteps) and one Domain class (CustomerDetails.java ) for representing all customer details for bookings.
 



