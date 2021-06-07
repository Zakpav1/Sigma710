package functions.parsers;

import functions.Function;
import functions.basic.*;
import functions.meta.*;
import android.util.Pair;

import java.lang.reflect.InvocationTargetException;
import java.util.*;


import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;

public class FunctionParser {//TM парсер функции

    private Map<String, Function > funcNamesMap = new HashMap<>();
    private String[] funcNames = new String[]
            {"arccos", "arcctg", "arcsin", "arctan", "cos", "ch", "ctg", "sin", "sh", "th", "tg", "cth", "abs"};

    private char[] ops = new char[]{'+', '-', '*', '/', '^'};

    private Map<Character, Function> opsNamesMap = new HashMap<>();

    private String expression_;

    private char variable_;

    public FunctionParser(String expression, char variable){
        expression_=expression;
        variable_=variable;

        opsNamesMap.put('+', new Sum(new Const(1), new Const(2))); //добавление в словарь, связвающий названия операций с классами операций
        opsNamesMap.put('-', new Sub(new Const(1), new Const(2)));
        opsNamesMap.put('*', new Mult(new Const(1), new Const(2)));
        opsNamesMap.put('/', new Div(new Const(1), new Const(2)));
        opsNamesMap.put('^', new Pow(new Const(1), new Const(2)));

        funcNamesMap.put("arccos", new Acos());//добавление в словарь, связвающий названия функций с классами функций
        funcNamesMap.put("arcsin", new Asin());
        funcNamesMap.put("arcctg", new Acot());
        funcNamesMap.put("arctg", new Atan());
        funcNamesMap.put("cos", new Cos());
        funcNamesMap.put("sin", new Sin());
        funcNamesMap.put("tg", new Tan());
        funcNamesMap.put("ctg", new Cot());
        funcNamesMap.put("ch", new Cosh());
        funcNamesMap.put("sh", new Sinh());
        funcNamesMap.put("th", new Tanh());
        funcNamesMap.put("cth", new Coth());
        funcNamesMap.put("abs", new Abs());
    }

    public Function parseFunction(){//вызов этой функции вызывается из main
        checkSpaces();
        expression_ = expression_.replaceAll(" ", "");
        checkCorrect(expression_);
        Function f = parse(expression_);
        return f;
    }

    private Function parse(String expression){//разбиение на функции
        List<Function> funcs = new ArrayList<>();
        List<Character> ops = new ArrayList<>();
        int wasAnOp = 1; //переменная состояний знака - (необходимо для корректной работы напр. -9)
        for (int i = 0; i < expression.length(); ++i){
            if (expression.charAt(i)=='(') {//если встретил (, запуск parse от (...)
                if (wasAnOp==2) {
                    funcs.add(new Sub(new Const(0), parse(expression.substring(i, findBracket(expression, i + 1) - 1))));
                    wasAnOp=0;
                }
                else {
                    funcs.add(parse(expression.substring(i+1, findBracket(expression, i + 1))));
                    wasAnOp=0;
                }
                i=findBracket(expression, i+1);
            }
            else if (inOps(expression.charAt(i))!=-1){ //если встретил операцию
                if (expression.charAt(i)=='-'&&wasAnOp==1){
                    wasAnOp=2;
                }
                else {
                    wasAnOp = 1;
                    ops.add(expression.charAt(i));
                }
            }
            else{
                Pair<Function, Integer> val = recognizeFunc(expression, i); // распознавание функции
                if (wasAnOp==2) {//проверка для выражения типа +-function(замена на +(0-function))
                    funcs.add(new Sub(new Const(0), val.first));
                    wasAnOp=0;
                }
                else{
                    funcs.add(val.first);
                    wasAnOp = 0;
                }
                i=val.second;
            }
        }

        return buildFunc(funcs, ops);//запуск построения выражения на основе имеющихся списков
    }

