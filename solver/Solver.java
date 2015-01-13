public class Solver {
	private Board myBoard;
	private boolean[][][] bBoard;
	
	public static void main(String[] args) {
		Board b = new Board();
		b.loadTest2();
		Solver s = new Solver(b);
		s.solve();
		b.display();
		//int[] arr = new int[]{1, 2, 3, 4, 5,3};
		//System.out.println(repeats(arr));
	}
	//will mutate b
	//bBoard is 9x9x10. first index of last dimension tells if the piece is established or not
	public Solver(Board b) {
		myBoard = b;
		bBoard = new boolean[9][9][10];
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				for(int k = 1; k < 10; k++) {
					bBoard[i][j][k] = true;
				}
			}
		}
		
	}
	
	private boolean isComplete() {
		return numFilled() == 81;
	}
	//returns number of locations solved
	private int numFilled() {
		int count = 0;
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				if(myBoard.getPiece(i,j) != 0) {
					count++;
				}
			}
		}
		return count;
	}
	public void solve() {
		if(!sanityCheck()) {
			System.out.println("Bad board");
			return;
		}
		initiate();
		int count = numFilled();
		while(!isComplete()) {
			System.out.println(numFilled());
			reduce();
			deduce();
			apply();
			int newCount = numFilled();
			if(count == newCount) {
				System.out.println("Sorry. Too hard for me.");
				return;
			}
			count = newCount;
		}
		if(sanityCheck()) {
			System.out.println("Successful solve!");
		}
		else {
			System.out.println("I messed up.. oops");
		}
		
	}
	
	//initializes stuff for first time. probably not necessary
	private void initiate() {
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				int piece = myBoard.getPiece(i,j);
				if(piece != 0) {
					for(int k = 1; k < 10; k++) {
						bBoard[i][j][k] = false;
					}
					bBoard[i][j][piece] = true;
					bBoard[i][j][0] = true;
				}
			}
		}
	}
	//reduces options on the boolean matrix
	private void reduce() {
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				if(myBoard.getPiece(i,j) == 0) {
					int[] takenPieces = myBoard.getAllRelevant(i,j);
					for(int k = 0; k < takenPieces.length; k++) {
						bBoard[i][j][takenPieces[k]] = false;
					}
				}
			}	
		}
	}
	
	//finds all fully reduced locations and deals with them
	private void apply() {
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				if(myBoard.getPiece(i,j) == 0) {
					int numPossibles = 0;
					int track = 0;
					for(int k = 1; k < 10; k++) {
						if(bBoard[i][j][k]) {
							track = k;
							numPossibles++;
						}
					}
					if(numPossibles == 1) { //winner!
						myBoard.changePiece(i, j, track);
						bBoard[i][j][0] = true;
					}
				}
			}
		}
	}
	
	private static boolean contains(int[] arr, int x) {
		for(int i = 0; i < arr.length; i++) {
			if(arr[i] == x) {
				return true;
			}
		}
		return false;
	}
	private void deduce() {
		//vertical
		for(int k = 1; k < 10; k++) {
			for(int i = 0; i < 9; i++) {
				int[] checkFirst = myBoard.getVerticalValues(i,0);
				if(!contains(checkFirst, k)) {
					int count = 0;
					int track = 0;
					for(int j = 0; j < 9; j++) {
						if(bBoard[i][j][k]) {
							count++;
							track = j;
						}
					}
					if(count == 1) {
						for(int l = 0; l < 10; l++) {
							bBoard[i][track][l] = false;
						}
						bBoard[i][track][k] = true;
					}
				}
			}
		}
		//horizontal
		for(int k = 1; k < 10; k++) {
			for(int j = 0; j < 9; j++) {
				int[] checkFirst = myBoard.getHorizontalValues(0,j);
				if(!contains(checkFirst, k)) {
					int count = 0;
					int track = 0;
					for(int i = 0; i < 9; i++) {
						if(bBoard[i][j][k]) {
							count++;
							track = i;
						}
					}
					if(count == 1) {
						for(int l = 0; l < 10; l++) {
							bBoard[track][j][l] = false;
						}
						bBoard[track][j][k] = true;
					}
				}
			}
		}
		//gridwise
		for(int k = 1; k < 10; k++) {
				for(int i = 0; i < 9; i+=3) {
					for(int j = 0; j < 9; j+=3) {
						int[] checkFirst = myBoard.getGridValues(i,j);
						if(!contains(checkFirst, k)) {
							int count = 0; 
							int trackX = 0;
							int trackY = 0;
							for(int x = 0;  x < 3; x++) {
								for(int y = 0; y < 3; y++) {
									if(bBoard[i + x][j + y][k]) {
										count++;
										trackX = i + x;
										trackY = j + y;
									}
								}
							}
							if(count == 1) {
								for(int l = 0; l < 10; l++) {
									bBoard[trackX][trackY][l] = false;
								}
								bBoard[trackX][trackY][k] = true;
							}
						}
					}
				}
		}
		
	}
	
	//returns true if the board has nothing wrong with it
	private boolean sanityCheck() {
		for(int i = 0; i < 9; i++) {
			int[] vertical = myBoard.getVerticalValues(i,0);
			if(repeats(vertical)) {
				return false;
			}
		}
		for(int j = 0; j < 9; j++) {
			int[] horizontal = myBoard.getHorizontalValues(0,j);
			if(repeats(horizontal)) {
				return false;
			}
		}
		for(int i = 0; i < 9; i += 3) {
			for(int j = 0; j < 9; j += 3) {
				int[] grid = myBoard.getGridValues(i,j);
				if(repeats(grid)) {
					return false;
				}
			}
		}
		return true;
	}
	
	//returns true if there are repeats 
	private static boolean repeats(int[] arr) {
		boolean[] check = new boolean[10];
		for(int i = 0; i < arr.length; i++) {
			if(check[arr[i]]) {
				return true;
			}
			else {
				check[arr[i]] = true;
			}
		}
		return false;
	}
}