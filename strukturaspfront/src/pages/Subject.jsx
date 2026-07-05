import React,{useState,useEffect} from 'react'
import { useNavigate } from "react-router-dom";
import http from "../api/http"; 
import { toast } from "react-toastify";


export const Subject = () => {

    const [subjects, setSubjects]= useState([]);
    const [loading, setLoading]= useState(true);
    const [search, setSearch] = useState("");
    const [sort, setSort] = useState({by: "name",dir: "asc"});

    const navigate = useNavigate();

    function toggleSort(col) {
        setSort((s) =>
            s.by === col? { by: col, dir: s.dir === "asc" ? "desc" : "asc"}: {by: col, dir: "asc"}
        );

    }   

    const loadSubjects = async () => {

        setLoading(true);
        setSearch("");

        try {

            const res = await http.get("/subject");

            setSubjects(
                Array.isArray(res.data) ? res.data : []
            );

        } catch (e) {

            toast.error(
                e?.response?.data?.message ||
                "Došlo je do neočekivane greške."
            );
        } finally {

            setLoading(false);

        }

};

    const deleteSubject = async (id) => {

        if (!window.confirm("Da li ste sigurni da želite da obrišete predmet?")) {
            return;
        }

        try {

            await http.delete(`/subject/${id}`);
            toast.success('Predmet je uspešno obrisan.')

            loadSubjects();

        } catch (e) {
            toast.error(
                e?.response?.data?.message ||
                "Došlo je do greške prilikom brisanja predmeta."
            );
        }

    };

    useEffect(() => {
        loadSubjects();
    }, []);

    let filteredSubjects = subjects.filter((s) => {

        const value = search.toLowerCase();

        return (
            s.name.toLowerCase().includes(value)          
        );

    });

    filteredSubjects.sort((a, b) => {

        let first = a[sort.by];
        let second = b[sort.by];

        if (typeof first === "string") {
            first = first.toLowerCase();
            second = second.toLowerCase();
        }

        if (first < second) {
            return sort.dir === "asc" ? -1 : 1;
        }

        if (first > second) {
            return sort.dir === "asc" ? 1 : -1;
        }

        return 0;

    });

    return (

        <div className="container mt-4">
            <div className="d-flex justify-content-between align-items-center mb-4">

                <div>
                    <h2>Predmeti</h2>
                    <p className="text-muted">
                        Pregled svih predmeta
                    </p>
                </div>

            </div>
            <div className="row mb-3">

                    <input
                        className="form-control mb-3"
                        placeholder="Pretraži po imenu"
                        value={search}
                        onChange={(e) => setSearch(e.target.value)}
                    />                

                    <div className="d-flex align-items-center mb-3">
                        <button
                            className="btn btn-outline-secondary"
                            onClick={loadSubjects}
                        >
                            Osveži
                        </button>

                        <button
                            className="btn btn-primary ms-auto"
                            onClick={() => navigate("/subject/new")}
                        >
                            + Dodavanje novog predmeta
                        </button>

                    </div>

                

            </div>   


            {
                loading &&
                <div className="text-center">

                    <div className="spinner-border"/>

                </div>
            }

            {
                !loading &&

                <div className="table-responsive">

                    <table className="table table-striped table-hover align-middle">

                        <thead className="table-dark">

                            <tr>

                                <th
                                    style={{ cursor: "pointer" }}
                                    onClick={() => toggleSort("name")}
                                >
                                    Naziv{" "}
                                    {sort.by==="name" && (sort.dir==="asc" ? "▲" : "▼")}
                                </th>                            

                                <th
                                    style={{ cursor: "pointer" }}
                                    onClick={() => toggleSort("espb")}
                                >
                                    ESPB{" "}
                                    {sort.by==="espb" && (sort.dir==="asc" ? "▲" : "▼")}
                                </th>

                                <th className="text-center">
                                    Akcije
                                </th>

                            </tr>

                        </thead>

                        <tbody>

                            {filteredSubjects.length === 0 &&

                                <tr>

                                    <td
                                        colSpan="5"
                                        className="text-center"
                                    >
                                        Nisu pronađeni predmeti po kriterijumu.
                                    </td>

                                </tr>

                            }

                            {filteredSubjects.map((s) => (

                                <tr
                                    key={s.subjectId}
                                >

                                    <td>

                                        {s.name}

                                    </td>                       

                                    <td>

                                        {s.espb}

                                    </td>

                                    <td>

                                        <div className="d-flex flex-wrap gap-2">

                                            <button
                                                className="btn btn-info btn-sm"
                                                onClick={() => navigate(`/subject/${s.subjectId}`)}
                                            >

                                                <i className="bi bi-eye me-1"></i> Pregled

                                            </button>

                                            <button
                                                className="btn btn-warning btn-sm"
                                                onClick={() => navigate(`/subject/edit/${s.subjectId}`)}
                                            >

                                                <i className="bi bi-pencil me-1"></i> Izmeni

                                            </button>

                                            <button
                                                className="btn btn-danger btn-sm"
                                                onClick={() => deleteSubject(s.subjectId)}
                                            >

                                                <i className="bi bi-trash"></i> Obriši

                                            </button>  

                                        </div>  

                                    </td>

                                </tr>

                            ))}

                        </tbody>

                    </table>

                </div>

            }

        </div>

    );

};

export default Subject;