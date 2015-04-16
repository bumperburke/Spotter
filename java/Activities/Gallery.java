package stefan.burke.mydit.ie.spotter.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

import stefan.burke.mydit.ie.spotter.Classes.Image;
import stefan.burke.mydit.ie.spotter.Classes.ImageAdapter;
import stefan.burke.mydit.ie.spotter.R;


public class Gallery extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private ImageAdapter imageAdapter; //create an instance of the image adapter class
    ArrayList<Image> images; //create an array list of Images
    TextView noEntries; //text view if no images in gallery
    ImageButton back; //back button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery);

        images = getFromSdCard(); //get the images from the sd card
        if(images.isEmpty()) //if there are no images
        {
            noEntries = (TextView)findViewById(R.id.noEntries); //text view for no entries
            noEntries.setText(R.string.noPics); //set the text for the text view
            back = (ImageButton)findViewById(R.id.back); //button to go back to previous avtivity
            back.setImageResource(R.drawable.back); //set the image resource of the button
            back.setOnClickListener(this); //set on click listener to button
        }

        if(!images.isEmpty()) //if there are images retireved
        {
            GridView imageGrid = (GridView) findViewById(R.id.imageGrid); //create a gridview to hold the images in
            imageAdapter = new ImageAdapter(Gallery.this, images); //create the adapter giving the context and the images to be put in
            imageGrid.setAdapter(imageAdapter); //set the adapter to the image grid
            imageGrid.setOnItemClickListener(this); //set an on click item listener to listen for individual item clicks
        }
    }

    //Reference: http://javatechig.com/android/android-gridview-example-building-image-gallery-in-android
    private ArrayList<Image> getFromSdCard() { //method to get image files from sd card returned in an arraylist of images
        File[] listFile; //create a files array
        ArrayList<Image> images = new ArrayList<>(); //create an array list of Images

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Spotter Progress"); //get the directory of where the pictures are held
        BitmapFactory.Options bmOptions = new BitmapFactory.Options(); //create bitmapfactory options variable to help decode the bitmap retrieved from the file

        if (file.isDirectory()) { //if the directory exists
            listFile = file.listFiles(); //list all the files in that directory

            for (int i = 0; i < listFile.length; i++) //for loop to iterate through the files
            {
                File image = listFile[i]; //create a file with each one
                Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(), bmOptions); //create a bitmap based on the path and using the options
                String picName = image.getName(); //get the name of the file
                images.add(new Image(bitmap, picName)); //create new image of that file and add it to the array list
            }
        }



        return images; //return the array list
    }
    //Reference End

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Image img = images.get(position); //create a new image based on the image clicked

        Intent i = new Intent(Gallery.this, ImageFullscreen.class); //create an intent from this screen to another
        Bitmap b = img.getImage(); //get the image from the item and assign to bitmap

        //this is done because image was too large to pass through intent so needed compression
        //Reference: http://stackoverflow.com/questions/23577827/how-to-pass-images-through-intent
        ByteArrayOutputStream bS = new ByteArrayOutputStream(); //create a byte array output stream
        b.compress(Bitmap.CompressFormat.JPEG, 50, bS); //compress the bitmap giving the format, quality and byte array to pass into
        i.putExtra("image", bS.toByteArray()); //put the byte array into intent
        i.putExtra("title", img.getTitle()); //put the title into intent
        //Reference End
        startActivity(i); //start the activity
    }

    @Override
    public void onClick(View v) { //if back arrow is clicked
        finish(); //finish this activity
    }
}