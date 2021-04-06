package com.example.tiktukapp.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tiktukapp.R;
import com.example.tiktukapp.data.model.Video;
import com.example.tiktukapp.ui.adapter.VideoAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VideoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    VideoAdapter videoAdapter;
    RecyclerView recyclerView;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public VideoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VideoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VideoFragment newInstance(String param1, String param2) {
        VideoFragment fragment = new VideoFragment();
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

        View view = inflater.inflate(R.layout.fragment_video, container, false);


        db.collection("video")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    List<Video> arr = new ArrayList<>();
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Video video = document.toObject(Video.class);
                                arr.add(video);

                            }


                            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewVideo);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
                            linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
                            //recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(linearLayoutManager);

                            videoAdapter = new VideoAdapter(getContext(),arr);
                            recyclerView.setAdapter(videoAdapter);
                            videoAdapter.notifyDataSetChanged();

                            SnapHelper snapHelper = new PagerSnapHelper();
                            snapHelper.attachToRecyclerView(recyclerView);
                        } else {

                        }
                    }
                });
        //List<Video> arr = new ArrayList<>();
//        arr.add(new Video("1","",0L,""));
//        arr.add(new Video("2","",0L,""));
//        arr.add(new Video("3","",0L,""));
//        arr.add(new Video("4","",0L,""));
//        arr.add(new Video("5","",0L,""));
//        arr.add(new Video("6","",0L,""));
//        arr.add(new Video("7","",0L,""));
//        arr.add(new Video("8","",0L,""));
//        arr.add(new Video("9","",0L,""));
//        arr.add(new Video("0","",0L,""));
//        arr.add(new Video("11","",0L,""));

//        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewVideo);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
//        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
//        //recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(linearLayoutManager);
//
//        videoAdapter = new VideoAdapter(getContext(),arr);
//        recyclerView.setAdapter(videoAdapter);
//        videoAdapter.notifyDataSetChanged();
//
//        SnapHelper snapHelper = new PagerSnapHelper();
//        snapHelper.attachToRecyclerView(recyclerView);
        // Inflate the layout for this fragment
        return view;
    }
}