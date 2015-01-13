//for sudoku only
public class Board {
	private int[][] board;
	public final int SIZE = 9;
	
	//board values range from 0 to 9. 0 means empty.
	public Board() {
		board = new int[9][9];
	}
	
	
	//loads the board with manual input
	public void loadManual() {
	
	}
	
	//loads test board
	public void loadTest() {
		String load = "020000070309504601500090003030607090000000000070308040800020004204706805050000060";
		loadString(load);
	}
	public void loadTest2() {
		String load = "001903600800040001700602008010809070900000003020401050400308005500060002008705900";
		loadString(load);
	}
	//str must be 81 characters long;
	public void loadString(String str) {
		int index = 0;
		for(int j = 0; j < 9; j++) {
			for(int i = 0; i < 9; i++) {
				board[i][j] = Integer.parseInt(str.substring(index, index + 1));
				index++;
			}
		}
	}
	//displays to the command line.
	public void display() {
		System.out.println();
		for(int j = 0; j < 9; j++) {
			if(j == 3 || j == 6) {
				System.out.println("---------------------");
			}
			for(int i = 0; i < 9; i++) {
				if(i == 3 || i == 6) {
					System.out.print("| ");
				}
				if(board[i][j] == 0) {
					System.out.print("  ");
				}
				else {
					System.out.print(board[i][j] + " ");
				}
			}
			System.out.println();
		}
		
	}
	// gets the numbers in the grid of the piece at x, y. Includes the piece if it is not empty.
	public int[] getGridValues(int x, int y) {
		x = x/3;
		y = y/3;
		int[] values = new int[9];
		int index = 0;
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				int piece = board[x*3 + i][y*3 + j];
				if(piece != 0) {
					values[index] = piece;
					index++;
				}
			}
		}
		int[] compact = new int[index];
		for(int i = 0; i < index; i++) {
			compact[i] = values[i];
		}
		return compact;
	}
	
	//gets the numbers in the vertical bar of the piece at x, y. Includes the piece if it is not empty
	public int[] getVerticalValues(int x, int y) {
		int[] values = new int[9];
		int index = 0;
		for(int i = 0; i < 9; i++) {
			int piece = board[x][i];
			if(piece != 0) {
				values[index] = piece;
				index++;
			}
		}
		int[] compact = new int[index];
		for(int i = 0; i < index; i++) {
			compact[i] = values[i];
		}
		return compact;
	}
	// same thing but for horizontal bar
	public int[] getHorizontalValues(int x, int y) {
		int[] values = new int[9];
		int index = 0;
		for(int i = 0; i < 9; i++) {
			int piece = board[i][y];
			if(piece != 0) {
				values[index] = piece;
				index++;
			}
		}
		int[] compact = new int[index];
		for(int i = 0; i < index; i++) {
			compact[i] = values[i];
		}
		return compact;
	}
	
	//gets combined grid, horizontal, and vertical values. no repeats
	public int[] getAllRelevant(int x, int y) {
		int[] grid = getGridValues(x, y);
		int[] horiz = getHorizontalValues(x, y);
		int[] vertical = getVerticalValues(x, y);
		boolean[] hasNum = new boolean[10];
		for(int i = 0; i < grid.length; i++) {
			hasNum[grid[i]] = true;
		}
		for(int i = 0; i < horiz.length; i++) {
			hasNum[horiz[i]] = true;
		}
		for(int i = 0; i < vertical.length; i++) {
			hasNum[vertical[i]] = true;
		}
		int length = 0;
		for(int i = 0; i < hasNum.length; i++) {
			if(hasNum[i]) {
				length++;
			}
		}
		int[] compact = new int[length];
		int index = 0;
		for(int i = 1; i < hasNum.length; i++) {
			if(hasNum[i]) {
				compact[index] = i;
				index++;
			}
		}
		return compact;
	}
	public void changePiece(int x, int y, int num) {
		if( x >= 9 || x < 0 || y >= 9 || y < 0 || num < 0 || num > 9) {
			return;
		}
		else {
			board[x][y] = num; 
		}
	}
	public int getPiece(int x, int y) {
		if(x >= 9 || x < 0 || y >= 9|| y < 0) {
			System.out.println("getPiece out of bounds");
			return -1;
		}
		else {
			return board[x][y];
		}
	}
}