package FilmMakers;

import java.util.Map;
import java.util.TreeMap;

public class Combiner extends Thread {

	private Buffer<String> bufferCombine;
	private Buffer<String> printBuffer;

	private Map<Integer, Integer> map = new TreeMap<>();

	public Combiner(Buffer<String> buf, Buffer<String> printBuffer) {
		super("Combiner");
		bufferCombine = buf;
		this.printBuffer = printBuffer;
	}

	@Override
	public void run() {
		while (true) {
			String s = bufferCombine.get();
			if (s == null)
				break;
			parseLine(s);
		}
		for (Map.Entry<Integer, Integer> elem : map.entrySet()) {
			printBuffer.put("Dekada: " + Integer.toString(elem.getKey()) + "-" + Integer.toString((elem.getKey() + 9)) +
					" broj zivih glumaca: " + Integer.toString(elem.getValue()));
		}
	}

	private void parseLine(String s) {
		if (map.containsKey(Integer.parseInt(s))) {
			Integer cnt = map.get(Integer.parseInt(s));
			cnt++;
			map.put(Integer.parseInt(s), cnt);
		} else {
			map.put(Integer.parseInt(s), 1);
		}
	}

}
