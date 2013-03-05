package marina.factory;

import javafx.concurrent.Task;
import marina.group.Group;

public abstract class AbstractAlignmentFactory extends Task<Void>{
	protected Group query;
	protected Group baseline;
}
