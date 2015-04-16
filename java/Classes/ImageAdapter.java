package stefan.burke.mydit.ie.spotter.Classes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import stefan.burke.mydit.ie.spotter.R;

//Reference: http://javatechig.com/android/android-gridview-example-building-image-gallery-in-android
public class ImageAdapter extends BaseAdapter
{
    ArrayList<Image> imgs;
    private LayoutInflater inflater;
    private Context context;

    public ImageAdapter(Context c, ArrayList<Image> files) {
        this.context = c;
        this.imgs = files;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return imgs.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.gallery_item, null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.image);
            holder.imageText = (TextView) convertView.findViewById(R.id.imageText);
            convertView.setTag(holder);

        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        Image myImage = imgs.get(position);
        holder.imageView.setImageBitmap(myImage.getImage());
        holder.imageText.setText(myImage.getTitle());
        return convertView;
    }

    class ViewHolder {
        ImageView imageView;
        TextView imageText;
    }

    //Reference End

    public String parseImageName(String fullName)
    {
        String[] retName = fullName.split("files/");
        String toBeReturned = retName[1];
        return toBeReturned;
    }
}