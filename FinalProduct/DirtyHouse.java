import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

public class DirtyHouse {

	public static void main(String[] args) {

		Scanner scone = new Scanner(System.in);
		ArrayList<Room> world = null;
		PC player = null;
		
		SAXParserFactory spf = SAXParserFactory.newInstance();
		String inputFileName;
		boolean scanning = true;
		boolean running = true;
		
		//gets the filename for the xml input to build the world
		while(running) {
			while(scanning) {
				System.out.println("What file would you like to use for the world? \n(type 'EXIT' to quit program)");
				System.out.println("ex: input.xml");
				inputFileName = scone.next();
				if(inputFileName.toUpperCase().equals("EXIT")) {
					running = false;
					scanning = false;
					break;
				}
				else {
					try {
						InputStream xmlInput  = new FileInputStream(inputFileName);
						SAXParser saxParser = spf.newSAXParser();
						ProjectXMLParser sxp = new ProjectXMLParser();
						saxParser.parse(xmlInput, sxp);
						world = sxp.getRooms();
						scanning = false;
						running = false;
					}
						
					catch(SAXException|ParserConfigurationException|IOException e){
						System.out.println("No such file name. Please try again.");
					}
				}	
			}
		}
		//in the case that the initial loop was exited
		if(world==null) {
			System.out.println("Program quit before building world.");
		}
		//otherwise we find the player
		else {
			for(int i = 0; i<world.size(); i++) {
				for(Creature c: world.get(i).getList()) {
					//we store the player for later use
					if(c.isPlayer()) {
						player = (PC) c;
						System.out.println("Player is "+player.toString());
						System.out.println("Player's room is "+player.getRoom());
						//this method is used to give the player info about the world for cheats
						player.feedWorld(world);
						break;
					}	
				}
			}
			
			//clears the page of all the prior stuff
			for(int i = 0; i<100; i++) {
				System.out.println();
			}
			//screen crawl!
			String l1 = "In a world...";
			String l2 = "Where animals and NPCs live together...";
			String l3 = "It's up to you, to save the day,";
			String l4 = "Make everyone love you, or you lose...";
			String l5 = "Welcome, to Dirty House!";
			
			for(int i = 0; i<l1.length(); i++) {
				System.out.print(l1.substring(i, i+1));
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			for(int i = 0; i<l2.length(); i++) {
				System.out.print(l2.substring(i, i+1));
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			for(int i = 0; i<l3.length(); i++) {
				System.out.print(l3.substring(i, i+1));
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			for(int i = 0; i<l4.length(); i++) {
				System.out.print(l4.substring(i, i+1));
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			for(int i = 0; i<l5.length(); i++) {
				System.out.print(l5.substring(i, i+1));
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
			//fancy title screen
			String title = "";
			
			title = title + "------------000-------------\n";
			title = title + "----------0000000-----------\n";
			title = title + "--------00000000000---------\n";
			title = title + "------000000000000000-------\n";
			title = title + "----0000000000000000000-----\n";
			title = title + "------1=============1-------\n";
			title = title + "------1=|+|====|+|==1-------\n";
			title = title + "------1=============1-------\n";
			title = title + "------1=|+|====|+|==1-------\n";
			title = title + "------1=|*|=========1-------\n";
			title = title + "";
			
			
			System.out.println(title);
			//and now we play
			player.play(scone);
		}
		scone.close();

	}

}
