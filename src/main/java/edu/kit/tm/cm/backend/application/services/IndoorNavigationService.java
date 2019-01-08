package edu.kit.tm.cm.backend.application.services;

import com.google.common.base.Charsets;
import com.google.common.io.ByteSource;
import edu.kit.tm.cm.backend.domain.model.Building;
import org.springframework.stereotype.Service;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


@Service
public class IndoorNavigationService {




    public List<Building> getBuildings() {
        String text = getJSONFromFacilityManagerByURL("localhost:8080/buildings");

        List<Building> list = convertToBuildings(text);

        return list;
    }



    public Building getBuildingByID(Long id) {
        String text = getJSONFromFacilityManagerByURL("localhost:8080/building/"+id);

        Building op = convertToBuilding(text);
        
        return op;
    }






    public Building  getBuildingByBeaconID(Long id) {
        String text = getJSONFromFacilityManagerByURL("localhost:8080/building/beacon/"+id);

        Building op = convertToBuilding(text);

        return op;
    }





    private List<Building> convertToBuildings(String text) {
        return new ArrayList<Building>();
    }

    private Building convertToBuilding(String text) {
        return new Building();
    }

    private String getJSONFromFacilityManagerByURL(String urle) {
        String url = "http://localhost:8080/building/45245";
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
