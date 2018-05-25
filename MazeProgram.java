 import java.awt.Graphics;
 import java.awt.Color;
 import java.awt.Font;
 import java.awt.Polygon;
 import javax.swing.*;
 import java.awt.event.*;
 import java.io.*;
 import java.util.Scanner;
 import java.util.ArrayList;
 public class MazeProgram extends JPanel implements KeyListener,MouseListener //weird stuff at the end, visual stuff, finished stuff
 {
 	JFrame frame;
 	//declare an array to store the maze
 	int x=0,y=2;
 	int dx=5,dy=10;
 	String[][] board= new String[20][60];
 	Boolean[][] loc=new Boolean[20][60];
 	int dir=2;
 	String nameDir="Down";
 	int moves=0;
 	ArrayList<Wall> walls;
 	Color myTeal=new Color(0,128,128);
 	Color myTealGray=new Color(47,79,79);
	Color myPurple=new Color(195,0,195);
	Color myDarkGray=new Color(38,38,38);
	Color myMidGray=new Color(50,50,50);
	Color myLightGray=new Color(58,58,58);

 	public MazeProgram()
 	{
 		setBoard();
 		set3D();
 		frame=new JFrame();
 		frame.add(this);
 		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 		frame.setSize(1500,800);
 		frame.setVisible(true);
 		frame.addKeyListener(this);
 		//this.addMouseListener(this);
 	}
 	public void paintComponent(Graphics g)
 	{
 		super.paintComponent(g);
 		g.setColor(Color.BLACK);	//this will set the background color
 		g.fillRect(0,0,1500,800);


 		//drawBoard here!
 		g.setColor(myTeal);
 		//g.drawRect(x,y,800,600);	//x & y would be used to located your
 									//playable character
 									//values would be set below

 		//other commands that might come in handy
 		//g.setFont("Times New Roman",Font.PLAIN,18);
 				//you can also use Font.BOLD, Font.ITALIC, Font.BOLD|Font.Italic
 		//g.fillRect(x,y,100,100);
 		//g.fillOval(x,y,10,10);
 		int r2=10;
 		int c2=10;
 		for (int c=0;c<board[0].length;c++){
			for (int r=0;r<board.length;r++){
				if (board[r][c]!=null){
					if (board[r][c].equals(" ")){
						g.setColor(Color.lightGray);
						g.drawRect(c*c2+725,r*r2+100,10,10);
						g.fillRect(c*c2+725,r*r2+100,10,10);
					}


					else{
						loc[r][c]=false;
						g.setColor(myTeal);
						g.fillRect(c*c2+725,r*r2+100,10,10);
						g.setColor(Color.lightGray);
						g.drawRect(c*c2+725,r*r2+100,10,10);
					}
				}

			}

		}

		g.setColor(myPurple);
		g.drawOval(y*c2+725,x*r2+100,dx,dy);
		g.fillOval(y*c2+725,x*r2+100,dx,dy);
		switch(dir){
			case 0: nameDir="Up";
			break;
			case 1: nameDir="Left";
			break;
			case 2: nameDir="Down";
			break;
			case 3: nameDir="Right";
			break;
		}
		Font fd=new Font("Lucida Console",Font.PLAIN,20);
		g.setFont(fd);
		g.drawString("Direction: "+nameDir,10,20);
		g.drawString("Moves: " +moves/2,10,50);


			for (Wall w: walls){
				g.setColor(w.getColor());
				g.fillPolygon(w.getPoly());
				if (w.getColor()==myDarkGray) g.setColor(myDarkGray);
				else g.setColor(Color.BLACK);
				g.drawPolygon(w.getPoly());
			}

	if (x==board.length-1){
		g.setColor(Color.BLACK);
		int[] c={100,100,700,700};
		int[] r={100,700,700,100};
		g.fillPolygon(c,r,4);
		/*int[] cEndL={100,100,250,250};
		int[] rEndL={100,700,550,250};
		g.fillPolygon(cEndL,rEndL,4);
		int[] cEndR={550,550,700,700};
		int[] rEndR={250,550,700,100};
		g.fillPolygon(cEndR,rEndR,4);
		int[] cEndC={100,700,550,250};
		int[] rEndC={100,100,250,250};
		g.fillPolygon(cEndC,rEndC,4);
		g.setColor(Color.BLACK);
		int[] cEndF={250,250,550,550};
		int[] rEndF={250,550,550,250};
		g.fillPolygon(cEndF,rEndF,4);*/

		g.setColor(myPurple);
		Font f=new Font("Veranda",Font.BOLD,36);
		g.setFont(f);
		g.drawString("You have reached the end!",175,350);
	}

 		g.setColor(myPurple);
 		Font end=new Font("Lucida Console",Font.BOLD,16);
 		g.setFont(end);
 		g.drawString("START",745,90); //NOTE: these numbers will need to change if board is changed
 		g.drawString("END",945,320); //NOTE: these numbers will need to change if board is changed

 	}
 	public void setBoard()
 	{
 		//choose your maze design

 		//pre-fill maze array here

 		File name = new File("MazeProgram.txt");
 		int r=0;
 		try
 		{
 			BufferedReader input = new BufferedReader(new FileReader(name));
 			String text;
 			while( (text=input.readLine())!= null)
 			{
 				//System.out.println(text);

 				for (int c=0;c<text.length();c++){
					board[r][c]=text.substring(c,c+1);
					System.out.print(board[r][c]);
				}
				System.out.println();
				r++;
 				//your code goes in here to chop up the maze design
 				//fill maze array with actual maze stored in text file
 			}
 		}
 		catch (IOException io)
 		{
 			System.err.println("File error");
 		}
 	}

 	public void set3D(){

		walls=new ArrayList<Wall>();
		for (int i=0;i<3;i++){
			switch (dir){
				case 0:	if (x==board.length-1 || x<=1 || y==board[0].length-1 || y<=1) makeLeft(i);
						if (x-i>=0 && y-1>=0 && board[x-i][y-1].equals("#"))
						makeLeft(i);
						else if (x-i>=0 && y-1>=0 && board[x-i][y-1].equals(" ")){
						makeLeftPathTop(i);
						makeLeftPathMid(i);
						makeLeftPathBottom(i);

						}
						if (x==board.length-1 || x<=1 || y==board[0].length-1 || y<=1) makeRight(i);
						if (x-i>=0 && y+1<=board[0].length && board[x-i][y+1].equals("#"))
						makeRight(i);
						else if (x-i>=0 && y+1<=board[0].length && board[x-i][y+1].equals(" ")){
						makeRightPathTop(i);
						makeRightPathMid(i);
						makeRightPathBottom(i);
						}

						makeFloor(i);
						makeCeiling(i);

						makeFrontWall(0);
						if (x-2<=-1 || board[x-2][y].equals("#")) makeFrontWall(1);
					    if (x-1<=-1 || board[x-1][y].equals("#")) makeFrontWall(2);
						if (x-3<0) makeFrontWall(Math.abs(x+1-3)); //have to do stuff with board[x-3][y]=""


				break;

				case 1: if (x==board.length-1 || x<=1 || y==board[0].length-1 || y<=1) makeLeft(i);
						if (y-i>=0 && x+1<=board.length && board[x+1][y-i].equals("#"))
						makeLeft(i);
						else if (y-i>=0 && x+1<=board.length && board[x+1][y-i].equals(" ")){
						makeLeftPathTop(i);
						makeLeftPathMid(i);
						makeLeftPathBottom(i);

						}
						if (x<=1 || x==board.length-1 || y<=1 || y==board[0].length-1 ) makeRight(i);
						if (y-i>=0 && x-1>=0 && board[x-1][y-i].equals("#"))
						makeRight(i);
						else if (y-i>=0 && x-1>=0 && board[x-1][y-i].equals(" ")){
						makeRightPathTop(i);
						makeRightPathMid(i);
						makeRightPathBottom(i);

						}

						makeFloor(i);
						makeCeiling(i);

						makeFrontWall(0);
						if (y-2<=-1 || board[x][y-2].equals("#")) makeFrontWall(1);
						if (y-1<=-1 || board[x][y-1].equals("#")) makeFrontWall(2);
						if (y-3<0) makeFrontWall(Math.abs(y+1-3));



				break;

				case 2: if (x==board.length-1 || x<=1 || y==board[0].length-1 || y<=1) makeLeft(i);
						if (x+i<=board.length-1 && y+1<=board[0].length-1 && board[x+i][y+1].equals("#"))
						makeLeft(i);
						else if (x+i<=board.length-1 && y+1<=board[0].length-1 && board[x+i][y+1].equals(" ")){
						makeLeftPathTop(i);
						makeLeftPathMid(i);
						makeLeftPathBottom(i);

						}
						if (x==board.length-1 || x<=1 || y==board[0].length-1 || y<=1) makeRight(i);
						if (x+i<=board.length-1 && y-1>=0 && board[x+i][y-1].equals("#"))
						makeRight(i);
						else if (x+i<=board.length-1 && y-1>=0 && board[x+i][y-1].equals(" ")){
						makeRightPathTop(i);
						makeRightPathMid(i);
						makeRightPathBottom(i);
						}

						makeFloor(i);
						makeCeiling(i);

						makeFrontWall(0);
						if (x+2>=board.length || board[x+2][y].equals("#")) makeFrontWall(1);
						if (x+1>=board.length || board[x+1][y].equals("#")) makeFrontWall(2);
						if (x+3>board.length-1) makeFrontWall(Math.abs(x+3-board.length));

				break;

				case 3: if (x<=1 || x==board.length-1 || y<=1 || y==board[0].length-1) makeLeft(i);
						if (y+i<=board[0].length-1 && x-1>=0 && board[x-1][y+i].equals("#"))
						makeLeft(i);
						else if (y+i<=board[0].length-1 && x-1>=0 && board[x-1][y+i].equals(" ")){
						makeLeftPathTop(i);
						makeLeftPathMid(i);
						makeLeftPathBottom(i);
						}
						if (x==board.length-1 || x<=1 || y==board[0].length-1 || y<=1) makeRight(i);
						if (y+i<=board[0].length-1 && x+1<=board.length-1 && board[x+1][y+i].equals("#"))
						makeRight(i);
						else if (y+i<=board[0].length-1 && x+1<=board.length-1 && board[x+1][y+i].equals(" ")){
						makeRightPathTop(i);
						makeRightPathMid(i);
						makeRightPathBottom(i);

						}

						makeFloor(i);
						makeCeiling(i);

						makeFrontWall(0);
						if (y+2>=board[0].length || board[x][y+2].equals("#")) makeFrontWall(1);
						if (y+1>=board[0].length || board[x][y+1].equals("#")) makeFrontWall(2);
						if (y+3>board[0].length-1) makeFrontWall(Math.abs(y+3-board[0].length));

				break;

			}


		}
	}

	public void makeLeft(int i){
		int[] r={100+(50*i),700-(50*i),650-(50*i),150+(50*i)};
		int[] c={100+(50*i),100+(50*i),150+(50*i),150+(50*i)};
		walls.add(new Wall(r,c,myLightGray));

	}

	public void makeRight(int i){
		int[] r={100+(50*i),700-(50*i),650-(50*i),150+(50*i)};
		int[] c={700-(50*i),700-(50*i),650-(50*i),650-(50*i)};
		walls.add(new Wall(r,c,myLightGray));

	}

	public void makeFloor(int i){
		int[] r= {650-(50*i),700-(50*i), 700-(50*i), 650-(50*i)};
		int[] c= {150+(50*i), 100+(50*i), 700-(50*i), 650-(50*i)};
		walls.add(new Wall(r,c,myTealGray));

	}

	public void makeCeiling(int i){
		int[] r={100+(50*i), 150+(50*i), 150+(50*i), 100+(50*i)};
		int[] c={100+(50*i), 150+(50*i), 650-(50*i), 700-(50*i)};
		walls.add(new Wall(r,c,myTealGray));

	}
	public void makeLeftPathTop(int i){
		int[] r={100+(50*i), 150+(50*i), 150+(50*i)};
		int[] c={100+(50*i), 100+(50*i), 150+(50*i)};
		walls.add(new Wall(r,c,myTealGray));

	}
	public void makeLeftPathMid(int i){
		int[] r={150+(50*i), 650-(50*i), 650-(50*i), 150+(50*i)};
		int[] c={100+(50*i), 100+(50*i), 150+(50*i), 150+(50*i)};
		walls.add(new Wall(r,c,myMidGray));
	}
	public void makeLeftPathBottom(int i){
		int[] r={650-(50*i), 700-(50*i), 650-(50*i)};
		int[] c={100+(50*i), 100+(50*i), 150+(50*i)};
		walls.add(new Wall(r,c,myTealGray));
	}

	public void makeRightPathTop(int i){
		int[] r= {150+(50*i), 150+(50*i), 100+(50*i)};
		int[] c= {650-(50*i), 700-(50*i), 700-(50*i)};
		walls.add(new Wall(r,c,myTealGray));

	}
	public void makeRightPathMid(int i){
		int[] r= {150+(50*i), 650-(50*i), 650-(50*i), 150+(50*i)};
		int[] c= {650-(50*i), 650-(50*i), 700-(50*i), 700-(50*i)};
		walls.add(new Wall(r,c,myMidGray));

	}

	public void makeRightPathBottom(int i){
		int[] r={650-(50*i), 700-(50*i), 650-(50*i)};
		int[] c={650-(50*i), 700-(50*i), 700-(50*i)};
		walls.add(new Wall(r,c,myTealGray));

	}
	public void makeFrontWall(int i){
		int[] r={250-(50*i), 550+(50*i),550+(50*i),250-(50*i)};
		int[] c={250-(50*i), 250-(50*i), 550+(50*i), 550+(50*i)};
		walls.add(new Wall(r,c,myDarkGray));

	}
 	public void keyPressed(KeyEvent e)
 	{
		System.out.println(dir);
		if (e.getKeyCode()==37){
			if (dx==5) dx=10;
			else if( dx==10) dx=5;
			if (dy==5) dy=10;
			else if (dy==10) dy=5;

			System.out.println("Left");
			dir++;
			if (dir==4) dir=0;
			//y--;
		}
		else if (e.getKeyCode()==39){
			if (dx==5) dx=10;
			else if( dx==10) dx=5;
			if (dy==5) dy=10;
			else if (dy==10) dy=5;

			System.out.println("Right");
			dir--;
			if (dir==-1) dir=3;
			//y++;
		}

		else if (e.getKeyCode()==38){
			System.out.println("Up");
			moves++;
			switch (dir){
				case 0: if(x-1>=0 && board[x-1][y].equals(" "))
								x--;
								moves++;
						break;
				case 1: if (y-1>=0 && board[x][y-1].equals(" "))
								y--;
								moves++;
						break;
				case 2: if (x+1<=board.length-1 && board[x+1][y].equals(" "))
								x++;
								moves++;
						break;
				case 3: if (y+1<=board[0].length-1 && board[x][y+1].equals(" "))
								y++;
								moves++;
						break;

			}
		}

		set3D();
		repaint();

 	}
 	public void keyReleased(KeyEvent e)
 	{
 	}
 	public void keyTyped(KeyEvent e)
 	{
 	}
 	public void mouseClicked(MouseEvent e)
 	{
 	}
 	public void mousePressed(MouseEvent e)
 	{
 	}
 	public void mouseReleased(MouseEvent e)
 	{
 	}
 	public void mouseEntered(MouseEvent e)
 	{
 	}
 	public void mouseExited(MouseEvent e)
 	{
 	}
 	public static void main(String args[])
 	{
 		MazeProgram app=new MazeProgram();
 	}
}