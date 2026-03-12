const resetPageSearchparams = (searchparams: URLSearchParams) => {
    searchparams.delete("page")
}

export default resetPageSearchparams