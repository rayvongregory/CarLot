import "../Styles/input-field.css"
import {
  faAsterisk,
  faEye,
  faEyeSlash,
} from "@fortawesome/free-solid-svg-icons"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import { Component } from "react"

export class InputField extends Component {
  constructor(props) {
    super(props)
    this.state = {
      see: false,
      addedClasses: "",
    }
    this.handleFocus = this.handleFocus.bind(this)
    this.toggleSee = this.toggleSee.bind(this)
    this.onChange = this.onChange.bind(this)
  }

  handleFocus() {
    this.setState({ addedClasses: "float" })
  }

  toggleSee = (e) => {
    const { target } = e
    const { previousElementSibling: input } = target
    if (this.state.see) {
      input.type = "password"
      this.setState({ see: false })
    } else {
      input.type = "text"
      this.setState({ see: true })
    }
  }

  onChange = (e) => {
    this.handleFocus(e)
    this.props.handleRequired(e)
  }

  render() {
    return (
      <div className="mx-2 flex flex-nowrap">
        <div
          className={`flex auth-input relative grow pl-2 pr-10 border border-gs-accent-blue rounded pointer-events-none ${
            this.props.required ? "" : "pr-[2px]"
          } ${this.state.addedClasses}`}
          data-placeholder={this.props.placeholder}
        >
          <FontAwesomeIcon
            icon={faAsterisk}
            className={`pointer-events-none absolute right-3 top-1/2 -translate-y-2/4 opacity-1 text-gs-dark-orange transition-{opacity} duration-150 ease-out ${
              this.props.required ? "" : "opacity-0"
            }`}
          />
          <input
            id={this.props.id}
            className="grow pointer-events-auto focus:outline-none pt-2.5 pb-1.5"
            onFocus={this.handleFocus}
            onChange={this.onChange}
            type={this.props.type}
            autoComplete=""
          />
          {this.props.type === "password" ? (
            <button
              onPointerUp={this.toggleSee}
              type="button"
              className={`pointer-events-auto w-[40px] border-gs-accent-blue border-l border-r ${
                this.props.required ? "" : "border-r-0"
              }`}
            >
              {this.state.see === false ? (
                <FontAwesomeIcon
                  icon={faEye}
                  className="pointer-events-none text-gs-dark-orange"
                />
              ) : (
                <FontAwesomeIcon
                  icon={faEyeSlash}
                  className="pointer-events-none text-gs-dark-orange"
                />
              )}
            </button>
          ) : null}
        </div>
      </div>
    )
  }
}
