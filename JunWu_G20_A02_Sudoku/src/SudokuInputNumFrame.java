import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * the sub dialog for user input number
 * 
 * @author wujun
 *
 */
public class SudokuInputNumFrame extends JDialog implements ActionListener {

	private JPanel contentPane;
	private SudoGame sudoGame;
	private SudokuFrame parent;
	/**
	 * which row of the user click from parent
	 */
	private int row = 0;
	/**
	 * which col of the user click from parent
	 */
	private int col = 0;

	/**
	 * Create the frame.
	 */
	public SudokuInputNumFrame() {
		setBounds(800, 200, 200, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(contentPane);
		contentPane.setLayout(new GridLayout(3, 3));
		for (int i = 1; i <= 9; i++) {
			JButton jButton = new JButton(i + "");
			jButton.addActionListener(this);
			contentPane.add(jButton);
		}
	}// SudokuInputNumFrame()

	/**
	 * Create the frame.
	 */
	public SudokuInputNumFrame(SudokuFrame parent, SudoGame sudoGame, int row, int col) {
		this.parent = parent;
		this.sudoGame = sudoGame;
		this.row = row;
		this.col = col;
		setBounds(800, 200, 200, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(contentPane);
		contentPane.setLayout(new GridLayout(3, 3));
		for (int i = 1; i <= 9; i++) {
			JButton jButton = new JButton(i + "");
			jButton.addActionListener(this);
			contentPane.add(jButton);
		}
	}// SudokuInputNumFrame(SudokuFrame parent, SudoGame sudoGame, int row, int col)

	@Override
	public void actionPerformed(ActionEvent e) {
		String source = e.getActionCommand();
		for (int i = 1; i < 10; i++) {
			if (source.equals(i + "")) {
				processMove(i);
				this.dispose();
			}
		}

	} // actionPerformed(ActionEvent)

	/**
	 * process the action of move
	 * 
	 * @param num
	 */
	private void processMove(int num) {
		if (sudoGame.validate(row, col, num)) {
			parent.getjButtons()[row][col].setText(num + "");
			sudoGame.moveAction(row, col, num);
			parent.setSudoGame(sudoGame);
		} else {
			JOptionPane.showMessageDialog(this, sudoGame.getValidStr(), "Move Failure", JOptionPane.ERROR_MESSAGE);
		}
	}// processMove(int num)

	public SudoGame getSudoGame() {
		return sudoGame;
	}

	public void setSudoGame(SudoGame sudoGame) {
		this.sudoGame = sudoGame;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

}
