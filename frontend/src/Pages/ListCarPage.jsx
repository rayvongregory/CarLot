import { useEffect, useState } from "react"
import { useNavigate } from "react-router-dom"
import { ListCarForm } from "../Components/ListCarForm"
import { Nav } from "../Components/Nav"

export const ListCarPage = (props) => {
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
      <p>
        Have three sections here for PENDING APPROVAL, DENIED REQUESTS, and
        LISTED
      </p>
      <ListCarForm />
    </>
  )
}
