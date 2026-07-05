import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import http from "../api/http";
import { toast } from "react-toastify";


const ElectiveGroupForm = () => {

    const navigate = useNavigate();

    const { studyProgramId, moduleId, electiveGroupId } = useParams();

    const [subjects, setSubjects] = useState([]);
    const [formData, setFormData] = useState({

        moduleId: Number(moduleId),

        name: "",

        semester: 1,

        numberToChoose: 1,

        subjectIds: []

    });  

    const handleChange = (e) => {

        const { name, value } = e.target;

        setFormData((prev) => ({

            ...prev,

            [name]:
                name === "name"
                    ? value
                    : Number(value)

        }));

    };

    const loadSubjects = async () => {

        try {           

            const moduleRes = await http.get(`/module/${moduleId}`);

            const subjectRes = await http.get("/subject");

            const electiveModuleSubjects = (moduleRes.data.moduleSubjects || [])
                .filter(ms => ms.elective);

            const allSubjects = Array.isArray(subjectRes.data)
                ? subjectRes.data
                : [];

            const electiveSubjects = allSubjects.filter(subject =>
                electiveModuleSubjects.some(
                    ms => ms.subjectId === subject.subjectId
                )
            );

            setSubjects(electiveSubjects);

        } catch (e) {

           toast.error(
                e?.response?.data?.message ||
                "Došlo je do neočekivane greške."
            );

        }

    };

    const loadElectiveGroup = async () => {

        if (!electiveGroupId) {
            return;
        }

        try {

            const res = await http.get(
                `/electiveGroup/${electiveGroupId}`
            );

            setFormData(res.data);

        } catch (e) {

            toast.error(
                e?.response?.data?.message ||
                "Došlo je do neočekivane greške."
            );
        }

    };

    const toggleSubject = (subjectId) => {

        setFormData((prev) => ({

            ...prev,

            subjectIds: prev.subjectIds.includes(subjectId)

                ? prev.subjectIds.filter(id => id !== subjectId)

                : [...prev.subjectIds, subjectId]

        }));

    };

    useEffect(() => {

        loadSubjects();

        loadElectiveGroup();

    }, [moduleId]);


    const handleSubmit = async (e) => {

        e.preventDefault();

        try {

            if (electiveGroupId) {

                await http.put(
                    `/electiveGroup/${electiveGroupId}`,
                    formData
                );
                toast.success('Izborna grupa je uspešno izmenjena.')

            } else {

                await http.post(
                    "/electiveGroup",
                    formData
                );
                toast.success('Izborna grupa je uspešno kreirana.')

            }

            navigate(
                `/studyProgram/${studyProgramId}/module/${moduleId}`
            );

        } catch (e) {

             toast.error(
                e?.response?.data?.message ||
                "Došlo je do greške prilikom kreiranja/izmene izborne grupe."
                );

        }

    };


    return (

        <div className="container mt-4">

            <button
                className="btn btn-outline-secondary mb-3"
                onClick={() =>
                    navigate(`/studyProgram/${studyProgramId}/module/${moduleId}`)
                }
            >

                <i className="bi bi-arrow-left me-2"></i>

                Nazad

            </button>

            <div className="card shadow">

                <div className="card-header">

                    <h3>

                        {
                            electiveGroupId
                                ? "Izmena izborne grupe"
                                : "Dodavanje izborne grupe"
                        }

                    </h3>
                   

                </div>

                <div className="card-body">

                    <form onSubmit={handleSubmit}>

                        <div className="mb-3">

                            <label className="form-label">

                                Naziv

                            </label>

                            <input
                                type="text"
                                className="form-control"
                                name="name"
                                value={formData.name}
                                onChange={handleChange}
                                required
                            />

                        </div>

                        <div className="row">

                            <div className="col-md-6">

                                <div className="mb-3">

                                    <label className="form-label">

                                        Semestar

                                    </label>

                                    <select
                                        className="form-select"
                                        name="semester"
                                        value={formData.semester}
                                        onChange={handleChange}
                                    >

                                        {
                                            [1,2,3,4,5,6,7,8].map((s) => (

                                                <option
                                                    key={s}
                                                    value={s}
                                                >

                                                    {s}

                                                </option>

                                            ))
                                        }

                                    </select>

                                </div>

                            </div>

                            <div className="col-md-6">

                                <div className="mb-3">

                                    <label className="form-label">

                                        Broj predmeta za izbor

                                    </label>

                                    <input
                                        type="number"
                                        min="1"
                                        className="form-control"
                                        name="numberToChoose"
                                        value={formData.numberToChoose}
                                        onChange={handleChange}
                                        required
                                    />

                                </div>

                            </div>

                        </div>

                        <hr />

                        <h5 className="mb-3">

                            Izborni predmeti

                        </h5>

                        {

                            subjects.length === 0 ?

                                <div className="alert alert-warning">

                                    Modul nema izbornih predmeta.

                                </div>

                            :

                                <div className="row">

                                    {

                                        subjects.map((s) => (

                                            <div
                                                className="col-md-6"
                                                key={s.subjectId}
                                            >

                                                <div className="form-check mb-2">

                                                    <input
                                                        className="form-check-input"
                                                        type="checkbox"
                                                        id={`subject-${s.subjectId}`}
                                                        checked={formData.subjectIds.includes(s.subjectId)}
                                                        onChange={() => toggleSubject(s.subjectId)}
                                                    />

                                                    <label
                                                        className="form-check-label"
                                                        htmlFor={`subject-${s.subjectId}`}
                                                    >

                                                        {s.name}

                                                    </label>

                                                </div>

                                            </div>

                                        ))

                                    }

                                </div>

                        }

                        <div className="d-flex justify-content-end mt-4">

                            <button
                                type="submit"
                                className="btn btn-success me-2"
                            >

                                Sačuvaj

                            </button>

                            <button
                                type="button"
                                className="btn btn-secondary"
                                onClick={() =>
                                    navigate(`/studyProgram/${studyProgramId}/module/${moduleId}`)
                                }
                            >

                                Otkaži

                            </button>

                        </div>

                    </form>

                </div>

            </div>

        </div>

    );

};

export default ElectiveGroupForm;