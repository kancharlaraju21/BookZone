package com.example.bookzone;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    View mView;
    public ViewHolder(View itemView)
    {
        super(itemView);
        mView=itemView;
        //item click
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListner.onItemClick(v,getAdapterPosition());
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mClickListner.onItemLongClick(v,getAdapterPosition());
                return true;
            }
        });
    }
    //set details to recycler view row
    public void setDetails(Context context,String title,String author,String price,String image,String des,String status)
    {
        TextView mTitleView=mView.findViewById(R.id.myBookTitle);
        TextView mAuthorView=mView.findViewById(R.id.myBookAuthor);
        TextView mPriceView=mView.findViewById(R.id.myBookPrice);
        ImageView mImageView=mView.findViewById(R.id.bookPicture);
        TextView mDesView=mView.findViewById(R.id.myBookDescription);
        TextView mstatus=mView.findViewById(R.id.myBookStatus);


        //set data to Views
        mTitleView.setText(title);
        mAuthorView.setText("by "+author);
        mPriceView.setText("\u20B9"+price);
        Picasso.get().load(image).into(mImageView);
        mDesView.setText(des);
        mstatus.setText("Book for "+status);


    }
    private ViewHolder.ClickListner mClickListner;

    //interface to send callbacks
    public interface ClickListner{
        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);
    }

    public void setOnClickListner(ViewHolder.ClickListner clickListner){
        mClickListner=clickListner;
    }
}
