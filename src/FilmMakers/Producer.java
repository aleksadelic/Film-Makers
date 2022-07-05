package FilmMakers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Producer extends Thread {
	
	Buffer<String> buffer;
	FileReader reader;

	public Producer(Buffer<String> buffer, File file) throws FileNotFoundException {
		super("Producer");
		this.buffer = buffer;
		reader = new FileReader(file);
		
	}

	@Override
	public void run() {
		String s;
		BufferedReader br = new BufferedReader(reader);
		try {
			while((s = br.readLine()) != null) {
				buffer.put(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		buffer.put(null);
		//System.out.println("PRODUCER FINISHED");
	}

}
