import React from "react";
import { BsDot } from "react-icons/bs";
import { FaCheck, FaCrosshairs, FaTimes, FaAnkh, FaPlus } from "react-icons/fa";
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

const addOns = [
  {
    name: "Visit Reminder",
    price: "15.00 €",
    description: "Recordatorio de cita por correo electronico",
  },
  {
    name: "Pet Picks",
    price: "10.00 €",
    description: "Fotos de mascotas",
  },
];

function booleanFeature(label, enabled) {
  return (
    <li>
      {enabled ? <FaCheck color="green" /> : <FaTimes color="red" />} {label}
    </li>
  );
}

export default function PlanList() {
  return (
    <div className="pricing-page-container">
      <div>
        <h1 className="pricing-title">Pricing Plans</h1>
      </div>
      <div className="section-pricing">
        <div className="pricing-container">
          {plans.map((plan) => (
            <div key={plan.name} className="pricing-card text-center">
              <div
                className="title"
                style={{
                  display: "flex",
                  flexDirection: "column",
                  alignItems: "center",
                }}
              >
                <div className="icon">{plan.icon}</div>
                <h2>{plan.name}</h2>
              </div>
              <div className="plan-price">
                <h4>{plan.price}</h4>
              </div>
              <div className="option">
                <ul>
                  <li>
                    <BsDot color="white" /> {plan.maxPets} pets per owner
                  </li>
                  <li>
                    <BsDot color="white" /> {plan.maxVisits} visit
                    {plan.maxVisits > 1 ? "s" : ""} per month and pet
                  </li>
                  <li>
                    <BsDot color="white" /> {plan.supportPriority} support
                    priority
                  </li>
                  {booleanFeature("Pet Hotel", plan.petHotel)}
                  {booleanFeature("Adoptions", plan.adoptions)}
                  {booleanFeature(
                    "Online Consultations",
                    plan.onlineConsultations
                  )}
                </ul>
              </div>
            </div>
          ))}
        </div>
      </div>
      <div>
        <h1 className="pricing-title">Add-ons</h1>
      </div>
      <div className="section-pricing">
        <div className="pricing-container">
          {addOns.map((addOn) => (
            <div key={addOn.name} className="pricing-card text-center">
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
                  <li>
                    <BsDot color="white" /> {addOn.description}
                  </li>
                </ul>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}