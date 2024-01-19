ECLAIR_MR1 squad
#Student Id:
s3927241
s3926387
#Student Name:
Dang Tran Huy Hoang
Nguyen Cong Thinh

#Functionality of the app (write down all features that your app has):
1. The System has 1 role of users;
2. Users can register and login both using entering authentication information and by Gmail Authentication Service;
3. Users can search for their dating mates (by their interest, preference settings with distance from current location)
4. Users can see all previous chat and matches;
5. Users can chat with their mates in realtime;
6. Users can view, update their profile, preferences;


#Technology use (explain what technology that you use and its purpose), any drawback:
1. The UI:
- The UI contains several UI layout including: LinearLayout, ScrollView, EditText, TextView, Button, CardView,
  ConstrainView, FlingContainer, FirebaseListAdapter to display Chat list, Matches, create list of Mates Card, display detailed personal information,
  and handle the events occurring inside the runtime.
- The system uses the Intent to create and move between activities in our system.
  To go back, it use the finish() function to go back to previous activity in the backstack and render the data

2. The Backend:
   Server:
- It Uses Deployed server of NodeJS as the environment, the ExpressJS as the HTTP handler framework
  for the server.
- The MongoDB online cloud DB connected with the server via a secret API url, The Mongoose
  a library to help and make it easier to query data from the MongoDB cloud server.
- The Firebase realtime database connected with the Android to store the chat message data in realtime.

References:
HTTP FormData Multipart Class Referenced From an Answer on StackOverflow by Author Georgevik in 2016:
https://stackoverflow.com/questions/11766878/sending-files-using-post-with-httpurlconnection
