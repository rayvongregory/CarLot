import { useEffect } from "react"
import { useState } from "react"
import { InputField } from "./InputField"

export const AccountInfo = (props) => {
  const [pfp, setPfp] = useState("default_user_pfp.png")
  useEffect(() => {
    console.log("fetching user pfp")
  }, [])

  return (
    <>
      <div className="max-w-[1500px] m-auto pb-20 tablet:pt-10">
        <div className="account-info tablet:w-[80%] p-2 tablet:max-w-[650px] m-auto drop-shadow-md tablet:bg-white">
          <h3 className="relative text-xl font-semibold m-auto text-white bg-gs-accent-blue p-2 text-center w-full mb-6 tablet:w-fit tablet:m-4">
            Account information
          </h3>
          <div className="pfp flex flex-col items-center tablet:items-start tablet:grid tablet:gap-x-4">
            <div className="bg-gs-blue">
              <img className="w-[220px]" src={pfp} alt="user pfp" />
            </div>
            <button className="bg-gs-accent-blue text-white p-2 w-[220px]">
              Choose a new profile picture
            </button>
          </div>
        </div>
      </div>
    </>
  )
}
