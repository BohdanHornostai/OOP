import java.io.*;
import java.util.Iterator;

public class container implements Iterable<String>, Serializable {
	private String[] data;
	private int currentLength = 0;

	public String toString() {
		if (currentLength == 0)
			return null;
		StringBuilder result = new StringBuilder();
		result.append("[ ");
		for (int i = 0; i < currentLength - 1; i++) {
			result.append(data[i]);
			result.append(", ");
		}
		result.append(data[currentLength - 1]);
		result.append(" ]");
		return new String(result);
	}

	public void add(String s) {
		data[currentLength++] = s;
	}

	void clear() {
		for (int i = 0; i < currentLength; i++) {
			data[i] = null;
		}
		currentLength = 0;
	}

	boolean remove(String string) {
		for (int i = 0; i < currentLength; i++) {
			if (string.equals(data[i])) {
				currentLength--;
				if (currentLength - i >= 0)
					System.arraycopy(data, i + 1, data, i, currentLength - i);
				data[currentLength] = null;
				return true;
			}
		}
		return false;
	}

	String[] toArray() {
		String[] result = new String[currentLength];
		System.arraycopy(data, 0, result, 0, currentLength);
		return result;
	}

	int size() {
		return currentLength;
	}

	boolean contains(String string) {
		for (int i = 0; i < currentLength; i++)
			if (string.equals(data[i]))
				return true;
		return false;
	}

	boolean containsAll(container container) {
		boolean found = false;
		for (String i : container) {
			for (int j = 0; j < currentLength; j++) {
				if (i.equals(data[j])) {
					found = true;
					break;
				}
			}
			if (!found)
				return false;
			found = false;
		}
		return true;
	}

	container(int length) {
		if (length > 0) {
			data = new String[length];
		} else
			throw new ExceptionInInitializerError();
	}

	boolean serialize(String filename) {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(filename);
		} catch (FileNotFoundException e) {
			System.out.println("file not found");
			return false;
		}
		try {
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this);
			oos.flush();
			oos.close();
			fos.close();
			return true;
		} catch (IOException e) {
			System.out.println("smth goes wrong with IO");
			return false;
		}
	}

	boolean deserialize(String filename) {
		FileInputStream fis;
		try {
			fis = new FileInputStream(filename);
		} catch (FileNotFoundException e) {
			System.out.println("file not found");
			return false;
		}
		try {
			ObjectInputStream ois = new ObjectInputStream(fis);
			container temp = (container) ois.readObject();
			if (temp.currentLength > this.data.length) {
				System.out.println("too large container");
				return false;
			}
			this.data = temp.data;
			this.currentLength = temp.currentLength;
			return true;
		} catch (IOException e) {
			System.out.println("smth goes wrong with IO");
			return false;
		} catch (ClassNotFoundException e) {
			System.out.println("wrong file format");
			return false;
		}
	}

	String get(int index) {
		if (index >= currentLength)
			return null;
		else
			return data[index];
	}

	private int elemCompare(int index1, int index2) {
		String str1 = data[index1];
		String str2 = data[index2];
		int length1 = str1.length();
		int length2 = str2.length();

		for (int i = 0; i < length1 && i < length2; i++) {
			int str1_ch = (int) str1.charAt(i);
			int str2_ch = (int) str2.charAt(i);
			if (str1_ch != str2_ch)
				return str1_ch - str2_ch;
		}

		if (length1 != length2) {
			return length1 - length2;
		} else {
			return 0;
		}
	}

	private void quicksort(String arr[], int first, int last) {
		int i = first, j = last;
		int m = (first + last) / 2;
		String swap;
		do {
			while (elemCompare(i, m) < 0)
				i++;
			while (elemCompare(j, m) > 0)
				j--;
			if (i <= j) {
				if (i < j) {
					swap = arr[i];
					arr[i] = arr[j];
					arr[j] = swap;
				}
				i++;
				j--;
			}
		} while (i <= j);
		if (i < last)
			quicksort(arr, i, last);
		if (first < j)
			quicksort(arr, first, j);
	}

	void sort() {
		quicksort(data, 0, currentLength - 1);
	}

	int maxSize() {
		return data.length;
	}

	@Override
	public Iterator<String> iterator() {
		return new iterator(data, currentLength);
	}
}