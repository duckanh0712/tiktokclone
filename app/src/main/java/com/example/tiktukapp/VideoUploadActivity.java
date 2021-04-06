package com.example.tiktukapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tiktukapp.data.model.Video;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.type.DateTime;

import java.time.LocalDateTime;

public class VideoUploadActivity extends AppCompatActivity {

    private static final String TAG = "VideoUploadActivity";
    EditText edtMotaVideo;
    Uri videoUri;
    private FirebaseFirestore dbStore;
    private FirebaseAuth dbAuth;
    private Context context;
    Button btnPut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_upload);
        load();
        Intent intent = getIntent();
        videoUri = Uri.parse(intent.getStringExtra("videoUri"));
        Log.d(TAG, videoUri.toString());
        btnPut.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                publishNow(videoUri);
            }
        });

    }

    public void load() {
        dbStore = FirebaseFirestore.getInstance();
        dbAuth = FirebaseAuth.getInstance();
        context = getApplicationContext();
        edtMotaVideo = findViewById(R.id.edtMoTaVideo);
        btnPut = findViewById(R.id.btnDang);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void publishNow(Uri uri) {
        if (uri != null) {
            FirebaseUser currentUser = dbAuth.getCurrentUser();
            String title = edtMotaVideo.getText().toString();
            Video video = new Video();
            video.setTitle(title);
            video.setLike(0L);
            video.setUploadBy(currentUser.getUid());
            //video.setCratedAt(LocalDateTime.now());
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storeRef = storage.getReference().child("videos/" + videoUri.getLastPathSegment());
            StorageMetadata metadata = new StorageMetadata.Builder()
                    .setContentType("video/mp4")
                    .build();
            UploadTask uploadTask = storeRef.putFile(uri, metadata);
            uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                    Log.d(TAG, "Upload is " + progress + "% done");
                }
            }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.d(TAG, "Upload is paused");
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            Log.d(TAG, "Upload fail!");
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.d(TAG, "Upload success!");
                    storeRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Log.d(TAG, uri.toString());
                            video.setUrl(uri.toString());
                            saveData(video);
                        }
                    });
                }
            });
        }
    }

    private void saveData(Video video) {
        dbStore.collection("video")
                .add(video)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Intent intent = new Intent(context, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Save data faild!", Toast.LENGTH_LONG).show();
            }
        });
    }
}