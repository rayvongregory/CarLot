import axios from "axios"
import { getAccessToken, PATH } from "../Services/constants"

// Registers user
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

// Logins in user and returns response... either an error or an authToken
export const loginUser = async (userInfo) => {
  const { email, password } = userInfo
  let err
  await axios
    .post(`${PATH}/api/login-user`, { email, password })
    .then((res) => {
      const { accessToken } = res.data
      document.cookie = `accessToken=${accessToken}; SameSite=Lax;`
    })
    .catch((e) => {
      console.log(e)
      const { data } = e.response
      err = data
    })
  return err
}

export const getUserInfo = async () => {
  // at this point, the token has already been verified and
  // access to the page has already been granted so we just need to decode this token
  const response = {}
  let accessToken = getAccessToken()
  await axios
    .post(`${PATH}/api/get-user-info`, { accessToken })
    .then((res) => {
      const { pfp, fname, lname, email } = res.data
      console.log(res)
      response.p = pfp
      response.f = fname
      response.l = lname
      response.e = email
    })
    .catch((err) => {
      console.log(err)
    })
  return response
}

export const updateUserFname = async (fname) => {
  let accessToken = getAccessToken()
  await axios
    .patch(`${PATH}/api/update-fname`, { accessToken, fname })
    .then((res) => {})
    .catch((err) => {
      console.log(err)
    })
}

export const updateUserLname = async (lname) => {
  let accessToken = getAccessToken()
  await axios
    .patch(`${PATH}/api/update-lname`, { accessToken, lname })
    .then((res) => {})
    .catch((err) => {
      console.log(err)
    })
}

export const updateUserEmail = async (email) => {
  console.log(`changing email to ${email}`)
  let accessToken = getAccessToken()
  await axios
    .patch(`${PATH}/api/update-email`, { accessToken, email })
    .then((res) => {})
    .catch((err) => {
      console.log(err)
    })
}

export const uploadImage = async (file) => {
  let accessToken = getAccessToken()
  let { cloudname, uploadpreset, folder } = await getCloudinaryConfig(
    accessToken
  )
  await deletePrevPfp(accessToken)
  const cloudinaryUploadUrl =
    "https://api.cloudinary.com/v1_1/" + cloudname + "/image/upload"
  const formData = new FormData()
  formData.append("file", file)
  formData.append("folder", folder)
  formData.append("upload_preset", uploadpreset)
  const { public_id, url, asset_id } = await uploadToCloudinary(
    cloudinaryUploadUrl,
    formData
  )
  await savePfpRef(public_id, url, asset_id)
  return url
}

const getCloudinaryConfig = async (accessToken) => {
  let response = {}
  await axios
    .post(`${PATH}/api/get-cloudinary-config`, { accessToken })
    .then((res) => {
      response = res.data
    })
    .catch((err) => {
      console.log(err)
    })
  return response
}

const uploadToCloudinary = async (url, formData) => {
  let response = {}
  await axios
    .post(url, formData)
    .then((res) => {
      response = res.data
    })
    .catch((err) => {
      console.log(err)
    })
  return response
}

const savePfpRef = async (public_id, url, asset_id) => {
  let accessToken = getAccessToken()
  let response = {}
  await axios
    .post(`${PATH}/api/save-pfp-ref`, {
      accessToken,
      publicId: public_id,
      url,
      assetId: asset_id,
    })
    .then((res) => {
      response = res.data
    })
    .catch((err) => {
      console.log(err)
    })
  return response
}

const deletePrevPfp = async (accessToken) => {
  await axios
    .post(`${PATH}/api/delete-user-pfp`, { accessToken })
    .then((res) => {})
    .catch((err) => {
      console.log(err)
    })
}
