import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import { library } from "@fortawesome/fontawesome-svg-core"
import {
  faCar,
  faUser,
  faDollarSign,
  faArrowRightToBracket,
} from "@fortawesome/free-solid-svg-icons"
import React, { useEffect, useState } from "react"
import { Link } from "react-router-dom"
import "../Styles/nav.css"
import { getNumRequests } from "../Services/adminService"

export const Nav = (props) => {
  const [displayWidth, setDisplayWidth] = useState(0)
  const [numNewRequests, setNumNewRequests] = useState(0)
  library.add(faCar, faUser, faDollarSign, faArrowRightToBracket, faCar)

  useEffect(() => {
    window.addEventListener("resize", () => {
      const { innerWidth: width } = window
      if (
        (width <= 640 && displayWidth > 640) ||
        (width > 640 && displayWidth <= 640)
      ) {
        setDisplayWidth(width)
      }
    })
    window.dispatchEvent(new Event("resize"))
  }, [displayWidth])

  useEffect(() => {
    if (props.role === "admin") setNumNewRequests(getNumRequests())
  }, [numNewRequests, props.role])

  return (
    <nav className="fixed left-0 bottom-0 w-full min-h-[60px] max-w-[1500px] flex justify-center items-center flex-wrap gap-y-[10px] m-auto bg-gs-blue  text-white z-10 tablet:relative tablet:bg-white tablet:text-black tablet:z-0 desktop:bg-transparent">
      {displayWidth <= 640 ? (
        <>
          <Link to="/">
            <img className="h-[16px]" src="cars.png" alt="cars" />
            <p>Cars</p>
          </Link>
          {props.userRole !== "guest" ? (
            <Link to="/list">
              <FontAwesomeIcon icon="dollar-sign" />
              <p>Sell</p>
            </Link>
          ) : null}
          {props.userRole === "admin" ? (
            <Link
              className="num-requests relative"
              data-num-requests={numNewRequests}
              to="/requests"
            >
              <FontAwesomeIcon icon="car" />
              <p>Requests</p>
            </Link>
          ) : null}
          {props.userRole === "guest" ? (
            <Link to="/login">
              <FontAwesomeIcon icon="arrow-right-to-bracket" />
              <p>Sign In</p>
            </Link>
          ) : null}
          {props.userRole !== "guest" ? (
            <Link to="/account">
              <FontAwesomeIcon icon="user" />
              <p>Account</p>
            </Link>
          ) : null}
        </>
      ) : (
        <>
          <Link to="/">Cars for sale</Link>
          {props.userRole !== "guest" ? (
            <Link to="/list">Sell your car</Link>
          ) : null}
          {props.userRole === "admin" ? (
            <Link
              className="num-requests relative"
              data-num-requests={numNewRequests}
              to="/requests"
            >
              New Requests
            </Link>
          ) : null}
          {props.userRole === "guest" ? <Link to="/login">Sign in</Link> : null}
          {props.userRole !== "guest" ? (
            <Link to="/account">Account</Link>
          ) : null}
        </>
      )}
    </nav>
  )
}
