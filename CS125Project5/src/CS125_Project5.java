import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/* CS 125 - Intro to Computer Science II
 * File Name: CS125_Project5.java
 * Project 5 - Due X/XX/XXXX
 * Instructor: Dr. Dan Grissom
 * 
 * Name: FirstName LastName
 * Description: Insert your meaningful description for Project 5.
 */


import java.util.ArrayList;
import java.util.Scanner;
import java.util.*;
public class CS125_Project5
{
	private final static Scanner scnr = new Scanner(System.in);
	static private int count1 = 0;
	public static void main(String[] args)
	{
		// Your program should always output your name and the project number.
				// DO NOT DELETE OR COMMENT OUT. Replace with relevant info.
				System.out.println("Cole Gunter");
				System.out.println("Project 5");
				System.out.println("");
				
		// stores player guesses
		ArrayList<Integer> guess = new ArrayList<Integer>();
		// stores row values
		ArrayList<Integer> rowVal = new ArrayList<Integer>();
		// stores column values
		ArrayList<Integer> columnVal= new ArrayList<Integer>();
		int count = 0;
		
		// game board
		String gBoard[][] ={ {"r/c","0","1","2","3","4","5","6"}, 
				{"0","-","-","-","-","-","-","-",},
				{"1","-","-","-","-","-","-","-",},
				{"2","-","-","-","-","-","-","-",},
				{"3","-","-","-","-","-","-","-",},
				{"4","-","-","-","-","-","-","-",},
				{"5","-","-","-","-","-","-","-",},
				{"6","-","-","-","-","-","-","-",}};
		
		// updated board
		String uBoard[][] ={ {" ","0","1","2","3","4","5","6"},
				{"0","-","-","-","-","-","-","-",},
				{"1","-","-","-","-","-","-","-",},
				{"2","-","-","-","-","-","-","-",},
				{"3","-","-","-","-","-","-","-",},
				{"4","-","-","-","-","-","-","-",},
				{"5","-","-","-","-","-","-","-",},
				{"6","-","-","-","-","-","-","-",}};

		// Variables
		int hitCount = 0;
		char direction;
		int column;
		int row;

		// intro and prompt user for row/column to place ships
		System.out.println("Welcome to a friendly game of BATTLESHIP!");
		System.out.print("Please enter a ROW number (0-6): ");
		row = scnr.nextInt();
		System.out.print("Please enter a COL number (0-6): ");
		column = scnr.nextInt();
		System.out.print("Please enter d(down) or r(right): ");
		direction = scnr.next().charAt(0);


		while (printBoard(gBoard,row+1,column+1,direction) == false) {

			// re-prompt user
			System.out.print("Please enter a ROW number (0-6): ");
			row = scnr.nextInt();
			System.out.print("Please enter a COL number (0-6): ");
			column = scnr.nextInt();
			System.out.print("Please enter d(down) or r(right): ");
			direction = scnr.next().charAt(0);	
			printBoard(gBoard,row+1,column+1,direction);

		}
		// variables
		int row1;
		int col1;

		while(count1 < 9) {
			
			// prompt user to attack
			System.out.print("Please enter a ROW number (0-6): ");
			row1 = scnr.nextInt();
			System.out.print("Please enter a COL number (0-6): ");
			col1 = scnr.nextInt();
			checkBoard(gBoard,uBoard,row1+1,col1+1);

			// check for miss
			if (uBoard[row1+1][col1+1] == "X") {
				hitCount++;
				count++;
				count1++;
				System.out.println("HIT!");
				guess.add(count); 
				rowVal.add(row1); 
				columnVal.add(col1); 
			}
			// check for miss
			else if(uBoard[row1+1][col1+1] == "O") { 
				count++;
				System.out.println("MISS :(");
				guess.add(count); 
				rowVal.add(row1); 
				columnVal.add(col1); 
			}

		}
		System.out.println("Guess | Row Col ");
		for(int i = 0; i < guess.size(); i++) { 
			System.out.print(guess.get(i)+ "     | ");
			System.out.print(rowVal.get(i)+ "    ");
			System.out.print(columnVal.get(i)+ " ");
			System.out.println(" ");
		}
		System.out.println("Game is over, thanks for playing!");
	}




