import {
  faAsterisk,
  faEye,
  faEyeSlash,
} from "@fortawesome/free-solid-svg-icons"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import { useEffect } from "react"
import { useState } from "react"

export function AccountInfoPasswordField(props) {
  const [see, setSee] = useState(false)

  useEffect(() => {
    setSee(false)
  }, [props.editing])

  const toggleSee = () => {
    setSee(!see)
  }

  return (
    <div className="flex flex-nowrap bg-white">
      <div
        className={`flex auth-input float relative grow pl-2 border border-gs-accent-blue rounded pointer-events-none ${
          props.required ? "pr-11" : "pr-0"
        }`}
        data-placeholder={props.label}
      >
        <input
          id={props.id}
          className={`grow pointer-events-auto focus:outline-none pt-2.5 pb-1.5`}
          type={`${see ? "text" : "password"}`}
          placeholder="**********"
          onChange={(e) => {
            props.setValue(e.target.value)
          }}
        />
        <button
          className={`transition-{border} px-3 w-[45px] border-gs-blue duration-200 ease-in-out pointer-events-auto ${
            props.required ? "border-x" : "border-l"
          }`}
          onPointerUp={toggleSee}
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

        <div
          className={`absolute flex justify-center gap-3 items-center w-[45px] h-[40px] right-0 top-2/4 -translate-y-2/4 transition-opacity duration-200 ease-out ${
            props.required ? "" : "opacity-0"
          }`}
        >
          <FontAwesomeIcon
            icon={faAsterisk}
            className="pointer-events-none opacity-1 text-gs-dark-orange"
          />
        </div>
      </div>
    </div>
  )
}
