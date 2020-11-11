import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * The class includes main logic functions and variables of Sudo game
 * 
 * @author wujun
 * 
 */
public class SudoGame {
	/**
	 * default file name of sudo game
	 */
	public final static String DEFAULT_FILE_NAME = "sudoku.txt";
	/**
	 * the symbol for delimit of the number
	 */
	public final static String BOARD_DELIMIT_SYMBOL = "~";
	/**
	 * the symbol an empty
	 */
	public final static String BOARD_EMPTY_SYMBOL = "*";
	
	public final static int BOARD_ROW_LEN = 10;

	public final static int BOARD_COL_LEN = 10;
	
	public final static int BOARD_LAST_STEP_LEN = 3;
	
	/**
	 * a two-dimensional array to store the game board,and do not use the index 0
	 */
	private int[][] board = new int[BOARD_ROW_LEN][BOARD_COL_LEN];

	/**
	 * a dimensional array to store last step of the game board,[0] row, [1] col,
	 * [2] value
	 */
	private int[] preMove = new int[BOARD_LAST_STEP_LEN];
	/**
	 * writer of file
	 */
	private FileWriter fileWriter;
	/**
	 * reader of file
	 */
	private Scanner fileReader;
	/**
	 * check the writer is already opened or not
	 */
	private boolean writerIsOpen;
	/**
	 * put the error message of validation.
	 */
	private String validStr;
	/**
	 * put the name of sudo game file
	 */
	private String fileName;

	/**
	 * load the sudo file
	 * 
	 * @param fullFileName file name
	 * @return
	 * @throws Exception 
	 */
	public boolean loadFile(String fullFileName) throws Exception {
		if (fullFileName == null || fullFileName.equals(""))
			fullFileName = DEFAULT_FILE_NAME;
		openInput(fullFileName);
		fileName = fullFileName;
		int row = 1;
		int col = 1;
		try {
			while (fileReader.hasNext()) {
				StringTokenizer inputLine = new StringTokenizer(fileReader.nextLine(), "~");
				if (inputLine.countTokens() >= 9) {
					while (inputLine.hasMoreTokens()) {
						String numStr = inputLine.nextToken();
						if (numStr.equals(BOARD_EMPTY_SYMBOL))
							numStr = "0";
						int num = Integer.parseInt(numStr);
						board[row][col] = num;
						col++;
					}
				} // if (inputLine.countTokens() >= 9)
				col = 1;
				row++;
			} // while (inventoryReader.hasNext())
		} catch (Exception e) {
//			System.out.println("ERROR: Load Board file " + fullFileName + " occurs some error: " + e.getMessage());
			throw new Exception("ERROR: Load Board file " + fullFileName + " occurs some error: " + e.getMessage());
		} finally {
			fileReader.close();
		}
		return true;
	}// loadFile(String fullFileName)

	/**
	 * save the board array to file
	 * 
	 * @param fullFileName
	 * @return
	 * @throws Exception 
	 */
	public boolean save2File() throws Exception {
		if (!writerIsOpen) {
			openOutput(fileName);
		}
		try {
			for (int i = 1; i < board.length; i++) {
				for (int j = 1; j < board[i].length; j++) {
					int value = board[i][j];
					if (value == 0)
						fileWriter.write(BOARD_EMPTY_SYMBOL);
					else {
						fileWriter.write(value + "");
					}
					fileWriter.write(BOARD_DELIMIT_SYMBOL);
				}
				fileWriter.write("\n");
			}
		} // try
		catch (IOException e) {
//			System.out.println("ERROR: Board  could not be written to file " + fileName + ": " + e.getMessage());
			throw new Exception("ERROR: Board  could not be written to file " + fileName + ": " + e.getMessage());
		} finally {
			closeOutput(fileName);
		}
		return true;
	} // save2File(fullFileName)

