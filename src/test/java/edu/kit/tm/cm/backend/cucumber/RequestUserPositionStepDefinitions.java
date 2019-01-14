package edu.kit.tm.cm.backend.cucumber;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
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

public class RequestUserPositionStepDefinitions {
    private String rssiString;
    private PositionResponse userPosition;

    @Given("^I have a list of Beacons with their rssi values$")
    public void i_have_a_list_of_Beacons_with_their_rssi_values() {
        this.rssiString = "{\"id\":\"A0:E6:F8:77:9C:43\",\"name\":\"B-YckkeQ\",\"rssi\":-81}," +
                "{\"id\":\"A0:E6:F8:5A:C7:CB\",\"name\":\"B-00gAxI\",\"rssi\":-87},{\"id\":\"B0:B4:48:E9:46:85\"," +
                "\"name\":\"B-20.21_Lobby\",\"rssi\":-72}," +
                "{\"id\":\"A0:E6:F8:5A:C7:BC\",\"name\":\"B-sdiAI7\",\"rssi\":-72}";
    }

    @When("^I ask for my position in the building$")
    public void i_ask_for_my_position_in_the_building() {
        IndoorNavigationService service = new IndoorNavigationService();
        BuildingMapper bmapper = new BuildingMapper() {
            @Override
            public BuildingResponse toResponse(Building buildingDto) {
                return null;
            }

            @Override
            public List<BuildingResponse> toResponseList(List<Building> buildingDtos) {
                return null;
            }
        };
        PositionMapper pmapper = new PositionMapper() {
            @Override
            public PositionResponse toResponse(Position position) {
                return null;
            }
        };
        IndoorNavigationController controller = new IndoorNavigationController(service, bmapper, pmapper);
        this.userPosition = controller.getPositionByBeaconSignals(rssiString);
    }

    @Then("^Position of user is returned$")
    public void position_of_user_is_returned() {
        assertThat(this.userPosition.getCoordinates().length).isEqualTo(2);
    }

    @Given("^I have an empty list of Beaons with their rssi values$")
    public void i_have_an_empty_list_of_Beaons_with_their_rrsi_values() {
        this.rssiString = "";
    }

    @Then("^Position of user can not be returned$")
    public void position_of_user_can_not_be_returned() {
        //TODO 404 error? What exactly happens?
    }

    @Given("^I have a list of Beacons with their rssi values that are not in the database$")
    public void i_have_a_list_of_Beacons_with_their_rssi_values_that_are_not_in_the_database() {
        this.rssiString = "{\"id\":\"A0:E6:F8:77:9C:43\",\"name\":\"B-Yck234\",\"rssi\":-81}," +
                "{\"id\":\"A0:E6:F8:5A:C7:CB\",\"name\":\"B-00234\",\"rssi\":-87},{\"id\":\"B0:B4:48:E9:46:85\"," +
                "\"name\":\"B-20.21_234\",\"rssi\":-72}," +
                "{\"id\":\"A0:E6:F8:5A:C7:BC\",\"name\":\"B-sd234\",\"rssi\":-72}";
    }
}
