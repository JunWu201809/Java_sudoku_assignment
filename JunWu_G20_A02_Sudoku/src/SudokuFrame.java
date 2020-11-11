import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;

/**
 * the main swing frame of a sudoku game
 * 
 * @author wujun
 *
 */
public class SudokuFrame extends JFrame implements ActionListener {

	private JMenuBar menuBar = new JMenuBar();
	private JMenu actionMenu = new JMenu("Actions");
	private JMenu fileMenu = new JMenu("File");
	private JMenuItem defaultFileItem = new JMenuItem("load default");
	private JMenuItem loadFileItem = new JMenuItem("other files...");
	private JTextArea areaDisplay = new JTextArea();
	private JMenuItem menuItemUndo;
	private JMenuItem menuItemSave;
	private JMenuItem menuItemHelp;
	private JMenuItem menuItemAbout;
	private JMenuItem menuItemExit;
	/**
	 * default JPanel
	 */
	private JPanel jPanel;
	/**
	 * two-dimensional button array, 9*9 
	 */
	private JButton[][] jButtons;
	/**
	 * load sudo game object
	 */
	private SudoGame sudoGame = new SudoGame();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SudokuFrame frame = new SudokuFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SudokuFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 622, 492);

		setJMenuBar(menuBar);
		menuBar.add(fileMenu);
		menuBar.add(actionMenu);
		fileMenu.add(defaultFileItem);
		fileMenu.add(loadFileItem);
		menuItemUndo = new JMenuItem("Undo");
		actionMenu.add(menuItemUndo);

		menuItemSave = new JMenuItem("Save");
		actionMenu.add(menuItemSave);

		menuItemHelp = new JMenuItem("Help");
		actionMenu.add(menuItemHelp);

		menuItemAbout = new JMenuItem("About");
		actionMenu.add(menuItemAbout);

		menuItemExit = new JMenuItem("Exit");
		actionMenu.add(menuItemExit);

		areaDisplay.setEditable(false);
		areaDisplay.setFont(new Font("Courier New", 0, 11));
		areaDisplay.setBounds(10, 55, 648, 525);
		jPanel = new JPanel();
		jPanel.setLayout(new GridLayout(9, 9));
		getContentPane().add(jPanel);

		defaultFileItem.addActionListener(this);
		loadFileItem.addActionListener(this);
		menuItemUndo.addActionListener(this);
		menuItemSave.addActionListener(this);
		menuItemExit.addActionListener(this);
		menuItemAbout.addActionListener(this);
		menuItemHelp.addActionListener(this);
	}//SudokuFrame()

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == menuItemExit)
			System.exit(0);
		else if (e.getSource() == defaultFileItem)
			defaultFileItem_actionPerformed(e);
		else if (e.getSource() == menuItemUndo)
			undo_actionPerformed(e);
		else if (e.getSource() == loadFileItem) {
			loadFile_actionPerformed(e);
		} else if (e.getSource() == menuItemHelp)
			JOptionPane.showMessageDialog(this, new Sudoku_HelpPanel(), "Help", JOptionPane.PLAIN_MESSAGE);
		else if (e.getSource() == menuItemSave) {
			try {
				sudoGame.save2File();
			} catch (Exception e1) {
				System.out.println(e1.getMessage());
				System.exit(0);
			}
			JOptionPane.showMessageDialog(this, "You have saved the game successfully!");
		} else if (e.getSource() == menuItemAbout) {
			JOptionPane.showMessageDialog(this, new Sudoku_AboutPanel(), "About", JOptionPane.PLAIN_MESSAGE);
		} else if (e.getSource() == menuItemExit) {
			try {
				sudoGame.save2File();
			} catch (Exception e1) {
				System.out.println(e1.getMessage());
				System.exit(0);
			}
			System.exit(0);
		} else
			loadBatchButs_actionPerformed(e);
	} // actionPerformed(ActionEvent)

	/**
	 * process click any button of puzzle
	 * @param e
	 */
	protected void loadBatchButs_actionPerformed(ActionEvent e) {
		if (jButtons != null) {
			for (int i = 1; i < jButtons.length; i++) {
				for (int j = 1; j < jButtons[i].length; j++) {
					if (e.getSource() == jButtons[i][j]) {
						if (sudoGame.getBoard()[i][j] == 0) {
							SudokuInputNumFrame child = new SudokuInputNumFrame(this, sudoGame, i, j);
							child.setVisible(true);
							break;
						}
					}
				}
			}

		}
	}//loadBatchButs_actionPerformed(ActionEvent e)

	/**
	 * process click the menu of loading other files
	 * @param e
	 */
	protected void loadFile_actionPerformed(ActionEvent e) {
		JFileChooser jf = new JFileChooser();
		jf.showOpenDialog(this);
		try {
			File f = jf.getSelectedFile();
			String fileName = f.getAbsolutePath();
			if (!sudoGame.checkLegalFileFormat(fileName)) {
				JOptionPane.showMessageDialog(this, "the "+fileName+"'s format not correct!");
				return;
			}
			sudoGame.loadFile(fileName);
			printBoard();
		} catch (Exception e2) {
			System.err.println("load file occur errors:" + e2.getMessage());
		}

	}// loadFile_actionPerformed(ActionEvent e) 

	/**
	 * process click the undo of loading other files menu
	 * @param e
	 */
	protected void undo_actionPerformed(ActionEvent e) {
		if (sudoGame.checkAbleUndo()) {
			sudoGame.undoAction();
			JOptionPane.showMessageDialog(this, "You have undo the game successfully!");
			printBoard();
		} else {
			JOptionPane.showMessageDialog(this, "You don't allow to undo in there.");
			;
		}
	} // defaultFileItem_actionPerformed(ActionEvent)

	/**
	 *  process click the default file menu
	 * @param e
	 */
	protected void defaultFileItem_actionPerformed(ActionEvent e) {
		try {
			sudoGame.loadFile("");
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
			System.exit(0);
		}
		areaDisplay.setText("");
		printBoard();
	} // defaultFileItem_actionPerformed(ActionEvent)

	/**
	 * print the current board to console
	 */
	private void printBoard() {
		Border leftBorder = BorderFactory.createMatteBorder(1, 1, 0, 2, Color.BLUE);
		Border nobBorder = BorderFactory.createMatteBorder(1, 1, 0, 0, Color.BLUE);
		Border bottomBorder = BorderFactory.createMatteBorder(1, 1, 2, 0, Color.BLUE);
		Border leftAndbottomBorder = BorderFactory.createMatteBorder(1, 1, 2, 2, Color.BLUE);
		jButtons = new JButton[10][10];
		int[][] board = sudoGame.getBoard();
		jPanel.removeAll();
		for (int i = 1; i < board.length; i++) {
			for (int j = 1; j < board[i].length; j++) {
				String butStr = " ";
				if (board[i][j] != 0)
					butStr = board[i][j] + butStr;
				JButton jButton = new JButton(butStr);
				if (i % 3 == 0) {
					if (j % 3 == 0)
						jButton.setBorder(leftAndbottomBorder);
					else
						jButton.setBorder(bottomBorder);
				} else if (j % 3 == 0)
					jButton.setBorder(leftBorder);
				else
					jButton.setBorder(nobBorder);
				jButton.addActionListener(this);
				jButtons[i][j] = jButton;
				jPanel.add(jButton);
			}
		}
		jPanel.revalidate();
	}// printBoard()

	public JButton[][] getjButtons() {
		return jButtons;
	}

	public void setjButtons(JButton[][] jButtons) {
		this.jButtons = jButtons;
	}

	public SudoGame getSudoGame() {
		return sudoGame;
	}

	public void setSudoGame(SudoGame sudoGame) {
		this.sudoGame = sudoGame;
	}

}
