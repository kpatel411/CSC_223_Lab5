package visitor;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
		// Unpack the input object containing a Stringbuilder and an indentation level
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



		// TODO
		
		for (PointNode adjListName : node.getAdjLists().keySet()) {
			sb.append(StringUtilities.indent(level+1) + adjListName + " : ");
			for (String edgeName : node.edgesAsList(adjListName)) {
				visitSegmentNode(new SegmentNode(adjListName, node. .getNodeByName(edgeName)), o);
				//_segments.edgesAsList(_points.getNodeByName(name))) {
				sb.append(edgeName + "    ");
			}			
			sb.append("\n");
		}
//		for (Map<PointNode, Set<PointNode>> adjList: node.getAdjLists()) {
//			String name = sn.getPoint1().getName();
//			sb.append(StringUtilities.indent(level+1) + name + " : ");
//			visitSegmentNode(sn, o);
//		}
		
		
//		for (PointNode name : names.keySet()) {
//			/**
//			 * for each segment (edge), append the segment information in the following format:
//			 * (indent one level past sub-section outline) name ":" 
//			 */
//			sb.append(StringUtilities.indent(level+1) + name + " : ");
//			/**
//			 * Populate the edges with a for loop that iterates over them, and add them behind 
//			 * 		the ":" colon character 
//			 * After the loop is finished, add a new line to separate the next segment adjacency list 
//			 */
//
//			for (String edgeName : node.edgesAsList(name)) {
//				sb.append(edgeName + "    ");
//			}			
//			sb.append("\n");
//		}
//		sb.append(StringUtilities.indent(level) + "}" + "\n");
//
//		return null;
	}

	/**
	 * This method should NOT be called since the segment database
	 * uses the Adjacency list representation
	 */
	@Override
	public Object visitSegmentNode(SegmentNode node, Object o)
	{
		@SuppressWarnings("unchecked")
		AbstractMap.SimpleEntry<StringBuilder, Integer> pair = (AbstractMap.SimpleEntry<StringBuilder, Integer>)(o);
		StringBuilder sb = pair.getKey();
		int level = pair.getValue();
		
		node.getPoint1(), node.getPoint2()

		
		
		/**
		 * for each segment (edge), append the segment information in the following format:
		 * (indent one level past sub-section outline) name ":" 
		 */
		sb.append(StringUtilities.indent(level+1) + name + " : ");
		/**
		 * Populate the edges with a for loop that iterates over them, and add them behind 
		 * 		the ":" colon character 
		 * After the loop is finished, add a new line to separate the next segment adjacency list 
		 */

		for (String edgeName : node.edgesAsList(name)) {
			sb.append(edgeName + "    ");
		}			
		sb.append("\n");
		
		
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