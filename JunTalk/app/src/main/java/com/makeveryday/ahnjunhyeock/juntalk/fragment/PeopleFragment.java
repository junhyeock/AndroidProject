package com.makeveryday.ahnjunhyeock.juntalk.fragment;

import android.app.ActivityOptions;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.makeveryday.ahnjunhyeock.juntalk.R;
import com.makeveryday.ahnjunhyeock.juntalk.chat.MessageActivity;
import com.makeveryday.ahnjunhyeock.juntalk.model.UserModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ahnjunhyeock on 2017. 12. 29..
 */

public class PeopleFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_people, container, false);
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.peopleFragment_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        recyclerView.setAdapter(new PeopleFragmentRecyclerViewAdapter());

        return view;
    }

    class PeopleFragmentRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        List<UserModel> userModels;

        public PeopleFragmentRecyclerViewAdapter() {
            userModels = new ArrayList<>();
            final String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            FirebaseDatabase.getInstance().getReference().child("users").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    userModels.clear();

                    for(DataSnapshot snapshot:dataSnapshot.getChildren()) {
                        UserModel userModel = snapshot.getValue(UserModel.class);
                        if(userModel.uid.equals(myUid)) {
                            continue;
                        }
                        userModels.add(userModel);
                    }
                    notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend, parent, false);

            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            final int pos = position;
            Glide.with(holder.itemView.getContext()).
                    load(userModels.get(position).profileImageUrl).apply(new RequestOptions().circleCrop())
                    .into(((CustomViewHolder)holder).imageView);
            ((CustomViewHolder)holder).textView.setText(userModels.get(position).userName);

            holder.itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(v.getContext(), MessageActivity.class);
                    intent.putExtra("destinationUid", userModels.get(pos).uid);
                    ActivityOptions activityOptions = ActivityOptions.makeCustomAnimation(v.getContext(), R.anim.fromright, R.anim.toleft);
                    startActivity(intent, activityOptions.toBundle());
                }
            });
            if(userModels.get(pos).comment != null) {
                ((CustomViewHolder) holder).textView_comment.setText(userModels.get(pos).comment);
            }
        }

        @Override
        public int getItemCount() {
            return userModels.size();
        }

        private class CustomViewHolder extends RecyclerView.ViewHolder {
            public ImageView imageView;
            public TextView textView;
            public TextView textView_comment;

            public CustomViewHolder(View view) {
                super(view);
                imageView = (ImageView)view.findViewById(R.id.friendItem_imageView);
                textView = (TextView)view.findViewById(R.id.friendItem_textView);
                textView_comment = (TextView)view.findViewById(R.id.friendItem_textView_comment);
            }
        }
    }
}
