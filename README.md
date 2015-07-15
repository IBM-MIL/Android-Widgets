# Android Widgets
This library contains a collection of reusable widgets for Android. Please note that this library and sample application require Android KitKat or newer versions of the Android platform.

## MILRing
An Android custom view to display sentiment statistics (we also refer to this widget as the sentiment ring). This ring uses different colors to represent the positive and negative sentiment percentage values. For the positive sentiment value, you specify the color that should be used when the value is above a specified threshold. You also specify the color that should be used when the positive value is under the threshold.

The MILRing widget also displays the positive sentiment numeric value in its center. An optional numeric value that represents the number of mentions can also be displayed. This value appears underneath the positive sentiment value. For example, say that after analyzing 899 tweets, the positive sentiment toward a new Android wearable product is 90%. In this case, the MILRing instace will display the number 90 in its center and the number 899 underneath it. As a reference, see the image below.

The MILRing is configured by default to use green as the color for the positive sentiment (when the value is above a threshold value of 50) and blue for the negative sentiment. Because the background color of the parent view is also blue, this gives the impression that the ring is not fully closed (which was a requirement in our main use case).

<image>


You can create an instance of the MILRing programmatically in Java or declaratively in XML. The following XML code snippet shows the attribute values used to render the sentiment MILRing above:

``` xml
<mil.ibm.com.reusable.widgets.MILRing
  android:id="@+id/ring_left"
  android:layout_width="90dp"
  android:layout_height="90dp"
  android:layout_alignParentLeft="true"
  android:layout_alignParentTop="true"
  app:aboveThresholdColor="@color/MILGreen"
  app:arcWidth="5dp"
  app:belowThresholdColor="@color/MILYellow"
  app:mentions="899"
  app:mentionsTextSize="12sp"
  app:negativeColor="@color/MILBlue"
  app:percentage="90"
  app:percentageTextSize="18sp"
  app:threshold="50"/>
```

The MILRing below has a positive sentiment value of 35. Since this value is below the specified threshold (50), the yellow color was used instead of green. 

<image>

The following XML code shows the minimum set of attributes that should be set in order to display a MILRing instance with a percentage value and mentions value:

``` xml
<mil.ibm.com.reusable.widgets.MILRing
  android:id="@+id/ring_center"
  android:layout_width="90dp"
  android:layout_height="90dp"
  android:layout_alignParentTop="true"
  android:layout_centerHorizontal="true"
  app:mentions="125"
  app:percentage="75"/>
```

The image below shows the MILRing instance that corresponds to this XML snippet of code.

<image>

The following snippet of Java code shows how to obtain a reference to the MILRectangle instance defined in XML and then sets the positive percentage and number of mentions values:

``` java
MILRing ringLeft = (MILRing) findViewById(R.id.ring_left);
ringLeft.setMentions(899);
ringLeft.setPercentage(90);
```

## MILRectangle
An Android custom view to display a either a solid or an empty rectangle. You can create an instance of the MILRectangle programmatically in Java or declaratively in XML.

<image>

The following XML code snippet shows the attribute values used to render the empty green rectangle above:

``` xml
<mil.ibm.com.reusable.widgets.MILRectangle
  android:id="@+id/rightRectangle"
  android:layout_width="70dp"
  android:layout_height="74dp"
  android:layout_alignBottom="@+id/leftRectangle"
  android:layout_alignParentRight="true"
  app:borderWidth="5dp"
  app:rectangleColor="@color/MILGreen"
  app:solid="false"/>
```

The following snippet of Java code shows how to obtain a reference to the MILRectangle instance defined in XML and then set its color and solid properties:

``` java
MILRectangle leftRectangle = (MILRectangle) findViewById(R.id.leftRectangle);
rightRectangle.setSolid(false);
rightRectangle.setRectangleColor(getResources().getColor(R.color.MILGreen));
```

To render a solid rectangle, the borderWidth attribute is not required (if it is specified, it is simply ignored):

``` xml
<mil.ibm.com.reusable.widgets.MILRectangle
  android:id="@+id/leftRectangle"
  android:layout_width="70dp"
  android:layout_height="74dp"
  android:layout_alignParentLeft="true"
  android:layout_below="@+id/ring_left"
  android:layout_marginTop="40dp"
  app:rectangleColor="@color/MILYellow"
  app:solid="true"/>
```

<image>

## SpinningProgressDialog and SpinningAbstractAsyncTask

SpinningProgressDialog is a custom Dialog that displays a non-cancelable spinning image for a predefined amount of time. This dialog is commonly used to represent a load state. For instance, say that your mobile app needs to make a network call and while doing so you want to provide a visual cue to let the user know that they should wait until the data is loaded. An instance of the SpinningProgressDialog can be used for this.

<image>

The following code shows how an asynchronous network call could leverage the SpinningProgressDialog and the SpinningAbstractAsyncTask classes to display a spinning soccer ball to let the user know that data is being loaded. The DummyNetworkCall class extends the abstract SpinningAbstractAsyncTask and provides to its parent constructor the integer reference ID of the image that its SpinningProgressDialog instance should use. Optionally, you can also set the minimum amount of time (loadingMinimumThreshold) that the image should be displayed to the user to avoid flickering. This value is set to 1200 milliseconds by default. The SpinningAbstractAsyncTask class implements the onPreExecute() and onPostExecute() method to display and hide the spinning image.

``` java
/**
 * Asynchronous task for simulating a network call. To simulate a network call,
 * this task simply sleeps for 5 seconds.
 */
public class DummyNetworkCall extends SpinningAbstractAsyncTask<Void, Void, Void> {

    private long sleepTime = 5000;
    private NetworkDataHandler networkDataHandler;

    public DummyNetworkCall(Context context, NetworkDataHandler networkDataHandler) {
        super(context, R.drawable.loading_soccer_ball);
        this.networkDataHandler = networkDataHandler;
    }

    @Override
    protected Void doInBackground(Void... params) {
        // Simulate network call for N seconds
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            // Ignore
        }
        return null;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        networkDataHandler = null;
    }

    @Override
    protected void onPostExecute(Void param) {
        super.onPostExecute(param);
        networkDataHandler.onDataLoaded();
    }

}
```

Please note that you don’t need to use the SpinningAbstractAsyncTask class to make to use of the SpinningProgressDialog class. The SpinningAbstractAsyncTask is just provided a utility abstract task class that implements the plumbing code that otherwise more than likely you'd need to implement if wanting to leverage the SpinningProgressDialog class. Instead of using the SpinningAbstractAsyncTask class, your code can instantiate directly the SpinningProgressDialog class and call its show() and dismiss() methods.

## License
```
Licensed Materials - Property of IBM
© Copyright IBM Corporation 2015. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
