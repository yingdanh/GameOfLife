import java.util.Timer;
import java.util.TimerTask;


public class MainController {
	private MainView view;
	private Board board;
	private Timer timer;
	
	public MainController(){
		view = new MainView(this);
		view.openDbox();
		board = new Board(view.getRows(), view.getColumns());
	}

	/**
	 * toggle a cell to 0 if it is 1
	 * toggle a cell to 1 if it is 0
	 * 
	 * @param r		row of the clicked cell
	 * @param c		column of the clicked cell
	 */
	public void toggleCell(int r, int c){
		this.pauseGame();
		board.setCell(r, c);
	}
	
	/**
	 * 
	 * @return		the game board
	 */
	public int[][] getBoard(){
		return board.getBoard();
	}
	
	/**
	 * Game Animation
	 */
	public void runGame(){
		timer = new Timer();
		timer.schedule(new TimerTask() {
			  @Override
			  public void run() {
				  //create a 3-row buffer with 0 at the front and back
				  int[][] buffer = new int[3][view.getColumns()/32+3];
				  board.nextBoard(buffer);
				  view.repaintCanvas();
			  }
			}, 500, 300);
	}
	
	/**
	 * Pause the game
	 */
	public void pauseGame(){
		if(timer != null)
			timer.cancel();
	}
	
	/**
	 * Clean the board to let users to restart the game
	 */
	public void wipeBoard(){
		pauseGame();
		board.initBoard();
	}
	
	/**
	 * Reset the game board dimension
	 */
	public void resetDimension(){
		pauseGame();
		board = new Board(view.getRows(), view.getColumns());
		view.repaintCanvas();
	}
}
