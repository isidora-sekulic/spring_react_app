function Home() {
  return (
    <div>
      <section className="bg-light" style={{ padding: "90px 0" }}>
        <div className="container">
          <div className="row align-items-center">
            
            <div className="col-md-7">
              <h1 className="display-4 fw-bold">
                Study Program Management System
              </h1>

              <p className="lead mt-4 fs-4">
                Upravljanje studijskim programima, modulima, predmetima i
                izbornim grupama na jednom mestu.
              </p>
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

      <section className="container my-5">
        <div className="row g-4">
          <div className="col-md-3">
            <div className="card h-100 shadow-sm">
              <div className="card-body">
                <h5 className="card-title">Studijski programi</h5>
                <p className="card-text">
                  Pregled i upravljanje studijskim programima.
                </p>
              </div>
            </div>
          </div>

          <div className="col-md-3">
            <div className="card h-100 shadow-sm">
              <div className="card-body">
                <h5 className="card-title">Moduli</h5>
                <p className="card-text">
                  Dodavanje i izmena modula studijskog programa.
                </p>
              </div>
            </div>
          </div>

          <div className="col-md-3">
            <div className="card h-100 shadow-sm">
              <div className="card-body">
                <h5 className="card-title">Predmeti</h5>
                <p className="card-text">
                  Upravljanje predmetima i ESPB bodovima.
                </p>
              </div>
            </div>
          </div>

          <div className="col-md-3">
            <div className="card h-100 shadow-sm">
              <div className="card-body">
                <h5 className="card-title">Izborne grupe</h5>
                <p className="card-text">
                  Organizacija izbornih grupa i izbornih predmeta.
                </p>
              </div>
            </div>
          </div>
        </div>
      </section>
    </div>
  );
}

export default Home;