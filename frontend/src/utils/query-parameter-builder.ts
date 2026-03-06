function paramsBuilder<T>(data:T) {
    if(typeof data != "object"){
        throw new Error("can not create params from this data")
    }
    let params = "?"
    for(const key in data){
        if(data[key]){
            params+=key + "="+data[key]+"&"
        }
    }
    return params
}

export default paramsBuilder