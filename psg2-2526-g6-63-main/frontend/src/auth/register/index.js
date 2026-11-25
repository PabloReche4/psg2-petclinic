import "../../static/css/auth/authButton.css";
import "../../static/css/auth/authPage.css";
import tokenService from "../../services/token.service";
import FormGenerator from "../../components/formGenerator/formGenerator";
import { registerFormOwnerInputs } from "./form/registerFormOwnerInputs";
import { registerFormVetInputs } from "./form/registerFormVetInputs";
import { registerFormClinicOwnerInputs } from "./form/registerFormClinicOwnerInputs";
import { useEffect, useRef, useState } from "react";

export default function Register() {
  let [type, setType] = useState(null);
  let [authority, setAuthority] = useState(null);
  let [clinics, setClinics] = useState([]);

  const registerFormRef = useRef();

  function handleButtonClick(event) {
    const target = event.target;
    let value = target.value;
    if (value === "Back") value = null;
    else setAuthority(value);
    setType(value);
  }

  function handleSubmit({ values }) {
    if (!registerFormRef.current.validate()) return;

    const request = { ...values };
    request["authority"] = authority;

// Only Owner/Vet forms include "clinic"
if (typeof request.clinic === "string") {
  if (request.clinic === "None" || request.clinic.trim() === "") {
    // Don't send clinic at all when "None" is selected
    delete request.clinic;
  } else {
    const selected = clinics.find((c) => c.name === request.clinic);
    if (selected) request.clinic = selected;
    else delete request.clinic; // safety
  }
}
    let state = "";

    fetch("/api/v1/auth/signup", {
      headers: { "Content-Type": "application/json" },
      method: "POST",
      body: JSON.stringify(request),
    })
      .then(function (response) {
        if (response.status === 200) {
          const loginRequest = {
            username: request.username,
            password: request.password,
          };

          fetch("/api/v1/auth/signin", {
            headers: { "Content-Type": "application/json" },
            method: "POST",
            body: JSON.stringify(loginRequest),
          })
            .then(function (response) {
              if (response.status === 200) {
                state = "200";
                return response.json();
              } else {
                state = "";
                return response.json();
              }
            })
            .then(function (data) {
              if (state !== "200") alert(data.message);
              else {
                tokenService.setUser(data);
                tokenService.updateLocalAccessToken(data.token);
                window.location.href = "/dashboard";
              }
            })
            .catch((message) => {
              alert(message);
            });
        } else {
          return response.json().then((data) => alert(data.message));
        }
      })
      .catch((message) => {
        alert(message);
      });
  }

  useEffect(() => {
    if (type !== "Owner" && type !== "Vet") return;

    // Find the clinic select input safely (NO hard-coded index)
    const clinicInput =
      registerFormVetInputs.find((i) => i.name === "clinic") ||
      registerFormOwnerInputs.find((i) => i.name === "clinic");

    // Guard: clinicInput or clinicInput.values might not exist yet
    if (!clinicInput || !Array.isArray(clinicInput.values)) return;

    // Avoid refetching if already populated (default is ["None"])
    if (clinicInput.values.length !== 1) return;

    fetch("/api/v1/clinics")
      .then((response) => response.json())
      .then((data) => {
        setClinics(data);

        if (Array.isArray(data) && data.length !== 0) {
          const clinicNames = data.map((clinic) => clinic.name);
          clinicInput.values = ["None", ...clinicNames];
        }
      })
      .catch((message) => {
        alert(message);
      });
  }, [type]);

  if (type) {
    return (
      <div className="auth-page-container">
        <h1>Register</h1>
        <div className="auth-form-container">
          <FormGenerator
            ref={registerFormRef}
            inputs={
              type === "Owner"
                ? registerFormOwnerInputs
                : type === "Vet"
                ? registerFormVetInputs
                : registerFormClinicOwnerInputs
            }
            onSubmit={handleSubmit}
            numberOfColumns={1}
            listenEnterKey
            buttonText="Save"
            buttonClassName="auth-button"
          />
        </div>
      </div>
    );
  } else {
    return (
      <div className="auth-page-container">
        <div className="auth-form-container">
          <h1>Register</h1>
          <h2 className="text-center text-md">
            What type of user will you be?
          </h2>
          <div className="options-row">
            <button
              className="auth-button"
              value="Owner"
              onClick={handleButtonClick}
            >
              Owner
            </button>
            <button
              className="auth-button"
              value="Vet"
              onClick={handleButtonClick}
            >
              Vet
            </button>
            <button
              className="auth-button"
              value="Clinic Owner"
              onClick={handleButtonClick}
            >
              Clinic Owner
            </button>
          </div>
        </div>
      </div>
    );
  }
}