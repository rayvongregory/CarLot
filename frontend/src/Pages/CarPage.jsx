import React from "react"
import { useEffect } from "react"
import { useState } from "react"
import { useNavigate } from "react-router-dom"
import { CarInfo } from "../Components/CarInfo"
import { Nav } from "../Components/Nav"

export const CarPage = (props) => {
  const navigate = useNavigate()
  const [role, setRole] = useState("user")

  useEffect(() => {
    async function verifyToken() {
      const response = await props.verifyToken()
      if (!response.validToken) navigate("/")
      setRole(response.role)
    }
    verifyToken()
  }, [navigate, props])

  return (
    <>
      <Nav role={role} />
      <CarInfo />
    </>
  )
}
