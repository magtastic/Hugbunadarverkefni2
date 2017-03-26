package project.hugbunadarverkefni2;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity implements OnConnectionFailedListener/*, EventFragment.OnFragmentInteractionListener*/ {
    private static final int PLACE_PICKER_REQUEST = 1;
    private GoogleApiClient mGoogleApiClient;
    private ListView listOfEventsView;
    private List<Event> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        listOfEventsView = (ListView) findViewById(R.id.list_of_events);

    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                // Click the search icon
                try {
                    PlacePicker.IntentBuilder intentBuilder =
                            new PlacePicker.IntentBuilder();
                    Intent intent = intentBuilder.build(MainActivity.this);
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);

                } catch (GooglePlayServicesRepairableException
                        | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();


                RequestQueue queue = Volley.newRequestQueue(this);

                // search string
                LatLng latLng = place.getLatLng();
                String url = "https://hugbunadarverkefni2server-kovrishssy.now.sh";
                url += "/search?searchString="+latLng.latitude+","+latLng.longitude;

                // get request
                JsonObjectRequest jsObjRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("Response: ", response.toString());
                                //// Events
                                events = parseJSONtoEvent(response);
                                // Create
                                String[] eventsTitles = new String[events.size()];
                                for(int i = 0; i<eventsTitles.length; i++) {
                                    eventsTitles[i] = events.get(i).getTitle();
                                }

                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, eventsTitles);
                                listOfEventsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                        Log.d("Position>>>>>", String.valueOf(position));
                                        Log.d("ID >>>>>>>", String.valueOf(id));

                                        // Fragment
                                        FragmentManager manager = getFragmentManager();
                                        Fragment frag = manager.findFragmentByTag("single_event_fragment");

                                        if(frag != null) {
                                            manager.beginTransaction().remove(frag).commit();
                                        }

                                        EventFragment eventFragment = EventFragment.newInstance(events.get(position).getDescription());
                                        eventFragment.show(manager, "single_event_fragment");
                                    }
                                });
                                listOfEventsView.setAdapter(adapter);


                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO Auto-generated method stub
                                Log.d("Villa", "Villa");
                            }
                        });
                queue.add(jsObjRequest);
            }
        }
    }

    public List<Event> parseJSONtoEvent(JSONObject data) {
        List<Event> events = new ArrayList<Event>();
        SimpleDateFormat customDate = new SimpleDateFormat("yyyy-MM-dd'T'HH':'mm':'ss'+'SSSS");

        try {
            JSONArray eventsJSONArray = data.getJSONArray("events");

            for(int i = 0; i<eventsJSONArray.length(); i++) {
                JSONObject eventJSONObject = eventsJSONArray.getJSONObject(i);
                // Creating Events
                events.add(new Event(
                        eventJSONObject.getString("id"),
                        eventJSONObject.getJSONObject("stats").getString("attending"),
                        customDate.parse(eventJSONObject.getString("startTime")),
                        customDate.parse(eventJSONObject.getString("endTime")),
                        eventJSONObject.getString("name"),
                        eventJSONObject.getString("coverPicture"),
                        eventJSONObject.getString("profilePicture"),
                        eventJSONObject.getString("description"),
                        eventJSONObject.getJSONObject("venue").getString("name")
                        ));
                Log.d("events", events.get(i).getTitle());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return events;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("Connection:", "Failed");
    }

}
