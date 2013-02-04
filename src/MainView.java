import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;

import java.awt.*;
import java.awt.event.WindowEvent;

public class MainView extends JFrame{

	Toolkit toolkit = Toolkit.getDefaultToolkit();
	Dimension dim = toolkit.getScreenSize();
	private final int WIN_WIDTH = (int)dim.getWidth();
	private final int WIN_HEIGHT = (int)dim.getHeight();
	private int row, column;
	
	private MainController ctrl;
	private JTextField rowTextField;
	private JTextField colTextField;
	private JButton setDimBtn = new JButton("Set Dimension");
	private JLabel currentDimension;
	private GameCanvas canvas = null;
	private JButton runBtn;
	private JButton pauseBtn;
	private JButton clearBtn;

	public MainView(MainController control) {
		super("Game of Life");
		this.ctrl = control;
		row = 5;
		column = 5;
	}
	
	protected JComponent createMainPanel() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		mainPanel.add(initDimension(), BorderLayout.NORTH);
		mainPanel.add(createCanvas(), BorderLayout.CENTER);
		mainPanel.add(createControls(), BorderLayout.SOUTH);

		return mainPanel;
	}
	
	protected JComponent initDimension(){
		JPanel dimPanel = new JPanel();
		dimPanel.setLayout(new GridLayout(2, 1));
		JPanel resetPanel = new JPanel();
		JPanel infoPanel = new JPanel();
		JPanel rowPanel = new JPanel();
		JPanel colPanel = new JPanel();
		
		rowTextField = new JTextField("5");
		rowTextField.setToolTipText("3-100");
		rowTextField.setPreferredSize(new Dimension(100, 25));
		rowPanel.add(new JLabel("rows: "));
		rowPanel.add(rowTextField);
		
		colTextField = new JTextField("5");
		colTextField.setToolTipText("3-100");
		colTextField.setPreferredSize(new Dimension(100, 25));
		colPanel.add(new JLabel("Columns: "));
		colPanel.add(colTextField);
		
		setDimBtn = new JButton("Reset Dimension (Input 3-100)");
		setDimBtn.setPreferredSize(new Dimension(250, 25));
		setDimBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				row = Integer.parseInt(rowTextField.getText());
				column = Integer.parseInt(colTextField.getText());
				canvas.resetDimension(row, column);
				ctrl.resetDimension();
				currentDimension.setText("Current game board has " + getRows() + " rows" + 
						" x " + getColumns() + " columns.");
			}
		});
		
		currentDimension = new JLabel("Current game board has " + getRows() + " rows" + 
		" x " + getColumns() + " columns.");
		
		resetPanel.add(rowPanel);
		resetPanel.add(colPanel);
		resetPanel.add(setDimBtn);
		infoPanel.add(currentDimension);
		dimPanel.add(resetPanel);
		dimPanel.add(infoPanel);
		return dimPanel;
	}
	
	protected JComponent createCanvas(){
		canvas = new GameCanvas(this.ctrl, WIN_WIDTH, WIN_HEIGHT-250, getRows(), getColumns());
		
		return canvas;
	}
	
	protected JComponent createControls(){
		JPanel ctrlPanel = new JPanel();
		runBtn = new JButton("Run");
		runBtn.setPreferredSize(new Dimension(100, 30));
		runBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				ctrl.runGame();
			}
		});
		
		pauseBtn = new JButton("Pause");
		pauseBtn.setPreferredSize(new Dimension(100, 30));
		pauseBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				ctrl.pauseGame();
			}
		});
		
		clearBtn = new JButton("Wipe Out");
		clearBtn.setPreferredSize(new Dimension(100, 30));
		clearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				ctrl.wipeBoard();
				canvas.repaint();
			}
		});
		
		ctrlPanel.add(runBtn);
		ctrlPanel.add(pauseBtn);
		ctrlPanel.add(clearBtn);
		
		return ctrlPanel;
	}
	
	public void openDbox() {
		Container cp = getContentPane();
		cp.add(this.createMainPanel());

		this.setSize(WIN_WIDTH, WIN_HEIGHT);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				System.exit(0);
			}
		});
		this.setVisible(true);
	}
	
	public int getRows(){
		return this.row;
	}
	
	public int getColumns(){
		return this.column;
	}
	
	public void repaintCanvas(){
		canvas.repaint();
	}
}
