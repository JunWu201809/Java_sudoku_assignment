
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * the Popup sub panel of help
 * 
 * @author wujun
 *
 */
public class Sudoku_HelpPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public Sudoku_HelpPanel() {
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblAuthor = new JLabel("1. You must place the numbers 1-9 in each row without repeating a number.");
		GridBagConstraints gbc_lblAuthor = new GridBagConstraints();
		gbc_lblAuthor.insets = new Insets(0, 0, 5, 0);
		gbc_lblAuthor.gridx = 0;
		gbc_lblAuthor.gridy = 1;
		add(lblAuthor, gbc_lblAuthor);

		JLabel lblCopyright = new JLabel(
				"2. You must place the numbers 1-9 in each column without repeating a number.");
		GridBagConstraints gbc_lblCopyright = new GridBagConstraints();
		gbc_lblCopyright.insets = new Insets(0, 0, 5, 0);
		gbc_lblCopyright.gridx = 0;
		gbc_lblCopyright.gridy = 2;
		add(lblCopyright, gbc_lblCopyright);

		JLabel lblCompany = new JLabel(
				"3. You must place the numbers 1-9 in each of the marked 3*3 boxes without repeating a number.");
		GridBagConstraints gbc_lblCompany = new GridBagConstraints();
		gbc_lblCompany.gridx = 0;
		gbc_lblCompany.gridy = 3;
		add(lblCompany, gbc_lblCompany);
	}

}
