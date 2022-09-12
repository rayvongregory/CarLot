import { useNavigate } from "react-router-dom"
import { CarSearchForm } from "../Components/CarSearchForm"
import { Hero } from "../Components/Hero"
import { Nav } from "../Components/Nav"
import "../Styles/hero.css"
import "../Styles/car-search-form.css"
import { useEffect, useState } from "react"

export const HomePage = (props) => {
  const navigate = useNavigate()
  const [role, setRole] = useState("guest")

  useEffect(() => {
    async function verifyToken() {
      const response = await props.verifyToken()
      if (response.validToken) {
        setRole(response.role)
      }
    }
    verifyToken()
  }, [navigate, props])

  return (
    <>
      <Nav role={role} />
      <Hero />
      <h1 className="hero-msg relative text-center text-white font-semibold m-auto w-full max-w-[1500px] p-2">
        Finding a ride has never been easier.
      </h1>
      <CarSearchForm />
    </>
  )
}
