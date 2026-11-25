package org.springframework.samples.petclinic.contracts;

import java.util.HashMap;
import java.util.Map;

import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.stereotype.Service;

import io.github.isagroup.spaceclient.SpaceClient;
import io.github.isagroup.spaceclient.types.ContractToCreate;
import io.github.isagroup.spaceclient.types.ContractToCreate.BillingPeriodToCreate;
import io.github.isagroup.spaceclient.types.UserContact;

@Service
public class SpaceContractService {
  private final SpaceClient spaceClient;

  public SpaceContractService(SpaceClient spaceClient) {
    this.spaceClient = spaceClient;
  }

  public void createContractForOwner(Owner owner) {
		UserContact userContact = new UserContact(owner.getUser().getUsername(), owner.getUser().getUsername());
		userContact.setFirstName(owner.getUser().getUsername());

		BillingPeriodToCreate billingPeriod = new BillingPeriodToCreate(true, 30);

		Map<String, String> contractedServices = new HashMap<>();
		contractedServices.put("petclinic", "2.1.0");

		Map<String, String> subscriptionPlans = new HashMap<>();
		subscriptionPlans.put("petclinic", owner.getClinic().getPlan().toString());
		
		ContractToCreate contractToCreate = new ContractToCreate(
				userContact,
				billingPeriod,
				contractedServices,
        owner.getClinic().getId().toString(),
				subscriptionPlans,
				new HashMap<>());

		this.spaceClient.contracts.addContract(contractToCreate);

	}
}