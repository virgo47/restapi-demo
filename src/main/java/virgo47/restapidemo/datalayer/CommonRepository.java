package virgo47.restapidemo.datalayer;

import virgo47.restapidemo.entities.Entity;

import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class CommonRepository<T extends Entity> {

	private long idSequence = 0;
	private Map<Long, T> data = new HashMap<>();

	private synchronized long nextId() {
		idSequence++;
		return idSequence;
	}

	public synchronized T find(Long id) {
		return data.get(id);
	}

	public synchronized Collection<T> list() {
		return new HashSet<>(data.values());
	}

	public synchronized T save(T entity) {
		updateTimestamps(entity);
		assignIdIfMissing(entity);

		data.put(entity.getId(), entity);
		return entity;
	}

	private void updateTimestamps(T entity) {
		if (entity.getId() == null) {
			entity.setCreated(Instant.now());
		} else {
			copyCreatedFromExistingEntity(entity);
		}
		entity.setUpdated(Instant.now());
	}

	private void copyCreatedFromExistingEntity(T entity) {
		entity.setCreated(find(entity.getId()).getCreated());
	}

	private void assignIdIfMissing(T entity) {
		if (entity.getId() == null) {
			entity.setId(nextId());
		}
	}

	public synchronized void delete(Long id) {
		data.remove(id);
	}
}
