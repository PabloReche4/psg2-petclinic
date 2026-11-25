import { useEffect, useState } from "react";
import { Button, ButtonGroup, Table } from "reactstrap";
import tokenService from "../../services/token.service";
import getErrorModal from "../../util/getErrorModal";
import { resolveImageUrl } from "../../util/imageUrl";
import "../../static/css/admin/adminPage.css";

const jwt = tokenService.getLocalAccessToken();

export default function CommunityPhotoModeration() {
  const [message, setMessage] = useState(null);
  const [visible, setVisible] = useState(false);
  const [photos, setPhotos] = useState([]);

  useEffect(() => {
    loadPending();
  }, []); // eslint-disable-line react-hooks/exhaustive-deps

  function loadPending() {
    fetch("/api/v1/community/photos/pending?limit=100", {
      headers: { Authorization: `Bearer ${jwt}` },
    })
      .then((data) => data.json())
      .then((data) => {
        if (data.message) {
          setMessage(data.message);
          setVisible(true);
        } else {
          setPhotos(data || []);
        }
      })
      .catch((error) => {
        setMessage(String(error));
        setVisible(true);
      });
  }

  function updateStatus(photoId, status) {
    fetch(`/api/v1/community/photos/${photoId}/status?status=${status}`, {
      method: "PUT",
      headers: { Authorization: `Bearer ${jwt}` },
    })
      .then((data) => data.json())
      .then((data) => {
        if (data.message) {
          setMessage(data.message);
          setVisible(true);
        }
        setPhotos((prev) => prev.filter((photo) => photo.id !== photoId));
      })
      .catch((error) => {
        setMessage(String(error));
        setVisible(true);
      });
  }

  const modal = getErrorModal(setVisible, visible, message);

  return (
    <div className="admin-page-container">
      <h1 className="text-center">Community Photo Moderation</h1>
      {modal}
      <div>
        <Table aria-label="pending-community-photos" className="mt-4">
          <thead>
            <tr>
              <th>Photo</th>
              <th>Pet</th>
              <th>Owner</th>
              <th>Caption</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {photos.length === 0 ? (
              <tr>
                <td colSpan="5">No pending photos.</td>
              </tr>
            ) : (
              photos.map((photo) => (
                <tr key={photo.id}>
                  <td>
                    <img
                      src={resolveImageUrl(photo.imageUrl)}
                      alt={photo.petName || "Pet"}
                      style={{ width: 120, height: 90, objectFit: "cover" }}
                    />
                  </td>
                  <td>{photo.petName || "-"}</td>
                  <td>{photo.ownerName || "-"}</td>
                  <td>{photo.caption || "-"}</td>
                  <td>
                    <ButtonGroup>
                      <Button
                        size="sm"
                        color="success"
                        onClick={() => updateStatus(photo.id, "APPROVED")}
                      >
                        Approve
                      </Button>
                      <Button
                        size="sm"
                        color="danger"
                        onClick={() => updateStatus(photo.id, "REJECTED")}
                      >
                        Reject
                      </Button>
                    </ButtonGroup>
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </Table>
      </div>
    </div>
  );
}
