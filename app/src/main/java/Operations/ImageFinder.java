package Operations;

import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ImageFinder {///TM поиск по справочнику
    private Map<String, String[]> data_ = new HashMap<>();//String - название картинки, в String[0] - полное название содержимого картинки, далее - ключевые слова

    public ImageFinder() {
        data_.put("trig_formulas", new String[]{"формулы тригонометрии", "тригонометри", "формул",
                "умножени", "сложени", "кратн", "произведени", "сумм"});
        data_.put("alphabet", new String[]{"алфавит", "алфавит", "букв", "латинск", "греческ"});
        data_.put("avg", new String[]{"среднее значение ряда", "средн", "арифметическ", "геометрическ", "значени", "гармоническ"});
        data_.put("cast_trigonometry", new String[]{"формулы приведения", "формул", "приведени", "знак", "тригонометри"});
        data_.put("combinatorics", new String[]{"комбинаторика", "сочетани", "размещени", "перестановк", "повторени", "комбинаторик"});
        data_.put("complex_args", new String[]{"функции комплексных аргументов", "функци", "комплекс", "аргумент"});
        data_.put("complex_nums", new String[]{"комплексные числа", "комплекс", "числ", "мним", "эйлер", "муавр", "действит"});
        data_.put("constant", new String[]{"математические константы", "радиан", "конст", "математическ" , "непер"});
        data_.put("curve2", new String[]{"кривые 2 порядка", "фокальн", "эксцетриситет", "крив", "вторы","второ",
                "втора", "поряд", "2", "окружност", "эллипс", "гипербол", "парабол"});
        data_.put("degree_decrease", new String[]{"формулы понижения степени", "формул", "понижени", "степен"});
        data_.put("derivative", new String[]{"таблица производных", "производн", "таблиц", "определен"});
        data_.put("det", new String[]{"определитель", "определител", "детерминант", "свойств", "операци"});
        data_.put("diff_equations", new String[]{"дифференциальные уравнения", "дифффернциальн", "уравнен", "бернулл",
                "однородн", "линейн", "разделяющ", "ду"});
        data_.put("differentiation", new String[]{"дифференциальное исчисление", "градиент", "диффернциал", "исчислени", "произв", "направлени"});
        data_.put("differentiation_rules", new String[]{"правила дифференцирования", "правил", "диффернцирован", "лейбниц", "производн"});
        data_.put("distance", new String[]{"расстояние", "расстояни", "точк", "прям", "плоскост"});
        data_.put("factorial", new String[]{"факториал", "факториал", "стирлинг", "двойн"});
        data_.put("func_properties", new String[]{"свойства функций", "касательн", "нормал", "экстремум", "выпукл", "вогнутост", "функци", "перегиб", "крив"});
        data_.put("half_angle_formula", new String[]{"формулы половинного угла", "формул", "половин", "угол", "угл"});
        data_.put("inf_small", new String[]{"бесконечно малые", "бесконеч", "мал"});
        data_.put("integral", new String[]{"интеграл", "интеграл", "таблиц"});
        data_.put("integral_rules", new String[]{"правила интегрирования", "интеграл", "эйлер", "правил", "неопределен",
                "коэффиц", "эйлер", "част", "симпсон", "ньютон", "лейбниц"});
        data_.put("limits", new String[]{"пределы", "предел", "замечат", "свойств"});
        data_.put("log", new String[]{"логарифмы", "логарифм", "тождеств", "непер", "бриг", "основн", "определен"});
        data_.put("math_funcs", new String[]{"обозначения функций", "математи", "функци", "обознач"});
        data_.put("math_signs", new String[]{"математические обозначения", "математи", "обознач"});
        data_.put("matrix", new String[]{"матрицы", "матриц", "операц", "лаплас", "гаусс"});
        data_.put("module", new String[]{"модуль", "модул", "свойств"});
        data_.put("newton_symbol", new String[]{"символ Ньютона", "символ", "ньютон"});
        data_.put("one_angle_formulas", new String[]{"формулы 1 угла", "формул", "функци", "1", "одно", "угол", "угл", "связ"});
        data_.put("phase_portraits", new String[]{"фазовые портреты", "седло", "центр", "фокус", "узел", "узл", "устойчив", "фаз"});
        data_.put("plane", new String[]{"плоскость", "плоскост", "уравнен", "общ", "канонич"});
        data_.put("power", new String[]{"возведение в степень", "степен", "свойств"});
        data_.put("probability", new String[]{"вероятности", "лаплас", "байес", "пуассон", "бернулл",
                "вероятност", "дисперси", "ожидани", "биномиальн", "распределен", "плотност"});
        data_.put("progression", new String[]{"прогресии", "прогресс", "арифмет", "геометрич", "бесконеч", "убываю", "сумм"});
        data_.put("proportion", new String[]{"пропорции", "пропорци"});
        data_.put("root", new String[]{"арифметический корень", "корен", "корн", "свойств", "арифмет"});
        data_.put("series", new String[]{"ряды", "коши", "даламбер", "ряд", "лейбниц", "тейлор", "маклорен",
                "сходи", "степен", "разложен"});
        data_.put("series_table", new String[]{"таблица рядов", "таблиц", "степен", "разлож", "ряд", "функци"});
        data_.put("short_mult", new String[]{"формулы сокращенного умножения", "бином", "ньютон", "сокращен", "умножен", "формул"});
        data_.put("straight", new String[]{"прямая", "прям", "уравнени", "общ", "параметр", "канон"});
        data_.put("trig_funcs", new String[]{"обратные тригонометрические функции", "обрат", "тригонометр", "функци"});
        data_.put("trig_substitution", new String[]{"универсальная тригонометрическая подстановка",
                "универсальн", "тригонометрич", "подстановк"});
        data_.put("trig_theorems", new String[]{"формулы косоугольных треугольников", "теорем", "косинус", "синус", "тангенс", "формул",
                "косоугольн", "треугольник"});
        data_.put("trigonometry", new String[]{"тригонометрия", "тригонометр", "таблиц", "значен", "функци", "поняти", "радиан"});
        data_.put("uncertainties", new String[]{"неопределенности", "лопитал", "неопределнност", "раскрыти",
                "золот", "отношен", "многочлен"});
        data_.put("vector", new String[]{"вектор", "скалярн", "смешанн", "точк", "модул"});
    }

    public Pair<String, ArrayList<String>> findPicture(String request) {//поиск нужной картинки
        request = request.toLowerCase();
        int curPoints = 0;
        int maxPoints = 0;
        ArrayList<String> possibleRequests = new ArrayList<>();
        Pair<String, ArrayList<String>> ans = new Pair<>(null, possibleRequests);
        if (checkConsts(request)){
            return new Pair<>("constant", possibleRequests);
        }
        for (Map.Entry entry : data_.entrySet()) {
            String[] value = (String[]) entry.getValue();
            String key = (String) entry.getKey();
            if (request == value[0]) {//Проверка на полное совпадение с полным названием картинки в map
                return new Pair<>(key, null);
            }
            for (int i = 0; i < value.length; ++i) {
                if (request.contains(value[i])) {
                    curPoints++;
                }
            }

            if (curPoints == maxPoints) {
                possibleRequests.add(value[0]);
            } else if (curPoints > maxPoints) {
                possibleRequests.clear();
                ans = new Pair<>(key, possibleRequests);
                maxPoints = curPoints;
            }
            curPoints = 0;

        }
        return ans;
    }


    private boolean checkConsts(String request) {//спец. проверка на константы
        if (request.equals("пи") || request.equals("e") || request.equals("е") || request.equals("pi") || request.equals("рад") || request.equals("rad")) {
            return true;
        }
        return false;
    }

}//\TM


