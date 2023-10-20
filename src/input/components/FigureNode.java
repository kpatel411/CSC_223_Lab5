/**
 * @author Grace Warren, Khushi Patel, and Wick Martin 
 * FigureNode: reads JSON file and reads parsed information to be returned as a 
 * 		final figure formatted as a StringBuilder.
 * 		The FigureNode object is created by the JSONParser class, and is now navigable as a tree
 */

package input.components;

import input.components.point.PointNodeDatabase;
import input.components.segments.SegmentNodeDatabase;
import utilities.io.StringUtilities;
import visitor.ComponentNodeVisitor;
import visitor.UnparseVisitor;

/**
 * A basic figure consists of points, segments, and an optional description
 * Each figure has distinct points and segments (thus unique database objects).
 * 
 *  The FigureNode class implements ComponentNode 
 *  	This is the same generic object created in the JSONParser class
 *  	The JSON File has been parsed and is now navigable as a tree, 
 *  	allowing us to print it in a more readable format with the unparse() method
 */ 
public class FigureNode implements ComponentNode
{

	@Override
	public Object accept(ComponentNodeVisitor visitor, Object o)
	{
		return visitor.visitFigureNode(this, o);
	}

	/**
	 * Instantiation of instance variables _description, _points, and _segments,
	 * 		which contain instances of a String, PointNodeDatabase, and SegmentNodeDatabase,
	 * 		created in JSONParser
	 */
	protected String              _description;
	protected PointNodeDatabase   _points;
	protected SegmentNodeDatabase _segments;

	/**
	 * These methods return each instance variable object, which will be helpful in later labs
	 * @return the Description, PointNodeDatabase, and SegmentNodeDatabase objects
	 */
	public String              getDescription()    { return _description; }
	public PointNodeDatabase   getPointsDatabase() { return _points; }
	public SegmentNodeDatabase getSegments()       { return _segments; }

	/**
	 * FigureNode() constructor 
	 * @param description contains the description of the figure
	 * @param points contains the PointNode points within the figure as a PointNodeDatabase
	 * @param segments contains the SegmentNode segments within the figure as a SegmentNodeDatabase
	 */
	public FigureNode(String description, PointNodeDatabase points, SegmentNodeDatabase segments)
	{
		_description = description;
		_points = points;
		_segments = segments;
	}

	/**
	 *  unparse() method
	 *  	Takes the ComponentNode object and traverses it, creating a StringBuilder object
	 *  	This StringBuilder is then printed during testing, allowing us to verify the
	 *  	digestion of a given JSON File in a more readable format 
	 */
	public void unparse(StringBuilder sb, int level)
	{
		//initialize level of the tree for reading
		/**
		 * initialize level to 0 for indentation purposes
		 * create basic "Figure" header and opening/closing braces
		 * within those braces, call helper method handleDescription(), handlePoints() and handleSegments()
		 * 		note that level is passed as level + 1; this indents each sub-section another level
		 */
		level = 0;
		sb.append(StringUtilities.indent(level) + "Figure" + "\n");
		sb.append(StringUtilities.indent(level) + "{" + "\n");
		UnparseVisitor unparseVisitor = new UnparseVisitor();
		unparseVisitor.visitFigureNode(this, level+1);
		sb.append(StringUtilities.indent(level) + "}" + "\n");
	}
}