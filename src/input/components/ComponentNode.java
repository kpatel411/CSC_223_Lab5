package input.components;
import visitor.ComponentNodeVisitor;

public interface ComponentNode
{
 Object accept(ComponentNodeVisitor visitor, Object o);
}
