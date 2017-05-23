package restapidemo.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/** Box is a container for items. */
public class Box extends Entity {

	public Integer volume;
	public boolean sealed;

	private List<Item> items = new ArrayList<>();

	public Collection<Item> items() {
		return items;
	}
}
