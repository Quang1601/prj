package Minesweeper;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class Minesweeper implements ActionListener {
    JFrame frame;
    JPanel textPanel;
    JPanel buttonPanel;
    JButton[][] buttons;
    boolean [][] flagged;
    JButton reset;
    JButton flag;
    int [][] solution;
    JLabel textfield;
Random random;
    int size;
    int mines;
    ArrayList<Integer> xPos;
    ArrayList<Integer> yPos;
boolean flagging;
int count=0;
int lastxchecked;
int lastychecked;
int xZero;
int yZero;

    public Minesweeper() {
    
        xPos = new ArrayList<>();
        yPos = new ArrayList<>();
        
        
        size = 9;
        mines = 10;
    	lastxchecked=size+1;
    	lastychecked=size+1;
    	flagged= new boolean [size][size];
        random = new Random();
        for (int i = 0; i < mines; i++) {
            xPos.add(random.nextInt(size));
            yPos.add(random.nextInt(size));
        }

        
       for (int i = 0; i < mines; i++) {
            for (int j = i+1 ; j < mines; j++) {
                if (xPos.get(i).equals(xPos.get(j)) && yPos.get(i).equals(yPos.get(j))) {
                    xPos.set(i, random.nextInt(size));
                    yPos.set(j, random.nextInt(size));
                    i=0;
                    j=0;
                }
            }
        }


        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textPanel = new JPanel();
        textPanel.setBackground(Color.black);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(size,size));

        textfield = new JLabel();
        textfield.setHorizontalAlignment(JLabel.CENTER);
        textfield.setFont(new Font("Times New Roman", Font.BOLD, 21));
        textfield.setForeground(Color.CYAN);
        textfield.setText("Minesweeper");
        
        
        
        reset= new JButton();
        reset.setBackground(Color.BLACK);
        reset.setForeground(Color.magenta);
        reset.setText("reset");
        reset.setFont(new Font("Time New Romans",Font.BOLD,20));
        reset.addActionListener(this);
        
        
        flag= new JButton();
        flag.setBackground( Color.WHITE);
        flag.setForeground(new Color(100,0,56));
        flag.setText("l►");
        flag.setFont(new Font("Time New Romans",Font.BOLD,20));
        flag.addActionListener(this);
        
solution= new int [size][size];
        buttons = new JButton[size][size];
        
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[0].length; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setFocusable(false);
                buttons[i][j].setFont(new Font("Times New Roman", Font.BOLD, 11));
                buttons[i][j].addActionListener(this);
                buttons[i][j].setText("");
                buttonPanel.add(buttons[i][j]);
                                                                   
            }
        }

        textPanel.add(textfield);
        frame.add(textPanel, BorderLayout.NORTH);
        frame.add(buttonPanel);
        frame.add(reset, BorderLayout.SOUTH);
        frame.add(flag,BorderLayout.WEST);
         
        frame.setSize(570, 570);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true); 
        getSolution();
    }
public void getSolution() {
	for(int y=0;y<solution.length;y++) {
		for (int x=0; x<solution[0].length;x++) {
			boolean change= false;
			int minesAround=0;
			for(int i=0; i<xPos.size();i++)
			{
				if( x==xPos.get(i)&& y==yPos.get(i)) {
					solution[y][x]=size+1;
					change=true;
				}
			}
			if(!change) {
				for(int i=0;i<xPos.size();i++) {
					if (x-1==xPos.get(i)&&y==yPos.get(i))
						minesAround++;
					if (x+1==xPos.get(i)&&y==yPos.get(i))
						minesAround++;
					if (x==xPos.get(i)&& y-1==yPos.get(i))
						minesAround++;
					if (x==xPos.get(i)&& y+1==yPos.get(i))
						minesAround++;
					if (x+1==xPos.get(i)&&y+1==yPos.get(i))
						minesAround++;
					if (x-1==xPos.get(i)&& y-1==yPos.get(i))
                        minesAround++;
					if (x-1==xPos.get(i)&& y+1==yPos.get(i))
						minesAround++;
					if (x+1==xPos.get(i)&& y-1==yPos.get(i))
						minesAround++;
					
				}
				solution[y][x]= minesAround;
				
			}
		}
	}
	for(int i=0; i<solution.length;i++) {
		for(int j=0;j<solution[0].length;j++) {
		System.out.print(solution[i][j]+" ");
		
		
	}System.out.println();}
}
public void check(int y, int x) {
    boolean over = false;
    if (solution[y][x] == (size + 1)) {
        gameOver(false);
        over = true;
    }
    if (!over) {
        getColor(y, x);

        if (solution[y][x] == 0) {
            xZero = x;
            yZero = y;
            count = 0;
            display();
        }
        else  {
            buttons[y][x].setText(String.valueOf(solution[y][x]));
    		
        }
        gameWinner();
    }
 
}

