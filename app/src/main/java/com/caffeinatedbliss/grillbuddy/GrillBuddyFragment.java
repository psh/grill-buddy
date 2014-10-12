package com.caffeinatedbliss.grillbuddy;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

public class GrillBuddyFragment extends Fragment {
    private static Typeface cambria;

    private final MeatTemperatureModel model;
    private TextView meat;
    private TextView done;
    private TextView range;

    public GrillBuddyFragment() {
        this.model = new MeatTemperatureModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_main, container, false);

        setFont(view.findViewById(R.id.heading));
        meat = (TextView) view.findViewById(R.id.meat);

        meat.setOnTouchListener(new OnSwipeTouchListener(container.getContext()) {

            @Override
            public void onSwipeLeft() {
                model.prevMeat();
                updateFromModel();
            }
            @Override
            public void onSwipeRight() {
                model.nextMeat();
                updateFromModel();
            }
        });


        view.findViewById(R.id.next_meat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.nextMeat();
                updateFromModel();
            }
        });
        view.findViewById(R.id.previous_meat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.prevMeat();
                updateFromModel();
            }
        });

        done = (TextView) view.findViewById(R.id.done);

        done.setOnTouchListener(new OnSwipeTouchListener(container.getContext()) {
            @Override
            public void onSwipeLeft() {
                model.prevDone();
                updateFromModel();
            }
            @Override
            public void onSwipeRight() {
                model.nextDone();
                updateFromModel();
            }
        });
        view.findViewById(R.id.next_done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.nextDone();
                updateFromModel();
            }
        });
        view.findViewById(R.id.previous_done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.prevDone();
                updateFromModel();
            }
        });

        range = (TextView) view.findViewById(R.id.range);

        range.setOnTouchListener(new OnSwipeTouchListener(container.getContext()) {
            @Override
            public void onSwipeLeft() {
                model.prevDone();
                updateFromModel();
            }
            @Override
            public void onSwipeRight() {
                model.nextDone();
                updateFromModel();
            }
        });
        view.findViewById(R.id.next_range).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.nextDone();
                updateFromModel();
            }
        });
        view.findViewById(R.id.previous_range).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.prevDone();
                updateFromModel();
            }
        });

        updateFromModel();

        return view;
    }

    private void updateFromModel() {
        meat.setText(model.getMeat().getDescription(this.getActivity()));
        done.setText(model.getDone().getDescription(this.getActivity()));
        range.setText(model.getRange().getDescription(this.getActivity()));
    }


    public static void setFont(View view) {
        cambria = loadFont(view.getContext(), "fonts/cambriaz.ttf");

        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            Typeface typeface = textView.getTypeface();
            if (typeface == null) {
                textView.setTypeface(cambria);
            } else if (typeface.isBold() && typeface.isItalic()) {
                textView.setTypeface(cambria);
            } else if (typeface.isBold()) {
                textView.setTypeface(cambria);
            } else if (typeface.isItalic()) {
                textView.setTypeface(cambria);
            } else {
                textView.setTypeface(cambria);
            }
        } else if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            for (int i = 0; i < group.getChildCount(); i++) {
                setFont(group.getChildAt(i));
            }
        }
    }

    private static Typeface loadFont(Context context, String font) {
        return Typeface.createFromAsset(context.getAssets(), font);
    }
}
