package org.example;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Do Not change file name, class name, BufferedReader, BufferedWriter!!
 */
public class Solution {

    public BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    public Solution() {}

    public Solution(BufferedReader br, BufferedWriter bw) {
        this.br = br;
        this.bw = bw;
    }

    /**
     * implement code from here
     *
     * YOU CAN ONLY USE given br and bw. DO NOT USE System.out.println() when testing!!
     */

    int T;
    int x1, y1, x2, y2;

    int N;
    Circle[] circles;

    public void solution() throws IOException {

        T = Integer.parseInt(br.readLine());

        for (int t = 0; t < T; t++) {

            StringTokenizer st = new StringTokenizer(br.readLine());
            x1 = Integer.parseInt(st.nextToken());
            y1 = Integer.parseInt(st.nextToken());
            x2 = Integer.parseInt(st.nextToken());
            y2 = Integer.parseInt(st.nextToken());

            N = Integer.parseInt(br.readLine());

            circles = new Circle[N];

            for (int i = 0; i < N; i++) {

                st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                int r = Integer.parseInt(st.nextToken());

                circles[i] = new Circle(y, x, r);
            }

            int ans = 0;
            for (int i = 0; i < N; i++) {
                if (isPointInCircle(x1, y1, circles[i]) ^ isPointInCircle(x2, y2, circles[i])) ans++;
            }

            bw.write(String.valueOf(ans));
            bw.write("\n");
            bw.flush(); // TODO
        }
    }

    boolean isPointInCircle(int x, int y, Circle circle) {

        return Math.pow(x - circle.x, 2) + Math.pow(y - circle.y, 2) < Math.pow(circle.r, 2);
    }

    private static class Circle {
        public int x, y, r;

        public Circle(int y, int x, int r) {
            this.y = y;
            this.x = x;
            this.r = r;
        }
    }
}