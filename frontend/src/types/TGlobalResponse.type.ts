export  type TGlobalResponse<T> = {
     data : T,
     status : string,
     message : string,
     authToken : string,
     errors : any
}