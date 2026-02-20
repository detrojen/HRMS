export  type TGlobalResponse<T> = {
     data : T,
     status : number | string,
     message : string,
     authToken : string,
     errors : {message:string}[]
}