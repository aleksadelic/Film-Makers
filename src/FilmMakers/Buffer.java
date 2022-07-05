package FilmMakers;

public interface Buffer<T> {

	void put(T item);
	
	T get();
}
