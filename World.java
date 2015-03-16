public class World {

	private final int ROWSIZE;
	private final int COLSIZE;
	private final int VIEWROW;
	private final int VIEWCOL;
	private WorldNode[][] world;
	private Mouse m;
	private Position p;

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
		int q = 0;
		int w = 0;
		int I = VIEWROW;
		int J = VIEWCOL;
		if(PresidentController.hasPresident()){
			m = PresidentController.getPresident();
			p = m.getPosition();
			q = p.row - VIEWROW/2;
			w = p.col - VIEWCOL/2;
			I = p.row + VIEWROW/2;
			J = p.col + VIEWCOL/2;
			if(q < 0) q = 0;
			if(w < 0) w = 0;
			if(I > ROWSIZE) I = ROWSIZE;
			if(J > COLSIZE) J = COLSIZE;
			
			//Stream.debug("i I: " + i + " " + I + "  | j J: "+j + " " + J);
		} 
		for(int i = q; i < I; i++){
			System.out.print("\t ");
			for(int j = w; j < J; j++){
				System.out.print(world[i][j]+" ");
			}
			System.out.println(" ");
		}
		// System.out.print("\t ");
		// for(int k=0; k < J+1; k++){
		// 	System.out.print("-"+" ");
		// }
		System.out.println();
	}

}