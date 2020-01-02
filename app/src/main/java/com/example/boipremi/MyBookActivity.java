package com.example.boipremi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class MyBookActivity extends AppCompatActivity implements View.OnClickListener{


    private EditText bname;
    private EditText wname;
    private EditText des;
    private EditText price;
    private ImageView imageView;

    private Button button_choose;
    private Button button_save;
    private Button button_display;
    private ProgressBar progressBar;
    private Uri imageUri;

    DatabaseReference databaseReference;
    StorageReference storageReference;

    StorageTask storageTask;

    private static final int IMAGE_REQUEST=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_book);

        bname=findViewById(R.id.book_name);
        wname=findViewById(R.id.writer_name);
        des=findViewById(R.id.description);
        price=findViewById(R.id.price);
        imageView=findViewById(R.id.image);
        progressBar=findViewById(R.id.progress);

        button_save=findViewById(R.id.btn_save);
        button_choose =findViewById(R.id.btn_choose);
        button_display=findViewById(R.id.btn_display);

        databaseReference= FirebaseDatabase.getInstance().getReference( "Upload");
        storageReference= FirebaseStorage.getInstance().getReference( "Upload");

        button_choose.setOnClickListener(this);
        button_save.setOnClickListener(this);
        button_display.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.btn_choose:

                openFileChooser();

                break;

            case R.id.btn_display:


                break;

            case R.id.btn_save:

                if(storageTask!=null && storageTask.isInProgress())
                {
                    Toast.makeText(getApplicationContext(),"Wait till complete",Toast.LENGTH_LONG).show();

                }

                else {
                    saveData();

                }



                break;

        }

    }

    void openFileChooser()
    {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==IMAGE_REQUEST && requestCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            imageUri =data.getData();
            Picasso.get().load("imageUri").into(imageView);
            //Picasso.get(this).load(imageUri).into(imageView);
        }
    }


    public String getFileExtension(Uri imageUri)
    {
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imageUri));
    }
    private void saveData()
    {
        progressBar.setVisibility(View.VISIBLE);
        final String book_name=bname.getText().toString().trim();
        String writer_name=wname.getText().toString().trim();
        String description=des.getText().toString().trim();
        String Price=price.getText().toString().trim();


        if(book_name.isEmpty())
        {
            bname.setError("বইয়ের নাম দিন");
            bname.requestFocus();
            return;
        }

        if(writer_name.isEmpty())
        {
            wname.setError("লেখকের দাম দি্ন");
            bname.requestFocus();
            return;
        }

        if(Price.isEmpty())
        {
            price.setError("Enter Book Name");
            bname.requestFocus();
            return;
        }

        StorageReference ref=storageReference.child(System.currentTimeMillis()+"."+getFileExtension(imageUri));

        ref.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content

                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),"book added",Toast.LENGTH_LONG).show();

                        Upload upload=new Upload(book_name,taskSnapshot.getStorage().getDownloadUrl().toString());

                        String uploadId=databaseReference.push().getKey();
                        databaseReference.child(uploadId).setValue(upload);


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...

                        Toast.makeText(getApplicationContext(),"book is not added",Toast.LENGTH_LONG).show();

                    }
                });

    }




}