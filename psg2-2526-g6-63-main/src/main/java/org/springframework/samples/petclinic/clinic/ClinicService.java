package org.springframework.samples.petclinic.clinic;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.exceptions.PlanDowngradeException;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.pet.Pet;
import org.springframework.samples.petclinic.pet.PetRepository;
import org.springframework.samples.petclinic.vet.Vet;
import org.springframework.samples.petclinic.visit.VisitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.isagroup.spaceclient.SpaceClient;
import io.github.isagroup.spaceclient.types.Contract;
import io.github.isagroup.spaceclient.types.Subscription;

@Service
public class ClinicService {
    private ClinicRepository clinicRepository;
	private final SpaceClient spaceClient;
	private final PetRepository petRepository;
	private final VisitRepository visitRepository;


    @Autowired
	public ClinicService(ClinicRepository clinicRepository, SpaceClient spaceClient, PetRepository petRepository,
			VisitRepository visitRepository) {
		this.clinicRepository = clinicRepository;
		this.spaceClient = spaceClient;
		this.petRepository = petRepository;
		this.visitRepository = visitRepository;
	}

	/**
	 * Checks that no owner of the clinic already exceeds the usage limits of the
	 * target plan. Throws {@link PlanDowngradeException} otherwise so the clinic
	 * owner gets a clear alert instead of silently breaking the owners' access.
	 */
	private void validatePlanLimits(int clinicId, PricingPlan targetPlan) {
		int maxPets = targetPlan.getMaxPets();
		int maxVisits = targetPlan.getMaxVisits();
		LocalDate today = LocalDate.now();

		for (Owner owner : clinicRepository.findOwnersByClinicId(clinicId)) {
			Integer petCount = petRepository.countPetsByOwner(owner.getId());
			if (petCount != null && petCount > maxPets) {
				throw new PlanDowngradeException(String.format(
						"No puedes cambiar al plan %s: el propietario %s tiene %d mascotas y ese plan solo permite %d.",
						targetPlan, owner.getUser().getUsername(), petCount, maxPets));
			}
			for (Pet pet : petRepository.findAllPetsByOwnerId(owner.getId())) {
				Integer visitCount = visitRepository.countVisitsByPetInMonth(pet.getId(), today.getMonthValue(),
						today.getYear());
				if (visitCount != null && visitCount > maxVisits) {
					throw new PlanDowngradeException(String.format(
							"No puedes cambiar al plan %s: la mascota %s del propietario %s tiene %d visitas este mes y ese plan solo permite %d.",
							targetPlan, pet.getName(), owner.getUser().getUsername(), visitCount, maxVisits));
				}
			}
		}
	}

	@Transactional(readOnly = true)
	public List<Clinic> findAll() throws DataAccessException {
		
		return (List<Clinic>) clinicRepository.findAll();
	}

	@Transactional(readOnly = true)
	public List<Clinic> findClinicsByUserId(int userId) throws DataAccessException {
		return clinicRepository.findClinicsByUserId(userId);
	}

	@Transactional(readOnly = true)
	public Clinic findClinicById(int clinicId) throws DataAccessException {
		
		Optional<Clinic> clinic = clinicRepository.findById(clinicId);
		
		if(clinic.isPresent()) {
			return clinic.get();
		}else{
			return null;
		}
	}

	@Transactional(readOnly = true)
	public List<Owner> findOwnersOfUserClinics(int userId) throws DataAccessException {
		return clinicRepository.findOwnersOfUserClinics(userId);
	}

	@Transactional(readOnly = true)
	public List<Vet> findVetsOfUserClinics(int userId) throws DataAccessException {
		return clinicRepository.findVetsOfUserClinics(userId);
	}

    @Transactional
	public Clinic save(Clinic clinic) throws DataAccessException {
		clinicRepository.save(clinic);
		return clinic;
	}

