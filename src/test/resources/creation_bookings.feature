Feature: To check the creation of a booking

  Scenario: Check the creation of booking
    Given I am on the bookings page
    When I create a booking with following details
      | firstName | lastName | price | deposit | checkIn    | checkOut   |
      | Phan      | Js       | 100   | true    | 2017-02-26 | 2017-02-28 |
      | tom       | Js       | 100   | true    | 2017-02-26 | 2017-02-28 |
    Then i verify the details of booking are added to the system