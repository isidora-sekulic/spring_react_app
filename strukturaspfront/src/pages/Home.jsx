function Home() {
  return (
    <div>
      <section className="bg-light" style={{ padding: "90px 0" }}>
        <div className="container">
          <div className="row align-items-center">
            
            <div className="col-md-7">
              <h1 className="display-4 fw-bold">
                  Sistem za upravljanje strukturom studijskih programa
              </h1>

              <p className="lead mt-4 fs-4">
                Jednostavno upravljanje studijskim programima, modulima, 
                predmetima i izbornim grupama, na jednom mestu.
              </p>

              <div className="mt-4">

                <p>📖 Upravljanje studijskim programima</p>

                <p>📖 Upravljanje modulima</p>

                <p>📖 Upravljanje predmetima</p>

                <p>📖Upravljanje izbornim grupama</p>

                <p>📖Generisanje PDF izveštaja</p>

            </div>
            </div>

            

            <div className="col-md-5 text-center mt-4 mt-md-0">
    <div className="card shadow-lg border-0">
        <div className="card-body p-5">

            <div className="display-2 text-primary mb-4">
                🎓
            </div>

            <h5 className="card-title fw-bold">
                Struktura studijskih programa
            </h5>

            <p className="card-text text-muted">
                Studijski programi se organizuju kroz module, gde svaki od njih
                sadrži obavezne i izborne predmete.
            </p>

            <div className="d-grid gap-2">
                <div className="badge bg-primary p-2">
                    Studijski program
                </div>

                <div className="badge bg-secondary p-2">
                    Modul
                </div>

                <div className="badge bg-success p-2">
                    Obavezni predmeti
                </div>

                <div className="badge bg-info text-dark p-2">
                    Izborni predmeti
                </div>
            </div>

        </div>
    </div>
</div>

          </div>
        </div>
      </section>

      <section className="bg-light py-5 mt-5">

        <div className="container">

            <h2 className="text-center mb-5 fw-bold">

                Ključne funkcionalnosti

            </h2>

            <div className="row text-center">

                <div className="col-md-3">

                    <i className="bi bi-diagram-3-fill display-4 text-primary"></i>

                    <h5 className="mt-3">

                        Studijski programi

                    </h5>

                    <p>

                        Kreiranje, izmena, pregled i brisanje studijskih programa.

                    </p>

                </div>

                <div className="col-md-3">

                    <i className="bi bi-grid-fill display-4 text-success"></i>

                    <h5 className="mt-3">

                        Moduli

                    </h5>

                    <p>

                        Organizacija studijskih programa kroz module.

                    </p>

                </div>

                <div className="col-md-3">

                    <i className="bi bi-journal-bookmark-fill display-4 text-warning"></i>

                    <h5 className="mt-3">

                        Predmeti

                    </h5>

                    <p>

                        Upravljanje obaveznim i izbornim predmetima i ESPB bodovima.

                    </p>

                </div>

                <div className="col-md-3">

                    <i className="bi bi-file-earmark-pdf-fill display-4 text-danger"></i>

                    <h5 className="mt-3">

                        PDF izveštaji

                    </h5>

                    <p>

                        Izvoz kompletne strukture studijskog programa.

                    </p>

                </div>

            </div>

        </div>

    </section>
    </div>
  );
}

export default Home;