package visitor;

import java.util.AbstractMap;

import input.components.*;
import input.components.point.*;
import input.components.segments.SegmentNode;
import input.components.segments.SegmentNodeDatabase;
import utilities.io.StringUtilities;

//
// This file implements a Visitor (design pattern) with 
// the intent of building an unparsed, String representation
// of a geometry figure.
//
public class UnparseVisitor implements ComponentNodeVisitor
{
	@Override
	public Object visitFigureNode(FigureNode node, Object o)
	{
		@SuppressWarnings("unchecked")
		AbstractMap.SimpleEntry<StringBuilder, Integer> pair = (AbstractMap.SimpleEntry<StringBuilder, Integer>)(o);
		StringBuilder sb = pair.getKey();
		int level = pair.getValue();

		level = 0;
		sb.append(StringUtilities.indent(level) + "Figure" + "\n");
		sb.append(StringUtilities.indent(level) + "{" + "\n");
		sb.append(StringUtilities.indent(level+1) + "Description: " + node.getDescription() + "\n");
		node.getPointsDatabase().accept(this, o);
		node.getSegments().accept(this, o);
		sb.append(StringUtilities.indent(level) + "}" + "\n");

		return sb;
	}

	@Override
	public Object visitSegmentDatabaseNode(SegmentNodeDatabase node, Object o)
	{
		@SuppressWarnings("unchecked")
		AbstractMap.SimpleEntry<StringBuilder, Integer> pair = (AbstractMap.SimpleEntry<StringBuilder, Integer>)(o);
		StringBuilder sb = pair.getKey();
		int level = pair.getValue();

		sb.append(StringUtilities.indent(level) + "Segments: " + "\n");
		sb.append(StringUtilities.indent(level) + "{" + "\n");

		
		for (PointNode adjList : node.getAdjLists().keySet()) {
			sb.append(StringUtilities.indent(level+1) + adjList + " : ");
			for (PointNode adjListNameNode : node.getAdjLists().get(adjList)) {
				visitSegmentNode(new SegmentNode(adjList, adjListNameNode), o);
				sb.append(adjListNameNode.getName() + "    ");
			}			
			sb.append("\n");
		}
		return sb;
	}

	@Override
	public Object visitSegmentNode(SegmentNode node, Object o)
	{
		@SuppressWarnings("unchecked")
		AbstractMap.SimpleEntry<StringBuilder, Integer> pair = (AbstractMap.SimpleEntry<StringBuilder, Integer>)(o);
		StringBuilder sb = pair.getKey();
		int level = pair.getValue();

		sb.append(StringUtilities.indent(level+1) + node.getPoint2().getName() + " : ");
		return sb;
	}

	@Override
	public Object visitPointNodeDatabase(PointNodeDatabase node, Object o)
	{
		@SuppressWarnings("unchecked")
		AbstractMap.SimpleEntry<StringBuilder, Integer> pair = (AbstractMap.SimpleEntry<StringBuilder, Integer>)(o);
		StringBuilder sb = pair.getKey();
		int level = pair.getValue();
		// TODO
		sb.append(StringUtilities.indent(level) + "Points: " + "\n");
		sb.append(StringUtilities.indent(level) + "{" + "\n");
		for (String pointNodeName : node.getAllNodeNames()) {
			PointNode pn = node.getNodeByName(pointNodeName);
			visitPointNode(pn, o);	
		}
		
		sb.append(StringUtilities.indent(level) + "}" + "\n");

		return sb;
	}

	@Override
	public Object visitPointNode(PointNode node, Object o)
	{
		@SuppressWarnings("unchecked")
		AbstractMap.SimpleEntry<StringBuilder, Integer> pair = (AbstractMap.SimpleEntry<StringBuilder, Integer>)(o);
		StringBuilder sb = pair.getKey();
		int level = pair.getValue();
		
		sb.append(StringUtilities.indent(level + 1) + "Point(" + node.getName() 
		+ ")(" + node.getX() + ", " + node.getY() + ")" + "\n");
		
		return sb;
	}
}