package com.example.mydiabetesdiary;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class SettingActivity extends AppCompatActivity {

    public TableLayout settingsTable;
    public BottomNavigationView navBar;
    public static RatioDatabase ratioDatabase;
    public static BasalDatabase basalDatabase;
    public static NoteDatabase database;
    public TextView isf;
    public TextView dailyBasal;
    public EditText start1;
    public EditText end1;
    public EditText ratio1;
    public EditText start2;
    public EditText end2;
    public EditText ratio2;
    public EditText start3;
    public EditText end3;
    public EditText ratio3;
    public EditText start4;
    public EditText end4;
    public EditText ratio4;

    public EditText start5;
    public EditText end5;
    public EditText basal5;
    public EditText start6;
    public EditText end6;
    public EditText basal6;
    public EditText start7;
    public EditText end7;
    public EditText basal7;
    public EditText start8;
    public EditText end8;
    public EditText basal8;
    public TextView bolusAvg;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);
        settingsTable = findViewById(R.id.settings_table);

        isf = findViewById(R.id.isf_text);
        dailyBasal = findViewById(R.id.total_basal);
        bolusAvg = findViewById(R.id.average_bolus);

        start1 = findViewById(R.id.start_time_1);
        end1 = findViewById(R.id.end_time_1);
        ratio1 = findViewById(R.id.ratio_1);

        start2 = findViewById(R.id.start_time_2);
        end2 = findViewById(R.id.end_time_2);
        ratio2 = findViewById(R.id.ratio_2);

        start3 = findViewById(R.id.start_time_3);
        end3 = findViewById(R.id.end_time_3);
        ratio3 = findViewById(R.id.ratio_3);

        start4 = findViewById(R.id.start_time_4);
        end4 = findViewById(R.id.end_time_4);
        ratio4 = findViewById(R.id.ratio_4);

        start5 = findViewById(R.id.start_time_5);
        end5 = findViewById(R.id.end_time_5);
        basal5 = findViewById(R.id.basal_5);

        start6 = findViewById(R.id.start_time_6);
        end6 = findViewById(R.id.end_time_6);
        basal6 = findViewById(R.id.basal_6);

        start7 = findViewById(R.id.start_time_7);
        end7 = findViewById(R.id.end_time_7);
        basal7 = findViewById(R.id.basal_7);

        start8 = findViewById(R.id.start_time_8);
        end8 = findViewById(R.id.end_time_8);
        basal8 = findViewById(R.id.basal_8);

        database = Room.databaseBuilder(getApplicationContext(), NoteDatabase.class,"notes")
                .addMigrations(MIGRATION_1_2,MIGRATION_2_3)
                .allowMainThreadQueries()
                .build();

        ratioDatabase = Room.databaseBuilder(getApplicationContext(), RatioDatabase.class, "ratios")
                .allowMainThreadQueries()
                .build();

        basalDatabase = Room.databaseBuilder(getApplicationContext(),BasalDatabase.class,"basals")
                .allowMainThreadQueries()
                .build();


        for (int i = 0; i < 5; i++) {
            SettingActivity.ratioDatabase.ratioDao().getAll();
            if (ratios.size() < 4) {
                SettingActivity.ratioDatabase.ratioDao().createContents();
            }
        }
        List<Ratio> ratios = SettingActivity.ratioDatabase.ratioDao().getAll();
            Ratio data1 = ratios.get(0);
            start1.setText(data1.start_time);
            end1.setText(data1.end_time);
            ratio1.setText(data1.ratio);

            Ratio data2 = ratios.get(1);
            start2.setText(data2.start_time);
            end2.setText(data2.end_time);
            ratio2.setText(data2.ratio);

            Ratio data3 = ratios.get(2);
            start3.setText(data3.start_time);
            end3.setText(data3.end_time);
            ratio3.setText(data3.ratio);

            Ratio data4 = ratios.get(3);
            start4.setText(data4.start_time);
            end4.setText(data4.end_time);
            ratio4.setText(data4.ratio);

        for (int i = 0; i < 5; i++) {
            SettingActivity.basalDatabase.basalDao().getAllBasal();
            if (basals.size() < 5) {
                SettingActivity.basalDatabase.basalDao().createBasal();
            }
        }

            List<Basal> basals = SettingActivity.basalDatabase.basalDao().getAllBasal();
            Basal data5 = basals.get(0);
            start5.setText(data5.start_time);
            end5.setText(data5.end_time);
            basal5.setText(data5.basal);

            Basal data6 = basals.get(1);
            start6.setText(data6.start_time);
            end6.setText(data6.end_time);
            basal6.setText(data6.basal);

            Basal data7 = basals.get(2);
            start7.setText(data7.start_time);
            end7.setText(data7.end_time);
            basal7.setText(data7.basal);

            Basal data8 = basals.get(3);
            start8.setText(data8.start_time);
            end8.setText(data8.end_time);
            basal8.setText(data8.basal);


        navBar = findViewById(R.id.navigation_bar_settings);
        navBar.setBackgroundColor(100);
        navBar.setSelectedItemId(R.id.nav_pump_settings);

        calculateISF();


        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_notes:
                        Intent notes_intent = new Intent (navBar.getContext(),MainActivity.class);
                        navBar.getContext().startActivity(notes_intent);
                        break;
                    case R.id.nav_graphs:
                        Intent graphs_intent = new Intent(navBar.getContext(),GraphActivity.class);
                        navBar.getContext().startActivity(graphs_intent);

                        break;
                }
                return true;
            }
        });

    }

    private List<Ratio> ratios = new ArrayList<>();
    private List<Basal> basals = new ArrayList<>();

    @Override
    protected void onResume(){
        super.onResume();
        reload();

    }

    public void calculateISF(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        try {
            Date startDate5 = df.parse(start5.getText().toString());
            Date endDate5 = df.parse(end5.getText().toString());
            Date endDate6 = df.parse(end6.getText().toString());
            Date endDate7 = df.parse(end7.getText().toString());
            Date endDate8 = df.parse(end8.getText().toString());


            Double basal5Int = Double.parseDouble(basal5.getText().toString());
            Double basal6Int = Double.parseDouble(basal6.getText().toString());
            Double basal7Int = Double.parseDouble(basal7.getText().toString());
            Double basal8Int = Double.parseDouble(basal8.getText().toString());

            double time5MS = endDate5.getTime() - startDate5.getTime();
            double time6MS = endDate6.getTime() - endDate5.getTime();
            double time7MS = endDate7.getTime() - endDate6.getTime();
            double time8MS = endDate8.getTime() - endDate7.getTime();

            double MStoHr = 3600000;

            double units5 =  time5MS * basal5Int / MStoHr;
            double units6 = time6MS * basal6Int / MStoHr;
            double units7 = time7MS * basal7Int / MStoHr;
            double units8 = (time8MS * basal8Int + 60000)/ MStoHr;

            double totalBasal = units5 + units6 + units7 + units8;
            String basal = String.valueOf(totalBasal);
            String finalBasal = basal.substring(0,5);

            List<Basal> basals = SettingActivity.basalDatabase.basalDao().getAllBasal();
            Basal saveDailyBasal = basals.get(4);
            SettingActivity.basalDatabase.basalDao().saveBasal(finalBasal,saveDailyBasal.id);

            dailyBasal.setText(finalBasal + " Units");

            String bolus = MainActivity.database.noteDao().bolusAvg();
            double bolusDouble = Double.parseDouble(bolus);

            double totalDaily = bolusDouble + totalBasal;
            double ISFDouble = 100/totalDaily;

            String ISF = String.valueOf(ISFDouble);
            String finalISF = ISF.substring(0,4);

            isf.setText(finalISF);

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void onPause() {
        super.onPause();
        List<Ratio> ratios = SettingActivity.ratioDatabase.ratioDao().getAll();
        Ratio data1 = ratios.get(0);
        int id1 = data1.id;
        SettingActivity.ratioDatabase.ratioDao().saveStart(start1.getText().toString(), id1);
        SettingActivity.ratioDatabase.ratioDao().saveEnd(end1.getText().toString(),id1);
        SettingActivity.ratioDatabase.ratioDao().saveRatio(ratio1.getText().toString(),id1);

        Ratio data2 = ratios.get(1);
        int id2 = data2.id;
        SettingActivity.ratioDatabase.ratioDao().saveStart(start2.getText().toString(),id2);
        SettingActivity.ratioDatabase.ratioDao().saveEnd(end2.getText().toString(),id2);
        SettingActivity.ratioDatabase.ratioDao().saveRatio(ratio2.getText().toString(),id2);

        Ratio data3 = ratios.get(2);
        int id3 = data3.id;
        SettingActivity.ratioDatabase.ratioDao().saveStart(start3.getText().toString(),id3);
        SettingActivity.ratioDatabase.ratioDao().saveEnd(end3.getText().toString(),id3);
        SettingActivity.ratioDatabase.ratioDao().saveRatio(ratio3.getText().toString(),id3);

        Ratio data4 = ratios.get(3);
        int id4 = data4.id;
        SettingActivity.ratioDatabase.ratioDao().saveStart(start4.getText().toString(),id4);
        SettingActivity.ratioDatabase.ratioDao().saveEnd(end4.getText().toString(),id4);
        SettingActivity.ratioDatabase.ratioDao().saveRatio(ratio4.getText().toString(),id4);


        List<Basal> basals = SettingActivity.basalDatabase.basalDao().getAllBasal();
        Basal data5 = basals.get(0);
        int id5 = data5.id;
        SettingActivity.basalDatabase.basalDao().saveStartBasal(start5.getText().toString(),id5);
        SettingActivity.basalDatabase.basalDao().saveEndBasal(end5.getText().toString(),id5);
        SettingActivity.basalDatabase.basalDao().saveBasal(basal5.getText().toString(),id5);

        Basal data6 = basals.get(1);
        int id6 = data6.id;
        SettingActivity.basalDatabase.basalDao().saveStartBasal(start6.getText().toString(),id6);
        SettingActivity.basalDatabase.basalDao().saveEndBasal(end6.getText().toString(),id6);
        SettingActivity.basalDatabase.basalDao().saveBasal(basal6.getText().toString(),id6);

        Basal data7 = basals.get(2);
        int id7 = data7.id;
        SettingActivity.basalDatabase.basalDao().saveStartBasal(start7.getText().toString(),id7);
        SettingActivity.basalDatabase.basalDao().saveEndBasal(end7.getText().toString(),id7);
        SettingActivity.basalDatabase.basalDao().saveBasal(basal7.getText().toString(),id7);

        Basal data8 = basals.get(3);
        int id8 = data8.id;
        SettingActivity.basalDatabase.basalDao().saveStartBasal(start8.getText().toString(),id8);
        SettingActivity.basalDatabase.basalDao().saveEndBasal(end8.getText().toString(),id8);
        SettingActivity.basalDatabase.basalDao().saveBasal(basal8.getText().toString(),id8);


    }
    public void reload(){
        ratios = SettingActivity.ratioDatabase.ratioDao().getAll();
        basals = SettingActivity.basalDatabase.basalDao().getAllBasal();
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


}

