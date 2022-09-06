import { useEffect, useState } from "react"
import { useNavigate } from "react-router-dom"
import { emailRegex, passwordRegex } from "../Assets/regex"
import { loginUser } from "../Services/userServices"
import { InputField } from "./InputField"

export const LoginForm = (props) => {
  const [email, setEmail] = useState("")
  const [password, setPassword] = useState("")
  const [disabled, setDisabled] = useState(true)
  const [requiredEmail, setRequiredEmail] = useState(true)
  const [requiredPassword, setRequiredPassword] = useState(true)
  const [response, setResponse] = useState("")
  const navigate = useNavigate()

  useEffect(() => {
    const valid = email.match(emailRegex) && password.match(passwordRegex)
    if (valid) setDisabled(false)
    else setDisabled(true)
  }, [email, password])

  useEffect(() => {
    setTimeout(() => {
      setResponse("")
    }, 5000)
  }, [response])

  const handleLogin = async (e) => {
    e.preventDefault()
    const response = await loginUser({ email, password }) // creates an authToken and returns basic user info
    if (response.err) {
      setResponse(response.err)
    }
    if (response.id) {
      setResponse("")
      const { id, fname, lname, role } = response
      props.setUser({ id, fname, lname, role })
      navigate("/")
    }
  }

  const handleRequiredEmail = (e) => {
    const { target } = e
    const { value } = target
    const trimmed = value.trim()
    target.value = trimmed
    setEmail(trimmed)
    setRequiredEmail(!trimmed.match(emailRegex))
  }

  const handleRequiredPassword = (e) => {
    const { target } = e
    const { value } = target
    const trimmed = value.trim()
    target.value = trimmed
    setPassword(trimmed)
    setRequiredPassword(!trimmed.match(passwordRegex))
  }

  return (
    <div className="max-w-[1500px] m-auto pt-10 pb-20">
      <div className="w-[80%] tablet:w-[530px] m-auto drop-shadow-md">
        <h3 className="relative text-center font-semibold text-xl py-6 border bg-gs-accent-blue text-white border-gs-accent-blue rounded-t">
          WELCOME BACK!
        </h3>
        <form
          className="flex flex-col pt-6 gap-6 justify-center bg-white rounded-b"
          onSubmit={handleLogin}
        >
          <InputField
            placeholder="Email"
            type="text"
            id="email"
            handleRequired={handleRequiredEmail}
            required={requiredEmail}
          />
          <InputField
            placeholder="Password"
            type="password"
            id="password"
            handleRequired={handleRequiredPassword}
            required={requiredPassword}
          />
          <div className="flex flex-col gap-2 m-auto w-fit tablet:grid grid-cols-2 tablet:gap-4 place-content-center px-[10px]">
            <a
              className="text-center tablet:text-left font-bold text-sm"
              href="/register"
            >
              Need an account?
            </a>
            <a
              className="text-center tablet:text-right font-bold text-sm"
              href="/forgot-password"
            >
              Forgot password?
            </a>
          </div>
          <button
            className="bg-black text-white text-center h-[40px] rounded mb-2 mx-2"
            type="submit"
            disabled={disabled ? "disabled" : null}
          >
            LOG IN
          </button>
        </form>
        <p className="text-center font-bold text-sm text-red-800">{response}</p>
      </div>
    </div>
  )
}
