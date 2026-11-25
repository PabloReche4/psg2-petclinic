import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import adoptionService from "../../../services/adoption.service";
import getErrorModal from "../../../util/getErrorModal";
import "../../../static/css/owner/adoptions.css";

export default function MyAdoptionApplications() {
  const [applications, setApplications] = useState([]);
  const [message, setMessage] = useState(null);
  const [visible, setVisible] = useState(false);

  useEffect(() => {
    async function loadData() {
      try {
        const response = await adoptionService.getMyApplications();
        setApplications(response.data);
      } catch (error) {
        setMessage(error?.response?.data?.message || "Failed to load applications");
        setVisible(true);
      }
    }
    loadData();
  }, []);

  return (
    <div className="adoptions-page-container">
      <div className="adoptions-header">
        <h1>My Applications</h1>
        <Link to="/adoptions" className="auth-button" style={{ textDecoration: "none" }}>
          Back to Adoptions
        </Link>
      </div>

      {applications.length > 0 ? (
        applications.map((application) => (
          <div key={application.id} className="adoption-card">
            <div className="adoption-card-left">
              <h4>{application.adoptionRequest?.pet?.name}</h4>
              <span><strong>Status:</strong> {application.status}</span>
              <span><strong>Description:</strong> {application.description}</span>
              <span><strong>Created at:</strong> {application.createdAt?.replace("T", " ")}</span>
            </div>
            <div className="adoption-card-right">
              <Link
                to={`/adoptions/${application.adoptionRequest?.id}`}
                className="auth-button brown-edit"
                style={{ textDecoration: "none" }}
              >
                View Request
              </Link>
            </div>
          </div>
        ))
      ) : (
        <p>You have no adoption applications.</p>
      )}

      {getErrorModal(setVisible, visible, message)}
    </div>
  );
}