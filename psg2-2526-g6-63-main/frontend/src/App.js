import React, { useEffect } from "react";
import { Route, Routes } from "react-router-dom";
import jwt_decode from "jwt-decode";
import { ErrorBoundary } from "react-error-boundary";
import AppNavbar from "./AppNavbar";
import Home from "./home";
import PrivateRoute from "./privateRoute";
import PricingPlan from "./owner/plan";
import Register from "./auth/register";
import Login from "./auth/login";
import Logout from "./auth/logout";
import OwnerPetList from "./owner/pets/petList";
import OwnerPetEdit from "./owner/pets/petEdit";
import OwnerVisitEdit from "./owner/visits/visitEdit";
import PlanList from "./public/plan";
import tokenService from "./services/token.service";
import OwnerDashboard from "./owner/dashboard";
import OwnerConsultationList from "./owner/consultations/consultationList";
import OwnerConsultationEdit from "./owner/consultations/consultationEdit";
import OwnerConsultationTickets from "./owner/consultations/tickets/ticketList";
import VetConsultationList from "./vet/consultations/consultationList";
import VetConsultationTickets from "./vet/consultations/tickets/ticketList";
import PetEditAdmin from "./admin/pets/PetEditAdmin";
import PetListAdmin from "./admin/pets/PetListAdmin";
import UserListAdmin from "./admin/users/UserListAdmin";
import UserEditAdmin from "./admin/users/UserEditAdmin";
import OwnerListAdmin from "./admin/owners/OwnerListAdmin";
import OwnerEditAdmin from "./admin/owners/OwnerEditAdmin";
import SpecialtyListAdmin from "./admin/vets/SpecialtyListAdmin";
import SpecialtyEditAdmin from "./admin/vets/SpecialtyEditAdmin";
import VetListAdmin from "./admin/vets/VetListAdmin";
import VetEditAdmin from "./admin/vets/VetEditAdmin";
import VisitListAdmin from "./admin/visits/VisitListAdmin";
import VisitEditAdmin from "./admin/visits/VisitEditAdmin";
import ConsultationListAdmin from "./admin/consultations/ConsultationListAdmin";
import TicketListAdmin from "./admin/consultations/TicketListAdmin";
import ConsultationEditAdmin from "./admin/consultations/ConsultationEditAdmin";
import SwaggerDocs from "./public/swagger";
import ClinicsList from "./clinicOwner/clinicsList"
import EditClinic from "./clinicOwner/clinicEdit"
import ClinicPlan from "./clinicOwner/clinicPlan"
import ClinicAddons from "./clinicOwner/clinicAddons"
import OwnerListClinicOwner from "./clinicOwner/ownersList"
import ClinicOwnerListAdmin from "./admin/clinicOwners/ClinicOwnerListAdmin";
import ClinicOwnerEditAdmin from "./admin/clinicOwners/ClinicOwnerEditAdmin";
import ClinicListAdmin from "./admin/clinics/ClinicListAdmin";
import ClinicEditAdmin from "./admin/clinics/ClinicEditAdmin";
import ConsultationListClinicOwner from "./clinicOwner/consultations/ConsultationListClinicOwner";
import ConsultationEditClinicOwner from "./clinicOwner/consultations/ConsultationEditClinicOwner";
import VetListClinicOwner from "./clinicOwner/vets/VetListClinicOwner";
import VetEditClinicOwner from "./clinicOwner/vets/VetEditClinicOwner";
import HotelRoomsList from "./clinicOwner/hotelRoomsList";
import EditHotelRoom from "./clinicOwner/hotelRoomEdit";
import BookingList from "./owner/bookings/bookingList";
import BookingEdit from "./owner/bookings/bookingEdit";
import AdoptionList from "./owner/adoptions/list";
import MyAdoptionRequests from "./owner/adoptions/myRequests";
import MyAdoptionApplications from "./owner/adoptions/myApplications";
import AdoptionRequestDetail from "./owner/adoptions/requestDetail";
import { useSpaceClient } from "space-react-client";
import ClinicOwnerDashboard from "./clinicOwner/dashboard";
import CommunityPage from "./community";
import CommunityPhotoModeration from "./admin/community/CommunityPhotoModeration";


function ErrorFallback({ error, resetErrorBoundary }) {
  return (
    <div role="alert">
      <p>Something went wrong:</p>
      <pre>{error.message}</pre>
      <button onClick={resetErrorBoundary}>Try again</button>
    </div>
  )
}

