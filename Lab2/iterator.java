import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

class iterator implements Iterator<String> {
	private ArrayList data;
	private int current = 0;
	private int stop;

	iterator(String data[], int length) {
		this.data = new ArrayList<>(Arrays.asList(data));
		stop = length - 1;
	}

	@Override
	public boolean hasNext() {
		return (current <= stop);
	}

	@Override
	public String next() {
		return (String) data.get(current++);
	}

	@Override
	public void remove() {
		data.remove(--current);
	}
}