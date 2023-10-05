package builder;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import input.components.ComponentNode;
import input.components.FigureNode;
import input.components.PointNode;
import input.components.PointNodeDatabase;
import input.components.SegmentNode;
import input.components.SegmentNodeDatabase;
import input.exception.ParseException;

public class GeometryBuilder extends DefaultBuilder {

	public GeometryBuilder() { }

	@Override
    public FigureNode buildFigureNode(String description,
    		                          PointNodeDatabase points,
    		                          SegmentNodeDatabase segments) {
		return new FigureNode(description, points, segments);
    }
    
	@Override
    public SegmentNodeDatabase buildSegmentNodeDatabase() {
		SegmentNodeDatabase segmentNodeDB = new SegmentNodeDatabase();
        return segmentNodeDB;
    }
    
	@Override
    public void addSegmentToDatabase(SegmentNodeDatabase segments, PointNode from, PointNode to) {
    	if (segments != null) segments.addUndirectedEdge(from, to);
    	//TODO: figure out what method is supposed to iterate over segmentNodeDatabase
    }
    
	@Override
    public SegmentNode buildSegmentNode(PointNode pt1, PointNode pt2) {
		return new SegmentNode(pt1, pt2);
    }
    
	@Override
    public PointNodeDatabase buildPointDatabaseNode(List<PointNode> points) {
		PointNodeDatabase pointNodeDB = new PointNodeDatabase();
		for (PointNode pointNode : points) {
			buildPointNode(pointNode.getName(), pointNode.getX(), pointNode.getY());
		}
        return pointNodeDB;
    }
	
	@Override
    public PointNode buildPointNode(String name, double x, double y) {
        return new PointNode(name, x, y);
    }
}
