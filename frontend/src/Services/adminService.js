import axios from "axios"
import { getAccessToken, PATH } from "./constants"

export const getNumRequests = async () => {
  const accessToken = getAccessToken()
  let numRequests = 0
  await axios
    .post(`${PATH}/api/get-num-new-requests`, { accessToken })
    .then((res) => {
      numRequests = res.data.numNewRequests
    })
    .catch((err) => {
      console.log(err)
    })
  return numRequests
}
