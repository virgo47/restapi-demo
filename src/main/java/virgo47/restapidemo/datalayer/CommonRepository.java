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
		assignIdIfMissing(entity);
		updateTimestamps(entity);

		data.put(entity.getId(), entity);
		return entity;
	}

	private void assignIdIfMissing(T entity) {
		if (entity.getId() == null) {
			entity.setId(nextId());
		}
	}

	private void updateTimestamps(T entity) {
		if (entity.getCreated() == null) {
			entity.setCreated(Instant.now());
		}
		entity.setUpdated(Instant.now());
	}

	public synchronized void delete(Long id) {
		data.remove(id);
	}
}
