package ua.stepanchuk.bean;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import ua.stepanchuk.annotation.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Component("NasaPicturesClient")
public class NasaPicturesClient {

    public List<String> getAllMarsRoverPhotos() {
        HttpURLConnection con = getConnection();
        String json = getJsonFromConnection(con);
        return getPicturesFromJson(json);
    }

    @SneakyThrows
    private List<String> getPicturesFromJson(String json) {
        JsonNode jsonNode = new ObjectMapper().readTree(json);
        return jsonNode.findValuesAsText("img_src");
    }

    @SneakyThrows
    private String getJsonFromConnection(HttpURLConnection con) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            return in.lines().collect(Collectors.joining());
        }
    }

    @SneakyThrows
    private HttpURLConnection getConnection() {
        URL url = new URL("https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=100&api_key=q6M5cR0PuAgZKIyOFWXoW7bekajUeTYYv8mtxF4L");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        return con;
    }

}
