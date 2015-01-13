public class Test {
	public static void main(String[] args) {
		//String str = "hello";
		//System.out.println(str.substring(1,2));
		Board newB = new Board();
		newB.loadTest();	
		newB.display();
		System.out.println(atos(newB.getHorizontalValues(3,5)));
		System.out.println(atos(newB.getVerticalValues(3,5)));
		System.out.println(atos(newB.getGridValues(3,5)));
		System.out.println(atos(newB.getAllRelevant(3,5)));
	}
	public static String atos(int[] arr) {
		String s = "[ ";
		for(int i = 0; i < arr.length; i++) {
			s += arr[i] + " ";
		}
		return s + "]";
	}
}