export const CarSearchForm = () => {
  return (
    <div className="car-search-form-container p-2 max-w-[1500px] m-auto">
      <h3 className="relative text-xl font-semibold w-fit m-4 mb-6 text-black">
        Search cars
      </h3>
      <div className="m-2 py-2 px-4 bg-white border border-slate-300 rounded desktop:my-2 desktop:mx-auto desktop:px-3">
        <form action="">
          <select className="border border-black rounded" name="make" id="make">
            <option value="">Make</option>
            <option value="Honda">Honda</option>
            <option value="Nissan">Nissan</option>
            <option value="Mercedez">Mercedez</option>
          </select>
        </form>
      </div>
    </div>
  )
}
