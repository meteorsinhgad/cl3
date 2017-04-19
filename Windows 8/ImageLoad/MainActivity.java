package com.example.nero.image_op;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;

public class MainActivity extends Activity
{

    int RESULT_LOAD_IMG=1;
    Button load,rname,delete;
    ImageView img;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,                           // this if loop for access permission popup
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        load=(Button) findViewById(R.id.button1);
        rname=(Button) findViewById(R.id.button2);
        delete=(Button) findViewById(R.id.button3);

        img=(ImageView) findViewById(R.id.imageView1);

        rname.setVisibility(View.INVISIBLE);
        delete.setVisibility(View.INVISIBLE);

        load.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                // Loading Image here....

                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Start the Intent
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);


            }
        });

        rname.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                // RENAME Image here....


                try{
                    File photo=new File(path);

                    Toast.makeText(getApplicationContext()," Selected Path="+path, Toast.LENGTH_LONG).show();

                    String imgName=photo.getName();
                    Toast.makeText(getApplicationContext(),imgName, Toast.LENGTH_LONG).show();

                    String s=path;
                    Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG).show();
                    s=s.replace(imgName,"Myimage1.JPG");
                    path = s;
                    //Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG).show();

                    //photo=new File(s,imgName);


                    Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG).show();

                    File newPhoto=new File(s);
                    photo.renameTo(newPhoto);

                    Toast.makeText(getApplicationContext()," Renaming SUCCESS.."+newPhoto, Toast.LENGTH_LONG).show();
                }catch(Exception e)
                {
                    Toast.makeText(getApplicationContext()," Renaming Fails..", Toast.LENGTH_LONG).show();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                // DELETE Image here...
                try{
                    File photo=new File(path);

                    boolean d=photo.delete();
                    if(d==true)
                        Toast.makeText(getApplicationContext()," DELETE SUCCESS (Erase from Storage)..", Toast.LENGTH_LONG).show();
                }catch(Exception de)
                {
                    Toast.makeText(getApplicationContext()," DELETE Fails..", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

                path=cursor.getString(columnIndex);
                cursor.close();

                Bitmap bmp=null;
                try{
                    bmp=getBitmapFromUri(selectedImage);
                    img.setImageBitmap(bmp);

                    Toast.makeText(getApplicationContext()," Selected Path="+path, Toast.LENGTH_LONG).show();

                    rname.setVisibility(View.VISIBLE);
                    delete.setVisibility(View.VISIBLE);

                }catch(Exception e){}

            } else
            {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e)
        {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }

    }

    private Bitmap getBitmapFromUri(Uri uri)throws IOException
    {
        ParcelFileDescriptor parcel=getContentResolver().openFileDescriptor(uri, "r");

        FileDescriptor file=parcel.getFileDescriptor();

        Bitmap image= BitmapFactory.decodeFileDescriptor(file);
        parcel.close();
        return image;

    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true; */
}

