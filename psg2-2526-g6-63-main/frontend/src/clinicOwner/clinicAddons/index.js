import React, { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import { Button, Modal, ModalBody, ModalFooter, ModalHeader } from "reactstrap";
import { FaPlus } from "react-icons/fa";
import tokenService from "../../services/token.service";
import "../../static/css/pricing/pricingPage.css";

// Add-ons alineados con src/main/resources/configuration/pricing/PetClinic.yml
const addOns = [
  {
    key: "visitReminder",
    name: "Visit Reminder",
    price: "15.00 €",
    description: "Recordatorio de cita por correo electronico",
  },
  {
    key: "petPicks",
    name: "Pet Picks",
    price: "10.00 €",
    description: "Fotos de mascotas",
  },
];

export default function ClinicAddons() {
  const { id } = useParams();
  const jwt = tokenService.getLocalAccessToken();

  const [clinic, setClinic] = useState(null);
  const [selectedAddOns, setSelectedAddOns] = useState([]);
  const [message, setMessage] = useState(null);
  const [modalShow, setModalShow] = useState(false);

  useEffect(() => {
    setUp();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  async function setUp() {
    const response = await (
      await fetch(`/api/v1/clinics/${id}`, {
        headers: { Authorization: `Bearer ${jwt}` },
      })
    ).json();
    if (response.message) {
      setMessage(response.message);
      setModalShow(true);
    } else {
      setClinic(response);
      setSelectedAddOns(response.addOns || []);
    }
  }

  async function toggleAddOn(addOnKey) {
    const previous = selectedAddOns;
    const newSelection = previous.includes(addOnKey)
      ? previous.filter((a) => a !== addOnKey)
      : [...previous, addOnKey];

    setSelectedAddOns(newSelection);

    const response = await fetch(`/api/v1/clinics/${id}/subscription`, {
      method: "PUT",
      headers: {
        Authorization: `Bearer ${jwt}`,
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ plan: clinic.plan, addOns: newSelection }),
    });
    const data = await response.json();
    if (!response.ok || data.message) {
      setSelectedAddOns(previous);
      setMessage(data.message || "No se ha podido actualizar la suscripción.");
      setModalShow(true);
    } else {
      setSelectedAddOns(data.addOns || newSelection);
    }
  }

  if (!clinic) {
    return (
      <>
        <p className="text-center">Loading...</p>
        <Modal isOpen={modalShow} toggle={() => setModalShow(!modalShow)} keyboard={false}>
          <ModalHeader>Aviso</ModalHeader>
          <ModalBody>{message || ""}</ModalBody>
          <ModalFooter>
            <Button color="primary" tag={Link} to="/clinics">
              Volver a clínicas
            </Button>
          </ModalFooter>
        </Modal>
      </>
    );
  }

  return (
    <div className="pricing-page-container">
      <div>
        <h1 className="pricing-title">{clinic.name} - Add-ons</h1>
      </div>
      <div className="section-pricing">
        <div className="pricing-container">
          {addOns.map((addOn) => {
            const selected = selectedAddOns.includes(addOn.key);
            return (
              <div key={addOn.key} className="pricing-card text-center">
                <div
                  className="title"
                  style={{
                    display: "flex",
                    flexDirection: "column",
                    alignItems: "center",
                  }}
                >
                  <div className="icon">
                    <FaPlus color="white" />
                  </div>
                  <h2>{addOn.name}</h2>
                </div>
                <div className="plan-price">
                  <h4>{addOn.price}</h4>
                </div>
                <div className="option">
                  <ul>
                    <li>{addOn.description}</li>
                  </ul>
                </div>
                <button onClick={() => toggleAddOn(addOn.key)}>
                  {selected ? "REMOVE" : "ADD"}
                </button>
              </div>
            );
          })}
        </div>
      </div>
      <div className="text-center" style={{ marginTop: "2rem" }}>
        <Link to="/clinics" className="auth-button" style={{ textDecoration: "none" }}>
          Back to clinics
        </Link>
      </div>

      <Modal isOpen={modalShow} toggle={() => setModalShow(!modalShow)} keyboard={false}>
        <ModalHeader
          toggle={() => setModalShow(!modalShow)}
          close={
            <button className="close" onClick={() => setModalShow(false)} type="button">
              &times;
            </button>
          }
        >
          No se puede actualizar la suscripción
        </ModalHeader>
        <ModalBody>{message || ""}</ModalBody>
        <ModalFooter>
          <Button color="primary" onClick={() => setModalShow(false)}>
            Cerrar
          </Button>
        </ModalFooter>
      </Modal>
    </div>
  );
}