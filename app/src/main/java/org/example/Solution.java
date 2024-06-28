package org.example;

import java.io.*;

/**
 * Do Not change file name, class name, BufferedReader, BufferedWriter!!
 */
public class Solution {

    public BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    /**
     * implement code from here
     *
     * YOU CAN ONLY USE given br and bw. DO NOT USE System.out.println() when testing!!
     */

    public Solution() {}

    public Solution(BufferedReader br, BufferedWriter bw) {
        this.br = br;
        this.bw = bw;
    }

    public void solution() throws IOException {

        try {
            String[] s = br.readLine().split(" ");
            int answer = 0;

            for (var s1 : s) {
                answer += Integer.parseInt(s1);
            }
            bw.write(String.valueOf(answer));
            bw.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}