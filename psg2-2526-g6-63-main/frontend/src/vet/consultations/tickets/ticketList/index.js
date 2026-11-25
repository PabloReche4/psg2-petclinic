import "../../../../static/css/owner/ticketPage.css";
import { useEffect, useRef, useState } from "react";
import {
  Button,
  ButtonGroup,
  Card,
  CardBody,
  CardText,
  CardTitle,
  Col,
  Form,
  FormGroup,
  Input,
  Row,
} from "reactstrap";
import tokenService from "../../../../services/token.service";

export default function VetConsultationTickets() {
  let [consultation, setConsultation] = useState({
    id: null,
    title: "",
    status: null,
  });

  let [tickets, setTickets] = useState([]);
  let [newTicket, setNewTicket] = useState({
    id: null,
    description: "",
  });

  let [message, setMessage] = useState(null);

  const conversationRef = useRef(null);

  const jwt = tokenService.getLocalAccessToken();
  const currentUser = tokenService.getUser();

  let pathArray = window.location.pathname.split("/");
  const id = pathArray[2];

  function sortTicketsByCreationDateAsc(list) {
    return [...list].sort(
      (a, b) => new Date(a.creationDate).getTime() - new Date(b.creationDate).getTime()
    );
  }

  function handleChange(event) {
    const value = event.target.value;
    setNewTicket({
      ...newTicket,
      description: value,
    });
  }

  async function reloadTickets() {
    const ticketsResp = await (
      await fetch(`/api/v1/consultations/${id}/tickets`, {
        headers: { Authorization: `Bearer ${jwt}` },
      })
    ).json();

    if (ticketsResp.message) setMessage(ticketsResp.message);
    else setTickets(sortTicketsByCreationDateAsc(ticketsResp));
  }

  async function handleSubmit(event) {
    event.preventDefault();

    const response = await (
      await fetch(`/api/v1/consultations/${id}/tickets`, {
        method: "POST",
        headers: {
          Authorization: `Bearer ${jwt}`,
          Accept: "application/json",
          "Content-Type": "application/json",
        },
        body: JSON.stringify(newTicket),
      })
    ).json();

    if (response.message) setMessage(response.message);
    else {
      // keep tickets sorted after adding
      setTickets((prev) => sortTicketsByCreationDateAsc([...prev, response]));
      setNewTicket({ id: null, description: "" });
    }
  }

  async function handleEditTicket(ticket) {
    const newText = window.prompt("Edit your message:", ticket.description || "");
    if (newText === null) return; // cancel
    if (newText.trim() === "") return;

    const res = await fetch(`/api/v1/consultations/${id}/tickets/${ticket.id}`, {
      method: "PUT",
      headers: {
        Authorization: `Bearer ${jwt}`,
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ description: newText }),
    });

    if (!res.ok) {
      let msg = "Could not edit the message.";
      try {
        const data = await res.json();
        if (data?.message) msg = data.message;
      } catch (_) {}
      alert(msg);
      return;
    }

    await reloadTickets();
  }

  async function handleDeleteTicket(ticketId) {
    const ok = window.confirm("Are you sure you want to delete this message?");
    if (!ok) return;

    const res = await fetch(`/api/v1/consultations/${id}/tickets/${ticketId}`, {
      method: "DELETE",
      headers: {
        Authorization: `Bearer ${jwt}`,
        Accept: "application/json",
      },
    });

    if (!res.ok) {
      // 403 often has no JSON body; handle both cases
      let msg = "You are not allowed to delete this message.";
      try {
        const data = await res.json();
        if (data?.message) msg = data.message;
      } catch (_) {}
      alert(msg);
      return;
    }

    await reloadTickets();
  }

  async function handleClose(event) {
    event.preventDefault();

    const consultationRequest = { ...consultation, status: "CLOSED" };

    const response = await (
      await fetch(`/api/v1/consultations/${id}`, {
        method: "PUT",
        headers: {
          Authorization: `Bearer ${jwt}`,
          Accept: "application/json",
          "Content-Type": "application/json",
        },
        body: JSON.stringify(consultationRequest),
      })
    ).json();

    if (response.message) setMessage(response.message);
    else window.location.href = "/consultations";
  }

  async function setUp() {
    const consultationResp = await (
      await fetch(`/api/v1/consultations/${id}`, {
        headers: { Authorization: `Bearer ${jwt}` },
      })
    ).json();

    if (consultationResp.message) setMessage(consultationResp.message);
    else setConsultation(consultationResp);

    await reloadTickets();
  }

  useEffect(() => {
    setUp();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  function getTicketList(tickets, status) {
    // tickets already sorted, but ensure it anyway
    const sorted = sortTicketsByCreationDateAsc(tickets);
    const length = sorted.length;

    return sorted.map((t, index) => {
      const isLast = index === length - 1;
      const isVetMessage = t.user?.authority?.authority === "VET";
      const isMine =
        t.user?.id === currentUser?.id ||
        t.user?.username === currentUser?.username;

      // Backend requires: last ticket + consultation open + author is current user
      const canModify = isLast && isVetMessage && isMine && status !== "CLOSED";

      const alignment = t.user?.authority?.authority === "OWNER" ? "me-auto" : "ms-auto";
      const style =
        t.user?.authority?.authority === "OWNER"
          ? { maxWidth: "80%", backgroundColor: "#88FFFF" }
          : { maxWidth: "80%", backgroundColor: "#A6FF7D", alignSelf: "end" };

      return (
        <div key={t.id}>
          <Card mb="3" className={alignment} style={style}>
            <Row className="no-gutters">
              <Col md="8">
                <CardBody>
                  <CardTitle>
                    {t.user.username}-&gt; {t.description}
                  </CardTitle>
                  <CardText>
                    <small className="text-muted">
                      {new Date(t.creationDate).toLocaleString()}
                    </small>
                  </CardText>

                  {canModify && (
                    <ButtonGroup>
                      <Button
                        size="sm"
                        color="primary"
                        type="button"
                        onClick={() => handleEditTicket(t)}
                      >
                        Edit
                      </Button>
                      <Button
                        size="sm"
                        color="danger"
                        type="button"
                        onClick={() => handleDeleteTicket(t.id)}
                      >
                        Delete
                      </Button>
                    </ButtonGroup>
                  )}
                </CardBody>
              </Col>
            </Row>
          </Card>
          <br />
        </div>
      );
    });
  }

  function getTicketInput(newTicket, status) {
    if (status !== "CLOSED")
      return (
        <div className="ticket-input-div">
          <Form onSubmit={handleSubmit}>
            <FormGroup>
              <Input
                type="textarea"
                required
                name="description"
                id="description"
                value={newTicket.description || ""}
                onChange={handleChange}
              />
            </FormGroup>
            <FormGroup>
              <Button color="primary" type="submit">
                Save
              </Button>{" "}
            </FormGroup>
          </Form>
        </div>
      );
    return <></>;
  }

  useEffect(() => {
    if (conversationRef.current) {
      conversationRef.current.scrollTop = conversationRef.current.scrollHeight;
    }
  }, [tickets, newTicket]);

  if (message) return <h2 className="text-center">{message}</h2>;

  return (
    <div className="ticket-page">
      <h2 className="text-center">Consultation Number {consultation.id}</h2>
      <h3>{consultation.title}</h3>

      {consultation.status !== "CLOSED" && (
        <Button color="danger" onClick={handleClose} style={{ marginBottom: "50px" }}>
          Close Consultation
        </Button>
      )}

      <div className="conversation-container" ref={conversationRef} style={{ maxHeight: "65vh" }}>
        {getTicketList(tickets, consultation.status)}
      </div>

      {getTicketInput(newTicket, consultation.status)}
    </div>
  );
}