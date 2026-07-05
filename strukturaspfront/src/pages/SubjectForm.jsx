import React, { useState,useEffect } from "react";
import { useNavigate,useParams } from "react-router-dom";
import http from "../api/http";
import { toast } from "react-toastify";

const SubjectForm = () => {

    const navigate = useNavigate();
    const { id } = useParams();

    const [formData, setFormData] = useState({

        name: "",
        description: "",
        espb: 6,
        lectures: 0,
        exercises: 0,
        laboratoryExercises: 0,
        researchWork: 0,
        otherTeaching: 0
    });

    const handleChange = (e) => {

        const { name, value } = e.target;

        setFormData((prev) => ({

            ...prev,

            [name]: name === "name" || name === "description"
                ? value
                : Number(value)

        }));

    };


    useEffect(() => {

        const loadSubject = async () => {

            if (!id) return;

            try {

                const res = await http.get(`/subject/${id}`);

                setFormData(res.data);

            } catch (e) {

                toast.error(
                e?.response?.data?.message ||
                "Došlo je do neočekivane greške."
                );

            }

        };

        loadSubject();

    }, [id]);


    const handleSubmit = async (e) => {

        e.preventDefault();
        

        try {

           if (id) {
                await http.put(`/subject/${id}`, formData);
                toast.success('Predmet je uspešno izmenjen.')


            } else {
                await http.post("/subject", formData);
                toast.success('Predmet je uspešno kreiran.')

            }

            navigate("/subject");

        } catch (e) {

            toast.error(
                e?.response?.data?.message ||
                "Došlo je do greške prilikom kreiranja/izmene predmeta."
            );

        }

    };

    return (

        <div className="container mt-4">

            <button
                className="btn btn-outline-secondary mb-3"
                onClick={() =>
                    navigate(`/subject`)
                }
            >

                <i className="bi bi-arrow-left me-2"></i>

                Nazad

            </button>

            <div className="card shadow-lg border-0">

                <div className="card-header bg-primary text-white">
                    <h3>
                        {id ? "Izmena predmeta" : "Dodavanje predmeta"}
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

                        <div className="mb-4">

                            <label className="form-label">
                                Opis
                            </label>

                            <textarea
                                className="form-control"
                                rows="4"
                                name="description"
                                value={formData.description}
                                onChange={handleChange}
                                required
                            />
                        </div>

                        <div className="mb-4">

                            <label className="form-label">ESPB</label>

                            <input
                                type="number"
                                min="1"
                                max="30"
                                className="form-control"
                                name="espb"
                                value={formData.espb}
                                onChange={handleChange}
                                required
                            />

                        </div>

                        <h5 className="mb-4">

                            <i className="bi bi-clock-history me-2"></i>

                            Fond časova

                        </h5>

                        <div className="row">

                            <div className="col-md-6">

                                <div className="mb-3">

                                    <label className="form-label">
                                        Predavanja
                                    </label>

                                    <input
                                        type="number"
                                        min="0"
                                        className="form-control"
                                        name="lectures"
                                        value={formData.lectures}
                                        onChange={handleChange}
                                        required
                                    />

                                </div>

                            </div>

                            <div className="col-md-6">

                                <div className="mb-3">

                                    <label className="form-label">
                                        Vežbe
                                    </label>

                                    <input
                                        type="number"
                                        min="0"
                                        className="form-control"
                                        name="exercises"
                                        value={formData.exercises}
                                        onChange={handleChange}
                                        required
                                    />

                                </div>

                            </div>

                        </div>

                        <div className="row">

                            <div className="col-md-6">

                                <div className="mb-3">

                                    <label className="form-label">

                                        Laboratorijske vežbe

                                    </label>

                                    <input
                                        type="number"
                                        min="0"
                                        className="form-control"
                                        name="laboratoryExercises"
                                        value={formData.laboratoryExercises}
                                        onChange={handleChange}
                                        required
                                    />

                                </div>

                            </div>

                            <div className="col-md-6">

                                <div className="mb-3">

                                    <label className="form-label">

                                        Istraživački rad

                                    </label>

                                    <input
                                        type="number"
                                        min="0"
                                        className="form-control"
                                        name="researchWork"
                                        value={formData.researchWork}
                                        onChange={handleChange}
                                        required
                                    />

                                </div>

                            </div>

                        </div>

                        <div className="mb-4">

                            <label className="form-label">

                                Drugi oblici nastave

                            </label>

                            <input
                                type="number"
                                min="0"
                                className="form-control"
                                name="otherTeaching"
                                value={formData.otherTeaching}
                                onChange={handleChange}
                                required
                            />

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
                                onClick={() => navigate("/subject")}
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

export default SubjectForm;