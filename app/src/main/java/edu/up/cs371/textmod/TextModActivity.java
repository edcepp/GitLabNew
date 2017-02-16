package edu.up.cs371.textmod;

/**
 * class TextModActivity
 *
 * Allow text to be modified in simple ways with button-presses.
 */
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import java.util.ArrayList;
import android.widget.TextView;
import android.widget.Button;

public class TextModActivity extends ActionBarActivity {

    private TextView editText;
    private int mySpinnerPosition;

    // array-list that contains our images to display
    private ArrayList<Bitmap> images;

    // instance variables containing widgets
    private ImageView imageView; // the view that shows the image

    /**
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // perform superclass initialization; load the layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_mod);

        // set instance variables for our widgets
        imageView = (ImageView) findViewById(R.id.imageView);

        // Set up the spinner so that it shows the names in the spinner array resources
        //
        // get spinner object
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // get array of strings
        String[] spinnerNames = getResources().getStringArray(R.array.spinner_names);
        // create adapter with the strings
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, spinnerNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // bind the spinner and adapter
        spinner.setAdapter(adapter);

        // load the images from the resources
        //
        // create the arraylist to hold the images
        images = new ArrayList<Bitmap>();
        // get array of image-resource IDs
        TypedArray imageIds2 = getResources().obtainTypedArray(R.array.imageIdArray);
        // loop through, adding one image per string
        for (int i = 0; i < spinnerNames.length; i++) {
            // determine the index; use 0 if out of bounds
            int id = imageIds2.getResourceId(i, 0);
            if (id == 0) id = imageIds2.getResourceId(0, 0);
            // load the image; add to arraylist
            Bitmap img = BitmapFactory.decodeResource(getResources(), id);
            images.add(img);
        }

        // define a listener for the spinner
        spinner.setOnItemSelectedListener(new MySpinnerListener());

        editText = (TextView) findViewById(R.id.editText);
        Button clearButton = (Button) findViewById(R.id.clear_button);
        clearButton.setOnClickListener(new ClearButtonListener());
        Button upperButton = (Button) findViewById(R.id.upper_button);
        upperButton.setOnClickListener(new UpperButtonListener());
        Button lowerButton = (Button) findViewById(R.id.lower_button);
        lowerButton.setOnClickListener(new LowerButtonListener());
        Button reverseButton = (Button) findViewById(R.id.reverse_button);
        reverseButton.setOnClickListener(new ReverseButtonListener());
        Button copyButton = (Button) findViewById(R.id.copy_button);
        copyButton.setOnClickListener(new CopyButtonListener());
        Button noSpaceButton = (Button) findViewById(R.id.nospace_button);
        noSpaceButton.setOnClickListener(new NoSpaceButtonListener());
        mySpinnerPosition = 0;
    }

    /**
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_text_mod, menu);
        return true;
    }

    /**
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * class that handles our spinner's selection events
     */
    private class MySpinnerListener implements OnItemSelectedListener {

        /**
         * @see android.widget.AdapterView.OnItemSelectedListener#onItemSelected(
         *android.widget.AdapterView, android.view.View, int, long)
         */
        @Override
        public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
                                   int position, long id) {
            // set the image to the one corresponding to the index selected by the spinner
            imageView.setImageBitmap(images.get(position));
            mySpinnerPosition = position;
        }

        /**
         * @see android.widget.AdapterView.OnItemSelectedListener#onNothingSelected(
         *android.widget.AdapterView)
         */
        @Override
        public void onNothingSelected(AdapterView<?> parentView) {
            // your code here
        }
    }

    private class ClearButtonListener implements View.OnClickListener {
        public void onClick(View v) {
            editText.setText(" ");
        }
    }

    private class UpperButtonListener implements View.OnClickListener {
        public void onClick(View v) {
            String upper_string = editText.getText().toString().toUpperCase();
            editText.setText(upper_string);
        }
    }

    private class LowerButtonListener implements View.OnClickListener {
        public void onClick(View v) {
            String lower_string = editText.getText().toString().toLowerCase();
            editText.setText(lower_string);
        }
    }

    private class ReverseButtonListener implements View.OnClickListener {
        public void onClick(View v) {
            String original_string = editText.getText().toString();
            String reverse_string = new StringBuffer(original_string).reverse().toString();
            editText.setText(reverse_string);
        }
    }

    private class CopyButtonListener implements View.OnClickListener {
        public void onClick(View v) {
            String[] spinnerNames = getResources().getStringArray(R.array.spinner_names);
            editText.setText(editText.getText().toString() + spinnerNames[mySpinnerPosition]);
        }
    }
    private class NoSpaceButtonListener implements View.OnClickListener {
        public void onClick(View v) {
            editText.setText(editText.getText().toString().replace(" ", ""));
        }
    }
}