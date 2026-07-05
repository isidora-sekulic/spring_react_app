import { NavLink, useNavigate } from "react-router-dom";

function Navbar() {

    const navigate = useNavigate();

    const loggedIn = !!localStorage.getItem("token");

    const me = JSON.parse(localStorage.getItem("user") || "null");

    const logout = () => {

        localStorage.removeItem("token");
        localStorage.removeItem("user");

        window.location.href = "/login";

    };

    return (

        <nav className="navbar navbar-expand-lg navbar-dark bg-dark">

            <div className="container">

                <span className="navbar-brand fw-bold">

                    Struktura studijskog programa

                </span>

                <button
                    className="navbar-toggler"
                    type="button"
                    data-bs-toggle="collapse"
                    data-bs-target="#navbarNav"
                >

                    <span className="navbar-toggler-icon"></span>

                </button>

                <div
                    className="collapse navbar-collapse"
                    id="navbarNav"
                >

                    <div className="navbar-nav">

                        <NavLink
                            to="/"
                            className="nav-link"
                        >
                            Početna
                        </NavLink>

                        {

                            loggedIn &&

                            <>

                                <NavLink
                                    to="/studyProgram"
                                    className="nav-link"
                                >
                                    Studijski programi
                                </NavLink>

                                <NavLink
                                    to="/subject"
                                    className="nav-link"
                                >
                                    Predmeti
                                </NavLink>

                            </>

                        }

                    </div>

                    <div className="navbar-nav ms-auto align-items-lg-center">

                        {

                            me &&

                            <span className="navbar-text text-white me-lg-3">

                                Dobrodošli, {me.username}

                            </span>

                        }

                        {

                            loggedIn ?

                                <button
                                    className="btn btn-outline-danger mt-2 mt-lg-0"
                                    onClick={logout}
                                >

                                    Odjavi se

                                </button>

                                :

                                <>

                                    <button
                                        className="btn btn-outline-primary mt-2 mt-lg-0 me-lg-2"
                                        onClick={() => navigate("/login")}
                                    >

                                        Prijavi se

                                    </button>

                                    <button
                                        className="btn btn-primary mt-2 mt-lg-0"
                                        onClick={() => navigate("/register")}
                                    >

                                        Registruj se

                                    </button>

                                </>

                        }

                    </div>

                </div>

            </div>

        </nav>

    );

}

export default Navbar;