package com.cyril.training.day28;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity
{
    private static final String LOG_TAG ="day28Log";

    LinearLayout mRootLinearLayout; // To show TextView insertion's Transition.
    TextView mDynamicTextView; // To create a dynamic textView to be inserted into LinearLayout.
    EditText mEditText; // To get text from user for dynamic TextView.
    Button mInsertButton; // To perform dynamic TextView insertion.
    Button mClearTextViewsButton; // To remove inserted TextView(s).
    Button mGotoActivity2Button; // To go to Activity 2.

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRootLinearLayout= (LinearLayout) findViewById(R.id.LinearLayout_for_TextView_transition);
        mInsertButton = (Button) findViewById(R.id.button_to_insert_TextView_with_Transition);
        mClearTextViewsButton = (Button) findViewById(R.id.button_to_clear_textViews);
        mEditText = (EditText) findViewById(R.id.editText_for_view_transition);
        mGotoActivity2Button = (Button) findViewById(R.id.button_to_goto_A2);
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        // Button to create dynamic TextView and insert it in LinearLayout,
        // with custom animation, using TransitionManager.
        mInsertButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Checking for text-data input.
                if(mEditText.getText().length() != 0)
                {
                    // Creating a dynamic TextView to be inserted into the LinearLayout.
                    String text= mEditText.getText().toString();
                    mDynamicTextView= dynamicTextView(text);

                    // Adding an animation to the TextView insertion,
                    // using TransitionManager.
                    TransitionManager.beginDelayedTransition(mRootLinearLayout/*ViewGroup*/,
                                                            new Fade(Fade.IN)/*Animation*/);

                    // Adding the TextView to the LinearLayout.
                    mRootLinearLayout.addView(mDynamicTextView);

                }
                // Text not inserted.
                else
                {
                    Log.v(LOG_TAG, "Insert text in EditText");
                    Toast.makeText(MainActivity.this, "Insert text in EditText", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Button to remove the populated TextView(s) for the LinearLayout.
        mClearTextViewsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Checking if TextView(s) is/are present.
                if(mRootLinearLayout.getChildCount() > 0)
                {
                    for(int i=0; i <= mRootLinearLayout.getChildCount(); i++)
                    {
                        TransitionManager.beginDelayedTransition(mRootLinearLayout/*ViewGroup*/, new Fade(Fade.OUT)/*Animation*/);
                        mRootLinearLayout.removeView(mRootLinearLayout.getChildAt(i));
                    }
                }
                // TextView(s) not present.
                else
                {
                    Log.v(LOG_TAG, "No TextView(s) to remove.");
                    Toast.makeText(MainActivity.this, "No TextView(s) to remove.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Button to go to Main2Activity.
        mGotoActivity2Button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent= new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Method to create a dynamic TextView to be added to the LinearLayout.
     * @param textToSet is used to setText for TextView.
     * @return a custom TextView.
     */
    private TextView dynamicTextView(String textToSet)
    {
        TextView textView= new TextView(getApplicationContext());

        textView.setText(textToSet); // Setting text for TextView.

        // Setting custom ID for TextView.
        // Custom ID created in res/values/custom_ids.xml
        textView.setId(R.id.dynamic_textView);

        // Setting Layout parameters for the TextView.
        textView.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT/*WIDTH*/,
                                                            ViewGroup.LayoutParams.WRAP_CONTENT/*HEIGHT*/));

        // Random background Color.
        Random random= new Random();
        int index= random.nextInt(5); // Between 0 to 4
        textView.setBackgroundColor(ColorForTextView().get(index));

        textView.setTextColor(Color.WHITE); // Text color.
        textView.setPadding(10/*LEFT*/, 10/*TOP*/, 10/*RIGHT*/, 10/*BOTTOM*/); // Padding.
        textView.setGravity(Gravity.CENTER); // Gravity.
        textView.setTextSize(30.0f); // Font size.

        return textView;
    }

    /**
     * Method to provide Color for dynamic TextView's background.
     * @return a Color based on the Index.
     */
    private ArrayList<Integer> ColorForTextView()
    {
        ArrayList<Integer> color= new ArrayList<>();

        color.add(Color.BLUE);
        color.add(Color.GREEN);
        color.add(Color.RED);
        color.add(Color.DKGRAY);
        color.add(Color.BLACK);

        return color;
    }
}
