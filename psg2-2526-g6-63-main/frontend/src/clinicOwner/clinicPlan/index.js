import React, { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import { Button, Modal, ModalBody, ModalFooter, ModalHeader } from "reactstrap";
import { BsDot } from "react-icons/bs";
import { FaCheck, FaAnkh, FaCrosshairs, FaTimes } from "react-icons/fa";
import { ImStarFull } from "react-icons/im";
import tokenService from "../../services/token.service";
import "../../static/css/pricing/pricingPage.css";

// Datos alineados con src/main/resources/configuration/pricing/PetClinic.yml
const plans = [
  {
    name: "BASIC",
    price: "FREE",
    icon: <FaAnkh color="white" />,
    maxPets: 2,
    maxVisits: 1,
    supportPriority: "LOW",
    petHotel: false,
    adoptions: false,
    onlineConsultations: false,
  },
  {
    name: "GOLD",
    price: "29.99 €",
    icon: <FaCrosshairs color="white" />,
    maxPets: 5,
    maxVisits: 3,
    supportPriority: "MEDIUM",
    petHotel: true,
    adoptions: true,
    onlineConsultations: false,
  },
  {
    name: "PLATINUM",
    price: "59.99 €",
    icon: <ImStarFull color="white" />,
    maxPets: 7,
    maxVisits: 5,
    supportPriority: "HIGH",
    petHotel: true,
    adoptions: true,
    onlineConsultations: true,
  },
];

function booleanFeature(label, enabled) {
  return (
    <li>
      {enabled ? <FaCheck color="green" /> : <FaTimes color="red" />} {label}
    </li>
  );
}

export default function ClinicPlan() {
  const { id } = useParams();
  const jwt = tokenService.getLocalAccessToken();

  const [clinic, setClinic] = useState(null);
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
    } else setClinic(response);
  }

  async function selectPlan(planName) {
    const response = await fetch(`/api/v1/clinics/${id}/plan`, {
      method: "PUT",
      headers: {
        Authorization: `Bearer ${jwt}`,
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      body: JSON.stringify(planName),
    });
    const data = await response.json();
    if (!response.ok || data.message) {
      setMessage(data.message || "No se ha podido cambiar el plan.");
      setModalShow(true);
    } else {
      setClinic(data);
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
        <h1 className="pricing-title">
          {clinic.name} - Plan: {clinic.plan}
        </h1>
      </div>
      <div className="section-pricing">
        <div className="pricing-container">
          {plans.map((p) => (
            <div key={p.name} className="pricing-card text-center">
              <div
                className="title"
                style={{
                  display: "flex",
                  flexDirection: "column",
                  alignItems: "center",
                }}
              >
                <div className="icon">{p.icon}</div>
                <h2>{p.name}</h2>
              </div>
              <div className="plan-price">
                <h4>{p.price}</h4>
              </div>
              <div className="option">
                <ul>
                  <li>
                    <BsDot color="white" /> {p.maxPets} pets per owner
                  </li>
                  <li>
                    <BsDot color="white" /> {p.maxVisits} visit
                    {p.maxVisits > 1 ? "s" : ""} per month and pet
                  </li>
                  <li>
                    <BsDot color="white" /> {p.supportPriority} support priority
                  </li>
                  {booleanFeature("Pet Hotel", p.petHotel)}
                  {booleanFeature("Adoptions", p.adoptions)}
                  {booleanFeature("Online Consultations", p.onlineConsultations)}
                </ul>
              </div>
              {clinic.plan === p.name ? (
                <button disabled> ACTIVE </button>
              ) : (
                <button onClick={() => selectPlan(p.name)}> CREATE </button>
              )}
            </div>
          ))}
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
          No se puede cambiar el plan
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