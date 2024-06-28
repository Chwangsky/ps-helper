package org.example;

import org.example.enums.Mode;
import org.example.module.PsHandler;

/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2024 YoungWoo Choi
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 *
 */

public class App {

    public static void main(String[] args) {
        /**
         *
         * Mode.SUBMIT : 코드 제출 환경과 동일한 환경으로, 표준 입력을 입력으로 받으며, 표준 출력으로 출력한다.
         * Mode.TEST_SIMPLE : 간단한 테스트케이스 용도로, input.txt 파일에 입력을 받으며, output.txt 파일에 출력한다.
         * Mode.TEST_MULTIPLE_SOURCE : 여러 파일로 구성된 테스트케이스를 테스트하기 위한 용도로, src/in 폴더에 위치한 파일 전부를 입력으로 받으며, src/out 폴더에 출력한다.
         *
         * main 함수를 실행하면 src 폴더에 Main.java파일이 생성된다.
         */
        Mode mode = Mode.TEST_SIMPLE;

        PsHandler psHandler = new PsHandler(mode);
        psHandler.handle();

    }
}