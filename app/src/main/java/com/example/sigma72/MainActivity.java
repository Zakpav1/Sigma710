package com.example.sigma72;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sigma72.ui.main.SectionsPagerAdapter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.util.ArrayList;

import Operations.Approximation;
import Operations.Integral;
import Operations.Systems;
import functions.Function;
import functions.Graph;
import functions.Matrix;
import functions.parsers.FunctionParser;
import functions.parsers.GraphParser;
import functions.parsers.MatrixParser;
import functions.parsers.TableParser;

import static java.lang.Character.isLetter;

public class MainActivity extends AppCompatActivity {
    public void Change (View view) {
        Fragment fragment= null;
        switch (view.getId()){
            case R.id.button:
                fragment = new BlankFragment();
                break;
            case R.id.button2:
                fragment = new BlankFragment2();
                break;
            case R.id.button3:
                fragment = new BlankFragment3();
                break;
            case R.id.button4:
                fragment = new BlankFragment4();
                break;
            case R.id.button5:
                fragment = new BlankFragment5();
                break;
            case R.id.button6:
                fragment = new BlankFragment6();
                break;
            case R.id.button7:
                fragment = new BlankFragment7();
                break;
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fr,fragment);
        ft.commit();
    }

    //private EditText editTextTextPersonName8; //variable
    //private EditText editTextTextPersonName7; //function
    //private EditText editTextTextPersonName9; //from
    //private EditText editTextTextPersonName10; //to
    //private Button button10; //activate
    //private LineGraphSeries<DataPoint> series;
    //private GraphView graphView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);




    }

    public void drawPlot(View view) {
        EditText editTextTextPersonName7 = (EditText) findViewById(R.id.editTextTextPersonName7);
        EditText editTextTextPersonName8 = (EditText) findViewById(R.id.editTextTextPersonName8);
        EditText editTextTextPersonName9 = (EditText) findViewById(R.id.editTextTextPersonName9);
        EditText editTextTextPersonName10 = (EditText) findViewById(R.id.editTextTextPersonName10);
        Button button10 = (Button) findViewById(R.id.button10);
        GraphView graphView = (GraphView) findViewById(R.id.graph);
        if (graphView.getSeries()!=null)
            graphView.removeAllSeries();
        FunctionParser parser = new FunctionParser(editTextTextPersonName7.getText().toString(), editTextTextPersonName8.getText().charAt(0));
        Function f = parser.parseFunction();
        double from = Double.parseDouble(editTextTextPersonName9.getText().toString());
        double to = Double.parseDouble(editTextTextPersonName10.getText().toString());
        double x = from;
        double y;
        double k = (to-from)/10000;
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();
        for (int i=0; i<10000; ++i){
            y = f.getValueAt(x);
            if (Double.isFinite(y)&& !Double.isNaN(y))
                series.appendData(new DataPoint(x,y), true, 10000);
            x+=k;
        }
        graphView.addSeries(series);
        graphView.getGridLabelRenderer().setVerticalLabelsColor(Color.BLACK);
        graphView.getGridLabelRenderer().setHorizontalLabelsColor(Color.BLACK);
        graphView.getGridLabelRenderer().setGridColor(Color.BLACK);
    }
    public void drawApprox(View view) {
        EditText editTextTextPersonName26 = (EditText) findViewById(R.id.editTextTextPersonName26);
        GraphView graphView = (GraphView) findViewById(R.id.lin_graph);
        if (graphView.getSeries()!=null)
            graphView.removeAllSeries();
        double[][]values = TableParser.parseTable(editTextTextPersonName26.getText().toString());
        Approximation approximation = Approximation.getInstance();
        approximation.LS_linear(values[0], values[1]);
        double from = Math.min(0., -approximation.getA()/approximation.getB());
        double to = values[0][values[0].length-1] + 1;
        double x = from;
        double y;
        double k = (to-from)/10000;
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();
        PointsGraphSeries<DataPoint> points = new PointsGraphSeries<>();
        for (int i=0; i<10000; ++i){
            y = approximation.getValueAt(x);
            series.appendData(new DataPoint(x,y), true, 10000);
            x+=k;
        }
        for (int i=0; i < values[0].length; ++i) {
            points.appendData(new DataPoint(values[0][i], values[1][i]), true, values[0].length);
        }
        series.setColor(Color.GREEN);
        graphView.addSeries(series);
        points.setSize((float) 5);
        graphView.addSeries(points);
        graphView.getGridLabelRenderer().setVerticalLabelsColor(Color.BLACK);
        graphView.getGridLabelRenderer().setHorizontalLabelsColor(Color.BLACK);
        graphView.getGridLabelRenderer().setGridColor(Color.BLACK);
    }
    public void getValue(View view) {
        EditText xEditText = (EditText) findViewById(R.id.editTextTextPersonName270);
        EditText yEditText = (EditText) findViewById(R.id.editTextTextPersonName277);
        Approximation approximation = Approximation.getInstance();
        yEditText.setText(Double.toString(approximation.getValueAt(Double.parseDouble(xEditText.getText().toString()))));
    }
    public void executeDerivative(View view){
        EditText variableEditText = (EditText) findViewById(R.id.editTextTextPersonName12);
        String variable = variableEditText.getText().toString();
        if (variable.length()!=1||!isLetter(variable.charAt(0))){

        }
        EditText functionEditText = (EditText)findViewById(R.id.editTextTextPersonName13);
        String functionStr = functionEditText.getText().toString();
        FunctionParser functionParser = new FunctionParser(functionStr, variable.charAt(0));
        try {
            Function func = functionParser.parseFunction();
            Function f = func.derivative();
            EditText answer = findViewById(R.id.editTextTextPersonName14);
            answer.setText(f.toString());
        }
        catch (IllegalArgumentException e){

        }
    }

    public void executeDijkstra(View view){
        EditText graphWeightsEdit = (EditText) findViewById(R.id.editTextTextPersonName4);
        String graphWeights = graphWeightsEdit.getText().toString();
        try {
            GraphParser parser = new GraphParser(graphWeights);
            Graph graph = parser.parseGraph();
            EditText beginVertex = (EditText) findViewById(R.id.editTextTextPersonName11);
            EditText endVertex = (EditText) findViewById(R.id.editTextTextPersonName15);
            Pair<Double, ArrayList<Integer>> res = graph.Dijkstra(Integer.parseInt(beginVertex.getText().toString()),
                    Integer.parseInt(endVertex.getText().toString()));
            EditText len = (EditText) findViewById(R.id.editTextTextPersonName16);
            len.setText(res.first.toString());
            EditText route = (EditText) findViewById(R.id.editTextTextPersonName17);
            route.setText(res.second.toString());
        }
        catch (IllegalArgumentException e){

        }
    }

    public void executeDeterminant(View view) {
        EditText determEdit = (EditText) findViewById(R.id.editTextTextPersonName2);
        MatrixParser parser = new MatrixParser(determEdit.getText().toString());
        try {
            Matrix matrix = parser.parseMatrix();
            EditText determAns = (EditText) findViewById(R.id.editTextTextPersonName3);
            determAns.setText(Double.toString(matrix.det()));
        }
        catch(IllegalArgumentException e){

        }
    }

    public void executeLU(View view){
        EditText LUEdit = (EditText) findViewById(R.id.editTextTextPersonName2);
        MatrixParser parser = new MatrixParser(LUEdit.getText().toString());
        try {
            Matrix matrix = parser.parseMatrix();
            EditText L = (EditText) findViewById(R.id.editTextTextPersonName5);
            Matrix[] ans = matrix.LU_decomposition();
            L.setText(ans[0].toString());
            EditText U = (EditText) findViewById(R.id.editTextTextPersonName6);
            U.setText(ans[1].toString());
        }
        catch(IllegalArgumentException e){

        }
    }

    public void executeTranslate(View view){
        try {
            EditText baseEdit = (EditText) findViewById(R.id.editTextTextPersonName18);
            EditText requiredBaseEdit = (EditText) findViewById(R.id.editTextTextPersonName19);
            int base = Integer.parseInt(baseEdit.getText().toString());
            int requiredBase = Integer.parseInt(requiredBaseEdit.getText().toString());
            EditText numEdit = (EditText) findViewById(R.id.editTextTextPersonName20);
            EditText ansEdit = (EditText) findViewById(R.id.editTextTextPersonName200);
            ansEdit.setText(Systems.from_p_to_q(numEdit.getText().toString(), base, requiredBase));
        }
        catch (IllegalArgumentException e){

        }

    }

    public void executeIntegral(android.view.View view){
        try{
            EditText funcEdit = (EditText) findViewById(R.id.editTextTextPersonName22);
            EditText variableEdit = (EditText) findViewById(R.id.editTextTextPersonName23);
            EditText ulimitEdit = (EditText) findViewById(R.id.editTextTextPersonName21);
            EditText llimitEdit = (EditText) findViewById(R.id.editTextTextPersonName24);
            TextView ans = (TextView) findViewById(R.id.textView27);
            if (variableEdit.getText().length()!=1||!isLetter(variableEdit.getText().charAt(0))){
                throw new IllegalStateException();
            }
            FunctionParser parser = new FunctionParser(funcEdit.getText().toString(),
                    variableEdit.getText().toString().charAt(0));
            Function func = parser.parseFunction();
            String res = Double.toString(Integral.integrate(func, Double.parseDouble(llimitEdit.getText().toString()),
                    Double.parseDouble(ulimitEdit.getText().toString())));
            ans.setText(res);
        }
        catch (IllegalArgumentException | IllegalStateException e){

        }
    }
}