package echang.pxd.netease.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;

/**
 * @Description
 * @Author 彭孝东
 * @QQ 932056657
 */
public class Utils {
    public static Bitmap DecodeBitmapFromStream(InputStream is){
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        return bitmap;
    }
}
