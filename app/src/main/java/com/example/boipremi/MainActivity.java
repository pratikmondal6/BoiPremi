package com.example.boipremi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView_boighor, textView_amarboi, textView_chirkut, textView_amartotho;
    private Typeface typeface;

    private CardView cardView_bookshelf, cardView_mybook, cardView_bookletter, cardView_aboutme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //font
        textView_boighor=findViewById(R.id.boighor);
        textView_amarboi=findViewById(R.id.amarboi);
        textView_chirkut=findViewById(R.id.chirkuth);
        textView_amartotho=findViewById(R.id.amartotho);

        typeface=typeface.createFromAsset(getAssets(), "fonts/Nikosh.ttf");

        textView_boighor.setTypeface(typeface);
        textView_amarboi.setTypeface(typeface);
        textView_chirkut.setTypeface(typeface);
        textView_amartotho.setTypeface(typeface);

        //cardview

        cardView_bookshelf=findViewById(R.id.bookshelf_cardview);;
        cardView_mybook=findViewById(R.id.mybook_cardview);
        cardView_bookletter=findViewById(R.id.book_letter_cardview);
        cardView_aboutme=findViewById(R.id.about_me_cardview);

        cardView_bookshelf.setOnClickListener(this);
        cardView_mybook.setOnClickListener(this);
        cardView_bookletter.setOnClickListener(this);
        cardView_aboutme.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        Intent intent = null;
        if(view.getId()==R.id.bookshelf_cardview)
        {
            intent =new Intent(MainActivity.this,BookShelfActivity.class);
        }else if(view.getId()==R.id.mybook_cardview)
        {
            intent=new Intent(this,MyBookActivity.class); //error
        }else if(view.getId()==R.id.book_letter_cardview)
        {
            intent=new Intent(this,BookLetterActivity.class); //error

        }else if(view.getId()==R.id.about_me_cardview)
        {
            intent=new Intent(this,AboutMeActivity.class); //error
        }
        startActivity(intent);

    }
}
