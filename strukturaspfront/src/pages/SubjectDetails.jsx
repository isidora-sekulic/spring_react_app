import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import http from "../api/http";
import { toast } from "react-toastify";


const SubjectDetails = () => {

    const navigate = useNavigate();
    const { id } = useParams();

    const [loading, setLoading] = useState(true);
    const [subject, setSubject] = useState(null);


    const loadSubject = async () => {

        setLoading(true);

        try {

            const res = await http.get(`/subject/${id}`);

            setSubject(res.data);

        } catch (e) {

            toast.error(
                e?.response?.data?.message ||
                "Došlo je do neočekivane greške."
            );

        } finally {

            setLoading(false);

        }

    }; 
    

    useEffect(() => {

        loadSubject();
       
    }, [id]);


    if (loading) {

        return (
            <div className="container mt-4">
                <div className="spinner-border"></div>
            </div>
        );
    } 

    return (

        <div className="container  mt-5 mb-5">

            <button
                className="btn btn-outline-secondary mb-3"
                onClick={() => navigate("/subject")}
            >
                ← Nazad
            </button>

            <div className="card shadow-lg border-0">

                <div className="card-header d-flex justify-content-between align-items-center">

                    <h2 className="mb-0">

                        <i className="bi bi-journal-bookmark me-2"></i>

                        {subject.name}

                    </h2>

                    <span className="badge bg-primary fs-6">

                        {subject.espb} ESPB

                    </span>

                </div>

                <div className="card-body">

                    <h5 className="mb-3">

                        Opis

                    </h5>

                    <p className="mb-5">

                        {subject.description}

                    </p>

                    <hr />

                    <h5 className="mt-4 mb-4">

                        <i className="bi bi-clock-history me-2"></i>

                        Fond časova

                    </h5>

                    <div className="row mt-4">

                        <div className="col-md-5">

                            <strong>ESPB:</strong>

                        </div>

                        <div className="col-md-7">

                            {subject.espb}

                        </div>

                    </div>

                    <div className="row mb-2">

                        <div className="col-md-5">

                            <strong>Predavanja:</strong>

                        </div>

                        <div className="col-md-7">

                            {subject.lectures}

                        </div>

                    </div>

                    <div className="row mb-2">

                        <div className="col-md-5">

                            <strong>Vežbe:</strong>

                        </div>

                        <div className="col-md-7">

                            {subject.exercises}

                        </div>

                    </div>

                    <div className="row mb-2">

                        <div className="col-md-5">

                            <strong>Laboratorijske vežbe:</strong>

                        </div>

                        <div className="col-md-7">

                            {subject.laboratoryExercises}

                        </div>

                    </div>

                    <div className="row mb-2">

                        <div className="col-md-5">

                            <strong>Istraživački rad:</strong>

                        </div>

                        <div className="col-md-7">

                            {subject.researchWork}

                        </div>

                    </div>

                    <div className="row mb-2">

                        <div className="col-md-5">

                            <strong>Drugi oblici nastave:</strong>

                        </div>

                        <div className="col-md-7">

                            {subject.otherTeaching}

                        </div>

                    </div>

                </div>

            </div>

        </div>

    );

};

export default SubjectDetails;