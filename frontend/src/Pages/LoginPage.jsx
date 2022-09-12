import { useEffect } from "react"
import { useNavigate } from "react-router-dom"
import { Hero } from "../Components/Hero"
import { LoginForm } from "../Components/LoginForm"
import { Nav } from "../Components/Nav"
import "../Styles/login_register.css"

export const LoginPage = (props) => {
  const navigate = useNavigate()

  useEffect(() => {
    async function verifyToken() {
      const response = await props.verifyToken()
      if (response.validToken) navigate("/")
    }
    verifyToken()
  }, [navigate, props])

  return (
    <>
      <Nav role="guest" />
      <Hero />
      <LoginForm />
    </>
  )
}
