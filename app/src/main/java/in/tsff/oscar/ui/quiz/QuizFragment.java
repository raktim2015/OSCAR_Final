package in.tsff.oscar.ui.quiz;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.radiobutton.MaterialRadioButton;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import in.tsff.oscar.R;
import in.tsff.oscar.ui.quiz.QuizViewModel;


// create one question at a time for display
class QuizDataClass {
    private String time;
    private String questionHeader;
    private String question;
    private ArrayList <String> options;
    private boolean changeQuestion = false;


    public void setTime(String time) {
        this.time = time;
    }
    public void setQuestionHeader(String questionHeader) {
        this.questionHeader = questionHeader;
    }
    public void setQuestion(String question) {
        this.question = question;
    }
    public void setOptions(ArrayList <String> options) {
        this.options = options;
    }
    public void setChangeQuestion(boolean val) {this.changeQuestion = val;}
    public String getTime() {return time;}
    public String getQuestionHeader() {return questionHeader;}
    public String getQuestion() {return question;}
    public ArrayList <String> getOptions() {return options;}
    public boolean getChangeQuestion() {return changeQuestion;}
}

// Mock data for quiz. Will be replaced by necessary API call in future.
class QuizDataProvider {

    ArrayList <ArrayList <String> > questions;
    QuizDataClass questionToDisplay;


    QuizDataProvider() {
        questionToDisplay = new QuizDataClass();
        questions = new ArrayList <ArrayList <String> > ();
        questions.add(generateQuestion(1,4));
        questions.add(generateQuestion(2,3));
        questions.add(generateQuestion(3,2));
        questions.add(generateQuestion(4,3));
        questions.add(generateQuestion(5,4));

    }

    ArrayList <String> generateQuestion(int questionNo, int noOfOptions) {
        ArrayList <String> eachQuestion = new ArrayList <String>();
        eachQuestion.add("This is Question "+questionNo);
        for(int i=1;i<=noOfOptions;i++) {
            eachQuestion.add("This is option "+i+ " for question " + questionNo);
        }

        return eachQuestion;
    }

    public QuizDataClass getQuestion(int questionNo, int totalQuestions) {
        questionToDisplay.setTime("00:10");
        questionToDisplay.setQuestionHeader(("Question " + questionNo + "/" + totalQuestions));
        questionToDisplay.setQuestion(questions.get(questionNo-1).get(0));
        List<String> op = (questions.get(questionNo-1).subList(1,questions.get(questionNo-1).size()));
        ArrayList<String> op1 = new ArrayList<String>();
        op1.addAll(op);
        questionToDisplay.setOptions(op1);
        questionToDisplay.setChangeQuestion(true);
        return questionToDisplay;
    }
    public int totalQuestions() {
        return questions.size();
    }

}

public class QuizFragment extends Fragment {

    private QuizViewModel quizViewModel;
    private QuizDataProvider dataProviderInstance;
    private QuizDataClass data;
    private int currentQuestion;
    private int totalQuestions;

    public QuizFragment() {
        currentQuestion = 1;
        dataProviderInstance = new QuizDataProvider();
        totalQuestions = dataProviderInstance.totalQuestions();
        data = dataProviderInstance.getQuestion(currentQuestion,totalQuestions);

    }

    private void changeQuestion(TextView quizQuestionHeaderView, TextView quizQuestionView, RadioGroup quizOptionsView) {
        data = dataProviderInstance.getQuestion(currentQuestion,5);
        quizQuestionHeaderView.setText(data.getQuestionHeader());
        quizQuestionView.setText(data.getQuestion());
        ArrayList<String> options = data.getOptions();
        int count = quizOptionsView.getChildCount();
        if (count > 0) {
            for (int i = count - 1; i >= 0; i--) {
                View o = quizOptionsView.getChildAt(i);
                if (o instanceof MaterialRadioButton) {
                    quizOptionsView.removeViewAt(i);
                }
            }
        }
        for (int index = 0; index < options.size(); index++)
            quizOptionsView.addView(createNewRadioButton(index, options.get(index)));
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        quizViewModel = new ViewModelProvider(this).get(QuizViewModel.class);
        View root = inflater.inflate(R.layout.fragment_quiz, container, false);

        final TextView quizTimerView = root.findViewById(R.id.quiz_timer);
        final TextView quizQuestionHeaderView = root.findViewById(R.id.quiz_question_header);
        final TextView quizQuestionView = root.findViewById(R.id.quiz_question);
        final RadioGroup quizOptionsView = root.findViewById(R.id.quiz_options);
        final MaterialButton nextButton = root.findViewById(R.id.quiz_next_button);
        final MaterialButton backButton = root.findViewById(R.id.quiz_back_button);


        changeQuestion(quizQuestionHeaderView,quizQuestionView,quizOptionsView);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentQuestion = (currentQuestion >= totalQuestions) ? 1 : ++currentQuestion;
                changeQuestion(quizQuestionHeaderView,quizQuestionView,quizOptionsView);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                currentQuestion = (currentQuestion <= 1)? totalQuestions: --currentQuestion;
                changeQuestion(quizQuestionHeaderView,quizQuestionView,quizOptionsView);
            }
        });




        quizViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                quizTimerView.setText(s);
            }

        });

        return root;
    }
    public MaterialRadioButton createNewRadioButton(int buttonId, String option) {
        MaterialRadioButton button = new MaterialRadioButton(this.getContext());
        button.setId(View.generateViewId());
        button.setText(option);
        return button;
    }
}