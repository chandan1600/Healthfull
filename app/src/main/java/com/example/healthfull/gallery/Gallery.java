package com.example.healthfull.gallery;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.healthfull.R;
import com.example.healthfull.search_nutri.NutritionInfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Gallery extends AppCompatActivity {

    //required constants
    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int GALLERY_REQUEST_CODE = 105;
    //required global variables
    ImageView galleryImageView;
    String currentPhotoPath;
    StorageReference storageReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //required
        Button cameraButton;
        Button phoneGalleryButton;
        Button seeAllButton;
        storageReference = FirebaseStorage.getInstance().getReference();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        galleryImageView = findViewById(R.id.galleryImageView);
        cameraButton = findViewById(R.id.cameraButton);
        phoneGalleryButton = findViewById(R.id.phoneGalleryButton);
        seeAllButton = findViewById(R.id.seeAllButton);

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCameraPermission();
            }
        });

        phoneGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent phoneGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(phoneGallery, GALLERY_REQUEST_CODE);
            }
        });

        seeAllButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent seeAllImages = new Intent(getApplicationContext(), GalleryGridView.class);
                startActivity(seeAllImages);
            }
        });


    }

    /**
     * This method simply prompts the user to give permission to the app in order to be able to use
     * the phone camera to take pictures. The if statement checks if the system has permission, if
     * it doesn't then it requires the user to give permission.
     */
    private void getCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        } else {
            dispatchTakePictureIntent();
        }
    }

    /**
     * This method is called inside the dispatch take picture intent method. The method creates
     * a file for the picture that's taken or uploaded. It gives the file a name and stores in
     * directory and assigns the images absolute path to a variable called currentPhotoPath.
     * @return
     * @throws IOException
     */
    private File createImageFile() throws IOException {
        //Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName, // prefix
                ".jpg", //suffix
                storageDir //directory
        );

        //Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    /**
     * This method is run called upon getting the camera permission, if the intent is not null
     * and if the photo file is not then then it gets the photo uri and puts in the intent.
     */
    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //Ensure that there's a camera activity to handle the intent
        if(takePictureIntent.resolveActivity(getPackageManager()) != null) {

            //Create the file where the photo should go
            File photoFile = null;
            try{
                photoFile = createImageFile();

            }catch (IOException ex) {
            }
            //Continue only if the File was successfully created
            if(photoFile != null){
                Uri photoURI = FileProvider.getUriForFile(this, "com.example.android.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }

    /**
     * This method, upon getting the camera request permission if creates a new file
     * and sets the imageview with the image uri. Then it also uploads it to the firebase
     * using the upload image to firebase method.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //take picture from camera
        if (requestCode == CAMERA_REQUEST_CODE) {
            File f = new File(currentPhotoPath);
            galleryImageView.setImageURI(Uri.fromFile(f));
            //Log.d("tag", "Absolute URL of image is " + Uri.fromFile(f));
            galleryImageView.invalidate();
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.fromFile(f);
            mediaScanIntent.setData(contentUri);
            this.sendBroadcast(mediaScanIntent);
            uploadImageToFirebase(f.getName(), contentUri);
        }

        //take picture from gallery
        if (requestCode == GALLERY_REQUEST_CODE) {
            Uri contentUri = data.getData();
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "." + getFileExt(contentUri);
            Log.d("tag", "onActivityResult: Gallery Image Uri:  " + imageFileName);
            galleryImageView.setImageURI(contentUri);
            galleryImageView.invalidate();
            uploadImageToFirebase(imageFileName, contentUri);
        }
    }

    /**
     * The method uploads the image taken from either the camera or phone gallery gets uploaded to
     * the apps firebase, firestore database. The images are stored by their uris.
     * @param name
     * @param contentUri
     */
    private void uploadImageToFirebase(String name, Uri contentUri) {
        //specify path where to save image
        //all images uploaded by the user will be in the images in firebase
        //only the latest image will be shown in the image view
        final StorageReference image = storageReference.child("images/" + name); //contains path of the image to upload
        image.putFile(contentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d("tag", "onSuccess: Upload Image URL is " + uri.toString());
                    }
                });
                Toast.makeText(Gallery.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Gallery.this, "Upload Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Method gets the file extension as a MimeTypeMap and returns it.
     * @param contentUri
     * @return
     */
    private String getFileExt(Uri contentUri) {
        ContentResolver c = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(c.getType(contentUri));
    }

}
