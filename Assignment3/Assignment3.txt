/**
 * 
 * @author Phoenix Boisier
 * 
 * Change-log:
 * 	10/7/17
 * 	-reformatted to accommodate error handling for getRoomNorth(), getRoomSouth(), getRoomEast(), getRoomWest()
 * 	
 *
 */
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

public class Assignment3 {

	public static void main(String[] args) {
		
		ArrayList<Room> world = new ArrayList<>();
		
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {
			InputStream xmlInput  = new FileInputStream("input.xml");
			SAXParser saxParser = spf.newSAXParser();
			ProjectXMLParser sxp = new ProjectXMLParser();
			saxParser.parse(xmlInput, sxp);
			
			world = sxp.getRooms();
		}
			
		catch(SAXException|ParserConfigurationException|IOException e){
			e.printStackTrace();
		}
			
		for(int i = 0; i<world.size(); i++) {
			System.out.println(world.get(i).toString()+"\n\n");
		}
		
		System.out.println(world.get(0).getRoomNorth().getName());
		System.out.println(world.get(0).getRoomSouth().getName());
		System.out.println(world.get(0).getRoomEast().getName());
		System.out.println(world.get(0).getRoomWest().getName());
		try {	
			System.out.println(world.get(5).getRoomNorth().getName());

		}
		catch (NullPointerException e) {
			System.out.println("No room found.");
		}
		System.out.println(world.get(9).getRoomNorth().getList()[0].getName());
		
		Scanner scone = new Scanner(System.in);
		String inputFileName;
		boolean scanning = true;
		boolean checking = false;
		boolean running = true;
		
		while(running) {
			while(scanning) {
				System.out.println("What file would you like to use for the world? \n(type 'EXIT' to quit program)");
				inputFileName = scone.next();
				if(inputFileName.toUpperCase().equals("EXIT")) {
					running = false;
					scanning = false;
					checking = false;
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
						checking = true;
					}
						
					catch(SAXException|ParserConfigurationException|IOException e){
						System.out.println("No such file name. Please try again.");
					}
				}
				
			}
			while(checking) {
				System.out.println("Which room would you like to find? "
						+ "\n(type 'EXIT' to quit program)\n(type 'NEW' for new input file)");
				inputFileName = scone.next();
				if(inputFileName.toUpperCase().equals("NEW")) {
					checking = false;
					scanning = true;
					break;
				}
				else if (inputFileName.toUpperCase().equals("EXIT")) {
					running = false;
					scanning = false;
					checking = false;
					break;
				}
				else {
					for(int i = 0; i<world.size(); i++) {
						if(world.get(i).getName().toUpperCase().equals(inputFileName.toUpperCase())) {
							System.out.println(world.get(i).toString());
							break;
						}
						if((i==(world.size()-1))&&!(world.get(i).getName().toUpperCase().equals(inputFileName.toUpperCase()))) {
							System.out.println("No such room exists in the world. Please try again.");
						}
					}
				}
				
			}
		}
		System.out.println("Thank you for playing. Have a nice day.");
		scone.close();
	}
}