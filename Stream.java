public class Stream {
	
	private static final int MAXHEADLINES = 12;
	private static QueueLinkedList<String> headlines = new QueueLinkedList<>(MAXHEADLINES);

	public static void close() {
		headlines.close();
	}

	public static void history(String s) {
		headlines.printStraightToHistory(MouseSim.getRuntime()+") "+s);
	}

	public static void print() {
		System.out.println(headlines);
	}

	public static void update(String s) {
		headlines.enqueue(MouseSim.getRuntime()+": "+s);
	}

	public static void debug(String s) {
		if(MouseSim.DEBUG) headlines.enqueue(">>>"+MouseSim.getRuntime()+" "+s);
	}

}