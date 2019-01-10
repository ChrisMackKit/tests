package edu.kit.tm.cm.backend.application.services;

import com.google.common.base.Charsets;
import com.google.common.io.ByteSource;
import edu.kit.tm.cm.backend.domain.model.Building;
import edu.kit.tm.cm.backend.domain.model.Floor;
import edu.kit.tm.cm.backend.domain.model.POI;
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
        return new ArrayList<Building>();
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
        JSONArray coordinatesArray = buildingGeometry.getJSONArray("coordinates");
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
            /*
            *   "properties": {
                "title": "022",
                "id": 1,
                "poiId": 1,
                "identifier": "022",
                "campusId": 1,
                "floorId": 1,
                "zLevel": 1,
                "floorName": "EG",
                "buildingName": "50.30.",
                "description": null,
              }
            * */

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
            /*"properties": {
                "id": 1,
                "name": "EG",
                "buildingId": 1,
                "campusId": 1,
                "z": 1
            }*/
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




}
