import { formValidators } from "../../../../validators/formValidators";

export const bookingEditInputs = [
    {
        tag: "Start Date",
        name: "startDate",
        type: "date",
        defaultValue: "",
        isRequired: true,
        validators: [formValidators.notEmptyValidator],
    },
    {
        tag: "End Date",
        name: "endDate",
        type: "date",
        defaultValue: "",
        isRequired: true,
        validators: [formValidators.notEmptyValidator],
    },
    {
        tag: "Pet",
        name: "pet",
        type: "select",
        values: ["None"],
        defaultValue: "None",
        isRequired: true,
        validators: [formValidators.notEmptyValidator, formValidators.notNoneTypeValidator],
    },
    {
        tag: "Room",
        name: "room",
        type: "select",
        values: ["None"],
        defaultValue: "None",
        isRequired: true,
        validators: [formValidators.notEmptyValidator, formValidators.notNoneTypeValidator],
    },
];