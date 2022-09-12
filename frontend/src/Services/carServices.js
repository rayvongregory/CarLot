import axios from "axios"
import { getAccessToken, PATH } from "../Services/constants"

export const getAllCars = async () => {
  console.log("getting all cars")
  const accessToken = getAccessToken()
  await axios
    .post(`${PATH}/api/get-all-cars`, { accessToken })
    .then((res) => {})
    .catch((err) => {
      console.log(err)
    })
}

export const getFilteredResults = (filters) => {
  console.log("getting filtered results")
}

export const getCarInfo = (carId) => {
  console.log("getting all car info with car id")
  return {
    id: window.crypto.randomUUID(),
    year: 2011,
    make: "Honda",
    model: "CR-V",
    mileage: 1001005,
    exteriorColor: "gray",
    interiorColor: "black",
    drivetrain: "4WD",
    mpg: 23,
    fuelType: "gasoline",
    transmission: "automatic",
  }
}

export const getBasicCarInfo = () => {
  console.log("getting basic car info")
  return {
    id: window.crypto.randomUUID(),
    year: 2011,
    make: "Honda",
    model: "CR-V",
    mileage: 1001005,
  }
}

export const listCar = (carInfo) => {
  console.log("adding new car to the lot")
}

export const seeOffers = (carId) => {
  console.log("getting offers for car with car id")
}