public void getColor(int y, int x) {
    if (solution[y][x] == (size + 1)) {
        buttons[y][x].setText("💥");
        buttons[y][x].setFont(new Font("Ariel", Font.BOLD, 15));
        buttons[y][x].setForeground(Color.BLACK);
        
        buttons[y][x].setBackground(null); 
    } else {
        if (solution[y][x] == 0) 
            buttons[y][x].setEnabled(false);
        buttons[y][x].setText("");
        if (solution[y][x] == 1) 	
            buttons[y][x].setForeground(Color.pink);
        if (solution[y][x] == 2)
            buttons[y][x].setForeground(Color.blue);
        if (solution[y][x] == 3)
            buttons[y][x].setForeground(new Color(27, 156, 0));
        if (solution[y][x] == 4)
            buttons[y][x].setForeground(Color.YELLOW);
        if (solution[y][x] == 5)
            buttons[y][x].setForeground(new Color(20, 156, 0));
        if (solution[y][x] == 6)
            buttons[y][x].setForeground(new Color(30, 16, 90));
        if (solution[y][x] == 7)
            buttons[y][x].setForeground(new Color(24, 76, 80));
        if (solution[y][x] == 8)
            buttons[y][x].setForeground(new Color(120, 156, 60));
        buttons[y][x].setBackground(null);
        
      buttons[y][x].setText(String.valueOf(solution[y][x]));
        }
    }


public void gameWinner() {
	int buttonsleft=0;
	for (int i =0; i<buttons.length;i++)
	{
		for (int j =0; j<buttons[0].length;j++)
		{
			if ( buttons[i][j].getText()==""|| buttons[i][j].getText()=="1►") 
				buttonsleft++;
			}
		
		}
		if(buttonsleft== mines) 
			gameOver(true);
		}
	
