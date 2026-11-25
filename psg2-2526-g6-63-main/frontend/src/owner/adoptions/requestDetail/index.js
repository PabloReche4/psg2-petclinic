/* eslint-disable react-hooks/exhaustive-deps */
import { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import adoptionService from "../../../services/adoption.service";
import tokenService from "../../../services/token.service";
import getErrorModal from "../../../util/getErrorModal";
import "../../../static/css/owner/adoptions.css";

export default function AdoptionRequestDetail() {
  const { requestId } = useParams();
  const jwt = tokenService.getLocalAccessToken();

  const [request, setRequest] = useState(null);
  const [applications, setApplications] = useState([]);
  const [description, setDescription] = useState("");
  const [isOwnerOfRequest, setIsOwnerOfRequest] = useState(false);
  const [message, setMessage] = useState(null);
  const [visible, setVisible] = useState(false);

  useEffect(() => {
    loadRequest();
  }, [requestId]);

  async function loadRequest() {
    try {
      const requestResponse = await adoptionService.getAdoptionById(requestId);
      const requestData = requestResponse.data;
      setRequest(requestData);

      const currentOwnerResponse = await fetch(`/api/v1/owners/current`, {
        headers: {
          Authorization: `Bearer ${jwt}`,
          "Content-Type": "application/json",
        },
      });
      const currentOwner = await currentOwnerResponse.json();

      const ownerRequest = currentOwner?.id === requestData?.originalOwner?.id;
      setIsOwnerOfRequest(ownerRequest);

      if (ownerRequest) {
        const applicationsResponse = await adoptionService.getApplicationsByRequestId(requestId);
        setApplications(applicationsResponse.data);
      }
    } catch (error) {
      setMessage(error?.response?.data?.message || "Failed to load adoption request");
      setVisible(true);
    }
  }

  async function handleApply(e) {
    e.preventDefault();
    try {
      await adoptionService.createApplication(requestId, description);
      setDescription("");
      setMessage("Application sent successfully");
      setVisible(true);
    } catch (error) {
      setMessage(error?.response?.data?.message || "Failed to create application");
      setVisible(true);
    }
  }

  async function handleApprove(applicationId) {
    try {
      await adoptionService.approveApplication(applicationId);
      await loadRequest();
      setMessage("Application approved successfully");
      setVisible(true);
    } catch (error) {
      setMessage(error?.response?.data?.message || "Failed to approve application");
      setVisible(true);
    }
  }

  async function handleReject(applicationId) {
    try {
      await adoptionService.rejectApplication(applicationId);
      await loadRequest();
      setMessage("Application rejected successfully");
      setVisible(true);
    } catch (error) {
      setMessage(error?.response?.data?.message || "Failed to reject application");
      setVisible(true);
    }
  }

  if (!request) {
    return <p className="adoptions-page-container">Loading...</p>;
  }

  return (
    <div className="adoptions-page-container">
      <div className="adoptions-header">
        <h1>Adoption Detail</h1>
        <Link to="/adoptions" className="auth-button" style={{ textDecoration: "none" }}>
          Back to Adoptions
        </Link>
      </div>

      <div className="adoption-card">
        <div className="adoption-card-left">
          <h4>{request.pet?.name}</h4>
          <span><strong>Type:</strong> {request.pet?.type?.name}</span>
          <span><strong>Birth date:</strong> {request.pet?.birthDate}</span>
          <span><strong>Status:</strong> {request.status}</span>
          <span><strong>Owner:</strong> {request.originalOwner?.firstName} {request.originalOwner?.lastName}</span>
        </div>
      </div>

      {!isOwnerOfRequest && request.status === "OPEN" && (
        <form className="adoption-form-column" onSubmit={handleApply}>
          <h3>Apply for adoption</h3>
          <textarea
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            required
            placeholder="Explain how you will take care of the pet"
            rows="6"
          />
          <button className="auth-button" type="submit">
            Send Application
          </button>
        </form>
      )}

      {isOwnerOfRequest && (
        <div className="applications-section">
          <h3>Applications</h3>
          {applications.length > 0 ? (
            applications.map((application) => (
              <div key={application.id} className="application-card">
                <span>
                  <strong>Applicant:</strong> {application.applicant?.firstName} {application.applicant?.lastName}
                </span>
                <span><strong>Status:</strong> {application.status}</span>
                <span><strong>Description:</strong> {application.description}</span>
                {request.status === "OPEN" && application.status === "PENDING" && (
                  <div className="application-actions">
                    <button
                      className="auth-button brown-edit"
                      onClick={() => handleApprove(application.id)}
                    >
                      Approve
                    </button>
                    <button
                      className="auth-button brown-delete"
                      onClick={() => handleReject(application.id)}
                    >
                      Reject
                    </button>
                  </div>
                )}
              </div>
            ))
          ) : (
            <p>No applications yet.</p>
          )}
        </div>
      )}

      {getErrorModal(setVisible, visible, message)}
    </div>
  );
}