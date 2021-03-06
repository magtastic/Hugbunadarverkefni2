package project.hugbunadarverkefni2;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by svein on 4.4.2017.
 */

public class EventManager {

    private List<Event> allEvents;
    private List<Event> shownEvents;

    public EventManager() {
        // empty constructor
    }

    public void fetchFBEvents(String lat, String lng, Context activity, final Runnable displayEventsCallback) {

        RequestQueue queue = Volley.newRequestQueue(activity);

        // Search url
        String url = "https://hugbunadarverkefni2server-tjjsgkmvbu.now.sh";
        url += "/search?searchString="+lat+","+lng;

        // get request to the constructed url
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        allEvents = parseJSONtoEvent(response);

                        if(shownEvents != null){
                            shownEvents.clear();
                        }
                        shownEvents = new ArrayList<Event>();

                        for(int i = 0; i < allEvents.size(); i++) {
                            shownEvents.add(allEvents.get(i));
                        }

                        // displaying events in MainActivity
                        displayEventsCallback.run();

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        error.printStackTrace();
                    }
                });
        queue.add(jsObjRequest);
    }

    public void applyActiveFilter(Filter activeFilter) {

        List<Event> eventsToShow = new ArrayList<Event>();

        // filter attendees and days
        for(int i = 0; i < this.allEvents.size(); i++) {

            int numAttendees = Integer.parseInt(this.allEvents.get(i).getNumberOfAttendees());
            Date startTime = this.allEvents.get(i).getStartTime();
            Date today = new Date();

            int daysUntil = daysBetweenDates(today, startTime);
            if(numAttendees >= activeFilter.getMinAttendees() &&
               numAttendees <= activeFilter.getMaxAttendees() &&
               daysUntil <= activeFilter.getMaxDaysUntil()    &&
               daysUntil >= activeFilter.getMinDaysUntil()){
                // fulfills attendees and date criteria
                eventsToShow.add(allEvents.get(i));
            }
        }

        this.shownEvents.clear();
        for(Event e : eventsToShow) {
            this.shownEvents.add(e);
        }
        eventsToShow.clear();

        // If no event was found we generate a new event 
        // that is basically an error message
        if(shownEvents.size() == 0) {
            shownEvents.add(new Event(
                    "N/A",
                    "N/A",
                    new Date(),
                    new Date(),
                    "No events available",
                    "N/A",
                    "N/A",
                    "Try different location and check your filtering!",
                    "Nowhere",
                    "N/A",
                    "N/A"
            ));
        }
    }


    public List<Event> getAllEvents() {
        return this.allEvents;
    }

    public List<Event> getShownEvents() {
        return this.shownEvents;
    }


    // parses JSON object to an Event object
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
                        eventJSONObject.getJSONObject("venue").getString("name"),
                        String.valueOf(eventJSONObject.getJSONObject("venue").getJSONObject("location").getDouble("latitude")),
                        String.valueOf(eventJSONObject.getJSONObject("venue").getJSONObject("location").getDouble("longitude"))

                ));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return events;
    }

    public int daysBetweenDates(Date startDate, Date endDate) {

        long startTime = startDate.getTime();
        long endTime = endDate.getTime();
        return (int) (endTime - startTime)/(24 * 60 * 60 * 1000);

    }


}
