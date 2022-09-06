import { ListCarForm } from "../Components/ListCarForm"
import { Nav } from "../Components/Nav"

export const ListCarPage = (props) => {
  console.log(props)

  return (
    <>
      <Nav role={props.user?.role} />
      <ListCarForm id={props.user?.id} />
    </>
  )
}
