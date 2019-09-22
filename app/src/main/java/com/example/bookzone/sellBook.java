package com.example.bookzone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class sellBook extends AppCompatActivity {
    EditText uTitle,uAuthor,uPrice,uDes,uStatus,uStream,uTime;
    ImageView uImage;
    Button upload;
    //Folder Path to Firebase Storage
    String mStoragePath="All_Image_Uploads/";
    //Root Database Path
    String mDatabasePath="Data";
    //Creating URI
    Uri mFilePathUri;

    //Firebase user Details



    //Creating Storage Reference and Database Reference
    StorageReference mStorageReference;
    DatabaseReference mDatabaseReference;

    //Progress Dialog
    ProgressDialog mProgressDialog;

    //Image Request Code for Choosing Image
    int IMAGE_REQUEST_CODE=5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_book);
        //Bottom Navigation
        final BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().findItem(R.id.sell).setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.buy:
                        Intent intent=new Intent(sellBook.this,user_dashboard.class);
                        startActivity(intent);
                        break;

                    case R.id.sell:
                        Intent intent1=new Intent(sellBook.this,sellBook.class);
                        startActivity(intent1);
                        break;

                    case R.id.internship:
                        Intent intent2=new Intent(sellBook.this,internship.class);
                        startActivity(intent2);
                        break;

                    case R.id.others:
                        Intent intent3=new Intent(sellBook.this,others.class);
                        startActivity(intent3);
                        break;


                }
               // bottomNavigationView.setSelectedItemId(item.getItemId());
                return true;


            }
        });

        //Views
        uTitle=findViewById(R.id.uBookTitle);
        uAuthor=findViewById(R.id.uBookAuthor);
        uPrice=findViewById(R.id.uBookPrice);
        uDes=findViewById(R.id.uBookDes);
        uImage=findViewById(R.id.uBookImage);
        upload=findViewById(R.id.uploadimg);
        uStatus=findViewById(R.id.uBookStatus);
        uStream=findViewById(R.id.uBookStream);
        uTime=findViewById(R.id.uBookTime);


        //Image click to choose Image
        uImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Choose Image"),IMAGE_REQUEST_CODE);

            }
        });
        //Button to Upload
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Call Method to Upload Data to Fire base
                UploadDataToFirebase();

            }


        });
        //assign FirebaseStorage instance to storage reference object
        mStorageReference= FirebaseStorage.getInstance().getReference();
        //assign FirebaseStorage instance to root to database name
        mDatabaseReference= FirebaseDatabase.getInstance().getReference(mDatabasePath);
        //Progress Dialog
        mProgressDialog=new ProgressDialog(sellBook.this);



    }

    private void UploadDataToFirebase() {
        //check whether filepath is empty or not
        if(mFilePathUri!=null){
            //setting progress bar title
            mProgressDialog.setTitle("Image Uploading");
            //show Progress Dialog
            mProgressDialog.show();
            //create Secondary Storage Reference
            StorageReference storageReference2nd=mStorageReference.child(mStoragePath+System.currentTimeMillis()+"."+getFileExtension(mFilePathUri));

            //adding on Success for 2ndStorga
            storageReference2nd.putFile(mFilePathUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //getting Uri
                    if (taskSnapshot.getMetadata() != null) {
                        if (taskSnapshot.getMetadata().getReference() != null) {
                            Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                            result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imageUrl = uri.toString();
                                    String uploadTitle=uTitle.getText().toString().trim();
                                    String uploadAuthor=uAuthor.getText().toString().trim();
                                    String uploadPrice=uPrice.getText().toString().trim();
                                    String uploadDes=uDes.getText().toString().trim();
                                    String uploadStatus=uStatus.getText().toString().trim();
                                    String uploadSearch=uploadTitle.toLowerCase();
                                    String uploadStream=uStream.getText().toString().toUpperCase();
                                    String uploadTime=uTime.getText().toString();
                                    uploadStatus+=":"+uploadTime+" Days";
                                    System.out.println(uploadStatus);
                                    //get User ID
                                    FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
                                    String userId=firebaseUser.getUid();
                                    //Hide Dialog Box
                                    mProgressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(),"Uploaded Successfully.....",Toast.LENGTH_LONG).show();
                                    ImageUploadInfo imageUploadInfo=new ImageUploadInfo(uploadTitle,imageUrl,uploadDes,uploadAuthor,uploadPrice,uploadSearch,uploadStatus,uploadStream,userId);
                                    //get Image Id
                                    String uploadedImageId=mDatabaseReference.push().getKey();
                                    //Add Image to Database Reference
                                    mDatabaseReference.child(uploadedImageId).setValue(imageUploadInfo);
                                }
                            });
                        }
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //somethings goes wrong
                    //Hide Dialog Box
                    mProgressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Uploaded Failed....",Toast.LENGTH_LONG).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    mProgressDialog.setTitle("Image is Uploading");
                }
            });
        }
        else{ ;
        Toast.makeText(getApplicationContext(),"Please Select Image",Toast.LENGTH_LONG).show();
        }
    }
    //method to get selected image from file extension to Uri
    private String getFileExtension(Uri uri)
    {
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        //returining file Extension

        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==IMAGE_REQUEST_CODE && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            mFilePathUri=data.getData();
            try{
                //Image to BitMap
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),mFilePathUri);
                //Setting Bitmap Into ImageView
                uImage.setImageBitmap(bitmap);
            }
            catch (Exception e)
            {
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }
    }
}
