package edu.kit.tm.cm.backend.domain.model;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
public class BuildingTests {

    @Test
    public void findZeroFindsSmallestXY() {
        //TODO Coordinates as input and look for smallest X and Y value
        ArrayList<double[]> coordinates = new ArrayList<double[]>();
        coordinates.add(new double[]{8.419357538223267, 49.01448344028011});
        coordinates.add(new double[]{8.419612348079681, 49.01447288522493});
        coordinates.add(new double[]{8.419550657272339, 49.013883557759414});
        coordinates.add(new double[]{8.419389724731445, 49.01379383863784});
        coordinates.add(new double[]{8.41929316520691, 49.013848373809424});
        coordinates.add(new double[]{8.419303894042969,  49.01400142381064});
        coordinates.add(new double[]{8.419319987297058, 49.01417558358448});
        coordinates.add(new double[]{8.419336080551147, 49.0143515019294});

        assertThat(Building.findZero(coordinates)[0]).isEqualTo(8.41929316520691);
        assertThat(Building.findZero(coordinates)[1]).isEqualTo(49.01448344028011);
    }

    @Test
    public void setCoordinatesReturnFormatedCoordinates() {
        ArrayList<double[]> coordinates = new ArrayList<double[]>();
        coordinates.add(new double[]{8.419357538223267, 49.01448344028011});
        coordinates.add(new double[]{8.419612348079681, 49.01447288522493});
        coordinates.add(new double[]{8.419550657272339, 49.013883557759414});
        coordinates.add(new double[]{8.419389724731445, 49.01379383863784});
        coordinates.add(new double[]{8.41929316520691, 49.013848373809424});
        coordinates.add(new double[]{8.419303894042969,  49.01400142381064});
        coordinates.add(new double[]{8.419319987297058, 49.01417558358448});
        coordinates.add(new double[]{8.419336080551147, 49.0143515019294});

        ArrayList<double[]> newCoordinates = Building.newList(coordinates);
        for (int i = 0; i < newCoordinates.size(); i++) {
            System.out.println(newCoordinates.get(i)[0] + " " + newCoordinates.get(i)[1]);
        }
        assertThat(Building.findZero(newCoordinates)[0]).isEqualTo(0);
        //Because the Algo does look for the biggest y value
        double biggestYValue = Building.intoMeter(new double[] {8.419389724731445, 49.01379383863784},
                new double[] {8.41929316520691, 49.01448344028011})[1];
        assertThat(Building.findZero(newCoordinates)[1]).isEqualTo(biggestYValue);
    }
}
