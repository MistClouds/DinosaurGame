package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Item;

public class Tag extends Item{
	/**
	 * 
	 * @param name - The name of the tag (usually just 'Tag')
	 * @param displayChar - Placeholder because of you can't drop the tag
	 * @param portable - Whether the item is portable or not (Yes in the case of tag)
	 */
	public Tag(String name, char displayChar, boolean portable) {
		super(name, displayChar, portable);
	}
	
	public void addAction(Action action) {
		this.allowableActions.add(action);
	}
}

