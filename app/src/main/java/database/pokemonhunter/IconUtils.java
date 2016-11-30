package database.pokemonhunter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by gc on 11/27/16.
 */

public class IconUtils {
    public static Bitmap resizeMapIcons(MapsActivity mapsActivity, String iconName, int width, int height){
        Bitmap imageBitmap = BitmapFactory.decodeResource(mapsActivity.getResources(),mapsActivity.getResources().getIdentifier(iconName, "drawable", mapsActivity.getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }
}
