package myapk.asm3;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CustomImageAdapter extends ArrayAdapter<Bitmap> {
    private Context context;
    private List<String> names;
    private List<String> ages;
    private List<String> descriptions;
    private List<String> oemail;

    public CustomImageAdapter(Context context, int resource, List<Bitmap> itemList, List<String> names, List<String> ages, List<String> descriptions, List<String> oemail) {
        super(context, resource, itemList);
        this.context = context;
        this.names = names;
        this.ages = ages;
        this.descriptions = descriptions;
        this.oemail = oemail;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item, parent, false);
        }

        ImageView imageView = v.findViewById(R.id.profileImage);
        TextView nameTextView = v.findViewById(R.id.name);
        TextView ageTextView = v.findViewById(R.id.age);
        TextView descriptionTextView = v.findViewById(R.id.description);

        // Load image using Glide
//        int resourceId = context.getResources().getIdentifier(getItem(position), "drawable", context.getPackageName());
//        Glide.with(context)
//                .load(resourceId)
//                .into(imageView);
        // Load Bitmap into ImageView
        Bitmap bitmap = getItem(position);
        String name = names.get(position);
        String age = ages.get(position);
        String description = descriptions.get(position);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        }

        // Set name, age, and description
        nameTextView.setText(name);
        ageTextView.setText(age);
        descriptionTextView.setText(description);

        return v;
    }
}

