import axios from "axios"
import { PATH, getAccessToken } from "../Services/constants"

// Returns true if accessToken is valid
export const verifyToken = async () => {
  let response = { validToken: false }
  let accessToken = getAccessToken()
  if (!accessToken) return response
  await axios
    .post(`${PATH}/api/verify-token`, { accessToken })
    .then((res) => {
      const { role, accessToken: at } = res.data
      response.role = role
      if (at) document.cookie = `accessToken=${at}; SameSite=Lax;`
      response.validToken = true
    })
    .catch((err) => {
      console.log(err)
      document.cookie =
        "accessToken=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;"
      response.validToken = false
    })
  return response
}

//

// export const getToken
