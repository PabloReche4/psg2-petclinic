package org.springframework.samples.petclinic.clinic;

import java.util.List;

public record SubscriptionRequest(PricingPlan plan, List<String> addOns) {
}