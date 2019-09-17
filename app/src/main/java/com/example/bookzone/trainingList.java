package com.example.bookzone;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;



@RequiresApi(api = Build.VERSION_CODES.N)
public class trainingList  extends Fragment {
    ListView list;


    String[] maintitle ={
            "UI DEsign",
            "Unity 3DGame Development",
            "Mobile App Development",
            "Content Development(Metallurgical and Material Engineering)",
            "Content Development(Mechanical Engineeering)",
            "Robotic Process Automation",
            "Learn Python|Python for Begineers",
            "AutoCAD Training",
            "Learn c|Learn c++",
            "title"
    };

    String[] subtitle ={
            "Planet Technologies Private Limited",
            "Vikalp India Private Limited",
            "Do Your Thng",
            "Sanfoundary",
            "Sanfoundary",
            "G1ANT",
            "Internshala Trainings",
            "Internshala Trainings",
            "Internshala Trainings",
            "subtitle"
    };

    String[] links={
            "<a href=https://internshala.com/internship/detail/ui-design-work-from-home-job-internship-at-hack-planet-technologies-private-limited1564486904>https://internshala.com/internship/detail/ui-design-work-from-home-job-internship-at-hack-planet-technologies-private-limited1564486904</a>",
            "<a href=https://internshala.com/internship/detail/unity-3d-game-development-work-from-home-job-internship-at-vikalp-india-private-limited1564074389>https://internshala.com/internship/detail/unity-3d-game-development-work-from-home-job-internship-at-vikalp-india-private-limited1564074389</a>",
            "<a href=https://internshala.com/internship/detail/mobile-app-development-work-from-home-job-internship-at-do-your-thng1564634273>https://internshala.com/internship/detail/mobile-app-development-work-from-home-job-internship-at-do-your-thng1564634273</a>",
            "<a href=https://internshala.com/internship/detail/content-development-metallurgical-and-material-engineering-work-from-home-job-internship-at-sanfoundry1562222140>https://internshala.com/internship/detail/content-development-metallurgical-and-material-engineering-work-from-home-job-internship-at-sanfoundry1562222140</a>",
            "<a href=https://internshala.com/internship/detail/content-development-mechanical-engineering-work-from-home-job-internship-at-sanfoundry1562222514>https://internshala.com/internship/detail/content-development-mechanical-engineering-work-from-home-job-internship-at-sanfoundry1562222514</a>",
            "<a href=https://internshala.com/internship/detail/robotic-process-automation-work-from-home-job-internship-at-g1ant1563998124>https://internshala.com/internship/detail/robotic-process-automation-work-from-home-job-internship-at-g1ant1563998124</a>",
            "<a href=https://trainings.internshala.com/python-training>https://trainings.internshala.com/python-training</a>",
            "<a href=https://trainings.internshala.com/autocad-training>https://trainings.internshala.com/autocad-training</a>",
            "<a href=https://trainings.internshala.com/c-plus-plus-training>https://trainings.internshala.com/c-plus-plus-training</a>",
            "<a href=http://google.com>https://google.com</a>"
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_training_list, container, false);

        list=(ListView)rootView.findViewById(R.id.listView);
        MyAdapter myAdapter=new MyAdapter(getContext(),maintitle,subtitle,links);
        list.setAdapter(myAdapter);
        return rootView;

    }
    class MyAdapter extends ArrayAdapter<String>{
        Context context;
        String mTitle[];
        String sTitle[];
        String sLinks[];
        MyAdapter(Context c, String title[], String subti[],String sl[]){
            super(c,R.layout.row,R.id.listView,title);
            this.context=c;
            this.mTitle=title;
            this.sTitle=subti;
            this.sLinks=sl;

        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater=(LayoutInflater)getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row=layoutInflater.inflate(R.layout.row,parent,false);
            ImageView imageView=(ImageView)row.findViewById(R.id.image);
            TextView textView=(TextView)row.findViewById(R.id.title);
            TextView txt2=(TextView)row.findViewById(R.id.linkstxt);
            TextView txt=(TextView)row.findViewById(R.id.subtitle) ;


            textView.setText(mTitle[position]);
            txt2.setClickable(true);
            txt2.setMovementMethod(LinkMovementMethod.getInstance());
            txt2.setText(Html.fromHtml(sLinks[position],Html.FROM_HTML_MODE_COMPACT));
            //txt2.setAutoLinkMask(Linkify.WEB_URLS);
            txt.setText(sTitle[position]);
            return row;
        }
    }
}
