import Navbar from "./components/Navbar";
import Footer from "./components/Footer";
import Home from "./pages/Home";
import StudyProgram from "./pages/StudyProgram";
import StudyProgramForm from "./pages/StudyProgramForm";
import StudyProgramDetails from "./pages/StudyProgramDetails";
import ModuleForm from "./pages/ModuleForm";
import ModuleDetails from "./pages/ModuleDetails";
import Subject from "./pages/Subject";
import SubjectForm from "./pages/SubjectForm";
import SubjectDetails from "./pages/SubjectDetails";
import ModuleSubjectForm from "./pages/ModuleSubjectForm";
import ElectiveGroupForm from "./pages/ElectiveGroupForm";
import Login from "./pages/Login";
import Register from "./pages/Register";
import ProtectedRoute from "./components/ProtectedRoute";
import "react-toastify/dist/ReactToastify.css";



import { ToastContainer } from "react-toastify";

import { BrowserRouter, Routes, Route } from "react-router-dom";

function App() {

  return (
  
    <>
    <BrowserRouter>
      <div className="d-flex flex-column min-vh-100">
        <Navbar/>

        <main className="flex-grow-1">
          <Routes>
            
            <Route path="/" element={<Home />} />
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />

            <Route path="/studyProgram" element={
                  <ProtectedRoute>
                    <StudyProgram />
                  </ProtectedRoute>
                }
              />
            <Route
              path="/studyProgram/new"
              element={
                <ProtectedRoute>
                  <StudyProgramForm />
                </ProtectedRoute>
              }
            />

            <Route
              path="/studyProgram/edit/:id"
              element={
                <ProtectedRoute>
                  <StudyProgramForm />
                </ProtectedRoute>
              }
            />

            <Route
              path="/studyProgram/:id"
              element={
                <ProtectedRoute>
                  <StudyProgramDetails />
                </ProtectedRoute>
              }
            />

            <Route
              path="/studyProgram/:studyProgramId/module/new"
              element={
                <ProtectedRoute>
                  <ModuleForm />
                </ProtectedRoute>
              }
            />

            <Route
              path="/studyProgram/:studyProgramId/module/edit/:moduleId"
              element={
                <ProtectedRoute>
                  <ModuleForm />
                </ProtectedRoute>
              }
            />

            <Route
              path="/studyProgram/:studyProgramId/module/:moduleId"
              element={
                <ProtectedRoute>
                  <ModuleDetails />
                </ProtectedRoute>
              }
            />

            <Route
              path="/subject"
              element={
                <ProtectedRoute>
                  <Subject />
                </ProtectedRoute>
              }
            />

            <Route
              path="/subject/new"
              element={
                <ProtectedRoute>
                  <SubjectForm />
                </ProtectedRoute>
              }
            />

            <Route
              path="/subject/edit/:id"
              element={
                <ProtectedRoute>
                  <SubjectForm />
                </ProtectedRoute>
              }
            />

            <Route
              path="/subject/:id"
              element={
                <ProtectedRoute>
                  <SubjectDetails />
                </ProtectedRoute>
              }
            />

            <Route
              path="/studyProgram/:studyProgramId/module/:moduleId/subject/new"
              element={
                <ProtectedRoute>
                  <ModuleSubjectForm />
                </ProtectedRoute>
              }
            />

            <Route
              path="/studyProgram/:studyProgramId/module/:moduleId/subject/edit/:moduleSubjectId"
              element={
                <ProtectedRoute>
                  <ModuleSubjectForm />
                </ProtectedRoute>
              }
            />

            <Route
              path="/studyProgram/:studyProgramId/module/:moduleId/electiveGroup/new"
              element={
                <ProtectedRoute>
                  <ElectiveGroupForm />
                </ProtectedRoute>
              }
            />

            <Route
              path="/studyProgram/:studyProgramId/module/:moduleId/electiveGroup/edit/:electiveGroupId"
              element={
                <ProtectedRoute>
                  <ElectiveGroupForm />
                </ProtectedRoute>
              }
            />
                                                                                        
          </Routes>

        </main>

        <Footer/>

    </div>

    </BrowserRouter>

        <ToastContainer
        position="top-right"
        autoClose={3000}
        theme="colored"/>


    </>
  );
}

export default App;
