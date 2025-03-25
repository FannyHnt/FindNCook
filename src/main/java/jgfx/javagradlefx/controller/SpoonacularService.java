package jgfx.javagradlefx.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class SpoonacularService {
    private String key ="4b292240b23a42c89c878c3baf1ececd";
    private String prefix = "https://api.spoonacular.com/recipes/";

    public void essaie (){
        try {
            // Create a URL object with the API endpoint
            URL url = new URL("https://api.spoonacular.com/recipes/complexSearch?apiKey=4b292240b23a42c89c878c3baf1ececd");

            // Open a connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the request method to GET
            connection.setRequestMethod("GET");

            // Get the response code
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // Read the response
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            // Close the connections
            in.close();
            connection.disconnect();

            // Print the response
            System.out.println(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
