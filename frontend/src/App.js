import { Route, Routes } from "react-router-dom"
import { AccountPage } from "./Pages/AccountPage"
import { CarPage } from "./Pages/CarPage"
import { HomePage } from "./Pages/HomePage"
import { ListCarPage } from "./Pages/ListCarPage"
import { LoginPage } from "./Pages/LoginPage"
import { RegisterPage } from "./Pages/RegisterPage"
import { verifyToken } from "./Services/authServices"

export default function App() {
  return (
    <Routes>
      <Route
        exact
        path="/"
        element={<HomePage verifyToken={verifyToken} />}
      ></Route>
      <Route
        path="/id/:carId"
        element={<CarPage verifyToken={verifyToken} />}
      ></Route>
      <Route
        exact
        path="/list"
        element={<ListCarPage verifyToken={verifyToken} />}
      ></Route>{" "}
      {/* only users can access this page */}
      <Route
        exact
        path="/requests"
        element={<HomePage verifyToken={verifyToken} />}
      ></Route>{" "}
      {/* only admin can access this page */}
      <Route
        exact
        path="/dms"
        element={<HomePage verifyToken={verifyToken} />}
      ></Route>
      <Route
        exact
        path="/login"
        element={<LoginPage verifyToken={verifyToken} />}
      ></Route>
      <Route
        exact
        path="/register"
        element={<RegisterPage verifyToken={verifyToken} />}
      ></Route>
      <Route
        exact
        path="/account"
        element={<AccountPage verifyToken={verifyToken} />}
      ></Route>
    </Routes>
  )
}
