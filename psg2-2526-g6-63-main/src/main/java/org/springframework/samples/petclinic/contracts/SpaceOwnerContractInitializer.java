package org.springframework.samples.petclinic.contracts;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.github.isagroup.spaceclient.SpaceClient;

import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerService;
import org.springframework.samples.petclinic.pet.Pet;
import org.springframework.samples.petclinic.pet.PetService;
import org.springframework.samples.petclinic.visit.Visit;
import org.springframework.samples.petclinic.visit.VisitService;

@Component
public class SpaceOwnerContractInitializer implements CommandLineRunner {

	private final OwnerService ownerService;
	private final PetService petService;
	private final VisitService visitService;
	private final SpaceClient spaceClient;
  private final SpaceContractService spaceContractService;
	private final HttpClient httpClient;
	private final String spaceClientUrl;
	private final String spaceClientApiKey;

	public SpaceOwnerContractInitializer(OwnerService ownerService, PetService petService, VisitService visitService,
			SpaceClient spaceClient,
      SpaceContractService spaceContractService,
			@Value("${space.client.url}") String spaceClientUrl,
			@Value("${space.client.api-key}") String spaceClientApiKey) {
		this.ownerService = ownerService;
		this.petService = petService;
		this.visitService = visitService;
		this.spaceClient = spaceClient;
		this.spaceContractService = spaceContractService;
		this.spaceClientUrl = spaceClientUrl;
		this.spaceClientApiKey = spaceClientApiKey;
		this.httpClient = HttpClient.newHttpClient();
	}

	@Override
	public void run(String... args) {
		for (Owner owner : this.ownerService.findAll()) {
			removeContractForOwnerIfExists(owner);
			this.spaceContractService.createContractForOwner(owner);
			updateOwnerContractUsage(owner);
		}
	}

	private void removeContractForOwnerIfExists(Owner owner) {
		System.out.println("Comprobando contrato SPACE para owner " + owner.getUser().getUsername());
		
		if (this.spaceClient.contracts.getContract(owner.getUser().getUsername()) != null) {
			String ownerId = owner.getUser().getUsername();
			String encodedOwnerId = URLEncoder.encode(ownerId, StandardCharsets.UTF_8);
			String baseUrl = this.spaceClientUrl.endsWith("/")
					? this.spaceClientUrl.substring(0, this.spaceClientUrl.length() - 1)
					: this.spaceClientUrl;
			String deleteContractUrl = String.format("%s/api/v1/contracts/%s", baseUrl, encodedOwnerId);
			System.out.println("Eliminando contrato SPACE para owner " + ownerId);
			System.out.println("URL de eliminación: " + deleteContractUrl);
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(deleteContractUrl))
					.header("x-api-key", this.spaceClientApiKey)
					.DELETE()
					.build();

			try {
				this.httpClient.send(request, HttpResponse.BodyHandlers.discarding());
			} catch (IOException | InterruptedException e) {
				if (e instanceof InterruptedException) {
					Thread.currentThread().interrupt();
				}
				throw new IllegalStateException("Error eliminando contrato SPACE para owner " + ownerId, e);
			}
		}
	}

	private void updateOwnerContractUsage(Owner owner) {
		Map<String, Number> novation = new HashMap<>();
		List<Pet> pets = petService.findAllPetsByOwnerId(owner.getId());
		Collection<Visit> visits = visitService.findVisitsByOwnerId(owner.getId());
		novation.put("maxPets", pets.size());
		novation.put("maxVisits", visits.size());

		this.spaceClient.contracts.updateContractUsageLevels(owner.getUser().getUsername(), "petclinic", novation);
	}
}