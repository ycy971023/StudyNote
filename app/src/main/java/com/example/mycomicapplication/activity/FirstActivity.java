package com.example.mycomicapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.mycomicapplication.until.CheckPermissionsActivity;
import com.example.mycomicapplication.until.ComicSaveUtils;
import com.example.mycomicapplication.ComicwebAdapter;
import com.example.mycomicapplication.R;
import com.example.mycomicapplication.javabean.ComicWeb;

import java.util.ArrayList;

public class FirstActivity extends CheckPermissionsActivity {
    private static ComicWeb[] comicWebs={new ComicWeb("X漫画（外网）", R.drawable.xmanhua,"http://www.xmanhua.com/",0,
            "http://www.xmanhua.com/search?title="), new ComicWeb("漫画DB",R.drawable.manhuadb,"https://www.manhuadb.com/",1,"" +
            "https://www.manhuadb.com/search?q="),new ComicWeb("爱看漫画",R.drawable.ikanmh,"http://www.ikanmh.top/",2,"http://www.ikanmh.top/search?keyword=")};
    private ArrayList<ComicWeb> comicWebArrayList=new ArrayList<>();
    private RecyclerView recyclerView;
    private ComicSaveUtils comicSaveUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initcomicweb();
        GridLayoutManager layoutManager=new GridLayoutManager(FirstActivity.this,3);
        recyclerView.setLayoutManager(layoutManager);
        ComicwebAdapter adapter=new ComicwebAdapter(comicWebArrayList);
        recyclerView.setAdapter(adapter);
        comicSaveUtils=new ComicSaveUtils(FirstActivity.this,"Comics.db",null,2);

    }
    private void initcomicweb(){
        for (int i=0;i<comicWebs.length;i++)
        comicWebArrayList.add(comicWebs[i]);
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar,menu);
        SearchView searchView=(SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent=new Intent(FirstActivity.this, ResultActivity.class);
                intent.putExtra("search_text",query);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent;
        switch (item.getItemId()){
            case R.id.settings:
                intent=new Intent(FirstActivity.this, MyActivity.class);
                startActivity(intent);
                break;
            case R.id.histroy:
                intent=new Intent(FirstActivity.this, HistoryActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }
    public static String getweburl(int id){
        String url=comicWebs[id].getWeburl();
        return url;
    }
    public static int getweblenth(){
      int weblenth=comicWebs.length;
      return weblenth;
    };
    public static String getwebname(int i){
       String webname=comicWebs[i].getWebname();
        return webname;
    };

}