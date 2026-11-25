package org.springframework.samples.petclinic.clinic;

public enum PricingPlan {

	// Limites alineados con src/main/resources/configuration/pricing/PetClinic.yml
	BASIC(2, 1),
	GOLD(5, 3),
	PLATINUM(7, 5);

	private final int maxPets;
	private final int maxVisits;

	PricingPlan(int maxPets, int maxVisits) {
		this.maxPets = maxPets;
		this.maxVisits = maxVisits;
	}

	public int getMaxPets() {
		return maxPets;
	}

	public int getMaxVisits() {
		return maxVisits;
	}
}