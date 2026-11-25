import React from "react";
import { useEffect, useState, useRef } from "react";
import { Link } from "react-router-dom";
import { Button, Modal, ModalBody, ModalFooter, ModalHeader } from "reactstrap";
import FormGenerator from "../../../components/formGenerator/formGenerator";
import { petEditFormInputs } from "./form/petEditFormInputs";
import { resolveImageUrl } from "../../../util/imageUrl";
import "../../../static/css/owner/editPet.css";
import "../../../static/css/auth/authButton.css";
import "../../../static/css/owner/petPhotos.css";

export default function OwnerPetEdit() {
  let pathArray = window.location.pathname.split("/");

  const emptyItem = {
    id: null,
    name: "",
    birthDate: "",
    type: {},
    owner: {},
  };

  const jwt = JSON.parse(window.localStorage.getItem("jwt"));
  const [message, setMessage] = useState(null);
  const [modalShow, setModalShow] = useState(false);
  const [types, setTypes] = useState([]);
  const [pet, setPet] = useState(emptyItem);
  const [petId, setPetId] = useState(pathArray[2]);
  const [petPhotos, setPetPhotos] = useState([]);
  const [suggestedPhotos, setSuggestedPhotos] = useState([]);
  const [selectedPhotoUrl, setSelectedPhotoUrl] = useState("");
  const [selectedTypeName, setSelectedTypeName] = useState("");
  const [loadingSuggestions, setLoadingSuggestions] = useState(false);
  const editPetFormRef = useRef();

  const defaultPhotoUrl = "data:image/svg+xml;utf8," +
    encodeURIComponent(
      "<svg xmlns='http://www.w3.org/2000/svg' width='320' height='240' viewBox='0 0 320 240'>" +
      "<rect width='320' height='240' fill='#f1e8d8'/>" +
      "<circle cx='160' cy='110' r='60' fill='#d9c9ad'/>" +
      "<circle cx='135' cy='95' r='8' fill='#7a6a55'/>" +
      "<circle cx='185' cy='95' r='8' fill='#7a6a55'/>" +
      "<path d='M140 130 Q160 150 180 130' stroke='#7a6a55' stroke-width='6' fill='none'/>" +
      "<text x='160' y='205' text-anchor='middle' fill='#7a6a55' font-size='16' font-family='Arial'>No photo</text>" +
      "</svg>"
    );

  useEffect(() => setupPet(), []); // eslint-disable-line react-hooks/exhaustive-deps

  function setupPet() {
    // Load pet if editing
    if (petId !== "new" && pet.id == null) {
      fetch(`/api/v1/pets/${petId}`, {
        headers: { Authorization: `Bearer ${jwt}` },
      })
        .then((p) => p.json())
        .then((p) => {
          if (p.message) {
            setMessage(p.message);
            setModalShow(true);
          } else {
            setPet(p);
            setPetId(p.id);
            setSelectedTypeName(p.type?.name || "");
            fetchPetPhotos(p.id);
          }
        })
        .catch((m) => {
          setMessage(String(m));
          setModalShow(true);
        });
    }

    // Load pet types
    if (types.length === 0) {
      fetch(`/api/v1/pets/types`, {
        headers: { Authorization: `Bearer ${jwt}` },
      })
        .then((data) => data.json())
        .then((data) => {
          if (!data.message) setTypes(data);
          else {
            setMessage(data.message);
            setModalShow(true);
          }
        })
        .catch((error) => {
          setMessage(String(error));
          setModalShow(true);
        });
    }
  }

  function fetchPetPhotos(id) {
    fetch(`/api/v1/pets/${id}/photos`, {
      headers: { Authorization: `Bearer ${jwt}` },
    })
      .then((data) => data.json())
      .then((data) => {
        if (!data.message) setPetPhotos(data);
      })
      .catch(() => {});
  }

  function handleChange(event) {
    const target = event.target;
    const value = target.value;
    const name = target.name;

    let newPet = { ...pet };

    if (name === "type") {
      newPet.type = types.filter((type) => type.id === Number(value))[0];
      setSelectedTypeName(newPet.type?.name || "");
    } else {
      // IMPORTANT: update the correct field (name or birthDate)
      newPet[name] = value;
    }

    setPet(newPet);
  }

  async function handleSubmit({ values }) {
    if (!editPetFormRef.current.validate()) return;

    // Backend sets owner automatically when logged as OWNER,
    // so we don't need to send owner in the request.
    const mypet = {
      id: pet.id,
      name: values["name"],
      birthDate: values["birthDate"],
      type: types.filter((type) => type.name === values["type"])[0],
    };

    // ✅ FIX: correct endpoint is /api/v1/pets (NOT /api/v1/mascots)
    const submit = await (
      await fetch(`/api/v1/pets${pet.id ? "/" + petId : ""}`, {
        method: mypet.id ? "PUT" : "POST",
        headers: {
          Authorization: `Bearer ${jwt}`,
          Accept: "application/json",
          "Content-Type": "application/json",
        },
        body: JSON.stringify(mypet),
      })
    ).json();

    if (submit.message) {
      setMessage(submit.message);
      setModalShow(true);
    } else {
      const savedPetId = submit.id || petId;
      const filePhoto = values["photoFiles"]?.length > 0 ? values["photoFiles"][0] : null;

      if (selectedPhotoUrl) {
        await fetch(`/api/v1/pets/${savedPetId}/photo`, {
          method: "POST",
          headers: {
            Authorization: `Bearer ${jwt}`,
            Accept: "application/json",
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            url: selectedPhotoUrl,
            source: "EXTERNAL",
            setAsPrimary: true,
          }),
        });
      } else if (filePhoto) {
        const formData = new FormData();
        formData.append("file", filePhoto);
        formData.append("setAsPrimary", "true");
        await fetch(`/api/v1/pets/${savedPetId}/photo/upload`, {
          method: "POST",
          headers: {
            Authorization: `Bearer ${jwt}`,
            Accept: "application/json",
          },
          body: formData,
        });
      }
      window.location.href = `/myPets`;
    }
  }

  function loadSuggestions() {
    const typeName = selectedTypeName || pet.type?.name || valuesFallbackType();
    if (!typeName) return;
    setLoadingSuggestions(true);
    fetch(`/api/v1/photos/suggestions?petType=${encodeURIComponent(typeName)}&limit=8`, {
      headers: { Authorization: `Bearer ${jwt}` },
    })
      .then((data) => data.json())
      .then((data) => setSuggestedPhotos(data || []))
      .finally(() => setLoadingSuggestions(false));
  }

  function valuesFallbackType() {
    const typeInput = petEditFormInputs.find((input) => input.name === "type");
    return typeInput?.defaultValue || "";
  }

  const title = <h2 className="text-center">{pet.id ? "Edit Pet" : "Add Pet"}</h2>;

  petEditFormInputs.forEach((i) => (i.handleChange = handleChange));

  // Fill type options once
  if (petEditFormInputs[2].values.length < 2) {
    petEditFormInputs[2].values = [
      ...petEditFormInputs[2].values,
      ...types.map((type) => type.name),
    ];
  }

  // Set defaults when editing
  if (pet && petEditFormInputs[2].values.length >= 2) {
    petEditFormInputs[0].defaultValue = pet.name || "";
    petEditFormInputs[1].defaultValue = pet.birthDate || "";
    petEditFormInputs[2].defaultValue = pet.type?.name || "None";
  }

  function handleShow() {
    setModalShow(false);
    setMessage(null);
  }

  let modal = <></>;
  if (message) {
    const show = modalShow;
    const closeBtn = (
      <button className="close" onClick={handleShow} type="button">
        &times;
      </button>
    );
    const cond = message.includes("limit");
    modal = (
      <div>
        <Modal isOpen={show} toggle={handleShow} keyboard={false}>
          {cond ? (
            <ModalHeader>Warning!</ModalHeader>
          ) : (
            <ModalHeader toggle={handleShow} close={closeBtn}>
              Error!
            </ModalHeader>
          )}
          <ModalBody>{message || ""}</ModalBody>
          <ModalFooter>
            <Button color="primary" tag={Link} to={`/myPets`}>
              Back
            </Button>
          </ModalFooter>
        </Modal>
      </div>
    );
  }

  return (
    <div className="edit-pet-page-container">
      <div className="edit-pet-form-container">
        {title}
        <FormGenerator
          ref={editPetFormRef}
          inputs={petEditFormInputs}
          onSubmit={handleSubmit}
          buttonText="Save"
          buttonClassName="auth-button"
        />
        <div className="pet-photo-section">
          <div className="pet-photo-main">
            <h4>Profile photo</h4>
            <img
              className="pet-photo-preview"
              src={resolveImageUrl(selectedPhotoUrl || pet.photoUrl) || defaultPhotoUrl}
              alt="Pet"
            />
            <button
              className="auth-button"
              type="button"
              onClick={loadSuggestions}
              disabled={loadingSuggestions}
            >
              {loadingSuggestions ? "Loading..." : "Load cat suggestions"}
            </button>
          </div>
          <div className="pet-photo-suggestions">
            <h4>Suggested photos</h4>
            {suggestedPhotos.length === 0 ? (
              <p className="pet-photo-empty">No suggestions yet.</p>
            ) : (
              <div className="pet-photo-grid">
                {suggestedPhotos.map((photo) => (
                  <button
                    key={photo.id}
                    type="button"
                    className={
                      selectedPhotoUrl === photo.url
                        ? "pet-photo-thumb selected"
                        : "pet-photo-thumb"
                    }
                    onClick={() => setSelectedPhotoUrl(photo.url)}
                  >
                    <img src={resolveImageUrl(photo.url)} alt="Suggestion" />
                  </button>
                ))}
              </div>
            )}
          </div>
        </div>
        {petId !== "new" && (
          <div className="pet-photo-gallery">
            <h4>Gallery</h4>
            {petPhotos.length === 0 ? (
              <p className="pet-photo-empty">No photos yet.</p>
            ) : (
              <div className="pet-photo-grid">
                {petPhotos.map((photo) => (
                  <div key={photo.id} className="pet-photo-thumb">
                    <img src={resolveImageUrl(photo.url)} alt="Pet" />
                  </div>
                ))}
              </div>
            )}
          </div>
        )}
      </div>
      {modal}
    </div>
  );
}