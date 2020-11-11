import java.util.ArrayList;
import java.util.Random;

public class MyTest {
	
	public void arrayListPractice() {
		ArrayList<Integer> testList = new ArrayList<Integer>();
		for (int i = 0; i < 6; i++) {
			int num = 1 + (int)(Math.random() * 10);
			testList.add(num);
		}
		for (int i = testList.size() -1; i >= 0; i--) {
			int num = testList.get(i);
			if(num % 3 == 0)
				testList.remove(i);
		}
		for (int i = testList.size() -1; i >= 0; i--) {
			int num = testList.get(i);
			testList.add(num);
		}
		for (int i = testList.size()-1; i >= 0; i--) {
			int num = testList.get(i);
			System.out.print(num+",");
		}
	}
	public static void main(String[] args) {
		MyTest mTest = new MyTest();
		mTest.arrayListPractice();

	}

}
