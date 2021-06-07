package com.example.sigma72;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Parcelable;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

import org.joda.time.DateTime;
import org.joda.time.Weeks;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

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
import static java.lang.Math.abs;

public class MainActivity extends AppCompatActivity {

    public ArrayList<String> toDoList;
    public ArrayList<Task> OutputList;
    public ArrayAdapter arrayAdapter;
    public ListView listView;
    public EditText editDate;
    public Button button;
    public  EditText editText;
    private ArrayList<Notification> notes;
    private ArrayList<AlarmManager> alarms;
    private ArrayList<PendingIntent> pendings;
    private AlarmManager alarmManager;
    //private AlarmHelper alarmHelper;

    private int NOTIFICATION_ID = 1;

    //private NotificationHelper nHelp;

//    private EditText editTextD;

//    private void saveArrayList(String name, ArrayList<String> list) {
//        SharedPreferences prefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
//        SharedPreferences.Editor editor = prefs.edit();
//        StringBuilder sb = new StringBuilder();
//        for (String s : list) sb.append(s).append("<s>");
//        sb.delete(sb.length() - 3, sb.length());
//        editor.putString(name, sb.toString()).apply();
//    }
//
//    private ArrayList<String> loadArrayList(String name) {
//        SharedPreferences prefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
//        String[] strings = prefs.getString(name, "").split("<s>");
//        ArrayList<String> list = new ArrayList<>();
//        list.addAll(Arrays.asList(strings));
//        return list;
//    }

//    private SharedPreferences pref;
//    private void init(){
//        pref = getSharedPreferences("Planer",MODE_PRIVATE);
//
//    }

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
        if (savedInstanceState != null) {
            String[] values = savedInstanceState.getStringArray("myKey");
            if (values != null) {
                // arrayAdapter = new MyAdapter(values);
            }
        }
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);


        toDoList = new ArrayList<String>();
        OutputList = new ArrayList<Task>();
        notes = new ArrayList<>();
        alarms = new ArrayList<>();
        pendings = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, R.layout.list_view_layout, toDoList);
        listView = findViewById(R.id.ListView);
        editText = findViewById(R.id.editText);
        editDate = findViewById(R.id.textDate);
        //nHelp = new NotificationHelper(this);
        //AlarmHelper.getInstance().init(getApplicationContext());
        //alarmHelper = AlarmHelper.getInstance();
        try {
            maini();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static class Task implements Serializable {
        private String nameOfTask;
        public Date date;

        public Task(String name, Date inDate) {
            date = inDate;
            nameOfTask = name;
        }

        public String getName() {
            return nameOfTask;
        }

        public Date getDate() {
            return date;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setAlarm(Date data) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, data.getHours());
        calendar.set(Calendar.MINUTE, data.getMinutes());
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MONTH, data.getMonth());
        calendar.set(Calendar.DAY_OF_MONTH, data.getDay());
        calendar.set(Calendar.YEAR, data.getYear());
        startAlarm(calendar);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void startAlarm(Calendar calendar) {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //Intent intent = new Intent(this, AlarmReceiver.class);
        //PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        //alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
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

    public static boolean hasConnection(final Context context)
    {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        wifiInfo = cm.getActiveNetworkInfo();
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        return false;
    }

    public void showSchedule(View view){
        editDate = (EditText) findViewById(R.id.textDate);
        DateTime userDate;
        if (editDate.getText().toString().equals("")){
            userDate = DateTime.now();
        }
        else{
            try {
                Date temp = new SimpleDateFormat("dd.MM.yyyy").parse(editDate.getText().toString());
                int year = temp.getYear()+1900;
                int month = temp.getMonth()+1;
                int day = Integer.parseInt(editDate.getText().toString().substring(0, 2));
                editDate.setText("");
                userDate = new DateTime(year, month, day,
                       0, 0);
            }
            catch (ParseException e){
                userDate = DateTime.now();
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Error!")
                        .setMessage("Неправильный формат даты, по умолчанию выбран сегодняшний день. " +
                                "Формат ввода для расписания: dd.mm.year")
                        .setCancelable(false)
                        .setNegativeButton("ОК",
                                (dialog, id) -> dialog.cancel());
                AlertDialog alert = builder.create();
                alert.show();
            }
        }
        editText  = (EditText) findViewById(R.id.TextGroup);
        Parser parser = new Parser(view, this.getApplicationContext());
        StringBuilder url = new StringBuilder();
        String group = editText.getText().toString();
        editText.setText("");
        switch (group){
            case "6210":
                url.append("https://ssau.ru/rasp?groupId=531029067");
                break;
            case"6209":
                url.append("https://ssau.ru/rasp?groupId=531873000");
                break;
            case "6208":
                url.append("https://ssau.ru/rasp?groupId=531872999");
                break;
            case "6207":
                url.append("https://ssau.ru/rasp?groupId=531872998");
                break;
            default:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Error!")
                        .setMessage("Неправильный номер группы")
                        .setCancelable(false)
                        .setNegativeButton("ОК",
                                (dialog, id) -> dialog.cancel());
                AlertDialog alert = builder.create();
                alert.show();
                return;
        }

        DateTime beginDate = new DateTime(2021, 2, 8, 12, 0);
        Integer numOfWeeks = abs(Weeks.weeksBetween(userDate, beginDate).getWeeks())+1;
        Integer numOfDays = abs(userDate.getDayOfWeek()-beginDate.getDayOfWeek())+1;
        url.append("&selectedWeek="+numOfWeeks.toString()+"&selectedWeekday="+numOfDays.toString());
        try {
            if (numOfDays==7){
                throw new IllegalArgumentException("");
            }
            parser.execute(url.toString());
        }
        catch (IllegalArgumentException zeroTasks){
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Error!")
                    .setMessage("В воскресенье занятий нет")
                    .setCancelable(false)
                    .setNegativeButton("ОК",
                            (dialog, id) -> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();
            return;
        }
        catch (Exception e){

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Error!")
                    .setMessage("Не получается подключиться к сайту")
                    .setCancelable(false)
                    .setNegativeButton("ОК",
                            (dialog, id) -> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();
            return;
        }


    }

    private void cancelAlarm() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //Intent intent = new Intent(this, AlarmReceiver.class);
       // PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

      //  alarmManager.cancel(pendingIntent);
    }

    public void sendToChannel(String title, String message) {
       // NotificationCompat.Builder nb = nHelp.getChannelNotification(title, message);
       // nHelp.getManager().notify(1, nb.build());
    }

    public class Parser extends AsyncTask<String, Void, Document> {


        public ArrayList<ArrayList<String>> value = new ArrayList<ArrayList<String>>();
        private View view;
        private Context context;

        public Parser(View v, Context c){
            context = c;
            view = v;
        }

        private ArrayList<ArrayList<String>> getText(Document page) throws IOException{//returns arraylist of dates, times, subjects, offices
            Elements temp = page.getElementsByAttributeValue("class", "card-default timetable-card");
            ArrayList<ArrayList<String>> res = new ArrayList<>();
            //System.out.println(temp.toString());
            Element date = page.getElementsByAttributeValue("class", "week-nav-current_date").first();
            res.add(new ArrayList<>());
            res.get(0).add(date.toString().substring(38, 48));
            Element timetable = temp.first();
            temp = timetable.getElementsByAttributeValue("class", "schedule__time-item");
            res.add(new ArrayList<>());
            for (Element t: temp){
                res.get(1).add(t.toString().substring(36, 42));
            }
            temp = timetable.getElementsByAttributeValue("class", "schedule__item schedule__item_show");
            res.add(new ArrayList<>());
            res.add(new ArrayList<>());
            for (Element t: temp){
                if (t.toString().charAt(49)!='/'){
                    String text = t.toString();
                    int beginIndex = 0;
                    int endIndex = 0;
                    for (int i = 0; i < text.length(); ++i){
                        if (isRu(text.charAt(i))&&beginIndex==0){
                            beginIndex=i;
                        }
                        if (beginIndex!=0&&endIndex==0&&!(text.charAt(i)==' ')&&!isRu(text.charAt(i))){
                            endIndex=i;
                        }
                    }
                    res.get(2).add(t.toString().substring(beginIndex, endIndex));
                }
                else{
                    res.get(2).add("");
                }
            }
            for (Element t: temp){
                String text = t.toString();
                int beginIndex = 0;
                int endIndex = 0;
                if (text.indexOf("place")!=-1){
                    beginIndex = text.indexOf("place")+12;//12 - is a length of 'place">'+1+length of spaces
                    endIndex = (text.substring(text.indexOf("place"))).indexOf("<")+beginIndex-15;//15 - is a is a length of 'place">'+ length of "\n<"
                }
                res.get(3).add(text.substring(beginIndex, endIndex));
            }
            return res;
        }

        boolean isRu(char i){
            if (i>='А'&&i<='Я' || i>='а'&& i<='я'){
                return true;
            }
            return false;
        }

        @Override
        protected Document doInBackground(String... voids) {
            Document page = null;
            String str = voids[0];
            try {
                if (hasConnection(context)) {
                    page = Jsoup.connect(str).get();
                }
            }
            catch (IOException e){

            }
            return page;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onPostExecute(Document result) {
            try {
                if (!hasConnection(context)){
                    throw new IllegalArgumentException("");
                }
                value = getText(result);

                ArrayList<ArrayList<String>> list = this.value;

                for (int i =0; i< list.get(1).size()/2; ++i) {
                    if (!list.get(2).get(i).equals("")) {
                        String str = new String(" " +
                                list.get(2).get(i) + " " + list.get(3).get(i));
                        DateTime date = DateTime.now();
                        String hours = list.get(1).get(i * 2 ).substring(0, 2);
                        String mins = list.get(1).get(i * 2 ).substring(3, 5);
                        editText = (EditText) findViewById(R.id.editText);
                        editText.setText(str);
                        editDate = (EditText) findViewById(R.id.textDate);
                        int day = date.getDayOfMonth();
                        String dayStr;
                        if (day<10){
                            dayStr = "0"+String.valueOf(day);
                        }
                        else{
                            dayStr = String.valueOf(day);
                        }
                        int month = date.getMonthOfYear();
                        String monthStr;
                        if (day<10){
                            monthStr = "0"+String.valueOf(month);
                        }
                        else{
                            monthStr = String.valueOf(month);
                        }
                        editDate.setText(dayStr+"."+monthStr+"."
                                +date.getYear()+" "+hours+ ":"+mins);

                        addItemToList(this.view);

                    }
                }
            }
            catch (IOException e){

            }
            catch (ParseException e){

            }
            catch (IllegalArgumentException e){
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Ошибка!")
                        .setMessage("Не удается подключиться к сайту, проверьте интернет соединение")
                        .setCancelable(false)
                        .setNegativeButton("ОК",
                                (dialog, id) -> dialog.cancel());
                AlertDialog alert = builder.create();
                alert.show();
            }

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void addItemToList (View view) throws ParseException {
        listView = findViewById(R.id.ListView);
        editText = (EditText) findViewById(R.id.editText);
        String str = editText.getText().toString();
        editDate = (EditText) findViewById(R.id.textDate);
        String date1 = editDate.getText().toString();
        if (str.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Ошибка!")
                    .setMessage("Добавьте текст")
                    .setCancelable(false)
                    .setNegativeButton("ОК",
                            (dialog, id) -> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            if (date1.equals("")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Ошибка!")
                        .setMessage("Добавьте дату")
                        .setCancelable(false)
                        .setNegativeButton("ОК",
                                (dialog, id) -> dialog.cancel());
                AlertDialog alert = builder.create();
                alert.show();
            } else {

    }
                Date date = null;
                int iftry = 0;
                try {
                    date = new SimpleDateFormat("dd.MM.yyyy HH:mm").parse(date1);

                } catch (ParseException e) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Ошибка!")
                            .setMessage("Неверный формат даты")
                            .setCancelable(false)
                            .setNegativeButton("ОК",
                                    (dialog, id) -> dialog.cancel());
                    AlertDialog alert = builder.create();
                    alert.show();
                    iftry = iftry + 1;
                }
                if (iftry == 0) {
                    String DATE_FORMAT = "dd.MM.yyyy HH:mm";
                    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
                    String d1 = sdf.format(date);
                    if (!d1.equals( date1)) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Ошибка!")
                                .setMessage("Неверный формат даты")
                                .setCancelable(false)
                                .setNegativeButton("ОК",
                                        (dialog, id) -> dialog.cancel());
                        AlertDialog alert = builder.create();
                        alert.show();
                    } else {
                        toDoList.clear();
                        OutputList.add(new Task(str, date));
                        Collections.sort(OutputList, new Comparator<Task>() {  //непосредственно сортировка
                            public int compare(Task o1, Task o2) {
                                return o1.date.compareTo(o2.date);
                            }
                        });
                        OutputList.forEach(new Consumer<Task>() {
                            @Override
                            public void accept(Task task) {
                                String DATE_FORMAT = "dd.MM.yyyy HH:mm";
                                SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
                                toDoList.add(sdf.format(task.date) + "  " + task.nameOfTask);
                                try {
                                    maino();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        listView.setAdapter(arrayAdapter);
                        arrayAdapter.notifyDataSetChanged();
                        editText.setText("");
                        editDate.setText("");


                        NotificationCompat.Builder builder =
                                new NotificationCompat.Builder(MainActivity.this, "Sigma")
                                        .setContentTitle("Напоминание")
                                        .setContentText(str)
                                        .setSmallIcon(R.drawable.c15)
                                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            builder.setChannelId("com.example.sigma72");
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            NotificationChannel channel = new NotificationChannel(
                                    "com.example.sigma72",
                                    "Sigma710",
                                    NotificationManager.IMPORTANCE_DEFAULT
                            );
                            if (notificationManager != null) {
                                notificationManager.createNotificationChannel(channel);
                            }
                        }
                        notificationManager.notify(NOTIFICATION_ID,builder.build());
                        ++NOTIFICATION_ID;

                        //setAlarm(OutputList.get(0).getDate());
                        //alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        //alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, 5000, 0, );

                        //new Alarm().onReceive(getApplicationContext(), new Intent(str));

                    }
                }
            }
        }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void maino() throws IOException {
        File dir = new File(getApplicationContext().getFilesDir(), "for_smth");
        if(!dir.exists()){
            dir.mkdir();
        }
        File gpxfile = new File(dir, "temp.txt");
        FileOutputStream fos = new FileOutputStream(gpxfile);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(OutputList.size());
        /*OutputList.forEach(new Consumer<Task>() {
            @Override
            public void accept(Task task) {
                try {
                    oos.writeObject(task);
                }
                catch (IOException e){

                }
            }
    });*/
        for (int i = 0; i < OutputList.size(); ++i) {
            oos.writeObject(OutputList.get(i));
        }
        oos.flush();
        oos.close();
        fos.close();
    }

    public  void maini() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("/data/user/0/com.example.sigma72/files/for_smth/temp.txt");
        ObjectInputStream oin = new ObjectInputStream(fis);
        int size = (Integer) oin.readObject();
        Task ts;
        for (int i=0; i<size; ++i){
            ts = (Task) oin.readObject();
            OutputList.add(ts);
        }
        oin.close();
        fis.close();
    }
    

    @RequiresApi(api = Build.VERSION_CODES.N)
    public  void  Show (View view){
        if (OutputList.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Ой")
                    .setMessage("Не понятно что добавить ")
                    .setCancelable(false)
                    .setNegativeButton("Сорри",
                            (dialog, id) -> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            toDoList.clear();
            listView = findViewById(R.id.ListView);
            listView.setAdapter(arrayAdapter);
            OutputList.forEach(new Consumer<Task>() {
                @Override
                public void accept(Task task) {
                    String DATE_FORMAT = "dd.MM.yyyy HH:mm";
                    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
                    toDoList.add(sdf.format(task.date) + "  " + task.nameOfTask);
                }
            });
        }
    }

    public void Clear (View view){
        if (toDoList.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Эй!")
                    .setMessage("Ты че удалять собрался?")
                    .setCancelable(false)
                    .setNegativeButton("А?",
                            (dialog, id) -> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            listView = findViewById(R.id.ListView);
            listView.setAdapter(arrayAdapter);
            toDoList.clear();
            OutputList.clear();
        }
        //        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                TextView textView =(TextView) view;
//                textView.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//            }
//        });
    }

    public void showHelp(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Help!")
                .setMessage(
                        "Функции:\n" +
                        "-сумма: +\n" +
                        "-разность: -\n" +
                        "-произведение: * (в том числе для умножения на константу)\n" +
                        "-частное: /\n" +
                        "-тригонометрические функции: sin(), cos(), tg(), ctg()\n" +
                        "-обратные тригонометрические функции: arcsin(), arccos(), arctg(), arcctg()\n" +
                        "-гиперболические функции: sh(), cosh(), th(), cth()\n" +
                        "-степень: ^\n" +
                        "-логарифм: log_a(), a - основание логарифма\n" +
                        "-модуль: abs()\n" +
                        "-математические константы: e, pi\n" +
                        "\nМатрица/граф\n" +
                        "[a11, a12, ..., a1n; a21, a22, ..., a2n; ... ; an1, an2, ..., ann]\n" +
                        "\nНабор точек\n" +
                        "[x1, y1; x2, y2; …; xn, yn]")
                .setCancelable(false)
                .setPositiveButton("Mersi",
                        (dialog, id) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
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
            double y_min = Double.POSITIVE_INFINITY;
            double y_max = Double.NEGATIVE_INFINITY;
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
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
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