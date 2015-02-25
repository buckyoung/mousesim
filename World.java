public class World {

	private final int SIZE;
	private WorldNode[][] world;

	public World(int s) {
		SIZE = s;
		world = new WorldNode[SIZE][SIZE];

		for(int i=0; i < SIZE; i++){
			for(int j=0; j < SIZE; j++){
				world[i][j] = new WorldNode();
			}
		}
	}

	public WorldNode getRandomWorldNode() {
		return world[MouseSim.rand.nextInt(SIZE)][MouseSim.rand.nextInt(SIZE)];
	}

	public WorldNode getWorldNode(Position p) {
		return world[p.row][p.col];
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

}