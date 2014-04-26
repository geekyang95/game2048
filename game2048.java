package game2048;
import java.util.Random;
import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
public class game2048 {
    public static void main(String[] args){
    		GUI window=new GUI();
    		window.add(window);
    }
}
class GUI extends JFrame implements KeyListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public GUI(){								//constructor;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 200, 450, 300);
		HighScore=0;
		dialog=new JDialog(this,false);
		message=new JLabel("",JLabel.CENTER);
		button=new JButton("I've known");	
		lab=new JButton[5][4];
		Container container=getContentPane();
		container.setLayout(new GridLayout(5,4));
		for(int t1=0;t1<5;t1++)
			for(int t2=0;t2<4;t2++){
				lab[t1][t2]=new JButton();
				container.add(lab[t1][t2]);
			}
		setButton();
		Container dia=dialog.getContentPane();
		dia.setLayout(new BorderLayout());
		dia.add(message,BorderLayout.CENTER);
		dia.add(button,BorderLayout.SOUTH);
		running();
	}
	public void add(GUI w){							//add listener;
		lab[4][0].addKeyListener(w);
	}
	public void visibleDialog(){						//make the dialog visible;
		dialog.setBounds(200, 200, 500, 500);
		message.setFont(new Font("Default",Font.PLAIN,30));
		dialog.setVisible(true);
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent act){
				dialog.dispose();
				}
			});
	}
	public void keyTyped(KeyEvent k){
		action(k.getKeyChar());
		setWordSize(lab);
	}
	public void keyReleased(KeyEvent k){
		
	}
	public void keyPressed(KeyEvent k){
	
	}
	public void shows(){							//show the window;
		event=new Event();
		event.start();
		setText(lab);
	}
	public void setButton(){						//set text for buttons;
		for(int t1=0;t1<4;t1++){
			if(t1==0) lab[4][t1].setText("Start/continue");
			else if(t1==1) lab[4][t1].setText("Again");
			else if(t1==2) lab[4][t1].setText("High Score");
			else lab[4][t1].setText("Exit");
		}
	}
	public void running(){						//run the game;
		shows();
		setVisible(true);
		lab[4][1].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent act){
				if(HighScore<event.findMax()) HighScore=event.findMax();
				running();
				}
			});
		lab[4][0].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent act){
				}
			});
		lab[4][3].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent act){
				System.exit(0);
				}
			});
		lab[4][2].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent act){
				message.setText("Max Score :"+Integer.toString(HighScore));
				message.setBackground(Color.green);
				visibleDialog();
				}
			});
	}
	public void setText(JButton [][] label){				//set the text of label;
		for(int t1=0;t1<4;t1++)
			for(int t2=0;t2<4;t2++){
				if(event.getValue(t1, t2)==0) label[t1][t2].setText(null);
				else label[t1][t2].setText(Integer.toString(event.getValue(t1, t2)));
				if(label[t1][t2].getText()==null) label[t1][t2].setBackground(Color.white);
				else if(event.getValue(t1,t2)<=4) label[t1][t2].setBackground(Color.lightGray);
				else if(event.getValue(t1,t2)<=16) label[t1][t2].setBackground(Color.orange);
				else if(event.getValue(t1,t2)<=64) label[t1][t2].setBackground(Color.red);
				else if(event.getValue(t1,t2)<=1024) label[t1][t2].setBackground(Color.yellow);
				else label[t1][t2].setBackground(Color.black);
			}
	}
	public void setWordSize(JButton [][] label){					//auto set the word size;
		for(int t1=0;t1<5;t1++)
			for(int t2=0;t2<4;t2++){
				label[t1][t2].setFont(new Font("Default",Font.PLAIN,label[t1][t2].getHeight()/2));
				if(t1==4) label[t1][t2].setFont(new Font("Default",Font.PLAIN,label[t1][t2].getHeight()/4));
			}
	}
	public void action(char act){							//deal with the action;
		switch(act){
		case 'w':
			if(event.check_Longitudinal()) event.up();
			break;
		case 's':
			if(event.check_Longitudinal()) event.down();
			break;
		case 'a':
			if(event.check_Lateral()) event.left();
			break;
		case 'd':
			if(event.check_Lateral()) event.right();
			break;
		default:
			;
			break;
		}
		setText(lab);
		setVisible(true);
		if(check(message,lab)==true){
			if(HighScore<event.findMax()) HighScore=event.findMax();
			message.setBackground(Color.black);
			visibleDialog();
		}
	}
	public boolean check(JLabel message,JButton [][] label){			//game check;
			if((event.check_Lateral()==false)&&(event.check_Longitudinal()==false)){
				message.setText("Game over!Try again!");
				message.setBackground(Color.black);
				return true;
			}
			if(event.findMax()==2048){
				message.setText("Congratulations!You've succeed!");
				message.setBackground(Color.red);
				return true;
			}
			return false;
	}
	private Event event;
	private JButton button;
	private JButton [][] lab;
	private JDialog dialog;
	private JLabel message;
	private int HighScore;
}
class init{							//the data of the game;
	public init(){						//constructor;
		value=new int[4][4];
		for(int t1=0;t1<4;t1++)
			for(int t2=0;t2<4;t2++)
				value[t1][t2]=0;
		max=0;
	}
	public void setValue(int x,int y,int num){		//set value to the position (x,y);
		value[x][y]=num;
		setMax(num);
	}
	public int getValue(int x,int y){			//get the value of position (x,y);
		return value[x][y];
	}
	public void setMax(int x){				//write the max number;
		if(x>max) max=x;
	}
	public int findMax(){					//get the value of max number;
		return max;
	}
	private int [][] value;			
	private int max;
}
class position{							//a type to store the position imformation of type init;
	public position(){					//constructor;
		po_x=0;
		po_y=0;
	}
	public void setValue(int x,int y){			//store the position information;
		po_x=x;
		po_y=y;
	}
	public int getPo_x(){					//get the value of position x;
		return po_x;	
	}
	public int getPo_y(){					//get the value of position y;
		return po_y;
	}
	private int po_x,po_y;
}
class Event{							//create a type to deal with the action of user;
	public Event(){						//constructor;
	ex=new init();
	ra=new rand();
	pos=new position[16];
	for(posEmpty=0;posEmpty<16;posEmpty++)
		pos[posEmpty]=new position();
	}	
	public void start(){					//the beginning of the game;
		fill();
		fill();
	}
	public void up(){					//the action of up;
		move_up();
		for(lon=0;lon<4;lon++)
			for(lat=0;lat<3;lat++){
				if(ex.getValue(lat,lon)==ex.getValue(lat+1,lon)){
					ex.setValue(lat,lon,ex.getValue(lat,lon)*2);
					ex.setValue(lat+1, lon, 0);
					lat++;
				}
			}
		move_up();
		fill();
	}
	public void down(){					//the action of down;
		move_down();
		for(lon=0;lon<4;lon++)
			for(lat=3;lat>0;lat--){
				if(ex.getValue(lat,lon)==ex.getValue(lat-1,lon)){
					ex.setValue(lat,lon,ex.getValue(lat,lon)*2);
					ex.setValue(lat-1, lon, 0);
					lat--;
				}
			}
		move_down();
		fill();
	}
	public void left(){					//the action of left;
		move_left();
		for(lat=0;lat<4;lat++)
			for(lon=0;lon<3;lon++){
				if(ex.getValue(lat,lon)==ex.getValue(lat,lon+1)){
					ex.setValue(lat,lon,ex.getValue(lat,lon)*2);
					ex.setValue(lat, lon+1, 0);
					lon++;
				}
			}
		move_left();
		fill();
	}
	public void right(){					//the action of right;
		move_right();
		for(lat=0;lat<4;lat++)
			for(lon=3;lon>0;lon--){
				if(ex.getValue(lat,lon)==ex.getValue(lat,lon-1)){
					ex.setValue(lat,lon,ex.getValue(lat,lon)*2);
					ex.setValue(lat, lon-1, 0);
					lon--;
				}
			}
		move_right();
		fill();
	}
	public void move_up(){					//move the number up;
		for(lon=0;lon<4;lon++)
			for(lat=0;lat<3;lat++){
				if(ex.getValue(lat,lon)==0)
					for(third=lat+1;third<4;third++){
						if(ex.getValue(third,lon)!=0){
							ex.setValue(lat, lon, ex.getValue(third, lon));
							ex.setValue(third,lon,0);
							break;
						}
					}
			}
	}
	public void move_down(){				//move the number down;
		for(lon=0;lon<4;lon++)
			for(lat=3;lat>0;lat--){
				if(ex.getValue(lat,lon)==0)
					for(third=lat-1;third>=0;third--){
						if(ex.getValue(third,lon)!=0){
							ex.setValue(lat, lon, ex.getValue(third, lon));
							ex.setValue(third,lon,0);
							break;
						}
					}
			}
	}
	public void move_left(){				//move the number left;
		for(lat=0;lat<4;lat++)
			for(lon=0;lon<3;lon++){
				if(ex.getValue(lat,lon)==0)
					for(third=lon+1;third<4;third++){
						if(ex.getValue(lat,third)!=0){
							ex.setValue(lat, lon, ex.getValue(lat,third));
							ex.setValue(lat,third,0);
							break;
						}
					}
			}
	}
	public void move_right(){				//move the number right;
		for(lat=0;lat<4;lat++)
			for(lon=3;lon>0;lon--){
				if(ex.getValue(lat,lon)==0)
					for(third=lon-1;third>=0;third--){
						if(ex.getValue(lat,third)!=0){
							ex.setValue(lat, lon, ex.getValue(lat,third));
							ex.setValue(lat,third,0);
							break;
						}
					}
			}
	}
	public boolean check_Longitudinal(){			//to check if it is possible to move longitudinally ;；
		for(lon=0;lon<4;lon++)
			for(lat=0;lat<3;lat++){
				if(ex.getValue(lat, lon)==ex.getValue(lat+1,lon ))
					return true;
				else if(ex.getValue(lat,lon)!=0&&ex.getValue(lat+1,lon)==0)
					return true;
				else if(ex.getValue(lat,lon)==0&&ex.getValue(lat+1,lon)!=0)
					return true;
			}
		return false;
	}
	public boolean check_Lateral(){				//check if it is possible to move laterally;
		for(lat=0;lat<4;lat++)
			for(lon=0;lon<3;lon++){
				if(ex.getValue(lat, lon)==ex.getValue(lat, lon+1))
					return true;
				else if(ex.getValue(lat,lon)!=0&&ex.getValue(lat,lon+1)==0)
					return true;
				else if(ex.getValue(lat,lon)==0&&ex.getValue(lat,lon+1)!=0)
					return true;
			}
		return false;
	}
	public int findMax(){					//get the max number of the game;
		return ex.findMax();
	}
	public void fill(){					//give a random number to a random position;
		setPos();
		ra.next(posEmpty);
		ex.setValue(pos[ra.getPos()].getPo_x(),pos[ra.getPos()].getPo_y(),ra.getValue());
	}
	public void setPos(){					//find that witch position is empty;
		posEmpty=0;
		for(lat=0;lat<4;lat++)
			for(lon=0;lon<4;lon++)
				if(ex.getValue(lat, lon)==0){
					pos[posEmpty].setValue(lat,lon);
					posEmpty++;
				}

	}
	public int getValue(int x,int y){			//get the value of position(x,y);
		return ex.getValue(x, y);
	}
	private int lat,lon,third,posEmpty;
	private init ex;
	private rand ra;
	private position [] pos;
}
class rand{								//type to get random number;
	public rand(){						//constructor;
		random=new int[2];
		number=new Random();
	}
	public void next(int amount){				//get next random number;
		random[0]=number.nextInt(amount);
		random[1]=number.nextInt(10);
		if(random[1]==9) random[1]=4;
		else random[1]=2;
	}
	public int getPos(){					//get a random position;
		return random[0];
	}
	public int getValue(){					//get a random value;
		return random[1];
	}
	private int [] random;
	private Random number;
}