	/**
	 * open a new file writer
	 * 
	 * @param fileName
	 * @throws Exception 
	 */
	private void openOutput(String fileName) throws Exception {
		File boardFile = new File(fileName);
		try {
			fileWriter = new FileWriter(boardFile);
			writerIsOpen = true;
		} // try
		catch (IOException e) {
//			System.out.println("ERROR: File " + boardFile + "could not opened: " + e.getMessage());
			throw new Exception("ERROR: File " + boardFile + "could not opened: " + e.getMessage());
		} // catch
	} // openOutput(String)

	/**
	 * close the file writer
	 * 
	 * @param filename
	 * @throws Exception 
	 */
	private void closeOutput(String filename) throws Exception {
		try {
			fileWriter.close();
			writerIsOpen = false;
		} // try
		catch (IOException e) {
//			System.out.println("ERROR: File " + filename + "could not closed: " + e.getMessage());
			throw new Exception("ERROR: File " + filename + "could not closed: " + e.getMessage());
		} // catch
	} // closeOutput(String filename)

	/**
	 * check the file reader is open or not
	 * 
	 * @param fileName
	 * @return
	 */
	private boolean openInput(String fileName) {
		try {
			fileReader = new Scanner(new File(fileName));
			return true;
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: " + fileName + " was not found");
			return false;
		} catch (IOException e) {
			System.out.println("ERROR: Error opening " + fileName + ": " + e.getMessage());
			return false;
		}
	} // openInput()

	/**
	 * the top validation of a sudo game move
	 * 
	 * @param row
	 * @param col
	 * @param num
	 * @return true:legal move, false,inlegal move
	 */
	public boolean validate(int row, int col, int num) {
		if (!validateColumn(col))
			return false;
		else if (!validateRow(row))
			return false;
		else if (!checkIlegalMove(row, col, num))
			return false;
		return true;
	}// validate(int row, int col, int num)

	/**
	 * check the col of input is legal or not
	 * 
	 * @param col
	 * @return
	 */
	private boolean validateColumn(int col) {
		if (col < 10 && col > 0)
			return true;
		else {
			validStr = "Invalid column number. Please try again.";
			return false;
		}
	}// validateColumn(int col)

	/**
	 * check the row of input is legal or not
	 * 
	 * @param row
	 * @return
	 */
	private boolean validateRow(int row) {
		if (row < 10 && row > 0)
			return true;
		else {
			validStr = "Invalid row number. Please try again.";
			return false;
		}
	}// validateRow(int row)

	/**
	 * check the num of input is legal or not no same num in the same row/num/sqr
	 * 
	 * @param row
	 * @param col
	 * @param num
	 * @return
	 */
	private boolean checkIlegalMove(int row, int col, int num) {
		if (board[row][col] != 0) {
			validStr = "Invalid move. That location already has a value. Please try again.";
			return false;
		}else if(num >9 || num < 1)
		{
			validStr = "Invalid move. The " + num + " is ilegal. Please try again.";
			return false;
		}
		else if (!checkNumInRow(row, num)) {
			validStr = "Invalid move. There is a " + num + " in that row/column/square. Please try again.";
			return false;
		} else if (!checkNumInCol(col, num)) {
			validStr = "Invalid move. There is a " + num + " in that row/column/square. Please try again.";
			return false;
		} else if (!checkNumInSqr(row, col, num)) {
			validStr = "Invalid move. There is a " + num + " in that row/column/square. Please try again.";
			return false;
		}
		return true;
	}// checkIllegalMove(int row, int col, int num)

	/**
	 * check the num of input is legal or not no same num in the same row
	 * 
	 * @param row
	 * @param num
	 * @return
	 */
	private boolean checkNumInRow(int row, int num) {
		for (int i = 1; i < board[row].length; i++) {
			if (board[row][i] == num)
				return false;
		}
		return true;
	}// checkNumInRow(int row, int num)

	/**
	 * check the num of input is legal or not no same num in the same col
	 * 
	 * @param col
	 * @param num
	 * @return
	 */
	private boolean checkNumInCol(int col, int num) {
		for (int i = 1; i < board.length; i++) {
			if (board[i][col] == num)
				return false;
		}
		return true;
	}// checkNumInCol(int col, int num)

