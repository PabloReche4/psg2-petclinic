import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { Button, ButtonGroup, Table } from "reactstrap";
import tokenService from "../../../services/token.service";
import useFetchState from "../../../util/useFetchState";
import getErrorModal from "../../../util/getErrorModal";
import "../../../static/css/admin/adminPage.css";

const user = tokenService.getUser();
const jwt = tokenService.getLocalAccessToken();

export default function BookingList() {
    const [message, setMessage] = useState(null);
    const [visible, setVisible] = useState(false);
    const [bookings, setBookings] = useFetchState([], `/api/v1/bookings`, jwt, setMessage, setVisible);
    const navigator = useNavigate();

    const isAdmin = user.roles.includes("ADMIN");

    const deleteBooking = (id) => {
        if (!window.confirm("Are you sure?")) return;
        fetch(`/api/v1/bookings/${id}`, {
            method: "DELETE",
            headers: { Authorization: `Bearer ${jwt}` },
        }).then((res) => {
            if (res.status === 204 || res.status === 200) {
                navigator(0);
            }
        });
    };

    const bookingsList = bookings.map((b) => (
        <tr key={b.id}>
            <td className="text-center">{b.pet.name}</td>
            <td className="text-center">{b.room.name}</td>
            <td className="text-center">{b.startDate}</td>
            <td className="text-center">{b.endDate}</td>
            {!isAdmin && (
                <td className="text-center">
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link} to={`/bookings/${b.id}`}>Edit</Button>
                        <Button size="sm" color="danger" onClick={() => deleteBooking(b.id)}>Delete</Button>
                    </ButtonGroup>
                </td>
            )}
        </tr>
    ));

    return (
        <div className="admin-page-container">
            <h1 className="text-center">Pet Hotel Bookings</h1>
            {getErrorModal(setVisible, visible, message)}
            {!isAdmin && (
                <div className="float-right">
                    <Button color="success" tag={Link} to="/bookings/new">New Booking</Button>
                </div>
            )}
            <Table className="mt-4">
                <thead>
                    <tr>
                        <th className="text-center">Pet</th>
                        <th className="text-center">Room</th>
                        <th className="text-center">Start Date</th>
                        <th className="text-center">End Date</th>
                        {!isAdmin && <th className="text-center">Actions</th>}
                    </tr>
                </thead>
                <tbody>{bookingsList}</tbody>
            </Table>
        </div>
    );
}