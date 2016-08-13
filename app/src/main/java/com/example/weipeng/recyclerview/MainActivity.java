package com.example.weipeng.recyclerview;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<String> ls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rl = (RecyclerView) findViewById(R.id.myrv);
        final GridLayoutManager manager = new GridLayoutManager(this,2);
        rl.setLayoutManager(manager);

        ls = new ArrayList<>();
        for(int i = 0 ; i< 100 ; i ++){
            ls.add(i + "");
        }
        MyAdapter myAdapter = new MyAdapter();
        myAdapter.addTitle(7,"呵呵哒");
        myAdapter.addTitle(11,"萌萌哒");
        myAdapter.addTitle(20,"饿饿哒");
        rl.setAdapter(myAdapter);


    }

    class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        public SparseArray<String> titles = new SparseArray<>();
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if(isTitle(viewType - 100000)){
                View v = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_title,parent,false);
                TitleViewHolder titleViewHolder = new TitleViewHolder(v);
                return titleViewHolder;

            }else{
                View v = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_test,parent,false);
                MyViewHolder myViewHolder = new MyViewHolder(v);
                return myViewHolder;
            }


        }



        @Override
        public int getItemViewType(int position) {
            if(isTitle(position + 100000)){
                return position;
            }
            return super.getItemViewType(position);
        }

        @Override

        public void onBindViewHolder(RecyclerView.ViewHolder holder,int position) {
            if(isTitle(position)){
                TitleViewHolder titleViewHolder = (TitleViewHolder) holder;
                titleViewHolder.tv.setText(titles.get(position));
                return;
            }
            //获取空过去的item(关键)
            for(int i = 0; i < titles.size(); i++){
                int key = titles.keyAt(i);
                if(position > titles.keyAt(titles.size() - 1)){
                    position -= titles.size();
                    break;
                }else if(position > key && position < titles.keyAt(i + 1)){
                    position -= (i+1);
                    break;
                }

            }
            final int s = position;
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            myViewHolder.tv.setText(ls.get(s));

            myViewHolder.tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MainActivity.this,"您点击了" + s,Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return (ls.size() + titles.size());
        }
        public boolean isTitle(int position){
           return titles.get(position) == null ? false:true;
        }
        public void addTitle(int position,String title){
            titles.put(position,title);
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            //如果是title就占据2个单元格(重点)
            GridLayoutManager manager = (GridLayoutManager) recyclerView.getLayoutManager();
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if(isTitle(position)){
                        return 2;
                    }
                    return 1;
                }
            });
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv_ID);
        }
    }
    class TitleViewHolder extends RecyclerView.ViewHolder{
        TextView tv;
        public TitleViewHolder(View itemView ) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv_title);

        }
    }
}
