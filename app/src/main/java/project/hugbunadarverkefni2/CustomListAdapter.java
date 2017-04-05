package project.hugbunadarverkefni2;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by svein on 5.4.2017.
 */

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] title;
    private final String[] imgSrc;

    public CustomListAdapter(Activity context, String[] itemname, String[] imgid) {
        super(context, R.layout.my_list_item, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.title=itemname;
        this.imgSrc=imgid;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.my_list_item, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.list_item_title);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.list_item_profilePicture);

        txtTitle.setText(title[position]);

        Picasso.with(imageView.getContext()).load(imgSrc[position]).into(imageView);

        return rowView;

    };

}
