
public class Board {
	int row;
	int column;
	int[][] board;	//use 32-bit int array as data structure
	
	/**
	 * Constructor
	 * @param row			How many rows the game board has
	 * @param column		How many columns the game board has
	 */
	public Board(int row, int column) {
		this.row = row;
		this.column = column;
		initBoard();
	}
	
	/**
	 * Initiate the game board as a 2D int array
	 * Use a bit to represent a cell
	 */
	public void initBoard(){
		int col = column/32+1;	
		board = new int[row][col];
	}
	
	/**
	 * Set up a buffer to keep track of the original board
	 * @param buffer	A 3-row buffer padded with 0
	 * @param i			The target row of the board
	 */
	private void setBuffer(int[][] buffer, int i){
		int col = column/32+3;
		if(i==0){
			//pad the first row with 0
			for(int c=0; c<col; c++){
				buffer[0][c] = 0;
			}
			//copy 2 rows from the board to the buffer
			for(int j=1, k=i; j<3; j++, k++){
				buffer[j][0] = 0;
				buffer[j][col-1] = 0;
				for(int c=1; c<col-1; c++){
					buffer[j][c] = board[k][c-1];
				}
			}
		}else if(i==row-1){
			// move 2 rows up in the buffer
			for(int k=0; k<2; k++){
				for(int c=0; c<col; c++){
					buffer[k][c] = buffer[k+1][c];
				}
			}
			
			//pad the last row with 0
			for(int c=0; c<col; c++){
				buffer[2][c] = 0;
			}
		}else{
			//for a regular row
			//move 2 rows up from the buffer
			for(int k=0; k<2; k++){
				for(int c=0; c<col; c++){
					buffer[k][c] = buffer[k+1][c];
				}
			}
			
			//copy 1 row from the board to the buffer
			buffer[2][0] = 0;
			buffer[2][col-1] = 0;
			for(int c=1; c<col-1; c++){
				buffer[2][c] = board[i+1][c-1];
			}
		}
	}
	
	/**
	 * Get the board of the next generation based on the rules
	 * @param buffer
	 */
	public void nextBoard(int[][] buffer){
		int col = column/32+1;
		
		for(int i=0; i<row; i++){
			setBuffer(buffer, i);
			
			for(int j=0; j<col; j++){
				for(int k=0; k<32 && j*32+k<column; k++){
					int mid = board[i][j]>>(31-k) & 1;
					int sum = getNeighbors(i, j+1, k, buffer);
					int mask = 1<<(31-k%32);
					
					if(mid == 1){
						if(sum < 2 || sum > 3){
							//dying
							board[i][j] = board[i][j]^mask;
						}
					}else{
						if(sum == 3){
							//become alive
							board[i][j] = board[i][j]^mask;
						}
					}
				}
			}
		}
	}
	
	/**
	 * Calculate the sum of the 8 neighbors
	 * @param r			Row of the board
	 * @param c			Column of the board
	 * @param d			Position of a bit
	 * @param buf		buffer to keep track of the original board
	 * @return			the sum of the 8 neighbors
	 */
	private int getNeighbors(int r, int c, int d, int[][] buf){
		int preColumn = c;
		int nextColumn = c;
		int prePos = d-1;
		int nextPos = d+1;
		if(d==0){
			preColumn = c-1;
			prePos = 31;
		}else if(d==31){
			nextColumn = c+1;
			nextPos = 0;
		}
	
		//upper left neighbor
		int ul    = buf[0][preColumn]>>(31-prePos) & 1;
		//upper middle neighbor
		int um    = buf[0][c]>>(31-d) & 1;
		//upper right neighbor
		int ur    = buf[0][nextColumn]>>(31-nextPos) & 1;
		//lower left neighbor		
		int ll    = buf[2][preColumn]>>(31-prePos) & 1;
		//lower middle neighbor
		int lm    = buf[2][c]>>(31-d) & 1;
		//lower right neighbor
		int lr    = buf[2][nextColumn]>>(31-nextPos) & 1;
		//the left neighbor
		int left  = buf[1][preColumn]>>(31-prePos) & 1;
		//the right neighbor
		int right = buf[1][nextColumn]>>(31-nextPos) & 1;
		
		return (ul + um + ur + ll + lm + lr + left + right);
	}
	
	/**
	 * Set a cell to 0 if it is 1, set it to 1 if it is 0
	 * @param r		The row of the cell on the board
	 * @param c		The column of the cell 
	 */
	public void setCell(int r, int c){
		int col = c/32;
		int mask = 1<<(31-c%32);
		
		board[r][col] = board[r][col]^mask;
	}
	
	/**
	 * @return		The game board
	 */
	public int[][] getBoard(){
		return board;
	}
	
	/**
	 * Print a 2D array
	 * @param arr		2D array
	 */
	public void printArray(int[][] arr){
		for(int i=0; i<arr.length; i++){
			int[] oneRow = arr[i];
			for(int j=0; j<oneRow.length; j++){
				System.out.print(Integer.toBinaryString(oneRow[j]) + ", ");
			}
			System.out.println();
		}
	}
}
