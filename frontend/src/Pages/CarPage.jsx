import React from "react"
import { CarInfo } from "../Components/CarInfo"
import { Nav } from "../Components/Nav"

export const CarPage = (props) => {
  console.log(props)

  return (
    <>
      <Nav role={props.user?.role} />
      <CarInfo role={props.user?.role} />
    </>
  )
}
