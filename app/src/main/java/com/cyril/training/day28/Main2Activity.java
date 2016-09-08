package com.cyril.training.day28;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity
{
    private static final String LOG_TAG ="day28Log";

    CardView mCardViewWithFlashIcon; // CardView with Flash icon, to apply animations on.
    CardView mCardViewWithTextView; // CardView with TextView, to apply animations on.
    TextView mTextViewForInfiniteAnimation; // TextViewView to apply infinite loop animation.

    ObjectAnimator mInfiniteObjAnim; // ObjectAnimator for infinite TextView animation.

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mCardViewWithFlashIcon= (CardView) findViewById(R.id.cardView_with_flash_icon);
        mCardViewWithTextView= (CardView) findViewById(R.id.cardView_with_TextView);
        mTextViewForInfiniteAnimation= (TextView) findViewById(R.id.textView_for_infinite_animations);
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        // Animating the CardView containing Flash drawable.
        animateCardViewWithFlashIcon();

        // Animating the CardView containing TextView.
        animateCardViewWithTextView();

        // Animating CardView infinite times.
        infinitelyAnimateCardView();
    }

    /**
     * Method to perform animations on CardView with Flash icon, using ObjectAnimator and AnimatorSet.
     */
    private void animateCardViewWithFlashIcon()
    {
        // Button to play CardView animations.
        mCardViewWithFlashIcon.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Creating an Object Animator to animate the CardView with Flash icon.
                // Animating scaleY property of the CardView.
                ObjectAnimator objectAnim1= ObjectAnimator.ofFloat(mCardViewWithFlashIcon/*Object to animate*/,
                                                                    "scaleX"/*Property of the Object to animate*/,
                                                                    1f/*Starting value of the property*/,
                                                                    1.5f/*Ending value of the property*/);

                // Setting the duration of the animation in milliseconds.
                objectAnim1.setDuration(3000); // 3 seconds.

                // Animating scaleY property of the CardView
                ObjectAnimator objectAnim2= ObjectAnimator.ofFloat(mCardViewWithFlashIcon, "scaleY", 1f, 1.5f);
                objectAnim2.setDuration(3000); // 3 seconds.

                // Animating scaleX property (back to normal) of the CardView.
                ObjectAnimator objectAnim3= ObjectAnimator.ofFloat(mCardViewWithFlashIcon, "scaleX", 1.5f, 1f);
                objectAnim3.setDuration(100); // 0.1 seconds.

                // Animating scaleY property (back to normal) of the CardView.
                ObjectAnimator objectAnim4= ObjectAnimator.ofFloat(mCardViewWithFlashIcon, "scaleY", 1.5f, 1f);
                objectAnim3.setDuration(100); // 0.1 seconds.


                // Creating an AnimatorSet to animate ObjectAnimators sequentially.
                AnimatorSet animatorSet= new AnimatorSet();
                // Playing objectAnim1 before objectAnim2.
                animatorSet.play(objectAnim1).before(objectAnim2);
                // Playing objectAnim3 and objectAnim4 together, after objectAnim2 animation.
                animatorSet.play(objectAnim3).with(objectAnim4).after(objectAnim2);

                animatorSet.start(); // Start animation.
            }
        });
    }

    /**
     * Method to perform animations on CardView with TextView, using ObjectAnimator and AnimatorSet.
     */
    private void animateCardViewWithTextView()
    {
        mCardViewWithTextView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // To animate CardView's corner radius.
                ObjectAnimator objAnim1= ObjectAnimator.ofFloat(mCardViewWithTextView, "translationX", 0f, 50f);
                objAnim1.setDuration(2000);

                // To animate CardView's background color.
                ObjectAnimator objAnim2= ObjectAnimator.ofInt(mCardViewWithTextView, "cardBackgroundColor", Color.BLACK, Color.BLUE);
                objAnim2.setDuration(2000);

                // Animating the CardView.
                AnimatorSet animatorSet= new AnimatorSet();
                animatorSet.play(objAnim2).with(objAnim1);
                animatorSet.start();
            }
        });
    }

    /**
     * Method to apply infinite-loop animation on CardView, using ObjectAnimator.
     */
    private void infinitelyAnimateCardView()
    {
        // Repeating TextView animation infinite times.
        // Rotating TextView along Y-axis.
        mInfiniteObjAnim= ObjectAnimator.ofFloat(mTextViewForInfiniteAnimation, "rotationY", 0f, 360f);
        mInfiniteObjAnim.setDuration(1000); // Duration of each animation.
        mInfiniteObjAnim.setRepeatCount(Animation.INFINITE); // Setting the repeat count to infinite.

        mTextViewForInfiniteAnimation.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                // Toggling the animation states based on clicks.
                // If animation is not started, start animation.
                if(!mInfiniteObjAnim.isStarted())
                {
                    Log.v(LOG_TAG, "Is started.");
                    mInfiniteObjAnim.start();
                }
                // If animation if paused, resume animation.
                else if(mInfiniteObjAnim.isPaused())
                {
                    Log.v(LOG_TAG, "Is resumed.");
                    mInfiniteObjAnim.resume();
                }
                // If animation is running, pause animation.
                else if(mInfiniteObjAnim.isRunning())
                {
                    Log.v(LOG_TAG, "Is paused.");
                    mInfiniteObjAnim.pause();
                }
            }
        });
    }
}
