import axios from "axios"

export const getAllCars = () => {
  console.log("getting all cars")
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

export const addCar = (carInfo) => {
  console.log("adding new car to the lot")
}

export const seeOffers = (carId) => {
  console.log("getting offers for car with car id")
}
