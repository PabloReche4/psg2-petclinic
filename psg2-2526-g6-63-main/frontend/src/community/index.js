import React, { useCallback, useEffect, useMemo, useRef, useState } from "react";
import jwt_decode from "jwt-decode";
import FormGenerator from "../components/formGenerator/formGenerator";
import tokenService from "../services/token.service";
import { resolveImageUrl } from "../util/imageUrl";
import "../static/css/community/community.css";

const communityUploadInputsBase = [
  {
    tag: "Caption",
    name: "caption",
    type: "text",
    defaultValue: "",
    isRequired: false,
    validators: [],
  },
  {
    tag: "Pet",
    name: "petSelection",
    type: "select",
    values: ["None"],
    defaultValue: "None",
    isRequired: false,
    validators: [],
  },
  {
    tag: "Photo",
    name: "photoFiles",
    type: "files",
    defaultValue: "",
    isRequired: true,
    validators: [],
  },
];

export default function CommunityPage() {
  const jwt = tokenService.getLocalAccessToken();
  const user = tokenService.getUser();
  const roles = jwt ? jwt_decode(jwt).authorities : [];
  const isOwner = roles.includes("OWNER");
  const [pets, setPets] = useState([]);
  const [photos, setPhotos] = useState([]);
  const [topPhotos, setTopPhotos] = useState([]);
  const [message, setMessage] = useState("");
  const [loading, setLoading] = useState(true);
  const formRef = useRef();

  const uploadInputs = useMemo(() => {
    const petOptions = pets.map((pet) => `${pet.id} - ${pet.name}`);

    return communityUploadInputsBase.map((input) => {
      if (input.name !== "petSelection") {
        return input;
      }

      return {
        ...input,
        values: ["None", ...petOptions],
      };
    });
  }, [pets]);

  const loadPets = useCallback(() => {
    return fetch(`/api/v1/pets?userId=${user.id}`, {
      headers: { Authorization: `Bearer ${jwt}` },
    })
      .then((response) => response.json())
      .then((data) => {
        if (!data.message) {
          setPets(data);
        }
      })
      .catch(() => {});
  }, [jwt, user.id]);

  const loadPhotos = useCallback(() => {
    setLoading(true);
    return fetch("/api/v1/community/photos?limit=24", {
      headers: { Authorization: `Bearer ${jwt}` },
    })
      .then((response) => response.json())
      .then((data) => {
        if (!data.message) {
          setPhotos(data);
        }
      })
      .finally(() => setLoading(false));
  }, [jwt]);

  const loadTopPhotos = useCallback(() => {
    const month = new Date().toISOString().slice(0, 7);
    return fetch(`/api/v1/community/top?month=${month}&limit=5`, {
      headers: { Authorization: `Bearer ${jwt}` },
    })
      .then((response) => response.json())
      .then((data) => {
        if (!data.message) {
          setTopPhotos(data);
        }
      })
      .catch(() => {});
  }, [jwt]);

  const refreshFeed = useCallback(() => {
    loadPhotos();
    loadTopPhotos();
  }, [loadPhotos, loadTopPhotos]);

  useEffect(() => {
    if (isOwner) {
      loadPets();
    }
    loadPhotos();
    loadTopPhotos();
  }, [isOwner, loadPets, loadPhotos, loadTopPhotos]);

  async function handleUpload({ values }) {
    if (!isOwner) {
      setMessage("Only owners can upload photos.");
      return;
    }
    if (!formRef.current.validate()) return;

    const photoFile = values.photoFiles?.length > 0 ? values.photoFiles[0] : "";
    if (!photoFile) {
      setMessage("Please select a photo.");
      return;
    }

    const petSelection = values.petSelection;
    let petId = null;
    if (petSelection && petSelection !== "None") {
      petId = Number(petSelection.split(" - ")[0]);
    }

    const formData = new FormData();
    formData.append("file", photoFile);
    if (values.caption) {
      formData.append("caption", values.caption);
    }
    if (petId !== null) {
      formData.append("petId", String(petId));
    }

    const response = await fetch("/api/v1/community/photos/upload", {
      method: "POST",
      headers: {
        Authorization: `Bearer ${jwt}`,
        Accept: "application/json",
      },
      body: formData,
    });

    const data = await response.json();
    if (data.message) {
      setMessage(data.message);
    } else {
      setMessage("Photo submitted for review.");
      refreshFeed();
    }
  }

  const vote = useCallback((photoId) => {
    fetch(`/api/v1/community/photos/${photoId}/votes`, {
      method: "POST",
      headers: { Authorization: `Bearer ${jwt}` },
    })
      .then((response) => response.json())
      .then((data) => {
        if (data.message) {
          setMessage(data.message);
        }
        refreshFeed();
      });
  }, [jwt, refreshFeed]);

  return (
    <div className="community-page">
      <div className="community-sidebar">
        <h2>Share your pet</h2>
        {isOwner ? (
          <FormGenerator
            ref={formRef}
            inputs={uploadInputs}
            onSubmit={handleUpload}
            buttonText="Share"
            buttonClassName="auth-button"
          />
        ) : (
          <p className="community-empty">Only owners can upload photos.</p>
        )}
        {message && <p className="community-message">{message}</p>}

        <div className="community-top">
          <h3>Top pets this month</h3>
          {topPhotos.length === 0 ? (
            <p className="community-empty">No ranking yet.</p>
          ) : (
            topPhotos.map((photo, index) => (
              <div key={photo.id} className="community-top-item">
                <span className="community-rank">#{index + 1}</span>
                <img src={resolveImageUrl(photo.imageUrl)} alt={photo.petName || "Pet"} />
                <div>
                  <p>{photo.petName || "Pet"}</p>
                  <span>{photo.votes} votes</span>
                </div>
              </div>
            ))
          )}
        </div>
      </div>

      <div className="community-feed">
        <div className="community-feed-header">
          <h2>Community feed</h2>
        </div>
        {loading ? (
          <p className="community-empty">Loading photos...</p>
        ) : photos.length === 0 ? (
          <p className="community-empty">No photos yet.</p>
        ) : (
          <div className="community-grid">
            {photos.map((photo) => (
              <div key={photo.id} className="community-card">
                <img src={resolveImageUrl(photo.imageUrl)} alt={photo.petName || "Pet"} />
                <div className="community-card-body">
                  <div>
                    <h4>{photo.petName || "Pet"}</h4>
                    <p>{photo.caption || "Shared by " + photo.ownerName}</p>
                  </div>
                  <button
                    className={
                      photo.hasVoted
                        ? "community-vote voted"
                        : "community-vote"
                    }
                    onClick={() => vote(photo.id)}
                  >
                    {photo.hasVoted ? "Voted" : "Like"} · {photo.votes}
                  </button>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}
