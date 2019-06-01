import java.io.Serializable;
import java.util.Iterator;

public class LinkedList<T> implements Iterable<T>, Serializable {

    private class Node implements Serializable {
        private T data;
        private Node next;

        private T getData() {
            return data;
        }

        private Node getNext() {
            return next;
        }

        private void setData(T data) {
            this.data = data;
        }

        private void setNext(Node next) {
            this.next = next;
        }

    }

    private Node root;

    public LinkedList() {
        root = new Node();
    }

    public void add(T data) {
        Node last = root;
        while(last.getNext() != null)
            last = last.getNext();
        last.setNext(new Node());
        last.getNext().setData(data);
    }

    public T get(int index) {
        Node result = root;
        for (int i = -1; i < index; i++) {
            if (result.getNext() == null)
                return null;
            else
                result = result.getNext();
        }
        return result.getData();
    }

    public boolean remove(int index) {
        Node result = root;
        if (size() != 0 && index>=0) {
            for (int i = -1; i < index-1; i++)
                if (result.getNext() == null)
                    return false;
                else
                    result = result.getNext();
            if (result.getNext() == null)
                return false;
            else if (result.getNext().getNext() == null) {
                result.setNext(null);
                return true;
            }
            else {
                result.setNext(result.getNext().getNext());
                return true;
            }
        }
        else return false;
    }

    public int size() {
        Node last = root;
        int count = 0;
        while(last.getNext() != null) {
            count++;
            last = last.getNext();
        }
        return count;
    }

    public void clear() {
        root = new Node();
    }

    public String toString() {
        if (size() == 0) return "[ - ]";
        StringBuilder result = new StringBuilder();
        result.append("[ ");
        for (int i = 0; i < size()-1; i++) {
            result.append(get(i)).append(", ");
        }
        result.append(get(size()-1)).append(" ]");
        return new String(result);
    }

    public T[] toArray() {
        @SuppressWarnings("unchecked")
        T[] result = (T[]) new Object[size()];
        for (int i = 0; i < size(); i++) {
            result[i] = get(i);
        }
        return result;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int counter;

            {
                counter = 0;
            }

            @Override
            public boolean hasNext() {
                return counter < size();
            }

            @Override
            public T next() {
                return get(counter++);
            }
        };
    }
}