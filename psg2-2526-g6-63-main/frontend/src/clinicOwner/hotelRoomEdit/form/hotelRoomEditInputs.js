import { formValidators } from "../../../validators/formValidators";

export const hotelRoomEditInputs = [
    {
        tag: "Name",
        name: "name",
        type: "text",
        defaultValue: "",
        isRequired: true,
        validators: [formValidators.notEmptyValidator],
    },
    {
        tag: "Size (m²)",
        name: "size",
        type: "number",
        defaultValue: "",
        isRequired: true,
        validators: [formValidators.notEmptyValidator],
    },
    {
        tag: "Allowed Pet Type",
        name: "allowedPetType",
        type: "select",
        values: ["None"], 
        defaultValue: "None",
        isRequired: true,
        validators: [formValidators.notEmptyValidator, formValidators.notNoneTypeValidator],
    },
    {
        tag: "Associated Clinic",
        name: "clinic",
        type: "select",
        values: ["None"], 
        defaultValue: "None",
        isRequired: true,
        validators: [formValidators.notEmptyValidator, formValidators.notNoneTypeValidator],
    },
];