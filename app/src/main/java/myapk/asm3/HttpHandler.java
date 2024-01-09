package myapk.asm3;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpHandler {
    public static String loginEmail = "";
//    static String URL = "http://192.168.55.107:8888";
//    static String URL = "http://192.168.155.62:8888";
    static String URL = "https://asm3android-a0efc67bf4a3.herokuapp.com";
    private static final String BOUNDARY = "*****";


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

    public static String postInterests(String email, int phone, String name, String password, String description, int age, String interest, String gender, String partner, String programs, String imagePath) {
        String status = "";

        try {
            // Step 1 - prepare the connection
            URL url = new URL(URL + "/register");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + BOUNDARY);
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            // Step 2 - prepare the request body
            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            writeFormField("email", email, os);
            writeFormField("phone", String.valueOf(phone), os);
            writeFormField("password", password, os);
            writeFormField("name", name, os);
            writeFormField("description", description, os);
            writeFormField("age", String.valueOf(age), os);
            writeFormField("interest", interest, os);
            writeFormField("gender", gender, os);
            writeFormField("partner", partner, os);
            writeFormField("program", programs, os);

            // Attach image if imagePath is provided
            if (imagePath != null) {
                File imageFile = new File(imagePath);
                writeImageFile(imageFile, os);
            }

            // Close the request body
            os.writeBytes("--" + BOUNDARY + "--\r\n");
            os.flush();
            os.close();

            // Step 3 - Read the response code and message
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
        }

        return status;
    }

    private static void writeFormField(String fieldName, String value, DataOutputStream os) throws IOException {
        os.writeBytes("--" + BOUNDARY + "\r\n");
        os.writeBytes("Content-Disposition: form-data; name=\"" + fieldName + "\"\r\n\r\n");
        os.writeBytes(value + "\r\n");
        os.flush();
    }

    private static void writeImageFile(File imageFile, DataOutputStream os) throws IOException {
        os.writeBytes("--" + BOUNDARY + "\r\n");
        os.writeBytes("Content-Disposition: form-data; name=\"image\"; filename=\"" + imageFile.getName() + "\"\r\n");
        os.writeBytes("Content-Type: image/jpeg\r\n\r\n");

        FileInputStream fis = new FileInputStream(imageFile);
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = fis.read(buffer)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.writeBytes("\r\n");
        os.flush();
        fis.close();
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
