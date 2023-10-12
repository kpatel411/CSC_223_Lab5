package visitor;

import java.io.BufferedReader;
import java.util.AbstractMap;

import org.json.*;
import input.components.FigureNode;
import input.components.point.PointNode;
import input.components.point.PointNodeDatabase;
import input.components.segments.SegmentNode;
import input.components.segments.SegmentNodeDatabase;
import input.parser.JSONParser;
import utilities.io.StringUtilities;

public class ToJSONVisitor implements ComponentNodeVisitor {

	@Override
	public Object visitFigureNode(FigureNode node, Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitSegmentDatabaseNode(SegmentNodeDatabase node, Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitSegmentNode(SegmentNode node, Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitPointNode(PointNode node, Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitPointNodeDatabase(PointNodeDatabase node, Object o) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
