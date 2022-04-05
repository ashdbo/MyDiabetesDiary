package com.example.mydiabetesdiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ButtonBarLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.TimeZoneFormat;
import android.media.TimedMetaData;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.ui.contextmenu.Item;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.internal.NavigationMenuItemView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NotesAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    public static NoteDatabase database;
    public BottomNavigationView navBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = Room.databaseBuilder(getApplicationContext(), NoteDatabase.class,"notes")
                .addMigrations(MIGRATION_1_2,MIGRATION_2_3)
                .allowMainThreadQueries()
                .build();

        recyclerView = findViewById(R.id.recycler_view);
        layoutManager  = new LinearLayoutManager(this);
        adapter = new NotesAdapter();

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        navBar = findViewById(R.id.navigation_bar);
        navBar.setBackgroundColor(100);

        adapter.reload();

        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_pump_settings:
                        Intent settings_intent = new Intent(navBar.getContext(), SettingActivity.class);
                        navBar.getContext().startActivity(settings_intent);
                        break;
                    case R.id.nav_graphs:
                        Intent graphs_intent = new Intent(navBar.getContext(),GraphActivity.class);
                        navBar.getContext().startActivity(graphs_intent);

                        break;
                }
                return true;
            }
        });

        FloatingActionButton button = findViewById(R.id.add_note_button);
        //click listener for creating a new note
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();

                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                long hour = calendar.get(Calendar.HOUR_OF_DAY);
                long minute = calendar.get(Calendar.MINUTE);
                String min = String.format("%02d",minute);
                String h = String.format("%02d",hour);

                String date = year + "-" + String.format("%02d",month) + "-" + String.format("%02d",day);
                String time = h + ":" + min;
                String title = date + ", " + time;

                database.noteDao().createContents(title, date, time);
                adapter.reload();
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onResume(){
        super.onResume();
        adapter.reload();
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE notes ADD COLUMN title TEXT");
        }
    };

    static final Migration MIGRATION_2_3 = new Migration(2,3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE notes RENAME COLUMN 'blood glucose' TO blood_glucose");
        }
    };


    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            if (ItemTouchHelper.LEFT == 4) {
                int position = viewHolder.getAdapterPosition();
                List<Note> notes = database.noteDao().getAll();
                Note toDelete = notes.get(position);
                final int id = toDelete.getId();

                Snackbar.make(recyclerView, "Confirm Deletion?", BaseTransientBottomBar.LENGTH_LONG)
                        .setAction("Confirm", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                database.noteDao().delete(id);
                                database.noteDao().getAll();
                                adapter.reload();

                            }
                        }).show();
                adapter.reload();
            }
        }
    };
}