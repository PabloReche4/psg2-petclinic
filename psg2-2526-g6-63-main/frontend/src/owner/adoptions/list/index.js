import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import adoptionService from "../../../services/adoption.service";
import getErrorModal from "../../../util/getErrorModal";
import "../../../static/css/owner/adoptions.css";

export default function AdoptionList() {
  const [requests, setRequests] = useState([]);
  const [message, setMessage] = useState(null);
  const [visible, setVisible] = useState(false);

  useEffect(() => {
    async function loadData() {
      try {
        const response = await adoptionService.getAllOpenAdoptions();
        setRequests(response.data);
      } catch (error) {
        setMessage(error?.response?.data?.message || "Failed to load adoptions");
        setVisible(true);
      }
    }
    loadData();
  }, []);

  return (
    <div className="adoptions-page-container">
      <div className="adoptions-header">
        <h1>Available Adoptions</h1>
        <div className="adoptions-links">
          <Link to="/adoptions/my-requests" className="auth-button" style={{ textDecoration: "none" }}>
            My Requests
          </Link>
          <Link to="/adoptions/my-applications" className="auth-button" style={{ textDecoration: "none" }}>
            My Applications
          </Link>
        </div>
      </div>

      {requests.length > 0 ? (
        requests.map((request) => (
          <div key={request.id} className="adoption-card">
            <div className="adoption-card-left">
              <h4>{request.pet.name}</h4>
              <span><strong>Type:</strong> {request.pet.type?.name}</span>
              <span><strong>Birth date:</strong> {request.pet.birthDate}</span>
              <span><strong>Status:</strong> {request.status}</span>
              <span><strong>Current owner:</strong> {request.originalOwner?.firstName} {request.originalOwner?.lastName}</span>
            </div>
            <div className="adoption-card-right">
              <Link
                to={`/adoptions/${request.id}`}
                className="auth-button brown-edit"
                style={{ textDecoration: "none" }}
              >
                View Detail
              </Link>
            </div>
          </div>
        ))
      ) : (
        <p>No adoption requests available.</p>
      )}

      {getErrorModal(setVisible, visible, message)}
    </div>
  );
}