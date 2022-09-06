import React from "react"
import { Hero } from "../Components/Hero"
import { Nav } from "../Components/Nav"
import { RegisterForm } from "../Components/RegisterForm"
import "../Styles/login_register.css"

export const RegisterPage = (props) => {
  console.log(props)

  return (
    <>
      <Nav role={props.user?.role} />
      <Hero />
      <RegisterForm />
    </>
  )
}
