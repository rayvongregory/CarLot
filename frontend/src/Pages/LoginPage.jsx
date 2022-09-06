import { useState } from "react"
import { useEffect } from "react"
import { useNavigate } from "react-router-dom"
import { Hero } from "../Components/Hero"
import { LoginForm } from "../Components/LoginForm"
import { Nav } from "../Components/Nav"
import "../Styles/login_register.css"

export const LoginPage = (props) => {
  const navigate = useNavigate()
  const [user, setUser] = useState(0)

  console.log(props)
  useEffect(() => {
    if (props.id) navigate("/")
  }, [navigate, props.id])

  return (
    <>
      <Nav role={props.user?.role} />
      <Hero />
      <LoginForm setUser={props.setUser} />
    </>
  )
}
