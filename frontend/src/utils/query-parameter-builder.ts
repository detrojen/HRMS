function paramsBuilder<T>(data:T) {
    if(typeof data != "object"){
        throw new Error("can not create params from this data")
    }
    let params = "?"
    for(const key in data){
        if(data[key]){
            
            if(Array.isArray(data[key])){
                for(let i =0;i<data[key].length; i++){
                    params+=key + "="+data[key][i]+"&"
                }
            }
            else{
                params+=key + "="+data[key]+"&"
            }
        }
    }
    return params
}

export default paramsBuilder