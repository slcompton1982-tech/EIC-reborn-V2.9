package com.eic.reborn;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity {

    private final int GREEN = Color.rgb(0, 255, 80);
    private final int BACKGROUND = Color.rgb(5, 12, 8);

    private LinearLayout menu;
    private LinearLayout content;
    private EditText search;

    private final List<String> categories = Arrays.asList(
        "Movement", "Visuals", "Player",
        "Camera", "Fun", "Utility",
        "Customization", "Environment",
        "Experimental", "Settings"
    );

    private final List<String> mods = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        generateMods();
        buildInterface();
    }

    private void generateMods() {
        String[] features = {
            "Custom Color", "Glow Effect", "Particle Effect",
            "Rainbow Theme", "Dark Theme", "Neon Theme",
            "Show FPS", "Show Clock", "Show Notifications",
            "Custom Background", "Animation", "Sound Effect",
            "Button Style", "Menu Scale", "Menu Transparency"
        };

        for (String category : categories) {
            for (String feature : features) {
                mods.add(category + " - " + feature);
            }
        }
    }

    private GradientDrawable background(int color) {
        GradientDrawable shape = new GradientDrawable();
        shape.setColor(color);
        shape.setCornerRadius(18);
        shape.setStroke(2, GREEN);
        return shape;
    }

    private TextView makeText(String text, int size) {
        TextView view = new TextView(this);
        view.setText(text);
        view.setTextColor(GREEN);
        view.setTextSize(size);
        view.setTypeface(Typeface.DEFAULT_BOLD);
        view.setGravity(Gravity.CENTER);
        view.setPadding(12, 12, 12, 12);
        return view;
    }

    private void buildInterface() {
        menu = new LinearLayout(this);
        menu.setOrientation(LinearLayout.VERTICAL);
        menu.setPadding(20, 20, 20, 20);
        menu.setBackground(background(BACKGROUND));

        TextView title = makeText("EIC REBORN", 30);
        menu.addView(title);

        TextView subtitle = makeText(
            "NEON VR CONTROL CENTER", 12
        );
        menu.addView(subtitle);

        search = new EditText(this);
        search.setHint("Search 150+ menu entries...");
        search.setHintTextColor(Color.GRAY);
        search.setTextColor(GREEN);
        search.setSingleLine(true);
        menu.addView(search);

        LinearLayout tabs = new LinearLayout(this);
        tabs.setOrientation(LinearLayout.HORIZONTAL);

        for (String category : categories) {
            Button button = new Button(this);
            button.setText(category);
            button.setTextColor(GREEN);
            button.setBackgroundColor(BACKGROUND);

            button.setOnClickListener(v ->
                showCategory(category)
            );

            tabs.addView(button);
        }

        HorizontalScrollView tabScroll = new HorizontalScrollView(this);
        tabScroll.addView(tabs);
        menu.addView(tabScroll);

        content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);

        ScrollView scroll = new ScrollView(this);
        scroll.addView(content);

        menu.addView(scroll,
            new LinearLayout.LayoutParams(
                -1, 0, 1
            )
        );

        Button compact = new Button(this);
        compact.setText("COMPACT / EXPANDED");
        compact.setTextColor(GREEN);

        compact.setOnClickListener(v -> {
            menu.setScaleX(menu.getScaleX() == 1f ? 0.8f : 1f);
            menu.setScaleY(menu.getScaleY() == 1f ? 0.8f : 1f);
        });

        menu.addView(compact);

        setContentView(menu);

        showCategory("Movement");

        search.addTextChangedListener(
            new android.text.TextWatcher() {
                public void beforeTextChanged(
                    CharSequence s, int start, int count, int after
                ) {}

                public void onTextChanged(
                    CharSequence s, int start, int before, int count
                ) {
                    filterMods(s.toString());
                }

                public void afterTextChanged(
                    android.text.Editable e
                ) {}
            }
        );
    }

    private void showCategory(String category) {
        content.removeAllViews();

        for (String mod : mods) {
            if (mod.startsWith(category + " - ")) {
                addMod(mod);
            }
        }
    }

    private void filterMods(String query) {
        content.removeAllViews();

        for (String mod : mods) {
            if (mod.toLowerCase().contains(
                query.toLowerCase()
            )) {
                addMod(mod);
            }
        }
    }

    private void addMod(String name) {
        LinearLayout row = new LinearLayout(this);
        row.setGravity(Gravity.CENTER_VERTICAL);
        row.setPadding(10, 8, 10, 8);
        row.setBackground(background(Color.rgb(10, 25, 15)));

        TextView label = makeText(name, 14);

        Switch toggle = new Switch(this);
        toggle.setText("ON");
        toggle.setTextColor(GREEN);

        toggle.setOnCheckedChangeListener((button, checked) -> {
            button.setText(checked ? "ON" : "OFF");
        });

        row.addView(label,
            new LinearLayout.LayoutParams(
                0, -2, 1
            )
        );

        row.addView(toggle);

        LinearLayout.LayoutParams params =
            new LinearLayout.LayoutParams(-1, -2);

        params.setMargins(0, 5, 0, 5);

        content.addView(row, params);
    }
}
