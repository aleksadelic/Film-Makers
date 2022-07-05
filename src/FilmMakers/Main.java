package FilmMakers;

import java.io.File;
import java.io.FileNotFoundException;

public class Main {

	public static void main(String[] args) {

		int consumersNumber = 5;
		int N = 1000;
		String fileName = "D:\\Aleksa\\Programi\\Java\\FilmMakers\\data.tsv";
		File file = new File(fileName);
		
		Integer done = 0;
		
		Buffer<String> buffer = new MonitorBuffer<>(100);
		Buffer<String> bufferCombine = new MonitorBuffer<>(100);
		Buffer<String> printBuffer = new MonitorBuffer<>(100);


		Producer producer = null;
		try {
			producer = new Producer(buffer, file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		producer.start();

		for (int i = 0; i < consumersNumber; i++) {
			Consumer consumer = new Consumer(i, buffer, bufferCombine, N, done);
			consumer.start();
		}

		Combiner combiner = new Combiner(bufferCombine, printBuffer);
		combiner.start();

		Printer printer = new Printer(printBuffer);
		printer.start();

		try {
			printer.join();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
