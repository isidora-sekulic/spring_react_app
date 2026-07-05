import React, { useState,useEffect } from "react";
import { useNavigate,useParams } from "react-router-dom";
import http from "../api/http";
import { toast } from "react-toastify";

const StudyProgramForm = () => {

    const navigate = useNavigate();
    const { id } = useParams();

    const [formData, setFormData] = useState({
        name: "",
        description: "",
        durationYears: 3,
        totalEspb: 180,
        userId: null
    });

    const handleChange = (e) => {

        const { name, value } = e.target;

        if (name === "durationYears") {

            setFormData((prev) => ({
                ...prev,
                durationYears: Number(value),
                totalEspb: Number(value) === 3 ? 180 : 240
            }));

            return;
        }


        setFormData((prev) => ({
            ...prev,
            [name]: value
        }));

    };


    useEffect(() => {


        if (!id) {
            return;
        }

        http.get(`/studyProgram/${id}`)
            .then((res) => {

                setFormData(res.data);

            })
            .catch((e) => {

                toast.error(
                e?.response?.data?.message ||
                "Došlo je do neočekivane greške."
                );

            });

    }, [id]);


    const handleSubmit = async (e) => {

        e.preventDefault();
        
        if (id){
            try {

           
                await http.put(`/studyProgram/${id}`, formData);
                toast.success("Studijski program je uspešno izmenjen.");
                

                navigate("/studyProgram");

            } catch (e) {

                toast.error(
                    e?.response?.data?.message ||
                    "Došlo je do greške prilikom izmene studijskog programa."
                    );

            }
        }
        

        if (!id){

            try {

                await http.post("/studyProgram", formData);
                toast.success("Studijski program je uspešno kreiran.");

                navigate("/studyProgram");

            } catch (e) {

                toast.error(
                    e?.response?.data?.message ||
                    "Došlo je do greške prilikom dodavanja studijskog programa."
                    );

            }

        }
        


    };

    return (

        <div className="container mt-4">

            <button
                className="btn btn-outline-secondary mb-3"
                onClick={() =>
                    navigate(`/studyProgram`)
                }
            >

                <i className="bi bi-arrow-left me-2"></i>

                Nazad

            </button>

            <div className="card shadow-lg border-0">

                <div className="card-header bg-primary text-white">
                    <h3>
                        {id ? "Izmena studijskog programa" : "Dodavanje studijskog programa"}
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

                        <div className="row">

                            <div className="col-md-6">

                                <div className="mb-3">

                                    <label className="form-label">
                                        Trajanje studija
                                    </label>

                                    <select
                                        className="form-select"
                                        name="durationYears"
                                        value={formData.durationYears}
                                        onChange={handleChange}
                                    >
                                        <option value={3}>3 godine</option>

                                        <option value={4}>4 godine</option>

                                    </select>

                                </div>

                            </div>

                            <div className="col-md-6">

                                <div className="mb-3">

                                    <label className="form-label">
                                        Ukupan ESPB
                                    </label>

                                    <input
                                        className="form-control"                                       
                                        value={formData.totalEspb}
                                        disabled
                                    
                                    />

                                </div>

                            </div>

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
                                onClick={() => navigate("/studyProgram")}
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

export default StudyProgramForm;