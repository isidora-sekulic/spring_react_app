import React,{useState,useEffect} from 'react'
import { useNavigate } from "react-router-dom";
import http from "../api/http"; 
import { toast } from "react-toastify";


export const StudyProgram = () => {

    const [studyPrograms, setStudyPrograms]= useState([]);
    const [loading, setLoading]= useState(true);
    const [search, setSearch] = useState("");
    const [sort, setSort] = useState({by: "name",dir: "asc"});

    const navigate = useNavigate();

    function toggleSort(col) {
        setSort((s) =>
            s.by === col? { by: col, dir: s.dir === "asc" ? "desc" : "asc"}: {by: col, dir: "asc"}
        );

    }   

    const loadStudyPrograms = async () => {

        setLoading(true);
        setSearch("");

        try {

            const res = await http.get("/studyProgram");

            setStudyPrograms(
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

    const deleteStudyProgram = async (id) => {

        if (!window.confirm("Da li ste sigurni da želite da obrišete studijski program?")) {
            return;
        }

        try {

            await http.delete(`/studyProgram/${id}`);          
            toast.success("Studijski program je uspešno obrisan.")

            loadStudyPrograms();

        } catch (e) {
            toast.error(
                e?.response?.data?.message ||
                "Došlo je greške prilikom brisanaja studijskog programa."
            );
        }

    };

    useEffect(() => {
        loadStudyPrograms();
    }, []);

    let filteredPrograms = studyPrograms.filter((sp) => {

        const value = search.toLowerCase();

        return (
            sp.name.toLowerCase().includes(value) ||
            sp.description.toLowerCase().includes(value)
        );

    });

    filteredPrograms.sort((a, b) => {

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
                    <h2>Studijski programi</h2>
                    <p className="text-muted">
                        Pregled svih studijskih programa
                    </p>
                </div>

            </div>
            <div className="row mb-3">

            
                <input
                    type="text"
                    className="form-control mb-3"
                    placeholder="Pretraži po imenu ili opisu..."
                    value={search}
                    onChange={(e) => setSearch(e.target.value)}
                />
            

                <div className="d-flex justify-content-between align-items-center flex-wrap gap-2 mb-3">

                    <button
                        className="btn btn-outline-secondary"
                        onClick={loadStudyPrograms}
                    >
                        Osveži
                    </button>

                    <button
                        className="btn btn-primary"
                        onClick={() => navigate("/studyProgram/new")}
                    >
                        + Dodavanje novog studijskog programa
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
                                    onClick={() => toggleSort("description")}
                                >
                                    Opis{" "}
                                    {sort.by==="description" && (sort.dir==="asc" ? "▲" : "▼")}
                                </th>

                                <th
                                    style={{ cursor: "pointer" }}
                                    onClick={() => toggleSort("durationYears")}
                                >
                                    Trajanje{" "}
                                    {sort.by==="durationYears" && (sort.dir==="asc" ? "▲" : "▼")}
                                </th>

                                <th
                                    style={{ cursor: "pointer" }}
                                    onClick={() => toggleSort("totalEspb")}
                                >
                                    ESPB{" "}
                                    {sort.by==="totalEspb" && (sort.dir==="asc" ? "▲" : "▼")}
                                </th>

                                <th className="text-center">
                                    Akcije
                                </th>

                            </tr>

                        </thead>

                        <tbody>

                            {filteredPrograms.length === 0 &&

                                <tr>

                                    <td
                                        colSpan="5"
                                        className="text-center"
                                    >
                                        Nisu pronađeni studijski programi po kriterijumu.
                                    </td>

                                </tr>

                            }

                            {filteredPrograms.map((sp) => (

                                <tr
                                    key={sp.studyProgramId}
                                >

                                    <td>

                                        {sp.name}

                                    </td>

                                    <td>

                                        {sp.description}

                                    </td>

                                    <td>

                                        {sp.durationYears}

                                    </td>

                                    <td>

                                        {sp.totalEspb}

                                    </td>

                                    <td>
                                        <div className="d-flex flex-wrap gap-2">

                                            <button
                                                className="btn btn-info btn-sm"
                                                onClick={() => navigate(`/studyProgram/${sp.studyProgramId}`)}
                                            >

                                                <i className="bi bi-eye me-1"></i> Pregled

                                            </button>

                                            <button
                                                className="btn btn-warning btn-sm"
                                                onClick={() => navigate(`/studyProgram/edit/${sp.studyProgramId}`)}
                                            >

                                                <i className="bi bi-pencil me-1"></i> Izmeni

                                            </button>

                                            <button
                                                className="btn btn-danger btn-sm"
                                                onClick={() => deleteStudyProgram(sp.studyProgramId)}
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

export default StudyProgram;
 