public void gameOver(boolean won) {
    if (!won) {
        textfield.setForeground(Color.RED);
        textfield.setText("Lose.Game Over‼");
        JOptionPane.showMessageDialog(frame, "BOOM!");
    } else {
        textfield.setForeground(Color.GREEN);
        textfield.setText("Winner");
        JOptionPane.showMessageDialog(frame, "Congratulation");
    }

    for (int i = 0; i < buttons.length; i++) {
        for (int j = 0; j < buttons[0].length; j++) {
            buttons[i][j].setEnabled(false);

            for (int count = 0; count < xPos.size(); count++) {
                if (j == xPos.get(count) && i == yPos.get(count)) {
                    buttons[i][j].setText("💥");
                    buttons[i][j].setFont(new Font("Ariel", Font.BOLD, 15));
                    buttons[i][j].setForeground(Color.BLACK);
                    buttons[i][j].setBackground(Color.RED); 
                }
            }
        }
    }
}

    @Override
    public void actionPerformed(ActionEvent e) {
    	if(e.getSource()==flag)
    	{
    		if(flagging )
    		{
    			flag.setBackground(Color.WHITE);
    			flagging=false;
    		}
    		else
    		{
    			flag.setBackground(Color.green);
    			flagging=true;
    		}
    	}
    	if(e.getSource()==reset) {
    	frame.dispose();
    	new Minesweeper();
    	}
        // TODO: Implement the logic for button clicks
    	
    	for (int i =0;i<buttons.length;i++) {
    		for(int j=0;j<buttons[0].length;j++) {
    			if(e.getSource()==buttons[i][j])
    				if(flagging&&(buttons[i][j].getText()==""||buttons[i][j].getText()=="1►")) {
    					
    				
					if(flagged[i][j]) {
						buttons[i][j].setText("");
						buttons[i][j].setBackground(null);
						flagged[i][j]=false;
					}
					else {
						buttons[i][j].setText("1►");
						buttons[i][j].setBackground(Color.green);
						buttons[i][j].setForeground(Color.black);
						flagged[i][j]=true;			
					}
					
				}
    				else
    				{
    					if(!flagged[i][j]) {
    						check(i,j);
    					}
    				}
    			
    			
    		}
    		
    	}}
        public void display() {
        	
        if (count < 1) {
        	
            if ((xZero - 1) >= 0)
                getColor(yZero, xZero -1);
           if ((xZero + 1) < size)
               getColor(yZero, xZero + 1);
           if ((yZero - 1) >= 0)
                getColor(yZero - 1, xZero);
            if ((yZero + 1) < size)
                getColor(yZero + 1, xZero);
            if ((yZero - 1) >= 0 && (xZero - 1) >= 0)
                getColor(yZero - 1, xZero - 1);
            if ((yZero + 1) < size && (xZero + 1) < size)
                getColor(yZero + 1, xZero + 1);
            if ((yZero - 1) >= 0 && (xZero + 1) < size)
                getColor(yZero - 1, xZero + 1);
            if ((yZero + 1) < size && (xZero - 1) >= 0)
                getColor(yZero + 1, xZero - 1);

            count++;
            display();
        } else {
            for (int y = 0; y < buttons.length; y++) {
                for (int x = 0; x < buttons[0].length; x++) {
                    if (y >= 0 && y < buttons.length && x >= 0 && x < buttons[0].length) { 
                        if (buttons[y][x].getText().equals("0")) {
                        	
                            if (y - 1 >= 0) {
                                if (buttons[y - 1][x].getText().equals("") || buttons[y - 1][x].getText().equals("1►")) {
                                    lastxchecked = x;
                                    lastychecked = y;
                                }
                            }

                            if (y + 1 < size) {
                                if (buttons[y + 1][x].getText().equals("") || buttons[y + 1][x].getText().equals("1►")) {
                                    lastxchecked = x;
                                    lastychecked = y;
                                }
                            }

                            if (x + 1 < size) {
                                if (buttons[y][x + 1].getText().equals("") || buttons[y][x + 1].getText().equals("1►")) {
                                    lastxchecked = x;
                                    lastychecked = y;
                                }
                            }

                            if (x - 1 >= 0) {
                                if (buttons[y][x - 1].getText().equals("") || buttons[y][x - 1].getText().equals("1►")) {
                                    lastxchecked = x;
                                    lastychecked = y;
                                }
                            }

                            if (y - 1 >= 0 && x - 1 >= 0) {
                                if (buttons[y - 1][x - 1].getText().equals("") || buttons[y - 1][x - 1].getText().equals("1►")) {
                                    lastxchecked = x;
                                    lastychecked = y;
                                }
                            }

                            if (y + 1 < size && x + 1 < size) {
                                if (buttons[y + 1][x + 1].getText().equals("") || buttons[y + 1][x + 1].getText().equals("1►")) {
                                    lastxchecked = x;
                                    lastychecked = y;
                                }
                            }

                            if (y - 1 >= 0 && x + 1 < size) {
                                if (buttons[y - 1][x + 1].getText().equals("") || buttons[y - 1][x + 1].getText().equals("1►")) {
                                    lastxchecked = x;
                                    lastychecked = y;
                                }
                            }

                            if (y + 1 < size && x - 1 >= 0) {
                                if (buttons[y + 1][x - 1].getText().equals("") || buttons[y + 1][x - 1].getText().equals("1►")) {
                                    lastxchecked = x;
                                    lastychecked = y;
                                }
                            }
                           
                           
                        }
                     

                        }
                   
                        
                    }
                }
            }

            if (lastxchecked < size && lastychecked < size) {
                xZero = lastxchecked;
                yZero = lastychecked;
                count = 0;
               lastxchecked = size + 1;  
               lastychecked = size + 1; 
                display();
            }
            
        }
    }



    		
        							
        						
        			

