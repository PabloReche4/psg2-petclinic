import React, { useState, useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";
import tokenService from "../../../services/token.service";
import getIdFromUrl from "../../../util/getIdFromUrl";
import getErrorModal from "../../../util/getErrorModal";
import useFetchState from "../../../util/useFetchState";
import { bookingEditInputs } from "./form/bookingEditInputs";
import FormGenerator from "../../../components/formGenerator/formGenerator";

const user = tokenService.getUser();
const jwt = tokenService.getLocalAccessToken();

export default function BookingEdit() {
    const id = getIdFromUrl(2);
    const navigator = useNavigate();
    const [message, setMessage] = useState(null);
    const [visible, setVisible] = useState(false);
    
    const [allPets, setAllPets] = useState([]);
    const [allRooms, setAllRooms] = useState([]);
    const [dataLoaded, setDataLoaded] = useState(false);
    const formRef = useRef(null);

    const [booking, setBooking] = useFetchState(
        { id: "", startDate: "", endDate: "", pet: null, room: null },
        `/api/v1/bookings/${id}`, jwt, setMessage, setVisible, id
    );

    useEffect(() => {
        fetch(`/api/v1/pets?userId=${user.id}`, { headers: { Authorization: `Bearer ${jwt}` } })
            .then(res => res.json())
            .then(data => {
                setAllPets(data);
                bookingEditInputs.find(i => i.name === "pet").values = ["None", ...data.map(p => p.name)];
            });

        fetch(`/api/v1/pethotelrooms`, { headers: { Authorization: `Bearer ${jwt}` } })
            .then(res => res.json())
            .then(data => {
                setAllRooms(data);
                bookingEditInputs.find(i => i.name === "room").values = ["None", ...data.map(r => r.name)];
            });
    }, []);

    useEffect(() => {
        if (booking.id !== "" || id === "new") {
            bookingEditInputs.forEach(input => {
                if (input.name === "pet") input.defaultValue = booking.pet?.name || "None";
                else if (input.name === "room") input.defaultValue = booking.room?.name || "None";
                else input.defaultValue = booking[input.name] || "";
            });
            setDataLoaded(true);
        }
    }, [booking, id]);

    function handleSubmit({ values }) {
        if (!formRef.current.validate()) return;

        const bookingToSave = {
            id: id !== "new" ? id : null,
            startDate: values.startDate,
            endDate: values.endDate,
            pet: allPets.find(p => p.name === values.pet),
            room: allRooms.find(r => r.name === values.room),
        };

        const method = id !== "new" ? "PUT" : "POST";
        const url = id !== "new" ? `/api/v1/bookings/${id}` : `/api/v1/bookings`;

        fetch(url, {
            method: method,
            headers: { "Content-Type": "application/json", Authorization: `Bearer ${jwt}` },
            body: JSON.stringify(bookingToSave),
        }).then(async res => {
            if (res.ok) navigator("/bookings");
            else {
                const textError = await res.text();
                setMessage(textError || "Error processing booking");
                setVisible(true);
            }
        }).catch(err => setMessage(err.message));
    }

    return (
        <div className="auth-page-container">
            <h2>{id !== "new" ? "Edit Booking" : "New Booking"}</h2>
            {getErrorModal(setVisible, visible, message)}
            <div className="auth-form-container">
                {dataLoaded && (
                    <FormGenerator
                        ref={formRef}
                        inputs={bookingEditInputs}
                        onSubmit={handleSubmit}
                        buttonText="Save"
                        buttonClassName="auth-button"
                    />
                )}
            </div>
        </div>
    );
}