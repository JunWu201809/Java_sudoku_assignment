import java.util.Scanner;

/**
 * the command interface of sudo games
 * 
 * @author wujun
 *
 */
public class SudokuInterface {

	static Scanner input = new Scanner(System.in);
	/**
	 * sudo game base class
	 */
	private SudoGame sudoGame = new SudoGame();

	/**
	 * the first action after player came in
	 */
	private void initGame() {
		System.out.println("Welcome to Heritage SudoKu.");
		System.out.println("Please enter the filename for your puzzle:");
		String fileName = input.nextLine();
		if (fileName == null || fileName.equals(""))
			fileName = sudoGame.DEFAULT_FILE_NAME;

		if (!sudoGame.checkLegalFile(fileName)) {
			System.out.println(fileName + " does not exist.");
			System.out.println("Please enter the filename for your puzzle:");
			fileName = input.nextLine();
		} else if (!sudoGame.checkLegalFileFormat(fileName)) {
			System.out.println(fileName + " does not have the correct file format.");
			System.out.println("Please enter the filename for your puzzle:");
			fileName = input.nextLine();
		}
		try {
			sudoGame.loadFile(fileName);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		System.out.println("Type Q at any time to exit the game, S to save the game or U to undo your last move");
	}// initGame()

	/**
	 * print the current board to console
	 */
	private void printBoard() {
		int[][] board = sudoGame.getBoard();
		for (int i = 1; i < board.length; i++) {
			for (int j = 1; j < board[i].length; j++) {
				System.out.print(board[i][j] + " ");
				if (j % 3 == 0)
					System.out.print("| ");
			}
			System.out.println("");
			if (i % 3 == 0)
				System.out.println("-----------------------");
		}
	}// printBoard()

	/**
	 * process the player's input, such as 'U' 'S' 'Q' and move
	 */
	private void processMove() {
		System.out.println("Enter square number (row,column)->");
		int row = 0, col = 0, num = 0;
		String lineStr = input.nextLine();
		if (processAction(lineStr))
			processMove();
		else {
			if (lineStr.length() < 3 || lineStr.indexOf(',') == -1) {
				System.out.println("ERROR:Invalid row and col number input");
				processMove();
			}
			String rowStr = lineStr.substring(0, 1);
			String colStr = lineStr.substring(2);
			try {
				row = Integer.parseInt(rowStr);
				col = Integer.parseInt(colStr);
			} catch (Exception e) {
				System.out.println("ERROR: Read the row col numbers occurs some error: " + e.getMessage());
			}
		}
		System.out.println("Enter value:");
		lineStr = input.nextLine();
		if (processAction(lineStr))
			processMove();
		else {
			try {
				num = Integer.parseInt(lineStr);
			} catch (Exception e) {
				System.out.println("ERROR: Read the value number occurs some error: " + e.getMessage());
			}
			if (sudoGame.validate(row, col, num)) {
				sudoGame.moveAction(row, col, num);
				System.out.println("You have moved successfully!");
				printBoard();
			} else {
				System.out.println(sudoGame.getValidStr());
			}
			processMove();
		}
	}// processMove()

	/**
	 * recognize and process the player's input, when it's a 'Q', 'S','U'
	 * 
	 * @param key
	 * @return
	 */
	private boolean processAction(String key) {
		if (key.equalsIgnoreCase("Q")) {
			try {
				sudoGame.save2File();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.exit(0);
			}
			System.out.println("You quit from the sudo game, welcome to play it again!");
			System.exit(0);
			return true;
		} else if (key.equalsIgnoreCase("S")) {
			try {
				sudoGame.save2File();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.exit(0);
			}
			System.out.println("You have saved the game successfully!");
			return true;
		} else if (key.equalsIgnoreCase("U")) {
			if (sudoGame.checkAbleUndo()) {
				sudoGame.undoAction();
				System.out.println("You have undo the game successfully!");
			} else {
				System.out.println("You don't allow to undo in there.");
			}
			printBoard();
			return true;
		}
		return false;
	}// processAction(String key)

	/**
	 * the main entrance of this program
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SudokuInterface sdkInf = new SudokuInterface();
		sdkInf.initGame();
		sdkInf.printBoard();
		sdkInf.processMove();
	}// main(String[] args)

}
