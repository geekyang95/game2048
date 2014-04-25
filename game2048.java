import java.util.Random;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class game2048{
	public static void main(String [] arges){
		GUI window=new GUI();
		window.show();
	}
}
class GUI{
	public GUI(){									//constructor;
		event=new Event();
		frame=new JFrame("2048");
		dialog=new JDialog(frame,false);
		message=new JLabel();
		button=new JButton("exit");
		lab=new JButton[4][4];
		Container container=frame.getContentPane();
		container.setLayout(new GridLayout(4,4));
		for(int t1=0;t1<4;t1++)
			for(int t2=0;t2<4;t2++){
				lab[t1][t2]=new JButton(" ");
				container.add(lab[t1][t2]);
			}
		Container dia=dialog.getContentPane();
		dia.setLayout(new BorderLayout());
		dia.add(message,BorderLayout.CENTER);
		dia.add(button,BorderLayout.SOUTH);
	}
	public void show(){									//show the window;
		frame.setSize(400,400);
		event.start();
		setText(lab);
		frame.setVisible(true);
		start(message,lab);
		dialog.setSize(200, 100);
		dialog.setVisible(true);
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
	}
	public void setText(JButton [][] label){				//set the text of label;
		for(int t1=0;t1<4;t1++)
			for(int t2=0;t2<4;t2++){
				if(event.getValue(t1, t2)==0) label[t1][t2].setText(" ");
				else label[t1][t2].setText(Integer.toString(event.getValue(t1, t2)));
				if(label[t1][t2].getText()==" ") label[t1][t2].setBackground(Color.white);
				else if(event.getValue(t1,t2)<=4) label[t1][t2].setBackground(Color.lightGray);
				else if(event.getValue(t1,t2)<=16) label[t1][t2].setBackground(Color.orange);
				else if(event.getValue(t1,t2)<=64) label[t1][t2].setBackground(Color.red);
				else if(event.getValue(t1,t2)<=1024) label[t1][t2].setBackground(Color.yellow);
				else label[t1][t2].setBackground(Color.black);
			}
	}
	public void action(int x){							//deal with the action;
		switch(x){
		case 0:
			if(event.check_Longitudinal()) event.up();
			break;
		case 1:
			if(event.check_Longitudinal()) event.down();
			break;
		case 2:
			if(event.check_Lateral()) event.left();
			break;
		case 3:
			if(event.check_Lateral()) event.right();
			break;
		default:
			;
			break;
		}
	}
	public void start(JLabel message,JButton [][] label){			//game start;
		while(true){
			action(event.getTestNumber());
			setText(label);
			if((event.check_Lateral()==false)&&(event.check_Longitudinal()==false)){
				message.setText("Game over!Try again!");
				message.setBackground(Color.black);
				break;
			}
			if(event.findMax()==2048){
				message.setText("Congratulations!You've succeed!");
				message.setBackground(Color.red);
				break;
			}
		}
	}
	private Event event;
	private JButton button;
	private JFrame frame;
	private JButton [][] lab;
	private JDialog dialog;
	private JLabel message;
}
class init{								//the data of the game;
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
	public int getValue(int x,int y){	//get the value of position (x,y);
		return value[x][y];
	}
	public void setMax(int x){			//write the max number;
		if(x>max) max=x;
	}
	public int findMax(){				//get the value of max number;
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
	public int getPo_x(){				//get the value of position x;
		return po_x;
	}
	public int getPo_y(){				//get the value of position y;
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
	public void start(){				//the beginning of the game;
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
	public void right(){				//the action of right;
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
	public void move_up(){				//the number move up;
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
	public void move_down(){			//the number move down;
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
	public void move_left(){			//the number move left;
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
	public void move_right(){			//the number move right;
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
	public boolean check_Longitudinal(){		//to check if it is possible to move longitudinally ;；
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
	public boolean check_Lateral(){		//check if it is possible to move laterally;
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
	public int findMax(){				//get the max number of the game;
		return ex.findMax();
	}
	public void fill(){					//give a random number to a random position;
		setPos();
		ra.next(posEmpty);
		ex.setValue(pos[ra.getPos()].getPo_x(),pos[ra.getPos()].getPo_y(),ra.getValue());
	}
	public void setPos(){				//find that witch position is empty;
		posEmpty=0;
		for(lat=0;lat<4;lat++)
			for(lon=0;lon<4;lon++)
				if(ex.getValue(lat, lon)==0){
					pos[posEmpty].setValue(lat,lon);
					posEmpty++;
				}
				
	}
	public int getValue(int x,int y){		//get the value of position(x,y);
		return ex.getValue(x, y);
	}
	public int getTestNumber(){			
		return ra.test();
	}
	private int lat,lon,third,posEmpty;
	private init ex;
	private rand ra;
	private position [] pos;
}
class rand{								//type to get random number;
	public rand(){						//constructor;
		random=new int[3];
		number=new Random();
	}
	public void next(int amount){		//get next random number;
		random[0]=number.nextInt(amount);
		random[1]=number.nextInt(10);
		if(random[1]==9) random[1]=4;
		else random[1]=2;
	}
	public int getPos(){				//get a random position;
		return random[0];
	}
	public int getValue(){				//get a random value;
		return random[1];
	}
	public int test(){
		random[2]=number.nextInt(4);
		return random[2];
	}
	public int [] random;
	private Random number;
}
