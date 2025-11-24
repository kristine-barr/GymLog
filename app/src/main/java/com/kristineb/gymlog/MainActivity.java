package com.kristineb.gymlog;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.kristineb.gymlog.databinding.ActivityMainBinding;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    // global variables

    private static final String TAG = "KB_GYMLOG";
    String mExercise = "";
    double mWeight = 0.0;
    int mReps = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Scroll
        binding.logDisplayTextView.setMovementMethod(new ScrollingMovementMethod());

        binding.logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInformationFromDisplay();
                updateDisplay();
            }
        });

    }

    private void updateDisplay() {
        // Read content of edit text
        String currentInfo = binding.logDisplayTextView.getText().toString();

        // Log the value that we are getting from currentInfo
        Log.d(TAG, "current info: " + currentInfo);
        // New Display
        String newDisplay = String.format(Locale.US,"Exercise:%s%nWeight:%.2f%nReps:%d%n=-=-=-=:%n%s", mExercise, mWeight, mReps, currentInfo);
        binding.logDisplayTextView.setText(newDisplay);
    }
    private void getInformationFromDisplay() {
        // Set mExercise value equal to what the user inputs
        mExercise = binding.exerciseInputEditText.getText().toString();

        try{
            mWeight = Double.parseDouble(binding.weightInputEditText.getText().toString());

        } catch (NumberFormatException e) {
            Log.d(TAG, "Error reading value from Weight edit text.");
        }

        try{
            mReps = Integer.parseInt(binding.repInputEditText.getText().toString());

        } catch (NumberFormatException e) {
            Log.d(TAG, "Error reading value from Reps edit text.");
        }
    }
}