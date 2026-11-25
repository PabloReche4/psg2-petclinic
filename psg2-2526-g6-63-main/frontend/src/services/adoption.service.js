import axios from "axios";
import TokenService from "./token.service";

const adoptionApi = axios.create({
  baseURL: "/api/v1",
  headers: {
    "Content-Type": "application/json",
  },
});

adoptionApi.interceptors.request.use(
  (config) => {
    const token = TokenService.getLocalAccessToken();
    if (token) {
      config.headers["Authorization"] = "Bearer " + token;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

const getAllOpenAdoptions = () => {
  return adoptionApi.get("/adoptions");
};

const getAdoptionById = (id) => {
  return adoptionApi.get(`/adoptions/${id}`);
};

const getMyRequests = () => {
  return adoptionApi.get("/adoptions/my-requests");
};

const getMyApplications = () => {
  return adoptionApi.get("/adoptions/my-applications");
};

const getApplicationsByRequestId = (requestId) => {
  return adoptionApi.get(`/adoptions/${requestId}/applications`);
};

const createRequest = (petId) => {
  return adoptionApi.post("/adoptions", { petId });
};

const createApplication = (requestId, description) => {
  return adoptionApi.post(`/adoptions/${requestId}/applications`, { description });
};

const approveApplication = (applicationId) => {
  return adoptionApi.put(`/adoptions/applications/${applicationId}/approve`);
};

const rejectApplication = (applicationId) => {
  return adoptionApi.put(`/adoptions/applications/${applicationId}/reject`);
};

const closeRequest = (requestId) => {
  return adoptionApi.put(`/adoptions/${requestId}/close`);
};

const adoptionService = {
  getAllOpenAdoptions,
  getAdoptionById,
  getMyRequests,
  getMyApplications,
  getApplicationsByRequestId,
  createRequest,
  createApplication,
  approveApplication,
  rejectApplication,
  closeRequest,
};

export default adoptionService;