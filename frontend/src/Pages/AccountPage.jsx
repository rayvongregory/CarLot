import { Nav } from "../Components/Nav"
import { AccountInfo } from "../Components/AccountInfo"
import "../Styles/account.css"
import { useEffect } from "react"
import { getUserFromCookie } from "../Services/userServices"
import { useState } from "react"
import { useNavigate } from "react-router-dom"

export const AccountPage = (props) => {
  let navigate = useNavigate()
  const [user, setUser] = useState(0)
  useEffect(() => {
    const getUser = async () => {
      const response = await getUserFromCookie()
      console.log(response)
      const { id, fname, lname, role } = response
      setUser({ id, fname, lname, role })
    }
    if (!user) getUser()
    if (user.role === "guest") navigate("/")
  }, [navigate, user])
  return (
    <>
      <Nav role={user.role} />
      <AccountInfo />
    </>
  )
}