	static boolean printBoard(String[][] board, int row, int col, char dir)
	{
		
		int countS = 0;
		int cc = 0;
		
		for (int rowCheck = 0; rowCheck < board.length; rowCheck++) {
			
			for (int columnCheck = 0; columnCheck <board[rowCheck].length; columnCheck++) {
				
				if(board[rowCheck][columnCheck]=="S") {
					countS++;
				
					if(countS == 2) {
						cc = 2;
					} else if(countS == 6) {
						cc = 3;
					}
				}
			}
		}


		for (int numRow = 0; numRow < board.length; numRow++) {
			for (int numCol = 0; numCol <board[numRow].length; numCol++) {
				if(row==numRow && col==numCol && dir=='d' && countS<9) {
					board[numRow][numCol]="S";
					countS++;
					for(int i=row;i<board[col].length;i++) {
						if(countS<=2 ) {
							board[i][col]="S";
							countS++;
						}
						if(countS<=5 && cc==2) {
							board[i][col]="S";
							countS++;
						}
						if(countS<=10 && cc==3) {
							board[i][col]="S";
							countS++;
						}
					}
				}
				else if(row == numRow && col == numCol && dir == 'r' && countS < 9) {
					board[numRow][numCol]="S";
					countS++;
					for(int rr=col;rr<board.length;rr++) {
						if(countS<=2) {
							board[row][rr]="S";
							countS++;
						}
						else if(countS<=5 && cc==2) {
							board[row][rr]="S";
							countS++;
						}
						else if(countS<=10 && cc==3) {
							board[row][rr]="S";
							countS++;
						}
					}
				}
				System.out.print(board[numRow][numCol]+ " ");
			}
			System.out.println(" ");
		}	
		if(countS==11) {
			return true;
		}
		else {
			return false;
		}

	}
	static void checkBoard(String[][] oBoard, String[][] board, int Row, int Col)
	{
		for (int d = 0; d < oBoard.length; d++) {
			for (int r = 0; r <oBoard[d].length; r++) {
				if(oBoard[Row][Col]=="S") {
					board[Row][Col]="X";
				}
				else if(oBoard[Row][Col]=="-") {
					board[Row][Col]="O";
				}
				System.out.print(board[d][r]+ " ");
			}
			System.out.println(" ");
		}	
	}

}



