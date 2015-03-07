public class Stream {
	
	private static final int MAXHEADLINES = 12;
	private static final int STALE_TIME = 400;
	private static QueueLinkedList<Update> headlines = new QueueLinkedList<>(MAXHEADLINES);

	public static void close() {
		headlines.close();
	}

	public static void history(String s) {
		headlines.printStraightToHistory("("+(new Update(s))+")");
	}

	public static void print() {
		System.out.println(headlines);
	}

	public static void update(String s) {
		headlines.enqueue(new Update(s));
	}

	public static void debug(String s) {
		if(MouseSim.DEBUG) headlines.enqueue(new Update(">>>"+s));
	}

	public static int getStaleTime() {
		return STALE_TIME;
	}

}