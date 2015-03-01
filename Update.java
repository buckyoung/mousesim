public class Update {

	private String msg;
	private int time;
	private static final int STALE_TIME = Stream.getStaleTime();

	public Update(String s) {
		this.msg = s;
		this.time = MouseSim.getRuntime();
	}

	public boolean isStale() {
		return MouseSim.getRuntime() > this.time+STALE_TIME;
	}

	@Override
	public String toString() {
		return this.time + ": " + this.msg;
	}

}