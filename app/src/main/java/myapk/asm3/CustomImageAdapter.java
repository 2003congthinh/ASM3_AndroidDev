package myapk.asm3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CustomImageAdapter extends ArrayAdapter<String> {
    private Context context;

    public CustomImageAdapter(Context context, int resource, List<String> itemList) {
        super(context, resource, itemList);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item, parent, false);
        }

        ImageView imageView = v.findViewById(R.id.profileImage); // Assuming you have an ImageView in your item layout

        // Load image using Glide
        int resourceId = context.getResources().getIdentifier(getItem(position), "drawable", context.getPackageName());
        Glide.with(context)
                .load(resourceId)
                .into(imageView);

        return v;
    }
}

