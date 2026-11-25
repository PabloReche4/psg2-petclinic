import React from "react";
import "../../static/css/clinicOwner/dashboard.css";

const services = [
  {
    id: "clinics",
    icon: "CL",
    title: "Clinics",
    description: "Manage all of your clinic details just from one place.",
  },
  {
    id: "owners",
    icon: "OW",
    title: "Owners",
    description: "Administer pet owners and their details.",
  },
  {
    id: "consultations",
    icon: "CO",
    title: "Consultations",
    description: "Create and review consultations sent to your clinic.",
  },
  {
    id: "vets",
    icon: "VT",
    title: "Vets",
    description: "Veterinarian list, specialties, and availability.",
  },
  {
    id: "hotel-rooms",
    icon: "HR",
    title: "Hotel Rooms",
    description: "Control rooms, occupancy and reservations.",
  },
  {
    id: "docs",
    icon: "DC",
    title: "Docs",
    description: "Access documentation, API references, and support resources.",
  },
  {
    id: "pricing-plans",
    icon: "PP",
    title: "Pricing Plans",
    description: "Available plans and rates, rules, and validity.",
  },
];

export default function ClinicOwnerDashboard() {
  return (
    <div className="clinic-owner-dashboard-page">
      <div className="clinic-owner-dashboard-shell">
        <header className="clinic-owner-dashboard-header">
          <p className="clinic-owner-eyebrow">Clinic Owner View</p>
          <h1>WELCOME TO PETCLINIC!</h1>
          <p className="clinic-owner-subtitle">
            This dashboard helps you find every service quickly and stay on top of
            clinic operations.
          </p>
        </header>

        <section className="clinic-owner-intro">
          <p className="clinic-owner-intro-title">Here's what you can do:</p>
          <p className="clinic-owner-intro-hint">
            Use the tab name to jump quickly.
          </p>
        </section>

        <section className="clinic-owner-grid">
          {services.map((service) => (
            <article className="clinic-owner-card" key={service.id}>
              <div className="clinic-owner-card-icon" aria-hidden="true">
                {service.icon}
              </div>
              <div>
                <h2>{service.title}</h2>
                <p>{service.description}</p>
              </div>
            </article>
          ))}
        </section>
      </div>
    </div>
  );
}
