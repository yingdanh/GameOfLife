import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.JPanel;


public class GameCanvas extends JPanel{
	private int canvasWidth;
	private int canvasHeight;
	private int boardWidth;
	private int boardHeight;
	private int cellDim;
	private int row;
	private int column;
	private MainController ctrl;

	public GameCanvas(MainController ctrl, int w, int h, int row, int column) {
		canvasWidth = w;
		canvasHeight = h;
		cellDim = Math.min(canvasWidth/column, canvasHeight/row);
		boardWidth = cellDim*column;
		boardHeight = cellDim*row;
		this.row = row;
		this.column = column;
		this.ctrl = ctrl;
		
		addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                toggleCell(e.getX(), e.getY());
            }
        });
	}
	
	/**
	 * Toggle a cell alive or dead
	 * @param mx		Mouse x
	 * @param my		Mouse y
	 */
	public void toggleCell(int mx, int my){
		int midX = canvasWidth/2;
		int midBoard = boardWidth/2;
		int startX = midX-midBoard;
		int c = (mx - startX)/cellDim;
		int r = my/cellDim;
		ctrl.toggleCell(r, c);
		this.repaint();
	}
	
	public void paintComponent(Graphics g){
		int midX = canvasWidth/2;
		int midBoard = boardWidth/2;
		
		g.setColor(this.getBackground());
		g.fillRect(0, 0, canvasWidth, canvasHeight);
		g.setColor(Color.GRAY);
		g.fillRect(midX-midBoard,  0,  boardWidth, boardHeight);
		drawBoard(g, ctrl.getBoard());
		drawGrid(g);
	}
	
	/**
	 * Draw the game board 
	 * @param g
	 * @param board			The 2D int array which is the game board model
	 */
	public void drawBoard(Graphics g, int[][] board){
		for(int i=0; i<row; i++){
			for(int j=0; j<board[i].length; j++){
				int d = board[i][j];
				
				for(int k=0; k<32; k++){
					//System.out.println(d>>(31-k) & 1);
					if(j*32+k<column && (d>>(31-k) & 1) == 1){
						paintCell(g, i, j, k);
					}
				}
			}
		}
	}
	
	/**
	 * Paint a cell green if it is alive
	 * @param g
	 * @param r			Row of the cell of the game board
	 * @param c			Column of the cell of the game board
	 * @param d			The bit position of the cell
	 */
	public void paintCell(Graphics g, int r, int c, int d){
		System.out.println("paintCell " + r + ", " + c);
		int midX = canvasWidth/2;
		int midBoard = boardWidth/2;
		int startX = midX-midBoard;
		
		g.setColor(Color.GREEN);
		g.fillRect(startX+cellDim*(c*32+d),  cellDim*r,  cellDim, cellDim);
	}
	
	/**
	 * Draw grid of the game board
	 * @param g
	 */
	public void drawGrid(Graphics g){
		int midX = canvasWidth/2;
		int midBoard = boardWidth/2;
		int startX = midX-midBoard;
		int endX = midX+midBoard;
		
		g.setColor(Color.WHITE);
		//draw horizontal lines
		for(int i=0; i<row; i++){
			int y = i*cellDim;
			g.drawLine(startX, y, endX, y);
		}
		
		//draw vertical lines
		for(int i=0; i<column; i++){
			int x = i*cellDim+startX;
			g.drawLine(x, 0, x, boardHeight);
		}
	}
	
	/**
	 * Redraw the game board if the user hits the Reset Dimension button
	 * @param row			The new number of rows that the user input
	 * @param column		The new number of columns that the user input
	 */
	public void resetDimension(int row, int column){
		this.row = row;
		this.column = column;
		cellDim = Math.min(canvasWidth/column, canvasHeight/row);
		boardWidth = cellDim*column;
		boardHeight = cellDim*row;
	}
}
