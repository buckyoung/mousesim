public class Position {
	public int row;
	public int col;

	public Position(int r, int c) {
		row = r;
		col = c;
	}

	public Position(Position p) {
		row = p.row;
		col = p.col;
	}

}