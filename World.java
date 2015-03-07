public class World {

	private final int ROWSIZE;
	private final int COLSIZE;
	private final int VIEWROW;
	private final int VIEWCOL;
	private WorldNode[][] world;

	public World(int rowsize, int colsize, int viewrow, int viewcol) {
		ROWSIZE = rowsize;
		COLSIZE = colsize;
		VIEWROW = viewrow;
		VIEWCOL = viewcol;
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
		for(int i=0; i < VIEWROW; i++){
			System.out.print("\t ");
			for(int j=0; j < VIEWCOL; j++){
				System.out.print(world[i][j]+" ");
			}
			System.out.println(" ");
		}
		System.out.print("\t ");
		for(int i=0; i < VIEWCOL+1; i++){
			System.out.print("- ");
		}
		System.out.println();
	}

}