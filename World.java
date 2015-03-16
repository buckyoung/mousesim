public class World {

	private final int ROWSIZE;
	private final int COLSIZE;
	private WorldNode[][] world;

	public World(int rowsize, int colsize) {
		ROWSIZE = rowsize;
		COLSIZE = colsize; 
		world = new WorldNode[ROWSIZE][COLSIZE];

		for(int i=0; i < ROWSIZE; i++){
			for(int j=0; j < COLSIZE; j++){
				world[i][j] = new WorldNode(i, j);
			}
		}
	}

	public WorldNode getRandomWorldNode() {
		return world[MouseSim.rand.nextInt(ROWSIZE)][MouseSim.rand.nextInt(COLSIZE)];
	}

	public WorldNode getWorldNode(Position p) {
		return world[p.row][p.col];
	}

	public void render() {
		System.out.println();
		System.out.println();
		System.out.println();
		for(int i=0; i < ROWSIZE; i++){
			System.out.print("\t ");
			for(int j=0; j < COLSIZE; j++){
				System.out.print(world[i][j]+" ");
			}
			System.out.println(" ");
		}
		System.out.print("\t ");
		for(int i=0; i < COLSIZE+1; i++){
			System.out.print("- ");
		}
		System.out.println();
	}

}