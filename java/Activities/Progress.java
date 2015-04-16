package stefan.burke.mydit.ie.spotter.Activities;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import stefan.burke.mydit.ie.spotter.R;

public class Progress extends Activity implements View.OnClickListener {

    ImageButton gallery, camera; //create variables for the gallery and camera buttons
    static final int REQUEST_IMAGE_CAPTURE = 1; //set code to be passed in intent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress);


        gallery = (ImageButton)findViewById(R.id.viewGallery); //create gallery image button
        gallery.setOnClickListener(this); //add on click listener to button

        camera = (ImageButton)findViewById(R.id.takePicture); //create camera image button
        camera.setOnClickListener(this); //add on click listener to button

    }


    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.viewGallery) //if gallery clicked
        {
            Intent gallery = new Intent(this, Gallery.class); //create activity
            startActivity(gallery); //start activity
        }

        if(v.getId() == R.id.takePicture) //if picture clicked
        {
            takePicture(); //call take picture method
        }
    }

    // Reference: http://developer.android.com/training/camera/photobasics.html
    private void takePicture()
    {
        Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //creates an intent to request a picture to be taken
        if(takePic.resolveActivity(getPackageManager()) != null) //if the camera app is available
        {
            File picFile = null; //create a blank file
            try {
                picFile = createImageFile(); //call create image file
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(picFile != null) //if the picfile is empty
            {
                takePic.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(picFile)); //put extra in intent
                startActivityForResult(takePic, REQUEST_IMAGE_CAPTURE); //start activity and wait for result
            }
        }
    }

    private File createImageFile() throws IOException
    {
        String time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()); //create time stamp
        String picFileName = "JPEG_" +time+ "_"; //create file name
        String directoryName = "Spotter Progress"; //create directory name to store files
        File directoryToStore = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), directoryName); //allocate the directory from phone

        directoryToStore.mkdirs(); //create the directory
        File pic = File.createTempFile(picFileName, ".jpg", directoryToStore); //create a temp file of th image
        return pic; //return the file
    }
    //Reference end

}
