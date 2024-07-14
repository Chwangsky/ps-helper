package org.example.module;

import org.example.Solution;
import org.example.SolutionInterface;
import org.example.enums.Mode;
import org.example.handler.DynamicProxyHandler;
import org.example.util.SimpleFormatter;

import java.io.*;
import java.lang.reflect.Proxy;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PsHandler {
    private final static String MAIN_PATH = "app/src/main";
    private final static String SINGLE_INPUT_FILE_PATH = MAIN_PATH + "/" + "resources/simple/input.txt";
    private final static String SINGLE_OUTPUT_FILE_PATH = MAIN_PATH + "/" + "resources/simple/output.txt";

    private final static String MULTI_INPUT_FILE_PREFIX = MAIN_PATH + "/" + "resources/simple/in";
    private final static String MULTI_OUTPUT_FILE_PREFIX = MAIN_PATH + "/" + "resources/simple/out";

    private final static String SOLUTION_JAVA_PATH = MAIN_PATH + "/" + "java/org/example/Solution.java";
    private final static String OUTPUT_MAIN_JAVA_PATH = "Main.java";
    private final static String HANDLER_VERSION = "0.02";

    private final Mode mode;
    private static final Logger logger = Logger.getLogger(PsHandler.class.getName());

    static {
        // 콘솔 핸들러를 추가하여 표준 출력으로 로그를 출력하도록 설정
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);

        SimpleFormatter formatter = new SimpleFormatter();
        // TODO : customize your formatter

        consoleHandler.setFormatter(formatter);
        logger.addHandler(consoleHandler);
        logger.setLevel(Level.ALL);
        logger.setUseParentHandlers(false);
    }

    public PsHandler(Mode mode) {
        this.mode = mode;
    }

    public void handle() {

        logger.info(String.format("현재 %s 모드로 실행중입니다.", mode));

        // STEP 1. run solution.java code correspond to
        switch (mode) {
            case SUBMIT:
                solutionWithStandardIO();
                break;
            case TEST_SIMPLE:
                solutionWithSimpleSource();
                break;
            case TEST_MULTIPLE_SOURCE:
                testWithMultipleSource();
                break;
            default:
                throw new IllegalArgumentException("Unknown mode: " + mode);
        }

        submitCodeGenerator();
    }

    private void solutionWithStandardIO() {

        logger.info("입력 값을 콘솔에 입력해주세요.");

        try {

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

            Solution solution = new Solution(br, bw);

            solution.solution();

        } catch (IOException e) {
            logger.log(Level.SEVERE, "An IO error occurred", e);
        }

    }

    /**
     * 1. in 폴더 내의 모든 파일을 읽음. 파일 형식은 지정되지 않음
     * 2. out 폴더 내에 모든 파일에 대한 알고리즘을 돌려서 출력. in 폴더 내에 있는 파일 이름을 그대로 out 폴더에 둠
     * 2-2. 만약 out 폴더 자체가 없으면 out 폴더를 자체적으로 만들어 줌.
     *
     */
    private void testWithMultipleSource() {

        // Directory paths
        File inDir = new File(MULTI_INPUT_FILE_PREFIX);
        File outDir = new File(MULTI_OUTPUT_FILE_PREFIX);

        // List files in inDir
        File[] files = inDir.listFiles();

        if (files != null) {
            // Process each file in inDir
            for (File file : files) {
                if (file.isFile()) {
                    String inputFilePath = file.getAbsolutePath();
                    String outputFilePath = outDir.getAbsolutePath() + File.separator + file.getName();

                    try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath));
                            BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilePath))) {

                        // Process each file using solution method
                        Solution solution = new Solution(br, bw);
                        solution.solution();

                    } catch (IOException e) {
                        logger.log(Level.SEVERE, "An IO error occurred", e);
                    }
                }
            }
        }
    }

    private void solutionWithSimpleSource() {
        try (BufferedReader br = new BufferedReader(new FileReader(SINGLE_INPUT_FILE_PATH));
                BufferedWriter bw = new BufferedWriter(new FileWriter(SINGLE_OUTPUT_FILE_PATH))) {

            // 주석 부분을 일치시키는 정규 표현식
            String regex = "(?s)/\\*.*?\\*/|//.*?(\\R|$)";
            Pattern pattern = Pattern.compile(regex);

            StringBuilder inputBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                inputBuilder.append(line).append("\n");
            }

            // 전체 내용을 하나의 문자열로 결합
            String content = inputBuilder.toString();

            // 주석을 기준으로 문자열 분할
            String[] parts = pattern.split(content);

            // regex Pattern for whitespace
            String regexForWhiteSpace = "^\\s+";

            // 주석이 아닌 부분만 bw에 작성

            int i = 1;
            for (String part : parts) {
                String trimmed = stripWhitespace(part);
                if (!trimmed.isEmpty()) {
                    bw.write("------------TEST #" + String.valueOf(i++) + " ------------\n");
                    Solution solution = new Solution(new BufferedReader(new StringReader(trimmed)), bw);
                    solution.solution();
                    bw.newLine();
                }

            }

            bw.flush();

        } catch (IOException e) {
            logger.log(Level.SEVERE, "An IO error occurred", e);
        }
    }

    // private BufferedWriter createBufferedWriter() {
    // try {
    // return new BufferedWriter(new FileWriter(SINGLE_OUTPUT_FILE_PATH));
    // } catch (IOException e) {
    // logger.log(Level.WARNING, "출력 파일 경로를 찾을 수 없습니다. Standard IO로 출력합니다.");
    // return new BufferedWriter(new OutputStreamWriter(System.out));
    // }
    // }

    private void submitCodeGenerator() {

        BufferedReader br;
        BufferedWriter bw;

        String fileContent;

        try {
            br = new BufferedReader(new FileReader(SOLUTION_JAVA_PATH));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            fileContent = sb.toString();
            br.close();

            fileContent = removeComments(fileContent);

        } catch (IOException e) {

            logger.log(Level.SEVERE, "error - 제출용 코드 생성 중 오류 발생");
            logger.log(Level.SEVERE, "Solution.java 파일이 존재하지 않습니다. src 폴더 내에 Solution.java 파일이 존재하는지 확인해 주세요.");
            logger.log(Level.SEVERE, String.format("현재 Solution.java 저장 위치로 지정된 위치 : %s\n", SOLUTION_JAVA_PATH));

            return;
        }

        fileContent = removeInterface(fileContent); // (proxy 구현을 위해 생성된) implements SolutionInterface 부분 제거

        String[] dividedFileContent = splitJavaFile(fileContent);

        // 만약 resources/result에 Main.java 파일이 이미 존재하는 경우, 삭제합니다.
        try {

            File file = new File(OUTPUT_MAIN_JAVA_PATH);
            if (file.exists()) {
                if (file.delete()) {
                    logger.info("이미 존재하는 Main.java 파일을 삭제합니다.");
                }
            }

        } catch (Exception e) {

            System.out.println("An error occurred while trying to delete src/Main.java.");
            logger.log(Level.SEVERE, "An IO error occurred", e);

        }

        try {
            bw = new BufferedWriter(new FileWriter(OUTPUT_MAIN_JAVA_PATH));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "error - 제출용 코드 생성중 오류 발생.\n 출력 파일 버퍼 실패\n");
            return;
        }

        try {

            bwWriteTag(bw);
            if (dividedFileContent.length == 2)
                bw.write(dividedFileContent[0]);

            bw.write("\n");
            bw.write("\n");
            bw.write("public class Main {\n");
            bw.write("    public static void main(String[] args) throws IOException {\n");
            bw.write("\n");
            bw.write("        MySolution solution = new MySolution();\n");
            bw.write("        solution.solution();\n");
            bw.write("\n");
            bw.write("    }\n");
            bw.write("}\n");
            bw.write("\n");

            bw.write(dividedFileContent[dividedFileContent.length - 1]);

            bw.flush();
            bw.close();

            logger.log(Level.INFO, "Main.java 파일 생성에 성공했습니다.");

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Main.java 파일 생성에 실패했습니다.");
        }

    }

    private void bwWriteTag(BufferedWriter bw) throws IOException {
        bw.write("/**\n");
        bw.write(" * This code is auto generated by boj-helper-in-java v." + HANDLER_VERSION + " \n");
        bw.write(" * Anyone can join this simple open-source project any time*\n");
        bw.write(" *\n");
        bw.write(" * github : https://github.com/Chwangsky\n");
        bw.write(" */ \n");
        bw.write("\n");
    }

    // 주어진 문자열에서 모든 주석을 제거하는 메서드
    private String removeComments(String content) {

        // 블록 주석과 라인 주석을 제거하는 정규식
        String blockCommentsPattern = "/\\*(?:.|[\\n\\r])*?\\*/";
        String lineCommentsPattern = "//.*";
        String combinedPattern = blockCommentsPattern + "|" + lineCommentsPattern;

        Pattern pattern = Pattern.compile(combinedPattern);
        Matcher matcher = pattern.matcher(content);

        return matcher.replaceAll("");
    }

    private String[] splitJavaFile(String content) {

        // 패키지 정의 삭제 (package org.example...; 부분을 찾아서 삭제함)
        String packagePattern = "package.+;\n";
        content = content.replaceFirst(packagePattern, "");

        content = content.replaceFirst("\\bSolution\\b", "MySolution"); // class 이름 변경
        content = content.replaceFirst("\\bSolution\\b", "MySolution"); // no args constructor 변경
        content = content.replaceFirst("\\bSolution\\b", "MySolution"); // all args constructor qusrud

        // 정규 표현식을 사용하여 'public class'를 찾음
        String classPattern = "\\bpublic\\s+class\\b";
        Pattern pattern = Pattern.compile(classPattern);
        Matcher matcher = pattern.matcher(content);

        String imports = "";
        String classDefinition;

        if (matcher.find()) {
            int classIndex = matcher.start();
            imports = content.substring(0, classIndex).trim();
            classDefinition = content.substring(classIndex + 6).trim(); // 'public' 단어를 빼고 추출
        } else {
            classDefinition = content.trim();
        }

        return new String[] { imports, classDefinition };
    }

    private String removeInterface(String string) {
        String packagePattern = "implements SolutionInterface ";
        string = string.replaceFirst(packagePattern, "");
        return string;
    }

    private String stripWhitespace(String string) {
        int start_index = 0;
        int end_index = string.length() - 1;

        // 시작 인덱스에서 모든 공백 문자 제거
        while (start_index <= end_index && isWhitespace(string.charAt(start_index))) {
            start_index++;
        }

        // 끝 인덱스에서 모든 공백 문자 제거
        while (end_index >= start_index && isWhitespace(string.charAt(end_index))) {
            end_index--;
        }

        // 부분 문자열 반환
        return string.substring(start_index, end_index + 1);
    }

    private boolean isWhitespace(char c) {
        return c == ' ' || c == '\n' || c == '\t' || c == '\r';
    }

}
