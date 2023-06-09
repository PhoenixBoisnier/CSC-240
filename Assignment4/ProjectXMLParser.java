/**
 * 
 * @author Phoenix Boisier
 * 
 * Change-log:
 * 	10/15/17
 * 	-moved building of creatures and rooms to beginElement()
 * 	-removed most println() statements
 * 
 * 	10/9/17
 * 	-added break to switch case to avoid all rooms being half-dirty
 * 
 * 	10/7/17
 * 	-thought to myself about using maps, but decided I'm still more familiar with ArrayList 
 * 	-removed characters() method as it was not needed here
 * 	
 *
 */
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.util.ArrayList;

public class ProjectXMLParser extends DefaultHandler {

	//these variables are the holding cell for the info needed to create the world
	//they are cleansed after each new room
    private Room roomBuffer;
    private Animal animalBuffer;
    private NPC NPCBuffer;
    private PC PCBuffer;
    
    //these are the variables that handle rooms' neighbors
    private ArrayList<ArrayList<String>> neighbors = new ArrayList<>();
    private String northRoom = "NO SUCH ROOM";
    private String southRoom = "NO SUCH ROOM";
    private String eastRoom = "NO SUCH ROOM";
    private String westRoom = "NO SUCH ROOM";
    private ArrayList<String> neighboringRoomsList = new ArrayList<>();
    
    //this is the variable used to hold creatures until they are placed into a room
    //this is cleansed after they are all placed into the room
    private ArrayList<Creature> creatureBuffer = new ArrayList<>();
    
    //this is the final product
    private ArrayList<Room> rooms = new ArrayList<>();
    
	public void startDocument() throws SAXException {
		
		}
 
	/**
	 * Locates and assigns information to the variables used to create the world in endElement().
	 */
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
		
		if(qName.equals("room")) {
			northRoom = atts.getValue("north");
			southRoom = atts.getValue("south");
			eastRoom = atts.getValue("east");
			westRoom = atts.getValue("west");
			neighboringRoomsList.add(northRoom);
			neighboringRoomsList.add(southRoom);
			neighboringRoomsList.add(eastRoom);
			neighboringRoomsList.add(westRoom);
        	//adds the neighboringRooms array for later assignment
			neighbors.add(neighboringRoomsList);
        	//creates the new room
        	roomBuffer = new Room(atts.getValue("name"),atts.getValue("description"));
        	//this sets the room to the appropriate cleanliness level
        	switch (atts.getValue("state")) {
        		case "clean":{ 
        			roomBuffer.setState(State.CLEAN);
        			break;
        		}
        		case "dirty":{
        			roomBuffer.setState(State.DIRTY);
        			break;
        		}
        		default:{
        			roomBuffer.setState(State.HALFDIRTY);
        		}
        	}
		}
		if(qName.equals("animal")) {
			animalBuffer = new Animal(atts.getValue("name"),atts.getValue("description"));
			creatureBuffer.add(animalBuffer);
			
		}
		if(qName.equals("NPC")) {
			NPCBuffer = new NPC(atts.getValue("name"),atts.getValue("description"));
			creatureBuffer.add(NPCBuffer);
		}
		if(qName.equals("PC")) {
			PCBuffer = new PC(atts.getValue("name"),atts.getValue("description"));
			creatureBuffer.add(PCBuffer);
		}
    }
 
	/**
	 * Builds the individual rooms and creatures in the world, and adds the creatures to the rooms.
	 * This is also where the rooms get their neighboring rooms assigned.
	 */
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        
        if(qName.equals("room")) {
        	//puts each creature into the room then removes it from the buffer
        	for(int i = 0; i<creatureBuffer.size(); i = 0) {
        		roomBuffer.addCreature(creatureBuffer.get(i));
        		creatureBuffer.remove(i);
        	}
        	//removes the strings in neighboringRoomsList
        	neighboringRoomsList = new ArrayList<>();
        	//adds the room into the room list
        	rooms.add(roomBuffer);
        }
        if(qName.equals("xml")) {
    		//this is where all the neighboring rooms are assigned to each other
    		for(int i = 0; i<rooms.size(); i++) {
    			//for all of the rooms
    			for(int p = 0; p<4; p++) {
    				//and for all of the corresponding neighbor sets
    				if(neighbors.get(i).get(p)!=null) {
    					//if the neighbor at index p exists
    					if(p==0) {
    						//where p=0 is the north room
    						for(int t = 0; t<rooms.size(); t++) {
    							//finding the room located at p in neighboring rooms is now found in rooms
    							if(rooms.get(t).getName().equals(neighbors.get(i).get(p))) {
    								//now that we've found that room
    								rooms.get(i).setRoomNorth(rooms.get(t));
    								//this room is now set to rooms at i as the north room
    								rooms.get(t).setRoomSouth(rooms.get(i));
    								//the original room is now set to room at t's south neighbor
    							}
    						}
    					}
    					if(p==1) {
    						//where p=1 is the south room
    						for(int t = 0; t<rooms.size(); t++) {
    							//finding the room located at p in neighboring rooms is now found in rooms
    							if(rooms.get(t).getName().equals(neighbors.get(i).get(p))) {
    								//now that we've found that room
    								rooms.get(i).setRoomSouth(rooms.get(t));
    								//this room is now set to rooms at i as the south room
    								rooms.get(t).setRoomNorth(rooms.get(i));
    								//the original room is now set to room at t's north neighbor
    							}
    						}
    					}
    					if(p==2) {
    						//where p=2 is the east room
    						for(int t = 0; t<rooms.size(); t++) {
    							//finding the room located at p in neighboring rooms is now found in rooms
    							if(rooms.get(t).getName().equals(neighbors.get(i).get(p))) {
    								//now that we've found that room
    								rooms.get(i).setRoomEast(rooms.get(t));
    								//this room is now set to rooms at i as the east room
    								rooms.get(t).setRoomWest(rooms.get(i));
    								//the original room is now set to room at t's West neighbor
    							}
    						}
    					}
    					if(p==3) {
    						//where p=3 is the west room
    						for(int t = 0; t<rooms.size(); t++) {
    							//finding the room located at p in neighboring rooms is now found in rooms
    							if(rooms.get(t).getName().equals(neighbors.get(i).get(p))) {
    								//now that we've found that room
    								rooms.get(i).setRoomWest(rooms.get(t));
    								//this room is now set to rooms at i as the west room
    								rooms.get(t).setRoomEast(rooms.get(i));
    								//the original room is now set to room at t's east neighbor
    							}
    						}
    					}
    				}
    			}
    		}
        }
    }

    /**
     * This doesn't do much.
     */
	public void endDocument() throws SAXException {	
		System.out.println("\n\n\n");
		}
 
	/**
	 * This returns the world to be used as the game world.
	 * @return The ArrayList of rooms that is build by the parser.
	 */
	public ArrayList<Room> getRooms(){
		return rooms;
		}
	}	