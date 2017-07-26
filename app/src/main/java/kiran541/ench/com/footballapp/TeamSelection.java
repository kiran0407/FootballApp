package kiran541.ench.com.footballapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class TeamSelection extends AppCompatActivity {
    ListView teamlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_selection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        teamlist=(ListView)findViewById(R.id.teamlist1);
        new kilomilo().execute(MyGlobal_Url.MYBASIC_TEAMLIST);
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
                MovieAdap adapter = new MovieAdap(getApplicationContext(), R.layout.teamlist, movieMode);
                teamlist.setAdapter(adapter);
                teamlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Teamname item = movieMode.get(position);
                        Intent intent=new Intent(TeamSelection.this,PersonDetails.class);
                        intent.putExtra("pname",item.getName());
                        intent.putExtra("plogo",item.getTeamlogo());
                        startActivity(intent);
                    }
                });
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Check your internet connection",Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signin, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}






