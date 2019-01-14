package edu.kit.tm.cm.backend.application.services;

import com.google.common.base.Charsets;
import com.google.common.io.ByteSource;
import com.lemmingapex.trilateration.NonLinearLeastSquaresSolver;
import com.lemmingapex.trilateration.TrilaterationFunction;
import edu.kit.tm.cm.backend.domain.model.*;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;


@Service
public class IndoorNavigationService {




    public List<Building> getBuildings() {
        String text = getJSONFromFacilityManagerByURL("http://localhost:8080/building");

        List<Building> list = convertToBuildings(text);

        return list;
    }



    public Building getBuildingByID(Long id) {
        String text = getJSONFromFacilityManagerByURL("http://localhost:8080/building/" + id);

        Building op = convertToBuilding(text);
        
        return op;
    }






    public Building  getBuildingByBeaconID(Long id) {
        String text = getJSONFromFacilityManagerByURL("http://localhost:8080/building/beacon/" + id);

        Building op = convertToBuilding(text);

        return op;
    }





    private List<Building> convertToBuildings(String text) {
        ArrayList<Building> list = new ArrayList<Building>();
        JSONObject jsonAsArray = new JSONObject(text);
        JSONArray array = (JSONArray) jsonAsArray.get("buildings");
        for (int i = 0; i < array.length(); i++) {
            JSONObject json = (JSONObject) array.get(i);
            list.add(convertToBuilding(json.toString()));
        }
        return list;
    }

    private Building convertToBuilding(String text) {

        Building result;

        ArrayList<Floor> floors;
        ArrayList<POI> pois;

        JSONObject json = new JSONObject(text);
        JSONArray poisJson = json.getJSONArray("pois");
        JSONArray floorsJson = json.getJSONArray("floors");
        JSONObject building = json.getJSONObject("building");

        result = extractBuildingFromJSON(building);
        floors = extractFloorsFromJSON(floorsJson);
        pois = extractPoisFromJSON(poisJson);
        result.setPois(pois);
        result.setFloors(floors);

        return result;
    }

    private Building extractBuildingFromJSON(JSONObject building) {
        Building result = new Building();
        ArrayList<double[]> coordinates = new ArrayList<double[]>();
        JSONObject buildingProperties = building.getJSONObject("properties");
        JSONObject buildingGeometry = building.getJSONObject("geometry");
        result.setGeometryType(buildingGeometry.getString("type"));
        JSONArray coordinatesArray2 = buildingGeometry.getJSONArray("coordinates");
        JSONArray coordinatesArray = coordinatesArray2.getJSONArray(0);
        for (int i = 0; i < coordinatesArray.length(); i++) {
            JSONArray jsonArray = (JSONArray) coordinatesArray.get(i);
            double[] tr = {jsonArray.getDouble(0), jsonArray.getDouble(1)};
            coordinates.add(tr);
        }
        result.setCoordinates(coordinates);
        result.setId(buildingProperties.getLong("id"));
        result.setCampusId(buildingProperties.getInt("campusId"));
        result.setName(buildingProperties.getString("name"));

        return result;
    }

    private ArrayList<POI> extractPoisFromJSON(JSONArray poisJson) {
        ArrayList<POI> pois = new ArrayList<POI>();

        for (int i = 0; i < poisJson.length(); i++) {
            POI poi = new POI();
            JSONObject jsonObj = poisJson.getJSONObject(i);
            JSONObject geometry = jsonObj.getJSONObject("geometry");
            JSONArray cascader = geometry.getJSONArray("coordinates");
            JSONObject properties = jsonObj.getJSONObject("properties");

            poi.setTitle(properties.getString("title"));
            poi.setId(properties.getLong("id"));
            poi.setPoiId(properties.getInt("poiId"));
            poi.setIdentifier(properties.getString("identifier"));
            poi.setCampusId(properties.getInt("campusId"));

            poi.setBuildingName(properties.getString("buildingName"));
            poi.setFloorId(properties.getInt("floorId"));
            poi.setFloorName(properties.getString("floorName"));
            poi.setzLevel(properties.getInt("zLevel"));

            ArrayList<double[]> coordinates = new ArrayList<double[]>();

            JSONArray coordinatesArray = cascader.getJSONArray(0);
            System.out.println(poi.getPoiId());
            for (int k = 0; k < coordinatesArray.length(); k++) {
                JSONArray jsonArray = (JSONArray) coordinatesArray.get(k);
                double[] tr = {jsonArray.getDouble(0), jsonArray.getDouble(1)};
                coordinates.add(tr);
            }


            ArrayList<String> names = new ArrayList<String>();
            JSONArray namesJson = properties.getJSONArray("names");
            for (int k = 0; k < namesJson.length(); k++) {
                names.add((String) namesJson.get(k));
            }

            JSONArray doorsJson = properties.getJSONArray("doors");
            ArrayList<double[]> doors = new ArrayList<>();

            for (int k = 0; k < doorsJson.length(); k++) {
                JSONArray jsonArray = (JSONArray) doorsJson.get(k);
                double[] tr = {jsonArray.getDouble(0), jsonArray.getDouble(1), jsonArray.getDouble(2)};
                doors.add(tr);
            }


            poi.setCoordinates(coordinates);
            poi.setNames(names);
            poi.setDoor(doors);
            pois.add(poi);


        }

        return pois;
    }

