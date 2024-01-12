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

    private HttpURLConnection httpConn;
    private DataOutputStream request;
    private final String boundary =  "*****";
    private final String crlf = "\r\n";
    private final String twoHyphens = "--";


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

    public static String postLocation(String email, double latitude, double longitude){
        String status = "";
        try {
            // Step 1 - prepare the connection
            URL url = new URL(URL + "/updateUserLocation");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            // Step 2 - prepare the JSON object
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", email);
            jsonObject.put("latitude", latitude);
            jsonObject.put("longitude", longitude);
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


    public static String getMatches(String email){
        String status = "";
        try {
            // Step 1 - prepare the connection
            URL url = new URL(URL + "/findMatches" + "/" + email);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            // Step 3 - Writing data to the web service
            try (DataOutputStream os = new DataOutputStream(conn.getOutputStream())) {
//                os.writeBytes();
                os.flush();
            }
            // Step 4 - Read the response code and message
            int responseCode = conn.getResponseCode();
            String responseMessage = conn.getResponseMessage();
            // Handle the response appropriately
            if (responseCode == HttpURLConnection.HTTP_OK) {
                status = responseMessage;
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
        };
        return status;
    }


    public static String getMatch(String oemail, String email){
        String status = "";
        try {
            // Step 1 - prepare the connection
            URL url = new URL(URL + "/findMatch");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            // Step 2 - prepare the JSON object
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", email);
            jsonObject.put("oemail", oemail);
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
                status = responseMessage;
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
        }catch (JSONException e) {
            e.printStackTrace();
            status = "Error - JSONException: " + e.getMessage();
        };
        return status;
    }

    public static String updateProfile(String email, String phone, String name, String description, String gender, String program, String interest, int age, String partner){
        String status = "";
        try {
            // Step 1 - prepare the connection
            URL url = new URL(URL + "/updateUserProfile");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            // Step 2 - prepare the JSON object
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", email);
            jsonObject.put("phone", phone);
            jsonObject.put("name", name);
            jsonObject.put("description", description);
            jsonObject.put("gender", gender);
            jsonObject.put("program", program);
            jsonObject.put("interest", interest);
            jsonObject.put("age", age);
            jsonObject.put("partner", partner);
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

    public static String getMates(String email) {
        StringBuilder builder = new StringBuilder();
        try {
            // Step 1 - prepare the connection
            URL url = new URL(URL + "/findMates");
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

    public static String setMatches(String email, String oemail){
        String status = "";
        try {
            // Step 1 - prepare the connection
            URL url = new URL(URL + "/matches");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            // Step 2 - prepare the JSON object
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", email);
            jsonObject.put("oemail", oemail);
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
}
