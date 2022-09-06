import { Link } from "react-router-dom"
import { CarSearchForm } from "../Components/CarSearchForm"
import { Hero } from "../Components/Hero"
import { Nav } from "../Components/Nav"
import "../Styles/hero.css"
import "../Styles/car-search-form.css"

export const HomePage = (props) => {
  console.log(props)

  return (
    <>
      <Nav role={props.user?.role} />
      <Hero />
      <h1 className="hero-msg relative text-center text-white font-semibold m-auto w-full max-w-[1500px] p-2">
        Finding a ride has never been easier.
      </h1>
      <CarSearchForm />
    </>
  )
}
