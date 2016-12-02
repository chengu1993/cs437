package database.pokemonhunter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by gc on 11/27/16.
 */

public class IconUtils {
    public static Bitmap resizeMapIcons(MapsActivity mapsActivity, String iconName){
        Bitmap imageBitmap = BitmapFactory.decodeResource(mapsActivity.getResources(),mapsActivity.getResources().getIdentifier(iconName, "drawable", mapsActivity.getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, imageBitmap.getWidth()/2, imageBitmap.getHeight()/2, false);
        return resizedBitmap;
    }
}
