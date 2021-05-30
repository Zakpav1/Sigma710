package com.example.sigma72;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sigma72.ui.main.SectionsPagerAdapter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.Calendar;
import java.util.List;

import Operations.Approximation;
import Operations.ImageFinder;
import Operations.Integral;
import Operations.Systems;
import functions.Function;
import functions.Graph;
import functions.Matrix;
import functions.basic.Const;
import functions.parsers.FunctionParser;
import functions.parsers.GraphParser;
import functions.parsers.MatrixParser;
import functions.parsers.TableParser;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;

public class MainActivity extends AppCompatActivity {
    private List<String> toDoList;
    private ArrayAdapter arrayAdapter;
    private ListView listView;
    private Button button;
    private EditText editText;
    private EditText editTextD;

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

        toDoList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this,R.layout.list_view_layout,toDoList);
        listView = findViewById(R.id.ListView);
        editText = findViewById(R.id.editText);
        editTextD = (EditText) findViewById(R.id.editDate);
        Fragment frag2 = new Planer();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //ft.add(R.id.fragment_blank2, frag2);
        ft.commit();
       // frag2 = getSupportFragmentManager().findFragmentById(R.layout.fragment_blank2);
       // ((TextView) frag2.getView().findViewById(R.id.editDate)).setText(new SimpleDateFormat("yyyy-MM-dd ").format(Calendar.getInstance().getTime()));

    }

    public void setData(EditText view){

        String date = new SimpleDateFormat("yyyy-MM-dd ").format(Calendar.getInstance().getTime());
        view.setText(date);
    }

    public void addItemToList (View view){
        toDoList.add(editText.getText().toString());
        //arrayAdapter.notifyDataSetChanged();
        editText.setText("");

    }

    public void sigmaClick(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("УВЕДОМЛЕНИЕ")
                .setMessage("Мне все ваши эти уголы и гуголы в жизни не нужны!!")
                .setCancelable(false)
                .setNegativeButton("ОК",
                        (dialog, id) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.show();
    }


    public void drawPlot(View view) {
        try {
            EditText editTextTextPersonName7 = (EditText) findViewById(R.id.editTextTextPersonName7);
            EditText editTextTextPersonName8 = (EditText) findViewById(R.id.editTextTextPersonName8);
            EditText editTextTextPersonName9 = (EditText) findViewById(R.id.editTextTextPersonName9);
            EditText editTextTextPersonName10 = (EditText) findViewById(R.id.editTextTextPersonName10);
            Button button10 = (Button) findViewById(R.id.button10);
            GraphView graphView = (GraphView) findViewById(R.id.graph);
            if (graphView.getSeries() != null)
                graphView.removeAllSeries();
            String variable = editTextTextPersonName8.getText().toString();
            if (variable.length()!=1||!isLetter(variable.charAt(0))){
                throw new IllegalArgumentException("Неверный формат переменной!");
            }
            checkOnMinuses(editTextTextPersonName7.getText().toString());
            FunctionParser parser = new FunctionParser(editTextTextPersonName7.getText().toString(), variable.charAt(0));
            Function f = parser.parseFunction();
            double from = Double.parseDouble(editTextTextPersonName9.getText().toString());
            double to = Double.parseDouble(editTextTextPersonName10.getText().toString());
            double x = from;
            double y;
            double k = (to - from) / 10000;
            LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();
            for (int i = 0; i < 10000; ++i) {
                y = f.getValueAt(x);
                if (y < y_min)
                    y_min = y;
                if (y > y_max)
                    y_max = y;
                if (Double.isFinite(y) && !Double.isNaN(y))
                    series.appendData(new DataPoint(x, y), true, 10000);
                x += k;
            }
            graphView.addSeries(series);
            graphView.getGridLabelRenderer().setVerticalLabelsColor(Color.BLACK);
            graphView.getGridLabelRenderer().setHorizontalLabelsColor(Color.BLACK);
            graphView.getGridLabelRenderer().setGridColor(Color.BLACK);
            graphView.getViewport().setMinX(from-1);
            graphView.getViewport().setMaxX(to+1);
            graphView.getViewport().setMinY(y_min-1);
            graphView.getViewport().setMaxY(y_max+1);

            graphView.getViewport().setYAxisBoundsManual(true);
            graphView.getViewport().setXAxisBoundsManual(true);
        }
        catch (IllegalArgumentException e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Error!")
                    .setMessage(e.getMessage())
                    .setCancelable(false)
                    .setNegativeButton("ОК",
                            (dialog, id) -> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    private void checkOnMinuses(String text){
        text = throwBrackets(text);
        StringBuilder num = new StringBuilder();
        int i =0;
        if (text.charAt(i)!='-'){
            return;
        }
        ++i;
        while (i < text.length() && (isDigit(text.charAt(i)) || text.charAt(i) == '.')) {
            num.append(text.charAt(i));
            ++i;
        }
        double d1 = (-1)*Double.parseDouble(num.toString());
        if (i == 0 || d1>=0 || text.length()==i+1){
            return;
        }

        if (text.charAt(i)!='*'){
            return;
        }
        ++i;
        if (text.charAt(i)!='-'){
            return;
        }
        ++i;
        StringBuilder num2 = new StringBuilder();
        while (i < text.length() && (isDigit(text.charAt(i)) || text.charAt(i) == '.')) {
            num2.append(text.charAt(i));
            ++i;
        }
        d1 = (-1)*Double.parseDouble(num2.toString());
        if (num2.length()==0 || d1>=0 || i!=text.length()){
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("ПРЕДУПРЕЖДЕНИЕ")
                .setMessage(("Задумайтесь: что в вашей жизни могло пойти не так, чтобы вам " +
                        "понадобилось перемножать два отрицательных числа?"))
                .setCancelable(false)
                .setNegativeButton("ОК",
                        (dialog, id) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.show();
    }

    private String throwBrackets(String text){
        StringBuilder ret = new StringBuilder();
        for (int i =0; i < text.length();++i){
            if (text.charAt(i)!=')'&&text.charAt(i)!='('){
                ret.append(text.charAt(i));
            }
        }
        return ret.toString();
    }

    public void drawApprox(View view) {
        try {
            EditText editTextTextPersonName26 = (EditText) findViewById(R.id.editTextTextPersonName26);
            GraphView graphView = (GraphView) findViewById(R.id.lin_graph);
            if (graphView.getSeries() != null)
                graphView.removeAllSeries();
            double[][] values = TableParser.parseTable(editTextTextPersonName26.getText().toString());
            Approximation approximation = Approximation.getInstance();
            approximation.LS_linear(values[0], values[1]);
            double from = Math.min(0., -approximation.getA() / approximation.getB());
            double to = values[0][values[0].length - 1] + 1;
            double x = from;
            double y;
            double k = (to - from) / 10000;
            LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();
            PointsGraphSeries<DataPoint> points = new PointsGraphSeries<>();
            for (int i = 0; i < 10000; ++i) {
                y = approximation.getValueAt(x);
                series.appendData(new DataPoint(x, y), true, 10000);
                x += k;
            }
            for (int i = 0; i < values[0].length; ++i) {
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
        catch (IllegalArgumentException e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Error!")
                    .setMessage(e.getMessage())
                    .setCancelable(false)
                    .setNegativeButton("ОК",
                            (dialog, id) -> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
    public void getValue(View view) {
        try {
            EditText xEditText = (EditText) findViewById(R.id.editTextTextPersonName270);
            EditText yEditText = (EditText) findViewById(R.id.editTextTextPersonName277);
            Approximation approximation = Approximation.getInstance();
            yEditText.setText(Double.toString(approximation.getValueAt(Double.parseDouble(xEditText.getText().toString()))));
        }
        catch (IllegalArgumentException e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Error!")
                    .setMessage(e.getMessage())
                    .setCancelable(false)
                    .setNegativeButton("ОК",
                            (dialog, id) -> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
    public void executeDerivative(View view){
        try {
            EditText variableEditText = (EditText) findViewById(R.id.editTextTextPersonName12);
            String variable = variableEditText.getText().toString();
            if (variable.length()!=1||!isLetter(variable.charAt(0))){
                throw new IllegalArgumentException("Неверный формат переменной!");
            }
            EditText functionEditText = (EditText)findViewById(R.id.editTextTextPersonName13);
            String functionStr = functionEditText.getText().toString();
            FunctionParser functionParser = new FunctionParser(functionStr, variable.charAt(0));
            Function func = functionParser.parseFunction();
            Function f = func.derivative();
            EditText answer = findViewById(R.id.editTextTextPersonName14);
            answer.setText(f.toString());
        }
        catch (IllegalArgumentException e){
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Error!")
                    .setMessage(e.getMessage())
                    .setCancelable(false)
                    .setNegativeButton("ОК",
                            (dialog, id) -> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    public void executeDijkstra(View view){
        EditText graphWeightsEdit = (EditText) findViewById(R.id.editTextTextPersonName4);
        String graphWeights = graphWeightsEdit.getText().toString();
        try {
            if (graphWeights.equals("[]"))
                throw new IllegalStateException();
            GraphParser parser = new GraphParser(graphWeights);
            Graph graph = parser.parseGraph();
            EditText beginVertex = (EditText) findViewById(R.id.editTextTextPersonName11);
            EditText endVertex = (EditText) findViewById(R.id.editTextTextPersonName15);
            Pair<Double, ArrayList<Integer>> res = graph.Dijkstra(Integer.parseInt(beginVertex.getText().toString()),
                    Integer.parseInt(endVertex.getText().toString()));
            if (Double.isInfinite(res.first))
                throw new IllegalArgumentException("Нет пути между этими вершинами!");
            EditText len = (EditText) findViewById(R.id.editTextTextPersonName16);
            len.setText(res.first.toString());
            EditText route = (EditText) findViewById(R.id.editTextTextPersonName17);
            route.setText(res.second.toString());
        }
        catch (IllegalArgumentException e){
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Error!")
                    .setMessage(e.getMessage())
                    .setCancelable(false)
                    .setNegativeButton("ОК",
                            (dialog, id) -> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();
        }
        catch (IllegalStateException e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Так!")
                    .setMessage("От вашего сообщения веет недоброжелательностью!!!")
                    .setCancelable(false)
                    .setNegativeButton(":(",
                            (dialog, id) -> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();
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
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Error!")
                    .setMessage(e.getMessage())
                    .setCancelable(false)
                    .setNegativeButton("ОК",
                            (dialog, id) -> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();
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
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Error!")
                    .setMessage(e.getMessage())
                    .setCancelable(false)
                    .setNegativeButton("ОК",
                            (dialog, id) -> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();
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
        catch (IllegalArgumentException | IllegalStateException e){
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Error!")
                    .setMessage(e.getMessage())
                    .setCancelable(false)
                    .setNegativeButton("ОК",
                            (dialog, id) -> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();
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
            if (func instanceof Const & func.getValueAt(0)==0)
                throw new ArithmeticException("Дети! Думайте, а не смотрите на котика!");
            double result = Integral.integrate(func, Double.parseDouble(llimitEdit.getText().toString()),
                    Double.parseDouble(ulimitEdit.getText().toString()));
            if (Double.isInfinite(result))
                throw new IllegalArgumentException("Расходящийся интеграл!");
            if (Double.isNaN(result))
                throw new IllegalArgumentException("Пределы выходят за область определения!");
            String res = Double.toString(result);
            ans.setText(res);
        }
        catch (IllegalArgumentException | IllegalStateException e){
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Error!")
                    .setMessage(e.getMessage())
                    .setCancelable(false)
                    .setNegativeButton("ОК",
                            (dialog, id) -> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();
        }
        catch (ArithmeticException e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            ImageView image = new ImageView(this);
            int i = (int)(17 * Math.random() + 1);
            String name = "c" + i;
            int resID = getResources().getIdentifier(name, "drawable", getApplicationContext().getPackageName());
            image.setImageResource(resID);

            builder.setTitle("Мяу")
                    .setMessage(e.getMessage())
                    .setView(image)
                    .setNegativeButton("Галя, отмена!", (dialog, id) -> {
                        dialog.cancel();
                    } );
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    public void dictFind(android.view.View view){
        try {
            ImageFinder finder = new ImageFinder();
            TextView possibleRequests = (TextView) findViewById(R.id.textView31);
            possibleRequests.setVisibility(View.INVISIBLE);
            SubsamplingScaleImageView image = (SubsamplingScaleImageView) findViewById(R.id.imageView4);
            EditText text = (EditText) findViewById(R.id.editTextTextPersonName);
            String strText = text.getText().toString();
            if (strText.toLowerCase().equals("тфкп"))
                throw new IllegalStateException();
            Pair<String, ArrayList<String>> picture = finder.findPicture(strText);
            int maxsize = 47;
            if (picture.second.size() != maxsize) {
                image.setImage(ImageSource.resource(getImageId(this, picture.first)));
                if (picture.second.size() == 1) {
                    possibleRequests.setText("Возможно вы искали: " + picture.second.get(0));
                    possibleRequests.setVisibility(View.VISIBLE);
                } else if (picture.second.size() == 2) {
                    possibleRequests.setText("Возможно вы искали: " + picture.second.get(1) + ", " + picture.second.get(0));
                    possibleRequests.setVisibility(View.VISIBLE);
                }

            } else {
                possibleRequests.setText("Увы, ничего не найдено");
                possibleRequests.setVisibility(View.VISIBLE);
            }
        }
        catch (IllegalStateException e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            final String[] phrase = {"Не поооонял", "Ну что тут непонятного?!", "82 балла! А запрос как на 60..."};
            Random random = new Random();
            int index = random.nextInt(phrase.length);
            builder.setTitle("БЯКА!")
                    .setMessage(phrase[index])
                    .setCancelable(false)
                    .setNegativeButton("Что 'хорошо'? Нет, не хорошо!",
                            (dialog, id) -> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();

        }
    }
    private static int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }//https://stackoverflow.com/questions/6783327/setimageresource-from-a-string if you see this code delete it*/
}