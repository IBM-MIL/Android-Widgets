/*
 * Licensed Materials - Property of IBM
 * © Copyright IBM Corporation 2015. All Rights Reserved.
 */

package mil.ibm.com.reusable.utils;

import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

/**
 * Collections of utility functions for I/O operations.
 */
public class IOUtils {

    private final String TAG = getClass().getSimpleName();

    private ContextWrapper ctxWrapper;

    public IOUtils(ContextWrapper ctxWrapper) {
        super();
        this.ctxWrapper = ctxWrapper;
    }

    /**
     * Returns the contents of the specified file. The file must be an asset file in the Android
     * project. If the contents of the file cannot be read, null is returned.
     *
     * @param fileName
     * @return The contents of the file.
     */
    public String loadFileContent(String fileName) {
        // Load file content
        InputStream input = null;
        String fileContent = null;
        try {
            AssetManager assetManager = ctxWrapper.getAssets();
            input = assetManager.open(fileName);
            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();
            fileContent = new String(buffer);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    // Nothing can be done
                }
            }
        }
        return fileContent;
    }

    /**
     * Convenience method for logging an array of integers to the Android console.
     *
     * @param arrayLabel The label that should be used for logging the array.
     * @param tag        The logging tag to use.
     * @param values     The array of integers.
     */
    public static void logIntArray(String arrayLabel, String tag, int[] values) {
        Log.d(tag, arrayLabel + " -- start");
        for (int value : values) {
            Log.d(tag, String.valueOf(value));
        }
        Log.d(tag, arrayLabel + " -- end");
    }
}
