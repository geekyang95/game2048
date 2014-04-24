import java.util.Random;
public class game2048{
	public static void main(String [] arges){
		event=new Event();
		event.start();
		event.show();
		while(true){
			switch(event.getTestNumber()){
			case 0 :
				if(event.check_Longitudinal()) event.up();
				break;
			case 1 :
				if(event.check_Longitudinal()) event.down();
				break;
			case 2 :
				if(event.check_Lateral()) event.left();
				break;
			case 3 :
				if(event.check_Lateral()) event.right();
				break;
			default :
				System.out.println("Wrong input!please enter again!");
			}
			event.show();
			if(event.findMax()==2048){
				System.out.println("Congratulations!You have passed the game!");
				break;
			}
			if((event.check_Lateral()==false)&&(event.check_Longitudinal()==false)){
				System.out.println("Game Over!Try again!");
				break;
			}
		}
		
	}
	static Event event;
}
class init{
	public init(){
		value=new int[4][4];
		int t1,t2;
		for(t1=0;t1<4;t1++)
			for(t2=0;t2<4;t2++)
				value[t1][t2]=0;
		max=0;
	}
	public void show(){
		int t1,t2;
		for(t1=0;t1<4;t1++){
			for(t2=0;t2<4;t2++){
				if(value[t1][t2]/1000>0) System.out.print(value[t1][t2]+" ");
				else if(value[t1][t2]/100>0) System.out.print(value[t1][t2]+"  ");
				else if(value[t1][t2]/10>0) System.out.print(value[t1][t2]+"   ");
				else System.out.print(value[t1][t2]+"    ");
			}
			System.out.print("\n");
		}
	}
	public void setValue(int x,int y,int num){
		value[x][y]=num;
		setMax(num);
	}
	public int getValue(int x,int y){
		return value[x][y];
	}
	public void setMax(int x){
		if(x>max) max=x;
	}
	public int findMax(){
		return max;
	}
	private int [][] value;
	private int max;
}
class position{
	public position(){
		po_x=0;
		po_y=0;
	}
	public void setValue(int x,int y){
		po_x=x;
		po_y=y;
	}
	public int getPo_x(){
		return po_x;
	}
	public int getPo_y(){
		return po_y;
	}
	private int po_x,po_y;
}
class Event{
	public Event(){
	ex=new init();
	ra=new rand();
	pos=new position[16];
	for(posEmpty=0;posEmpty<16;posEmpty++)
		pos[posEmpty]=new position();
	}
	public void start(){
		fill();
		fill();
	}
	public void show(){
		ex.show();
		System.out.println("======================================");
	}
	public void up(){
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
	public void down(){
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
	public void left(){
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
	public void right(){
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
	public void move_up(){
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
	public void move_down(){
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
	public void move_left(){
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
	public void move_right(){
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
	public boolean check_Longitudinal(){
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
	public boolean check_Lateral(){
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
	public int findMax(){
		return ex.findMax();
	}
	public void fill(){
		setPos();
		ra.next(posEmpty);
		ex.setValue(pos[ra.getPos()].getPo_x(),pos[ra.getPos()].getPo_y(),ra.getValue());
	}
	public void setPos(){
		posEmpty=0;
		for(lat=0;lat<4;lat++)
			for(lon=0;lon<4;lon++)
				if(ex.getValue(lat, lon)==0){
					pos[posEmpty].setValue(lat,lon);
					posEmpty++;
				}
				
	}
	public int getTestNumber(){
		return ra.test();
	}
	private int lat,lon,third,posEmpty;
	private init ex;
	private rand ra;
	private position [] pos;
}
class rand{
	public rand(){
		random=new int[3];
		number=new Random();
	}
	public void next(int amount){
		random[0]=number.nextInt(amount);
		random[1]=number.nextInt(10);
		if(random[1]==9) random[1]=4;
		else random[1]=2;
	}
	public int getPos(){
		return random[0];
	}
	public int getValue(){
		return random[1];
	}
	public int test(){
		random[2]=number.nextInt(4);
		return random[2];
	}
	public int [] random;
	private Random number;
}