/******************************************************************************
Insert 2 test cases, which represent program input/output for two different
combinations of inputs. You may literally copy and paste your console contents,
but your two test cases should be DIFFERENT from any test cases given in the
Project description itself.

------------
Test Case 1:
------------
Cole Gunter
Project 5

Welcome to a friendly game of BATTLESHIP!
Please enter a ROW number (0-6): 0
Please enter a COL number (0-6): 0
Please enter d(down) or r(right): r
r/c 0 1 2 3 4 5 6  
0 S S - - - - -  
1 - - - - - - -  
2 - - - - - - -  
3 - - - - - - -  
4 - - - - - - -  
5 - - - - - - -  
6 - - - - - - -  
Please enter a ROW number (0-6): 0
Please enter a COL number (0-6): 3
Please enter d(down) or r(right): d
r/c 0 1 2 3 4 5 6  
0 S S - S - - -  
1 - - - S - - -  
2 - - - S - - -  
3 - - - - - - -  
4 - - - - - - -  
5 - - - - - - -  
6 - - - - - - -  
r/c 0 1 2 3 4 5 6  
0 S S - S - - -  
1 - - - S - - -  
2 - - - S - - -  
3 - - - - - - -  
4 - - - - - - -  
5 - - - - - - -  
6 - - - - - - -  
Please enter a ROW number (0-6): 4
Please enter a COL number (0-6): 0
Please enter d(down) or r(right): r
r/c 0 1 2 3 4 5 6  
0 S S - S - - -  
1 - - - S - - -  
2 - - - S - - -  
3 - - - - - - -  
4 S - - - - - -  
5 - - - - - - -  
6 - - - - - - -  
r/c 0 1 2 3 4 5 6  
0 S S - S - - -  
1 - - - S - - -  
2 - - - S - - -  
3 - - - - - - -  
4 S S S S - - -  
5 - - - - - - -  
6 - - - - - - -  
Please enter a ROW number (0-6): 0
Please enter a COL number (0-6): 0
  0 1 2 3 4 5 6  
0 X - - - - - -  
1 - - - - - - -  
2 - - - - - - -  
3 - - - - - - -  
4 - - - - - - -  
5 - - - - - - -  
6 - - - - - - -  
HIT!
Please enter a ROW number (0-6): 0
Please enter a COL number (0-6): 1
  0 1 2 3 4 5 6  
0 X X - - - - -  
1 - - - - - - -  
2 - - - - - - -  
3 - - - - - - -  
4 - - - - - - -  
5 - - - - - - -  
6 - - - - - - -  
HIT!
Please enter a ROW number (0-6): 0
Please enter a COL number (0-6): 4
  0 1 2 3 4 5 6  
0 X X - - O - -  
1 - - - - - - -  
2 - - - - - - -  
3 - - - - - - -  
4 - - - - - - -  
5 - - - - - - -  
6 - - - - - - -  
MISS :(
Please enter a ROW number (0-6): 0
Please enter a COL number (0-6): 2
  0 1 2 3 4 5 6  
0 X X O - O - -  
1 - - - - - - -  
2 - - - - - - -  
3 - - - - - - -  
4 - - - - - - -  
5 - - - - - - -  
6 - - - - - - -  
MISS :(
Please enter a ROW number (0-6): 0
Please enter a COL number (0-6): 3
  0 1 2 3 4 5 6  
0 X X O X O - -  
1 - - - - - - -  
2 - - - - - - -  
3 - - - - - - -  
4 - - - - - - -  
5 - - - - - - -  
6 - - - - - - -  
HIT!
Please enter a ROW number (0-6): 1
Please enter a COL number (0-6): 3
  0 1 2 3 4 5 6  
0 X X O X O - -  
1 - - - X - - -  
2 - - - - - - -  
3 - - - - - - -  
4 - - - - - - -  
5 - - - - - - -  
6 - - - - - - -  
HIT!
Please enter a ROW number (0-6): 2
Please enter a COL number (0-6): 3
  0 1 2 3 4 5 6  
0 X X O X O - -  
1 - - - X - - -  
2 - - - X - - -  
3 - - - - - - -  
4 - - - - - - -  
5 - - - - - - -  
6 - - - - - - -  
HIT!
Please enter a ROW number (0-6): 4
Please enter a COL number (0-6): 0
  0 1 2 3 4 5 6  
0 X X O X O - -  
1 - - - X - - -  
2 - - - X - - -  
3 - - - - - - -  
4 X - - - - - -  
5 - - - - - - -  
6 - - - - - - -  
HIT!
Please enter a ROW number (0-6): 4
Please enter a COL number (0-6): 1
  0 1 2 3 4 5 6  
0 X X O X O - -  
1 - - - X - - -  
2 - - - X - - -  
3 - - - - - - -  
4 X X - - - - -  
5 - - - - - - -  
6 - - - - - - -  
HIT!
Please enter a ROW number (0-6): 2
Please enter a COL number (0-6): 0
  0 1 2 3 4 5 6  
0 X X O X O - -  
1 - - - X - - -  
2 O - - X - - -  
3 - - - - - - -  
4 X X - - - - -  
5 - - - - - - -  
6 - - - - - - -  
MISS :(
Please enter a ROW number (0-6): 4
Please enter a COL number (0-6): 2
  0 1 2 3 4 5 6  
0 X X O X O - -  
1 - - - X - - -  
2 O - - X - - -  
3 - - - - - - -  
4 X X X - - - -  
5 - - - - - - -  
6 - - - - - - -  
HIT!
Please enter a ROW number (0-6): 4
Please enter a COL number (0-6): 3
  0 1 2 3 4 5 6  
0 X X O X O - -  
1 - - - X - - -  
2 O - - X - - -  
3 - - - - - - -  
4 X X X X - - -  
5 - - - - - - -  
6 - - - - - - -  
HIT!
Guess | Row Col 
1     | 0    0  
2     | 0    1  
3     | 0    4  
4     | 0    2  
5     | 0    3  
6     | 1    3  
7     | 2    3  
8     | 4    0  
9     | 4    1  
10     | 2    0  
11     | 4    2  
12     | 4    3  
Game is over, thanks for playing!

------------
Test Case 2:
------------
Cole Gunter
Project 5

Welcome to a friendly game of BATTLESHIP!
Please enter a ROW number (0-6): 3
Please enter a COL number (0-6): 0
Please enter d(down) or r(right): d
r/c 0 1 2 3 4 5 6  
0 - - - - - - -  
1 - - - - - - -  
2 - - - - - - -  
3 S - - - - - -  
4 S - - - - - -  
5 - - - - - - -  
6 - - - - - - -  
Please enter a ROW number (0-6): 0
Please enter a COL number (0-6): 5
Please enter d(down) or r(right): d
r/c 0 1 2 3 4 5 6  
0 - - - - - S -  
1 - - - - - S -  
2 - - - - - S -  
3 S - - - - - -  
4 S - - - - - -  
5 - - - - - - -  
6 - - - - - - -  
r/c 0 1 2 3 4 5 6  
0 - - - - - S -  
1 - - - - - S -  
2 - - - - - S -  
3 S - - - - - -  
4 S - - - - - -  
5 - - - - - - -  
6 - - - - - - -  
Please enter a ROW number (0-6): 6
Please enter a COL number (0-6): 1
Please enter d(down) or r(right): r
r/c 0 1 2 3 4 5 6  
0 - - - - - S -  
1 - - - - - S -  
2 - - - - - S -  
3 S - - - - - -  
4 S - - - - - -  
5 - - - - - - -  
6 - S - - - - -  
r/c 0 1 2 3 4 5 6  
0 - - - - - S -  
1 - - - - - S -  
2 - - - - - S -  
3 S - - - - - -  
4 S - - - - - -  
5 - - - - - - -  
6 - S S S S - -  
Please enter a ROW number (0-6): 0
Please enter a COL number (0-6): 0
  0 1 2 3 4 5 6  
0 O - - - - - -  
1 - - - - - - -  
2 - - - - - - -  
3 - - - - - - -  
4 - - - - - - -  
5 - - - - - - -  
6 - - - - - - -  
MISS :(
Please enter a ROW number (0-6): 0
Please enter a COL number (0-6): 4
  0 1 2 3 4 5 6  
0 O - - - O - -  
1 - - - - - - -  
2 - - - - - - -  
3 - - - - - - -  
4 - - - - - - -  
5 - - - - - - -  
6 - - - - - - -  
MISS :(
Please enter a ROW number (0-6): 0
Please enter a COL number (0-6): 5
  0 1 2 3 4 5 6  
0 O - - - O X -  
1 - - - - - - -  
2 - - - - - - -  
3 - - - - - - -  
4 - - - - - - -  
5 - - - - - - -  
6 - - - - - - -  
HIT!
Please enter a ROW number (0-6): 1
Please enter a COL number (0-6): 5
  0 1 2 3 4 5 6  
0 O - - - O X -  
1 - - - - - X -  
2 - - - - - - -  
3 - - - - - - -  
4 - - - - - - -  
5 - - - - - - -  
6 - - - - - - -  
HIT!
Please enter a ROW number (0-6): 2
Please enter a COL number (0-6): 5
  0 1 2 3 4 5 6  
0 O - - - O X -  
1 - - - - - X -  
2 - - - - - X -  
3 - - - - - - -  
4 - - - - - - -  
5 - - - - - - -  
6 - - - - - - -  
HIT!
Please enter a ROW number (0-6): 3
Please enter a COL number (0-6): 0
  0 1 2 3 4 5 6  
0 O - - - O X -  
1 - - - - - X -  
2 - - - - - X -  
3 X - - - - - -  
4 - - - - - - -  
5 - - - - - - -  
6 - - - - - - -  
HIT!
Please enter a ROW number (0-6): 4
Please enter a COL number (0-6): 0
  0 1 2 3 4 5 6  
0 O - - - O X -  
1 - - - - - X -  
2 - - - - - X -  
3 X - - - - - -  
4 X - - - - - -  
5 - - - - - - -  
6 - - - - - - -  
HIT!
Please enter a ROW number (0-6): 6
Please enter a COL number (0-6): 1
  0 1 2 3 4 5 6  
0 O - - - O X -  
1 - - - - - X -  
2 - - - - - X -  
3 X - - - - - -  
4 X - - - - - -  
5 - - - - - - -  
6 - X - - - - -  
HIT!
Please enter a ROW number (0-6): 6
Please enter a COL number (0-6): 2
  0 1 2 3 4 5 6  
0 O - - - O X -  
1 - - - - - X -  
2 - - - - - X -  
3 X - - - - - -  
4 X - - - - - -  
5 - - - - - - -  
6 - X X - - - -  
HIT!
Please enter a ROW number (0-6): 6
Please enter a COL number (0-6): 3
  0 1 2 3 4 5 6  
0 O - - - O X -  
1 - - - - - X -  
2 - - - - - X -  
3 X - - - - - -  
4 X - - - - - -  
5 - - - - - - -  
6 - X X X - - -  
HIT!
Please enter a ROW number (0-6): 6
Please enter a COL number (0-6): 4
  0 1 2 3 4 5 6  
0 O - - - O X -  
1 - - - - - X -  
2 - - - - - X -  
3 X - - - - - -  
4 X - - - - - -  
5 - - - - - - -  
6 - X X X X - -  
HIT!
Guess | Row Col 
1     | 0    0  
2     | 0    4  
3     | 0    5  
4     | 1    5  
5     | 2    5  
6     | 3    0  
7     | 4    0  
8     | 6    1  
9     | 6    2  
10     | 6    3  
11     | 6    4  
Game is over, thanks for playing!
******************************************************************************/