/**
 * @author Grace Warren, Khushi Patel, and Wick Martin 
 * JSONParser: takes JSON file and stores information to be read in FigureNode.
 * takes strings and node objects to be returned as a complete figure.
 */

package input.parser;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import builder.DefaultBuilder;
import input.components.*;
import input.components.point.PointNode;
import input.components.point.PointNodeDatabase;
import input.components.segments.SegmentNodeDatabase;
import input.exception.ParseException;

/**
 * Public class JSONParser: takes a JSON file as input and stores information to be read in FigureNode.
 * Takes strings and node objects to be returned as a complete figure.
 */
public class JSONParser
{
	/**
	 * _astRoot instance variable contains a component node.
	 * ComponentNode is implemented by FigureNode; 
	 * 		JSONParser creates a ComponentNode by traversing the JSON file,
	 * 		creating a description, PointNodeDatabase, and SegmentNodeDatabase,
	 * 		instantiating a FigureNode which contains these items, and assigning
	 * 		that FigureNode to _astRoot.
	 */
	protected ComponentNode  _astRoot;
	protected DefaultBuilder _builder;

	/**
	 * JSONParser constructor
	 * Initializes the _astRoot variable value 
	 */
	public JSONParser(DefaultBuilder builder)
	{
		_builder = builder;
		_astRoot = null;
	}

	/**
	 * error() method for JSONParser
	 * Throws a message when a portion of the JSONFile cannot be parsed 
	 * 		This occurs when there is nothing in the file to parse
	 * @param message contains the message shown to the user when this error (exception) is thrown
	 */
	private void error(String message)
	{
		throw new ParseException("Parse error: " + message);
	}

	/**
	 * parse() method for JSONParser
	 * This method reads the JSON File, and digests the nested dictionaries within that file,
	 * 		converting that information to generic classes PointNodeDatabase and SegmentNodeDatabase.
	 * 		This provides what is needed to create a tree, which is much easier to traverse.
	 * @param str contains the JSON File as a string
	 * @return _astRoot - a ComponentNode
	 * @throws ParseException when there is no figure to be parsed within str
	 */
	public ComponentNode parse(String str) throws ParseException
	{
		/**
		 * Parsing is accomplished via the JSONTokenizer class.
		 */
		JSONTokener tokenizer = new JSONTokener(str);
		JSONObject  JSONroot = (JSONObject)tokenizer.nextValue();

		/**
		 * This case handles exceptions wherein the JSON File has no figure to parse
		 */
		if (!JSONroot.has("Figure")) {
			error("No figure found.");
		}
		/**
		 * This extracts the (valid) figure from the JSON File
		 */
		JSONObject figure = JSONroot.getJSONObject("Figure");

		/**
		 * From the figure, this extracts the corresponding data for the figure's 
		 * 		description, points (PointNode), and segments (SegmentNode)
		 * Note that description is fully handled in lines 90, while pndb and sndb are not
		 */
		String description = figure.getString("Description");
		JSONArray pndb = figure.getJSONArray("Points");
		JSONArray sndb = figure.getJSONArray("Segments");

		/**
		 * These method calls read the respective pndb and sndb information to create 
		 * 		instances of PointNodeDatabase and SegmentNodeDatabase
		 */
		PointNodeDatabase pointNodeDatabase = readsPNDB(pndb);
		//should become a call to buildPointNodeDatabase 
		SegmentNodeDatabase segmentNodeDatabase = readsSNDB(sndb, pointNodeDatabase);
		//should become a call to buildSegmentNodeDatabase 


		/**
		 * Instantiates a FigureNode object, and assigns it to _astRoot
		 * returns _astRoot
		 */
		_astRoot = new FigureNode(description, pointNodeDatabase, segmentNodeDatabase);
		return _astRoot;
	}

	/**
	 * readsPNDB() method 
	 * 		reads JSON 'pndb' information and converts it to a PointNodeDatabase
	 * @param pndbArray contains 'pndb' - a JSONArray with the point information for the file
	 * @return an instance of PointNodeDatabase, containing the points from the JSON figure
	 */
	public PointNodeDatabase readsPNDB(JSONArray pndbArray) {
		/**
		 * Stores points in an ArrayList, making them iterable
		 * Instantiates the PointNodeDatabase object 
		 */
		ArrayList<JSONObject> newPNDB = new ArrayList<JSONObject>();
		ArrayList<PointNode> pointNodeList = new ArrayList<PointNode>();
		PointNodeDatabase pointNodeDB = new PointNodeDatabase();
		/**
		 * Loops through objects (points) in the pndbArray and populates the ArrayList
		 * 		again: this makes the points iterable 
		 */
		for (Object obj : pndbArray) {
			newPNDB.add((JSONObject) obj);
		}
		/**
		 * For each item in the (now populated) ArrayList, a PointNode object is created
		 * 		This is then added to the instance of PointNodeDatabase
		 */
		for (JSONObject individual_node : newPNDB) {
			String name = individual_node.getString("name");
			Double x = individual_node.getDouble("x");
			Double y = individual_node.getDouble("y");
			pointNodeList.add(_builder.buildPointNode(name, x, y));
		}
		
		_builder.buildPointDatabaseNode(pointNodeList);
		return pointNodeDB;
	}

	/**
	 * readsSNDB() method 
	 * 		reads JSON 'sndb' information and converts it to a SegmentNodeDatabase
	 * @param sndbArray contains 'sndb' - a JSONArray with the segment information for the file
	 * @param pointNodeDatabase passes the PointNodeDatabase created by readsPNDB() 
	 * 		this makes PointNode information more easily accessible, when applicable 
	 * @return an instance of SegmentNodeDatabase, containing the segments for the JSON Figure 
	 */
	public SegmentNodeDatabase readsSNDB(JSONArray sndbArray, PointNodeDatabase pointNodeDatabase) {
		ArrayList<JSONObject> newSNDB = new ArrayList<JSONObject>();
		SegmentNodeDatabase segmentNodeDB = _builder.buildSegmentNodeDatabase();
		for (Object segmentObj : sndbArray) {
			newSNDB.add((JSONObject) segmentObj);
		}
		for (JSONObject adjacencyListObj : newSNDB) {
			String key = adjacencyListObj.keys().next();
			JSONArray values = adjacencyListObj.getJSONArray(key);
			for (Object value : values) {
				PointNode currentKey = pointNodeDatabase.getNodeByName(key);
				PointNode currentValue =  pointNodeDatabase.getNodeByName((String) value);
				_builder.addSegmentToDatabase(segmentNodeDB, currentKey, currentValue);
			}
		}
		return segmentNodeDB;
	}

}