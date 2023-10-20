package visitor;

import org.json.*;
import input.components.FigureNode;
import input.components.point.PointNode;
import input.components.point.PointNodeDatabase;
import input.components.segments.SegmentNode;
import input.components.segments.SegmentNodeDatabase;

//TODO: add comment describing class and structure of JSON object
//TODO: check with default builder 
//TODO: check that JSONVisitor does create JSONObject properly 
	//see if there's an equals method? depth based?
	//check with original file and produced FigureNode
public class ToJSONVisitor implements ComponentNodeVisitor {

	@Override
	public Object visitFigureNode(FigureNode node, Object o) {
		JSONObject figureNodeObj = new JSONObject();
		figureNodeObj.put("Description", node.getDescription());
		figureNodeObj.put("Points", node.getPointsDatabase().accept(this, o));
		figureNodeObj.put("Segments", node.getSegments().accept(this, o));	
		return figureNodeObj;
	}

	@Override
	public Object visitSegmentDatabaseNode(SegmentNodeDatabase node, Object o) {
		JSONArray sndbArray = new JSONArray();
		for (PointNode adjList : node.getAdjLists().keySet()) {
			for (PointNode adjListNameNode : node.getAdjLists().get(adjList)) {
				sndbArray.put(visitSegmentNode(new SegmentNode(adjList, adjListNameNode), o));
			}			
		}
		return sndbArray;
	}

	@Override
	public Object visitSegmentNode(SegmentNode node, Object o) {
		JSONObject segmentObj = new JSONObject();
		segmentObj.put("Node1", node.getPoint1());
		segmentObj.put("Node2", node.getPoint2());
		return segmentObj;
	}

	@Override
	public Object visitPointNode(PointNode node, Object o) {
		JSONObject nodeObj = new JSONObject();
		nodeObj.put("Name", node.getName());
		nodeObj.put("X", node.getX());
		nodeObj.put("Y", node.getY());
		return nodeObj;
	}

	@Override
	public Object visitPointNodeDatabase(PointNodeDatabase node, Object o) {
		JSONArray pndbArray = new JSONArray();
		for (String pointNodeName : node.getAllNodeNames()) {
			PointNode pn = node.getNodeByName(pointNodeName);
			pndbArray.put(visitPointNode(pn, o));	
		}		
		return pndbArray;
	}
	
}