    private Function buildFunc(List<Function> funcs, List<Character> ops){
        Class[] params = {Function.class, Function.class};
        while (funcs.size()>1) {//выходим если размер = 0, ответ в funcs[0]
            if (funcs.size() == 2) {//если 2 функции в списке
                try {
                    funcs.add(getOp(ops.get(0)).getClass().getConstructor(params).newInstance(funcs.get(0), funcs.get(1)));
                }
                catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
                    System.err.println("Error after split");
                }
                funcs.remove(0);
                funcs.remove(0);
                ops.remove(0);
            } else {
                if (inOps(ops.get(0)) >= inOps(ops.get(1))) {//если 0 операция главнее 1-й в списке то соединяем 1-е две функции
                    Function fresh = new Sin();
                    try {
                        fresh = getOp(ops.get(0)).getClass().getConstructor(params).newInstance(funcs.get(0), funcs.get(1));
                    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
                        System.err.println("Error after split");
                    }
                    funcs.remove(0);
                    funcs.set(0, fresh);
                    ops.remove(0);
                } else {//если 1-я операция главнее 0-й то ищем 1-ю операцию, равную по статусу 0-й и запуск buildFunc
                    // от части списка от 0 до найденного

                    Function fresh = new Sin();
                    fresh = buildFunc(funcs.subList(1, 1+findEqualOp(ops, ops.get(1))),
                            ops.subList(1, findEqualOp(ops, ops.get(1))));
                    funcs.set(1, fresh);//установка найденной функции на 1-ю позицию

                }
            }
        }
        return funcs.get(0);
    }

    private int findEqualOp(List<Character> ops, char op){//поиск равной по статусу операции
        int status = inOps(op);
        if (op=='+'||op=='*'){
            ++status;
        }
        for (int i =2; i < ops.size(); ++i){
            if (inOps(ops.get(i))<=status){
                return i;
            }
        }
        return ops.size();
    }

    private Function getOp(char c){//возврат экземпляра функции операции равной по смыслу символу c на входе
        for (Map.Entry entry : opsNamesMap.entrySet()) {
            char op = (char) entry.getKey();
            if (op == c){
                return (Function)entry.getValue();
            }
        }
        return opsNamesMap.get('+');
    }

    private Pair<Function, Integer> recognizeFunc(String expression, int i) {//распознавание типа функции
        if (isLetter(expression.charAt(i)) && expression.charAt(i) != 'e' &&
                logCheck(expression, i) == 0 &&
                (expression.charAt(i) != variable_ || expression.length() - 1 != i && isLetter(expression.charAt(i + 1)))
                && (expression.length() - i >= 2 && !expression.substring(i, i + 2).equals("pi") || expression.length() - i < 2)) {

            for (Map.Entry entry : funcNamesMap.entrySet()) {//проверка на функции из словаря
                String str = (String) entry.getKey();
                Function function = (Function) entry.getValue();
                if (str.length() < expression.length() - i &&
                        str.equals(expression.substring(i, i + str.length()))) {
                    i += str.length();

                    int temp = findBracket(expression, i);

                    try {
                        return new Pair<Function, Integer>(new Comp((Function) entry.getValue().getClass().newInstance(),
                                parse(expression.substring(i + 1, temp))), temp);
                    } catch (InstantiationException | IllegalAccessException e) {
                        System.err.println("Exception in returning function");
                    }


                }
            }
        } else if (expression.length() - i >= 2 && expression.substring(i, i + 2).equals("pi"))// проверка на пи
        {
            return new Pair<Function, Integer>(new Const(Math.PI), i+1);
        } else if (expression.charAt(i) == 'e') {//проверка на е
            return new Pair<Function, Integer>(new Const(Math.E), i);
        } else if (expression.charAt(i) == variable_) {//проверка на переменную
            return new Pair<Function, Integer>(new Polynomial(new double[]{1, 0}), i);
        } else if (isDigit(expression.charAt(i))) {//проверка на число
            StringBuilder num = new StringBuilder();
            while (i < expression.length() && (isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                num.append(expression.charAt(i));
                ++i;
            }
            double d = Double.parseDouble(num.toString());
            --i;
            return new Pair<Function, Integer>(new Const(d), i);
        } else {//проверка на логарифм
            return getLog(expression, i);
        }
        return new Pair<Function, Integer>(new Const(0), i);
    }

    private void checkSpaces(){//проверка на пробелы напр. 2 + 1 2
        int s =0;
        for (int i = 0; i < expression_.length(); ++i){
            if (isDigit(expression_.charAt(i))&&s == 2){
                throw new IllegalArgumentException("Недопустимая функция! (Wrong function!)");
            }
            else if (isDigit(expression_.charAt(i))) {
                s = 1;
            }
            else if (expression_.charAt(i)==' '&&s==1){
                s=2;
            }
            else{
                s =0 ;
            }
        }
    }

    private void checkCorrect(String expression){ //проверка на правильность выражения
        if (!checkBrackets()){
            throw new IllegalArgumentException("Недопустимая функция! (Wrong function!)");
        }
        boolean isLastAnOp = true;
        boolean isLastANum = false;
        for (int i = 0; i < expression.length(); ++i){//проход по символам

            if (expression.charAt(i)=='('){
                checkCorrect(expression.substring(i+1, findBracket(expression, i)));
                i=findBracket(expression, i)+1;
                if (i>=expression.length()){
                    return;
                }
            }
            else if (!isLetter(expression.charAt(i))&&!isDigit(expression.charAt(i))&&inOps(expression.charAt(i))==-1){//если ! или тп
                throw new IllegalArgumentException("Недопустимая функция! (Wrong function!)");
            }
            if (isLetter(expression.charAt(i)) && expression.charAt(i)!='e' &&
                    logCheck(expression, i)==0 &&
                    (expression.charAt(i)!=variable_||expression.length()-1!=i&&isLetter(expression.charAt(i+1)))
                    &&(expression.length()-i>=2&&!expression.substring(i, i+2).equals("pi")||expression.length()-i<2)){//блок для проверки функций
                if(isLastAnOp==false){
                    throw new IllegalArgumentException("Недопустимая функция! (Wrong function!)");
                }
                int j =0;
                for (; j < funcNames.length; ++j){
                    if (funcNames[j].length()<expression.length()-i &&
                            funcNames[j].equals(expression.substring(i, i+funcNames[j].length()))){
                        i+=funcNames[j].length();
                        if (expression.charAt(i)!='('){
                            throw new IllegalArgumentException("Недопустимая функция! (Wrong function!)");
                        }
                        else {
                            checkCorrect(expression.substring(i+1, findBracket(expression, i)));
                            i=findBracket(expression, i);
                            if (i>=expression.length()){
                                return;
                            }
                            j = 20;
                        }
                    }
                    if (j==funcNames.length-1){
                        throw new IllegalArgumentException("Недопустимая функция! (Wrong function!)");
                    }

                }
            }
            else if(expression.length()-i>=2&&expression.substring(i, i+2).equals("pi"))// проверка на пи
            {
                if(isLastAnOp==false){
                    throw new IllegalArgumentException("Недопустимая функция! (Wrong function!)");
                }
                isLastAnOp=false;
                isLastANum=false;
                i+=1;
                if (i>=expression.length()) {
                    return;
                }
            }
            else if (inOps(expression.charAt(i))!=-1){//проверка на операции
                if (i<expression.length()-1&&(i!=0||expression.charAt(i)=='-')){
                    isLastAnOp=true;
                    isLastANum=false;
                    checkCorrect(expression.substring(i+1, expression.length()));
                    return;
                }
                else{
                    throw new IllegalArgumentException("Недопустимая функция! (Wrong function!)");
                }
            }
            else if (expression.charAt(i)=='e'||expression.charAt(i)==variable_){
                if(isLastAnOp==false){
                    throw new IllegalArgumentException("Недопустимая функция! (Wrong function!)");
                }
                isLastAnOp = false;
                isLastANum=false;
            }
            else if (isDigit(expression.charAt(i))){
                if(isLastANum!=true &&isLastAnOp!=true){
                    throw new IllegalArgumentException("Недопустимая функция! (Wrong function!)");
                }
                StringBuilder num = new StringBuilder();
                while (i<expression.length()&&(isDigit(expression.charAt(i))||expression.charAt(i)=='.')){
                    num.append(expression.charAt(i));
                    ++i;
                }
                try {
                    double d = Double.parseDouble(num.toString());
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Недопустимая функция! (Wrong function!)");
                }
                isLastAnOp = false;
                isLastANum=true;
                --i;
            }
            else if (logCheck(expression, i)==0){
                throw new IllegalArgumentException("Недопустимая функция! (Wrong function!)");
            }
            else{
                i+=logCheck(expression,  i)-1;
                if (i>=expression.length()){
                    return;
                }
            }

        }
        return;
    }

    private Pair<Function, Integer>  getLog(String expression, int i){// получение логарифма
        double d;
        i+=4;
        if (expression.charAt(i)=='e'){
            i+=2;
            d = Math.E;
        }
        else if (expression.charAt(i)=='p'&&expression.charAt(i+1)=='i'){
            i+=3;
            d = Math.PI;
        }
        else{
            StringBuilder num = new StringBuilder();
            while (i<expression.length()&&expression.charAt(i)!='('){
                num.append(expression.charAt(i));
                ++i;
            }
            d = Double.parseDouble(num.toString());
            ++i;
        }
        return new Pair<Function, Integer>(new Comp(new Log(d), parse(expression.substring(i, findBracket(expression, i)))),
                findBracket(expression, i));

    }

    private int logCheck(String expression, int i){ //проверка на логарифм
        if (expression.length()-i>=8&&expression.substring(i, i+4).equals("log_")){
            i+=4;
            if (expression.charAt(i)=='e'&&expression.charAt(i+1)=='('){
                i+=2;
            }
            else if (expression.charAt(i)=='p'&&expression.charAt(i+1)=='i'&&expression.charAt(i+2)=='('){
                i+=3;
            }
            else if (!isDigit(expression.charAt(i))){
                throw new IllegalArgumentException("Недопустимая функция! (Wrong function!)");
            }
            else{
                StringBuilder num = new StringBuilder();
                while (i<expression.length()&&expression.charAt(i)!='('){
                    num.append(expression.charAt(i));
                    ++i;
                }
                try {
                    double d = Double.parseDouble(num.toString());
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Недопустимая функция! (Wrong function!)");
                }
                ++i;
            }

            checkCorrect(expression.substring(i, findBracket(expression, i)));
            return findBracket(expression, i)+1;
        }
        else {
            return 0;
        }
    }

    private int inOps(char s){ // проверка на принадлежность массиву операций
        for (int i =0; i<ops.length; ++i){
            if (s==ops[i]){
                return i;
            }
        }
        return -1;
    }

    private boolean checkBrackets(){ //проверка на корректное кол-во скобок
        int brackets = 0;
        int lastInd = 0;
        for (int i = 0; i < expression_.length() && brackets>=0; ++i){
            if (expression_.charAt(i)=='('){
                ++brackets;
                lastInd = i;
            }
            if (expression_.charAt(i)==')'){
                --brackets;
                if (i-lastInd==1){
                    brackets=-1;
                }
            }
        }
        if (brackets==0){
            return true;
        }
        return false;
    }

    private int findBracket(String expression, int index){ // возвращает индекс закрывающей скобки, принимает индекс следующий после открывающей
        int brackets = 0;
        int i =1;
        for (; i < expression.length()-index && brackets>=0; ++i){
            if (expression.charAt(i+index)=='('){
                ++brackets;
            }
            if (expression.charAt(i+index)==')'){
                --brackets;
            }
        }
        return i+index-1;
    }

}//\TM
