package edu.kit.tm.cm.backend.cucumber;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import edu.kit.tm.cm.backend.CucumberIntegrationTest;
import edu.kit.tm.cm.backend.application.controllers.IndoorNavigationController;
import edu.kit.tm.cm.backend.application.dtos.BuildingMapper;
import edu.kit.tm.cm.backend.application.dtos.PositionMapper;
import edu.kit.tm.cm.backend.application.dtos.response.BuildingResponse;
import edu.kit.tm.cm.backend.application.dtos.response.PositionResponse;
import edu.kit.tm.cm.backend.application.services.IndoorNavigationService;
import edu.kit.tm.cm.backend.domain.model.Building;
import edu.kit.tm.cm.backend.domain.model.Position;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

public class RequestBuildingStepDefinitions  extends CucumberIntegrationTest {

    private long beaconID;
    private long buildingID;
    private BuildingResponse bresponse = new BuildingResponse();
    private IndoorNavigationService service = new IndoorNavigationService();
    private BuildingMapper bmapper = new BuildingMapper() {
        @Override
        public BuildingResponse toResponse(Building buildingDto) {
            return null;
        }

        @Override
        public List<BuildingResponse> toResponseList(List<Building> buildingDtos) {
            return null;
        }
    };
    private PositionMapper pmapper = new PositionMapper() {
        @Override
        public PositionResponse toResponse(Position position) {
            return null;
        }
    };
    IndoorNavigationController controller = new IndoorNavigationController(service, bmapper, pmapper);

    @Given("^I have a Beacon ID$")
    public void i_have_a_Beacon_ID() {
        this.beaconID = 65;
    }

    @When("^I search for the building with the Beacon ID$")
    public void i_search_for_the_building_with_the_Beacon_ID() {

        this.bresponse = this.controller.getBuildingByBeaconID(this.beaconID);
    }

    @Then("^Building that Beacon is part of is returned$")
    public void building_that_Beacon_is_part_of_is_returned() {
        assertThat(this.bresponse.getId()).isEqualTo(45245);
    }

    @Given("^I have a Beacon ID that i not in the database$")
    public void i_have_a_Beacon_ID_that_i_not_in_the_database() {
        this.beaconID = 250;
    }

    @Then("^No building is found for that Beacon$")
    public void no_building_is_found_for_that_Beacon() {
        assertThat(this.bresponse.getId()).isEqualTo(null);
    }

    @Given("^I have a Building ID$")
    public void i_have_a_Building_ID() {
        this.buildingID = 45245;
    }

    @When("^I search for the building with the Building ID$")
    public void i_search_for_the_building_with_the_Building_ID() {

        this.bresponse = this.controller.getBuildingByID(this.beaconID);
    }

    @Then("^Building that belongs to the ID is returned$")
    public void building_that_belongs_to_the_ID_is_returned() {
        assertThat(this.bresponse.getId()).isEqualTo(this.buildingID);
    }

    @Given("^I have a Building ID that is not in the database$")
    public void i_have_a_Building_ID_that_is_not_in_the_database() {
        this.buildingID = 452457;
    }

    @Then("^No building is found for the building ID$")
    public void no_building_is_found_for_the_building_ID() {
        assertThat(this.bresponse.getId()).isEqualTo(null);
    }
}
