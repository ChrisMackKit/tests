Feature: Requesting user position

  Scenario: Successfully finding of position through Beacon
    Given I have a list of Beacons with their rssi values
    When I ask for my position in the building
    Then Position of user is returned

  Scenario: failing to find position of user because no Beacon is found
    Given I have an empty list of Beaons with their rssi values
    When I ask for my position in the building
    Then Position of user can not be returned

  Scenario: failing to find position of user because Beacons are not found
    Given I have a list of Beacons with their rssi values that are not in the database
    When I ask for my position in the building
    Then Position of user can not be returned

