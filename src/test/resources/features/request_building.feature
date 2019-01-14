Feature: Requesting a Building

 Scenario: Building is requested by beacon and found
    Given I have a Beacon ID
    When I search for the building with the Beacon ID
    Then Building that Beacon is part of is returned

  Scenario: Building is requested by beacon and not found
    Given I have a Beacon ID that i not in the database
    When I search for the building with the Beacon ID
    Then No building is found for that Beacon

  Scenario: Building is requested by buildingID and found
    Given I have a Building ID
    When I search for the building with the Building ID
    Then Building that belongs to the ID is returned

  Scenario: Building is requested by buildingID and not found
    Given I have a Building ID that is not in the database
    When I search for the building with the Building ID
    Then No building is found for the building ID