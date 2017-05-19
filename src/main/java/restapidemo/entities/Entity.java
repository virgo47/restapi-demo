package restapidemo.entities;

import java.time.Instant;

public abstract class Entity {

	private Long id;
	private Instant created;
	private Instant updated;

	// rather business values, but all three entities share these
	public String name;
	public String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instant getCreated() {
		return created;
	}

	public void setCreated(Instant created) {
		this.created = created;
	}

	public Instant getUpdated() {
		return updated;
	}

	public void setUpdated(Instant updated) {
		this.updated = updated;
	}
}
