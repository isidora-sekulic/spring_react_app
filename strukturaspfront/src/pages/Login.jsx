import { useState } from "react";
import { useNavigate } from "react-router-dom";
import http from "../api/http";

import { useSearchParams } from "react-router-dom";
import { toast } from "react-toastify";



const Login = () => {

    const [formData, setFormData] = useState({

        username: "",
        password: ""

    });


    const navigate=useNavigate();


    const [searchParams] = useSearchParams();
    const verified = searchParams.get("verified");



    const handleSubmit = async (e) => {

        e.preventDefault();

        try {

            const res = await http.post("/auth/login", formData);

            toast.success("Uspešno ste se prijavili!");

            localStorage.setItem("token", res.data.token);

            localStorage.setItem(
                "user",
                JSON.stringify(res.data.user)
            );

            setFormData({

                username: "",

                password: ""

            });

            navigate("/");

        } catch (e) {

            const msg = e?.response?.data?.message || "";

                if (msg.toLowerCase().includes("bad")) {

                   toast.error("Pogrešno korisničko ime ili lozinka.");

                }
                
                else {

                    toast.error(msg || "Prijava nije uspela.");

                }


        }

    };

    const handleChange = (e) => {

        const { name, value } = e.target;

        setFormData(prev => ({

            ...prev,
            [name]: value

        }));

    };

    return (

        <div className="container mt-5" style={{ maxWidth: "450px" }}>

            <div className="card shadow">

                <form className="card-body" onSubmit={handleSubmit}>

                    <div className="text-center mb-3">

                        <i className="bi bi-person-circle text-primary"
                        style={{ fontSize: "70px" }}>
                        </i>

                    </div>

                    <h3 className="text-center mb-4">

                        Prijava na sistem

                    </h3>

                    {
                        verified === "success" &&

                        <div className="alert alert-success">

                            Nalog je uspešno aktiviran.

                            Sada se možete prijaviti.

                        </div>
                    }

                    {
                        verified === "expired" &&

                        <div className="alert alert-warning">

                            Aktivacioni link je istekao.

                        </div>
                    }

                    {
                        verified === "invalid" &&

                        <div className="alert alert-danger">

                            Aktivacioni link nije ispravan.

                        </div>
                    }
                

                    <div className="mb-3">

                        <label className="form-label">

                            Korisničko ime

                        </label>

                        <input
                            type="text"
                            name="username"
                            className="form-control"
                            value={formData.username}
                            onChange={handleChange}
                        />

                    </div>

                    <div className="mb-3">

                        <label className="form-label">

                            Lozinka

                        </label>

                        <input
                            type="password"
                            name="password"
                            className="form-control"
                            value={formData.password}
                            onChange={handleChange}
                        />

                    </div>

                    <button
                        type="submit"
                        className="btn btn-primary w-100"
                    >

                        Prijavi se

                    </button>

                </form>

            </div>

        </div>

    );

};

export default Login;