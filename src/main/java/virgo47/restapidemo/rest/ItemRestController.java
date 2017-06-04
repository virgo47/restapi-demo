package virgo47.restapidemo.rest;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import static virgo47.restapidemo.rest.ControllerUtils.REQUESTED_SUCCESS_CODE;

import virgo47.restapidemo.datalayer.ItemRepository;
import virgo47.restapidemo.entities.Item;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/items")
public class ItemRestController {

	private final ItemRepository itemRepository;

	@Autowired
	public ItemRestController(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}

	@RequestMapping(method = GET)
	public Collection<Item> listItems() {
		// TODO: can I dynamically filter what not to serialize (e.g. omit "box" attribute)?
		// among others: http://www.cowtowncoder.com/blog/archives/2011/02/entry_443.html
		return itemRepository.list();
	}

	@RequestMapping(method = POST)
	public ResponseEntity<Void> addItem(@RequestBody Item item) {
		RestChecker.checkForbiddenAttribute(item.getId(),
			"ID is assigned automatically and MUST NOT be provided");
		Item newItem = itemRepository.save(item);

		return ControllerUtils.createdResponse(newItem);
	}

	@RequestMapping(value = "/{itemId}", method = GET)
	public Item readItem(@PathVariable("itemId") Long itemId) {
		return RestChecker.checkFound(
			itemRepository.find(itemId));
	}

	@RequestMapping(value = "/{itemId}", method = PUT)
	public ResponseEntity<?> updateItem(
		@PathVariable("itemId") Long itemId,
		@RequestParam(value = REQUESTED_SUCCESS_CODE, required = false) Integer successCode,
		@RequestBody Item item)
	{
		RestChecker.checkUpdatedIdMatch(itemId, item);
		Item updatedItem = itemRepository.save(item);

		return ControllerUtils.updateResponse(successCode, updatedItem);
	}

	@RequestMapping(value = "/{itemId}", method = DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteItem(@PathVariable("itemId") Long itemId) {
		Item item = RestChecker.checkFound(itemRepository.find(itemId));
		if (item.box != null) {
			item.box.items().remove(item);
		}
		itemRepository.delete(itemId);
	}
}
