import { Nav } from "../Components/Nav"
import { AccountInfo } from "../Components/AccountInfo"
import "../Styles/account.css"
import { useEffect } from "react"
import { useState } from "react"
import { useNavigate } from "react-router-dom"

export const AccountPage = (props) => {
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
      <AccountInfo />
    </>
  )
}
