package project.hugbunadarverkefni2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android/*.support.v4*/.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.android.gms.vision.text.Text;
import com.squareup.picasso.Picasso;

import java.util.Date;


public class EventFragment extends DialogFragment {
    // the fragment initialization parameters
    private static final Event shownEvent = new Event("","",new Date(),new Date(),"","","","","","","");
    private static int width;
    private static int height;
    private Event event;

    public EventFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static EventFragment newInstance(Event event) {
        EventFragment fragment = new EventFragment();
        Bundle args = new Bundle();
        args.putSerializable("shownEvent", event);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            event = (Event) getArguments().getSerializable("shownEvent");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_2, container, false);

        // Displaying, image, description, title, location and date in the Event Fragment
        ImageView imageView = (ImageView) view.findViewById(R.id.image_in_fragment2);
        View descriptionView = view.findViewById(R.id.description_in_fragment2);
        View titleView = view.findViewById(R.id.title_in_fragment2);
        View locationView = view.findViewById(R.id.location_in_fragment2);
        View dateView = view.findViewById(R.id.date_in_fragment2);

        // loading image with Picasso
        Picasso.with(imageView.getContext()).load(event.getCoverPhotosSrc()).into(imageView);
        // convert Date object to a readable format
        String [] dateAndTime = dateToString(event.getStartTime());

        // update height for the fragment after image is loaded
        ((TextView) descriptionView).setText(event.getDescription());
        int ih = imageView.getHeight();
        int dh = ((TextView) descriptionView).getHeight();
        ((TextView) descriptionView).setHeight(dh + ih);

        // finish off setting info for Event
        ((TextView) titleView).setText(event.getTitle());
        ((TextView) locationView).setText(event.getVenue());
        ((TextView) dateView).setText(dateAndTime[0]+" "+dateAndTime[1]);

        return view;
    }

    public String[] dateToString(Date date) {
        // converts a Date object to a readable format
        // returns array of Strings.
        String dateString = date.toString();
        String [] parts =  dateString.split(" ");

        String month = parts[1];
        String day = parts[2];
        String hourSpecific = parts[3];
        String hourSimple = hourSpecific.substring(0,5);
        String year = parts[5];

        String myCustomDate = day+" "+month+" "+year;
        String myCustomTime = hourSimple;

        return new String[]{myCustomDate, myCustomTime};
    }

}
