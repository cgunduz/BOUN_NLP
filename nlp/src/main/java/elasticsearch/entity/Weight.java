package elasticsearch.entity;

public class Weight {

	private String type;
	private String identifier;
	private Double multiplier;
	
	public Weight(String type, String identifier, double multiplier) {
		super();
		this.type = type;
		this.identifier = identifier;
		this.multiplier = multiplier;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public double getMultiplier() {
		return multiplier;
	}
	public void setMultiplier(double multiplier) {
		this.multiplier = multiplier;
	}
}
