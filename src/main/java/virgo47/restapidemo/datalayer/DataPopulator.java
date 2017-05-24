package virgo47.restapidemo.datalayer;

import virgo47.restapidemo.entities.Box;
import virgo47.restapidemo.entities.Item;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataPopulator {

	private final BoxRepository boxRepository;
	private final ItemRepository itemRepository;

	@Autowired
	public DataPopulator(BoxRepository boxRepository, ItemRepository itemRepository) {
		this.boxRepository = boxRepository;
		this.itemRepository = itemRepository;
	}

	@PostConstruct
	public void createInitialData() {
		box("Vacation", "Things needed for vacation like passports and foreign money.");
		Box electroWaste = box("Electro-waste", "Old electronics for recycling.");
		item("Old battery", electroWaste);
		item("HP with blank display", electroWaste);
	}

	private Item item(String name, Box box) {
		Item item = new Item();
		item.name = name;
		if (box != null) {
			item.box = box;
			box.items().add(item);
		}
		return itemRepository.save(item);
	}

	private Box box(String name, String description) {
		Box box = new Box();
		box.name = name;
		box.description = description;
		return boxRepository.save(box);
	}
}
