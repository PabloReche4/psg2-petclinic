import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import adoptionService from "../../../services/adoption.service";
import tokenService from "../../../services/token.service";
import getErrorModal from "../../../util/getErrorModal";
import "../../../static/css/owner/adoptions.css";

export default function MyAdoptionRequests() {
  const [requests, setRequests] = useState([]);
  const [pets, setPets] = useState([]);
  const [selectedPetId, setSelectedPetId] = useState("");
  const [message, setMessage] = useState(null);
  const [visible, setVisible] = useState(false);

  const jwt = tokenService.getLocalAccessToken();

  useEffect(() => {
    loadData();
    loadPets();
  }, []);

  async function loadData() {
    try {
      const response = await adoptionService.getMyRequests();
      setRequests(response.data);
    } catch (error) {
      setMessage(error?.response?.data?.message || "Failed to load requests");
      setVisible(true);
    }
  }

  async function loadPets() {
    try {
      const response = await fetch(`/api/v1/pets/my`, {
        headers: {
          Authorization: `Bearer ${jwt}`,
          "Content-Type": "application/json",
        },
      });

      const data = await response.json();

      if (!response.ok) {
        setMessage(data?.message || "Failed to load pets");
        setVisible(true);
        return;
      }

      if (Array.isArray(data)) {
        setPets(data);
      } else {
        setPets([]);
        setMessage("Pets response is not a list");
        setVisible(true);
      }
    } catch (error) {
      setMessage("Failed to load pets");
      setVisible(true);
    }
  }

  async function handleCreateRequest(e) {
    e.preventDefault();
    try {
      await adoptionService.createRequest(Number(selectedPetId));
      setSelectedPetId("");
      await loadData();
      await loadPets();
      setMessage("Adoption request created successfully");
      setVisible(true);
    } catch (error) {
      setMessage(error?.response?.data?.message || "Failed to create adoption request");
      setVisible(true);
    }
  }

  async function handleCloseRequest(requestId) {
    try {
      await adoptionService.closeRequest(requestId);
      await loadData();
      await loadPets();
      setMessage("Adoption request closed successfully");
      setVisible(true);
    } catch (error) {
      setMessage(error?.response?.data?.message || "Failed to close request");
      setVisible(true);
    }
  }

  return (
    <div className="adoptions-page-container">
      <div className="adoptions-header">
        <h1>My Adoption Requests</h1>
        <Link to="/adoptions" className="auth-button" style={{ textDecoration: "none" }}>
          Back to Adoptions
        </Link>
      </div>

      <form className="adoption-form" onSubmit={handleCreateRequest}>
        <select
          value={selectedPetId}
          onChange={(e) => setSelectedPetId(e.target.value)}
          required
        >
          <option value="">Select one of your pets</option>
          {pets.map((pet) => (
            <option key={pet.id} value={pet.id}>
              {pet.name} - {pet.type?.name}
            </option>
          ))}
        </select>
        <button className="auth-button" type="submit">
          Create Adoption Request
        </button>
      </form>

      {requests.length > 0 ? (
        requests.map((request) => (
          <div key={request.id} className="adoption-card">
            <div className="adoption-card-left">
              <h4>{request.pet.name}</h4>
              <span><strong>Type:</strong> {request.pet.type?.name}</span>
              <span><strong>Status:</strong> {request.status}</span>
              <span><strong>Created at:</strong> {request.createdAt?.replace("T", " ")}</span>
            </div>
            <div className="adoption-card-right">
              <Link
                to={`/adoptions/${request.id}`}
                className="auth-button brown-edit"
                style={{ textDecoration: "none" }}
              >
                View Applications
              </Link>
              {request.status === "OPEN" && (
                <button
                  type="button"
                  className="auth-button brown-delete"
                  onClick={() => handleCloseRequest(request.id)}
                >
                  Close Request
                </button>
              )}
            </div>
          </div>
        ))
      ) : (
        <p>You have no adoption requests.</p>
      )}

      {getErrorModal(setVisible, visible, message)}
    </div>
  );
}