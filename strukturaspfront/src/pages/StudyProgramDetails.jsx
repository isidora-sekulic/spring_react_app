import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import http from "../api/http";
import { toast } from "react-toastify";
import { exportStudyProgramPdf } from "../utils/pdfExport";



const StudyProgramDetails = () => {

    const navigate = useNavigate();
    const { id } = useParams();

    const [loading, setLoading] = useState(true);

    const [studyProgram, setStudyProgram] = useState(null);

    const [modules, setModules] = useState([]);


    const loadStudyProgram = async () => {

        setLoading(true);

        try {

            const res = await http.get(`/studyProgram/${id}`);

            setStudyProgram(res.data);

        } catch (e) {

            toast.error(
                e?.response?.data?.message ||
                "Došlo je do neočekivane greške."
            );

        } finally {

            setLoading(false);

        }

    };
    
    const loadModules = async () => {

        try {

            const res = await http.get("/module");

            console.log("Svi moduli:", res.data);

            const modulesForProgram = res.data.filter(

                (m) => m.studyProgramId === Number(id)

            );

            console.log("ID programa:", id);
            console.log("Filtrirani moduli:", modulesForProgram);

            setModules(modulesForProgram);

        } catch (e) {

            toast.error(
                e?.response?.data?.message ||
                "Došlo je do neočekivane greške."
            );

        }

    };

    const deleteModule = async (moduleId) => {

        if (!window.confirm("Da li ste sigurni da želite da obrišete modul?")) {
            return;
        }

        try {

            await http.delete(`/module/${moduleId}`);
            toast.success("Modul je uspešno obrisan.")

            loadModules();

        } catch (e) {

            toast.error(
                e?.response?.data?.message ||
                "Došlo je do greške prilikom brisanja modula."
            );

        }

    };

    const exportPdf = async () => {

        try {

            const res = await http.get(`/studyProgram/${id}/report`);

            exportStudyProgramPdf(res.data);

        } catch (e) {       

            toast.error(
                e?.response?.data?.message ||
                "Došlo je do greške pri generisanju PDF-a."
            );

        }

    };

    useEffect(() => {

        loadStudyProgram();
        loadModules();
    }, [id]);


    if (loading) {

        return (
            <div className="container mt-4">
                <div className="spinner-border"></div>
            </div>
        );
    }

    

    return (

        <div className="container mt-4">

            <div className="d-flex align-items-center mb-3">

                <button
                    className="btn btn-outline-secondary"
                    onClick={() => navigate("/studyProgram")}
                >
                    ← Nazad
                </button>

                <button
                    className="btn btn-danger ms-auto"
                    onClick={exportPdf}
                >
                    <i className="bi bi-file-earmark-pdf me-2"></i>
                    Izvezi u PDF
                </button>

            </div>

            <div className="card shadow">

                <div className="card-header">

                    <h2>{studyProgram.name}</h2>

                </div>

                <div className="card-body">

                    <h5>Opis</h5>

                    <p>

                        {studyProgram.description}

                    </p>

                    <div className="row mt-4">

                        <div className="col-md-3">

                            <strong>Trajanje:</strong>

                        </div>

                        <div className="col-md-9">

                            {studyProgram.durationYears} godine

                        </div>

                    </div>

                    <div className="row">

                        <div className="col-md-3">

                            <strong>Ukupan ESPB:</strong>

                        </div>

                        <div className="col-md-9">

                            {studyProgram.totalEspb}

                        </div>

                    </div>

                </div>

            </div>

            <div className="card shadow mt-4">

                <div className="card-header d-flex flex-column flex-md-row justify-content-between align-items-md-center gap-2">

                    <h4 className="mb-0">Moduli</h4>

                    <button className="btn btn-primary"

                        onClick={() =>
                            navigate(`/studyProgram/${id}/module/new`)
                        }
                    >                     

                            + Dodaj modul

                    </button>

                </div>

                <div className="card-body">

                    <div className="table-responsive">

                        <table className="table table-striped table-hover">

                            <thead className="table-dark">

                                <tr>

                                    <th>Naziv</th>

                                    <th>Opis</th>

                                    <th width="220">
                                        Akcije
                                    </th>

                                </tr>

                            </thead>

                            <tbody>

                                {
                                    modules.length === 0 ?

                                        <tr>

                                            <td
                                                colSpan="3"
                                                className="text-center"
                                            >
                                                Nema modula.
                                            </td>

                                        </tr>

                                    :

                                    modules.map((m) => (

                                        <tr key={m.moduleId}>

                                            <td>{m.name}</td>

                                            <td>{m.description}</td>

                                            <td>
                                                <div className="d-flex flex-wrap gap-2">

                                                    <button
                                                        className="btn btn-info btn-sm me-2"
                                                        onClick={() =>
                                                            navigate(`/studyProgram/${id}/module/${m.moduleId}`)
                                                        }
                                                    >
                                                        <i className="bi bi-eye"></i>
                                                    </button>

                                                    <button
                                                        className="btn btn-warning btn-sm me-2"
                                                        onClick={() =>
                                                            navigate(`/studyProgram/${id}/module/edit/${m.moduleId}`)
                                                        }
                                                    >
                                                        <i className="bi bi-pencil"></i>
                                                    </button>

                                                    <button
                                                        className="btn btn-danger btn-sm"
                                                        onClick={() => deleteModule(m.moduleId)}
                                                    >

                                                        <i className="bi bi-trash"></i>

                                                    </button>

                                                </div>

                                            </td>

                                        </tr>

                                    ))

                                }

                            </tbody>

                        </table>
                    </div>

                </div>

            </div>

        </div>

    );

};

export default StudyProgramDetails;