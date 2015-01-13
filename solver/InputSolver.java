import java.io.FileReader;

public class InputSolver {
	public static void main(String[] args) {
		Board b =  readBoard();
		solveBoard(b);
		
	}
	
	private static Board readBoard() {
		Board b = new Board();
		try {
			FileReader reader = new FileReader("Puzzle.txt");
			String str = "";
			int asc = reader.read();
			while(asc != 35) { // 35 is #
				if(asc != 13 && asc!= 10) {
					str += (asc - 48);
				}
				asc = reader.read();
			}
			System.out.println(str);
			b.loadString(str);
			
		} catch(Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return b;
		
	}
	
	private static void solveBoard(Board b) {
		b.display();
		System.out.println("Solving...");
		System.out.println();
		Solver s = new Solver(b);
		s.solve();
		b.display();
	}
	
	private static void test() {		
		try {
			FileReader reader = new FileReader("Puzzle.txt");
			String str = "";
			int asc = reader.read();
			while(asc != 35) { // 35 is #
				if(asc != 13 && asc != 10) {
					str += (asc - 48) ;
				}
				asc = reader.read();
			}
			System.out.println(str);
		} catch(Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	
	}
	
}