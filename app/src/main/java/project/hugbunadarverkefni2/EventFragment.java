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
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final Event ARG_PARAM = new Event("a","a",new Date(),new Date(),"a","a","a","a","a","a","a");
    private static int width;
    private static int height;

    // TODO: Rename and change types of parameters
    private Event event;




    public EventFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param event Parameter 1.
     * @return A new instance of fragment EventFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventFragment newInstance(Event event) {
        EventFragment fragment = new EventFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM, event);
        args.putSerializable("ARG_PARAM", event);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            event = (Event) getArguments().getSerializable("ARG_PARAM");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_2, container, false);

        ImageView imageView = (ImageView) view.findViewById(R.id.image_in_fragment2);
        View descriptionView = view.findViewById(R.id.description_in_fragment2);
        View titleView = view.findViewById(R.id.title_in_fragment2);
        View locationView = view.findViewById(R.id.location_in_fragment2);
        View dateView = view.findViewById(R.id.date_in_fragment2);

        Picasso.with(imageView.getContext()).load(event.getCoverPhotosSrc()).into(imageView);

        String [] dateAndTime = dateToString(event.getStartTime());

//        ((TextView) descriptionView).setMovementMethod(new ScrollingMovementMethod());
        ((TextView) descriptionView).setText(event.getDescription());
        int ih = imageView.getHeight();
        int dh = ((TextView) descriptionView).getHeight();
        ((TextView) descriptionView).setHeight(dh + ih);

        ((TextView) titleView).setText(event.getTitle());
        ((TextView) locationView).setText(event.getVenue());
        ((TextView) dateView).setText(dateAndTime[0]+" "+dateAndTime[1]);




//        Log.d("DESCR >>>>>", descr);
        return view;
    }

    public String[] dateToString(Date date) {

        Log.d("dateFormat>>>>> ", date.toString() );

        String dateString = date.toString();

        String [] parts =  dateString.split(" ");

        for(int i = 0; i< parts.length; i++) {
            Log.d("i >>>> ", parts[i]);
        }

        String month = parts[1];
        String day = parts[2];
        String hourSpecific = parts[3];
        String hourSimple = hourSpecific.substring(0,5);
        String year = parts[5];

        String myCustomDate = day+" "+month+" "+year;
        String myCustomTime = hourSimple;

        return new String[]{myCustomDate, myCustomTime};
    }

//    public void setSize(int width, int height) {
//        this.width = width;
//        this.height = height;
//    }

}
