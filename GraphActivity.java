package com.example.mydiabetesdiary;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.androidplot.pie.PieChart;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;
import com.androidplot.util.PixelUtils;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.*;
import com.androidplot.xy.XYSeries;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GraphActivity extends AppCompatActivity {

    public TextView carbAvg;
    public TextView bolusAvg;
    public TextView bgAvg;
    public NoteDatabase database;
    public BasalDatabase basalDatabase;
    public BottomNavigationView navBar;
    public XYPlot bgGraph;
    public List<Number> seriesY;
    public List<Number> seriesX;
    public List<Note> data;
    public PieChart pieChart;
    public PieChart basalBolus;
    public TextView bgChartTitle;
    public TextView bgXAxis;
    public TextView pieLegend1;
    public TextView pieLegend2;
    public TextView pieLegend3;
    public TextView pieLegend4;
    private List<Basal> basal;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_activity);

        database = Room.databaseBuilder(getApplicationContext(), NoteDatabase.class,"notes")
                .addMigrations(MIGRATION_1_2,MIGRATION_2_3)
                .allowMainThreadQueries()
                .build();

        basalDatabase = Room.databaseBuilder(getApplicationContext(),BasalDatabase.class,"basals")
                .allowMainThreadQueries()
                .build();

        carbAvg = findViewById(R.id.average_carb);
        bolusAvg = findViewById(R.id.average_bolus);
        bgAvg = findViewById(R.id.average_BG);

        carbAvg.setTypeface(null, Typeface.NORMAL);
        bolusAvg.setTypeface(null,Typeface.NORMAL);
        bgAvg.setTypeface(null,Typeface.NORMAL);

        navBar = findViewById(R.id.navigation_bar);
        navBar.setBackgroundColor(100);
        navBar.setSelectedItemId(R.id.nav_graphs);

        carbAvg.setText("Average Carbs:\n " + MainActivity.database.noteDao().carbsAvg() + " grams");
        bolusAvg.setText("Average Bolus:\n " + MainActivity.database.noteDao().bolusAvg() + " Units");
        bgAvg.setText("Average BG:\n " + MainActivity.database.noteDao().bgAvg() + " mmol/L" );

        bgGraph = findViewById(R.id.bg_graph);

        bgChartTitle = findViewById(R.id.bg_graph_title);
        bgChartTitle.bringToFront();

        bgXAxis = findViewById(R.id.x_Axis_Title);
        bgXAxis.bringToFront();
        bgXAxis.setTypeface(null,Typeface.NORMAL);

        pieChart = findViewById(R.id.pie_chart);
        basalBolus = findViewById(R.id.basal_bolus);

        pieLegend1 = findViewById(R.id.pie_legend_1);
        pieLegend1.bringToFront();
        pieLegend1.setTypeface(null, Typeface.NORMAL);

        pieLegend2 = findViewById(R.id.pie_legend_2);
        pieLegend2.bringToFront();
        pieLegend2.setTypeface(null, Typeface.NORMAL);

        pieLegend3 = findViewById(R.id.pie_legend_3);
        pieLegend3.bringToFront();
        pieLegend3.setTypeface(null, Typeface.NORMAL);

        pieLegend4 = findViewById(R.id.pie_legend_4);
        pieLegend4.bringToFront();
        pieLegend4.setTypeface(null, Typeface.NORMAL);

        scatterChart();
        pieChart();
        basalBolusChart();


        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_pump_settings:
                        Intent settings_intent = new Intent(navBar.getContext(), SettingActivity.class);
                        navBar.getContext().startActivity(settings_intent);
                        break;
                    case R.id.nav_notes:
                        Intent notes_intent = new Intent (navBar.getContext(),MainActivity.class);
                        navBar.getContext().startActivity(notes_intent);
                        break;
                }
                return true;
            }
        });

    }

     private void scatterChart() {
             data = new ArrayList<>();
             data = MainActivity.database.noteDao().graphBG();
             seriesY = new ArrayList<>();
             seriesX = new ArrayList<>();

             for (int i = 0; i < data.size(); i++) {
                 Note note = data.get(i);
                 String bgX = note.time;
                 SimpleDateFormat df = new SimpleDateFormat("HH:mm");
                 if ((note.BG).equals("")) {
                     continue;
                 } else {
                     double bgY = Double.parseDouble(note.BG);
                     try {
                         Date date = df.parse(bgX);
                         seriesY.add(bgY);
                         seriesX.add(date.getTime());

                     } catch (ParseException e) {
                         e.printStackTrace();
                     }
                 }
             }

             XYSeries series1 = new SimpleXYSeries(seriesX, seriesY, "BG versus Time");
             bgGraph.setDomainBoundaries(0,87000000,BoundaryMode.GROW);
             bgGraph.setRangeBoundaries(0,24,BoundaryMode.FIXED);
             bgGraph.addSeries(series1, new FastLineAndPointRenderer.Formatter(null,Color.BLACK,null));
             bgGraph.setDomainStep(StepMode.INCREMENT_BY_VAL,14400000);
             bgGraph.setRangeStep(StepMode.INCREMENT_BY_VAL,4.0);
             bgGraph.getGraph().getLineLabelInsets().setLeft(PixelUtils.dpToPix(20));
             bgGraph.getGraph().getLineLabelInsets().setBottom(PixelUtils.dpToPix(-50));
             bgGraph.getLegend().setVisible(false);
         }

        public void pieChart(){
            pieChart.getLegend().setVisible(false);
            final float padding = PixelUtils.dpToPix(30);
            final float paddingRight = PixelUtils.dpToPix(170);
            pieChart.getPie().setPadding(padding,padding,paddingRight,padding);

            List<Note> pieData = MainActivity.database.noteDao().normalBgCount();

            int hiCount = 0;
            int lowCount = 0;
            int normalCount =0;
            int midCount = 0;

            for (int j = 0; j < pieData.size(); j++) {
                Note note = pieData.get(j);

                if(note.BG.equals("")){
                    continue;
                }
                double bgDouble = Double.parseDouble(note.BG);

                if (bgDouble > 10.0 && bgDouble <13.9){
                    midCount++;
                }
                else if(bgDouble > 4.0 && bgDouble <= 9.9){
                    normalCount++;
                }
                else if (bgDouble < 4.0){
                    lowCount++;
                }
                else{
                    hiCount++;
                }
            }

            Segment segment1 = new Segment("",lowCount);
            SegmentFormatter formater1 = new SegmentFormatter(Color.rgb(102,255, 255));
            Segment segment2 = new Segment("",normalCount);
            SegmentFormatter formater2 = new SegmentFormatter(Color.rgb(102,178,255));
            Segment segment3 = new Segment("",midCount);
            SegmentFormatter formater3 = new SegmentFormatter(Color.rgb(102,102,255));
            Segment segment4 = new Segment("",hiCount);
            SegmentFormatter formater4 = new SegmentFormatter(Color.rgb(178,102,255));

            pieChart.addSegment(segment1,formater1);
            pieChart.addSegment(segment2,formater2);
            pieChart.addSegment(segment3,formater3);
            pieChart.addSegment(segment4,formater4);

        }

        public void basalBolusChart(){

            basalBolus.getLegend().setVisible(false);
            final float padding = PixelUtils.dpToPix(30);
            final float paddingRight = PixelUtils.dpToPix(170);
            basalBolus.getPie().setPadding(padding,padding,paddingRight,padding);

            String bolus = MainActivity.database.noteDao().bolusAvg();
            double bolusTotal = Double.parseDouble(bolus);

            basal = new ArrayList<>();
            basal = basalDatabase.basalDao().getAllBasal();

            Basal position = basal.get(4);
            String totalDaily = position.basal;

            double total = Double.parseDouble(totalDaily);

            Segment segment1 = new Segment("",bolusTotal);
            SegmentFormatter formater1 = new SegmentFormatter(Color.rgb(102,255, 255));

            Segment segment2 = new Segment("",total);
            SegmentFormatter formater2 = new SegmentFormatter(Color.rgb(102,102,255));

            basalBolus.addSegment(segment1,formater1);
            basalBolus.addSegment(segment2,formater2);

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