function App() {
  const spaceClient = useSpaceClient();
  const jwt = tokenService.getLocalAccessToken();
  let roles = []
  if (jwt) {
    roles = getRolesFromJWT(jwt);
  }

  function getRolesFromJWT(jwt) {
    return jwt_decode(jwt).authorities;
  }

  let adminRoutes = <></>;
  let ownerRoutes = <></>;
  let userRoutes = <></>;
  let vetRoutes = <></>;
  let publicRoutes = <></>;

  roles.forEach((role) => {
    if (role === "ADMIN") {
      adminRoutes = (
        <>
          <Route path="/users" exact={true} element={<PrivateRoute><UserListAdmin /></PrivateRoute>} />
          <Route path="/users/:username" exact={true} element={<PrivateRoute><UserEditAdmin /></PrivateRoute>} />
          <Route path="/owners" exact={true} element={<PrivateRoute><OwnerListAdmin /></PrivateRoute>} />
          <Route path="/owners/:id" exact={true} element={<PrivateRoute><OwnerEditAdmin /></PrivateRoute>} />
          <Route path="/clinics" exact={true} element={<PrivateRoute><ClinicListAdmin /></PrivateRoute>} />
          <Route path="/clinics/:id" exact={true} element={<PrivateRoute><ClinicEditAdmin /></PrivateRoute>} />
          <Route path="/clinicOwners" exact={true} element={<PrivateRoute><ClinicOwnerListAdmin /></PrivateRoute>} />
          <Route path="/clinicOwners/:id" exact={true} element={<PrivateRoute><ClinicOwnerEditAdmin /></PrivateRoute>} />
          <Route path="/pets" exact={true} element={<PrivateRoute><PetListAdmin /></PrivateRoute>} />
          <Route path="/pets/:id" exact={true} element={<PrivateRoute><PetEditAdmin /></PrivateRoute>} />
          <Route path="/pets/:petId/visits" exact={true} element={<PrivateRoute><VisitListAdmin /></PrivateRoute>} />
          <Route path="/pets/:petId/visits/:visitId" exact={true} element={<PrivateRoute><VisitEditAdmin /></PrivateRoute>} />
          <Route path="/vets" exact={true} element={<PrivateRoute><VetListAdmin /></PrivateRoute>} />
          <Route path="/vets/:id" exact={true} element={<PrivateRoute><VetEditAdmin /></PrivateRoute>} />
          <Route path="/vets/specialties" exact={true} element={<PrivateRoute><SpecialtyListAdmin /></PrivateRoute>} />
          <Route path="/vets/specialties/:specialtyId" exact={true} element={<PrivateRoute><SpecialtyEditAdmin /></PrivateRoute>} />
          <Route path="/consultations" exact={true} element={<PrivateRoute><ConsultationListAdmin /></PrivateRoute>} />
          <Route path="/consultations/:consultationId" exact={true} element={<PrivateRoute><ConsultationEditAdmin /></PrivateRoute>} />
          <Route path="/consultations/:consultationId/tickets" exact={true} element={<PrivateRoute><TicketListAdmin /></PrivateRoute>} />
          <Route path="/bookings" exact={true} element={<PrivateRoute><BookingList /></PrivateRoute>} />
          <Route path="/community/moderation" exact={true} element={<PrivateRoute><CommunityPhotoModeration /></PrivateRoute>} />
          <Route path="/vets/specialties/new" exact={true} element={<PrivateRoute><SpecialtyEditAdmin /></PrivateRoute>} />
        </>)
    }
    if (role === "OWNER") {
      ownerRoutes = (
        <>
          <Route path="/dashboard" element={<PrivateRoute><OwnerDashboard /></PrivateRoute>} />
          <Route path="/plan" exact={true} element={<PrivateRoute><PricingPlan /></PrivateRoute>} />
          <Route path="/myPets" exact={true} element={<PrivateRoute><OwnerPetList /></PrivateRoute>} />
          <Route path="/myPets/:id" exact={true} element={<PrivateRoute><OwnerPetEdit /></PrivateRoute>} />
          <Route path="/myPets/:id/visits/:id" exact={true} element={<PrivateRoute><OwnerVisitEdit /></PrivateRoute>} />
          <Route path="/consultations" exact={true} element={<PrivateRoute><OwnerConsultationList /></PrivateRoute>} />
          <Route path="/consultations/:consultationId" exact={true} element={<PrivateRoute><OwnerConsultationEdit /></PrivateRoute>} />
          <Route path="/consultations/:consultationId/tickets" exact={true} element={<PrivateRoute><OwnerConsultationTickets /></PrivateRoute>} />
          <Route path="/bookings" exact={true} element={<PrivateRoute><BookingList /></PrivateRoute>} />
          <Route path="/bookings/:id" exact={true} element={<PrivateRoute><BookingEdit /></PrivateRoute>} />
          <Route path="/adoptions" exact={true} element={<PrivateRoute><AdoptionList /></PrivateRoute>} />
          <Route path="/adoptions/my-requests" exact={true} element={<PrivateRoute><MyAdoptionRequests /></PrivateRoute>} />
          <Route path="/adoptions/my-applications" exact={true} element={<PrivateRoute><MyAdoptionApplications /></PrivateRoute>} />
          <Route path="/adoptions/:requestId" exact={true} element={<PrivateRoute><AdoptionRequestDetail /></PrivateRoute>} />
        </>)
    }
    if (role === "VET") {
      vetRoutes = (
        <>
          {/* <Route path="/dashboard" element={<PrivateRoute><OwnerDashboard /></PrivateRoute>} /> */}
          <Route path="/myPets" exact={true} element={<PrivateRoute><OwnerPetList /></PrivateRoute>} />
          <Route path="/consultations" exact={true} element={<PrivateRoute><VetConsultationList /></PrivateRoute>} />
          <Route path="/consultations/:consultationId/tickets" exact={true} element={<PrivateRoute><VetConsultationTickets /></PrivateRoute>} />
          <Route path="/consultations/:consultationId" exact={true} element={<PrivateRoute><OwnerConsultationEdit /></PrivateRoute>} />
        </>)
    }
    if (role === "CLINIC_OWNER") {
      vetRoutes = (
        <>
          <Route path="/dashboard" element={<PrivateRoute><ClinicOwnerDashboard /></PrivateRoute>} />
          <Route path="/owners" exact={true} element={<PrivateRoute><OwnerListClinicOwner /></PrivateRoute>} />
          <Route path="/clinics" exact={true} element={<PrivateRoute><ClinicsList /></PrivateRoute>} />
          <Route path="/clinics/:id" exact={true} element={<PrivateRoute><EditClinic /></PrivateRoute>} />
          <Route path="/clinics/:id/plan" exact={true} element={<PrivateRoute><ClinicPlan /></PrivateRoute>} />
          <Route path="/clinics/:id/addons" exact={true} element={<PrivateRoute><ClinicAddons /></PrivateRoute>} />
          <Route path="/consultations" exact={true} element={<PrivateRoute><ConsultationListClinicOwner /></PrivateRoute>} />
          <Route path="/consultations/:id" exact={true} element={<PrivateRoute><ConsultationEditClinicOwner /></PrivateRoute>} />
          <Route path="/consultations/:id/tickets" exact={true} element={<PrivateRoute><VetConsultationTickets /></PrivateRoute>} />
          <Route path="/vets" exact={true} element={<PrivateRoute><VetListClinicOwner /></PrivateRoute>} />
          <Route path="/vets/specialties" exact={true} element={<PrivateRoute><SpecialtyListAdmin /></PrivateRoute>} />
          <Route path="/vets/specialties/:specialtyId" exact={true} element={<PrivateRoute><SpecialtyEditAdmin /></PrivateRoute>} />
          <Route path="/vets/:id" exact={true} element={<PrivateRoute><VetEditClinicOwner /></PrivateRoute>} />
          <Route path="/hotelRooms" exact={true} element={<PrivateRoute><HotelRoomsList /></PrivateRoute>} />
          <Route path="/hotelRooms/:id" exact={true} element={<PrivateRoute><EditHotelRoom /></PrivateRoute>} />
          <Route path="/vets/specialties/new" exact={true} element={<PrivateRoute><SpecialtyEditAdmin /></PrivateRoute>} />
        </>)
    }
  })
  if (!jwt) {
    publicRoutes = (
      <>
        <Route path="/register" element={<Register />} />
        <Route path="/login" element={<Login />} />
      </>
    )
  } else {
    userRoutes = (
      <>
        {/* <Route path="/dashboard" element={<PrivateRoute><Dashboard /></PrivateRoute>} /> */}
        <Route path="/community" exact={true} element={<PrivateRoute><CommunityPage /></PrivateRoute>} />
        <Route path="/logout" element={<Logout />} />
        <Route path="/login" element={<Login />} />
      </>
    )
  }

  useEffect(() => {
    if (!jwt) return;

    const userRoles = jwt_decode(jwt).authorities;
    const username = jwt_decode(jwt).sub;

    // Solo los OWNERs tienen contrato en SPACE; evita errores "Contract not found" para otros roles
    if (userRoles.includes("OWNER")) {
      spaceClient.setUserId(username)
        .then(() => console.log("User's pricing token set"))
        .catch(console.error);
    }

    const onSync = () => console.log("Connected & synchronized with Space");
    spaceClient.on("synchronized", onSync);
    return () => spaceClient.off("synchronized", onSync);
  }, [spaceClient, jwt]);

  useEffect(() => {
    if (!jwt) return;
    const userRoles = jwt_decode(jwt).authorities;
    if (!userRoles.includes("OWNER")) return;

    const username = jwt_decode(jwt).sub;
    // Refresca el pricing token periodicamente para que los cambios de plan se reflejen sin re-login
    const interval = setInterval(() => {
      spaceClient.setUserId(username).catch(console.error);
    }, 30000);
    return () => clearInterval(interval);
  }, [spaceClient, jwt]);

  return (
    <div>
      <ErrorBoundary FallbackComponent={ErrorFallback} >
        <AppNavbar />
        <Routes>
          <Route path="/" exact={true} element={<Home />} />
          <Route path="/plans" element={<PlanList />} />
          <Route path="/docs" element={<SwaggerDocs />} />
          {publicRoutes}
          {userRoutes}
          {adminRoutes}
          {ownerRoutes}
          {vetRoutes}
        </Routes>
    </ErrorBoundary>
    </div >
  );
}

export default App;
