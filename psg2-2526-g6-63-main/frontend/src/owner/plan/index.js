import React, { useEffect, useState } from "react";
import { BsDot } from "react-icons/bs";
import { FaCheck, FaAnkh, FaCrosshairs, FaTimes } from "react-icons/fa";
import { ImStarFull } from "react-icons/im";
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

export default function PricingPlan() {
  const [plan, setPlan] = useState(null);
  const [message, setMessage] = useState(null);
  const jwt = JSON.parse(window.localStorage.getItem("jwt"));

  useEffect(() => {
    setUp();
  }, []);

  async function setUp() {
    const myowner = await (
      await fetch(`/api/v1/plan`, {
        headers: {
          Authorization: `Bearer ${jwt}`,
        },
      })
    ).json();
    if (myowner.message) setMessage(myowner.message);
    else setPlan(myowner.clinic.plan);
  }

  if (message) {
    return <h2 className="text-center">{message}</h2>;
  }

  return (
    <div className="pricing-page-container">
      <div>
        <h1 className="pricing-title">My Plan - {plan}</h1>
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
              {plan === p.name && <button disabled> ACTIVE </button>}
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}