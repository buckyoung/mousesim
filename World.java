import java.util.Random;

public class World {

	private final int SIZE;
	private Node[][] world;
	private static final String EMPTYCELL = ".";
	private static final String MOUSECELL = "M";
	private static final String FOODCELL = "f";
	private Random rand;

	public World(int s) {
		rand = new Random(Double.doubleToLongBits(Math.random()));

		SIZE = s;
		world = new Node[SIZE][SIZE];

		for(int i=0; i < SIZE; i++){
			for(int j=0; j < SIZE; j++){
				world[i][j] = new Node();
			}
		}
	}

	public void addFood(Position p) {
		world[p.row][p.col].value = World.FOODCELL;
	}

	public void render() {
		System.out.println();
		System.out.println();
		System.out.println();
		for(int i=0; i < SIZE; i++){
			System.out.print("\t");
			for(int j=0; j < SIZE; j++){
				System.out.print(world[i][j] + " ");
			}
			System.out.println("");
		}
		System.out.println();
		System.out.println();
		System.out.println();
	}

	public void resetCell(Position p) {
		world[p.row][p.col].value = EMPTYCELL;
	}
	public void updateMousePosition(Mouse mouse) {
		if(world[mouse.position.row][mouse.position.col].value == FOODCELL) {
			Food.eatenBy(mouse);
		}

		world[mouse.position.row][mouse.position.col].value = MOUSECELL;
	}

	private class Node {
		public String value;

		public Node(){
			value = EMPTYCELL;
		}

		@Override
		public String toString(){
			return value;
		}
	}

	public boolean canMove(Direction d, Position p) {
		switch(d) {
			case UP:
				return p.row > 0;
			case DOWN:
				return p.row < SIZE-1;
			case LEFT:
				return p.col > 0;
			case RIGHT:
				return p.col < SIZE-1;
			default:
				return false;
		}
	}

}