package myapk.asm3;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpHandler {
    public static String loginEmail = "";
    static String URL = "http://192.168.55.107:8888";

    //POST
    public static String postRequestLogin(String email, String password){
        String status = "";
        try {
            // Step 1 - prepare the connection
            URL url = new URL(URL + "/authentication");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            // Step 2 - prepare the JSON object
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", email);
            jsonObject.put("password", password);
            // Step 3 - Writing data to the web service
            try (DataOutputStream os = new DataOutputStream(conn.getOutputStream())) {
                os.writeBytes(jsonObject.toString());
                os.flush();
            }
            // Step 4 - Read the response code and message
            int responseCode = conn.getResponseCode();
            String responseMessage = conn.getResponseMessage();
            // Handle the response appropriately
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Successfully connected
                loginEmail = email;
                status = "Success: " + responseMessage;
            } else {
                // Handle other response codes
                status = "Error - " + responseCode + ": " + responseMessage;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            status = "Error - MalformedURLException: " + e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            status = "Error - IOException: " + e.getMessage();
        } catch (JSONException e) {
            e.printStackTrace();
            status = "Error - JSONException: " + e.getMessage();
        }
        return status;
    }

    public static String postRequestLoginByGmail(String email){
        String status = "";
        try {
            // Step 1 - prepare the connection
            URL url = new URL(URL + "/authenticationemail");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            // Step 2 - prepare the JSON object
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", email);
            // Step 3 - Writing data to the web service
            try (DataOutputStream os = new DataOutputStream(conn.getOutputStream())) {
                os.writeBytes(jsonObject.toString());
                os.flush();
            }
            // Step 4 - Read the response code and message
            int responseCode = conn.getResponseCode();
            String responseMessage = conn.getResponseMessage();
            // Handle the response appropriately
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Successfully connected
                loginEmail = email;
                status = "Success: " + responseMessage;
            } else {
                // Handle other response codes
                status = "Error - " + responseCode + ": " + responseMessage;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            status = "Error - MalformedURLException: " + e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            status = "Error - IOException: " + e.getMessage();
        } catch (JSONException e) {
            e.printStackTrace();
            status = "Error - JSONException: " + e.getMessage();
        }
        return status;
    }

    public static String postInterests(String email, int phone, String name, String password, String description, int age, String interest, String gender, String partner, String programs){
        String status = "";
        try {
            // Step 1 - prepare the connection
            URL url = new URL(URL + "/register");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            // Step 2 - prepare the JSON object
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", email);
            jsonObject.put("phone", phone);
            jsonObject.put("password", password);
            jsonObject.put("name", name);
            jsonObject.put("description", description);
            jsonObject.put("age", age);
            jsonObject.put("interest", interest);
            jsonObject.put("gender", gender);
            jsonObject.put("partner", partner);
            jsonObject.put("program", programs);
            // Step 3 - Writing data to the web service
            try (DataOutputStream os = new DataOutputStream(conn.getOutputStream())) {
                os.writeBytes(jsonObject.toString());
                os.flush();
            }
            // Step 4 - Read the response code and message
            int responseCode = conn.getResponseCode();
            String responseMessage = conn.getResponseMessage();
            // Handle the response appropriately
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Successfully connected
                status = "Success: " + responseMessage;
            } else {
                // Handle other response codes
                status = "Error - " + responseCode + ": " + responseMessage;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            status = "Error - MalformedURLException: " + e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            status = "Error - IOException: " + e.getMessage();
        } catch (JSONException e) {
            e.printStackTrace();
            status = "Error - JSONException: " + e.getMessage();
        }
        return status;
    }

    public static String getMyInfo(String email) {
        StringBuilder builder = new StringBuilder();
        try {
            // Step 1 - prepare the connection
            URL url = new URL(URL + "/getProfile");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");

            // Step 2 - prepare the JSON object
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", email);

            // Step 3 - Writing data to the web service
            try (DataOutputStream os = new DataOutputStream(conn.getOutputStream())) {
                os.writeBytes(jsonObject.toString());
                os.flush();
            }

            // Step 4 - Read the response code and message
            int responseCode = conn.getResponseCode();
            String responseMessage = conn.getResponseMessage();

            // Handle the response appropriately
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Successfully connected
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            } else {
                // Handle other response codes
                builder.append("Error - ").append(responseCode).append(": ").append(responseMessage);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            builder.append("Error - MalformedURLException: ").append(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            builder.append("Error - IOException: ").append(e.getMessage());
        } catch (JSONException e) {
            e.printStackTrace();
            builder.append("Error - JSONException: ").append(e.getMessage());
        }

        return builder.toString();
    }

}
