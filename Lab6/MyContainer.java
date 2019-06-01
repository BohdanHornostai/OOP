import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import static java.lang.Math.min;

public class MyContainer implements Iterable<String>, Serializable {
    private String[] data;
    private int currentLength = 0;

    public MyContainer(int maxLength) {
        if (maxLength > 0) {
            data = new String[maxLength];
        }
        else
            throw new RuntimeException("trying to set negative size");
    }

    public void add(String s) {
        data[currentLength++] = s;
    }

    public void addFromArray(String[] array) {
        System.arraycopy(array, 0, data, 0, min(array.length, data.length));
        currentLength = min(array.length, data.length);
    }

    String get(int index) {
        if (index >= currentLength)
            return null;
        else
            return data[index];
    }

    int size() {
        return currentLength;
    }

    private int stringCompare(String str1, String str2) {
        int length1 = str1.length();
        int length2 = str2.length();

        for (int i = 0; i < length1 && i < length2; i++) {
            int str1_ch = (int)str1.charAt(i);
            int str2_ch = (int)str2.charAt(i);
            if (str1_ch != str2_ch)
                return str1_ch - str2_ch;
        }

        if (length1 != length2) {
            return length1 - length2;
        }
        else {
            return 0;
        }
    }

    int maxSize() {
        return data.length;
    }

    public List<String> getAllByPredicate(Predicate<String> stringPredicate) {
        List<String> result = new ArrayList<>();
        for (String i : this) {
            if (stringPredicate.test(i))
                result.add(i);
            /*try {
                Thread.sleep(120);
            } catch (InterruptedException e) {
                System.err.println("thread interrupted");
            }*/
        }
        return result;
    }

    public static boolean isVowel(char c) {
        c = Character.toLowerCase(c);
        return c == 'a' || c == 'o' || c == 'y' || c == 'i' || c == 'e' || c == 'u';
    }

    public String getMax() {
        String result = String.valueOf(0);
        for (String i : this) {
            if (stringCompare(i, result) > 0)
                result = i;
        }
        return result;
    }

    public String getMin() {
        String result = String.valueOf(127);
        for (String i : this) {
            if (stringCompare(i, result) < 0)
                result = i;
        }
        return result;
    }

    @Override
    public Iterator<String> iterator() {
        return new Iterator<String>() {
            int counter = 0;

            @Override
            public boolean hasNext() {
                return counter < size();
            }

            @Override
            public String next() {
                return get(counter++);
            }
        };
    }
}