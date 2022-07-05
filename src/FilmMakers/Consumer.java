package FilmMakers;

public class Consumer extends Thread {
	
	Buffer<String> buffer;
	Buffer<String> bufferCombine;

	private int decade;
	private int cnt;
	Integer done;

	public Consumer(int id, Buffer<String> buffer, Buffer<String> bufCom, int N, Integer done) {
		super("Consumer" + id);
		this.buffer = buffer;
		this.decade = decade;
		cnt = N;
		this.done = done;
		bufferCombine = bufCom;
	}

	@Override
	public void run() {
		int i = 0;
		while(true) {
			String s = buffer.get();
			if(s == null)
				break;
			parseLine(s);
			i++;
			if(i == 1000) {
				i = 0;
				synchronized (done) {
					done += 1000;
				}
			}
		}
		bufferCombine.put(null);
		//System.out.println(getName() + " FINISHED");
	}

	private void parseLine(String line) {
		String[] args = line.split("\t");
		String profession = args[4];
		String birthYear = args[2];
		String deathYear = args[3];
		if(profession.contains("actor") || profession.contains("actress")) {
			if(birthYear.equals("\\N")){
				return;
			}
			int birth = Integer.parseInt(birthYear);
			if(deathYear.equals("\\N")) {
				birth /= 10;
				birth *= 10;
				String decade = Integer.toString(birth);
				bufferCombine.put(decade);
				//System.out.println(args[0] + profession + birthYear);
			}
		}
	}

}