    private ArrayList<Floor> extractFloorsFromJSON(JSONArray floorsJson) {
        ArrayList<Floor> floors = new ArrayList<Floor>();

        for (int i = 0; i < floorsJson.length(); i++) {
            JSONObject jsonObj = floorsJson.getJSONObject(i);
            JSONObject properties = jsonObj.getJSONObject("properties");
            Floor floor = new Floor();
            floor.setId(properties.getLong("id"));
            floor.setName(properties.getString("name"));
            floor.setBuildingId(properties.getLong("buildingId"));
            floor.setZ(properties.getInt("z"));
            floor.setCampusId(properties.getInt("campusId"));
            floors.add(floor);
        }

        return floors;
    }

    private String getJSONFromFacilityManagerByURL(String url) {
        String charset = "UTF-8";  // Or in Java 7 and later, use the constant: java.nio.charset.StandardCharsets.UTF_8.name()
        URLConnection connection = null;
        try {
            connection = new URL(url).openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        connection.setRequestProperty("Accept-Charset", charset);
        InputStream response = null;
        try {
            response = connection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream finalResponse = response;
        ByteSource byteSource = new ByteSource() {
            @Override
            public InputStream openStream() throws IOException {
                return finalResponse;
            }
        };

        String text = "";
        try {
            text = byteSource.asCharSource(Charsets.UTF_8).read();
            System.err.print(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }


    public Position getPositionByBeaconSignals(String beaconsSignals) {
        Position response = new Position();
        JSONObject beaconSignals = new JSONObject(beaconsSignals);
        ArrayList<Beacon> beacons = extractBeaconsFromJSON(beaconSignals);
        ArrayList<double[]> beaconPositions = new ArrayList<>();
        ArrayList<Double> rssiValues = new ArrayList<Double>();
        for (int i = 0; i < beacons.size(); i++) {
            Beacon beacon = beacons.get(i);
            beaconPositions.add(beacon.getPosition());
            rssiValues.add(beacon.getRssi());
        }
        double[][] positions = new double[30][];

        double[] distances = new double[30];


        for (int i = 0; i < beacons.size(); i++) {
            positions[i] = beaconPositions.get(i);
            distances[i] = rssiValues.get(i);
        }

        positions = new double[][]{{4.0, 5.0}, {10.0, 4.5}, {8, 10.0}};
        distances = new double[]{5.0, 3.0, 3.0};

        NonLinearLeastSquaresSolver solver = new NonLinearLeastSquaresSolver(new TrilaterationFunction(positions, distances), new LevenbergMarquardtOptimizer());
        LeastSquaresOptimizer.Optimum optimum = solver.solve();

        // the answer
        double[] centroid = optimum.getPoint().toArray();

        response.setCoordinates(centroid);
        for (int i = 0; i < centroid.length; i++) {
            System.out.println(centroid[i]);
        }

        // error and geometry information; may throw SingularMatrixException depending the threshold argument provided
        RealVector standardDeviation = optimum.getSigma(0);
        RealMatrix covarianceMatrix = optimum.getCovariances(0);


        return response;
    }

    private ArrayList<Beacon> extractBeaconsFromJSON(JSONObject beaconSignals) {
        //extract beacons signals and names from the JSON
        //search Beacons by their names
        //add the rssi values to beacons
        //classing the beacons in their rangeClasses
        // 0 - 40 klasse 1
        // 40 -70 klasse 2
        // 70 - 100 klasse 3
        // return them as List of Beacons

       /* ArrayList<Beacon> beacons = new ArrayList<Beacon>();
        for (int i = 0; i < beaconSignals.length(); i++) {
            JSONObject jsonObj = beaconSignals.getJSONObject(i);
            Beacon beacon = new Beacon();
            beacon.setId(jsonObj.getString("id"));  //id has to be String
            beacon.setName(jsonObj.getString("name"));
            beacon.setRssi(jsonObj.getLong("rssi"));
            beacons.add(beacon);
        }

        return beacons;*/

        return null;
    }

    private double getDistance(int rssi) {
        int txPower = -62; //hard coded power value. Usually ranges between -59 to -65

        if (rssi == 0) {
            return -1.0;
        }

        var ratio = rssi * 1.0 / txPower;
        if (ratio < 1.0) {
            return Math.pow(ratio, 10);
        } else {
            double distance = (0.89976) * Math.pow(ratio, 7.7095) + 0.111;
            return distance;
        }
    }
}
