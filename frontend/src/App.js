import { useEffect, useState } from "react"
import { Route, Routes } from "react-router-dom"
import { AccountPage } from "./Pages/AccountPage"
import { CarPage } from "./Pages/CarPage"
import { HomePage } from "./Pages/HomePage"
import { ListCarPage } from "./Pages/ListCarPage"
import { LoginPage } from "./Pages/LoginPage"
import { RegisterPage } from "./Pages/RegisterPage"
import { getUserFromCookie } from "./Services/userServices"

export default function App() {
  return (
    <Routes>
      <Route exact path="/" element={<HomePage />}></Route>
      <Route path="/id/:carId" element={<CarPage />}></Route>
      <Route exact path="/list" element={<ListCarPage />}></Route>
      <Route exact path="/requests" element={<HomePage />}></Route>
      <Route exact path="/login" element={<LoginPage />}></Route>
      <Route exact path="/register" element={<RegisterPage />}></Route>
      <Route exact path="/account" element={<AccountPage />}></Route>
    </Routes>
  )
}
