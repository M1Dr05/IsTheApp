package com.github.midros.istheapp.utils.hiddenCameraServiceUtils;

import android.hardware.Camera;
import java.util.Comparator;

/**
 * Created by luis rafael on 20/03/18.
 */
class PictureSizeComparator implements Comparator<Camera.Size> {
    // Used for sorting in ascending order of
    // roll name
    public int compare(Camera.Size a, Camera.Size b) {
        return (b.height * b.width) - (a.height * a.width);
    }
}
