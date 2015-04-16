package stefan.burke.mydit.ie.spotter.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.sql.SQLException;

import stefan.burke.mydit.ie.spotter.Classes.Image;
import stefan.burke.mydit.ie.spotter.R;

public class ImageFullscreen extends Activity implements View.OnClickListener {

    ImageView image; //image view for full screen image
    String title; //title of image

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_fullscreen);

        //Reference: http://stackoverflow.com/questions/23577827/how-to-pass-images-through-intent
        Bitmap img = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("image"), 0, getIntent().getByteArrayExtra("image").length); //retrieve the image from the byte array passed in intent
        title = getIntent().getStringExtra("title"); //retrieve the title
        //Reference End

        image = (ImageView)findViewById(R.id.imageFull); //create the image view
        image.setImageBitmap(img); //set the image
        image.setOnClickListener(this); //set on click listener to image
    }


    @Override
    public void onClick(View v) {

        //Refrence: http://stackoverflow.com/questions/12244297/how-to-add-multiple-buttons-on-a-single-alertdialog
        AlertDialog.Builder builder = new AlertDialog.Builder(ImageFullscreen.this); //create an alert box
        builder.setTitle("Delete "+title); //set the title of alert box
        builder.setItems(new CharSequence[]
                        {"Delete", "Cancel"}, //set the name of each button
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        switch (which) {
                            case 0: //if delete is pressed
                                //Reference: http://stackoverflow.com/questions/16052393/android-delete-image-from-sd-card-with-onclick
                                /*
                                 * Had problems deleting as my images are saving to sdcard0 which is emulated storage so the image
                                 * remains stored to the actually memory. I tried multiple different things and got nowhere.
                                 */
                                File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Spotter Progress"); //get location of image
                                String directory = dir.toString(); //convert the location to string

                                File file = new File(android.os.Environment.getExternalStorageDirectory()+"\"/"+directory+"/"+title+"\""); //get the file based on location and title
                                if(file.exists()) //if file exists
                                {
                                    file.delete(); //delete file
                                }
                                finish(); //finish activity
                                //Reference End
                                break;

                            case 1: //if cancel is clicked
                                break;

                        }
                    }
                });
        builder.create().show(); //show the alert box
        //Reference End
    }
}
