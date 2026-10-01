import { useState } from "react";
import { useNavigate } from "react-router-dom";
import http from "../api/http";
import { toast } from "react-toastify";


const Register= () =>{

    const navigate = useNavigate();

    const [formData, setFormData] = useState({

        email:"",
        username: "",
        password: ""

    });

    const [success, setSuccess] = useState(false);



    const handleChange = (e) => {

        const { name, value } = e.target;

        setFormData(prev => ({

            ...prev,

            [name]: value

        }));

    };

    const handleSubmit = async (e) => {

        e.preventDefault();
        setSuccess(false);


        try {

            await http.post("/auth/register", formData);            

            setSuccess(true);

                setFormData({

                    email: "",

                    username: "",

                    password: ""

                });

        } catch (e) {

            const msg = e?.response?.data?.message || "";

            if (msg.toLowerCase().includes("username")) {

                toast.error("Korisničko ime je već zauzeto.");

            }
            else if (msg.toLowerCase().includes("email")) {

                toast.error("Korisnik sa ovom email adresom već postoji.");

            }
            else {

                toast.error(msg || "Registracija nije uspela.");

            }

        }

    };


    return (

        <div className="container mt-5" style={{ maxWidth: "500px" }}>

            <div className="card shadow">

                <div className="card-body">

                    <h3 className="text-center mb-4">

                        Registracija

                    </h3>

                    {
                        success &&

                        <div className="alert alert-success">

                            <strong>Registracija je uspešna!</strong>

                            <br />

                            Poslali smo aktivacioni link na Vašu email adresu.

                            Nakon aktivacije naloga možete se prijaviti na sistem.

                        </div>
                    }
                   

                    <form onSubmit={handleSubmit}>

                        <div className="mb-3">

                            <label className="form-label">

                                Email

                            </label>

                            <input
                                type="email"
                                className="form-control"
                                name="email"
                                value={formData.email}
                                onChange={handleChange}
                                required
                            />

                        </div>

                        <div className="mb-3">

                            <label className="form-label">

                                Korisničko ime

                            </label>

                            <input
                                type="text"
                                className="form-control"
                                name="username"
                                value={formData.username}
                                onChange={handleChange}
                                required
                            />

                        </div>

                        <div className="mb-4">

                            <label className="form-label">

                                Lozinka

                            </label>

                            <input
                                type="password"
                                className="form-control"
                                name="password"
                                value={formData.password}
                                onChange={handleChange}
                                required
                            />

                        </div>

                        <div className="d-flex justify-content-end">

                            <button
                                type="submit"
                                className="btn btn-success me-2"
                            >

                                Registruj se

                            </button>

                            <button
                                type="button"
                                className="btn btn-secondary"
                                onClick={() => navigate("/login")}
                            >

                                Otkaži

                            </button>

                        </div>

                    </form>

                </div>

            </div>

        </div>

    );

}

export default Register;