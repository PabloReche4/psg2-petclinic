import "../../static/css/auth/authButton.css";
import "../../static/css/auth/authPage.css";
import tokenService from "../../services/token.service";
import getIdFromUrl from "../../util/getIdFromUrl";
import getErrorModal from "../../util/getErrorModal";
import useFetchState from "../../util/useFetchState";
import { hotelRoomEditInputs } from "./form/hotelRoomEditInputs";
import FormGenerator from "../../components/formGenerator/formGenerator";
import { useState, useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";

const user = tokenService.getUser();
const jwt = tokenService.getLocalAccessToken();

export default function EditHotelRoom() {
  const id = getIdFromUrl(2);
  const navigator = useNavigate();

  const emptyItem = {
    id: "",
    name: "",
    size: "",
    allowedPetType: null,
    clinic: null,
  };

  const [message, setMessage] = useState(null);
  const [visible, setVisible] = useState(false);
  const [room, setRoom] = useFetchState(
    emptyItem,
    `/api/v1/pethotelrooms/${id}`,
    jwt,
    setMessage,
    setVisible,
    id
  );

  const [dataLoaded, setDataLoaded] = useState(false);
  
  const [petTypes, setPetTypes] = useState([]);
  const [clinics, setClinics] = useState([]);

  const editRoomFormRef = useRef(null);

  useEffect(() => {
    fetch(`/api/v1/pets/types`, {
      headers: { Authorization: `Bearer ${jwt}` },
    })
      .then((res) => res.json())
      .then((data) => {
        setPetTypes(data);
        const petTypeNames = ["None", ...data.map((pt) => pt.name)];
        hotelRoomEditInputs.find((input) => input.name === "allowedPetType").values = petTypeNames;
      });

    fetch(`/api/v1/clinics?userId=${user.id}`, {
      headers: { Authorization: `Bearer ${jwt}` },
    })
      .then((res) => res.json())
      .then((data) => {
        setClinics(data);
        const clinicNames = ["None", ...data.map((c) => c.name)];
        hotelRoomEditInputs.find((input) => input.name === "clinic").values = clinicNames;
      });
  }, []);

  useEffect(() => {
    if (room.id !== "") {
      hotelRoomEditInputs.forEach((input) => {
        if (input.name === "allowedPetType") {
          input.defaultValue = room.allowedPetType.name;
        } else if (input.name === "clinic") {
          input.defaultValue = room.clinic.name;
        } else {
          input.defaultValue = room[input.name];
        }
      });
      setDataLoaded(true);
    } else {
      hotelRoomEditInputs.forEach((input) => {
        input.defaultValue = input.type === "select" ? "None" : "";
      });
    }
  }, [room]);

  function handleSubmit({ values }) {
    if (!editRoomFormRef.current.validate()) return;


    const selectedPetType = petTypes.find((pt) => pt.name === values.allowedPetType);
    const selectedClinic = clinics.find((c) => c.name === values.clinic);

    const roomToSave = {
      name: values.name,
      size: parseInt(values.size), 
      allowedPetType: selectedPetType,
      clinic: selectedClinic,
    };

    if (id !== "new") {
      fetch(`/api/v1/pethotelrooms/${id}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${jwt}`,
        },
        body: JSON.stringify(roomToSave),
      })
        .then((res) => {
          if (res.status === 200) navigator("/hotelRooms");
        })
        .catch((err) => setMessage(err.message));
    } else {
      fetch(`/api/v1/pethotelrooms`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${jwt}`,
        },
        body: JSON.stringify(roomToSave),
      })
        .then((res) => {
          if (res.status === 201) navigator("/hotelRooms");
        })
        .catch((err) => setMessage(err.message));
    }
  }

  const modal = getErrorModal(setVisible, visible, message);

  return (
    <div className="auth-page-container">
      {<h2>{id !== "new" ? "Edit Room" : "Add Room"}</h2>}
      {modal}
      <div className="auth-form-container">
        {dataLoaded ? (
          <FormGenerator
            ref={editRoomFormRef}
            inputs={hotelRoomEditInputs}
            onSubmit={handleSubmit}
            buttonText="Edit"
            buttonClassName="auth-button"
          />
        ) : (
          <FormGenerator
            ref={editRoomFormRef}
            inputs={hotelRoomEditInputs}
            onSubmit={handleSubmit}
            buttonText="Add"
            buttonClassName="auth-button"
          />
        )}
      </div>
    </div>
  );
}