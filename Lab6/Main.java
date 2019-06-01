import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static final int MAX_CONTAINER_LENGTH = 100000;


    public static void main(String[] args) {
        Scanner file;
        Scanner stdin = new Scanner(System.in);
        MyContainer container = new MyContainer(MAX_CONTAINER_LENGTH);
        Path p = null;
        while (true) {
            System.out.println("~~--");
            System.out.println("1 - set file to read text from");
            System.out.printf("%s2 - read from file%s%s%n",
                    p == null ? ("/*") : "" ,
                    p != null ? (" " + p.getFileName()) : "",
                    p == null ? ("*/" ) : "");
            System.out.printf("%s3 - show text%s%n",
                    container.size() == 0 ? ("/*") : "",
                    container.size() == 0 ? ("*/") : "");
            System.out.printf("%s4 - test threads%s%n",
                    container.size() == 0 ? ("/*") : "",
                    container.size() == 0 ? ("*/") : "");
            System.out.println("5 - exit");
            String path = "C:\\Users\\User\\L6.xml";
            p = Paths.get(path);
            byte command = stdin.nextByte();
            switch (command) {
                case 1:
                    System.out.println("give me a valid path to the file, it can be absolute or relative");
                    stdin.skip("\n");
                    //String path = stdin.nextLine();
                    //String path = "\\C:\\Users\\User\\L6.xml";
                    Pattern pattern = Pattern.compile("(\\w:\\\\)?((\\w+\\\\)+)?(\\w+(\\.\\w+)?)$");
                    Matcher matcher = pattern.matcher(path);
                    if (matcher.matches()) {
                        p = Paths.get(path);
                    } else {
                        System.err.println("wrong path format!");
                    }
                    break;
                case 2:
                    if (p == null) {
                        System.err.println("you must first set a file");
                        break;
                    }
                    try (FileInputStream fis = new FileInputStream(p.toString())) {
                        file = new Scanner(new BufferedInputStream(fis)).useDelimiter("\\Z");
                        container.addFromArray(file.next().replaceAll("\\s+", " ")
                                .replaceAll("\\W+", " ").split(" "));
                    } catch (IOException e) {
                        System.err.println("file not found!");
                    }
                    break;
                case 3:
                    if (container.size() == 0) {
                        System.err.println("container is empty!");
                        break;
                    }
                    for (String i : container) {
                        System.out.print(i+" ");
                    }
                    System.out.println();
                    break;
                case 4:
                    if (container.size() == 0) {
                        System.err.println("container is empty");
                        break;
                    }
                    Callable<?> tasks[] = new Callable<?>[]{
                            container::getMin,//() -> Collections.singletonList(container.getMin()), // 0 - getMin()
                            container::getMax,//() -> Collections.singletonList(container.getMax()), // 1 - getMax()
                            () -> container.getAllByPredicate(str -> Character.isUpperCase(str.charAt(0))), // 2 - 1st char is Upper
                            () -> container.getAllByPredicate(str -> Character.isLowerCase(str.charAt(0))), // 3 - 1st char is Lower
                            () -> container.getAllByPredicate(str -> {
                                int vowels = 0;
                                for (int i = 0; i < str.length(); i++) {
                                    vowels += MyContainer.isVowel(str.charAt(i)) ? 1 : 0;
                                }
                                return vowels >= 3;
                            }), // 4 - vowels count >= 3
                            () -> container.getAllByPredicate(str -> {
                                int notVowels = 0;
                                for (int i = 0; i < str.length(); i++) {
                                    notVowels += MyContainer.isVowel(str.charAt(i)) ? 0 : 1;
                                }
                                return notVowels >= 5;
                            }), 
                            () -> container.getAllByPredicate(str -> str.length() > 6), // 6 - length > 6
                            () -> container.getAllByPredicate(str -> Character.toLowerCase(str.charAt(0)) == 'a') // 7 - 1st char is A
                    };
                    System.out.println("available tasks");
                    System.out.println("1 - find min");
                    System.out.println("2 - find max");
                    System.out.println("3 - find all words with 1st uppercase char");
                    System.out.println("4 - find all words with 1st lowercase char");
                    System.out.println("5 - find all words with 3 or more vowels");
                    System.out.println("6 - find all words with 5 or more consonants");
                    System.out.println("7 - find all words with length greater than 6");
                    System.out.println("8 - find all words with 1st letter A");
                    System.out.println("how many tasks would you like to run?");
                    byte tasksCount = stdin.nextByte();
                    if (tasksCount < 2 || tasksCount > tasks.length) {
                        System.err.printf("tasks count must be > 2 and < %d", tasks.length);
                        break;
                    }
                    System.out.printf("Enter %d numbers of chosen tasks", tasksCount);
                    byte todo[] = new byte[tasksCount];
                    for (int i = 0; i < tasksCount; i++) {
                        todo[i] = stdin.nextByte();
                        todo[i]--;
                        if (todo[i] < 0 || todo[i] > tasks.length-1 ) {
                            System.err.println("wrong input, try again!");
                            i--;
                        }
                    }
                    System.out.println("now enter number of threads:");
                    ExecutorService executorServiceFixedThreads = Executors.newFixedThreadPool(stdin.nextInt());
                    ExecutorService executorServiceCachedThreads = Executors.newCachedThreadPool();
                    long start = System.currentTimeMillis();
                    System.out.println("multi-threaded test started");
                    for (int i : todo) {
                        executorServiceFixedThreads.submit(tasks[i]);
                    }
                    executorServiceFixedThreads.shutdown();
                    /*try {
                        //executorServiceFixedThreads.awaitTermination(tasks.length*40000, TimeUnit.MILLISECONDS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                    System.out.printf("done work in %d mills%n",
                            System.currentTimeMillis()-start);
                    start = System.currentTimeMillis();
                    //System.out.println("executor with cached thread pool started");
                    for (int i : todo) {
                        executorServiceCachedThreads.submit(tasks[i]);
                    }
                    executorServiceCachedThreads.shutdown();
                    /*try {
                        executorServiceCachedThreads.awaitTermination(tasks.length*40000, TimeUnit.MILLISECONDS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                    /*System.out.printf("executor with cached thread pool done work in %d mills%n",
                            System.currentTimeMillis()-start);*/
                    start = System.currentTimeMillis();
                    System.out.println("sequential processing started");
                    for (int i : todo) {
                        try {
                            System.out.println(tasks[i].call());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.printf("sequential processing took %d mills%n", System.currentTimeMillis() - start);
                    break;
                case 5:
                    System.out.println("bye");
                    System.exit(1);
            }
        }
    }
}