package kiran541.ench.com.footballapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static kiran541.ench.com.footballapp.R.layout.teamlist;

public class PersonDetails extends AppCompatActivity {
        TextView pdetails;
    Button start;
    ListView clist,clist1;
    ImageView plogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       pdetails=(TextView)findViewById(R.id.toolbar_title);
        //plogo=(ImageView)findViewById(R.id.fab);
        start=(Button)findViewById(R.id.start);
        clist=(ListView)findViewById(R.id.clist);
        clist1=(ListView)findViewById(R.id.clist1);
        new kilomilo().execute(MyGlobal_Url.MYBASIC_TEAMLIST);
        Intent intent = getIntent();
        String nam = intent.getStringExtra("pname");
       /* String logo = intent.getStringExtra("plogo");
        Glide.with(this).load(logo).into(plogo);*/
       // Toast.makeText(getApplicationContext(),inp,Toast.LENGTH_LONG).show();
        pdetails.setText(nam);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(PersonDetails.this,NewsActivity.class));
            }
        });

        //setTitle(inp);
    }

    public class MovieAdap extends ArrayAdapter {
        private List<Teamname> movieModelList;
        private int resource;
        Context context;
        private LayoutInflater inflater;
        MovieAdap(Context context, int resource, List<Teamname> objects) {
            super(context, resource, objects);
            movieModelList = objects;
            this.context =context;
            this.resource = resource;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getViewTypeCount() {
            return 1;
        }
        @Override
        public int getItemViewType(int position) {
            return position;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder  ;
            if(convertView == null){
                convertView = inflater.inflate(resource,null);
                holder = new ViewHolder();
                holder.logo=(ImageView) convertView.findViewById(R.id.teamlogo);
                holder.tname=(TextView) convertView.findViewById(R.id.teamname);
                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }
            Teamname cc=movieModelList.get(position);
            holder.tname.setText(cc.getName());
            //Toast.makeText(getApplicationContext(),cc.getTeamlogo(),Toast.LENGTH_LONG).show();
            // Picasso.with(context).load(cc.getTeamlogo()).fit().error(R.drawable.footballlogo).fit().into(holder.logo);
            Glide.with(context).load(cc.getTeamlogo()).into(holder.logo);
            //Glide.with(context).load(plogo.getTeamlogo()).into(holder.logo);
            return convertView;
        }
        class ViewHolder{

            public ImageView logo;
            public TextView tname;

        }
    }
    public class kilomilo extends AsyncTask<String,String, List<Teamname>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected List<Teamname> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder buffer = new StringBuilder();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                String finalJson = buffer.toString();
                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("result");
                List<Teamname> milokilo = new ArrayList<>();
                Gson gson = new Gson();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    Teamname catego = gson.fromJson(finalObject.toString(), Teamname.class);
                    milokilo.add(catego);
                }
                return milokilo;
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(final List<Teamname> movieMode) {
            super.onPostExecute(movieMode);
            if (movieMode.size()>0)
            {
                MovieAdap adapter = new MovieAdap(getApplicationContext(), teamlist, movieMode);
                clist.setAdapter(adapter);
                clist1.setAdapter(adapter);
                clist1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Teamname item = movieMode.get(position);
                       /* Intent intent=new Intent(PersonDetails.this,PersonDetails.class);
                        intent.putExtra("pname",item.getName());
                        intent.putExtra("plogo",item.getTeamlogo());
                        startActivity(intent);*/
                    }
                });

                clist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Teamname item = movieMode.get(position);
                       /* Intent intent=new Intent(PersonDetails.this,PersonDetails.class);
                        intent.putExtra("pname",item.getName());
                        intent.putExtra("plogo",item.getTeamlogo());
                        startActivity(intent);*/
                    }
                });
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Check your internet connection",Toast.LENGTH_SHORT).show();
            }
        }
    }


}
