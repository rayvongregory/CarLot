import { useEffect } from "react"
import { useState } from "react"
import { getUserInfo, uploadImage } from "../Services/userServices"
import { AccountInfoInputField } from "./AccountInfoInputField"
import { ChangePasswordForm } from "./ChangePasswordForm"

export const AccountInfo = (props) => {
  const [pfp, setPfp] = useState("")
  const [fname, setFname] = useState("")
  const [lname, setLname] = useState("")
  const [email, setEmail] = useState("")
  const [updateMsg, setUpdateMsg] = useState("")

  useEffect(() => {
    async function getAccountInfo() {
      const { p, f, l, e } = await getUserInfo()
      if (p) setPfp(p)
      else setPfp("default_user_pfp.png")
      setFname(f)
      setLname(l)
      setEmail(e)
    }
    getAccountInfo()
  }, [])

  useEffect(() => {
    setTimeout(() => {
      setUpdateMsg("")
    }, 3000)
  }, [updateMsg])

  const handleNewPicture = async (e) => {
    const file = e.target.files[0]
    const url = await uploadImage(file)
    setPfp(url)
  }

  return (
    <>
      <div className="max-w-[1500px] m-auto pb-20 tablet:pt-10">
        <div className="account-info tablet:w-[80%] tablet:max-w-[650px] m-auto drop-shadow-md tablet:bg-white">
          <h3 className="relative text-2xl font-semibold m-auto text-white bg-gs-accent-blue py-4 px-2 text-center w-full mb-6">
            Account information
          </h3>
          <div className="pfp flex flex-col items-center">
            <div className="bg-gs-blue">
              <img className="w-[220px]" src={pfp} alt="user pfp" />
            </div>
            <label className="bg-gs-accent-blue text-white p-2 w-[220px] cursor-pointer">
              <input
                className="hidden"
                type="file"
                onChange={handleNewPicture}
              />
              Choose a new profile picture
            </label>
          </div>
          <div className="account-info-grid grid mt-10 gap-5">
            <AccountInfoInputField
              id="fname"
              label="First Name"
              value={fname}
              setValue={setFname}
              setUpdateMsg={setUpdateMsg}
            />
            <AccountInfoInputField
              id="lname"
              label="Last Name"
              value={lname}
              setValue={setLname}
              setUpdateMsg={setUpdateMsg}
            />
            <AccountInfoInputField
              id="email"
              label="Email"
              value={email}
              setValue={setEmail}
              setUpdateMsg={setUpdateMsg}
            />
            <ChangePasswordForm />
            <p className="text-center font-bold text-sm text-red-800">
              {updateMsg}
            </p>
          </div>
        </div>
      </div>
    </>
  )
}