	@Transactional
	public Clinic update(Clinic clinic, int clinicId) throws DataAccessException {
		
		Clinic clinicToUpdate = clinicRepository.findById(clinicId).get();
		if (clinic.getClinicOwner() != null){
			BeanUtils.copyProperties(clinic, clinicToUpdate, "id", "owners");
		}else{
			BeanUtils.copyProperties(clinic, clinicToUpdate, "id", "clinicOwner", "owners");
		}

		return save(clinicToUpdate);
	}

	@Transactional
	public void delete(int clinicId) throws DataAccessException {
		clinicRepository.deleteById(clinicId);
	}
	
	@Transactional
	public Clinic updatePlan(PricingPlan plan, int clinicId) throws DataAccessException {
		validatePlanLimits(clinicId, plan);
		Clinic toUpdate = findClinicById(clinicId);
		toUpdate.setPlan(plan);
		Clinic updatedClinic = save(toUpdate);

		Subscription newSubscription = new Subscription();
		Map<String, String> contractedServices = new HashMap<>();
		contractedServices.put("petclinic", "2.1.0");
		Map<String, String> subscriptionPlans = new HashMap<>();
		subscriptionPlans.put("petclinic", plan.toString());
		Map<String, Map<String, Integer>> subscriptionAddOns = new HashMap<>();
		Map<String, Integer> addOnMap = new HashMap<>();
		for (String addOn : toUpdate.getAddOns()) {
			addOnMap.put(addOn, 1);
		}
		subscriptionAddOns.put("petclinic", addOnMap);
		newSubscription.setContractedServices(contractedServices);
		newSubscription.setSubscriptionPlans(subscriptionPlans);
		newSubscription.setSubscriptionAddOns(subscriptionAddOns);

		List<Contract> updatedContracts = this.spaceClient.contracts.updateContractSubscriptionByGroupId(toUpdate.getId().toString(), newSubscription);
		// updateContractSubscriptionByGroupId devuelve null si SPACE no pudo actualizar
		// (p. ej. la clinica no tiene owners y por tanto no hay contratos con ese groupId).
		// En ese caso el plan en la BD ya esta guardado; solo evitamos el NullPointerException.
		System.out.println("Updated contracts in SPACE: " + (updatedContracts != null ? updatedContracts.size() : 0));

		return updatedClinic;
	}

@Transactional
public Clinic saveAddOns(int clinicId, List<String> addOns) throws DataAccessException {
    Clinic clinic = findClinicById(clinicId);
    clinic.setAddOns(addOns != null ? addOns : new ArrayList<>());
    return save(clinic);
}

@Transactional
public Clinic updateSubscription(PricingPlan plan, List<String> addOns, int clinicId) throws DataAccessException {
  // No se valida aqui: gestionar add-ons no cambia el plan ni sus limites de uso.
  Clinic toUpdate = findClinicById(clinicId);
  toUpdate.setPlan(plan);
  Clinic updatedClinic = save(toUpdate);

  Subscription newSubscription = new Subscription();

  Map<String, String> contractedServices = new HashMap<>();
  contractedServices.put("petclinic", "2.1.0");

  Map<String, String> subscriptionPlans = new HashMap<>();
  subscriptionPlans.put("petclinic", plan.toString());

  Map<String, Map<String, Integer>> subscriptionAddOns = new HashMap<>();
  Map<String, Integer> addOnMap = new HashMap<>();

  for (String addOn : addOns) {
      addOnMap.put(addOn, 1);
  }

  subscriptionAddOns.put("petclinic", addOnMap);

  newSubscription.setContractedServices(contractedServices);
  newSubscription.setSubscriptionPlans(subscriptionPlans);
  newSubscription.setSubscriptionAddOns(subscriptionAddOns);

  this.spaceClient.contracts.updateContractSubscriptionByGroupId(
                                            toUpdate.getId().toString(), 
                                            newSubscription
                                          );
  return updatedClinic;
}

}
