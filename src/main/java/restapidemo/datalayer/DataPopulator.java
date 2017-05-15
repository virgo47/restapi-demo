package restapidemo.datalayer;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import restapidemo.entities.Box;

@Configuration
public class DataPopulator {

	private final BoxRepository boxRepository;

	@Autowired
	public DataPopulator(BoxRepository boxRepository) {
		this.boxRepository = boxRepository;
	}

	@PostConstruct
	public void createInitialData() {
		box("Vacation", "Things needed for vacation like passports and foreign money.");
		box("Electro-waste", "Old electronics for recycling.");
	}

	private void box(String name, String description) {
		Box box = new Box();
		box.name = name;
		box.description = description;
		boxRepository.save(box);
	}
}
