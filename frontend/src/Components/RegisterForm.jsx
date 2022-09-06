import { useEffect, useState } from "react"
import { emailRegex, passwordRegex } from "../Assets/regex"
import { registerUser } from "../Services/userServices"
import { InputField } from "./InputField"

export const RegisterForm = () => {
  const [fname, setFname] = useState("")
  const [lname, setLname] = useState("")
  const [email, setEmail] = useState("")
  const [confirmEmail, setConfirmEmail] = useState("")
  const msg =
    "Your password must be at least 8 characters long, and contain at least one letter, at least one number, and at least one special character."
  const [password, setPassword] = useState("")
  const [confirmPassword, setConfirmPassword] = useState("")
  const [disabled, setDisabled] = useState(true)

  const [requiredFname, setRequiredFname] = useState(true)
  const [requiredLname, setRequiredLname] = useState(true)
  const [requiredEmail, setRequiredEmail] = useState(true)
  const [requiredConfirmEmail, setRequiredConfirmEmail] = useState(true)
  const [requiredPassword, setRequiredPassword] = useState(true)
  const [requireConfirmPassword, setRequiredConfirmPassword] = useState(true)

  useEffect(() => {
    const valid =
      fname !== "" &&
      lname !== "" &&
      email.match(emailRegex) &&
      email === confirmEmail &&
      password.match(passwordRegex) &&
      password === confirmPassword
    if (valid) setDisabled(false)
    else setDisabled(true)
  }, [fname, lname, email, confirmEmail, password, confirmPassword])

  const handleRegistration = (e) => {
    e.preventDefault()
    registerUser({
      fname,
      lname,
      email,
      confirmEmail,
      password,
      confirmPassword,
    })
  }

  const handleRequiredName = (e) => {
    const { target } = e
    const { id, value } = target
    const trimmed = value.trim()
    switch (id) {
      case "fname":
        setFname(trimmed)
        if (trimmed !== "") {
          setRequiredFname(false)
        } else {
          setRequiredFname(true)
        }
        break
      case "lname":
        setLname(trimmed)
        if (trimmed !== "") {
          setRequiredLname(false)
        } else {
          setRequiredLname(true)
        }
        break
      default:
        break
    }
  }

  const handleRequiredEmail = (e) => {
    const { target } = e
    const { value } = target
    const trimmed = value.trim()
    target.value = trimmed
    setEmail(trimmed)
    setRequiredEmail(!trimmed.match(emailRegex))
    setRequiredConfirmEmail(trimmed !== confirmEmail)
  }

  const handleRequiredConfirmEmail = (e) => {
    const { target } = e
    const { value } = target
    const trimmed = value.trim()
    target.value = trimmed
    setConfirmEmail(trimmed)
    setRequiredConfirmEmail(!trimmed.match(emailRegex) || trimmed !== email)
  }

  const handleRequiredPassword = (e) => {
    const { target } = e
    const { value } = target
    const trimmed = value.trim()
    target.value = trimmed
    setPassword(trimmed)
    setRequiredPassword(!trimmed.match(passwordRegex))
  }

  const handleRequiredConfirmPassword = (e) => {
    const { target } = e
    const { value } = target
    const trimmed = value.trim()
    target.value = trimmed
    setConfirmPassword(trimmed)
    setRequiredConfirmPassword(
      !trimmed.match(passwordRegex) || trimmed !== password
    )
  }

  return (
    <div className="max-w-[1500px] mx-auto pt-10 pb-20">
      <div className="w-[80%] tablet:w-[530px] m-auto drop-shadow-md">
        <h3 className="relative text-center font-semibold text-xl py-6 border bg-gs-accent-blue text-white border-gs-accent-blue rounded-t">
          REGISTER
        </h3>
        <form
          className="flex flex-col pt-6 gap-6 justify-center bg-white rounded-b"
          onSubmit={handleRegistration}
        >
          <InputField
            placeholder="First name"
            type="text"
            id="fname"
            handleRequired={handleRequiredName}
            required={requiredFname}
          />
          <InputField
            placeholder="Last name"
            type="text"
            id="lname"
            handleRequired={handleRequiredName}
            required={requiredLname}
          />
          <InputField
            placeholder="Email"
            type="text"
            id="email"
            handleRequired={handleRequiredEmail}
            required={requiredEmail}
          />
          <InputField
            placeholder="Confirm email"
            type="text"
            id="confirm_email"
            handleRequired={handleRequiredConfirmEmail}
            required={requiredConfirmEmail}
          />
          <h4 className="mx-5 font-semibold mb-4">{msg}</h4>
          <InputField
            placeholder="Password"
            type="password"
            id="password"
            handleRequired={handleRequiredPassword}
            required={requiredPassword}
          />
          <InputField
            placeholder="Confirm password"
            type="password"
            id="confirm_password"
            handleRequired={handleRequiredConfirmPassword}
            required={requireConfirmPassword}
          />
          <div className="mr-4 flex flex-col items-end">
            <a
              className="text-center tablet:text-left font-bold text-sm"
              href="/login"
            >
              Already have an account?
            </a>
          </div>
          <button
            className="bg-black text-white text-center h-[40px] rounded mb-2 mx-2"
            type="submit"
            disabled={disabled ? "disabled" : null}
          >
            SIGN UP
          </button>
        </form>
      </div>
    </div>
  )
}
