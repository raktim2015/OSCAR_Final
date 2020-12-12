package in.tsff.oscar.ui.quiz;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.islamkhsh.CardSliderAdapter;
import com.github.islamkhsh.CardSliderViewPager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import in.tsff.oscar.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuizLanding#newInstance} factory method to
 * create an instance of this fragment.
 */


class QuizCard {
    protected String header;
    protected String time;
    protected String info;
    protected String currAttempts;
    protected String totalAttempts;
    protected String maxScore;
    protected String currScore;
    protected String color;

    public QuizCard(String header, String time, String info, String currAttempts, String totalAttempts, String maxScore, String currScore, String color) {
        this.header = header;
        this.time = time;
        this.info = info;
        this.currAttempts = currAttempts;
        this.totalAttempts = totalAttempts;
        this.maxScore = maxScore;
        this.currScore = currScore;
        this.color = color;
    }


}

class MovieAdapter extends CardSliderAdapter<MovieAdapter.MovieViewHolder> {

    private ArrayList<QuizCard> quizCards;

    public MovieAdapter(ArrayList<QuizCard> quizCards){
        this.quizCards = quizCards;

    }

    @Override
    public int getItemCount(){
        return quizCards.size();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_card_layout, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void bindVH(@NotNull MovieViewHolder movieViewHolder, int i) {
        View currentView = movieViewHolder.itemView;

        CardView container = currentView.findViewById(R.id.quiz_card_container);
        MaterialTextView cardHeader = currentView.findViewById(R.id.quiz_card_header);
        MaterialTextView cardTime = currentView.findViewById(R.id.quiz_card_time);
        MaterialTextView cardInfo = currentView.findViewById(R.id.quiz_card_info);
        MaterialTextView cardAttempts = currentView.findViewById(R.id.quiz_card_attempts);
        MaterialTextView cardScore = currentView.findViewById(R.id.quiz_card_score);

        QuizCard currentCard = quizCards.get(i);
        cardHeader.setText(currentCard.header);
        cardTime.setText(currentCard.time);
        cardInfo.setText(currentCard.info);
        cardAttempts.setText("Attempts "+currentCard.currAttempts + "/" +currentCard.totalAttempts);
        cardScore.setText("Highest score " + currentCard.currScore + "/" + currentCard.maxScore);
        container.setCardBackgroundColor(Color.parseColor(currentCard.color));



    }


    class MovieViewHolder extends RecyclerView.ViewHolder {
        public MovieViewHolder(View view){
            super(view);
        }
    }
}

public class QuizLanding extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public QuizLanding() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuizLanding.
     */
    // TODO: Rename and change types and number of parameters
    public static QuizLanding newInstance(String param1, String param2) {
        QuizLanding fragment = new QuizLanding();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_quiz_landing, container, false);
        CardSliderViewPager cardSliderViewPager = root.findViewById(R.id.viewPager);

        // Set card contents
        ArrayList <QuizCard> cardContents = new ArrayList<QuizCard>();
        cardContents.add(
                new QuizCard(
                        "Full Syllabus",
                        "45 mins",
                        "Check your preparation for upcoming ASOC Exam",
                        "1",
                        "4",
                        "40",
                        "20",
                        "#FF40C4FF"
                ));

        cardContents.add(
                new QuizCard(
                        "Electronics",
                        "30 mins",
                        "Check your knowledge in electronics domain",
                        "0",
                        "4",
                        "30",
                        "0",
                        "#FF8C00"

                ));

        cardContents.add(
                new QuizCard(
                        "Regulations",
                        "30 mins",
                        "Check your knowledge in communication domain",
                        "2",
                        "4",
                        "30",
                        "30",
                        "#8B4513"
                ));

        cardSliderViewPager.setAdapter(new MovieAdapter(cardContents));

        /*final MaterialButton button = root.findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuizFragment fragment = new QuizFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.nav_host_fragment,fragment)
                            .commit();
            }
        });*/


        return root;
    }
}