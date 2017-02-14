Feature: To check deletion of a booking


  Scenario: Check the deletion of booking (Adds a booking and deletes the same booking)
    Given I am on the bookings page
    When I create a booking with following details
    | firstName | lastName | price | deposit | checkIn    | checkOut   |
    | Phantom   | Js       | 100   | true    | 2017-02-26 | 2017-02-28 |
    | A         | Js       | 100   | true    | 2017-02-26 | 2017-02-28 |
    When I submit the create booking button
    Then i verify the details of booking are added to the system
    When I delete the booking
    Then I verify that the booking is deleted


  Scenario: Check the deletion of existing booking
    Given I am on the bookings page
    When I delete the booking with below details
      | firstName | lastName | price | deposit | checkIn    | checkOut   |
      | Phan      | Js       | 100   | true    | 2017-02-26 | 2017-02-28 |
      | tom       | Js       | 100   | true    | 2017-02-26 | 2017-02-28 |
    Then I verify that the booking is deleted