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
import java.util.Properties;

/**
 * This class can be used to read property files for any application.
 */
public class PropertyReader {

    private Properties properties;
    private final String TAG = getClass().getSimpleName();

    private ContextWrapper ctxWrapper;

    public PropertyReader(ContextWrapper ctxWrapper) {
        super();
        this.ctxWrapper = ctxWrapper;
    }

    public void loadFileContent(String fileName) {
        try {
            properties = new Properties();
            // AssetManager provides access to an application's raw asset files
            AssetManager assetManager = ctxWrapper.getAssets();
            InputStream inputStream = assetManager.open(fileName);
            properties.load(inputStream);
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }

    }

    public String getProperty(String property) {
        return properties.getProperty(property);
    }

    public int getIntProperty(String property) {
        return Integer.valueOf(getProperty(property));
    }

}
