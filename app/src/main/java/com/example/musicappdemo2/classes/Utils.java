package com.example.musicappdemo2.classes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;

public class Utils {
    public static Bitmap loadBitmapFromAssets(Context context, String path, String folderName)
    {
        InputStream stream = null;
        try
        {
            stream = context.getAssets().open(folderName+"/"+path);
            return BitmapFactory.decodeStream(stream);
        }
        catch (Exception ignored) {} finally
        {
            try
            {
                if(stream != null)
                {
                    stream.close();
                }
            } catch (Exception ignored) {}
        }
        return null;
    }
}
