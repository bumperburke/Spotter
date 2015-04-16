package stefan.burke.mydit.ie.spotter.Classes;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by le on 06/04/2015.
 */
public class Image implements Serializable {

    private Bitmap image;
    private String title;

    public Image()
    {

    }

    public Image(Bitmap img, String t)
    {
        this();
        this.setImage(img);
        this.setTitle(t);
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String t) {
        this.title = t;
    }
}
