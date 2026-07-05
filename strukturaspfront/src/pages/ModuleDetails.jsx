import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import http from "../api/http";
import { toast } from "react-toastify";


const ModuleDetails = () => {

    const navigate = useNavigate();
    const { studyProgramId, moduleId } = useParams();

    const [module, setModule] = useState(null);
    const [subjects, setSubjects] = useState([]);
    const [moduleSubjects, setModuleSubjects] = useState([]);
    const [electiveGroups, setElectiveGroups] = useState([]);

    const [selectedGroup, setSelectedGroup] = useState(null);

    const [loading, setLoading] = useState(true);


    const loadModule = async () => {

        setLoading(true);

        try {

            const res = await http.get(`/module/${moduleId}`);

            setModule(res.data);

            setModuleSubjects(res.data.moduleSubjects || []);

        } catch (e) {

            toast.error(
                e?.response?.data?.message ||
                "Došlo je do neočekivane greške."
            );

        } finally {

            setLoading(false);

        }

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

    const loadElectiveGroups = async () => {

        try {

            const res = await http.get("/electiveGroup");

            const groups = Array.isArray(res.data)
                ? res.data.filter(
                    g => g.moduleId === Number(moduleId)
                )
                : [];

            setElectiveGroups(groups);

        } catch (e) {

            toast.error(
                e?.response?.data?.message ||
                "Došlo je do neočekivane greške."
            );

        }

    };

    const deleteModuleSubject = async (moduleSubjectId) => {

        if (!window.confirm("Da li ste sigurni da želite da uklonite predmet iz modula?")) {
            return;
        }

        try {

            await http.delete(
                `/module/subject/${moduleSubjectId}`
            );
            toast.success('Predmet je uspešno obrisan iz modula.')

            loadModule();

        } catch (e) {

            toast.error(
                e?.response?.data?.message ||
                "Došlo je do greške prilikom brisanja predmeta iz modula."
            );

        }

    };

    const deleteElectiveGroup = async (electiveGroupId) => {

        if (!window.confirm("Da li ste sigurni da želite da obrišete izbornu grupu?")) {
            return;
        }

        try {

            await http.delete(
                `/electiveGroup/${electiveGroupId}`
            );
            toast.success('Izborna grupa je uspešno obrisana.')

            loadElectiveGroups();

        } catch (e) {

            toast.error(
                e?.response?.data?.message ||
                "Došlo je do greške prilikom brisanja izborne grupe."
            );

        }

    };


    useEffect(() => {

        loadModule();

        loadSubjects();

        loadElectiveGroups();

    }, [moduleId]);


    if (loading) {

        return (

            <div className="container mt-4">

                <div className="spinner-border"></div>

            </div>

        );

    }


    const sortedModuleSubjects = [...moduleSubjects].sort((a, b) => {

        if (a.semester !== b.semester) {

            return a.semester - b.semester;

        }

        const subjectA = subjects.find(
            s => s.subjectId === a.subjectId
        )?.name || "";

        const subjectB = subjects.find(
            s => s.subjectId === b.subjectId
        )?.name || "";

        return subjectA.localeCompare(subjectB);

    });

    const requiredSubjects = sortedModuleSubjects.filter(
        ms => !ms.elective
    );

    const electiveSubjects = sortedModuleSubjects.filter(
        ms => ms.elective
    );


    const selectedSubjects = selectedGroup ? subjects.filter(subject => selectedGroup.subjectIds.includes(subject.subjectId)) : [];

    const getYear = (semester) => {

        if (semester <= 2) return 1;
        if (semester <= 4) return 2;
        if (semester <= 6) return 3;
        return 4;

    };

    const years = [1, 2, 3, 4];

    const requiredEspb = requiredSubjects.reduce((sum, ms) => {

        const subject = subjects.find(
            s => s.subjectId === ms.subjectId
        );

        return sum + (subject?.espb || 0);

    }, 0);

    const electiveGroupsWithEspb = electiveGroups.map(group => {

    const groupSubjects = subjects.filter(subject =>
        group.subjectIds.includes(subject.subjectId)
    );

    const oneSubjectEspb =groupSubjects.length > 0
            ? groupSubjects[0].espb
            : 0;

        return {

            ...group,

            espb: oneSubjectEspb * group.numberToChoose

        };

    });

    const electiveEspb = electiveGroupsWithEspb.reduce(

        (sum, group) => sum + group.espb,

        0

    );

    const totalModuleEspb =requiredEspb + electiveEspb;


    return (

        <div className="container mt-4">

            <button
                className="btn btn-outline-secondary mb-3"
                onClick={() => navigate(`/studyProgram/${studyProgramId}`)}
            >

                ← Nazad

            </button>

            <div className="card shadow">

                <div className="card-header">

                    <h2>

                        {module.name}

                    </h2>

                </div>

                <div className="card-body">

                    <h5>Opis</h5>

                    <p>

                        {module.description}

                    </p>

                </div>

            </div>

            <div className="card shadow mt-4">

                <div className="card-header d-flex flex-column flex-md-row justify-content-between align-items-md-center gap-2">

                    <h4 className="mb-0">
                        Obavezni predmeti
                    </h4>

                    <button
                        className="btn btn-primary ms-auto"
                        onClick={() =>navigate(`/studyProgram/${studyProgramId}/module/${moduleId}/subject/new`)}
                    >
                        + Dodaj predmet

                    </button>

                </div>

                <div className="card-body">

                    <div className="table-responsive">

                        <table className="table table-striped table-hover">

                            <thead className="table-dark">

                                <tr>

                                    <th>Naziv</th>

                                    <th>ESPB</th>

                                    <th>Semestar</th>

                                    <th width="150">Akcije</th>

                                </tr>

                            </thead>

                            <tbody>

                                {
                                    moduleSubjects.length === 0 ?

                                        <tr>

                                            <td
                                                colSpan="4"
                                                className="text-center"
                                            >

                                                Modul nema dodeljene predmete.

                                            </td>

                                        </tr>

                                    :

                                    years.map(year => {

                                        const yearSubjects = requiredSubjects.filter(
                                            ms => getYear(ms.semester) === year
                                        );

                                        if (yearSubjects.length === 0) {
                                            return null;
                                        }

                                        const yearEspb = yearSubjects.reduce((sum, ms) => {

                                            const subject = subjects.find(
                                                s => s.subjectId === ms.subjectId
                                            );

                                            return sum + (subject?.espb || 0);

                                        }, 0);

                                        return (

                                            <React.Fragment key={year}>

                                                <tr className="table-primary">

                                                    <td
                                                        colSpan="4"
                                                        className="fw-bold"
                                                    >

                                                        {year}. godina

                                                    </td>

                                                </tr>

                                                {

                                                    yearSubjects.map(ms => {

                                                        const subject = subjects.find(
                                                            s => s.subjectId === ms.subjectId
                                                        );

                                                        return (

                                                            <tr key={ms.moduleSubjectId}>

                                                                <td>

                                                                    {subject?.name}

                                                                </td>

                                                                <td>

                                                                    {subject?.espb}

                                                                </td>

                                                                <td>

                                                                    {ms.semester}

                                                                </td>

                                                                <td>

                                                                    <div className="d-flex flex-wrap gap-2">

                                                                        <button
                                                                            className="btn btn-warning btn-sm"
                                                                            onClick={() =>
                                                                                navigate(
                                                                                    `/studyProgram/${studyProgramId}/module/${moduleId}/subject/edit/${ms.moduleSubjectId}`,
                                                                                    {
                                                                                        state: {
                                                                                            moduleSubject: ms,
                                                                                            subject
                                                                                        }
                                                                                    }
                                                                                )
                                                                            }
                                                                        >

                                                                            <i className="bi bi-pencil"></i>

                                                                        </button>

                                                                        <button
                                                                            className="btn btn-danger btn-sm"
                                                                            onClick={() =>
                                                                                deleteModuleSubject(ms.moduleSubjectId)
                                                                            }
                                                                        >

                                                                            <i className="bi bi-trash"></i>

                                                                        </button>

                                                                    </div>

                                                                </td>

                                                            </tr>

                                                        );

                                                    })

                                                }

                                                <tr className="table-light">

                                                    <td
                                                        colSpan="4"
                                                        className="text-end fw-bold"
                                                    >

                                                        Ukupno ESPB {year}. godine: {yearEspb}

                                                    </td>

                                                </tr>

                                            </React.Fragment>

                                        );

                                    })

                                }

                                {

                                    requiredSubjects.length > 0 &&

                                    <tr className="table-success">

                                        <td
                                            colSpan="4"
                                            className="text-end fw-bold fs-5"
                                        >

                                            Ukupno ESPB obaveznih predmeta: {requiredEspb}

                                        </td>

                                    </tr>

                                }

                            </tbody>
                        </table>
                    </div>

                </div>

            </div>


            <div className="card shadow mt-4">

                <div className="card-header d-flex flex-column flex-md-row justify-content-between align-items-md-center gap-2">

                    <h4 className="mb-0">

                        Izborne grupe

                    </h4>

                    <div className="d-flex mb-3">
                        <button
                            className="btn btn-primary ms-auto"
                            onClick={() =>
                                navigate(`/studyProgram/${studyProgramId}/module/${moduleId}/electiveGroup/new`)
                            }
                        >       

                            + Dodaj izbornu grupu

                        </button>

                    </div>


                </div>

                <div className="card-body">

                    <div className="table-responsive">

                        <table className="table table-striped table-hover">

                            <thead className="table-dark">

                                <tr>

                                    <th>Naziv</th>

                                    <th>Semestar</th>

                                    <th>Broj predmeta za izbor</th>

                                    <th>Predmeti</th>

                                    <th>ESPB</th>

                                    <th width="180">Akcije</th>

                                </tr>

                            </thead>

                            <tbody>

                                {
                                    electiveGroups.length === 0 ?

                                        <tr>

                                            <td
                                                colSpan="5"
                                                className="text-center"
                                            >

                                                Modul nema izbornih grupa.

                                            </td>

                                        </tr>

                                    :

                                    years.map(year => {

                                        const yearGroups = electiveGroupsWithEspb.filter(
                                            g => getYear(g.semester) === year
                                        );

                                        if (yearGroups.length === 0) {
                                            return null;
                                        }

                                        const yearEspb = yearGroups.reduce(
                                            (sum, g) => sum + g.espb,
                                            0
                                        );

                                        return (

                                            <React.Fragment key={year}>

                                                <tr className="table-primary">

                                                    <td
                                                        colSpan="7"
                                                        className="fw-bold"
                                                    >

                                                        {year}. godina

                                                    </td>

                                                </tr>

                                                {

                                                    yearGroups.map(g => (

                                                        <tr key={g.electiveGroupId}>

                                                            <td>                                                                                                                         

                                                                {g.name}

                                                            </td>

                                                            <td>

                                                                {g.semester}

                                                            </td>

                                                            <td>

                                                                {g.numberToChoose}

                                                            </td>

                                                            <td>

                                                                {

                                                                    g.subjectIds.map(subjectId => {

                                                                        const subject = subjects.find(
                                                                            s => s.subjectId === subjectId
                                                                        );

                                                                        return (

                                                                            <div key={subjectId}>

                                                                                • {subject?.name}

                                                                            </div>

                                                                        );

                                                                    })

                                                                }

                                                            </td>

                                                            <td>

                                                                {g.espb}

                                                            </td>

                                                            <td>

                                                                <button
                                                                    className="btn btn-info btn-sm me-2"
                                                                    onClick={() => setSelectedGroup(g)}
                                                                >

                                                                    <i className="bi bi-eye"></i>

                                                                </button>

                                                                <button
                                                                    className="btn btn-warning btn-sm me-2"
                                                                    onClick={() =>
                                                                        navigate(
                                                                            `/studyProgram/${studyProgramId}/module/${moduleId}/electiveGroup/edit/${g.electiveGroupId}`
                                                                        )
                                                                    }
                                                                >

                                                                    <i className="bi bi-pencil"></i>

                                                                </button>

                                                                <button
                                                                    className="btn btn-danger btn-sm"
                                                                    onClick={() =>
                                                                        deleteElectiveGroup(g.electiveGroupId)
                                                                    }
                                                                >

                                                                    <i className="bi bi-trash"></i>

                                                                </button>

                                                            </td>

                                                        </tr>

                                                    ))

                                                }

                                                <tr className="table-light">

                                                    <td
                                                        colSpan="7"
                                                        className="text-end fw-bold"
                                                    >

                                                        Ukupno ESPB {year}. godine: {yearEspb}

                                                    </td>

                                                </tr>

                                            </React.Fragment>

                                        );

                                    })

                                }

                                {

                                    electiveGroups.length > 0 &&

                                    <tr className="table-success">

                                        <td
                                            colSpan="7"
                                            className="text-end fw-bold fs-5"
                                        >

                                            Ukupno ESPB izbornih grupa: {electiveEspb}

                                        </td>

                                    </tr>

                                }

                                </tbody>

                        </table>

                    </div>

                </div>

                <div className="card shadow mt-4">

                    <div className="card-header">

                        <h4>

                            Pregled ESPB modula

                        </h4>

                    </div>

                    <div className="card-body">

                        <div className="row text-center">

                            <div className="col-md-4">

                                <h5>

                                    Obavezni predmeti

                                </h5>

                                <h3 className="text-primary">

                                    {requiredEspb} ESPB

                                </h3>

                            </div>

                            <div className="col-md-4">

                                <h5>

                                    Izborne grupe

                                </h5>

                                <h3 className="text-warning">

                                    {electiveEspb} ESPB

                                </h3>

                            </div>

                            <div className="col-md-4">

                                <h5>

                                    Ukupno modul

                                </h5>

                                <h2 className="text-success">

                                    {totalModuleEspb} ESPB

                                </h2>

                            </div>

                        </div>

                    </div>

                </div>

                {
                    selectedGroup &&

                    <div
                        className="modal d-block"
                        tabIndex="-1"
                        style={{ backgroundColor: "rgba(0,0,0,0.5)" }}
                    >

                        <div className="modal-dialog modal-lg">

                            <div className="modal-content">

                                <div className="modal-header">

                                    <h5 className="modal-title">

                                        {selectedGroup.name}

                                    </h5>

                                    <button
                                        className="btn-close"
                                        onClick={() => setSelectedGroup(null)}
                                    ></button>

                                </div>

                                <div className="modal-body">

                                    <div className="row mb-4">

                                        <div className="col-md-6">

                                            <strong>Semestar:</strong> {selectedGroup.semester}

                                        </div>

                                        <div className="col-md-6">

                                            <strong>Student bira:</strong> {selectedGroup.numberToChoose} predmeta

                                        </div>

                                    </div>

                                    <h6 className="mb-3">

                                        Predmeti u izbornoj grupi

                                    </h6>

                                    <table className="table table-striped table-hover">

                                        <thead className="table-dark">

                                            <tr>

                                                <th>Naziv</th>

                                                <th>ESPB</th>

                                            </tr>

                                        </thead>

                                        <tbody>

                                            {
                                                selectedSubjects.length === 0 ?

                                                    <tr>

                                                        <td
                                                            colSpan="2"
                                                            className="text-center"
                                                        >

                                                            Nema predmeta u izbornoj grupi.

                                                        </td>

                                                    </tr>

                                                :

                                                    selectedSubjects.map((s) => (

                                                        <tr key={s.subjectId}>

                                                            <td>

                                                                {s.name}

                                                            </td>

                                                            <td>

                                                                {s.espb}

                                                            </td>

                                                        </tr>

                                                    ))

                                            }

                                        </tbody>

                                    </table>

                                </div>

                                <div className="modal-footer">

                                    <button
                                        className="btn btn-secondary"
                                        onClick={() => setSelectedGroup(null)}
                                    >

                                        Zatvori

                                    </button>

                                </div>

                            </div>

                        </div>

                    </div>
                }

            </div>

        </div>

    );



};



export default ModuleDetails;