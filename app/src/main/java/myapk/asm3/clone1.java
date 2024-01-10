//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.widget.ImageView;
//
//import java.io.ByteArrayInputStream;
//import java.io.InputStream;
//
//public class YourActivity extends AppCompatActivity {
//
//    private ImageView imageView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.your_layout); // Replace with your actual layout XML
//
//        imageView = findViewById(R.id.yourImageView); // Replace with your actual ImageView ID
//
//        // Assume avatarImg is the object received from the server
//        // avatarImg.data is the byte array of image data
//        // avatarImg.contentType is the MIME type of the image
//
//        byte[] imageData = avatarImg.data;
//        String contentType = avatarImg.contentType;
//
//        // Convert the byte array to a Bitmap using AsyncTask
//        new ConvertImageTask().execute(imageData, contentType);
//    }
//
//    private class ConvertImageTask extends AsyncTask<Object, Void, Bitmap> {
//
//        @Override
//        protected Bitmap doInBackground(Object... params) {
//            byte[] imageData = (byte[]) params[0];
//            String contentType = (String) params[1];
//
//            // Convert byte array to InputStream
//            InputStream inputStream = new ByteArrayInputStream(imageData);
//
//            // Convert InputStream to Bitmap
//            return decodeBitmapFromStream(inputStream);
//        }
//
//        @Override
//        protected void onPostExecute(Bitmap bitmap) {
//            // Set the bitmap to your ImageView
//            if (bitmap != null) {
//                imageView.setImageBitmap(bitmap);
//            }
//        }
//    }
//
//    private Bitmap decodeBitmapFromStream(InputStream inputStream) {
//        // Decode the InputStream into a Bitmap
//        return BitmapFactory.decodeStream(inputStream);
//    }
//}


//try {
//        // Assume jsonResult is the JSON object received from the server
//        JSONObject userObj = jsonResult.getJSONObject("user");
//
//        // Get the "avatarImg" object from the user object
//        JSONObject avatarImgObj = userObj.getJSONObject("avatarImg");
//
//        if (avatarImgObj != null) {
//        // Get the "data" attribute from the "avatarImg" object
//        String base64Data = avatarImgObj.getString("data");
//
//        if (!base64Data.isEmpty()) {
//        // Get the "contentType" attribute from the "avatarImg" object
//        String contentType = avatarImgObj.getString("contentType");
//
//        // Decode the Base64 string to obtain the binary data
//        byte[] imageData = Base64.decode(base64Data, Base64.DEFAULT);
//
//        // Set the Bitmap to the ImageView
//        imageView.setImageBitmap(BitmapFactory.decodeByteArray(imageData, 0, imageData.length));
//        } else {
//        Log.e("onPostExecute", "Empty or missing 'data' attribute in avatarImg");
//        }
//        } else {
//        Log.e("onPostExecute", "No value for 'avatarImg' in userObj");
//        }
//        } catch (JSONException e) {
//        e.printStackTrace();
//        Log.e("onPostExecute", "Error parsing JSON");
//        }