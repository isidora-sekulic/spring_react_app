import React, { useState,useEffect } from "react";
import { useNavigate, useParams, useLocation } from "react-router-dom";
import http from "../api/http";
import { toast } from "react-toastify";


const ModuleSubjectForm = () => {

    const navigate = useNavigate();

    const { studyProgramId, moduleId, moduleSubjectId } = useParams();

    const location=useLocation();

    const editData=location.state?.moduleSubject;
    const selectedSubject=location.state?.subject;

    const [subjectName, setSubjectName] = useState("");

    const [subjects, setSubjects] = useState([]);

    const [formData, setFormData] = useState({ moduleId: Number(moduleId), subjectId: "", semester: "" ,elective:false});


    const handleChange = (e) => {

        const { name, value, type, checked } = e.target;

        setFormData((prev) => ({

            ...prev,

            [name]:
                type === "checkbox"
                    ? checked
                    : Number(value)

        }));

    };

    const loadSubjects = async () => {

        try {

            const res = await http.get("/subject");

            setSubjects(res.data);

        } catch (e) {

            toast.error(
                e?.response?.data?.message ||
                "Došlo je do neočekivane greške."
                );

        }

    };

    useEffect(() => {

        loadSubjects();

        if (moduleSubjectId && editData) {

            setFormData({
            moduleId: Number(moduleId),
            subjectId: editData.subjectId,
            semester: editData.semester,
            elective:editData.elective
        });

        setSubjectName(selectedSubject?.name || "");

        }

    }, [moduleSubjectId, editData, selectedSubject, moduleId]);


    const handleSubmit = async (e) => {

        e.preventDefault();

        try {

            if (moduleSubjectId) {

                await http.put(
                    `/module/subject/${moduleSubjectId}`,
                    formData
                );
                toast.success('Predmet iz modula je uspešno izmenjen.')

            } else {

                await http.post(
                    `/module/${moduleId}/subject`,
                    formData
                );
                toast.success('Predmet iz modula je uspešno kreiran.')

            }

            navigate(
                `/studyProgram/${studyProgramId}/module/${moduleId}`
            );

        } catch (e) {

           toast.error(
                e?.response?.data?.message ||
                "Došlo je do greške prilikom izmene/dodavanja predmeta u modul."
                );

        }

    };

    return (

        <div className="container mt-5">

            <button
                className="btn btn-outline-secondary mb-3"
                onClick={() =>
                    navigate(`/studyProgram/${studyProgramId}/module/${moduleId}`)
                }
            >

                <i className="bi bi-arrow-left me-2"></i>

                Nazad

            </button>

            <div className="card shadow-lg border-0">

                <div className="card-header bg-primary text-white">

                    <h3 className="mb-0">

                        {moduleSubjectId ? "Izmena predmeta u modulu" : "Dodavanje predmeta u modul"}

                    </h3>

                </div>

                <div className="card-body">

                    <form onSubmit={handleSubmit}>

                        <div className="mb-4">

                            <label className="form-label">

                                Predmet

                            </label>

                            {
                                moduleSubjectId ?

                                <input
                                    className="form-control"
                                    value={subjectName}
                                    disabled
                                />

                                :

                                <select
                                    className="form-select"
                                    name="subjectId"
                                    value={formData.subjectId}
                                    onChange={handleChange}
                                    required
                                >

                                    <option value="" disabled hidden></option>

                                    {
                                        subjects.map((s) => (

                                            <option
                                                key={s.subjectId}
                                                value={s.subjectId}
                                            >
                                                {s.name}
                                            </option>

                                        ))
                                    }

                                </select>
                            }

                        </div>

                        <div className="mb-4">

                            <label className="form-label">

                                Semestar

                            </label>

                            <select
                                className="form-select"
                                name="semester"
                                value={formData.semester}
                                onChange={handleChange}
                                required
                            >

                                <option value="" disabled hidden></option>

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
                        <div className="form-check mb-4">

                            <input
                                className="form-check-input"
                                type="checkbox"
                                name="elective"
                                checked={formData.elective}
                                onChange={handleChange}
                                id="elective"
                            />

                            <label
                                className="form-check-label"
                                htmlFor="elective"
                            >

                                Izborni predmet

                            </label>

                        </div>

                        <div className="d-flex justify-content-end">

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

export default ModuleSubjectForm;