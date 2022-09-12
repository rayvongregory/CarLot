import { faEdit, faEye, faEyeSlash } from "@fortawesome/free-solid-svg-icons"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import { useEffect } from "react"
import { useState } from "react"
import { passwordRegex } from "../Assets/regex"
import { AccountInfoPasswordField } from "./AccountInfoPasswordField"

export function ChangePasswordForm(props) {
  const [editing, setEditing] = useState(false)
  const [see, setSee] = useState(false)
  const [disabled, setDisabled] = useState(true)
  const [password, setPassword] = useState("")
  const [newPassword, setNewPassword] = useState("")
  const [confirmNewPassword, setConfirmNewPassword] = useState("")
  const [requiredNewPassword, setRequiredNewPassword] = useState(true)
  const [requiredConfirmNewPassword, setRequiredConfirmNewPassword] =
    useState(true)
  const msg =
    "Your new password must be at least 8 characters long, and contain at least one letter, at least one number, and at least one special character."

  useEffect(() => {
    const passwordMatches = password.match(passwordRegex)
    const newPasswordMatches = newPassword.match(passwordRegex)
    const confirmNewPasswordMatches = confirmNewPassword.match(passwordRegex)

    if (
      passwordMatches &&
      newPasswordMatches &&
      confirmNewPasswordMatches &&
      newPassword === confirmNewPassword
    ) {
      setDisabled(false)
    } else {
      setDisabled(true)
    }

    if (newPasswordMatches) {
      setRequiredNewPassword(false)
    } else {
      setRequiredNewPassword(true)
    }

    if (confirmNewPasswordMatches && newPassword === confirmNewPassword) {
      setRequiredConfirmNewPassword(false)
    } else {
      setRequiredConfirmNewPassword(true)
    }
  }, [password, newPassword, confirmNewPassword])

  const editPassword = () => {
    if (editing) return
    setEditing(true)
  }

  const handleChangePassword = (e) => {
    e.preventDefault()
  }

  const handleDiscardChanges = (e) => {
    e.preventDefault()
    const passwordInput = document.getElementById("password")
    const newPasswordInput = document.getElementById("new_password")
    const confirmNewPasswordInput = document.getElementById(
      "confirm_new_password"
    )
    passwordInput.value = ""
    newPasswordInput.value = ""
    confirmNewPasswordInput.value = ""
    setRequiredNewPassword(true)
    setRequiredConfirmNewPassword(true)
    setEditing(false)
  }

  const toggleSee = () => {
    setSee(!see)
  }

  return (
    <div className="mx-2 flex flex-col flex-nowrap">
      <div
        className={`flex auth-input float relative grow pl-2 border border-gs-accent-blue bg-white rounded pointer-events-none`}
        data-placeholder="Password"
      >
        <p
          className={`grow font-bold tracking-wider focus:outline-none pt-2.5 pb-1.5 ${
            editing === true ? "hidden" : ""
          }`}
        >
          **********
        </p>
        <input
          id="password"
          className={`grow pointer-events-auto focus:outline-none pt-2.5 pb-1.5 ${
            editing === true ? "" : "hidden"
          }`}
          type={`${see ? "text" : "password"}`}
          placeholder="**********"
          onChange={(e) => {
            setPassword(e.target.value)
          }}
        />
        <button
          className={`transition-{opacity transform} w-[45px] border-l border-gs-blue origin-right duration-200 ease-in-out ${
            editing
              ? "translate-x-3 opacity-0 pointer-events-none"
              : "translate-x-0 opacity-1 delay-300 pointer-events-auto"
          }`}
          onPointerUp={editPassword}
          title="Change password"
        >
          <FontAwesomeIcon
            icon={faEdit}
            className="pointer-events-none opacity-1 text-gs-blue"
          />
        </button>
        <div
          className={`absolute flex content-center gap-3 items-center top-2/4 -translate-y-2/4 right-0 transition-{opacity transform} duration-200 ease-out ${
            editing
              ? "translate-x-0 opacity-1 delay-300 pointer-events-auto"
              : "translate-x-3 opacity-0 pointer-events-none"
          }`}
        >
          <button
            className={`w-[45px] border-l border-gs-blue h-[40px]`}
            onPointerUp={toggleSee}
            title={`${see ? "Hide password" : "See password"}`}
          >
            {see ? (
              <FontAwesomeIcon
                icon={faEyeSlash}
                className="pointer-events-none opacity-1 text-gs-blue"
              />
            ) : (
              <FontAwesomeIcon
                icon={faEye}
                className="pointer-events-none opacity-1 text-gs-blue"
              />
            )}
          </button>
        </div>
      </div>
      <h4 className={`mt-5 mx-5 font-semibold mb-4 ${editing ? "" : "hidden"}`}>
        {msg}
      </h4>
      <div className={`mt-5 grid gap-5 ${editing ? "" : "hidden"}`}>
        <AccountInfoPasswordField
          id="new_password"
          label="New Password"
          setValue={setNewPassword}
          editing={editing}
          required={requiredNewPassword}
        />
        <AccountInfoPasswordField
          id="confirm_new_password"
          label="Confirm New Password"
          setValue={setConfirmNewPassword}
          editing={editing}
          required={requiredConfirmNewPassword}
        />
        <div className="flex items-center justify-center gap-3">
          <button
            className="block bg-black text-white text-center py-3 px-4 mb-2 rounded"
            type="submit"
            disabled={disabled ? "disabled" : null}
            onPointerUp={handleChangePassword}
          >
            Change Password
          </button>
          <button
            className="block bg-gs-accent-blue text-white text-center py-3 px-4 mb-2 rounded"
            onPointerUp={handleDiscardChanges}
          >
            Discard Changes
          </button>
        </div>
      </div>
    </div>
  )
}
