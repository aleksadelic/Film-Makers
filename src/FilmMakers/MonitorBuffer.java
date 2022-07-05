package FilmMakers;

import java.util.LinkedList;
import java.util.List;

public class MonitorBuffer<T> implements Buffer<T> {

	private List<T> buffer;
	private int capacity;
	
	private boolean finished;

	public MonitorBuffer(int size) {
		buffer = new LinkedList<>();
		capacity = size;
		finished = false;
	}

	@Override
	public synchronized void put(T item) {
		if(item == null)
			finished = true;
		while (buffer.size() == capacity) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		buffer.add(item);
		notifyAll();
	}

	@Override
	public synchronized T get() {
		while (buffer.size() == 0 && !finished) {
			try {
				wait();
				if(finished)
					return null;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		T item = buffer.remove(0);
		notifyAll();
		return item;
	}

}
