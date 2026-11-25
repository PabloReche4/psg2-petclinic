import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { Form, Input, Label } from "reactstrap";
import tokenService from "../../services/token.service";
import "../../static/css/admin/adminPage.css";
import getErrorModal from "../../util/getErrorModal";
import getIdFromUrl from "../../util/getIdFromUrl";

const jwt = tokenService.getLocalAccessToken();

export default function SpecialtyEditAdmin() {
  const emptyItem = {
    id: "",
    name: "",
  };

  const id = getIdFromUrl(3);
  const isNew = id === "new";

  const [message, setMessage] = useState(null);
  const [visible, setVisible] = useState(false);
  const [specialty, setSpecialty] = useState(emptyItem);

  useEffect(() => {
    if (isNew) {
      setSpecialty(emptyItem);
      return;
    }

    fetch(`/api/v1/vets/specialties/${id}`, {
      headers: {
        Authorization: `Bearer ${jwt}`,
        Accept: "application/json",
        "Content-Type": "application/json",
      },
    })
      .then((response) => response.json())
      .then((json) => {
        if (json.message) {
          setMessage(json.message);
          setVisible(true);
        } else {
          setSpecialty(json);
        }
      })
      .catch((message) => alert(message));
  }, [id, isNew]);

  function handleChange(event) {
    const target = event.target;
    const value = target.value;
    const name = target.name;
    setSpecialty({ ...specialty, [name]: value });
  }

  function handleSubmit(event) {
    event.preventDefault();

    fetch("/api/v1/vets/specialties" + (isNew ? "" : "/" + id), {
      method: isNew ? "POST" : "PUT",
      headers: {
        Authorization: `Bearer ${jwt}`,
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      body: JSON.stringify(specialty),
    })
      .then((response) => response.json())
      .then((json) => {
        if (json.message) {
          setMessage(json.message);
          setVisible(true);
        } else {
          window.location.href = "/vets/specialties";
        }
      })
      .catch((message) => alert(message));
  }

  const modal = getErrorModal(setVisible, visible, message);

  return (
    <div className="auth-page-container">
      <h2>{isNew ? "Add Specialty" : "Edit Specialty"}</h2>
      {modal}
      <div className="auth-form-container">
        <Form onSubmit={handleSubmit}>
          <div className="custom-form-input">
            <Label for="name" className="custom-form-input-label">
              Name
            </Label>
            <Input
              type="text"
              name="name"
              id="name"
              value={specialty.name || ""}
              onChange={handleChange}
              className="custom-input"
            />
          </div>
          <div className="custom-button-row">
            <button className="auth-button">Save</button>
            <Link
              to="/vets/specialties"
              className="auth-button"
              style={{ textDecoration: "none" }}
            >
              Cancel
            </Link>
          </div>
        </Form>
      </div>
    </div>
  );
}