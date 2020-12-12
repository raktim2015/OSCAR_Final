package in.tsff.oscar.ui.quiz;


import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.material.button.MaterialButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import in.tsff.oscar.MainActivity;
import in.tsff.oscar.R;



public class QuizViewModel extends ViewModel {


    private MutableLiveData<String> mText;
    private CountDownTimer timer;

    public QuizViewModel() {


        mText = new MutableLiveData<>();
        timer = new CountDownTimer(50000, 1000) {
            public void onTick(long millisUntilFinished) {
                String timeToDisplay = ((millisUntilFinished/1000) > 9)?("00:"+(millisUntilFinished / 1000)):("00:0"+(millisUntilFinished / 1000));
                mText.setValue(timeToDisplay);
            }
            public void onFinish() {
                timer.cancel();
            }
        };
        startCountDown();
    }





    private void startCountDown() {
        timer.cancel();
        timer.start();
    }

    public LiveData<String> getText() {
        return mText;
    }
}