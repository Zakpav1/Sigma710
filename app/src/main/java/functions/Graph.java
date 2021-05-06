package functions;

import android.util.Pair;

import java.util.*;

public class Graph {
    private double[][] weights_matrix; // матрица весов ребер
    private int n; // количество вершин

    public Graph (double[][] m, int s) {
        weights_matrix = m;
        n = s;
        lengths = new double[s][s];
        ways = new ArrayList[s][s];
    }

    private double[][] lengths; //матрица кратчайших путей (длин)
    private ArrayList<Integer>[][] ways; //матрица маршрутов для кратчайших путей

    public Pair<Double, ArrayList<Integer>> Dijkstra(int from, int to) {
        Fill_ways();
        if(from > 0 && from <= n && to > 0 && to <= n)
            return new Pair<>(lengths[from-1][to-1], ways[from-1][to-1]);
        throw new IllegalArgumentException("Несуществующие вершины!");
    }

    private void Fill_ways() {
        for (int i = 0; i < n; ++i){
            Dijkstra_rows(i);
            lengths[i][i] = 0.;
            ways[i][i] = new ArrayList<>();
            ways[i][i].add(i+1);
        }
    }

    private void Dijkstra_rows(int start) {
        int counter = n;
        boolean[] visited = new boolean[n];
        for (int i = 0; i < n; ++i) {
            visited[i] = false;
            lengths[start][i] = Double.POSITIVE_INFINITY;
        }
        visited[start] = true;
        int[] prev = new int[n];
        prev[start] = -1;
        int current = start;
        double mark = 0.;
        while(counter--!=0) {
            for (int i = 0; i < n; ++i) {
                if (i != current && i != start && !visited[i] && weights_matrix[current][i] > 0.) {
                    double mark_new = mark + weights_matrix[current][i];
                    if (mark_new < lengths[start][i]) {
                        lengths[start][i] = mark_new;
                        prev[i] = current;
                    }
                }
            }
            double min = Double.POSITIVE_INFINITY;
            for (int i = 0; i < n; ++i) {
                if (lengths[start][i] < min && !visited[i]) {
                    min = lengths[start][i];
                    current = i;
                   }
            }
            visited[current] = true;
            mark = min;

        }
        for (int i = 0; i < n; ++i) {
            if (i!=start && lengths[start][i]!=Double.POSITIVE_INFINITY) {
                ways[start][i] = new ArrayList<>();
                ways[start][i].add(i+1);
                int j = i;
                while (prev[j]!=-1) {
                    ways[start][i].add( prev[j] + 1);
                    j = prev[j];
                }
                Collections.reverse(ways[start][i]);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                res.append(weights_matrix[i][j])
                    .append(' ');
                if (j == n-1)
                    res.setCharAt(res.length()-1, '\n');
            }
        }
        return res.toString();
    }
}
