import React, { useState,useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import http from "../api/http";
import { toast } from "react-toastify";


    const ModuleForm = () => {

        const navigate = useNavigate();
        const { studyProgramId, moduleId } = useParams();

        const [formData, setFormData] = useState({
            name: "",
            description: ""
    });

    const handleChange = (e) => {

        const { name, value } = e.target;

        setFormData((prev) => ({
            ...prev,
            [name]: value
        }));

    };

    const handleSubmit = async (e) => {

        e.preventDefault();

        try {

            if (moduleId) {

                await http.put(`/module/${moduleId}`, {
                    ...formData,
                    studyProgramId: Number(studyProgramId)
                });
                toast.success('Modul je uspešno izmenjen.')

            } else {

                await http.post("/module", {
                    ...formData,
                    studyProgramId: Number(studyProgramId)
                });
                toast.success('Modul je uspešno kreiran.')

            }

            navigate(`/studyProgram/${studyProgramId}`);

        } catch (e) {

            toast.error(
                e?.response?.data?.message ||
                "Došlo je greške prilikom dodavanja/izmene modula."
            );

        }

    };

    useEffect(() => {

        if (!moduleId) {
            return;
        }

        http.get(`/module/${moduleId}`)
            .then((res) => {

                setFormData(res.data);

            })
            .catch((e) => {

                toast.error(
                    e?.response?.data?.message ||
                    "Došlo je do neočekivane greške."
                );

            });

    }, [moduleId]);

    return (

        <div className="container mt-4">

            <button
                className="btn btn-outline-secondary mb-3"
                onClick={() =>
                    navigate(`/studyProgram/${studyProgramId}`)
                }
            >

                <i className="bi bi-arrow-left me-2"></i>

                Nazad

            </button>

            <div className="card shadow-lg border-0">

                <div className="card-header bg-primary text-white">

                    <h3>{moduleId ? "Izmena modula" : "Dodavanje modula"}</h3>

                </div>

                <div className="card-body">

                    <form onSubmit={handleSubmit}>

                        <div className="mb-3">

                            <label className="form-label">

                                Naziv

                            </label>

                            <input
                                className="form-control"
                                name="name"
                                value={formData.name}
                                onChange={handleChange}
                                required
                            />

                        </div>

                        <div className="mb-3">

                            <label className="form-label">

                                Opis

                            </label>

                            <textarea
                                className="form-control"
                                rows="4"
                                name="description"
                                value={formData.description}
                                onChange={handleChange}
                            />

                        </div>

                        <div className="text-end">

                            <button
                                type="submit"
                                className="btn btn-success me-2"
                            >

                                Sačuvaj

                            </button>

                            <button
                                type="button"
                                className="btn btn-secondary"
                                onClick={() => navigate(`/studyProgram/${studyProgramId}`)}
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

export default ModuleForm;