	/**
	 * check the num of input is legal or not no same num in the same square
	 * 
	 * @param row
	 * @param col
	 * @param num
	 * @return
	 */
	private boolean checkNumInSqr(int row, int col, int num) {
		int rSqr = ((row - 1) / 3) * 3;
		int cSqr = ((col - 1) / 3) * 3;
		for (int i = 1; i <= 3; i++) {
			for (int j = 1; j <= 3; j++) {
				if ((i + rSqr != row || j + cSqr != col) && board[i + rSqr][j + cSqr] == num)
					return false;
			}
		}
		return true;
	}// checkNumInSqr(int row, int col, int num)

	/**
	 * the action of a moving
	 * 
	 * @param row
	 * @param col
	 * @param num
	 */
	public void moveAction(int row, int col, int num) {
		preMove[0] = row;
		preMove[1] = col;
		preMove[2] = board[row][col];
		board[row][col] = num;
	}// moveAction(int row, int col, int num)

	/**
	 * check enable of undoing
	 * 
	 * @return
	 */
	public boolean checkAbleUndo() {
		if (preMove[0] != 0 && preMove[1] != 0)
			return true;
		else
			return false;
	}// checkAbleUndo()

	/**
	 * check the file is exist or not
	 * 
	 * @param fileName
	 * @return
	 */
	public boolean checkLegalFile(String fileName) {
		File file = new File(fileName);
		return file.exists();
	}// checkLegalFile(String fileName)

	/**
	 * check the format of file is legal or not
	 * 
	 * @param fileName
	 * @return
	 */
	public boolean checkLegalFileFormat(String fileName) {
		openInput(fileName);
		int row = 1;
		int col = 1;
		try {
			while (fileReader.hasNext()) {
				StringTokenizer inputLine = new StringTokenizer(fileReader.nextLine(), "~");
				if (inputLine.countTokens() != 9)
					return false;
				row++;
			} // while (inventoryReader.hasNext())
			if (row != 10)
				return false;
		} catch (Exception e) {
			System.out.println("ERROR: Load Board file " + fileName + " occurs some error: " + e.getMessage());
			return false;
		} finally {
			fileReader.close();
		}
		return true;
	}// checkLegalFileFormat(String fileName)

	/**
	 * the action of a undoing
	 */
	public void undoAction() {
		int row = preMove[0];
		int col = preMove[1];
		int num = preMove[2];
		board[row][col] = num;
		preMove[0] = 0;
		preMove[1] = 0;
		preMove[2] = 0;
	}// undoAction()

	public int[][] getBoard() {
		return board;
	}

	public void setBoard(int[][] board) {
		this.board = board;
	}

	public String getValidStr() {
		return validStr;
	}

	public void setValidStr(String validStr) {
		this.validStr = validStr;
	}

//	/**
//	 * print board array
//	 */
//	private void printBoard() {
//		for (int i = 1; i < board.length; i++) {
//			for (int j = 1; j < board[i].length; j++) {
//				System.out.print(board[i][j]);
//			}
//			System.out.println("");
//		}
//	}// printBoard()
//
//	/**
//	 * main method for testing
//	 * 
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		SudoGame sudoGame = new SudoGame();
//		sudoGame.loadFile(DEFAULT_FILE_NAME);
//		sudoGame.printBoard();
//		sudoGame.validateColumn(4);
//		System.out.println(sudoGame.getValidStr());
//		sudoGame.validateRow(11);
//		System.out.println(sudoGame.getValidStr());
//		sudoGame.checkIlegalMove(1, 4, 8);
//		System.out.println(sudoGame.getValidStr());
//		sudoGame.moveAction(1, 4, 6);
//		sudoGame.save2File();
//		System.out.println(sudoGame.checkLegalFileFormat("sudoku.txt"));
//	}// main(String[] args)
}// class SudoGame
