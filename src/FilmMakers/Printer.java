package FilmMakers;

public class Printer extends Thread {

	Buffer<String> printBuffer;

	public Printer(Buffer<String> printBuffer) {
		super("Printer");
		this.printBuffer = printBuffer;
	}

	@Override
	public void run() {
		while (true) {
			String s = printBuffer.get();
			if(s == null)
				break;
			System.out.println(s);
		}
	}

}
