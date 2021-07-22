package com.example.campustour;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FootBtnClickedActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_footbtn);

        ListView listView = findViewById(R.id.listView);
        SingerAdapter adapter = new SingerAdapter();

        db.collection("foot").document("IT")
                .collection("computer").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d("DB", document.getId() + "=>" + document.getData());
                                String when = document.getString("when").toString();
                                if (when.equals("")) {
                                    adapter.addItem(new SingerItem(document.getString("title").toString(), when, R.drawable.question));
                                } else {
                                    adapter.addItem(new SingerItem(document.getString("title").toString(), "2021-01-01", R.drawable.paw));
                                }
                                listView.setAdapter(adapter);
                            }
                        }
                    }
                });
    }

    class SingerAdapter extends BaseAdapter {
        ArrayList<SingerItem> items = new ArrayList<SingerItem>();

        @Override
        public int getCount(){
            return items.size();
        }
        public void addItem(SingerItem item){
            items.add(item);
        }

        public void addList(ArrayList<String> list){
            for(String str:list){
                //Log.d(str,"str" + str.toString());
                this.addItem(new SingerItem(str.toString(),"2021-01-01",R.drawable.complete1));
            }
        }

        @Override
        public Object getItem(int position){
            return items.get(position);
        }

        @Override
        public long getItemId(int position){
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            SingerItemView singerItemView = null;
            if(convertView==null){
                singerItemView = new SingerItemView(getApplicationContext());
            } else {
                singerItemView = (SingerItemView)convertView;
            }
            SingerItem item = items.get(position);
            singerItemView.setName(item.getName());
            singerItemView.setMobile(item.getMobile());
            singerItemView.setImage(item.getResld());
            return singerItemView;
        }
    }
}