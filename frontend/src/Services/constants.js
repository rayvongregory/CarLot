export const PATH = "http://localhost:8080"

export const getAccessToken = () => {
  let accessToken = null
  const cookies = document.cookie.split("; ")
  for (let c of cookies) {
    const parts = c.split("=")
    if (parts[0] === "accessToken") {
      accessToken = parts[1]
      break
    }
  }
  return accessToken
}
