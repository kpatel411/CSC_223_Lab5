/**
 * @author Grace Warren, Khushi Patel, and Wick Martin 
 * JSONParser: takes JSON file and stores information to be read in FigureNode.
 * takes strings and node objects to be returned as a complete figure.
 */

package input.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import builder.DefaultBuilder;
import input.components.*;
import input.components.point.PointNode;
import input.components.point.PointNodeDatabase;
import input.components.segments.SegmentNode;
import input.components.segments.SegmentNodeDatabase;
import input.exception.ParseException;

/**
 * Public class JSONParser: takes a JSON file as input and stores information to be read in FigureNode.
 * Takes strings and node objects to be returned as a complete figure.
 */
public class JSONParser
{
	protected ComponentNode  _astRoot;
	protected DefaultBuilder _builder;

	public JSONParser(DefaultBuilder builder)
	{
		_astRoot = null;
		_builder = builder;
	}

	private void error(String message)
	{
		throw new ParseException("Parse error: " + message);
	}

	public ComponentNode parse(String str) throws ParseException
	{

		JSONTokener tokenizer = new JSONTokener(str);
		JSONObject  JSONroot = (JSONObject)tokenizer.nextValue();

		if (!JSONroot.has("Figure")) {
			error("No figure found.");
		}

		JSONObject figure = JSONroot.getJSONObject("Figure");

		String description = figure.getString("Description");
		JSONArray pndb = figure.getJSONArray("Points");
		JSONArray sndb = figure.getJSONArray("Segments");

		PointNodeDatabase pointNodeDatabase = readsPNDB(pndb);
		SegmentNodeDatabase segmentNodeDatabase = readsSNDB(sndb, pointNodeDatabase);

		pointNodeDatabase = readsPNDB(pndb);
		segmentNodeDatabase = readsSNDB(sndb, pointNodeDatabase);
		
		_astRoot = _builder.buildFigureNode(description, pointNodeDatabase, segmentNodeDatabase);
		return _astRoot;
	}

	public PointNodeDatabase readsPNDB(JSONArray pndbArray) {

		List<PointNode> pointNodeDB = new ArrayList<PointNode>();
		
		if (pndbArray == null) return _builder.buildPointDatabaseNode(pointNodeDB);
		
		for (int index = 0; index < pndbArray.length(); index++) {
			PointNode point = readsPoint((JSONObject)pndbArray.get(index));
			if (point != null) pointNodeDB.add(point);
		}
		return _builder.buildPointDatabaseNode(pointNodeDB);
	}
	
	public PointNode readsPoint(JSONObject pointObj) {
		String name = pointObj.getString(JSON_Constants.JSON_NAME);
		double x = pointObj.getDouble(JSON_Constants.JSON_X);
		double y = pointObj.getDouble(JSON_Constants.JSON_Y);
		return _builder.buildPointNode(name, x, y);
	}
	
	public SegmentNodeDatabase readsSNDB(JSONArray sndbArray, PointNodeDatabase pndb) {
		SegmentNodeDatabase segmentNodeDB = _builder.buildSegmentNodeDatabase();
		
		if (sndbArray == null) return segmentNodeDB;
		
		for (Object adjList : sndbArray) {
			JSONObject currentAdjList = (JSONObject) adjList;
			String key = currentAdjList.keys().next();
			PointNode pointOne = pndb.getNodeByName(key);
			
			JSONArray segmentPoints = currentAdjList.getJSONArray(key);
			List<PointNode> edgeList = readsSegment(segmentPoints, pndb);
			
			segmentNodeDB.addAdgacencyList(pointOne, edgeList);
			edgeList.clear();
		}
		return segmentNodeDB;
	}
	
	public List<PointNode> readsSegment(JSONArray edges, PointNodeDatabase pndb) {
		List<PointNode> edgePoints = new ArrayList<PointNode>();
		
		for(Object edge : edges) {
			String currentEdge = (String) edge;
			PointNode currPoint = pndb.getNodeByName(currentEdge);
			edgePoints.add(currPoint);
		}
		return edgePoints;
	}
}