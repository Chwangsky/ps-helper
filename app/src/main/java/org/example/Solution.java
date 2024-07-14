package org.example;

import java.io.*;

/**
 * Do Not change file name, class name, BufferedReader, BufferedWriter!!
 */
public class Solution implements SolutionInterface {

    public BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    public Solution() {
    }

    public Solution(BufferedReader br, BufferedWriter bw) {
        this.br = br;
        this.bw = bw;
    }

    /**
     * IMPLEMENT CODE FROM HERE
     *
     * YOU CAN ONLY USE given br and bw. DO NOT USE System.out.println() when
     * testing!!
     * 
     * + don't forget add to add bw.flush();
     */

    public void solution() throws IOException {
        String[] s = br.readLine().split(" ");

        int answer = 0;
        for (int i = 0; i < s.length; i++) {
            answer += Integer.parseInt(s[i]);
        }
        bw.write(String.valueOf(answer));
        bw.flush();
        bw.close();

    }

}