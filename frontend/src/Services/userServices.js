import axios from "axios"

const PATH = "http://localhost:8080"

export const registerUser = (userInfo) => {
  const { fname, lname, email, confirmEmail, password, confirmPassword } =
    userInfo
  axios
    .post(`${PATH}/api/register-user`, {
      fname,
      lname,
      email,
      confirmEmail,
      password,
      confirmPassword,
    })
    .then((res) => {
      console.log(res)
    })
    .catch((err) => {
      console.error(err)
    })
}

export const loginUser = async (userInfo) => {
  const { email, password } = userInfo
  let response
  await axios
    .post(`${PATH}/api/login-user`, { email, password })
    .then((res) => {
      response = res.data
      const { id, fname, lname, email, role, authToken } = res.data
      response = { id, fname, lname, email, role }
      document.cookie = `authToken=${authToken}; SameSite=Lax;`
    })
    .catch((err) => {
      response = err.response.data
    })
  return response
}

export const getUserFromCookie = async () => {
  const cookies = document.cookie.split("; ")
  let authCookie
  for (let c of cookies) {
    const parts = c.split("=")
    const key = parts[0]
    if (key === "authToken") authCookie = parts[1]
  }
  const guest = {
    id: 0,
    fname: "Guest",
    lname: "",
    email: "",
    role: "guest",
    authToken: null,
  }
  if (!authCookie) return guest
  let response
  await axios
    .post(`${PATH}/api/get-user-info`, { authCookie })
    .then((res) => {
      response = res.data
    })
    .catch((err) => {
      console.log(err)
      response = guest
    })
  return response
}

export const authorizeUser = (userId) => {
  console.log("authorizing user") // checks tokens and such
}
