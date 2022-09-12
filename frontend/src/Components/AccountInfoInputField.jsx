// import "../Styles/input-field.css"
import { faEdit, faSave, faTrash } from "@fortawesome/free-solid-svg-icons"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import { Component } from "react"
import { emailRegex } from "../Assets/regex"
import {
  updateUserEmail,
  updateUserFname,
  updateUserLname,
} from "../Services/userServices"

export class AccountInfoInputField extends Component {
  constructor(props) {
    super(props)
    this.state = {
      editing: false,
      updateMsg: "",
    }
    this.toggleEdit = this.toggleEdit.bind(this)
    this.handleSave = this.handleSave.bind(this)
    this.handleDiscard = this.handleDiscard.bind(this)
  }

  toggleEdit() {
    const { id } = this.props
    const input = document.getElementById(id)
    this.setState({ editing: !this.state.editing })
    setTimeout(() => {
      input.focus()
    }, 0)
  }

  handleSave() {
    const { id } = this.props
    const input = document.getElementById(id)
    const value = input.value.trim()

    switch (id) {
      case "fname":
        if (value !== "") {
          updateUserFname(value)
          this.props.setUpdateMsg("First name changed successfully")
          input.value = ""
          this.props.setValue(value)
        } else {
          this.props.setUpdateMsg("First name invalid")
        }
        this.setState({ editing: !this.state.editing })
        break
      case "lname":
        if (value !== "") {
          updateUserLname(value)
          this.props.setUpdateMsg("Last name changed successfully")
          input.value = ""
          this.props.setValue(value)
        } else {
          this.props.setUpdateMsg("Last name invalid")
        }
        this.setState({ editing: !this.state.editing })
        break
      case "email":
        if (value.match(emailRegex)) {
          updateUserEmail(value)
          this.props.setUpdateMsg("Email changed successfully")
          input.value = ""
          this.props.setValue(value)
        } else {
          this.props.setUpdateMsg("Email invalid")
        }
        this.setState({ editing: !this.state.editing })
        break
      default:
        break
    }
  }

  handleDiscard() {
    document.getElementById(this.props.id).value = ""
    this.setState({ editing: !this.state.editing })
  }

  render() {
    return (
      <div className="mx-2 flex flex-nowrap bg-white">
        <div
          className={`flex auth-input float relative grow pl-2 border border-gs-accent-blue rounded pointer-events-none`}
          data-placeholder={this.props.label}
        >
          <p
            className={`grow focus:outline-none pt-2.5 pb-1.5 ${
              this.state.editing === true ? "hidden" : ""
            }`}
          >
            {this.props.value}
          </p>
          <input
            id={this.props.id}
            className={`grow pointer-events-auto focus:outline-none pt-2.5 pb-1.5 ${
              this.state.editing === true ? "" : "hidden"
            }`}
            type="text"
            placeholder={this.props.value}
          />
          <button
            className={`transition-{opacity w-[45px] transform} duration-200 ease-out border-l px-3 border-gs-blue ${
              this.state.editing
                ? "opacity-0 translate-x-3 pointer-events-none"
                : "opacity-1 translate-x-0 pointer-events-auto delay-300"
            }`}
            onPointerUp={this.toggleEdit}
            title="Edit field"
          >
            <FontAwesomeIcon
              icon={faEdit}
              className="pointer-events-none opacity-1 text-gs-blue"
            />
          </button>
          <div
            className={`absolute flex content-center items-center right-0 top-2/4 -translate-y-2/4 transition-{opacity transform} origin-right duration-200 ease-out ${
              this.state.editing
                ? "translate-x-0 opacity-1 delay-300"
                : "translate-x-3 opacity-0"
            }`}
          >
            <button
              className={`w-[45px] h-[40px] border-l border-gs-blue ${
                this.state.editing ? "pointer-events-auto " : ""
              }`}
              onPointerUp={this.handleSave}
              title="Save changes"
            >
              <FontAwesomeIcon
                icon={faSave}
                className="pointer-events-none opacity-1 text-gs-blue"
              />
            </button>
            <button
              className={`w-[45px] h-[40px] border-l border-gs-blue  ${
                this.state.editing ? "pointer-events-auto " : ""
              }`}
              onPointerUp={this.handleDiscard}
              title="Discard changes"
            >
              <FontAwesomeIcon
                icon={faTrash}
                className="pointer-events-none opacity-1 text-gs-blue"
              />
            </button>
          </div>
        </div>
      </div>
    )
  }
}
