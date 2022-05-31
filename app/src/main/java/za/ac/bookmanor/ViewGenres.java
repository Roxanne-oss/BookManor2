package za.ac.bookmanor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;


public class ViewGenres extends AppCompatActivity
{
    //Declarations
    private CardView poetryCardView;
    private CardView fictionCardView;
    private CardView romanceCardView;
    private CardView comedyCardView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_genres);
        //finds views
        poetryCardView.findViewById(R.id.poetryCardView);
        fictionCardView.findViewById(R.id.fictionCardView);
        romanceCardView.findViewById(R.id.romanceCardView);
        comedyCardView.findViewById(R.id.comedyCardView);

        //sets on click listener
        poetryCardView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {


            }
        });

        fictionCardView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

            }
        });

        romanceCardView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {


            }
        });

        comedyCardView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

            }
        });

    }



}